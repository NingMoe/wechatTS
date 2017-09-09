package com.mcu32.firstq.wechat.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mcu32.firstq.common.bean.bo.DoorSystemBO;
import com.mcu32.firstq.common.bean.bo.OrgBO;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.service.IDoorSystemSendService;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.ShortUrlUtil;
import com.mcu32.firstq.wechat.util.ToolUtil;
import com.mcu32.firstq.wechat.util.export.ExportExcel;
import com.mcu32.firstq.wechat.util.mail.SendMailFactory;

@Service
public class DoorSystemSendServiceImpl implements IDoorSystemSendService {
	/**
	 * 全国的周报邮件
	 */
	@Override
	public void sendThisWeekCountyEmail(List<String> userList) {
		
		//发送的内容
		String content="您好：<br>&nbsp;&nbsp;&nbsp;&nbsp;附件为第一象限为您生成的本周门禁项目建设进度填报周报，请查收。<br>";
		
		Map <String,String> qryMap=new HashMap<String,String>();
		qryMap.put("week", "thisweek");
		Map<String,DoorSystemBO> mbProvinceThisWeekMap = FirstQInterfaces.getIDoorSystemService().doorSystemMap(qryMap);
		if(!mbProvinceThisWeekMap.isEmpty()  && mbProvinceThisWeekMap.keySet().size()<31){
			String filledProvinceStr=StringUtils.join(mbProvinceThisWeekMap.keySet(),",");
			content=content+"&nbsp;&nbsp;&nbsp;&nbsp;截止邮件发出时间，共有"+filledProvinceStr+" "+mbProvinceThisWeekMap.keySet().size()+"个省份填报完成，详见附件，请审阅。<br>";
		}
		//抄送给的人
		List<String> ccList=new ArrayList<String>();
		//ccList.add("chenyan@mcu32.com");
		
		//生成excel 文件
		qryMap.put("week", "preweek");
		Map<String,DoorSystemBO> mbProvincePreweekWeekMap = FirstQInterfaces.getIDoorSystemService().doorSystemMap(qryMap);
		
		Set<String> preweekWeekProvinceSet=new HashSet<String>(mbProvincePreweekWeekMap.keySet());
		Set<String> thisweekWeekProvinceSet=new HashSet<String>(mbProvinceThisWeekMap.keySet());
		preweekWeekProvinceSet.removeAll(thisweekWeekProvinceSet);
		String notFillProvinceStr=StringUtils.join(preweekWeekProvinceSet,",");//剩下的是本周没有填报，上周填报了的数据
		mbProvincePreweekWeekMap.putAll(mbProvinceThisWeekMap);//本周的数据覆盖上周的数据
		
		List<DoorSystemBO> mbAllList=new ArrayList<DoorSystemBO>(mbProvincePreweekWeekMap.values());
		List<String> fileList=new ArrayList<String>();
		String excelPath = prepareDataAndCreateExcel(mbAllList,notFillProvinceStr,"doorsystemreport.xlsx","全国");
		fileList.add(excelPath);
		
		SendMailFactory.sendMailContent(content,"本周门禁项目建设进度统计周报",userList,ccList,fileList);
	}
	/**
	 * 全部填完之后发送的周报邮件
	 */
	@Override
	public void sendLastEmile(List<DoorSystemBO> doorSystemList) {
		//发送的内容
				String content="您好：<br>&nbsp;&nbsp;&nbsp;&nbsp;附件为第一象限为您生成的本周门禁项目建设进度填报周报，请查收。<br>";
				content=content+"&nbsp;&nbsp;&nbsp;&nbsp;截止邮件发出时间，各省均已填报完成，详见附件，请审阅。<br>";
				//发送给的人
				List<String> toUserList=getEmailToUserByProvince("全国");
				//抄送给的人
				List<String> ccList=new ArrayList<String>();
				//ccList.add("chenyan@mcu32.com");
				List<String> fileList=new ArrayList<String>();
				String excelPath = prepareDataAndCreateExcel(doorSystemList,null,"doorsystemreport.xlsx","全国");
				fileList.add(excelPath);
				SendMailFactory.sendMailContent(content,"本周门禁项目建设进度统计周报",toUserList,ccList,fileList);
		
	}
	/**
	 * 生成Excel
	 */
	public static String prepareDataAndCreateExcel(List<DoorSystemBO> doorSystemList,String colorRowStrings, String fileName,String provinceid) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> date = new ArrayList<Map<String, Object>>();
		if (doorSystemList != null) {
			for (int i = 0; i < doorSystemList.size(); i++) {// 开始填充list
				DoorSystemBO mbro=doorSystemList.get(i);
				
				@SuppressWarnings("unchecked")
				Map<String,Object> m=JSON.parseObject(JSON.toJSONString(mbro,SerializerFeature.WriteDateUseDateFormat), Map.class);
				
				m.put("sid", i+1);
				
				if(null!=colorRowStrings && -1!=colorRowStrings.indexOf(mbro.getProvinceId()))
					m.put(ExportExcel.ROWFOREGROUNDCOLOR, IndexedColors.RED.getIndex());
				date.add(m);
			}
		}
		
		dataMap.put("datalist", date);
		ExportExcel.putCellWhithBackgroundColor(dataMap,"explain","红色背景表示上周填报数据",IndexedColors.RED.getIndex());
		String docTempPath=BaseController.getFileDocTemp();
		//String docTempPath="F:/";
		String excelPath = docTempPath + provinceid+"门禁项目建设进度周报表.xlsx";
		ExportExcel.exportExcel(fileName, excelPath, dataMap);
		return excelPath;
	}
	
	
	//////////////////////////////////////////////////////////
	/**
	 * 发送已完成的通知
	 */
	@Override
	public void sendDoorSystemIsFilled(){
		Map<String,String> qryMap=new HashMap<String,String>();
		qryMap.put("week", "thisweek");
		try {
			String content="";
			String url=FirstqTool.getToPageURL("/main/toPage?toPageUrl=doorSystem/main");
			Map<String,DoorSystemBO> mbProvincePreweekWeekMap= FirstQInterfaces.getIDoorSystemService().doorSystemMap(qryMap);
			
			if(mbProvincePreweekWeekMap!=null && mbProvincePreweekWeekMap.keySet().size()<31){
				String filledProvinceStr=StringUtils.join(mbProvincePreweekWeekMap.keySet(),",");
				content="[门禁建设进度填报集团通知]\n截止当前，共有"+filledProvinceStr+" "+mbProvincePreweekWeekMap.keySet().size()+"个省份完成门禁项目建设进度填报，其他省份暂未填报，详情请点击"+url;
				JSONArray j=FirstqTool.sendDSToWechatGroup(content,"全国");
				System.out.println(j.toJSONString());
				System.out.println(content);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 发送未完成的通知
	 */
	@Override
	public void sendDoorSystemNotFill(){
		Map<String,String> qryMap=new HashMap<String,String>();
		try {
			String content="";
			String url=FirstqTool.getToPageURL("/main/toPage?toPageUrl=doorSystem/main?selectId=2");
			qryMap.put("week", "thisweek");
			Map<String,DoorSystemBO> mbProvincePreweekWeekMap= FirstQInterfaces.getIDoorSystemService().doorSystemMap(qryMap);
			
			List<OrgBO> listob=FirstQInterfaces.getIOrganizationService().getParents();
			StringBuilder sb=new StringBuilder();
			for(OrgBO ob:listob)
				sb.append(ob.getName()+",");
			String orgsStr=sb.toString();
			if(mbProvincePreweekWeekMap!=null && mbProvincePreweekWeekMap.keySet().size()<31){
				for(String province:mbProvincePreweekWeekMap.keySet())
					orgsStr = orgsStr.replace(province+",", "");
				
				content="[门禁建设进度填报集团通知]\n截止当前，共有"+mbProvincePreweekWeekMap.keySet().size()+"个省份完成门禁项目建设进度填报，未填报省份包括"+orgsStr+"，详情请点击"+url;
				JSONArray j=FirstqTool.sendDSToWechatGroup(content,"全国");
				System.out.println(j.toJSONString());
				System.out.println(content);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 发送全部完成的的通知
	 */
	@Override
	public void sendLastFillMsg(List<DoorSystemBO> doorSystemList) {
		Map<String,String> qryMap=new HashMap<String,String>();
		qryMap.put("week", "thisweek");
		try {
			String content="";
			String url=FirstqTool.getToPageURL("/main/toPage?toPageUrl=doorSystem/main?selectId=2");
			Map<String,DoorSystemBO> mbProvincePreweekWeekMap= FirstQInterfaces.getIDoorSystemService().doorSystemMap(qryMap);
			
			if(mbProvincePreweekWeekMap!=null && mbProvincePreweekWeekMap.keySet().size()==31){
				content="[门禁建设进度填报集团通知]\n截至当前，各省已完成门禁项目建设进度填报，详情请点击"+url;
				JSONArray j=FirstqTool.sendDSToWechatGroup(content,"全国");
				System.out.println(j.toJSONString());
				System.out.println(content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getEmailToUserByProvince(String province){
		List<String> userList=new ArrayList<String>();
		if("全国".equals(province)){
			userList.add("liujie@chinatowercom.cn");
		}
		userList.add("wangshixiu@mcu32.com");
		userList.add("chenlonglong@mcu32.com");
//		userList.add("tianfeng@mcu32.com");
		userList.add("wangfang@mcu32.com");
		return userList;
	}
}
