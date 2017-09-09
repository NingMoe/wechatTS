package com.mcu32.firstq.wechat.util.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.mcu32.firstq.common.bean.bo.DictEntryBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;


public class SelectTag extends SimpleTagSupport{

	
	private String value;
	private String dictTypeId;
	private String name;
	private String id;
	private String onchange;
	private String height;

	@Override
	public void doTag() throws JspException, IOException {
		super.doTag();
		
		Map<String, String> queryCondition = new HashMap<String, String>();
		queryCondition.put("dictTypeId", this.dictTypeId);
		queryCondition.put("status", 1+"");
		
		StringBuffer result = new StringBuffer();
		result.append("<select data-am-selected='{searchBox: 1,maxHeight: "+this.height+"}' id='"+this.id+"' name='"+this.name+"' ");
		if (!"".equals(this.onchange) && this.onchange != null) {
			result.append("onchange='"+this.onchange+"'");
		}
		result.append(">");
		List<DictEntryBO> entryList = null;
		
		try {
			entryList = FirstQInterfaces.getIDictEntryService().dictEnterList(queryCondition);
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		if ("".equals(this.value) || this.value == null) {
			result.append("<option value=''>--请选择--</option>");
		}
		for (DictEntryBO dictEntryBO : entryList) {
			 if (dictEntryBO.getDictId().equals(this.value)) {
				result.append("<option value='"+dictEntryBO.getDictId()+"' selected = 'selected'>"+dictEntryBO.getDictName()+"</option>");
			}else {
				result.append("<option value='"+dictEntryBO.getDictId()+"'>"+dictEntryBO.getDictName()+"</option>");
			}
		}
		result.append("</select>");
		getJspContext().getOut().println(result.toString());
		
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public void setValue(String value) {
		this.value = value;
	}
	public void setDictTypeId(String dictTypeId) {
		this.dictTypeId = dictTypeId;
	}
	
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
	public void setHeight(String height) {
		this.height = height;
	}
}
