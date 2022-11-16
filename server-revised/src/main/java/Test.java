import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        List<Order> list=new ArrayList<>();
        for (int i=1;i<150;i++){
            long time=System.currentTimeMillis();
            list.add(new Order(time,"name"+i));
            Thread.sleep(500);
        }

        Collections.sort(list, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                if (o1.getCurrentTime() - o2.getCurrentTime() == 0)
                    return 0;
                return o1.getCurrentTime() - o2.getCurrentTime() > 0 ? -1 : 1;
            }
        });
        AutoPage autoPage=new AutoPage(4,5,list);
        System.out.println(autoPage.getRecordList().toString());
    }


    static class Order{
       private long currentTime;
       private String name;

       Order(long currentTime,String name){
           this.currentTime=currentTime;
           this.name=name;
       }

        public long getCurrentTime() {
            return currentTime;
        }

        public void setCurrentTime(long currentTime) {
            this.currentTime = currentTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String toString(){
           return "currentTime:"+currentTime+",name:"+name;
        }
    }


}
