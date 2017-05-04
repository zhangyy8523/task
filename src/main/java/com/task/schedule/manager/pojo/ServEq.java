package com.task.schedule.manager.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jing.system.model.BaseEntity;

/**
 * 服务均衡实体
 * @author yuejing
 * @date 2015年4月5日 下午10:09:28
 * @version V1.0.0
 */
@Alias("servEq")
@SuppressWarnings("serial")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ServEq extends BaseEntity implements Serializable {
	//编号
	private Integer id;
	//编码
	private String servid;
	//任务编号
	private Integer jobid;
	//添加时间
	private Date addtime;
	//释放时间
	private Date destroytime;
	//状态[10待释放、20已释放]
	private Integer status;
	
	//======================== 扩展属性
	//状态名称
	private String statusname;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getServid() {
		return servid;
	}

	public void setServid(String servid) {
		this.servid = servid;
	}

	public Integer getJobid() {
		return jobid;
	}

	public void setJobid(Integer jobid) {
		this.jobid = jobid;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public Date getDestroytime() {
		return destroytime;
	}

	public void setDestroytime(Date destroytime) {
		this.destroytime = destroytime;
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