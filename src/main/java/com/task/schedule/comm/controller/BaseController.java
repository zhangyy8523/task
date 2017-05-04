package com.task.schedule.comm.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.jing.system.utils.FrameJsonUtil;
import com.task.schedule.comm.constants.Constant;
import com.task.schedule.manager.pojo.SysUser;

/**
 * Base Controller
 * @author jing.yue
 * @version 2012/08/29 1.0.0
 */
public class BaseController {

	private static final Logger LOGGER = Logger.getLogger(BaseController.class);

	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	public static final String RESULT = "result";
	public static final String MSG = "msg";
	public static final String DATA = "data";
	
	public void writerJson(HttpServletResponse response, Object obj) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(FrameJsonUtil.toString(obj));
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	/**
	 * 设置值到Session中
	 * @param request
	 * @param key
	 * @param value
	 */
	public void setSession(HttpServletRequest request, String key, Object value) {
		request.getSession().setAttribute(key, value);
	}
	/**
	 * 获取Session中的值
	 * @param request
	 * @param key
	 * @return
	 */
	public Object getSession(HttpServletRequest request, String key) {
		return request.getSession().getAttribute(key);
	}
	/**
	 * 移除Session中的信息
	 * @param request
	 * @param key
	 */
	public void removeSession(HttpServletRequest request, String key) {
		request.getSession().removeAttribute(key);
	}
	
	/**
	 * 从Session中获取Share用户信息
	 * @param request
	 * @return
	 */
	public SysUser getSessionSysUser(HttpServletRequest request) {
		return (SysUser) request.getSession().getAttribute(Constant.SESSION_SYS_USER_KEY);
	}
	/**
	 * 设置用户信息到Session中
	 * @param request
	 * @param user
	 */
	public void setSessionSysUser(HttpServletRequest request, SysUser user) {
		request.getSession().setAttribute(Constant.SESSION_SYS_USER_KEY, user);
	}
	/**
	 * 移除授权了的Session中的用户信息
	 * @param request
	 * @param response 
	 */
	public void removeSessionSysUser(HttpServletRequest request) {
		request.getSession().removeAttribute(Constant.SESSION_SYS_USER_KEY);
	}
}