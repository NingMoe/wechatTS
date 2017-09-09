package com.mcu32.firstq.wechat.bean;

public class RoomInfo {
	private String roomId;//
	private String roomType;//
	private String buildingPattern;//
	private String stationId;
	private String roomStructure;
	
	public String getRoomStructure() {
		return roomStructure;
	}
	public void setRoomStructure(String roomStructure) {
		this.roomStructure = roomStructure;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getBuildingPattern() {
		return buildingPattern;
	}
	public void setBuildingPattern(String buildingPattern) {
		this.buildingPattern = buildingPattern;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	
}
