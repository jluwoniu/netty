package com.jluwoniu.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class BaseClient {

    static final String HOST = System.getProperty("host","127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port","8080"));
    static final int SIZE = Integer.parseInt(System.getProperty("size","256"));
    public static void main(String[] args) throws Exception{
        EventLoopGroup group = new NioEventLoopGroup();
        /*String message = "hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world." +
                "hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world." +
                "hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world." +
                "hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world." +
                "hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world." +
                "hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world." +
                "hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world.hello world." +
                "hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty." +
                "hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty." +
                "hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty." +
                "hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty." +
                "hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty." +
                "hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty.hello netty." +
                "hello netty.";*/
        String message = "hello world.";
        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,true).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline channelPipeline = ch.pipeline();
                    channelPipeline.addLast("decoder",new StringDecoder());
                    channelPipeline.addLast("encoder",new StringEncoder());
                    channelPipeline.addLast(new BaseClientHandler());
                }
            });
            ChannelFuture future = bootstrap.connect(HOST,PORT).sync();
            //while(true){
            //    future.channel().writeAndFlush("Hello Netty Server, I'm common client");
            //    Thread.sleep(5000);
            //}
            for(int i = 0; i < 1000; i++){
                future.channel().writeAndFlush(message);
            }
            //future.channel().writeAndFlush(message);
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
