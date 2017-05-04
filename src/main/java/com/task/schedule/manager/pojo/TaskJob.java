package com.task.schedule.manager.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jing.system.model.BaseEntity;

/**
 * task_job实体
 * @author yuejing
 * @date 2015-03-30 14:07:27
 * @version V1.0.0
 */
@Alias("taskJob")
@SuppressWarnings("serial")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TaskJob extends BaseEntity implements Serializable {
	//编号
	private Integer id;
	//项目编号
	private Integer projectid;
	//名称
	private String name;
	//描叙
	private String remark;
	//调用链接
	private String link;
	//执行规则
	private String cron;
	//失败发邮件（0否1是）
	private Integer isfailmail;
	//添加时间
	private Date addtime;
	//添加人
	private Integer adduser;
	//状态【0正常、50停止、100待添加、150添加异常、500异常终止】
	private Integer status;
	//状态消息
	private String statusmsg;
	
	//服务编号
	private String servid;
	//更新时间
	private Date updatetime;
	
	//====================== 扩展属性
	//状态名称
	private String statusname;
	//失败是否发邮件名称
	private String isfailmailname;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProjectid() {
		return projectid;
	}
	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
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
	public String getStatusmsg() {
		return statusmsg;
	}
	public void setStatusmsg(String statusmsg) {
		this.statusmsg = statusmsg;
	}
	public String getStatusname() {
		return statusname;
	}
	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
	public Integer getIsfailmail() {
		return isfailmail;
	}
	public void setIsfailmail(Integer isfailmail) {
		this.isfailmail = isfailmail;
	}
	public String getIsfailmailname() {
		return isfailmailname;
	}
	public void setIsfailmailname(String isfailmailname) {
		this.isfailmailname = isfailmailname;
	}
	public String getServid() {
		return servid;
	}
	public void setServid(String servid) {
		this.servid = servid;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
}