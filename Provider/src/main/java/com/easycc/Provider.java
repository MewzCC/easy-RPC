package com.easycc;

import com.cc.common.URL;
import com.cc.protocol.HttpServer;
import com.cc.register.LocalRegister;
import com.cc.register.MapRemoteRegister;

public class Provider {
    public static void main(String[] args) {
        // 本地注册
        LocalRegister.register(HelloService.class.getName(),"1.0", HelloServiceImpl.class);

        // 注册中心
        URL url = new URL("localhost",8080);
        MapRemoteRegister.register(HelloService.class.getName(),url,"1.0");

        HttpServer httpServer = new HttpServer();
        httpServer.start(url.getHostname(),url.getPort());


    }
}
