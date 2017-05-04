package com.task.schedule.comm.constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;

import com.jing.system.model.KvEntity;
import com.task.schedule.comm.enums.Boolean;
import com.task.schedule.comm.enums.Config;
import com.task.schedule.comm.enums.GeneralStatus;
import com.task.schedule.comm.enums.JobLogStatus;
import com.task.schedule.comm.enums.JobStatus;
import com.task.schedule.comm.enums.ProjectSign;

/**
 * 字典常量
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @date    2014年10月27日 下午1:55:23 
 * @version 1.0.0
 */
public class DictCons {
	
	private static final Logger LOGGER = Logger.getLogger(DictCons.class);

	private static Map<String, Object> dictMap = new HashMap<String, Object>();
	
	/**
	 * 初始化权限需要的信息
	 * @param servletContext
	 */
	public static void init(ServletContext servletContext) {
		DictCons.addValue(GeneralStatus.KEY		, GeneralStatus.getList(), servletContext);
		DictCons.addValue(Boolean.KEY			, Boolean.getList(), servletContext);
		DictCons.addValue(JobStatus.KEY			, JobStatus.getList(), servletContext);
		DictCons.addValue(JobLogStatus.KEY		, JobLogStatus.getList(), servletContext);
		DictCons.addValue(ProjectSign.KEY		, ProjectSign.getList(), servletContext);
		DictCons.addValue(Config.KEY			, Config.getList(), servletContext);
		if(LOGGER.isInfoEnabled()) {
			LOGGER.info("========================= 初始化字典信息成功 ===========================");
		}
	}
	
	/**
	 * 添加字典信息
	 * @param key
	 * @param list
	 * @param servletContext
	 */
	public static void addValue(String key, List<KvEntity> list, ServletContext servletContext) {
		Map<String, String> valueMap = new HashMap<String, String>();
		for (KvEntity kvEntity : list) {
			valueMap.put(kvEntity.getKcode(), kvEntity.getKvalue());
		}
		dictMap.put(key, valueMap);
		dictMap.put(key + "_list", list);
		servletContext.setAttribute(key, list);
	}
	
	/**
	 * 根据值获取显示值
	 * @param key
	 * @param value
	 * @return
	 */
	public static String getValue(String key, Object value) {
		if(value == null) {
			return null;
		}
		return getMap(key).get(value.toString());
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> getMap(String key) {
		return (Map<String, String>) dictMap.get(key);
	}
	
	@SuppressWarnings("unchecked")
	public static List<KvEntity> getList(String key) {
		return (List<KvEntity>) dictMap.get(key + "_list");
	}
}