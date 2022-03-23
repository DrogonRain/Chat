package cn.zhangbin;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = -1032594805834079169L;
    // 发送类型(1 - 服务器,2 - 好友,3 - 群发)
    private Integer type;
    // 发送的文本内容
    private String content;

    public Message() {
    }

    public Message(Integer type, String content) {
        this.type = type;
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
