package com.mcu32.firstq.wechat.action.generatePower;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
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

import com.mcu32.firstq.common.bean.bo.GeneratePowerRecordBO;
import com.mcu32.firstq.common.bean.bo.PhotoBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.GeneratePowerRecord;
import com.mcu32.firstq.wechat.bean.PhotoInfo;
import com.mcu32.firstq.wechat.bean.StationInfo;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.interceptor.AccessRequired;
import com.mcu32.firstq.wechat.tools.TemplateMessages;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.SessionConstants;
import com.mcu32.firstq.wechat.util.ShortUrlUtil;
import com.mcu32.firstq.wechat.util.ToolUtil;
import com.mcu32.firstq.wechat.util.WebContentConstants;
import com.mcu32.firstq.wechat.util.date.DateUtil;

@Controller
@RequestMapping(value = "/generatePowerSingleBatteryCheck")
public class GeneratePowerSingleBatteryController extends BaseController{
	
	
	//
	@AccessRequired(needLogin=true)
	@RequestMapping(value = "/saveBatteryCheckStepOne", method = RequestMethod.POST)
	public String saveBatteryCheckStepOne(GeneratePowerRecord record,HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		int gpTimeRestrict= "0".equals(record.getGeneratePowerType())?30:60;
		int difSeconds=DateUtil.getInterval(new Date(),record.getPowerCutTime(),1000*60,false);
		if(difSeconds<0 || difSeconds>gpTimeRestrict){
			model.addAttribute("succ", false);
			model.addAttribute("msg", "发电开始时间跟当前时间相差应小于"+gpTimeRestrict+"分钟");
			return "";
		}
		UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		
		//发电测试记录
		if("0".equals(record.getStep())){
			
			record.setRecordId(getUUID());
			record.setStationId(station.getStationId());
			record.setLastOperator(ui.getUserName());
			record.setLastOperatorId(ui.getUserId());
			record.setOperatTime(new Date());
			
			GeneratePowerRecordBO gprBO1 = new GeneratePowerRecordBO();
			
			try {
				//添加发电人联系方式及归属公司 add by chenyan
				gprBO1.setPhone(ui.getPhoneNo());
				gprBO1.setBelongDept(ui.getDeptId());
				
				String area=("市辖区".equals(station.getCityId()))?station.getProvinceId():station.getCityId();
				String msg="";
				String regionalManager=station.getRegionalManager();
				if(StringUtils.isEmpty(regionalManager)){
					regionalManager="";
				}
				String shortUrl=FirstqTool.getToPageURL("/main/toPage?toPageUrl=station/getStationDetail?stationId="+station.getStationId());
				//String record.getsta
				String starTime=DateUtil.DateToString(record.getPowerCutTime(),"yyyy-MM-dd HH:mm");
				String operatTime=DateUtil.DateToString(record.getOperatTime(),"yyyy-MM-dd HH:mm");
				String stationPowercutTime=DateUtil.DateToString(record.getStationPowercutTime(),"yyyy-MM-dd HH:mm");
				Map<String, String> dataMap=new HashMap<String,String>();
				if("2".equals(record.getGeneratePowerType())){
					record.setStep("3");
					record.setStatus(WebContentConstants.STATUS_COMPLETE);
					record.setLastTime(record.getOperatTime());
					record.setFinishTime(record.getOperatTime());
					msg="[基站未发电申报通知]\n基站："+station.getStationName()+"\n区域："+station.getProvinceId()+"/"+station.getCityId()+"/"+station.getCountyId()+"\n区域经理："+regionalManager+"\n恢复时间："+starTime+"\n填报时间："+operatTime+"\n发电人员："+ui.getUserName()+"\n点击"+shortUrl+"查看基站详情";
					dataMap.put("url", shortUrl);
					dataMap.put("first", "[基站未发申报通知]");
					dataMap.put("keyword1", starTime);
					dataMap.put("keyword2", "未发电申报\\n基站："+station.getStationName()+"\\n区域："+station.getProvinceId()+"/"+station.getCityId()+"/"+station.getCountyId()+"\\n区域经理："+regionalManager+"\\n填报时间："+operatTime+"\\n申报人员："+ui.getUserName());
					dataMap.put("remark", "");
				}else{
					record.setStep("1");
					record.setStatus(WebContentConstants.STATUS_PROGRESS);
					
					String gpType= "1".equals(record.getGeneratePowerType())?"发电补报":"正常发电";
					msg="[油机"+gpType+"开始通知]\n基站："+station.getStationName()+"\n区域："+station.getProvinceId()+"/"+station.getCityId()+"/"+station.getCountyId()+"\n区域经理："+regionalManager+"\n停电时间："+stationPowercutTime+"\n起始时间："+starTime+"\n填报时间："+operatTime+"\n起始电压："+(StringUtils.isEmpty(record.getPowercutVoltage())?"":record.getPowercutVoltage()+"V")+"\n发电人员："+ui.getUserName()+"\n点击"+shortUrl+"查看基站详情";
					dataMap.put("url", shortUrl);
					dataMap.put("first", "[油机"+gpType+"开始通知]");
					dataMap.put("keyword1", starTime);
					dataMap.put("keyword2", gpType+"\\n基站："+station.getStationName()+"\\n区域："+station.getProvinceId()+"/"+station.getCityId()+"/"+station.getCountyId()+"\\n区域经理："+regionalManager+"\\n停电时间："+stationPowercutTime+"\\n起始时间："+starTime+"\\n填报时间："+operatTime+"\\n起始电压："+(StringUtils.isEmpty(record.getPowercutVoltage())?"":record.getPowercutVoltage()+"V")+"\\n发电人员："+ui.getUserName());
					dataMap.put("remark", "");
				}
				BeanCopier.create(GeneratePowerRecord.class, GeneratePowerRecordBO.class, false).copy(record, gprBO1, null);
				boolean flag =  FirstQInterfaces.getIGeneratePowerRecordService().saveGeneratePowerRecord(gprBO1);
				if (flag) {
					FirstqTool.sendGPToWechatGroup(msg,area,station.getCountyId());//调机器人发消息
					TemplateMessages.send(ui.getWeChatService(), dataMap);//发送模版消息
				}
			} catch (Exception e) {
				LogUtil.error("保存基站发电记录发生错误",e);
				return "commons/error";
			}
			
		}else{
			GeneratePowerRecordBO gprBO1 = new GeneratePowerRecordBO();
			BeanCopier.create(GeneratePowerRecord.class, GeneratePowerRecordBO.class, false).copy(record, gprBO1, null);
			try {
				FirstQInterfaces.getIGeneratePowerRecordService().updateGeneratePowerRecord(gprBO1);
			} catch (FirstQException e) {
				e.printStackTrace();
			}
		}
		
		//保存开始电压照片
		String startTimeImg = request.getParameter("startTimeImg");
		if(StringUtils.hasText(startTimeImg)){
			saveWaitUploadPhoto(startTimeImg,station.getStationId(),record.getRecordId(),"cutPowerStartTime",ui);
		}
		
		model.addAttribute("succ", true);
		model.addAttribute("msg", "保存成功");
		return "";
	}
	
	//
	@AccessRequired(needLogin=true)
	@RequestMapping(value = "/saveBatteryCheckFinish")
	public String saveBatteryCheckFinish(GeneratePowerRecord record,HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		setWechatConfig(request,model);
		UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		GeneratePowerRecord gpr = (GeneratePowerRecord) session.getAttribute(SessionConstants.GENERATEPOWER_RECORD);
		
		DecimalFormat df = new DecimalFormat("#0.00");
		Date finishT = record.getFinishTime();
		if(finishT == null){
			finishT=(null==record.getPowerCutTime())?new Date():record.getPowerCutTime();
		}
		record.setRecordId(gpr.getRecordId());
		record.setStationId(gpr.getStationId());
		record.setStep("3");
		record.setStatus(WebContentConstants.STATUS_COMPLETE);
		record.setLastTime(new Date());
		if(DateUtil.getIntervalMilliseconds(finishT,record.getLastTime())>0){
			record.setRemark("发电结束时间大于操作时间，自动更新为操作时间");
			record.setFinishTime(record.getLastTime());
		}
		try {
			GeneratePowerRecordBO gprBO = new GeneratePowerRecordBO();
			BeanCopier.create(GeneratePowerRecord.class, GeneratePowerRecordBO.class, false).copy(record, gprBO, null);
			boolean updateSucc = FirstQInterfaces.getIGeneratePowerRecordService().updateGeneratePowerRecord(gprBO);
			if(updateSucc){
				//保存开始电压照片
				String startTimeImg = request.getParameter("startTimeImg");
				if(StringUtils.hasText(startTimeImg)){
					saveWaitUploadPhoto(startTimeImg,station.getStationId(),gpr.getRecordId(),"cutPowerStartTime",ui);
				}
				
				double timeSpan = (finishT.getTime() - record.getPowerCutTime().getTime())/(double)1000/(double)60/(double)60;
				
				//TODO 机器人发结束消息 
				Date endDate=null==record.getFinishTime()?new Date():record.getFinishTime();
				String finishTime=DateUtil.DateToString(record.getFinishTime(),"yyyy-MM-dd HH:mm");
				String lastTime=DateUtil.DateToString(record.getLastTime(),"yyyy-MM-dd HH:mm");
				
				String area=("市辖区".equals(station.getCityId()))?station.getProvinceId():station.getCityId();
				String gpType= "1".equals(gpr.getGeneratePowerType())?"发电补报":"正常发电";
				String regionalManager=station.getRegionalManager();
				if(StringUtils.isEmpty(regionalManager)){
					regionalManager="";
				}
				String shortUrl=FirstqTool.getToPageURL("/main/toPage?toPageUrl=station/getStationDetail?stationId="+station.getStationId());
				FirstqTool.sendGPToWechatGroup("[油机"+gpType+"结束通知]\n基站："+station.getStationName()+"\n区域："+station.getProvinceId()+"/"+station.getCityId()+"/"+station.getCountyId()+"\n区域经理："+regionalManager+"\n结束时间："+finishTime+"\n填报时间："+lastTime+"\n总计时长："+df.format(timeSpan)+"小时\n发电人员："+ui.getUserName()+"\n点击"+shortUrl+"查看基站详情",area,station.getCountyId());
				
				Map<String, String> dataMap=new HashMap<String,String>();
				dataMap.put("url", shortUrl);
				dataMap.put("first", "[油机"+gpType+"结束通知]");
				dataMap.put("keyword1", finishTime);
				dataMap.put("keyword2", "\\n基站："+station.getStationName()+"\\n区域："+station.getProvinceId()+"/"+station.getCityId()+"/"+station.getCountyId()+"\\n区域经理："+regionalManager+"\\n结束时间："+finishTime+"\\n填报时间："+lastTime+"\\n总计时长："+df.format(timeSpan)+"小时\\n发电人员："+ui.getUserName());
				dataMap.put("remark", "");
				TemplateMessages.send(ui.getWeChatService(), dataMap);//发送模版消息
				
				
				//保存结束电压照片
				String endVoltageImg = request.getParameter("endVoltageImg");
				if(StringUtils.hasText(endVoltageImg)){
					saveWaitUploadPhoto(endVoltageImg,station.getStationId(),gpr.getRecordId(),"generateEndVoltage",ui);
				}
				model.addAttribute("succ", true);
				model.addAttribute("msg", "保存成功");
			}else{
				return "commons/error";
			}
		
		} catch (Exception e) {
			LogUtil.error(e);
			return "commons/error";
		}
		
		
		return "";
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
