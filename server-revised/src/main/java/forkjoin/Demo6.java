package forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo6 {

    private static AtomicInteger sum=new AtomicInteger();
    private static AtomicInteger sumcs=new AtomicInteger();

    public static void main(String[] args) {
        int startNum=48;
        int endNum=58;
        int numDigit=6;
        long startTime=System.currentTimeMillis();
//        List<LimitInfo> limitList=checkNumDigits(4);
//        long endTime=System.currentTimeMillis();
//        System.out.println("limitList take time is"+(endTime-startTime));
//        recursiveNum(startNum,endNum,"",numDigit,limitList);
//        System.out.println("min_val="+getMinVal(numDigit));
        ForkJoinPool pool=new ForkJoinPool();
        //从 1-100的累加大任务
        TaskCount task = new TaskCount(numDigit,null);
        //提交到ForkJoinPool
        Future<Long> result=pool.submit(task);

        try {
            sumcs.incrementAndGet();
            System.out.println("sumcs:"+(sumcs.incrementAndGet()));
            System.out.println("sum task time:"+result.get());
            long endTime=System.currentTimeMillis();
            System.out.println("=========耗时"+(endTime-startTime));
            System.out.println("sum:"+(sum.incrementAndGet()));
        }catch (InterruptedException e){
            System.out.println("忽略中断异常");
        }catch (ExecutionException e){
            System.out.println("忽略中断异常");
        }
        if(task.isCompletedAbnormally()){
            System.out.println(task.getException().getMessage());
        }
//        makePwd1();
    }

    private static class TaskCount extends RecursiveTask<Long> {
        private static final int MIN_THRESHOLD=1000;
        private static int pwdDigit;
        private static LimitInfo limit=null;

        TaskCount(int pwdDigit,LimitInfo limit){
            this.pwdDigit=pwdDigit;
            this.limit=limit;
        }
        public long getMakeSum(){
            long result=1;
            for (int i=0;i<pwdDigit;i++){
                result=result*10;
            }
            return result;
        }

        @Override
        protected Long compute() {
            long result =0;
            int startNum=48;
            int endNum=58;
            if(limit!=null){
                long startTime=System.currentTimeMillis();
                recursiveNum(startNum,endNum,"",pwdDigit,limit);
                long endTime=System.currentTimeMillis();
                result=endTime-startTime;
                return result;
            }
//            boolean shouldCompute = getMakeSum() <= MIN_THRESHOLD;
            //如果任务小于阈值则计算任务
//            if(shouldCompute){
//                long startTime=System.currentTimeMillis();
//                LimitInfo newRes=new LimitInfo(48,58,pwdDigit,null);
//                recursiveNum(startNum,endNum,"",pwdDigit,newRes);
//                long endTime=System.currentTimeMillis();
//                result=endTime-startTime;
//                return result;
//            }//大于阈值则继续切分任务
//            else{
                long startTime=System.currentTimeMillis();
                List<LimitInfo> limitList=checkNumDigits(pwdDigit);
                long endTime=System.currentTimeMillis();
                System.out.println("limitList take time is"+(endTime-startTime));

//                recursiveNum(startNum,endNum,"",pwdDigit,limitList);
                long spendTime=0;

                //将任务加入工作队列，被工作线程执行，fork方法立即返回不阻塞
                List<Demo6.TaskCount> taskList=new ArrayList<>();
                for(LimitInfo limit:limitList){
                    Demo6.TaskCount taskA = new Demo6.TaskCount(pwdDigit,limit);
                    taskA.fork();
                    taskList.add(taskA);
                }

                //阻塞,直到任务被完成或取消
                for(Demo6.TaskCount task:taskList){
                    long taskATime=task.join();
                    System.out.println("taskATime===task time:"+taskATime);
                    spendTime=spendTime+taskATime;
                }

                //合并子任务

                return spendTime;
//            }
        }
    }


    static class LimitInfo{
        private int startNum=48;
        private int endNum=58;
        private int numDigit=4;
        private LimitInfo childLimit=null;

        LimitInfo(int startNum, int endNum, int numDigit, LimitInfo childLimit){
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

    public static void recursiveNum(int startNum, int endNum, String num, int numDigit, LimitInfo limit){
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
                    getRealPwd();
//                    System.out.println(temp);
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
                    getRealPwd();
//                    System.out.println(temp);
                }
            }
        }

    }

    public static void recursiveNum(int startNum, int endNum, String num, int numDigit, List<LimitInfo> limits){
        for(LimitInfo limit:limits){
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
                        getRealPwd();
//                    System.out.println(temp);
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
                        getRealPwd();
//                    System.out.println(temp);
                    }
                }
            }
        }
    }


    public static LimitInfo checkNumDigit(int numDigit){//改善思路 map<int,int[]> ? list<int[]>
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
                    for(int i=startNum;i<endNum;i++){
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

    public static long getMinVal(int numDigit){
        long min_val=1;
        for (int i=0;i<numDigit;i++){
            min_val=min_val*10;
        }
        return min_val;
    }

    public static List<LimitInfo> checkNumDigits(int numDigit){//改善思路 map<int,int[]> ? list<int[]>
        List<LimitInfo> result=new ArrayList<>();
        long min_val=getMinVal(numDigit);
        System.out.println("min_val="+min_val);
        int endNum=58;
        int startNum=48;
        int newnumDigit=numDigit;
        if(min_val>1000){//10000~10999，12000~12999
            newnumDigit=4;
            for(int nd=newnumDigit;nd<numDigit;nd++){
                if((nd+1)==numDigit){
                    for(int i=startNum;i<endNum;i++){
                        for(int j=startNum;j<endNum;j++){
                            LimitInfo newRes=new LimitInfo(i,i+1,numDigit,null);
                            LimitInfo childRes=new LimitInfo(j,j+1,nd,null);
                            newRes.setChildLimit(childRes);
                            result.add(newRes);
                        }
                    }
                }else{
                    List<LimitInfo> newRes=checkNumDigits(nd);
                    result.addAll(newRes);
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
            sum.incrementAndGet();
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
