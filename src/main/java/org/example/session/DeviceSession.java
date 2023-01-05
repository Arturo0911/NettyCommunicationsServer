package org.example.session;

import io.netty.channel.ChannelHandlerContext;

public class DeviceSession {

    private final ChannelHandlerContext ctx;

    //private final String imei;


    public DeviceSession(ChannelHandlerContext ctx /*String imei*/){
        this.ctx = ctx;
        //this.imei = imei;
    }
    public ChannelHandlerContext getCtx(){
        return ctx;
    }
    
    /*public String getImei(){
        return imei;
    }*/


}
