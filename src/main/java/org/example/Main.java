package org.example;

public class Main {

    public static void main(String[] args){
        try {

            NettyServer netty = new NettyServer();
            netty.start(5001);
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }
}
