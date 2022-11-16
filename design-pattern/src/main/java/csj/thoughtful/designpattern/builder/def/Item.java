package csj.thoughtful.designpattern.builder.def;

//创建一个表示食物条目和食物包装的接口。
public interface Item {
   public String name();
   public Packing packing(); //包装
   public float price();    //价格
}