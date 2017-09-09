package com.mcu32.firstq.wechat.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.mcu32.firstq.common.util.HttpClientUtil;
import com.mcu32.firstq.wechat.util.ShortUrlUtil;
import com.mcu32.firstq.wechat.util.ToolUtil;
import com.mcu32.firstq.wechat.util.date.DateUtil;


public class TemplateMessages{
	
	public static String send(String openId,Map<String, String> dataMap){
		String jsonText="{\"touser\":\""+openId+"\",\"template_id\":\"Io7zd1mGpYx5BuKv-uzv-_qlipvyrumMgHk5xhj9Z-s\","
				+"\"url\":\""+dataMap.get("url")+"\","
				+"\"topcolor\":\"#FF0000\","
				+"\"data\":{\"first\": {\"value\":\""+dataMap.get("first")+"\",\"color\":\"#000\"},"
				+"\"keyword1\": {\"value\":\""+dataMap.get("keyword1")+"\",\"color\":\"#000\"},"
				+"\"keyword2\": {\"value\":\""+dataMap.get("keyword2")+"\",\"color\":\"#000\"},"
				+"\"remark\": {\"value\":\""+dataMap.get("remark")+"\",\"color\":\"#000\"}}}";
		return sendTemplateMessage(jsonText);
	}
	public static String send(List<String> openIds,Map<String, String> dataMap){
		if(null!=openIds && openIds.size()>0)
		for(String openId:openIds){
			send(openId,dataMap);
		}
		return "";
	}
	public static String sendReceiveQuestion(String openId, Map<String, String> dataMap) {
//		{{first.DATA}}提交人：{{keyword1.DATA}}问题主题：{{keyword2.DATA}}提交时间：{{keyword3.DATA}}{{remark.DATA}}
		String jsonText="{\"touser\":\""+openId+"\",\"template_id\":\"ITylGPQHWrKZa_auGVQQosF9ZYcy0YSwsmXsbD-iG0M\","
				+"\"url\":\""+dataMap.get("url")+"\","
				+"\"topcolor\":\"#FF0000\","
				+"\"data\":{\"first\": {\"value\":\""+dataMap.get("first")+"\",\"color\":\"#000\"},"
				+"\"keyword1\": {\"value\":\""+dataMap.get("keyword1")+"\",\"color\":\"#000\"},"
				+"\"keyword2\": {\"value\":\""+dataMap.get("keyword2")+"\",\"color\":\"#000\"},"
				+"\"keyword3\": {\"value\":\""+dataMap.get("keyword3")+"\",\"color\":\"#000\"},"
				+"\"remark\": {\"value\":\""+dataMap.get("remark")+"\",\"color\":\"#000\"}}}";
		
		return sendTemplateMessage(jsonText);
	}
	
	public static String sendDealQuestion(String openId, Map<String, String> dataMap) {
//		OPENTM400144925
//		{{first.DATA}}反馈/投诉内容：{{keyword1.DATA}}处理结果：{{keyword2.DATA}}处理时间：{{keyword3.DATA}}{{remark.DATA}}
		
		String jsonText="{\"touser\":\""+openId+"\",\"template_id\":\"QLvXxTDfBGhPB6pxNr1Tj4pzEVIytucPKLrw1svIZM4\","
				+"\"url\":\""+dataMap.get("url")+"\","
				+"\"topcolor\":\"#FF0000\","
				+"\"data\":{\"first\": {\"value\":\""+dataMap.get("first")+"\",\"color\":\"#000\"},"
				+"\"keyword1\": {\"value\":\""+dataMap.get("keyword1")+"\",\"color\":\"#000\"},"
				+"\"keyword2\": {\"value\":\""+dataMap.get("keyword2")+"\",\"color\":\"#000\"},"
				+"\"keyword3\": {\"value\":\""+dataMap.get("keyword3")+"\",\"color\":\"#000\"},"
				+"\"remark\": {\"value\":\""+dataMap.get("remark")+"\",\"color\":\"#000\"}}}";
		
		return sendTemplateMessage(jsonText);
	}
	
	public static String sendDoorSystem(List<String> openIds,Map<String, String> dataMap){
		if(null!=openIds && openIds.size()>0)
		for(String openId:openIds){
			sendDoorSystem(openId,dataMap);
		}
		return "";
	}
	
	public static String sendDoorSystem(String openId, Map<String, String> dataMap){
		//{{first.DATA}}类型：{{keyword1.DATA}}时间：{{keyword2.DATA}}{{remark.DATA}}
		//G-5W0CW-kEFE3iK0TO1A6PCr4WYxvyf0wGIBiwa2XLY
		
		String jsonText="{\"touser\":\""+openId+"\",\"template_id\":\"G-5W0CW-kEFE3iK0TO1A6PCr4WYxvyf0wGIBiwa2XLY\","
				//+"\"url\":\""+dataMap.get("url")+"\","
				+"\"topcolor\":\"#FF0000\","
				+"\"data\":{\"first\": {\"value\":\""+dataMap.get("first")+"\",\"color\":\"#000\"},"
				+"\"keyword1\": {\"value\":\""+dataMap.get("keyword1")+"\",\"color\":\"#000\"},"
				+"\"keyword2\": {\"value\":\""+dataMap.get("keyword2")+"\",\"color\":\"#000\"},"
				+"\"remark\": {\"value\":\""+dataMap.get("remark")+"\",\"color\":\"#000\"}}}";
		
		return sendTemplateMessage(jsonText);
	}
	public static String systemUpdate(String openId, Map<String, String> dataMap){
		//{{first.DATA}}通知内容：{{keyword1.DATA}}开始时间：{{keyword2.DATA}}结束时间：{{keyword3.DATA}}{{remark.DATA}}
		//c2-lSjX4zSy3eo8QbFmyiorWlA3m2uuezMTEOSVtZ8k
		
		String jsonText="{\"touser\":\""+openId+"\",\"template_id\":\"c2-lSjX4zSy3eo8QbFmyiorWlA3m2uuezMTEOSVtZ8k\","
				//+"\"url\":\""+dataMap.get("url")+"\","
				+"\"topcolor\":\"#FF0000\","
				+"\"data\":{\"first\": {\"value\":\""+dataMap.get("first")+"\",\"color\":\"#000\"},"
				+"\"keyword1\": {\"value\":\""+dataMap.get("keyword1")+"\",\"color\":\"#000\"},"
				+"\"keyword2\": {\"value\":\""+dataMap.get("keyword2")+"\",\"color\":\"#000\"},"
				+"\"keyword3\": {\"value\":\""+dataMap.get("keyword3")+"\",\"color\":\"#000\"},"
				+"\"remark\": {\"value\":\""+dataMap.get("remark")+"\",\"color\":\"#000\"}}}";
		return sendTemplateMessage(jsonText);
		//return sendTemplateMessageTest(jsonText);
		
	}
	public static String systemUpdate(List<String> openIds,Map<String, String> dataMap){
		if(null!=openIds && openIds.size()>0)
		for(String openId:openIds){
			if(StringUtils.isNotEmpty(openId))
				systemUpdate(openId,dataMap);
		}
		return "";
	}
	public static String systemException(String openId, Map<String, String> dataMap){
		//{{first.DATA}}系统名称：{{keyword1.DATA}}发生时间：{{keyword2.DATA}}异常信息：{{keyword3.DATA}}{{remark.DATA}}
		//1ajG1xtYe-eB-Lo11sm8Xd5u2UiSEJPC4G7YZhxsTX0
		String jsonText="";
		String url=dataMap.get("url");
		jsonText="{\"touser\":\""+openId+"\",\"template_id\":\"1ajG1xtYe-eB-Lo11sm8Xd5u2UiSEJPC4G7YZhxsTX0\","
				+  (StringUtils.isNotEmpty(url)?"\"url\":\""+url+"\",":"")
				+"\"topcolor\":\"#FF0000\","
				+"\"data\":{\"first\": {\"value\":\""+dataMap.get("first")+"\",\"color\":\"#000\"},"
				+"\"keyword1\": {\"value\":\""+dataMap.get("keyword1")+"\",\"color\":\"#000\"},"
				+"\"keyword2\": {\"value\":\""+dataMap.get("keyword2")+"\",\"color\":\"#000\"},"
				+"\"keyword3\": {\"value\":\""+dataMap.get("keyword3")+"\",\"color\":\"#000\"},"
				+"\"remark\": {\"value\":\""+dataMap.get("remark")+"\",\"color\":\"#000\"}}}";
		return sendTemplateMessage(jsonText);
		//return sendTemplateMessageTest(jsonText);
	}
	/**
	 * dataMap对应的key:first 标题,keyword1  系统名称,keyword2  发生时间,keyword3  异常信息,remark 备注
	 * @param dataMap
	 * @return
	 */
	public static String systemException(List<String> openIds,Map<String, String> dataMap){
		if(null!=openIds && openIds.size()>0)
		for(String openId:openIds){
			if(StringUtils.isNotEmpty(openId))
				systemException(openId,dataMap);
		}
		return "";
	}
	public static String systemUpdateFinished(String openId, Map<String, String> dataMap){
		//{{first.DATA}}类型：{{keyword1.DATA}}时间：{{keyword2.DATA}}{{remark.DATA}}
		//c2-lSjX4zSy3eo8QbFmyiorWlA3m2uuezMTEOSVtZ8k
		
		String jsonText="{\"touser\":\""+openId+"\",\"template_id\":\"G-5W0CW-kEFE3iK0TO1A6PCr4WYxvyf0wGIBiwa2XLY\","
				//+"\"url\":\""+dataMap.get("url")+"\","
				+"\"topcolor\":\"#FF0000\","
				+"\"data\":{\"first\": {\"value\":\""+dataMap.get("first")+"\",\"color\":\"#000\"},"
				+"\"keyword1\": {\"value\":\""+dataMap.get("keyword1")+"\",\"color\":\"#000\"},"
				+"\"keyword2\": {\"value\":\""+dataMap.get("keyword2")+"\",\"color\":\"#000\"},"
				+"\"remark\": {\"value\":\""+dataMap.get("remark")+"\",\"color\":\"#000\"}}}";
		return sendTemplateMessage(jsonText);
		//return sendTemplateMessageTest(jsonText);
	}
	public static String systemUpdateFinished(List<String> openIds,Map<String, String> dataMap){
		if(null!=openIds && openIds.size()>0)
		for(String openId:openIds){
			if(StringUtils.isNotEmpty(openId))
				systemUpdateFinished(openId,dataMap);
		}
		return "";
	}
	
	private static String appid = ToolUtil.getAppConfig("dyxxAPPID");
	public static String sendTemplateMessage(String content){
		try {
			String access_token = MyTokenAndTicketManager.getToken(appid);
			String urlStr = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
			String info = HttpClientUtil.getResult(urlStr, content, "utf-8");
			System.out.println(info);
			return info;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	public static String sendTemplateMessageTest(String content){
		try {
		String token = ShortUrlUtil.getResult("http://www.vwbl.cn/wechat/getWeiXinMessage/getTokenAndTicket.json","","utf-8");
		String urlStr = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+JSON.parseObject(token).get("token");
			String info = HttpClientUtil.getResult(urlStr, content, "utf-8");
			System.out.println(info);
			return info;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	public static void sendOffLine(String msg,String users){
		Map<String,String> dataMap=new HashMap<String,String>();
		System.out.println(msg);
		dataMap.put("first", "");
		dataMap.put("keyword1", "机器人");
		dataMap.put("keyword2", DateUtil.DateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
		dataMap.put("keyword3", "异常信息如下\\n"+ msg);
		dataMap.put("remark", "");
		
		String [] userarr=users.split(",");
		List<String> openIds=new ArrayList<String>();
		Collections.addAll(openIds, userarr);
		if(null!=openIds && openIds.size()>0)
			TemplateMessages.systemException(openIds,dataMap);
	}
	
	
}
