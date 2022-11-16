package csj.thoughtful.vipconcurrent.threadpool.cdl;

//饿汉式   在JVM中， 对类的加载和类的初始化，由虚拟机保证线程安全
public class SingleEHan {//如果类很大，占用的内存空间很多，使用类初始化模式
    public  static SingleEHan singleEHan = new SingleEHan();
    private SingleEHan(){

    }

}
