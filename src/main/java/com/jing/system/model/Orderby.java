package com.jing.system.model;

import java.io.Serializable;

import com.jing.system.utils.FrameStringUtil;

/**
 * 排序的实体<br>
 * public static void main(String[] args) {
		Orderby o = new Orderby();
		o.setProperty("dRfd");
		o.setType("asc");
		o.setOrder(1);
		System.out.println(o.getProperty());
	}
 * @date 2016年3月30日 下午7:34:43
 * @version V1.0
 */
@SuppressWarnings("serial")
public class Orderby implements Serializable {

	//属性
	private String property;
	//排序类型：asc（升序）、desc（降序）
	private String type;
	//顺序：数字越小，则排序规则根据第一个来
	private Integer order;

	public Orderby() {
		super();
	}
	public Orderby(String property, String type, Integer order) {
		super();
		this.property = property;
		this.type = type;
		this.order = order;
	}

	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		if(FrameStringUtil.isNotEmpty(property)) {

			boolean bool = property.matches("^[a-z0-9A-Z]+$");
			if(!bool) {
				this.property = null;
				return;
			}
		}
		this.property = property;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		if(!"asc".equals(type) && !"desc".equals(type)) {
			//不为指定的排序类型，则设置为升序
			type = "asc";
		}
		this.type = type;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
}