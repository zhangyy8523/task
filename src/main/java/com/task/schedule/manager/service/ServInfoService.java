package com.task.schedule.manager.service;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jing.system.model.MyPage;
import com.jing.system.utils.FrameAddressUtil;
import com.jing.system.utils.FrameTimeUtil;
import com.task.schedule.comm.constants.Constant;
import com.task.schedule.comm.enums.Boolean;
import com.task.schedule.comm.enums.Config;
import com.task.schedule.comm.enums.ServInfoStatus;
import com.task.schedule.manager.dao.ServInfoDao;
import com.task.schedule.manager.pojo.ServInfo;

/**
 * 服务的Service
 * @date 2016年4月25日 下午2:45:39 
 * @version V1.0
 */
@Component
public class ServInfoService {

	@Autowired
	private ServInfoDao servInfoDao;
	@Autowired
	private SysConfigService configService;
	
	/**
	 * 注册服务
	 */
	public void registerServer() {
		String servid = Constant.serviceCode();
		ServInfo org = get(servid);
		if(org != null) {
			Constant.resetServiceCode();
			servid = Constant.serviceCode();
		}
		ServInfo servInfo = new ServInfo();
		servInfo.setServid(servid);
		try {
			servInfo.setIp(FrameAddressUtil.getLocalIP());
		} catch (UnknownHostException e) {
			servInfo.setIp("127.0.0.1");
		}
		servInfo.setStatus(ServInfoStatus.NORMAL.getCode());
		servInfo.setIsleader(Boolean.FALSE.getCode());
		servInfoDao.save(servInfo);
	}

	/**
	 * 删除
	 * @param servid
	 */
	public void delete(String servid) {
		servInfoDao.delete(servid);
	}

	/**
	 * 根据servid获取对象
	 * @param servid
	 * @return
	 */
	public ServInfo get(String servid) {
		return servInfoDao.get(servid);
	}

	/**
	 * 分页获取对象
	 * @param servInfo
	 * @return
	 */
	public MyPage<ServInfo> pageQuery(ServInfo servInfo) {
		servInfo.setDefPageSize();
		int total = servInfoDao.findServInfoCount(servInfo);
		List<ServInfo> rows = null;
		if(total > 0) {
			rows = servInfoDao.findServInfo(servInfo);
			for (ServInfo si : rows) {
				si.setStatusname(ServInfoStatus.getText(si.getStatus()));
				if(ServInfoStatus.NORMAL.getCode() == si.getStatus().intValue()) {
					//设置运行时间
					si.setRunminute(FrameTimeUtil.getDateDiff(si.getAddtime(), FrameTimeUtil.getTime(), 1));
				} else {
					si.setRunminute(-1l);
				}
			}
		}
		MyPage<ServInfo> page = new MyPage<ServInfo>(servInfo.getPage(), servInfo.getSize(), total, rows);
		return page;
	}

	/**
	 * 修改活动的服务的updatetime为当前时间
	 * @param servid
	 */
	public void updateUpdatetimeByServid(String servid) {
		servInfoDao.updateUpdatetimeStatusByServid(servid, ServInfoStatus.NORMAL.getCode());
	}

	/**
	 * 将过期的服务状态变更为停止
	 */
	public void updateDestroy() {
		Integer destroyTime = Integer.valueOf(configService.getValue(Config.LOCK_DESTROY_TIME, "30"));
		servInfoDao.updateDestroy(ServInfoStatus.DESTROY.getCode(), destroyTime);
	}

	public void deleteDestroyLtDate(Date date) {
		servInfoDao.deleteDestroyLtDate(ServInfoStatus.DESTROY.getCode(), date);
	}

	/**
	 * 获取领导对象
	 * @return
	 */
	public ServInfo chooseLeader() {
		ServInfo si = getLeader();
		if(si == null) {
			//选取Leader
			servInfoDao.updateChooseLeader(ServInfoStatus.NORMAL.getCode(), Boolean.TRUE.getCode());
			si = getLeader();
		}
		return si;
	}
	
	/**
	 * 获取Leader对象
	 * @return
	 */
	public ServInfo getLeader() {
		return servInfoDao.getByStatusIsleader(ServInfoStatus.NORMAL.getCode(), Boolean.TRUE.getCode());
	}

	/**
	 * 修改已销毁服务为非Leader
	 */
	public void destroyLeader() {
		servInfoDao.updateIsleaderByStatus(ServInfoStatus.DESTROY.getCode(), Boolean.FALSE.getCode());
	}

	/**
	 * 根据状态获取服务集合
	 * @param status
	 * @return
	 */
	public List<ServInfo> findByStatus(Integer status) {
		return servInfoDao.findByStatus(status);
	}

}