package com.task.schedule.manager.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.task.schedule.manager.pojo.SysUser;

/**
 * sys_user的Dao
 * @author yuejing
 * @date 2015-03-30 14:07:27
 * @version V1.0.0
 */
public interface SysUserDao {

	public void save(SysUser sysUser);

	public void update(SysUser sysUser);

	public void updatePassword(@Param("id")Integer id, @Param("password")String password);

	public void delete(@Param("id")Integer id);

	public SysUser get(@Param("id")Integer id);

	public SysUser getByUsername(@Param("username")String username);

	public List<SysUser> findSysUser(SysUser sysUser);
	public int findSysUserCount(SysUser sysUser);

}