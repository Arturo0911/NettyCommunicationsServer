package org.example.handerls;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.example.model.Position;

import java.net.InetSocketAddress;

public class RemoteAddressHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        InetSocketAddress remoteAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        String hostAddress = remoteAddress != null ?remoteAddress.getAddress().getHostAddress() : null;
        Position position = (Position) msg;

        position.set("ip", hostAddress);
        ctx.fireChannelRead(msg);
    }
}
