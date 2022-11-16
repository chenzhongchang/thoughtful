package csj.thoughtful.designpattern.proxy.test;

import csj.thoughtful.designpattern.proxy.def.Image;
import csj.thoughtful.designpattern.proxy.impl.ProxyImage;
import csj.thoughtful.designpattern.proxy.impl.RealImage;

/**
 * 代理模式（Proxy Pattern）：给某一个对象提供一个代理或占位符，
 * 并由代理对象来控制对原对象的访问。
 * */
//当被请求时，使用 ProxyImage 来获取 RealImage 类的对象。
public class ProxyPatternDemo {
   
   public static void main(String[] args) {
      Image image = new ProxyImage("test_10mb.jpg");
 
      // 图像将从磁盘加载
      image.display(); 
      System.out.println("");
      // 图像不需要从磁盘加载
      image.display();

      Image image1 = new RealImage("test_11mb.jpg");

      // 图像将从磁盘加载
      image1.display();
      System.out.println("");
      // 图像不需要从磁盘加载
      image1.display();
   }
}