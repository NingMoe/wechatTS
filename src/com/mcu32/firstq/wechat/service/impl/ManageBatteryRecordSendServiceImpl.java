package com.mcu32.firstq.wechat.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.mcu32.firstq.common.bean.bo.ManageBatteryRecordBO;
import com.mcu32.firstq.common.bean.bo.ManageBatteryRecordScrapBO;
import com.mcu32.firstq.common.bean.bo.OrgBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.service.IManageBatteryRecordSendService;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.ShortUrlUtil;
import com.mcu32.firstq.wechat.util.ToolUtil;
import com.mcu32.firstq.wechat.util.export.ExportExcel;
import com.mcu32.firstq.wechat.util.mail.SendMailFactory;

@Service
public class ManageBatteryRecordSendServiceImpl implements IManageBatteryRecordSendService {
	/**
	 * 全国的周报
	 */
	@Override
	public void sendThisWeekCountyEmail() {
		
		//发送的内容
		String content="您好：<br>&nbsp;&nbsp;&nbsp;&nbsp;附件为第一象限为您生成的本周蓄电池填报周报，请查收。<br>";
		
		Map <String,String> qryMap=new HashMap<String,String>();
		qryMap.put("week", "thisweek");
		Map<String,ManageBatteryRecordBO> mbProvinceThisWeekMap = FirstQInterfaces.getIManageBatteryRecordService().manageBatteryRecordMap_J(qryMap);
		
		if(!mbProvinceThisWeekMap.isEmpty()  && mbProvinceThisWeekMap.keySet().size()<31){
			String filledProvinceStr=StringUtils.join(mbProvinceThisWeekMap.keySet(),",");
			content=content+"&nbsp;&nbsp;&nbsp;&nbsp;截止邮件发出时间，共有"+filledProvinceStr+" "+mbProvinceThisWeekMap.keySet().size()+"个省份填报完成，详见附件，请审阅。<br>";
		}else{
			content=content+"&nbsp;&nbsp;&nbsp;&nbsp;截止邮件发出时间，各省均已填报完成，详见附件，请审阅。<br>";
		}
		
		//发送给的人
		List<String> toUserList=getEmailToUserByProvince("全国");
		
		//抄送给的人
		List<String> ccList=new ArrayList<String>();
		//ccList.add("chenyan@mcu32.com");
		
		//生成excel 文件
		qryMap.put("week", "beforethisweek");
		Map<String,ManageBatteryRecordBO> mbProvincePreweekWeekMap = FirstQInterfaces.getIManageBatteryRecordService().manageBatteryRecordMap_J(qryMap);
		
		Set<String> preweekWeekProvinceSet=new HashSet<String>(mbProvincePreweekWeekMap.keySet());
		Set<String> thisweekWeekProvinceSet=new HashSet<String>(mbProvinceThisWeekMap.keySet());
		preweekWeekProvinceSet.removeAll(thisweekWeekProvinceSet);
		String notFillProvinceStr=StringUtils.join(preweekWeekProvinceSet,",");//剩下的是本周没有填报，上周填报了的数据
		
		mbProvincePreweekWeekMap.putAll(mbProvinceThisWeekMap);//本周的数据覆盖上周的数据
		List<ManageBatteryRecordBO> mbAllList=new ArrayList<ManageBatteryRecordBO>(mbProvincePreweekWeekMap.values());
		List<String> fileList=new ArrayList<String>();
		String excelPath = prepareDataAndCreateExcel(mbAllList,notFillProvinceStr,"managebatteryreport.xlsx","全国");
		fileList.add(excelPath);
				
		SendMailFactory.sendMailContent(content,"本周蓄电池整治周报",toUserList,ccList,fileList);
	}
	
	/**
	 * 以下为推送地市公司的周报
	 */
	@Override
	public void sendThisWeekCityEmail() {
		HashMap<String,Object> qryM=new HashMap<String,Object>();
		
		List<String> porvinceList=needCityTotalCityArray();
		
		List<ManageBatteryRecordBO> manageBatteryRecordProvinceList =null;
		List<OrgBO> listob=null;
		for(String province:porvinceList){
			qryM.put("provinceid",province);
			qryM.put("week", "thisweek");
			try {
				manageBatteryRecordProvinceList= FirstQInterfaces.getIManageBatteryRecordService().manageBatteryRecordProvinceList(qryM);
				listob=FirstQInterfaces.getIOrganizationService().getByCityNameSons(province);
			} catch (FirstQException e) {
				e.printStackTrace();
			}
			
			//正文内容
			String contentCity="您好：<br>&nbsp;&nbsp;&nbsp;&nbsp;附件为第一象限为您生成的本周蓄电池填报周报，请查收。<br>&nbsp;&nbsp;&nbsp;&nbsp;截止邮件发出时间，"+province;
			int completCount=manageBatteryRecordProvinceList.size()-2>0?completCount=manageBatteryRecordProvinceList.size()-2:0;//减去sql中两行合计内容
			if(null!=listob && completCount==listob.size()){
				contentCity=contentCity+"所有地市已经完成蓄电池整治填报，详见附件，请审阅。<br>";
			}else{
				contentCity=contentCity+"已有"+completCount+"个地市已经完成蓄电池整治填报，详见附件，请审阅。<br>";
			}
			
			//要发送给的人
			List<String> toUserList=getEmailToUserByProvince(province);
			
			//抄送给的人
			List<String> ccList=new ArrayList<String>();
			//ccList.add("chenyan@mcu32.com");
			
			//生成的附件excel
			List<String> fileList=new ArrayList<String>();
			if(province.equals("黑龙江省")){//黑龙江增加蓄电池整治周报
				String excelPath2=prepareDataAndCreateExcel(manageBatteryRecordProvinceList,null,"managebatteryreportHeilongjiangCity.xlsx",province);
				String excelPath3 = prepareDataAndCreateExcelScrap(province); 
				fileList.add(excelPath2);
				fileList.add(excelPath3);
			}else{
				String excelPath2=prepareDataAndCreateExcel(manageBatteryRecordProvinceList,null,"managebatteryreportCity.xlsx",province);
				fileList.add(excelPath2);
			}
			
			SendMailFactory.sendMailContent(contentCity,province+"本周蓄电池整治周报",toUserList,ccList,fileList);   
		}
	}
	
	public static String prepareDataAndCreateExcel(List<ManageBatteryRecordBO> manageBatteryRecordGroupList,String colorRowStrings, String fileName,String provinceid) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> date = new ArrayList<Map<String, Object>>();
		if (manageBatteryRecordGroupList != null) {
			for (int i = 0; i < manageBatteryRecordGroupList.size(); i++) {// 开始填充list
				ManageBatteryRecordBO mbro=manageBatteryRecordGroupList.get(i);
				
				@SuppressWarnings("unchecked")
				Map<String,Object> m=JSON.parseObject(JSON.toJSONString(mbro,SerializerFeature.WriteDateUseDateFormat), Map.class);
				
				m.put("sid", i+1);
				m.put("completePro", null!=mbro.getCompletePro() ?mbro.getCompletePro()+"%":"");
				m.put("paymentPro", null!=mbro.getPaymentPro()?mbro.getPaymentPro()+"%":"");
				m.put("completeSumPmsPro", null!=mbro.getCompleteSumPmsPro() ? mbro.getCompleteSumPmsPro()+"%":"");
				m.put("ascumulativeTransferAmountPro", null!=mbro.getAscumulativeTransferAmountPro() ?mbro.getAscumulativeTransferAmountPro()+"%":"");
				
				if(null!=colorRowStrings && -1!=colorRowStrings.indexOf(mbro.getProvinceid()))
					m.put(ExportExcel.ROWFOREGROUNDCOLOR, IndexedColors.YELLOW.getIndex());
				
				date.add(m);
			}
		}
		
		dataMap.put("datalist", date);
		ExportExcel.putCellWhithBackgroundColor(dataMap,"explain","黄色背景表示上周填报数据",IndexedColors.YELLOW.getIndex());
		
		String docTempPath=BaseController.getFileDocTemp();
		String excelPath = docTempPath + provinceid+"蓄电池整治进度周报表.xlsx";
		ExportExcel.exportExcel(fileName, excelPath, dataMap);
		return excelPath;
	}
	
	/**
	 * 生成蓄电池报废Excel附件
	 */
	public String prepareDataAndCreateExcelScrap(String provinceid) {
	    HashMap<String, String> scrapMap=new HashMap<String,String>();
		scrapMap.put("week", "thisweek");
		scrapMap.put("provinceid",provinceid);
		List<ManageBatteryRecordScrapBO> manageBatteryRecordScrapList =null;
		try {
			manageBatteryRecordScrapList = FirstQInterfaces.getIManageBatteryRecordService().selectManageBatteryRecordScrapList(scrapMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> exportList = new ArrayList<Map<String, Object>>();
		for(int i=0;i<manageBatteryRecordScrapList.size();i++){
			ManageBatteryRecordScrapBO mbrso=manageBatteryRecordScrapList.get(i);
			@SuppressWarnings("unchecked")
			Map<String,Object> m=JSON.parseObject(JSON.toJSONString(mbrso,SerializerFeature.WriteDateUseDateFormat), Map.class);
			m.put("sid", i+1);
			exportList.add(m);
		}
		
		dataMap.put("datalist", exportList);
		
		String docTempPath=BaseController.getFileDocTemp();
		String excelPath = docTempPath + provinceid+"蓄电池报废填报周报表.xlsx";
		ExportExcel.exportExcel("managebatteryreportScrapCity.xlsx", excelPath, dataMap);
		return excelPath;
	}
	//////////////////////////////////////////////////////////
	@Override
	public void sendManageBatteryIsFilled(){
		Map<String,String> qryMap=new HashMap<String,String>();
		qryMap.put("week", "thisweek");
		try {
			String content="";
			String url=FirstqTool.getToPageURL("/main/toPage?toPageUrl=report/manageBatteryRecordByProvince?selectId=3");
			Map<String,ManageBatteryRecordBO> mbProvincePreweekWeekMap= FirstQInterfaces.getIManageBatteryRecordService().manageBatteryRecordMap_J(qryMap);
			
			if(mbProvincePreweekWeekMap!=null && mbProvincePreweekWeekMap.keySet().size()<31){
				String filledProvinceStr=StringUtils.join(mbProvincePreweekWeekMap.keySet(),",");
				content="[蓄电池整治填报集团通知]\n截止当前，共有"+filledProvinceStr+" "+mbProvincePreweekWeekMap.keySet().size()+"个省份填报完成，其他省份暂未填报，详情请查看集团蓄电池整治统计页面"+url;
				JSONArray j=FirstqTool.sendMBToWechatGroup(content,"全国");
				System.out.println(j.toJSONString());
				System.out.println(content);
			}
			
			sendRobotInfoToCityIsFilled();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void sendManageBatteryNotFill(){
		Map<String,String> qryMap=new HashMap<String,String>();
		try {
			String content="";
			String url=FirstqTool.getToPageURL("/main/toPage?toPageUrl=report/manageBatteryRecordByProvince?selectId=3");
			qryMap.put("week", "thisweek");
			Map<String,ManageBatteryRecordBO> mbProvincePreweekWeekMap= FirstQInterfaces.getIManageBatteryRecordService().manageBatteryRecordMap_J(qryMap);
			
			List<OrgBO> listob=FirstQInterfaces.getIOrganizationService().getParents();
			StringBuilder sb=new StringBuilder();
			for(OrgBO ob:listob)
				sb.append(ob.getName()+",");
			String orgsStr=sb.toString();
			if(mbProvincePreweekWeekMap!=null && mbProvincePreweekWeekMap.keySet().size()<31){
				for(String province:mbProvincePreweekWeekMap.keySet())
					orgsStr = orgsStr.replace(province+",", "");
				
				content="[蓄电池整治填报集团通知]\n截止当前，共有"+mbProvincePreweekWeekMap.keySet().size()+"个省份填报完成，未填报省份包括"+orgsStr+"详情请查看集团蓄电池整治统计页面"+url;
				JSONArray j=FirstqTool.sendMBToWechatGroup(content,"全国");
				System.out.println(content);
				System.out.println(j.toJSONString());
			}
			
			sendRobotInfoToCityNotFill();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void sendRobotInfoToCityIsFilled(){
		List<String> porvinceList= needCityTotalCityArray();
		for(String province:porvinceList){
			Map<String,String> m=new HashMap<String,String>();
			m.put("provinceid", province);
			m.put("week", "thisweek");
			
			StringBuilder sb=new StringBuilder();
			String url=FirstqTool.getToPageURL("/main/toPage?toPageUrl=report/manageBatteryRecordByProvince?selectId=2");
			try {
				List<ManageBatteryRecordBO> list= FirstQInterfaces.getIManageBatteryRecordService().selectManageBatteryRecord_City(m);
				List<OrgBO> listob=FirstQInterfaces.getIOrganizationService().getByCityNameSons(province);
				if(null!=list && list.size()<listob.size()){
					for(int i=0;i<list.size();i++)
						sb.append(","+list.get(i).getCityid());

					if(sb.length()>0) sb.deleteCharAt(0);
					sb.insert(0, "[蓄电池整治填报省级通知]\n截止当前，"+province+" ");
					sb.append(" "+list.size()+"个地市已经完成蓄电池整治填报，"+ ((0==list.size())?"":"其他地市暂未填报，") +"详情请查看蓄电池整治统计页面"+url);
					System.out.println(sb.toString());
					JSONArray j=FirstqTool.sendMBToWechatGroup(sb.toString(),province);
					System.out.println(j.toJSONString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	private void sendRobotInfoToCityNotFill(){
		List<String> porvinceList=needCityTotalCityArray();
		for(String province:porvinceList){
			Map<String,String> m=new HashMap<String,String>();
			m.put("provinceid", province);
			m.put("week", "thisweek");
			
			StringBuilder sb=new StringBuilder();
			String url=FirstqTool.getToPageURL("/main/toPage?toPageUrl=report/manageBatteryRecordByProvince?selectId=2");
			try {
				List<ManageBatteryRecordBO> list= FirstQInterfaces.getIManageBatteryRecordService().selectManageBatteryRecord_City(m);
				List<OrgBO> listob=FirstQInterfaces.getIOrganizationService().getByCityNameSons(province);
				
				if(null!=list && list.size()<listob.size()){
					
					StringBuilder orgs=new StringBuilder();
					for(OrgBO org:listob)
						orgs.append(org.getName()+"，");
					
					String orgsStr=orgs.toString();
					for(int i=0;i<list.size();i++)
						orgsStr = orgsStr.replace(list.get(i).getCityid()+"，", "");

					sb.append("[蓄电池整治填报省级通知]\n截止当前，"+province+" ");
					sb.append("共有"+list.size()+"个地市已经完成蓄电池整治填报，"+ ((0==list.size())?"":"未填报地市包括："+orgsStr) +" 详情请查看蓄电池整治统计页面"+url);
					System.out.println(sb.toString());
					JSONArray j=FirstqTool.sendMBToWechatGroup(sb.toString(),province);
					System.out.println(j.toJSONString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void sendLastFillMsg() {
		
		try {
			String content="";
			String url=FirstqTool.getToPageURL("/main/toPage?toPageUrl=report/manageBatteryRecordByProvince?selectId=3");
			Map<String,String> qryMap=new HashMap<String,String>();
			qryMap.put("week", "thisweek");
			Map<String,ManageBatteryRecordBO> mbProvincePreweekWeekMap= FirstQInterfaces.getIManageBatteryRecordService().manageBatteryRecordMap_J(qryMap);
			
			if(mbProvincePreweekWeekMap!=null && mbProvincePreweekWeekMap.keySet().size()==31){
				content="[蓄电池整治填报集团通知]\n截止当前，各省份均已完成蓄电池整治填报，详情请查看集团蓄电池整治统计页面"+url;
				JSONArray j=FirstqTool.sendMBToWechatGroup(content,"全国");
				System.out.println(j.toJSONString());
				System.out.println(content);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void sendCityLastFillMsg(String privinceid) {
		List<String> porvinceList= needCityTotalCityArray();
		if(porvinceList.contains(privinceid)){
			Map<String,String> m=new HashMap<String,String>();
			m.put("provinceid", privinceid);
			m.put("week", "thisweek");
			
			StringBuilder sb=new StringBuilder();
			String url=FirstqTool.getToPageURL("/main/toPage?toPageUrl=report/manageBatteryRecordByProvince?selectId=2");
			try {
				List<ManageBatteryRecordBO> list= FirstQInterfaces.getIManageBatteryRecordService().selectManageBatteryRecord_City(m);
				List<OrgBO> listob=FirstQInterfaces.getIOrganizationService().getByCityNameSons(privinceid);
				if(list.size()==listob.size()){
					sb.append("[蓄电池整治填报省级通知]\n截止当前，"+privinceid+"各地市已经完成蓄电池整治填报，详情请查看蓄电池整治统计页面"+url);
					System.out.println(sb.toString());
					JSONArray j=FirstqTool.sendMBToWechatGroup(sb.toString(),privinceid);
					System.out.println(j.toJSONString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 需要地市汇总
	 */
	@Override
	public boolean needCityTotal(String province){
		String needCityTotalProvince=ToolUtil.getAppConfig("NeedCityTotalProvince");
		if(-1!=needCityTotalProvince.indexOf(province))
			return true;
		return false;
	}
	
	@Override
	public List<String> needCityTotalCityArray(){
		String needCityTotalProvince=ToolUtil.getAppConfig("NeedCityTotalProvince");
		return Arrays.asList(needCityTotalProvince.split(","));
	}
	
	@Override
	public List<String> getEmailToUserByProvince(String province){
		List<String> userList=new ArrayList<String>();
		
		if("全国".equals(province)){
			userList.add("universe_wang@139.com");
		}else if("黑龙江省".equals(province)){
			userList.add("chenzd@chinatowercom.cn");
		}else if("重庆市".equals(province)){
			userList.add("zhanghw3@chinatowercom.cn");
		}else if("青海省".equals(province)){
			userList.add("weijp@chinatowercom.cn");
		}else if("江西省".equals(province)){
			userList.add("huhui3@chinatowercom.cn");
		}
		
		userList.add("wangshixiu@mcu32.com");
		//userList.add("zongmiaojiao@mcu32.com");
		userList.add("chenlonglong@mcu32.com");
		
		userList.add("tianfeng@mcu32.com");
		
		return userList;
	}

	@Override
	public Map<String, String> selectLeaderByProvinceId(String provinceId) {
		// TODO Auto-generated method stub
		Map<String,String> map=new HashMap<String,String>();
		if("重庆市".equals(provinceId))//重庆的省级领导是张翰文
			map.put("userId", "7063c22c-bb5a-4807-ba80-a01159153b3d");//张翰文
		
		if("黑龙江省".equals(provinceId))//重庆的省级领导是陈志东
			map.put("userId", "a4f80dfa-9545-42dc-a399-15f92a22334c");//陈志东
		
		return map;
	}


}
