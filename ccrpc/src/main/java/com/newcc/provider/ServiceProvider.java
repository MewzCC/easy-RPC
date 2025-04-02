package com.newcc.provider;

import com.newcc.config.RpcServiceConfig;
import com.newcc.dto.RpcRequest;

/**
 * 发布服务获取服务
 **/
public interface ServiceProvider {
    // 发布服务
    void publicService(RpcServiceConfig config);

    // 发现服务
    Object getService(String serviceName);

}
