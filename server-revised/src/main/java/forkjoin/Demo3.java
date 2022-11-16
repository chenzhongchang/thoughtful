package forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

//makePwd4
public class Demo3 {

    private static class TaskCount extends RecursiveTask<Integer> {
        private static final int MIN_THRESHOLD=10000;
        private static int pwdDigit;

        TaskCount(int pwdDigit){
            this.pwdDigit=pwdDigit;
        }
        public long getMakeSum(){
            long result=1;
            for (int i=0;i<pwdDigit;i++){
                result=result*10;
            }
            System.out.println("getMakeSum="+result);
            return result;
        }

        @Override
        protected Integer compute() {
            int result =0;
            boolean shouldCompute = getMakeSum() <= MIN_THRESHOLD;
            //如果任务小于阈值则计算任务
            if(shouldCompute){
                 makePwd2();
            }//大于阈值则继续切分任务
            else{
//                int middle=(start+end) >>> 1;
//                Demo3.TaskCount taskA = new Demo3.TaskCount(start,middle);
//                Demo3.TaskCount taskB = new Demo3.TaskCount(middle+1,end);
//                //将任务加入工作队列，被工作线程执行，fork方法立即返回不阻塞
//                taskA.fork();
//                taskB.fork();
//
//                //阻塞,直到任务被完成或取消
//                Integer resultA = taskA.join();
//                Integer resultB = taskB.join();
//
//                //合并子任务
//                result = resultA+resultB;
            }
            return result;
        }
        public static void makePwd2(){
            long startTime=System.currentTimeMillis();
            StringBuffer sb;
            byte a0,a1,a2,a3;
            int num=0;
            for(a0=48;a0<58;a0++) {
                for (a1 = 48; a1 < 58; a1++) {
                    for (a2=48;a2<58;a2++){
                        for (a3=48;a3<58;a3++){
                            sb=new StringBuffer();
                            sb.append((char)a0).append((char)a1).append((char)a2).append((char)a3);
                            getRealPwd();
                            num++;
                        }
                    }
                }
            }
            long endTime=System.currentTimeMillis();
            System.out.println("makePwd1------num="+num);
            System.out.println("makePwd1------耗时="+(endTime-startTime)/1000);

            for(int i=0;i<pwdDigit;i++){

            }

        }

        public static void recursivePwd(String pwd,int pwdDigit){
            for(int i=48;i<58;i++){
                char a= (char) i;
                pwd=pwd+a;
                if(pwdDigit>0){
                    recursivePwd(pwd,pwdDigit-1);
                }else{
                    System.out.println(pwd);
                }
            }
        }
    }
    public static void recursiveNum(int startNum,int endNum,String num,int numDigit,int[] limit){
        if(limit.length==3&&numDigit==limit[2]){
            int startNum1=limit[0];
            int endNum1=limit[1];
            int numDigit1=limit[2];
            for(int i=startNum1;i<endNum1;i++){
                char a= (char) i;
                if(numDigit>1){
                    String temp=num+a;
                    recursiveNum(startNum,endNum,temp,numDigit1-1,limit);
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

    public static void recursiveNum(int startNum,int endNum,String num,int numDigit,List<int []> limitList){
        if(limitList.size()!=0){
            //&&
           for(int[] limit:limitList){
               System.out.println("========limit--index:"+limit[0]);
               if(numDigit==limit[2]){
                   int startNum1=limit[0];
                   int endNum1=limit[1];
                   int numDigit1=limit[2];
                   for(int i=startNum1;i<endNum1;i++){
                       char a= (char) i;
                       if(numDigit>1){
                           String temp=num+a;
                           recursiveNum(startNum,endNum,temp,numDigit1-1,limit);
                       }else{
                           String temp=num+a;
                           System.out.println(temp);
                       }
                   }
               }
           }
        }else{
            for(int i=startNum;i<endNum;i++){
                char a= (char) i;
                if(numDigit>1){
                    String temp=num+a;
                    recursiveNum(startNum,endNum,temp,numDigit-1,new int[0]);
                }else{
                    String temp=num+a;
                    System.out.println(temp);
                }
            }
        }

    }

    public static List<int []> checkNumDigits(int numDigit){//改善思路 map<int,int[]> ? list<int[]>
        List<int []> result=new ArrayList<>();
        int[] res=new int[3];
        long min_val=1;
        for (int i=0;i<numDigit;i++){
            min_val=min_val*10;
        }
        System.out.println("min_val="+min_val);
        int endNum=58;
        int startNum=48;
        int newnumDigit=numDigit;
        res[0]=startNum;
        res[1]=endNum;
        res[2]=newnumDigit;
        if(min_val>1000){//1~1000，1001~2000
            newnumDigit=4;
            for(int i=48;i<endNum;i++){
                int[] newRes=new int[3];
                newRes[0]=i;
                newRes[1]=i+1;
                newRes[2]=newnumDigit;
                result.add(newRes);
            }

        }else{
            return new ArrayList<>();
        }
        return result;
    }

    public static void main(String[] args) {
        int startNum=48;
        int endNum=58;
        int numDigit=4;
        List<int []> limitList=checkNumDigits(numDigit);
//        int[] limit=checkNumDigit(numDigit);
        recursiveNum(startNum,endNum,"",numDigit,limitList);


    }

    public static void makePwd1(){
        long startTime=System.currentTimeMillis();
        StringBuffer sb;
        byte a0,a1,a2,a3;
        int num=0;
        for(a0=48;a0<58;a0++) {
            for (a1 = 48; a1 < 58; a1++) {
                for (a2=48;a2<58;a2++){
                    for (a3=48;a3<58;a3++){
                        sb=new StringBuffer();
                        sb.append((char)a0).append((char)a1).append((char)a2).append((char)a3);
                        getRealPwd();
                        num++;
                    }
                }
            }
        }
        long endTime=System.currentTimeMillis();
        System.out.println("makePwd1------num="+num);
        System.out.println("makePwd1------耗时="+(endTime-startTime)/1000);
    }


    public static int[] checkNumDigit(int numDigit){//改善思路 map<int,int[]> ? list<int[]>
        int[] result=new int[3];
        long min_val=1;
        for (int i=0;i<numDigit;i++){
            min_val=min_val*10;
        }
        System.out.println("min_val="+min_val);
        int endNum=58;
        int startNum=48;
        int newnumDigit=numDigit;
        result[0]=startNum;
        result[1]=endNum;
        result[2]=newnumDigit;
        if(min_val>1000){//1~1000，1001~2000
//            newnumDigit=4;
//            endNum=49;
//            result[0]=startNum;
//            result[1]=endNum;
//            result[2]=newnumDigit;
            newnumDigit=4;
            startNum=49;
            endNum=50;
            result[0]=startNum;
            result[1]=endNum;
            result[2]=newnumDigit;
        }else{
            return new int[0];
        }
        return result;
    }



    public static void getRealPwd(){
        try {
            Thread.sleep(10);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
