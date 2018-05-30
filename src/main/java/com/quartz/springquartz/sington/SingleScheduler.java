//package com.quartz.springquartz.sington;
///**
// * SpringBoot定时任务默认单线程，即使将任务放到不同的类中也是单线程，
// * 多线程需要自行实现或配置文件，本例中使用了ScheduleConfig来配置线程池数，
// * 可将类上的@Configuration注解去掉来测试单线程
// *（1）cron：cron表达式，指定任务在特定时间执行；
//    （2）fixedDelay：表示上一次任务执行完成后多久再次执行，参数类型为long，单位ms；
//    （3）fixedDelayString：与fixedDelay含义一样，只是参数类型变为String；
//    （4）fixedRate：表示按一定的频率执行任务，参数类型为long，单位ms；
//    （5）fixedRateString: 与fixedRate的含义一样，只是将参数类型变为String；
//    （6）initialDelay：表示延迟多久再第一次执行任务，参数类型为long，单位ms；
//    （7）initialDelayString：与initialDelay的含义一样，只是将参数类型变为String；
//    （8）zone：时区，默认为当前时区，一般没有用到。
// */
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SingleScheduler {
//
//	private Logger logger = LoggerFactory.getLogger(SingleScheduler.class);
//	private int fixedDelayCount = 1;
//	private int fixedRateCount = 1;
//	private int initialDelayCount = 1;
//	private int cronCount = 1;
//
//	// 每10秒执行一次
//	@Scheduled(cron = "0/10 * * * * ?")
//	public void timerToNow() {
//		System.out.println("当前线程名:" + Thread.currentThread().getName() + ",now time:"
//				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//	}
//
//	@Scheduled(cron = "0/1 * * * * ?")
//	public void executeUploadTask() {
//		System.out.println("executeUploadTask任务执行了:" + ",name:" + Thread.currentThread().getName());
//	}
//
//	@Scheduled(fixedDelay = 5000) // fixedDelay = 5000表示当前方法执行完毕5000ms后，Spring
//									// scheduling会再次调用该方法
//	public void testFixDelay() {
//		logger.info("===fixedDelay: 第{}次执行方法", fixedDelayCount++);
//	}
//
//	@Scheduled(fixedRate = 5000) // fixedRate = 5000表示当前方法开始执行5000ms后，Spring
//									// scheduling会再次调用该方法
//	public void testFixedRate() {
//		logger.info("===fixedRate: 第{}次执行方法", fixedRateCount++);
//	}
//
//	@Scheduled(initialDelay = 1000, fixedRate = 5000) // initialDelay =
//														// 1000表示延迟1000ms执行第一次任务
//	public void testInitialDelay() {
//		logger.info("===initialDelay: 第{}次执行方法", initialDelayCount++);
//	}
//
//	@Scheduled(cron = "0 0/1 * * * ?") // cron接受cron表达式，根据cron表达式确定定时规则
//	public void testCron() {
//		logger.info("===initialDelay: 第{}次执行方法", cronCount++);
//	}
//
//}
