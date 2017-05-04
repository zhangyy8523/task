package com.task.schedule.core.listener;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import com.task.schedule.core.data.TaskJobData;

/**
 * 监听job的线程
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.suyunyou.com
 * @date    2015年3月30日 下午5:46:49 
 * @version 1.0.0
 */
public class TaskJobListener implements JobListener {

	//task的id
	private String name;

	public TaskJobListener(String name) {
		super();
		this.name = name;
		TaskJobData.addJobName(name);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void jobToBeExecuted(JobExecutionContext inContext) {
		//执行任务前执行
	}

	@Override
	public void jobExecutionVetoed(JobExecutionContext inContext) {
		
	}

	@Override
	public void jobWasExecuted(JobExecutionContext inContext, JobExecutionException inException) {
		//任务执行完后，执行
		
		//Task的name: inContext.getJobDetail().getKey().getName()
		//Task的group: inContext.getJobDetail().getKey().getGroup()
		
	}
}