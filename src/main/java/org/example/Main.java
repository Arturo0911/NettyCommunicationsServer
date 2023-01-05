package org.example;

import com.google.inject.Injector;

public class Main {

    private static Injector injector;

    public static Injector getInjector() {
        return injector;
    }

    public static void main(String[] args){
        try {

            NettyServer netty = new NettyServer();
            netty.start();
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
    }
}
