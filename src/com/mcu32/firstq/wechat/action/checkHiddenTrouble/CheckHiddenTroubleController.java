package com.mcu32.firstq.wechat.action.checkHiddenTrouble;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.mcu32.firstq.common.bean.bo.RoomBO;
import com.mcu32.firstq.common.bean.bo.StationBO;
import com.mcu32.firstq.common.bean.bo.TowerBO;
import com.mcu32.firstq.common.bean.bo.YhBatteryBO;
import com.mcu32.firstq.common.bean.bo.YhBatteryDetailBO;
import com.mcu32.firstq.common.bean.bo.YhDischargeRecordBO;
import com.mcu32.firstq.common.bean.bo.YhEasyAccessBO;
import com.mcu32.firstq.common.bean.bo.YhPhotoInfoBO;
import com.mcu32.firstq.common.bean.bo.YhSwitchPowerBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.RoomInfo;
import com.mcu32.firstq.wechat.bean.StationInfo;
import com.mcu32.firstq.wechat.bean.TowerInfo;
import com.mcu32.firstq.wechat.bean.YHExportDetail;
import com.mcu32.firstq.wechat.util.FirstQBeanUtils;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.SessionConstants;
import com.mcu32.firstq.wechat.util.WebContentConstants;
import com.mcu32.firstq.wechat.util.export.ExportExcel;
import com.mcu32.firstq.wechat.util.export.MDoc;
import com.mcu32.firstq.wechat.util.mail.SendMailFactory;

@Controller
@RequestMapping(value = "/checkHiddenTrouble")
public class CheckHiddenTroubleController extends BaseController{
	
	@RequestMapping(value="/goStationInfo")
	public String goStationInfo(HttpServletRequest request, HttpSession session,ModelMap model){
		String stationId = request.getParameter("stationId");
		String clear = request.getParameter("clear");
		
		//StationInfo si = session.getAttribute(SessionConstants.STATION) != null ? (StationInfo) session.getAttribute(SessionConstants.STATION) : null;
		
		StationInfo stationInfo = (StationInfo)session.getAttribute(SessionConstants.STATION);
		if(StringUtils.isEmpty(clear) && (StringUtils.isNotEmpty(stationId) || null!=stationInfo)) {
			try {
				if(null==stationInfo){
					StationBO stationBO = FirstQInterfaces.getIStationService().getStationById(stationId);
					stationInfo = FirstQBeanUtils.getStationInfo(stationBO,null);
				}else{
					RoomInfo roomInfo = stationInfo.getRoomInfo();
					model.addAttribute("roomInfo",roomInfo);
					TowerInfo towerInfo = stationInfo.getTowerInfo();
					model.addAttribute("towerInfo",towerInfo);
					if(roomInfo != null) {
						String roomId = roomInfo.getRoomId();
							List<YhPhotoInfoBO> yhPhotoInfos = FirstQInterfaces.getIYHDealService().getPhotoListByRelatedId(roomId);
							if(yhPhotoInfos != null && yhPhotoInfos.size() > 0) {
								FirstqTool.convertPhotoPath(yhPhotoInfos);
								model.addAttribute("yhPhotoInfos",yhPhotoInfos);
							}
					}
				}
				model.addAttribute("stationInfo",stationInfo);
			} catch (FirstQException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "checkHiddenTrouble/stationInfo";
	}
	
	@RequestMapping(value="/goDeviceCheck",method=RequestMethod.GET)
	public String goDeviceCheck(HttpServletRequest request, HttpSession session,ModelMap model){
		StationInfo si = session.getAttribute(SessionConstants.STATION) != null ? (StationInfo) session.getAttribute(SessionConstants.STATION) : null;
		try {
			YhSwitchPowerBO yhSwitchPowerBO = FirstQInterfaces.getIYHDealService().getSwitchPowerByStationId(si.getStationId());
			YhBatteryBO yhBatteryBO = FirstQInterfaces.getIYHDealService().getBatteryByStationId(si.getStationId());
			if(yhSwitchPowerBO != null) {
				String recordId = yhSwitchPowerBO.getRecordId();
					List<YhPhotoInfoBO> yhPhotoInfos = FirstQInterfaces.getIYHDealService().getPhotoListByRelatedId(recordId);
					if(yhPhotoInfos != null && yhPhotoInfos.size() > 0) {
						FirstqTool.convertPhotoPath(yhPhotoInfos);
						model.addAttribute("yhSwitchPowerPhotoInfos",yhPhotoInfos);
					}
			}
			if(yhBatteryBO != null) {
				String recordId = yhBatteryBO.getRecordId();
					List<YhPhotoInfoBO> yhPhotoInfos = FirstQInterfaces.getIYHDealService().getPhotoListByRelatedId(recordId);
					if(yhPhotoInfos != null && yhPhotoInfos.size() > 0) {
						FirstqTool.convertPhotoPath(yhPhotoInfos);
						List<YhPhotoInfoBO> yhBatteryExceptionPhotoInfos = new ArrayList<YhPhotoInfoBO>();
						List<YhPhotoInfoBO> yhBatteryPhotoInfos = new ArrayList<YhPhotoInfoBO>();
						for(int i = 0; i < yhPhotoInfos.size(); i++) {
							YhPhotoInfoBO yhBatteryPhotoInfo = yhPhotoInfos.get(i);
							String photoType = yhBatteryPhotoInfo.getPhotoType();
							if("battery".equals(photoType)) {
								yhBatteryPhotoInfos.add(yhBatteryPhotoInfo);
							} else if("exception".equals(photoType)) {
								yhBatteryExceptionPhotoInfos.add(yhBatteryPhotoInfo);
							}
						}
						model.addAttribute("yhBatteryExceptionPhotoInfos",yhBatteryExceptionPhotoInfos);
						model.addAttribute("yhBatteryPhotoInfos",yhBatteryPhotoInfos);
					}
			}
			model.addAttribute("yhSwitchPowerBO",yhSwitchPowerBO);
			model.addAttribute("yhBatteryBO",yhBatteryBO);
		} catch (FirstQException e) {
			LogUtil.info(e.getMessage(), e);
		}
		return "checkHiddenTrouble/deviceCheck";
	}
	@RequestMapping(value="/goDeviceCheckNoBattery",method=RequestMethod.GET)
	public String goDeviceCheckNoBattery(HttpServletRequest request, HttpSession session,ModelMap model){
		StationInfo si = session.getAttribute(SessionConstants.STATION) != null ? (StationInfo) session.getAttribute(SessionConstants.STATION) : null;
		try {
			YhSwitchPowerBO yhSwitchPowerBO = FirstQInterfaces.getIYHDealService().getSwitchPowerByStationId(si.getStationId());
			YhBatteryBO yhBatteryBO = FirstQInterfaces.getIYHDealService().getBatteryByStationId(si.getStationId());
			model.addAttribute("yhSwitchPowerBO",yhSwitchPowerBO);
			model.addAttribute("yhBatteryBO",yhBatteryBO);
		} catch (FirstQException e) {
			LogUtil.info(e.getMessage(), e);
		}
		return "checkHiddenTrouble/deviceCheckNoBattery";
	}
	
	@RequestMapping(value="/addSwitchPowerAndBattery",method=RequestMethod.POST)
	public String addSwitchPowerAndBattery(YhSwitchPowerBO ysp,YhBatteryBO ybs,HttpServletRequest request, HttpSession session,ModelMap model){
		System.out.println("1、进入到提交方法----------------》"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
		StationInfo si=(StationInfo)session.getAttribute(SessionConstants.STATION);
		try {
			String isInflation = ybs.getIsInflation(); 
			if(isInflation != null && "否".equals(isInflation)) {
				ybs.setInflationNum(" ");
			}
			String isCracked = ybs.getIsCracked();  
			if(isCracked != null && "否".equals(isCracked)) {
				ybs.setCrackedNum(" ");
			}
			String isLeakage = ybs.getIsLeakage();  
			if(isLeakage != null && "否".equals(isLeakage)) {
				ybs.setLeakageNum(" ");
			}
			String yhSwitchPowerId = request.getParameter("yhSwitchPowerId");
			String yhBatteryId = request.getParameter("yhBatteryId");
			
			if(yhSwitchPowerId != null && !"".equals(yhSwitchPowerId)) {
				ysp.setRecordId(yhSwitchPowerId);
				FirstQInterfaces.getIYHDealService().updateSwitchPower(ysp);
			} else {
				ysp.setRecordId(getUUID());
				FirstQInterfaces.getIYHDealService().saveSwitchPower(ysp);
				
			}
			YhPhotoInfoBO pi=new YhPhotoInfoBO();
			String switchPowerPhoto =request.getParameter("switchPowerPhoto");
			if(StringUtils.isNotEmpty(switchPowerPhoto)){
				pi.setStationId(si.getStationId());
				pi.setRelateId(ysp.getRecordId());
				pi.setPhotoType("switch_power");
				FirstQInterfaces.getIYHDealService().updatePhotoById(switchPowerPhoto,pi);
			}
			if(yhBatteryId != null && !"".equals(yhBatteryId)) {
				ybs.setRecordId(yhBatteryId);
				FirstQInterfaces.getIYHDealService().updateBattery(ybs);
			} else {
				ybs.setRecordId(getUUID());
				FirstQInterfaces.getIYHDealService().saveBattery(ybs);
			}
			
			
			String batteryPhoto =request.getParameter("batteryPhoto");
			if(StringUtils.isNotEmpty(batteryPhoto)){
				pi=new YhPhotoInfoBO();
				pi.setStationId(si.getStationId());
				pi.setRelateId(ybs.getRecordId());
				pi.setPhotoType("battery");
				FirstQInterfaces.getIYHDealService().updatePhotoById(batteryPhoto,pi);
			}

			String exceptionPhoto =request.getParameter("exceptionPhoto");
			if(StringUtils.isNotEmpty(exceptionPhoto)){
				pi=new YhPhotoInfoBO();
				pi.setStationId(si.getStationId());
				pi.setRelateId(ybs.getRecordId());
				pi.setPhotoType("exception");
				FirstQInterfaces.getIYHDealService().updatePhotoById(exceptionPhoto,pi);
			}
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		
		if(StringUtils.isEmpty(ybs.getBatteryBrand())) {
			model.addAttribute("deviceCheckNoBattery","deviceCheckNoBattery");
			return "checkHiddenTrouble/greenChannel";
		}
		session.setAttribute(SessionConstants.YHSWITCHPOWER_STRING, ysp);
		session.setAttribute(SessionConstants.YHBATTERY, ybs);
		model.addAttribute("powerBrand",ysp.getPowerBrand());
		model.addAttribute("deviceCheck","deviceCheck");
		if("prev".equals(request.getParameter("step"))){
			return "forward:/checkHiddenTrouble/goStationInfo";
		}
		return "checkHiddenTrouble/voltage/adjust";
	}
	@RequestMapping(value="/toAdjust")
	public String toAdjust(HttpServletRequest request, HttpSession session,ModelMap model){
		YhSwitchPowerBO ysp=(YhSwitchPowerBO)session.getAttribute(SessionConstants.YHSWITCHPOWER_STRING);
		//YhBatteryBO ybs=(YhBatteryBO)session.getAttribute(SessionConstants.YHBATTERY);
		model.addAttribute("powerBrand",ysp.getPowerBrand());
		model.addAttribute("deviceCheck","deviceCheck");
		return "checkHiddenTrouble/voltage/adjust";
	}
	
	@RequestMapping(value="/searchStation",method=RequestMethod.POST)
	public String searchStation(HttpServletRequest request, HttpSession session,ModelMap model){
		String stationName = request.getParameter("stationName");
		String pageCurrent = request.getParameter("pageCurrent");
		int pageCu = 1;
		if(pageCurrent != null && !"".equals(pageCurrent)) {
			pageCu = Integer.parseInt(pageCurrent);
		}
		try {
			Map<String, String> queryCondition = new HashMap<String, String>();
			queryCondition.put("stationName", stationName);
			List<StationBO> stationBOList= FirstQInterfaces.getIStationService().getCheckHiddenTroubleStationsList(queryCondition, 10, pageCu);
			List<StationInfo> stationList = FirstQBeanUtils.getStationInfoList(stationBOList,null);
			String totalRecords= FirstQInterfaces.getIStationService().getStationCount(queryCondition) + "";
			List<RoomInfo> roomInfos = new ArrayList<RoomInfo>();
			List<TowerInfo> towerInfos = new ArrayList<TowerInfo>();
			if(stationList != null && stationList.size() > 0) {
				for(int i = 0; i < stationList.size(); i++) {
					StationInfo stationInfo = stationList.get(i);
					if(stationInfo != null && stationInfo.getRoomId() != null && !"".equals(stationInfo.getRoomId())) {
						RoomInfo roomInfo = stationInfo.getRoomInfo();
						roomInfos.add(roomInfo);
					} else {
						roomInfos.add(new RoomInfo());
					}
					
					if(stationInfo != null && stationInfo.getTowerId() != null && !"".equals(stationInfo.getTowerId())) {
						TowerInfo towerInfo = stationInfo.getTowerInfo();
						towerInfos.add(towerInfo);
					} else {
						towerInfos.add(new TowerInfo());
					}
				}
			}
			session.setAttribute(SessionConstants.STATIONLIST, stationList);
			model.addAttribute("stationList",stationList);
			model.addAttribute("roomInfos",roomInfos);
			model.addAttribute("towerInfos",towerInfos);
			model.addAttribute("totalRecords",totalRecords);
		} catch (Exception e) {
			LogUtil.error(e);
		}
		return "";
	}
	
	@RequestMapping(value="/getRoomPhoto",method=RequestMethod.POST)
	public String getRoomPhoto(HttpServletRequest request, HttpSession session,ModelMap model){
		String roomId = request.getParameter("roomId");
		try {
			List<YhPhotoInfoBO> yhPhotoInfos = FirstQInterfaces.getIYHDealService().getPhotoListByRelatedId(roomId);
			if(yhPhotoInfos != null && yhPhotoInfos.size() > 0) {
				FirstqTool.convertPhotoPath(yhPhotoInfos);
				model.addAttribute("yhPhotoInfos",yhPhotoInfos);
			}
		} catch (FirstQException e) {
			LogUtil.error(e);
		}
		return "";
	}
	
	@RequestMapping(value = "/add",method=RequestMethod.POST)
	public String addWaitCheck(StationInfo si, TowerInfo ti, RoomInfo ri,HttpServletRequest request, HttpSession session, ModelMap model) {
		System.out.println("1、进入到提交方法----------------》"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
		String stationId = si.getStationId();
		if(stationId == null || "".equals(stationId)) {
			si.setStationId(getUUID());
			si.setCreateDate(new Date());
			si.setCheckStatus("存量站");
			si.setPullingLevel("");
			si.setBatteryNum("0");
		}
		si.setLastDate(new Date());
		RoomBO riBO = new RoomBO();
		if(si.getRoomId() == null || "".equals(si.getRoomId())) {
			ri.setRoomId(getUUID());
		}
		ri.setStationId(si.getStationId());
		BeanCopier.create(RoomInfo.class, RoomBO.class, false).copy(ri, riBO, null);
		
		TowerBO tiBO = new TowerBO();
		if(si.getTowerId() == null || "".equals(si.getTowerId())) {
			ti.setTowerId(getUUID());
		}
		ti.setStationId(si.getStationId());
		BeanCopier.create(TowerInfo.class, TowerBO.class, false).copy(ti, tiBO, null);
		si.setRoomInfo(ri);
		si.setTowerInfo(ti);
		try {
			StationBO siBO = new StationBO();
			BeanCopier.create(StationInfo.class, StationBO.class, false).copy(si, siBO, null);
			siBO.setRoom(riBO);
			siBO.setTower(tiBO);
			if(stationId != null && !"".equals(stationId)) {
				FirstQInterfaces.getIStationService().updateYhStation(siBO);
			} else {
				FirstQInterfaces.getIStationService().saveYhStation(siBO);
			}
			if(si.getTowerId() == null || "".equals(si.getTowerId())) {
				FirstQInterfaces.getIStationService().saveTower(tiBO);
			} else {
				FirstQInterfaces.getIStationService().updateTower(tiBO);
			}
			if(si.getRoomId() == null || "".equals(si.getRoomId())) {
				FirstQInterfaces.getIStationService().saveRoom(riBO);
			} else {
				FirstQInterfaces.getIStationService().updateRoom(riBO);
			}
			String base64Imgstr=request.getParameter("base64Imgstr");
			if(StringUtils.isNotEmpty(base64Imgstr)){
				YhPhotoInfoBO pi=new YhPhotoInfoBO();
				pi.setStationId(si.getStationId());
				pi.setRelateId(ri.getRoomId());
				pi.setPhotoType("room");
				FirstQInterfaces.getIYHDealService().updatePhotoById(base64Imgstr,pi);
			}
			
		} catch (Exception e) {
			LogUtil.info(e.getMessage(), e);
		}
		
		try {
			YhSwitchPowerBO yhSwitchPowerBO = FirstQInterfaces.getIYHDealService().getSwitchPowerByStationId(si.getStationId());
			YhBatteryBO yhBatteryBO = FirstQInterfaces.getIYHDealService().getBatteryByStationId(si.getStationId());
			if(yhSwitchPowerBO != null) {
				String recordId = yhSwitchPowerBO.getRecordId();
					List<YhPhotoInfoBO> yhPhotoInfos = FirstQInterfaces.getIYHDealService().getPhotoListByRelatedId(recordId);
					if(yhPhotoInfos != null && yhPhotoInfos.size() > 0) {
						FirstqTool.convertPhotoPath(yhPhotoInfos);
						model.addAttribute("yhSwitchPowerPhotoInfos",yhPhotoInfos);
					}
			}
			if(yhBatteryBO != null) {
				String recordId = yhBatteryBO.getRecordId();
					List<YhPhotoInfoBO> yhPhotoInfos = FirstQInterfaces.getIYHDealService().getPhotoListByRelatedId(recordId);
					if(yhPhotoInfos != null && yhPhotoInfos.size() > 0) {
						FirstqTool.convertPhotoPath(yhPhotoInfos);
						List<YhPhotoInfoBO> yhBatteryExceptionPhotoInfos = new ArrayList<YhPhotoInfoBO>();
						List<YhPhotoInfoBO> yhBatteryPhotoInfos = new ArrayList<YhPhotoInfoBO>();
						for(int i = 0; i < yhPhotoInfos.size(); i++) {
							YhPhotoInfoBO yhBatteryPhotoInfo = yhPhotoInfos.get(i);
							String photoType = yhBatteryPhotoInfo.getPhotoType();
							if("battery".equals(photoType)) {
								yhBatteryPhotoInfos.add(yhBatteryPhotoInfo);
							} else if("exception".equals(photoType)) {
								yhBatteryExceptionPhotoInfos.add(yhBatteryPhotoInfo);
							}
						}
						model.addAttribute("yhBatteryExceptionPhotoInfos",yhBatteryExceptionPhotoInfos);
						model.addAttribute("yhBatteryPhotoInfos",yhBatteryPhotoInfos);
					}
			}
			model.addAttribute("yhSwitchPowerBO",yhSwitchPowerBO);
			model.addAttribute("yhBatteryBO",yhBatteryBO);
		} catch (FirstQException e) {
			LogUtil.info(e.getMessage(), e);
		}
		session.setAttribute(SessionConstants.STATION, si);
		if("是".equals(si.getHaveBattery())) {
			return "checkHiddenTrouble/deviceCheck";
		}
			
		return "checkHiddenTrouble/deviceCheckNoBattery";
	}
	
	/**TODO
	 */
	@RequestMapping(value="/deleteYhPhoto")
	public String deleteYhPhoto(YhPhotoInfoBO yhpi,HttpServletRequest request, HttpSession session,ModelMap model){
		try {
			boolean saveOk=FirstQInterfaces.getIYHDealService().deletePhotoById(yhpi.getRecordId());
			model.addAttribute("succ", saveOk);
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 
	 */
	@RequestMapping(value="/changeParamerter")
	public String changeParamerter(HttpServletRequest request, HttpSession session,ModelMap model){
		
		return "checkHiddenTrouble/voltage/adjust";
	}
	@RequestMapping(value="/recoverParamerter")
	public String recoverParamerter(HttpServletRequest request, HttpSession session,ModelMap model){
		YhBatteryBO yhBatteryBO = (YhBatteryBO) session.getAttribute(SessionConstants.YHBATTERY);
		if (yhBatteryBO != null) {
			long noeDate = Calendar.getInstance().getTime().getTime();
			if ("风帆、吉天利、银泰、利克、理士、普天电池".indexOf(yhBatteryBO.getBatteryBrand()) > -1) {
				String floatChargeVoltage = "";
				String equalizeChargeVoltage = "";
				if(noeDate <= justicYear(yhBatteryBO.getStartdate(), 2)){
					floatChargeVoltage = "54v";
					equalizeChargeVoltage = "56.4v";
				}else if (noeDate > justicYear(yhBatteryBO.getStartdate(), 2) && noeDate <= justicYear(yhBatteryBO.getStartdate(), 4)) {
					floatChargeVoltage = "54.2v";
					equalizeChargeVoltage = "56.5v";
				}else if (noeDate > justicYear(yhBatteryBO.getStartdate(), 4)) {
					floatChargeVoltage = "54.3v";
					equalizeChargeVoltage = "56.6v";
				}
				model.addAttribute("floatChargeVoltage",floatChargeVoltage);
				model.addAttribute("equalizeChargeVoltage",equalizeChargeVoltage);
			}else if ("双登".equals(yhBatteryBO.getBatteryBrand())) {
				String floatChargeVoltage = "";
				String equalizeChargeVoltage = "";
				if(noeDate <= justicYear(yhBatteryBO.getStartdate(), 2)){
					floatChargeVoltage = "53.6v";
					equalizeChargeVoltage = "55.2v";
				}else if (noeDate > justicYear(yhBatteryBO.getStartdate(), 2) && noeDate <= justicYear(yhBatteryBO.getStartdate(), 4)) {
					floatChargeVoltage = "53.7v";
					equalizeChargeVoltage = "55.3v";
				}else if (noeDate > justicYear(yhBatteryBO.getStartdate(), 4)) {
					floatChargeVoltage = "53.8v";
					equalizeChargeVoltage = "55.5v";
				}
				model.addAttribute("floatChargeVoltage",floatChargeVoltage);
				model.addAttribute("equalizeChargeVoltage",equalizeChargeVoltage);
			}else if ("光宇".equals(yhBatteryBO.getBatteryBrand())) {
				String floatChargeVoltage = "";
				String equalizeChargeVoltage = "";
				if(noeDate <= justicYear(yhBatteryBO.getStartdate(), 2)){
					floatChargeVoltage = "53.7v";
					equalizeChargeVoltage = "53.8v";
				}else if (noeDate > justicYear(yhBatteryBO.getStartdate(), 2) && noeDate <= justicYear(yhBatteryBO.getStartdate(), 4)) {
					floatChargeVoltage = "53.8v";
					equalizeChargeVoltage = "53.9v";
				}else if (noeDate > justicYear(yhBatteryBO.getStartdate(), 4)) {
					floatChargeVoltage = "53.9v";
					equalizeChargeVoltage = "54v";
				}
				model.addAttribute("floatChargeVoltage",floatChargeVoltage);
				model.addAttribute("equalizeChargeVoltage",equalizeChargeVoltage);
			}
			model.addAttribute("batteryBrand",yhBatteryBO.getBatteryBrand());
		}
		return "checkHiddenTrouble/voltage/recoverParamerter";
	}
	
	@RequestMapping(value="/devDischarge")
	public String devDischarge(){
		return "checkHiddenTrouble/discharge";
	}
	
	/**TODO
	 */
	@RequestMapping(value="/toDischarge")
	public String toDischarge(HttpServletRequest request, HttpSession session,ModelMap model){
		
		StationInfo si=(StationInfo)session.getAttribute(SessionConstants.STATION);
		try {
			YhDischargeRecordBO dr = FirstQInterfaces.getIYHDealService().getDischargeRecordByStationId(si.getStationId());
			session.setAttribute(WebContentConstants.YHDISCHARGERECORD, dr);
			model.addAttribute("dr",dr);
			if(null!=dr && null!=dr.getBatteryDetailList())
				model.addAttribute("batteryDetailList",JSON.toJSONString(dr.getBatteryDetailList()));
			
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		
		return "checkHiddenTrouble/discharge";
	}
	@RequestMapping(value="/saveOrUpdateDischargeRecord")
	public String saveOrUpdateDischargeRecord(YhDischargeRecordBO dr,HttpServletRequest request, HttpSession session,ModelMap model){
		StationInfo si=(StationInfo)session.getAttribute(SessionConstants.STATION);
		YhDischargeRecordBO sdr=(YhDischargeRecordBO)session.getAttribute(WebContentConstants.YHDISCHARGERECORD);
		dr.setStationId(si.getStationId());
		if(null!=sdr && null!=sdr.getRecordId())
			dr.setRecordId(sdr.getRecordId());
		
		try {
			String voltagesListStr=request.getParameter("voltageList");
			if(StringUtils.isNotEmpty(voltagesListStr)){
				dr.setBatteryDetailList(JSON.parseArray(voltagesListStr, YhBatteryDetailBO.class));
			}
			
			boolean saveOk = FirstQInterfaces.getIYHDealService().saveOrUpdateDischargeRecord(dr);
			session.setAttribute(WebContentConstants.YHDISCHARGERECORD, dr);
			model.addAttribute("succ",saveOk);
			model.addAttribute("msg","保存成功");
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**TODO
	 */
	@RequestMapping(value="/toGreenChannel")
	public String greenChannel(HttpServletRequest request, HttpSession session,ModelMap model){
		StationInfo si=(StationInfo)session.getAttribute(SessionConstants.STATION);
		try {
			YhEasyAccessBO yhea= FirstQInterfaces.getIYHDealService().getEasyAccessByStationId(si.getStationId());
			model.addAttribute("yhea", yhea);
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		return "checkHiddenTrouble/greenChannel";
	}
	
	/**TODO
	 */
	@RequestMapping(value="/pointFromMap")
	public String selectPointFormMap(HttpServletRequest request, HttpSession session,ModelMap model){
		return "checkHiddenTrouble/selectPointFromMap";
	}
	
	/**TODO
	 */
	@RequestMapping(value="/saveOrUpdateasyAccess")
	public String saveOrUpdateasyAccess(YhEasyAccessBO yhea,HttpServletRequest request, HttpSession session,ModelMap model){
		StationInfo si=(StationInfo)session.getAttribute(SessionConstants.STATION);
		boolean saveOk=false;
		try {
			yhea.setStationId(si.getStationId());
			if(StringUtils.isEmpty(yhea.getRecordId())){
				yhea.setRecordId(getUUID());
				saveOk=FirstQInterfaces.getIYHDealService().saveEasyAccess(yhea);
			}else{
				saveOk=FirstQInterfaces.getIYHDealService().updateEasyAccess(yhea);
			}
			if (saveOk) {
				//生成word结果
				String docPath = prepareDataAndCreateDoc(si,session.getServletContext().getRealPath("/"));
				String excelPath = prepareDataAndCreateExcel(si,session.getServletContext().getRealPath("/"));
				String[] files = {docPath,excelPath};
				model.addAttribute("email",yhea.getEmail());
				String codeUrl = session.getServletContext().getRealPath("assets/i")+"\\code.png";
				SendMailFactory.sendMail(si.getStationName() +"-隐患排查结果",yhea.getEmail(),codeUrl,files);
				return "checkHiddenTrouble/success";
			}
			model.addAttribute("succ", saveOk);
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 时间加几年，返回date
	 * */
	public  Long justicYear(Date date,int later){
		Calendar old = Calendar.getInstance();
		old.setTime(date);
		old.add(Calendar.YEAR, later);
		return old.getTime().getTime();
	}
	
	public String prepareDataAndCreateExcel(StationInfo stationInfo,String projectPath){
		String docTempPath=projectPath + "docTemp"+File.separator;
		File filePath = new File(docTempPath);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		String excelPath=docTempPath+"蓄电池隐患排查表-"+stationInfo.getStationName()+".xlsx";
		try {
			StationBO stationBO = FirstQInterfaces.getIStationService().getStationById(stationInfo.getStationId());
			BeanCopier.create(StationBO.class, StationInfo.class, false).copy(stationBO, stationInfo, null);
		} catch (FirstQException e1) {
			LogUtil.error(e1.getMessage(), e1);
		}
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		
		List<Map<String,Object>> date=new ArrayList<Map<String,Object>>();
    	for(int i=0;i<1;i++){//开始填充list
    		Map<String,Object> m=new HashMap<String,Object>();
    		m.put("no", i+1);
    		m.put("province", stationInfo.getProvinceId());
    		m.put("city", stationInfo.getCityId());
    		m.put("stationName", stationInfo.getStationName());
    		m.put("stationNo", stationInfo.getStationNo());
    		m.put("mobileOperator", stationInfo.getMobileOperator());
			try {
				YhDischargeRecordBO yhDischargeRecordBO = FirstQInterfaces.getIYHDealService().getDischargeRecordByStationId(stationInfo.getStationId());
				if(null!=yhDischargeRecordBO){
					m.put("remark", yhDischargeRecordBO.getRemark());
				}
				
				YhBatteryBO yhBatteryBO = FirstQInterfaces.getIYHDealService().getBatteryByStationId(stationInfo.getStationId());
				
				
				
				if(null!=yhBatteryBO){
					if ( null !=yhBatteryBO.getStartdate()) {
						dataMap.put("startdate", new SimpleDateFormat("yyyyMM").format(yhBatteryBO.getStartdate()));
					}else {
						dataMap.put("startdate","");
					}
					m.put("batteryBrand", yhBatteryBO.getBatteryBrand());
		    		m.put("floatingChargeVoltage", yhBatteryBO.getFloatingChargeVoltage());
		    		m.put("singleCapacity", yhBatteryBO.getSingleCapacity());//当前单组容量(Ah)
		    		m.put("groupNum", yhBatteryBO.getGroupNum());//0,1,2,3,4
		    		m.put("voltageAndBatteryNumber", yhBatteryBO.getCellVoltage());/* 单组电池电压等级/单组中的单体数量 */
		    		m.put("singleVoltage", yhBatteryBO.getCellVoltage());//单体电压
		    		
		    		/* 隐患类别
		    		 * 1.外观严重变形、开裂或严重漏液
		    		 * 2.无法满足0.5小时备电
		    		 * 3.1应配未配：原配置电池被盗
		    		 * 3.2应配未配：2家或以上共享站点
		    		 * 3.3应配未配：单家独享，但运营商确认配置需求
		    		 */
					if("否".equals(stationInfo.getHaveBattery())){
						String type=yhBatteryBO.getPotentialType();
						if("11".equals(type)){
							m.put("yhType", "3.1应配未配：原配置电池被盗");
						}else if("12".equals(type)){
							m.put("yhType", "3.22应配未配：2家或以上共享站点");
						}else if("13".equals(type)){
							m.put("yhType", "3.3应配未配：单家独享，但运营商确认配置需求");
						}
					}else if(yhDischargeRecordBO.getDischargeLength()<5){
						m.put("yhType", "2.无法满足0.5小时备电");
					}else if(!"否".equals(yhBatteryBO.getIsInflation()) || !"否".equals(yhBatteryBO.getIsCracked()) || !"否".equals(yhBatteryBO.getIsLeakage()) || !"否".equals(yhBatteryBO.getBondingStrip())){
						m.put("yhType", "1.外观严重变形、开裂或严重漏液");
					}
				}
    		
	    		m.put("loadCurrent", stationInfo.getLoadCurrent());
	    		
	    		m.put("yhGroupNumber", 0);/*隐患组数0,1,2,3,4*/
	    		m.put("yhBatteryNumber", 0);/*隐患单体数0,1,2,3,4*/
	    		m.put("renovationPlan", "");/*整治方案 1.整组更换或新增 2.更换电池单体*/
	    		m.put("renovationgGroupNumber", "");/*整治的组数*/
	    		m.put("renovationgCapacity", "");/*整治后的单组容量*/
	    		
	    		m.put("replaceBatteryNumber", "");//更换单体数量
	    		m.put("stationBudget", "");//单站整治预算
			} catch (Exception e) {
				e.printStackTrace();
			}
    		date.add(m);
    	}
    	
    	dataMap.put("datalist", date);
    	ExportExcel.exportExcel("battery.xlsx",excelPath,dataMap);
		return excelPath;
	}
	public String prepareDataAndCreateDoc(StationInfo stationInfo,String projectPath){
		//创建doc放置路径
		File filePath = new File(projectPath + "/docTemp");
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		String docPath = projectPath + "docTemp/存量站蓄电池隐患排查记录表-"+stationInfo.getStationName()+".doc";
		
		RoomInfo roomInfo = new RoomInfo();
		try {
			StationBO stationBO = FirstQInterfaces.getIStationService().getStationById(stationInfo.getStationId());
			BeanCopier.create(StationBO.class, StationInfo.class, false).copy(stationBO, stationInfo, null);
			BeanCopier.create(RoomBO.class, RoomInfo.class, false).copy(stationBO.getRoom(), roomInfo, null);
		} catch (FirstQException e1) {
			LogUtil.error(e1.getMessage(), e1);	
		}
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("stationNo", dealStr(stationInfo.getStationNo()));
		dataMap.put("stationName", dealStr(stationInfo.getStationName()));
		dataMap.put("stationAddress", dealStr(stationInfo.getStationAddress()));
		dataMap.put("loadCurrent", stationInfo.getLoadCurrent());
		dataMap.put("mobileOperator", dealStr(stationInfo.getMobileOperator()));
		
		dataMap.put("roomStructure", dealStr(roomInfo.getRoomStructure()));
		
		try {
			YhBatteryBO yhBatteryBO = FirstQInterfaces.getIYHDealService().getBatteryByStationId(stationInfo.getStationId());
			if (yhBatteryBO.getStartdate() != null) {
				dataMap.put("startdate", new SimpleDateFormat("yyyy年MM月dd日").format(yhBatteryBO.getStartdate()));
			}else {
				dataMap.put("startdate","");
			}
			dataMap.put("batteryBrand", dealStr(yhBatteryBO.getBatteryBrand()));
			dataMap.put("batteryModel", dealStr(yhBatteryBO.getBatteryModel()));
			dataMap.put("groupNum", dealStr(yhBatteryBO.getGroupNum()));
			dataMap.put("singleCapacity", dealStr(yhBatteryBO.getSingleCapacity()));
			dataMap.put("floatingChargeVoltage", yhBatteryBO != null ? yhBatteryBO.getFloatingChargeVoltage(): "");
			dataMap.put("floatingChargeCurrent", yhBatteryBO != null ? yhBatteryBO.getFloatingChargeCurrent(): "");
			dataMap.put("isInflation", dealStr(yhBatteryBO.getIsInflation()));
			dataMap.put("inflationNum", dealStr(yhBatteryBO.getInflationNum()));
			dataMap.put("isCracked", dealStr(yhBatteryBO.getIsCracked()));
			dataMap.put("crackedNum", dealStr(yhBatteryBO.getCrackedNum()));
			dataMap.put("isLeakage", dealStr(yhBatteryBO.getIsLeakage()));
			dataMap.put("leakageNum", dealStr(yhBatteryBO.getLeakageNum()));
			dataMap.put("bondingStrip", dealStr(yhBatteryBO.getBondingStrip()));
			dataMap.put("batteryRemark", dealStr(yhBatteryBO.getRemark()));
			
		} catch (FirstQException e1) {
			LogUtil.error(e1.getMessage(), e1);
		}
		
		try {
			YhDischargeRecordBO yhDischargeRecordBO = FirstQInterfaces.getIYHDealService().getDischargeRecordByStationId(stationInfo.getStationId());
			dataMap.put("terminalVoltage",yhDischargeRecordBO != null ? yhDischargeRecordBO.getTerminalVoltage() : "");
			List<YhBatteryDetailBO> detailList = yhDischargeRecordBO != null ?yhDischargeRecordBO.getBatteryDetailList() : null;
			Map<Integer, String> map1 = new HashMap<Integer, String>();
			Map<Integer, String> map2 = new HashMap<Integer, String>();
			
			if (detailList != null && detailList.size() > 0) {
				for (YhBatteryDetailBO yhBatteryDetailBO : detailList) {
					if (yhBatteryDetailBO.getBatteryGroup() == 1) {
						map1.put(yhBatteryDetailBO.getSequence(), yhBatteryDetailBO.getRecordValue());
					}else {
						map2.put(yhBatteryDetailBO.getSequence(), yhBatteryDetailBO.getRecordValue());
					}
				}
			}
			
			List<YHExportDetail> battertDetal = new ArrayList<YHExportDetail>();
			for (int i = 1; i <= 12; i++) {
				YHExportDetail yDetail = new YHExportDetail();
				yDetail.setFirstSeq1(i);
				yDetail.setFirestDetail1(map1.containsKey(i) ? map1.get(i) : "");
				yDetail.setFirstSeq2(i+12);
				yDetail.setFirstDetail2(map1.containsKey(i+12) ? map1.get(i+12) : "");
				
				yDetail.setSecondSeq1(i);
				yDetail.setSecondDetail1(map2.containsKey(i) ? map2.get(i) : "");
				yDetail.setSecondSeq2(i+12);
				yDetail.setSecondDetail2(map2.containsKey(i+12) ? map2.get(i+12) : "");
				battertDetal.add(yDetail);
			}
			
			dataMap.put("battertDetal", battertDetal);
		} catch (FirstQException e1) {
			LogUtil.error(e1.getMessage(), e1);
		}
		
		
		MDoc mdoc = new MDoc();
		
		try {
			mdoc.createDoc(dataMap, docPath);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return docPath;
	}
	
	@RequestMapping(value="/mail")
	public String testMail(HttpServletRequest request, ModelMap model,HttpSession session){
		String[] files = {};
		String codeUrl = session.getServletContext().getRealPath("assets/i")+"\\code.png";
		SendMailFactory.sendMail("测试中文行不行",request.getParameter("email"),codeUrl,files);
		model.addAttribute("mail",request.getParameter("email"));
		return "";
	}
	@RequestMapping(value="/base64ImgUpload")
	public String base64ImgUpload(HttpServletRequest request, ModelMap model,HttpSession session){
		String imgdata=request.getParameter("imgdata");
		YhPhotoInfoBO pi=new YhPhotoInfoBO();
		if(StringUtils.isNotEmpty(imgdata)){
			pi.setStationId(null);
			pi.setCreateTime(new Date());
			pi.setSubmitTime(new Date());
			pi.setStatus("已上传");
			pi.setPhotoType("");
			try {
				FirstQInterfaces.getIYHDealService().saveBase64Photo(imgdata,getFileUploadRealPath(),pi);
			} catch (FirstQException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("2、执行到处理图片之前----------------》"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
		model.addAttribute("succ",true);
		model.addAttribute("recordId",pi.getRecordId());
		return "";
	}
	
	private String dealStr(String str){
		if (str == null || "".equals(str)) {
			return "";
		}else {
			return str;
		}
	}
	
}
