package org.example;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SimpleTCPChannelHandler extends SimpleChannelInboundHandler<String> {

    CommandHandler command = new CommandHandler();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //Utils.log(ctx.channel().remoteAddress(), "Channel Active");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) {
        try {
            Utils.log(ctx.channel().remoteAddress(), s);
            command.checkStatusCoban(ctx, s);
            String uniqueId  = command.decode(s);
            String commandEncoded = getCommand("alarmDisarm", uniqueId);
            System.out.println(commandEncoded);
            //ctx.channel().writeAndFlush("thanks\n");
        }catch (Exception e ){
            System.out.println(e.toString());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //System.out.println(ctx.channel().remoteAddress()+ " Channel Inactive");
    }


    public String getCommand(String commandType, String uniqueId){
        return command.encodeCommand(commandType, uniqueId);
    }



}
