package com.newcc.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShutdownUtils {

    public static void shutdownAll()
    {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            log.info("系统停止，清理资源");

            ThreadPoolUtils.shutdownAllThreadPool();
        }));
    }
}
