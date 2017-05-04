package com.task.schedule.manager.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.task.schedule.manager.pojo.TaskJobLog;

/**
 * task_job_log的Dao
 * @author yuejing
 * @date 2015-03-31 14:26:09
 * @version V1.0.0
 */
public interface TaskJobLogDao {

	public void save(TaskJobLog taskJobLog);

	public void deleteLtDate(@Param("date")Date date);

	public TaskJobLog get(@Param("id")Integer id);

	public List<TaskJobLog> findTaskJobLog(TaskJobLog taskJobLog);
	public int findTaskJobLogCount(TaskJobLog taskJobLog);

}
