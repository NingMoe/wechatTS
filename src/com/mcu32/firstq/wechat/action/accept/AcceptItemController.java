package com.mcu32.firstq.wechat.action.accept;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcu32.firstq.common.bean.bo.AcceptCategoryBO;
import com.mcu32.firstq.common.bean.bo.StationBO;
import com.mcu32.firstq.common.bean.bo.UserBO;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.AcceptCategoryInfo;
import com.mcu32.firstq.wechat.bean.AcceptItemInfo;
import com.mcu32.firstq.wechat.bean.StationInfo;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.interceptor.AccessRequired;
import com.mcu32.firstq.wechat.util.FirstQBeanUtils;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.SessionConstants;

@Controller
@RequestMapping(value = "/acceptItem")
public class AcceptItemController extends BaseController {
	
	
	@RequestMapping(value = "/goToMain", method = RequestMethod.GET)
	public String goToMain(HttpServletRequest request, HttpSession session,ModelMap model) {
		String resultString = "commons/main";
		return resultString;
	}
	// TODO
	/**
	 * 根据stationId获取该基站状态（未验收、验收中和已验收），如果状态为未验收，则进行初始化交维信息，否则不进行初始化
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@AccessRequired(needLogin = true)
	@RequestMapping(value = "/checkItem", method = RequestMethod.GET)
	public String checkItem(HttpServletRequest request, HttpSession session,ModelMap model) {
		StationInfo stationInfo = (StationInfo) session.getAttribute(SessionConstants.STATION);
		String stationId=stationInfo.getStationId();
		model.addAttribute("stationid", stationId);
		try {
			String resultString = "accept/checkItem";
			StationInfo acceptStation = new StationInfo();
			StationBO resultStationBO = FirstQInterfaces.getIStationService().getStationById(stationId);
			if(resultStationBO != null) {
				FirstQBeanUtils.getTarget(resultStationBO, acceptStation);
			} 
			String checkStatus = "";
			if (acceptStation != null && StringUtils.isNotEmpty(acceptStation.getStationId())) {
				session.setAttribute(SessionConstants.STATION, acceptStation);
				checkStatus = acceptStation.getCheckStatus();
			}
			if (StringUtils.isNotEmpty(checkStatus)) {
				if("未验收".equals(checkStatus)) {
					UserBO userBO = new UserBO();
					UserInfo userInfo = (UserInfo) session.getAttribute(SessionConstants.SUSER);
					FirstQBeanUtils.getTarget(userInfo, userBO);
					String status = FirstQInterfaces.getICheckStationService().initRecord(stationId, userBO);
					LogUtil.info("初始化交维信息 status " + status);
					//初始化成功之后，修改基站状态为 验收中
					resultStationBO.setCheckStatus("验收中");
					boolean flag  = FirstQInterfaces.getIStationService().saveWaitCheckStation(resultStationBO, userBO);
					if(flag) {
						acceptStation.setCheckStatus("验收中");
						LogUtil.info("基站状态 验收中" + "更新成功");
					}
				}
			}
			List<AcceptCategoryInfo> allInspeCates = new ArrayList<AcceptCategoryInfo>();
			List<AcceptCategoryInfo> towerInspeCates = new ArrayList<AcceptCategoryInfo>();
			List<AcceptCategoryInfo> roomInspeCates = new ArrayList<AcceptCategoryInfo>();
			List<AcceptCategoryBO> resultAllInspeCates =  FirstQInterfaces.getICheckStationService().getCategories(stationId);
			if(resultAllInspeCates != null) {
				FirstQBeanUtils.getAcceptCategoryInfoList(resultAllInspeCates, allInspeCates);
			} 
			//将铁塔和机房大类添加到单独分开的集合中
			getRoomAndTowerInspeCates(allInspeCates, towerInspeCates, roomInspeCates);
			//获取铁塔和机房 检查小类项和初始化不一致的数量
			int towerWarningSum = 0;
			int roomWarningSum = 0;
			towerWarningSum = getItemModifyQuality(towerInspeCates);
			roomWarningSum = getItemModifyQuality(roomInspeCates);
			session.setAttribute(SessionConstants.TOWERINSPECATES, towerInspeCates);
			session.setAttribute(SessionConstants.ROOMINSPECATES, roomInspeCates);
			model.addAttribute("acceptStation", acceptStation);
			model.addAttribute("towerInspeCates", towerInspeCates);
			model.addAttribute("roomInspeCates", roomInspeCates);
			model.addAttribute("towerWarningSum", towerWarningSum);
			model.addAttribute("roomWarningSum", roomWarningSum);
			return resultString;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	// TODO
	/**
	 * 根据stationId获取该基站状态（未验收、验收中和已验收），如果状态为未验收，则进行初始化交维信息，否则不进行初始化
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@AccessRequired(needLogin = true)
	@RequestMapping(value = "/updateStationCheckStatus", method = RequestMethod.GET)
	public String updateStationCheckStatus(HttpServletRequest request, HttpSession session,ModelMap model) {
		try {
			// 获取stationId和token
			String stationId = request.getParameter("stationId");
			
			StationInfo acceptStation = new StationInfo();
			StationBO resultStationBO = FirstQInterfaces.getIStationService().getStationById(stationId);
			if(resultStationBO != null) {
				FirstQBeanUtils.getTarget(resultStationBO, acceptStation);
			} 
			String checkStatus = "";
			if (acceptStation != null && StringUtils.isNotEmpty(acceptStation.getStationId())) {
				session.setAttribute(SessionConstants.STATION, acceptStation);
				checkStatus = acceptStation.getCheckStatus();
			}
			if (checkStatus != null && !"".equals(checkStatus)) {
				if("验收中".equals(checkStatus)) {
					UserBO userBO = new UserBO();
					UserInfo userInfo = (UserInfo) session.getAttribute(SessionConstants.SUSER);
					FirstQBeanUtils.getTarget(userInfo, userBO);
					resultStationBO.setCheckStatus("已验收");
					boolean flag  = FirstQInterfaces.getIStationService().saveWaitCheckStation(resultStationBO, userBO);
					if(flag) {
						acceptStation.setCheckStatus("已验收");
						LogUtil.info("基站状态已验收" + "更新成功");
					}
				} 
			} 
			return "redirect:/acceptItem/checkItem.html?stationId=" + stationId;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	//将检查大类的集合，分别放到机房和铁塔检查大类的集合中
	public void getRoomAndTowerInspeCates(List<AcceptCategoryInfo> allInspeCates, List<AcceptCategoryInfo> towerInspeCates, List<AcceptCategoryInfo> roomInspeCates) {
		//获取铁塔和机房 检查小类项和初始化不一致的数量
		if (allInspeCates != null && allInspeCates.size() > 0) {
			// 将基站检查结果存储到session对象中
			for (int i = 0; i < allInspeCates.size(); i++) {
				AcceptCategoryInfo ac = allInspeCates.get(i);
				if(ac.getCheckType() != null && !"".equals(ac.getCheckType())) {
					if("room".equals(ac.getCheckType())) {
						roomInspeCates.add(ac);
					}
					if("tower".equals(ac.getCheckType())) {
						towerInspeCates.add(ac);
					}
				}
				//LogUtil.info(ac.getCategory() + " ");
			}
		}
	}
	
	//获取小类检查事项和初始值不一致的数量
	public int getItemModifyQuality(List<AcceptCategoryInfo> inspeCates) {
		int warningSum = 0;
		if (inspeCates != null && inspeCates.size() > 0) {
			// 将基站检查结果存储到session对象中
			for (int i = 0; i < inspeCates.size(); i++) {
				AcceptCategoryInfo ac = inspeCates.get(i);
					List<AcceptItemInfo> aiInfos = ac.getItems();
					if(aiInfos != null && aiInfos.size() > 0) {
						for(int j = 0; j < aiInfos.size(); j++) {
							AcceptItemInfo ai = new AcceptItemInfo();
							FirstQBeanUtils.getTarget(aiInfos.get(j), ai);
							String checkResult = ai.getCheckResult();
							String defaultValue = ai.getDefaultValue();
							if(StringUtils.isNotEmpty(checkResult) &&StringUtils.isNotEmpty(defaultValue) && !checkResult.equals(defaultValue)) {
								warningSum = warningSum + 1;
							}
						}
					}
				//LogUtil.info(ac.getCategory() + " ");
			}
		}
		return warningSum;
	}
	
}
