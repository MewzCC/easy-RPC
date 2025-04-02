package com.easycc.server;

import com.easycc.HelloService;

import java.io.Serializable;

public class HelloServiceImpl implements HelloService, Serializable {

    @Override
    public String say(String name) {
        return name;
    }
}
