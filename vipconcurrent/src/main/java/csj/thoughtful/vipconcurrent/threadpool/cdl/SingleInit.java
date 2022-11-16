package csj.thoughtful.vipconcurrent.threadpool.cdl;

// 懒汉式-类初始化模式/也叫延长实例
public class SingleInit {
    private SingleInit(){}

    //定义一个私有类，来持有当前类的实例
    private static class InstanceHolder{
        public static SingleInit instance = new SingleInit();
    }

    public static SingleInit getInstance(){
        return InstanceHolder.instance;
    }


}
