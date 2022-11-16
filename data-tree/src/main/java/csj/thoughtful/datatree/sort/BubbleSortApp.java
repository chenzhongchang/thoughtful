package csj.thoughtful.datatree.sort;

public class BubbleSortApp {
    public static void main(String[] args) {
        int maxSize=100;
        ArrayBub arr;
        System.out.println("===================================");
        arr=new ArrayBub(maxSize);

        arr.inset(77);
        arr.inset(99);
        arr.inset(44);
        arr.inset(55);
        arr.inset(22);
        arr.inset(88);
        arr.inset(11);
        arr.inset(66);
        arr.inset(00);
        arr.inset(33);

        arr.display();

        arr.bubbleSort();
        arr.bubbleSort();
        System.out.println("=============select======================");
        ArrayBub arr1=new ArrayBub(maxSize);
        arr1.inset(77);
        arr1.inset(99);
        arr1.inset(44);
        arr1.inset(55);
        arr1.inset(22);
        arr1.inset(88);
        arr1.inset(11);
        arr1.inset(66);
        arr1.inset(00);
        arr1.inset(33);
        arr1.display();

        arr1.selectSort();
        arr1.selectSort();
        System.out.println("================insetion===================");
        ArrayBub arr2=new ArrayBub(maxSize);
        arr2.inset(77);
        arr2.inset(99);
        arr2.inset(44);
        arr2.inset(55);
        arr2.inset(22);
        arr2.inset(88);
        arr2.inset(11);
        arr2.inset(66);
        arr2.inset(00);
        arr2.inset(33);
        arr2.display();
        arr2.insetionSort();
        arr2.insetionSort();
    }
}
