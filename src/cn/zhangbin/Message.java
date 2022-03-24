package cn.zhangbin;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = -1032594805834079169L;
    // 发送类型(1 - 服务器,2 - 好友,3 - 群发)
    private Integer type;
    // 发送的文本内容
    private String content;
    private String username;
    private String password;
    private String friendUsername;

    public Message() {
    }

    public Message(Integer type, String content, String username) {
        this.type = type;
        this.content = content;
        this.username = username;
    }

    public Message(Integer type, String content, String username, String friendUsername) {
        this.type = type;
        this.content = content;
        this.username = username;
        this.friendUsername = friendUsername;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public void setFriendUsername(String friendUsername) {
        this.friendUsername = friendUsername;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", friendUsername='" + friendUsername + '\'' +
                '}';
    }
}
