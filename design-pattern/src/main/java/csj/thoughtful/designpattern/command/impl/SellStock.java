package csj.thoughtful.designpattern.command.impl;

import csj.thoughtful.designpattern.command.def.Order;

public class SellStock implements Order {
   private Stock abcStock;
 
   public SellStock(Stock abcStock){
      this.abcStock = abcStock;
   }
 
   public void execute() {
      abcStock.sell();
   }
}