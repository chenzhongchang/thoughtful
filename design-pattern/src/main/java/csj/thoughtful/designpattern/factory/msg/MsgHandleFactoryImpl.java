package csj.thoughtful.designpattern.factory.msg;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Service
public class MsgHandleFactoryImpl implements MsgHandleFactory{

    private final Map<String,MsgHandle> handleMap=new ConcurrentHashMap<>();

    @Override
    public void addMsgHandle(String type, MsgHandle handle) {
        handleMap.put(type,handle);
    }

    @Override
    public MsgHandle getMsgHandle(String type) {
        return handleMap.get(type);
    }
}
