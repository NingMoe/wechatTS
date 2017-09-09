package com.mcu32.firstq.wechat.bean;

public class SelectEntity {
	private String id;
	private String name;
	private String nodeName;
	private String calculateResult;
	private String batteryBrand;
	
	public String getBatteryBrand() {
		return batteryBrand;
	}
	public void setBatteryBrand(String batteryBrand) {
		this.batteryBrand = batteryBrand;
	}
	public String getCalculateResult() {
		return calculateResult;
	}
	public void setCalculateResult(String calculateResult) {
		this.calculateResult = calculateResult;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
