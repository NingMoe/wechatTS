package com.mcu32.firstq.wechat.bean;

import java.util.Date;
import java.util.List;

/**
 * 放电记录
 * @author gaokeming
 *
 */
public class DischargeRecord {

	private String recordId;//记录Id
	private String stationId;//基站Id
	private String temp;//温度
	private String dischargeType;//放电方式
	private String dischargeCurrent;//放电电流
	private String startVoltage;//放电开始电压
	private Date startVoltageTime;//放电开始电压时间
	private List<PhotoInfo> startVoltagePhotoList;//开始放电电压照片
	private String endVoltage;//放电结束电压
	private Date endVoltageTime;//放电结束电压时间
	private List<PhotoInfo> endVoltagePhotoList;//放电结束电压照片
	private String step;//步骤
	private String lastOperator;//最后一次操作人
	private String lastOperatorId;
	private Date operatTime;//操作时间
	private String status;//状态：未开始、检测中、完成、紧急终止
	private String remark;//备注
	private String batteryVoltage;//单体电压
	private List<BatteryCheckRecord> checkRecordList;//每轮检测记录
	private String inspectionId;//巡检ID
	private Date finishTime;

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getDischargeType() {
		return dischargeType;
	}

	public void setDischargeType(String dischargeType) {
		this.dischargeType = dischargeType;
	}

	public String getDischargeCurrent() {
		return dischargeCurrent;
	}

	public void setDischargeCurrent(String dischargeCurrent) {
		this.dischargeCurrent = dischargeCurrent;
	}

	public String getStartVoltage() {
		return startVoltage;
	}

	public void setStartVoltage(String startVoltage) {
		this.startVoltage = startVoltage;
	}

	public Date getStartVoltageTime() {
		return startVoltageTime;
	}

	public void setStartVoltageTime(Date startVoltageTime) {
		this.startVoltageTime = startVoltageTime;
	}

	public List<PhotoInfo> getStartVoltagePhotoList() {
		return startVoltagePhotoList;
	}

	public void setStartVoltagePhotoList(List<PhotoInfo> startVoltagePhotoList) {
		this.startVoltagePhotoList = startVoltagePhotoList;
	}

	public String getEndVoltage() {
		return endVoltage;
	}

	public void setEndVoltage(String endVoltage) {
		this.endVoltage = endVoltage;
	}

	public Date getEndVoltageTime() {
		return endVoltageTime;
	}

	public void setEndVoltageTime(Date endVoltageTime) {
		this.endVoltageTime = endVoltageTime;
	}

	public List<PhotoInfo> getEndVoltagePhotoList() {
		return endVoltagePhotoList;
	}

	public void setEndVoltagePhotoList(List<PhotoInfo> endVoltagePhotoList) {
		this.endVoltagePhotoList = endVoltagePhotoList;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getLastOperator() {
		return lastOperator;
	}

	public void setLastOperator(String lastOperator) {
		this.lastOperator = lastOperator;
	}

	public String getLastOperatorId() {
		return lastOperatorId;
	}

	public void setLastOperatorId(String lastOperatorId) {
		this.lastOperatorId = lastOperatorId;
	}

	public Date getOperatTime() {
		return operatTime;
	}

	public void setOperatTime(Date operatTime) {
		this.operatTime = operatTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBatteryVoltage() {
		return batteryVoltage;
	}

	public void setBatteryVoltage(String batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
	}

	public List<BatteryCheckRecord> getCheckRecordList() {
		return checkRecordList;
	}

	public void setCheckRecordList(List<BatteryCheckRecord> checkRecordList) {
		this.checkRecordList = checkRecordList;
	}

	public String getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
	}
	
}
