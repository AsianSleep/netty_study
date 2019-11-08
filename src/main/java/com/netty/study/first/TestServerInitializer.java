package com.netty.study.first;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;


/**
 * Created by IntelliJ IDEA.
 * $desc$
 *
 * @author fuzhijie
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("",new HttpServerCodec());
        pipeline.addLast("",new TestServerHandler());
    }
}
