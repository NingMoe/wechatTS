package com.mcu32.firstq.wechat.action.discharge;
import java.util.ArrayList;
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

import com.mcu32.firstq.common.bean.bo.HLDischargeDetailBO;
import com.mcu32.firstq.common.bean.bo.HLDischargeRecordBO;
import com.mcu32.firstq.common.bean.bo.OrgBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.util.ConfigManager;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.SessionConstants;

@Controller
@RequestMapping(value = "/hldischarge")
public class HlDischargeRecordController extends BaseController{
	
	//准备开始放电任务
	@RequestMapping(value = "/goStart", method = RequestMethod.GET)
	public String readyStart(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String selectId=request.getParameter("selectId");
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		String cityId=user.getCityId();
		String provinceId=user.getProvinceId();
		Map<String,String> map=new HashMap<String,String>();
		map.put("provinceId", provinceId);
		map.put("cityId", cityId);
		String serial=request.getParameter("serial");
		String saveOrUpdate=request.getParameter("saveOrUpdate");
		if(serial=="" || serial==null){
			serial="1";
		}
		if(saveOrUpdate=="" || saveOrUpdate==null){
			saveOrUpdate="1";
		}
		List<HLDischargeRecordBO> recordList=FirstQInterfaces.getIHLDischargeService().getRecordList(map);
		model.addAttribute("recordList", recordList);
		model.addAttribute("selectId", selectId);
		model.addAttribute("curDate", new Date());
		model.addAttribute("serial", serial);
		model.addAttribute("saveOrUpdate", saveOrUpdate);
		model.addAttribute("userInfo", user);
		HLDischargeRecordBO recordBO = new HLDischargeRecordBO();
		List<String> provinceList = ConfigManager.getProvince();
		if ("".equals(recordBO.getProvinceId()) || recordBO.getProvinceId() == null) recordBO.setProvinceId(provinceId);
		if ("".equals(recordBO.getCityId()) || recordBO.getCityId() == null) recordBO.setCityId(cityId);
		if (provinceList != null){
			List<String> cityList = ConfigManager.getCity(recordBO.getProvinceId());
			model.addAttribute("provinceList", provinceList);
			model.addAttribute("cityList", cityList);
			if (cityList != null) {
				List<OrgBO> countryList = ConfigManager.getCountry(recordBO.getProvinceId(), recordBO.getCityId());
				model.addAttribute("countryList", countryList);
			}
		}   
		model.addAttribute("recordBO", recordBO);
		return "dischargehl/dischargeMain";
	}
	@RequestMapping(value = "/toFillDischarge")
	public String toFillDischarge(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String recordId=request.getParameter("recordId");
		String saveOrUpdate=request.getParameter("saveOrUpdate");
		//String serial=request.getParameter("serial");
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		String cityId=user.getCityId();
		String provinceId=user.getProvinceId();
		Map<String,String> map=new HashMap<String,String>();
		map.put("provinceId", provinceId);
		map.put("cityId", cityId);
		HLDischargeRecordBO recordBO=FirstQInterfaces.getIHLDischargeService().selectByRecordId(recordId);
		List<HLDischargeRecordBO> recordList=FirstQInterfaces.getIHLDischargeService().getRecordList(map);
		List<HLDischargeDetailBO> hlDischargeDetailList=FirstQInterfaces.getIHLDischargeService().getDetailList(recordId);
		model.addAttribute("recordBO", recordBO);
		model.addAttribute("hlDischargeDetailList", hlDischargeDetailList);
		model.addAttribute("selectId", 1);
		model.addAttribute("saveOrUpdate", saveOrUpdate);
		model.addAttribute("recordList", recordList);
		model.addAttribute("curDate", new Date());
		model.addAttribute("userInfo", user);
		
		List<String> provinceList = ConfigManager.getProvince();
		if ("".equals(recordBO.getProvinceId()) || recordBO.getProvinceId() == null) recordBO.setProvinceId(provinceId);
		if ("".equals(recordBO.getCityId()) || recordBO.getCityId() == null) recordBO.setCityId(cityId);
		if (provinceList != null){
			List<String> cityList = ConfigManager.getCity(recordBO.getProvinceId());
			model.addAttribute("provinceList", provinceList);
			model.addAttribute("cityList", cityList);
			if (cityList != null) {
				List<OrgBO> countryList = ConfigManager.getCountry(recordBO.getProvinceId(), recordBO.getCityId());
				model.addAttribute("countryList", countryList);
			}
		} 
		
		return "dischargehl/dischargeMain";
	}
	@RequestMapping(value = "/toFillDischargeList")
	public String toFillDischargeList(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String selectId=request.getParameter("selectId");
		String saveOrUpdate=request.getParameter("saveOrUpdate");
		//String serial=request.getParameter("serial");
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		String cityId=user.getCityId();
		String provinceId=user.getProvinceId();
		Map<String,String> map=new HashMap<String,String>();
		map.put("provinceId", provinceId);
		map.put("cityId", cityId);
		List<HLDischargeRecordBO> recordList=FirstQInterfaces.getIHLDischargeService().getRecordList(map);
		List<HLDischargeRecordBO> list=new ArrayList<HLDischargeRecordBO>();
		for(HLDischargeRecordBO recordBo:recordList){
			if(recordBo.getPlanName()!="" && recordBo.getPlanName()!=null){
				list.add(recordBo);
			}
		}
		model.addAttribute("selectId", selectId);
		model.addAttribute("saveOrUpdate", saveOrUpdate);
		model.addAttribute("recordList", list);
		model.addAttribute("curDate", new Date());
		model.addAttribute("userInfo", user);
		
		HLDischargeRecordBO recordBO = new HLDischargeRecordBO();
		List<String> provinceList = ConfigManager.getProvince();
		if ("".equals(recordBO.getProvinceId()) || recordBO.getProvinceId() == null) recordBO.setProvinceId(provinceId);
		if ("".equals(recordBO.getCityId()) || recordBO.getCityId() == null) recordBO.setCityId(cityId);
		if (provinceList != null){
			List<String> cityList = ConfigManager.getCity(recordBO.getProvinceId());
			model.addAttribute("provinceList", provinceList);
			model.addAttribute("cityList", cityList);
			if (cityList != null) {
				List<OrgBO> countryList = ConfigManager.getCountry(recordBO.getProvinceId(), recordBO.getCityId());
				model.addAttribute("countryList", countryList);
			}
		}   
		model.addAttribute("recordBO", recordBO);
		return "dischargehl/dischargeMain";
	}
	
	@RequestMapping(value = "/flash")
	public String flashList(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String selectId=request.getParameter("selectId");
		String saveOrUpdate=request.getParameter("saveOrUpdate");
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		String cityId=user.getCityId();
		String provinceId=user.getProvinceId();
		Map<String,String> map=new HashMap<String,String>();
		map.put("provinceId", provinceId);
		map.put("cityId", cityId);
		List<HLDischargeRecordBO> recordList=FirstQInterfaces.getIHLDischargeService().getRecordList(map);
		List<HLDischargeRecordBO> list=new ArrayList<HLDischargeRecordBO>();
		for(HLDischargeRecordBO recordBo:recordList){
			if(recordBo.getPlanName()!="" && recordBo.getPlanName()!=null){
				list.add(recordBo);
			}
		}
		model.addAttribute("selectId", selectId);
		model.addAttribute("saveOrUpdate", saveOrUpdate);
		model.addAttribute("recordList", list);
		model.addAttribute("curDate", new Date());
		model.addAttribute("userInfo", user);
		return "dischargehl/dischargeMain";
	}
	@RequestMapping(value = "/deleteDischargeRecord")
	public String deleteDischargeRecordId(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		String recordId=request.getParameter("recordId");
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		boolean flag=FirstQInterfaces.getIHLDischargeService().deleteRecord(recordId,user.getUserName());
		model.addAttribute("succ", flag);
		return "";
	}
	@RequestMapping(value = "/cancleDischargeRecord")
	public String cancleDischargeRecordId(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		String recordId=request.getParameter("recordId");
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		boolean flag=FirstQInterfaces.getIHLDischargeService().deleteRecord(recordId,user.getUserName());
		model.addAttribute("succ", flag);
		if(flag){
			return "redirect:/hldischarge/goStart";
		}
		return "";
	}
	@RequestMapping(value = "/saveDischargeRecord" )
	public String saveDischargeRecordId(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		String recordId=request.getParameter("recordId");
		HLDischargeRecordBO dischargeRecord=new HLDischargeRecordBO();
		dischargeRecord.setRecordId(recordId);
		Date date = new Date();
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		dischargeRecord.setLastOperateTime(date);
		dischargeRecord.setLastOperateUser(user.getUserName());
		dischargeRecord.setLastOperateUserId(user.getUserId());
		dischargeRecord.setOperateTime(date);
		dischargeRecord.setOperateUser(user.getUserName());
		dischargeRecord.setOperateUserId(user.getUserId());
		boolean flag=FirstQInterfaces.getIHLDischargeService().saveRecord(dischargeRecord);
		if(!flag){
			return ERRORPAGE;
		}
		return "";
	}
	@RequestMapping(value = "/updateDischargeRecordId" )
	public String updateDischargeRecordId(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		String recordId=request.getParameter("recordId");
		String oldRecordId=request.getParameter("oldRecordId");
		boolean flag=FirstQInterfaces.getIHLDischargeService().updateRecordId(recordId, oldRecordId,null);
		if(!flag){
			return ERRORPAGE;
		}
		return "";
	}
	@RequestMapping(value = "/updateDischargeRecord" )
	public String updateDischargeRecord(HLDischargeRecordBO dischargeRecord,HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		Date date = new Date();
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		dischargeRecord.setLastOperateTime(date);
		dischargeRecord.setLastOperateUser(user.getUserName());
		dischargeRecord.setLastOperateUserId(user.getUserId());
		boolean flag=FirstQInterfaces.getIHLDischargeService().updateRecord(dischargeRecord);
		if(!flag){
			return ERRORPAGE;
		}
		return "dischargehl/dischargeMain";
	}
	
	@RequestMapping(value = "/judgeBarCodeExist" )
	public String judgeBarCodeExist(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		String barCode=request.getParameter("barCode");
		String recordId=request.getParameter("recordId");
		Map<String,String> map=new HashMap<String,String>();
		map.put("barCode", barCode);
		map.put("recordId", recordId);
		String msg="服务器繁忙！";
		boolean flag=true;
		try {
			
			HLDischargeRecordBO hlDischargeRecordBO = FirstQInterfaces.getIHLDischargeService().selectByRecordId(barCode);
			if(hlDischargeRecordBO != null){
				msg = "此条码已经作为蓄电池组，不允许做放电测试！";
			}else {
				boolean flg = FirstQInterfaces.getIHLDischargeService().judgeBarCodeExist(map);
				if (flg) {
					msg = "此条码在本组蓄电池中已存在，请更换条码！";
				}else{
					flag = false;
				}
			}
			
		} catch (FirstQException e) {
			LogUtil.error(e);
		}
		model.addAttribute("succ",flag);
		model.addAttribute("msg", msg);
		return "";
	}
	
	/**
	 * 判断所有条码是否陪占用情况
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = "/judgeRecordIdExist" )
	public String judgeRecordIdExist(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		String barCode=request.getParameter("barCode");
		Map<String,String> map=new HashMap<String,String>();
		map.put("barCode", barCode);
		String msg="服务器繁忙！";
		boolean flag=true;
		try {
			flag = FirstQInterfaces.getIHLDischargeService().judgeRecordIdExist(barCode);
			msg= flag? "条码已被使用!" : "";
		} catch (FirstQException e) {
			// TODO Auto-generated catch block
			LogUtil.error(e);
		}
		model.addAttribute("isExist",flag);
		model.addAttribute("msg", msg);
		model.addAttribute("barCode", barCode);
		return "";
	}
	
	@RequestMapping(value = "/saveDischargeDetailBarCode")
	public String saveDischargeDetailBarCode(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		String barCode=request.getParameter("barCode");
		String recordId=request.getParameter("recordId");
		String serialNumber=request.getParameter("serialNumber");
		HLDischargeDetailBO dischargeDetail=new HLDischargeDetailBO();
		dischargeDetail.setRecordId(recordId);
		dischargeDetail.setBarCode(barCode);
		dischargeDetail.setSerialNumber(Integer.parseInt(serialNumber));
		boolean flag=FirstQInterfaces.getIHLDischargeService().saveDetail(dischargeDetail);
		model.addAttribute("serialNumber", serialNumber);
		if(!flag){
			return ERRORPAGE;
		}
		return "";
	}
	@RequestMapping(value = "/updateDischargeDetail" )
	public String updateDischargeDetail(HLDischargeDetailBO dischargeDetail,HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		boolean flag=FirstQInterfaces.getIHLDischargeService().updateDetail(dischargeDetail);
		if(!flag){
			return ERRORPAGE;
		}
		return "dischargehl/dischargeMain";
	}
	@RequestMapping(value = "/updateDischargeDetailBarCode" )
	public String updateDischargeDetailBarCode(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		String barCode=request.getParameter("barCode");
		String oldBarCode=request.getParameter("oldBarCode");
		String recordId=request.getParameter("recordId");
		Map<String,String> map=new HashMap<String,String>();
		map.put("barCode", barCode);
		map.put("oldBarCode", oldBarCode);
		map.put("recordId", recordId);
		boolean flag=FirstQInterfaces.getIHLDischargeService().updateDetailPrimaryKey(map);
		if(!flag){
			return ERRORPAGE;
		}
		return "";
	}
	@RequestMapping(value = "/deleteDischargeDetail" )
	public String deleteDischargeDetail(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		String barCode=request.getParameter("barCode");
		String recordId=request.getParameter("recordId");
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		int  serial=FirstQInterfaces.getIHLDischargeService().deleteDetailByPrimaryKey(barCode,recordId,user.getUserName());
		model.addAttribute("serial", serial);
		return "";
	}
	
	@RequestMapping(value = "/getDischargeDetail")
	public String getDischargeDetail(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		String recordId=request.getParameter("recordId");
		String selectId=request.getParameter("selectId");
		String code=request.getParameter("code");
		if(code !="" && code !=null){
			model.addAttribute("code", code);
		}
		HLDischargeRecordBO recordBO=FirstQInterfaces.getIHLDischargeService().selectByRecordId(recordId);
		List<HLDischargeDetailBO> hlDischargeDetailList=FirstQInterfaces.getIHLDischargeService().getDetailList(recordId);
		model.addAttribute("recordBO", recordBO);
		model.addAttribute("selectId", selectId);
		model.addAttribute("hlDischargeDetailList", hlDischargeDetailList);
		return "dischargehl/dischargeDetail";
	}
	@RequestMapping(value = "/selectMsgByCode")
	public String selectMsgByCode(HttpServletRequest request, HttpServletResponse response, HttpSession session, ModelMap model) {
		setWechatConfig(request,model);//拼写微信js需要的config参数
		String code=request.getParameter("code");
		UserInfo user = (UserInfo) session.getAttribute(SessionConstants.SUSER);
		String cityId=user.getCityId();
		String provinceId=user.getProvinceId();
		Map<String,String> map=new HashMap<String,String>();
		map.put("provinceId", provinceId);
		map.put("cityId", cityId);
		map.put("code", code);
		boolean flag=false;
		HLDischargeRecordBO recordBO=FirstQInterfaces.getIHLDischargeService().selectByCodeAndProvince(map);
		if(recordBO!=null){
			flag=true;
			model.addAttribute("recordMsg", recordBO);
		}else{
			List<HLDischargeRecordBO> recordMsglist=FirstQInterfaces.getIHLDischargeService().selectMsgByCode(map);
			model.addAttribute("recordMsg", recordMsglist);
		}
		model.addAttribute("flag", flag);
		model.addAttribute("code", code);
		return "dischargehl/dischargtRecordBySelectCode";
	}
}
