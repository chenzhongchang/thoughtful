package cn;

import forkjoin.ForkJoinTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class PwdTestFactory {

    public static void main(String[] args) {

        System.out.println(101 >>> 1);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                makePed();//260s
//            }
//        }).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                makePed1();//66s
//            }
//        }).start();

       /* new Thread(new Runnable() {
            @Override
            public void run() {
                makePed2(); //带过滤----151s, 不过滤----696s
            }
        }).start();*/


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

    public static String makePed1(){
        byte a0,a1,a2,a3,a4,a5,a6,a7;
        StringBuffer sb;
        long startTime=System.currentTimeMillis();
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
                                        if(checkLogarithm(pwd)&&checkNatural(pwd))
                                            System.out.println("pwd:"+sb.toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        long endTime=System.currentTimeMillis();
        System.out.println("makePed1---------耗时："+(endTime-startTime)/1000+"s");
        return null;
    }


    public static String makePed2(){
        ForkJoinPool pool=new ForkJoinPool();
        //从 1-100的累加大任务
        PwdTestFactory.TaskCount task = new PwdTestFactory.TaskCount(null);
        //提交到ForkJoinPool
        Future<Integer> result=pool.submit(task);

        try {
            System.out.println(result.get());
            System.out.println("makePed2---------耗时："+result.get()+"s");
        }catch (InterruptedException e){
            System.out.println("忽略中断异常");
        }catch (ExecutionException e){
            System.out.println("忽略中断异常");
        }
        if(task.isCompletedAbnormally()){
            System.out.println(task.getException().getMessage());
        }
//        System.out.println("makePed2---------耗时："+(endTime-startTime)/1000+"s");
        return null;
    }

    private static class TaskCount extends RecursiveTask<Integer> {
        private String type;

        TaskCount(String type){
            this.type=type;
        }

        @Override
        protected Integer compute() {
            Integer result=0;
            if("A".equals(type)){
                System.out.println("=============A");
                result=Integer.parseInt(toTask()+"");
            }else if("B".equals(type)){
                System.out.println("=============B");
                result=Integer.parseInt(toTask1()+"");
            }else{
                System.out.println("=============C");
                PwdTestFactory.TaskCount taskA = new PwdTestFactory.TaskCount("A");
                PwdTestFactory.TaskCount taskB = new PwdTestFactory.TaskCount("B");
                //将任务加入工作队列，被工作线程执行，fork方法立即返回不阻塞
                taskA.fork();
                taskB.fork();

                //阻塞,直到任务被完成或取消
                Integer resultA = taskA.join();
                Integer resultB = taskB.join();

                //合并子任务
                result = resultA+resultB;
            }

            return result;
        }

        public long toTask(){
            long startTime=System.currentTimeMillis();
            byte a0,a1,a2,a3,a4,a5,a6,a7;
            StringBuffer sb;
            for(a0=53;a0<58;a0++){
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
                                            if(checkLogarithm(pwd)&&checkNatural(pwd))
                                                System.out.println("pwd:"+sb.toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            long endTime=System.currentTimeMillis();
            long result=(endTime-startTime)/1000;
            return result;
        }

        public long toTask1(){
            long startTime=System.currentTimeMillis();
            byte a0,a1,a2,a3,a4,a5,a6,a7;
            StringBuffer sb;
            for(a0=49;a0<53;a0++){
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
                                            if(checkLogarithm(pwd)&&checkNatural(pwd))
                                                System.out.println("pwd:"+sb.toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            long endTime=System.currentTimeMillis();
            long result=(endTime-startTime)/1000;
            return result;
        }
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

}
