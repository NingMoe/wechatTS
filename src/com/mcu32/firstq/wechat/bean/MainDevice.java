package com.mcu32.firstq.wechat.bean;

public class MainDevice {
	private String device_id;//设备ID
	private String old_device_id;//旧设备ID
	private String device_name;//设备名称
	private String owned_operator;//
	private String station_id;//基站ID
	private String out_lose;//退网或者丢失标记
	private String remark;//备注
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String deviceId) {
		device_id = deviceId;
	}
	public String getOld_device_id() {
		return old_device_id;
	}
	public void setOld_device_id(String oldDeviceId) {
		old_device_id = oldDeviceId;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String deviceName) {
		device_name = deviceName;
	}
	public String getOwned_operator() {
		return owned_operator;
	}
	public void setOwned_operator(String ownedOperator) {
		owned_operator = ownedOperator;
	}
	public String getStation_id() {
		return station_id;
	}
	public void setStation_id(String stationId) {
		station_id = stationId;
	}
	public String getOut_lose() {
		return out_lose;
	}
	public void setOut_lose(String outLose) {
		out_lose = outLose;
	}
	
}
