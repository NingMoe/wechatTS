package com.mcu32.firstq.wechat.bean;

import java.util.Date;

/**
 * 
 * 类名称: SFUEquipment
 * <p/>  
 * 类描述:FSU设备维护
 * <p/>
 * 创建者: yuwx
 * <p/>
 * 创建时间: 2016年6月15日 上午8:58:02
 * <p/>
 */
public class SFUEquipment extends BaseDevice{
	private String sfuId;
	private String sfuCompany;		//FSU厂家
	private String brandName;		//品牌简称
	private String chargePerson;	//负责人
	private String chargePhone;		//维护人电话
	private String wirelessModleType;//无线模块型号
	private String longitude;		//经度
	private String latitude;		//纬度
	private String signalStrength;	//信号强度
	
	private String distance;		//与物理站址位置距离
	private String outLose;			//退网或者丢失标记
	private String stationId;		//基站id
	private Date createTime;		//创建时间
	private Date lastTime;		//最后修改时间
	
	private String lastUser;
	private String lastUserId;
	private String remark;
	public String getSfuId() {
		return sfuId;
	}
	public void setSfuId(String sfuId) {
		this.sfuId = sfuId;
	}
	public String getSfuCompany() {
		return sfuCompany;
	}
	public void setSfuCompany(String sfuCompany) {
		this.sfuCompany = sfuCompany;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getChargePerson() {
		return chargePerson;
	}
	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}
	public String getChargePhone() {
		return chargePhone;
	}
	public void setChargePhone(String chargePhone) {
		this.chargePhone = chargePhone;
	}
	public String getWirelessModleType() {
		return wirelessModleType;
	}
	public void setWirelessModleType(String wirelessModleType) {
		this.wirelessModleType = wirelessModleType;
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
	public String getSignalStrength() {
		return signalStrength;
	}
	public void setSignalStrength(String signalStrength) {
		this.signalStrength = signalStrength;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getOutLose() {
		return outLose;
	}
	public void setOutLose(String outLose) {
		this.outLose = outLose;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
