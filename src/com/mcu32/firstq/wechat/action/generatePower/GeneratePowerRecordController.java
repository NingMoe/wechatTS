package com.mcu32.firstq.wechat.action.generatePower;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcu32.firstq.common.bean.bo.BatteryCheckRecordBO;
import com.mcu32.firstq.common.bean.bo.BatteryCheckRecordDetailBO;
import com.mcu32.firstq.common.bean.bo.GeneratePowerRecordBO;
import com.mcu32.firstq.common.bean.bo.GeneratePowerRecordReportBO;
import com.mcu32.firstq.common.bean.bo.PhotoBO;
import com.mcu32.firstq.common.bean.bo.SoundBO;
import com.mcu32.firstq.common.bean.bo.UserBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.BatteryCheckRecord;
import com.mcu32.firstq.wechat.bean.BatteryCheckRecordDetail;
import com.mcu32.firstq.wechat.bean.GeneratePowerRecord;
import com.mcu32.firstq.wechat.bean.PhotoInfo;
import com.mcu32.firstq.wechat.bean.SoundInfo;
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
/**
 * 添加/修改设备信息
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/generatePower")
public class GeneratePowerRecordController extends BaseController {
	
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/electricIndex",method=RequestMethod.GET)
	public String electricIndex(HttpServletRequest request, HttpServletResponse response, HttpSession session,ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		if(station ==  null){
			return "commons/error";
		}
		GeneratePowerRecord gpr;
		try {
			gpr = getGeneratePowerRecord(station.getStationId(), (UserInfo)session.getAttribute(SessionConstants.SUSER));
		} catch (Exception e) {
			LogUtil.error("查询发电记录发生错误",e);
			return "commons/error";
		}
		//是否继续操作标识
		if(((gpr != null && StringUtils.isNotEmpty(gpr.getRecordId()))) ){
			session.setAttribute(SessionConstants.GENERATEPOWER_RECORD, gpr);
			boolean flag2 = false;
			if(gpr.getStatus().equals(WebContentConstants.STATUS_COMPLETE)|| gpr.getStatus().equals(WebContentConstants.STATUS_SUSPEND)){
				flag2 = true;
			}
			if(flag2){
				model.addAttribute("status", "todo");
			}else{
				model.addAttribute("status", "continue");
			}
			DecimalFormat df = new DecimalFormat("#0.00");
			Date pct=gpr.getPowerCutTime();
			if(null == pct)
				pct=new Date();
			Date finishT = gpr.getFinishTime();
			if(finishT == null)
				finishT = pct;
			//double timeSpan = (finishT.getTime() - gpr.getOperatTime().getTime())/(double)1000/(double)60/(double)60;
			double timeSpan = (finishT.getTime() - pct.getTime())/(double)1000/(double)60/(double)60;
			
			model.addAttribute("timeSpan", df.format(timeSpan));
			model.addAttribute("finishT", finishT);
			model.addAttribute("history", "true");
		}else{
			model.addAttribute("status", "todo");
			model.addAttribute("history", "false");
		}
		return "generatePower/startGeneratePower";
	}
	
	@AccessRequired(needLogin=true)
	@RequestMapping(value = "/toStart", method = RequestMethod.GET)
	public String toStart(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		model.addAttribute("step", "0");
		session.setAttribute(SessionConstants.GENERATEPOWER_RECORD, null);
		return "generatePower/generatePowerRecord";
	}
	
	//继续放电测试任务
	@AccessRequired(needLogin=true)
	@RequestMapping(value = "/toContinue", method = RequestMethod.GET)
	public String toContinue(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		model.addAttribute("step", "1");
		//基站巡检记录
		GeneratePowerRecord gpr;
		try {
			gpr = getGeneratePowerRecord(station.getStationId(), (UserInfo)session.getAttribute(SessionConstants.SUSER));
			session.setAttribute(SessionConstants.GENERATEPOWER_RECORD, gpr);
		} catch (Exception e) {
			LogUtil.error("查询发电记录发生错误",e);
			return "commons/error";
		}
		return "generatePower/generatePowerRecord";
	}
	
	//结束放电测试任务
	@AccessRequired(needLogin=true)
	@RequestMapping(value = "/toFinishJob", method = RequestMethod.GET)
	public String toFinishJob(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		//基站巡检记录
		GeneratePowerRecord gpr;
		try {
			gpr = getGeneratePowerRecord(station.getStationId(), (UserInfo)session.getAttribute(SessionConstants.SUSER));
			session.setAttribute(SessionConstants.GENERATEPOWER_RECORD, gpr);
			model.addAttribute("isFinishGP", "true");
		} catch (Exception e) {
			LogUtil.error("查询发电记录发生错误",e);
			return "commons/error";
		}
		return "generatePower/generatePowerRecord";
	}
	
	// 更新任务状态
	@RequestMapping(value = "/updateRecordStatus", method = RequestMethod.GET)
	public String updateRecordStatus(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		String status = request.getParameter("status");
		UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		StationInfo station = (StationInfo)session.getAttribute(SessionConstants.STATION);
		if(station ==  null){
			return "commons/error";
		}
		
		GeneratePowerRecord gpr = (GeneratePowerRecord) session.getAttribute(SessionConstants.GENERATEPOWER_RECORD);
		gpr.setFinishTime(new Date());
		if("0".equals(status)){
			gpr.setStatus(WebContentConstants.STATUS_COMPLETE);
		}else if("1".equals(status)){
			gpr.setStatus(WebContentConstants.STATUS_SUSPEND);
		}
		gpr.setLastTime(gpr.getFinishTime());
		GeneratePowerRecordBO gprBO = new GeneratePowerRecordBO();
		BeanCopier.create(GeneratePowerRecord.class, GeneratePowerRecordBO.class, false).copy(gpr, gprBO, null);
		try {

			FirstQInterfaces.getIGeneratePowerRecordService().updateGeneratePowerRecord(gprBO);
			//FirstqTool.sendMessageToWechatGroup("");
			DecimalFormat df = new DecimalFormat("#0.00");
			Date finishT = new Date();
			double timeSpan = (finishT.getTime() - gpr.getPowerCutTime().getTime())/(double)1000/(double)60/(double)60;
			String finishTime=DateUtil.DateToString(finishT,"yyyy-MM-dd HH:mm");
			String regionalManager=station.getRegionalManager();
			if(StringUtils.isEmpty(regionalManager)){
				regionalManager="";
			}
			String shortUrl=FirstqTool.getToPageURL("/main/toPage?toPageUrl=station/getStationDetail?stationId="+station.getStationId());
			//TODO 异常中断发送机器人消息
			String gpType= "1".equals(gpr.getGeneratePowerType())?"发电补报":"正常发电";
			String area=("市辖区".equals(station.getCityId()))?station.getProvinceId():station.getCityId();
			FirstqTool.sendGPToWechatGroup("[油机"+gpType+"紧急终止通知]\n基站："+station.getStationName()+"\n区域："+station.getProvinceId()+"/"+station.getCityId()+"/"+station.getCountyId()+"\n区域经理："+regionalManager+"\n终止时间："+finishTime+"\n总计时长："+df.format(timeSpan)+"小时\n发电人员："+ui.getUserName()+"\n点击"+shortUrl+"查看基站详情",area,station.getCountyId());
			
			Map<String, String> dataMap=new HashMap<String,String>();
			dataMap.put("url", shortUrl);
			dataMap.put("first", "[油机"+gpType+"紧急终止通知]");
			dataMap.put("keyword1", finishTime);
			dataMap.put("keyword2", gpType+"紧急终止\\n基站："+station.getStationName()+"\\n区域："+station.getProvinceId()+"/"+station.getCityId()+"/"+station.getCountyId()+"\\n区域经理："+regionalManager+"\\n终止时间："+finishTime+"\\n总计时长："+df.format(timeSpan)+"小时\\n发电人员："+ui.getUserName());
			dataMap.put("remark", "");
			TemplateMessages.send(ui.getWeChatService(), dataMap);//发送模版消息
		} catch (Exception e) {
			LogUtil.error("更新记录状态发生错误",e);
			return "commons/error";
		}
		return "redirect:/generatePower/electricIndex";
	}
	
	/**
	 * 未发申报
	 */
	@RequestMapping(value = "/toSwitchIn", method = RequestMethod.GET)
	public String toSwitchIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		model.addAttribute("step", "0");
		return "generatePower/switchIn";
	}
	
	// 检查全部是否完成
	@RequestMapping(value = "/checkStationGenerate", method = RequestMethod.GET)
	public String checkStationGenerate(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		String stationId = request.getParameter("stationId");
		try {
			GeneratePowerRecordBO generatePowerRecordBO = FirstQInterfaces.getIGeneratePowerRecordService().getGeneratePowerRecordBOByStationId(stationId);
			if (generatePowerRecordBO != null ) {
				UserBO user = FirstQInterfaces.getIUserService().getUserInfoByUserId(generatePowerRecordBO.getLastOperatorId());
				model.addAttribute("user", user);
			}
			model.addAttribute("generatePowerRecordBO", generatePowerRecordBO);
		} catch (FirstQException e) {
			LogUtil.error(e.getMessage());
		}
		return "";
	}
	@RequestMapping(value = "/startGeneratePower", method = RequestMethod.GET)
	public String toStartGeneratePower(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String region=request.getParameter("region");
		String curTime=request.getParameter("curTime");
		Map<String,String> map=new HashMap<String,String>();
		map.put("region", region);
		map.put("curTime", curTime);
		try {
			List<GeneratePowerRecordReportBO> recordList=FirstQInterfaces.getIGeneratePowerRecordService().curGeneratePowerRecordList(map);
			int count=FirstQInterfaces.getIGeneratePowerRecordService().curGeneratePowerRecordListCount(map);
			model.addAttribute("generatePowerList", recordList);
			model.addAttribute("count", count);
			model.addAttribute("curTime", curTime);
		} catch (FirstQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "generatePower/curGeneratePower";
	}
	@RequestMapping(value = "/startGeneratePowerCount", method = RequestMethod.GET)
	public String toStartGeneratePowerCount(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String region=request.getParameter("region");
		String curTime=request.getParameter("curTime");
		Map<String,String> map=new HashMap<String,String>();
		map.put("region", region);
		map.put("curTime", curTime);
		try {
			int count=FirstQInterfaces.getIGeneratePowerRecordService().curGeneratePowerRecordListCount(map);
			model.addAttribute("count", count);
		} catch (FirstQException e) {
			LogUtil.error("请求发电数量异常",e);
		}
		
		return "";
	}
	public  GeneratePowerRecord getGeneratePowerRecord(String stationId,UserInfo user) throws Exception{
		GeneratePowerRecord gpr = new GeneratePowerRecord();
		GeneratePowerRecordBO gprBO = FirstQInterfaces.getIGeneratePowerRecordService().getGeneratePowerRecord(stationId, user.getUserId());
		if(gprBO != null && StringUtils.isNotEmpty(gprBO.getRecordId())){
			BeanCopier.create(GeneratePowerRecordBO.class, GeneratePowerRecord.class, false).copy(gprBO, gpr, null);
			List<BatteryCheckRecordBO> checkRecordBOList = gprBO.getCheckRecordList();
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
				gpr.setCheckRecordList(checkRecordList);
			}
		}
		if(gpr != null && gpr.getCheckRecordList() != null){
			for(BatteryCheckRecord checkRecord : gpr.getCheckRecordList()){
				if(checkRecord.getDetailList() != null){
					for(BatteryCheckRecordDetail detail : checkRecord.getDetailList() ){
						String objectKey = "batteryId="+checkRecord.getBatteryGroupId()+"/turnNum="+checkRecord.getCheckTime()+"/checkNum="+detail.getSequence();
						detail.setObjectKey(objectKey);
					}
				}
			}
			Map<String, List<PhotoBO>> mapBO = FirstQInterfaces.getIPhotoService().getPhotoByRelateIds(new String[]{gpr.getRecordId()});
			if (mapBO != null) {
				List<PhotoBO> photoBOList = mapBO.get(gpr.getRecordId());
				if(photoBOList != null){
					List<PhotoInfo> cutPowerStartTimeList = new ArrayList<PhotoInfo>();
					List<PhotoInfo> startVoltagePiList = new ArrayList<PhotoInfo>();
					List<PhotoInfo> endVoltagePiList = new ArrayList<PhotoInfo>();
					for(PhotoBO pi : photoBOList){
						PhotoInfo photoInfo = new PhotoInfo();
						BeanCopier.create(PhotoBO.class, PhotoInfo.class, false).copy(pi, photoInfo, null);
						if(pi.getPhotoType().equals("generateStartVoltage")){
							startVoltagePiList.add(photoInfo);
						}else if(pi.getPhotoType().equals("generateEndVoltage")){
							endVoltagePiList.add(photoInfo);
						}else if ("cutPowerStartTime".equals(pi.getPhotoType())) {
							cutPowerStartTimeList.add(photoInfo);
						}
					}
					FirstqTool.convertPhotoPath(cutPowerStartTimeList,user);
					FirstqTool.convertPhotoPath(startVoltagePiList,user);
					FirstqTool.convertPhotoPath(endVoltagePiList,user);
					gpr.setPowerCutPhotos(cutPowerStartTimeList);
					gpr.setStartVoltagePhotos(startVoltagePiList);
					gpr.setEndVoltagePhotos(endVoltagePiList);
				}
			}
			List<BatteryCheckRecord> list = gpr.getCheckRecordList();
			String[] idList = null;
			if(list!= null){
				for(int i=0;i<list.size();i++){
					idList = (String[]) ArrayUtils.add(idList, list.get(i).getRecordId());
				}
			}
			Map<String, List<SoundBO>> soundMap = FirstQInterfaces.getISoundService().getSoundByRelatedId(idList);
			if (soundMap != null) {
				for(int i=0;i<gpr.getCheckRecordList().size();i++){
					List<SoundInfo> soundList = new ArrayList<SoundInfo>();
					List<SoundBO> soundBOList = soundMap.get(gpr.getCheckRecordList().get(i).getRecordId());
					if (soundBOList != null) {
						for (SoundBO soundBO : soundBOList) {
							SoundInfo soundInfo = new SoundInfo();
							BeanCopier.create(SoundBO.class, SoundInfo.class, false).copy(soundBO, soundInfo, null);
							soundList.add(soundInfo);
						}
						gpr.getCheckRecordList().get(i).setSoundList(soundList);
					}
				}
			}
		}
		return gpr;
	}
}
