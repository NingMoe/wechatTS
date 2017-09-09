package com.mcu32.firstq.wechat.bean;

import java.util.List;

public class BaseDevice {

	private String deviceId;
	private String deviceType;
	private String deviceTypeName;
	private String inStatus;
	private String outStatus;
	private String remark;
	private String outLose;
	
	private List<PhotoInfo> photoList;
	
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceTypeName() {
		return deviceTypeName;
	}
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	public String getInStatus() {
		return inStatus;
	}
	public void setInStatus(String inStatus) {
		this.inStatus = inStatus;
	}
	public String getOutStatus() {
		return outStatus;
	}
	public void setOutStatus(String outStatus) {
		this.outStatus = outStatus;
	}
	public List<PhotoInfo> getPhotoList() {
		return photoList;
	}
	public void setPhotoList(List<PhotoInfo> photoList) {
		this.photoList = photoList;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOutLose() {
		return outLose;
	}
	public void setOutLose(String outLose) {
		this.outLose = outLose;
	}
	
}
