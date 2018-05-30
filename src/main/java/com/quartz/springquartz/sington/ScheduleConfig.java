//package com.quartz.springquartz.sington;
//
//import java.util.concurrent.Executors;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//
///*
// * Description: 多线程执行定时任务 官网：www.fhadmin.org Designer
// * 设置spring的定时调度任务为多线程的
// * Version: 1.0.0
// */
//
//@Configuration // 所有的定时任务都放在一个线程池中，定时任务启动时使用不同的线程。
//public class ScheduleConfig implements SchedulingConfigurer {
//	@Override
//	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//		// 设定一个长度10的定时任务线程池
//		taskRegistrar.setScheduler(Executors.newScheduledThreadPool(10));
//	}
//}
