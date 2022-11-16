package csj.thoughtful.designpattern.command.test;

import csj.thoughtful.designpattern.command.impl.Broker;
import csj.thoughtful.designpattern.command.impl.BuyStock;
import csj.thoughtful.designpattern.command.impl.SellStock;
import csj.thoughtful.designpattern.command.impl.Stock;

//使用 Broker 类来接受并执行命令。
/**
 * 命令模式（Command Pattern）：将一个请求封装为一个对象，
 * 从而可用不同的请求对客户进行参数化，对请求排队或者记录请求日志，
 * 以及支持可撤销的操作。
 * */
public class CommandPatternDemo {
   public static void main(String[] args) {
      Stock abcStock = new Stock();
 
      BuyStock buyStockOrder = new BuyStock(abcStock);
      SellStock sellStockOrder = new SellStock(abcStock);
 
      Broker broker = new Broker();
      broker.takeOrder(buyStockOrder);
      broker.takeOrder(sellStockOrder);
 
      broker.placeOrders();
   }
}