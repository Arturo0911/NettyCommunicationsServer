package org.example.helpers;

import java.net.SocketAddress;

public class NetworkMessage {

    private final SocketAddress remoteAddress;
    private final Object message;

    public NetworkMessage(Object message, SocketAddress remoteAddress){
        this.remoteAddress = remoteAddress;
        this.message = message;
    }

    public SocketAddress getRemoteAddress(){
        return remoteAddress;
    }

    public Object getMessage(){
        return message;
    }
}
