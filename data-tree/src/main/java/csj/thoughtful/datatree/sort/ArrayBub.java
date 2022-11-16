package csj.thoughtful.datatree.sort;

public class ArrayBub {
    private long[] a;
    private int nElems;

    public ArrayBub(int max){
        a=new long[max];
        nElems=0;
    }

    public void inset(long value){
        a[nElems]=value;
        nElems++;
    }

    public void display(){
        for(int j=0; j<nElems; j++)
            System.out.println(a[j] + " ");
        System.out.print("");
    }

    //快速排序：
    public void bubbleSort(){
        int out,in;
        int count=0;
        for(out=nElems-1; out>1; out--){
            for(in=0; in<out; in++){
                if(a[in]>a[in+1]){
                    swap(in,in+1);
                    count=count+1;
                }
            }
        }
        System.out.println("bubble count:"+count);
    }


    public void selectSort(){
        int out,in,min = 0;
        int count=0;
        for(out=0;out<nElems-1;out++){
            min=out;
            for(in=out+1; in<nElems; in++){
                if(a[in]<a[min]){
                    min=in;
                    count=count+1;
                }
                count=count+1;
                swap(out, min);
            }
        }
        System.out.println("select count:"+count);
    }

    public void insetionSort(){
        int in,out;
        int count=0;
        for(out=1;out<nElems;out++){
            long temp=a[out];
            in=out;
            while(in>0&&a[in-1]>=temp){
                a[in]=a[in-1];
                --in;
                count=count+1;
            }
            a[in]=temp;
        }
        System.out.println("insetion count:"+count);
    }


    private void swap(int one, int two){
        long temp=a[one];
        a[one]=a[two];
        a[two]=temp;
    }

}
