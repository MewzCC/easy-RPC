package com.newcc.transmission;

import com.newcc.dto.RpcRequest;
import com.newcc.dto.RpcResponse;

public interface RpcClient {

    RpcResponse<?> sendReq(RpcRequest request);
}
