package csj.thoughtful.designpattern.observer.test;

import csj.thoughtful.designpattern.observer.def.Subject;
import csj.thoughtful.designpattern.observer.impl.BinaryObserver;
import csj.thoughtful.designpattern.observer.impl.HexaObserver;
import csj.thoughtful.designpattern.observer.impl.OctalObserver;

public class ObserverPatternDemo {
   public static void main(String[] args) {
      Subject subject = new Subject();
 
      new HexaObserver(subject);
      new OctalObserver(subject);
      new BinaryObserver(subject);
 
      System.out.println("First state change: 15");   
      subject.setState(15);
      System.out.println("Second state change: 10");  
      subject.setState(10);
   }
}