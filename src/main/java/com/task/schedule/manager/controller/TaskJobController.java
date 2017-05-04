package com.task.schedule.manager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jing.system.model.MyPage;
import com.task.schedule.comm.controller.BaseController;
import com.task.schedule.comm.enums.ServInfoStatus;
import com.task.schedule.manager.pojo.ServInfo;
import com.task.schedule.manager.pojo.SysUser;
import com.task.schedule.manager.pojo.TaskJob;
import com.task.schedule.manager.service.ServInfoService;
import com.task.schedule.manager.service.TaskJobService;
import com.task.schedule.manager.service.TaskProjectService;

/**
 * task_job的Controller
 * @author yuejing
 * @date 2015-03-30 14:07:27
 * @version V1.0.0
 */
@Controller
public class TaskJobController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(TaskJobController.class);

	@Autowired
	private TaskJobService taskJobService;
	@Autowired
	private TaskProjectService taskProjectService;
	@Autowired
	private ServInfoService servInfoService;
	
	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/taskJob/f_view/manager")
	public String manger(HttpServletRequest request, ModelMap modelMap) {
		List<ServInfo> servInfos = servInfoService.findByStatus(ServInfoStatus.NORMAL.getCode());
		modelMap.put("servInfos", servInfos);
		return "manager/task/job_manager";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/taskJob/f_json/pageQuery")
	@ResponseBody
	public MyPage<TaskJob> pageQuery(HttpServletRequest request, TaskJob taskJob) {
		MyPage<TaskJob> page = null;
		try {
			page = taskJobService.pageQuery(taskJob);
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
	@RequestMapping(value = "/taskJob/f_view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap, Integer id, Integer projectid) {
		if(id != null) {
			modelMap.put("taskJob", taskJobService.get(id));
		} else {
			modelMap.put("taskProject", taskProjectService.get(projectid));
		}
		return "manager/task/job_edit";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/taskJob/f_json/save")
	@ResponseBody
	public ModelMap save(HttpServletRequest request, TaskJob taskJob) {
		String result = null;
		SysUser sysUser = getSessionSysUser(request);
		try {
			if(taskJob.getId() == null) {
				taskJob.setAdduser(sysUser.getId());
				taskJobService.save(taskJob);
			} else {
				taskJobService.update(taskJob);
			}
			result = SUCCESS;
		} catch (Exception e) {
			LOGGER.error("保存异常: " + e.getMessage(), e);
			result = ERROR;
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put(RESULT, result);
		return modelMap;
	}
	
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value = "/taskJob/f_json/delete")
	@ResponseBody
	public ModelMap delete(HttpServletRequest request, Integer id) {
		String result = null;
		try {
			taskJobService.delete(id);
			result = SUCCESS;
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			result = ERROR;
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put(RESULT, result);
		return modelMap;
	}

	/**
	 * 修改任务状态
	 * @return
	 */
	@RequestMapping(value = "/taskJob/f_json/updateStatus")
	@ResponseBody
	public ModelMap updateStatus(HttpServletRequest request, Integer id, Integer status) {
		String result = null;
		try {
			taskJobService.updateStatus(id, status);
			result = SUCCESS;
		} catch (Exception e) {
			LOGGER.error("修改任务状态异常: " + e.getMessage(), e);
			result = ERROR;
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put(RESULT, result);
		return modelMap;
	}
	

	/**
	 * 执行job
	 * @return
	 */
	@RequestMapping(value = "/taskJob/f_json/execJob")
	@ResponseBody
	public ModelMap execJob(HttpServletRequest request, Integer id) {
		String result = null;
		try {
				taskJobService.execJob(id);
			result = SUCCESS;
		} catch (Exception e) {
			LOGGER.error("执行job异常: " + e.getMessage(), e);
			result = ERROR;
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put(RESULT, result);
		return modelMap;
	}
}