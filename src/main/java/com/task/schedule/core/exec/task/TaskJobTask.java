package com.task.schedule.core.exec.task;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.task.schedule.core.base.AbstractTask;
import com.task.schedule.manager.service.TaskJobService;

/**
 * 系统公用的定时任务类
 * @author yuejing
 * @date 2015年3月29日 下午10:05:34
 * @version V1.0.0
 */
@Component
public class TaskJobTask extends AbstractTask {

	@Autowired
	private TaskJobService taskJobService;

	@Override
	public void execute(JobExecutionContext context) {
		String id = (String) context.getJobDetail().getJobDataMap().get("taskId");
		taskJobService.execJob(Integer.valueOf(id));
	}
}