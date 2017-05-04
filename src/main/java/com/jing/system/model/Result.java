package com.jing.system.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 返回结果
 * @author yuejing
 * @date 2013-8-16 下午9:33:12
 * @version V1.0.0
 */
@SuppressWarnings("serial")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Result<T> implements Serializable {
	//返回结果
	private String result;
	//返回消息
	private String msg;
	//返回数据
	private T data;

	public Result() {
	}
	public Result(String result) {
		this.result = result;
	}
	public Result(String result, String msg) {
		this.result = result;
		this.msg = msg;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
}