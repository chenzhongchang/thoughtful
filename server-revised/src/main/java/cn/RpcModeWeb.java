package cn;


import java.util.List;
import java.util.concurrent.*;

/**
 *@author Mark老师   享学课堂 https://enjoy.ke.qq.com 
 *
 *类说明：rpc服务端，采用生产者消费者模式，生产者消费者还会级联
 */
public class RpcModeWeb {
	
	//负责生成文档
	private static ExecutorService docMakeService 
		= Executors.newFixedThreadPool(Consts.CPU_COUNT*2);
	
	private static CompletionService<String> docCs
	    = new ExecutorCompletionService<>(docMakeService); 
	
	public static void main(String[] args) throws InterruptedException,
	ExecutionException {
        System.out.println("题库开始初始化...........");
        PwdDictionary.initBank();
        System.out.println("题库初始化完成。");
        
        //创建两个待处理文档
        List<SrcDocVo> docList = CreatePendingDocs.makePendingDoc(60);
        long startTotal = System.currentTimeMillis();
        
        for(SrcDocVo doc:docList){
        	docCs.submit(new MakeDocTask(doc));
        }

        System.out.println("------------共耗时："
        		+(System.currentTimeMillis()-startTotal)+"ms-------------");
	}
	
	//生成文档的任务
	private static class MakeDocTask implements Callable<String>{
		
		private SrcDocVo pendingDocVo;
		
		public MakeDocTask(SrcDocVo pendingDocVo) {
			super();
			this.pendingDocVo = pendingDocVo;
		}

		@Override
		public String call() throws Exception {
			long start = System.currentTimeMillis();
            //String localName = ProduceDocService.makeDoc(pendingDocVo);
			String localName = ProduceDocService.makeDocAsyn(pendingDocVo);
            System.out.println("文档"+localName+"生成耗时："
            		+(System.currentTimeMillis()-start)+"ms");
			return localName;
		}
	}
	

	
	

}
