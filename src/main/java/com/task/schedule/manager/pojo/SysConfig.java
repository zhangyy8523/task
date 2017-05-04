package com.task.schedule.manager.pojo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jing.system.model.BaseEntity;

/**
 * 系统配置实体
 * @author yuejing
 * @date 2015年4月5日 下午10:09:28
 * @version V1.0.0
 */
@Alias("sysConfig")
@SuppressWarnings("serial")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class SysConfig extends BaseEntity implements Serializable {
	//编号
	private Integer id;
	//编码
	private String code;
	//名称
	private String name;
	//值
	private String value;
	//备注
	private String remark;
	//扩展1
	private String exp1;
	//扩展2
	private String exp2;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getExp1() {
		return exp1;
	}
	public void setExp1(String exp1) {
		this.exp1 = exp1;
	}
	public String getExp2() {
		return exp2;
	}
	public void setExp2(String exp2) {
		this.exp2 = exp2;
	}
}