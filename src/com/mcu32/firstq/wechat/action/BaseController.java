package com.mcu32.firstq.wechat.action;

import java.io.File;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mcu32.firstq.common.bean.bo.PermissionBO;
import com.mcu32.firstq.common.bean.bo.UserBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.bean.StationInfo;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.tools.MyJsUtil;
import com.mcu32.firstq.wechat.util.CopyObject;
import com.mcu32.firstq.wechat.util.FirstQBeanUtils;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.MySession;
import com.mcu32.firstq.wechat.util.SessionConstants;
import com.mcu32.firstq.wechat.util.ToolUtil;
import com.mcu32.firstq.wechat.util.springMVCBinder.BaseDoubleEditor;
import com.mcu32.firstq.wechat.util.springMVCBinder.BaseFloatEditor;
import com.mcu32.firstq.wechat.util.springMVCBinder.BaseIntegerEditor;
import com.mcu32.firstq.wechat.util.springMVCBinder.BaseLongEditor;
import com.mcu32.firstq.wechat.util.springMVCBinder.DateEditor;
import com.mcu32.firstq.wechat.util.springMVCBinder.DoubleEditor;
import com.mcu32.firstq.wechat.util.springMVCBinder.FloatEditor;
import com.mcu32.firstq.wechat.util.springMVCBinder.IntegerEditor;
import com.mcu32.firstq.wechat.util.springMVCBinder.LongEditor;
public class BaseController {

	//常量//////////////////////////////
	public final static String FAILSJSON = "{\"status\":\"服务器繁忙\"}";
	public final static String ERRORPAGE = "commons/error";
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new DateEditor());    
		//binder.registerCustomEditor(int.class, new CustomNumberEditor(int.class, true));    
		//binder.registerCustomEditor(long.class, new CustomNumberEditor(long.class, true));  
		binder.registerCustomEditor(int.class, new BaseIntegerEditor());
		binder.registerCustomEditor(long.class, new BaseLongEditor());    
		binder.registerCustomEditor(double.class, new BaseDoubleEditor());    
		binder.registerCustomEditor(float.class, new BaseFloatEditor());
		binder.registerCustomEditor(Integer.class, new IntegerEditor());
		binder.registerCustomEditor(Long.class, new LongEditor());    
		binder.registerCustomEditor(Double.class, new DoubleEditor());    
		binder.registerCustomEditor(Float.class, new FloatEditor());
		LogUtil.debug("....................initBinder work..................");
	}
	/**
	 * TODO 获取微信用户的唯一标识即 openId
	 * 如果是click事件请求服务器，则会利用图文消息的形式，重新访问服务器，并且带来参数openId（所有的访问页面的url，必须传递参数openId,来标识微信用户）
	 *	获取openId有三种方式：
	 *		1、通过url后面传递参数；
	 *		2、微信用户获取动态密码时，可以直接得到微信用户的openId,将获取的openId存放到request.setAttribute("openId", openId)中，通过request对象获得
	 *		3、通过OAuth2.0获取code的方式，调用微信接口，获取openId;
	 * 
	 * @param request
	 * @return
	 */
	public String getOpenId(HttpServletRequest request) {
		String openId = request.getParameter("openId");//方法1
		
		if(StringUtils.isEmpty(openId)) {//方法2
			Object openIdFromAttribute=request.getAttribute("openId");
			
			if(null!=openIdFromAttribute && !"".equals(openIdFromAttribute))
				openId = request.getAttribute("openId").toString(); 
		}
		
		if(StringUtils.isEmpty(openId)) {//方法3
			String code = request.getParameter("code");
			openId = MyJsUtil.getOpenId(ToolUtil.getAppConfig("dyxxAPPID"),ToolUtil.getAppConfig("dyxxAPPSECRET"), code);
		}
		return openId;
	}
	
	/**
	 * TODO 登录
	 * 获取微信用户openId,调用后台登录获取令牌接口，如果登录获取令牌接口返回空，则登录失败，返回FAIL，成功，返回OK
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return OK,登录成功   FAIL,登录失败
	 */
	public String checkLoginAndSetUserToSession(String openIdParam, HttpServletRequest request, HttpSession session){ 
		String result = "FAIL";
		try {
			String openId =StringUtils.isNotEmpty(openIdParam)?openIdParam:getOpenId(request);//如果Openid没有传过来，就调接口获取
			
			if(openId != null && !"".equals(openId)) {//获取到微信用户唯一标识，才会进行登录的验证
				UserInfo userInfo = new UserInfo();
				UserBO userBO = new UserBO();
				userBO.setWeChat(openId);
				UserBO returnUserBO = null;
				returnUserBO = FirstQInterfaces.getIUserService().getUserInfoByLogin(userBO);
				if(returnUserBO != null) {
					FirstQBeanUtils.getTarget(returnUserBO, userInfo);
					userInfo.setOpenId(openId);
					userInfo.setToken(returnUserBO.getUserId());
					Map<String, PermissionBO> permissionBOMap=FirstQInterfaces.getIPermissionService().selectUserPermissionListMap(returnUserBO.getUserId());
					
					session.setAttribute(SessionConstants.SPERMISSION, permissionBOMap);
					session.setAttribute(SessionConstants.SUSER, userInfo);
					session.setAttribute(SessionConstants.OPENID, openId);
					//getUserInfoFrom2Session(session);
		        	result = "OK";
				} else {
					result = "FAIL";
				}
			} else {
				result = "未获取到openId";
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.info("checkLogin exception.getMessaget()" + e.getMessage());
			result = e.getMessage();
		}
		return result;
	
    }
	
	/**
	 * TODO 登录
	 * 获取微信用户openId,调用后台登录获取令牌接口，如果登录获取令牌接口返回空，则登录失败，返回FAIL，成功，返回OK
	 * @param openIdParam
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 */
	public String checkLoginBySession(String openIdParam, HttpServletRequest request,HttpSession session){ 
		String result = "FAIL";
		try {
			String openId =StringUtils.isNotEmpty(openIdParam)?openIdParam:getOpenId(request);//如果Openid没有传过来，就调接口获取
			
			if(openId != null && !"".equals(openId)) {//获取到微信用户唯一标识，才会进行登录的验证
				
				if(StringUtils.isNotEmpty(getOpenIdBySu(session)) && StringUtils.isNotEmpty(getTokenBySu(session))) {
					//当前session里面有用户的信息，返回正常
					return "OK";
				} else {//当前session里面没有用户信息，调接口获取token
					UserBO userBO = new UserBO();
					userBO.setWeChat(openId);
					UserBO returnUserBO = null;
					returnUserBO = FirstQInterfaces.getIUserService().getUserInfoByLogin(userBO);
			        if(returnUserBO != null && !"".equals(returnUserBO.getUserId())) {
			        	UserInfo userInfo = getUserInfo(returnUserBO.getUserId());
			        	userInfo.setOpenId(openId);
			        	userInfo.setToken(returnUserBO.getUserId());
						session.setAttribute(SessionConstants.SUSER, userInfo);
						//getUserInfoFrom2Session(session);
			        	LogUtil.info("token is " + returnUserBO.getUserId() + ",openId is " + openId + ",sessionId is " + session.getId());
			        	result = "OK";
			        } else {
			        	result = "FAIL";
			        }
				}
				
			} else {
				result = "未获取到openId";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = e.getMessage();
		}
		return result;
	
    }
	
	/**
	 * TODO 判断用户是否登录，来跳转到相应的页面
	 * @param resultString 跳转的action路径
	 * @param forwardJsp  返回的跳转页面路径
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 */
	public String getLoginInspectionResultString(String resultString, String forwardJsp, HttpServletRequest request, HttpServletResponse response, HttpSession session,ModelMap model) {
		String openId = getOpenIdBySession(session);
		//System.out.println("session openId " + openId);
		if(openId == null || "".equals(openId)) {
			openId = getOpenId(request);
			//System.out.println("GET OPENID openId " + openId);
		}
		if(openId != null && !"".equals(openId)) {
			/*
			 * 根据openId验证该用户是否登录，
			 * 如果是OK 说明登录成功，正常跳转
			 * 如果是FAIL 说明该微信用户还未注册，跳转到注册页面
			 */
			String checkLogin = checkLoginAndSetUserToSession(openId, request, session);
			if("OK".equals(checkLogin)) {
				//在登录验证中，已经将用户信息存入到session中了，这里就不用再次存入了
				//String token=getTokenBySu(session);//获取token，从session对象中获取
				//UserInfo userInfo = getUserInfo(token);
				//session.setAttribute(SessionConstants.SUSER, userInfo);//存session
			} else if("FAIL".equals(checkLogin)) {
				
				model.addAttribute("ForwardJsp", forwardJsp);//当注册成功后返回的页面
				model.addAttribute("openId", openId);
				resultString = "user/register";
			} else {
				String appId = ToolUtil.getAppConfig("dyxxAPPID");  // 第三方用户唯一凭证  
		        String callBackDomain=ToolUtil.getAppConfig("CallBackDomain");//项目的域名
		    	String requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri=http://"+callBackDomain+"/wechat/main/toStart.html&response_type=code&scope=snsapi_base&state=1";
				model.addAttribute("msg", checkLogin);
				model.addAttribute("requestUrl", requestUrl);
				resultString = "commons/errorpage";
			}
		} else {
			String appId = ToolUtil.getAppConfig("dyxxAPPID");  // 第三方用户唯一凭证  
	        String callBackDomain=ToolUtil.getAppConfig("CallBackDomain");//项目的域名
	    	String requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri=http://"+callBackDomain+"/wechat/main/toStart.html&response_type=code&scope=snsapi_base&state=1";
			model.addAttribute("msg", "未获取到openId");
			model.addAttribute("requestUrl", requestUrl);
			resultString = "commons/errorpage";
			/*请检查 app.properties 中的以下三个参数是否正确配置，不要使用其他人的配置信息*/
	        //String appId = ToolUtil.getAppConfig("dyxxAPPID");  // 第三方用户唯一凭证  
	        //String callBackDomain=ToolUtil.getAppConfig("CallBackDomain");//项目的域名
	    	//String requestUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri=http://"+callBackDomain+"/wechat/main/toStart.html&response_type=code&scope=snsapi_base&state=1";
	    	//System.out.println("请求requestUrl： "+requestUrl);
    		//MyJsUtil.httpRequest1(requestUrl, "GET", null);
		}
		return resultString;
	}
	/**
	 * TODO 获取后台用户的的主键，也就是token
	 * @param openId
	 * @param request
	 * @return 返回空值，说明没有获取到token
	 */
	public String getToken(String openId, HttpServletRequest request, HttpSession session) {
		//通过调用后台登录获取令牌接口，返回成功，则获取到token的值，将openId存放到request对象中
		String token = "";
		if(openId != null && !"".equals(openId) && request != null) {
			request.setAttribute("openId", openId);
		}
		//判断用户是否登录成功
		String isLogin = checkLoginAndSetUserToSession(openId, request, session);
		//如果登录成功，则从用户对象中获取token
		if("OK".equals(isLogin)) {
			UserInfo userInfo = session.getAttribute(SessionConstants.SUSER) != null ? (UserInfo) session.getAttribute(SessionConstants.SUSER) : null;
			if(userInfo != null ) {
				token = userInfo.getToken();
			}
		}
		return token;
	}

	/**
	 * TODO 前台页面调用微信js需要的配置
	 * @param request
	 * @param model
	 */
	public void setWechatConfig(HttpServletRequest request,ModelMap model){
		String config=MyJsUtil.generateConfigJsonDefault(request);//拼写微信js需要的config参数
		model.addAttribute("jsConfig", config);//拼写微信js需要的config参数
	}
	
	/**
	 * TODO 处理从服务器端获得的照片路径
	 * @param url
	 * @param path
	 * @param location
	 * @return
	 */
	public String getPhotoLocation(String url, String path, String location){
		if(StringUtils.isEmpty(url)||StringUtils.isEmpty(path)||StringUtils.isEmpty(location))
			return "";
		
		String before = url.substring(0, url.indexOf(path));
		String after = location.substring(location.indexOf(path));
		
		return before + after;
	}
	/**
	 * TODO 处理从服务器端获得的照片路径
	 * @param url
	 * @param location
	 * @return
	 */
	public String getPhotoLocation(String url, String location){
		if(url == null || "".equals(url) || location == null || "".equals(location)) {
			return "";
		}
		String path = url.substring(0, url.lastIndexOf('/'));
		path = path.substring(path.lastIndexOf('/') + 1);
		
		String before = url.substring(0, url.indexOf(path));
		
		String after = location.substring(location.indexOf(path));
		after = after.replace('\\', '/');
		
		return  before + after;
	}
	
	/**
	 * 从session 里面获得登录的user
	 * @param session
	 * @return
	 */
	public UserInfo getSessionUser(HttpSession session){
		if(null!=session)
			return (UserInfo)session.getAttribute(SessionConstants.SUSER);
		return null;
	}
	
	/**
	 * 从session 里面获得登录的user的 token
	 * @param session
	 * @return
	 */
	public String getTokenBySu(HttpSession session){
		UserInfo su=getSessionUser(session);
		if(null!=su){
			return StringUtils.isEmpty(su.getToken())?"":su.getToken();
		}
		return "";
	}
	
	/**
	 * 从session 里面获得登录的user的 openId
	 * @param session
	 * @return
	 */
	public String getOpenIdBySu(HttpSession session){
		UserInfo su=getSessionUser(session);
		if(null!=su){
			return StringUtils.isEmpty(su.getOpenId())?"":su.getOpenId();
		}
		return "";
	}
	public String getOpenIdBySession(HttpSession session){
		if(null!=session)
			return session.getAttribute(SessionConstants.OPENID) != null && !"".equals(session.getAttribute(SessionConstants.OPENID)) ?(String)session.getAttribute(SessionConstants.OPENID) :"";
		return "";
	}
	public StationInfo getSessionStation(HttpSession session){
		if(null!=session)
			return (StationInfo)session.getAttribute(SessionConstants.STATION);
		return null;
	}
	public void setSessionStationId(HttpSession session,String stationId){
		StationInfo se=getSessionStation(session);
		if(null!=se){
			se.setStationId(stationId);
		}else{
			se=new StationInfo();
			se.setStationId(stationId);
			session.setAttribute(SessionConstants.STATION, se);
		}
	}
	public String getStationIdBySession(HttpSession session){
		StationInfo se=getSessionStation(session);
		if(null!=se){
			return se.getStationId();
		}
		return "";
	}
	
	/**
	 * 把自定义session中存储的不为空的数据复制到当前 HttpSession 中
	 * @param session
	 * @return
	 */
	public UserInfo getUserInfoFrom2Session(HttpSession session){
		UserInfo u=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		if(null!=u){
			UserInfo us=(UserInfo)MySession.getAttribute(u.getOpenId(), SessionConstants.SUSER);
			if(null==us)us=new UserInfo();
			CopyObject.convertEachBeanNotNull(us, u);
			MySession.setAttribute(u.getOpenId(), SessionConstants.SUSER, us);
		}
		return u;
	}
	
	public String getUUID(){
		UUID randomUUID = UUID.randomUUID();
		return randomUUID.toString();
	}
	
	public UserInfo getUserInfo(String token){
		UserInfo userInfo = new UserInfo();
		UserBO returnUserBO = null;
		try {
			returnUserBO = FirstQInterfaces.getIUserService().getUserInfoByUserId(token);
		} catch (FirstQException e) {
			LogUtil.error(e);
		}
		if(returnUserBO != null) {
			FirstQBeanUtils.getTarget(returnUserBO, userInfo);
			userInfo.setOpenId(token);
		}
		return userInfo;
	}
	
	/**
	 * 通过openId调接口登录的方法
	 */
	public UserInfo getUserInfoByOpenId(String openId,HttpSession session){
		try {
		    UserBO userBO = new UserBO();
			userBO.setWeChat(openId);
			UserBO returnUserBO = null;
			returnUserBO = FirstQInterfaces.getIUserService().getUserInfoByLogin(userBO);
		    if(returnUserBO == null){
		    	return null;
		    }
		    UserInfo userInfo = getUserInfo(returnUserBO.getUserId());
	    	userInfo.setOpenId(openId);
	    	userInfo.setToken(returnUserBO.getUserId());
			session.setAttribute(SessionConstants.SUSER, userInfo);
			//getUserInfoFrom2Session(session);
			return userInfo;
		} catch (Exception e) {
			LogUtil.error(e);
		}
		return null;
	}
	
	/**
	 * 通过openId登录的方法，得到的信息会存到当前session和自定义的Mysession中<br>
	 * --判断当前MySession中是否有<br>
	 * ----如果 有    返回用户<br>
	 * ----如果 没有 通过openId调接口登录
	 */
	public UserInfo loginByOpenId(String openId,HttpSession session){
		UserInfo userInMySession=MySession.getAttribute(openId, SessionConstants.SUSER);
		if(null==userInMySession) {//如果MySession里面有，说明微信已经给公众号发送过消息，第一次发送的时候已经从数据库里查询了 用户的完整信息，存到了MySession里面
			userInMySession=getUserInfoByOpenId(openId,session);//通过openid调接口登录
			if(null==userInMySession){//如果用户没有注册，并且刚进来，需要new一个，否则会空指针
				userInMySession=new UserInfo();
			}
			MySession.setAttribute(openId, SessionConstants.SUSER, userInMySession);
		}
		userInMySession.setOpenId(openId);
		session.setAttribute(SessionConstants.SUSER, userInMySession);
		return userInMySession;
	}
	public String getContextPath(){
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getContextPath();
	}
	public String getFileUploadRealPath(){
		String projectUri = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession().getServletContext().getRealPath("/");
		return projectUri.substring(0, projectUri.indexOf("wechat"))+"services"+File.separator+"uploadFile";
	}
	public static String getFileDocTemp(){
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();   
        ServletContext servletContext = webApplicationContext.getServletContext();
        String docTempPath=servletContext.getRealPath("/") + "docTemp"+File.separator;
        File filePath = new File(docTempPath);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
        return docTempPath;
	}
	/**
	 * request.getParameterMap();获得的参数是数组类型的参数 通过request的map获得真实的map参数
	 * 参考：http://blog.sina.com.cn/s/blog_87216a0001014f5k.html
	 */
	public static Map<String, String> getRealMapByReqeustMap(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		// 得到枚举类型的参数名称，参数名称若有重复的只能得到第一个
		@SuppressWarnings("unchecked")
		Enumeration<String> enumt = request.getParameterNames();
		while (enumt.hasMoreElements()) {
			String paramName = enumt.nextElement();
			String paramValue = request.getParameter(paramName);
			// 形成键值对应的map
			map.put(paramName, paramValue);
			//System.out.println(paramName+","+paramValue);
		}
		return map;
	}
}
