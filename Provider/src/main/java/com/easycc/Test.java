package com.easycc;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.nio.charset.StandardCharsets;

import static com.ibm.cuda.CudaError.Assert;

public class Test {
    static RetryPolicy retryPolicy = new ExponentialBackoffRetry(100, 3);
    static CuratorFramework zkClient = CuratorFrameworkFactory.builder()
            .connectString("127.0.0.1:2181")
            .retryPolicy(retryPolicy)
            .build();

    static final String PATH = "/test";

    public static void main(String[] args) throws Exception {

        zkClient.start();

        NodeCache nodeCache = new NodeCache(zkClient, PATH);
        // 监听器
        NodeCacheListener listener = () -> {
            if (nodeCache.getCurrentData()!=null){
                System.err.println(new String(nodeCache.getCurrentData().getData(), StandardCharsets.UTF_8));
            }else{
                System.out.println("not data change ");
            }
        };

        // 放入监听器
        nodeCache.getListenable().addListener(listener);

        nodeCache.start();
        testNode();
        System.in.read();

        testNode2();
    }

    public static void testNode() throws Exception {
        zkClient.create().forPath(PATH);

        // 如果父节点不存在会自动创建
        zkClient.create().creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .forPath(PATH+"/test1" ,"test".getBytes());


        Stat stat = zkClient.checkExists().forPath(PATH + "/test1");
        System.out.println(stat!=null?"success":"error");

        System.err.println(new String(zkClient.getData().forPath(PATH + "/test1"), StandardCharsets.UTF_8));
        zkClient.setData().forPath(PATH + "/test1", "test2".getBytes());
        System.err.println(new String(zkClient.getData().forPath(PATH + "/test1"), StandardCharsets.UTF_8));
        Thread.sleep(1000);
    }

    public static void testNode2() throws Exception {
        //zkClient.delete().forPath(PATH);
        zkClient.delete().deletingChildrenIfNeeded()
                .forPath(PATH);

        System.out.println(zkClient.checkExists().forPath(PATH + "/test1") == null?"success":"error" );
    }
}
