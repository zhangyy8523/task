package com.task.schedule.manager.service;

import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jing.system.model.MyPage;
import com.jing.system.utils.FrameTimeUtil;
import com.task.schedule.comm.enums.Boolean;
import com.task.schedule.manager.dao.TaskProjectDao;
import com.task.schedule.manager.pojo.TaskProject;

/**
 * task_project的Service
 * @author yuejing
 * @date 2015-03-30 14:07:28
 * @version V1.0.0
 */
@Component
public class TaskProjectService {

	@Autowired
	private TaskProjectDao taskProjectDao;
	@Autowired
	private TaskJobService taskJobService;
	
	/**
	 * 保存
	 * @param taskProject
	 */
	public void save(TaskProject taskProject) {
		taskProject.setAddtime(FrameTimeUtil.getTime());
		if(taskProject.getIsrecemail() == null) {
			taskProject.setIsrecemail(Boolean.FALSE.getCode());
		}
		taskProjectDao.save(taskProject);
	}

	/**
	 * 删除
	 * @param id
	 * @throws SchedulerException 
	 */
	public void delete(Integer id) throws SchedulerException {
		taskProjectDao.delete(id);
		//根据项目ID删除任务
		taskJobService.deleteProjectid(id);
	}

	/**
	 * 修改
	 * @param taskProject
	 */
	public void update(TaskProject taskProject) {
		taskProjectDao.update(taskProject);
	}

	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	public TaskProject get(Integer id) {
		return taskProjectDao.get(id);
	}

	/**
	 * 分页获取对象
	 * @param taskProject
	 * @return
	 */
	public MyPage<TaskProject> pageQuery(TaskProject taskProject) {
		taskProject.setDefPageSize();
		int total = taskProjectDao.findTaskProjectCount(taskProject);
		List<TaskProject> rows = null;
		if(total > 0) {
			rows = taskProjectDao.findTaskProject(taskProject);
			for (TaskProject project : rows) {
				project.setIsrecemailname(Boolean.getText(project.getIsrecemail()));
			}
		}
		MyPage<TaskProject> page = new MyPage<TaskProject>(taskProject.getPage(), taskProject.getSize(), total, rows);
		return page;
	}
}
