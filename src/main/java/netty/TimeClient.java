package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * Created by sheng on 14/11/2018
 *
 * @author sheng
 */
public class TimeClient {

    public void connect(int port, String host) throws Exception {

        // 创建客户端读写 IO 的线程组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            // 创建客户端辅助启动类 Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            // 对 Bootstrap 进行配置： 设置客户端的 channel，对channel进行相关的配置；创建客户端信息的处理类
            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new LineBasedFrameDecoder(1024))
                                    .addLast(new StringDecoder())
                                    .addLast(new TimeClientHandler());
                        }
                    });

            // 调用 connect 方法发起异步连接，然后调用同步方法等待连接成功。
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

            channelFuture.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        int port = 6666;

        new TimeClient().connect(port, "127.0.0.1");
    }
}
