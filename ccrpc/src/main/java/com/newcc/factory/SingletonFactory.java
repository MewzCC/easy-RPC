package com.newcc.factory;

import lombok.SneakyThrows;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class SingletonFactory {

    private static final Map<Class<?>, Object> instance_cache = new ConcurrentHashMap<>();

    private SingletonFactory() {
    }

    @SneakyThrows
    public static <T> T getInstance(Class<T> clazz) {
        if (Objects.isNull(clazz)) {
            throw new IllegalArgumentException("参数不能为空");
        }
        if(instance_cache.containsKey(clazz)){
            return clazz.cast(instance_cache.get(clazz));
        }


        synchronized (SingletonFactory.class) {
            if (instance_cache.containsKey(clazz)){
                return clazz.cast(instance_cache.get(clazz));
            }
            // 构造
            T t = clazz.getConstructor().newInstance();
            instance_cache.put(clazz, t);
            return t;
        }
    }
}
