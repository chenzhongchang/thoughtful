package single.pool.original;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.dubbo.common.utils.ConcurrentHashSet;
import com.alibaba.rocketmq.shade.io.netty.util.internal.ConcurrentSet;
import single.pool.original.util.DateUtils;
import single.pool.original.util.ExceptionUtils;
import single.pool.original.vo.DoubleComException;

/**
 * @author yangtao
 *
 */
public class IExecutorImpl implements IExecutor {

    private String name;
    private int pool_size;
    private AtomicLong count = new AtomicLong();
    private final static Logger logger = LoggerFactory.getLogger(IExecutorImpl.class);
    private ISingleThreadPoolExecutor[] executors = null;
    private final Set<IExecutorListener> executorListeners = new ConcurrentSet<IExecutorListener>();
    private volatile boolean fused = false;
    private volatile int fuseValue = 1000;
    private volatile int warningValue = 500;
    private volatile int warningTime=50;//任务执行时间超过50毫秒,平台打印警告提示
    private volatile int criticalTime=100;//任务执行时间超过100毫秒,打印严重提示

    public IExecutorImpl(String name, int pool_size) {
        this.name = name;
        this.pool_size = pool_size;
        if (executors == null) {
            executors = new ISingleThreadPoolExecutor[pool_size];
            for (int i = 0; i < executors.length; i++) {
                executors[i] = new ISingleThreadPoolExecutor();
            }
        }
    }

    public int getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(int warningTime) {
        this.warningTime = warningTime;
    }

    public int getCriticalTime() {
        return criticalTime;
    }

    public void setCriticalTime(int criticalTime) {
        this.criticalTime = criticalTime;
    }

    @Override
    public void execute(String src, Runnable task) {
        if (!executeBefore())
            throw new DoubleComException("Executor has been fused");
        for (IExecutorListener executorListener : executorListeners) {
            try {
                executorListener.before(task);
            } catch (Exception ex) {
                logger.error("", ex);
            }
        }
        executors[Math.abs(src.hashCode() % pool_size)].execute(new RunableWapper(src, task));

    }

    @Override
    public void execute(String src, IRunnable task) {
        execute(src, task);
    }

    @Override
    public void clearTask(String src) {
        ISingleThreadPoolExecutor executor = executors[Math.abs(src.hashCode() % pool_size)];
        executor.shutdown();
        executors[Math.abs(src.hashCode() % pool_size)] = new ISingleThreadPoolExecutor();

    }

    @Override
    public <T> T executeSync(String src, Callable<T> task) {
        if (!executeBefore())
            throw new DoubleComException("Executor has been fused");
        for (IExecutorListener executorListener : executorListeners) {
            try {
                executorListener.before(task);
            } catch (Exception ex) {
                logger.error("", ex);
            }
        }
        Future<T> future = executors[Math.abs(src.hashCode() % pool_size)].submit(new CallableWapper<T>(src, task));
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error("", e);
            throw new DoubleComException(ExceptionUtils.getMessage(e));
        }

    }

    @Override
    public <T> Future<T> executeSyncRetureFuture(String src, Callable<T> task) {
        if (!executeBefore())
            throw new DoubleComException("Executor has been fused");
        for (IExecutorListener executorListener : executorListeners) {
            try {
                executorListener.before(task);
            } catch (Exception ex) {
                logger.error("", ex);
            }
        }
        Future<T> future = ((ExecutorService) executors[Math.abs(src.hashCode() % pool_size)])
                .submit(new CallableWapper<T>(src, task));
        return future;
    }

    @Override
    public int getIRunnableTaskCount(String src, String tag) {
        ISingleThreadPoolExecutor threadPoolExecutor = executors[Math.abs(src.hashCode() % pool_size)];
        Map<String, Map<String, Set<IRunnable>>> runnables = threadPoolExecutor.getRunnables();
        if (!runnables.containsKey(tag))
            return 0;
        else {
            Map<String, Set<IRunnable>> map = runnables.get(tag);
            if (!map.containsKey(src))
                return 0;
            return map.get(src).size();
        }
    }

    @Override
    public int getIRunnableTaskCount(String src) {
        int count = 0;
        ISingleThreadPoolExecutor threadPoolExecutor = executors[Math.abs(src.hashCode() % pool_size)];
        Map<String, Map<String, Set<IRunnable>>> runnables = threadPoolExecutor.getRunnables();
        for (Entry<String, Map<String, Set<IRunnable>>> entry : runnables.entrySet()) {
            if (entry.getValue().containsKey(src)) {
                count = count + entry.getValue().get(src).size();
            }
        }
        return count;
    }

    public Set<IRunnable> getIRunnableTasks(String src, String tag) {
        ISingleThreadPoolExecutor threadPoolExecutor = executors[Math.abs(src.hashCode() % pool_size)];
        Map<String, Map<String, Set<IRunnable>>> runnables = threadPoolExecutor.getRunnables();
        if (!runnables.containsKey(tag))
            return null;
        else {
            Map<String, Set<IRunnable>> map = runnables.get(tag);
            if (!map.containsKey(src))
                return null;
            return map.get(src);
        }
    }

    private boolean executeBefore() {
        Long taskNum = count.get();
        if (taskNum == 0) {
            fused = false;
        }
        if (fused) {
            return false;
        }

        if (taskNum > fuseValue) {
            logger.error(name + " task count fused==" + count.get(), "fuse");
            fused = true;
            return false;
        }

        if (taskNum > warningValue) {
            logger.info(name + " task count warning==" + count.get());
            for (ISingleThreadPoolExecutor threadPoolExecutor : executors) {
                Map<String, Map<String, Set<IRunnable>>> runnables = threadPoolExecutor.getRunnables();
                for (Entry<String, Map<String, Set<IRunnable>>> entry : runnables.entrySet()) {
                    logger.info("task src=" + entry.getKey() + " tag=" + entry.getValue().keySet());// 打印任务内部状态
                }
            }
        }

        count.incrementAndGet();
        return true;
    }

    @Override
    public boolean isFused() {
        return fused;
    }

    @Override
    public int setFuseValue(int fuseValue) {
        this.fuseValue = fuseValue;
        return fuseValue;
    }

    @Override
    public int getFuseValue() {
        return fuseValue;
    }

    @Override
    public void setWarningValue(int warningValue) {
        this.warningValue = warningValue;

    }

    @Override
    public int getWarningValue() {
        return this.warningValue;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPoolSize() {
        return pool_size;
    }

    @Override
    public void addIExecutorListener(IExecutorListener executorListener) {
        executorListeners.add(executorListener);
    }

    @Override
    public void removeIExecutorListener(IExecutorListener executorListener) {
        executorListeners.remove(executorListener);

    }

    private class RunableWapper implements Runnable {
        private Runnable runnable;
        private String src;
        private String startTime;
        private String endTime;

        public RunableWapper(String src, Runnable runnable) {
            this.runnable = runnable;
            this.src = src;

        }

        public String getSrc() {
            return src;
        }

        public Runnable getRunnable() {
            return runnable;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            startTime=DateUtils.getLongDate(start);
            logger.info("开始执行任务 Class="+runnable.getClass().getName());
            try {
                runnable.run();
                for (IExecutorListener executorListener : executorListeners) {
                    try {
                        executorListener.after(runnable);
                    } catch (Exception ex1) {
                        logger.error("", ex1);
                    }
                }
            } catch (Exception ex) {
                logger.error("", ex);
                for (IExecutorListener executorListener : executorListeners) {
                    try {
                        executorListener.exception(runnable, ex);
                    } catch (Exception ex1) {
                        logger.error("", ex1);
                    }
                }
            } finally {
                count.decrementAndGet();
                long end = System.currentTimeMillis();
                long time=end-start;
                if (time>= warningTime&&time<criticalTime) {
                    logger.warn("线程池任务警告,执行时间:"+time+"ms,Class="+ runnable.getClass().getName());
                }else if(time>=criticalTime) {
                    logger.warn("线程池任务过慢,执行时间:"+time+"ms,Class="+ runnable.getClass().getName());
                }
                endTime= DateUtils.getLongDate(end);
                logger.info("结束执行任务 Class="+runnable.getClass().getName());
            }

        }

        public String toString() {
            return "Class:"+runnable.getClass()+",startTime="+this.startTime+",endTime="+this.endTime;
        }

    }

    private class CallableWapper<T> implements Callable<T> {
        private Callable<T> callable;
        private String src;
        private String startTime;
        private String endTime;
        public CallableWapper(String src, Callable<T> callable) {
            this.callable = callable;
            this.src = src;

        }

        public String getSrc() {
            return src;
        }

        @Override
        public T call() {
            long start = System.currentTimeMillis();
            startTime=DateUtils.getLongDate(start);
            T result = null;
            try {
                result = callable.call();
                for (IExecutorListener executorListener : executorListeners) {
                    try {
                        executorListener.after(callable);
                    } catch (Exception ex1) {
                        logger.error("", ex1);
                    }
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                for (IExecutorListener executorListener : executorListeners) {
                    try {
                        executorListener.exception(callable, ex);
                    } catch (Exception ex1) {
                        logger.error("", ex1);
                    }
                }
                if (ex instanceof RuntimeException) {
                    throw (RuntimeException) ex;
                } else
                    throw new DoubleComException(ex);
            } finally {
                count.decrementAndGet();
                long end = System.currentTimeMillis();
                long time=end-start;
                if (time>= warningTime&&time<criticalTime) {
                    logger.warn("线程池任务警告,执行时间:"+time+"ms,执行Java类:"+ callable.getClass().getSimpleName());
                }else if(time>=criticalTime) {
                    logger.warn("线程池任务过慢,执行时间:"+time+"ms,执行Java类:"+ callable.getClass().getSimpleName());
                }
                endTime=DateUtils.getLongDate(end);
            }

            return result;

        }

    }

    class ISingleThreadPoolExecutor extends ThreadPoolExecutor {
        private final Map<String, Map<String, Set<IRunnable>>> mRunnables = new ConcurrentHashMap<String, Map<String, Set<IRunnable>>>();

        public ISingleThreadPoolExecutor() {
            super(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        }

        public Map<String, Map<String, Set<IRunnable>>> getRunnables() {
            return new HashMap<String, Map<String, Set<IRunnable>>>(mRunnables);
        }

        public void execute(Runnable command) {
            if (command instanceof RunableWapper) {
                RunableWapper runableWapper = (RunableWapper) command;
                Runnable runnable = runableWapper.getRunnable();
                if (runnable instanceof IRunnable) {
                    IRunnable mRunnable = (IRunnable) runnable;
                    if (!mRunnables.containsKey(mRunnable.getTag()))
                        mRunnables.put(mRunnable.getTag(), new ConcurrentHashMap<String, Set<IRunnable>>());
                    Map<String, Set<IRunnable>> map = mRunnables.get(mRunnable.getTag());
                    if (!map.containsKey(mRunnable.getSrc()))
                        map.put(mRunnable.getSrc(), new ConcurrentHashSet<IRunnable>());
                    map.get(mRunnable.getSrc()).add(mRunnable);
                }
            }
            super.execute(command);
        }

        protected void afterExecute(Runnable command, Throwable t) {
            if (command instanceof RunableWapper) {
                RunableWapper runableWapper = (RunableWapper) command;
                Runnable runnable = runableWapper.getRunnable();
                if (runnable instanceof IRunnable) {
                    IRunnable mRunnable = (IRunnable) runnable;
                    Map<String, Set<IRunnable>> map = mRunnables.get(mRunnable.getTag());
                    if (map == null)
                        return;
                    Set<IRunnable> mrSet = map.get(mRunnable.getSrc());
                    if (mrSet == null)
                        return;
                    mrSet.remove(mRunnable);
                    if (mrSet.size() == 0) {
                        map.remove(mRunnable.getSrc());
                    }
                }

            }
        }
    }

}
