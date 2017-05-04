package com.task.schedule.core.exec.task;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jing.system.utils.FrameTimeUtil;
import com.task.schedule.comm.enums.Config;
import com.task.schedule.core.base.AbstractTask;
import com.task.schedule.manager.service.ServEqService;
import com.task.schedule.manager.service.ServInfoService;
import com.task.schedule.manager.service.SysConfigService;
import com.task.schedule.manager.service.TaskJobLogService;

/**
 * 清空任务日志的定时任务类
 * @author yuejing
 * @date 2015年3月29日 下午10:05:34
 * @version V1.0.0
 */
@Component
public class CleanTask extends AbstractTask {

	private static final Logger LOGGER = Logger.getLogger(CleanTask.class);
	@Autowired
	private TaskJobLogService taskJobLogService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private ServInfoService servInfoService;
	@Autowired
	private ServEqService servEqService;

	@Override
	public void execute(JobExecutionContext context) {
		//清空小于指定日期的日志
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("清空小于指定日期日志的定时任务");
		}
		String logValue = sysConfigService.getValue(Config.JOBLOG_SAVE_DAY, "7");
		Date logDate = FrameTimeUtil.addDays(FrameTimeUtil.getTime(), - Integer.valueOf(logValue));
		taskJobLogService.deleteLtDate(logDate);

		String siValue = sysConfigService.getValue(Config.SERV_SAVE_DAY, "7");
		Date siDate = FrameTimeUtil.addDays(FrameTimeUtil.getTime(), - Integer.valueOf(siValue));
		//清空小于指定日期的已停止的服务
		servInfoService.deleteDestroyLtDate(siDate);
		
		//修改已销毁的服务为非Leader
		servInfoService.destroyLeader();
		
		//清空小于指定日期的负载的服务
		servEqService.deleteDestroyLtDate(siDate);
	}
}