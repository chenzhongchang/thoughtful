package single.pool.version.a.demo;

import single.pool.version.a.demo.user.UserInfo;
import single.pool.version.a.executor.RosExecutor;

import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class TestF {

    public static void main(String[] args) {
        TestF testE=new TestF();
        testE.init();
    }

    public void init(){
        TestF.AdaptationConfig config=new TestF.AdaptationConfig();
        config.start();
    }

    private AtomicInteger num=new AtomicInteger(0);
    private AtomicInteger sum=new AtomicInteger(0);
    private volatile int b=2;

    public void increment(UserInfo user){
        int result=num.incrementAndGet();
        System.out.println("=====result:"+result);
        System.out.println(user.toString());

    }
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public String getBirthday(int index){
        String str=index+"";
        int len=str.length();

        String time=System.currentTimeMillis()+"";
        int leng=time.length();
        String prefix=time.substring(0,(leng-(5-len)));
        String suffix=time.substring(leng-(3-len));
        String newtime=prefix+index+suffix;
        if(newtime==time){
            long dateTime=Long.parseLong(newtime);
            return sdf.format(dateTime);
        }else{
            long dateTime=Long.parseLong(time);
            return sdf.format(dateTime);
        }

    }


    class AdaptationConfig extends Thread{
        @Override
        public void run() {
            while (b>0){
                long startTime=System.currentTimeMillis();
                int leng=300;
                CountDownLatch countDownLatch=new CountDownLatch(leng);
                for(int i=0;i<leng;i++){
                    if(i==3){
                        countDownLatch.countDown();
                        continue;
                    }
                    int index=sum.incrementAndGet();
                    String userName="阳翟"+index;
                    String pwd="yangdi"+index;
                    String idCard=System.currentTimeMillis()+"-"+index;
                    int tn=index%2;
                    String sex=tn==1?"女":"男";
                    String birthday=getBirthday(index);
                    String prefix=tn==1?"A":"B";
                    String seq=prefix+index;
                    String version="A."+index;
                    String remark=birthday+"-"+index;

                    RosExecutor.execute(i+"i",new Runnable() {
                        @Override
                        public void run() {
                            try {

                                UserInfo user=new UserInfo();
                                user.setId(index);
                                user.setUserName(userName);
                                user.setUserPwd(pwd);
                                user.setIdCard(idCard);
                                user.setSex(sex);
                                user.setBirthday(birthday);
                                user.setSeq(seq);
                                user.setVersion(version);
                                user.setRemark(remark);
                                increment(user);
                                System.out.println("====sum.incrementAndGet():" + index);
                                Thread.sleep(2000);
                            } catch (Exception e) {
                                System.out.println("=========e:"+e.getMessage());
                            }finally{
                                countDownLatch.countDown();
                            }
                        }
                    });
                }
                try {
                    countDownLatch.await();
                    long endTime=System.currentTimeMillis();
                    System.out.println("===============================================耗时："+(endTime-startTime));
                    Thread.sleep(10*1000);
                    b--;
                }catch (InterruptedException e){
                    System.out.println("=========e:"+e.getMessage());
                }
            }


        }
    }


}
