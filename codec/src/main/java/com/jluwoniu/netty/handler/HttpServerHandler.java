package com.jluwoniu.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

        String uri = msg.uri();
        String method = msg.method().toString();
        HttpVersion version = msg.protocolVersion();
        StringBuffer sb = new StringBuffer();
        sb.append("HTTP VERSION:").append(version.toString()).append(" ");
        sb.append("method:").append(method).append(" ");
        sb.append("uri:").append(uri);
        System.out.println(sb.toString());
        ByteBuf body = ctx.alloc().buffer().writeBytes("<html><body>hello netty</body></html>".getBytes(CharsetUtil.UTF_8));
        int lenght = body.readableBytes();
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK,body);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH,lenght);
        response.headers().set(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
        ctx.writeAndFlush(response);
    }
}
