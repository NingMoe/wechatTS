package com.mcu32.firstq.wechat.bean;

import java.util.Date;
import java.util.List;

/**
 * 单体检测记录
 * @author gaokeming
 *
 */
public class BatteryCheckRecord {
	
	private String recordId;//每轮检测记录Id
	private String recordType;//记录类型(发电、放电)
	private String dischargeRecordId;//放电记录Id
	private String batteryGroupId;//电池组Id
	private List<BatteryCheckRecordDetail> detailList;//单体电池集合
	private int checkTime;//检测轮次
	private List<SoundInfo> soundList; //声音列表
	private String terminalVoltage;//端电压
	private Date finishTime;//完成时间
	private String operator;//操作人
	private String operatorId;//操作人
	private String status;//状态：未检测、检测中、完成
	
	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getDischargeRecordId() {
		return dischargeRecordId;
	}

	public void setDischargeRecordId(String dischargeRecordId) {
		this.dischargeRecordId = dischargeRecordId;
	}

	public String getBatteryGroupId() {
		return batteryGroupId;
	}

	public void setBatteryGroupId(String batteryGroupId) {
		this.batteryGroupId = batteryGroupId;
	}

	public List<BatteryCheckRecordDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<BatteryCheckRecordDetail> detailList) {
		this.detailList = detailList;
	}

	public int getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(int checkTime) {
		this.checkTime = checkTime;
	}

	public List<SoundInfo> getSoundList() {
		return soundList;
	}

	public void setSoundList(List<SoundInfo> soundList) {
		this.soundList = soundList;
	}

	public String getTerminalVoltage() {
		return terminalVoltage;
	}

	public void setTerminalVoltage(String terminalVoltage) {
		this.terminalVoltage = terminalVoltage;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
