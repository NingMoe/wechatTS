package com.mcu32.firstq.wechat.service;

import java.util.List;
import java.util.Map;

public interface IManageBatteryRecordSendService {

	void sendThisWeekCountyEmail();

	void sendThisWeekCityEmail();

	void sendManageBatteryNotFill();

	void sendManageBatteryIsFilled();

	boolean needCityTotal(String province);

	List<String> needCityTotalCityArray();

	List<String> getEmailToUserByProvince(String province);

	Map<String, String> selectLeaderByProvinceId(String provinceId);

	void sendLastFillMsg();

	void sendCityLastFillMsg(String privinceid);

	
}
