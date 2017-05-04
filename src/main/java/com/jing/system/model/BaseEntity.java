package com.jing.system.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jing.system.comparator.OrderbyComparator;

/**
 * 分页查询条件的实体类
 * @author yuejing
 * @date 2013-8-10 下午5:16:33
 * @version V1.0.0
 */
@JsonIgnoreProperties("currentIndex")
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -4495987129630008126L;

	private Integer page;
	private Integer size;
	private Integer rows;
	private Integer currentIndex;
	
	//排序
	private List<Orderby> orderbys;

	public List<Orderby> getOrderbys() {
		return orderbys;
	}
	@SuppressWarnings("unchecked")
	public void setOrderbys(List<Orderby> orderbys) {
		if(orderbys != null && orderbys.size() > 0) {
			Collections.sort(orderbys, new OrderbyComparator());
		}
		this.orderbys = orderbys;
	}

	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public Integer getRows() {
		return rows;
	}
	public void setRows(Integer rows) {
		this.rows = rows;
		if(rows != null) {
			this.size = rows;
		}
	}
	/**
	 * 此属性在用Jackson转换时，不显示
	 * @return
	 */
	public Integer getCurrentIndex() {
		if(currentIndex == null && getPage() != null && getSize() != null) {
			this.currentIndex = (getPage() - 1) * getSize();
		}
		return currentIndex;
	}
	public void setCurrentIndex(Integer currentIndex) {
		if(currentIndex != null) {
			this.currentIndex = currentIndex;
		} else if(currentIndex == null && getPage() != null && getSize() != null) {
			this.currentIndex = (getPage() - 1) * getSize();
		}
		this.currentIndex = currentIndex;
	}
	
	public void setDefPageSize() {
		if(this.page == null) {
			this.page = Page.DEF_PAGE;
		}
		if(this.size == null) {
			this.size = Page.DEF_SIZE;
		}
	}
	
}