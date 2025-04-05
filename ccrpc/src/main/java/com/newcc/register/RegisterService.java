package com.newcc.register;

import java.net.InetSocketAddress;

public interface RegisterService {

    void serverRegister(String interfaceName, InetSocketAddress address);
}
