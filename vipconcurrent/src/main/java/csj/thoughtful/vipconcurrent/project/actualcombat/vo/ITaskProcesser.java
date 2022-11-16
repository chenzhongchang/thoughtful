package csj.thoughtful.vipconcurrent.project.actualcombat.vo;

public interface ITaskProcesser<T,R> {

    TaskResult<R> taskExecute(T data);

}
