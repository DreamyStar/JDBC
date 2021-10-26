package com.zlq.JDBC.tcp;
/**
 * 使用Socket套接字，实现基于TCP的多客户端和服务端的双向通信。
 */

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        try {
            //创建客户端Socket，指定服务器地址和端口
            Socket socket = new Socket("localhost", 1700);
            //建立连接后，获取输出流，向服务器端发送信息
            OutputStream os = socket.getOutputStream();
            //输出流包装为打印流
            PrintWriter pw = new PrintWriter(os);
            //向服务器端发送信息
            pw.write("用户名：zlq；密码：123");     //写入内存缓冲区
            pw.flush();                             //刷新缓存，向服务器输出信息
            socket.shutdownOutput();                //关闭输出流
            //获取输入流，接收服务器端响应信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf8"));
            String data = null;
            while ((data = br.readLine()) != null) {
                System.out.println("我是客户端，服务器端提交信息为：" + data.toString());
            }
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
