package com.sheng.nio;

/**
 * Created by sheng on 12/11/2018
 *
 * @author sheng
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 6666;

        MultiplexerTimeServer multiplexerTimeServer = new MultiplexerTimeServer(port);

        new Thread(multiplexerTimeServer, "NIO-MultiplexerTimeServer-001").start();
    }
}