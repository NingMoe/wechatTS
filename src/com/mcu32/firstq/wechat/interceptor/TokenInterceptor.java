package com.mcu32.firstq.wechat.interceptor;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mcu32.firstq.common.bean.bo.PermissionBO;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.util.SessionConstants;

public class TokenInterceptor extends HandlerInterceptorAdapter {
	
	/**
	 * 有注解，并且注解为true的才验证
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session=request.getSession();
		StringBuffer requestURL=request.getRequestURL();
		String logInfo="sessionId is "+session.getId()+";requestURL is "+requestURL+";";
		String interceptInfo="no intercept";
		boolean returnVal=true;
		
    	if (handler instanceof HandlerMethod) {
	        HandlerMethod handlerMethod = (HandlerMethod) handler;
	        Method method = handlerMethod.getMethod();
	        AccessRequired annotation = method.getAnnotation(AccessRequired.class);
	        if (null != annotation){//有注解
	        	boolean needLogin = annotation.needLogin();
	        	boolean interceptURL = annotation.interceptURL();
	        	if(interceptURL)needLogin=true;//如果需要验证是否有权限，必须登录
	        	
                if (needLogin) {//needSaveSession 为true，需要验证
                	String token="";
                	UserInfo su= (UserInfo)session.getAttribute(SessionConstants.SUSER);
	            	if(su != null)
	            		token = su.getToken();
    	            if(StringUtils.isEmpty(token)) {
    	            	interceptInfo="dont have token";
    	            	returnVal= false;
    	            }else{
    	            	interceptInfo="have token";
    	            }
                }
                if(interceptURL){
                	int indexOfProject=requestURL.indexOf(request.getContextPath())+request.getContextPath().length();
                	
                	/*http://localhost/wechat/report/daysReportList.html 
                	 * 这样的url 取出字符串： /report/daysReportList
                	 * 考虑后缀有时有，有时没有
                	 */
                	String permissionURL=requestURL.substring(indexOfProject+1);
                	int lastIndex= -1==permissionURL.indexOf(".")?permissionURL.length():permissionURL.indexOf(".");
                	permissionURL=permissionURL.substring(0, lastIndex);
//                	System.out.println("url key is:"+permissionURL);
                	@SuppressWarnings("unchecked")
					Map<String, PermissionBO> permissionBOMap = (Map<String, PermissionBO>)session.getAttribute(SessionConstants.SPERMISSION);
                	if(null==permissionBOMap || !permissionBOMap.containsKey(permissionURL)){
                		interceptInfo="没有访问权限";
                		returnVal= false;
                	}else{
                		interceptInfo="可以访问";
                	}
                }
                if(!returnVal){
                	if(isAjax(request)){
                		logInfo+="ajax请求;";
                		response.getWriter().write("{\"succ\":false,\"msg\" : \"没有权限！\",\"hasError\":true,\"error\" : \"该用户没有分配权限！\"}");
            			response.getWriter().flush();
                	}else{
                		logInfo+="普通请求;";
                		response.sendRedirect(request.getContextPath() + "/assets/gxui/tokenInterceptor.jsp");
                	}
                }
	        }//没注解
    	}//没注解
    	System.out.println(logInfo+interceptInfo);
    	return returnVal;
    }
	
	/**
	 * 判断ajax请求
	 * @param request
	 * @return
	 */
	boolean isAjax(HttpServletRequest request){
	    return  (request.getHeader("X-Requested-With") != null  && "XMLHttpRequest".equals( request.getHeader("X-Requested-With").toString())   ) ;
	}
}
