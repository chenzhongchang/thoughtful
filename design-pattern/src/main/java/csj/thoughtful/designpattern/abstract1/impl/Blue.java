package csj.thoughtful.designpattern.abstract1.impl;

import csj.thoughtful.designpattern.abstract1.def.Color;

public class Blue implements Color {
 
   @Override
   public void fill() {
      System.out.println("Inside Blue::fill() method.");
   }
}