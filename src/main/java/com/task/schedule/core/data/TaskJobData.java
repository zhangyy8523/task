package com.task.schedule.core.data;

import java.util.ArrayList;
import java.util.List;

/**
 * 任务的数据信息
 * @date 2016年4月25日 上午10:41:36 
 * @version V1.0
 */
public class TaskJobData {

	/** 任务监听的名称集合 */
	private static List<String> jobNames = new ArrayList<String>();
	
	/**
	 * 添加job的名称
	 * @param jobName
	 */
	public static void addJobName(String jobName) {
		if(!jobNames.contains(jobName)) {
			jobNames.add(jobName);
		}
	}
	
	/**
	 * 获取所有job的任务名称集合
	 * @return
	 */
	public static List<String> getJobNames() {
		return jobNames;
	}
	
	/**
	 * 移除job的名称
	 * @param jobName
	 */
	public static void removeJobName(String jobName) {
		if(jobNames.contains(jobName)) {
			jobNames.remove(jobName);
		}
	}
}