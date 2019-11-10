package com.group.common.core.quartz;

/**
 * 定时器
 * @author dailing
 * 2016-04-12
 */
public class QuartzJobModel {
	
	//定时器名称
	String schedName;
	
	//任务名称
	String jobName;
	
	//描述
	String description;
	
	//任务执行类名称
	Class targetObject;
	
	//执行方法
	String targetMethod;
	
	//定时器表达式
	String cronExpression;
	
	Class[] classes;
	
	Object[] objects;

	public String getSchedName() {
		return schedName;
	}

	public void setSchedName(String schedName) {
		this.schedName = schedName;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Class getTargetObject() {
		return targetObject;
	}

	public void setTargetObject(Class targetObject) {
		this.targetObject = targetObject;
	}

	public String getTargetMethod() {
		return targetMethod;
	}

	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Class[] getClasses() {
		return classes;
	}

	public void setClasses(Class[] classes) {
		this.classes = classes;
	}

	public Object[] getObjects() {
		return objects;
	}

	public void setObjects(Object[] objects) {
		this.objects = objects;
	}

}
