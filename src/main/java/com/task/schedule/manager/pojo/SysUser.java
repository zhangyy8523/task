package com.task.schedule.manager.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jing.system.model.BaseEntity;

/**
 * sys_user实体
 * @author yuejing
 * @date 2015-03-30 14:07:27
 * @version V1.0.0
 */
@Alias("sysUser")
@SuppressWarnings("serial")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SysUser extends BaseEntity implements Serializable {
	//编号
	private Integer id;
	//用户名
	private String username;
	//密码
	private String password;
	//昵称
	private String nickname;
	//添加时间
	private Date addtime;
	//添加人
	private Integer adduser;
	//状态【0正常、1冻结】GeneralStatus
	private Integer status;
	
	//========================== 扩展字段
	//状态名称
	private String statusname;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Integer getAdduser() {
		return adduser;
	}
	public void setAdduser(Integer adduser) {
		this.adduser = adduser;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusname() {
		return statusname;
	}
	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
}