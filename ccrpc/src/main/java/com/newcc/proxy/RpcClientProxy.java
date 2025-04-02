package com.newcc.proxy;

import com.easycc.proxy.ProxyFactory;
import com.newcc.config.RpcServiceConfig;
import com.newcc.dto.RpcRequest;
import com.newcc.dto.RpcResponse;
import com.newcc.enums.RpcRespStatusEnums;
import com.newcc.exception.RpcException;
import com.newcc.transmission.RpcClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.UUID;

/**
 * 通过jdk动态代理完成
 */
public class RpcClientProxy implements InvocationHandler {
    private RpcClient rpcClient;
    private RpcServiceConfig config;


    public RpcClientProxy(RpcClient rpcClient) {
        this(rpcClient, new RpcServiceConfig());
    }

    public RpcClientProxy(RpcClient rpcClient, RpcServiceConfig config) {
        this.rpcClient = rpcClient;
        this.config = config;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<?> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        RpcRequest request = RpcRequest.builder()
                .reqId(UUID.randomUUID().toString())
                .interfaceName(method.getDeclaringClass().getCanonicalName())
                .methodName(method.getName())
                .params(args)
                .paramTypes(method.getParameterTypes())
                .version(config.getVersion())
                .group(config.getGroup())
                .build();

        // 发送请求
        RpcResponse<?> rpcResponse = rpcClient.sendReq(request);
        checked(request,rpcResponse);

        return rpcResponse.getData();
    }

    public void checked(RpcRequest request,RpcResponse rpcResponse){
        if(Objects.isNull(rpcResponse)){
            throw new RpcException(rpcResponse+"为空,调用服务失败");
        }
        if(!Objects.equals(request.getReqId(),rpcResponse.getRespId())){
            throw new RpcException("请求和响应不匹配");
        }

        if(RpcRespStatusEnums.isFailed(rpcResponse.getCode())){
            throw new RpcException("响应码错误 "+rpcResponse.getMsg());
        }

    }
}
