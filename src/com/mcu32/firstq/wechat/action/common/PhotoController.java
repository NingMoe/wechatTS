package com.mcu32.firstq.wechat.action.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gtm.wechat.popular.support.TokenAndTicketManager;
import com.mcu32.firstq.common.bean.bo.PhotoBO;
import com.mcu32.firstq.common.bean.bo.SoundBO;
import com.mcu32.firstq.common.bean.bo.StationHistoryBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.PhotoInfo;
import com.mcu32.firstq.wechat.bean.PhotoType;
import com.mcu32.firstq.wechat.bean.SoundInfo;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.interceptor.AccessRequired;
import com.mcu32.firstq.wechat.service.IStationHistoryService;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.SessionConstants;
import com.mcu32.firstq.wechat.util.ToolUtil;
import com.mcu32.firstq.wechat.util.WebContentConstants;

@Controller
@RequestMapping(value="/acceptPhoto")
public class PhotoController extends BaseController{
	
	@Autowired IStationHistoryService iStationHistoryService;
	
	/**
	 * 添加照片
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public String photoAdd(HttpServletRequest request, ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String resultString="newStation/checkStation";
		return resultString;
	}
	/** 删除照片*/
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String photoDelete(HttpServletRequest request, HttpSession session, ModelMap model){
		UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
		String photoId=request.getParameter("photoId");
		boolean flag=false;
		try {
			PhotoBO photoBO = FirstQInterfaces.getIPhotoService().getPhotoInfoById(photoId);
			if(WebContentConstants.STATION_TOWER.equals(photoBO.getPhotoType()) || WebContentConstants.STATION_ROOM.equals(photoBO.getPhotoType()) || WebContentConstants.DEVICE_SWITCH_POWER.equals(photoBO.getPhotoType()) || WebContentConstants.DEVICE_BATTERY.equals(photoBO.getPhotoType()) || WebContentConstants.DEVICE_AIRCONDITIONING.equals(photoBO.getPhotoType())){
				//设置操作记录的属性
				StationHistoryBO stationHistoryBO = new StationHistoryBO();
				stationHistoryBO.setUserId(user.getUserId());
				stationHistoryBO.setUserName(user.getUserName());
				stationHistoryBO.setOperateTime(new Date());
				stationHistoryBO.setOperateType("delete");
				stationHistoryBO.setOperateObject(photoBO.getPhotoType() + "Photo");
				stationHistoryBO.setStationId(photoBO.getStationId());
				stationHistoryBO.setJobId(user.getJobId());
				iStationHistoryService.saveStationHistory(null, null, stationHistoryBO);
			}
			flag= FirstQInterfaces.getIPhotoService().deletePhotoInfo(photoId);
		} catch (Exception e) {
			LogUtil.error(e);
		}
		
		model.addAttribute("succ", flag);
		return "";
	}
	/**
	 * 查询照片
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/get",method=RequestMethod.GET)
	public String photoGet(HttpServletRequest request, ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String resultString="newStation/checkStation";
		return resultString;
	}
	
	@RequestMapping(value = "/toUploadResources")
	public String toUploadResources(HttpServletRequest request,ModelMap model,HttpSession session){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String token = getTokenBySu(session);
		String openId = getOpenIdBySu(session);
		List<PhotoInfo> photoInfos = new ArrayList<PhotoInfo>();
		List<SoundInfo> firstSoundInfos = new ArrayList<SoundInfo>();
		try {
			List<PhotoBO> photoBOList = FirstQInterfaces.getIPhotoService().getPhotoByUser(token,  WebContentConstants.STATUS_WAIT_UPLOAD);
			if (photoBOList != null) {
				for (PhotoBO photoBO : photoBOList) {
					PhotoInfo photoInfo = new PhotoInfo();
					photoBO.setPhotoType(PhotoType.getName(photoBO.getPhotoType()));
					BeanCopier.create(PhotoBO.class, PhotoInfo.class, false).copy(photoBO, photoInfo, null);;
					photoInfos.add(photoInfo);
				}
			}
			List<SoundBO> soundBOList = FirstQInterfaces.getISoundService().getSound(token, WebContentConstants.STATUS_WAIT_UPLOAD);
			//将relatedId相同的soundInfo的soundId和mediaId存入第一个soundInfo
			
			List<String> idList = new ArrayList<String>();
			if(soundBOList != null) {
				SoundInfo soundWithFirstId = null;
				for(SoundBO soundBO : soundBOList){
					if(soundBO.getSoundType().equals("discharge")){
						soundBO.setSoundType("放电检测");
					}
					if(!idList.contains(soundBO.getRelatedId())){
						soundWithFirstId = new SoundInfo();
		            	idList.add(soundBO.getRelatedId());
		            	BeanCopier.create(SoundBO.class, SoundInfo.class, false).copy(soundBO, soundWithFirstId, null);
		            	firstSoundInfos.add(soundWithFirstId);
		            }else{
		            	soundWithFirstId.setSoundId(soundWithFirstId.getSoundId()+","+soundBO.getSoundId());
		            	soundWithFirstId.setMediaId(soundWithFirstId.getMediaId()+","+soundBO.getMediaId());
		            }
				}
				//验证list信息是否完整,打印true则正确
		        LogUtil.info("compareResult:"+(firstSoundInfos.size() == soundBOList.size()));
			}
		} catch (Exception e) {
			LogUtil.error(e.getMessage(), e);
			return "commons/toUploadResources";
		}
		model.addAttribute("photoInfosJson",JSON.toJSONStringWithDateFormat(photoInfos, "yyyy/MM/dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat));
		model.addAttribute("photoInfos",photoInfos);
		model.addAttribute("openId",openId);
		//model.addAttribute("soundInfos",soundInfos);//待删除
		model.addAttribute("firstSoundInfos",firstSoundInfos);
		return "commons/toUploadResources";
	}
	
	@RequestMapping(value = "/uploadById")
	public String uploadById(HttpServletRequest request,ModelMap model,HttpSession session){
		UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
		String projectUri = session.getServletContext().getRealPath("/");
		String realPath = projectUri.substring(0, projectUri.indexOf("wechat"))+"services/uploadFile";
		String thumbPath = projectUri.substring(0, projectUri.indexOf("wechat"))+"services/uploadFile/thumb";
		String photoId = request.getParameter("photoId"); 
		String mediaId = request.getParameter("mediaId");
		String appid = ToolUtil.getAppConfig("dyxxAPPID");
		String access_token = TokenAndTicketManager.getToken(appid);
		boolean result = false;
		try {
			result = FirstQInterfaces.getIPhotoService().uploadPhoto(photoId, access_token, mediaId, realPath, thumbPath,projectUri+"assets/i/imgIcon.png");
			PhotoBO photoBO = FirstQInterfaces.getIPhotoService().getPhotoInfoById(photoId);
			if(WebContentConstants.STATION_TOWER.equals(photoBO.getPhotoType()) || WebContentConstants.STATION_ROOM.equals(photoBO.getPhotoType()) || WebContentConstants.DEVICE_SWITCH_POWER.equals(photoBO.getPhotoType()) || WebContentConstants.DEVICE_BATTERY.equals(photoBO.getPhotoType()) || WebContentConstants.DEVICE_AIRCONDITIONING.equals(photoBO.getPhotoType())){
				//设置操作记录的属性
				StationHistoryBO stationHistoryBO = new StationHistoryBO();
				stationHistoryBO.setUserId(user.getUserId());
				stationHistoryBO.setUserName(user.getUserName());
				stationHistoryBO.setOperateTime(new Date());
				stationHistoryBO.setOperateType("insert");
				stationHistoryBO.setOperateObject(photoBO.getPhotoType() + "Photo");
				stationHistoryBO.setStationId(photoBO.getStationId());
				stationHistoryBO.setJobId(user.getJobId());
				iStationHistoryService.saveStationHistory(null, null, stationHistoryBO);
			}
		} catch (Exception e) {
			model.addAttribute("status",result);
			return "";
		}
		model.addAttribute("status",result);
		return "";
	}
	
	@RequestMapping(value = "/uploadPhotoByBase64")
	public String uploadPhotoByBase64(HttpServletRequest request,ModelMap model,HttpSession session){
		UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
		String projectUri = session.getServletContext().getRealPath("/");
		String realPath = getFileUploadRealPath();
		String thumbPath = getFileUploadRealPath()+File.separator+"thumb";
		String photoId = request.getParameter("photoId"); 
		String base64data = request.getParameter("mediaId");
		
		boolean result = false;
		try {
			result = FirstQInterfaces.getIPhotoService().uploadPhotoByBase64(photoId, base64data, realPath, thumbPath,projectUri+"assets/i/imgIcon.png");
			PhotoBO photoBO = FirstQInterfaces.getIPhotoService().getPhotoInfoById(photoId);
			if(WebContentConstants.STATION_TOWER.equals(photoBO.getPhotoType()) || WebContentConstants.STATION_ROOM.equals(photoBO.getPhotoType()) || WebContentConstants.DEVICE_SWITCH_POWER.equals(photoBO.getPhotoType()) || WebContentConstants.DEVICE_BATTERY.equals(photoBO.getPhotoType()) || WebContentConstants.DEVICE_AIRCONDITIONING.equals(photoBO.getPhotoType())){
				//设置操作记录的属性
				StationHistoryBO stationHistoryBO = new StationHistoryBO();
				stationHistoryBO.setUserId(user.getUserId());
				stationHistoryBO.setUserName(user.getUserName());
				stationHistoryBO.setOperateTime(new Date());
				stationHistoryBO.setOperateType("insert");
				stationHistoryBO.setOperateObject(photoBO.getPhotoType() + "Photo");
				stationHistoryBO.setStationId(photoBO.getStationId());
				stationHistoryBO.setJobId(user.getJobId());
				iStationHistoryService.saveStationHistory(null, null, stationHistoryBO);
			}
		} catch (Exception e) {
			model.addAttribute("status",result);
			return "";
		}
		model.addAttribute("status",result);
		return "";
	}
	@RequestMapping(value = "/uploadPhotoByBase64RealTime")
	public String uploadPhotoByBase64RealTime(HttpServletRequest request,ModelMap model,HttpSession session){
		UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
		String projectUri = session.getServletContext().getRealPath("/");
		String realPath = getFileUploadRealPath();
		String thumbPath = getFileUploadRealPath()+File.separator+"thumb";
		String photoId = request.getParameter("photoId"); 
		String base64data = request.getParameter("mediaId");
		
		boolean result = false;
		try {
			//result=true;
			result = FirstQInterfaces.getIPhotoService().uploadPhotoByBase64RealTime(photoId, base64data, realPath, thumbPath,projectUri+"assets/i/imgIcon.png");
			PhotoBO photoBO = FirstQInterfaces.getIPhotoService().getPhotoInfoById(photoId);
			if(WebContentConstants.STATION_TOWER.equals(photoBO.getPhotoType()) || WebContentConstants.STATION_ROOM.equals(photoBO.getPhotoType()) || WebContentConstants.DEVICE_SWITCH_POWER.equals(photoBO.getPhotoType()) || WebContentConstants.DEVICE_BATTERY.equals(photoBO.getPhotoType()) || WebContentConstants.DEVICE_AIRCONDITIONING.equals(photoBO.getPhotoType())){
				//设置操作记录的属性
				StationHistoryBO stationHistoryBO = new StationHistoryBO();
				stationHistoryBO.setUserId(user.getUserId());
				stationHistoryBO.setUserName(user.getUserName());
				stationHistoryBO.setOperateTime(new Date());
				stationHistoryBO.setOperateType("insert");
				stationHistoryBO.setOperateObject(photoBO.getPhotoType() + "Photo");
				stationHistoryBO.setStationId(photoBO.getStationId());
				stationHistoryBO.setJobId(user.getJobId());
				iStationHistoryService.saveStationHistory(null, null, stationHistoryBO);
			}
		} catch (Exception e) {
			model.addAttribute("status",result);
			return "";
		}
		model.addAttribute("status",result);
		return "";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/uploadSound")
	public String uploadSound(HttpServletRequest request,ModelMap model,HttpSession session){
		String projectUri = session.getServletContext().getRealPath("/");
		String realPath = projectUri.substring(0, projectUri.indexOf("wechat"))+"services/uploadFile";
		String voiceIds = request.getParameter("voiceIds"); 
		String mediaIds = request.getParameter("mediaIds");
			if(voiceIds!=""){
			String[] voiceIdz = voiceIds.split(",");
			String[] mediaIdz = mediaIds.split(",");
			String appid = ToolUtil.getAppConfig("dyxxAPPID");
			boolean result = false;
				for(int i=0;i<voiceIdz.length;i++){
				String access_token = TokenAndTicketManager.getToken(appid);
					try {
						result = FirstQInterfaces.getISoundService().uploadSound(voiceIdz[i], access_token, mediaIdz[i], realPath);
						if(!result){
							return "false";
						}
					} catch (Exception e) {
						model.addAttribute("status",result);
						return "";
					}
				model.addAttribute("status",result);
				}
			}
		return "true";
	}
	
	
	@RequestMapping(value = "/getCount")
	public String getCount(ModelMap model,HttpSession session){
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		int result;
		//int count = 0;
		String[] localId=new String[0];
		try {
			List<PhotoBO> photoList = FirstQInterfaces.getIPhotoService().getPhotoByUser(user.getUserId(), WebContentConstants.STATUS_WAIT_UPLOAD);
			
			if(null==photoList){
				result=0;
			}else{
				result =  photoList.size();
				localId=new String[photoList.size()];
				for(int i=0;i<photoList.size();i++){
					localId[i]=photoList.get(i).getLocalId();
				}
			}
			
			/*List<SoundBO> soundList = FirstQInterfaces.getISoundService().getSound(user.getUserId(), WebContentConstants.STATUS_WAIT_UPLOAD);
			if(soundList != null){
				List<String> idList = new ArrayList<String>();
				SoundBO soundBO = null;
				for(int i = 0;i<soundList.size();i++){
					soundBO = soundList.get(i);
					if(!idList.contains(soundBO.getRelatedId())){
						idList.add(soundBO.getRelatedId());
						count++;
					}
				}
			}
			result +=count;*/
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("result",0);
			return "";
		}
		model.addAttribute("storeArray",localId);
		model.addAttribute("result",result);
		return "";
	}
	@RequestMapping(value = "/testUploadPhoto")
	public String testUploadPhoto(@RequestParam("fileToUpload") MultipartFile[] file,HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		System.out.println("1、进入到提交方法----------------》"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
		System.out.println(request.getParameter("imgdata"));
		System.out.println(request.getParameter("imgdatablob"));
		String realPath = request.getSession().getServletContext().getRealPath("/uploadFile");
			File pathFile = new File(realPath);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			/*for (MultipartFile f : file) {
				try {
					if (!f.getOriginalFilename().equals("")) {
						f.transferTo(new File(realPath + "/" + f.getOriginalFilename()));
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}*/
			System.out.println("2、上传成功之后----------------》"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
		model.addAttribute("status","上传成功");
		return "";
	}
	@RequestMapping(value = "/adjuest")
	public String adjuest(){
		return "checkHiddenTrouble/voltage/adjust";
	}
	
	@RequestMapping(value = "/notice")
	public String notice(HttpServletRequest request,ModelMap model,HttpSession session){
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		String appid = ToolUtil.getAppConfig("dyxxAPPID");
		String access_token = TokenAndTicketManager.getToken(appid);
		try {
			String info = FirstQInterfaces.getINoticeService().CommunicationAlarm(access_token, user.getWeChatService());
			model.addAttribute("info", info);
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		return "";
	}
}