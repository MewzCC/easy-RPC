package com.newcc.register.impl;

import com.newcc.constant.RpcConstant;
import com.newcc.factory.SingletonFactory;
import com.newcc.register.RegisterService;
import com.newcc.register.zk.ZkClient;
import com.newcc.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class zkRegisterServiceImpl implements RegisterService {
    private ZkClient zkClient;

    public zkRegisterServiceImpl() {
        this(SingletonFactory.getInstance(ZkClient.class));
    }

    public zkRegisterServiceImpl(ZkClient zkClient) {
        this.zkClient = zkClient;
    }

    @Override
    public void serverRegister(String interfaceName, InetSocketAddress address) {
        log.info("注册服务{},地址为{}", interfaceName, address);

        zkClient = new ZkClient();
        String path = RpcConstant.ZK_PATH + IPUtils.TranceIpPort(address);
        zkClient.createPersistent(path);

        log.info("服务注册成功");

    }

}
