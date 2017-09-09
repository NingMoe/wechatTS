package com.mcu32.firstq.wechat.action.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mcu32.firstq.common.bean.bo.UserBO;
import com.mcu32.firstq.common.util.CommonUtil;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.SessionConstants;

@Controller
@RequestMapping(value = "/register")
public class RegisterController  extends BaseController{
	@RequestMapping(value = "/gotoRegister")
    public String gotoRegister(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model) throws IOException{
		String returnString="";
		String openId = request.getParameter("openId");
		if(openId != null && !"".equals(openId)) {
			request.setAttribute("openId", openId);
			String isLogin = checkLoginAndSetUserToSession(openId, request, session);
			if("OK".equals(isLogin)) {
				return  "redirect:/userInfo/getUserInfo?openId=" + openId; 
			} else if("FAIL".equals(isLogin)) {
				returnString="user/register";
			} else {
				model.addAttribute("msg", isLogin);
				returnString = "commons/errorpage";
			}
		} else {
			model.addAttribute("msg", "未获取到openId");
			returnString = "commons/errorpage";
		}
		model.addAttribute("openId", openId);
		//如果是经这个action进来，跳转到注册页面，那肯定为微信消息框，点击注册链接进来的，当注册成功后，直接跳转到用户信息页面
		model.addAttribute("ForwardJsp", "userInfo/getUserInfo");
		return returnString;
    }
	@RequestMapping(value = "/register")
    public String doRegister(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model) throws IOException{
		String returnString="user/register";
		//获取微信用户openId,以及前台传递的参数，调用后台微信注册接口，如果注册返回空，则注册失败，返回FAIL，成功，返回OK
		String openId = request.getParameter("openId");//微信用户唯一标识
		String ForwardJsp = request.getParameter("ForwardJsp");//注册成功后，需要转向的页面
		String toPageUrl=request.getParameter("toPageUrl");
		try {
			String user_name = request.getParameter("userName");//真实姓名
			String phone_number = request.getParameter("phoneNumber");//电话号码
			String deptId = request.getParameter("deptId");//所属公司
			String province = request.getParameter("province");//所在省
			String city = request.getParameter("city");//所在市
			String county = request.getParameter("county");//所在县
			String regiPwd = request.getParameter("regiPwd");//注册密码
			String roleName = request.getParameter("roleName");//角色名称
			Date date = new Date();
			//获取到微信用户唯一标识
			if(openId != null && !"".equals(openId)) {
				UserBO ro = new UserBO();
				ro.setUserId(CommonUtil.getUUID());
				ro.setUserName(user_name);
				ro.setRoleName(roleName);
				ro.setPhoneNo(phone_number);
				ro.setWeChatEnterprise("");
				ro.setWeChatService(openId);
				ro.setCreateTime(date);
				ro.setLastClickDate(date);
				ro.setDeptId(deptId);
				ro.setProvinceId(province);
				ro.setCityId(city);
				ro.setCountyId(county);
				String token = FirstQInterfaces.getIUserService().addRegister(ro);
		        if(token != null && !"".equals(token)) {
					UserInfo user = new UserInfo(token,openId,null,null);
					user.setUserId(token);
					user.setUserName(user_name);
					user.setPhoneNo(phone_number);
					user.setDeptId(deptId);
					user.setProvinceId(province);
					user.setCityId(city);
					user.setCountyId(county);
					user.setRoleName(roleName);
					user.setPwd(regiPwd);
					user.setToken(token);
					session.setAttribute(SessionConstants.SUSER, user);
					session.setAttribute(SessionConstants.OPENID, openId);
		        	if(ForwardJsp != null && !"".equals(ForwardJsp)) {
		        		if(StringUtils.isNotEmpty(toPageUrl)){
		        			
		        			LogUtil.info("页面过来的url -------->" +toPageUrl);
		        			try {
		        				toPageUrl = URLDecoder.decode(toPageUrl, "UTF-8");
		        			} catch (UnsupportedEncodingException e) {
		        				e.printStackTrace();
		        			}
		        			LogUtil.info("页面过来的url解码 -------->" +toPageUrl);
		        			toPageUrl = FirstqTool.dealParmaEncode(toPageUrl);
		        			LogUtil.info("处理完成之后的url -------->" + toPageUrl);
		        			
		        			return  "redirect:/"+ ForwardJsp +"?openId=" + openId+"&toPageUrl="+toPageUrl; 
		        		}
		        		return  "redirect:/"+ ForwardJsp +"?openId=" + openId; 
					}
		        } else {
		        	model.addAttribute("Msg", "注册失败,请重新注册");
		        	model.addAttribute("openId", openId);
		        	returnString = "user/register";//注册失败，不刷新页面，注册页面提示失败信息
		        }
			} else {
				model.addAttribute("msg", "未获取到openId");
				returnString = "commons/errorpage";
			}
		} catch (Exception e) {
			LogUtil.info("注册失败" + e.getMessage());
			model.addAttribute("Msg", "注册失败,请重新注册");
			model.addAttribute("openId", openId);
        	returnString = "user/register";//注册失败，不刷新页面，注册页面提示失败信息
		}
		return returnString;
    }
}
