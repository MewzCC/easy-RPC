package com.easycc;

import com.cc.common.Invocation;

public class hello {
    private HelloService helloService;

    public static void main(String[] args) {

        Invocation invocation = new Invocation(HelloService.class.getName(),"sayhello",
                new Class[]{String.class},new Object[]{"cc"});



    }
}
