package com.mcu32.firstq.wechat.bean;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.mcu32.firstq.wechat.util.LogUtil;

public class RequestParam {
	public RequestParam(){}
	public RequestParam(String token){
		this.token=token;
	}
	private String token;

	private Map<String, Object> rpm =new LinkedHashMap<String,Object>();
	
	public void putParam(String key,String value){
		rpm.put(key, value);
	}
	
	public void putParam(String key,Object obj){
		rpm.put(key, obj);
	}
	
	public String createJsonStr(){
		if(token == null || "".equals(token)){
			LogUtil.error("没有token参数,请检查!");
		}
		StringBuffer sb = new StringBuffer();
		sb.append("{\"token\":\""+token+"\"");
		if(rpm.size() > 0){
			for (Iterator<String> it = rpm.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				Object value = rpm.get(key);
				if (value instanceof String) {
					sb.append(",\"");
					sb.append(key);
					sb.append("\":\"");
					sb.append(value);
					sb.append("\"");
				}else{
					String jsonValue = JSON.toJSONString(value);
					sb.append(",\"");
					sb.append(key);
					sb.append("\":");
					sb.append(jsonValue);
				}
			}
		}
		sb.append("}");
		
		return sb.toString();
	}
	public void clearParamMap(){
		rpm.clear();
	}
	//////
	public void setToken(String token) {
		this.token = token;
	}
	public String getToken() {
		return token;
	}
	public Map<String, Object> getRpm() {
		return rpm;
	}
	public void setRpm(Map<String, Object> rpm) {
		this.rpm = rpm;
	}
	public String getStringFromRequestParam(String key){
		Object o=rpm.get(key);
		if(null!=o)
			return o.toString();
		return "";
	}
	
	public static void main(String[] args){
		RequestParam rp = new RequestParam();
		rp.setToken("2222");
		/*rp.putParam("k1", "v1");
		rp.putParam("k2", "v3");
		rp.putParam("k3", "v4");
		String json =rp.createJsonStr();
		System.out.println(json);*/
		RoomInfo r = new RoomInfo();
		r.setRoomId("sss");
		r.setRoomType("ssddd");
		rp.putParam("keykey", r);
		String json =rp.createJsonStr();
		System.out.println(json);
	}
}