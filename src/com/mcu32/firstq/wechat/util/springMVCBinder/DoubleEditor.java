package com.mcu32.firstq.wechat.util.springMVCBinder;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.PropertiesEditor;

public class DoubleEditor extends PropertiesEditor {    
    @Override    
    public void setAsText(String text) throws IllegalArgumentException {
    	Double d=null;
    	if (StringUtils.isEmpty(text)) {
    		d = null;    
        }else{
        	d=Double.parseDouble(text.replace(",", ""));
        }
        setValue(d);    
    }    
    
    @Override    
    public String getAsText() {    
        return getValue().toString();    
    }    
} 
