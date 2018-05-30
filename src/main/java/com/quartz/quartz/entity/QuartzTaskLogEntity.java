package com.quartz.quartz.entity;

import com.baomidou.mybatisplus.annotations.TableName;

@TableName("quartz_task_log")
public class QuartzTaskLogEntity {
    
    private Long id;
    private Integer taskId;
    private String state; // 'C开始调度，F正常结束，E异常调度',
    private String startTime; // 定时调度任务开始时间
    private String endTime; // 定时调度任务结束时间
    private String result; // 成功返回结果或失败记录的失败原因
    private String countTime; // 总耗时,毫秒
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getCountTime() {
		return countTime;
	}
	public void setCountTime(String countTime) {
		this.countTime = countTime;
	}
}
