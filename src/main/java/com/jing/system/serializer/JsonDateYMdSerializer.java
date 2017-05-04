package com.jing.system.serializer;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jing.system.utils.FrameTimeUtil;

/**
 * 日期格式以yyyy-MM-dd这种形式展示
 * @author  yuejing
 * @email   yuejing0129@163.com 
 * @date    2014年10月23日 下午2:32:57 
 * @version 1.0.0
 */
public class JsonDateYMdSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
		jgen.writeString(FrameTimeUtil.parseString(value, FrameTimeUtil.FMT_YYYY_MM_DD));
	}
}