package csj.thoughtful.vipconcurrent.project.actualcombat.vo;

public class TaskResult<R> {
    private final TaskResultType returnType;
    private final R resultValue;
    private final String reason;

    public TaskResult(TaskResultType returnType, R resultValue, String reason) {
        this.returnType = returnType;
        this.resultValue = resultValue;
        this.reason = reason;
    }

    public TaskResult(TaskResultType returnType, R resultValue) {
        this.returnType = returnType;
        this.resultValue = resultValue;
        this.reason = "Success";
    }

    public TaskResultType getReturnType() {
        return returnType;
    }

    public R getResultValue() {
        return resultValue;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "returnType=" + returnType +
                ", resultValue=" + resultValue +
                ", reason='" + reason + '\'' +
                '}';
    }
}
