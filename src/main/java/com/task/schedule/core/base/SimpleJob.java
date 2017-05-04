package com.task.schedule.core.base;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定时任务Job
 * @author jing.yue
 * @version 2012/09/18 1.0.0
 */
public class SimpleJob extends QuartzJobBean {

    private ITask task;

    public void setTask(ITask task) {
        this.task = task;
    }

    public ITask getTask() {
        return task;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        task.execute(context);
    }
}
