package com.easycc;


import com.cc.proxy.ProxyFactory;



public class Consumer {
    public static void main(String[] args) {


        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String cc = helloService.say("cc");
        System.out.println(cc);


    }
}
