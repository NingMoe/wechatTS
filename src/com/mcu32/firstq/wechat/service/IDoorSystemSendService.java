package com.mcu32.firstq.wechat.service;

import java.util.List;

import com.mcu32.firstq.common.bean.bo.DoorSystemBO;

public interface IDoorSystemSendService {

	void sendThisWeekCountyEmail(List<String> userList);

	void sendDoorSystemIsFilled();

	void sendDoorSystemNotFill();

	List<String> getEmailToUserByProvince(String province);

	void sendLastFillMsg(List<DoorSystemBO> doorSystemList);

	void sendLastEmile(List<DoorSystemBO> doorSystemList);
}
