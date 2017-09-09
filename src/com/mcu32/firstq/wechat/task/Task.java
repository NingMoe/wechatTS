package com.mcu32.firstq.wechat.task;

import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcu32.firstq.common.bean.bo.WeatherAlertsRealTimeBO;
import com.mcu32.firstq.common.bean.bo.WeatherAlertsTimerTaskBO;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.service.IDoorSystemSendService;
import com.mcu32.firstq.wechat.service.IKPMaintenanceCostService;
import com.mcu32.firstq.wechat.service.IProclamationService;
import com.mcu32.firstq.wechat.tools.MyTokenAndTicketManager;
import com.mcu32.firstq.wechat.util.ConfigManager;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.MySession;
import com.mcu32.firstq.wechat.util.ShortUrlUtil;
import com.mcu32.firstq.wechat.util.ToolUtil;
import com.mcu32.firstq.wechat.util.date.DateUtil;

@Component
public class Task {
	@Autowired private IDoorSystemSendService doorSystemSendService;
	@Autowired private IKPMaintenanceCostService maintenanceCostService;
	@Autowired private IProclamationService proclamationService;
	public Task() {}
	
	@PostConstruct
	public void projectStartDoSomeThing(){
		String appid = ToolUtil.getAppConfig("dyxxAPPID");
		String appsecret = ToolUtil.getAppConfig("dyxxAPPSECRET");

		// 在生产环境使用
		MyTokenAndTicketManager.init(appid, appsecret);

		MySession.startClearSessionTimer(120);

		LogUtil.info("wechat token and ticket is refresh when the project start....");
		
		//初始化组织机构
		ConfigManager.getInstance();
	}
	
	/**
	 * 周五9点、13点 推送哪里填报完成
	 */
	@Scheduled(cron = "0 0 9,13 ? * FRI")
	public void fri913() {
		//bmrsService.sendManageBatteryIsFilled();
		doorSystemSendService.sendDoorSystemIsFilled();
	}
	
	/**
	 * 每4小时推送哪里没填
	 */
	@Scheduled(cron = "0 0 8,12,16,20 * * SUN,MON,TUE,THU,FRI,SAT")
	public void foreHours() {
		doorSystemSendService.sendDoorSystemNotFill();
	}
	
	/**
	 * 推送天气预警
	 */
	@Scheduled(cron = "0 0 8,12,20 * * ?")
	public void eight() {
		sendWeatherTimerTaskToGroup();
	}
	private void sendWeatherTimerTaskToGroup(){
		LogUtil.info("开始每天定时查询");
		String uniqueId=DateUtil.DateToString(new Date(), "yyyyMMddHHmmssSSS");
		try {
			LogUtil.info("开始查询天气数据，uniqueId是"+uniqueId);
			List<WeatherAlertsTimerTaskBO> list=FirstQInterfaces.getIWeatherAlertsService().getWeatherTimerTaskFromInterFace(uniqueId);
			if(null==list || list.size()==0)return;
			
			int[] wholeCountry={0,0,0,0};
			Map<String,int[]> areaSize= new HashMap<String,int[]>();
			for(int i=0;i<list.size();i++){
				WeatherAlertsTimerTaskBO watt=list.get(i);
				if("高温".equals(watt.getAlertType())|| "大雾".equals(watt.getAlertType()))
					continue;
				
				String weatherId=watt.getPageUrl().replace(".html", "");
				String[] alertInfo=weatherId.split("-");
				String provinceAreaId=alertInfo[0].substring(0, 5);
				String cityAreaId="";
				if(alertInfo[0].length()>=7){
					cityAreaId=alertInfo[0].substring(0, 7);
				}
	    		if("红色".equals(watt.getAlertLevel())){
	    			wholeCountry[0]++;
	    			if(areaSize.containsKey(provinceAreaId)){
	    				areaSize.get(provinceAreaId)[0]++;
	    			}else{
	    				areaSize.put(provinceAreaId, new int[]{1,0,0,0});
	    			}
	    			
	    			if(StringUtils.isNotEmpty(cityAreaId)){
		    			if(areaSize.containsKey(cityAreaId)){
		    				areaSize.get(cityAreaId)[0]++;
		    			}else{
		    				areaSize.put(cityAreaId, new int[]{1,0,0,0});
		    			}
	    			}
	    		}else if("橙色".equals(watt.getAlertLevel())){
	    			wholeCountry[1]++;
	    			if(areaSize.containsKey(provinceAreaId)){
	    				areaSize.get(provinceAreaId)[1]++;
	    			}else{
	    				areaSize.put(provinceAreaId, new int[]{0,1,0,0});
	    			}
	    			
	    			if(StringUtils.isNotEmpty(cityAreaId)){
		    			if(areaSize.containsKey(cityAreaId)){
		    				areaSize.get(cityAreaId)[1]++;
		    			}else{
		    				areaSize.put(cityAreaId, new int[]{0,1,0,0});
		    			}
	    			}
	    		}else if("黄色".equals(watt.getAlertLevel())){
	    			wholeCountry[2]++;
	    			if(areaSize.containsKey(provinceAreaId)){
	    				areaSize.get(provinceAreaId)[2]++;
	    			}else{
	    				areaSize.put(provinceAreaId, new int[]{0,0,1,0});
	    			}
	    			
	    			if(StringUtils.isNotEmpty(cityAreaId)){
		    			if(areaSize.containsKey(cityAreaId)){
		    				areaSize.get(cityAreaId)[2]++;
		    			}else{
		    				areaSize.put(cityAreaId, new int[]{0,0,1,0});
		    			}
	    			}
	    		}else if("蓝色".equals(watt.getAlertLevel())){
	    			wholeCountry[3]++;
	    			if(areaSize.containsKey(provinceAreaId)){
	    				areaSize.get(provinceAreaId)[3]++;
	    			}else{
	    				areaSize.put(provinceAreaId, new int[]{0,0,0,1});
	    			}
	    			
	    			if(StringUtils.isNotEmpty(cityAreaId)){
		    			if(areaSize.containsKey(cityAreaId)){
		    				areaSize.get(cityAreaId)[3]++;
		    			}else{
		    				areaSize.put(cityAreaId, new int[]{0,0,0,1});
		    			}
	    			}
	    		}
	    	}
			areaSize.put("全国", wholeCountry);
			List<String[]> robotlistInfo=FirstqTool.getAllConfigInfoByLevel("天气预警定时群");
			int sendInterval = computeInterval(robotlistInfo.size());//消息发送间隔
			for(String[] robotInfoArr:robotlistInfo){
				String areaId=robotInfoArr[0];
				String chatroom=robotInfoArr[1];
				String robotDomain=robotInfoArr[2];
				String areaName=robotInfoArr[3];
				String robotName=robotInfoArr[4];
				
				int[] areaData=areaSize.get(areaId);
				String areaIdNotAll="全国".equals(areaId)?"":areaId;
				String purl=URLEncoder.encode("/main/toPageNotLocation?toPageUrl=damageWarning/damageWarningTimerTask?para="+uniqueId+"_"+areaIdNotAll, "utf-8");
				String pholeURL="http://"+ToolUtil.getAppConfig("CallBackDomain")+"/"+ToolUtil.getAppConfig("WechatProjectName")+"/main/toRedirectPage?toPage="+purl;
				String shortUrli = ShortUrlUtil.creatShort(pholeURL);
				if(null==shortUrli) shortUrli=pholeURL;
				
				String content="[灾害预警定时提醒]\n截止当前时间"+areaName+"共发布红色预警"+0+"条，橙色预警"+0+"条，详情点击"+shortUrli;
				if(null!=areaData){
					content="[灾害预警定时提醒]\n截止当前时间"+areaName+"共发布红色预警"+areaData[0]+"条，橙色预警"+areaData[1]+"条，详情点击"+shortUrli;
				}
				
				if(StringUtils.isNotEmpty(robotDomain)&&StringUtils.isNotEmpty(chatroom)){
					JSONObject rm=FirstqTool.sendMessageToAppointedWechatGroup(robotDomain,content,chatroom,robotName);
					if(null==rm || rm.isEmpty()){
						LogUtil.info("发送失败，"+robotDomain+","+chatroom);
					}else{
						LogUtil.info("天气预警推送成功，"+robotDomain+","+chatroom);
						if(sendInterval > 0){
							LogUtil.info("--------消息发送暂停"+sendInterval+"毫秒");
							Thread.sleep(sendInterval);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private int computeInterval(int size){
		String sendTime = FirstqTool.getWeatherSendTime();
		int sendInterval = 0;
		if(StringUtils.isNotEmpty(sendTime) && size > 0){
			int time = Integer.parseInt(sendTime);
			sendInterval = time*60000/size;
		}
		return sendInterval;
	}
	/**
	 * 每5分钟查询是否有预警
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void checkWeatherAlerts() {
		System.out.println("开始5分钟实时查询");
		String uniqueId=DateUtil.DateToString(new Date(), "yyyyMMddHHmmssSSS");
		List<WeatherAlertsRealTimeBO> list=FirstQInterfaces.getIWeatherAlertsService().getWeatherRealTimeFromInterFace(uniqueId);
		if(null==list || list.size()==0)return;
		LogUtil.info("开始处理需要提醒的天气数据");
		StringBuffer widsb=new StringBuffer();
		try {
			String msg="";
			for(WeatherAlertsRealTimeBO wart:list){
				widsb.append(",'"+wart.getWeatherId()+"'");
				
				if("高温".equals(wart.getAlertType())|| "大雾".equals(wart.getAlertType()))
					continue;
				
				String inHoleUrl="http://www.weather.com.cn/alarm/newalarmcontent.shtml?file="+wart.getPageUrl();
        		String shortUrl = ShortUrlUtil.creatShort(inHoleUrl);
        		if(null==shortUrl) shortUrl=inHoleUrl;
        		msg="[灾害预警实时提醒]\n【"+wart.getAlertName()+"】，详情点击"+shortUrl;
        		
        		if( ("红色".equals(wart.getAlertLevel()))){
        			LogUtil.info("有红色预警，给全国发消息");
    				JSONArray jaa=FirstqTool.sendWAToWechatGroup(msg,"全国");
    				LogUtil.info("有红色预警，给全国发消息的结果为："+jaa.toJSONString());
        		}
        		
        		String weatherId=wart.getPageUrl().replace(".html", "");
				String[] alertInfo=weatherId.split("-");
				String provinceId=alertInfo[0].substring(0, 5);
				
				LogUtil.info("遍历提醒的信息为："+wart.getAlertName()+"，向机器人发消息");
				if(alertInfo[0].length()>=7){
					String cityAreaId=alertInfo[0].substring(0, 7);
					JSONArray jaa=FirstqTool.sendWAToWechatGroup(msg,wart.getAlertLevel(),cityAreaId);
					LogUtil.info("遍历提醒，发消息的结果为："+jaa.toJSONString());
				}
				JSONArray jaa=FirstqTool.sendWAToWechatGroup(msg,wart.getAlertLevel(),provinceId);
				LogUtil.info("遍历提醒，发消息的结果为："+jaa.toJSONString());
			}
        	if(widsb.length()>0){
	        	widsb.deleteCharAt(0);
	        	FirstQInterfaces.getIWeatherAlertsService().updateToAltertById(widsb.toString());
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 周五16点 推送哪里没填
	 */
	//@Scheduled(cron = "0 0 16 ? * FRI")
	public void fri16() {
		//bmrsService.sendManageBatteryNotFill();
	}
	/**
	 * 周一9点、16点 推送哪里没填
	 */
	//@Scheduled(cron = "0 0 9,16 ? * MON")
	public void mon916() {
		//bmrsService.sendManageBatteryNotFill();
	}
	/**
	 * 每30分钟检查是否掉线
	 */
//	@Scheduled(cron = "0 0/30 * * * ?")
	public void checkRobotIsLogin() {
		FirstqTool.checkRobotIsLogin();
	}
	/**
	 * 每月16-20号8点、16点 推送代维费用填报情况
	 */
//	@Scheduled(cron = "0 0 8,16 16-20 * ?")
	public void EveryMonth816() {
		maintenanceCostService.sendMaintenanceCostIsFilled();
	}
	//每天8点-20点推送
	//@Scheduled(cron = "0 0 8,12,16,20 * * ?")
	public void sendProclamation(){
		try {
			LogUtil.info("每天定时推送公告栏");
			proclamationService.sendProclamation();
		} catch (Exception e) {
			LogUtil.error(e);
		}
	}
}