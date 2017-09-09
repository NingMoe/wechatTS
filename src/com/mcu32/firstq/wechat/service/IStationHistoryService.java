package com.mcu32.firstq.wechat.service;

import com.mcu32.firstq.common.bean.bo.StationHistoryBO;
import com.mcu32.firstq.wechat.exception.FirstQException;

public interface IStationHistoryService {

	public boolean saveStationHistory(Object old, Object now, StationHistoryBO stationHistoryBO) throws FirstQException;
	
}
