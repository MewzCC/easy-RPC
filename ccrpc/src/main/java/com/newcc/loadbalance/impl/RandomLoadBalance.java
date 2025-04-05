package com.newcc.loadbalance.impl;

import cn.hutool.core.util.RandomUtil;
import com.newcc.loadbalance.LoadBalance;

import java.util.List;

public class RandomLoadBalance implements LoadBalance {
    @Override
    public String selectServer(List<String> addrList) {
        return RandomUtil.randomEle(addrList);
    }
}
