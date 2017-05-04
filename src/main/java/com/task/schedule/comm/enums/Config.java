package com.task.schedule.comm.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jing.system.model.KvEntity;

/**
 * 系统配置
 * @author yuejing
 * @date 2015年4月5日 下午10:05:22
 * @version V1.0.0
 */
public enum Config {
	TASK_MAIN_CRON		("task.main.cron", 		"主线程的时间表达式"),
	LEADER_CRON			("leader.cron", 		"Leader的时间表达式"),
	//TASK_WAIT_NUM		("task.wait.num", 		"获取待添加任务的数目"),
	MAIL_SMTP			("mail.smtp", 			"发送邮箱的smtp"),
	MAIL_FROM			("mail.from", 			"发送邮件的邮箱"),
	MAIL_USERNAME		("mail.username", 		"发送邮件的用户名"),
	MAIL_PASSWORD		("mail.password", 		"发送邮件的密码"),
	CLEAN_CRON			("clean.cron", 			"清空调度记录表达式"),
	JOBLOG_SAVE_DAY		("joblog.save.day", 	"调度记录保存天数"),
	SERV_SAVE_DAY		("serv.save.day", 		"已停止的服务保存天数"),
	LOCK_DESTROY_TIME	("lock.destroy.time", 	"消耗服务和任务的时间[单位:s]"),
	;

	public static final String KEY = "config";
	
	private String code;
	private String name;
	private static List<KvEntity> list = new ArrayList<KvEntity>();
	private static Map<String, String> map = new HashMap<String, String>();

	private Config(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	static {
		Set<Config> set = EnumSet.allOf(Config.class);
		for(Config e : set){
			map.put(e.getCode(), e.getName());
			list.add(new KvEntity(e.getCode().toString(), e.getName()));
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
}
