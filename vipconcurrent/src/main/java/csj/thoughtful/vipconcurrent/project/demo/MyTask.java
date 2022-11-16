package csj.thoughtful.vipconcurrent.project.demo;

import csj.thoughtful.vipconcurrent.project.vo.ITaskProcesser;
import csj.thoughtful.vipconcurrent.project.vo.TaskResult;
import csj.thoughtful.vipconcurrent.project.vo.TaskResultType;
import csj.thoughtful.vipconcurrent.tools.SleepTools;

import java.util.Random;

public class MyTask implements ITaskProcesser<Integer,Integer> {

    @Override
    public TaskResult<Integer> taskExecute(Integer data) {
        Random r = new Random();
        int flag = r.nextInt(500);
        SleepTools.ms(flag);
        if(flag<=300){//正常处理的情况
            Integer returnValue=data.intValue()+flag;
            return new TaskResult<Integer>(TaskResultType.Success,returnValue);
        }else if(flag>301&&flag<=400){
            return new TaskResult<>(TaskResultType.Failure,-1,"Failure");
        }else {//发生异常的情况
            try {
                throw new RuntimeException("异常发生了！！");
            }catch (Exception e){
                return new TaskResult<>(TaskResultType.Exception,-1,e.getMessage());
            }
        }
    }
}
