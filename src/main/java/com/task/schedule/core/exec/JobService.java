package com.task.schedule.core.exec;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Component;

import com.jing.system.utils.FrameSpringBeanUtil;
import com.task.schedule.core.base.ITask;
import com.task.schedule.core.base.SimpleJob;

/**
 * 定时任务的工具类
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.suyunyou.com
 * @date    2015年3月30日 上午11:54:55 
 * @version 1.0.0
 */
@Component
public class JobService {

	/**
	 * 添加定时任务
	 * @param id		名称和组的后缀
	 * @param cron		执行规则
	 * @param execTask	添加的执行的任务类
	 * @param jobListener 
	 * @throws SchedulerException
	 */
	public void addJob(String id, String cron, ITask execTask, JobListener jobListener) throws SchedulerException {
		Scheduler scheduler = FrameSpringBeanUtil.getBean(Scheduler.class);
		// 可执行的任务列表
		// 任务名称和任务组设置规则：
		// 名称：task_1 ..
		// 组 ：group_1 ..
		String name = "task_" + id;
		String group = "group_" + id;
		TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		// 不存在，创建一个
		JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity(name, group).build();
		jobDetail.getJobDataMap().put("task", execTask);
		jobDetail.getJobDataMap().put("taskId", id);
		// 表达式调度构建器
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
		// 按新的表达式构建一个新的trigger
		trigger = TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(scheduleBuilder).build();
		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.getListenerManager().addJobListener(jobListener);
	}

	/**
	 * 更新任务
	 * @param id		名称和组的后缀
	 * @param cron		执行规则
	 * @throws SchedulerException
	 */
	public void updateJob(String id, String cron) throws SchedulerException {
		Scheduler scheduler = FrameSpringBeanUtil.getBean(Scheduler.class);
		// 可执行的任务列表
		// 任务名称和任务组设置规则：
		// 名称：task_1 ..
		// 组 ：group_1 ..
		String name = "task_" + id;
		String group = "group_" + id;
		TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		// trigger已存在，则更新相应的定时设置
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
		// 按新的cronExpression表达式重新构建trigger
		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
		// 按新的trigger重新设置job执行
		scheduler.rescheduleJob(triggerKey, trigger);
	}
	
	/**
	 * 判断是否存在job
	 * @param id
	 * @return
	 * @throws SchedulerException
	 */
	public boolean isExistJob(String id) throws SchedulerException {
		Scheduler scheduler = FrameSpringBeanUtil.getBean(Scheduler.class);
		// 可执行的任务列表
		// 任务名称和任务组设置规则：
		// 名称：task_1 ..
		// 组 ：group_1 ..
		String name = "task_" + id;
		String group = "group_" + id;
		TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		if(trigger != null) {
			return true;
		}
		return false;
	}

	/**
	 * 添加或更新Job
	 * @param id
	 * @param cron
	 * @param execTask
	 * @param jobListener
	 * @throws SchedulerException
	 */
	public void addOrUpdateJob(String id, String cron, ITask execTask, JobListener jobListener) throws SchedulerException {
		Scheduler scheduler = FrameSpringBeanUtil.getBean(Scheduler.class);
		// 可执行的任务列表
		// 任务名称和任务组设置规则：
		// 名称：task_1 ..
		// 组 ：group_1 ..
		String name = "task_" + id;
		String group = "group_" + id;
		TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		if(trigger == null) {
			// 不存在，创建一个
			JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).withIdentity(name, group).build();
			jobDetail.getJobDataMap().put("task", execTask);
			jobDetail.getJobDataMap().put("taskId", id);
			// 表达式调度构建器
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
			// 按新的表达式构建一个新的trigger
			trigger = TriggerBuilder.newTrigger().withIdentity(name, group).withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.getListenerManager().addJobListener(jobListener);
		} else {
			// trigger已存在，则更新相应的定时设置
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		}
	}

	/**
	 * 暂停任务
	 * @param id
	 * @throws SchedulerException
	 */
	public void pauseJob(String id) throws SchedulerException {
		String name = "task_" + id;
		String group = "group_" + id;
		Scheduler scheduler = FrameSpringBeanUtil.getBean(Scheduler.class);
		JobKey jobKey = JobKey.jobKey(name, group);
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 恢复任务
	 * @param id
	 * @throws SchedulerException
	 */
	public void resumeJob(String id) throws SchedulerException {
		String name = "task_" + id;
		String group = "group_" + id;
		Scheduler scheduler = FrameSpringBeanUtil.getBean(Scheduler.class);
		JobKey jobKey = JobKey.jobKey(name, group);
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 删除任务
	 * @param id
	 * @throws SchedulerException
	 */
	public void deleteJob(String id) throws SchedulerException {
		String name = "task_" + id;
		String group = "group_" + id;
		Scheduler scheduler = FrameSpringBeanUtil.getBean(Scheduler.class);
		JobKey jobKey = JobKey.jobKey(name, group);
		scheduler.deleteJob(jobKey);
	}

	/**
	 * 立即运行任务
	 * @param id
	 * @throws SchedulerException
	 */
	public void triggerJob(String id) throws SchedulerException {
		String name = "task_" + id;
		String group = "group_" + id;
		Scheduler scheduler = FrameSpringBeanUtil.getBean(Scheduler.class);
		JobKey jobKey = JobKey.jobKey(name, group);
		scheduler.triggerJob(jobKey);
	}
}