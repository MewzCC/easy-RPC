package com.cc.proxy;

import com.cc.common.Invocation;
import com.cc.common.URL;
import com.cc.loadbalance.LoadBalance;
import com.cc.protocol.HttpClient;
import com.cc.register.MapRemoteRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 动态代理
 */
public class ProxyFactory {

    public static <T> T getProxy(Class interfaceClass) {
        /**
         * 指定代理类的类加载器
         * 代理对象需要实现的接口列表
         * 处理代理对象方法调用的处理器
         */
        Object proxyInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 获取invocation
                Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(),
                        method.getParameterTypes(), args);

                HttpClient httpClient = new HttpClient();

                // 服务发现
                List<URL> urlList = MapRemoteRegister.get(interfaceClass.getName(), "1.0");

                // 负载均衡
                //URL url = new URL("localhost",8080);
                URL url = LoadBalance.random(urlList);

                // 服务调用
                String res = null;
                try {

                    res = httpClient.send(url.getHostname(), url.getPort(), invocation);

                } catch (Exception e) {
                    // todo 容错逻辑
                    return "服务调用错误";
                }


                return res;
            }
        });
        return (T) proxyInstance;
    }

}
