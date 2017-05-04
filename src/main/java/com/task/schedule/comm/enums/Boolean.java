package com.task.schedule.comm.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jing.system.model.KvEntity;

/**
 * 是否
 * @author yuejing
 * @date 2014年11月16日 下午7:52:03
 * @version V1.0.0
 */
public enum Boolean {
	FALSE	(0, "否"),
	TRUE	(1, "是");
	
	public static final String KEY = "boolean";

	private Integer code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private Boolean(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		Set<Boolean> set = EnumSet.allOf(Boolean.class);
		for(Boolean e : set){
			map.put(e.getCode(), e.getName());
			list.add(new KvEntity(e.getCode().toString(), e.getName()));
		}
	}

	/**
	 * 根据Code获取对应的汉字
	 * @param code
	 * @return
	 */
	public static String getText(Integer code) {
		return map.get(code);
	}
	
	/**
	 * 获取集合
	 * @return
	 */
	public static List<KvEntity> getList() {
		return list;
	}

	public Integer getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
}
