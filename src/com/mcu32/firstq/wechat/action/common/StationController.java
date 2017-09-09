package com.mcu32.firstq.wechat.action.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcu32.firstq.common.bean.bo.DeviceInspectionRecordBO;
import com.mcu32.firstq.common.bean.bo.PhotoBO;
import com.mcu32.firstq.common.bean.bo.RegionalManagerBO;
import com.mcu32.firstq.common.bean.bo.RelationStationBo;
import com.mcu32.firstq.common.bean.bo.RoomBO;
import com.mcu32.firstq.common.bean.bo.StationAbnormalBO;
import com.mcu32.firstq.common.bean.bo.StationActionBO;
import com.mcu32.firstq.common.bean.bo.StationBO;
import com.mcu32.firstq.common.bean.bo.StationHistoryBO;
import com.mcu32.firstq.common.bean.bo.StationInspectionRecordBO;
import com.mcu32.firstq.common.bean.bo.StationRecordBO;
import com.mcu32.firstq.common.bean.bo.TowerBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.BaseDevice;
import com.mcu32.firstq.wechat.bean.DeviceInspectRecord;
import com.mcu32.firstq.wechat.bean.PhotoInfo;
import com.mcu32.firstq.wechat.bean.PhotoType;
import com.mcu32.firstq.wechat.bean.RequestParam;
import com.mcu32.firstq.wechat.bean.RoomInfo;
import com.mcu32.firstq.wechat.bean.StationInfo;
import com.mcu32.firstq.wechat.bean.StationRecord;
import com.mcu32.firstq.wechat.bean.TowerInfo;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.interceptor.AccessRequired;
import com.mcu32.firstq.wechat.service.IStationHistoryService;
import com.mcu32.firstq.wechat.util.ActionCommonMethod;
import com.mcu32.firstq.wechat.util.FirstQBeanUtils;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.SessionConstants;
import com.mcu32.firstq.wechat.util.ToolUtil;
import com.mcu32.firstq.wechat.util.WebContentConstants;
import com.mcu32.firstq.wechat.util.date.DateStyle;
import com.mcu32.firstq.wechat.util.date.DateUtil;

import com.mcu32.firstq.common.util.HttpClientUtil;

@Controller
@RequestMapping(value = "/station")
public class StationController extends BaseController {	
	private int calculatorDistance = Integer.parseInt(ToolUtil.getAppConfig("CalculatorDistance")); 
	private int actualDistance = Integer.parseInt(ToolUtil.getAppConfig("ActualDistance")); 
	
	@Autowired IStationHistoryService iStationHistoryService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/gotoStation",method=RequestMethod.GET)
	public String toAddWaitCheck(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		UserInfo user = this.getSessionUser(session);
		String province = "未知";
		String city = "未知";
		String county = "未知";
		String street = "";
		String streetNumber = "";
		String stationAddress = "";
		if(user != null) {
			String addr = user.getAddr();
			if(StringUtils.isNotEmpty(addr)) {
				String[] data = org.springframework.util.StringUtils.tokenizeToStringArray(addr, ",");
				if(data.length == 3){
					province = data[0];
					city = data[1];
					county = data[2];
					if(city != null && !"".equals(city) && city.indexOf("北京") != -1) {
						if(county != null && !"".equals(county)) {
							if(county.endsWith("区")) {
								city = "市辖区";
							} else if(county.endsWith("县")) {
								city = "县";
							}
						}
					}
				}else if(data.length == 4){
					province = data[0];
					city = data[1];
					county = data[2];
					street = data[3];
					if(city != null && !"".equals(city) && city.indexOf("北京") != -1) {
						if(county != null && !"".equals(county)) {
							if(county.endsWith("区")) {
								city = "市辖区";
							} else if(county.endsWith("县")) {
								city = "县";
							}
						}
					}
				}else if(data.length == 5){
					province = data[0];
					city = data[1];
					county = data[2];
					street = data[3];
					streetNumber = data[4];
					if(city != null && !"".equals(city) && city.indexOf("北京") != -1) {
						if(county != null && !"".equals(county)) {
							if(county.endsWith("区")) {
								city = "市辖区";
							} else if(county.endsWith("县")) {
								city = "县";
							}
						}
					}
				}
			}
		}
		stationAddress = street + streetNumber;
		String area = "";
		area = province + "/" + county;
		if(!"".equals(province) && !"".equals(city) && !city.equals(province)) {
			area = province + "/" + city + "/" +  county;
		}
		model.addAttribute("province", province);
		model.addAttribute("city", city);
		model.addAttribute("county", county);
		model.addAttribute("stationAddress", stationAddress);
		model.addAttribute("area", area);
		List<StationInfo> stationList = (List<StationInfo>) session.getAttribute(SessionConstants.STATIONLIST);
		if(stationList!=null){
			model.addAttribute("stationList",stationList);
		}
		String resultString = "accept/addAcceptStation";
		
		try {
			List<RegionalManagerBO> regionalManagerList = FirstQInterfaces.getIRegionalManagerService().getRegionalManagerByArea(province, city, county);
			if(regionalManagerList != null && regionalManagerList.size() > 0) {
				model.addAttribute("regionalManager",regionalManagerList.get(0));
			}
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		return resultString;
	}
	/**
	 * 
	 */
	
	@RequestMapping(value="/saveOrUpdateTower")
	public String saveOrUpdateTower(HttpSession session,HttpServletRequest request, ModelMap model, TowerBO tb){
		String res = "";
		String operateType = "新增铁塔";
		if(null==tb.getTowerId()) {
			
		} else {
			operateType = "修改铁塔";
		}
		try {
			StationInfo station =(StationInfo) session.getAttribute(SessionConstants.STATION);
			UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
			StationHistoryBO stationHistoryBO = new StationHistoryBO();
			stationHistoryBO.setUserId(user.getUserId());
			stationHistoryBO.setUserName(user.getUserName());
			stationHistoryBO.setOperateTime(new Date());
			stationHistoryBO.setOperateObject("tower");
			stationHistoryBO.setStationId(station.getStationId());
			stationHistoryBO.setJobId(user.getJobId());
			TowerBO tbold =FirstQInterfaces.getIStationService().getTowerByStationId(station.getStationId());
			try {
				if(StringUtils.isNotEmpty(tb.getTowerId())){
					stationHistoryBO.setOperateType("update");
					iStationHistoryService.saveStationHistory(tbold, tb, stationHistoryBO);
				}else{
					stationHistoryBO.setOperateType("insert");
					iStationHistoryService.saveStationHistory(null, tb, stationHistoryBO);
				}
			} catch (com.mcu32.firstq.wechat.exception.FirstQException e) {
				e.printStackTrace();
			}
			
			boolean towerSaveSuccess=FirstQInterfaces.getIStationService().saveOrUpdateTower(tb);
			model.addAttribute("status", towerSaveSuccess);
			if(towerSaveSuccess) {
				res = operateType + "成功";
			} else {
				res = operateType + "失败";
			}
		} catch (FirstQException e) {
			e.printStackTrace();
			res = operateType + "失败";
		}
		model.addAttribute("res",res);
		return "";
	}
	@RequestMapping(value="/saveOrUpdateRoom")
	public String saveOrUpdateRoom(HttpSession session,HttpServletRequest request, ModelMap model, RoomBO rb){
		String res = "";
		String operateType = "新增机房";
		if(null==rb.getRoomId() || ""==rb.getRoomId()) {
			
		} else {
			operateType = "修改机房";
		}
		try {
			StationInfo station =(StationInfo) session.getAttribute(SessionConstants.STATION);
			UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
			StationHistoryBO stationHistoryBO = new StationHistoryBO();
			stationHistoryBO.setUserId(user.getUserId());
			stationHistoryBO.setUserName(user.getUserName());
			stationHistoryBO.setOperateTime(new Date());
			stationHistoryBO.setOperateObject("room");
			stationHistoryBO.setStationId(station.getStationId());
			stationHistoryBO.setJobId(user.getJobId());
			RoomBO rbold =FirstQInterfaces.getIStationService().getRoomByStationId(station.getStationId());
			try {
				if(StringUtils.isNotEmpty(rb.getRoomId())){
					stationHistoryBO.setOperateType("update");
					iStationHistoryService.saveStationHistory(rbold, rb, stationHistoryBO);
				}else{
					stationHistoryBO.setOperateType("insert");
					iStationHistoryService.saveStationHistory(null, rb, stationHistoryBO);
				}
			} catch (com.mcu32.firstq.wechat.exception.FirstQException e) {
				e.printStackTrace();
			}
			rb.setStationId(station.getStationId());
			boolean roomSaveSuccess=FirstQInterfaces.getIStationService().saveOrUpdateRoom(rb);
			model.addAttribute("status", roomSaveSuccess);
			if(roomSaveSuccess) {
				res = operateType + "成功";
			} else {
				res = operateType + "失败";
			}
		} catch (FirstQException e) {
			e.printStackTrace();
			res = operateType + "失败";
		}
		model.addAttribute("res",res);
		return "";
	}
	
	@RequestMapping(value = "/add",method=RequestMethod.POST)
	public String addWaitCheck(RequestParam rp,StationInfo si, TowerInfo ti, RoomInfo ri,PhotoBO pi,HttpServletRequest request, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		//获取跳转页面
		String jobid = user.getJobId();
		String returnString = "redirect:/"+getPagePathByJobId(jobid)[1];
		//保存基站信息
		if(WebContentConstants.JOB_STATION_RAILWAY.equals(jobid)){//交维
			si.setCheckStatus("未验收");
		}else{
			si.setCheckStatus("存量站");
		}
		si.setStationId(getUUID());
		si.setPullingLevel("");
		si.setBatteryNum("0");
		si.setLastDate(new Date());
		si.setLastUser(user.getUserName());
		si.setLastUserId(user.getUserId());
		si.setCreateDate(new Date());
		si.setCreateUser(user.getUserName());
		si.setCreateUserId(user.getUserId());
		
		RoomBO riBO = new RoomBO();
		ri.setRoomId(getUUID());
		ri.setStationId(si.getStationId());
		BeanCopier.create(RoomInfo.class, RoomBO.class, false).copy(ri, riBO, null);
		
		TowerBO tiBO = new TowerBO();
		ti.setTowerId(getUUID());
		ti.setStationId(si.getStationId());
		BeanCopier.create(TowerInfo.class, TowerBO.class, false).copy(ti, tiBO, null);
		
		si.setRoomInfo(ri);
		si.setTowerInfo(ti);
		try {
			StationBO siBO = new StationBO();
			BeanCopier.create(StationInfo.class, StationBO.class, false).copy(si, siBO, null);
			siBO.setRoom(riBO);
			siBO.setTower(tiBO);
			FirstQInterfaces.getIStationService().saveStation(siBO);
			session.setAttribute(SessionConstants.STATION, si);
			String stationId = siBO.getStationId();
			//基站信息保存成功则上传照片信息
			if(org.apache.commons.lang.StringUtils.isEmpty(stationId)){
				returnString = FAILSJSON;
			}else{
				String towerId = ti.getTowerId();
				String roomId = ri.getRoomId();
				pi.setStationId(stationId);
				//遍历id数组
				String towerLocalIds = request.getParameter("towerImgs");
				String[] tArray = towerLocalIds.split(",");
				String roomLocalIds = request.getParameter("roomImgs");
				String[] rArray = roomLocalIds.split(",");
				if(towerLocalIds.length()>0){
					for(int i=0;i<tArray.length;i++){
						pi.setPhotoId(getUUID());
						pi.setPhotoName(getUUID());
						pi.setLocalId(tArray[i]);
						pi.setPhotoType("tower");
						pi.setRelateId(towerId);
						pi.setSqe(i);
						pi.setSubmitTime(new Date());
						pi.setStationId(stationId);
						pi.setCreateTime(new Date());
						pi.setSubmitTime(new Date());
						pi.setCreateUser(user.getUserName());
						pi.setCreateUserId(user.getUserId());
						pi.setLatitude(user.getLa());
						pi.setLongitude(user.getLo());
						pi.setFileLocation("");
						pi.setThumbLocation("");
						if(StringUtils.isEmpty(pi.getRemark())){
							pi.setRemark("");
						}
						pi.setStatus(WebContentConstants.STATUS_WAIT_UPLOAD);
						pi.setFileSize(0);
						boolean b = FirstQInterfaces.getIPhotoService().savePhotoInfo(pi);
						if(!b){
							returnString = FAILSJSON;
						}
					}
				}
				if(roomLocalIds.length()>0){
					for(int i=0;i<rArray.length;i++){
						pi.setPhotoId(getUUID());
						pi.setPhotoName(getUUID());
						pi.setLocalId(rArray[i]);
						pi.setPhotoType("room");
						pi.setRelateId(roomId);
						pi.setSqe(i);
						pi.setSubmitTime(new Date());
						pi.setStationId(stationId);
						pi.setCreateTime(new Date());
						pi.setSubmitTime(new Date());
						pi.setCreateUser(user.getUserName());
						pi.setCreateUserId(user.getUserId());
						pi.setLatitude(user.getLa());
						pi.setLongitude(user.getLo());
						pi.setFileLocation("");
						pi.setThumbLocation("");
						if(StringUtils.isEmpty(pi.getRemark())){
							pi.setRemark("");
						}
						pi.setStatus(WebContentConstants.STATUS_WAIT_UPLOAD);
						pi.setFileSize(0);
						boolean b = FirstQInterfaces.getIPhotoService().savePhotoInfo(pi);
						if(!b){
							returnString = FAILSJSON;
						}
					}
				}
			}
		} catch (Exception e) {
			LogUtil.info(e.getMessage(), e);
		}
		return returnString;
	}

	/**
	 * 基站列表
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/getNearbyList",method=RequestMethod.GET)
	public String getNearbyStationList(HttpServletRequest request, HttpSession session, ModelMap model) throws UnsupportedEncodingException{
		String jobId = request.getParameter("jobId");
		String la = request.getParameter("la");
		String lo = request.getParameter("lo");
		String addr=request.getParameter("addr");
		if(addr != null && !"".equals(addr)) {
			addr=URLDecoder.decode(addr,"UTF-8");
		}
		String screenWidth=request.getParameter("screenWidth");
		if(screenWidth != null && !"".equals(screenWidth)) {
			screenWidth = (Integer.parseInt(screenWidth) - 170) + "";
		} 
		UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
		user.setJobId(jobId);
		user.updateNotNull(null,null,lo,la);
		if(addr != null && !"".equals(addr)) {
			user.setAddr(addr);
		}
		if(screenWidth != null && !"".equals(screenWidth)) {
			user.setScreenWidth(screenWidth);
		}
		try {
			List<StationBO> stationBOList= FirstQInterfaces.getIStationService().getStationByLocation(Double.parseDouble(lo),Double.parseDouble(la), calculatorDistance, actualDistance);
			List<StationInfo> stationList = FirstQBeanUtils.getStationInfoList(stationBOList,(UserInfo)session.getAttribute(SessionConstants.SUSER));
			session.setAttribute(SessionConstants.STATIONLIST, stationList);
			model.addAttribute("role",user.getRole());
			model.addAttribute("stationList",stationList);
		} catch (Exception e) {
			LogUtil.error(e);
		}
		return "station/stationList";
	}
	
	/**
	 * 基站列表
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/gotoNearbyList",method=RequestMethod.GET)
	public String gotoNearbyList(HttpServletRequest request, HttpSession session, ModelMap model) throws UnsupportedEncodingException{
		UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
		String la = user.getLa();
		String lo = user.getLo();
		try {
			List<StationBO> stationBOList= FirstQInterfaces.getIStationService().getStationByLocation(Double.parseDouble(lo),Double.parseDouble(la), calculatorDistance, actualDistance);
			List<StationInfo> stationList = FirstQBeanUtils.getStationInfoList(stationBOList,(UserInfo)session.getAttribute(SessionConstants.SUSER));
			session.setAttribute(SessionConstants.STATIONLIST, stationList);
			model.addAttribute("stationList",stationList);
		} catch (Exception e) {
			LogUtil.error(e);
		}
		return "station/stationList";
	}
	
	/**
	 * 基站列表的ajax方法
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/getStationList",method=RequestMethod.POST)
	public String getStationList(HttpServletRequest request, HttpSession session, ModelMap model) throws UnsupportedEncodingException{
		
		String la = request.getParameter("la");
		String lo = request.getParameter("lo");
		UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
		user.updateNotNull(null,null,lo,la);
		try {
			List<StationBO> stationBOList= FirstQInterfaces.getIStationService().getStationByLocation(Double.parseDouble(lo),Double.parseDouble(la), calculatorDistance, actualDistance);
			List<StationInfo> stationList = FirstQBeanUtils.getStationInfoList(stationBOList,(UserInfo)session.getAttribute(SessionConstants.SUSER));
			session.setAttribute(SessionConstants.STATIONLIST, stationList);
			model.addAttribute("stationList",stationList);
		} catch (Exception e) {
			LogUtil.error(e);
		}
		return "";
	}
	
	/**
	 * 点击基站列表中的基站开始任务
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/entryJob",method=RequestMethod.GET)
	public String entryJob(HttpServletRequest request, HttpSession session, ModelMap model){
		String stationId = request.getParameter("stationId");
		List<StationInfo> stationList = (List<StationInfo>)session.getAttribute(SessionConstants.STATIONLIST);
		if(stationList != null){
			for(StationInfo station : stationList){
				if(stationId.equals(station.getStationId())){
					session.setAttribute(SessionConstants.STATION, station);
					break;
				}
			}
		}
		UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
		String jobId = user.getJobId();
		String[] pagePathArr=getPagePathByJobId(jobId);
		String resultStr = pagePathArr[0];
		return "redirect:/"+resultStr;
	}
	
	/**
	 * 基站详情
	 */
	@RequestMapping(value="/getStationDetail",method=RequestMethod.GET)
	public String getStationDetail(HttpServletRequest request, HttpSession session, ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		try {
			String openId=request.getParameter("openId");
			if(openId != null && !"".equals(openId)) {
				loginByOpenId(openId,session);
			}
			String jobId = request.getParameter("jobId");	
			String stationId = request.getParameter("stationId");
			if(jobId==null || "".equals(jobId)){
				jobId = "1";
			}
			model.addAttribute("jobId",jobId); 
			//设备信息
			UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
			List<BaseDevice> divicelist = ActionCommonMethod.getAllDevices(stationId, "",user);
			session.setAttribute(SessionConstants.SDEVICESLIST, divicelist);
			model.addAttribute("devicesList", divicelist);
			
			//异常隐患
			List<DeviceInspectRecord> deviceInspectRecords = new ArrayList<DeviceInspectRecord>();
			//获得最新一次巡检记录
			StationInspectionRecordBO s = FirstQInterfaces.getIInspectionStationService().getInspectionStation(stationId, "");
			if(s!=null && s.getInspectionId()!=null){
				//设备异常信息
				List<DeviceInspectionRecordBO> inspectionRecords = FirstQInterfaces.getIDeviceService().getInspectionRecordListByInspectionId(s.getInspectionId());
				if(inspectionRecords!=null && inspectionRecords.size()>0){
					for (DeviceInspectionRecordBO deviceInspectionRecordBO : inspectionRecords) {
						DeviceInspectRecord dir = new DeviceInspectRecord();
						BeanCopier.create(DeviceInspectionRecordBO.class, DeviceInspectRecord.class, false).copy(deviceInspectionRecordBO, dir, null);
						List<PhotoInfo> photoInfoList = new ArrayList<PhotoInfo>();
						if(deviceInspectionRecordBO.getPhotoList()!=null && deviceInspectionRecordBO.getPhotoList().size() > 0){
							for(PhotoBO pb : deviceInspectionRecordBO.getPhotoList()){
								PhotoInfo p = new PhotoInfo();
								BeanCopier.create(PhotoBO.class, PhotoInfo.class, false).copy(pb, p, null);
								photoInfoList.add(p);
							}
						}
						FirstqTool.convertPhotoPath(photoInfoList,(UserInfo)session.getAttribute(SessionConstants.SUSER));
						dir.setPhotoList(photoInfoList);
						if(dir.getDeviceName() == null || "".equals(dir.getDeviceName())) {
							dir.setDeviceName("铁塔");
						}
						if("null".equals(dir.getRemark())) {
							dir.setRemark("");
						}
						deviceInspectRecords.add(dir);
					}
					devicesExceptionStr(deviceInspectRecords);
				}
			}
			model.addAttribute("deviceInspectRecords", deviceInspectRecords);
			//基站信息
			StationInfo stationInfo = new StationInfo();
			StationBO stationBO = FirstQInterfaces.getIStationService().getStationById(stationId);
			BeanCopier.create(StationBO.class, StationInfo.class, false).copy(stationBO, stationInfo, null);
			List<PhotoInfo> photoInfoList = new ArrayList<PhotoInfo>();
			if(stationBO.getPhotoList()!=null && stationBO.getPhotoList().size()>0){
				for(PhotoBO pb : stationBO.getPhotoList()){
					PhotoInfo p = new PhotoInfo();
					BeanCopier.create(PhotoBO.class, PhotoInfo.class, false).copy(pb, p, null);
					photoInfoList.add(p);
				}
			}
			FirstqTool.convertPhotoPath(photoInfoList,(UserInfo)session.getAttribute(SessionConstants.SUSER));
			stationInfo.setPhotoList(photoInfoList);
			TowerInfo towerInfo = new TowerInfo();
			if(stationBO.getTower()!=null){
				BeanCopier.create(TowerBO.class, TowerInfo.class, false).copy(stationBO.getTower(), towerInfo, null);
			}
			RoomInfo roomInfo = new RoomInfo();
			if(stationBO.getRoom()!=null){
				BeanCopier.create(RoomBO.class, RoomInfo.class, false).copy(stationBO.getRoom(), roomInfo, null);
			}
			if(stationInfo!=null){
				//照片
				List<PhotoInfo> roomPhotoInfos = new ArrayList<PhotoInfo>();
				List<PhotoInfo> towerPhotoInfos = new ArrayList<PhotoInfo>();
				if(stationInfo.getPhotoList() != null && stationInfo.getPhotoList().size() > 0) {
					for(int i = 0; i < stationInfo.getPhotoList().size(); i++) {
						PhotoInfo photoInfo = stationInfo.getPhotoList().get(i);
						if(photoInfo.getPhotoType() != null && !"".equals(photoInfo.getPhotoType())) {
							if("room".equals(photoInfo.getPhotoType())) {
								roomPhotoInfos.add(photoInfo);
							}
							if("tower".equals(photoInfo.getPhotoType())) {
								towerPhotoInfos.add(photoInfo);
							}
						}
					}
				}
				if(towerPhotoInfos != null && towerPhotoInfos.size()>0){
					stationInfo.setThumbLocation(towerPhotoInfos.get(0).getThumbLocation());
				}else if(roomPhotoInfos != null && roomPhotoInfos.size()>0){
					stationInfo.setThumbLocation(roomPhotoInfos.get(0).getThumbLocation());
				}
				model.addAttribute("acceptStation", stationInfo);
				model.addAttribute("acceptRoom", roomInfo);
				model.addAttribute("acceptTower", towerInfo);
				model.addAttribute("roomPhotoInfos", roomPhotoInfos);
				model.addAttribute("towerPhotoInfos", towerPhotoInfos);
				request.setAttribute("searchStationHistoryFirst","0");
				searchStationHistory(request, session, model);
				//searchStationMotion(request, session, model);
				//调用根据基站ID查询停电信息
				List<RelationStationBo> powerCutLines = FirstQInterfaces.getIRobotInfoService().getPowerCutLinesByStationId(stationId);
				int powerCutLinesCount = 0;
				if(powerCutLines != null && powerCutLines.size() > 0) {
					powerCutLinesCount = powerCutLines.size();
				}
				model.addAttribute("powerCutLines", powerCutLines);
				model.addAttribute("powerCutLinesCount", powerCutLinesCount);
			}
			
		} catch (Exception e) {
			LogUtil.error(e.getMessage(),e);
		}
		return "station/stationDetail";
	}
	
	/**
	 * 修改基站页面
	 */
	@SuppressWarnings("unchecked")
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/toUpdateStation",method=RequestMethod.GET)
	public String toUpdateStation(HttpServletRequest request, HttpSession session, ModelMap model){
		setWechatConfig(request, model);
		try {
			String stationId = request.getParameter("stationId");	
			//基站信息
			StationInfo stationInfo = new StationInfo();
			StationBO stationBO = FirstQInterfaces.getIStationService().getStationById(stationId);
			BeanCopier.create(StationBO.class, StationInfo.class, false).copy(stationBO, stationInfo, null);
			List<PhotoInfo> photoInfoList = new ArrayList<PhotoInfo>();
			if(stationBO.getPhotoList()!=null && stationBO.getPhotoList().size()>0){
				for(PhotoBO pb : stationBO.getPhotoList()){
					PhotoInfo p = new PhotoInfo();
					BeanCopier.create(PhotoBO.class, PhotoInfo.class, false).copy(pb, p, null);
					photoInfoList.add(p);
				}
			}
			stationInfo.setPhotoList(photoInfoList);
			TowerInfo towerInfo = new TowerInfo();
			if(stationBO.getTower()!=null){
				BeanCopier.create(TowerBO.class, TowerInfo.class, false).copy(stationBO.getTower(), towerInfo, null);
			}
			RoomInfo roomInfo = new RoomInfo();
			if(stationBO.getRoom()!=null){
				BeanCopier.create(RoomBO.class, RoomInfo.class, false).copy(stationBO.getRoom(), roomInfo, null);
			}
			if(stationInfo!=null){
				model.addAttribute("acceptStation", stationInfo);
				model.addAttribute("acceptRoom", roomInfo);
				model.addAttribute("acceptTower", towerInfo);
				
				String regionalManager = stationInfo.getRegionalManager();
				String managerPhone = stationInfo.getManagerPhone();
				String province = stationInfo.getProvinceId();
				String city = stationInfo.getCityId();
				String county = stationInfo.getCountyId();
				if(regionalManager == null || "".equals(regionalManager)) {
					try {
						List<RegionalManagerBO> regionalManagerList = FirstQInterfaces.getIRegionalManagerService().getRegionalManagerByArea(province, city, county);
						if(regionalManagerList != null && regionalManagerList.size() > 0) {
							RegionalManagerBO regionalManagerBO = regionalManagerList.get(0);
							stationInfo.setRegionalManager(regionalManagerBO.getUserName());
							if(managerPhone == null || "".equals(managerPhone)) {
								stationInfo.setManagerPhone(regionalManagerBO.getPhone());
								managerPhone = regionalManagerBO.getPhone();
							}
						}
					} catch (FirstQException e) {
						e.printStackTrace();
					}
				}
				
				if(managerPhone == null || "".equals(managerPhone)) {
					try {
						List<RegionalManagerBO> regionalManagerList = FirstQInterfaces.getIRegionalManagerService().getRegionalManagerByArea(province, city, county);
						if(regionalManagerList != null && regionalManagerList.size() > 0) {
							RegionalManagerBO regionalManagerBO = regionalManagerList.get(0);
							stationInfo.setManagerPhone(regionalManagerBO.getPhone());
							if(regionalManager == null || "".equals(regionalManager)) {
								stationInfo.setRegionalManager(regionalManagerBO.getUserName());
							}
						}
					} catch (FirstQException e) {
						e.printStackTrace();
					}
				}
				
				//照片
				List<PhotoInfo> roomPhotoInfos = new ArrayList<PhotoInfo>();
				List<PhotoInfo> towerPhotoInfos = new ArrayList<PhotoInfo>();
				if(stationInfo.getPhotoList() != null && stationInfo.getPhotoList().size() > 0) {
					for(int i = 0; i < stationInfo.getPhotoList().size(); i++) {
						PhotoInfo photoInfo = stationInfo.getPhotoList().get(i);
						if(photoInfo.getPhotoType() != null && !"".equals(photoInfo.getPhotoType())) {
							if("room".equals(photoInfo.getPhotoType())) {
								roomPhotoInfos.add(photoInfo);
							}
							if("tower".equals(photoInfo.getPhotoType())) {
								towerPhotoInfos.add(photoInfo);
							}
						}
					}
				}
				FirstqTool.convertPhotoPath(roomPhotoInfos,(UserInfo)session.getAttribute(SessionConstants.SUSER));
				FirstqTool.convertPhotoPath(towerPhotoInfos,(UserInfo)session.getAttribute(SessionConstants.SUSER));
				model.addAttribute("roomPhotoInfos", roomPhotoInfos);
				model.addAttribute("towerPhotoInfos", towerPhotoInfos);
				
				List<StationInfo> stationList = (List<StationInfo>) session.getAttribute(SessionConstants.STATIONLIST);
				if(stationList!=null){
					model.addAttribute("stationList",stationList);
					for(StationInfo station : stationList){
						if(stationId.equals(station.getStationId())){
							session.setAttribute(SessionConstants.STATION, station);
							break;
						}
					}
				}
				LogUtil.info(".......................................................stationInfo location is "+stationInfo.getLongitude()+","+stationInfo.getLatitude());
			}
		} catch (Exception e) {
			LogUtil.error(e.getMessage(),e);
		}
		UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
		LogUtil.info(".......................................................user location is "+user.getLo()+","+user.getLa());
		
		
		model.addAttribute("user",user);
		return "station/updateStation";
	}
	
	/**
	 * 修改基站
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/updateStation",method=RequestMethod.POST)
	public String updateStation(StationInfo si, TowerInfo ti, RoomInfo ri,PhotoBO pi,HttpServletRequest request, HttpSession session, ModelMap model){
		UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
		//String returnString = "redirect:/" + getPagePathByJobId(user.getJobId())[1];
		String returnString = "redirect:/station/gotoNearbyList";
		StationBO stationBO = new StationBO();
		BeanCopier.create(StationInfo.class, StationBO.class, false).copy(si, stationBO, null);
		RoomBO roomBO = new RoomBO();
		BeanCopier.create(RoomInfo.class, RoomBO.class, false).copy(ri, roomBO, null);
		TowerBO towerBO = new TowerBO();
		BeanCopier.create(TowerInfo.class, TowerBO.class, false).copy(ti, towerBO, null);
		try {
			//设置操作记录的属性
			StationHistoryBO stationHistoryBO = new StationHistoryBO();
			stationHistoryBO.setUserId(user.getUserId());
			stationHistoryBO.setUserName(user.getUserName());
			stationHistoryBO.setOperateTime(new Date());
			stationHistoryBO.setOperateType("update");
			stationHistoryBO.setOperateObject("station");
			stationHistoryBO.setStationId(si.getStationId());
			stationHistoryBO.setJobId(user.getJobId());
			//根据基站id查询，与提交过来的基站相比较
			StationBO oldStationBO = FirstQInterfaces.getIStationService().getStationById(stationBO.getStationId());
			iStationHistoryService.saveStationHistory(oldStationBO, stationBO, stationHistoryBO);
			//与机房相比较
			RoomBO oldRoomBO = oldStationBO.getRoom();
			if(oldRoomBO == null){
				stationHistoryBO.setOperateType("insert");
			}
			stationHistoryBO.setOperateObject("room");
			iStationHistoryService.saveStationHistory(oldRoomBO, roomBO, stationHistoryBO);
			//与铁塔相比较
			TowerBO oldTowerBO = oldStationBO.getTower();
			stationHistoryBO.setOperateObject("tower");
			iStationHistoryService.saveStationHistory(oldTowerBO, towerBO, stationHistoryBO);
			stationBO.setLastUserId(user.getUserId());
			stationBO.setLastUser(user.getUserName());
			stationBO.setLastDate(new Date());
			stationBO.setRoom(roomBO);
			stationBO.setTower(towerBO);
			boolean ba = FirstQInterfaces.getIStationService().updateStation(stationBO);
			if(ba){
				String lo = session.getAttribute(SessionConstants.SUSER) != null ? ((UserInfo)session.getAttribute(SessionConstants.SUSER)).getLo() : null;
				String la = session.getAttribute(SessionConstants.SUSER) != null ? ((UserInfo)session.getAttribute(SessionConstants.SUSER)).getLa() : null;
				List<StationBO> stationBOList= FirstQInterfaces.getIStationService().getStationByLocation(Double.parseDouble(lo),Double.parseDouble(la), calculatorDistance, actualDistance);
				List<StationInfo> stationList = FirstQBeanUtils.getStationInfoList(stationBOList,(UserInfo)session.getAttribute(SessionConstants.SUSER));
				session.setAttribute(SessionConstants.STATIONLIST, stationList);
				String towerId = stationBO.getTower().getTowerId();
				String roomId = stationBO.getRoom().getRoomId();
				pi.setStationId(si.getStationId());
				//遍历id数组
				String towerLocalIds = request.getParameter("towerLocalIds");
				String[] tArray = towerLocalIds.split(",");
				String roomLocalIds = request.getParameter("roomLocalIds");
				String[] rArray = roomLocalIds.split(",");
				if(towerLocalIds.length()>0){
					for(int i=0;i<tArray.length;i++){
						pi.setPhotoId(getUUID());
						pi.setPhotoName(getUUID());
						pi.setLocalId(tArray[i]);
						pi.setPhotoType("tower");
						pi.setRelateId(towerId);
						pi.setSqe(i);
						pi.setSubmitTime(new Date());
						pi.setCreateTime(new Date());
						pi.setSubmitTime(new Date());
						pi.setCreateUser(user.getUserName());
						pi.setCreateUserId(user.getUserId());
						pi.setLatitude(user.getLa());
						pi.setLongitude(user.getLo());
						pi.setFileLocation("");
						pi.setThumbLocation("");
						if(StringUtils.isEmpty(pi.getRemark())){
							pi.setRemark("");
						}
						pi.setStatus(WebContentConstants.STATUS_WAIT_UPLOAD);
						pi.setFileSize(0);
						boolean b = FirstQInterfaces.getIPhotoService().savePhotoInfo(pi);
						if(!b){
							returnString = FAILSJSON;
						}
					}
				}
				if(roomLocalIds.length()>0){
					for(int i=0;i<rArray.length;i++){
						pi.setPhotoId(getUUID());
						pi.setPhotoName(getUUID());
						pi.setLocalId(rArray[i]);
						pi.setPhotoType("room");
						pi.setRelateId(roomId);
						pi.setSqe(i);
						pi.setSubmitTime(new Date());
						pi.setCreateTime(new Date());
						pi.setSubmitTime(new Date());
						pi.setCreateUser(user.getUserName());
						pi.setCreateUserId(user.getUserId());
						pi.setLatitude(user.getLa());
						pi.setLongitude(user.getLo());
						pi.setFileLocation("");
						pi.setThumbLocation("");
						if(StringUtils.isEmpty(pi.getRemark())){
							pi.setRemark("");
						}
						pi.setStatus(WebContentConstants.STATUS_WAIT_UPLOAD);
						pi.setFileSize(0);
						boolean b = FirstQInterfaces.getIPhotoService().savePhotoInfo(pi);
						if(!b){
							returnString = FAILSJSON;
						}
					}
				}
			}
		} catch (Exception e) {
			LogUtil.info(e.getMessage(), e);
		}
		session.setAttribute(SessionConstants.STATION, si);
		return returnString;
	}
	
	/**
	 * 删除基站
	 */
	@RequestMapping(value="/deleteStation",method=RequestMethod.GET)
	public String deleteStation(HttpServletRequest request, HttpSession session, ModelMap model){
		String stationId = request.getParameter("stationId");
		boolean b = false;
		try {
			if(stationId!=null && !"".equals(stationId)){
				b = FirstQInterfaces.getIStationService().deleteById(stationId);
			}			
		} catch (Exception e) {
			LogUtil.info(e.getMessage(), e);
		}
		model.addAttribute("succ", b);
		return "";

	}
	
	/**
	 * 添加工作笔记
	 */
	@RequestMapping(value="/addWorkNote",method=RequestMethod.POST)
	public String addWorkNote(PhotoInfo pi, StationRecord sr, HttpServletRequest request, HttpSession session, ModelMap model){
		String returnString = "redirect:/station/getStationDetail?jobId=4&stationId=" + sr.getStationId();
		try {
			UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
			sr.setCreateTime(new Date());
			sr.setCreateUserId(user.getUserId());
			sr.setCreateUserName(user.getUserName());
			StationRecordBO stationRecordBO = new StationRecordBO();
			FirstQBeanUtils.getTarget(sr, stationRecordBO);
			String recordId = "";
			recordId = FirstQInterfaces.getIStationService().saveStationRecord(stationRecordBO);
			if(recordId!=null && !"".equals(recordId)){
				sr.setRecordId(recordId);
				pi.setStationId(sr.getStationId());
				//遍历id数组
				String workNoteLocalIds = request.getParameter("workNoteLocalIds");
				String[] tArray = workNoteLocalIds.split(",");
				if(workNoteLocalIds.length()>0){
					for(int i=0;i<tArray.length;i++){
						pi.setFileLocation("");
						pi.setThumbLocation("");
						pi.setFileSize(0);
						pi.setRemark("");
						pi.setPhotoId(getUUID());
						pi.setPhotoName(getUUID());
						pi.setLocalId(tArray[i]);
						pi.setPhotoType("workNote");
						pi.setRelateId(sr.getRecordId());
						pi.setCreateTime(new Date());
						pi.setSubmitTime(new Date());
						pi.setCreateUser(user.getUserName());
						pi.setCreateUserId(user.getUserId());
						pi.setLatitude(user.getLa());
						pi.setLongitude(user.getLo());
						pi.setStatus(WebContentConstants.STATUS_WAIT_UPLOAD);
						PhotoBO photoBO = new PhotoBO();
						FirstQBeanUtils.getTarget(pi, photoBO);
						boolean b;
						b = FirstQInterfaces.getIPhotoService().savePhotoInfo(photoBO);
						if(!b){
							returnString = FAILSJSON;
						}
					}
				}
				
			} else if(org.apache.commons.lang.StringUtils.isEmpty(sr.getStationId())){
				returnString = FAILSJSON;
			}
		} catch (com.mcu32.firstq.common.exception.FirstQException e) {
			LogUtil.info(e.getMessage(), e);
		}
		return returnString;
	}
	
	/**
	 * 通过jobid判断页面的路径
	 * @param jobId
	 * @return String[0]=toPage; 跳转的页面
	 * <br>String[1]=addStationReturnPage. 添加基站完成之后跳转的页面
	 */
	private String[] getPagePathByJobId(String jobId){
		String toPage="";
		String addStationReturnPage="";
		if(WebContentConstants.JOB_STATION_INSPECTION.equals(jobId)){//巡检
			toPage= "inspection/readyStartTask";
			addStationReturnPage= "inspection/readyStartTask";
		}else if(WebContentConstants.JOB_STATION_RAILWAY.equals(jobId)){//新站验收
			toPage= "acceptItem/checkItem";
			addStationReturnPage= "acceptItem/checkItem";
		}else if(WebContentConstants.JOB_PARAMETER_GENERAL.equals(jobId)){//参数普调
			toPage= "parameter/setParameter";
			addStationReturnPage= "parameter/setParameter";
		}else if(WebContentConstants.JOB_GENERATE_POWER.equals(jobId)){//发电
			toPage= "generatePower/electricIndex";
			addStationReturnPage= "generatePower/electricIndex";
		}else if(WebContentConstants.JOB_DISCHARGE.equals(jobId)){//放电测试
			toPage= "discharge/readyStart";
			addStationReturnPage= "discharge/readyStart";
		}else if(WebContentConstants.JOB_STATION_QUARTER_INSPECTION.equals(jobId)){//季度巡检
			toPage= "quarterInspection/readyStartTask";
			addStationReturnPage= "quarterInspection/readyStartTask";
		}
		
		
		return new String[]{toPage,addStationReturnPage};
	}
	
	@RequestMapping(value="/goWorkNoteDetail",method=RequestMethod.GET)
	public String goWorkNoteDetail(HttpServletRequest request, HttpSession session, ModelMap model) throws UnsupportedEncodingException{
		String recordId = request.getParameter("recordId");
		StationRecord resultStationRecord = new StationRecord();
		try {
			StationRecordBO resultStationRecordBO = FirstQInterfaces.getIStationService().getStationRecordById(recordId);
			if(resultStationRecordBO != null) {
				FirstQBeanUtils.getTarget(resultStationRecordBO, resultStationRecord);
				List<PhotoBO> photoBOs = resultStationRecordBO.getPhotoList();
				List<PhotoInfo> photoInfos = new ArrayList<PhotoInfo>();
				if(photoBOs != null && photoBOs.size() > 0) {
					FirstQBeanUtils.getPhotoInfoList(photoBOs, photoInfos);
				}
				if(photoInfos != null && photoInfos.size() > 0) {
					FirstqTool.convertPhotoPath(photoInfos,(UserInfo)session.getAttribute(SessionConstants.SUSER));
					resultStationRecord.setPhotoList(photoInfos);
				}
			}
		} catch (com.mcu32.firstq.common.exception.FirstQException e) {
			LogUtil.info(e.getMessage(), e);
		}
		UserInfo userInfo = (UserInfo)session.getAttribute(SessionConstants.SUSER);
		if(userInfo != null) {
			model.addAttribute("token", userInfo.getToken());
		}
		model.addAttribute("stationRecord",resultStationRecord);
		return "station/workNoteDetail";
	}
	
	@RequestMapping(value="/goExceptionInfoDetail",method=RequestMethod.GET)
	public String goExceptionInfoDetail(HttpServletRequest request, HttpSession session, ModelMap model) throws UnsupportedEncodingException{
		String recordId = request.getParameter("recordId");
		StationRecord resultStationRecord = new StationRecord();
		try {
			StationRecordBO resultStationRecordBO = FirstQInterfaces.getIStationService().getStationRecordById(recordId);
			if(resultStationRecordBO != null) {
				FirstQBeanUtils.getTarget(resultStationRecordBO, resultStationRecord);
				List<PhotoBO> photoBOs = resultStationRecordBO.getPhotoList();
				List<PhotoInfo> photoInfos = new ArrayList<PhotoInfo>();
				if(photoBOs != null && photoBOs.size() > 0) {
					FirstQBeanUtils.getPhotoInfoList(photoBOs, photoInfos);
				}
				if(photoInfos != null && photoInfos.size() > 0) {
					FirstqTool.convertPhotoPath(photoInfos,(UserInfo)session.getAttribute(SessionConstants.SUSER));
					resultStationRecord.setPhotoList(photoInfos);
				}
			}
		} catch (com.mcu32.firstq.common.exception.FirstQException e) {
			LogUtil.info(e.getMessage(), e);
		}
		UserInfo userInfo = (UserInfo)session.getAttribute(SessionConstants.SUSER);
		if(userInfo != null) {
			model.addAttribute("token", userInfo.getToken());
		}
		model.addAttribute("stationRecord",resultStationRecord);
		return "station/exceptionInfoDetail";
	}
	
	@RequestMapping(value = "/deleteStationRecord",method=RequestMethod.GET)
	public String deleteStationRecord(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		String recordId = request.getParameter("recordId");
		String stationId = request.getParameter("stationId");
		boolean flag = false;
		try {
			flag = FirstQInterfaces.getIStationService().deleteStationRecordById(recordId);
			if(flag) {
				//删除记录成功，则删除该记录上传的照片
				FirstQInterfaces.getIPhotoService().deletePhotoByRelateId(recordId);
			}
		} catch (com.mcu32.firstq.common.exception.FirstQException e) {
			LogUtil.info(e.getMessage(), e);
		}
		return "redirect:/station/getStationDetail?jobId=4&stationId=" + stationId;
		
	}
	
	@RequestMapping(value="/goAddWorkNote",method=RequestMethod.GET)
	public String goAddWorkNote(HttpServletRequest request, HttpSession session, ModelMap model) throws UnsupportedEncodingException{
		setWechatConfig(request, model);
		String stationId = request.getParameter("stationId");
		model.addAttribute("stationId",stationId);
		return "station/addWorkNote";
	}
	
	public void devicesExceptionStr(List<DeviceInspectRecord> deviceInspectRecords){
		for(DeviceInspectRecord d : deviceInspectRecords){
			if(d.getAbnormalCode()!=null && !"".equals(d.getAbnormalCode())){
				StringBuffer exceptionStr = new StringBuffer();
				String str[] = d.getAbnormalCode().split(",");
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
					}
				}
				if (',' == exceptionStr.charAt(exceptionStr.length() - 1)){
					exceptionStr = exceptionStr.deleteCharAt(exceptionStr.length() - 1); 
				}
				d.setAbnormalCode(exceptionStr.toString());
				exceptionStr=null;
			}else{
				d.setAbnormalCode("异常类型未定");
			}
		}
	}
	
	@RequestMapping(value="/goToSearchStation",method=RequestMethod.GET)
	public String goToSearchStation(HttpServletRequest request, HttpSession session, ModelMap model) throws UnsupportedEncodingException{
		return "station/searchStation";
	}
	
	@RequestMapping(value="/searchStation",method=RequestMethod.POST)
	public String searchStation(HttpServletRequest request, HttpSession session,ModelMap model){
		String provinceId = "";
		String cityId = "";
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		cityId = user.getCityId();
		provinceId = user.getProvinceId();
//		if(user != null) {
//			cityId = user.getCityId();
//			String provinceId = user.getProvinceId();
//			if(provinceId.indexOf("北京") != -1 || provinceId.indexOf("上海") != -1 || provinceId.indexOf("天津") != -1 || provinceId.indexOf("重庆") != -1 ) {
//				cityId = provinceId;
//			}
//		}
		String stationName = request.getParameter("stationName");
		String pageCurrent = request.getParameter("pageCurrent");
		int pageCu = 1;
		if(pageCurrent != null && !"".equals(pageCurrent)) {
			pageCu = Integer.parseInt(pageCurrent);
		}
		try {
			Map<String, String> queryCondition = new HashMap<String, String>();
			queryCondition.put("stationNameOrDH", stationName);
			queryCondition.put("provinceId", provinceId);
			queryCondition.put("cityId", cityId);
			List<StationBO> stationBOList= FirstQInterfaces.getIStationService().getSearchStationsList(queryCondition, 10, pageCu);
			List<StationInfo> stationList = FirstQBeanUtils.getStationInfoList(stationBOList,null);
			session.setAttribute(SessionConstants.STATIONLIST, stationList);
			model.addAttribute("stationList",stationList);
			String totalRecords= FirstQInterfaces.getIStationService().getStationCount(queryCondition) + "";
			model.addAttribute("totalRecords",totalRecords);
		} catch (Exception e) {
			LogUtil.error(e);
		}
		return "";
	}
	
	@RequestMapping(value="/getStationAbnormal")
	public String getStationAbnormal(HttpServletRequest request, HttpSession session, ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String stationId = request.getParameter("stationId");
		String url = request.getParameter("url");
		StationAbnormalBO tower = new StationAbnormalBO();
		StationAbnormalBO room = new StationAbnormalBO();
		try {
			List<StationAbnormalBO> list = FirstQInterfaces.getIStationService().getAbnormalByStationId(stationId);
			if(list.size()>0){
				for(StationAbnormalBO sa : list){
					if("tower".equals(sa.getRoomTower())){
						tower = sa;
					}else{
						room = sa;
					}
				}
			}
			String[] relateIds = {tower.getRecordId(),room.getRecordId()};
			Map<String, List<PhotoBO>> photoMap = FirstQInterfaces.getIPhotoService().getPhotoByRelateIds(relateIds);
			List<PhotoInfo> towerPhotoInfos = new ArrayList<PhotoInfo>();
			List<PhotoInfo> roomPhotoInfos = new ArrayList<PhotoInfo>();
			if(photoMap!=null){
				List<PhotoBO> towerPhotoBOs = photoMap.get(tower.getRecordId());
				if(towerPhotoBOs!=null && towerPhotoBOs.size()>0){
					for(PhotoBO pb : towerPhotoBOs){
						PhotoInfo p = new PhotoInfo();
						BeanCopier.create(PhotoBO.class, PhotoInfo.class, false).copy(pb, p, null);
						towerPhotoInfos.add(p);
					}
				}
				FirstqTool.convertPhotoPath(towerPhotoInfos,(UserInfo)session.getAttribute(SessionConstants.SUSER));
				
				List<PhotoBO> roomPhotoBOs = photoMap.get(room.getRecordId());
				if(roomPhotoBOs!=null && roomPhotoBOs.size()>0){
					for(PhotoBO pb : roomPhotoBOs){
						PhotoInfo p = new PhotoInfo();
						BeanCopier.create(PhotoBO.class, PhotoInfo.class, false).copy(pb, p, null);
						roomPhotoInfos.add(p);
					}
				}
				FirstqTool.convertPhotoPath(roomPhotoInfos,(UserInfo)session.getAttribute(SessionConstants.SUSER));
			}
			model.addAttribute("towerPhotoInfos",towerPhotoInfos);
			model.addAttribute("roomPhotoInfos",roomPhotoInfos);
		} catch (FirstQException e) {
			LogUtil.error(e);
		}
		model.addAttribute("stationId",stationId);
		model.addAttribute("url",url);
		model.addAttribute("tower",tower);
		model.addAttribute("room",room);
		return "station/stationExceptionReport";
	}
	
	/**
	 * 新增或修改基站和铁塔的异常信息
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value = "/saveOrUpdatestationAbnormal", method = RequestMethod.POST)
	public String saveOrUpdatestationAbnormal(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
		String url = request.getParameter("url");
		String[] abnormalCode = request.getParameterValues("abnormalCode");
		String ab="";
		StringBuilder abnormalCodeB=new StringBuilder();
		if(null!=abnormalCode && abnormalCode.length>0){
			for(String str:abnormalCode){
				abnormalCodeB.append(","+str);
			}
			ab=abnormalCodeB.substring(1);
		}
		String towerLocalIds = request.getParameter("towerLocalIds");
		String remark = request.getParameter("remark");
		String[] abnormalCode1 = request.getParameterValues("abnormalCode1");
		String ab1="";
		StringBuilder abnormalCodeB1=new StringBuilder();
		if(null!=abnormalCode1 && abnormalCode1.length>0){
			for(String str:abnormalCode1){
				abnormalCodeB1.append(","+str);
			}
			ab1=abnormalCodeB1.substring(1);
		}
		String roomLocalIds = request.getParameter("roomLocalIds");
		String remark1 = request.getParameter("remark1");
		String stationId = request.getParameter("stationId");
		String towerrecordId = request.getParameter("towerrecordId");
		String roomrecordId = request.getParameter("roomrecordId");
		//铁塔信息
		StationAbnormalBO saBo = new StationAbnormalBO();
		saBo.setAbnormalContent(ab);
		saBo.setStationId(stationId);
		saBo.setRemark(remark);
		saBo.setRoomTower("tower");
		saBo.setCreateTime(new Date());
		saBo.setLastTime(new Date());
		saBo.setCreateUserid(user.getUserId());
		saBo.setCreateUser(user.getUserName());
		saBo.setLastUserid(user.getUserId());
		saBo.setLastUser(user.getUserName());
		//机房信息
		StationAbnormalBO saBo1 = new StationAbnormalBO();
		saBo1.setAbnormalContent(ab1);
		saBo1.setStationId(stationId);
		saBo1.setRemark(remark1);
		saBo1.setRoomTower("room");
		saBo1.setCreateTime(new Date());
		saBo1.setLastTime(new Date());
		saBo1.setCreateUserid(user.getUserId());
		saBo1.setCreateUser(user.getUserName());
		saBo1.setLastUserid(user.getUserId());
		saBo1.setLastUser(user.getUserName());
		//判断是否是新增还是修改
		try {
			if(towerrecordId == null || "".equals(towerrecordId)){
				saBo.setRecordId(getUUID());
				saBo1.setRecordId(getUUID());
				FirstQInterfaces.getIStationService().saveStationAbnormal(saBo);
				FirstQInterfaces.getIStationService().saveStationAbnormal(saBo1);
				boolean photoSaveSuccess = saveWaitUploadPhoto(towerLocalIds,stationId,saBo.getRecordId(),"towerExcept",(UserInfo)session.getAttribute(SessionConstants.SUSER));//保存照片
				if(!photoSaveSuccess) return ERRORPAGE;
				boolean photoSaveSuccess1 = saveWaitUploadPhoto(roomLocalIds,stationId,saBo1.getRecordId(),"roomExcept",(UserInfo)session.getAttribute(SessionConstants.SUSER));//保存照片
				if(!photoSaveSuccess1) return ERRORPAGE;
			}else{
				saBo.setRecordId(towerrecordId);
				saBo1.setRecordId(roomrecordId);
				FirstQInterfaces.getIStationService().updateStationAbnormal(saBo);
				FirstQInterfaces.getIStationService().updateStationAbnormal(saBo1);
				boolean photoSaveSuccess = saveWaitUploadPhoto(towerLocalIds,stationId,saBo.getRecordId(),"towerExcept",(UserInfo)session.getAttribute(SessionConstants.SUSER));//保存照片
				if(!photoSaveSuccess) return ERRORPAGE;
				boolean photoSaveSuccess1 = saveWaitUploadPhoto(roomLocalIds,stationId,saBo1.getRecordId(),"roomExcept",(UserInfo)session.getAttribute(SessionConstants.SUSER));//保存照片
				if(!photoSaveSuccess1) return ERRORPAGE;
			}
		} catch (FirstQException e) {
			LogUtil.error(e);
		}
		
		return "redirect:" + url +"?stationId=" + stationId;
	}
	
	/**保存照片*/
	private boolean saveWaitUploadPhoto(String deviceImgs,String stationId,String relateId,String deviceType,UserInfo user){
		if(StringUtils.isNotEmpty(deviceImgs)){
			Date date = new Date();
			String[] deviceImgsArry = deviceImgs.split(",");
			PhotoInfo pi = new PhotoInfo();
			PhotoBO photoBO = new PhotoBO();
			int sqe = 1;
			for(String imgId : deviceImgsArry){
				pi.setPhotoId(getUUID());
				pi.setPhotoName(getUUID());
				pi.setLocalId(imgId);
				pi.setPhotoType(deviceType);
				pi.setSqe(sqe);
				pi.setSubmitTime(date);
				pi.setStationId(stationId);
				pi.setRelateId(relateId);
				pi.setCreateTime(date);
				pi.setSubmitTime(date);
				pi.setCreateUser(user.getUserName());
				pi.setCreateUserId(user.getUserId());
				pi.setLatitude(user.getLa());
				pi.setLongitude(user.getLo());
				pi.setFileLocation("");
				pi.setThumbLocation("");
				pi.setFileSize(0);
				if(StringUtils.isEmpty(pi.getRemark())){
					pi.setRemark("");
				}
				pi.setStatus(WebContentConstants.STATUS_WAIT_UPLOAD);
				sqe++;
				BeanCopier.create(PhotoInfo.class, PhotoBO.class, false).copy(pi, photoBO, null);
				try {
					FirstQInterfaces.getIPhotoService().savePhotoInfo(photoBO);
				} catch (Exception e) {
					LogUtil.error(e);
					return false;
				}
			}
		}
		return true;
	}
	
	@RequestMapping(value="/searchStationHistory",method=RequestMethod.POST)
	public String searchStationHistory(HttpServletRequest request, HttpSession session,ModelMap model){
		String pageCurrent = request.getParameter("pageCurrent");
		String stationId = request.getParameter("stationId");
		int pageCu = 1;
		if(pageCurrent != null && !"".equals(pageCurrent)) {
			pageCu = Integer.parseInt(pageCurrent);
		}
		Map<String, String> queryCondition = new HashMap<String, String>();
		queryCondition.put("stationId", stationId);
		try {
			List<StationHistoryBO> stationHistoryBOs = FirstQInterfaces.getIStationHistoryService().stationHistoryList(queryCondition, 10, pageCu);
			for(StationHistoryBO stationHistoryBO : stationHistoryBOs) {
				Date date = stationHistoryBO.getOperateTime();
				stationHistoryBO.setOperateTimeDesc(DateUtil.DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS_EN));
				stationHistoryBO.setOperateType(PhotoType.getName(stationHistoryBO.getOperateType()));
				stationHistoryBO.setOperateObject(PhotoType.getName(stationHistoryBO.getOperateObject()));
				if(stationHistoryBO.getOperateProperty() != null && !"".equals(stationHistoryBO.getOperateProperty())) {
					stationHistoryBO.setOperateProperty(PhotoType.getName(stationHistoryBO.getOperateProperty()));
				}
				String tempDateOld = stationHistoryBO.getOldValue();
				if(tempDateOld != null && !"".equals(tempDateOld) && tempDateOld.indexOf("CST") != -1 && tempDateOld.indexOf(":") != -1) {
					Date date1 = new Date(tempDateOld);
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					String sDate=sdf.format(date1);
					stationHistoryBO.setOldValue(sDate);
				}
				
				String tempDateNew = stationHistoryBO.getNewValue();
				if(tempDateNew != null && !"".equals(tempDateNew) && tempDateNew.indexOf("CST") != -1 && tempDateNew.indexOf(":") != -1) {
					Date date1 = new Date(tempDateNew);
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					String sDate=sdf.format(date1);
					stationHistoryBO.setNewValue(sDate);
				}
				
				String operateProperty = stationHistoryBO.getOperateProperty();
				if("备注".equals(operateProperty)) {
					if(stationHistoryBO.getOldValue() != null && !"".equals(stationHistoryBO.getOldValue())) {
						stationHistoryBO.setOldValue(PhotoType.getName(stationHistoryBO.getOldValue()));
					}
					if(stationHistoryBO.getNewValue() != null && !"".equals(stationHistoryBO.getNewValue())) {
						stationHistoryBO.setNewValue(PhotoType.getName(stationHistoryBO.getNewValue()));
					}
				}
				if("基站经度".equals(operateProperty) || "基站纬度".equals(operateProperty)) {
					if((stationHistoryBO.getOldValue().length() - stationHistoryBO.getOldValue().indexOf(".")) >= 5) {
						stationHistoryBO.setOldValue(stationHistoryBO.getOldValue().substring(0, stationHistoryBO.getOldValue().indexOf(".") + 5));
					}
					
					if((stationHistoryBO.getNewValue().length() - stationHistoryBO.getNewValue().indexOf(".")) >= 5) {
						stationHistoryBO.setNewValue(stationHistoryBO.getNewValue().substring(0, stationHistoryBO.getNewValue().indexOf(".") + 5));
					}
					
					
				}
				String operateSrc = stationHistoryBO.getOperateSrc();
				if("wechat".equals(operateSrc)) {
					stationHistoryBO.setOperateSrc("手持端");
				} else if("pc".equals(operateSrc)) {
					stationHistoryBO.setOperateSrc("PC端");
				}
			}
			String totalRecords= FirstQInterfaces.getIStationHistoryService().stationHistoryListCount(queryCondition)+ "";
			String searchStationHistoryFirst = request.getAttribute("searchStationHistoryFirst") != null ? (String)request.getAttribute("searchStationHistoryFirst"): "";
			if("0".equals(searchStationHistoryFirst)) {
				model.addAttribute("stationHistoryBOs",JSON.toJSONString(stationHistoryBOs));
				model.addAttribute("totalRecords",JSON.toJSONString(totalRecords));
			} else {
				model.addAttribute("stationHistoryBOs",stationHistoryBOs);
				model.addAttribute("totalRecords",totalRecords);
			}
			
		} catch (FirstQException e) {
			// TODO Auto-generated catch block
			LogUtil.error(e);
		}
		
		
		return "";
	}
	
	@RequestMapping(value="/savePowerFailurePrediction",method=RequestMethod.POST)
	public String savePowerFailurePrediction(HttpServletRequest request, HttpSession session,ModelMap model){
		try {
			String openId = "";
			UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
			if(user != null) {
				openId = user.getOpenId();
			}
			String stationId = request.getParameter("stationId");
			String status = request.getParameter("status");
			String linename = request.getParameter("linename");
			String lineno = request.getParameter("lineno");
			RelationStationBo relationStationBo = new RelationStationBo();
			relationStationBo.setId(getUUID());
			relationStationBo.setStation_id(stationId);
			relationStationBo.setLineno(lineno);
			relationStationBo.setLinename(linename);
			relationStationBo.setStatus(status);
			relationStationBo.setSign_time(new Date());
			relationStationBo.setMark_man(openId);
			relationStationBo.setStarttime("");
			relationStationBo.setRealtime("");
			int flag = -1;
			
				flag = FirstQInterfaces.getIRobotInfoService().savePowerCutLinesMark(relationStationBo);
			 
			//调用保存停电信息接口
			if(flag > 0) {
				model.addAttribute("res","success");
			} else {
				model.addAttribute("res","fail");
			}
		}catch (FirstQException e) {
			// TODO Auto-generated catch block
			model.addAttribute("res","fail");
			LogUtil.error(e);
		}
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value="/getStationByCode")
	public String getStationByCode(HttpServletRequest request, HttpSession session, ModelMap model){
		String jsoncallback=request.getParameter("jsoncallback");//这个是固定的
		String stationCode = request.getParameter("stationCode");
		String stationId=FirstQInterfaces.getIStationService().getStationByStationCode(stationCode);
		model.addAttribute("stationId",stationId);
		return jsoncallback+"({stationId:'"+stationId+"'})";
	
	}
	@RequestMapping(value="/motion",method=RequestMethod.POST)
	public String searchStationMotion(HttpServletRequest request, HttpSession session,ModelMap model){
		String stationId = request.getParameter("stationId");
		try {
			String strUrl=ToolUtil.getAppConfig("WechatDomain")+"/"+ToolUtil.getAppConfig("WechatProjectName")+"/stationMotion/getMotion.json?stationId="+stationId; 
			 String result = HttpClientUtil.getResult(strUrl, "", "utf-8");
			 JSONObject json=JSONObject.parseObject(result);
				JSONArray arr=json.getJSONArray("actionList");
				List<StationActionBO> actionlist=new ArrayList<StationActionBO>();
				List<StationActionBO> actionlists=(List<StationActionBO>) JSON.parseArray(arr.toJSONString(), StationActionBO.class);
				actionlist.addAll(actionlists);
				Map<String,String> map=new HashMap<String,String>();
				map.put("stationId", stationId);
				List<StationActionBO> power=FirstQInterfaces.getIStationActionService().getPowerByStationId(map);
				actionlist.addAll(power);
				Collections.sort(actionlist, new Comparator<StationActionBO>(){
					
		            public int compare(StationActionBO o1, StationActionBO o2) {  
		              
		                return o2.getNoticeTime().compareTo(o1.getNoticeTime());  
		            }  
		        });   
				model.addAttribute("actionList", actionlist);
				model.addAttribute("actionListNum", actionlist.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
}
