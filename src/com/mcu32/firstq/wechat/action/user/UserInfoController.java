package com.mcu32.firstq.wechat.action.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.util.SessionConstants;
@Controller
@RequestMapping(value = "/userInfo")
public class UserInfoController  extends BaseController{
	@RequestMapping(value = "/getUserInfo",method=RequestMethod.GET)
	public String getUserInfo(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model) throws IOException{
		// TODO
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String returnString = "";
		//获取openId
		String openId = getOpenId(request);
		//为了测试，需要接收测试中传来的state的值，如果state为2，则为测试号的openId
		if(openId != null && !"".equals(openId)) {
			//根据openId验证该用户是否登录，如果登录，则执行下面的逻辑；如果没有登录，则调用获取登录令牌接口，返回的token不为空，说明登录成功，执行下面的逻辑，返回
			//的token为空，说明该微信用户还未注册，跳转到注册页面
			String checkLogin = checkLoginAndSetUserToSession(openId, request, session);
			if("OK".equals(checkLogin)) {
				UserInfo usInfo = session.getAttribute(SessionConstants.SUSER) != null ? (UserInfo)session.getAttribute(SessionConstants.SUSER) : null;
				model.addAttribute("userInfo", usInfo);
				returnString="user/userInfo";
				return returnString;
			} else if("FAIL".equals(checkLogin)) {
				model.addAttribute("openId", openId);
				//向注册页面传递，当注册成功后，返回的页面
				model.addAttribute("ForwardJsp", "userInfo/getUserInfo");
				returnString = "user/register"; 
			} else {
				model.addAttribute("msg", checkLogin);
				returnString = "commons/errorpage";
			}
		} else {
			model.addAttribute("msg", "未获取到openId");
			returnString = "commons/errorpage";
		}
		return returnString;
    }
}
