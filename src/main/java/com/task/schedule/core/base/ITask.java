package com.task.schedule.core.base;

import org.quartz.JobExecutionContext;

public interface ITask {
	
    abstract public void execute(JobExecutionContext context);
}