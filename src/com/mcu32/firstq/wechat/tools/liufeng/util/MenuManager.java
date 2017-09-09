package com.mcu32.firstq.wechat.tools.liufeng.util;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mcu32.firstq.wechat.tools.liufeng.bean.AccessToken;
import com.mcu32.firstq.wechat.util.ToolUtil;
  
/** 
 * 菜单管理器类 
 *  
 * @author liufeng 
 * @date 2013-08-08 
 */  
public class MenuManager {  
	
    private static Logger log = LoggerFactory.getLogger(MenuManager.class);  
  
    public static void main(String[] args) throws UnsupportedEncodingException {  
        
    	/*请检查 app.properties 中的以下三个参数是否正确配置，不要使用其他人的配置信息*/
        String appId = ToolUtil.getAppConfig("dyxxAPPID");  // 第三方用户唯一凭证  
        String appSecret = ToolUtil.getAppConfig("dyxxAPPSECRET");// 第三方用户唯一凭证密钥  
        String callBackDomain=ToolUtil.getAppConfig("CallBackDomain");//项目的域名
        String wechatProjectName=ToolUtil.getAppConfig("WechatProjectName");//项目的域名
        
        // 调用接口获取access_token  
        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);  
  
        if (null != at) {
            // 调用接口创建菜单  
            //int result = WeixinUtil.createMenu(getMenu(), at.getToken());  
            int result = WeixinUtil.createMenu(getMenuString(appId,callBackDomain,wechatProjectName), at.getToken());  
  
            // 判断菜单创建结果  
            if (0 == result)
                log.info("菜单创建成功！");  
            else  
                log.info("菜单创建失败，错误码：" + result);  
        }
    }
    /**
     * 字符串拼菜单
     * 菜单json在 readme.txt
     * json压缩转译工具
     * http://www.bejson.com/go.html?u=http://www.bejson.com/zhuanyi.html
     * @return
     * @throws UnsupportedEncodingException 
     */
    private static String getMenuString(String appId,String callBackDomain,String wechatProjectName) throws UnsupportedEncodingException{
    	String menustr="{"+
						    "\"button\": [    "+
						        "{    "+
						            "\"name\": \"维护助手\",    "+
						            "\"type\": \"view\",    "+
						            "\"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri=http://"+callBackDomain+"/"+wechatProjectName+"/main/toStart.html&response_type=code&scope=snsapi_base&state=1\"    "+
						        "},    "+
						        "{    "+
						            "\"name\": \"服务支持\",    "+
						            "\"sub_button\": [    "+
						                "{    "+
						                    "\"type\": \"view\",    "+
						                    "\"name\": \"专家答疑\",    "+
						                    "\"url\": \"http://"+callBackDomain+"/"+wechatProjectName+"/assets/gxui/functionNotOpenImg.jsp?msg=2\"    "+
						                "},    "+
						                "{    "+
						                    "\"type\": \"view\",    "+
						                    "\"name\": \"知识圈\",    "+
						                    "\"url\": \"http://"+callBackDomain+"/"+wechatProjectName+"/assets/gxui/functionNotOpenImg.jsp?msg=1\"    "+
					                    "},    "+
						                "{    "+
						                    "\"type\": \"view\",    "+
						                    "\"name\": \"优化建议\",    "+
						                    "\"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri=http://"+callBackDomain+"/"+wechatProjectName+"/main/toPageNotLocation?toPageUrl=question/questionMain&response_type=code&scope=snsapi_base&state=1\"    "+
					                    "},    "+
						                "{    "+
						                    "\"type\": \"view\",    "+
						                    "\"name\": \"利旧放电\",    "+
						                    "\"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri=http://"+callBackDomain+"/"+wechatProjectName+"/main/toPageNotLocation?toPageUrl=hldischarge/goStart&response_type=code&scope=snsapi_base&state=1\"    "+
									    "}    "+
						            "]    "+
						        "},    "+
						        "{    "+
						            "\"name\": \"个人中心\",    "+
						            "\"type\": \"view\",    "+
						            "\"url\": \"https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri=http://"+callBackDomain+"/"+wechatProjectName+"/userInfo/getUserInfo.html&response_type=code&scope=snsapi_base&state=1\"    "+
						        "}    "+
						    "]    "+
						"}";
    	return menustr;
    }
}  