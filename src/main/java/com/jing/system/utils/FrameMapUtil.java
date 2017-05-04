package com.jing.system.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Map工具类
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @date    2014年12月25日 上午10:42:27 
 * @version 1.0.0
 */
@SuppressWarnings("rawtypes")
public class FrameMapUtil {

	public static String getString(Map map, String key) {
		if(map == null) {
			return null;
		}
		Object value = map.get(key);
		if(value == null) {
			return null;
		}
		return value.toString();
	}
	
	public static Integer getInteger(Map map, String key) {
		if(map == null) {
			return null;
		}
		Object value = map.get(key);
		if(value == null || "".equals(value.toString())) {
			return null;
		}
		return Integer.valueOf(value.toString());
	}
	
	public static Long getLong(Map map, String key) {
		if(map == null) {
			return null;
		}
		Object value = map.get(key);
		if(value == null || "".equals(value.toString())) {
			return null;
		}
		return Long.valueOf(value.toString());
	}
	
	public static Float getFloat(Map map, String key) {
		if(map == null) {
			return null;
		}
		Object value = map.get(key);
		if(value == null || "".equals(value.toString())) {
			return null;
		}
		return Float.valueOf(value.toString());
	}
	
	public static Double getDouble(Map map, String key) {
		if(map == null) {
			return null;
		}
		Object value = map.get(key);
		if(value == null || "".equals(value.toString())) {
			return null;
		}
		return Double.valueOf(value.toString());
	}
	
	public static Date getDate(Map map, String key, String fmt) {
		if(map == null) {
			return null;
		}
		Object value = map.get(key);
		if(value == null || "".equals(value.toString())) {
			return null;
		}
		return FrameTimeUtil.parseDate(value.toString(), fmt);
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getListString(Map map, String key) {
		if(map == null) {
			return new ArrayList<String>();
		}
		Object value = map.get(key);
		if(value == null) {
			return new ArrayList<String>();
		}
		return (List<String>) value;
	}

	@SuppressWarnings("unchecked")
	public static List<Map> getListMap(Map map, String key) {
		if(map == null) {
			return new ArrayList<Map>();
		}
		Object value = map.get(key);
		if(value == null) {
			return new ArrayList<Map>();
		}
		return (List<Map>) value;
	}
	
	public static Map getMap(Map map, String key) {
		if(map == null) {
			return null;
		}
		Object value = map.get(key);
		if(value == null) {
			return null;
		}
		return (Map) value;
	}
}