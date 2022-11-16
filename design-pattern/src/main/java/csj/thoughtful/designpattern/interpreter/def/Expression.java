package csj.thoughtful.designpattern.interpreter.def;


//创建一个表达式接口。
public interface Expression {
   public boolean interpret(String context);
}