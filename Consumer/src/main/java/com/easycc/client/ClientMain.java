package com.easycc.client;

import com.easycc.proxy.RpcProxyUtils;
import com.easycc.api.UserService;

public class ClientMain {
    public static void main(String[] args) {

        RpcProxyUtils rpcProxy = new RpcProxyUtils();
        UserService userService = rpcProxy.getProxy(UserService.class);
        String user = userService.getUser(1);
        System.out.println(user);


    }




}
