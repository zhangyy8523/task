package com.jing.system.threadpool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

/**
 * 线程池
 * @author  yuejing
 * @date    2015年9月29日 上午11:44:17 
 * @version 1.0.0
 */
public class FrameThreadPool {

	//线程的数目等于CPU的核心数(数组大小由parallelism属性决定，parallelism默认为处理器个数)
	private static Map<String, ForkJoinPool> poolMap = new HashMap<String, ForkJoinPool>();
	
	//连接池线程数
	private Integer parallelism;
	//线程池名称
	private String poolName;

	/**
	 * 构造函数
	 * @param poolName		线程池名称
	 */
	public FrameThreadPool(String poolName) {
		this.poolName = poolName;
		init(poolName, null);
	}
	/**
	 * 构造函数
	 * @param poolName		线程池名称
	 * @param parallelism	线程数（为null默认为20）
	 */
	public FrameThreadPool(String poolName, Integer parallelism) {
		this.poolName = poolName;
		this.parallelism = parallelism;
		init(poolName, parallelism);
	}

	/*
	 * 获取ForkJoin连接池
	 * @param poolName		线程池名称
	 * @param parallelism	线程数（为null默认为20）
	 * @return
	 */
	private void init(String poolName, Integer parallelism) {
		ForkJoinPool pool = poolMap.get(poolName);
		if(pool == null) {
			if(parallelism == null) {
				parallelism = 20;
			}
			pool = new ForkJoinPool(parallelism);
			poolMap.put(poolName, pool);
		}
	}
	
	/*
	 * 获取连接池
	 */
	private ForkJoinPool getPool() {
		ForkJoinPool pool = poolMap.get(poolName);
		if(pool == null) {
			init(poolName, parallelism);
			pool = poolMap.get(poolName);
		}
		return pool;
	}

	/**
	 * 执行指定的任务，等待完成，返回结果
	 * @param task
	 */
	public void invoke(FrameThreadAction task) {
		getPool().invoke(task);
	}
	
	/**
	 * 异步执行指定的任务
	 * @param task
	 */
	public void execute(FrameThreadAction task) {
		getPool().execute(task);
	}

	/**
	 * 销毁线程
	 */
	public void destroy() {
		poolMap.put(poolName, null);
	}
}