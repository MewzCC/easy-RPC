package com.easycc;

import com.easycc.common.URL;
import com.easycc.protocol.HttpServer;
import com.easycc.register.LocalRegister;
import com.easycc.register.MapRemoteRegister;
import com.easycc.server.HelloServiceImpl;

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
