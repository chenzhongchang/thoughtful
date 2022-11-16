package csj.thoughtful.utils;

import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class UtilsTest {

    //生成随机字符串，代表题目的实际内容,length表示题目的长度
    private static String makeQuestionDetail(int length) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    private static String makeQuestionSeq(int length) {
        String base="0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        List<Test> newList=new CopyOnWriteArrayList<>();
        for (int i=0;i<10;i++){
            Test t=new Test();
            t.setAge(makeQuestionSeq(2));
            t.setId(i+"");
            t.setName(makeQuestionDetail(6));
            t.setSex(((i%2)+1)+"");
            t.setType(makeQuestionSeq(1));
            newList.add(t);
        }
        List<Test> dbList=new CopyOnWriteArrayList<>();
        for (int i=0;i<8;i++){
            Test t=new Test();
            t.setAge(makeQuestionSeq(2));
            t.setId(i+"");
            t.setName(makeQuestionDetail(6));
            t.setSex(((i%2)+1)+"");
            t.setType(makeQuestionSeq(1));
            dbList.add(t);
        }

        List<Test> addList=new CopyOnWriteArrayList<>();
        List<Test> updateList=new CopyOnWriteArrayList<>();
        List<Test> delList=new CopyOnWriteArrayList<>();

        System.out.println("");
        System.out.println("====dbList="+new Gson().toJson(dbList));
        System.out.println("");
        System.out.println("====newList="+new Gson().toJson(newList));
        System.out.println("");

        XObjectUtils.compare(dbList, newList, addList, updateList, delList, new Comparator<Test>() {
            @Override
            public int compare(Test o1, Test o2) {
                if (StringUtils.isBlank(o1.getAge()) || StringUtils.isBlank(o2.getAge()))
                    return -1;
                return o1.getAge().hashCode()-o2.getAge().hashCode();
            }
        });

        Collections.sort(dbList, new Comparator<Test>() {
            @Override
            public int compare(Test o1, Test o2) {
                int num1=Integer.parseInt(o1.getId());
                int num2=Integer.parseInt(o2.getId());
                if(num1-num2==0)
                    return 0;
                return num1-num2>0?1:-1;
            }
        });
        Collections.sort(newList, new Comparator<Test>() {
            @Override
            public int compare(Test o1, Test o2) {
                int num1=Integer.parseInt(o1.getId());
                int num2=Integer.parseInt(o2.getId());
                if(num1-num2==0)
                    return 0;
                return num1-num2>0?1:-1;
            }
        });
        Collections.sort(addList, new Comparator<Test>() {
            @Override
            public int compare(Test o1, Test o2) {
                int num1=Integer.parseInt(o1.getId());
                int num2=Integer.parseInt(o2.getId());
                if(num1-num2==0)
                    return 0;
                return num1-num2>0?1:-1;
            }
        });
        Collections.sort(updateList, new Comparator<Test>() {
            @Override
            public int compare(Test o1, Test o2) {
                int num1=Integer.parseInt(o1.getId());
                int num2=Integer.parseInt(o2.getId());
                if(num1-num2==0)
                    return 0;
                return num1-num2>0?1:-1;
            }
        });
        Collections.sort(delList, new Comparator<Test>() {
            @Override
            public int compare(Test o1, Test o2) {
                int num1=Integer.parseInt(o1.getId());
                int num2=Integer.parseInt(o2.getId());
                if(num1-num2==0)
                    return 0;
                return num1-num2>0?1:-1;
            }
        });

        if (CollectionUtils.isNotEmpty(addList)){
            System.out.println("====addList="+new Gson().toJson(addList));
        }

        if (CollectionUtils.isNotEmpty(updateList)){
            System.out.println("====updateList="+new Gson().toJson(updateList));
        }

        if (CollectionUtils.isNotEmpty(delList)){
            System.out.println("====delList="+new Gson().toJson(delList));
        }

    }

}
