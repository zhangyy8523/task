package com.jing.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页操作类
 * @author yuejing
 * @date 2013-8-10 下午5:16:26
 * @version V1.0.0
 */
public class Page<T> implements Serializable {
	private static final long serialVersionUID = -6252151650108228605L;
	public static final int DEF_PAGE = 1;
	public static final int DEF_SIZE = 10;
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

	public Page() {
		this(0, 0, 0, null);
	}

	public Page(List<T> rows) {
		this(0, 0, 0, rows);
	}

	public Page(Integer page, Integer size, int total, List<T> rows) {
		super();
		if(page == null) {
			page = Page.DEF_PAGE;
		}
		if(size == null) {
			size = Page.DEF_SIZE;
		}
		this.page = page;
		this.size = size;
		this.total = total;
		this.rows = (rows == null ? new ArrayList<T>() : rows);
		this.totalPage = getTotalPage();
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
}