package com.task.schedule.manager.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jing.system.model.BaseEntity;
import com.jing.system.serializer.JsonDateSimpleSerializer;

/**
 * 服务实体
 * @author yuejing
 * @date 2015年4月5日 下午10:09:28
 * @version V1.0.0
 */
@Alias("servInfo")
@SuppressWarnings("serial")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ServInfo extends BaseEntity implements Serializable {
	//编码
	private String servid;
	//ip
	private String ip;
	//更新时间
	private Date updatetime;
	//添加时间
	private Date addtime;
	//状态[10正常、20已销毁]
	private Integer status;
	//是否Leader[0否、1是]
	private Integer isleader;
	
	//======================== 扩展属性
	//状态名称
	private String statusname;
	//任务数目
	private Integer jobnum;
	//运行分钟
	private Long runminute;
	
	public String getServid() {
		return servid;
	}
	public void setServid(String servid) {
		this.servid = servid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@JsonSerialize(using = JsonDateSimpleSerializer.class)
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
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
	public String getStatusname() {
		return statusname;
	}
	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}
	public Integer getIsleader() {
		return isleader;
	}
	public void setIsleader(Integer isleader) {
		this.isleader = isleader;
	}
	public Integer getJobnum() {
		return jobnum;
	}
	public void setJobnum(Integer jobnum) {
		this.jobnum = jobnum;
	}
	public Long getRunminute() {
		return runminute;
	}
	public void setRunminute(Long runminute) {
		this.runminute = runminute;
	}
}