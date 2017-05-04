package com.task.schedule.comm.utils;

import com.jing.system.utils.FrameStringUtil;

/**
 * 校验规则
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @net		www.suyunyou.com
 * @date    2015年4月3日 下午5:17:29 
 * @version 1.0.0
 */
public class RuleVerifyUtil {

	/**
	 * 根据Http请求返回内容判断是否需要发送邮件
	 * @param content
	 * @return
	 */
	public static boolean isHttpResultSendMail(String content) {
		if(FrameStringUtil.isEmpty(content)) {
			return false;
		}
		int startNum = content.indexOf("{");
		if(startNum == -1) {
			return false;
		}
		int endNum = content.lastIndexOf("}");
		if(endNum == -1) {
			return false;
		}
		int ismNum = content.indexOf("isSendMail");
		if(ismNum < startNum || ismNum > endNum) {
			return false;
		}
		int mtNum = content.indexOf("mailTitle");
		if(mtNum < startNum || mtNum > endNum) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		String str = "{isSendMail:\"true\",mailTitle:\"邮件标题\",mailContent:\"邮件正文\",object:{id:1}}";
		System.out.println(isHttpResultSendMail(str));
	}
}