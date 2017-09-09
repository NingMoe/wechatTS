package com.mcu32.firstq.wechat.bean;

import java.util.Date;
import java.util.List;

public class StationRecord {
	private String recordId;
	private String stationId;
	private String recordType;
	private String title;
	private String content;
	private Date createTime;
	private String createUserId;
	private String createUserName;
	private List<PhotoInfo> photoList;
	
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Integer getPhotoNum() {
		if(photoList == null){
			return 0;
		}
		return photoList.size();
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
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public List<PhotoInfo> getPhotoList() {
		return photoList;
	}
	public void setPhotoList(List<PhotoInfo> photoList) {
		this.photoList = photoList;
	}
	
}
