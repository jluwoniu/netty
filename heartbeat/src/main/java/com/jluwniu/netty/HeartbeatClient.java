package com.jluwniu.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Random;

public class HeartbeatClient {

    private static final String HOST = System.getProperty("host","127.0.0.1");
    private static final int PORT = Integer.parseInt(System.getProperty("port","8080"));

    public static void main(String[] args){
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,true).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                pipeline.addLast(new HeartbeatClientHandler());
            }
        });
        try {
            ChannelFuture future = bootstrap.connect(HOST,PORT).sync();
            Random random = new Random();
            while(true){
                int second = random.nextInt(10) + 1;
                future.channel().writeAndFlush(String.format("sleep %d second then send next message",second));
                Thread.sleep(1000 * second);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        finally {
            group.shutdownGracefully();
        }

    }
}
