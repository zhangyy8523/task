package com.task.schedule.comm.init;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.jing.system.utils.FrameSpringBeanUtil;
import com.jing.system.utils.FrameTimeUtil;
import com.task.schedule.comm.constants.Constant;
import com.task.schedule.comm.constants.DictCons;
import com.task.schedule.core.exec.TaskManager;
import com.task.schedule.manager.service.ServInfoService;

/**
 * 初始化系统数据的Servlet
 * @author yuejing
 * @date 2013-8-16 下午9:54:12
 * @version V1.0.0
 */
public class TaskInit extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(TaskInit.class);

	private static final long serialVersionUID = -2274726206362496315L;

	/**
	 * 初始化方法
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		LOGGER.info("初始化数据中...");
		long startTime = System.currentTimeMillis();
		Constant.webroot = config.getServletContext().getContextPath();
		config.getServletContext().setAttribute("webroot", Constant.webroot);
		//版本号为年月日[如: 20130126]
		String version = String.format("?version=%s", FrameTimeUtil.parseString(FrameTimeUtil.getTime(), FrameTimeUtil.FMT_YYYYMMDDHH));
		config.getServletContext().setAttribute("version", version);
		
		//初始化字典信息
		DictCons.init(config.getServletContext());
		
		//生成服务信息
		ServInfoService servInfoService = (ServInfoService)FrameSpringBeanUtil.getBean(ServInfoService.class);
		servInfoService.registerServer();
		
		//添加定时任务
		TaskManager taskManager = (TaskManager)FrameSpringBeanUtil.getBean(TaskManager.class);
		taskManager.init();
		
		LOGGER.info("初始化资源花费" + (System.currentTimeMillis() - startTime) + "毫秒!");
	}

}
