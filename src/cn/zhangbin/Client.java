package cn.zhangbin;

import cn.zhangbin.constent.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();

        // 连接服务器
        socket.connect(new InetSocketAddress(8888));
        // 获取输出流
        OutputStream outputStream = socket.getOutputStream();
        // 创建对象输出流
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
        // 发送对象数据
        oos.writeObject(new Message(MessageType.TO_SERVER,"hello"));
        // 关闭输出流
        oos.close();
        outputStream.close();
    }
}
