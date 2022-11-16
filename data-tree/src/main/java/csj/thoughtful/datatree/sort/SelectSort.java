package csj.thoughtful.datatree.sort;

public class SelectSort {
    private long[] a;
    private int nElems;

    public SelectSort(int max){
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

   public void selectSort(){
        int out,in,min = 0;
        for(out=0;out<nElems-1;out++)
            min=out;
            for(in=out+1; in<nElems; in++)
                if(a[in]<a[min])
                    min=in;
            swap(out, min);
   }

    private void swap(int one, int two){
        long temp=a[one];
        a[one]=a[two];
        a[two]=temp;
    }
}
