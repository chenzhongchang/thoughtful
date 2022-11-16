package forkjoin;

import javafx.concurrent.Task;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {
    private static class TaskCount extends RecursiveTask<Integer>{
        private static final int MIN_THRESHOLD=5;
        private int start;
        private int end;

        TaskCount(int start, int end){
            this.start=start;
            this.end=end;
        }

        @Override
        protected Integer compute() {
            int result =0;
            boolean shouldCompute = (end-start) <= MIN_THRESHOLD;
            //如果任务小于阈值则计算任务
            if(shouldCompute){
                for (int i=start; i<=end; i++){
                    result +=i;
                }
            }//大于阈值则继续切分任务
            else{
                int middle=(start+end) >>> 1;
                TaskCount taskA = new TaskCount(start,middle);
                TaskCount taskB = new TaskCount(middle+1,end);
                //将任务加入工作队列，被工作线程执行，fork方法立即返回不阻塞
                taskA.fork();
                taskB.fork();

                //阻塞,直到任务被完成或取消
                Integer resultA = taskA.join();
                Integer resultB = taskB.join();

                //合并子任务
                result = resultA+resultB;
            }
            return result;
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool=new ForkJoinPool();
        //从 1-100的累加大任务
        TaskCount task = new TaskCount(1,100);
        //提交到ForkJoinPool
        Future<Integer> result=pool.submit(task);

        try {
            System.out.println(result.get());
        }catch (InterruptedException e){
            System.out.println("忽略中断异常");
        }catch (ExecutionException e){
            System.out.println("忽略中断异常");
        }
        if(task.isCompletedAbnormally()){
            System.out.println(task.getException().getMessage());
        }
    }

}
