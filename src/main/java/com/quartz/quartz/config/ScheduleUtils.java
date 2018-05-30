package com.quartz.quartz.config;

import java.io.Serializable;
import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.quartz.quartz.entity.QuartzTaskEntity;
import com.quartz.quartz.mapper.QuartzTaskMapper;

/**
 * 实现ApplicationListener接口，重写onApplicationEvent，在容器加载完成后 自动调用此方法
 * 
 * @author Administrator
 *
 * 下面的groupname,group等都可只用固定值+id的格式，这样的话就可以同时修改taskname等内容而不仅仅是只修改调度周期了
 *
 */
@Component
public class ScheduleUtils implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private Scheduler scheduler;

	@Autowired
	private QuartzTaskMapper quartzTaskMapper;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		this.initScheduleJob();
	}

	public void initScheduleJob() {
		// 查询有效的定时调度任务，从数据库表中查询
		List<QuartzTaskEntity> list = getAllEffectTask();

		try {
			// 要执行的 Job 的类
			for (QuartzTaskEntity quartzTaskEntity : list) {
				addJob(quartzTaskEntity);
				updateTableMemoryState(1, quartzTaskEntity.getId());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加定时调度任务.
	 * @param quartzTaskEntity 定时调度任务实体类
	 * @throws Exception
	 */
	public void addJob(QuartzTaskEntity quartzTaskEntity) throws Exception {
		Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(quartzTaskEntity.getTaskClass());

		JobDetail jobDetail = getJobDetail(quartzTaskEntity);
		CronTrigger cronTrigger = getCronTrigger(quartzTaskEntity);

		scheduler.scheduleJob(jobDetail, cronTrigger);
	}

	/**
	 * 动态刷新定时调度任务，此处只考虑动态修改调度周期.
	 * @param quartzTaskEntity 定时调度任务实体类.
	 * @throws SchedulerException
	 */
	public void rescheduleJob(QuartzTaskEntity quartzTaskEntity) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(quartzTaskEntity.getTaskName() + "_" + quartzTaskEntity.getId(),
				quartzTaskEntity.getTaskGroup() + "_" + quartzTaskEntity.getId());
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);// 获取内存中的定时调度任务
		String cronExpression = trigger.getCronExpression();// 内存中的调度周期

		if (!cronExpression.equals(quartzTaskEntity.getCron())) {
			// 刷新内存中的定时调度
			scheduler.rescheduleJob(triggerKey, getCronTrigger(quartzTaskEntity));
			quartzTaskMapper.updateById(quartzTaskEntity);
		}
	}

	/**
	 * 暂停某一个定时调度任务.
	 * @param id 主键id
	 * @throws SchedulerException
	 */
	public void pauseTrigger(Integer id) throws SchedulerException {
		QuartzTaskEntity quartzTaskEntity = quartzTaskMapper.selectById(id);
		TriggerKey triggerKey = new TriggerKey(quartzTaskEntity.getTaskName() + "_" + quartzTaskEntity.getId(),
				quartzTaskEntity.getTaskGroup() + "_" + quartzTaskEntity.getId());
		scheduler.pauseTrigger(triggerKey);
		updateTableMemoryState(0, id);
	}

	/**
	 * 使暂停的任务重新执行.
	 * @param id
	 * @throws SchedulerException
	 */
	public void resumeTrigger(Integer id) throws SchedulerException {
		QuartzTaskEntity quartzTaskEntity = quartzTaskMapper.selectById(id);
		TriggerKey triggerKey = new TriggerKey(quartzTaskEntity.getTaskName() + "_" + quartzTaskEntity.getId(),
				quartzTaskEntity.getTaskGroup() + "_" + quartzTaskEntity.getId());
		scheduler.resumeTrigger(triggerKey);
		updateTableMemoryState(1, id);
	}

	/**
	 * 暂停所有定时调度任务.
	 */
	public void pauseAll() throws SchedulerException {
		scheduler.pauseAll();
		List<QuartzTaskEntity> list = getAllEffectTask();
		for (int i = 0; i < list.size(); i++) {
			updateTableMemoryState(0, list.get(i).getId());
		}
	}

	/**
	 * 重新开始所有定时调度任务
	 * @throws SchedulerException
	 */
	public void resumeAll() throws SchedulerException {
		scheduler.resumeAll();
		List<QuartzTaskEntity> list = getAllEffectTask();
		for (int i = 0; i < list.size(); i++) {
			// 同步更新数据库里面表的状态
			updateTableMemoryState(1, list.get(i).getId());
		}
	}

	// 构造jobdetail
	private JobDetail getJobDetail(QuartzTaskEntity quartzTaskEntity) throws Exception {
		Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName(quartzTaskEntity.getTaskClass());
		JobDetail jobDetail = JobBuilder.newJob(jobClass)
				.withIdentity(quartzTaskEntity.getTaskName() + "_" + quartzTaskEntity.getId(),
						quartzTaskEntity.getTaskGroup() + "_" + quartzTaskEntity.getId())
				.withDescription(quartzTaskEntity.getDescription()).build();
		return jobDetail;
	}

	// 构造CronTrigger
	private CronTrigger getCronTrigger(QuartzTaskEntity quartzTaskEntity) {
		// 设置定时调度周期
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(quartzTaskEntity.getCron())
				.withMisfireHandlingInstructionDoNothing();

		CronTrigger cronTrigger = TriggerBuilder.newTrigger()
				.withIdentity(quartzTaskEntity.getTaskName() + "_" + quartzTaskEntity.getId(),
						quartzTaskEntity.getTaskGroup() + "_" + quartzTaskEntity.getId())
				.withDescription(quartzTaskEntity.getDescription()).withSchedule(scheduleBuilder).startNow().build();
		return cronTrigger;
	}

	/**
	 * 修改表中表示任务是否暂停状态.
	 * @param memoryState 1 正常，0 暂停
	 * @param id
	 */
	private void updateTableMemoryState(Integer memoryState, Integer id) {
		quartzTaskMapper.updateForSet("memory_state=" + memoryState,
				new EntityWrapper<QuartzTaskEntity>().eq("id", id));// 根据主键修改memory_state为1即在表中记录内存中状态正常

	}

	private List<QuartzTaskEntity> getAllEffectTask() {
		List<QuartzTaskEntity> list = quartzTaskMapper
				.selectList(new EntityWrapper<QuartzTaskEntity>().where("state={0}", 1));
		return list;
	}
}
