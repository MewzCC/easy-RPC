package com.cc.register;

import com.cc.common.URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 远程注册中心
 */
public class MapRemoteRegister {
   private static  Map<String, List<URL>> map = new HashMap<>();

    public static void register(String interfaceName,URL url,String version){
        List<URL> urlList = map.get(interfaceName+version);
        if(urlList == null){
            urlList = new ArrayList<>();
        }

        urlList.add(url);

        map.put(interfaceName+version,urlList);
    }

    public static List<URL> get(String interfaceName,String version){
       return map.get(interfaceName+version);
    }

}
