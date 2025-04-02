package com.easycc.proxy;

import com.newcc.proxy.RpcClientProxy;
import com.newcc.transmission.socket.client.SocketRpcClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RpcProxyUtils {
    private String hostname;
    private Integer port;

    public <T> T getProxy(Class<?> clazz) {
        return new RpcClientProxy(new SocketRpcClient(hostname, port)).getProxy(clazz);
    }

    public <T> T getProxy(String hostname,Integer port,Class<?> clazz){
        RpcClientProxy clientProxy = new RpcClientProxy(new SocketRpcClient(hostname, port));
        return clientProxy.getProxy(clazz);
    }
}
