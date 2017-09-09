package com.mcu32.firstq.wechat.action.inspection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.mcu32.firstq.common.bean.bo.ACDistributionBoxBO;
import com.mcu32.firstq.common.bean.bo.ACLineBO;
import com.mcu32.firstq.common.bean.bo.AirConditioningBO;
import com.mcu32.firstq.common.bean.bo.BaseDeviceBO;
import com.mcu32.firstq.common.bean.bo.BatteryBO;
import com.mcu32.firstq.common.bean.bo.DCDistributionBoxBO;
import com.mcu32.firstq.common.bean.bo.DeviceBarCodeBO;
import com.mcu32.firstq.common.bean.bo.DeviceInspectionRecordBO;
import com.mcu32.firstq.common.bean.bo.DeviceStatusBO;
import com.mcu32.firstq.common.bean.bo.GroundingForLightningBO;
import com.mcu32.firstq.common.bean.bo.Monitoring;
import com.mcu32.firstq.common.bean.bo.PhotoBO;
import com.mcu32.firstq.common.bean.bo.PressureRegulatorBO;
import com.mcu32.firstq.common.bean.bo.RoomBO;
import com.mcu32.firstq.common.bean.bo.StationHistoryBO;
import com.mcu32.firstq.common.bean.bo.SwitchPowerBO;
import com.mcu32.firstq.common.bean.bo.TowerBO;
import com.mcu32.firstq.common.bean.bo.TransformerBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.service.IDeviceService;
import com.mcu32.firstq.common.service.IPhotoService;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.ACDistributionBox;
import com.mcu32.firstq.wechat.bean.ACLine;
import com.mcu32.firstq.wechat.bean.AirConditioning;
import com.mcu32.firstq.wechat.bean.BaseDevice;
import com.mcu32.firstq.wechat.bean.Battery;
import com.mcu32.firstq.wechat.bean.DCDistributionBox;
import com.mcu32.firstq.wechat.bean.DeviceInspectRecord;
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
import com.mcu32.firstq.wechat.service.IStationHistoryService;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.SessionConstants;
import com.mcu32.firstq.wechat.util.WebContentConstants;
/**
 * 添加/修改设备信息
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/devices")
public class DevicesController extends BaseController {
	private IDeviceService iDeviceService = FirstQInterfaces.getIDeviceService();
	private IPhotoService iPhotoService = FirstQInterfaces.getIPhotoService();
	
	@Autowired IStationHistoryService iStationHistoryService;
	/**
	 * TODO 获取巡检设备列表
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value = "/getDeviceList", method = RequestMethod.GET)
	public String getDeviceList(HttpServletRequest request, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		StationInspectRecord sir = (StationInspectRecord)session.getAttribute(SessionConstants.STATION_INSPECT_RECORD);
		if(station ==  null){
			return ERRORPAGE;
		}
		String stationId = station.getStationId();
		List<BaseDevice> deviceList = new ArrayList<BaseDevice>();
		try {
			List<BaseDeviceBO> deviceBOs = iDeviceService.getAllBaseDevices(stationId, sir.getInspectionId());
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
			return ERRORPAGE;
		}
		model.addAttribute("devicesList", deviceList);
		UserInfo userInfo = getSessionUser(session);
		String jobId=userInfo.getJobId();
		if(WebContentConstants.JOB_STATION_INSPECTION.equals(jobId)){//巡检
			model.addAttribute("toPageUrl", "/inspection/readyStartTask");
			return "inspection/inspectionRecord";
		}else if (WebContentConstants.JOB_STATION_QUARTER_INSPECTION.equals(jobId)) {
			TowerBO towerBO = null;
			RoomBO roomBo=null;
			try {
				towerBO = FirstQInterfaces.getIStationService().getTowerByStationId(station.getStationId());
				roomBo=FirstQInterfaces.getIStationService().getRoomByStationId(station.getStationId());
			} catch (FirstQException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(towerBO != null) {
				DeviceStatusBO towerStatusBO = null;
				try {
					towerStatusBO = FirstQInterfaces.getIDeviceService().getTowerStatus(towerBO.getTowerId(), sir.getInspectionId());
					if(towerStatusBO != null) {
						model.addAttribute("towerStatusBO", towerStatusBO);
					}
				} catch (FirstQException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				model.addAttribute("towerBO", towerBO);
				session.setAttribute(SessionConstants.TOWERBO, towerBO);
			}
			if(roomBo != null) {
				DeviceStatusBO roomStatusBO = null;
				try {
					roomStatusBO = FirstQInterfaces.getIDeviceService().getRoomStatus(roomBo.getRoomId(), sir.getInspectionId());
					if(roomStatusBO != null) {
						model.addAttribute("roomStatusBO", roomStatusBO);
					}
				} catch (FirstQException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				model.addAttribute("roomBo", roomBo);
				session.setAttribute(SessionConstants.ROOMBO, roomBo);
			}
			model.addAttribute("toPageUrl", "/quarterInspection/readyStartTask");
			return "inspection/inspectionRecordTower";
		}
		
		return "inspection/inspectionRecord";
	}
	
	
	/**
	 * TODO 根据设备id获取设备信息
	 */
	@RequestMapping(value = "/getByIdDeviceInfo", method = RequestMethod.GET)
	public String getByIdDeviceInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		String deviceId = request.getParameter("deviceId");
		String stationId = request.getParameter("stationId");
		List<DeviceInspectRecord> deviceInspectRecords = new ArrayList<DeviceInspectRecord>();
		try {
			List<DeviceInspectionRecordBO> deviceInspectionRecordBOs = iDeviceService.getInspectionRecords(stationId, deviceId, 3);
			for (DeviceInspectionRecordBO deviceInspectionRecordBO : deviceInspectionRecordBOs) {
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
				deviceInspectRecords.add(dir);
			}
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
				}
			}
		} catch (Exception e) {
			LogUtil.error(e);
		}
		model.addAttribute("deviceInspectRecords", deviceInspectRecords);
		model.addAttribute("stationId", stationId);
		return "station/deviceInspectionDetail";	
	}
	
	
	/**
	 * TODO 获取设备信息
	 */
	@SuppressWarnings("unchecked")
	@AccessRequired(needLogin=true)
	@RequestMapping(value = "/getDeviceInfo", method = RequestMethod.GET)
	public String getDeviceInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		
		String isScan=request.getParameter("isScan");
		
		setWechatConfig(request,model);//拼写微信js需要的config参数
		List<BaseDevice> deviceList = (List<BaseDevice>)session.getAttribute(SessionConstants.SDEVICESLIST);
		// 获取设备信息
		String deviceId = request.getParameter("deviceId");
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		if(station ==  null){
			return ERRORPAGE;
		}
		UserInfo userInfo = getSessionUser(session);
		String jobId=userInfo.getJobId();
		if(WebContentConstants.JOB_STATION_INSPECTION.equals(jobId)){//巡检
			model.addAttribute("toPageUrl", "/devices/getDeviceList");
		}else if(WebContentConstants.JOB_DISCHARGE.equals(jobId)){//放电测试
			model.addAttribute("toPageUrl", "/discharge/getDeviceList");
		}else if (WebContentConstants.JOB_GENERATE_POWER.equals(jobId)) {
			model.addAttribute("toPageUrl","/generatePower/getDeviceList");
		}else if (WebContentConstants.JOB_STATION_QUARTER_INSPECTION.equals(jobId)) {
			model.addAttribute("toPageUrl","/quarterInspection/toContinueTask");
		}
		boolean isAdd = true;
		int isIncludeBattery = 1;//是否有蓄电池，如果有（不管几组蓄电池），此值都为true，用于再次新增蓄电池时，蓄电池组直接为组2
		BaseDevice currDevice = new BaseDevice();
		if(deviceList != null && deviceList.size() > 0) {
			for(BaseDevice device : deviceList){
				if(deviceId.equals(device.getDeviceId())){
					currDevice = device;
					isAdd = false;
					break;
				}
			}
		}
		if(deviceList != null && deviceList.size() > 0) {
			for(BaseDevice device : deviceList){
				if("蓄电池".equals(device.getDeviceTypeName())){
					isIncludeBattery = isIncludeBattery + 1;
					continue;
				}
			}
		}
		currDevice.setDeviceId(deviceId);
		model.addAttribute("baseDevice", currDevice);
		model.addAttribute("isAdd", isAdd);
		model.addAttribute("isIncludeBattery", isIncludeBattery);
		if(!Boolean.valueOf(isScan)){
			return "inspection/deviceNotCheck";
		}
		if(!isAdd){
			return "inspection/deviceInfo";
		}
		return "inspection/addDevices";
	}

	/**
	 * TODO 保存设备信息
	 */
	@RequestMapping(value = "/saveDeviceInfo", method = RequestMethod.POST)
	public String saveDeviceInfo(SwitchPower sp, Battery ba, AirConditioning ac,ACDistributionBox acBox,DCDistributionBox dcBox,Transformer transformer,GroundingForLightning groundingForLightning,PressureRegulator pressureRegulator,ACLine acLine,MonitoringInfo monitoringInfo,HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		// 添加设备信息
		Date date = new Date();
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		String deviceId = request.getParameter("barCode");
		String deviceType = request.getParameter("deviceType");
		String deviceImgs = "";
		String[] requestDeviceImgs = request.getParameterValues("deviceImgs");
		String remark=request.getParameter("remark");
		String deviceStatusStr=request.getParameter("deviceStatus");
		boolean thisDeviceHasChecked = StringUtils.isEmpty(deviceStatusStr);
		boolean deviceIsNormal= Boolean.valueOf(deviceStatusStr);
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		if(station == null){
			return ERRORPAGE;
		}
		//是否保存成功
		boolean flag = false;
		try{
			if(deviceType != null && !"".equals(deviceType)) {
				if("battery".equals(deviceType)) {
					deviceImgs = requestDeviceImgs[1];
					ba.setDeviceId(deviceId);
					ba.setBarCode(deviceId);
					ba.setBatteryCapacity("");
					ba.setBatteryId(deviceId);
					ba.setBatteryMakert("");
					ba.setBatteryName("蓄电池");
					ba.setBatteryType("");
					//ba.setBatteryVoltage("");
					ba.setEqualizingChargeVoltage("");
					ba.setFloatingChargeVoltage("");
					ba.setOldDeviceId("");
					ba.setOutLose("0");
					ba.setPowerId("");
					ba.setInStatus("");
					ba.setOutStatus("");
					ba.setStationId(station.getStationId());
					ba.setRemark("");
					ba.setLastDate(date);
					ba.setLastUser(user.getUserName());
					ba.setLastUserId(user.getUserId());
					ba.setCreateDate(date);
					ba.setCreateUser(user.getUserName());
					ba.setCreateUserId(user.getUserId());
					BatteryBO batteryBO = new BatteryBO();
					BeanCopier.create(Battery.class, BatteryBO.class, false).copy(ba, batteryBO, null);
					flag = iDeviceService.saveDevice(batteryBO);
				} else if("switch_power".equals(deviceType)) {
					deviceImgs = requestDeviceImgs[0];
					sp.setDeviceId(deviceId);
					sp.setBarCode(deviceId);
					sp.setBelongType("01");
					sp.setDeviceName("开关电源");
					sp.setNormalModNum(2);
					sp.setOutLose("0");
					sp.setRatedPower("5000");
					sp.setStationId(station.getStationId());
					sp.setPowerId(deviceId);
					sp.setPowerMaker("");
					sp.setRemark("");
					sp.setOldDeviceId("");
					sp.setLastDate(date);
					sp.setLastUser(user.getUserName());
					sp.setLastUserId(user.getUserId());
					sp.setCreateDate(date);
					sp.setCreateUser(user.getUserName());
					sp.setCreateUserId(user.getUserId());
					SwitchPowerBO switchPowerBO = new SwitchPowerBO();
					BeanCopier.create(SwitchPower.class, SwitchPowerBO.class, false).copy(sp, switchPowerBO, null);
					flag = iDeviceService.saveDevice(switchPowerBO);
				} else if("air_conditioning".equals(deviceType)) {
					deviceImgs = requestDeviceImgs[2];
					ac.setDeviceId(deviceId);
					ac.setBarCode(deviceId);
					ac.setDeviceName("空调");
					ac.setOldDeviceId("");
					ac.setStationId(station.getStationId());
					ac.setOutLose("0");
					ac.setDeviceType("air_conditioning");
					ac.setCreateId(user.getUserId());
					ac.setCreator(user.getUserName());
					AirConditioningBO airConditioningBO = new AirConditioningBO();
					BeanCopier.create(AirConditioning.class, AirConditioningBO.class, false).copy(ac, airConditioningBO, null);
					flag = iDeviceService.saveDevice(airConditioningBO);
				}else if("ac_distribution".equals(deviceType)) {
					deviceImgs = requestDeviceImgs[3];
					acBox.setAcDistributionId(deviceId);
					acBox.setDeviceId(deviceId);
					acBox.setOldDeviceId("");
					acBox.setOutLose("0");
					acBox.setDeviceType("ac_distribution");
					acBox.setDeviceTypeName("交流配电箱");
					acBox.setStationId(station.getStationId());
					acBox.setLastTime(new Date());
					acBox.setLastUser(user.getUserName());
					acBox.setLastUserId(user.getUserId());
					ba.setRemark("");
					ACDistributionBoxBO acBoxBo=new ACDistributionBoxBO();
					BeanCopier.create(ACDistributionBox.class, ACDistributionBoxBO.class, false).copy(acBox, acBoxBo, null);
					flag = iDeviceService.saveDevice(acBoxBo);
				}else if("dc_distribution".equals(deviceType)) {
					deviceImgs = requestDeviceImgs[4];
					dcBox.setDcDistributionId(deviceId);
					dcBox.setDeviceId(deviceId);
					dcBox.setOldDeviceId("");
					dcBox.setOutLose("0");
					dcBox.setDeviceType("dc_distribution");
					dcBox.setDeviceTypeName("直流配电箱");
					dcBox.setStationId(station.getStationId());
					dcBox.setLastTime(new Date());
					dcBox.setLastUser(user.getUserName());
					dcBox.setLastUserId(user.getUserId());
					dcBox.setRemark("");
					DCDistributionBoxBO dcBoxBo=new DCDistributionBoxBO();
					BeanCopier.create(DCDistributionBox.class, DCDistributionBoxBO.class, false).copy(dcBox, dcBoxBo, null);
					flag = iDeviceService.saveDevice(dcBoxBo);
				}else if("transformer".equals(deviceType)) {
					deviceImgs = requestDeviceImgs[5];
					transformer.setTransformerId(deviceId);
					transformer.setDeviceId(deviceId);
					transformer.setOldDeviceId("");
					transformer.setOutLose("0");
					transformer.setDeviceType("transformer");
					transformer.setDeviceTypeName("变压器");
					transformer.setStationId(station.getStationId());
					transformer.setLastTime(new Date());
					transformer.setLastUser(user.getUserName());
					transformer.setLastUserId(user.getUserId());
					transformer.setRemark("");
					TransformerBO transformerBo=new TransformerBO();
					BeanCopier.create(Transformer.class, TransformerBO.class, false).copy(transformer, transformerBo, null);
					flag = iDeviceService.saveDevice(transformerBo);
				}else if("grounding_lightning".equals(deviceType)) {
					deviceImgs = requestDeviceImgs[6];
					groundingForLightning.setGroundingForLightningId(deviceId);
					groundingForLightning.setDeviceId(deviceId);
					groundingForLightning.setOldDeviceId("");
					groundingForLightning.setOutLose("0");
					groundingForLightning.setDeviceType("grounding_lightning");
					groundingForLightning.setDeviceTypeName("防雷接地设备");
					groundingForLightning.setStationId(station.getStationId());
					groundingForLightning.setLastTime(new Date());
					groundingForLightning.setLastUser(user.getUserName());
					groundingForLightning.setLastUserId(user.getUserId());
					groundingForLightning.setRemark("");
					GroundingForLightningBO groundingForLightningBo=new GroundingForLightningBO();
					BeanCopier.create(GroundingForLightning.class, GroundingForLightningBO.class, false).copy(groundingForLightning, groundingForLightningBo, null);
					flag = iDeviceService.saveDevice(groundingForLightningBo);
				}else if("pressure_regulator".equals(deviceType)) {
					deviceImgs = requestDeviceImgs[7];
					pressureRegulator.setPrId(deviceId);
					pressureRegulator.setDeviceId(deviceId);
					pressureRegulator.setOldDeviceId("");
					pressureRegulator.setOutLose("0");
					pressureRegulator.setDeviceType("pressure_regulator");
					pressureRegulator.setDeviceTypeName("调压器");
					pressureRegulator.setStationId(station.getStationId());
					pressureRegulator.setLastTime(new Date());
					pressureRegulator.setLastUser(user.getUserName());
					pressureRegulator.setLastUserId(user.getUserId());
					pressureRegulator.setRemark("");
					PressureRegulatorBO pressureRegulatorBo=new PressureRegulatorBO();
					BeanCopier.create(PressureRegulator.class, PressureRegulatorBO.class, false).copy(pressureRegulator, pressureRegulatorBo, null);
					flag = iDeviceService.saveDevice(pressureRegulatorBo);
				}else if("ac_line".equals(deviceType)) {
					deviceImgs = requestDeviceImgs[8];
					acLine.setAclineId(deviceId);
					acLine.setDeviceId(deviceId);
					acLine.setOldDeviceId("");
					acLine.setDeviceType("ac_line");
					acLine.setOutLose("0");
					acLine.setDeviceTypeName("交流引入");
					acLine.setStationId(station.getStationId());
					acLine.setLastTime(new Date());
					acLine.setLastUser(user.getUserName());
					acLine.setLastUserId(user.getUserId());
					acLine.setRemark("");
					ACLineBO acLineBo=new ACLineBO();
					BeanCopier.create(ACLine.class, ACLineBO.class, false).copy(acLine, acLineBo, null);
					flag = iDeviceService.saveDevice(acLineBo);
				}else if("monitoring".equals(deviceType)) {
					deviceImgs = requestDeviceImgs[9];
					monitoringInfo.setMonitoringId(deviceId);
					monitoringInfo.setDeviceId(deviceId);
					monitoringInfo.setOldDeviceId("");
					monitoringInfo.setOutLose("0");
					monitoringInfo.setDeviceType("monitoring");
					monitoringInfo.setDeviceTypeName("监控设备");
					monitoringInfo.setStationId(station.getStationId());
					monitoringInfo.setLastTime(new Date());
					monitoringInfo.setLastUser(user.getUserName());
					monitoringInfo.setLastUserId(user.getUserId());
					monitoringInfo.setRemark("");
					Monitoring monitoringBo=new Monitoring();
					BeanCopier.create(MonitoringInfo.class, Monitoring.class, false).copy(monitoringInfo, monitoringBo, null);
					flag = iDeviceService.saveDevice(monitoringBo);
				}
			}
		} catch(Exception e) {
			LogUtil.error(e);
			return ERRORPAGE;
		}
		if(flag){
			if(deviceIsNormal && !thisDeviceHasChecked){//如果设备正常 并且 没有巡检过
				DeviceInspectRecord dir = saveDeviceInspectRecord(session, deviceId, deviceIsNormal+"",remark,"");//保存设备巡检记录
				if(StringUtils.isEmpty(dir.getRecordId()))
					return ERRORPAGE;
			}
			//设置操作记录的属性
			StationHistoryBO stationHistoryBO = new StationHistoryBO();
			stationHistoryBO.setUserId(user.getUserId());
			stationHistoryBO.setUserName(user.getUserName());
			stationHistoryBO.setOperateTime(new Date());
			stationHistoryBO.setOperateType("insert");
			stationHistoryBO.setOperateObject(deviceType);
			stationHistoryBO.setStationId(station.getStationId());
			stationHistoryBO.setJobId(user.getJobId());
			try {
				iStationHistoryService.saveStationHistory(null, null, stationHistoryBO);
			} catch (com.mcu32.firstq.wechat.exception.FirstQException e) {
				LogUtil.error(e);
			}
			
			flag  = saveWaitUploadPhoto(deviceImgs,station.getStationId(),deviceId,deviceType,(UserInfo)session.getAttribute(SessionConstants.SUSER));//保存照片
			if(!flag)
				return ERRORPAGE;
		}else{
			getSessionUser(session).setAlertMsg("设备条码已被占用！");
		}
		model.addAttribute("deviceType", deviceType);
		model.addAttribute("deviceId", deviceId);
		model.addAttribute("succ", true);
		
		if(!deviceIsNormal){
			setWechatConfig(request,model);
			model.addAttribute("toPageUrl", "/devices/getDeviceList");
			return "inspection/deviceExceptReport";
		}
		return getResultUrl(session);
	}
	
	/**
	 * TODO 更新设备信息
	 */
	@RequestMapping(value = "/updateDeviceInfo", method = RequestMethod.POST)
	public String updateDeviceInfo(SwitchPower sp, Battery ba, AirConditioning ac,ACDistributionBox acBox,DCDistributionBox dcBox,Transformer transformer,GroundingForLightning groundingForLightning,PressureRegulator pressureRegulator,ACLine acLine,MonitoringInfo monitoringInfo, HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		// 更新设备信息
		String deviceId = request.getParameter("barCode");
		String deviceType = request.getParameter("deviceType");
		String deviceImgs = request.getParameter("deviceImgs");
		String remark=request.getParameter("remark");
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		Date date = new Date();
		//设备状态
		String deviceStatusStr = request.getParameter("deviceStatus");
		boolean deviceIsNormal=Boolean.valueOf(deviceStatusStr);
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		if(station == null){
			return ERRORPAGE;
		}
		boolean flag = false;
		try{
			if(deviceType != null && !"".equals(deviceType)) {
				if("battery".equals(deviceType)) {
					ba.setDeviceId(deviceId);
					ba.setBarCode(deviceId);
					ba.setBatteryCapacity("");
					ba.setBatteryId(deviceId);
					ba.setBatteryMakert("");
					ba.setBatteryName("蓄电池");
					ba.setBatteryType("");
					//ba.setBatteryVoltage("");
					ba.setEqualizingChargeVoltage("");
					ba.setFloatingChargeVoltage("");
					ba.setOldDeviceId(deviceId);
					ba.setOutLose("0");
					ba.setPowerId("");
					ba.setInStatus("");
					ba.setOutStatus("");
					ba.setStationId(station.getStationId());
					ba.setLastDate(date);
					ba.setLastUser(user.getUserName());
					ba.setLastUserId(user.getUserId());
					BatteryBO batteryBO = new BatteryBO();
					BeanCopier.create(Battery.class, BatteryBO.class, false).copy(ba, batteryBO, null);
					flag = iDeviceService.updateDevice(batteryBO);
				} else if("switch_power".equals(deviceType)) {
					sp.setDeviceId(deviceId);
					sp.setBarCode(deviceId);
					sp.setBelongType("01");
					sp.setDeviceName("开关电源");
					sp.setNormalModNum(2);
					sp.setOutLose("0");
					sp.setRatedPower("5000");
					sp.setStationId(station.getStationId());
					sp.setPowerId(deviceId);
					sp.setPowerMaker("");
					sp.setRemark("");
					sp.setOldDeviceId(deviceId);
					sp.setLastDate(new Date());
					sp.setLastUser(user.getUserName());
					sp.setLastUserId(user.getUserId());
					SwitchPowerBO switchPowerBO = new SwitchPowerBO();
					BeanCopier.create(SwitchPower.class, SwitchPowerBO.class, false).copy(sp, switchPowerBO, null);
					flag = iDeviceService.updateDevice(switchPowerBO);
				} else if("air_conditioning".equals(deviceType)) {
					ac.setDeviceId(deviceId);
					ac.setBarCode(deviceId);
					ac.setDeviceName("空调");
					ac.setStationId(station.getStationId());
					ac.setOldDeviceId(deviceId);
					ac.setOutLose("0");
					AirConditioningBO airConditioningBO = new AirConditioningBO();
					BeanCopier.create(AirConditioning.class, AirConditioningBO.class, false).copy(ac, airConditioningBO, null);
					flag = iDeviceService.updateDevice(airConditioningBO);
				}else if("ac_distribution".equals(deviceType)) {
					acBox.setAcDistributionId(deviceId);
					acBox.setDeviceId(deviceId);
					acBox.setOldDeviceId(deviceId);
					acBox.setOutLose("0");
					acBox.setDeviceTypeName("交流配电箱");
					acBox.setStationId(station.getStationId());
					acBox.setLastTime(new Date());
					acBox.setLastUser(user.getUserName());
					acBox.setLastUserId(user.getUserId());
					acBox.setRemark("");
					ACDistributionBoxBO acBoxBo=new ACDistributionBoxBO();
					BeanCopier.create(ACDistributionBox.class, ACDistributionBoxBO.class, false).copy(acBox, acBoxBo, null);
					flag = iDeviceService.updateDevice(acBoxBo);
				}else if("dc_distribution".equals(deviceType)) {
					dcBox.setDcDistributionId(deviceId);
					dcBox.setDeviceId(deviceId);
					dcBox.setOldDeviceId(deviceId);
					dcBox.setOutLose("0");
					dcBox.setDeviceTypeName("直流配电箱");
					dcBox.setStationId(station.getStationId());
					dcBox.setLastTime(new Date());
					dcBox.setLastUser(user.getUserName());
					dcBox.setLastUserId(user.getUserId());
					dcBox.setRemark("");
					DCDistributionBoxBO dcBoxBo=new DCDistributionBoxBO();
					BeanCopier.create(DCDistributionBox.class, DCDistributionBoxBO.class, false).copy(dcBox, dcBoxBo, null);
					flag = iDeviceService.updateDevice(dcBoxBo);
				}else if("transformer".equals(deviceType)) {
					transformer.setTransformerId(deviceId);
					transformer.setDeviceId(deviceId);
					transformer.setOldDeviceId(deviceId);
					transformer.setOutLose("0");
					transformer.setDeviceTypeName("变压器");
					transformer.setStationId(station.getStationId());
					transformer.setLastTime(new Date());
					transformer.setLastUser(user.getUserName());
					transformer.setLastUserId(user.getUserId());
					transformer.setRemark("");
					TransformerBO transformerBo=new TransformerBO();
					BeanCopier.create(Transformer.class, TransformerBO.class, false).copy(transformer, transformerBo, null);
					flag = iDeviceService.updateDevice(transformerBo);
				}else if("grounding_lightning".equals(deviceType)) {
					groundingForLightning.setGroundingForLightningId(deviceId);
					groundingForLightning.setDeviceId(deviceId);
					groundingForLightning.setOldDeviceId(deviceId);
					groundingForLightning.setOutLose("0");
					groundingForLightning.setDeviceTypeName("防雷接地设备");
					groundingForLightning.setStationId(station.getStationId());
					groundingForLightning.setLastTime(new Date());
					groundingForLightning.setLastUser(user.getUserName());
					groundingForLightning.setLastUserId(user.getUserId());
					groundingForLightning.setRemark("");
					GroundingForLightningBO groundingForLightningBo=new GroundingForLightningBO();
					BeanCopier.create(GroundingForLightning.class, GroundingForLightningBO.class, false).copy(groundingForLightning, groundingForLightningBo, null);
					flag = iDeviceService.updateDevice(groundingForLightningBo);
				}else if("pressure_regulator".equals(deviceType)) {
					pressureRegulator.setPrId(deviceId);
					pressureRegulator.setDeviceId(deviceId);
					pressureRegulator.setOldDeviceId(deviceId);
					pressureRegulator.setOutLose("0");
					pressureRegulator.setDeviceTypeName("调压器");
					pressureRegulator.setStationId(station.getStationId());
					pressureRegulator.setLastTime(new Date());
					pressureRegulator.setLastUser(user.getUserName());
					pressureRegulator.setLastUserId(user.getUserId());
					pressureRegulator.setRemark("");
					PressureRegulatorBO pressureRegulatorBo=new PressureRegulatorBO();
					BeanCopier.create(PressureRegulator.class, PressureRegulatorBO.class, false).copy(pressureRegulator, pressureRegulatorBo, null);
					flag = iDeviceService.updateDevice(pressureRegulatorBo);
				}else if("ac_line".equals(deviceType)) {
					acLine.setAclineId(deviceId);
					acLine.setDeviceId(deviceId);
					acLine.setOldDeviceId(deviceId);
					acLine.setOutLose("0");
					acLine.setDeviceTypeName("交流引入");
					acLine.setStationId(station.getStationId());
					acLine.setLastTime(new Date());
					acLine.setLastUser(user.getUserName());
					acLine.setLastUserId(user.getUserId());
					acLine.setRemark("");
					ACLineBO acLineBo=new ACLineBO();
					BeanCopier.create(ACLine.class, ACLineBO.class, false).copy(acLine, acLineBo, null);
					flag = iDeviceService.updateDevice(acLineBo);
				}else if("monitoring".equals(deviceType)) {
					monitoringInfo.setMonitoringId(deviceId);
					monitoringInfo.setDeviceId(deviceId);
					monitoringInfo.setOldDeviceId(deviceId);
					monitoringInfo.setOutLose("0");
					monitoringInfo.setDeviceTypeName("监控设备");
					monitoringInfo.setStationId(station.getStationId());
					monitoringInfo.setLastTime(new Date());
					monitoringInfo.setLastUser(user.getUserName());
					monitoringInfo.setLastUserId(user.getUserId());
					monitoringInfo.setRemark("");
					Monitoring  monitoring=new Monitoring();
					BeanCopier.create(MonitoringInfo.class, Monitoring.class, false).copy(monitoringInfo, monitoring, null);
					flag = iDeviceService.updateDevice(monitoring);
				}
			}
		} catch(Exception e) {
			LogUtil.error(e);
			return ERRORPAGE;
		}
		if(flag){
			//如果设备正常
			if(deviceIsNormal){
				DeviceInspectRecord dir = saveDeviceInspectRecord(session, deviceId, deviceIsNormal+"",remark,"");//保存设备巡检记录
				if(StringUtils.isEmpty(dir.getRecordId()))
					return ERRORPAGE;
			}
			flag = saveWaitUploadPhoto(deviceImgs,station.getStationId(),deviceId,deviceType,(UserInfo)session.getAttribute(SessionConstants.SUSER));//保存照片
			if(!flag)
				return ERRORPAGE;
		}
		
		model.addAttribute("deviceType", deviceType);
		model.addAttribute("deviceId", deviceId);
		model.addAttribute("succ", true);
		model.addAttribute("msg", "保存成功");
		if(!deviceIsNormal){
			setWechatConfig(request,model);
			model.addAttribute("toPageUrl", "/devices/getDeviceList");
			return "inspection/deviceExceptReport";
		}
		return getResultUrl(session);
	}
	
	/**
	 * TODO 更新设备信息
	 */
	@RequestMapping(value = "/onlyUpdateDeviceInfo", method = RequestMethod.POST)
	public String onlyUpdateDeviceInfo(SwitchPower sp, Battery ba, AirConditioning ac,ACDistributionBox acBox,DCDistributionBox dcBox,Transformer transformer,GroundingForLightning groundingForLightning,PressureRegulator pressureRegulator,ACLine acLine,MonitoringInfo monitoringInfo,HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		// 更新设备信息
		String deviceId = request.getParameter("barCode");
		String deviceType = request.getParameter("deviceType");
		String deviceImgs = request.getParameter("deviceImgs");
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		Date date = new Date(); 
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		if(station == null){
			return ERRORPAGE;
		}
		boolean flag = false;
		try{
			BaseDeviceBO baseDeviceBO = iDeviceService.getBaseDevicesById(deviceId);
			if(baseDeviceBO==null){
				return "设备不存在";
			}
			//设置操作记录的属性
			StationHistoryBO stationHistoryBO = new StationHistoryBO();
			stationHistoryBO.setUserId(user.getUserId());
			stationHistoryBO.setUserName(user.getUserName());
			stationHistoryBO.setOperateTime(new Date());
			stationHistoryBO.setOperateType("update");
			stationHistoryBO.setOperateObject(deviceType);
			stationHistoryBO.setStationId(station.getStationId());
			stationHistoryBO.setJobId(user.getJobId());
			if(deviceType != null && !"".equals(deviceType)) {
				if("battery".equals(deviceType)) {
					ba.setDeviceId(deviceId);
					ba.setBarCode(deviceId);
					ba.setBatteryCapacity("");
					ba.setBatteryId(deviceId);
					ba.setBatteryMakert("");
					ba.setBatteryName("蓄电池");
					ba.setBatteryType("");
					//ba.setBatteryVoltage("");
					ba.setEqualizingChargeVoltage("");
					ba.setFloatingChargeVoltage("");
					ba.setOldDeviceId(deviceId);
					//ba.setOutLose("0");
					ba.setPowerId("");
					ba.setInStatus("");
					ba.setOutStatus("");
					ba.setStationId(station.getStationId());
					ba.setLastDate(date);
					ba.setLastUser(user.getUserName());
					ba.setLastUserId(user.getUserId());
					BatteryBO batteryBO = new BatteryBO();
					BeanCopier.create(Battery.class, BatteryBO.class, false).copy(ba, batteryBO, null);
					flag = iDeviceService.updateDevice(batteryBO);
					
					BatteryBO b = (BatteryBO)baseDeviceBO;
					b.setOldDeviceId(deviceId);
					b.setDeviceType(deviceType);
					b.setBarCode(deviceId);
					b.setDeviceId(deviceId);
					b.setLastDate(ba.getLastDate());
					b.setLastUser(ba.getLastUser());
					b.setLastUserId(ba.getLastUserId());
					iStationHistoryService.saveStationHistory(b, batteryBO, stationHistoryBO);
				} else if("switch_power".equals(deviceType)) {
					sp.setDeviceId(deviceId);
					sp.setBarCode(deviceId);
					sp.setBelongType("01");
					sp.setDeviceName("开关电源");
					sp.setNormalModNum(2);
					//sp.setOutLose("0");
					sp.setRatedPower("5000");
					sp.setStationId(station.getStationId());
					sp.setPowerId(deviceId);
					sp.setPowerMaker("");
					sp.setRemark("");
					sp.setOldDeviceId(deviceId);
					sp.setLastDate(new Date());
					sp.setLastUser(user.getUserName());
					sp.setLastUserId(user.getUserId());
					SwitchPowerBO switchPowerBO = new SwitchPowerBO();
					BeanCopier.create(SwitchPower.class, SwitchPowerBO.class, false).copy(sp, switchPowerBO, null);
					flag = iDeviceService.updateDevice(switchPowerBO);
					
					SwitchPowerBO b = (SwitchPowerBO)baseDeviceBO;
					b.setOldDeviceId(deviceId);
					b.setDeviceType(deviceType);
					b.setBarCode(deviceId);
					b.setDeviceId(deviceId);
					b.setLastDate(sp.getLastDate());
					b.setLastUser(sp.getLastUser());
					b.setLastUserId(sp.getLastUserId());
					iStationHistoryService.saveStationHistory(b, switchPowerBO, stationHistoryBO);
				} else if("air_conditioning".equals(deviceType)) {
					
					ac.setDeviceId(deviceId);
					ac.setBarCode(deviceId);
					ac.setDeviceName("空调");
					ac.setOwnedOperator("");
					ac.setStationId(station.getStationId());
					ac.setInStatus("");
					ac.setOutStatus("");
					ac.setOwnedOperator("");
					ac.setDeviceTypeName("");
					ac.setOldDeviceId(deviceId);
					//ac.setOutLose("0");
					ac.setRemark("");
					AirConditioningBO airConditioningBO = new AirConditioningBO();
					BeanCopier.create(AirConditioning.class, AirConditioningBO.class, false).copy(ac, airConditioningBO, null);
					flag = iDeviceService.updateDevice(airConditioningBO);
					
					AirConditioningBO b = (AirConditioningBO)baseDeviceBO;
					b.setOldDeviceId(deviceId);
					b.setDeviceType(deviceType);
					b.setBarCode(deviceId);
					b.setDeviceId(deviceId);
					iStationHistoryService.saveStationHistory(b, airConditioningBO, stationHistoryBO);
				}else if("ac_distribution".equals(deviceType)) {
					acBox.setDeviceId(deviceId);
					acBox.setBarCode(deviceId);
					acBox.setAcDistributionId(deviceId);
					acBox.setDeviceTypeName("交流配电箱");
					acBox.setDeviceType("ac_distribution");
					acBox.setRemark("");
					acBox.setOldDeviceId(deviceId);
					//ba.setOutLose("0");
					acBox.setInStatus("");
					acBox.setOutStatus("");
					acBox.setStationId(station.getStationId());
					acBox.setLastTime(date);
					acBox.setLastUser(user.getUserName());
					acBox.setLastUserId(user.getUserId());
					ACDistributionBoxBO acBoxBO = new ACDistributionBoxBO();
					BeanCopier.create(ACDistributionBox.class, ACDistributionBoxBO.class, false).copy(acBox, acBoxBO, null);
					flag = iDeviceService.updateDevice(acBoxBO);
					
					ACDistributionBoxBO acBoxBo = (ACDistributionBoxBO)baseDeviceBO;
					acBoxBo.setOldDeviceId(deviceId);
					acBoxBo.setDeviceType(deviceType);
					acBoxBo.setBarCode(deviceId);
					acBoxBo.setDeviceId(deviceId);
					acBoxBo.setLastTime(acBox.getLastTime());
					acBoxBo.setLastUser(acBox.getLastUser());
					acBoxBo.setLastUserId(acBox.getLastUserId());
					iStationHistoryService.saveStationHistory(acBoxBo, acBoxBO, stationHistoryBO);
				}else if("dc_distribution".equals(deviceType)) {
					dcBox.setDeviceId(deviceId);
					dcBox.setBarCode(deviceId);
					dcBox.setDcDistributionId(deviceId);
					dcBox.setDeviceTypeName("直流配电箱");
					dcBox.setDeviceType("dc_distribution");
					dcBox.setRemark("");
					dcBox.setOldDeviceId(deviceId);
					//ba.setOutLose("0");
					dcBox.setInStatus("");
					dcBox.setOutStatus("");
					dcBox.setStationId(station.getStationId());
					dcBox.setLastTime(date);
					dcBox.setLastUser(user.getUserName());
					dcBox.setLastUserId(user.getUserId());
					DCDistributionBoxBO dcBoxBO = new DCDistributionBoxBO();
					BeanCopier.create(DCDistributionBox.class, DCDistributionBoxBO.class, false).copy(dcBox, dcBoxBO, null);
					flag = iDeviceService.updateDevice(dcBoxBO);
					
					DCDistributionBoxBO b = (DCDistributionBoxBO)baseDeviceBO;
					b.setOldDeviceId(deviceId);
					b.setDeviceType(deviceType);
					b.setBarCode(deviceId);
					b.setDeviceId(deviceId);
					b.setLastTime(dcBox.getLastTime());
					b.setLastUser(dcBox.getLastUser());
					b.setLastUserId(dcBox.getLastUserId());
					iStationHistoryService.saveStationHistory(b, dcBoxBO, stationHistoryBO);
				}else if("transformer".equals(deviceType)) {
					transformer.setDeviceId(deviceId);
					transformer.setBarCode(deviceId);
					transformer.setTransformerId(deviceId);
					transformer.setDeviceTypeName("变压器");
					transformer.setDeviceType("transformer");
					transformer.setRemark("");
					transformer.setOldDeviceId(deviceId);
					//ba.setOutLose("0");
					transformer.setInStatus("");
					transformer.setOutStatus("");
					transformer.setStationId(station.getStationId());
					transformer.setLastTime(date);
					transformer.setLastUser(user.getUserName());
					transformer.setLastUserId(user.getUserId());
					TransformerBO transformerBO = new TransformerBO();
					BeanCopier.create(Transformer.class, TransformerBO.class, false).copy(transformer, transformerBO, null);
					flag = iDeviceService.updateDevice(transformerBO);
					
					TransformerBO b = (TransformerBO)baseDeviceBO;
					b.setOldDeviceId(deviceId);
					b.setDeviceType(deviceType);
					b.setBarCode(deviceId);
					b.setDeviceId(deviceId);
					b.setLastTime(transformer.getLastTime());
					b.setLastUser(transformer.getLastUser());
					b.setLastUserId(transformer.getLastUserId());
					iStationHistoryService.saveStationHistory(b, transformerBO, stationHistoryBO);
				}else if("grounding_lightning".equals(deviceType)) {
					groundingForLightning.setGroundingForLightningId(deviceId);
					groundingForLightning.setDeviceId(deviceId);
					groundingForLightning.setBarCode(deviceId);
					groundingForLightning.setOldDeviceId(deviceId);
					groundingForLightning.setDeviceTypeName("防雷接地设备");
					groundingForLightning.setDeviceType("grounding_lightning");
					groundingForLightning.setInStatus("");
					groundingForLightning.setOutStatus("");
					groundingForLightning.setStationId(station.getStationId());
					groundingForLightning.setLastTime(date);
					groundingForLightning.setLastUser(user.getUserName());
					groundingForLightning.setLastUserId(user.getUserId());
					groundingForLightning.setRemark("");
					GroundingForLightningBO groundingForLightningBo=new GroundingForLightningBO();
					BeanCopier.create(GroundingForLightning.class, GroundingForLightningBO.class, false).copy(groundingForLightning, groundingForLightningBo, null);
					flag = iDeviceService.updateDevice(groundingForLightningBo);
					
					GroundingForLightningBO b = (GroundingForLightningBO)baseDeviceBO;
					b.setOldDeviceId(deviceId);
					b.setDeviceType(deviceType);
					b.setBarCode(deviceId);
					b.setDeviceId(deviceId);
					b.setLastTime(groundingForLightning.getLastTime());
					b.setLastUser(groundingForLightning.getLastUser());
					b.setLastUserId(groundingForLightning.getLastUserId());
					iStationHistoryService.saveStationHistory(b, groundingForLightningBo, stationHistoryBO);
				}else if("pressure_regulator".equals(deviceType)) {
					pressureRegulator.setPrId(deviceId);
					pressureRegulator.setDeviceId(deviceId);
					pressureRegulator.setBarCode(deviceId);
					pressureRegulator.setOldDeviceId(deviceId);
					pressureRegulator.setDeviceTypeName("调压器");
					pressureRegulator.setDeviceType("pressure_regulator");
					pressureRegulator.setInStatus("");
					pressureRegulator.setOutStatus("");
					pressureRegulator.setStationId(station.getStationId());
					pressureRegulator.setLastTime(date);
					pressureRegulator.setLastUser(user.getUserName());
					pressureRegulator.setLastUserId(user.getUserId());
					pressureRegulator.setRemark("");
					PressureRegulatorBO pressureRegulatorBo=new PressureRegulatorBO();
					BeanCopier.create(PressureRegulator.class, PressureRegulatorBO.class, false).copy(pressureRegulator, pressureRegulatorBo, null);
					flag = iDeviceService.updateDevice(pressureRegulatorBo);
					
					PressureRegulatorBO b = (PressureRegulatorBO)baseDeviceBO;
					b.setOldDeviceId(deviceId);
					b.setDeviceType(deviceType);
					b.setBarCode(deviceId);
					b.setDeviceId(deviceId);
					b.setLastTime(pressureRegulator.getLastTime());
					b.setLastUser(pressureRegulator.getLastUser());
					b.setLastUserId(pressureRegulator.getLastUserId());
					iStationHistoryService.saveStationHistory(b, pressureRegulatorBo, stationHistoryBO);
				}else if("ac_line".equals(deviceType)) {
					acLine.setAclineId(deviceId);
					acLine.setDeviceId(deviceId);
					acLine.setBarCode(deviceId);
					acLine.setOldDeviceId(deviceId);
					acLine.setDeviceTypeName("交流引入");
					acLine.setDeviceType("ac_line");
					acLine.setInStatus("");
					acLine.setOutStatus("");
					acLine.setStationId(station.getStationId());
					acLine.setLastTime(date);
					acLine.setLastUser(user.getUserName());
					acLine.setLastUserId(user.getUserId());
					acLine.setRemark("");
					ACLineBO acLineBo=new ACLineBO();
					BeanCopier.create(ACLine.class, ACLineBO.class, false).copy(acLine, acLineBo, null);
					flag = iDeviceService.updateDevice(acLineBo);
					
					ACLineBO b = (ACLineBO)baseDeviceBO;
					b.setOldDeviceId(deviceId);
					b.setDeviceType(deviceType);
					b.setBarCode(deviceId);
					b.setDeviceId(deviceId);
					b.setLastTime(acLine.getLastTime());
					b.setLastUser(acLine.getLastUser());
					b.setLastUserId(acLine.getLastUserId());
					iStationHistoryService.saveStationHistory(b, acLineBo, stationHistoryBO);
				}else if("monitoring".equals(deviceType)) {
					monitoringInfo.setMonitoringId(deviceId);
					monitoringInfo.setDeviceId(deviceId);
					monitoringInfo.setBarCode(deviceId);
					monitoringInfo.setOldDeviceId(deviceId);
					monitoringInfo.setDeviceTypeName("监控设备");
					monitoringInfo.setDeviceType("monitoring");
					monitoringInfo.setInStatus("");
					monitoringInfo.setOutStatus("");
					monitoringInfo.setStationId(station.getStationId());
					monitoringInfo.setLastTime(date);
					monitoringInfo.setLastUser(user.getUserName());
					monitoringInfo.setLastUserId(user.getUserId());
					monitoringInfo.setRemark("");
					Monitoring monitoring=new Monitoring();
					BeanCopier.create(MonitoringInfo.class, Monitoring.class, false).copy(monitoringInfo, monitoring, null);
					flag = iDeviceService.updateDevice(monitoring);
					
					Monitoring b = (Monitoring)baseDeviceBO;
					b.setOldDeviceId(deviceId);
					b.setDeviceType(deviceType);
					b.setBarCode(deviceId);
					b.setDeviceId(deviceId);
					b.setLastTime(monitoringInfo.getLastTime());
					b.setLastUser(monitoringInfo.getLastUser());
					b.setLastUserId(monitoringInfo.getLastUserId());
					iStationHistoryService.saveStationHistory(b, monitoring, stationHistoryBO);
				}
			}
		} catch(Exception e) {
			LogUtil.error(e);
			return ERRORPAGE;
		}
		if(flag){
			flag  = saveWaitUploadPhoto(deviceImgs,station.getStationId(),deviceId,deviceType,(UserInfo)session.getAttribute(SessionConstants.SUSER));//保存照片
			if(!flag)
				return ERRORPAGE;
		}
		model.addAttribute("deviceType", deviceType);
		model.addAttribute("deviceId", deviceId);
		model.addAttribute("succ", true);
		model.addAttribute("msg", "保存成功");
		return getResultUrl(session);
	}
	
	/**
	 * TODO 设备异常上报
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value = "/submitDeviceExceptReport", method = RequestMethod.POST)
	public String submitDeviceExceptReport(HttpServletRequest request,  HttpSession session, ModelMap model){
		String deviceImgs = request.getParameter("deviceImgs");
		String deviceId = request.getParameter("deviceId");
		String remark = request.getParameter("remark");
		String[] abnormalCodeArray = request.getParameterValues("abnormalCode");
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		if(station == null) return ERRORPAGE;
		
		String abnormalCode="";
		StringBuilder abnormalCodeB=new StringBuilder();
		if(null!=abnormalCodeArray && abnormalCodeArray.length>0){
			for(String str:abnormalCodeArray){
				abnormalCodeB.append(","+str);
			}
			abnormalCode=abnormalCodeB.substring(1);
		}
		
		DeviceInspectRecord dir = saveDeviceInspectRecord(session, deviceId, "false",remark,abnormalCode);//保存设备巡检记录
		if(StringUtils.isEmpty(dir.getRecordId())) return ERRORPAGE;
		
		boolean photoSaveSuccess = saveWaitUploadPhoto(deviceImgs,station.getStationId(),dir.getRecordId(),"deviceExcept",(UserInfo)session.getAttribute(SessionConstants.SUSER));//保存照片
		if(!photoSaveSuccess) return ERRORPAGE;
		return getResultUrl(session);
	}
	/**
	 * 获取跳转页面之后的弹出信息
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/getAlertMsg", method = RequestMethod.POST)
	public String getAlertMsg(HttpSession session,HttpServletRequest request, ModelMap model){
		
		if(StringUtils.isNotEmpty(getSessionUser(session).getAlertMsg())){
			model.addAttribute("succ",true);
			model.addAttribute("msg", getSessionUser(session).getAlertMsg());
			getSessionUser(session).setAlertMsg(null);
			return "";
		}
		model.addAttribute("succ",false);
		return "";
	}
	/**
	 * 获取跳转页面之后的弹出信息
	 */
	@SuppressWarnings("unchecked")
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/barCodeBelongtoOtherStation", method = RequestMethod.POST)
	public String barCodeBelongtoOtherStation(HttpSession session,HttpServletRequest request, ModelMap model){
		String barCode=request.getParameter("barCode");
		List<BaseDevice> deviceList = (List<BaseDevice>)session.getAttribute(SessionConstants.SDEVICESLIST);
		boolean barCodeBelongtoOtherStation = true;
		String msg="服务器繁忙！";
		
		if(deviceList != null && deviceList.size() > 0) {
			for(BaseDevice device : deviceList){
				if(barCode.equals(device.getDeviceId())){
					barCodeBelongtoOtherStation=false;
					break;
				}
			}
		}
		
		if(barCodeBelongtoOtherStation && StringUtils.isNotEmpty(barCode)){
			try {
				barCodeBelongtoOtherStation=iDeviceService.judgeDeviceExist(barCode);
				msg= barCodeBelongtoOtherStation? "条码已被使用!" : "";
			} catch (Exception e) {
				LogUtil.error(e);
			}
		}
		
		model.addAttribute("succ",barCodeBelongtoOtherStation);
		model.addAttribute("msg", msg);
		return "";
	}
	
	/**
	 * 获取跳转页面之后的弹出信息
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/checkBarCodeExists", method = RequestMethod.POST)
	public String checkBarCodeExists(HttpSession session,HttpServletRequest request, ModelMap model){
		String barCode=request.getParameter("barCode");
		String msg = "";
		boolean isExists = true;
			try {
				isExists=iDeviceService.judgeDeviceExist(barCode);
				msg= isExists? "条码已被使用!" : "";
			} catch (Exception e) {
				LogUtil.error(e);
			}
		
		model.addAttribute("succ",isExists);
		model.addAttribute("msg", msg);
		return "";
	}
	
	/**
	 * 获取跳转页面之后的弹出信息
	 */
	@SuppressWarnings("unchecked")
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/replaceBarCode", method = RequestMethod.POST)
	public String replaceBarCode(HttpSession session,HttpServletRequest request, ModelMap model){
		String barCode=request.getParameter("barCode");
		String oldBarCode=request.getParameter("oldBarCode");
		String deviceType=request.getParameter("deviceType");
		String outLose = request.getParameter("outLose");
		Battery ba = new Battery();
		SwitchPower sp = new SwitchPower();
		AirConditioning ac = new AirConditioning();
		ACDistributionBox acBox=new ACDistributionBox();
		DCDistributionBox dcBox=new DCDistributionBox();
		Transformer transformer=new Transformer();
		GroundingForLightning groundingForLightning=new GroundingForLightning();
		PressureRegulator pressureRegulator=new PressureRegulator();
		ACLine acLine=new ACLine();
		MonitoringInfo monitoringInfo=new MonitoringInfo();
		boolean flag = false;
		try {
			if(deviceType != null && !"".equals(deviceType)) {
				if("battery".equals(deviceType)) {
					if(outLose != null && !"".equals(outLose) && "-1".equals(outLose)) {
						ba.setOutLose("99");//PC端新增的设备，条码更换后为99
					}
					ba.setBatteryId(barCode);
					ba.setOldDeviceId(oldBarCode);
					ba.setDeviceType(deviceType);
					BatteryBO batteryBO = new BatteryBO();
					BeanCopier.create(Battery.class, BatteryBO.class, false).copy(ba, batteryBO, null);
					flag = iDeviceService.updateBaseDeviceBarCode(batteryBO);
				} else if("switch_power".equals(deviceType)) {
					if(outLose != null && !"".equals(outLose) && "-1".equals(outLose)) {
						sp.setOutLose("99");//PC端新增的设备，条码更换后为99
					}
					sp.setPowerId(barCode);
					sp.setOldDeviceId(oldBarCode);
					sp.setDeviceType(deviceType);
					SwitchPowerBO switchPowerBO = new SwitchPowerBO();
					BeanCopier.create(SwitchPower.class, SwitchPowerBO.class, false).copy(sp, switchPowerBO, null);
					flag = iDeviceService.updateBaseDeviceBarCode(switchPowerBO);
				} else if("air_conditioning".equals(deviceType)) {
					if(outLose != null && !"".equals(outLose) && "-1".equals(outLose)) {
						ac.setOutLose("99");//PC端新增的设备，条码更换后为99
					}
					ac.setDeviceId(barCode);
					ac.setOldDeviceId(oldBarCode);
					ac.setDeviceType(deviceType);
					AirConditioningBO airConditioningBO = new AirConditioningBO();
					BeanCopier.create(AirConditioning.class, AirConditioningBO.class, false).copy(ac, airConditioningBO, null);
					flag = iDeviceService.updateBaseDeviceBarCode(airConditioningBO);
				}else if("ac_distribution".equals(deviceType)) {
					if(outLose != null && !"".equals(outLose) && "-1".equals(outLose)) {
						acBox.setOutLose("99");//PC端新增的设备，条码更换后为99
					}
					acBox.setAcDistributionId(barCode);
					acBox.setDeviceId(barCode);
					acBox.setOldDeviceId(oldBarCode);
					acBox.setDeviceType(deviceType);
					ACDistributionBoxBO acBoxBO=new ACDistributionBoxBO();
					BeanCopier.create(ACDistributionBox.class, ACDistributionBoxBO.class, false).copy(acBox, acBoxBO, null);
					flag = iDeviceService.updateBaseDeviceBarCode(acBoxBO);
				}else if("dc_distribution".equals(deviceType)) {
					if(outLose != null && !"".equals(outLose) && "-1".equals(outLose)) {
						dcBox.setOutLose("99");//PC端新增的设备，条码更换后为99
					}
					dcBox.setDcDistributionId(barCode);
					dcBox.setDeviceId(barCode);
					dcBox.setOldDeviceId(oldBarCode);
					dcBox.setDeviceType(deviceType);
					DCDistributionBoxBO dcBoxBO=new DCDistributionBoxBO();
					BeanCopier.create(DCDistributionBox.class, DCDistributionBoxBO.class, false).copy(dcBox, dcBoxBO, null);
					flag = iDeviceService.updateBaseDeviceBarCode(dcBoxBO);
				}else if("transformer".equals(deviceType)) {
					if(outLose != null && !"".equals(outLose) && "-1".equals(outLose)) {
						transformer.setOutLose("99");//PC端新增的设备，条码更换后为99
					}
					transformer.setTransformerId(barCode);
					transformer.setDeviceId(barCode);
					transformer.setOldDeviceId(oldBarCode);
					transformer.setDeviceType(deviceType);
					TransformerBO transformerBO=new TransformerBO();
					BeanCopier.create(Transformer.class, TransformerBO.class, false).copy(transformer, transformerBO, null);
					flag = iDeviceService.updateBaseDeviceBarCode(transformerBO);
				}else if("grounding_lightning".equals(deviceType)) {
					if(outLose != null && !"".equals(outLose) && "-1".equals(outLose)) {
						groundingForLightning.setOutLose("99");//PC端新增的设备，条码更换后为99
					}
					groundingForLightning.setGroundingForLightningId(barCode);
					groundingForLightning.setDeviceId(barCode);
					groundingForLightning.setOldDeviceId(oldBarCode);
					groundingForLightning.setDeviceType(deviceType);
					GroundingForLightningBO groundingForLightningBO=new GroundingForLightningBO();
					BeanCopier.create(GroundingForLightning.class, GroundingForLightningBO.class, false).copy(groundingForLightning, groundingForLightningBO, null);
					flag = iDeviceService.updateBaseDeviceBarCode(groundingForLightningBO);
				}else if("pressure_regulator".equals(deviceType)) {
					if(outLose != null && !"".equals(outLose) && "-1".equals(outLose)) {
						pressureRegulator.setOutLose("99");//PC端新增的设备，条码更换后为99
					}
					pressureRegulator.setPrId(barCode);
					pressureRegulator.setDeviceId(barCode);
					pressureRegulator.setOldDeviceId(oldBarCode);
					pressureRegulator.setDeviceType(deviceType);
					PressureRegulatorBO pressureRegulatorBO=new PressureRegulatorBO();
					BeanCopier.create(PressureRegulator.class, PressureRegulatorBO.class, false).copy(pressureRegulator, pressureRegulatorBO, null);
					flag = iDeviceService.updateBaseDeviceBarCode(pressureRegulatorBO);
				}else if("ac_line".equals(deviceType)) {
					if(outLose != null && !"".equals(outLose) && "-1".equals(outLose)) {
						acLine.setOutLose("99");//PC端新增的设备，条码更换后为99
					}
					acLine.setAclineId(barCode);
					acLine.setDeviceId(barCode);
					acLine.setOldDeviceId(oldBarCode);
					acLine.setDeviceType(deviceType);
					ACLineBO acLineBO=new ACLineBO();
					BeanCopier.create(ACLine.class, ACLineBO.class, false).copy(acLine, acLineBO, null);
					flag = iDeviceService.updateBaseDeviceBarCode(acLineBO);
				}else if("monitoring".equals(deviceType)) {
					if(outLose != null && !"".equals(outLose) && "-1".equals(outLose)) {
						acLine.setOutLose("99");//PC端新增的设备，条码更换后为99
					}
					monitoringInfo.setMonitoringId(barCode);
					monitoringInfo.setDeviceId(barCode);
					monitoringInfo.setOldDeviceId(oldBarCode);
					monitoringInfo.setDeviceType(deviceType);
					Monitoring monitoring=new Monitoring();
					BeanCopier.create(MonitoringInfo.class, Monitoring.class, false).copy(monitoringInfo, monitoring, null);
					flag = iDeviceService.updateBaseDeviceBarCode(monitoring);
				}
				DeviceBarCodeBO deviceBarCode = new DeviceBarCodeBO();
				deviceBarCode.setBarCode(barCode);
				deviceBarCode.setOldBarCode(oldBarCode);
				iDeviceService.updateDeviceBarCode(deviceBarCode);
				List<BaseDevice> deviceList = (List<BaseDevice>)session.getAttribute(SessionConstants.SDEVICESLIST);
				if(deviceList != null && deviceList.size() > 0) {
					for(int i = 0; i < deviceList.size(); i++) {
						BaseDevice bd = deviceList.get(i);
						if(oldBarCode.equals(bd.getDeviceId())) {
							bd.setDeviceId(barCode);
						}
					}
				}
				DeviceInspectionRecordBO deviceInspectionRecord = new DeviceInspectionRecordBO();
				deviceInspectionRecord.setBarCode(barCode);
				deviceInspectionRecord.setOldBarCode(oldBarCode);
				iDeviceService.updateDeviceInspectionBarCode(deviceInspectionRecord);
				
				PhotoBO photo = new PhotoBO();
				photo.setRelateId(barCode);
				photo.setOldRelateId(oldBarCode);
				iPhotoService.updatePhotoRelateId(photo);
			}
		} catch (FirstQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("succ",flag);
		String msg = "";
		msg= flag? "更换条码成功!" : "更换条码失败!";
		model.addAttribute("msg", msg);
		return "";
	}
	
	
	/**保存设备巡检记录*/
	private DeviceInspectRecord saveDeviceInspectRecord(HttpSession session,String deviceId,String deviceStatus,String remark,String abnormalCode){
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		StationInspectRecord sir = (StationInspectRecord)session.getAttribute(SessionConstants.STATION_INSPECT_RECORD);
		Date date = new Date();
		DeviceInspectRecord dir = new DeviceInspectRecord();
		if(station == null) return dir;
		
		dir.setStationId(station.getStationId());
		dir.setDeviceId(deviceId);
		dir.setInspectionId(sir.getInspectionId());
		dir.setDeviceStatus(deviceStatus);
		dir.setBarCode(deviceId);
		dir.setOutInFlag("in");
		dir.setAbnormalCode(abnormalCode);
		dir.setRemark(remark);
		dir.setScanDate(date);
		dir.setSubmitDate(date);
		dir.setLatitude("");
		dir.setLongitude("");
		UserInfo userInfo = getSessionUser(session);
		String jobId=userInfo.getJobId();
		if(WebContentConstants.JOB_STATION_INSPECTION.equals(jobId)){//巡检
			dir.setInspectionType("0");
		}else if (WebContentConstants.JOB_STATION_QUARTER_INSPECTION.equals(jobId)) { 
			dir.setInspectionType("1");
		}
		DeviceInspectionRecordBO deviceInspectionRecord = new DeviceInspectionRecordBO();
		BeanCopier.create(DeviceInspectRecord.class, DeviceInspectionRecordBO.class, false).copy(dir, deviceInspectionRecord, null);
		try {
			deviceInspectionRecord = iDeviceService.saveInspectionDevice(deviceInspectionRecord);
			BeanCopier.create(DeviceInspectionRecordBO.class, DeviceInspectRecord.class, false).copy(deviceInspectionRecord, dir, null);
		} catch (Exception e) {
			LogUtil.error(e);
		}
		return  dir;
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
	
	private String getResultUrl(HttpSession session){
		UserInfo userInfo = getSessionUser(session);
		String jobId=userInfo.getJobId();
		if(WebContentConstants.JOB_STATION_INSPECTION.equals(jobId)){//巡检
			return "redirect:/devices/getDeviceList";
		}else if(WebContentConstants.JOB_DISCHARGE.equals(jobId)){//放电测试
			return "redirect:/discharge/getDeviceList";
		}else if (WebContentConstants.JOB_GENERATE_POWER.equals(jobId)) {
			return "redirect:/generatePower/getDeviceList";
		}else if (WebContentConstants.JOB_STATION_QUARTER_INSPECTION.equals(jobId)) {
			return "redirect:/devices/getDeviceList";
		}
		return "";
	}

}
