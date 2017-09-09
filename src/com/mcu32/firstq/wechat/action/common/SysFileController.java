package com.mcu32.firstq.wechat.action.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;

@Controller
@RequestMapping(value="/sysFile")
public class SysFileController extends BaseController{
	
	@RequestMapping(value = "/toSysFileUpload")
	public String toSysFileUpload(HttpServletRequest request,HttpSession session,ModelMap model){
		return "commons/sysFileUpload";
	}
	
	@RequestMapping(value = "/addFile")
	public String notice(HttpServletRequest request,HttpSession session,ModelMap model){
		FirstQInterfaces.getSysFileService();
		return "";
	}
}