package com.task.schedule.core.exec.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.task.schedule.comm.constants.Constant;
import com.task.schedule.comm.enums.Config;
import com.task.schedule.comm.enums.JobStatus;
import com.task.schedule.core.base.AbstractTask;
import com.task.schedule.core.data.TaskJobData;
import com.task.schedule.core.exec.JobService;
import com.task.schedule.core.listener.TaskJobListener;
import com.task.schedule.manager.pojo.TaskJob;
import com.task.schedule.manager.service.ServInfoService;
import com.task.schedule.manager.service.SysConfigService;
import com.task.schedule.manager.service.TaskJobService;

/**
 * 集群任务调度线程的定时任务类
 * @author yuejing
 * @date 2015年3月29日 下午10:05:34
 * @version V1.0.0
 */
@Component
public class MainTask extends AbstractTask {

	private static final Logger LOGGER = Logger.getLogger(MainTask.class);
	@Autowired
	private JobService jobService;
	@Autowired
	private ServInfoService servInfoService;
	@Autowired
	private TaskJobService taskJobService;
	@Autowired
	private SysConfigService configService;
	@Autowired
	private TaskJobTask taskJobTask;

	@Override
	public void execute(JobExecutionContext context) {
		//获取当前服务正常的任务
		List<TaskJob> servJobs = taskJobService.findByServidStatus(Constant.serviceCode(), JobStatus.NORMAL.getCode());
		//=========================== 处理正常任务是否在执行 begin =============================
		try {
			for (TaskJob taskJob : servJobs) {
				try {
					if(!jobService.isExistJob(taskJob.getId().toString())) {
						//不存在job，则添加
						jobService.addOrUpdateJob(taskJob.getId().toString(), taskJob.getCron(), taskJobTask, new TaskJobListener(taskJob.getId().toString()));
						LOGGER.info("修复任务-数据库中正常，服务没执行的任务 ID【" + taskJob.getId() + "】名称【" + taskJob.getName() + "】成功");
						//修改状态为正常
						taskJobService.updateStatus(taskJob.getId(), JobStatus.NORMAL.getCode());
					}
				} catch (SchedulerException e) {
					LOGGER.error(e.getMessage(), e);
					taskJobService.updateStatus(taskJob.getId(), JobStatus.ERROR_ADD.getCode());
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		//=========================== 处理正常任务是否在执行 end =============================

		//=========================== 检测quartz执行的任务 begin ====================
		try {
			//任务是否还和自己绑定, 不是则删除quartz里面的任务
			List<String> waitDestroys = new ArrayList<String>();
			for (String jobName : TaskJobData.getJobNames()) {
				boolean exec = true;
				for (TaskJob taskJob : servJobs) {
					if(jobName.equals(taskJob.getId().toString())) {
						//和自己的服务绑定，则不删除
						exec = false;
						break;
					}
				}
				if(exec) {
					//得到待删除的任务id
					waitDestroys.add(jobName);
				}
			}
			for (String jobName : waitDestroys) {
				try {
					//删除任务
					jobService.deleteJob(jobName);
					TaskJobData.removeJobName(jobName);
					LOGGER.info("定时清理-删除服务已有任务，数据库中已移除的任务 ID【" + jobName + "】");
				} catch (SchedulerException e) {
					LOGGER.error("删除任务异常: " + e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			LOGGER.error("执行定时清理任务异常: " + e.getMessage(), e);
		}
		//=========================== 检测quartz执行的任务 end ====================

		//=========================== 获取待执行的任务 begin ====================
		try {
			//获取当前服务没有加入的任务
			List<TaskJob> jobs = taskJobService.findByServidStatus(Constant.serviceCode(), JobStatus.WAIT.getCode());
			for (TaskJob taskJob : jobs) {
				try {
					jobService.addOrUpdateJob(taskJob.getId().toString(), taskJob.getCron(), taskJobTask, new TaskJobListener(taskJob.getId().toString()));
					LOGGER.info("主线程添加任务 ID【" + taskJob.getId() + "】名称【" + taskJob.getName() + "】成功");
					//修改状态为正常
					taskJobService.updateStatus(taskJob.getId(), JobStatus.NORMAL.getCode());
				} catch (SchedulerException e) {
					LOGGER.error(e.getMessage(), e);
					taskJobService.updateStatus(taskJob.getId(), JobStatus.ERROR_ADD.getCode());
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		//=========================== 获取待执行的任务 end ====================

		//=========================== 发送任务心跳（间隔10s） begin ====================

		//修改当前任务的updatetime为当前时间
		taskJobService.updateUpdatetimeByServidStatus(Constant.serviceCode(), JobStatus.NORMAL.getCode());

		//修改当前服务的updatetime为当前时间
		servInfoService.updateUpdatetimeByServid(Constant.serviceCode());
		//将过期的服务状态变更为停止
		servInfoService.updateDestroy();
		//=========================== 发送任务心跳（间隔10s） end ====================

		//=========================== 系统配置的定时任务规则 begin ====================

		String mainCron = configService.getValue(Config.TASK_MAIN_CRON);
		if(!mainCron.equals(Constant.taskCronMap.get(Constant.TASK_ID_MAIN))) {
			//表达式不一致
			LOGGER.info("修改系统的定时任务-Main");
			try {
				jobService.updateJob(Constant.TASK_ID_MAIN, mainCron);
				Constant.taskCronMap.put(Constant.TASK_ID_MAIN, mainCron);
			} catch (SchedulerException e) {
				LOGGER.error("修改系统的定时任务异常: " + e.getMessage(), e);
			}
		}
		String leaderCron = configService.getValue(Config.LEADER_CRON);
		if(!leaderCron.equals(Constant.taskCronMap.get(Constant.TASK_ID_LEADER))) {
			//表达式不一致
			LOGGER.info("修改系统的定时任务-Leader");
			try {
				jobService.updateJob(Constant.TASK_ID_LEADER, leaderCron);
				Constant.taskCronMap.put(Constant.TASK_ID_LEADER, leaderCron);
			} catch (SchedulerException e) {
				LOGGER.error("修改系统的定时任务异常: " + e.getMessage(), e);
			}
		}
		String cleanCron = configService.getValue(Config.CLEAN_CRON);
		if(!cleanCron.equals(Constant.taskCronMap.get(Constant.TASK_ID_CLEAN))) {
			//表达式不一致
			LOGGER.info("修改系统的定时任务-Clean");
			try {
				jobService.updateJob(Constant.TASK_ID_CLEAN, cleanCron);
				Constant.taskCronMap.put(Constant.TASK_ID_CLEAN, cleanCron);
			} catch (SchedulerException e) {
				LOGGER.error("修改系统的定时任务异常: " + e.getMessage(), e);
			}
		}
		
		//=========================== 系统配置的定时任务规则 end ====================
	}
}