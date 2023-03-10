package org.example.handerls;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import org.example.helpers.NetworkMessage;
import java.net.InetSocketAddress;


public class NetworkMessageHandler extends ChannelDuplexHandler {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if(ctx.channel() instanceof DatagramChannel){
            DatagramPacket packet = (DatagramPacket) msg;
            ctx.fireChannelRead(new NetworkMessage(packet.content(), packet.sender()));
        } else if(msg instanceof ByteBuf){
            ByteBuf buffer = (ByteBuf) msg;
            ctx.fireChannelRead(new NetworkMessage(buffer, ctx.channel().remoteAddress()));
        }

    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) {
        if(msg instanceof NetworkMessage){
            NetworkMessage message = (NetworkMessage) msg;
            if(ctx.channel() instanceof DatagramChannel){
                InetSocketAddress recipient = (InetSocketAddress) message.getRemoteAddress();
                InetSocketAddress sender = (InetSocketAddress) ctx.channel().localAddress();
                ctx.write(new DatagramPacket((ByteBuf) message.getMessage(), recipient, sender), promise);
            }else{
                ctx.write(message.getMessage(), promise);
            }
        }else{
            ctx.write(msg, promise);
        }
    }

}
