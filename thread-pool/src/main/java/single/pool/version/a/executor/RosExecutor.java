package single.pool.version.a.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import single.pool.version.a.common.executor.IExecutor;
import single.pool.version.a.common.executor.IExecutorImpl;

/**
 * <p>
 * Title: ApExecutor
 * </p>
 * <p>
 * Description:
 * ApExecutor
 * </p>
 * 
 * @author yangtao
 * @created 2017年9月28日 上午9:21:58
 * @modified [who date description]
 * @check [who date description]
 */
public class RosExecutor {
    private static final int pool_size = Runtime.getRuntime().availableProcessors()*20;
    private static IExecutor executor=new IExecutorImpl("RosExecutor", pool_size);
    public static void execute(String mac,Runnable runnable) {
        executor.execute(mac,runnable);
    }
    
    public static <T> Future<T> executeSyncReturenFuture(String mac,Callable<T> callable){
    	return executor.executeSyncRetureFuture(mac, callable);
    }
    public static <T> void executeSync(String mac,Callable<T> callable){
    	executor.executeSync(mac, callable);
    }
}
