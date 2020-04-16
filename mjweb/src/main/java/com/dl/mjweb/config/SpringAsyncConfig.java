package com.dl.mjweb.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Setter
@Configuration
@ConfigurationProperties("async-thread-pool")
public class SpringAsyncConfig implements AsyncConfigurer {
    private Integer corePoolSize;
    private Integer maxPoolSize;
    private Integer keepAliveSeconds;
    private Integer queueCapacity;

    //构建线程工厂(其目的主要是为池线程对象起个名字)
    private ThreadFactory threadFactory = (Runnable r)-> {
        //线程安全原子操作对象(底层CAS算法，基于CPU硬件实现)
        AtomicInteger atomicInteger = new AtomicInteger(1);
        return new Thread(r,"thread-"+ atomicInteger.getAndIncrement());
    };

    //业务对象中使用@Async注解时，系统底层默认使用此方法创建的池对象
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        taskExecutor.setCorePoolSize(corePoolSize);
        //设置池中最大线程数
        taskExecutor.setMaxPoolSize(maxPoolSize);
        //设置线程最大空闲时间
        taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        //设置队列容量
        taskExecutor.setQueueCapacity(queueCapacity);
        //设置线程工厂
        taskExecutor.setThreadFactory(threadFactory);
        //设置拒绝执行的策略
        taskExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

            }
        });
        taskExecutor.initialize();
        return taskExecutor;
    }

    //记录任务执行过程中的异常。
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable ex, Method method, Object... params) {
                if (log.isErrorEnabled()) {
                    log.error("Unexpected exception occurred invoking async method: " + method, ex);
                }
            }
        };
    }
}
