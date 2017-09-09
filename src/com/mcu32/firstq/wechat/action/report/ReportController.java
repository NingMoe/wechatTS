package com.mcu32.firstq.wechat.action.report;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcu32.firstq.common.bean.bo.StationShareInfo;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.interceptor.AccessRequired;
import com.mcu32.firstq.wechat.util.SessionConstants;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/report")
public class ReportController extends BaseController{
	/**http://left.wicp.net/wechat/report/daysReportList
	 * 跳转到按天的日报列表页面
	 */
	@RequestMapping(value="/daysReportList",method=RequestMethod.GET)
	public String daysReportList(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		List<StationShareInfo> stationInfos = FirstQInterfaces.getIReportFormsService().getGenerateTimeWeekInfo(user.getCityId());
		
		model.addAttribute("daysReportList", stationInfos);
		return "report/daysReportList";
	}
	/**
	 * 跳转到某一天的日报页面
	 */
	@RequestMapping(value="/toDaysReport",method=RequestMethod.GET)
	public String toDaysReport(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String day=request.getParameter("day");
		String timeLength=request.getParameter("timeLength");
		String num=request.getParameter("num");
		String stationNum=request.getParameter("stationNum");
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		List<StationShareInfo> list=FirstQInterfaces.getIReportFormsService().getGenerateReportByCityId(user.getCityId(), day);

		JSONArray jsonArray = JSONArray.fromObject(list);
		System.out.println(jsonArray.toString());
		model.addAttribute("generateTimeByAreaData",list);
		model.addAttribute("day", day);
		model.addAttribute("timeLength", timeLength);
		model.addAttribute("userInfo", user);
		model.addAttribute("num", num);
		model.addAttribute("stationNum", stationNum);
		return "report/daysReport";
	}
	/**
	 * 获取日报数据
	 */
	@RequestMapping(value="/getDaysReportData",method=RequestMethod.POST)
	public String getDaysReportData(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String day=request.getParameter("day");
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		List<List<String>> list=FirstQInterfaces.getIReportFormsService().getGenerateTimeByCityId(user.getCityId(), day);
		model.addAttribute("generateTimeByAreaData",list);
		return "";
	}
	/**
	 * 获取代维公司发电日报数据
	 */
	@RequestMapping(value="/getDeptDayReportData",method=RequestMethod.POST)
	public String getDeptDayReportData(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String day=request.getParameter("day");
		List<List<String>> list=FirstQInterfaces.getIReportFormsService().getGenerateInfoByDeptId("唐山市", day);
		model.addAttribute("generateInfoByDeptDayData",list);
		return "";
	}
	
	/**
	 * 周报列表
	 */
	@RequestMapping(value="/weekReportList",method=RequestMethod.GET)
	public String weekReportList(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		List<StationShareInfo> stationInfos = FirstQInterfaces.getIReportFormsService().getGenerateTimeWeekInfo(user.getCityId());
		model.addAttribute("weekReportList", stationInfos);
		return "report/weekReportList";
	}
	/**
	 * 跳转到某一周的周报页面
	 */
	@RequestMapping(value="/toWeekReport",method=RequestMethod.GET)
	public String toWeekReport(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		String week=request.getParameter("week");
		String timeLength=request.getParameter("timeLength");
		String num=request.getParameter("num");
		String stationNum=request.getParameter("stationNum");
		List<StationShareInfo> list=FirstQInterfaces.getIReportFormsService().getGenerateReportByCityId(user.getCityId(), week);
		model.addAttribute("generateTimeByAreaData",list);
		model.addAttribute("week", week);
		model.addAttribute("timeLength", timeLength);
		model.addAttribute("userInfo", user);
		model.addAttribute("num", num);
		model.addAttribute("stationNum", stationNum);
		return "report/weekReport";
	}
	/**
	 * 获取周报数据
	 */
	@AccessRequired(interceptURL=true)
	@RequestMapping(value="/getWeekReportData",method=RequestMethod.POST)
	public String getWeekReportData(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String day=request.getParameter("day");
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		List<List<String>> list=FirstQInterfaces.getIReportFormsService().getGenerateTimeByCityId(user.getCityId(), day);
		model.addAttribute("generateTimeByAreaData",list);
		return "";
	}
	/**
	 * 获取代维公司发电周报数据
	 */
	@RequestMapping(value="/getDeptWeekReportData",method=RequestMethod.POST)
	public String getDeptWeekReportData(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String day=request.getParameter("day");
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		List<List<String>> list=FirstQInterfaces.getIReportFormsService().getGenerateInfoByDeptId(user.getCityId(), day);
		model.addAttribute("generateInfoByDeptWeekData",list);
		return "";
	}
	
}
