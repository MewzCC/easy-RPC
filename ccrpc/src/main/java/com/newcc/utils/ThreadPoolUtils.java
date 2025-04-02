package com.newcc.utils;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import cn.hutool.core.util.StrUtil;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import lombok.extern.slf4j.Slf4j;
import sun.nio.ch.ThreadPool;

import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class ThreadPoolUtils {
    private static final Map<String, ExecutorService> threadPoolMap = new ConcurrentHashMap<>();
    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    private static final int CPU_INTENSIVE_SIZE = CORE_POOL_SIZE + 1;
    private static final int IO_INTENSIVE_SIZE = CORE_POOL_SIZE * 2;
    private static final int DEFAULT_QUEUE_SIZE = 128;
    private static final int DEFAULT_KEEP_ALIVE_TIME = 60;


    // cpu密集型线程池
    public static ExecutorService createCpuIntensiveThreadPool(
            String threadName
    ) {
        return createThreadPool(CPU_INTENSIVE_SIZE, threadName);
    }

    // io密集型线程池
    public static ExecutorService createIOIntensiveThreadPool(
            String threadName
    ) {
        return createThreadPool(IO_INTENSIVE_SIZE, threadName);
    }

    public static ExecutorService createThreadPool(
            String threadName
    ) {
        return createThreadPool(CORE_POOL_SIZE, threadName);
    }

    public static ExecutorService createThreadPool(
            int coreSize,
            String threadName
    ) {
        return createThreadPool(coreSize, coreSize, threadName);
    }

    public static ExecutorService createThreadPool(
            int coreSize,
            int maxPoolSize,
            String threadName
    ) {
        return createThreadPool(coreSize, maxPoolSize, DEFAULT_QUEUE_SIZE, DEFAULT_KEEP_ALIVE_TIME, threadName, false);
    }

    public static ExecutorService createThreadPool(
            int coreSize,
            int maxPoolSize,
            long keepAliveTime,
            String threadName,
            boolean isDaemon
    ) {

        return createThreadPool(coreSize, maxPoolSize, DEFAULT_QUEUE_SIZE, keepAliveTime, threadName, isDaemon);
    }

    /**
     * 创建并配置线程池实例，并将其存入线程池映射表。
     *
     * @param threadName    线程池名称前缀，用于标识线程池实例
     * @param coreSize      线程池的核心线程数
     * @param maxPoolSize   线程池的最大线程数
     * @param keepAliveTime 空闲线程存活时间（单位：秒）
     * @param queueSize     任务队列的最大容量
     * @param isDaemon      是否将线程配置为守护线程
     * @return 配置完成的ThreadPoolExecutor实例
     */
    public static ExecutorService createThreadPool(
            int coreSize,
            int maxPoolSize,
            int queueSize,
            long keepAliveTime,
            String threadName,
            boolean isDaemon
    ) {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(coreSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize), createThreadFactory(threadName, isDaemon));
        /* 将创建的线程池实例存入线程池映射表，并返回该实例 */
        threadPoolMap.put(threadName, threadPoolExecutor);
        log.info("创建线程池:{}", threadName);
        return threadPoolExecutor;
    }

    /**
     * 使用守护线程
     **/
    public static ThreadFactory createThreadFactory(String poolName, boolean isDaemon) {
        ThreadFactoryBuilder threadFactoryBuilder = ThreadFactoryBuilder.create().setDaemon(isDaemon);

        if (StrUtil.isBlank(poolName)) {
            return threadFactoryBuilder.build();
        }

        return threadFactoryBuilder.setNamePrefix(poolName).build();

    }

    /**
     * 使用非守护线程
     **/
    public static ThreadFactory createThreadFactory(String poolName) {
        if (StrUtil.isBlank(poolName)) {
            return createThreadFactory(poolName, false);
        }

        return ThreadFactoryBuilder.create().setNamePrefix(poolName).build();

    }


    /** 关闭线程池 **/
    public static void shutdownAllThreadPool() {
        // 从线程池映射表中获取线程池实例
        threadPoolMap.entrySet().stream().forEach(entry -> {
            String threadName = entry.getKey();
            ExecutorService executorService = entry.getValue();
            // 关闭线程池
            executorService.shutdown();
            log.info("关闭线程池:{}", threadName);

            if(executorService.isTerminated()){
                log.info("{}，线程池已经关闭", threadName);
                return;
            }

            try {
                // 等待线程池关闭
                if(executorService.awaitTermination(60, TimeUnit.SECONDS)){
                    log.info("{}，线程池关闭成功", threadName);

                }else{
                    log.info("{}，线程池关闭超时", threadName);
                    executorService.shutdownNow();
                }

            } catch (InterruptedException e) {
                // 强制关闭
                executorService.shutdownNow();
                log.error("出现异常,线程池关闭失败", e);
            }
        });
    }

}
