package com.task.schedule.comm.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.jing.system.utils.FrameJsonUtil;
import com.jing.system.utils.FrameMd5Util;
import com.jing.system.utils.FrameStringUtil;
import com.task.schedule.manager.pojo.TaskProject;

/**
 * 签名工具类
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.suyunyou.com
 * @date    2015年3月31日 上午11:11:21 
 * @version 1.0.0
 */
public class SignUtil {
	
	public static Map<String, String> signParams(TaskProject project) {
		Map<String, String> params = null;
		if(FrameStringUtil.isEmpty(project.getSignstring())) {
			params = new HashMap<String, String>();
		} else {
			params = FrameJsonUtil.toMap(project.getSignstring());
		}
		String signString = "";
		String signParam = null;
		Iterator<Entry<String, String>> entryKeyIterator = params.entrySet().iterator();
		while (entryKeyIterator.hasNext()) {
			Entry<String, String> e = entryKeyIterator.next();
			String value = e.getValue();
			if("theCurrentTimestamp".equals(value)) {
				value = String.valueOf(System.currentTimeMillis());
				params.put(e.getKey(), value);
			} else if("encryptionParameters".equals(value)) {
				signParam = e.getKey();
				continue;
			}
			signString += value;
		}
		if(FrameStringUtil.isNotEmpty(signParam)) {
			params.put(signParam, FrameMd5Util.getInstance().encodePassword(signString).toLowerCase());
		}
		params.remove("token");
		return params;
	}
}
