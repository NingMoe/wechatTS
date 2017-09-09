package com.mcu32.firstq.wechat.action.doorSystem;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcu32.firstq.common.bean.bo.DoorSystemBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.service.IDoorSystemSendService;
import com.mcu32.firstq.wechat.tools.TemplateMessages;
import com.mcu32.firstq.wechat.util.SessionConstants;
import com.mcu32.firstq.wechat.util.date.DateUtil;

@Controller
@RequestMapping(value = "/doorSystem")
public class DoorSystemController extends BaseController {
	@Autowired private IDoorSystemSendService doorSystemSendService;
	
	@RequestMapping(value = "/main",method = RequestMethod.GET)
	public String main(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String selectId=request.getParameter("selectId");
		model.addAttribute("selectId", selectId);
		model.addAttribute("fillCycle","填报周期:"+DateUtil.getCurrentMonday("yyyy年MM月dd日")+"至"+DateUtil.getPreviousSunday("yyyy年MM月dd日"));
		UserInfo ui=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		String source = getUserSource(ui);
		Map<String,String> map=new HashMap<String,String>();
		map.put("week", "thisweek");
		map.put("provinceId", ui.getProvinceId());
		DoorSystemBO doorSystem=FirstQInterfaces.getIDoorSystemService().selectByProvinceId(map);
		if(doorSystem != null){
			model.addAttribute("doorSystem", doorSystem);
			model.addAttribute("fillUser","填报人员:"+doorSystem.getFillMan());
		}
		model.addAttribute("source", source);
		model.addAttribute("userInfo", ui);
		return "doorSystem/main";
	}
	
	@RequestMapping(value = "/addOrUpdate",method = RequestMethod.POST)
	public String addOrUpdate(DoorSystemBO doorSystem,HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		UserInfo user=(UserInfo)session.getAttribute(SessionConstants.SUSER);
		doorSystem.setDoorSystemId(getUUID());
		doorSystem.setFillDate(new Date());
		doorSystem.setFillMan(user.getUserName());
		doorSystem.setProvinceId(user.getProvinceId());
		doorSystem.setPhoneNumber(Long.parseLong(user.getPhoneNo()));
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("week", "thisweek");
		List<DoorSystemBO> doorSystemList=FirstQInterfaces.getIDoorSystemService().selectList(map);
		
		boolean saveSucc=FirstQInterfaces.getIDoorSystemService().insertOrUpdate(doorSystem);
		if(saveSucc){
			int beforeSaveSize=doorSystemList.size();
			String remark="";
			if(null !=doorSystemList&&doorSystemList.size()<31){
				doorSystemList=FirstQInterfaces.getIDoorSystemService().selectList(map);
			}
			if(null !=doorSystemList&&doorSystemList.size()==31){
				if(beforeSaveSize<31){
					doorSystemSendService.sendLastFillMsg(doorSystemList);
					doorSystemSendService.sendLastEmile(doorSystemList);
				}
				remark="截至当前，各省已完成门禁建设进度填报";
			}
			
			List<String> openIdList=getSendOpenIdList(user);
			Map<String, String> dataMap=new HashMap<String,String>();
			dataMap.put("first", "[门禁项目建设进度填报通知]");
			dataMap.put("keyword1", "门禁项目建设进度");
			dataMap.put("keyword2", DateUtil.DateToString(new Date(),"yyyy-MM-dd HH:mm:ss"));
			dataMap.put("remark", doorSystem.getProvinceId()+"已进行门禁项目建设进度填报\\n填报人："+doorSystem.getFillMan()+"\\n"+remark);
			
			TemplateMessages.sendDoorSystem(openIdList, dataMap);//发送模版消息
		}
		
		model.addAttribute("succ", saveSucc);
		return "";
	}
	
	@RequestMapping(value = "/selectList", method = RequestMethod.GET)
	public String getQuestionList(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		try {
			Map<String,String> map=new HashMap<String,String>();
			map.put("week", "thisweek");
			List<DoorSystemBO> doorSystemList=FirstQInterfaces.getIDoorSystemService().selectList(map);
			model.addAttribute("doorSystemList", doorSystemList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private String getUserSource(UserInfo ui){
		if(null==ui){
			return "group";
		}
		String source= null==ui?"":ui.getDeptId();
		if("铁塔公司管理人员".equals(ui.getRoleName()) && "省公司".equals(source)){
			source="province";
		}else{
			source="group";
		}
		return source;
	}
	
	private List<String> getSendOpenIdList( UserInfo user) {
		String source = getUserSource(user);
		Map<String,String> map =new HashMap<String,String>();
		map.put("provinceId", user.getProvinceId());
		map.put("roleName", "铁塔公司管理人员");
		map.put("deptId", "省公司");
		List<String> openIdList=null;
		try {
			if("province".equals(source))//刘杰
				map.put("ORuserId", "04f19544-1539-48de-88c8-4d92c75412df");//刘杰
			
			openIdList=FirstQInterfaces.getIUserService().selectSJOpenIdList(map);//查询省级领导
			
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		
		if(null==openIdList) openIdList=new ArrayList<String>();
		return openIdList;
	}
	@RequestMapping(value = "/sendMail")
	public String sendMail(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		String mailAdress=request.getParameter("mail");
		List<String> userList=new ArrayList<String>();
		userList.add(mailAdress);
		doorSystemSendService.sendThisWeekCountyEmail(userList);
		return "";
	}
}