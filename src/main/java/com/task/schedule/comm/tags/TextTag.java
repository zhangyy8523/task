package com.task.schedule.comm.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.jing.system.utils.FrameStringUtil;
import com.task.schedule.comm.constants.DictCons;

/**
 * 显示文本
 * 根据类型和ID显示文本
 * @author yuejing
 * @date 2013-8-29 下午6:40:13
 * @version V1.0.0
 */
public class TextTag extends TagSupport {

	private static final long serialVersionUID = 7293280902947614510L;

	private String id;
	private String dictcode;
	private String defvalue;

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			if(FrameStringUtil.isEmpty(defvalue)) {
				defvalue = "";
			}
			if(FrameStringUtil.isEmpty(id) || FrameStringUtil.isEmpty(dictcode)) {
				out.print(defvalue);
				return SKIP_BODY;
			}
			String result = DictCons.getValue(dictcode, id);
			if(FrameStringUtil.isEmpty(result)) {
				out.print(defvalue);
			} else {
				out.print(result);
			}
		} catch(Exception e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public void release() {
		super.release();
		this.id = null;
		this.dictcode = null;
		this.defvalue = null;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDictcode() {
		return dictcode;
	}
	public void setDictcode(String dictcode) {
		this.dictcode = dictcode;
	}
	public String getDefvalue() {
		return defvalue;
	}
	public void setDefvalue(String defvalue) {
		this.defvalue = defvalue;
	}
}