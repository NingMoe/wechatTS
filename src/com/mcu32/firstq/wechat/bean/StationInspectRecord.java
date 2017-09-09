package com.mcu32.firstq.wechat.bean;

import java.util.Date;

/**
 * 基站巡检记录
 * @author gaokeming
 *
 */
public class StationInspectRecord {

	private String inspectionId;
	private String stationId;
	private String jobId;	
	private StationInfo stationInfo;
	private Date createDate;
	private String creator;
	private String createId;
	private Date finishTime;
	private String status;
	
	public String getInspectionId() {
		return inspectionId;
	}
	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public StationInfo getStationInfo() {
		return stationInfo;
	}
	public void setStationInfo(StationInfo stationInfo) {
		this.stationInfo = stationInfo;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
}
