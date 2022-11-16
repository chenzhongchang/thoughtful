package single.pool.original;

import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author yangtao
 *
 */
public interface IExecutor {
	
	public void execute(String src,Runnable task);

	public <T> T executeSync(String src,Callable<T> callable);
	
	public void execute(String src,IRunnable task);
	
	public int getIRunnableTaskCount(String src,String tag);
	
	public int getIRunnableTaskCount(String src);
	
	public Set<IRunnable> getIRunnableTasks(String src,String tag);
	
	public <T> Future<T> executeSyncRetureFuture(String src, Callable<T> task);
	
	public String getName();
	
	public int getPoolSize();
	
	public boolean isFused();
	
	public int setFuseValue(int fuseValue);
	
	public int getFuseValue();
	
	public void setWarningValue(int warningValue);
	
	public int getWarningValue();
	
	public void addIExecutorListener(IExecutorListener executorListener);
	
	public void removeIExecutorListener(IExecutorListener executorListener);
	
	public void clearTask(String src);

}