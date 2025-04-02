package com.easycc.client;

import com.easycc.proxy.RpcProxyUtils;
import com.easycc.api.UserService;

public class ClientMain {
    public static void main(String[] args) {

        RpcProxyUtils rpcProxy = new RpcProxyUtils("127.0.0.1", 8888);
        UserService userService = rpcProxy.getProxy(UserService.class);
        String user = userService.getUser(1);
        System.out.println(user);


    }




}
