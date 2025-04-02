package com.easycc.client;

import com.newcc.dto.RpcRequest;
import com.newcc.dto.RpcResponse;
import com.newcc.transmission.RpcClient;
import com.newcc.transmission.socket.client.SocketRpcClient;
import com.easycc.api.UserSerice;
import com.newcc.utils.ThreadPoolUtils;

import java.util.concurrent.ExecutorService;

public class ClientMain {
    public static void main(String[] args) {
        RpcClient rpcClient = new SocketRpcClient("127.0.0.1",8888);

        RpcRequest request = RpcRequest.builder()
                .reqId("123")
                .interfaceName(UserSerice.class.getName())
                .methodName("getUser")
                .params(new Object[]{1})
                .paramTypes(new Class[]{Integer.class})
                .build();


        // 多线程进行调用
        for (int i = 0; i < 10; i++) {
            ExecutorService serviceThread = ThreadPoolUtils.createThreadPool("serviceThread");
            serviceThread.submit(() -> {
                RpcResponse<?> rpcResponse = rpcClient.sendReq(request);
                Object data = rpcResponse.getData();
                System.out.println(data);
            });
        }

    }


}
