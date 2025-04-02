package com.newcc.transmission.socket.client;

import com.newcc.dto.RpcRequest;
import com.newcc.dto.RpcResponse;
import com.newcc.transmission.RpcClient;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Slf4j
public class SocketRpcClient implements RpcClient {

    private String hostname;
    private Integer port;

    public SocketRpcClient(String hostname, Integer port) {
        this.hostname = hostname;
        this.port = port;
    }

    @Override
    public RpcResponse<?> sendReq(RpcRequest request) {
        try (Socket socket = new Socket(hostname,port)){
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
