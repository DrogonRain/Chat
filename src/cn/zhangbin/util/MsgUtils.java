package cn.zhangbin.util;

import cn.zhangbin.Message;

import java.io.*;
import java.util.Optional;

public class MsgUtils {

    /**
     * 读取消息
     * @param inputStream 输入流
     * @return 读取结果
     */
    public static Optional<Message>  readMsg(InputStream inputStream){
        // 创建对象输入流
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(inputStream);
            // 封装成一个optional对象,将来调用使用的时候可以避免NullPointerException
            return Optional.ofNullable((Message) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * 写消息
     * @param outputStream 输出流
     * @param message 对应消息
     */
    public static void writeMsg(OutputStream outputStream,Message message){
        ObjectOutputStream oos = null;
        try {
            //创建对象输出流
            oos = new ObjectOutputStream(outputStream);
            oos.writeObject(message);
            // 将流中的所有数据刷出去
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
