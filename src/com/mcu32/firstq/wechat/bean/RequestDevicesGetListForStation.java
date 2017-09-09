package com.mcu32.firstq.wechat.bean;
/**
 * @author Administrator
 */
public class RequestDevicesGetListForStation extends InterFaceRequestBaseObj{
	private String station_id;
	private String inspection_id;
	public String getStation_id() {
		return station_id;
	}
	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}
	public String getInspection_id() {
		return inspection_id;
	}
	public void setInspection_id(String inspection_id) {
		this.inspection_id = inspection_id;
	}
	
}
