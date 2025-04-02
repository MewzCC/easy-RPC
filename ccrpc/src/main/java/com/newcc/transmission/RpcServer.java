package com.newcc.transmission;

import com.newcc.config.RpcServiceConfig;

public interface RpcServer {

    void start();
    // 发布服务

    void publicService(RpcServiceConfig config);
}
