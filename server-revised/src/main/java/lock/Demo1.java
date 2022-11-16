package lock;

import forkjoin.Demo9;
import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Demo1 {
    private static ReentrantLock lock=new ReentrantLock();
    private static Demo1 demo1=new Demo1();

    //解压密码核对模块，file7zPath压缩文件路径，outPutPath解压文件存放路径，passWord压缩密码
    public static boolean un7z(String file7zPath, final String outPutPath, String passWord) throws Exception {
        boolean flag = false;
        IInArchive archive;
        RandomAccessFile randomAccessFile;
        randomAccessFile = new RandomAccessFile(file7zPath, "r");
        archive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile), passWord);
        int numberOfItems = archive.getNumberOfItems();
        ISimpleInArchive simpleInArchive = archive.getSimpleInterface();
        //for (final ISimpleInArchiveItem it\em : simpleInArchive.getArchiveItems()) {
        ISimpleInArchiveItem item=simpleInArchive.getArchiveItems()[0];
        if (!item.isFolder()) {
            ExtractOperationResult result;
            result = item.extractSlow(new ISequentialOutStream() {
                public int write(byte[] data) throws SevenZipException {
                    try {
                        String parentFilePath = outPutPath;
                        if (!new File(parentFilePath).exists()) {
                            new File(parentFilePath).mkdirs();
                        }
                        IOUtils.write(data, new FileOutputStream(new File(outPutPath + File.separator + item.getPath()), true));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return data.length; // Return amount of consumed
                }
            }, passWord);
            if (result == ExtractOperationResult.OK) {
                flag=true;
            } else {
                flag=false;
            }
        }
        archive.close();
        randomAccessFile.close();
        return flag;
    }
    private static AtomicInteger sum=new AtomicInteger();
    private static AtomicInteger sumcs=new AtomicInteger();

    public static void main(String[] args) {
        int numDigit=3;
        long startTime=System.currentTimeMillis();
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

        TaskCount(int pwdDigit, LimitInfo limit){
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

            long startTime=System.currentTimeMillis();
            List<LimitInfo> limitList=checkNumDigits(pwdDigit);
            System.out.println("========limitList--size:"+limitList.size());
            long endTime=System.currentTimeMillis();
            System.out.println("limitList take time is"+(endTime-startTime));

//                recursiveNum(startNum,endNum,"",pwdDigit,limitList);
            long spendTime=0;

            //将任务加入工作队列，被工作线程执行，fork方法立即返回不阻塞
            List<TaskCount> taskList=new ArrayList<>();
            for(LimitInfo limit:limitList){
                TaskCount taskA = new TaskCount(pwdDigit,limit);
                taskA.fork();
                taskList.add(taskA);
            }

            //阻塞,直到任务被完成或取消
            for(TaskCount task:taskList){
                long taskATime=task.join();
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
                    if(demo1.getRealPwd(temp))
                        return;
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
                    if(demo1.getRealPwd(temp))
                        return;
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
                        if(demo1.getRealPwd(temp))
                            return;
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
                        if(demo1.getRealPwd(temp))
                            return;
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

    public static List<LimitInfo> checkNumDigitChild(int numDigit){//改善思路 map<int,int[]> ? list<int[]>
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
            newnumDigit=numDigit-1;
            List<LimitInfo> list=checkNumDigitChild(newnumDigit);
            for(int i=startNum;i<endNum;i++){
                for(LimitInfo limit:list){
                    LimitInfo childRes=new LimitInfo(i,i+1,numDigit,limit);
                    result.add(childRes);
                }
            }
        }else{
            for(int i=startNum;i<endNum;i++){
                LimitInfo childRes=new LimitInfo(i,i+1,newnumDigit,null);
                result.add(childRes);
            }
        }
        return result;
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
        if(min_val>1000){//10000~10999，12000~12999
            for(int i=startNum;i<endNum;i++){
                List<LimitInfo> childRes=checkNumDigitChild(numDigit-1);
                for(LimitInfo child:childRes){
                    LimitInfo newRes=new LimitInfo(i,i+1,numDigit,child);
                    result.add(newRes);
                }
            }
        }else{
            LimitInfo newRes=new LimitInfo(startNum,endNum,numDigit,null);
            result.add(newRes);
        }
        return result;
    }
    private static boolean checkNatural(String pwd){
        for(int i=0;i<pwd.length()-1;i++){
            char a=pwd.charAt(i);
            char b=pwd.charAt(i+1);
            if(a==b-1)
                return false;
        }
        return true;
    }

    private static boolean checkLogarithm(String pwd){
        for(int i=0;i<pwd.length()-1;i++){
            char a=pwd.charAt(i);
            char b=pwd.charAt(i+1);
            if(a==b)
                return false;
        }
        return true;
    }
    public static synchronized boolean getRealPwd1 (String pwd) {
//        if(checkLogarithm(pwd)&&checkNatural(pwd)){
        try {
            long startTime=System.currentTimeMillis();
            if(un7z("E:\\signal.7z", "E:\\signal", pwd)){
                System.out.println("密码是"+pwd);
                return true;
            }
            long endTime=System.currentTimeMillis();
//                System.out.println("take time:"+(endTime-startTime));
            sum.incrementAndGet();
            Thread.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        }
        return false;
    }


    private static boolean getRealPwd2(String pwd){
        lock.lock();
        try {
            long startTime=System.currentTimeMillis();
            if(un7z("E:\\signal.7z", "E:\\signal", pwd)){
                System.out.println("密码是"+pwd);
                return true;
            }
            long endTime=System.currentTimeMillis();
                System.out.println("take time:"+(endTime-startTime));
            sum.incrementAndGet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        lock.unlock();
        return false;
    }

    private boolean getRealPwd(String pwd){
        synchronized (this){
            try {
                long startTime=System.currentTimeMillis();
                if(un7z("E:\\signal.7z", "E:\\signal", pwd)){
                    System.out.println("密码是"+pwd);
                    return true;
                }
                long endTime=System.currentTimeMillis();
                System.out.println("take time:"+(endTime-startTime));
                sum.incrementAndGet();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
