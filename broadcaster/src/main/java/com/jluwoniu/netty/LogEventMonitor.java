package com.jluwoniu.netty;

import com.jluwoniu.netty.handler.LogEventDecoder;
import com.jluwoniu.netty.handler.LogEventHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * Created by jluwo on 2017/11/17.
 */
public class LogEventMonitor {

    private final EventLoopGroup group;
    private final Bootstrap bootstrap;

    public LogEventMonitor(InetSocketAddress socketAddress)
    {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST,true).localAddress(socketAddress).handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new LogEventDecoder());
                pipeline.addLast(new LogEventHandler());
            }
        });
    }

    public Channel bind()
    {
        return bootstrap.bind().syncUninterruptibly().channel();
    }

    public void stop()
    {
        group.shutdownGracefully();
    }

    public static void main(String[] args)
    {
        int port = 9999;
        if(args.length ==1)
        {
            port = Integer.parseInt(args[0]);
        }
        LogEventMonitor monitor = new LogEventMonitor(new InetSocketAddress(port));
        Channel channel = monitor.bind();
        System.out.println("LogEventMonitor running");
        try {
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            monitor.stop();
        }


    }
}
