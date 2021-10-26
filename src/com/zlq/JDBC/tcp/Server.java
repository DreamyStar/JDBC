package com.zlq.JDBC.tcp;

/**
 * 使用Socket套接字，实现基于TCP的多客户端和服务端的双向通信。
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            //创建一个服务器端的Socket，即ServerSocket，绑定需要监听的端口号
            ServerSocket severSocket = new ServerSocket(1700);
            Socket socket = null;
            //记录连接过服务器的客户端数量
            int count = 0;
            System.out.println("***服务器即将启动，等待客户端的连接***");
            while (true) {           //循环客户端连接
                //调用accept（）方法，等待客户端的连接获取Socket实例
                socket = severSocket.accept();
                //创建新线程
                Thread thread = new Thread(new ServerThread(socket));
                thread.start();

                count++;
                System.out.println("服务器端被连接过的次数" + count);
                //InetAddress类的对象用于IP地址和域名,获取InetAddress对象的IP地址
                InetAddress address = socket.getInetAddress();
                System.out.println("当前客户端的IP为" + address.getHostAddress());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
