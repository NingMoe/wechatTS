package com.mcu32.firstq.wechat.tools;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.gtm.wechat.popular.util.JsUtil;
import com.mcu32.firstq.wechat.tools.liufeng.util.MyX509TrustManager;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.ToolUtil;

public class MyJsUtil extends JsUtil{
	private static Logger log = LogManager.getLogger(LogUtil.class);
	
	/**
	 * 获得默认的config
	 * @param request 
	 * @return
	 */
	public static String generateConfigJsonDefault(HttpServletRequest request){
		StringBuffer url = request.getRequestURL().append(StringUtils.isEmpty(request.getQueryString())?"":"?"+request.getQueryString());
		String appid=ToolUtil.getAppConfig("dyxxAPPID");
		String ticket=MyTokenAndTicketManager.getTicket(appid); 
		String[] jsApiList={
				"startRecord",
				"stopRecord",
				"onVoiceRecordEnd",
				"playVoice",
				"pauseVoice",
				"stopVoice",
				"onVoicePlayEnd",
				"uploadVoice",
				"downloadVoice",
	  		    "chooseImage",
	  		    "previewImage",
	  		    "uploadImage",
	  		    "downloadImage",
	  		    "getNetworkType",
	  		    "openLocation",
	  		    "getLocation",
	  		    "scanQRCode"
	  		    };
		return generateConfigJson(ticket,false,appid,url.toString(),jsApiList);
	}
	
	/**
	 * 获得默认的通过appid设置的config
	 * @param request
	 * @return
	 */
	public static String generateConfigJsonDefault(HttpServletRequest request,String appidconfig){
		StringBuffer url = request.getRequestURL();
		String appid=ToolUtil.getAppConfig(appidconfig);
		String ticket=MyTokenAndTicketManager.getTicket(appid);
		String[] jsApiList={
	  		      "chooseImage",
	  		      "previewImage",
	  		      "uploadImage",
	  		      "downloadImage",
	  		      "getNetworkType",
	  		      "openLocation",
	  		      "getLocation",
	  		      "scanQRCode"
	  		      };
		return generateConfigJson(ticket,false,appid,url.toString(),jsApiList);
	}
	/**
	 * 获得默认的config
	 * @param request
	 * @return
	 */
	public static String generateConfigJsonDefault(HttpServletRequest request,boolean debug){
		StringBuffer url = request.getRequestURL();
		String appid=ToolUtil.getAppConfig("dyxxAPPID");
		String ticket=MyTokenAndTicketManager.getTicket(appid);
		String[] jsApiList={
	  		      "chooseImage",
	  		      "previewImage",
	  		      "uploadImage",
	  		      "downloadImage",
	  		      "getNetworkType",
	  		      "openLocation",
	  		      "getLocation",
	  		      "scanQRCode"
	  		      };
		return generateConfigJson(ticket,debug,appid,url.toString(),jsApiList);
	}
	/**
	 * 获得全部的config
	 * @param request
	 * @return
	 */
	public static String generateConfigJsonHole(HttpServletRequest request){
		StringBuffer url = request.getRequestURL();
		String appid=ToolUtil.getAppConfig("dyxxAPPID");
		String ticket=MyTokenAndTicketManager.getTicket(appid);
		
		String[] jsApiList={
				  "checkJsApi",
			      "onMenuShareTimeline",
			      "onMenuShareAppMessage",
			      "onMenuShareQQ",
			      "onMenuShareWeibo",
			      "hideMenuItems",
			      "showMenuItems",
			      "hideAllNonBaseMenuItem",
			      "showAllNonBaseMenuItem",
			      "translateVoice",
			      "startRecord",
			      "stopRecord",
			      "onRecordEnd",
			      "playVoice",
			      "pauseVoice",
			      "stopVoice",
			      "uploadVoice",
			      "downloadVoice",
			      "chooseImage",
			      "previewImage",
			      "uploadImage",
			      "downloadImage",
			      "getNetworkType",
			      "openLocation",
			      "getLocation",
			      "hideOptionMenu",
			      "showOptionMenu",
			      "closeWindow",
			      "scanQRCode",
			      "chooseWXPay",
			      "openProductSpecificView",
			      "addCard",
			      "chooseCard",
			      "openCard"
	  		      };
		return generateConfigJson(ticket,false,appid,url.toString(),jsApiList);
	}

    
    //通过OAuth2.0方式不弹出授权页面获得用户基本信息
    /*//原view请求url,如http://mascot.duapp.com/oauth2.php
    //构造请求url如下：https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx8888888888888888&redirect_uri=http://mascot.duapp.com/oauth2.php&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect
    public static String unConstructionUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECTURI&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
    //获取构造请求url
    public static String getConstructionUrl(String appid, String redirectUri, String responseType, String scope, String state, String wechatRedirect) {
    	String constructionUrl = unConstructionUrl.replaceAll("APPID", appid).replaceAll("REDIRECTURI", redirectUri);
    	if(wechatRedirect != null && !"".equals(wechatRedirect)) {
    		constructionUrl = constructionUrl.replaceAll("#wechat_redirect", wechatRedirect);
    	} else {
    		constructionUrl = constructionUrl.replaceAll("#wechat_redirect", "");
    	}
    	return constructionUrl;
    }*/
    
    //获取openId的微信接口的url
    public static String getOpenIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    //使用code获取OpenId
    public static String getOpenIdUrl(String appid, String secret, String code) {
    	String openIdUrl = "";
    	if(code != null && !"".equals(code)) {
    		openIdUrl = getOpenIdUrl.replaceAll("APPID", appid).replaceAll("SECRET", secret).replaceAll("CODE", code);
    	}
    	
    	return openIdUrl;
    }
    
    /**
     * 获取openId
     * 由于微信的服务器不稳定，请求的openid的时候可能一次请求不到，经测试，请求服务器会出现三种情况
     * 1.正常，得到的返回值为 {"access_token":"OezXcEiiBSKSxW0eoylIeFTQ8EzP2yrTFqsDNHRgFoueTT2jnDHfrr5MxRPbnpVNvwtxezQOD80BZMZc3mRoeHd35CM62rtYIC5xBsxmK2AMBwGWMIWz1DZg52VW_rYslNrvKBERHibXqEGCukAZPg","expires_in":7200,"refresh_token":"OezXcEiiBSKSxW0eoylIeFTQ8EzP2yrTFqsDNHRgFoueTT2jnDHfrr5MxRPbnpVNuLYutk4X7Apo_8Atvhto5ITVZaI_J-FLUXM8QxzHtEOTVH-AeZosneEm2hr821NSrcyHHuLwEQ2hVc0kD9ycSg","openid":"oOVL3sjq3ZvjvRRWnJ3A4bXEh3rY","scope":"snsapi_base"}
     * 2.微信服务器出错，返回值为{"errcode":-1,"errmsg":"req id: QlAF3a0750ns60, system error"}
     * 		这种情况再请求一次就可以得到正确的返回值
     * 3.其他错误，返回值为{"errcode":40029,"errmsg":"req id: s3rPWa0856ns26, invalid code"}
     * 		未知错误，应该是微信代码的问题，这里不做处理
     * @param appid
     * @param secret
     * @param code
     * @return
     */
    public static String getOpenId(String appid, String secret, String code) {
    	String openId = "";
    	String requestUrl = getOpenIdUrl(appid, secret, code);
    	System.out.println("getOpenIdUrl-------->"+requestUrl);
    	if(StringUtils.isNotEmpty(requestUrl)){
    		JSONObject jsonObject = null;
    		for(int i=0;i<10;i++){

    			LogUtil.info("请求openid： "+requestUrl);
    			jsonObject = httpRequest(requestUrl, "GET", null);
    			if(null==jsonObject)continue;
    			LogUtil.info("请求openid得到的返回值： "+jsonObject);
    			
    			String openIdPara=jsonObject.getString("openid");
    			if(StringUtils.isNotEmpty(openIdPara)){
    				openId=openIdPara;
    				break;
    			}
    			
    			String errcode=jsonObject.getString("errcode");
    			if(StringUtils.isNotEmpty(errcode) && !"-1".equals(errcode)){
    				break;
    			}
    		}
    	}
    	return openId;
    }
    /** 
     * 发起https请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { new MyX509TrustManager() };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.addRequestProperty("Connection", "keep-alive");
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            httpUrlConn.addRequestProperty("Host", "api.weixin.qq.com");
            
            
            httpUrlConn.setConnectTimeout(500000);// 设置连接超时时间，单位毫秒
//            httpUrlConn.setReadTimeout(50000);// 设置读取数据超时时间，单位毫秒
            
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();  
  
            // 当有数据需要提交时  
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());  
        } catch (ConnectException ce) {  
            log.error("Weixin server connection timed out.");  
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
        }  
        return jsonObject;  
    }  
}
