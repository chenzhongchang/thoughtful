package csj.thoughtful.designpattern.observer.impl;

import csj.thoughtful.designpattern.observer.def.Observer;
import csj.thoughtful.designpattern.observer.def.Subject;

public class HexaObserver extends Observer {
 
   public HexaObserver(Subject subject){
      this.subject = subject;
      this.subject.attach(this);
   }
 
   @Override
   public void update() {
      System.out.println( "Hex String: " 
      + Integer.toHexString( subject.getState() ).toUpperCase() ); 
   }
}