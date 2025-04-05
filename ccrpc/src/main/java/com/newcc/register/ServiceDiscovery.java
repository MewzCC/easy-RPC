package com.newcc.register;

import com.newcc.dto.RpcRequest;

import java.net.InetSocketAddress;

public interface ServiceDiscovery {

    InetSocketAddress lookupServer(RpcRequest request);

}
