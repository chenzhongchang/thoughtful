package csj.thoughtful.designpattern.builder.impl;

import csj.thoughtful.designpattern.builder.def.Item;
import csj.thoughtful.designpattern.builder.def.Packing;

//ColdDrink 冷饮
public abstract class ColdDrink implements Item {

//  Bottle  瓶子
    @Override
    public Packing packing() {
       return new Bottle();
    }
 
    @Override
    public abstract float price();
}