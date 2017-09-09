package com.mcu32.firstq.wechat.service;

import java.util.List;

import com.mcu32.firstq.wechat.bean.StationInfo;
import com.mcu32.firstq.wechat.bean.StationRecord;
import com.mcu32.firstq.wechat.exception.FirstQException;

public interface IStationService {

	public StationInfo saveStationInfo(StationInfo stationInfo) throws FirstQException;
	
	public boolean updateStationInfo(StationInfo stationInfo) throws FirstQException;
	
	public List<StationInfo> getNearbyStationList(String la,String lo) throws FirstQException;
	
	public StationInfo getStationInfo(String stationId) throws FirstQException;
	
	public boolean deleteStationInfo(String stationId) throws FirstQException;

	public List<StationInfo> getStationList(String provinceId, String cityId, String countyId, String stationName, String stationNo, String stationAddress, String status, int currentPage, int pageLength) throws FirstQException;
	
	public StationRecord saveStationRecord(StationRecord stationRecord) throws FirstQException;
	
	public boolean deleteStationRecord(String recordId) throws FirstQException;
	
	public StationRecord getStationRecord(String recordId) throws FirstQException;
	
	public List<StationRecord> getStationRecords(StationRecord stationRecord) throws FirstQException;
}
