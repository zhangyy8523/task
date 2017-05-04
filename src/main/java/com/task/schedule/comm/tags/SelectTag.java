package com.task.schedule.comm.tags;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import com.jing.system.model.KvEntity;
import com.jing.system.utils.FrameStringUtil;
import com.task.schedule.comm.constants.DictCons;

/**
 * 下拉框
 * @author yuejing
 * @date 2013-8-18 上午11:00:11
 * @version V1.0.0
 */
public class SelectTag extends TagSupport {

	private static final long serialVersionUID = 7293280902947614510L;

	private static final Logger LOGGER = Logger.getLogger(SelectTag.class);

	private String id;
	private String name;
	private String value;
	private String cssCls;
	private String headerKey;
	private String headerValue;
	private List<KvEntity> items;
	private String dictcode;
	//扩展字符串
	private String exp;

	@Override
	public int doStartTag() {
		try {
			JspWriter out = this.pageContext.getOut();
			if(items == null && FrameStringUtil.isEmpty(dictcode)) {
				LOGGER.error("items is null");
				return SKIP_BODY;
			}
			StringBuilder result = new StringBuilder();
			result.append("<select id=\"").append(getId()).append("\" name=\"").append(getName()).append("\" class=\"").append(getCssCls()).append("\" ").append(getExp()).append(">");
			if(FrameStringUtil.isNotEmpty(headerValue)) {
				result.append("<option value=\"").append(headerKey).append("\">").append(headerValue).append("</option>");
			}
			if(FrameStringUtil.isNotEmpty(dictcode)) {
				//从字典中取集合
				items = DictCons.getList(dictcode);
			}
			for (KvEntity kvEntity : items) {
				result.append("<option value=\"").append(kvEntity.getKcode()).append("\"");
				if(FrameStringUtil.isNotEmpty(value) && value.equals(kvEntity.getKcode())) {
					result.append(" selected=\"selected\"");
				}
				result.append(">").append(kvEntity.getKvalue()).append("</option>");
			}
			result.append("</select>");
			out.println(result.toString());
		} catch(Exception e) {
			LOGGER.error("输出异常: " + e.getMessage());
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
		this.name = null;
		this.cssCls = null;
		this.value = null;
		this.headerKey = null;
		this.headerValue = null;
		this.items = null;
		this.dictcode = null;
		this.exp = null;
	}

	public String getId() {
		return id == null ? "" : id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name == null ? "" : name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCssCls() {
		return cssCls == null ? "" : cssCls;
	}
	public void setCssCls(String cssCls) {
		this.cssCls = cssCls;
	}
	public List<KvEntity> getItems() {
		return items;
	}
	public void setItems(List<KvEntity> items) {
		this.items = items;
	}
	public String getHeaderKey() {
		return headerKey;
	}
	public void setHeaderKey(String headerKey) {
		this.headerKey = headerKey;
	}
	public String getHeaderValue() {
		return headerValue;
	}
	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDictcode() {
		return dictcode;
	}
	public void setDictcode(String dictcode) {
		this.dictcode = dictcode;
	}
	public String getExp() {
		return exp == null ? "" : exp;
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
}