package cn.zhangbin.constent;

import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageType {

    // 登录
    public static final int LOGIN = 4;
    // 向服务器发送
    public static final int TO_SERVER = 1;
    // 向好友发送
    public static final int TO_FRIEND = 2;
    // 群发
    public static final int TO_ALL = 3;
    // 来自服务器
    public static final int FROM_SERVER = 5;
    // 服务器回复
    public static final String REPLY = "Copy that";
    // 服务器名称
    public static final String SERVER_NAME = "server";
    // 接收信息
    public static final int RECEIVER = 4;
    // 存储所有客户端
    public static final Map<String, Socket> ONLINE_USERS = new ConcurrentHashMap<>(8);
}
