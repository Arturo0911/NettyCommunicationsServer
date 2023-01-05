package org.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

public class NettyServer {

    public final ChannelGroup group = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    void start() throws InterruptedException{
        System.out.println("connection at port: "+ 5001);
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new SimpleTCPChannelInitializer(group))
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(5001).sync();
            if(f.isSuccess()) System.out.println("server started successfully...");
            f.channel().closeFuture().sync();
        }finally {
            System.out.println("Stopping server");

            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }
}