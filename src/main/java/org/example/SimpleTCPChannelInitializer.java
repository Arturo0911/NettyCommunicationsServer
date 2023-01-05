package org.example;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class SimpleTCPChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final ChannelGroup group;

    public SimpleTCPChannelInitializer(ChannelGroup group){
        this.group = group;
    }
    
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline().addLast(new StringEncoder());
        socketChannel.pipeline().addLast(new StringDecoder());
        socketChannel.pipeline().addLast(new SimpleTCPChannelHandler(group));
    }
}
