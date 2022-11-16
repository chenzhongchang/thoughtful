package single.pool.original.executor;

import single.pool.original.IExecutor;
import single.pool.original.IExecutorImpl;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class RosExecutor {
    private static final int pool_size = Runtime.getRuntime().availableProcessors()*20;
    private static IExecutor executor=new IExecutorImpl("RosExecutor", pool_size);
    public static void execute(String mac,Runnable runnable) {
        executor.execute(mac,runnable);
    }
    
    public static <T> Future<T> executeSyncReturenFuture(String mac, Callable<T> callable){
    	return executor.executeSyncRetureFuture(mac, callable);
    }
    public static <T> void executeSync(String mac,Callable<T> callable){
    	executor.executeSync(mac, callable);
    }
}