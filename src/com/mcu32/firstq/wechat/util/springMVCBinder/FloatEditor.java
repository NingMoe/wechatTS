package com.mcu32.firstq.wechat.util.springMVCBinder;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.PropertiesEditor;

public class FloatEditor extends PropertiesEditor {    
    @Override    
    public void setAsText(String text) throws IllegalArgumentException {
    	Float f=null;
    	if (StringUtils.isEmpty(text)) {
    		f = null;    
        }else{
        	f=Float.parseFloat(text.replace(",", ""));
        }
        setValue(f);    
    }    
    
    @Override    
    public String getAsText() {    
        return getValue().toString();    
    }    
} 
