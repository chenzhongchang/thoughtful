package csj.thoughtful.vipconcurrent.project.two;

import csj.thoughtful.vipconcurrent.project.two.assist.Consts;
import csj.thoughtful.vipconcurrent.project.two.service.ProduceDocService;
import csj.thoughtful.vipconcurrent.project.two.vo.SrcDocVo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

//rpc服务端，采用生产者消费者模式，生产者消费者还会级联
public class RpcModeWeb {

    //负责生成文档
    private static ExecutorService docMakeService
            = Executors.newFixedThreadPool(Consts.CPU_COUNT*2);

    //负责上传文档
    private static ExecutorService docUploadService
            = Executors.newFixedThreadPool(Consts.CPU_COUNT*2);

    //
    private static class MakeDocTask implements Callable<String> {

        private SrcDocVo pendingDocVo;

        public MakeDocTask(SrcDocVo pendingDocVo){
            super();
            this.pendingDocVo = pendingDocVo;
        }

        @Override
        public String call() throws Exception {
//            System.out.println("开始处理文档："+doc.getDocName()+".......");
//            long start = System.currentTimeMillis();
//            String localName = ProduceDocService.makeDoc(doc);
//            System.out.println("文档"+localName+"生成耗时："
//                    +(System.currentTimeMillis()-start)+"ms");
            return null;
        }
    }


}
