package csj.thoughtful.concurrentweb.one.vo;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskInfo<R> {

    private final String jobName;
    private final int length;
    private ITaskProcesser<?,?> taskProcesser;
    private final BlockingDeque<TaskResult> taskQueue;
    private final AtomicInteger successCount;
    private final AtomicInteger taskProcesserCount;
    private final long expireTime;

    public TaskInfo(String jobName, int length, ITaskProcesser<?, ?> taskProcesser, long expireTime) {
        this.jobName = jobName;
        this.length = length;
        this.taskProcesser=taskProcesser;
        this.taskQueue = new LinkedBlockingDeque<TaskResult>(length);
        this.successCount = new AtomicInteger(0);
        this.taskProcesserCount = new AtomicInteger(0);
        this.expireTime = expireTime;
    }

    public ITaskProcesser<?, ?> getTaskProcesser() {
        return taskProcesser;
    }

    public int getSuccessCount(){
        return successCount.get();
    }

    public int getProcesserCount(){
        return taskProcesserCount.get();
    }

    public int getFailureCount(){
        return taskProcesserCount.get()-successCount.get();
    }

    private class JobInfo<T,R> implements Runnable{

        private T processData;
        private TaskInfo<R> taskInfo;

        public JobInfo(T processData, TaskInfo<R> taskInfo) {
            this.processData = processData;
            this.taskInfo = taskInfo;
        }

        @Override
        public void run() {
//            R r=null;
//            ITaskProcesser<T,R> taskProcesser= (ITaskProcesser<T, R>) taskInfo.getTaskProcesser();
//            TaskResult<R> result;
//            try {
//                result= (TaskResult<R>) taskProcesser.taskException(processData);
//                if(result==null){
//                    result = new TaskResult<>(TaskResultType.Execute,r,"result is null");
//                }else{
//                    if(result.getReason()==null){
//                        result = new TaskResult<>(TaskResultType.Execute,r, "reason is null");
//                    }else{
//                        result = new TaskResult<>(TaskResultType.Execute,r, "result is null but reason:"+result.getReason());
//                    }
//                }
//
//            }catch (Exception e){
//                result = new TaskResult<>(TaskResultType.Execute,r, "reason:"+e.getMessage());
//            }
//            taskQueue.addLast(result);
        }
    }

    public String getTotalProcess(){
        return "success["+successCount.get()+"]/current["+taskProcesserCount.get()+"]/total["+length+"]";
    }

    public List<TaskResult<R>> getTaskDetail(){
        List<TaskResult<R>> result = new LinkedList<>();
        TaskResult<R> taskResult=null;
//        while((taskResult=taskQueue.pollFirst())!=null){
//            result.add(taskResult);
//        }
        return result;
    }

    public void addTaskResult(TaskResult<R> result){
//        if(TaskResultType.Success.equals(result.getResultType())){
//            successCount.incrementAndGet();
//        }
        taskProcesserCount.incrementAndGet();
    }


}
