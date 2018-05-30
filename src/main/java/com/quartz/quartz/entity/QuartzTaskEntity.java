package com.quartz.quartz.entity;

import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 定时任务实体类
 */
@TableName("quartz_task")
public class QuartzTaskEntity {
	// 任务ID
	private Integer id;
	// 任务名
	private String taskName;
	// 任务分组
	private String taskGroup;
	// 定时任务工作类全名
	private String taskClass;
	// 定时cron表达式
	private String cron;
	// 任务描述
	private String description;
	// 任务状态，1 有效，0 失效
	private Integer state;

	// 任务在内存中的状态，1 正常， 0 暂停
	private Integer memoryState;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskGroup() {
		return taskGroup;
	}

	public void setTaskGroup(String taskGroup) {
		this.taskGroup = taskGroup;
	}

	public String getTaskClass() {
		return taskClass;
	}

	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getMemoryState() {
		return memoryState;
	}

	public void setMemoryState(Integer memoryState) {
		this.memoryState = memoryState;
	}
}
