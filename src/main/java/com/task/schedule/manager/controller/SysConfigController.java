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
import com.task.schedule.manager.pojo.SysConfig;
import com.task.schedule.manager.service.SysConfigService;

/**
 * 系统配置的Controller
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.suyunyou.com
 * @date    2015年4月5日 下午10:21:22 
 * @version 1.0.0
 */
@Controller
public class SysConfigController extends BaseController {

	private static final Logger LOGGER = Logger.getLogger(SysConfigController.class);

	@Autowired
	private SysConfigService sysConfigService;
	
	@RequestMapping(value = "/sysConfig/f_view/manager")
	public String manger(HttpServletRequest request) {
		return "manager/sys/config_manager";
	}

	/**
	 * 分页获取信息
	 * @return
	 */
	@RequestMapping(value = "/sysConfig/f_json/pageQuery")
	@ResponseBody
	public MyPage<SysConfig> pageQuery(HttpServletRequest request, SysConfig sysConfig) {
		MyPage<SysConfig> page = null;
		try {
			page = sysConfigService.pageQuery(sysConfig);
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
	@RequestMapping(value = "/sysConfig/f_view/edit")
	public String edit(HttpServletRequest request, ModelMap modelMap, Integer id) {
		if(id != null) {
			modelMap.put("sysConfig", sysConfigService.get(id));
		}
		return "manager/sys/config_edit";
	}

	/**
	 * 保存
	 * @return
	 */
	@RequestMapping(value = "/sysConfig/f_json/save")
	@ResponseBody
	public ModelMap save(HttpServletRequest request, SysConfig sysConfig) {
		String result = null;
		try {
			if(sysConfig.getId() == null) {
				sysConfigService.save(sysConfig);
			} else {
				sysConfigService.update(sysConfig);
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
	
}