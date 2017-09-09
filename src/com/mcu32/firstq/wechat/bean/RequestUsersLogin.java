package com.mcu32.firstq.wechat.bean;

public class RequestUsersLogin  extends InterFaceRequestBaseObj{
	private String imei;//手机串号
	private String phone_no;//手机号码
	private String wechat;//微信号唯一标识（可以是 服务号或企业号）
	private String password;//动态密码（用于APP端进行登录校验）
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phoneNo) {
		phone_no = phoneNo;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	
}
