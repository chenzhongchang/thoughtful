package csj.thoughtful.designpattern.factory.msg;

public interface MsgHandleFactory {

    public void addMsgHandle(String type,MsgHandle handle);

    public MsgHandle getMsgHandle(String type);

}
