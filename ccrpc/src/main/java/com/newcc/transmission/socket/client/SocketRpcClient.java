package com.newcc.transmission.socket.client;

import com.newcc.dto.RpcRequest;
import com.newcc.dto.RpcResponse;
import com.newcc.factory.SingletonFactory;
import com.newcc.register.ServiceDiscovery;
import com.newcc.transmission.RpcClient;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

@Slf4j
public class SocketRpcClient implements RpcClient {

    private ServiceDiscovery serviceDiscovery;

    public SocketRpcClient(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public SocketRpcClient() {
        this(SingletonFactory.getInstance(ServiceDiscovery.class));
    }

    @Override
    public RpcResponse<?> sendReq(RpcRequest request) {
        InetSocketAddress address = serviceDiscovery.lookupServer(request);
        try (Socket socket = new Socket(address.getAddress(),address.getPort())){
            // 发送数据
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();

            // 接收数据
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object o = objectInputStream.readObject();

            return (RpcResponse<?>) o;
        } catch (Exception e) {
            log.info("发送数据失败 ");
        }
        return null;
    }
}
