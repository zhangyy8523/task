package com.task.schedule.core.exec;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.task.schedule.comm.constants.Constant;
import com.task.schedule.comm.enums.Config;
import com.task.schedule.core.exec.task.CleanTask;
import com.task.schedule.core.exec.task.LeaderTask;
import com.task.schedule.core.exec.task.MainTask;
import com.task.schedule.core.listener.MainListener;
import com.task.schedule.manager.service.SysConfigService;

/**
 * 定时任务加载工具类
 * @author yuejing
 * @date 2013-12-31 下午3:01:33
 * @version V1.0.0
 */
@Component
public class TaskManager {

	private static final Logger LOGGER = Logger.getLogger(TaskManager.class);

	@Autowired
	private JobService jobService;
	@Autowired
	private SysConfigService configService;
	
	@Autowired
	private MainTask mainTask;
	@Autowired
	private CleanTask cleanTask;
	@Autowired
	private LeaderTask leaderTask;

	public void init() {
		try {
			//添加-集群任务调度线程
			String mainCron = configService.getValue(Config.TASK_MAIN_CRON, "0/20 * * * * ?");
			Constant.taskCronMap.put(Constant.TASK_ID_MAIN, mainCron);
			jobService.addJob(Constant.TASK_ID_MAIN, mainCron, mainTask, new MainListener(Constant.TASK_ID_MAIN));
		} catch (SchedulerException e) {
			LOGGER.error("添加系统的定时任务异常: " + e.getMessage(), e);
		}
		try {
			//添加-Leader的任务
			String leaderCron = configService.getValue(Config.LEADER_CRON, "0/5 * * * * ?");
			Constant.taskCronMap.put(Constant.TASK_ID_LEADER, leaderCron);
			jobService.addJob(Constant.TASK_ID_LEADER, leaderCron, leaderTask, new MainListener(Constant.TASK_ID_LEADER));
		} catch (SchedulerException e) {
			LOGGER.error("添加-Leader的任务异常: " + e.getMessage(), e);
		}
		try {
			//添加-清除过期调度日志线程
			String cleanCron = configService.getValue(Config.CLEAN_CRON, "0 0 23 * * ?");
			Constant.taskCronMap.put(Constant.TASK_ID_CLEAN, cleanCron);
			jobService.addJob(Constant.TASK_ID_CLEAN, cleanCron, cleanTask, new MainListener(Constant.TASK_ID_CLEAN));
		} catch (SchedulerException e) {
			LOGGER.error("添加清除任务日志的定时任务异常: " + e.getMessage(), e);
		}
	}

}