package forkjoin;

import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.concurrent.RecursiveTask;

public class Demo1 {

    private static class TaskCount extends RecursiveTask<Integer> {
        private static final int MIN_THRESHOLD=4;
        private int length;
        private char start;
        private int end;


        TaskCount(char start, int end,int length){
            this.length=length;
            this.start=start;
            this.end=end;
        }

        @Override
        protected Integer compute() {
            int result =0;
            boolean shouldCompute = length <= MIN_THRESHOLD;
            //如果任务小于阈值则计算任务
            if(shouldCompute){

            }//大于阈值则继续切分任务
            else{
//                int middle=(start+end) >>> 1;
//                ForkJoinTest.TaskCount taskA = new ForkJoinTest.TaskCount(start,middle);
//                ForkJoinTest.TaskCount taskB = new ForkJoinTest.TaskCount(middle+1,end);
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
        public static String makePwd(int start,int end,int length){
            StringBuffer sb;
            long startTime=System.currentTimeMillis();
            byte[] arr=new byte[length];
            String[] pwdArr=new String[length];


            return null;
        }



        public static String makePed(){
            byte a0,a1,a2,a3,a4,a5,a6,a7;
            StringBuffer sb;
            long startTime=System.currentTimeMillis();
            long num=0;
            for(a0=49;a0<58;a0++){
                for(a1=48;a1<58;a1++){
                    for(a2=48;a2<58;a2++){
                        for(a3=48;a3<58;a3++){
                            for(a4=48;a4<58;a4++){
                                for(a5=48;a5<58;a5++){
                                    for(a6=48;a6<58;a6++){
                                        for(a7=48;a7<58;a7++){
                                            sb=new StringBuffer();
                                            sb.append((char)a0).append((char)a1).append((char)a2).append((char)a3).
                                                    append((char)a4).append((char)a5).append((char)a6).append((char)a7);
                                            String pwd=sb.toString();
                                            System.out.println("num:"+(++num));
                                            if(0==(num%5000)){
                                                long endTime=System.currentTimeMillis();
                                                System.out.println("num:"+num+"---------耗时："+(endTime-startTime)/1000+"s");
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            long endTime=System.currentTimeMillis();
            System.out.println("makePed---------耗时："+(endTime-startTime)/1000+"s");
            return null;
        }
    }


    public static void recursiveFun(){
//       String pwd[]={"9","9","9","9"};


    }

    public static void main(String[] args) {
//        String[] result=new String[4];
//        String[] result={"9","9","9","9"};
//        recursiveFun(48,0,result);
//        recursiveFun("",4, (byte) 48,58,4);
//        System.out.println(1<<10);//2^10  求 10^2
//        int num1=2;
//        for (int i=0;i<9;i++){
//            num1=num1*2;
//        }
//        System.out.println("num1="+num1);
//        System.out.println(2<<2);
//        byte a0,a1;
//        int num=0;
//        for(a0=48;a0<58;a0++) {
//            for (a1 = 48; a1 < 58; a1++) {
//                num++;
//                System.out.println(num);
//            }
//        }
        System.out.println(10*10*10*10*10*10*10*10);

    }
}
