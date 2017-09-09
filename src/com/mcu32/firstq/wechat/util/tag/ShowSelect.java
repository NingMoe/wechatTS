package com.mcu32.firstq.wechat.util.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.mcu32.firstq.common.bean.bo.DictEntryBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.util.LogUtil;

public class ShowSelect extends SimpleTagSupport{

	private String value;
	private String dictTypeId;


	@Override
	public void doTag() throws JspException, IOException {
		super.doTag();
		
		String result = "";
		try {
			DictEntryBO dictEntryBO = FirstQInterfaces.getIDictEntryService().getDictEntry(this.value, this.dictTypeId);
			if (dictEntryBO != null) {
				result = dictEntryBO.getDictName();
			}
		} catch (FirstQException e) {
			LogUtil.error(e.getMessage(), e);
		}
		getJspContext().getOut().println(result);
		
	}
	
	
	public void setValue(String value) {
		this.value = value;
	}
	public void setDictTypeId(String dictTypeId) {
		this.dictTypeId = dictTypeId;
	}
	
}
