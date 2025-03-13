package com.cc.register;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地注册
 */
public class LocalRegister {
    private static Map<String,Class> map = new HashMap<>();

    // 注册
    public  static void register(String interFaceName,String version,Class impl){
        map.put(interFaceName+version,impl);
    }

    public static Class get(String interfaceName,String version){
        return map.get(interfaceName+version);
    }

}
