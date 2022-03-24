package cn.zhangbin;

import cn.zhangbin.constent.Constent;
import cn.zhangbin.constent.MessageType;
import cn.zhangbin.thread.ServerThread;
import cn.zhangbin.util.MsgUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    public static void main(String[] args){
        // 创建ServerSocket
        try (ServerSocket serverSocket = new ServerSocket()){
            // 绑定端口
            serverSocket.bind(new InetSocketAddress(8888));
            System.out.println("服务器开始运行,端口号为:8888");
            while(true){
                // 建立一个连接
                Socket socket = serverSocket.accept();
                // 创建一个线程
                new ServerThread(socket).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
