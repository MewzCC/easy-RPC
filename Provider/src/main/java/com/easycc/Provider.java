package com.easycc;

import com.cc.protocol.HttpServer;
import com.cc.register.LocalRegister;

public class Provider {
    public static void main(String[] args) {

        LocalRegister.register(HelloService.class.getName(), "1.0",HelloServiceImpl.class);


        HttpServer httpServer = new HttpServer();
        httpServer.start("hostname",8080);


    }
}
