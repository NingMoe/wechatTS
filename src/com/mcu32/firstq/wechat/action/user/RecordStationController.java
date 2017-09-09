package com.mcu32.firstq.wechat.action.user;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcu32.firstq.common.bean.bo.StationInspectionRecordBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.interceptor.AccessRequired;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.SessionConstants;

@Controller
@RequestMapping(value = "/recordStation")
public class RecordStationController  extends BaseController{
	
	/**
	 * 上站巡检
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/toRecordStations")
	public String toRecordStations(HttpServletRequest request, HttpSession session, ModelMap model) throws UnsupportedEncodingException{
		UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
		List<StationInspectionRecordBO> inspectionStations = new ArrayList<StationInspectionRecordBO>();
		int inspectionStationsum = 0;
		int inspectionStationExceptionsum = 0;
		List<StationInspectionRecordBO> inspectionStationExceptions = new ArrayList<StationInspectionRecordBO>();
		String pageCurrent = request.getParameter("pageCurrent");
		String excurrentPage = request.getParameter("excurrentPage");
		int pageCu = 1;
		if(pageCurrent != null && !"".equals(pageCurrent)) {
			pageCu = Integer.parseInt(pageCurrent);
		}
		if(excurrentPage != null && !"".equals(excurrentPage)) {
			pageCu = Integer.parseInt(excurrentPage);
		}
		try {
			Map<String, String> map = new HashMap<String, String>();	
			map.put("userId", user.getUserId());
			inspectionStations = FirstQInterfaces.getIInspectionStationService().recordStationList(map,5, pageCu);
			inspectionStationsum = FirstQInterfaces.getIInspectionStationService().recordStationCount(map);
			inspectionStationExceptions = FirstQInterfaces.getIInspectionStationService().getInspectionStationExceptionList(map,5,pageCu);
			for(int i=0;i<inspectionStationExceptions.size();i++){
				StationInspectionRecordBO sir=inspectionStationExceptions.get(i);
				sir.setDeviceName(sir.getDeviceName().replace(",", " || "));
				sir.setAbnormalCode(sir.getAbnormalCode().replace("_,", " | ").replace("_", ""));
			}
			devicesExceptionStr(inspectionStationExceptions);
			inspectionStationExceptionsum = FirstQInterfaces.getIInspectionStationService().getInspectionStationExceptionCount(map);
		} catch (FirstQException e) {
			LogUtil.error(e.getMessage(),e);
		}
		model.addAttribute("inspectionStations", inspectionStations);
		model.addAttribute("inspectionStationsum", inspectionStationsum);
		model.addAttribute("inspectionStationExceptions", inspectionStationExceptions);
		model.addAttribute("inspectionStationExceptionsum", inspectionStationExceptionsum);
		return "user/recordStationList";
	}
	
	@RequestMapping(value="/recordStations",method=RequestMethod.POST)
	public String recordStations(HttpServletRequest request, HttpSession session, ModelMap model) throws UnsupportedEncodingException{
		UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
		List<StationInspectionRecordBO> inspectionStations = new ArrayList<StationInspectionRecordBO>();
		List<StationInspectionRecordBO> inspectionStationExceptions = new ArrayList<StationInspectionRecordBO>();
		String pageCurrent = request.getParameter("pageCurrent");
		int pageCu = 1;
		if(pageCurrent != null && !"".equals(pageCurrent)) {
			pageCu = Integer.parseInt(pageCurrent);
		}
		try {
			Map<String, String> map = new HashMap<String, String>();	
			map.put("userId", user.getUserId());
			inspectionStations = FirstQInterfaces.getIInspectionStationService().recordStationList(map,5, pageCu);
			inspectionStationExceptions = FirstQInterfaces.getIInspectionStationService().getInspectionStationExceptionList(map,5,pageCu);
			for(int i=0;i<inspectionStationExceptions.size();i++){
				StationInspectionRecordBO sir=inspectionStationExceptions.get(i);
				sir.setDeviceName(sir.getDeviceName().replace(",", " || "));
				sir.setAbnormalCode(sir.getAbnormalCode().replace("_,", " | ").replace("_", ""));
			}
			devicesExceptionStr(inspectionStationExceptions);
		} catch (FirstQException e) {
			LogUtil.error(e.getMessage(),e);
		}
		model.addAttribute("inspectionStations", inspectionStations);
		model.addAttribute("inspectionStationExceptions", inspectionStationExceptions);
		return "";
	}
	
	public void devicesExceptionStr(List<StationInspectionRecordBO> deviceInspectRecords){
		for(StationInspectionRecordBO d : deviceInspectRecords){
			if(d.getAbnormalCode()!=null && !" ".equals(d.getAbnormalCode())){
				String s[] = d.getAbnormalCode().split(" | ");
				String exstr = "";
				for(int j = 0;j<s.length;j++){
					StringBuffer exceptionStr = new StringBuffer();
					String str[] = s[j].split(",");
					for(int i = 0; i<str.length; i++){
						if("11".equals(str[i])){
							exceptionStr.append("防雷模块显示异常,");
						}else if("12".equals(str[i])){
							exceptionStr.append("模块功能异常,");
						}else if("13".equals(str[i])){
							exceptionStr.append("监控显示屏异常,");
						}else if("00".equals(str[i])){
							exceptionStr.append("其他,");
						}else if("21".equals(str[i])){
							exceptionStr.append("外观变形,");
						}else if("22".equals(str[i])){
							exceptionStr.append("漏液,");
						}else if("23".equals(str[i])){
							exceptionStr.append("连接片腐蚀,");
						}else if("24".equals(str[i])){
							exceptionStr.append("蓄电池链接部位沉重塌陷变形,");
						}else if("31".equals(str[i])){
							exceptionStr.append("出现制冷故障,");
						}else if("32".equals(str[i])){
							exceptionStr.append("室外机破损,");
						}else if("33".equals(str[i])){
							exceptionStr.append("室外机变形,");
						}else if("34".equals(str[i])){
							exceptionStr.append("室外机被盗,");
						}else if("41".equals(str[i])){
							exceptionStr.append("无塔高,");
						}else if("42".equals(str[i])){
							exceptionStr.append("校正经纬度,");
						}else if("43".equals(str[i])){
							exceptionStr.append("无塔型,");
						}else{
							exceptionStr.append(" ");
						}
					}
					if (exceptionStr.toString()!=" " && ',' == exceptionStr.charAt(exceptionStr.length() - 1)){
						exceptionStr = exceptionStr.deleteCharAt(exceptionStr.length() - 1); 
					}
					if("".equals(exstr)){
						exstr =  exceptionStr.toString();
					}else{
						exstr = exstr + " | " + exceptionStr.toString();
					}
					exceptionStr=null;
				}
				d.setDeviceName(d.getDeviceName().replace(" || ", "-"));
				d.setAbnormalCode(exstr.replace(" |   | ", "-").replace("|", "-"));
				if (d.getDeviceName().split("-").length > 0 && d.getAbnormalCode().split("-").length > 0) {
					String deviceNames[] = d.getDeviceName().split("-");
					String abnormals[] = d.getAbnormalCode() .split("-");
					d.setDeviceName("");
					for (int i = 0; i < deviceNames.length; i++) {
						if( null == abnormals[i] || abnormals[i].equals(" ") || abnormals[i].equals("  ")){
							
							d.setDeviceName(d.getDeviceName() + "<font style='font-weight: bold;'>"+deviceNames[i] + "</font>&nbsp;&nbsp;" + "异常类型未定" + "<br><hr style='margin: 5px 0px;'>");
						}else{
							d.setDeviceName(d.getDeviceName() + "<font style='font-weight: bold;'>"+deviceNames[i] + "</font>&nbsp;&nbsp;" + abnormals[i] + "<br><hr style='margin: 5px 0px;'>");
						}
					}
				}
			}
		}
	}
}
