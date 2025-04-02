package com.easycc.register;

import com.easycc.common.URL;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 远程注册中心
 */
public class MapRemoteRegister {
   private static  Map<String, List<URL>> map = new HashMap<>();

    public static void register(String interfaceName,URL url,String version)  {
        // todo redis 存储

        List<URL> urlList = map.get(interfaceName);
        if(urlList == null){
            urlList = new ArrayList<>();
        }

        urlList.add(url);

        map.put(interfaceName+version,urlList);
        saveFile();
    }

    public static List<URL> get(String interfaceName,String version){
        map = getFile();
       return map.get(interfaceName+version);
    }

    static void saveFile() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("/temp.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Map<String,List<URL>> getFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("/temp.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map<String, List<URL>>) objectInputStream.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

}
