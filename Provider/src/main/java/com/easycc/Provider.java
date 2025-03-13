package com.easycc;

import com.cc.protocol.HttpServer;

public class Provider {
    public static void main(String[] args) {

        HttpServer httpServer = new HttpServer();
        httpServer.start("hostname",8080);


    }
}
