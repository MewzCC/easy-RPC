package com.easycc;

public class HelloServiceImpl implements HelloService{

    @Override
    public String say(String name) {
        return name;
    }
}
