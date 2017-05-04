package com.jing.system.serializer;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jing.system.utils.FrameTimeUtil;

/**
 * 比如相对于当前时间来计算
 * 	10分钟前，1小时前等展示
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @date    2014年10月23日 下午2:32:33 
 * @version 1.0.0
 */
public class JsonDateSimpleSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(FrameTimeUtil.getSimpleTime(value));
	}
}