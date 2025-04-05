package com.easycc.proxy;

import com.newcc.factory.SingletonFactory;
import com.newcc.proxy.RpcClientProxy;
import com.newcc.register.ServiceDiscovery;
import com.newcc.transmission.socket.client.SocketRpcClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RpcProxyUtils {
    private final static ServiceDiscovery serviceDiscovery = SingletonFactory.getInstance(ServiceDiscovery.class);

    public <T> T getProxy(Class<?> clazz) {
        return new RpcClientProxy(new SocketRpcClient(serviceDiscovery)).getProxy(clazz);
    }

    public <T> T getProxy(String hostname,Integer port,Class<?> clazz){
        RpcClientProxy clientProxy = new RpcClientProxy(new SocketRpcClient(serviceDiscovery));
        return clientProxy.getProxy(clazz);
    }
}
