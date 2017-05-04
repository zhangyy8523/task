package com.jing.system.utils;

import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * 获取spring中的属性
 * @date 2016年4月20日 上午10:03:19 
 * @version V1.0
 */
@Component
public class FrameSpringPropertiesUtils implements EmbeddedValueResolverAware {

	private static StringValueResolver stringValueResolver;
	
	public FrameSpringPropertiesUtils() {
		super();
	}
	 
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        stringValueResolver = resolver;
    }
 
    public static String getPropertiesValue(String name){
    	name = "${" + name + "}";
        return stringValueResolver.resolveStringValue(name);
    }

}