package com.mcu32.firstq.wechat.action.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcu32.firstq.common.bean.bo.ManageBatteryRecordBO;
import com.mcu32.firstq.common.bean.bo.ManageBatteryRecordScrapBO;
import com.mcu32.firstq.common.bean.bo.OrgBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.interceptor.AccessRequired;
import com.mcu32.firstq.wechat.service.IManageBatteryRecordSendService;
import com.mcu32.firstq.wechat.tools.TemplateMessages;
import com.mcu32.firstq.wechat.util.SessionConstants;
import com.mcu32.firstq.wechat.util.date.DateUtil;

@Controller
@RequestMapping(value = "/report")
public class ManageBatteryRecordControll  extends BaseController{
	@Autowired private IManageBatteryRecordSendService bmrsService;
	/**
	 * 市角色显示市表中本市最新
	 * 省角色显示省表中最新
	 */
	@AccessRequired(needLogin=true)
	@RequestMapping(value="/manageBatteryRecordByProvince",method=RequestMethod.GET)
	public String manageBatteryRecordByProvince(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		setWechatConfig(request,model);//拼写微信js需要的config参数
		UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		String privinceid=ui.getProvinceId();
		String cityid=ui.getCityId();
		String source = getUserSource(ui);
		
		HashMap<String, String> qryMap=new HashMap<String,String>();
		qryMap.put("provinceid",privinceid);
		
		ManageBatteryRecordBO manageBatteryRecord=new ManageBatteryRecordBO();
		ManageBatteryRecordBO manageBatteryRecordpre=new ManageBatteryRecordBO();
		ManageBatteryRecordScrapBO manageBatteryRecordScrap=new ManageBatteryRecordScrapBO();
		ManageBatteryRecordScrapBO manageBatteryRecordScrapPre=new ManageBatteryRecordScrapBO();
		
		try {
			if("province".equals(source)){
				qryMap.put("week", "thisweek");
				List<ManageBatteryRecordBO> list=FirstQInterfaces.getIManageBatteryRecordService().manageBatteryRecordlist_J(qryMap);
				if(null!=list && list.size()>0)
					manageBatteryRecord=list.get(0);
				
				qryMap.put("week", "preweek");
				List<ManageBatteryRecordBO> listpre=FirstQInterfaces.getIManageBatteryRecordService().manageBatteryRecordlist_J(qryMap);
				if(null!=listpre && listpre.size()>0)
					manageBatteryRecordpre=listpre.get(0);
			}else{
				qryMap.put("cityid", cityid);
				qryMap.put("week", "thisweek");
				List<ManageBatteryRecordBO> list = FirstQInterfaces.getIManageBatteryRecordService().findbyprovince(qryMap);
				if(null!=list && list.size()>0)
					manageBatteryRecord=list.get(0);
				
				qryMap.put("week", "preweek");
				List<ManageBatteryRecordBO> listpre= FirstQInterfaces.getIManageBatteryRecordService().findbyprovince(qryMap);
				if(null!=listpre && listpre.size()>0)
					manageBatteryRecordpre=listpre.get(0);
			}
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		
		Map<String,String> scrapMap = new HashMap<String,String>();
		scrapMap.put("provinceid",privinceid);
		scrapMap.put("cityid", cityid);
		List<ManageBatteryRecordScrapBO> scrapList=FirstQInterfaces.getIManageBatteryRecordService().selectManageBatteryRecordScrapList(scrapMap);
		if(null!=scrapList&& scrapList.size()>0){
			manageBatteryRecordScrap=scrapList.get(0);
		}
		scrapMap.put("week", "preweek");
		List<ManageBatteryRecordScrapBO> scrapPreList=FirstQInterfaces.getIManageBatteryRecordService().selectManageBatteryRecordScrapList(scrapMap);
		if(null!=scrapPreList&& scrapPreList.size()>0){
			manageBatteryRecordScrapPre=scrapPreList.get(0);
		}
		boolean needCityTotal=bmrsService.needCityTotal(privinceid);
		boolean canFill="province".equals(source)||(needCityTotal&&("city".equals(source))) || "group".equals(source);
		model.addAttribute("canFill", canFill);
		model.addAttribute("dontNeedCityTotal", !needCityTotal);
		model.addAttribute("source", source);
		model.addAttribute("manageBatteryRecord", manageBatteryRecord);
		model.addAttribute("manageBatteryRecordpre", manageBatteryRecordpre);
		model.addAttribute("manageBatteryRecordScrap", manageBatteryRecordScrap);
		model.addAttribute("manageBatteryRecordScrapPre", manageBatteryRecordScrapPre);
		model.addAttribute("dateString", DateUtil.getCurrentMonday("yyyy年MM月dd日")+"至"+DateUtil.getPreviousSunday("yyyy年MM月dd日"));
		model.addAttribute("selectId", "group".equals(source)?"3":request.getParameter("selectId"));
		return "report/manageBatteryReport";
	}

	@AccessRequired(needLogin=true)
	@RequestMapping(value="/addManageBatteryRecord",method=RequestMethod.POST)
	public String addManageBatteryRecord(ManageBatteryRecordScrapBO mScrapBO,ManageBatteryRecordBO mBO,HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		String source=request.getParameter("source");
		//从session中取用户的信息
		UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		String privinceid=ui.getProvinceId();
		String cityid=ui.getCityId();
		String fillMan=ui.getUserName();

		mBO.setId(UUID.randomUUID().toString());
		mBO.setProvinceid(privinceid);
		mBO.setCityid(cityid);
		mBO.setFillMan(fillMan);
		mBO.setFillTime(DateUtil.DateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
		
		mScrapBO.setId(UUID.randomUUID().toString());
		mScrapBO.setProvinceid(privinceid);
		mScrapBO.setCityid(cityid);
		mScrapBO.setFillMan(fillMan);
		mScrapBO.setFillTime(new Date());
		mScrapBO.setMark(request.getParameter("scrapMark"));
		
		boolean provinceAllFill=false;
		boolean cityprovinceAllFill=false;
		try {
			Map<String,String> qryMap=new HashMap<String,String>();
			qryMap.put("week", "thisweek");
			if("province".equals(source)){
				List<ManageBatteryRecordBO>  mbProvincePreweekWeekList= FirstQInterfaces.getIManageBatteryRecordService().manageBatteryRecordlist_J(qryMap);
				if(mbProvincePreweekWeekList.size()==31){
					provinceAllFill=true;
				}
				FirstQInterfaces.getIManageBatteryRecordService().insertRecordSSelective(mBO,ui.getProvinceId(),ui.getUserName(),bmrsService.needCityTotal(ui.getProvinceId()));
			}else if("city".equals(source)){
				qryMap.put("provinceid", privinceid);
				List<ManageBatteryRecordBO> list= FirstQInterfaces.getIManageBatteryRecordService().selectManageBatteryRecord_City(qryMap);
				List<OrgBO> listob=FirstQInterfaces.getIOrganizationService().getByCityNameSons(privinceid);
				if(list.size()==listob.size()){
					cityprovinceAllFill=true;
				}
				FirstQInterfaces.getIManageBatteryRecordService().addManageBatteryRecord(mBO);
			}
			
			if("黑龙江省".equals(privinceid)){
				FirstQInterfaces.getIManageBatteryRecordService().addManageBatteryRecordScrap(mScrapBO);
			}
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		
		String source1 = getUserSource(ui);
		String area="city".equals(source1)?privinceid+cityid:privinceid;
		List<String> openIdList=getSendOpenIdList(source1,ui);
		Map<String, String> dataMap=new HashMap<String,String>();
		dataMap.put("first", "[蓄电池整治填报通知]");
		dataMap.put("keyword1", DateUtil.DateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
		dataMap.put("keyword2", area+"已进行蓄电池整治进度填报\\n填报人："+fillMan);
		dataMap.put("remark", "");
		TemplateMessages.send(openIdList, dataMap);//发送模版消息
		
		if(!provinceAllFill)
			bmrsService.sendLastFillMsg();
		if(!cityprovinceAllFill)
			bmrsService.sendCityLastFillMsg(privinceid);//本地市的
		
		
		model.addAttribute("source", source);
		model.addAttribute("selectId", request.getParameter("selectId"));
		return "redirect:/report/manageBatteryRecordByProvince";
	}
	
	//TODO 展示所有市的信息和省的汇总
	@RequestMapping(value="/manageBatteryRecordProvinceList",method=RequestMethod.GET)
	public String manageBatteryRecordProvinceList(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		HashMap<String, Object> qryMap=new HashMap<String,Object>();
		qryMap.put("provinceid",ui.getProvinceId());
		qryMap.put("week", "thisweek");
		List<ManageBatteryRecordBO> manageBatteryRecordProvinceList =null;
		try {
			manageBatteryRecordProvinceList = FirstQInterfaces.getIManageBatteryRecordService().manageBatteryRecordProvinceList(qryMap);
			
			boolean needCityTotal=bmrsService.needCityTotal(ui.getProvinceId());
			if(null!=manageBatteryRecordProvinceList&&manageBatteryRecordProvinceList.size()>1&&!needCityTotal){
				manageBatteryRecordProvinceList=manageBatteryRecordProvinceList.subList(manageBatteryRecordProvinceList.size()-1, manageBatteryRecordProvinceList.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("manageBatteryRecordProvinceList", manageBatteryRecordProvinceList);
		return "";
	}
	
	@RequestMapping(value="/manageBatteryRecordGroupList",method=RequestMethod.GET)
	public String manageBatteryRecordGroupList(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		String source=getUserSource(ui);
		String privinceid=ui.getProvinceId();
		List<ManageBatteryRecordBO> manageBatteryRecordGroupList =null;
		try {
			HashMap<String, String> qryMap=new HashMap<String,String>();
			
			if("city".equals(source))
				qryMap.put("provinceid", privinceid);
			
			qryMap.put("week", "thisweek");
			manageBatteryRecordGroupList = FirstQInterfaces.getIManageBatteryRecordService().manageBatteryRecordlist_J(qryMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("manageBatteryRecordGroupList", manageBatteryRecordGroupList);
		return "";
	}
	
	@RequestMapping(value="/addManageBatteryRecord_J",method=RequestMethod.POST)
	public String addManageBatteryRecord_J(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		String privinceid=ui.getProvinceId();
		//String cityid=ui.getCityId();
		String fillMan=ui.getUserName();
		
		Map<String,String> m=new HashMap<String,String>();
		m.put("provinceid", privinceid);
		m.put("fillMan", ui.getUserName());
		
		String source = getUserSource(ui);
		boolean provinceAllFill=false;
		Map<String,String> qryMap=new HashMap<String,String>();
		qryMap.put("week", "thisweek");
		try {
			List<ManageBatteryRecordBO>  mbProvincePreweekWeekList= FirstQInterfaces.getIManageBatteryRecordService().manageBatteryRecordlist_J(qryMap);
			if(mbProvincePreweekWeekList.size()==31){
				provinceAllFill=true;
			}
			
			int ai=FirstQInterfaces.getIManageBatteryRecordService().addManageBatteryRecord_J(m);
			if(1==ai){
				List<String> openIdList=getSendOpenIdList(source,ui);
				Map<String, String> dataMap=new HashMap<String,String>();
				dataMap.put("first", "[蓄电池整治填报通知]");
				dataMap.put("keyword1", DateUtil.DateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
				dataMap.put("keyword2", privinceid+"已进行蓄电池整治进度填报\\n填报人："+fillMan);
				dataMap.put("remark", "");
				
				TemplateMessages.send(openIdList, dataMap);//发送模版消息
				
				if(!provinceAllFill)
					bmrsService.sendLastFillMsg();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("source", source);
			model.addAttribute("succ", false);
		}
		model.addAttribute("source", source);
		model.addAttribute("succ", true);
		return "";
	}
	
	@RequestMapping(value="/manageBatteryRecordScrapList",method=RequestMethod.GET)
	public String manageBatteryRecordScrapList(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		HashMap<String, String> qryMap=new HashMap<String,String>();
		qryMap.put("week", "thisweek");
		qryMap.put("provinceid",ui.getProvinceId());
		List<ManageBatteryRecordScrapBO> manageBatteryRecordScrapList =null;
		try {
			manageBatteryRecordScrapList = FirstQInterfaces.getIManageBatteryRecordService().selectManageBatteryRecordScrapList(qryMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("manageBatteryRecordScrapList", manageBatteryRecordScrapList);
		return "";
	}
	
	private String getUserSource(UserInfo ui){
		if(null==ui){
			return "city";
		}
		String source= null==ui?"":ui.getDeptId();
		if("铁塔公司管理人员".equals(ui.getRoleName())){
			if("集团公司".equals(source)){
				source="group";
			}else if("省公司".equals(source)){
				source="province";
			}else  if("市公司".equals(source)){
				source="city";
			}
		}
		return source;
	}
	/**
	 * 自己的openId和领导openidList
	 */
	private List<String> getSendOpenIdList(String source1,UserInfo ui){
		Map<String,String> map =new HashMap<String,String>();
		map.put("provinceId", ui.getProvinceId());
		map.put("roleName", "铁塔公司管理人员");
		map.put("deptId", "省公司");
		List<String> openIdList=null;
		try {
			if("province".equals(source1))//省级填报抄送给王怀宇
				map.put("ORuserId", "bbdb0030-1677-4740-9860-c9b256f3dcd1");//王怀宇
			
			Map<String,String> leadermap=bmrsService.selectLeaderByProvinceId(ui.getProvinceId());
			map.putAll(leadermap);
			
			openIdList=FirstQInterfaces.getIUserService().selectSJOpenIdList(map);//查询省级领导
			
			if("city".equals(source1)){
				if(null==openIdList) openIdList=new ArrayList<String>();
				map.put("cityId", ui.getCityId());
				map.put("deptId", "市公司");
				List<String> openIdListcity=FirstQInterfaces.getIUserService().selectSJOpenIdList(map);
				if(null==openIdListcity) openIdListcity=new ArrayList<String>();
				openIdList.addAll(openIdListcity);
			}
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		
		if(null==openIdList) openIdList=new ArrayList<String>();
		return openIdList;
	}
}
