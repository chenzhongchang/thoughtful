package csj.thoughtful.designpattern.factory.msg.action;

import csj.thoughtful.designpattern.factory.msg.MsgHandle;

import java.util.Map;

public class PenHandle implements MsgHandle {
    @Override
    public String msgHandle(Map<?, ?> data) {
        return "钢笔，毛笔";
    }
}
