package custom.pool.vo;

//要求框架使用者实现的任务接口，因为任务的性质在调用时才知道，
//所以传入的参数和方法的放回值均使用泛型
public interface ITaskProcesser <T, R> {

    // data调用方法需要使用的业务数据
    // return 方法执行后业务方法的结果
    TaskResult<R> taskExecute(T data);

}
