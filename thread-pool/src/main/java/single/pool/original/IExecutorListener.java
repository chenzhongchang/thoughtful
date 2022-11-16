package single.pool.original;

import java.util.concurrent.Callable;

public interface IExecutorListener {
	
	public void before(Runnable r);
	
	public void before(Callable c);
	
	public void after(Runnable r);
	
	public void after(Callable c);
	
	public void exception(Runnable r,Exception ex);
	
	public void exception(Callable c,Exception ex);

}
