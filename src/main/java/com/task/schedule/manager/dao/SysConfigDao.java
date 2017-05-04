package com.task.schedule.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.task.schedule.manager.pojo.SysConfig;

/**
 * 系统配置的Dao
 * @author yuejing
 * @date 2015-03-31 14:26:09
 * @version V1.0.0
 */
public interface SysConfigDao {

	public void save(SysConfig sysConfig);

	public void update(SysConfig sysConfig);

	public SysConfig get(@Param("id")Integer id);

	public SysConfig getByCode(@Param("code")String code);

	public List<SysConfig> findSysConfig(SysConfig sysConfig);
	public int findSysConfigCount(SysConfig sysConfig);

}
