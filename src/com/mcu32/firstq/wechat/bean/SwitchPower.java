package com.mcu32.firstq.wechat.bean;

import java.util.Date;


public class SwitchPower extends BaseDevice{
	private String powerId;
	private String stationId;
	private String deviceName;
	private String belongType;
	private int powerModNum;
	private int normalModNum;
	private String ratedPower;
	private Date enterNetDate;
	private Date createDate;
	private String createUser;
	private String createUserId;
	private String powerMaker;
	private String brand;
	private String model;
	private Date lastDate;
	private String lastUser;
	private String lastUserId;
	private String barCode;
	private String outLose;
	private String inStatus;
	private String outStatus;
	private String oldDeviceId;
	private String remark;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOldDeviceId() {
		return oldDeviceId;
	}
	public void setOldDeviceId(String oldDeviceId) {
		this.oldDeviceId = oldDeviceId;
	}
	public String getOutStatus() {
		return outStatus;
	}
	public void setOutStatus(String outStatus) {
		this.outStatus = outStatus;
	}
	public String getInStatus() {
		return inStatus;
	}
	public void setInStatus(String inStatus) {
		this.inStatus = inStatus;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getLastUserId() {
		return lastUserId;
	}
	public void setLastUserId(String lastUserId) {
		this.lastUserId = lastUserId;
	}
	public String getOutLose() {
		return outLose;
	}
	public void setOutLose(String outLose) {
		this.outLose = outLose;
	}
	public String getPowerId() {
		return powerId;
	}
	public void setPowerId(String powerId) {
		this.powerId = powerId;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getBelongType() {
		return belongType;
	}
	public void setBelongType(String belongType) {
		this.belongType = belongType;
	}
	public int getPowerModNum() {
		return powerModNum;
	}
	public void setPowerModNum(int powerModNum) {
		this.powerModNum = powerModNum;
	}
	public int getNormalModNum() {
		return normalModNum;
	}
	public void setNormalModNum(int normalModNum) {
		this.normalModNum = normalModNum;
	}
	public String getRatedPower() {
		return ratedPower;
	}
	public void setRatedPower(String ratedPower) {
		this.ratedPower = ratedPower;
	}
	public Date getEnterNetDate() {
		return enterNetDate;
	}
	public void setEnterNetDate(Date enterNetDate) {
		this.enterNetDate = enterNetDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getPowerMaker() {
		return powerMaker;
	}
	public void setPowerMaker(String powerMaker) {
		this.powerMaker = powerMaker;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	public String getLastUser() {
		return lastUser;
	}
	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
}
