package com.quartz.quartz;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.quartz.quartz.config.ScheduleUtils;
import com.quartz.quartz.entity.QuartzTaskEntity;

@RestController
public class QuartzController {

	@Autowired
	private ScheduleUtils scheduleUtils;

	@RequestMapping("rescheduleJob")
	public String rescheduleJob(@RequestBody QuartzTaskEntity quartz) throws Exception {
		scheduleUtils.rescheduleJob(quartz);
		return "success";
	}

	@RequestMapping("pauseTrigger")
	public void pauseTrigger(Integer id) throws SchedulerException {
		scheduleUtils.pauseTrigger(id);
	}

	@RequestMapping("resumeTrigger")
	public void resumeTrigger(Integer id) throws SchedulerException {
		scheduleUtils.resumeTrigger(id);
	}

	@RequestMapping("pauseAll")
	public void pauseAll() throws SchedulerException {
		scheduleUtils.pauseAll();
	}

	@RequestMapping("resumeAll")
	public void resumeAll() throws SchedulerException {
		scheduleUtils.resumeAll();
	}

}
