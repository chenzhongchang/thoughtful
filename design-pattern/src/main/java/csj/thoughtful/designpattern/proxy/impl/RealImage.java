package csj.thoughtful.designpattern.proxy.impl;

import csj.thoughtful.designpattern.proxy.def.Image;

//创建实现接口的实体类。
public class RealImage implements Image {
 
   private String fileName;
 
   public RealImage(String fileName){
      this.fileName = fileName;
      loadFromDisk(fileName);
   }
 
   @Override
   public void display() {
      System.out.println("Displaying " + fileName);
   }
 
   private void loadFromDisk(String fileName){
      System.out.println("Loading " + fileName);
   }
}