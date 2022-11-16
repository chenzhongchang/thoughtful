package csj.thoughtful.vipconcurrent.project.vo;

//方法本身运行是否正确的结果类型
public enum TaskResultType {
    Success,Failure,Exception;
    //方法返回了业务人员需要的结果
    //方法返回了业务人员不需要的结果
    //方法抛出了Exception
}
