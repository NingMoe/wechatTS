package com.mcu32.firstq.wechat.bean;

import java.util.Date;

public class AirConditioning extends BaseDevice{
	private String deviceId;
	private String deviceName;
	private String ownedOperator;
	private String deviceType;
	private String stationId;
	private String barCode;
	private String outLose;
	private String remark;
	private Date createTime;
	private String creator;
	private String createId;
	private String airBrand;
    private String airModel;
    private String type;
	private String inStatus;
	private String outStatus;
	private String oldDeviceId;
	
	public String getAirBrand() {
		return airBrand;
	}
	public void setAirBrand(String airBrand) {
		this.airBrand = airBrand;
	}
	public String getAirModel() {
		return airModel;
	}
	public void setAirModel(String airModel) {
		this.airModel = airModel;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateId() {
		return createId;
	}
	public void setCreateId(String createId) {
		this.createId = createId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getOwnedOperator() {
		return ownedOperator;
	}
	public void setOwnedOperator(String ownedOperator) {
		this.ownedOperator = ownedOperator;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
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
	public String getOldDeviceId() {
		return oldDeviceId;
	}
	public void setOldDeviceId(String oldDeviceId) {
		this.oldDeviceId = oldDeviceId;
	}
	public String getOutLose() {
		return outLose;
	}
	public void setOutLose(String outLose) {
		this.outLose = outLose;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
