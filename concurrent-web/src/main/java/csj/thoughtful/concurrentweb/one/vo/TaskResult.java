package csj.thoughtful.concurrentweb.one.vo;

public class TaskResult<R> {

    private final TaskResultType resultType;
    private final R returnValue;
    private final String reason;

    public TaskResult(TaskResultType resultType,R returnValue,String reason){
        this.resultType=resultType;
        this.returnValue=returnValue;
        this.reason=reason;
    }

    public TaskResultType getResultType() {
        return resultType;
    }

    public R getReturnValue() {
        return returnValue;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "resultType='" + resultType + '\'' +
                ", returnValue=" + returnValue +
                ", reason='" + reason + '\'' +
                '}';
    }
}
