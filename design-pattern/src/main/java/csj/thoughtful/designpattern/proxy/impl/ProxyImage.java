package csj.thoughtful.designpattern.proxy.impl;

import csj.thoughtful.designpattern.proxy.def.Image;

public class ProxyImage implements Image {
 
   private RealImage realImage;
   private String fileName;
 
   public ProxyImage(String fileName){
      this.fileName = fileName;
   }
 
   @Override
   public void display() {
      if(realImage == null){
         realImage = new RealImage(fileName);
      }
      realImage.display();
   }
}