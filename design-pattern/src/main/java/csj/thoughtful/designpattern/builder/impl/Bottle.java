package csj.thoughtful.designpattern.builder.impl;

import csj.thoughtful.designpattern.builder.def.Packing;

public class Bottle implements Packing {
 
   @Override
   public String pack() {
      return "Bottle";
   }
}