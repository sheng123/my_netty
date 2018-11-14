package com.sheng.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by sheng on 14/11/2018
 *
 * @author sheng
 */
public class TimeServer {

    private void bind(int port) throws Exception {

        // 两个线程池组：boss组用来处理服务端接收客户端的连接；worker组用来处理 SocketChannel 网络读写
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // ServerBootstrap 用来启动 NIO 服务端的辅助启动类，目的是降低服务端的开发复杂度
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            // 将两个线程组作为参数传入到 ServerBootstrap 中；同时设置服务端的 ServerSocketChannel；
            // 设置网络读写的处理类为 ChildChannelHandler
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChildChannelHandler());

            // 调用 bind 方法绑定监听端口，调用同步阻塞方法 sync 等待绑定操作完成；
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

            // sync 对该方法进行阻塞，等待服务端链路关闭之后 main 函数才退出
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 优雅退出，释放所有相关的资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        int port = 6666;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        new TimeServer().bind(port);
    }
}
