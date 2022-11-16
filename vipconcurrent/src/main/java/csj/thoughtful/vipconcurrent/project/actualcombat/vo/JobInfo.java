package csj.thoughtful.vipconcurrent.project.actualcombat.vo;

import csj.thoughtful.vipconcurrent.project.actualcombat.CheckJobProcesser;
import javafx.concurrent.Task;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class JobInfo<R> {

    private final String jobName;
    private final int jobLength;
    private final ITaskProcesser<?,?> taskProcesser;
    private AtomicInteger successCount;
    private AtomicInteger taskProcessCount;
    private final LinkedBlockingDeque<TaskResult<R>> taskDetailQueue;
    private final long expireTime;

    public JobInfo(String jobName, int jobLength, ITaskProcesser<?, ?> taskProcesser, long expireTime) {
        this.jobName = jobName;
        this.jobLength = jobLength;
        this.taskProcesser = taskProcesser;
        this.taskDetailQueue = new LinkedBlockingDeque<>(jobLength);
        this.successCount = new AtomicInteger(0);
        this.taskProcessCount = new AtomicInteger(0);
        this.expireTime = expireTime;
    }

    public ITaskProcesser<?, ?> getTaskProcesser() {
        return taskProcesser;
    }

    public int getSuccessCount() {
        return successCount.get();
    }

    public int getTaskProcessCount() {
        return taskProcessCount.get();
    }

    public int getFailureCount() {
        return taskProcessCount.get()-successCount.get();
    }

    public String getTotalProcess(){
        return "Success["+successCount.get()+"]/Current"+taskProcessCount.get()+"]/Total["+jobLength+"]";
    }

    public List<TaskResult<R>> getTaskDetail(){
        List<TaskResult<R>> result=new LinkedList<>();
        TaskResult<R> taskResult;
        while((taskResult=taskDetailQueue.pollFirst())!=null){
            result.add(taskResult);
        }
        return result;
    }

    public void addTaskResult(TaskResult<R> taskResult, CheckJobProcesser checkJob){
        if(TaskResultType.Success.equals(taskResult.getReturnType())){
            successCount.incrementAndGet();
        }
        taskProcessCount.incrementAndGet();
        taskDetailQueue.addLast(taskResult);
        if(taskProcessCount.get()==jobLength){
            checkJob.putJob(jobName,jobLength);
        }
    }

}
