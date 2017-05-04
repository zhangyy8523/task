package com.task.schedule.manager.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.task.schedule.manager.pojo.ServInfo;

public interface ServInfoDao {

	public abstract void save(ServInfo servInfo);

	public abstract void delete(@Param("servid")String servid);

	public abstract ServInfo get(@Param("servid")String servid);

	public abstract List<ServInfo> findServInfo(ServInfo servInfo);
	public abstract int findServInfoCount(ServInfo servInfo);

	public abstract void updateUpdatetimeStatusByServid(@Param("servid")String servid, @Param("status")Integer status);

	public abstract void updateDestroy(@Param("destroyStatus")Integer destroyStatus, @Param("destroyTime")Integer destroyTime);

	public abstract void deleteDestroyLtDate(@Param("destroyStatus")Integer destroyStatus, @Param("date")Date date);

	public abstract ServInfo getByStatusIsleader(@Param("status")Integer status, @Param("isleader")Integer isleader);

	public abstract void updateChooseLeader(@Param("status")Integer status, @Param("isleader")Integer isleader);

	public abstract void updateIsleaderByStatus(@Param("status")Integer status, @Param("isleader")Integer isleader);

	public abstract List<ServInfo> findByStatus(@Param("status")Integer status);
}