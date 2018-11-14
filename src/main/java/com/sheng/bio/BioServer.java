package com.sheng.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {
    public static void main(String[] args) {
        int port = 6666;

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务端启动。。。。。");

            while (true) {
                Socket socket = serverSocket.accept();
                BioServerHandleExecutePool executePool = new BioServerHandleExecutePool(50, 10000);
//                new Thread(new BioServerHandle(socket)).start();
                executePool.execute(new BioServerHandle(socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}