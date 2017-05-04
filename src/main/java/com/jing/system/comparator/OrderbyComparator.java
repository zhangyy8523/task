package com.jing.system.comparator;

import java.util.Comparator;

import com.jing.system.model.Orderby;

/**
 * 排序对象的排序器
 * @date 2016年3月30日 下午7:49:27 
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public class OrderbyComparator implements Comparator {

	public int compare(Object obj0, Object obj1) {
		Orderby o1 = (Orderby)obj0;
		Orderby o2 = (Orderby)obj1;

		//首先比较年龄，如果年龄相同，则比较名字

		int flag = o1.getOrder().compareTo(o2.getOrder());
		return flag;
	}

}