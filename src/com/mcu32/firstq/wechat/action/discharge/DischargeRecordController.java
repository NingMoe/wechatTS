package com.mcu32.firstq.wechat.action.discharge;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcu32.firstq.common.bean.bo.BatteryCheckRecordBO;
import com.mcu32.firstq.common.bean.bo.BatteryCheckRecordDetailBO;
import com.mcu32.firstq.common.bean.bo.DischargeRecordBO;
import com.mcu32.firstq.common.bean.bo.PhotoBO;
import com.mcu32.firstq.common.bean.bo.SoundBO;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.BaseDevice;
import com.mcu32.firstq.wechat.bean.Battery;
import com.mcu32.firstq.wechat.bean.BatteryCheckRecord;
import com.mcu32.firstq.wechat.bean.BatteryCheckRecordDetail;
import com.mcu32.firstq.wechat.bean.DischargeRecord;
import com.mcu32.firstq.wechat.bean.PhotoInfo;
import com.mcu32.firstq.wechat.bean.SoundInfo;
import com.mcu32.firstq.wechat.bean.StationInfo;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.interceptor.AccessRequired;
import com.mcu32.firstq.wechat.util.ActionCommonMethod;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.SessionConstants;
import com.mcu32.firstq.wechat.util.WebContentConstants;

@Controller
@RequestMapping(value = "/discharge")
public class DischargeRecordController extends BaseController{
	
	//准备开始放电任务
	@RequestMapping(value = "/readyStart", method = RequestMethod.GET)
	public String readyStart(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		if(station ==  null){
			return "commons/error";
		}
		//获取基站蓄电池
		List<BaseDevice> batteryList = new ArrayList<BaseDevice>();
		try {
			List<BaseDevice> deviceList = ActionCommonMethod.getAllDevices(station.getStationId(), "",(UserInfo) session.getAttribute(SessionConstants.SUSER));
			for (BaseDevice baseDevice : deviceList) {
				if ("battery".equals(baseDevice.getDeviceType())) {
					batteryList.add(baseDevice);
				}
			}
		} catch (Exception e1) {
			LogUtil.error("查询设备出错",e1);
			return "commons/error";
		}
		session.setAttribute(SessionConstants.SDEVICESLIST, batteryList);
		DischargeRecord dr = null;
		try {
			dr = getDischargeRecord(station.getStationId(),(UserInfo)session.getAttribute(SessionConstants.SUSER));
		} catch (Exception e) {
			LogUtil.error("查询放电测试记录发生错误",e);
			return "commons/error";
		}
		//是否继续操作标识
		if(((dr != null && StringUtils.hasText(dr.getRecordId()))) ){
			session.setAttribute(SessionConstants.DISCHARGE_RECORD, dr);
			boolean flag2 = false;
			if(dr.getStatus().equals(WebContentConstants.STATUS_COMPLETE) || dr.getStatus().equals(WebContentConstants.STATUS_SUSPEND)){
				flag2 = true;
			}
			if(flag2){
				model.addAttribute("status", "todo");
			}else{
				model.addAttribute("status", "continue");
			}
			DecimalFormat df = new DecimalFormat("#0.00");
			Date finishT = dr.getFinishTime();
			if(finishT == null){
				finishT = new Date();
			}
			double timeSpan = (finishT.getTime() - dr.getOperatTime().getTime())/(double)1000/(double)60/(double)60;
			model.addAttribute("timeSpan", df.format(timeSpan));
			model.addAttribute("history", "true");
			//放电检测进度
			String step = dr.getStep();
			double progress2 = Double.valueOf(step)/(double)3*100;
			model.addAttribute("progress2", df.format(progress2));
		}else{
			model.addAttribute("status", "todo");
			model.addAttribute("history", "false");
		}
		model.addAttribute("batteryNum",batteryList.size());
		return "discharge/startDischarge";
	}
	
	//开始放电测试任务
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
		//放电测试记录
		DischargeRecord dr = new DischargeRecord();
		dr.setRecordId(getUUID());
		dr.setStationId(station.getStationId());
		dr.setLastOperator(userInfo.getUserName());
		dr.setLastOperatorId(userInfo.getUserId());
		dr.setOperatTime(new Date());
		dr.setStep("0");
		dr.setStatus(WebContentConstants.STATUS_PROGRESS);
		DischargeRecordBO drBO = new DischargeRecordBO();
		BeanCopier.create(DischargeRecord.class, DischargeRecordBO.class, false).copy(dr, drBO, null);;
		try {
			FirstQInterfaces.getIDischargeRecordService().saveDischargeRecord(drBO);
		} catch (Exception e) {
			LogUtil.error("保存基站巡检记录发生错误",e);
			return "commons/error";
		}
		session.setAttribute(SessionConstants.DISCHARGE_RECORD, dr);
		List<BaseDevice> deviceList = ActionCommonMethod.getAllDevices(station.getStationId(), "",(UserInfo) session.getAttribute(SessionConstants.SUSER));
		String batteryVoltage = getBatteryVoltage(deviceList);
		if(batteryVoltage != null && !"".equals(batteryVoltage)) {
			model.addAttribute("batteryVoltage", batteryVoltage);
		}
		return "discharge/dischargeRecord";
	}
	
	//继续放电测试任务
	@RequestMapping(value = "/toContinue", method = RequestMethod.GET)
	public String toContinue(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		DischargeRecord dr = null;
		List<BaseDevice> deviceList = ActionCommonMethod.getAllDevices(station.getStationId(), "",(UserInfo) session.getAttribute(SessionConstants.SUSER));
		String batteryVoltage = getBatteryVoltage(deviceList);
		if(batteryVoltage != null && !"".equals(batteryVoltage)) {
			model.addAttribute("batteryVoltage", batteryVoltage);
		}
		try {
			dr = getDischargeRecord(station.getStationId(),(UserInfo) session.getAttribute(SessionConstants.SUSER));
		} catch (Exception e) {
			LogUtil.error("查询放电测试记录发生错误",e);
			return "commons/error";
		}
		//根据步骤跳转到不同页面
		if(dr.getStep().equals("1")){
			//根据放电方式计算检测轮次
			String dischargeType = dr.getDischargeType();
			//获取测试轮次
			int turn = getTurnTime(dischargeType);
			List<String> turntimeList = new ArrayList<String>();
			for(int i=0;i<turn;i++){
				turntimeList.add((i+1)+"");
			}
			Map<Integer,List<BatteryCheckRecord>> map = new LinkedHashMap<Integer,List<BatteryCheckRecord>>();
			for(BatteryCheckRecord record : dr.getCheckRecordList()){
				List<BatteryCheckRecord> list = map.get(record.getCheckTime());
				if(list == null){
					list = new ArrayList<BatteryCheckRecord>();
					map.put(record.getCheckTime(), list);
				}
				list.add(record);
			}
			model.addAttribute("turntimeList", turntimeList);
			model.addAttribute("checkRecordMap", map);
			return "discharge/singleBatteryCheck";
		}else if(dr.getStep().equals("2") || dr.getStep().equals("3")){
			return "discharge/finishDischarge";
		}
		return "discharge/dischargeRecord";
	}
	
	// 更新任务状态
	@RequestMapping(value = "/updateRecordStatus", method = RequestMethod.GET)
	public String updateRecordStatus(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		String status = request.getParameter("status");
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		if(station ==  null){
			return "commons/error";
		}
		DischargeRecord dr = (DischargeRecord)session.getAttribute(SessionConstants.DISCHARGE_RECORD);
		dr.setOperatTime(new Date());
		dr.setOperatTime(null);
		dr.setFinishTime(new Date());
		dr.setLastOperator(user.getUserName());
		dr.setLastOperatorId(user.getUserId());
		if("0".equals(status)){
			dr.setStatus(WebContentConstants.STATUS_COMPLETE);
		}else if("1".equals(status)){
			dr.setStatus(WebContentConstants.STATUS_SUSPEND);
		}
		DischargeRecordBO drBO = new DischargeRecordBO();
		BeanCopier.create(DischargeRecord.class, DischargeRecordBO.class, false).copy(dr, drBO, null);
		try {
			FirstQInterfaces.getIDischargeRecordService().updateDischargeRecord(drBO);
		} catch (Exception e) {
			LogUtil.error("更新记录状态发生错误",e);
			return "commons/error";
		}
		return "redirect:/discharge/readyStart";
	}
	
	
	// 检查全部是否完成
	@RequestMapping(value = "/checkAllStatus", method = RequestMethod.GET)
	public String checkAllStatus(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		if(station ==  null){
			return "commons/error";
		}
		//0-全部完成,1-放电未完成,
		String status = "0";
		String result = "";
		//检查放电检测是否完成
		DischargeRecord dr;
		try {
			dr = getDischargeRecord(station.getStationId(),(UserInfo)session.getAttribute(SessionConstants.SUSER));
		} catch (Exception e) {
			LogUtil.error("查询放电测试记录发生错误",e);
			return "commons/error";
		}
		if(dr != null){
			if(!dr.getStatus().equals(WebContentConstants.STATUS_COMPLETE)){
				status = "1";
				result += "放电检测尚未完成<br/>";
			}
		}
		result += "是否结束本次放电任务？";
		model.addAttribute("status", status);
		model.addAttribute("result", result);
		return status;
	}
	
	
	// 检查单体检测是否完成
	@RequestMapping(value = "/checkBatteryCheckStatus", method = RequestMethod.GET)
	public String checkBatteryCheckStatus(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		if(station ==  null){
			return "commons/error";
		}
		DischargeRecord dr;
		try {
			dr = getDischargeRecord(station.getStationId(), (UserInfo)session.getAttribute(SessionConstants.SUSER));
		} catch (Exception e) {
			LogUtil.error("查询放电测试记录发生错误",e);
			return "commons/error";
		}
		String status = "0";
		if(dr != null && dr.getCheckRecordList() != null){
			List<BatteryCheckRecord> crList = dr.getCheckRecordList();
			for(BatteryCheckRecord cr : crList){
				if(cr.getDetailList() != null){
					boolean flag = false;
					for(BatteryCheckRecordDetail detail : cr.getDetailList()){
						if(detail.getRecordValue() == null){
							flag = true;
							status = "第"+cr.getCheckTime()+"轮单体检测记录填写未完成";
							break;
						}
					}
					if(flag){
						break;
					}
				}else if(!StringUtils.hasText(cr.getTerminalVoltage())){
					status = "第"+cr.getCheckTime()+"轮端电压未填写";
					break;
				}else if(StringUtils.hasText(cr.getTerminalVoltage())
						&& cr.getTerminalVoltage().equals("0")){
					status = "第"+cr.getCheckTime()+"轮端电压未填写";
					break;
				}
				
			}
		}
		model.addAttribute("status", status);
		return status;
	}
	
	// 获取测试轮次
	private int getTurnTime(String dischargeType){
		int turn = 0;
		if(dischargeType.equals("1")){
			turn = 2;
		}else if(dischargeType.equals("2")){
			turn = 4;
		}else if(dischargeType.equals("3")){
			turn = 6;
		}else if(dischargeType.equals("4")){
			turn = 8;
		}else if(dischargeType.equals("5")){
			turn = 10;
		}
		return turn;
	}
	
	public  DischargeRecord getDischargeRecord(String stationId,UserInfo user) throws Exception{
		DischargeRecord dr = new DischargeRecord();
		
		DischargeRecordBO drBO = FirstQInterfaces.getIDischargeRecordService().getDischargeRecord(stationId, user.getUserId());
		if(drBO != null && StringUtils.hasText(drBO.getRecordId())){
			BeanCopier.create(DischargeRecordBO.class, DischargeRecord.class, false).copy(drBO, dr, null);
			List<BatteryCheckRecordBO> checkRecordBOList = drBO.getCheckRecordList();
			List<BatteryCheckRecord>  checkRecordList = new ArrayList<BatteryCheckRecord>();
			if(checkRecordBOList != null){
				for(BatteryCheckRecordBO checkRecordBO : checkRecordBOList){
					BatteryCheckRecord checkRecord = new BatteryCheckRecord();
					BeanCopier.create(BatteryCheckRecordBO.class, BatteryCheckRecord.class, false).copy(checkRecordBO, checkRecord, null);
					checkRecordList.add(checkRecord);
					List<BatteryCheckRecordDetailBO> detailBOList = checkRecordBO.getDetailList();
					List<BatteryCheckRecordDetail> detailList = new ArrayList<BatteryCheckRecordDetail>();
					for (BatteryCheckRecordDetailBO batteryCheckRecordDetailBO : detailBOList) {
						BatteryCheckRecordDetail checkDetail = new BatteryCheckRecordDetail();
						BeanCopier.create(BatteryCheckRecordDetailBO.class, BatteryCheckRecordDetail.class, false).copy(batteryCheckRecordDetailBO, checkDetail, null);
						detailList.add(checkDetail);
					}
					checkRecord.setDetailList(detailList);
				}
				dr.setCheckRecordList(checkRecordList);
			}
		}
		if(dr != null && dr.getCheckRecordList() != null){
			for(BatteryCheckRecord checkRecord : dr.getCheckRecordList()){
				if(checkRecord.getDetailList() != null){
					for(BatteryCheckRecordDetail detail : checkRecord.getDetailList() ){
						String objectKey = "batteryId="+checkRecord.getBatteryGroupId()+"/turnNum="+checkRecord.getCheckTime()+"/checkNum="+detail.getSequence();
						detail.setObjectKey(objectKey);
					}
				}
			}
			Map<String, List<PhotoBO>> mapBO = FirstQInterfaces.getIPhotoService().getPhotoByRelateIds(new String[]{dr.getRecordId()});
			if (mapBO != null) {
				List<PhotoBO> photoBOList = mapBO.get(dr.getRecordId());
				if(photoBOList != null){
					List<PhotoInfo> startVoltagePiList = new ArrayList<PhotoInfo>();
					List<PhotoInfo> endVoltagePiList = new ArrayList<PhotoInfo>();
					for(PhotoBO pi : photoBOList){
						PhotoInfo photoInfo = new PhotoInfo();
						BeanCopier.create(PhotoBO.class, PhotoInfo.class, false).copy(pi, photoInfo, null);
						if(pi.getPhotoType().equals("startVoltage")){
							startVoltagePiList.add(photoInfo);
						}else if(pi.getPhotoType().equals("endVoltage")){
							endVoltagePiList.add(photoInfo);
						}
					}
					FirstqTool.convertPhotoPath(startVoltagePiList,user);
					FirstqTool.convertPhotoPath(endVoltagePiList,user);
					dr.setStartVoltagePhotoList(startVoltagePiList);
					dr.setEndVoltagePhotoList(endVoltagePiList);
				}
			}
			List<BatteryCheckRecord> list = dr.getCheckRecordList();
			String[] idList = null;
			if(list!= null){
				for(int i=0;i<list.size();i++){
					idList = (String[]) ArrayUtils.add(idList, list.get(i).getRecordId());
				}
			}
			Map<String, List<SoundBO>> soundMap = FirstQInterfaces.getISoundService().getSoundByRelatedId(idList);
			if (soundMap != null) {
				for(int i=0;i<dr.getCheckRecordList().size();i++){
					List<SoundInfo> soundList = new ArrayList<SoundInfo>();
					List<SoundBO> soundBOList = soundMap.get(dr.getCheckRecordList().get(i).getRecordId());
					if (soundBOList != null) {
						for (SoundBO soundBO : soundBOList) {
							SoundInfo soundInfo = new SoundInfo();
							BeanCopier.create(SoundBO.class, SoundInfo.class, false).copy(soundBO, soundInfo, null);
							soundList.add(soundInfo);
						}
						dr.getCheckRecordList().get(i).setSoundList(soundList);
					}
				}
			}
		}
		return dr;
	}
	// 获取蓄电池信息
	@RequestMapping(value = "/getBatteryInfo", method = RequestMethod.GET)
	public String getBatteryInfo(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		String batteryId = request.getParameter("batteryId");
		@SuppressWarnings("unchecked")
		List<Battery> deviceList = (List<Battery>)session.getAttribute(SessionConstants.SDEVICESLIST);
		Battery currDevice = new Battery();
		if(deviceList != null && deviceList.size() > 0) {
			for(Battery device : deviceList){
				if(batteryId.equals(device.getDeviceId())){
					currDevice = device;
					break;
				}
			}
		}
		UserInfo userInfo = (UserInfo)request.getSession().getAttribute(SessionConstants.SUSER);
		String jobid = userInfo.getJobId();
		model.addAttribute("jobid", jobid);
		model.addAttribute("baseDevice", currDevice);
		return "discharge/batteryInfo";	
	}
	
	//获取设备集合中，蓄电池的单体电压值
	public String getBatteryVoltage(List<BaseDevice> deviceList) {
		String batteryVoltage = "";
		if(deviceList != null && deviceList.size() > 0) {
			for(int i = 0; i < deviceList.size(); i++) {
				BaseDevice bd = deviceList.get(i);
				if(bd instanceof Battery) {
					String bv = ((Battery)bd).getBatteryVoltage();
					if(bv != null && !"".equals(bv)) {
						batteryVoltage = bv;
						break;
					}
				}
			}
		}
		return batteryVoltage;
	}
}
