package com.mcu32.firstq.wechat.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mcu32.firstq.wechat.service.IDoorSystemSendService;
import com.mcu32.firstq.wechat.service.IManageBatteryRecordSendService;

@Component
public class TaskEmail {
	@Autowired private IManageBatteryRecordSendService bmrsService;
	@Autowired private IDoorSystemSendService doorSystemSendService;
	public TaskEmail() {}
   /**
    * 每周五，周一17:30点推送本周的周报
    */
	@Scheduled(cron = "0 30 17 ? * FRI,MON")
	public void ManageBatterySendThisWeekCountyEmail() {
		//bmrsService.sendThisWeekCountyEmail();//蓄电池整治全国
		//bmrsService.sendThisWeekCityEmail();//蓄电池整治全国
		List<String> userList=new ArrayList<String>();
		userList.add("liujie@chinatowercom.cn");//全国时给此邮箱发送
		userList.add("wangshixiu@mcu32.com");
		userList.add("chenlonglong@mcu32.com");
		userList.add("tianfeng@mcu32.com");
		userList.add("wangfang@mcu32.com");
		doorSystemSendService.sendThisWeekCountyEmail(userList);//门禁全国
	}
	

}
