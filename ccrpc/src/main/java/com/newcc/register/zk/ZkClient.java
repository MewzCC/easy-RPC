package com.newcc.register.zk;

import cn.hutool.core.util.StrUtil;
import com.newcc.constant.RpcConstant;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
public class ZkClient {

    private CuratorFramework zkClient;

    private static final Integer BASE_SLEEP_TIME = 1000;
    private static final Integer MAX_RETRIES = 3;

    public ZkClient() {
        log.info("connecting zk");

        zkClient = CuratorFrameworkFactory.builder()
                .connectString(RpcConstant.ZK_HOSTNAME + ":" + RpcConstant.ZK_PORT)
                .connectionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES))
                .build();
        zkClient.start();
        log.info("connect zk success");

    }

    public void init(String hostname, Integer port) {

        String connectString = hostname + ":" + port;
        zkClient = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES))
                .build();

        zkClient.start();
        log.info("zkClient init connectString:{}", connectString);
    }

    /** 创建持久化节点*/
    @SneakyThrows
    public void createPersistent(String path) {

            if (zkClient.checkExists().forPath(path) != null) {
                log.info("该节已存在{}",path);
                return;
            }else{
                log.info("创建持久化节点{}",path);
                zkClient.create().creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(path);
                Stat stat = zkClient.checkExists().forPath(path);
                if (stat != null) {
                    log.info("创建持久化节点成功{}",path);
                }
            }

    }
    /** 获取zk上的所有节点*/
    @SneakyThrows
    public List<String> getChildrenNode (String path) {
        if(StrUtil.isBlank(path)){
            log.info("path is null");
            return null;
        }
        return zkClient.getChildren().forPath(RpcConstant.ZK_PATH+path);
    }
}
