//package com.group.common.core.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
///**
// * 版权：chaining100
// * 作者：dailing
// * 生成日期：2018/12/20 下午3:02
// * 描述：定时器线程设置（线程串行执行模式）
// */
//@Configuration
//public class ThreadPoolConfig {
//
//    @Bean
//    public ThreadPoolTaskExecutor taskExecutor() {
//        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
//        // 线程池维护线程的最少数量
//        pool.setCorePoolSize(8);
//        // 线程池维护线程的最大数量
//        pool.setMaxPoolSize(1000);
//        // 当调度器shutdown被调用时等待当前被调度的任务完成
//        pool.setWaitForTasksToCompleteOnShutdown(true);
//        return pool;
//    }
//
//
//}
