package com.newcc.transmission.socket.server;

import com.newcc.config.RpcServiceConfig;
import com.newcc.dto.RpcRequest;
import com.newcc.dto.RpcResponse;
import com.newcc.factory.SingletonFactory;
import com.newcc.handler.RpcRequestHandler;
import com.newcc.provider.ServiceProvider;
import com.newcc.provider.impl.SimpleServiceProvider;
import com.newcc.provider.impl.ZkServiceProvider;
import com.newcc.transmission.RpcServer;
import com.newcc.utils.ThreadPoolUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class SocketRpcServer implements RpcServer {
    private int port;
    private final ServiceProvider serviceProvider;

    private final RpcRequestHandler rpcRequestHandle;

    private final ExecutorService executors;

    public SocketRpcServer(int port) {
        this(port, SingletonFactory.getInstance(ZkServiceProvider.class));
    }

    public SocketRpcServer(int port, ServiceProvider serviceProvider) {
        this.port = port;
        this.serviceProvider = serviceProvider;
        this.rpcRequestHandle = new RpcRequestHandler(serviceProvider);
        this.executors = ThreadPoolUtils.createIOIntensiveThreadPool("Socket-RpcServer-");
    }

    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            log.info("服务器启动完成,port:{}", port);

            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                // 每监听到一个任务就进行处理
                executors.submit(new SocketRequestHandler(socket, rpcRequestHandle));
                log.info("客户端连接成功,{}:{}", socket.getInetAddress(), socket.getPort());
            }

        } catch (Exception e) {
            log.info("error");
        }
    }

    @Override
    public void publicService(RpcServiceConfig config) {
        serviceProvider.publicService(config);
    }


}
