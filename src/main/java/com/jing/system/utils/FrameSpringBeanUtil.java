package com.jing.system.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取spring对象的工具类
 * @author yuejing
 * @date 2016年1月29日 下午2:54:13
 * @version V1.0.0
 */
@Component
public class FrameSpringBeanUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		applicationContext = arg0;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

	public static <T> T getBean(Class<T> cls) {
        return (T) applicationContext.getBean(cls);
    }
}
