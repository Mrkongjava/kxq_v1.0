package com.group.common.core.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

public class DetailQuartzJobBean extends QuartzJobBean {
	private ApplicationContext applicationContext;
	private Class              targetObject;
	private String             targetMethod;
	private Class[]            classes;
	private Object[]           objects;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		Logger logger = LoggerFactory.getLogger(DetailQuartzJobBean.class);
		
		try {
			Object otargetObject = applicationContext.getBean(targetObject);
			Method m = otargetObject.getClass().getMethod(targetMethod, classes);
			m.invoke(otargetObject, objects);
		} catch (Exception e) {
			logger.error("执行定时任务失败-", e);
		} 
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
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	} 
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
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