package com.task.schedule.comm.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jing.system.model.KvEntity;

/**
 * 定时任务的状态
 * 		0正常、50停止、100待添加、150添加异常、500异常终止
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.suyunyou.com
 * @date    2015年3月30日 下午4:59:53 
 * @version 1.0.0
 */
public enum JobStatus {
	NORMAL		(0, "正常"),
	STOP		(50, "停止"),
	WAIT		(100, "待添加"),
	ERROR_ADD	(150, "添加异常"),
	ERROR		(500, "异常终止");
	
	public static final String KEY = "job_status";
	
	private Integer code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<Integer, String> map = new HashMap<Integer, String>();

	private JobStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		Set<JobStatus> set = EnumSet.allOf(JobStatus.class);
		for(JobStatus e : set){
			map.put(e.getCode(), e.getName());
			if(e.getCode().intValue() == NORMAL.getCode().intValue()
					|| e.getCode().intValue() == STOP.getCode().intValue()) {
				list.add(new KvEntity(e.getCode().toString(), e.getName()));
			}
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
