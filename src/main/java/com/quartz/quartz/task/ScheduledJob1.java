package com.quartz.quartz.task;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
/*
 * 不允许任务并行执行，说明：如果任务每10秒执行一次，但可能某次任务执行30秒
 * 都没执行完，则后面需要执行的任务需要等待前面一次执行完才能开始执行。
 * 参考:https://blog.csdn.net/lkforce/article/details/51841890
*/
@DisallowConcurrentExecution
public class ScheduledJob1 implements Job {

	@Autowired
	private JobTest jobTest;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			Thread.sleep(1000 * 15);//此处是为了测试不允许并行执行任务的
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("这是我的ScheduledJob1任务");
		jobTest.test();// 此处是为了测试此任务中是否可以注入其他bean
	}

}
