package com.jluwoniu.netty.handler;

import com.jluwoniu.netty.event.LogEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by jluwo on 2017/11/17.
 */
public class LogEventEncoder extends MessageToMessageEncoder<LogEvent>{

    private final InetSocketAddress remoteAddress;

    public LogEventEncoder(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, LogEvent msg, List<Object> out) throws Exception {
        byte[] file = msg.getFile().getBytes(CharsetUtil.UTF_8);
        byte[] message = msg.getMessage().getBytes(CharsetUtil.UTF_8);
        ByteBuf buf = ctx.alloc().buffer(file.length + message.length + 1);
        buf.writeBytes(file);
        buf.writeByte(LogEvent.getSEPARATOR());
        buf.writeBytes(message);
        DatagramPacket datagramPacket = new DatagramPacket(buf,remoteAddress);
        out.add(datagramPacket);
    }
}
