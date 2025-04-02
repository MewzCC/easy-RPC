package com.easycc.server;

import com.easycc.api.UserSerice;

import java.io.Serializable;

public class UserServerImpl implements UserSerice, Serializable {
    @Override
    public String getUser(Integer id) {
        return "有success use this api  "+ id*10;
    }
}
