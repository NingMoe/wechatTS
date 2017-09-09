package com.mcu32.firstq.wechat.action.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.mcu32.firstq.common.bean.bo.AgentCompanyBO;
import com.mcu32.firstq.common.bean.bo.DictEntryBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.util.ConfigManager;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.LogUtil;

@Controller
@RequestMapping(value = "/common")
public class CommonController {
	
	@RequestMapping(value = "/getProvince")
	public String getProvince(HttpServletRequest request,ModelMap model){
		model.addAttribute("status","0");
		model.addAttribute("orgs",ConfigManager.getProvince());
		return "";
	}
	
	@RequestMapping(value = "/getCity")
	public String getCity(HttpServletRequest request,ModelMap model){
		String province = request.getParameter("province");
		model.addAttribute("status","0");
		model.addAttribute("orgs",ConfigManager.getCity(province));
		return "";
	}
	
	@RequestMapping(value = "/getCountry")
	public String getCountry(HttpServletRequest request,ModelMap model){
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		model.addAttribute("status","0");
		model.addAttribute("orgs",ConfigManager.getCountry(province, city));
		return "";
	}
	
	@RequestMapping(value = "/getAddress", method = RequestMethod.POST)
	public String getAddress( HttpServletRequest request, HttpSession session,ModelMap model) {
		try {
			String longitude=request.getParameter("longitude");
			String latitude=request.getParameter("latitude");
			String coordtype=request.getParameter("coordtype");
			Double lon = Double.parseDouble(longitude);
			Double lat = Double.parseDouble(latitude);
			String result = FirstqTool.getResultByPoints(lat,lon,coordtype);
			model.addAttribute("result", result);
		} catch (Exception e) {
			LogUtil.error(e);
		}
		return "";
	}
	@RequestMapping(value = "/getBaiduPoint")
	public String getBaiduPoint(HttpServletRequest request,ModelMap model){
		String longitude=request.getParameter("lng");
		String latitude=request.getParameter("lat");
		Map<String, Double> pointMap = FirstqTool.getbaiduFromGps(Double.parseDouble(latitude), Double.parseDouble(longitude));
		JSONObject json = new JSONObject();
		json.put("lng", pointMap.get("lng"));
		json.put("lat", pointMap.get("lat"));
		model.addAttribute("result",json);
		return "";
	}
	
	@RequestMapping(value = "/getAgentCompany")
	public String getAgentCompany(HttpServletRequest request,ModelMap model) throws UnsupportedEncodingException{
		String provinceId = request.getParameter("provinceId");
		if(provinceId != null && !"".equals(provinceId)) {
			provinceId=URLDecoder.decode(provinceId,"UTF-8");
		}
		String cityId = request.getParameter("cityId");
		if(cityId != null && !"".equals(cityId)) {
			cityId=URLDecoder.decode(cityId,"UTF-8");
		}
		List<AgentCompanyBO> agentCompanyList;
		try {
			agentCompanyList = FirstQInterfaces.getIAgentCompanyService().getAgentCompanyByArea(provinceId, cityId);
			model.addAttribute("status","0");
			model.addAttribute("agentCompanyList",agentCompanyList);
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping(value = "/getDictEnterys")
	public String getDictEnterys(HttpServletRequest request,ModelMap model){
		String dictTypeId = request.getParameter("dictTypeId");
		if (dictTypeId != null && !"".equals(dictTypeId)) {
			try {
				dictTypeId = URLDecoder.decode(dictTypeId,"UTF-8");
				Map<String, String> queryCondition = new HashMap<String, String>();
				queryCondition.put("dictTypeId", dictTypeId);
				queryCondition.put("status", 1+"");
				List<DictEntryBO> entryList = FirstQInterfaces.getIDictEntryService().dictEnterList(queryCondition);
				model.addAttribute("entryList",entryList);
			} catch (Exception e) {
				LogUtil.error(e.getMessage(), e);
			}
					
		}
		return "";
	}
	
	@RequestMapping(value = "/reloadConfig")
	public String reloadConfig(HttpServletRequest request,ModelMap model){
		//重新加载组织机构
		ConfigManager.getInstance().reload();
		return "";
	}

}
