package com.mcu32.firstq.wechat.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.cglib.beans.BeanCopier;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mcu32.firstq.common.bean.bo.InspectionRoomTowerBO;
import com.mcu32.firstq.wechat.util.WebContentConstants;

public class InspectionRoomTowerInfo {
	private String sinkId;//
	private String sink;//机房基础是否沉降、倾斜
	
	private String damagedId;//
	private String damaged;//机房有无破损

	private String leakId;//
	private String leak;//机房有无漏水情况

	private String lockInfoId;//
	private String lockInfo;//机房门锁完好情况
	

	private String verticalId;//
	private String vertical;//垂直

	private String towerComponentId;//
	private String towerComponent;//塔构件

	private String towerStudId;//
	private String towerStud;//塔脚螺栓

	private String towerBaseId;//塔基
	private String towerBase;//塔基

	private String derrickId;//
	private String derrick;//抱杆情况

	private String towerCableId;//
	private String towerCable;//楼面塔拉线
	
	private int roomExceptionNum;
	private int towerExceptionNum;
	
	public InspectionRoomTowerInfo paseInspectionRoomTowerList(List<InspectionRoomTowerBO> exceptionList){
		Map<String,String> map=new HashMap<String,String>();
		map.put("roomExceptionNum", "0");
		map.put("towerExceptionNum", "0");
		
		if(null!=exceptionList)
			for(InspectionRoomTowerBO bo:exceptionList){
				map.put(bo.getCategoryName()+"Id", bo.getRecordId());
				if(!bo.getCheckValue().equals(bo.getDefaultValue())){
					map.put(bo.getCategoryName(), bo.getCheckValue());
					if(WebContentConstants.STATION_ROOM.equals(bo.getCheckType())){
						map.put("roomExceptionNum", Integer.parseInt(map.get("roomExceptionNum"))+1+"");
					}else if(WebContentConstants.STATION_TOWER.equals(bo.getCheckType())){
						map.put("towerExceptionNum", Integer.parseInt(map.get("towerExceptionNum"))+1+"");
					}
				}
			}
		JSONObject j=(JSONObject) JSON.toJSON(map);
		InspectionRoomTowerInfo irti=JSON.toJavaObject(j, InspectionRoomTowerInfo.class);
		BeanCopier.create(InspectionRoomTowerInfo.class, InspectionRoomTowerInfo.class, false).copy(irti, this, null);
		return this;
	}

	public String getSinkId() {
		return sinkId;
	}
	public void setSinkId(String sinkId) {
		this.sinkId = sinkId;
	}
	public String getSink() {
		return sink;
	}
	public void setSink(String sink) {
		this.sink = sink;
	}
	public String getDamagedId() {
		return damagedId;
	}
	public void setDamagedId(String damagedId) {
		this.damagedId = damagedId;
	}
	public String getDamaged() {
		return damaged;
	}
	public void setDamaged(String damaged) {
		this.damaged = damaged;
	}
	public String getLeakId() {
		return leakId;
	}
	public void setLeakId(String leakId) {
		this.leakId = leakId;
	}
	public String getLeak() {
		return leak;
	}
	public void setLeak(String leak) {
		this.leak = leak;
	}
	public String getLockInfoId() {
		return lockInfoId;
	}
	public void setLockInfoId(String lockInfoId) {
		this.lockInfoId = lockInfoId;
	}
	public String getLockInfo() {
		return lockInfo;
	}
	public void setLockInfo(String lockInfo) {
		this.lockInfo = lockInfo;
	}
	public String getVerticalId() {
		return verticalId;
	}
	public void setVerticalId(String verticalId) {
		this.verticalId = verticalId;
	}
	public String getVertical() {
		return vertical;
	}
	public void setVertical(String vertical) {
		this.vertical = vertical;
	}
	public String getTowerComponentId() {
		return towerComponentId;
	}
	public void setTowerComponentId(String towerComponentId) {
		this.towerComponentId = towerComponentId;
	}
	public String getTowerComponent() {
		return towerComponent;
	}
	public void setTowerComponent(String towerComponent) {
		this.towerComponent = towerComponent;
	}
	public String getTowerStudId() {
		return towerStudId;
	}
	public void setTowerStudId(String towerStudId) {
		this.towerStudId = towerStudId;
	}
	public String getTowerStud() {
		return towerStud;
	}
	public void setTowerStud(String towerStud) {
		this.towerStud = towerStud;
	}
	public String getTowerBaseId() {
		return towerBaseId;
	}
	public void setTowerBaseId(String towerBaseId) {
		this.towerBaseId = towerBaseId;
	}
	public String getTowerBase() {
		return towerBase;
	}
	public void setTowerBase(String towerBase) {
		this.towerBase = towerBase;
	}
	public String getDerrickId() {
		return derrickId;
	}
	public void setDerrickId(String derrickId) {
		this.derrickId = derrickId;
	}
	public String getDerrick() {
		return derrick;
	}
	public void setDerrick(String derrick) {
		this.derrick = derrick;
	}
	public String getTowerCableId() {
		return towerCableId;
	}
	public void setTowerCableId(String towerCableId) {
		this.towerCableId = towerCableId;
	}
	public String getTowerCable() {
		return towerCable;
	}
	public void setTowerCable(String towerCable) {
		this.towerCable = towerCable;
	}
	public int getRoomExceptionNum() {
		return roomExceptionNum;
	}
	public void setRoomExceptionNum(int roomExceptionNum) {
		this.roomExceptionNum = roomExceptionNum;
	}
	public int getTowerExceptionNum() {
		return towerExceptionNum;
	}
	public void setTowerExceptionNum(int towerExceptionNum) {
		this.towerExceptionNum = towerExceptionNum;
	}
	
}
