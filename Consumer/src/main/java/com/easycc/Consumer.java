package com.easycc;

import com.cc.common.Invocation;
import com.cc.protocol.HttpClient;

public class Consumer {
    public static void main(String[] args) {
        // 获取invocation
        Invocation invocation = new Invocation(HelloService.class.getName(),"say",
                new Class[]{String.class},new Object[]{"cc"});

        HttpClient httpClient = new HttpClient();
        String res = httpClient.send("localhost", 8080, invocation);
        System.out.println(res);

    }
}
