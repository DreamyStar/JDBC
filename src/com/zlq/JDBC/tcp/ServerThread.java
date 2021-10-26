package com.zlq.JDBC.tcp;

import java.io.*;
import java.net.Socket;

/**
 * 使用Socket套接字，实现基于TCP的多客户端和服务端的双向通信。
 */

public class ServerThread implements Runnable {
    Socket socket = null;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        OutputStream os = null;
        PrintWriter pw = null;//具有自动行刷新的缓冲字符输出流,特点是可以按行写出字符串,并且可以自动行刷新。
        try {
            //与客户端建立通信，获取输入流，读取取客户端提供的信息
            is = socket.getInputStream();
            isr = new InputStreamReader(is, "utf8");
            br = new BufferedReader(isr);
            String data = null;
            while ((data = br.readLine()) != null) {//循环读取客户端的信息
                System.out.println("我是服务器，客户提交信息为：" + data);
            }
            socket.shutdownInput();//只关闭相应的输入流，并没有同时关闭网络连接的功能。
            //获取输出流，响应客户端的请求
            os = socket.getOutputStream();
            pw = new PrintWriter(os);
            pw.write("服务器端响应成功！");
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
                if (os != null) {
                    os.close();
                }
                if (br != null) {
                    br.close();
                }
                if (is != null) {
                    is.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
}
