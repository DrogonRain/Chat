package cn.zhangbin.nio;

import cn.zhangbin.Message;
import cn.zhangbin.util.ScannerUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        // 获取Channel
        SocketChannel socket = SocketChannel.open();
        // 配置非阻塞
        socket.configureBlocking(false);
        socket.connect(new InetSocketAddress(6666));

        // 判断是否连接成功
        if (socket.finishConnect()){
            while(true){
                String name = ScannerUtils.input();
                Message message = new Message();
                message.setUsername(name);

                ByteArrayOutputStream bous = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bous);
                oos.writeObject(message);
                socket.write(ByteBuffer.wrap(bous.toByteArray()));
            }
        }

    }
}
