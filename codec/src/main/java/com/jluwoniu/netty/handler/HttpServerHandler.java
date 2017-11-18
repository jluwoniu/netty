package com.jluwoniu.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {

        //System.out.println("httpServerHandler received message");
        String uri = msg.uri();
        String method = msg.method().toString();
        HttpHeaders headers = msg.headers();
        HttpVersion version = msg.protocolVersion();
        ByteBuf content = msg.content();
        StringBuffer sb = new StringBuffer();
        sb.append("HTTP VERSION:").append(version.toString()).append(" ");
        sb.append("method:").append(method).append(" ");
        sb.append("uri:").append(uri);
        System.out.println(sb.toString());
        HttpVersion httpVersion = new HttpVersion("HTTP",1,1,false);
        ByteBuf body = ctx.alloc().buffer().writeBytes("<html><body>hello netty</body></html>".getBytes(CharsetUtil.UTF_8));
        DefaultFullHttpResponse response = new DefaultFullHttpResponse(httpVersion,HttpResponseStatus.OK,body);

        //ctx.channel().writeAndFlush(body);
        ctx.writeAndFlush(response);
    }
}
