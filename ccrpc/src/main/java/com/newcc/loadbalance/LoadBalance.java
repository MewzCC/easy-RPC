package com.newcc.loadbalance;

import java.util.List;

public interface LoadBalance {
    String selectServer(List<String> addrList);
}
