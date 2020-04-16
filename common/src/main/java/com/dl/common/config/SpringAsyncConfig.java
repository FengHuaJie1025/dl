package com.dl.common.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.lang.reflect.Method;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ConfigurationProperties 注解表示读取配置文件中
 * 以某个名字作为前缀配置信息，然后将读到配置信息，通过类
 * 中的set方法赋值属性。
 */
@Slf4j
@Setter
@ConfigurationProperties("async-thread-pool")
@Configuration
public class SpringAsyncConfig implements AsyncConfigurer {
    private Integer corePoolSize;
    private Integer maxPoolSize;
    private Integer keepAliveSeconds;
    private Integer queueCapacity;
    private ThreadFactory threadFactory;

    public SpringAsyncConfig(){
        this.threadFactory = new WorkThreadFactory();
    }
    public SpringAsyncConfig(String threadFactoryName){
        this.threadFactory = new WorkThreadFactory(threadFactoryName);
    }

    static class WorkThreadFactory implements ThreadFactory {
        private AtomicInteger atomicInteger =new AtomicInteger(0);
        String name;

        public WorkThreadFactory(){
            this.name = "SpringAsyncConfig-Threads-"+atomicInteger.getAndIncrement();
        }

        public WorkThreadFactory(String name){
            this.name = name;
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, name);
        }
    }

    @Override
    public Executor getAsyncExecutor() {
        /**使用阿里巴巴推荐的创建线程池的方式
         * 通过ThreadPoolExecutor构造函数自定义参数创建
         *
         * @param corePoolSize     核心池大小 int
         * @param maximumPoolSize  最大池大小 int
         * @param keepAliveTime    保活时间   long（任务完成后要销毁的延时）
         * @param unit             时间单位    决定参数3的单位，枚举类型的时间单位
         * @param workQueue        工作队列    用于存储任务的工作队列（BlockingQueue接口类型）
         * @param threadFactory    线程工厂    用于创建线程
         * @param RejectedExecutionHandler    拒绝策略
         *
         *线程不是越多越好，google工程师推荐  线程个数=cpu核心数+1（例如四核的开5个线程最好）
         * */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                keepAliveSeconds,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueCapacity),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    //记录任务执行过程中的异常。
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable ex, Method method, Object... params) {
                log.error("任务执行时出现了未知的异常", ex);
            }
        };
    }
}
