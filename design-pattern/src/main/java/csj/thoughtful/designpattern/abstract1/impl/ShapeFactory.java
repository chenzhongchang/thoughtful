package csj.thoughtful.designpattern.abstract1.impl;

import csj.thoughtful.designpattern.abstract1.def.AbstractFactory;
import csj.thoughtful.designpattern.abstract1.def.Color;
import csj.thoughtful.designpattern.abstract1.def.Shape;

public class ShapeFactory extends AbstractFactory {
    
   @Override
   public Shape getShape(String shapeType){
      if(shapeType == null){
         return null;
      }        
      if(shapeType.equalsIgnoreCase("CIRCLE")){
         return new Circle();
      } else if(shapeType.equalsIgnoreCase("RECTANGLE")){
         return new Rectangle();
      } else if(shapeType.equalsIgnoreCase("SQUARE")){
         return new Square();
      }
      return null;
   }
   
   @Override
   public Color getColor(String color) {
      return null;
   }
}