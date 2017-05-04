package com.task.schedule.manager.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.task.schedule.manager.pojo.ServEq;

public interface ServEqDao {

	public abstract void save(ServEq servEq);

	public abstract void deleteDestroyLtDate(@Param("date")Date date);

	public abstract List<ServEq> findByServid(@Param("servid")String servid, @Param("status")Integer status);

	public abstract void updateStatus(@Param("id")Integer id, @Param("status")Integer status);
}