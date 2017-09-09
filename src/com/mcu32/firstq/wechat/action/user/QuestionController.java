package com.mcu32.firstq.wechat.action.user;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gtm.wechat.popular.support.TokenAndTicketManager;
import com.mcu32.firstq.common.bean.bo.PhotoBO;
import com.mcu32.firstq.common.bean.bo.QuestionBO;
import com.mcu32.firstq.common.bean.bo.UserBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.PhotoInfo;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.tools.TemplateMessages;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.SessionConstants;
import com.mcu32.firstq.wechat.util.ShortUrlUtil;
import com.mcu32.firstq.wechat.util.ToolUtil;
import com.mcu32.firstq.wechat.util.WebContentConstants;
import com.mcu32.firstq.wechat.util.date.DateUtil;

@Controller
@RequestMapping(value = "/question")
public class QuestionController extends BaseController {

	@RequestMapping(value = "/questionMain")
	public String selectQuestionType(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String selectId=request.getParameter("selectId");
		String group=request.getParameter("groupName");
		if (StringUtils.isNotEmpty(group)) {
			try {
				group=URLDecoder.decode(group,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		LogUtil.info("从微信群过来的群名和昵称 -------->" +group);
		String groupName=request.getParameter("group");
		String nickName=request.getParameter("nickName");
		if(StringUtils.isNotEmpty(group)){
			if(group.indexOf("_")>=0){
				String [] str=group.split("_");
				groupName=str[0];
				nickName=group.replace(groupName+"_", "");
			}
		}else{
			if(StringUtils.isEmpty(groupName)){
				groupName="第一象限";
			}
		}
		
		model.addAttribute("selectId", selectId);
		model.addAttribute("groupName", groupName);
		model.addAttribute("nickName", nickName);
		return "question/questionMain";
	}
	
	@RequestMapping(value = "/addQuestion", method = RequestMethod.POST)
	public String addQuestion(QuestionBO questionBO, HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		try {
		UserInfo u=getSessionUser(session);
		Map<String,String> m=getRealMapByReqeustMap(request);
		questionBO.setFillDate(new Date());
		questionBO.setQuestionStatus("未处理");
		String uid=getUUID();
		questionBO.setQuestionId(uid);
		questionBO.setOperateTime(new Date());
		questionBO.setOperateUserId(u.getUserId());
		questionBO.setOperateUser(u.getUserName());
		LogUtil.info("从页面传过来的群名 -------->" +questionBO.getGroupName()+"昵称:"+questionBO.getNickName());
		boolean saveQSuccess=FirstQInterfaces.getIQuestionService().saveQuestion(questionBO);
		String projectUri = session.getServletContext().getRealPath("/");
		
		boolean saveImgSuccess  = saveWaitUploadPhoto(m.get("deviceImgs"),m.get("mediaImgs"),"-1",uid,"question",projectUri,(UserInfo)session.getAttribute(SessionConstants.SUSER));//保存照片
			
		model.addAttribute("succ", (saveQSuccess&&saveImgSuccess));
		
		String url=FirstqTool.getToPageURL("/main/toPage?toPageUrl=question/toDetail?questionId="+uid);
		
		String questionTitle=questionBO.getQuestionTitle().length()>10?questionBO.getQuestionTitle().substring(0, 10)+"...":questionBO.getQuestionTitle();
		
		FirstqTool.sendQuestionToWechatGroup("账号"+u.getUserName()+"进行了 “"+questionTitle+"”问题填报，请点击链接"+url+"进行处理。", "");
		
		Map<String, String> dataMap=new HashMap<String,String>();
		dataMap.put("url", url);
		dataMap.put("first", "您提交的问题已经反馈给我们，我们会尽快跟您进行联系，谢谢您的支持。");
		dataMap.put("keyword1", u.getUserName());
		dataMap.put("keyword2", questionTitle);
		dataMap.put("keyword3", DateUtil.DateToString(questionBO.getFillDate(), "yyyy-MM-dd HH:mm:ss"));
		dataMap.put("remark", "");
		TemplateMessages.sendReceiveQuestion(u.getWeChatService(), dataMap);//发送模版消息
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping(value = "/getQuestionList", method = RequestMethod.GET)
	public String getQuestionList(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		try {
			Map<String,String> map=getRealMapByReqeustMap(request);
			
			map.put("orderByClause", " FILL_DATE desc");
			List<QuestionBO> list=FirstQInterfaces.getIQuestionService().getQuestionBOList(map);
			int totalRecord=FirstQInterfaces.getIQuestionService().selectByBaseConditionsCount(map);
			String pageLengthStr=map.get("pageLength");
			int pageLength=Integer.valueOf(pageLengthStr);
			model.addAttribute("questionList", list);
			
			int totalPage=1;
			if(0==totalRecord){
				totalPage=1;
			}else if(totalRecord % pageLength == 0){
				totalPage=totalRecord/ pageLength;
			}else{
				totalPage=totalRecord/ pageLength+1;
			}
			model.addAttribute("totalPage", totalPage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping(value = "/toDetail")
	public String toDetail(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		UserInfo u=getSessionUser(session);
		
		String questionId=request.getParameter("questionId");
		String groupName=request.getParameter("group");
		String nickName=request.getParameter("nickName");
		QuestionBO qb=FirstQInterfaces.getIQuestionService().getByPrimaryKey(questionId);
		model.addAttribute("qb", qb);
		
		try {
			List<PhotoBO> photoBOList = FirstQInterfaces.getIPhotoService().getPhotoBOListByRelateId(qb.getQuestionId());
			for(int i=0;i<photoBOList.size();i++){
				PhotoBO pb=photoBOList.get(i);
				
				if(pb.getStatus().equals("已上传")){
					pb.setThumbLocation(FirstqTool.getPhotoLocation(pb.getThumbLocation()));
					pb.setFileLocation(FirstqTool.getPhotoLocation(pb.getFileLocation()));
				}else{
					if(pb.getCreateUserId().equals(u.getUserId())){
						pb.setThumbLocation(pb.getLocalId());
					}else{
						pb.setThumbLocation("http://"+ToolUtil.getAppConfig("CallBackDomain") +"/wechat/assets/i/waitUpload.png");
					}
				}
			}
			model.addAttribute("photoList", photoBOList);
			model.addAttribute("groupName", groupName);
			model.addAttribute("nickName", nickName);
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		
		return "question/detail";
	}
	@RequestMapping(value = "/updateQuestion")
	public String updateQuestion(QuestionBO questionBO,HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		UserInfo u=getSessionUser(session);
		questionBO.setQuestionStatus("已处理");
		questionBO.setResponseTime(new Date());
		questionBO.setResponseUserId(u.getUserId());
		questionBO.setResponseUser(u.getUserName());
		boolean updateSucc=FirstQInterfaces.getIQuestionService().updateQuestion(questionBO);
		model.addAttribute("succ", updateSucc);
		
		try {
			
			String url=FirstqTool.getToPageURL("/main/toPage?toPageUrl=question/toDetail?questionId="+questionBO.getQuestionId());
			UserBO ub=FirstQInterfaces.getIUserService().getUserInfoByUserId(questionBO.getOperateUserId());
			
			String questionTitle=questionBO.getQuestionTitle().length()>10?questionBO.getQuestionTitle().substring(0, 10)+"...":questionBO.getQuestionTitle();
			
			FirstqTool.sendQuestionToWechatGroup("用户"+u.getUserName()+"对账号"+ub.getUserName()+"提出的 “"+questionTitle+"”问题已经处理，详情请点击链接"+url+"进行查看。", "");
			
			Map<String, String> dataMap=new HashMap<String,String>();
			dataMap.put("url", url);
			dataMap.put("first", "问题已处理");
			dataMap.put("keyword1", questionTitle);
			dataMap.put("keyword2", "您提交的问题已经进行处理，请点击进行查看。");
			dataMap.put("keyword3", DateUtil.DateToString(questionBO.getResponseTime(), "yyyy-MM-dd HH:mm:ss"));
			dataMap.put("remark", "");
			TemplateMessages.sendDealQuestion(ub.getWeChatService(), dataMap);//发送模版消息
		} catch (FirstQException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**保存照片*/
	private boolean saveWaitUploadPhoto(String deviceImgs,String mediaImgs,String stationId,String relateId,String deviceType,String projectUri,UserInfo user){
		boolean result = true;
		if(StringUtils.isNotEmpty(deviceImgs)){
			Date date = new Date();
			String[] imgsArry = deviceImgs.split(",");
			String[] mediaImgsArry = mediaImgs.split(",");
			
			PhotoInfo pi = new PhotoInfo();
			PhotoBO photoBO = new PhotoBO();
			int sqe = 1;
			
			for(int i=0;i<imgsArry.length;i++){
				String imgId=imgsArry[i];
				String mediaId=mediaImgsArry[i];
				
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
					
					String realPath = projectUri.substring(0, projectUri.indexOf(ToolUtil.getAppConfig("WechatProjectName")))+"services/uploadFile";
					String thumbPath = projectUri.substring(0, projectUri.indexOf(ToolUtil.getAppConfig("WechatProjectName")))+"services/uploadFile/thumb";
					String appid = ToolUtil.getAppConfig("dyxxAPPID");
					String access_token = TokenAndTicketManager.getToken(appid);
					
					try {
						boolean imgSaveSucc =  FirstQInterfaces.getIPhotoService().uploadPhoto(pi.getPhotoId(), access_token, mediaId, realPath, thumbPath,projectUri+"assets/i/imgIcon.png");
						result=(imgSaveSucc && result);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					LogUtil.error(e);
					result = false;
				}
			}
		}
		return result;
	}
}
