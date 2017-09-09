package com.mcu32.firstq.wechat.action.agentcost;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcu32.firstq.common.bean.bo.KPMaintenanceCostBO;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.util.SessionConstants;

@Controller
@RequestMapping(value = "/maintenancecost")
public class KPMaintenanceCostController extends BaseController{
	@RequestMapping(value = "/goCost",method = RequestMethod.GET)
	public String readyStart(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		Date fillDate=new Date();
		String selectId=request.getParameter("selectId");
		if(selectId != "" && selectId != null){
			model.addAttribute("selectId", selectId);
		}
		Map<String,String> map=new HashMap<String,String>();
		map.put("provinceid", user.getProvinceId());
		List<String> costList=FirstQInterfaces.getIKPMaintenanceCostService().selectList(map);
		model.addAttribute("costList", costList);
		model.addAttribute("userInfo", user);
		model.addAttribute("fillDate", fillDate);
		return "agentCost/agentCostMain";
	}
	@RequestMapping(value = "/getCostDetail",method = RequestMethod.GET)
	public String getCostDetail(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		String fillMonth=request.getParameter("fillMonth");
		Map<String,String> map=new HashMap<String,String>();
		map.put("fillMonth", fillMonth);
		map.put("provinceid", user.getProvinceId());
		List<KPMaintenanceCostBO>  costByMonthList=FirstQInterfaces.getIKPMaintenanceCostService().selectMonthList(map);
		model.addAttribute("costByMonthList", costByMonthList);
		model.addAttribute("fillMonth", fillMonth);
		model.addAttribute("userInfo", user);
		return "agentCost/agentCostDetail";
	}
	
	@RequestMapping(value = "/saveMaintenanceCostInfo",method = RequestMethod.POST)
	public String saveMaintenanceCostInfo(KPMaintenanceCostBO costBO, HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		//String maintenanceCostId=request.getParameter("maintenanceCostId");
		costBO.setFillMan(user.getUserName());
		//if(maintenanceCostId == null || maintenanceCostId ==""){
			Map<String,String> map=new HashMap<String,String>();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
			String fillMonth=sdf.format(costBO.getFillMonth());
			map.put("fillMonth", fillMonth);
			map.put("provinceid", user.getProvinceId());
			map.put("cityid", user.getCityId());
			KPMaintenanceCostBO maintenanceCostBO=FirstQInterfaces.getIKPMaintenanceCostService().selectByProvinceAndCity(map);
			if(maintenanceCostBO == null){
				costBO.setMaintenanceCostId(getUUID());
				costBO.setProvinceid(user.getProvinceId());
				costBO.setCityid(user.getCityId());
				FirstQInterfaces.getIKPMaintenanceCostService().insertSelective(costBO);
			}else{
				costBO.setMaintenanceCostId(maintenanceCostBO.getMaintenanceCostId());
				FirstQInterfaces.getIKPMaintenanceCostService().updateByPrimaryKeySelective(costBO);
			}
			
		//}else{
			//costBO.setMaintenanceCostId(maintenanceCostId);
			//FirstQInterfaces.getIKPMaintenanceCostService().updateByPrimaryKeySelective(costBO);
		//}
		model.addAttribute("selectId", 2);
		model.addAttribute("userInfo", user);
		return "agentCost/agentCostMain";
	}
	@RequestMapping(value = "/updateCostRecord")
	public String updateCostRecord(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		String fillMonth=request.getParameter("fillMonth");
		Map<String,String> map=new HashMap<String,String>();
		map.put("fillMonth", fillMonth);
		map.put("provinceid", user.getProvinceId());
		map.put("cityid", user.getCityId());
		String selectId=request.getParameter("selectId");
		if(selectId != "" && selectId != null){
			model.addAttribute("selectId", selectId);
		}
		KPMaintenanceCostBO maintenanceCostBO=FirstQInterfaces.getIKPMaintenanceCostService().selectByProvinceAndCity(map);
		List<String> costList=FirstQInterfaces.getIKPMaintenanceCostService().selectList(map);
		model.addAttribute("maintenanceCostBO", maintenanceCostBO);
		model.addAttribute("fillDate", new Date());
		model.addAttribute("costList", costList);
		model.addAttribute("userInfo", user);
		return "agentCost/agentCostMain";
	}
	@RequestMapping(value = "/getRecordMsg")
	public String getRecordMsg(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		String fillMonth=request.getParameter("fillMonth");
		Map<String,String> map=new HashMap<String,String>();
		map.put("fillMonth", fillMonth);
		map.put("provinceid", user.getProvinceId());
		map.put("cityid", user.getCityId());
		KPMaintenanceCostBO maintenanceCostBO=FirstQInterfaces.getIKPMaintenanceCostService().selectByProvinceAndCity(map);
		model.addAttribute("maintenanceCostBO", maintenanceCostBO);
		return "";
	}
}
