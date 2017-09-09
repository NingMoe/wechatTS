package com.mcu32.firstq.wechat.bean;
/**
 * @author Administrator
 * {"token":"","lo":"xxx.xxxx","la":"xx.xxxx"}
 */
public class RequestBaseStationsGetNearList extends InterFaceRequestBaseObj{
	private String la;
	private String lo;
	public String getLo() {
		return lo;
	}
	public void setLo(String lo) {
		this.lo = lo;
	}
	public String getLa() {
		return la;
	}
	public void setLa(String la) {
		this.la = la;
	}
	
}
