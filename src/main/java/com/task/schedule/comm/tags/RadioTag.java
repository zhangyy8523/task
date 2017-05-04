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
 * 单选按钮
 * @author yuejing
 * @date 2013-8-18 上午11:00:11
 * @version V1.0.0
 */
public class RadioTag extends TagSupport {

	private static final long serialVersionUID = 7293280902947614510L;

	private static final Logger LOGGER = Logger.getLogger(RadioTag.class);

	private String id;
	private String name;
	private String value;
	private String defvalue;
	private String cssCls;
	private List<KvEntity> items;
	private String dictcode;
	//扩展字符串
	private String exp;

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = this.pageContext.getOut();
			if(items == null && FrameStringUtil.isEmpty(dictcode)) {
				LOGGER.error("items is null");
				return SKIP_BODY;
			}
			StringBuilder result = new StringBuilder();
			if(FrameStringUtil.isNotEmpty(dictcode)) {
				//从字典中取集合
				items = DictCons.getList(dictcode);
			}
			if(FrameStringUtil.isEmpty(value)) {
				value = defvalue;
			}
			for (KvEntity kvEntity : items) {
				result.append("<label style=\"font-weight: normal;\"><input type=\"radio\" id=\"").append(getId()).append(kvEntity.getKcode()).append("\" name=\"").append(getName()).append("\" class=\"").append(getCssCls()).append("\" ").append(getExp());
				result.append(" value=\"").append(kvEntity.getKcode()).append("\"");
				if(FrameStringUtil.isNotEmpty(value) && value.equals(kvEntity.getKcode())) {
					result.append(" checked=\"checked\"");
				}
				result.append(" /> ").append(kvEntity.getKvalue()).append("</label>&nbsp;&nbsp;");
			}
			out.println(result.toString());
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
		this.name = null;
		this.cssCls = null;
		this.value = null;
		this.defvalue = null;
		this.items = null;
		this.dictcode = null;
		this.exp = null;
	}

	public String getId() {
		return (id == null ? "" : id);
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return (name == null ? "" : name);
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCssCls() {
		return (cssCls == null ? "" : cssCls);
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
		return (exp == null ? "" : exp);
	}
	public void setExp(String exp) {
		this.exp = exp;
	}
	public String getDefvalue() {
		return defvalue;
	}
	public void setDefvalue(String defvalue) {
		this.defvalue = defvalue;
	}
}