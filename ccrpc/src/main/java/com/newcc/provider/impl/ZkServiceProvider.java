package com.newcc.provider.impl;

import com.easycc.protocol.DispatcherServlet;
import com.newcc.config.RpcServiceConfig;
import com.newcc.factory.SingletonFactory;
import com.newcc.provider.ServiceProvider;
import com.newcc.register.RegisterService;
import com.newcc.register.zk.ZkClient;
import lombok.extern.slf4j.Slf4j;


import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
public class ZkServiceProvider implements ServiceProvider {
    private final static Map<String, Object> serviceMap= new ConcurrentHashMap<>();
    private RegisterService registerService;

    public ZkServiceProvider(RegisterService registerService) {
        this.registerService = registerService;
    }

    public ZkServiceProvider() {
        this(SingletonFactory.getInstance(RegisterService.class));
    }


    public void publicService(String rpcServiceName, Object server) {
        // 服务注册

        // 获取当前服务ip 和端口
        Socket socket = new Socket();
        InetSocketAddress address = new InetSocketAddress(socket.getLocalAddress(), socket.getLocalPort());
        registerService.serverRegister(rpcServiceName, address);

        serviceMap.put(rpcServiceName, server);
        saveFile();

    }

    public void publicService(RpcServiceConfig config) {
        config.getRpcServiceName().forEach(rpcSetviceName -> {
            publicService(rpcSetviceName, config.getService());
        });
    }

    @Override
    public Object getService(String serviceName) {
        getFile();
        if(!serviceMap.containsKey(serviceName)){
            log.info("未找到服务{}",serviceName);
        }
        return serviceMap.get(serviceName);
    }

    static void saveFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/temp.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(serviceMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Map<String,Object> getFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("/temp.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map<String,Object>) objectInputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
