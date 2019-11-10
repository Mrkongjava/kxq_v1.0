package com.group.common.core.service;

import com.group.common.core.quartz.DetailQuartzJobBean;
import com.group.common.core.quartz.QuartzJobModel;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 定时任务
 * @author dailing
 * 2016-11-17
 */
@Service
public class QuartzService {

	@Autowired
	private Scheduler scheduler;

	/**
	 * 添加定时任务
	 */
	public void addQuartzJob(QuartzJobModel job) throws SchedulerException {
		String jobName = job.getJobName();//任务名称
		Class targetObject = job.getTargetObject();//执行类名称
		String targetMethod = job.getTargetMethod();//执行方法
		String description = job.getDescription();
		String cronExpression = job.getCronExpression();//表达式
		Class[] classeses = job.getClasses();
		Object[] objects = job.getObjects();
		
		//初始化JobDetail  
		JobDataMap dataMap = new JobDataMap();  
		dataMap.put("targetObject", targetObject);
		dataMap.put("targetMethod", targetMethod);  
		dataMap.put("classes", classeses);
		dataMap.put("objects", objects);
		JobDetail jobDetail = JobBuilder.newJob(DetailQuartzJobBean.class)
				.withIdentity(jobName, Scheduler.DEFAULT_GROUP).withDescription(description)  
				.usingJobData(dataMap).build();  

		//初始化CronTrigger  
		CronTrigger trigger = TriggerBuilder.newTrigger()  
				.withIdentity(jobName + "_trigger", Scheduler.DEFAULT_GROUP)  
				.forJob(jobDetail).withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();  
		
		//添加cornjob  
		scheduler.scheduleJob(jobDetail, trigger);
	}  
	  
	/**
	 * 删除定时任务
	 */
	public void deleteQuartzJob(String jobName) throws SchedulerException {
		scheduler.deleteJob(new JobKey(jobName, Scheduler.DEFAULT_GROUP));
	}
	
}
