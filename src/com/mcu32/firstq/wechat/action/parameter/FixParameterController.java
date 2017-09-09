package com.mcu32.firstq.wechat.action.parameter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mcu32.firstq.common.bean.bo.SetParamResultBO;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.RequestDevicesSetParameterRecord;
import com.mcu32.firstq.wechat.bean.SelectEntity;
import com.mcu32.firstq.wechat.bean.StationInfo;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.interceptor.AccessRequired;
import com.mcu32.firstq.wechat.service.IFixParameterService;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.SessionConstants;


@Controller
@RequestMapping(value = "/parameter")
public class FixParameterController  extends BaseController{
	
	@Autowired IFixParameterService ifixParamService;
	
	@AccessRequired(needLogin=true)
	@RequestMapping(value = "/setParameter", method = RequestMethod.GET)
	public String test(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model) throws UnsupportedEncodingException{
		
		StationInfo si=getSessionStation(session);
		String stationName=si.getStationName();
		
		model.addAttribute("stationId", si.getStationId());
		model.addAttribute("stationNo", si.getStationNo());
		model.addAttribute("stationName", stationName);
		
		List<SelectEntity> powerBrands = ifixParamService.getPowerBrandList();
		model.addAttribute("powerBrands", powerBrands);
		return "parameter/setParameter";
	}
	
	@RequestMapping(value = "/getType")
	public String findElementsById(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String brandId = (String) request.getParameter("brandId");
		List<SelectEntity> typeList = ifixParamService.getPowerType(brandId);
		model.addAttribute("typeList",typeList);
		return "";
	}
	
	@RequestMapping(value = "/getBatteryBrand")
	public String getBatteryBrand(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		List<SelectEntity> batteryList = ifixParamService.getBatteryBrand();
		model.addAttribute("batteryList",batteryList);
		return "";
	}
	
	@RequestMapping(value = "/getParameter", method = RequestMethod.POST)
	public String  getParameter(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model) throws UnsupportedEncodingException{
		setWechatConfig(request,model);//拼写微信js需要的config参数
		List<SelectEntity> calculateResult = ifixParamService.getParameter(request);
        model.addAttribute("calculateResult",calculateResult);
        
        JSONObject calculateResultJson = new JSONObject();
        for(SelectEntity se:calculateResult){
        	calculateResultJson.put(se.getNodeName(), se.getCalculateResult());
        }
        UserInfo su= (UserInfo)session.getAttribute(SessionConstants.SUSER);
        calculateResultJson.put("token", su.getToken());
        calculateResultJson.put("station_id", getStationIdBySession(session));
        calculateResultJson.put("power_brand", request.getParameter("powerBrand"));
        calculateResultJson.put("power_type", request.getParameter("powerType"));
        calculateResultJson.put("battery_brand", request.getParameter("batteryBrand"));
        calculateResultJson.put("production_date", request.getParameter("batterDate"));
        calculateResultJson.put("month_avg_poweroff", request.getParameter("monthAvgPoweroff"));
        calculateResultJson.put("once_avg_time", request.getParameter("onceAvgTime"));
        calculateResultJson.put("equalizing_charge_time_input", request.getParameter("equalizingChargeTimeInput"));
        LogUtil.info("==calculateResult json return to page is "+URLEncoder.encode(calculateResultJson.toString(), "UTF-8"));
        model.addAttribute("parameterRecord",URLEncoder.encode(calculateResultJson.toString(), "UTF-8"));
        
		return "parameter/getParameter";
	}
	
	@RequestMapping(value = "/setParameterRecord", method = RequestMethod.POST)
	public String  setParameterRecord(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model) throws UnsupportedEncodingException{
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		String result="";
		String parameterRecord=request.getParameter("parameterRecord");
		parameterRecord=URLDecoder.decode(parameterRecord, "UTF-8");
		RequestDevicesSetParameterRecord ro=JSON.parseObject(parameterRecord, RequestDevicesSetParameterRecord.class);
		SetParamResultBO paramBO = new SetParamResultBO();
		BeanCopier.create(RequestDevicesSetParameterRecord.class, SetParamResultBO.class, false).copy(ro, paramBO, null);
		paramBO.setId(getUUID());
		paramBO.setCreateUser(user.getUserName());
		paramBO.setCreateUserId(user.getUserId());
		paramBO.setCreateDate(new Date());
		try {
			FirstQInterfaces.getIDeviceService().saveParameterRecord(paramBO);
			model.addAttribute("succ", true);
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("succ", false);
		}
		return result;
	}
	
	
}
