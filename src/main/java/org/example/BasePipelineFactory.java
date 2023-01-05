package org.example;

import io.netty.channel.*;
import org.example.handerls.NetworkMessageHandler;
import org.example.handerls.RemoteAddressHandler;
import org.example.handerls.WrapperInboundHandler;
import org.example.handerls.WrapperOutboundHandler;
import org.example.helpers.PipelineBuilder;

public abstract class BasePipelineFactory extends ChannelInitializer<Channel> {


    protected abstract void addProtocolHandlers(PipelineBuilder pipeline);

    private final void addHandlers(ChannelPipeline pipeline, Class<? extends ChannelHandler>... handlerClasses){
        for (Class<? extends ChannelHandler> handlerClass : handlerClasses) {
            if (handlerClass != null) {
                pipeline.addLast(Main.getInjector().getInstance(handlerClass));
            }
        }
    }
    @Override
    protected void initChannel(Channel channel) {
        final ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new NetworkMessageHandler());
        addProtocolHandlers(handler -> {
            if(handler instanceof ChannelInboundHandler){
                handler = new WrapperInboundHandler((ChannelInboundHandler) handler);
            }else{
                handler = new WrapperOutboundHandler((ChannelOutboundHandler) handler);
            }
            pipeline.addLast(handler);
        });
        addHandlers(
                pipeline,
                RemoteAddressHandler.class
        );
    }
}
