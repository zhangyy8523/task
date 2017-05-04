package com.jing.system.threadpool;

import java.util.UUID;
import java.util.concurrent.RecursiveAction;

/**
 * 线程任务
 * @author  yuejing
 * @date    2015年9月29日 上午11:44:17 
 * @version 1.0.0
 */
public abstract class FrameThreadAction extends RecursiveAction {

	private static final long serialVersionUID = 5121593411433025219L;

	/**
	 * 获取该任务的唯一标识
	 * @return
	 */
	public String getUniqueName() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取线程编号
	 * @return
	 */
	public Long getThreadId() {
		Thread thread = Thread.currentThread();
		return thread.getId();
	}

	/**
	 * 获取线程名称
	 * @return
	 */
	public String getThreadName() {
		Thread thread = Thread.currentThread();
		return thread.getName();
	}
}