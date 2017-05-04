package com.task.schedule.manager.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jing.system.model.BaseEntity;
import com.jing.system.serializer.JsonDateSimpleSerializer;

/**
 * task_job_log实体
 * @author yuejing
 * @date 2015-03-31 14:26:09
 * @version V1.0.0
 */
@Alias("taskJobLog")
@SuppressWarnings("serial")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class TaskJobLog extends BaseEntity implements Serializable {
	//编号
	private Integer id;
	//任务编号
	private Integer jobid;
	//添加时间
	private Date addtime;
	//状态job_log_status
	private Integer status;
	//请求地址
	private String link;
	//调度返回结果
	private String result;
	//服务编号
	private String servicecode;

	//========================== 扩展字段
	//状态名称
	private String statusname;
	
	public TaskJobLog() {
		super();
	}
	public TaskJobLog(Integer jobid, Date addtime, Integer status, String link, String result) {
		super();
		this.jobid = jobid;
		this.addtime = addtime;
		this.status = status;
		this.link = link;
		this.result = result;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getJobid() {
		return jobid;
	}
	public void setJobid(Integer jobid) {
		this.jobid = jobid;
	}
	@JsonSerialize(using = JsonDateSimpleSerializer.class)
	public Date getAddtime() {
		return addtime;
	}
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getStatusname() {
		return statusname;
	}
	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
	public String getServicecode() {
		return servicecode;
	}
	public void setServicecode(String servicecode) {
		this.servicecode = servicecode;
	}
}