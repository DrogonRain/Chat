package cn.zhangbin;

import cn.zhangbin.constent.Constent;
import cn.zhangbin.constent.MessageType;
import cn.zhangbin.util.MsgUtils;
import cn.zhangbin.util.ScannerUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Optional;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();

        // 连接服务器
        socket.connect(new InetSocketAddress(8888));
        // 获取输入流
        InputStream inputStream = socket.getInputStream();
        // 获取输出流
        OutputStream outputStream = socket.getOutputStream();
        String username = null;
        while(true){
            if (username == null){
                username = login(inputStream, outputStream);
            }else{
                // 打印选项菜单
                printOrder();
                String input = ScannerUtils.input();
                switch (Integer.parseInt(input)){
                    case MessageType.TO_SERVER:
                        sendToServer(username,outputStream,inputStream);
                        break;
                    case MessageType.TO_FRIEND:
                        sendToFriend(username,outputStream,inputStream);
                        break;
                    case MessageType.TO_ALL:
                        sendToAll(username,outputStream,inputStream);
                        break;
                    case MessageType.RECEIVER:
                        receiverMsg(inputStream);
                        break;
                    default:
                        break;
                }
            }
        }
//        // 关闭输出流
//        outputStream.close();
//        socket.close();
    }

    private static void receiverMsg(InputStream inputStream) {
        while (true){
            Optional<Message> message = MsgUtils.readMsg(inputStream);
            message.ifPresent(m -> System.out.println(m.getUsername()+": "+m.getContent()));
        }

    }

    private static void sendToAll(String username,OutputStream outputStream, InputStream inputStream) {
        boolean flag = true;
        while(flag) {
            System.out.println(username + ":");
            String input = ScannerUtils.input();
            if ("bye".equals(input)) {
                flag = false;
            }
            MsgUtils.writeMsg(outputStream, new Message(MessageType.TO_ALL, input, username));
        }
    }

    private static void sendToFriend(String username,OutputStream outputStream, InputStream inputStream) {
        System.out.println("请输入好友的名称:");
        String friend = ScannerUtils.input();
        boolean flag = true;
        while(flag){
            System.out.println(username+":");
            String input = ScannerUtils.input();
            if ("bye".equals(input)){
                flag = false;
            }
            MsgUtils.writeMsg(outputStream,new Message(MessageType.TO_FRIEND,input,username,friend));
//            Optional<Message> message = MsgUtils.readMsg(inputStream);
//            message.ifPresent(m -> {
//                System.out.println(m.getUsername()+": "+m.getContent());
//            });
        }

    }
    // 向服务器发送消息
    private static void sendToServer(String username,OutputStream outputStream, InputStream inputStream) {
        System.out.println("Please input what you want to say:");
        String input = ScannerUtils.input();
        MsgUtils.writeMsg(outputStream,new Message(MessageType.TO_SERVER,input,username));
        // 接收信息
        Optional<Message> message = MsgUtils.readMsg(inputStream);
        message.ifPresent(m -> {
            System.out.println(m.getUsername()+": "+m.getContent());
        });
    }


    private static void printOrder(){
        System.out.println("请选择功能: " +
                MessageType.TO_SERVER+"、 给服务器发送 " +
                MessageType.TO_FRIEND+"、 给好友发送 " +
                MessageType.TO_ALL+"、 群发 " +
                MessageType.RECEIVER+"、 接受信息 ");
    }

    private static String login(InputStream inputStream, OutputStream outputStream) {
        System.out.println("Please input your username:");
        String name = ScannerUtils.input();
        System.out.println("Please input your password:");
        String pwd = ScannerUtils.input();
        // 封装信息
        Message message = new Message();
        message.setType(MessageType.LOGIN);
        message.setUsername(name);
        message.setPassword(pwd);
        // 发送对象数据
        MsgUtils.writeMsg(outputStream,message);
        Optional<Message> msg = MsgUtils.readMsg(inputStream);
        if (msg.isPresent() && Constent.SUCCESS.equals(msg.get().getContent())){
            System.out.println(msg.get().getContent());
            return name;
        }
        System.out.println(msg.get().getContent());
        return null;
    }
}
