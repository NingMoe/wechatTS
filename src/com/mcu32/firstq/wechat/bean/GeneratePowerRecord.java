package com.mcu32.firstq.wechat.bean;

import java.util.Date;
import java.util.List;

/**
 * 发电记录
 * @author gaokeming
 *
 */
public class GeneratePowerRecord {
	
	private String recordId;//记录Id
	private String stationId;//基站Id
	private String temp;//温度
	private String oilEnginePower;//油机功率
	private String oilEngineSerial;//油机序号
	private String dischargeCurrent;//放电电流
	private Date powerCutTime;//停电开始时间
	private String powercutVoltage; //停电开始电压
	private List<PhotoInfo> powerCutPhotos;//停电开始时间照片
	private String startVoltage;//发电开始电压
	private Date startVoltageTime;//发电开始电压时间
	private List<PhotoInfo> startVoltagePhotos;//发电开始电压照片
	private String endVoltage;//发电结束电压
	private Date endVoltageTime;//发电结束电压时间
	private List<PhotoInfo> endVoltagePhotos;//发电结束电压照片
	private String step;//步骤
	private String lastOperator;//最后一次操作人
	private String lastOperatorId;//最后一次操作人
	private Date operatTime;//操作时间
	private String status;//状态：未开始、检测中、完成、紧急终止
	private String remark;//备注
	private String batteryVoltage;//单体电压
	private List<BatteryCheckRecord> checkRecordList;//每轮检测记录
	private String detectionStatus;//单体检测状态
	private String inspectionId;//巡检ID
	private Date finishTime;
	private String generatePowerType;
	private Date lastTime;
	private Date stationPowercutTime;
	
	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getInspectionId() {
		return inspectionId;
	}

	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
	}

	public List<PhotoInfo> getPowerCutPhotos() {
		return powerCutPhotos;
	}

	public void setPowerCutPhotos(List<PhotoInfo> powerCutPhotos) {
		this.powerCutPhotos = powerCutPhotos;
	}

	public List<PhotoInfo> getStartVoltagePhotos() {
		return startVoltagePhotos;
	}

	public void setStartVoltagePhotos(List<PhotoInfo> startVoltagePhotos) {
		this.startVoltagePhotos = startVoltagePhotos;
	}

	public List<PhotoInfo> getEndVoltagePhotos() {
		return endVoltagePhotos;
	}

	public void setEndVoltagePhotos(List<PhotoInfo> endVoltagePhotos) {
		this.endVoltagePhotos = endVoltagePhotos;
	}

	public String getDetectionStatus() {
		return detectionStatus;
	}

	public void setDetectionStatus(String detectionStatus) {
		this.detectionStatus = detectionStatus;
	}

	public List<BatteryCheckRecord> getCheckRecordList() {
		return checkRecordList;
	}

	public void setCheckRecordList(List<BatteryCheckRecord> checkRecordList) {
		this.checkRecordList = checkRecordList;
	}

	public String getBatteryVoltage() {
		return batteryVoltage;
	}

	public void setBatteryVoltage(String batteryVoltage) {
		this.batteryVoltage = batteryVoltage;
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

	public String getOilEnginePower() {
		return oilEnginePower;
	}

	public void setOilEnginePower(String oilEnginePower) {
		this.oilEnginePower = oilEnginePower;
	}

	public String getOilEngineSerial() {
		return oilEngineSerial;
	}

	public void setOilEngineSerial(String oilEngineSerial) {
		this.oilEngineSerial = oilEngineSerial;
	}

	public String getDischargeCurrent() {
		return dischargeCurrent;
	}

	public void setDischargeCurrent(String dischargeCurrent) {
		this.dischargeCurrent = dischargeCurrent;
	}

	public Date getPowerCutTime() {
		return powerCutTime;
	}

	public void setPowerCutTime(Date powerCutTime) {
		this.powerCutTime = powerCutTime;
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

	public String getLastOperatorId() {
		return lastOperatorId;
	}

	public void setLastOperatorId(String lastOperatorId) {
		this.lastOperatorId = lastOperatorId;
	}

	public String getPowercutVoltage() {
		return powercutVoltage;
	}

	public void setPowercutVoltage(String powercutVoltage) {
		this.powercutVoltage = powercutVoltage;
	}

	public String getGeneratePowerType() {
		return generatePowerType;
	}

	public void setGeneratePowerType(String generatePowerType) {
		this.generatePowerType = generatePowerType;
	}

	public Date getStationPowercutTime() {
		return stationPowercutTime;
	}

	public void setStationPowercutTime(Date stationPowercutTime) {
		this.stationPowercutTime = stationPowercutTime;
	}
}
