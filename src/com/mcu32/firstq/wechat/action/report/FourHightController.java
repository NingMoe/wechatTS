package com.mcu32.firstq.wechat.action.report;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mcu32.firstq.common.bean.bo.HighBillOneLevelDivorce;
import com.mcu32.firstq.common.bean.bo.HighBillTimeOutByArea;
import com.mcu32.firstq.common.bean.bo.HighBillTimeOutByProxy;
import com.mcu32.firstq.common.bean.bo.HighBreakStation;
import com.mcu32.firstq.common.bean.bo.HighFSUOffLine;
import com.mcu32.firstq.common.bean.bo.HighGeneratePower;
import com.mcu32.firstq.common.bean.bo.HighHighTemperature;
import com.mcu32.firstq.common.bean.bo.HighHighTemperatureTS;
import com.mcu32.firstq.common.bean.bo.HighPower;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.interceptor.AccessRequired;
import com.mcu32.firstq.wechat.util.SessionConstants;

@Controller
@RequestMapping(value = "/fourHight")
public class FourHightController extends BaseController{
	/**
	 * 高fsu离线
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/highFSUOffLine",method=RequestMethod.GET)
	public String highFSUOffLine(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String params=request.getParameter("params");
		try {
			params = URLDecoder.decode(params,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		params="{\""+params.replaceAll(":", "\":\"").replaceAll("," , "\",\"")+"\"}";
		@SuppressWarnings("unchecked")
		Map<String,String> map = JSON.parseObject(params, Map.class);
		
		map.put("orderByClause", "NUM_OR_LENGTH");
		
		map.put("type", "0");
		List<HighFSUOffLine> fsuOffLineNumList=FirstQInterfaces.getIIHighFSUOffLineService().selectCurrentWeekTop(map);
		
		map.put("type", "1");
		List<HighFSUOffLine> fsuOffLineLengthList = FirstQInterfaces.getIIHighFSUOffLineService().selectCurrentWeekTop(map);
		
		model.addAttribute("fsuOffLineNumList", fsuOffLineNumList);
		model.addAttribute("fsuOffLineLengthList", fsuOffLineLengthList);
		model.addAttribute("impTime", map.get("impTime"));
		return "report/highFSUOffLine";
	}
	/**
	 * 高fsu离线
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/highFSUOffLineWeek",method=RequestMethod.GET)
	public String highFSUOffLineWeek(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String params=request.getParameter("params");
		try {
			params = URLDecoder.decode(params,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		params="{\""+params.replaceAll(":", "\":\"").replaceAll("," , "\",\"")+"\"}";
		@SuppressWarnings("unchecked")
		Map<String,String> map = JSON.parseObject(params, Map.class);
		map.put("impTimeWeek", map.get("impTime"));
		map.remove("impTime");
		
		map.put("orderByClause", "NUM_OR_LENGTH");
		
		map.put("type", "0");
		List<HighFSUOffLine> fsuOffLineNumList=FirstQInterfaces.getIIHighFSUOffLineService().selectCurrentWeekTop(map);
		
		map.put("type", "1");
		List<HighFSUOffLine> fsuOffLineLengthList = FirstQInterfaces.getIIHighFSUOffLineService().selectCurrentWeekTop(map);
		
		model.addAttribute("fsuOffLineNumList", fsuOffLineNumList);
		model.addAttribute("fsuOffLineLengthList", fsuOffLineLengthList);
		model.addAttribute("impTime", map.get("impTime"));
		return "report/highFSUOffLine";
	}
	/**
	 * 高fsu离线
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/updateHighFSUOffLine",method=RequestMethod.POST)
	public String updateHighFSUOffLine(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String offLineId=request.getParameter("offLineId");
		String reason=request.getParameter("reason");
		String statu=request.getParameter("statu");
		try {
			UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
			HighFSUOffLine record=new HighFSUOffLine();
			record.setOffLineId(offLineId);
			record.setReason(reason);
			record.setStatu(statu);
			record.setLastTime(new Date());
			record.setLastUser(ui.getUserName());
			record.setLastUserId(ui.getUserId());
			FirstQInterfaces.getIIHighFSUOffLineService().updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		model.addAttribute("succ", true);
		model.addAttribute("msg", "保存成功");
		return "report/highFSUOffLine";
	}
	/**
	 * 高高温
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/highTemperature",method=RequestMethod.GET)
	public String highTemperature(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String params=request.getParameter("params");
		try {
			params = URLDecoder.decode(params,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		params="{\""+params.replaceAll(":", "\":\"").replaceAll("," , "\",\"")+"\"}";
		@SuppressWarnings("unchecked")
		Map<String,String> map = JSON.parseObject(params, Map.class);
		
		map.put("orderByClause", "NUM_OR_LENGTH");
		map.put("type", "0");
		List<HighHighTemperature> hightHightNumList=FirstQInterfaces.getIHighHighTemperatureService().selectCurrentWeekTop(map);
		map.put("type", "1");
		List<HighHighTemperature> hightHightLengthList =FirstQInterfaces.getIHighHighTemperatureService().selectCurrentWeekTop(map);
		model.addAttribute("hightHightNumList", hightHightNumList);
		model.addAttribute("hightHightLengthList", hightHightLengthList);
		model.addAttribute("impTime", map.get("impTime"));
		return "report/highTemperature";
	}
	/**
	 * 高高温
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/updateHighHighTemperature",method=RequestMethod.POST)
	public String updateHighHighTemperature(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String numList=request.getParameter("numList");
		JSONArray numarr=JSONArray.parseArray(numList);
		String lengthList=request.getParameter("lengthList");
		JSONArray lengtharr=JSONArray.parseArray(lengthList);
		String reason=request.getParameter("reason");
		String statu=request.getParameter("statu");
		String highTemperatureId=request.getParameter("highTemperatureId");
		UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		HighHighTemperature record=new HighHighTemperature();
		record.setLastTime(new Date());
		record.setLastUser(ui.getUserName());
		record.setLastUserId(ui.getUserId());
		record.setHighTemperatureId(highTemperatureId);
		record.setStatu(statu);
		record.setReason(reason);
		FirstQInterfaces.getIHighHighTemperatureService().updateByPrimaryKeySelective(record);
		model.addAttribute("succ", true);
		model.addAttribute("msg", "保存成功");
		return "report/highTemperature";
	}
	/**
	 * 高工单超时
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/highBillTimeOut",method=RequestMethod.GET)
	public String highBillTimeOut(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String params=request.getParameter("params");
		try {
			params = URLDecoder.decode(params,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		params="{\""+params.replaceAll(":", "\":\"").replaceAll("," , "\",\"")+"\"}";
		@SuppressWarnings("unchecked")
		Map<String,String> map = JSON.parseObject(params, Map.class);
		
		map.put("orderByClause", "DIVORCE_NUM");
		List<HighBillOneLevelDivorce> divorceList=FirstQInterfaces.getIHighBillService().selectDivorceCurrentWeekTop(map);
		
		map.put("orderByClause", "TIME_OUT_NUM");
		List<HighBillTimeOutByArea> areaList =FirstQInterfaces.getIHighBillService().selectAreaCurrentWeekTop(map);
		List<HighBillTimeOutByProxy> proxyList =FirstQInterfaces.getIHighBillService().selectProxyCurrentWeekTop(map);

		model.addAttribute("divorceList", divorceList);
		model.addAttribute("areaList", areaList);
		model.addAttribute("proxyList", proxyList);
		model.addAttribute("impTime", map.get("impTime"));
		return "report/highBillTimeOut";
	}
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/updateHighBillTimeOut",method=RequestMethod.POST)
	public String updateHighBillTimeOut(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String billId=request.getParameter("billId");
		String type=request.getParameter("type");
		String notDealReason=request.getParameter("notDealReason");
		UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		HighBillTimeOutByArea area = new HighBillTimeOutByArea();
		HighBillOneLevelDivorce divorce = new HighBillOneLevelDivorce();
		HighBillTimeOutByProxy proxy = new HighBillTimeOutByProxy();
		area.setLastUser(ui.getUserName());
		area.setLastUserId(ui.getUserId());
		area.setLastTime(new Date());
		
		divorce.setLastUser(ui.getUserName());
		divorce.setLastUserId(ui.getUserId());
		divorce.setLastTime(new Date());
		
		proxy.setLastUser(ui.getUserName());
		proxy.setLastUserId(ui.getUserId());
		proxy.setLastTime(new Date());
		if(type.equals("area")){
			area.setHighTimeOutId(billId);
			area.setNotDealReason(notDealReason);
			FirstQInterfaces.getIHighBillService().updateAreaByPrimaryKeySelective(area);
		}else if(type.equals("divorce")){
			divorce.setHighDivorceId(billId);
			divorce.setNotDealReason(notDealReason);
			FirstQInterfaces.getIHighBillService().updateDivorceByPrimaryKeySelective(divorce);
		}else if(type.equals("proxy")){
			proxy.setHighTimeOutId(billId);
			proxy.setNotDealReason(notDealReason);
			FirstQInterfaces.getIHighBillService().updateProxyByPrimaryKeySelective(proxy);
		}
		model.addAttribute("succ", true);
		model.addAttribute("msg", "保存成功");
		return "report/updateHighBillTimeOut";
	}
	/**
	 * 高电量
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/highPower",method=RequestMethod.GET)
	public String highPower(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String params=request.getParameter("params");
		try {
			params = URLDecoder.decode(params,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		params="{\""+params.replaceAll(":", "\":\"").replaceAll("," , "\",\"")+"\"}";
		@SuppressWarnings("unchecked")
		Map<String,String> map = JSON.parseObject(params, Map.class);
		
		map.put("orderByClause", "RATIO");
		List<HighPower> hightPowerNumList=FirstQInterfaces.getIHighPowerService().selectCurrentWeekTop(map);
		model.addAttribute("hightPowerNumList", hightPowerNumList);
		model.addAttribute("impTime", map.get("impTime"));
		return "report/highPower";
	}
	/**
	 * 高电量
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/updateHighPower",method=RequestMethod.POST)
	public String updateHighPower(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String reason=request.getParameter("reason");
		String solution=request.getParameter("solution");
		String highPowerId=request.getParameter("highPowerId");
		UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		HighPower record=new HighPower();
		record.setLastTime(new Date());
		record.setLastUser(ui.getUserName());
		record.setLastUserId(ui.getUserId());
		record.setHighPowerId(highPowerId);
		record.setReason(reason);
		record.setSolution(solution);
		FirstQInterfaces.getIHighPowerService().updateByPrimaryKeySelective(record);
		model.addAttribute("succ", true);
		model.addAttribute("msg", "保存成功");
		return "report/highPower";
	}
	/**
	 * 唐山高高温
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/highTemperatureTS",method=RequestMethod.GET)
	public String highTemperatureTS(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String params=request.getParameter("params");
		try {
			params = URLDecoder.decode(params,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		params="{\""+params.replaceAll(":", "\":\"").replaceAll("," , "\",\"")+"\"}";
		@SuppressWarnings("unchecked")
		Map<String,String> map = JSON.parseObject(params, Map.class);
		
		map.put("orderByClause", "NUM_OR_LENGTH");
		map.put("type", "2");
		List<HighHighTemperature> hightHightNumList=FirstQInterfaces.getIHighHighTemperatureService().selectCurrentWeekTop(map);
		map.put("type", "3");
		List<HighHighTemperature> hightHightLengthList =FirstQInterfaces.getIHighHighTemperatureService().selectCurrentWeekTop(map);
		model.addAttribute("highHighTemperatureNumList", hightHightNumList);
		model.addAttribute("highHighTemperatureLengthList", hightHightLengthList);
		model.addAttribute("impTime",  map.get("impTime"));
		return "report/highTemperatureTS";
	}
	/**
	 * 唐山高高温
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/updateHighHighTemperatureTS",method=RequestMethod.POST)
	public String updateHighHighTemperatureTS(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String reason=request.getParameter("reason");
		String statu=request.getParameter("statu");
		String highTemperatureId=request.getParameter("highTemperatureId");
		UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		HighHighTemperatureTS record=new HighHighTemperatureTS();
		record.setLastTime(new Date());
		record.setLastUser(ui.getUserName());
		record.setLastUserId(ui.getUserId());
		record.setHighTemperatureId(highTemperatureId);
		record.setReason(reason);
		record.setStatu(statu);
		FirstQInterfaces.getIHighTSService().updateTemperatureByPrimaryKeySelective(record);
		model.addAttribute("succ", true);
		model.addAttribute("msg", "保存成功");
		return "report/highTemperatureTS";
	}
	/**
	 * 唐山高发电
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/highGenerationTS",method=RequestMethod.GET)
	public String highGenerationTS(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String params=request.getParameter("params");
		try {
			params = URLDecoder.decode(params,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		params="{\""+params.replaceAll(":", "\":\"").replaceAll("," , "\",\"")+"\"}";
		@SuppressWarnings("unchecked")
		Map<String,String> map = JSON.parseObject(params, Map.class);
		
		map.put("orderByClause", "NUM_OR_LENGTH");
		map.put("type", "0");
		List<HighGeneratePower> highGeneratePowerNumList=FirstQInterfaces.getIHighTSService().selectPowerCurrentWeekTop(map);
		map.put("type", "1");
		List<HighGeneratePower> hightGeneratePowerLengthList =FirstQInterfaces.getIHighTSService().selectPowerCurrentWeekTop(map);
		model.addAttribute("highGenerationNumList", highGeneratePowerNumList);
		model.addAttribute("highGenerationLengthList", hightGeneratePowerLengthList);
		model.addAttribute("impTime", map.get("impTime"));
		return "report/highGenerationPowerTS";
	}
	/**
	 * 唐山高发电
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/updateHighGenerationTS",method=RequestMethod.POST)
	public String updateHighGenerationTS(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String reason=request.getParameter("reason");
		String statu=request.getParameter("statu");
		String highGeneratepowerId=request.getParameter("highGeneratepowerId");
		
		UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		HighGeneratePower record=new HighGeneratePower();
		record.setLastTime(new Date());
		record.setLastUser(ui.getUserName());
		record.setLastUserId(ui.getUserId());
		record.setHighGeneratepowerId(highGeneratepowerId);
		record.setReason(reason);
		record.setStatu(statu);
		FirstQInterfaces.getIHighTSService().updatePowerByPrimaryKeySelective(record);
		model.addAttribute("succ", true);
		model.addAttribute("msg", "保存成功");
		return "report/highGenerationPowerTS";
	}
	/**
	 * 唐山高断站
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/highBreakStationTS",method=RequestMethod.GET)
	public String highBreakStationTS(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String params=request.getParameter("params");
		try {
			params = URLDecoder.decode(params,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		params="{\""+params.replaceAll(":", "\":\"").replaceAll("," , "\",\"")+"\"}";
		@SuppressWarnings("unchecked")
		Map<String,String> map = JSON.parseObject(params, Map.class);
		
		map.put("orderByClause", "NUM_OR_LENGTH");
		map.put("type", "0");
		List<HighBreakStation> highBreakStationNumList=FirstQInterfaces.getIHighTSService().selectStationCurrentWeekTop(map);
		map.put("type", "1");
		List<HighBreakStation> hightBreakStationLengthList =FirstQInterfaces.getIHighTSService().selectStationCurrentWeekTop(map);
		model.addAttribute("highBreakStationNumList", highBreakStationNumList);
		model.addAttribute("highBreakStationLengthList", hightBreakStationLengthList);
		model.addAttribute("impTime", map.get("impTime"));
		return "report/highBreakStationTS";
	}
	/**
	 * 唐山高断站
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/updateHighBreakStationTS",method=RequestMethod.POST)
	public String updateHighBreakStationTS(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String reason=request.getParameter("reason");
		String statu=request.getParameter("statu");
		String highBreakStationId=request.getParameter("highBreakStationId");
		
		UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		HighBreakStation record=new HighBreakStation();
		record.setLastTime(new Date());
		record.setLastUser(ui.getUserName());
		record.setLastUserId(ui.getUserId());
		record.setHighBreakStationId(highBreakStationId);
		record.setReason(reason);
		record.setStatu(statu);
		FirstQInterfaces.getIHighTSService().updateStationByPrimaryKeySelective(record);
		
		model.addAttribute("succ", true);
		model.addAttribute("msg", "保存成功");
		return "report/highBreakStationTS";
	}
	
	@RequestMapping(value="/selectSolveCount")
	public String selectSolveCount(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		Map<String,String> map = getRealMapByReqeustMap(request);
		System.out.println(JSON.toJSONString(map));
		
		map.put("statu", "1");
		map.put("orderByClause", "NUM_OR_LENGTH");
		
		int shHourTemperature = 0;
		int shHourFSU = 0;
		int tsHourTemperature = 0;
		int tsHourPower = 0;
		int tsHourStation = 0;
		
		int shCountTemperature = 0;
		int shCountFSU = 	0;
		int tsCountTemperature = 0;
		int tsCountPower = 0;
		int tsCountStation = 0;
		
		int shPower = 0;
		map.put("type", "1");
		String urlKey=map.get("urlKey");
		if(StringUtils.isNotEmpty(urlKey)){
			
			if("fourHight/highTemperature".equals(urlKey)){
				shHourTemperature = FirstQInterfaces.getIHighHighTemperatureService().selectSolveCount(map);
				map.put("type", "0");
				shCountTemperature = FirstQInterfaces.getIHighHighTemperatureService().selectSolveCount(map);
			}else if("fourHight/highFSUOffLine".equals(urlKey)){
				shHourFSU = 	FirstQInterfaces.getIIHighFSUOffLineService().selectSolveCount(map);
				map.put("type", "0");
				shCountFSU = 	FirstQInterfaces.getIIHighFSUOffLineService().selectSolveCount(map);
			}else if("fourHight/highTemperatureTS".equals(urlKey)){
				map.put("type", "3");
				tsHourTemperature = FirstQInterfaces.getIHighTSService().selectTemperatureSolveCount(map);
				map.put("type", "2");
				tsCountTemperature = FirstQInterfaces.getIHighTSService().selectTemperatureSolveCount(map);
			}else if("fourHight/highGenerationTS".equals(urlKey)){
				tsHourPower = FirstQInterfaces.getIHighTSService().selectPowerSolveCount(map);
				map.put("type", "0");
				tsCountPower = FirstQInterfaces.getIHighTSService().selectPowerSolveCount(map);
			}else if("fourHight/highBreakStationTS".equals(urlKey)){
				tsHourStation = FirstQInterfaces.getIHighTSService().selectStationSolveCount(map);
				map.put("type", "0");
				tsCountStation = FirstQInterfaces.getIHighTSService().selectStationSolveCount(map);
			}else if("fourHight/highPower".equals(urlKey)){
				map.remove("type");
				map.put("orderByClause", "RATIO");
				shPower = FirstQInterfaces.getIHighPowerService().selectSolveCount(map);
			}
			
		}else{
			shHourTemperature = FirstQInterfaces.getIHighHighTemperatureService().selectSolveCount(map);
			shHourFSU = 	FirstQInterfaces.getIIHighFSUOffLineService().selectSolveCount(map);
			tsHourPower = FirstQInterfaces.getIHighTSService().selectPowerSolveCount(map);
			tsHourStation = FirstQInterfaces.getIHighTSService().selectStationSolveCount(map);
			
			map.put("type", "0");
			shCountTemperature = FirstQInterfaces.getIHighHighTemperatureService().selectSolveCount(map);
			shCountFSU = 	FirstQInterfaces.getIIHighFSUOffLineService().selectSolveCount(map);
			tsCountPower = FirstQInterfaces.getIHighTSService().selectPowerSolveCount(map);
			tsCountStation = FirstQInterfaces.getIHighTSService().selectStationSolveCount(map);
			
			map.put("type", "2");
			tsHourTemperature = FirstQInterfaces.getIHighTSService().selectTemperatureSolveCount(map);
			map.put("type", "3");
			tsCountTemperature = FirstQInterfaces.getIHighTSService().selectTemperatureSolveCount(map);
			
			map.remove("type");
			map.put("orderByClause", "RATIO");
			shPower = FirstQInterfaces.getIHighPowerService().selectSolveCount(map);
		}
		
		
		model.addAttribute("fourHight/highTemperature", new int[]{shCountTemperature,shHourTemperature});
		model.addAttribute("fourHight/highFSUOffLine", new int[]{shCountFSU,shHourFSU});
		model.addAttribute("fourHight/highTemperatureTS", new int[]{tsCountTemperature,tsHourTemperature});
		model.addAttribute("fourHight/highGenerationTS", new int[]{tsCountPower,tsHourPower});
		model.addAttribute("fourHight/highBreakStationTS", new int[]{tsCountStation,tsHourStation});
		model.addAttribute("fourHight/highPower", new int[]{shPower});
		
		return "";
	}
}
