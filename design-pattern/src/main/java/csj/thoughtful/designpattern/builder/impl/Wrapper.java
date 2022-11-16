package csj.thoughtful.designpattern.builder.impl;

import csj.thoughtful.designpattern.builder.def.Packing;
// Wrapper 包装纸
public class Wrapper implements Packing {
   // pack 包裹
   @Override
   public String pack() {
      return "Wrapper";
   }
}