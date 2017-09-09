package com.mcu32.firstq.wechat.bean;
/**
 * @author Administrator
 */
public class RequestBaseStationsCheckin extends InterFaceRequestBaseObj{
	private String station_id;
	private String job_id;
	private String photo_content;
	
	public String getStation_id() {
		return station_id;
	}
	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}
	public String getJob_id() {
		return job_id;
	}
	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}
	public String getPhoto_content() {
		return photo_content;
	}
	public void setPhoto_content(String photo_content) {
		this.photo_content = photo_content;
	}
	

	
}
