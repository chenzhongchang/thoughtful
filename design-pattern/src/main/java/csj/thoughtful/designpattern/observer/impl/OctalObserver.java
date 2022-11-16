package csj.thoughtful.designpattern.observer.impl;

import csj.thoughtful.designpattern.observer.def.Observer;
import csj.thoughtful.designpattern.observer.def.Subject;

public class OctalObserver extends Observer {
 
   public OctalObserver(Subject subject){
      this.subject = subject;
      this.subject.attach(this);
   }
 
   @Override
   public void update() {
     System.out.println( "Octal String: " 
     + Integer.toOctalString( subject.getState() ) ); 
   }
}