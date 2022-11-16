package csj.thoughtful.designpattern.strategy.impl;

import csj.thoughtful.designpattern.strategy.def.Strategy;

public class OperationMultiply implements Strategy {
   @Override
   public int doOperation(int num1, int num2) {
      return num1 * num2;
   }
}