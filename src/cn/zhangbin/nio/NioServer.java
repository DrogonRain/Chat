package cn.zhangbin.nio;

import cn.zhangbin.Message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class NioServer {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // 创建服务器
        ServerSocketChannel server = ServerSocketChannel.open();
        // 绑定服务器端口
        server.bind(new InetSocketAddress(6666));
        // 配置成非阻塞式Channel
        server.configureBlocking(false);
        //创建一个IO多路复用选择器
        Selector selector = Selector.open();
        // 注册
        server.register(selector, SelectionKey.OP_ACCEPT);

        while(true){
            /**
             * 阻塞的方法, 返回盒子代表发生时间的通道的个数
             * 为 0  则代表超时
             * 为 -1 则代表错误
             */
            selector.select();

            // 获取所有选择器中的内容
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()){
                    // 若有连接则进行三次握手
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (selectionKey.isReadable()){
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    // 获取register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));附属内容 如 ByteBuffer
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    //清空上一次输入的内容
                    buffer.clear();
                    // 开始读取
                    channel.read(buffer);
                    // 创建字节数组,参数大小为buffer中的字节数组
                    ByteArrayInputStream bius = new ByteArrayInputStream(buffer.array());
                    // 创建对象输入流
                    ObjectInputStream ois = new ObjectInputStream(bius);
                    // 在内存中读取对象,为避免NullPointerException使用Optional对象进行保存
                    Optional<Message> message = Optional.ofNullable((Message) ois.readObject());
                    // 输出读取到的对象
                    message.ifPresent(m -> System.out.println(m.getUsername()));
                    // 从0开始到buffer指针所指向的位置结束
                    System.out.println(new String(buffer.array(),0,buffer.position()));
                }
            }
        }
    }
}
