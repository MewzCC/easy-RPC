package com.cc.protocol;

import com.cc.common.Invocation;
import com.cc.register.LocalRegister;
import org.apache.commons.io.IOUtils;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;

import java.lang.reflect.Method;

public class HttpServerHandle {
    public void handle(HttpServletRequest req, HttpServletResponse resp) {
        // 处理请求 ---> 接口 方法参数 方法
        try {
            Invocation invocation = (Invocation) new ObjectInputStream(req.getInputStream()).readObject();
            String interFaceName = invocation.getInterFaceName();

            // 获取实现类
            Class classImpl = LocalRegister.get(interFaceName, "1.0");

            // 获取方法
            Method method = classImpl.getMethod(invocation.getMethodName(), invocation.getParameterTypes());

            Object result = method.invoke(classImpl.newInstance(), invocation.getParameters());

            /*ServletOutputStream outputStream = resp.getOutputStream();
            outputStream.write(result.toString().getBytes());*/

            IOUtils.write(result.toString(),resp.getOutputStream());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
