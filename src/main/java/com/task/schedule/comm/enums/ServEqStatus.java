package com.task.schedule.comm.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jing.system.model.KvEntity;

/**
 * 服务的状态[10待释放、20已释放]
 * @date 2016年4月25日 下午2:58:00 
 * @version V1.0
 */
public enum ServEqStatus {
	WAIT		(10, "待释放"),
	DESTROY		(20, "已释放");
	
	public static final String KEY = "serv_eq_status";
	
	private Integer code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private ServEqStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		Set<ServEqStatus> set = EnumSet.allOf(ServEqStatus.class);
		for(ServEqStatus e : set){
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
