package com.mcu32.firstq.wechat.bean;

public class RequestUsersRegister extends InterFaceRequestBaseObj {
	private String role_name;//角色名称
	private String user_name;//真实姓名
	private String phone_no;//手机号码
	private String openId;//服务号微信唯一标识
	private String userId;//企业号微信唯一标识
	private String deptId;//部门
	private String province_id;//所在省
	private String city_id;//所在市
	private String county_id;//所在县
	private String regiPwd;//注册密码
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String roleName) {
		role_name = roleName;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phoneNo) {
		phone_no = phoneNo;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getProvince_id() {
		return province_id;
	}
	public void setProvince_id(String provinceId) {
		province_id = provinceId;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String cityId) {
		city_id = cityId;
	}
	public String getCounty_id() {
		return county_id;
	}
	public void setCounty_id(String countyId) {
		county_id = countyId;
	}
	public String getRegiPwd() {
		return regiPwd;
	}
	public void setRegiPwd(String regiPwd) {
		this.regiPwd = regiPwd;
	}
	
}
