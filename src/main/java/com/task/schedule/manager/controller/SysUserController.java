package com.task.schedule.manager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jing.system.model.MyPage;
import com.jing.system.model.Result;
import com.task.schedule.comm.constants.Constant;
import com.task.schedule.comm.controller.BaseController;
import com.task.schedule.manager.pojo.SysUser;
import com.task.schedule.manager.service.SysUserService;

/**
 * 用户的Controller
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.suyunyou.com
 * @date    2015年3月30日 下午2:21:22 
 * @version 1.0.0
 */
@Controller
public class SysUserController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(SysUserController.class);

	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * ajax登录
	 * @return
	 */
	@RequestMapping(value = "/sysUser/json/login")
	@ResponseBody
	public Result<SysUser> login(HttpServletRequest request, HttpServletResponse response, SysUser sysUser) {
		Result<SysUser> result = null;
		try {
			result = sysUserService.login(sysUser);
			if(Constant.SUCCESS.equals(result.getResult())) {
				setSessionSysUser(request, result.getData());
			}
			result.setData(null);
		} catch (Exception e) {
			LOGGER.error("登录异常: " + e.getMessage(), e);
			result = new Result<SysUser>(ERROR);
		}
		return result;
	}
	
	/**
	 * 退出
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sysUser/f_view/exit")
	public String exit(HttpServletRequest request) {
		removeSessionSysUser(request);
		return "redirect:/index.jsp";
	}
	
	/**
	 * 跳转到管理页
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/sysUser/f_view/main")
	public String main(HttpServletRequest request) {
		return "manager/main";
	}
	
	@RequestMapping(value = "/sysUser/f_view/manager")
	public String manger(HttpServletRequest request) {
		return "manager/user/user_manager";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/sysUser/f_json/pageQuery")
	@ResponseBody
	public MyPage<SysUser> pageQuery(HttpServletRequest request, SysUser sysUser) {
		MyPage<SysUser> page = null;
		try {
			page = sysUserService.pageQuery(sysUser);
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
	@RequestMapping(value = "/sysUser/f_view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap, Integer id) {
		if(id != null) {
			modelMap.put("sysUser", sysUserService.get(id));
		}
		return "manager/user/user_edit";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/sysUser/f_json/save")
	@ResponseBody
	public ModelMap save(HttpServletRequest request, SysUser sysUser) {
		String result = null;
		SysUser user = getSessionSysUser(request);
		try {
			if(sysUser.getId() == null) {
				sysUser.setAdduser(user.getId());
				sysUserService.save(sysUser);
			} else {
				sysUserService.update(sysUser);
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
	@RequestMapping(value = "/sysUser/f_json/delete")
	@ResponseBody
	public ModelMap delete(HttpServletRequest request, Integer id) {
		String result = null;
		try {
			sysUserService.delete(id);
			result = SUCCESS;
		} catch (Exception e) {
			LOGGER.error("删除异常: " + e.getMessage(), e);
			result = ERROR;
		}
		ModelMap modelMap = new ModelMap();
		modelMap.put(RESULT, result);
		return modelMap;
	}
}
