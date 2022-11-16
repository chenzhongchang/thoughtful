package csj.thoughtful.designpattern.observer.def;

public abstract class Observer {
   protected Subject subject;
   public abstract void update();
}