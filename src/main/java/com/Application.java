package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// @EnableScheduling // 此注解的作用是发现注解@Scheduled的任务并后台执行。
@MapperScan("com.quartz.*.mapper") // 扫描被@Mapper注解的接口，将其加入到spring容器
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
