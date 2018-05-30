package com.quartz.quartz.config;

import java.io.IOException;
import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class SchedulerConfig {

	@Bean
	public ScheduleJobFactory scheduleJobFactory() {
		return new ScheduleJobFactory();
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean() throws Exception {
		SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
		// schedulerFactoryBean.setSchedulerName("TASK_EXECUTOR");
		schedulerFactoryBean.setStartupDelay(10);// 在spring启动完后延迟10秒启动
		// schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
		schedulerFactoryBean.setOverwriteExistingJobs(true); // 设置是否任意一个已定义的Job会覆盖现在的Job。默认为false，即已定义的Job不会覆盖现有的Job。
		schedulerFactoryBean.setAutoStartup(true);
		schedulerFactoryBean.setQuartzProperties(quartzProperties());//属性设置
		// schedulerFactoryBean.setDataSource(dataSource);
		// 将 JobFactory 改为自定义的，否则在 Job 中注入 Bean 会失败
		schedulerFactoryBean.setJobFactory(scheduleJobFactory());
		// schedulerFactoryBean.afterPropertiesSet();
		return schedulerFactoryBean;

	}

	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		// 在quartz.properties中的属性被读取并注入后再初始化对象
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	/*
	 * quartz初始化监听器
	 */
	@Bean
	public QuartzInitializerListener executorListener() {
		return new QuartzInitializerListener();
	}

	/*
	* 通过SchedulerFactoryBean获取Scheduler的实例
	*/
	@Bean
	public Scheduler scheduler() throws Exception {
		return schedulerFactoryBean().getScheduler();
	}

}
