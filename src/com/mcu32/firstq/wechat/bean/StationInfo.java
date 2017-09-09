package com.mcu32.firstq.wechat.bean;

import java.util.Date;
import java.util.List;

public class StationInfo {
	private String stationId;//
	private String stationName;//
	private String stationDH;//
	private String stationNo;//
	private String provinceId;//
	private String cityId;//
	private String countyId;//
	private String stationType;//
	private String pullingLevel;//
	private String batteryNum;//
	private String loadCurrent;//
	private String stationAddress;//
	private String longitude;//
	private String latitude;//
	private String checkStatus;//
	private Date checkTime;//
	private Double distance;
	private String roomId;
	private String towerId;
	private String thumbLocation;
	private String photoStatus;
	private String photoLocalId;
	private String fileLocation;
	private String createUser;
	private String createUserId;
	private Date createDate;
	private String lastUser;
	private String lastUserId;
	private Date lastDate;
	private String regionalManager;
	private String managerPhone;
	private String mobileOperator;
	private String haveBattery;
	private String longBack;
	private String stationCompleteness;
	private List<PhotoInfo> photoList;
	
	private TowerInfo towerInfo;
	
	private RoomInfo roomInfo;
	
	private String proxyCompany;//代维公司
	private String proxyPerson;//代维人员
	private String proxyPhone;//代维联系方式
	private String towerNo;
	private String towerControlNo;
	
	public String getTowerNo() {
		return towerNo;
	}
	public void setTowerNo(String towerNo) {
		this.towerNo = towerNo;
	}
	public String getTowerControlNo() {
		return towerControlNo;
	}
	public void setTowerControlNo(String towerControlNo) {
		this.towerControlNo = towerControlNo;
	}
	public String getProxyCompany() {
		return proxyCompany;
	}
	public void setProxyCompany(String proxyCompany) {
		this.proxyCompany = proxyCompany;
	}
	public String getProxyPerson() {
		return proxyPerson;
	}
	public void setProxyPerson(String proxyPerson) {
		this.proxyPerson = proxyPerson;
	}
	public String getProxyPhone() {
		return proxyPhone;
	}
	public void setProxyPhone(String proxyPhone) {
		this.proxyPhone = proxyPhone;
	}
	public String getStationCompleteness() {
		return stationCompleteness;
	}
	public void setStationCompleteness(String stationCompleteness) {
		this.stationCompleteness = stationCompleteness;
	}
	public String getLongBack() {
		return longBack;
	}
	public void setLongBack(String longBack) {
		this.longBack = longBack;
	}
	public String getRegionalManager() {
		return regionalManager;
	}
	public void setRegionalManager(String regionalManager) {
		this.regionalManager = regionalManager;
	}
	public String getManagerPhone() {
		return managerPhone;
	}
	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}
	public String getMobileOperator() {
		return mobileOperator;
	}
	public void setMobileOperator(String mobileOperator) {
		this.mobileOperator = mobileOperator;
	}
	public String getHaveBattery() {
		return haveBattery;
	}
	public void setHaveBattery(String haveBattery) {
		this.haveBattery = haveBattery;
	}
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getLastUser() {
		return lastUser;
	}
	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}
	public String getLastUserId() {
		return lastUserId;
	}
	public void setLastUserId(String lastUserId) {
		this.lastUserId = lastUserId;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
	public String getStationDH() {
		return stationDH;
	}
	public void setStationDH(String stationDH) {
		this.stationDH = stationDH;
	}
	public String getStationNo() {
		return stationNo;
	}
	public void setStationNo(String stationNo) {
		this.stationNo = stationNo;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCountyId() {
		return countyId;
	}
	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}
	public String getStationType() {
		return stationType;
	}
	public void setStationType(String stationType) {
		this.stationType = stationType;
	}
	public String getPullingLevel() {
		return pullingLevel;
	}
	public void setPullingLevel(String pullingLevel) {
		this.pullingLevel = pullingLevel;
	}
	public String getBatteryNum() {
		return batteryNum;
	}
	public void setBatteryNum(String batteryNum) {
		this.batteryNum = batteryNum;
	}
	public String getLoadCurrent() {
		return loadCurrent;
	}
	public void setLoadCurrent(String loadCurrent) {
		this.loadCurrent = loadCurrent;
	}
	public String getStationAddress() {
		return stationAddress;
	}
	public void setStationAddress(String stationAddress) {
		this.stationAddress = stationAddress;
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
	public String getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getTowerId() {
		return towerId;
	}
	public void setTowerId(String towerId) {
		this.towerId = towerId;
	}
	public TowerInfo getTowerInfo() {
		return towerInfo;
	}
	public void setTowerInfo(TowerInfo towerInfo) {
		this.towerInfo = towerInfo;
	}
	public RoomInfo getRoomInfo() {
		return roomInfo;
	}
	public void setRoomInfo(RoomInfo roomInfo) {
		this.roomInfo = roomInfo;
	}
	public List<PhotoInfo> getPhotoList() {
		return photoList;
	}
	public void setPhotoList(List<PhotoInfo> photoList) {
		this.photoList = photoList;
	}
	public String getFileLocation() {
		return fileLocation;
	}
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	public String getThumbLocation() {
		return thumbLocation;
	}
	public void setThumbLocation(String thumbLocation) {
		this.thumbLocation = thumbLocation;
	}
	public String getPhotoStatus() {
		return photoStatus;
	}
	public void setPhotoStatus(String photoStatus) {
		this.photoStatus = photoStatus;
	}
	public String getPhotoLocalId() {
		return photoLocalId;
	}
	public void setPhotoLocalId(String photoLocalId) {
		this.photoLocalId = photoLocalId;
	}
	

	
}
