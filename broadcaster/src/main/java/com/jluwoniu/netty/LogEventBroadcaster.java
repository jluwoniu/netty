package com.jluwoniu.netty;

import com.jluwoniu.netty.event.LogEvent;
import com.jluwoniu.netty.handler.LogEventEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

/**
 * Created by jluwo on 2017/11/17.
 */
public class LogEventBroadcaster {

    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final File file;
    private final InetSocketAddress address;

    public LogEventBroadcaster(File file, InetSocketAddress address) {
        this.file = file;
        this.address = address;
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioDatagramChannel.class).option(ChannelOption.SO_BROADCAST,true).handler(new LogEventEncoder(address));
    }

    public void stop()
    {
        group.shutdownGracefully();
    }

    public void run() throws Exception
    {
        Channel channel = bootstrap.bind(0).sync().channel();
        long poionter = 0;
        for(;;)
        {
            long len = file.length();
            if(len < poionter)
            {
                poionter = len;
            }
            else if(len > poionter)
            {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
                randomAccessFile.seek(poionter);
                String line;
                while ((line = randomAccessFile.readLine()) != null)
                {
                    LogEvent event = new LogEvent(file.getAbsolutePath(),line);
                    channel.writeAndFlush(event);
                    Thread.sleep(1000);
                }
                poionter = randomAccessFile.getFilePointer();
                randomAccessFile.close();
            }
            /*try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                Thread.interrupted();
                break;
            }*/
        }
    }

    public static void main(String[] args) throws Exception {
        String host = "255.255.255.255";
        int port = 9999;
        String path = "/home/logs/test.log";
        if(args.length == 2)
        {
            port = Integer.parseInt(args[0]);
            path = args[1];
        }
        InetSocketAddress address = new InetSocketAddress(host,port);
        File file = new File(path);
        LogEventBroadcaster broadcaster = new LogEventBroadcaster(file,address);

        try {
            broadcaster.run();
        }
        finally {
            broadcaster.stop();
        }
    }
}
