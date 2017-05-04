package com.jing.system.threadpool;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程池工具类
 * @author  yuejing
 * @date    2015年9月29日 上午11:44:17 
 * @version 1.0.0
 */
public class FrameThreadPoolUtil {

	private static Map<String, FrameThreadPool> poolMap = new HashMap<String, FrameThreadPool>();
	
	/**
	 * 根据线程池名称获取线程池对象
	 * @param poolName
	 * @return
	 */
	public static FrameThreadPool getThreadPool(String poolName) {
		return getThreadPool(poolName, null);
	}
	
	/**
	 * 根据线程池名称和线程池大小获取线程池对象
	 * @param poolName
	 * @param parallelism
	 * @return
	 */
	public static FrameThreadPool getThreadPool(String poolName, Integer parallelism) {
		FrameThreadPool pool = poolMap.get(poolName);
		if(pool == null) {
			pool = new FrameThreadPool(poolName, parallelism);
			poolMap.put(poolName, pool);
		}
		return pool;
	}
}