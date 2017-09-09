package com.mcu32.firstq.wechat.util.springMVCBinder;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.PropertiesEditor;

public class LongEditor extends PropertiesEditor {    
    @Override    
    public void setAsText(String text) throws IllegalArgumentException {
    	Long l=null;
    	if (StringUtils.isEmpty(text)) {
    		l = null;    
        }else{
        	l = Long.parseLong(text.replace(",", ""));
        }
        setValue(l);  
        
    }    
    
    @Override    
    public String getAsText() {    
        return getValue().toString();    
    }    
} 
