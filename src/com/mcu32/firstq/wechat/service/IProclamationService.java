package com.mcu32.firstq.wechat.service;

public interface IProclamationService  {

	String sendProclamation() throws Exception;
	String getHoleMsg(String groupName);
}
