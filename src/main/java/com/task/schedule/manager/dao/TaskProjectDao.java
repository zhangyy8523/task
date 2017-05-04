package com.task.schedule.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.task.schedule.manager.pojo.TaskProject;

/**
 * task_project的Dao
 * @author yuejing
 * @date 2015-03-30 14:07:28
 * @version V1.0.0
 */
public interface TaskProjectDao {

	public void save(TaskProject taskProject);

	public void update(TaskProject taskProject);

	public void delete(@Param("id")Integer id);

	public TaskProject get(@Param("id")Integer id);

	public List<TaskProject> findTaskProject(TaskProject taskProject);
	public int findTaskProjectCount(TaskProject taskProject);

}
