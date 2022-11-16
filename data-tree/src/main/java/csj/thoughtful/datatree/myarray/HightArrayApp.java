package csj.thoughtful.datatree.myarray;

public class HightArrayApp {

    public static void main(String[] args) {
        int maxSize = 100;
        HighArray arr;
        arr=new HighArray(maxSize);
        arr.inset(77);
        arr.inset(99);
        arr.inset(44);
        arr.inset(55);
        arr.inset(33);
        arr.inset(88);
        arr.inset(00);
        arr.inset(66);
        arr.inset(33);

        arr.display();

        System.out.println("==================max:"+arr.getMax());
        System.out.println("==================removeMax:"+arr.removeMax());

//        int searchKey=35;
//        if (arr.find(searchKey))
//            System.out.println("Found "+searchKey);
//        else
//            System.out.println("Can't find "+searchKey);
//
//        arr.delete(00);
//        arr.delete(55);
//        arr.delete(99);

        arr.display();

    }

}
