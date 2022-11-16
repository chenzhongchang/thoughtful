package csj.thoughtful.concurrentweb.one.vo;

public interface ITaskProcesser<T,R> {
    TaskResult<R> taskException(T data);
}
