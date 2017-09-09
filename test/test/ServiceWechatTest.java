package test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mcu32.firstq.common.bean.bo.DoorSystemBO;
import com.mcu32.firstq.common.bean.bo.KPMaintenanceCostBO;
import com.mcu32.firstq.common.dao.DoorSystemBODao;
import com.mcu32.firstq.common.dao.KPMaintenanceCostMapper;
import com.mcu32.firstq.wechat.service.IDoorSystemSendService;
import com.mcu32.firstq.wechat.service.IKPMaintenanceCostService;
import com.mcu32.firstq.wechat.service.IManageBatteryRecordSendService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring*.xml" })
public class ServiceWechatTest{
	
	@Autowired private IManageBatteryRecordSendService bmrsService;
	
	@Autowired private IDoorSystemSendService doorSystemSendService;
	
	@Autowired private DoorSystemBODao doorDao;
	
	@Autowired KPMaintenanceCostMapper kpMaintenanceCostMapper;
	
	@Autowired IKPMaintenanceCostService maintenanceCostService;
	
	@Test
	public void fri913(){
		bmrsService.sendManageBatteryIsFilled();
	}
	
	@Test
	public void fri16() {
		bmrsService.sendManageBatteryNotFill();
	}
	
	
	@Test
	public void testSendMail(){
		bmrsService.sendThisWeekCountyEmail();
	}
	
	
	@Test
	public void testSendMainCity(){
		bmrsService.sendThisWeekCityEmail();
	}
	@Test
	public void sendLastFillMsg(){
		bmrsService.sendLastFillMsg();
	}
	
	@Test
	public void testDoorSystemSendMail(){
		List<String> userList=new ArrayList<String>();
		userList.add("wangfang@mcu32.com");
		userList.add("wf900404@163.com");
		doorSystemSendService.sendThisWeekCountyEmail(userList);
	}
	@Test
	public void testDoorSystemSendLastMail(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("week", "thisweek");
		List<DoorSystemBO> list=doorDao.selectList(map);
		doorSystemSendService.sendLastEmile(list);
	}
	@Test
	public void testsendDoorSystemIsFilled(){
		doorSystemSendService.sendDoorSystemIsFilled();
	}
	@Test
	public void testsendDoorSystemNotFill(){
		doorSystemSendService.sendDoorSystemNotFill();
	}
	@Test
	public void testdoorSystemList(){
		Map<String,String> map=new HashMap<String,String>();
		map.put("week", "thisweek");
		List<DoorSystemBO> list=doorDao.selectList(map);
		doorSystemSendService.sendLastFillMsg(list);
	}
	
	@Test
	public void testMaintenanceCostList(){
		Map<String,String> map=new HashMap<String,String>();
		//map.put("provinceid", "黑龙江省");
		List<KPMaintenanceCostBO> list=kpMaintenanceCostMapper.sendSelectMonthList(map);
		maintenanceCostService.sendMaintenanceCostIsFilled();
	}
}
