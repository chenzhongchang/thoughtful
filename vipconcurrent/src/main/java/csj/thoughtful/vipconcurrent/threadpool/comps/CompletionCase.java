package csj.thoughtful.vipconcurrent.threadpool.comps;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CompletionCase {
    private final int POOL_SIZE=Runtime.getRuntime().availableProcessors();
    private final int TOTAL_TASK=Runtime.getRuntime().availableProcessors()*10;

    //
    public void testByQueue() throws Exception {
        long start = System.currentTimeMillis();
        //统计所有任务休眠的总时长
        AtomicInteger count = new AtomicInteger(0);
        //
        ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);
        //
        BlockingQueue<Future<Integer>> queue = new LinkedBlockingQueue<>();

        // 向里面扔任务
        for (int i=0;i<TOTAL_TASK; i++){
            Future<Integer> future = pool.submit(new WorkTask("ExecTask" + i));
                    queue.add(future);//i=0 先进队列， i=1的任务跟着进
        }

        // 检查线程池任务执行结果
        for (int i=0;i<TOTAL_TASK; i++){
            int sleptTime = queue.take().get();// i=0先取到， i=1的后取到
            System.out.println(" slept "+sleptTime+" ms ...");
            count.addAndGet(sleptTime);
        }

        // 检查线程池任务执行结果
        for (int i=0; i < TOTAL_TASK; i++){
            int sleptTime=queue.take().get();

        }

    }

}
