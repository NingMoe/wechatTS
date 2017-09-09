package com.mcu32.firstq.wechat.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mcu32.firstq.common.bean.bo.StationHistoryBO;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.exception.FirstQException;
import com.mcu32.firstq.wechat.service.IStationHistoryService;
import com.mcu32.firstq.wechat.util.ToolUtil;

@Service
public class IStationHistoryServiceImpl implements IStationHistoryService {

	@Override
	public boolean saveStationHistory(Object old, Object now, StationHistoryBO stationHistoryBO) throws FirstQException{
		boolean b = true;
		try {
			if(old==null || now==null){
				stationHistoryBO.setRecordId(UUID.randomUUID().toString());
				stationHistoryBO.setOperateProperty("");
			    stationHistoryBO.setOldValue("");
			    stationHistoryBO.setNewValue("");
			    stationHistoryBO.setOperateSrc("wechat");
				b = FirstQInterfaces.getIStationHistoryService().saveStationHistory(stationHistoryBO);
			}else{
				Map<String,List<Object>> map = ToolUtil.equals(now,old);
				Iterator<Entry<String, List<Object>>> it = map.entrySet().iterator();
				while (it.hasNext()) {
				    Entry<String, List<Object>> entry = it.next();
				    String key = (String)entry.getKey();
				    List<Object> value = entry.getValue();
				    stationHistoryBO.setRecordId(UUID.randomUUID().toString());
				    stationHistoryBO.setOperateProperty(key);
				    stationHistoryBO.setOldValue(value.get(1).toString());
				    stationHistoryBO.setNewValue(value.get(0).toString());
				    stationHistoryBO.setOperateSrc("wechat");
					b = FirstQInterfaces.getIStationHistoryService().saveStationHistory(stationHistoryBO);
			    }
			}
		} catch (com.mcu32.firstq.common.exception.FirstQException e) {
			e.printStackTrace();
		}
		return b;
	}
}
