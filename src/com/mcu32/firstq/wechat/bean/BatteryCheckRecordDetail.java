package com.mcu32.firstq.wechat.bean;

/**
 * 单体检测记录详情
 * @author gaokeming
 *
 */
public class BatteryCheckRecordDetail {
	
	private String recordId;//检测记录详情Id
	private String batteryCheckRecordId;//单体检测记录
	private Double recordValue;//记录值
	private int sequence;//序号
	private String objectKey;//拼接后的唯一标识
	
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getBatteryCheckRecordId() {
		return batteryCheckRecordId;
	}
	public void setBatteryCheckRecordId(String batteryCheckRecordId) {
		this.batteryCheckRecordId = batteryCheckRecordId;
	}
	public Double getRecordValue() {
		return recordValue;
	}
	public void setRecordValue(Double recordValue) {
		this.recordValue = recordValue;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getObjectKey() {
		return objectKey;
	}
	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}
	
}
