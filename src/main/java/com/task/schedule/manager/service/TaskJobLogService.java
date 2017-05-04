package com.task.schedule.manager.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jing.system.model.MyPage;
import com.jing.system.utils.FrameStringUtil;
import com.task.schedule.comm.constants.Constant;
import com.task.schedule.comm.enums.JobLogStatus;
import com.task.schedule.manager.dao.TaskJobLogDao;
import com.task.schedule.manager.pojo.TaskJobLog;

/**
 * task_job_log的Service
 * @author yuejing
 * @date 2015-03-31 14:26:09
 * @version V1.0.0
 */
@Component
public class TaskJobLogService {

	@Autowired
	private TaskJobLogDao taskJobLogDao;
	
	/**
	 * 保存
	 * @param taskJobLog
	 */
	public void save(TaskJobLog taskJobLog) {
		if(FrameStringUtil.isEmpty(taskJobLog.getServicecode())) {
			taskJobLog.setServicecode(Constant.serviceCode());
		}
		taskJobLogDao.save(taskJobLog);
	}

	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	public TaskJobLog get(Integer id) {
		return taskJobLogDao.get(id);
	}

	/**
	 * 分页获取对象
	 * @param taskJobLog
	 * @return
	 */
	public MyPage<TaskJobLog> pageQuery(TaskJobLog taskJobLog) {
		taskJobLog.setDefPageSize();
		int total = taskJobLogDao.findTaskJobLogCount(taskJobLog);
		List<TaskJobLog> rows = null;
		if(total > 0) {
			rows = taskJobLogDao.findTaskJobLog(taskJobLog);
			for (TaskJobLog jobLog : rows) {
				jobLog.setStatusname(JobLogStatus.getText(jobLog.getStatus()));
			}
		}
		MyPage<TaskJobLog> page = new MyPage<TaskJobLog>(taskJobLog.getPage(), taskJobLog.getSize(), total, rows);
		return page;
	}

	/**
	 * 小于指定日期的删除
	 * @param date
	 */
	public void deleteLtDate(Date date) {
		taskJobLogDao.deleteLtDate(date);
	}
}