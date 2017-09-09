package com.mcu32.firstq.wechat.bean;

import org.apache.commons.lang.StringUtils;

public class UserInfo {

	public UserInfo(){}
	
	public UserInfo(String... s){
		token=s[0];
		openId=s[1];
		lo=s[2];
		la=s[3];
	}
	
	private String userId;//
	private String userName;//
	private String roleName;//
	private String phoneNo;//
	private String imei;//
	private String weChatEnterprise;//
	private String weChatService;//
	private String weChat;//
	private String createTime;//
	private String sumScore;//
	private String pwd;//
	private String lastClickDate;//
	private String deptId;//
	private String provinceId;//
	private String cityId;//
	private String countyId;//
	
	private String role;
	
	private String token;
	private String openId;
	private String lo;
	private String la;
	private String addr;
	private String jobId;
	
	private String toPageUrl;
	private String alertMsg;
	
	private String screenWidth;
	
	public String getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(String screenWidth) {
		this.screenWidth = screenWidth;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getWeChatEnterprise() {
		return weChatEnterprise;
	}
	public void setWeChatEnterprise(String weChatEnterprise) {
		this.weChatEnterprise = weChatEnterprise;
	}
	public String getWeChatService() {
		return weChatService;
	}
	public void setWeChatService(String weChatService) {
		this.weChatService = weChatService;
	}
	public String getWeChat() {
		return weChat;
	}
	public void setWeChat(String weChat) {
		this.weChat = weChat;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getSumScore() {
		return sumScore;
	}
	public void setSumScore(String sumScore) {
		this.sumScore = sumScore;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getLastClickDate() {
		return lastClickDate;
	}
	public void setLastClickDate(String lastClickDate) {
		this.lastClickDate = lastClickDate;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
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
	public String getCountyIdDesc() {
		String countyName = "";
		if(countyId != null && !"".equals(countyId)) {
			if("a".equals(countyId)) {
				countyName = "唐海县";
			} else if("b".equals(countyId)) {
				countyName = "滦 县";
			} else if("c".equals(countyId)) {
				countyName = "滦南县";
			} else if("d".equals(countyId)) {
				countyName = "玉田县";
			} else if("e".equals(countyId)) {
				countyName = "乐亭县";
			} else if("f".equals(countyId)) {
				countyName = "迁西县";
			} else if("g".equals(countyId)) {
				countyName = "丰润县";
			} else if("h".equals(countyId)) {
				countyName = "路南区";
			} else if("i".equals(countyId)) {
				countyName = "路北区";
			} else if("j".equals(countyId)) {
				countyName = "古冶区";
			} else if("k".equals(countyId)) {
				countyName = "开平区";
			} else if("l".equals(countyId)) {
				countyName = "新区";
			} else if("m".equals(countyId)) {
				countyName = "遵化市";
			} else if("n".equals(countyId)) {
				countyName = "丰南市";
			} else if("o".equals(countyId)) {
				countyName = "迁安市";
			}
		}
		return countyName;
	}
	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
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
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public void updateNotNull(String... s){
		if(StringUtils.isNotEmpty(s[0]))
			token=s[0];
		if(StringUtils.isNotEmpty(s[1]))
			openId=s[1];
		if(StringUtils.isNotEmpty(s[2]))
			lo=s[2];
		if(StringUtils.isNotEmpty(s[3]))
			la=s[3];
	}
	public String getToPageUrl() {
		return toPageUrl;
	}
	public void setToPageUrl(String toPageUrl) {
		this.toPageUrl = toPageUrl;
	}
	public String getAlertMsg() {
		return alertMsg;
	}
	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
