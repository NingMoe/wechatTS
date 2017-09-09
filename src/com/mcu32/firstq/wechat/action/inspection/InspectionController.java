package com.mcu32.firstq.wechat.action.inspection;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcu32.firstq.common.bean.bo.ACDistributionBoxBO;
import com.mcu32.firstq.common.bean.bo.ACLineBO;
import com.mcu32.firstq.common.bean.bo.AirConditioningBO;
import com.mcu32.firstq.common.bean.bo.BaseDeviceBO;
import com.mcu32.firstq.common.bean.bo.BatteryBO;
import com.mcu32.firstq.common.bean.bo.DCDistributionBoxBO;
import com.mcu32.firstq.common.bean.bo.GroundingForLightningBO;
import com.mcu32.firstq.common.bean.bo.InspectionRoomTowerBO;
import com.mcu32.firstq.common.bean.bo.Monitoring;
import com.mcu32.firstq.common.bean.bo.PhotoBO;
import com.mcu32.firstq.common.bean.bo.PressureRegulatorBO;
import com.mcu32.firstq.common.bean.bo.StationInspectionRecordBO;
import com.mcu32.firstq.common.bean.bo.SwitchPowerBO;
import com.mcu32.firstq.common.bean.bo.TransformerBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.ACDistributionBox;
import com.mcu32.firstq.wechat.bean.ACLine;
import com.mcu32.firstq.wechat.bean.AirConditioning;
import com.mcu32.firstq.wechat.bean.BaseDevice;
import com.mcu32.firstq.wechat.bean.Battery;
import com.mcu32.firstq.wechat.bean.DCDistributionBox;
import com.mcu32.firstq.wechat.bean.GroundingForLightning;
import com.mcu32.firstq.wechat.bean.MonitoringInfo;
import com.mcu32.firstq.wechat.bean.PhotoInfo;
import com.mcu32.firstq.wechat.bean.PressureRegulator;
import com.mcu32.firstq.wechat.bean.StationInfo;
import com.mcu32.firstq.wechat.bean.StationInspectRecord;
import com.mcu32.firstq.wechat.bean.SwitchPower;
import com.mcu32.firstq.wechat.bean.Transformer;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.interceptor.AccessRequired;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.SessionConstants;
import com.mcu32.firstq.wechat.util.WebContentConstants;


@Controller
@RequestMapping(value = "/inspection")
public class InspectionController extends BaseController{
	/**
	 * 跳转到巡检首页
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/readyStartTask")
	public String toInspectItem(HttpServletRequest request,HttpSession session, ModelMap model){
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		if(station ==  null){
			return "commons/error";
		}
		//基站巡检记录
		StationInspectRecord sir = new StationInspectRecord();
		//基站巡检记录
		List<BaseDevice> deviceList = new ArrayList<BaseDevice>();
		try {
			StationInspectionRecordBO sirBO = FirstQInterfaces.getIInspectionStationService().getInspectionStation(station.getStationId(), user.getUserId());
			if (sirBO != null && !WebContentConstants.STATUS_COMPLETE.equals(sir.getStatus()) && !WebContentConstants.STATUS_SUSPEND.equals(sir.getStatus())){
				BeanCopier.create(StationInspectionRecordBO.class, StationInspectRecord.class, false).copy(sirBO, sir, null);
			}
			
		} catch (Exception e) {
			LogUtil.error("查询巡检记录发生错误",e);
			return "commons/error";
		}
		//是否继续操作标识
		if((sir != null && StringUtils.hasText(sir.getInspectionId())) ){
			DecimalFormat df = new DecimalFormat("#0.00");
			if(sir.getStatus() != null && sir.getStatus().equals(WebContentConstants.STATUS_COMPLETE)){
				model.addAttribute("status", "todo");
			}else if(sir.getStatus() != null && sir.getStatus().equals(WebContentConstants.STATUS_SUSPEND)){
				model.addAttribute("status", "todo");
			}else{
				model.addAttribute("status", "continue");
			}
			session.setAttribute(SessionConstants.STATION_INSPECT_RECORD, sir);
			Date finishT = sir.getFinishTime();
			if(finishT == null){
				finishT = new Date();
			}
			double timeSpan = (finishT.getTime() - sir.getCreateDate().getTime())/(double)1000/(double)60/(double)60;
			model.addAttribute("timeSpan", df.format(timeSpan));
			try {
				List<BaseDeviceBO> deviceBOs = FirstQInterfaces.getIDeviceService().getAllBaseDevices(station.getStationId(), sir.getInspectionId());
				for (BaseDeviceBO baseDeviceBO : deviceBOs) {
						List<PhotoBO> photoBOList = baseDeviceBO.getPhotoList();
						List<PhotoInfo> photoInfoList = new ArrayList<PhotoInfo>();
						if (photoBOList != null && photoBOList.size() > 0) {
							for (PhotoBO photoBO : photoBOList) {
								PhotoInfo devicePhoto = new PhotoInfo();
								BeanCopier.create(PhotoBO.class, PhotoInfo.class, false).copy(photoBO, devicePhoto, null);
								photoInfoList.add(devicePhoto);
							}
							FirstqTool.convertPhotoPath(photoInfoList, (UserInfo)session.getAttribute(SessionConstants.SUSER));
						}
						BaseDevice bd = null;
						if ("switch_power".equals(baseDeviceBO.getDeviceType())) {
							bd = new SwitchPower();
							BeanCopier.create(SwitchPowerBO.class, SwitchPower.class,false).copy(baseDeviceBO, bd, null);
						}else if ("battery".equals(baseDeviceBO.getDeviceType())) {
							bd = new Battery();
							BeanCopier.create(BatteryBO.class, Battery.class,false).copy(baseDeviceBO, bd, null);
						}else if ("air_conditioning".equals(baseDeviceBO.getDeviceType())) {
							bd = new AirConditioning();
							BeanCopier.create(AirConditioningBO.class, AirConditioning.class,false).copy(baseDeviceBO, bd, null);
						}else if ("ac_distribution".equals(baseDeviceBO.getDeviceType())) {
							bd = new ACDistributionBox();
							BeanCopier.create(ACDistributionBoxBO.class, ACDistributionBox.class,false).copy(baseDeviceBO, bd, null);
						}else if ("dc_distribution".equals(baseDeviceBO.getDeviceType())) {
							bd = new DCDistributionBox();
							BeanCopier.create(DCDistributionBoxBO.class, DCDistributionBox.class,false).copy(baseDeviceBO, bd, null);
						}else if ("transformer".equals(baseDeviceBO.getDeviceType())) {
							bd = new Transformer();
							BeanCopier.create(TransformerBO.class, Transformer.class,false).copy(baseDeviceBO, bd, null);
						}else if ("grounding_lightning".equals(baseDeviceBO.getDeviceType())) {
							bd = new GroundingForLightning();
							BeanCopier.create(GroundingForLightningBO.class, GroundingForLightning.class,false).copy(baseDeviceBO, bd, null);
						}else if ("pressure_regulator".equals(baseDeviceBO.getDeviceType())) {
							bd = new PressureRegulator();
							BeanCopier.create(PressureRegulatorBO.class, PressureRegulator.class,false).copy(baseDeviceBO, bd, null);
						}else if ("ac_line".equals(baseDeviceBO.getDeviceType())) {
							bd = new ACLine();
							BeanCopier.create(ACLineBO.class, ACLine.class,false).copy(baseDeviceBO, bd, null);
						}else if ("monitoring".equals(baseDeviceBO.getDeviceType())) {
							bd = new MonitoringInfo();
							BeanCopier.create(Monitoring.class, MonitoringInfo.class,false).copy(baseDeviceBO, bd, null);
						}else {
							bd = new BaseDevice();
							BeanCopier.create(BaseDeviceBO.class, BaseDevice.class,false).copy(baseDeviceBO, bd, null);
						}
						bd.setPhotoList(photoInfoList);
						deviceList.add(bd);
					}
					session.setAttribute(SessionConstants.SDEVICESLIST, deviceList);
			} catch (Exception e) {
				LogUtil.error(e);
				return "commons/error";
			}
			int totalNum = deviceList.size();
			int checkNum = 0;
			int errorNum = 0;
			for(BaseDevice bd :deviceList){
				if(StringUtils.hasText(bd.getInStatus())){
					checkNum++;
				}
				if("false".equals(bd.getInStatus())){
					errorNum++;
				}
			}
			model.addAttribute("totalNum", totalNum);
			model.addAttribute("errorNum", errorNum);
			if(totalNum == 0){
				model.addAttribute("progress", 0);
			}else{
				double progress = (double)checkNum/(double)totalNum*100;
				model.addAttribute("progress", df.format(progress));
			}
			model.addAttribute("history", "true");
		}else{
			model.addAttribute("history", "false");
			model.addAttribute("status", "todo");
			//清空缓存
			session.removeAttribute(SessionConstants.STATION_INSPECT_RECORD);
			session.removeAttribute(SessionConstants.SDEVICESLIST);
		}
		
		return "inspection/startInspection";
	}
	
	//开始任务
	@AccessRequired(needLogin=true)
	@RequestMapping(value = "/toStart", method = RequestMethod.GET)
	public String toStart(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		if(station ==  null){
			return "commons/error";
		}
		UserInfo userInfo = getSessionUser(session);
		if(userInfo == null){
			return "commons/error";
		}
		List<BaseDevice> deviceList = new ArrayList<BaseDevice>();
		StationInspectRecord sir = new StationInspectRecord();
		try {
			StationInspectionRecordBO sirbo = FirstQInterfaces.getIInspectionStationService().getInspectionStation(station.getStationId(), userInfo.getUserId(), "进行中");
			List<BaseDeviceBO> deviceBOs = null;
			if(sirbo != null) {
				deviceBOs = FirstQInterfaces.getIDeviceService().getAllBaseDevices(station.getStationId(), sirbo.getInspectionId());
				BeanCopier.create(StationInspectRecord.class, StationInspectionRecordBO.class, false).copy(sir, sirbo, null);
			} else {
				//基站巡检记录
				sir.setStationId(station.getStationId());
				sir.setInspectionId(getUUID());
				sir.setCreateDate(new Date());
				sir.setCreator(userInfo.getUserName());
				sir.setCreateId(userInfo.getToken());
				sir.setJobId(userInfo.getJobId());
				sir.setStatus(WebContentConstants.STATUS_PROGRESS);
				//基站巡检记录
				
				StationInspectionRecordBO sirBO = new StationInspectionRecordBO();
				BeanCopier.create(StationInspectRecord.class, StationInspectionRecordBO.class, false).copy(sir, sirBO, null);
				FirstQInterfaces.getIInspectionStationService().saveInspectionStation(sirBO);
				deviceBOs = FirstQInterfaces.getIDeviceService().getAllBaseDevices(station.getStationId(), sir.getInspectionId());
			}
			for (BaseDeviceBO baseDeviceBO : deviceBOs) {
				List<PhotoBO> photoBOList = baseDeviceBO.getPhotoList();
				List<PhotoInfo> photoInfoList = new ArrayList<PhotoInfo>();
				if (photoBOList != null && photoBOList.size() > 0) {
					for (PhotoBO photoBO : photoBOList) {
						PhotoInfo devicePhoto = new PhotoInfo();
						BeanCopier.create(PhotoBO.class, PhotoInfo.class, false).copy(photoBO, devicePhoto, null);
						photoInfoList.add(devicePhoto);
					}
					FirstqTool.convertPhotoPath(photoInfoList, (UserInfo)session.getAttribute(SessionConstants.SUSER));
				}
				BaseDevice bd = null;
				if ("switch_power".equals(baseDeviceBO.getDeviceType())) {
					bd = new SwitchPower();
					BeanCopier.create(SwitchPowerBO.class, SwitchPower.class,false).copy(baseDeviceBO, bd, null);
				}else if ("battery".equals(baseDeviceBO.getDeviceType())) {
					bd = new Battery();
					BeanCopier.create(BatteryBO.class, Battery.class,false).copy(baseDeviceBO, bd, null);
				}else if ("air_conditioning".equals(baseDeviceBO.getDeviceType())) {
					bd = new AirConditioning();
					BeanCopier.create(AirConditioningBO.class, AirConditioning.class,false).copy(baseDeviceBO, bd, null);
				}else if ("ac_distribution".equals(baseDeviceBO.getDeviceType())) {
					bd = new ACDistributionBox();
					BeanCopier.create(ACDistributionBoxBO.class, ACDistributionBox.class,false).copy(baseDeviceBO, bd, null);
				}else if ("dc_distribution".equals(baseDeviceBO.getDeviceType())) {
					bd = new DCDistributionBox();
					BeanCopier.create(DCDistributionBoxBO.class, DCDistributionBox.class,false).copy(baseDeviceBO, bd, null);
				}else if ("transformer".equals(baseDeviceBO.getDeviceType())) {
					bd = new Transformer();
					BeanCopier.create(TransformerBO.class, Transformer.class,false).copy(baseDeviceBO, bd, null);
				}else if ("grounding_lightning".equals(baseDeviceBO.getDeviceType())) {
					bd = new GroundingForLightning();
					BeanCopier.create(GroundingForLightningBO.class, GroundingForLightning.class,false).copy(baseDeviceBO, bd, null);
				}else if ("pressure_regulator".equals(baseDeviceBO.getDeviceType())) {
					bd = new PressureRegulator();
					BeanCopier.create(PressureRegulatorBO.class, PressureRegulator.class,false).copy(baseDeviceBO, bd, null);
				}else if ("ac_line".equals(baseDeviceBO.getDeviceType())) {
					bd = new ACLine();
					BeanCopier.create(ACLineBO.class, ACLine.class,false).copy(baseDeviceBO, bd, null);
				}else if ("monitoring".equals(baseDeviceBO.getDeviceType())) {
					bd = new MonitoringInfo();
					BeanCopier.create(Monitoring.class, MonitoringInfo.class,false).copy(baseDeviceBO, bd, null);
				}else {
					bd = new BaseDevice();
					BeanCopier.create(BaseDeviceBO.class, BaseDevice.class,false).copy(baseDeviceBO, bd, null);
				}
				bd.setPhotoList(photoInfoList);
				deviceList.add(bd);
			}
		} catch (Exception e) {
			LogUtil.error("保存基站巡检记录发生错误",e);
			return "commons/error";
		}
		model.addAttribute("devicesList", deviceList);
		model.addAttribute("status", "continue");
		model.addAttribute("toPageUrl", "/inspection/readyStartTask");
		session.setAttribute(SessionConstants.SDEVICESLIST, deviceList);
		session.setAttribute(SessionConstants.STATION_INSPECT_RECORD, sir);
		return "inspection/inspectionRecord";
	}
	
	//继续任务
	@SuppressWarnings("unchecked")
	@AccessRequired(needLogin=true)
	@RequestMapping(value = "/toContinueTask", method = RequestMethod.GET)
	public String toContinueTask(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		
		StationInspectRecord sir = (StationInspectRecord)session.getAttribute(SessionConstants.STATION_INSPECT_RECORD);
		if(sir == null){
			return "commons/error";
		}
		
		List<BaseDevice> devicesList = (List<BaseDevice>)session.getAttribute(SessionConstants.SDEVICESLIST);
		model.addAttribute("devicesList", devicesList);
		model.addAttribute("toPageUrl", "/inspection/readyStartTask");
		return "inspection/inspectionRecord";
	}
	
	/**
	 * 新增或修改基站和铁塔的异常检查信息
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value = "/saveOrUpdateCheck", method = RequestMethod.POST)
	public String saveOrUpdateCheck(InspectionRoomTowerBO irtb,HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		int i=0;
		StationInspectRecord sir = (StationInspectRecord)session.getAttribute(SessionConstants.STATION_INSPECT_RECORD);
		irtb.setInspectionId(sir.getInspectionId());
		String recordId=irtb.getRecordId();
		try {
			if(StringUtils.isEmpty(recordId)){
				recordId=this.getUUID();
				irtb.setRecordId(recordId);
				irtb.setCreateDate(new Date());
				i+=FirstQInterfaces.getIInspectionStationService().saveInspectionRoomTower(irtb);
			}else{
				i+=FirstQInterfaces.getIInspectionStationService().updateInspectionRoomTower(irtb);
			}
			
			if(i>0){
				
				model.addAttribute("succ", true);
				model.addAttribute("recordId", recordId);
			}
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	//检查巡检任务是否完成
	@RequestMapping(value = "/checkfinishStatus", method = RequestMethod.GET)
	public String checkfinishStatus(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		if(station ==  null){
			return "commons/error";
		}
		StationInspectRecord sir = (StationInspectRecord)session.getAttribute(SessionConstants.STATION_INSPECT_RECORD);
		//获取基站巡检记录
		List<BaseDevice> deviceList = new ArrayList<BaseDevice>();
		try {
			List<BaseDeviceBO> deviceBOs = FirstQInterfaces.getIDeviceService().getAllBaseDevices(station.getStationId(), sir.getInspectionId());
			for (BaseDeviceBO baseDeviceBO : deviceBOs) {
				List<PhotoBO> photoBOList = baseDeviceBO.getPhotoList();
				List<PhotoInfo> photoInfoList = new ArrayList<PhotoInfo>();
				if (photoBOList != null && photoBOList.size() > 0) {
					for (PhotoBO photoBO : photoBOList) {
						PhotoInfo devicePhoto = new PhotoInfo();
						BeanCopier.create(PhotoBO.class, PhotoInfo.class, false).copy(photoBO, devicePhoto, null);
						photoInfoList.add(devicePhoto);
					}
					FirstqTool.convertPhotoPath(photoInfoList, (UserInfo)session.getAttribute(SessionConstants.SUSER));
				}
				BaseDevice bd = null;
				if ("switch_power".equals(baseDeviceBO.getDeviceType())) {
					bd = new SwitchPower();
					BeanCopier.create(SwitchPowerBO.class, SwitchPower.class,false).copy(baseDeviceBO, bd, null);
				}else if ("battery".equals(baseDeviceBO.getDeviceType())) {
					bd = new Battery();
					BeanCopier.create(BatteryBO.class, Battery.class,false).copy(baseDeviceBO, bd, null);
				}else if ("air_conditioning".equals(baseDeviceBO.getDeviceType())) {
					bd = new AirConditioning();
					BeanCopier.create(AirConditioningBO.class, AirConditioning.class,false).copy(baseDeviceBO, bd, null);
				}else if ("ac_distribution".equals(baseDeviceBO.getDeviceType())) {
					bd = new ACDistributionBox();
					BeanCopier.create(ACDistributionBoxBO.class, ACDistributionBox.class,false).copy(baseDeviceBO, bd, null);
				}else if ("dc_distribution".equals(baseDeviceBO.getDeviceType())) {
					bd = new DCDistributionBox();
					BeanCopier.create(DCDistributionBoxBO.class, DCDistributionBox.class,false).copy(baseDeviceBO, bd, null);
				}else if ("transformer".equals(baseDeviceBO.getDeviceType())) {
					bd = new Transformer();
					BeanCopier.create(TransformerBO.class, Transformer.class,false).copy(baseDeviceBO, bd, null);
				}else if ("grounding_lightning".equals(baseDeviceBO.getDeviceType())) {
					bd = new GroundingForLightning();
					BeanCopier.create(GroundingForLightningBO.class, GroundingForLightning.class,false).copy(baseDeviceBO, bd, null);
				}else if ("pressure_regulator".equals(baseDeviceBO.getDeviceType())) {
					bd = new PressureRegulator();
					BeanCopier.create(PressureRegulatorBO.class, PressureRegulator.class,false).copy(baseDeviceBO, bd, null);
				}else if ("ac_line".equals(baseDeviceBO.getDeviceType())) {
					bd = new ACLine();
					BeanCopier.create(ACLineBO.class, ACLine.class,false).copy(baseDeviceBO, bd, null);
				}else if ("monitoring".equals(baseDeviceBO.getDeviceType())) {
					bd = new MonitoringInfo();
					BeanCopier.create(Monitoring.class, MonitoringInfo.class,false).copy(baseDeviceBO, bd, null);
				}else {
					bd = new BaseDevice();
					BeanCopier.create(BaseDeviceBO.class, BaseDevice.class,false).copy(baseDeviceBO, bd, null);
				}
				bd.setPhotoList(photoInfoList);
				deviceList.add(bd);
			}
		} catch (Exception e) {
			LogUtil.error(e);
			return "commons/error";
		}
		int totalNum = deviceList.size();
		int checkNum = 0;
		for(BaseDevice bd :deviceList){
			if("true".equals(bd.getInStatus()) || "false".equals(bd.getInStatus()))
				checkNum++;
		}
		if(totalNum != 0){
			if(checkNum < totalNum){
				model.addAttribute("status", 1);
			}else{
				model.addAttribute("status", 0);
			}
		}else{
			model.addAttribute("status", 1);
		}
		return "";
	}
	
	//完成任务
	@RequestMapping(value = "/finish", method = RequestMethod.GET)
	public String finishInspection(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		//任务状态
		String status = request.getParameter("status");
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		if(station ==  null){
			return "commons/error";
		}
		StationInspectRecord sir = (StationInspectRecord)session.getAttribute(SessionConstants.STATION_INSPECT_RECORD);
		sir.setCreateDate(null);
		sir.setFinishTime(new Date());
		if(status.equals("0")){
			//巡检正常结束
			sir.setStatus(WebContentConstants.STATUS_COMPLETE);
		}else{
			//异常中止
			sir.setStatus(WebContentConstants.STATUS_SUSPEND);
		}
		
		StationInspectionRecordBO sirBO = new StationInspectionRecordBO();
		BeanCopier.create(StationInspectRecord.class, StationInspectionRecordBO.class, false).copy(sir, sirBO, null);
		try {
			FirstQInterfaces.getIInspectionStationService().updateInspectionStation(sirBO);
		} catch (Exception e) {
			LogUtil.error("保存巡检任务发生错误",e);
			return "commons/error";
		}
		return "redirect:/inspection/readyStartTask";
	}
	
}
