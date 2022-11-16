package csj.thoughtful.designpattern.abstract1.impl;

import csj.thoughtful.designpattern.abstract1.def.Color;

public class Green implements Color {
 
   @Override
   public void fill() {
      System.out.println("Inside Green::fill() method.");
   }
}