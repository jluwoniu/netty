package com.jluwoniu.netty.initializer;

import com.jluwoniu.netty.handler.HttpServerHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpAggregatorChannelInitializer extends ChannelInitializer<Channel> {

    private final boolean client;

    public HttpAggregatorChannelInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        if(client)
        {
            pipeline.addLast("codec",new HttpClientCodec());
            pipeline.addLast("aggregator",new HttpObjectAggregator(512 * 1024));
        }
        else
        {
            pipeline.addLast("codec",new HttpServerCodec());
            pipeline.addLast("aggregator",new HttpObjectAggregator(512 * 1024));
            pipeline.addLast("httpServerHandler",new HttpServerHandler());
        }

    }
}
