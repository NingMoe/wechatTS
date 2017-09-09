package com.mcu32.firstq.wechat.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mcu32.firstq.wechat.tools.MyJsUtil;
import com.mcu32.firstq.wechat.util.ToolUtil;
import com.mcu32.firstq.wechat.util.date.DateUtil;


@Controller
@RequestMapping(value = "/")
public class IndexController extends BaseController{

	@ResponseBody
    @RequestMapping(value = "/logs")
    public void getLogs(HttpServletResponse response) throws IOException{
		response.setHeader("Connection", "close");
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		
		//reader流，可以一次读取一行文字
		BufferedReader bufferedReader = new BufferedReader(
											new InputStreamReader(
												ToolUtil.openLogStream(),"UTF-8"
											)
										);
		//Writer流，
		BufferedWriter bufferedWriter=new BufferedWriter(
										new OutputStreamWriter(
											response.getOutputStream(),"UTF-8"
										)
									);
		
		String bufStr="";
		 while ((bufStr = bufferedReader.readLine()) != null) {
			 bufferedWriter.write(bufStr);  
             //newLine()方法写入与操作系统相依的换行字符，依执行环境当时的OS来决定该输出那种换行字符  
			 bufferedWriter.newLine();
		 }
		 bufferedWriter.close();
		 bufferedReader.close();
		 
    }
	
	@ResponseBody
    @RequestMapping(value = "/clearfile")
    public String clearFile(HttpServletResponse response){
		response.setHeader("Connection", "close");
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		try {
			ToolUtil.clearFile();
		} catch (Exception e) {
			return e.getMessage();
		}
		return "log has been cleard at "+DateUtil.DateToString(new Date(),"yyyy-MM-dd HH:mm:ss:SSS");
    }
	
	/**
	 * 测试微信js
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/testwechatjs")
    public String getStation(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model) throws IOException{
		String returnString="commons/testwechatjs";
		
		/**↓↓ ↓↓ ↓↓ 拼写微信js需要的config参数 start ↓↓ ↓↓ ↓↓ */
		String config=MyJsUtil.generateConfigJsonDefault(request,false);
		model.addAttribute("jsConfig", config);
		/**↑↑ ↑↑ ↑↑ 拼写微信js需要的config参数 end   ↑↑ ↑↑ ↑↑*/
		
		return returnString;
    }
	
	/**初始化配置文件*/
	@ResponseBody
	@RequestMapping(value="/initConfig")
	public String initConfig(){
		//Tool.initAppConfig();
		
		ToolUtil.initProperties();
		JSONObject json=new JSONObject();
		json.put("succ", true);
		json.put("msg", "初始化成功");
		return json.toJSONString();
	}

}
