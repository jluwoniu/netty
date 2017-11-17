package com.jluwoniu.netty.handler;

import com.jluwoniu.netty.event.LogEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * Created by jluwo on 2017/11/17.
 */
public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {
    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception {
        ByteBuf buf = msg.content();
        int index = buf.indexOf(0,buf.readableBytes(), LogEvent.getSEPARATOR());
        String file = buf.slice(0,index).toString(CharsetUtil.UTF_8);
        String message = buf.slice(index + 1,buf.readableBytes()).toString(CharsetUtil.UTF_8);
        LogEvent logEvent = new LogEvent(msg.sender(),file,message,System.currentTimeMillis());
        out.add(logEvent);
    }
}
