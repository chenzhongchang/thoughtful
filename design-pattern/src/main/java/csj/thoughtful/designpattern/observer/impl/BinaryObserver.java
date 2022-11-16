package csj.thoughtful.designpattern.observer.impl;

import csj.thoughtful.designpattern.observer.def.Observer;
import csj.thoughtful.designpattern.observer.def.Subject;

public class BinaryObserver extends Observer {
 
   public BinaryObserver(Subject subject){
      this.subject = subject;
      this.subject.attach(this);
   }
 
   @Override
   public void update() {
      System.out.println( "Binary String: " 
      + Integer.toBinaryString( subject.getState() ) ); 
   }
}