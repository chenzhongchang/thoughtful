package csj.thoughtful.vipconcurrent.threadpool.cdl;
//懒汉式-双重检查
public class SingleDcl {
    private int a;
//    private User user; 这个对象的域不一定赋值完成了
    private volatile static SingleDcl singleDcl;
    private SingleDcl() {

    }

    public static SingleDcl getInstance(){//看起来毫无破绽，实际并未实现双重检查
        if(singleDcl==null){
            synchronized(SingleDcl.class){//类锁
                singleDcl=new SingleDcl();
            }
        }
        return singleDcl;
    }

    //singleDcl.getUser.getId()-->NullException
    //解决办法：加volatil

}
