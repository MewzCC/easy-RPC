package com.newcc.register.impl;

import com.newcc.constant.RpcConstant;
import com.newcc.dto.RpcRequest;
import com.newcc.factory.SingletonFactory;
import com.newcc.loadbalance.impl.RandomLoadBalance;
import com.newcc.register.ServiceDiscovery;
import com.newcc.register.zk.ZkClient;
import com.newcc.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.List;
@Slf4j
public class zkServiceDiscoveryImpl implements ServiceDiscovery {
    private ZkClient zkClient;

    public zkServiceDiscoveryImpl() {
        this(SingletonFactory.getInstance(ZkClient.class));
    }

    public zkServiceDiscoveryImpl(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    @Override
    public InetSocketAddress lookupServer(RpcRequest request) {

        // 拿到接口名
        String serverInterfaceName = request.getInterfaceName();
        String serverPath = RpcConstant.ZK_PATH + serverInterfaceName;

        List<String> childrenNode = zkClient.getChildrenNode(serverPath);
        if (childrenNode == null || childrenNode.size() == 0) {
            log.error("没有可用的服务器");
            return null;
        }
        // 负载均衡
        RandomLoadBalance randomLoadBalance = new RandomLoadBalance();
        String selectServer = randomLoadBalance.selectServer(childrenNode);
        InetSocketAddress inetSocketAddress = IPUtils.toInetSocketAddress(selectServer);

        return inetSocketAddress;
    }
}
