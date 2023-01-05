package org.example.helpers;

import io.netty.channel.ChannelHandler;

public interface PipelineBuilder {

    void addLast(ChannelHandler channelHandler);
}
