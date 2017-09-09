package com.mcu32.firstq.wechat.action.common;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.tools.TemplateMessages;
import com.mcu32.firstq.wechat.util.ToolUtil;


@Controller
@RequestMapping(value = "/systemUpdate")
public class SystemUpdate extends BaseController {
	@RequestMapping(value = "/main")
	public String main(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		return "commons/systemUpdateMain";
	}
	@RequestMapping(value = "/update")
	public String updateSystem(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) throws FirstQException {
		Map<String, String> map=getRealMapByReqeustMap(request);
		Map<String, String> dataMap=new HashMap<String,String>();
		dataMap.put("first", map.get("title").trim());
		dataMap.put("keyword1", map.get("content").trim());
		dataMap.put("keyword2", map.get("startTime").trim());
		dataMap.put("keyword3", map.get("endTime").trim());
		dataMap.put("remark", map.get("remark").trim());
		Map<String,String>rmap=new HashMap<String,String>();
		List<String> openIds=FirstQInterfaces.getIUserService().selectOpenIdList(rmap);
//		String openId="o50AjuC038KHEeNCH6Nz1T2g1RQo";
//		TemplateMessages.systemUpdate(openId, dataMap);
		TemplateMessages.systemUpdate(openIds, dataMap);
		return "";
	}
	
	@RequestMapping(value = "/updateFinished")
	public String updateSystemFinished(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) throws FirstQException {
		Map<String, String> map=getRealMapByReqeustMap(request);
		Map<String, String> dataMap=new HashMap<String,String>();
		dataMap.put("first", map.get("title").trim());
		dataMap.put("keyword1", map.get("content").trim());
		dataMap.put("keyword2", map.get("finishedTime").trim());
		dataMap.put("remark", map.get("remark").trim());
		System.out.println(map.get("content"));
		Map<String,String>rmap=new HashMap<String,String>();
		List<String> openIds=FirstQInterfaces.getIUserService().selectOpenIdList(rmap);
//		List<String> openIds=new ArrayList<String>();
//		openIds.add("o50AjuKKnBkMxb0o6fT20JssWYjw");
//		openIds.add("o50AjuC038KHEeNCH6Nz1T2g1RQo");
		TemplateMessages.systemUpdateFinished(openIds, dataMap);
		return "";
	}
	@ResponseBody
	@RequestMapping(value = "/exception")
	public String systemException(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) throws FirstQException {
		Map<String, String> map=getRealMapByReqeustMap(request);
		try {
			String keyword2=URLDecoder.decode(map.get("keyword2"),"UTF-8");
			map.put("keyword2", keyword2);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		String users=ToolUtil.getAppConfig("OffLineToUser");
		String [] userarr=users.split(",");
		List<String> openIds=new ArrayList<String>();
		Collections.addAll(openIds, userarr);
		if(null!=openIds && openIds.size()>0)
			TemplateMessages.systemException(openIds,map);
		JSONObject json= new JSONObject();
		json.put("msg", "ok");
		return json.toString();
	}
}
