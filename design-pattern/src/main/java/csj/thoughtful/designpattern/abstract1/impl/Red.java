package csj.thoughtful.designpattern.abstract1.impl;

import csj.thoughtful.designpattern.abstract1.def.Color;

public class Red implements Color {
 
   @Override
   public void fill() {
      System.out.println("Inside Red::fill() method.");
   }
}