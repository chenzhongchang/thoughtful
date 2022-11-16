package forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class Demo4 {
    static class LimitInfo{
        private int startNum=48;
        private int endNum=58;
        private int numDigit=4;
        private LimitInfo childLimit=null;

        LimitInfo(int startNum,int endNum,int numDigit,LimitInfo childLimit){
            this.startNum=startNum;
            this.endNum=endNum;
            this.numDigit=numDigit;
            this.childLimit=childLimit;
        }

        public int getStartNum() {
            return startNum;
        }

        public void setStartNum(int startNum) {
            this.startNum = startNum;
        }

        public int getEndNum() {
            return endNum;
        }

        public void setEndNum(int endNum) {
            this.endNum = endNum;
        }

        public int getNumDigit() {
            return numDigit;
        }

        public void setNumDigit(int numDigit) {
            this.numDigit = numDigit;
        }

        public LimitInfo getChildLimit() {
            return childLimit;
        }

        public void setChildLimit(LimitInfo childLimit) {
            this.childLimit = childLimit;
        }
    }

    public static void recursiveNum(int startNum,int endNum,String num,int numDigit,LimitInfo limit){
        if(limit!=null&&numDigit==limit.getNumDigit()){
            int startNum1=limit.getStartNum();
            int endNum1=limit.getEndNum();
            int numDigit1=limit.getNumDigit();
            for(int i=startNum1;i<endNum1;i++){
                char a= (char) i;
                if(numDigit>1){
                    LimitInfo childLimit=limit.getChildLimit();
                    if(childLimit!=null){
                        String temp=num+a;
                        recursiveNum(startNum,endNum,temp,numDigit1-1,childLimit);
                    }else{
                        String temp=num+a;
                        recursiveNum(startNum,endNum,temp,numDigit1-1,limit);
                    }
                }else{
                    String temp=num+a;
                    System.out.println(temp);
                }
            }
        }else{
            for(int i=startNum;i<endNum;i++){
                char a= (char) i;
                if(numDigit>1){
                    String temp=num+a;
                    recursiveNum(startNum,endNum,temp,numDigit-1,limit);
                }else{
                    String temp=num+a;
                    System.out.println(temp);
                }
            }
        }

    }

    public static void main(String[] args) {
        int startNum=48;
        int endNum=58;
        int numDigit=5;
//        List<int []> limitList=checkNumDigits(numDigit);
//        int[] limit=checkNumDigit(numDigit);
//        recursiveNum(startNum,endNum,"",numDigit,limitList);
//        int[] limit=new int[]{48,49,5};
        LimitInfo childLimit=checkNumDigit(5);
        LimitInfo limit=new LimitInfo(49,50,5,new LimitInfo(53,54,4,childLimit));
        recursiveNum(startNum,endNum,"",numDigit,limit);
//        makePwd1();
    }

    public static LimitInfo checkNumDigit(int numDigit){//改善思路 map<int,int[]> ? list<int[]>
        List<LimitInfo> result=new ArrayList<>();
        LimitInfo newRes=new LimitInfo(48,58,numDigit,null);
        long min_val=1;
        for (int i=0;i<numDigit;i++){
            min_val=min_val*10;
        }
        System.out.println("min_val="+min_val);
        int endNum=58;
        int startNum=48;
        int newnumDigit=numDigit;
        if(min_val>1000){//10000~10999，12000~12999
            newnumDigit=4;
            for(int nd=newnumDigit;nd<numDigit;nd++){
                if((nd+1)==numDigit){
                    for(int i=48;i<endNum;i++){
                        LimitInfo childRes=new LimitInfo(i,i+1,nd,null);
                        newRes.setChildLimit(childRes);
                    }
                }else{
                    newRes=checkNumDigit(nd);
                }
            }
        }
        return newRes;
    }

    public static List<LimitInfo> checkNumDigits(int numDigit){//改善思路 map<int,int[]> ? list<int[]>
        List<LimitInfo> result=new ArrayList<>();
        long min_val=1;
        for (int i=0;i<numDigit;i++){
            min_val=min_val*10;
        }
        System.out.println("min_val="+min_val);
        int endNum=58;
        int startNum=48;
        int newnumDigit=numDigit;
        if(min_val>1000){//10000~10999，12000~12999
            newnumDigit=4;
            for(int nd=newnumDigit;nd<numDigit;nd++){
                if((nd+1)==numDigit){
                    for(int i=startNum;i<endNum;i++){
                        LimitInfo newRes=new LimitInfo(48,58,numDigit,null);
                        LimitInfo childRes=new LimitInfo(i,i+1,nd,null);
                        newRes.setChildLimit(childRes);
                        result.add(newRes);
                    }
                }else{
                    LimitInfo newRes=checkNumDigit(nd);
                    result.add(newRes);
                }
            }
        }else{
            LimitInfo newRes=new LimitInfo(48,58,numDigit,null);
            result.add(newRes);
        }
        return result;
    }

    public static void makePwd1() {
        long startTime = System.currentTimeMillis();
        StringBuffer sb;
        byte a0, a1, a2, a3, a4;
        int num = 0;
        for (a0 = 48; a0 < 58; a0++) {
            for (a1 = 48; a1 < 58; a1++) {
                for (a2 = 48; a2 < 58; a2++) {
                    for (a3 = 48; a3 < 59; a3++) {
                        for (a4 = 48; a4 < 49; a4++) {
                            sb = new StringBuffer();
                            sb.append((char) a0).append((char) a1).append((char) a2).append((char) a3).append((char) a4);
                            // getRealPwd();
                            num++;
                        }
                    }
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println("makePwd1------num=" + num);
            System.out.println("makePwd1------耗时=" + (endTime - startTime) / 1000);
        }
    }
    public static void getRealPwd () {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
