package com.easycc;

import com.easycc.api.UserService;
import com.easycc.server.UserServiceImpl;
import com.newcc.config.RpcServiceConfig;
import com.newcc.proxy.RpcClientProxy;
import com.newcc.transmission.RpcServer;
import com.newcc.transmission.socket.server.SocketRpcServer;

public class ServerMain {
    public static void main(String[] args) {

        RpcServiceConfig config = new RpcServiceConfig(new UserServiceImpl());

        RpcServer rpcServer = new SocketRpcServer(8888);
        rpcServer.publicService(config);
        rpcServer.start();




    }
}
