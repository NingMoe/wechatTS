package com.mcu32.firstq.wechat.action.discharge;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcu32.firstq.common.bean.bo.BatteryCheckRecordBO;
import com.mcu32.firstq.common.bean.bo.BatteryCheckRecordDetailBO;
import com.mcu32.firstq.common.bean.bo.DischargeRecordBO;
import com.mcu32.firstq.common.bean.bo.PhotoBO;
import com.mcu32.firstq.common.bean.bo.SoundBO;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.Battery;
import com.mcu32.firstq.wechat.bean.BatteryCheckRecord;
import com.mcu32.firstq.wechat.bean.BatteryCheckRecordDetail;
import com.mcu32.firstq.wechat.bean.DischargeRecord;
import com.mcu32.firstq.wechat.bean.PhotoInfo;
import com.mcu32.firstq.wechat.bean.SoundInfo;
import com.mcu32.firstq.wechat.bean.StationInfo;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.SessionConstants;
import com.mcu32.firstq.wechat.util.WebContentConstants;

@Controller
@RequestMapping(value = "/singleBatteryCheck")
public class SingleBatteryCheckRecordController extends BaseController{

	// 提交检测第一步
	@RequestMapping(value = "/saveBatteryCheckStepOne", method = RequestMethod.POST)
	public String saveBatteryCheckStepOne(DischargeRecord record,HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		setWechatConfig(request,model);
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		DischargeRecord dr = (DischargeRecord)session.getAttribute(SessionConstants.DISCHARGE_RECORD);
		record.setRecordId(dr.getRecordId());
		record.setStationId(dr.getStationId());
		record.setStep("1");
		//单体电压
		int batteryNum = 48/Integer.valueOf(record.getBatteryVoltage());
		//根据放电方式计算检测轮次
		String dischargeType = record.getDischargeType();
		//获取测试轮次
		int turn = getTurnTime(dischargeType);
		//蓄电池集合
		@SuppressWarnings("unchecked")
		List<Battery> batteryList = (List<Battery>)session.getAttribute(SessionConstants.SDEVICESLIST);
		//检测记录列表
		List<BatteryCheckRecord> checkRecordList = new ArrayList<BatteryCheckRecord>();
		DischargeRecordBO drBo = new DischargeRecordBO();
		try {
			boolean flag;
			try {
				BeanCopier.create(DischargeRecord.class, DischargeRecordBO.class, false).copy(record, drBo, null);
				flag = FirstQInterfaces.getIDischargeRecordService().updateDischargeRecord(drBo);
			} catch (Exception e) {
				LogUtil.error("更新放电记录失败！");
				return "commons/error";
			}
			if(!flag){
				return "commons/error";
			}
			
			//先删除后保存
			FirstQInterfaces.getIBatteryCheckRecordService().deleteByBatteryCheckRecordId(dr.getRecordId());
			
			for(int turnIndex = 1;turnIndex<=turn;turnIndex++){
				for(Battery battery : batteryList){
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.MINUTE, 30*(turnIndex-1));
					BatteryCheckRecord checkRecord = new BatteryCheckRecord();
					checkRecord.setRecordId(getUUID());
					checkRecord.setCheckTime(turnIndex);
					checkRecord.setDischargeRecordId(dr.getRecordId());
					checkRecord.setBatteryGroupId(battery.getDeviceId());
					checkRecord.setFinishTime(calendar.getTime());
					//保存一个蓄电池的单体测试
					BatteryCheckRecordBO bcrBO = new BatteryCheckRecordBO();
					BeanCopier.create(BatteryCheckRecord.class, BatteryCheckRecordBO.class, false).copy(checkRecord, bcrBO, null);
					FirstQInterfaces.getIBatteryCheckRecordService().saveBatteryCheckRecord(bcrBO);
					List<BatteryCheckRecordDetail> checkRecordDetailList = new ArrayList<BatteryCheckRecordDetail>();
					for(int batteryNumIndex = 1;batteryNumIndex<=batteryNum;batteryNumIndex++){
						BatteryCheckRecordDetail detail = new BatteryCheckRecordDetail();
						String objectKey = "batteryId="+checkRecord.getBatteryGroupId()+"/turnNum="+checkRecord.getCheckTime()+"/checkNum="+batteryNumIndex;
						detail.setRecordId(getUUID());
						detail.setBatteryCheckRecordId(checkRecord.getRecordId());
						detail.setSequence(batteryNumIndex);
						detail.setObjectKey(objectKey);
						checkRecordDetailList.add(detail);
						//保存一个单体测试的详细记录
						BatteryCheckRecordDetailBO detailBO = new BatteryCheckRecordDetailBO();
						BeanCopier.create(BatteryCheckRecordDetail.class, BatteryCheckRecordDetailBO.class, false).copy(detail, detailBO, null);
						FirstQInterfaces.getIBatteryCheckRecordDetailService().saveBatteryCheckRecordDetail(detailBO);
					}
					checkRecord.setDetailList(checkRecordDetailList);
					checkRecordList.add(checkRecord);
				}
			}
			
			dr.setCheckRecordList(checkRecordList);
		} catch (Exception e) {
			LogUtil.error(e);
			return "commons/error";
		}
		List<String> turntimeList = new ArrayList<String>();
		for(int i=0;i<turn;i++){
			turntimeList.add((i+1)+"");
		}
		Map<Integer,List<BatteryCheckRecord>> map = new LinkedHashMap<Integer,List<BatteryCheckRecord>>();
		for(BatteryCheckRecord checkRecord : dr.getCheckRecordList()){
			List<BatteryCheckRecord> list = map.get(checkRecord.getCheckTime());
			if(list == null){
				list = new ArrayList<BatteryCheckRecord>();
				map.put(checkRecord.getCheckTime(), list);
			}
			list.add(checkRecord);
		}
		model.addAttribute("turntimeList", turntimeList);
		model.addAttribute("checkRecordMap", map);
		//保存开始电压照片
		String imgs = request.getParameter("startVoltageImgs");
		saveWaitUploadPhoto(imgs,station.getStationId(),dr.getRecordId(),"startVoltage",(UserInfo)session.getAttribute(SessionConstants.SUSER));
		return "discharge/singleBatteryCheck";
	}
	
	// 提交检测第二步
	@RequestMapping(value = "/saveBatteryCheckStepTwo", method = RequestMethod.GET)
	public String saveBatteryCheckStepTwo(DischargeRecord record,HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		setWechatConfig(request,model);
		DischargeRecord dr = (DischargeRecord)session.getAttribute(SessionConstants.DISCHARGE_RECORD);
		record.setRecordId(dr.getRecordId());
		record.setStationId(dr.getStationId());
		record.setStep("2");
		try {
			DischargeRecordBO drBO = new DischargeRecordBO();
			BeanCopier.create(DischargeRecord.class, DischargeRecordBO.class, false).copy(record, drBO, null);
			FirstQInterfaces.getIDischargeRecordService().updateDischargeRecord(drBO);
		} catch (Exception e) {
			LogUtil.error(e);
			return "commons/error";
		}
		return "discharge/finishDischarge";
	}
	
	// 提交检测第三步
	@RequestMapping(value = "/saveBatteryCheckStepThree", method = RequestMethod.POST)
	public String saveBatteryCheckStepThree(DischargeRecord record,HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		DischargeRecord dr = (DischargeRecord)session.getAttribute(SessionConstants.DISCHARGE_RECORD);
		record.setRecordId(dr.getRecordId());
		record.setStationId(dr.getStationId());
		record.setStep("3");
		record.setFinishTime(new Date());
		record.setStatus(WebContentConstants.STATUS_COMPLETE);
		try {
			DischargeRecordBO drBO = new DischargeRecordBO();
			BeanCopier.create(DischargeRecord.class, DischargeRecordBO.class, false).copy(record, drBO, null);
			FirstQInterfaces.getIDischargeRecordService().updateDischargeRecord(drBO);
		} catch (Exception e) {
			LogUtil.error("更新记录状态发生错误",e);
			return "commons/error";
		}
		//保存开始电压照片
		String imgs = request.getParameter("endVoltageImgs");
		saveWaitUploadPhoto(imgs,station.getStationId(),dr.getRecordId(),"endVoltage",(UserInfo)session.getAttribute(SessionConstants.SUSER));
		return "redirect:/discharge/readyStart";
	}
	
	// 保存单体电压测试
	@RequestMapping(value = "/saveBatteryCheckRecord", method = RequestMethod.GET)
	public String saveBatteryCheckRecord(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		String groupId = request.getParameter("groupId");
		String turnNum = request.getParameter("turnNum");
		String checkNum = request.getParameter("checkNum");
		String voltage = request.getParameter("voltage");
		if(!StringUtils.hasText(voltage)){
			voltage = "0";
		}
		String objectKey = "batteryId="+groupId+"/turnNum="+turnNum+"/checkNum="+checkNum;
		DischargeRecord dr = (DischargeRecord)session.getAttribute(SessionConstants.DISCHARGE_RECORD);
		BatteryCheckRecordDetail updateDetail = null;
		for(BatteryCheckRecord checkRecord : dr.getCheckRecordList()){
			for(BatteryCheckRecordDetail detail : checkRecord.getDetailList()){
				if(detail.getObjectKey().equals(objectKey)){
					updateDetail = detail;
					updateDetail.setRecordValue(Double.valueOf(voltage));
					break;
				}
			}
		}
		try {
			BatteryCheckRecordDetailBO updateDetailBO = new BatteryCheckRecordDetailBO();
			BeanCopier.create(BatteryCheckRecordDetail.class, BatteryCheckRecordDetailBO.class, false).copy(updateDetail, updateDetailBO, null);
			boolean flag = FirstQInterfaces.getIBatteryCheckRecordDetailService().updateBatteryCheckRecordDetail(updateDetailBO);
			if(flag){
				model.addAttribute("status", "0");
			}
		} catch (Exception e) {
			LogUtil.error(e);
			model.addAttribute("status", "1");
		}
		return "";
	}
	
	// 保存端电压
	@RequestMapping(value = "/saveTerminalVoltage", method = RequestMethod.GET)
	public String saveTerminalVoltage(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		String groupId = request.getParameter("groupId");
		String turnNum = request.getParameter("turnNum");
		String voltage = request.getParameter("voltage");
		if(!StringUtils.hasText(voltage)){
			voltage = "0";
		}
		DischargeRecord dr = (DischargeRecord)session.getAttribute(SessionConstants.DISCHARGE_RECORD);
		BatteryCheckRecord updateCheckRecord = null;
		for(BatteryCheckRecord checkRecord : dr.getCheckRecordList()){
			if(checkRecord.getBatteryGroupId().equals(groupId)
					&& turnNum.equals(checkRecord.getCheckTime()+"")){
				checkRecord.setTerminalVoltage(voltage);
				updateCheckRecord = checkRecord;
				break;
			}
		}
		try {
			BatteryCheckRecordBO checkRecordBO = new BatteryCheckRecordBO();
			BeanCopier.create(BatteryCheckRecord.class, BatteryCheckRecordBO.class, false).copy(updateCheckRecord, checkRecordBO, null);
			boolean flag = FirstQInterfaces.getIBatteryCheckRecordService().updateBatteryCheckRecord(checkRecordBO);
			if(flag){
				model.addAttribute("status", "0");
			}
		} catch (Exception e) {
			LogUtil.error(e);
			model.addAttribute("status", "1");
		}
		return "";
	}
	// 保存录音
	@ResponseBody
	@RequestMapping(value = "/saveSound", method = RequestMethod.GET)
	public String saveSound(SoundInfo soundInfo,HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		String relatedId = request.getParameter("relatedId");
		String recordIds = request.getParameter("recordIds");
		String soundType = request.getParameter("soundType");
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		StationInfo stationInfo = (StationInfo) session.getAttribute(SessionConstants.STATION);
		if(!recordIds.equals("")&&!recordIds.equals(null)){
			String[] localIdz = recordIds.split(",");
			if(!localIdz[0].equals("")){
				soundInfo.setStationId(stationInfo.getStationId());
				if(relatedId!=""&&relatedId!=null){
					soundInfo.setRelatedId(relatedId);
				}
				soundInfo.setSoundType(soundType);
				String voiceIds = "";
				try {
					for(int i=0;i<localIdz.length;i++){
						String isoundId = getUUID();
						soundInfo.setSoundId(getUUID());
						soundInfo.setFileName(getUUID());
						soundInfo.setMediaId(localIdz[i]);
						soundInfo.setCreateTime(new Date());
						soundInfo.setCreateUser(user.getUserName());
						soundInfo.setCreateUserId(user.getUserId());
						soundInfo.setDurationTime(0);
						soundInfo.setFileLocation("");
						soundInfo.setStatus(WebContentConstants.STATUS_WAIT_UPLOAD);
						SoundBO soundBO = new SoundBO();
						BeanCopier.create(SoundInfo.class, SoundBO.class, false).copy(soundInfo, soundBO, null);
						boolean b = FirstQInterfaces.getISoundService().saveSound(soundBO);
						if(b){
							voiceIds += isoundId;
							voiceIds += ",";
						}
					}
					return "{'status':true,'voiceIds':'"+voiceIds.substring(0, voiceIds.length()-1)+"'}";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "";
	}
	// 删除录音
	@ResponseBody
	@RequestMapping(value = "/deleteSound", method = RequestMethod.GET)
	public String deleteSound(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		String voiceIds = request.getParameter("voiceIds");
		if(!voiceIds.equals("")&&!voiceIds.equals(null)){
			String[] voiceIdz = voiceIds.split(",");
			if(!voiceIdz[0].equals("")){
				for(int i=0;i<voiceIdz.length;i++){
					try {
						boolean b = FirstQInterfaces.getISoundService().deleteSound(voiceIdz[i]);
						if(!b){
							return "false";
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}
		return "true";
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
	//提交照片
	private boolean saveWaitUploadPhoto(String imgs,String stationId,String relateId,String deviceType,UserInfo user){
		if(StringUtils.hasText(imgs)){
			String[] deviceImgsArry = imgs.split(",");
			PhotoInfo pi = new PhotoInfo();
			int sqe = 1;
			for(String imgId : deviceImgsArry){
				pi.setPhotoId(getUUID());
				pi.setPhotoName(getUUID());
				pi.setLocalId(imgId);
				pi.setPhotoType(deviceType);
				pi.setSqe(sqe);
				pi.setSubmitTime(new Date());
				pi.setStationId(stationId);
				pi.setRelateId(relateId);
				pi.setCreateTime(new Date());
				pi.setSubmitTime(new Date());
				pi.setCreateUser(user.getUserName());
				pi.setCreateUserId(user.getUserId());
				pi.setLatitude(user.getLa());
				pi.setLongitude(user.getLo());
				pi.setFileLocation("");
				pi.setThumbLocation("");
				pi.setRemark("");
				pi.setStatus(WebContentConstants.STATUS_WAIT_UPLOAD);
				pi.setFileSize(0);
				PhotoBO photoBO = new PhotoBO();
				BeanCopier.create(PhotoInfo.class, PhotoBO.class, false).copy(pi, photoBO, null);
				sqe++;
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
	
}
