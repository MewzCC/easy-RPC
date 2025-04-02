package com.newcc.transmission.socket.server;

import com.newcc.dto.RpcRequest;
import com.newcc.dto.RpcResponse;
import com.newcc.handler.RpcRequestHandler;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

@Slf4j
@AllArgsConstructor
public class SocketRequestHandler implements Runnable {
    private final Socket socket;
    private final RpcRequestHandler requestHandler;

    @Override
    @SneakyThrows
    public void run() {

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        RpcRequest request = (RpcRequest)objectInputStream.readObject();
        log.info("获取请求参数{}",request);

        Object data = requestHandler.invoke(request);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        RpcResponse<Object> response = RpcResponse.success(request.getReqId(), data);
        log.info("返回结果参数{}",response.getData());
        objectOutputStream.writeObject(response);
        objectOutputStream.flush();



    }
}
