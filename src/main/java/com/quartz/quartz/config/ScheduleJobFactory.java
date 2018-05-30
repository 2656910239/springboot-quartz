package com.quartz.quartz.config;

import org.springframework.stereotype.Component;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 * ScheduleJobFactory 该类是为了将 Job管理注入到 Spring中
 * 
 * @author Administrator
 *
 */
@Component
public class ScheduleJobFactory extends AdaptableJobFactory {

	@Autowired
	private transient AutowireCapableBeanFactory beanFactory;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		beanFactory = applicationContext.getAutowireCapableBeanFactory();
	}

	@Override
	protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
		final Object job = super.createJobInstance(bundle);
		beanFactory.autowireBean(job);
		return job;
	}
}
