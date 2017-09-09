package com.mcu32.firstq.wechat.bean;

import java.util.Date;
/**
 * 个人工作记录
 * @author gaokeming
 *
 */
public class IndividualJobRecord {
	
	private String recordId;		//Id
	private String stationId;		//基站Id
	private StationInfo stationInfo;//基站对象
	private String relateRecordId;	//任务记录Id
	private String relateRecordType;//任务类型 日常巡检、油机发电、放电测试
	private Date createDate;		//任务开始时间
	private String creator;			//创建人姓名
	private String createId;		//创建人Id
	private Date finishTime;		//完成时间
	private String status;			//任务状态
	
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
	public StationInfo getStationInfo() {
		return stationInfo;
	}
	public void setStationInfo(StationInfo stationInfo) {
		this.stationInfo = stationInfo;
	}
	public String getRelateRecordId() {
		return relateRecordId;
	}
	public void setRelateRecordId(String relateRecordId) {
		this.relateRecordId = relateRecordId;
	}
	public String getRelateRecordType() {
		return relateRecordType;
	}
	public void setRelateRecordType(String relateRecordType) {
		this.relateRecordType = relateRecordType;
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
	
	
}
