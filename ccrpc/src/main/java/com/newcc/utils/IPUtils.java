package com.newcc.utils;

import cn.hutool.core.util.StrUtil;

import java.net.InetSocketAddress;
import java.util.Objects;

public class IPUtils {

    public static String TranceIpPort(InetSocketAddress address){
        if(Objects.isNull(address)){
            throw new IllegalArgumentException("参数不能为空");
        }

        String hostname = address.getHostString();
        if(StrUtil.equals(hostname,"localhost")){
            hostname = "127.0.0.1";
        }

        return hostname + ":" + address.getPort();
    }

    public static InetSocketAddress toInetSocketAddress(String address){
        if(StrUtil.isBlank(address)){
            throw new IllegalArgumentException("参数不能为空");
        }
        String[] split = address.split(":");
        if(split.length != 2){
            throw new IllegalArgumentException("参数格式错误");
        }
        return new InetSocketAddress(split[0],Integer.parseInt(split[1]));
    }
}
