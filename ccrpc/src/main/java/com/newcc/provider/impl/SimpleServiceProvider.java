package com.newcc.provider.impl;

import com.easycc.common.URL;
import com.newcc.config.RpcServiceConfig;
import com.newcc.provider.ServiceProvider;
import com.newcc.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
public class SimpleServiceProvider implements ServiceProvider {

    private static final Map<String, Object> serviceMap= new HashMap<>();

    @Override
    public void publicService(RpcServiceConfig config) {
        List<String> rpcServiceName = config.getRpcServiceName();
        if(rpcServiceName==null){
            throw new RuntimeException("未实现接口");
        }

        for (String name : rpcServiceName) {

            //redisUtils.setex(name,config.getService(),60*60*24);
            serviceMap.put(name,config.getService());
        }

        saveFile();
        log.info("服务注册成功{}",config.getRpcServiceName());

    }

    @Override
    public Object getService(String serviceName) {
        getFile();
        if(!serviceMap.containsKey(serviceName)){
            log.info("未找到服务{}",serviceName);
        }

        return serviceMap.get(serviceName);

    }

    static void saveFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/temp.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(serviceMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Map<String,Object> getFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("/temp.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map<String,Object>) objectInputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
