package com.task.schedule.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jing.system.model.MyPage;
import com.jing.system.model.Result;
import com.jing.system.utils.FrameMd5Util;
import com.jing.system.utils.FrameStringUtil;
import com.task.schedule.comm.constants.Constant;
import com.task.schedule.comm.enums.GeneralStatus;
import com.task.schedule.manager.dao.SysUserDao;
import com.task.schedule.manager.pojo.SysUser;

/**
 * sys_user的Service
 * @author yuejing
 * @date 2015-03-30 14:07:27
 * @version V1.0.0
 */
@Component
public class SysUserService {

	@Autowired
	private SysUserDao sysUserDao;

	/**
	 * 登录
	 * @param usrInfo
	 * @return
	 */
	public Result<SysUser> login(SysUser sysUser) {
		Result<SysUser> result = new Result<SysUser>();
		SysUser user = getByUsername(sysUser.getUsername());
		if(user == null) {
			//不存在用户
			result.setResult("error_info");
			result.setMsg("请输入正确的用户和密码!");
			return result;
		}
		if(user.getStatus().intValue() == GeneralStatus.FREEZE.getCode().intValue()) {
			//帐号冻结
			result.setResult("error_user_freeze");
			result.setMsg("您的帐号被冻结, 快去联系客服吧!");
			return result;
		}
		if(!FrameMd5Util.getInstance().encodePassword(sysUser.getPassword(), user.getId().toString()).equalsIgnoreCase(user.getPassword())) {
			//密码不正确
			result.setResult("error_info");
			result.setMsg("请输入正确的用户和密码!");
			return result;
		}
		//密码相同
		result.setData(user);
		result.setResult(Constant.SUCCESS);
		return result;
	}

	private SysUser getByUsername(String username) {
		return sysUserDao.getByUsername(username);
	}

	/**
	 * 保存
	 * @param sysUser
	 */
	public void save(SysUser sysUser) {
		sysUserDao.save(sysUser);
		//更新密码
		String password = FrameMd5Util.getInstance().encodePassword(sysUser.getPassword(), sysUser.getId().toString());
		sysUserDao.updatePassword(sysUser.getId(), password);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void delete(Integer id) {
		sysUserDao.delete(id);
	}

	/**
	 * 修改
	 * @param sysUser
	 */
	public void update(SysUser sysUser) {
		if(FrameStringUtil.isNotEmpty(sysUser.getPassword())) {
			String password = FrameMd5Util.getInstance().encodePassword(sysUser.getPassword(), sysUser.getId().toString());
			sysUser.setPassword(password);
		}
		sysUserDao.update(sysUser);
	}

	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	public SysUser get(Integer id) {
		return sysUserDao.get(id);
	}

	/**
	 * 分页获取对象
	 * @param sysUser
	 * @return
	 */
	public MyPage<SysUser> pageQuery(SysUser sysUser) {
		sysUser.setDefPageSize();
		int total = sysUserDao.findSysUserCount(sysUser);
		List<SysUser> rows = null;
		if(total > 0) {
			rows = sysUserDao.findSysUser(sysUser);
			for (SysUser user : rows) {
				user.setStatusname(GeneralStatus.getText(user.getStatus()));
			}
		}
		MyPage<SysUser> page = new MyPage<SysUser>(sysUser.getPage(), sysUser.getSize(), total, rows);
		return page;
	}

}