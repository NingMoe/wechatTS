package com.mcu32.firstq.wechat.bean;

import java.util.Date;

public class SoundInfo {
	private String fileName;//
	
	private String soundId;//唯一标识
	
	private String mediaId;//录音微信Id
	
	private long durationTime;//录音时长
	
	private String fileLocation;//文件路径
	
	private Date createTime;//创建时间

	private String createUser;//创建用户姓名
	
	private String createUserId;//创建用户Id
	
	private String soundType;//声音类型
	
	private String relatedId;//关联Id
	
	private String stationId;//
	
	private String status;//上传状态

	
	public String getSoundId() {
		return soundId;
	}

	public void setSoundId(String soundId) {
		this.soundId = soundId;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public long getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(long durationTime) {
		this.durationTime = durationTime;
	}

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getSoundType() {
		return soundType;
	}

	public void setSoundType(String soundType) {
		this.soundType = soundType;
	}

	public String getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(String relatedId) {
		this.relatedId = relatedId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getStationId() {
		return stationId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}
	
	
}
