package org.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;

import java.net.SocketAddress;
import java.util.ArrayList;

public class SimpleTCPChannelHandler extends ChannelInboundHandlerAdapter {

    private final ChannelGroup group;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        group.add(ctx.channel());
        //super.userEventTriggered(ctx, evt);
    }

    public SimpleTCPChannelHandler(ChannelGroup group){
        this.group = group;
    }
    CommandHandler command = new CommandHandler();
    private final ArrayList<SocketAddress> devices = new ArrayList<>();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        devices.add(ctx.channel().remoteAddress());
    }



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        String s = (String) msg;

        try {
            //System.out.println("group: "+group);
            System.out.println(devices);
            command.checkStatusCoban(ctx, s);
            String uniqueId  = command.decode(s);
            String commandEncoded = getCommand("alarmDisarm", uniqueId);
            System.out.println(commandEncoded);
            ctx.channel().writeAndFlush(commandEncoded);
            group.writeAndFlush("hello");
        }catch (Exception e ){
            e.printStackTrace();
        }
        //ctx.channel().writeAndFlush("thanks\n");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //devices.add(ctx.channel().remoteAddress());
        Utils.log(ctx.channel().remoteAddress(), "Channel Active");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        Utils.log(ctx.channel().remoteAddress(), "Channel Inactive");
    }

    public String getCommand(String commandType, String uniqueId){
        return command.encodeCommand(commandType, uniqueId);
    }

}
