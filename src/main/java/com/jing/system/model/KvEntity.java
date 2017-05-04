package com.jing.system.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Key/Value的实体类
 * @author yuejing
 * @date 2013-8-10 下午5:16:33
 * @version V1.0.0
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class KvEntity implements Serializable {

	private static final long serialVersionUID = -4495987129630008126L;
	private String kcode;
	private String kvalue;
	private String pid;
	private String exp;
	
	public KvEntity() {
		super();
	}

	public KvEntity(String kcode, String kvalue) {
		super();
		this.kcode = kcode;
		this.kvalue = kvalue;
	}
	public KvEntity(String kcode, String kvalue, String exp) {
		super();
		this.kcode = kcode;
		this.kvalue = kvalue;
		this.exp = exp;
	}

	public String getKcode() {
		return kcode;
	}
	public void setKcode(String kcode) {
		this.kcode = kcode;
	}
	public String getKvalue() {
		return kvalue;
	}
	public void setKvalue(String kvalue) {
		this.kvalue = kvalue;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getExp() {
		return exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
}