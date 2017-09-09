package com.mcu32.firstq.wechat.bean;

public class RequestPhotoWechatUpload extends InterFaceRequestBaseObj {
	public String wechattoken;
	public String media_id;
	public String filename;
	
	public String getWechattoken() {
		return wechattoken;
	}
	public void setWechattoken(String wechattoken) {
		this.wechattoken = wechattoken;
	}
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	
}
