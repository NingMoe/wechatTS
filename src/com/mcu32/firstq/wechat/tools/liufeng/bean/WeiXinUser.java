package com.mcu32.firstq.wechat.tools.liufeng.bean;

import java.util.Date;

/**
 * 微信用户信息表
 * @author Administrator
 *
 */
public class WeiXinUser {
	private String subscibe;//用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	private String openId;//用户的标识，对当前公众号唯一 服务号
	private String personId;//用户的标识，对当前公众号唯一 企业号
	private String nickname;//用户的昵称
	private char sex;//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private String city;//用户所在城市
	private String country;//用户所在国家
	private String province;//用户所在省份
	private String language;//用户的语言，简体中文为zh_CN
	private String headimgurl;//用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	private Date subscribe_time;//用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	private String unionid;//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	
	private String longitude;//经度
	private String latitude;//纬度
	private Date locationCreaTime;//经纬度生成时间
	
	//扩展字段，用于向本地后台服务器传递参数
	private String wechat;//后台数据库，对应用户表的唯一标识（企业号：userid,服务号：openid）
	private String user_name;//真实姓名
	private String phoneNumber;//手机号码
	private String deptId;//所属公司
	private String role_name;//角色名称
	
	//扩展字段，用于向后台服务器传递参数，登录获取令牌参数
	private String imei;//
	private String phone_no;
	//private String wechat;本地服务器和后台服务器字段名称一致
	private String password;
	
	private String token;//向后台传递参数的唯一标识
	private String length;//动态密码的长度
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String userName) {
		user_name = userName;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String roleName) {
		role_name = roleName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getSubscibe() {
		return subscibe;
	}
	public void setSubscibe(String subscibe) {
		this.subscibe = subscibe;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getHeadimgurl() {
		return headimgurl;
	}
	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	public Date getSubscribe_time() {
		return subscribe_time;
	}
	public void setSubscribe_time(Date subscribeTime) {
		subscribe_time = subscribeTime;
	}
	public String getUnionid() {
		return unionid;
	}
	public void setUnionid(String unionid) {
		this.unionid = unionid;
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
	public Date getLocationCreaTime() {
		return locationCreaTime;
	}
	public void setLocationCreaTime(Date locationCreaTime) {
		this.locationCreaTime = locationCreaTime;
	}
	
}
