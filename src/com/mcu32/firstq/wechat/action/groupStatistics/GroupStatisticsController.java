package com.mcu32.firstq.wechat.action.groupStatistics;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcu32.firstq.common.bean.bo.RobotInfoBO;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;

@Controller
@RequestMapping(value = "/groupStatistics")
public class GroupStatisticsController extends BaseController{
	/**
	 * 跳转到按天的列表页面
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String daysReportList(HttpServletRequest request,ModelMap model){
		return "groupStatistics/list";
	}
	
	/**
	 * 获取按天/周统计数据
	 */
	@RequestMapping(value="/getData",method=RequestMethod.POST)
	public String getData(HttpServletRequest request,ModelMap model){
		Map<String,String> map =getRealMapByReqeustMap(request);
		
		List<RobotInfoBO> groupNum=FirstQInterfaces.getIRobotReportService().getGroupNum(map);
		List<RobotInfoBO> userNum=FirstQInterfaces.getIRobotReportService().getUserNum(map);
		List<RobotInfoBO> demandNum=FirstQInterfaces.getIRobotReportService().getDemandNum(map);
		List<RobotInfoBO> userNumByOneGroup=FirstQInterfaces.getIRobotReportService().getUserNumByOneGroup(map);
		List<RobotInfoBO> demandNumByOneGroup=FirstQInterfaces.getIRobotReportService().getDemandNumByOneGroup(map);
		
		model.addAttribute("groupHits", groupNum);
		model.addAttribute("userHits", userNum);
		model.addAttribute("instructionsHits", demandNum);
		model.addAttribute("groupUserHits", userNumByOneGroup);
		model.addAttribute("groupInstructionsHits", demandNumByOneGroup);
		return "";
	}
	
}
