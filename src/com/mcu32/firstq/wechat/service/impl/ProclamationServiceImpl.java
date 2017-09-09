package com.mcu32.firstq.wechat.service.impl;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcu32.firstq.common.bean.bo.ITBill;
import com.mcu32.firstq.common.util.DateCommonUtils;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.common.util.HttpClientUtil;
import com.mcu32.firstq.wechat.service.IProclamationService;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.ShortUrlUtil;
import com.mcu32.firstq.wechat.util.ToolUtil;
import com.mcu32.firstq.wechat.util.date.DateUtil;
@Service
public class ProclamationServiceImpl implements IProclamationService {
	@Override
	public String sendProclamation() throws Exception {
		List<Element> list=FirstqTool.getProclamationChatRoom();
		//WechatRequestUtils weRequest = new WechatRequestUtils();
		if(null!=list)
		for(Element e:list){
			String sendmsgStr=e.attributeValue("sendmsg");
			boolean sendmsg = Boolean.parseBoolean(sendmsgStr);
			if(!sendmsg) continue;
			String chatroomStr=e.attributeValue("chatroom");
			String name=e.attributeValue("name");
			String s=getHoleMsg(chatroomStr);
			String chatroomUrlStr=chatroomStr;
			if (StringUtils.isNotEmpty(chatroomStr)) chatroomUrlStr=URLEncoder.encode(chatroomStr,"UTF-8");
			String url=FirstqTool.getToPageURL(URLEncoder.encode("/main/toPage?toPageUrl=question/questionMain?groupName="+chatroomUrlStr+"_"+URLEncoder.encode("自动推送","UTF-8"),"UTF-8"));
			
			s+="\n问题or建议？猛戳这里"+url+"，就怕你说得不够多！";
			//weRequest.sendMsgToSbd(s, chatroomStr,"room");
			JSONArray j=FirstqTool.sendFourHighToWechatGroup(s, name);
			System.out.println(j.toJSONString());
			System.out.println(chatroomUrlStr);
		}
		return "";
	}
	/**
	 * 全部字符串
	 */
	@Override
	public String getHoleMsg(String groupName){
		String p=getProclamation(groupName);
		String hight=getFourHigh(groupName);
		return "[公告栏]\n\n"+hight+"\n"+p+"\n";
	}
	/**
	 * ???公告栏
	 */
	private String getProclamation(String groupName){
		int i=1;
		String msg="";
		if(StringUtils.isNotEmpty(groupName)){
			try {
				String j = HttpClientUtil.getResultByReqeustMethod(ToolUtil.getAppConfig("WechatDomain")+"/"+ToolUtil.getAppConfig("WechatProjectName")+"/proclamation/getListTopThree.json", "groupName="+groupName, "utf-8","POST");
				LogUtil.info("请求前三条\n"+j);
				JSONObject json=JSON.parseObject(j);
				JSONArray jarr=json.getJSONArray("proclamationList");
				
				for(Object o:jarr){
					JSONObject jo=(JSONObject) JSON.toJSON(o);
					String proclamationTheme=jo.getString("proclamationTheme");
					msg+=(i++)+"."+proclamationTheme+"\n";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if("".equals(msg)) {
			msg="暂无公告";
		}else{
			try {
				String url=FirstqTool.getToPageURL(URLEncoder.encode("/main/toPage?toPageUrl=proclamation/getProclamationList?groupName="+URLEncoder.encode(groupName,"UTF-8"),"UTF-8"));
				msg+="详情点击"+url;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "《手动》\n"+ msg;
	}
	/**.json
	 * 四高
	 */
	private String getFourHigh(String groupName){
		//?groupName="+groupName
		String now=DateCommonUtils.DateToString(new Date(), "yyyy-MM-dd");
		String weekMsg="";
		String dayMsg="";
		if(StringUtils.isNotEmpty(groupName)){
			List<Map<String,String>> list=FirstqTool.getProclamationByChatRoom(groupName);
			try {
				
				for(Map<String,String> map:list){
					String urlKey=map.get("url");
					String province=map.get("province");
					String city=map.get("city");
					String rate=map.get("rate");
					
					String countP="impTime="+now+"&province="+province+"&city="+city+"&urlKey="+urlKey;
					
					String j = HttpClientUtil.getResultByReqeustMethod(ToolUtil.getAppConfig("WechatDomain")+"/"+ToolUtil.getAppConfig("WechatProjectName")+"/fourHight/selectSolveCount.json", countP, "utf-8","GET");
					JSONObject json=JSON.parseObject(j);
					
					String paramsStr="impTime:"+now+",province:"+province+",city:"+city;
					String url=FirstqTool.getToPageURL(URLEncoder.encode("/main/toPage?toPageUrl="+urlKey+"?params="+URLEncoder.encode(paramsStr, "UTF-8"), "UTF-8"));
					String content=map.get("content").replace("{url}", url)+"\n";
					JSONArray info =json.getJSONArray(urlKey);
					if(null!=info)
					for(int i=0;i<info.size();i++){
						String num=info.getString(i);
						content=content.replace("{"+i+"}", num);
					}
					if("week".equals(rate)){
						weekMsg+=content;
					}else if("day".equals(rate)){
						dayMsg+=content;
					}
					//weekMsg+=content;
				}
			} catch (Exception e) {
				LogUtil.error(e);
			}
		}
		if(StringUtils.isNotEmpty(dayMsg)) dayMsg="【请于"+now+"前处理以下问题】\n"+dayMsg;
		
		String endTime=DateUtil.getThisWeekSunDay(new Date(), "yyyy-MM-dd");
		if(StringUtils.isNotEmpty(weekMsg)) weekMsg="【请于"+endTime+"前处理以下问题】\n"+weekMsg;
		return "《自动》\n"+dayMsg+weekMsg;
	}
}
