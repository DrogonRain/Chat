package cn.zhangbin;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) throws IOException {
        // 创建serverSocket
        ServerSocket serverSocket = new ServerSocket();
        // 绑定端口
        serverSocket.bind(new InetSocketAddress(8888));
        // 开始监听
        serverSocket.accept();
    }
}
