package cn.zhangbin;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 创建serverSocket
        ServerSocket serverSocket = new ServerSocket();
        // 绑定端口
        serverSocket.bind(new InetSocketAddress(8888));
        System.out.println("服务器开始运行,端口号为:8888");
        // 开始监听
        Socket socket = serverSocket.accept();

        // 读取内容
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(inputStream);
        Object object = ois.readObject();
        Message message = null;
        if (object instanceof Message){
            message = (Message) object;
            System.out.println("接收到的信息为: "+message.getContent()+",类型为: "+message.getType());
        }

    }
}
