package com.netty.study.first;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by IntelliJ IDEA.
 * $desc$
 *
 * @author fuzhijie
 * @version 1.0.0.0
 * @since 1.0.0.0
 */
public class TestServer {
    public static void main(String[] args) throws Exception{

        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>(){
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //一个管道，里面有很多ChannelHandler，这些就像拦截器，可以做很多事
                        ChannelPipeline pipeline=ch.pipeline();
                        //增加一个处理器，neet提供的.名字默认会给，但还是自己写一个比较好
                        /**
                         * 注意这些new的对象都是多例的，每次new出来新的对象,因为每个连接的都是不同的用户
                         */
                        //HttpServerCodec完成http编解码，可查源码
                        pipeline.addLast("httpServerCodec",new HttpServerCodec());
                        //增加一个自己定义的处理器hander
                        pipeline.addLast("testHttpServerHandler",new TestServerHandler());
                    }
                });

        try {

            ChannelFuture channelFuture = bootstrap.bind(8080).sync();
            //对关闭的监听
            channelFuture.channel().closeFuture().sync();

        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
