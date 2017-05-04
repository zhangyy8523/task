package com.task.schedule.manager.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jing.system.model.MyPage;
import com.task.schedule.comm.controller.BaseController;
import com.task.schedule.manager.pojo.TaskJob;
import com.task.schedule.manager.pojo.TaskJobLog;
import com.task.schedule.manager.service.TaskJobLogService;
import com.task.schedule.manager.service.TaskJobService;

/**
 * task_job_log的Controller
 * @author yuejing
 * @date 2015-03-31 14:26:09
 * @version V1.0.0
 */
@Controller
public class TaskJobLogController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(TaskJobLogController.class);

	@Autowired
	private TaskJobLogService taskJobLogService;
	@Autowired
	private TaskJobService taskJobService;
	
	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/taskJobLog/f_view/manager")
	public String manger(HttpServletRequest request) {
		return "manager/task/jobLog_manager";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/taskJobLog/f_json/pageQuery")
	@ResponseBody
	public MyPage<TaskJobLog> pageQuery(HttpServletRequest request, TaskJobLog taskJobLog) {
		MyPage<TaskJobLog> page = null;
		try {
			page = taskJobLogService.pageQuery(taskJobLog);
		} catch (Exception e) {
			LOGGER.error("分页获取信息异常: " + e.getMessage(), e);
		}
		return page;
	}

	/**
	 * 跳转到编辑页[包含新增和编辑]
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/taskJobLog/f_view/look")
	public String look(HttpServletRequest request, ModelMap modelMap, Integer id) {
		if(id != null) {
			TaskJobLog taskJobLog = taskJobLogService.get(id);
			TaskJob taskJob = taskJobService.get(taskJobLog.getJobid());
			modelMap.put("taskJobLog", taskJobLog);
			modelMap.put("taskJob", taskJob);
		}
		return "manager/task/jobLog_look";
	}
}
