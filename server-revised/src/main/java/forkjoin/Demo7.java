package forkjoin;

import java.util.ArrayList;
import java.util.List;

public class Demo7 {

    public static void main(String[] args) {
        List<Demo5.LimitInfo> limitList=checkNumDigits(6);
        System.out.println(limitList.size());
    }

    public static List<Demo5.LimitInfo> checkNumDigitChild(int numDigit){//改善思路 map<int,int[]> ? list<int[]>
        List<Demo5.LimitInfo> result=new ArrayList<>();
        long min_val=1;
        for (int i=0;i<numDigit;i++){
            min_val=min_val*10;
        }
        System.out.println("min_val="+min_val);
        int endNum=58;
        int startNum=48;
        int newnumDigit=numDigit;
        if(min_val>1000){//10000~10999，12000~12999
            newnumDigit=numDigit-1;
            List<Demo5.LimitInfo> list=checkNumDigitChild(newnumDigit);
            for(int i=startNum;i<endNum;i++){
                for(Demo5.LimitInfo limit:list){
                    Demo5.LimitInfo childRes=new Demo5.LimitInfo(i,i+1,numDigit,limit);
                    result.add(childRes);
                }
            }
        }else{
            for(int i=startNum;i<endNum;i++){
                Demo5.LimitInfo childRes=new Demo5.LimitInfo(i,i+1,newnumDigit,null);
                result.add(childRes);
            }
        }
        return result;
    }

    public static List<Demo5.LimitInfo> checkNumDigits(int numDigit){//改善思路 map<int,int[]> ? list<int[]>
        List<Demo5.LimitInfo> result=new ArrayList<>();
        long min_val=1;
        for (int i=0;i<numDigit;i++){
            min_val=min_val*10;
        }
        System.out.println("min_val="+min_val);
        int endNum=58;
        int startNum=48;
        if(min_val>1000){//10000~10999，12000~12999
            for(int i=startNum;i<endNum;i++){
                List<Demo5.LimitInfo> childRes=checkNumDigitChild(numDigit-1);
                for(Demo5.LimitInfo child:childRes){
                    Demo5.LimitInfo newRes=new Demo5.LimitInfo(i,i+1,numDigit,child);
                    result.add(newRes);
                }
            }
        }else{
            Demo5.LimitInfo newRes=new Demo5.LimitInfo(endNum,startNum,numDigit,null);
            result.add(newRes);
        }
        return result;
    }
    public static long getMinVal(int numDigit){
        long min_val=1;
        for (int i=0;i<numDigit;i++){
            min_val=min_val*10;
        }
        return min_val;
    }
}