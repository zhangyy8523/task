package com.task.schedule.comm.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jing.system.model.KvEntity;

/**
 * 定时任务获取记录的状态
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.suyunyou.com
 * @date    2015年3月31日 下午3:17:59 
 * @version 1.0.0
 */
public enum JobLogStatus {
	NORMAL		(0, "正常"),
	ERROR		(50, "获取异常");

	public static final String KEY = "job_log_status";

	private Integer code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private JobLogStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	static {
		Set<JobLogStatus> set = EnumSet.allOf(JobLogStatus.class);
		for(JobLogStatus e : set){
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
