package com.mcu32.firstq.wechat.util.springMVCBinder;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.PropertiesEditor;

import com.mcu32.firstq.wechat.util.date.DateUtil;

public class DateEditor extends PropertiesEditor {    
    @Override    
    public void setAsText(String text) throws IllegalArgumentException {
    	Date date=null;
    	if (StringUtils.isEmpty(text)) {
        	date = null;    
        }else{
        	date=DateUtil.StringToDate(text);
        }
        setValue(date);
    }    
    
    @Override    
    public String getAsText() {    
        return getValue().toString();    
    }    
} 
