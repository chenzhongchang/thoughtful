package csj.thoughtful.vipconcurrent.project.actualcombat;

import csj.thoughtful.vipconcurrent.delayqueue.blockingqueue.ItemVo;

import java.util.concurrent.DelayQueue;

public class CheckJobProcesser {

    private static DelayQueue<ItemVo<String>> queue=new DelayQueue<>();

    public CheckJobProcesser(){}

    private static class CheckJobHolder{
        private static CheckJobProcesser checkJobProcesser = new CheckJobProcesser();
    }

    public static CheckJobProcesser getInstance(){
        return CheckJobHolder.checkJobProcesser;
    }

    private static class FetchJob implements Runnable{
        @Override
        public void run() {
            while (true){
                try {
//                    ItemVo<String> itemVo = queue.take();
//                    String jobName=itemVo.getData();
//                    PendingJobPool.getMap().remove();
//                    System.out.println(jobName+"data is out of, remove from map!");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void putJob(String jobName, long expireTime){
        ItemVo<String> item = new ItemVo<String>(expireTime,jobName);
        queue.offer(item);
        System.out.println("Job["+jobName+"已经放入了过期检查缓存，过期时长："+expireTime+"]");
    }

    static{
        Thread thread = new Thread(new FetchJob());
        thread.setDaemon(true);
        thread.start();
        System.out.println("已开启任务过期检查守护进程.......");
    }

}
