package csj.thoughtful.vipconcurrent.project.demo;

import csj.thoughtful.vipconcurrent.project.PendingJobPool;
import csj.thoughtful.vipconcurrent.project.vo.TaskResult;
import csj.thoughtful.vipconcurrent.tools.SleepTools;

import java.util.List;
import java.util.Random;

public class AppTest {
    private final static String JOB_NAME="计算数值";
    private final static int JOB_LENGTH=1000;

    //查询任务进度的线程
    private static class QueryResult implements Runnable{
        private PendingJobPool pool;

        public QueryResult (PendingJobPool pool){
            super();
            this.pool=pool;
        }

        @Override
        public void run() {
            int i=0;//查询次数
            while(i<350){
                List<TaskResult<String>> taskDetail = pool.getTaskDetail(JOB_NAME);
                if(!taskDetail.isEmpty()){
                    System.out.println(pool.getTaskProcess(JOB_NAME));
                    System.out.println(taskDetail);
                }
                SleepTools.ms(100);
                i++;
            }
        }
    }

    public static void main(String[] args) {
        MyTask myTask=new MyTask();
        //拿到框架的实例
        PendingJobPool pool = PendingJobPool.getInstance();
        //注册job
        pool.registerJob(JOB_NAME, JOB_LENGTH, myTask, 1000*5);
        Random r = new Random();
        for (int i=0;i<JOB_LENGTH;i++){
            //依次推入Task
            pool.putTask(JOB_NAME, r.nextInt(1000));
        }
        Thread t = new Thread(new QueryResult(pool));
        t.start();
        //第十一课 第二个视频 30分钟
    }

}
