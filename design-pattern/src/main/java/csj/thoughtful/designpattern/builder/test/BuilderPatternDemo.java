package csj.thoughtful.designpattern.builder.test;

import csj.thoughtful.designpattern.builder.impl.Meal;
import csj.thoughtful.designpattern.builder.impl.MealBuilder;

/**
 *建造者模式（Builder Pattern）：将一个复杂对象的构建与它的表示分离，
 * 使得同样的构建过程可以创建不同的表示
 * */
//BuiderPatternDemo 使用 MealBuilder 来演示建造者模式（Builder Pattern）。
public class BuilderPatternDemo {
   public static void main(String[] args) {
      MealBuilder mealBuilder = new MealBuilder();
 
      Meal vegMeal = mealBuilder.prepareVegMeal();
      System.out.println("Veg Meal");
      vegMeal.showItems();
      System.out.println("Total Cost: " +vegMeal.getCost());
 
      Meal nonVegMeal = mealBuilder.prepareNonVegMeal();
      System.out.println("\n\nNon-Veg Meal");//Non-Veg Meal 非素菜餐
      nonVegMeal.showItems();
      System.out.println("Total Cost: " +nonVegMeal.getCost());
      //Total Cost 总成本
   }
}