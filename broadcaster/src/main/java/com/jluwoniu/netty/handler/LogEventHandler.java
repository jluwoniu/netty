package com.jluwoniu.netty.handler;

import com.jluwoniu.netty.event.LogEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by jluwo on 2017/11/17.
 */
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogEvent msg) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(msg.getReceived());
        sb.append("[").append(msg.getSource().toString()).append("]");
        sb.append("[").append(msg.getFile()).append("]:");
        sb.append(msg.getMessage());
        System.out.println(sb.toString());
    }
}
