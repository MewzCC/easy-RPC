package com.newcc.handler;

import com.newcc.dto.RpcRequest;
import com.newcc.provider.ServiceProvider;
import com.newcc.provider.impl.SimpleServiceProvider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
@Slf4j
public class RpcRequestHandler {
    private ServiceProvider serviceProvider;

    public RpcRequestHandler(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    // 服务调用
    @SneakyThrows
    public Object invoke(RpcRequest request) {
        String serviceName = request.getRpcServiceName();
        Object service = new SimpleServiceProvider().getService(serviceName);

        log.info("获取到服务:{}", service.getClass().getCanonicalName());

        // 反射调用
        Method method = service.getClass().getMethod(request.getMethodName(), request.getParamTypes());
        return method.invoke(service, request.getParams());

    }
}
