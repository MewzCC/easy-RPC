package com.easycc.server;

import com.easycc.api.UserService;

import java.io.Serializable;

public class UserServiceImpl implements UserService, Serializable {
    @Override
    public String getUser(Integer id) {
        return "success use this api  haha "+ id*10;
    }
}
