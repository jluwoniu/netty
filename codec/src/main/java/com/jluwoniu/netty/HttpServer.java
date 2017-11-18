package com.jluwoniu.netty;

import com.jluwoniu.netty.handler.HttpServerHandler;
import com.jluwoniu.netty.initializer.HttpAggregatorChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class HttpServer {

    private final ServerBootstrap bootstrap;
    private final EventLoopGroup boss;
    private final EventLoopGroup worker;
    private final InetSocketAddress address;

    public HttpServer(InetSocketAddress address)
    {
        bootstrap = new ServerBootstrap();
        boss = new NioEventLoopGroup(1);
        worker = new NioEventLoopGroup();
        this.address = address;
        bootstrap.group(boss,worker).channel(NioServerSocketChannel.class).localAddress(address).childHandler(new HttpAggregatorChannelInitializer(false));
    }
    public void start()
    {
        try {
            ChannelFuture future = bootstrap.bind().sync();
            System.out.println("http server listen at: " + address.getPort());
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }


    public static void main(String[] args)
    {
        int port = 8080;
        if(args.length == 1)
        {

            port = Integer.parseInt(args[0]);
        }
        new HttpServer(new InetSocketAddress(port)).start();
    }
}
