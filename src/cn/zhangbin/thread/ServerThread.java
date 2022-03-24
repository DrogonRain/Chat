package cn.zhangbin.thread;

import cn.zhangbin.Message;
import cn.zhangbin.constent.Constent;
import cn.zhangbin.constent.MessageType;
import cn.zhangbin.util.MsgUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Optional;

public class ServerThread extends Thread{

    private Socket socket;

    public ServerThread(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            // 读取内容
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
        ){
            while(true){
                Optional<Message> message = MsgUtils.readMsg(inputStream);
                if (message.isPresent()){
                    Message msg = message.get();
                    switch (msg.getType()){
                        case MessageType.LOGIN:
                            loginHandler(outputStream,inputStream,msg,socket);
                            break;
                        case MessageType.TO_SERVER:
                            sendToClient(outputStream,inputStream,msg);
                            break;
                        case MessageType.TO_FRIEND:
                            sendToTarget(msg);
                            break;
                        case MessageType.TO_ALL:
                            sendToAll(msg);
                            break;
                        default:
                            break;
                    }
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void sendToAll(Message message) {
        for (Map.Entry<String,Socket> map : MessageType.ONLINE_USERS.entrySet()){
            try {
                MsgUtils.writeMsg(map.getValue().getOutputStream(),message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 给目标用户发送消息
    private void sendToTarget(Message message) {
        // 找到对应的socket
        Socket socket = MessageType.ONLINE_USERS.get(message.getFriendUsername());
        try {
            MsgUtils.writeMsg(socket.getOutputStream(),message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToClient(OutputStream outputStream, InputStream inputStream,Message message) {
        System.out.println(message.getUsername()+": "+message.getContent());
        MsgUtils.writeMsg(outputStream,new Message(MessageType.FROM_SERVER,MessageType.REPLY,MessageType.SERVER_NAME));
    }

    private void loginHandler(OutputStream outputStream, InputStream inputStream,Message message,Socket socket) {
        if (MessageType.ONLINE_USERS.containsKey(message.getUsername())){
            MsgUtils.writeMsg(outputStream,new Message(MessageType.FROM_SERVER, "用户名已存在!","server"));
            return;
        }
// 判断用户名及密码是否正确
        if (message.getUsername() == null || !message.getPassword().equals("123")){
            MsgUtils.writeMsg(outputStream,new Message(MessageType.FROM_SERVER, Constent.FAIL,"server"));
        }else{
            // 将socket及用户名保存在map中
            MessageType.ONLINE_USERS.put(message.getUsername(),socket);
            System.out.println(message.getUsername()+" , login successfully!");
            // 向客户端返回消息
            MsgUtils.writeMsg(outputStream,new Message(MessageType.FROM_SERVER,Constent.SUCCESS,"server"));
        }
    }
}
