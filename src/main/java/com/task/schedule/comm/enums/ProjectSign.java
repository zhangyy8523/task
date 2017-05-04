package com.task.schedule.comm.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jing.system.model.KvEntity;

/**
 * 项目的签名规则
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.suyunyou.com
 * @date    2015年3月31日 上午10:27:18 
 * @version 1.0.0
 */
public enum ProjectSign {
	NORMAL		("0", "不加密", "{}"),
	MD5_T		("10", "md5(token)", "{token:\"sdfsdfsfsdf\",sign:\"encryptionParameters\"}"),
	MD5_CT		("20", "md5(渠道+token)", "{channel:\"50\",token:\"sdfsdfsfsdf\",sign:\"encryptionParameters\"}"),
	MD5_TT		("30", "md5(时间戳+token)", "{time:\"theCurrentTimestamp\",token:\"sdfsdfsfsdf\",sign:\"encryptionParameters\"}"),
	MD5_CTT		("40", "md5(渠道+时间戳+token)", "{channel:\"50\",time:\"theCurrentTimestamp\",token:\"sdfsdfsfsdf\",sign:\"encryptionParameters\"}");
	
	public static final String KEY = "project_sign";
	
	private String code;
	private String name;
	private String params;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<String, String> map = new HashMap<String, String>();

	private ProjectSign(String code, String name, String params) {
		this.code = code;
		this.name = name;
		this.params = params;
	}
	
	static {
		Set<ProjectSign> set = EnumSet.allOf(ProjectSign.class);
		for(ProjectSign e : set){
			map.put(e.getCode(), e.getName());
			list.add(new KvEntity(e.getCode().toString(), e.getName(), e.getParams()));
		}
	}

	/**
	 * 根据Code获取对应的汉字
	 * @param code
	 * @return
	 */
	public static String getText(String code) {
		return map.get(code);
	}
	
	/**
	 * 获取集合
	 * @return
	 */
	public static List<KvEntity> getList() {
		return list;
	}

	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public String getParams() {
		return params;
	}
}