package com.mcu32.firstq.wechat.bean;

import java.util.Date;
import java.util.List;

/**
 * 基站设备巡检
 * @author gaokeming
 *
 */
public class DeviceInspectRecord {
	
	private String stationId;
	private String recordId;
	private String inspectionId;
	private String deviceStatus;
	private String deviceId;
	private String barCode;
	private String outInFlag;
	private String longitude;
	private String latitude;
	private Date scanDate;
	private Date submitDate;
	private String remark;
	private String abnormalCode;//异常代码
	private List<PhotoInfo> photoList;
	private String creator;
	private String deviceName;
	private String inspectionType;
	
	public String getInspectionType() {
		return inspectionType;
	}
	public void setInspectionType(String inspectionType) {
		this.inspectionType = inspectionType;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public List<PhotoInfo> getPhotoList() {
		return photoList;
	}
	public void setPhotoList(List<PhotoInfo> photoList) {
		this.photoList = photoList;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getInspectionId() {
		return inspectionId;
	}
	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public String getOutInFlag() {
		return outInFlag;
	}
	public void setOutInFlag(String outInFlag) {
		this.outInFlag = outInFlag;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Date getScanDate() {
		return scanDate;
	}
	public void setScanDate(Date scanDate) {
		this.scanDate = scanDate;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAbnormalCode() {
		return abnormalCode;
	}
	public void setAbnormalCode(String abnormalCode) {
		this.abnormalCode = abnormalCode;
	}
	
}
