//package com.jyeol.dividend.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//
//@Configuration
//public class SchedulerConfig implements SchedulingConfigurer {
//    scheduler thread pool 설정
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        ThreadPoolTaskScheduler threadPool = new ThreadPoolTaskScheduler();
//
//        int core = Runtime.getRuntime().availableProcessors(); // cpu core
//        threadPool.setPoolSize(core);
//        threadPool.initialize();
//
//        taskRegistrar.setTaskScheduler(threadPool);
//    }
//}
