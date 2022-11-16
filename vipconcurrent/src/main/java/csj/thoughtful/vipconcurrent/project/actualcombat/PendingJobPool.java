package csj.thoughtful.vipconcurrent.project.actualcombat;



import csj.thoughtful.vipconcurrent.project.actualcombat.vo.ITaskProcesser;
import csj.thoughtful.vipconcurrent.project.actualcombat.vo.JobInfo;
import csj.thoughtful.vipconcurrent.project.actualcombat.vo.TaskResult;
import csj.thoughtful.vipconcurrent.project.actualcombat.vo.TaskResultType;

import java.util.concurrent.*;

public class PendingJobPool {

    private final int THREAD_COUNTS = Runtime.getRuntime().availableProcessors();
    private final BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<Runnable>(5000);
    private ExecutorService taskExecute = new ThreadPoolExecutor(THREAD_COUNTS,THREAD_COUNTS,60,
            TimeUnit.SECONDS,taskQueue);

    private  ConcurrentHashMap<String,JobInfo<?>> jobInfoMap = new ConcurrentHashMap<String,JobInfo<?>>();

    private CheckJobProcesser checkJob = CheckJobProcesser.getInstance();

    public PendingJobPool(){}

    private static class JobPoolHolder{
        private static PendingJobPool pool=new PendingJobPool();
    }

    public static PendingJobPool getInstance(){
        return JobPoolHolder.pool;
    }

    private class TaskPending<T,R> implements Runnable{

        private JobInfo<R> jobInfo;
        private T processData;

        public TaskPending(JobInfo<R> jobInfo,T processData){
            this.jobInfo=jobInfo;
            this.processData=processData;
        }

        @Override
        public void run() {
            R r=null;
            ITaskProcesser taskProcesser = jobInfo.getTaskProcesser();
            TaskResult<R> result=null;
            try {
                result=taskProcesser.taskExecute(processData);
                if(result==null){
                    result = new TaskResult<R>(TaskResultType.Exception,r,
                            "result is null");
                }
                if(result.getReturnType()==null)
                {
                    if(result.getReason()==null){
                        result = new TaskResult<R>(TaskResultType.Exception,r,
                                "result is null");
                    }else{
                        result = new TaskResult<R>(TaskResultType.Exception,r,
                                "result is null,but reason:"+result.getReason());
                    }
                }
            }catch (Exception e){
                result = new TaskResult<R>(TaskResultType.Exception,r,
                        e.getMessage());
            }finally {
                jobInfo.addTaskResult(result,checkJob);
            }
        }
    }

}
