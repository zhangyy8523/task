package com.task.schedule.manager.service;

import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jing.system.model.MyPage;
import com.task.schedule.comm.enums.Config;
import com.task.schedule.core.exec.task.CleanTask;
import com.task.schedule.core.exec.task.MainTask;
import com.task.schedule.manager.dao.SysConfigDao;
import com.task.schedule.manager.pojo.SysConfig;

/**
 * 系统配置的Service
 * @author yuejing
 * @date 2015-03-30 14:07:27
 * @version V1.0.0
 */
@Component
public class SysConfigService {

	@Autowired
	private SysConfigDao sysConfigDao;
	@Autowired
	private MainTask mainTask;
	@Autowired
	private CleanTask taskJobLogCleanTask;

	/**
	 * 保存
	 * @param sysConfig
	 */
	public void save(SysConfig sysConfig) {
		sysConfigDao.save(sysConfig);
	}

	/**
	 * 修改
	 * @param sysUser
	 * @throws SchedulerException 
	 */
	public void update(SysConfig sysConfig) throws SchedulerException {
		sysConfigDao.update(sysConfig);
		//在MainTask中会自动识别是否有变更任务表达式(修改主线程的配置/修改清除日志任务的配置)，所以这里不需要执行修改
	}

	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	public SysConfig get(Integer id) {
		return sysConfigDao.get(id);
	}
	
	/**
	 * 获取值
	 * @param config
	 * @return
	 */
	public String getValue(Config config) {
		return getValue(config, null);
	}

	/**
	 * 根据code获取值
	 * @param id
	 * @param defValue
	 * @return
	 */
	public String getValue(Config config, String defValue) {
		SysConfig sysConfig = sysConfigDao.getByCode(config.getCode());
		if(sysConfig == null) {
			return defValue;
		}
		return sysConfig.getValue();
	}

	/**
	 * 分页获取对象
	 * @param sysUser
	 * @return
	 */
	public MyPage<SysConfig> pageQuery(SysConfig sysConfig) {
		sysConfig.setDefPageSize();
		int total = sysConfigDao.findSysConfigCount(sysConfig);
		List<SysConfig> rows = null;
		if(total > 0) {
			rows = sysConfigDao.findSysConfig(sysConfig);
		}
		MyPage<SysConfig> page = new MyPage<SysConfig>(sysConfig.getPage(), sysConfig.getSize(), total, rows);
		return page;
	}

}