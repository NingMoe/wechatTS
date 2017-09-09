package com.mcu32.firstq.wechat.util.springMVCBinder;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.PropertiesEditor;

public class BaseDoubleEditor extends PropertiesEditor {    
    @Override    
    public void setAsText(String text) throws IllegalArgumentException {    
    	if (StringUtils.isEmpty(text)) {
    		text = "0";    
        }else{
        	text=text.replace(",", "");
        }
        setValue(Double.parseDouble(text));    
    }    
    
    @Override    
    public String getAsText() {    
        return getValue().toString();    
    }    
} 
