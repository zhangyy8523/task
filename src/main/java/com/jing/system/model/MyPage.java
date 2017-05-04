package com.jing.system.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页操作类
 * @author yuejing
 * @date 2013-8-10 下午5:16:26
 * @version V1.0.0
 */
public class MyPage<T> {
	private static final String SUCCESS = "success";
	public static final int DEFAULT_PAGE = 1;
	public static final int DEFAULT_SIZE = 10;
	//当前第几页
	private int page;
	//每页多少个
	private int size;
	//总共多少页
	private int totalPage;
	//记录总数
	private int total;
	//当前查询结果的集合
	private List<T> rows;
	//记录返回状态[返回是否成功等]
	private String result;
	//返回消息
	private String msg;

	public MyPage() {
		this(0, 0, 0, null);
	}

	public MyPage(String result) {
		this(0, 0, 0, null);
		this.result = result;
	}

	public MyPage(String result, String msg) {
		this(0, 0, 0, null);
		this.result = result;
		this.msg = msg;
	}

	public MyPage(List<T> rows) {
		this(0, 0, 0, rows);
	}

	public MyPage(int page, int size, int total, List<T> rows) {
		super();
		this.page = page;
		this.size = size;
		this.total = total;
		this.rows = (rows == null ? new ArrayList<T>() : rows);
		this.totalPage = getTotalPage();
		this.result = SUCCESS;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalPage() {
		if (this.size > 0) {
			this.totalPage = (int) Math.ceil((double) total / this.size);
		} else {
			this.totalPage = 1;
		}
		return this.totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
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
}