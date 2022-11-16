package csj.thoughtful.datatree.myarray;

public class OrdArray {

    private long[] a;
    private int nElems;

    public OrdArray(int max){
        a=new long[max];
        nElems=0;
    }

    public int size(){
        return nElems;
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
