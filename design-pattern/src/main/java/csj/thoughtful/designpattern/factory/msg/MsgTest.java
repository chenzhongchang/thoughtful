package csj.thoughtful.designpattern.factory.msg;

import csj.thoughtful.designpattern.factory.msg.action.ArticleHandle;
import csj.thoughtful.designpattern.factory.msg.action.PaperHandle;
import csj.thoughtful.designpattern.factory.msg.action.PenHandle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MsgTest {

    private static MsgHandleFactory msgHandleFactory=new MsgHandleFactoryImpl();
    public static void initService(){//在项目实战中添加@Service注解实例化对象，在@PostConstruct注解中注入MsgHandleFactory
        MsgHandle articleHandle=new ArticleHandle();
        msgHandleFactory.addMsgHandle("article",articleHandle);
        MsgHandle penHandle=new PenHandle();
        msgHandleFactory.addMsgHandle("pen",penHandle);
        MsgHandle paperHandle=new PaperHandle();
        msgHandleFactory.addMsgHandle("paper",paperHandle);
    }

    public static void main(String[] args) {
        initService();
        List<String> msgList = new ArrayList<>();
        msgList.add("article"); //文章
        msgList.add("pen");     //笔
        msgList.add("paper");   //纸
//        msgList.add("grade");   //等级

        Random random = new Random();
        for(int i=0;i<15;i++){
            int num=random.nextInt(3);
            System.out.println("=====num:"+num);
            String msg=msgList.get(num);
            MsgHandle handle=msgHandleFactory.getMsgHandle(msg);
            String content = handle.msgHandle(null);
            System.out.println("=====content:"+content);
        }


        //总结：有点减少if、else， 新增一个类型只需要实现MsgHandle接口并在实现类中将其注入MsgHandleFactory
        //缺点：返回类型不可变，需要提前实例化类型对象

    }

}
