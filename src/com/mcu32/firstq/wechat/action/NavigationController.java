package com.mcu32.firstq.wechat.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcu32.firstq.common.bean.bo.PermissionBO;
import com.mcu32.firstq.common.util.LogUtil;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.SessionConstants;
import com.mcu32.firstq.wechat.util.ToolUtil;
import com.mcu32.firstq.wechat.util.date.DateUtil;

@Controller
@RequestMapping(value = "/main")
public class NavigationController extends BaseController{

	/**
	 * 通用首页方法
	 * http://left.wicp.net/wechat/main/toStart.html
	 */
	@RequestMapping(value="/toStart",method=RequestMethod.GET)
	public String toStart(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		
		String resultString="commons/main";
		String forwardJsp = "main/toStart";
		resultString = getLoginInspectionResultString(resultString, forwardJsp, request, response, session, model);
		
		return resultString;
	}
	
	@RequestMapping(value="/getNowTimeFromServer",method=RequestMethod.POST)
	public String getNowTimeFromServer(HttpServletRequest request,HttpServletResponse response, ModelMap model){
		Date now=new Date();
		model.addAttribute("serverTimestamp", now.getTime());
		model.addAttribute("serverTimeFormat", DateUtil.DateToString(now,"yyyy-MM-dd HH:mm:ss"));
		return "";
	}
	/**
	 * 登录之后首页跳转方法
	 * http://left.wicp.net/wechat/main/toStartNotCheck.html
	 */
	@RequestMapping(value="/toStartNotCheck",method=RequestMethod.GET)
	public String toStartNotCheck(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		model.addAttribute("lng",request.getAttribute("lng"));
		model.addAttribute("lat",request.getAttribute("lat"));
		String resultString="commons/main";
		
		return resultString;
	}
	
	@RequestMapping(value = "/navigation", method = RequestMethod.GET)
	public String navigate(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String pageUrl = request.getParameter("id");
		return pageUrl;
	}

	/**
	 * http%3a%2f%2fleft.wicp.net%2fwechat%2fmain%2ftoJob.html%3fjobId%3djob_4
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx62fd9aac1de0301e&redirect_uri=http%3a%2f%2fleft.wicp.net%2fwechat%2fmain%2ftoJob%3fjobId%3djob_4&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx93a50f039d4b5c33&redirect_uri=http%3a%2f%2fwww.vwbl.cn%2fwechat%2fmain%2ftoJob%3fjobId%3djob_4&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
	 */
	@RequestMapping(value="/toJob",method=RequestMethod.GET)
	public String toJob(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		
		String resultString = "user/register";
		String forwardJsp = "main/toJob";
		
		String openId=getOpenId(request);
		String isRegiste=checkLoginAndSetUserToSession(openId, request, session);
		
		String jobId=request.getParameter("jobId");
		if(StringUtils.isNotEmpty(jobId)){
			session.setAttribute(SessionConstants.JOBID, jobId);
		}else{
			jobId=(String) session.getAttribute(SessionConstants.JOBID);
		}
		if("OK".equals(isRegiste)){
			resultString="commons/getLocationMain";
			
			UserInfo user = (UserInfo)session.getAttribute(SessionConstants.SUSER);
			user.setJobId(jobId);
			model.addAttribute("jobId", jobId);
		}
		
		model.addAttribute("toPageUrl", "station/getNearbyList");
		model.addAttribute("ForwardJsp", forwardJsp);//当注册成功后返回的页面
		model.addAttribute("openId", openId);
		
		return resultString;
	}
	/**
	 * http://left.wicp.net/wechat/main/toPage?toPageUrl=report/daysReportList
	 * http%3a%2f%2fleft.wicp.net%2fwechat%2fmain%2ftoPage%3ftoPageUrl%3dreport%2fdaysReportList
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx62fd9aac1de0301e&redirect_uri=http%3a%2f%2fleft.wicp.net%2fwechatTS%2fmain%2ftoPage%3ftoPageUrl%3dreport%2fdaysReportList&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
	 */
	@RequestMapping(value="/toPage",method=RequestMethod.GET)
	public String toPage(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String resultString = "user/register";
		String forwardJsp = "main/toPage";
		
		String openId=getOpenId(request);
		String isRegiste=checkLoginAndSetUserToSession(openId, request, session);
		
		///report/daysReportList
		String toPageUrl=request.getParameter("toPageUrl");
		LogUtil.info("从微信群过来的url -------->" +toPageUrl);
		/*Matcher matcher = Pattern.compile("[\\u4e00-\\u9FFF]").matcher(toPageUrl);
        while (matcher.find()) {  
          String tmp=matcher.group();  
          try {
			toPageUrl=toPageUrl.replaceAll(tmp,java.net.URLEncoder.encode(tmp,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }*/
		toPageUrl = FirstqTool.dealUrlEncode(toPageUrl);
        LogUtil.info("处理完成之后的url-------->" + toPageUrl);
		if("OK".equals(isRegiste)){
			@SuppressWarnings("unchecked")
			Map<String, PermissionBO> permissionBOMap = (Map<String, PermissionBO>)session.getAttribute(SessionConstants.SPERMISSION);
			PermissionBO p=permissionBOMap.get(toPageUrl);
			if(null==p||new Integer(0).equals(p.getNeedLocation())){
				resultString="redirect:/"+toPageUrl;
			}else{
				resultString="commons/getLocationMain";
			}
			model.addAttribute("jobId", "");
		}
		model.addAttribute("toPageUrl", toPageUrl);
		model.addAttribute("ForwardJsp", forwardJsp);//当注册成功后返回的页面
		model.addAttribute("openId", openId);
		
		return resultString;
	}
	/**
	 * http://left.wicp.net/wechat/main/toPage?toPageUrl=report/daysReportList
	 * http%3a%2f%2fleft.wicp.net%2fwechat%2fmain%2ftoPage%3ftoPageUrl%3dreport%2fdaysReportList
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx62fd9aac1de0301e&redirect_uri=http%3a%2f%2fleft.wicp.net%2fwechatTS%2fmain%2ftoPageNotLocation%3ftoPageUrl%3dreport%2fdaysReportList&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx86497c6c572d5185&redirect_uri=http%3a%2f%2fwangf3190.xicp.cn%2fwechatTS%2fmain%2FtoPageNotLocation%3FtoPageUrl%3DfourHight%2FhighTemperatureTS&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect;
	 */
	@RequestMapping(value="/toPageNotLocation",method=RequestMethod.GET)
	public String toPageNotLocation(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String resultString = "user/register";
		String forwardJsp = "main/toPageNotLocation";
		
		String openId=getOpenId(request);
		String isRegiste=checkLoginAndSetUserToSession(openId, request, session);
		
		String toPageUrl=request.getParameter("toPageUrl");
		LogUtil.info("从微信群过来的url -------->" +toPageUrl);
		toPageUrl = FirstqTool.dealUrlEncode(toPageUrl);
		LogUtil.info("处理完成之后的url-------->" + toPageUrl);

		if("OK".equals(isRegiste)){
			resultString="redirect:/"+toPageUrl;
			model.addAttribute("jobId", "");
		}
		model.addAttribute("toPageUrl", toPageUrl);
		model.addAttribute("ForwardJsp", forwardJsp);//当注册成功后返回的页面
		model.addAttribute("openId", openId);
		
		return resultString;
	}
	
	/**
	 * http://left.wicp.net/wechat/main/toPage?toPageUrl=report/daysReportList
	 * http%3a%2f%2fleft.wicp.net%2fwechat%2fmain%2ftoPage%3ftoPageUrl%3dreport%2fdaysReportList
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx62fd9aac1de0301e&redirect_uri=http%3a%2f%2fleft.wicp.net%2fwechatTS%2fmain%2ftoPageNotLocation%3ftoPageUrl%3dreport%2fdaysReportList&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx86497c6c572d5185&redirect_uri=http%3a%2f%2fwangf3190.xicp.cn%2fwechatTS%2fmain%2FtoPageNotLocation%3FtoPageUrl%3Dproclamation%2Fmain&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect;
	 */
	@RequestMapping(value="/toPageWithUser",method=RequestMethod.GET)
	public String toWechatPageWithUser(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String resultString = "user/register";
		String forwardJsp = "main/toPageWithUser";
		
		String openId=getOpenId(request);
		String isRegiste=checkLoginAndSetUserToSession(openId, request, session);
		
		String toPageUrl=request.getParameter("toPageUrl");
		UserInfo u=getSessionUser(session);
		
		LogUtil.info("从微信群过来的url -------->" +toPageUrl);
		try {
			toPageUrl = URLDecoder.decode(toPageUrl, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		LogUtil.info("从微信群过来的url解码 -------->" +toPageUrl);
		if(null !=u){
			if(toPageUrl.indexOf("?")==-1){
				toPageUrl+="?userId="+u.getUserId();
			}else{
				toPageUrl+="&userId="+u.getUserId();
			}
		}
		
		
		toPageUrl = FirstqTool.dealParmaEncode(toPageUrl);
		LogUtil.info("处理完成之后的url -------->" + toPageUrl);
		
		if("OK".equals(isRegiste)){
			resultString="redirect:"+toPageUrl;
			model.addAttribute("jobId", "");
		}
		model.addAttribute("toPageUrl", toPageUrl);
		model.addAttribute("ForwardJsp", forwardJsp);//当注册成功后返回的页面
		model.addAttribute("openId", openId);
		
		return resultString;
	}

	/**
	 * 拼装 网页授权获取用户基本信息 的url，（详见微信api 网页授权获取用户基本信息）
	 * 拼装好的url放到 跳转页面上，跳转页面加载完成之后，请求url指向的微信服务
	 * 微信服务器做处理之后，让页面请求 toPage 指向的controller，并且给controller传入一个code参数，controller通过这个code，请求微信服务器获取openid
	 */
	@RequestMapping(value="/toRedirectPage")
	public String toRedirectPage(HttpServletRequest request,ModelMap model){
		String toPage=request.getParameter("toPage");//main%2ftoJob%3fjobId%3djob_4
		try {
			System.out.println("toPage编码前"+toPage);
			toPage = URLEncoder.encode(toPage, "utf-8");
			System.out.println("toPage编码后"+toPage);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ToolUtil.getAppConfig("dyxxAPPID")+"&redirect_uri=http%3a%2f%2f"+ToolUtil.getAppConfig("CallBackDomain")+"%2f"+getContextPath().replace("/", "")+toPage+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		model.addAttribute("requestUrl", url);
		System.out.println("url=="+url);
		return "commons/redirictPage";
	}
	/**
	 * 拼装 网页授权获取用户基本信息 的url，（详见微信api 网页授权获取用户基本信息）
	 * 拼装好的url放到 跳转页面上，跳转页面加载完成之后，请求url指向的微信服务
	 * 微信服务器做处理之后，让页面请求 toPage 指向的controller，并且给controller传入一个code参数，controller通过这个code，请求微信服务器获取openid
	 */
	@RequestMapping(value="/toRegistRedirect")
	public String toRedirectPage1(HttpServletRequest request,ModelMap model){
		String toPage=request.getParameter("toPage");//main%2ftoJob%3fjobId%3djob_4
		try {
			System.out.println("toPage编码前"+toPage);
			String toPageUrl=toPage.substring(0, toPage.indexOf("=")+1) + URLEncoder.encode(toPage.substring(toPage.indexOf("=")+1),"UTF-8");
			toPage = URLEncoder.encode(toPageUrl, "utf-8");
			System.out.println("toPage编码后"+toPage);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ToolUtil.getAppConfig("dyxxAPPID")+"&redirect_uri=http%3a%2f%2f"+ToolUtil.getAppConfig("CallBackDomain")+"%2f"+getContextPath().replace("/", "")+toPage+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
		model.addAttribute("requestUrl", url);
		System.out.println("url=="+url);
		return "commons/redirictPage";
	}
	
}
