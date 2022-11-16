package csj.thoughtful.datatree.myarray;

public class HighArray {

    private long[] a;
    private int nElems;

    public HighArray(int max){
        a=new long[max];
        nElems=0;
    }

    //task1
    public int getMax(){
        if(nElems==0){
            return -1;
        }
        int maxIn=0;
        int j;
        long max=a[0];
        for(j=1;j<nElems;j++){
            if(max<a[j]){
               max=a[j];
               maxIn=j;
            }
        }
        return maxIn;
    }

    //task2
    public long removeMax(){
        int maxIn=getMax();
        long max=a[maxIn];
        int j;
        for(j=maxIn;j<nElems;j++){
            a[j]=a[j+1];
        }
        return max;
    }

    public boolean find(long searchKey){
        int j;
        for(j=0; j<nElems;j++)
            if(a[j]==searchKey)
                break;
        if(j==nElems)
            return false;
        else
            return true;

    }

    public int binaryFind(long searchKey){
         int lowerBound=0;
         int upperBound=nElems-1;
         int curIn;

         while(true){
             curIn=(lowerBound+upperBound)/2;
             if(a[curIn]==searchKey)
                 return curIn;
             else if(lowerBound>upperBound)
                 return nElems;
             else {
                 if (a[curIn] < searchKey)
                     lowerBound = curIn + 1;
                 else
                     upperBound = curIn - 1;
             }
         }
    }

    public void inset(long value){  //inset与sortInset不能同时用
        a[nElems]=value;
        nElems++;
    }

    public void sortInset(long value){
        int j;
        for(j=0;j<nElems;j++){
            if(a[j]>value)
                break;
        }
        for (int k=nElems;k>j;k--)
            a[k]=a[k-1];
        a[j]=value;
        nElems++;
    }

    public boolean delete(long value){
        int j;
        for(j=0;j<nElems;j++)
            if(value==a[j])
                break;
        if(j==nElems)
            return false;
        else{
            for (int k=j;k<nElems;k++)
                a[k]=a[k+1];
            nElems--;
            return true;
        }
    }

    public boolean sortDelete(long value){
        int j=binaryFind(value);
        if(j==nElems)
            return false;
        else{
            for (int k=j;k<nElems;k++)
                a[k]=a[k+1];
            nElems--;
            return true;
        }
    }

    public void display(){
        for (int j=0;j<nElems;j++)
            System.out.println(a[j]+" ");
        System.out.println("");
    }

}
