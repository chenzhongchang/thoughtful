package csj.thoughtful.designpattern.abstract1.impl;

import csj.thoughtful.designpattern.abstract1.def.Shape;

public class Square implements Shape {
 
   @Override
   public void draw() {
      System.out.println("Inside Square::draw() method.");
   }
}