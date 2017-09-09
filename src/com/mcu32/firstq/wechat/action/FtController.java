package com.mcu32.firstq.wechat.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mcu32.firstq.common.bean.bo.PowerCutBO;
import com.mcu32.firstq.common.bean.bo.RobotPowOrgBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;

@Controller
@RequestMapping(value ="/F")

public class FtController extends BaseController{

	
	@RequestMapping(value="/T")
    public String forwardT(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		 
		 String t= new String(request.getParameter("o")).trim();
		
		 try {
			 
		    //查询shortId 对应的电力公司名称
			RobotPowOrgBO orgbo=new RobotPowOrgBO();
			orgbo.setShortId(t);
			RobotPowOrgBO robotPowOrgBO =FirstQInterfaces.getIRobotInfoService().getPowOrgInfo(orgbo);
            // 根据电力公司名称和当前时间查询停电
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			PowerCutBO powerCutBO=new PowerCutBO();
	    	String orgName=robotPowOrgBO.getCompanyName();
	    	String qtime=sdf.format(new Date());
	    	
	    	 powerCutBO.setCityname(orgName);
	    	 powerCutBO.setStartTime(qtime);
	    	 
	    	 List<PowerCutBO> powList;
			
			 powList = FirstQInterfaces.getIRobotInfoService().getPowerCutList(powerCutBO);
			
			   if(powList!=null){
				model.addAttribute("powList",powList);
			   }
			} catch (FirstQException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String resultString = "robot/powerCutDetail";
		
			return resultString;
		}
	 
	
}
