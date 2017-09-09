package com.mcu32.firstq.wechat.action.accept;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.mcu32.firstq.common.bean.bo.AcceptCategoryBO;
import com.mcu32.firstq.common.bean.bo.AcceptItemBO;
import com.mcu32.firstq.common.bean.bo.PhotoBO;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;
import com.mcu32.firstq.wechat.bean.AcceptCategoryInfo;
import com.mcu32.firstq.wechat.bean.AcceptItemInfo;
import com.mcu32.firstq.wechat.bean.PhotoInfo;
import com.mcu32.firstq.wechat.bean.RequestParam;
import com.mcu32.firstq.wechat.bean.StationInfo;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.interceptor.AccessRequired;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.SessionConstants;
import com.mcu32.firstq.wechat.util.WebContentConstants;

@Controller
@RequestMapping(value = "/categoryItem")
public class CategoryItemController extends BaseController{
	
	/**
	 * 跳转到新站验收检查列表页面 ok
	 * http://192.168.0.124/wechat/categoryItem/getCategoryAndItem.html?checkType=room&stationId=1
	 * left.wicp.net/wechat/categoryItem/getCategoryAndItem.html?checkType=room&stationId=1
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@AccessRequired(needLogin = true)
	@RequestMapping(value="/getCategoryAndItem",method=RequestMethod.GET)
	public String toStart(RequestParam rp,HttpServletRequest request, HttpSession session,ModelMap model){
		// TODO
		setWechatConfig(request,model);//拼写微信js需要的config参数
		
		String checkType=request.getParameter("checkType");
		
		StationInfo acceptStation =(StationInfo)session.getAttribute(SessionConstants.STATION);
		
		model.addAttribute("stationCheckStatusStatus", acceptStation.getCheckStatus());
		model.addAttribute("stationId", acceptStation.getStationId());
		model.addAttribute("acceptStation", acceptStation);
		
		if("room".equals(checkType)){
			List<AcceptCategoryInfo> roomInspeCates=(List<AcceptCategoryInfo>)session.getAttribute(SessionConstants.ROOMINSPECATES);
			model.addAttribute("categoryInfoList", roomInspeCates);
			model.addAttribute("categoryIdList", JSON.toJSON((getCaList(roomInspeCates))));
		}else if("tower".equals(checkType)){
			List<AcceptCategoryInfo> towerInspeCates=(List<AcceptCategoryInfo>)session.getAttribute(SessionConstants.TOWERINSPECATES);
			model.addAttribute("categoryInfoList", towerInspeCates);
			model.addAttribute("categoryIdList", JSON.toJSON((getCaList(towerInspeCates))));
		}
		
		return "accept/categoryAndItem";
	}
	/**
	 * 通过总list获得大类别id的List
	 */
	private List<Map<String,String>> getCaList(List<AcceptCategoryInfo> list){
		List<Map<String,String>> slist=new ArrayList<Map<String,String>>();
		if(null==list) return slist;
		for(AcceptCategoryInfo ac:list){
			Map<String,String> m=new HashMap<String,String>();
			m.put("categoryId", ac.getCategoryId());
			m.put("checkCategoryId", ac.getCheckCategoryId());
			slist.add(m);
		}
		return slist;
	}
	
	/**  TODO
	 * 通过检查类别获取检查项目 ok
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getItemList",method=RequestMethod.POST)
	public String getItem(HttpServletRequest request,HttpSession session, ModelMap model){
		String categoryId=request.getParameter("categoryId");
		String resultString="";
		try{
			
			List<AcceptCategoryInfo> roomInspeCates=(List<AcceptCategoryInfo>)session.getAttribute(SessionConstants.ROOMINSPECATES);
			List<AcceptCategoryInfo> towerInspeCates=(List<AcceptCategoryInfo>)session.getAttribute(SessionConstants.TOWERINSPECATES);
			
			List<AcceptItemInfo> itemList=getItemByCategory(categoryId,roomInspeCates);
			List<AcceptItemInfo> itemList1=getItemByCategory(categoryId,towerInspeCates);
			itemList.addAll(itemList1);
			
			String status="0";
			if(0==itemList.size()){
				status="暂无检查项目";
			}else{
				status= "0";
			}
			
			model.addAttribute("itemList", itemList);
			model.addAttribute("status", status);
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultString;
	}
	/**
	 * 通过类别获得项目
	 */
	private List<AcceptItemInfo> getItemByCategory(String categoryId,List<AcceptCategoryInfo> list){
		if(StringUtils.isNotEmpty(categoryId)&&null!=list)
			for(AcceptCategoryInfo ac:list){
				if(categoryId.equals(ac.getCategoryId())){
					if(null!=ac.getItems())
						return ac.getItems();
				}
			}
		return new ArrayList<AcceptItemInfo>();
	}
	
	/** TODO
	 * 提交检查项目
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@AccessRequired(needLogin = true)
	@RequestMapping(value="/updateCategoryAndItem",method=RequestMethod.POST)
	public String updateCategoryAndItem(AcceptCategoryBO aci,AcceptItemBO aii,HttpServletRequest request, HttpSession session,ModelMap model){
		String defaultValue=request.getParameter("defaultValue");
		
		if("是".equals(defaultValue)||"否".equals(defaultValue)){
			aii.setCheckResult("true".equals(aii.getCheckResult())?"是":"否");
		}else if("有".equals(defaultValue)||"无".equals(defaultValue)){
			aii.setCheckResult("true".equals(aii.getCheckResult())?"有":"无");
		}
		
		try {
			
			String status=FirstQInterfaces.getICheckStationService().updateCategoryAndItem(aci, aii);
			if("0".equals(status)){
				model.addAttribute("status","保存成功!");
			}
			
			StationInfo acceptStation =(StationInfo)session.getAttribute(SessionConstants.STATION);
			List<AcceptCategoryBO> allInspeCates=FirstQInterfaces.getICheckStationService().getCategories(acceptStation.getStationId());
			
			List<AcceptCategoryInfo> towerInspeCates = new ArrayList<AcceptCategoryInfo>();
			List<AcceptCategoryInfo> roomInspeCates = new ArrayList<AcceptCategoryInfo>();
			getRoomAndTowerInspeCates(allInspeCates, towerInspeCates, roomInspeCates);
			
			session.setAttribute(SessionConstants.TOWERINSPECATES, towerInspeCates);
			session.setAttribute(SessionConstants.ROOMINSPECATES, roomInspeCates);
			//提交照片信息
			String cateLocalIds = request.getParameter("cateImgs");
			if(StringUtils.isNotEmpty(cateLocalIds)){
				String[] cArray = cateLocalIds.split(",");
				if(cateLocalIds.length()>0){
					for(int i=0;i<cArray.length;i++){
						UserInfo user=(UserInfo)session.getAttribute(SessionConstants.SUSER);
						PhotoBO pb=new PhotoBO();
						pb.setPhotoId(getUUID());
						pb.setPhotoName(getUUID());
						pb.setLocalId(cArray[i]);
						pb.setPhotoType("category");
						pb.setRelateId(aci.getCheckCategoryId());
						pb.setStationId(acceptStation.getStationId());
						pb.setSqe(i);
						pb.setSubmitTime(new Date());
						pb.setCreateTime(new Date());
						pb.setSubmitTime(new Date());
						pb.setCreateUser(user.getUserName());
						pb.setCreateUserId(user.getUserId());
						pb.setLatitude(user.getLa());
						pb.setLongitude(user.getLo());
						pb.setFileLocation("");
						pb.setThumbLocation("");
						if(null==pb.getRemark()) pb.setRemark("");
						pb.setStatus(WebContentConstants.STATUS_WAIT_UPLOAD);
						pb.setFileSize(0);
						
						boolean b = FirstQInterfaces.getIPhotoService().savePhotoInfo(pb);
						if(!b)
							model.addAttribute("status","服务器繁忙!");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("status","失败!");
		}
		
		return "";
	}
	/**
	 * 将全部大类分成铁塔和机房两个list
	 */
	private void getRoomAndTowerInspeCates(List<AcceptCategoryBO> allInspeCates, List<AcceptCategoryInfo> towerInspeCates, List<AcceptCategoryInfo> roomInspeCates) {
		
		if (allInspeCates != null && allInspeCates.size() > 0) {
			// 将基站检查结果存储到session对象中
			for (int i = 0; i < allInspeCates.size(); i++) {
				AcceptCategoryBO ac = allInspeCates.get(i);
				if(ac.getCheckType() != null && !"".equals(ac.getCheckType())) {
					
					List<AcceptItemInfo> itemList=new ArrayList<AcceptItemInfo>();
					if(null!=ac.getItems()){
						for(AcceptItemBO aib:ac.getItems()){
							AcceptItemInfo item=new AcceptItemInfo();
							BeanCopier.create(AcceptItemBO.class, AcceptItemInfo.class, false).copy(aib, item, null);
							itemList.add(item);
						}
					}
					AcceptCategoryInfo aci=new AcceptCategoryInfo();
					BeanCopier.create(AcceptCategoryBO.class, AcceptCategoryInfo.class, false).copy(ac, aci, null);
					
					aci.setItems(itemList);
					
					if("room".equals(ac.getCheckType())) {
						roomInspeCates.add(aci);
					}
					if("tower".equals(ac.getCheckType())) {
						towerInspeCates.add(aci);
					}
				}
				//LogUtil.info(ac.getCategory() + " ");
			}
		}
	}
	
	/** TODO
	 * 通过检查类别获取检查类别的图片和备注信息
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getCategoryRemarkAndPhoto",method=RequestMethod.POST)
	public String getCategoryRemarkAndPhoto(HttpServletRequest request,HttpSession session, ModelMap model){
		String resultString="";
		
		String stationId=request.getParameter("stationId");
		String checkCategoryId=request.getParameter("checkCategoryId");
		try{
			
			List<PhotoBO>  photoList =FirstQInterfaces.getIPhotoService().getPhotoInfosByStationId(stationId);
			List<PhotoInfo> categoryPhotoInfos = new ArrayList<PhotoInfo>();
			fillPhotoInfosFromAll(photoList,categoryPhotoInfos,checkCategoryId);//通过总照片List，填充类别照片
			
			List<AcceptCategoryInfo> roomInspeCates=(List<AcceptCategoryInfo>)session.getAttribute(SessionConstants.ROOMINSPECATES);
			List<AcceptCategoryInfo> towerInspeCates=(List<AcceptCategoryInfo>)session.getAttribute(SessionConstants.TOWERINSPECATES);
			roomInspeCates.addAll(towerInspeCates);
			
			AcceptCategoryInfo ai=getCategoryByCheckId(roomInspeCates,checkCategoryId);
			if(null==ai)
				ai=getCategoryByCheckId(towerInspeCates,checkCategoryId);
			String remark= (null==ai||null==ai.getRemark())?"":ai.getRemark();
			//转换路径
			UserInfo userInfo = (UserInfo) session.getAttribute(SessionConstants.SUSER);
			FirstqTool.convertPhotoPath(categoryPhotoInfos,userInfo);
			model.addAttribute("photoList", categoryPhotoInfos);
			model.addAttribute("remark", remark);
			
			LogUtil.info("remark is "+remark+" ai.getRemark() = "+ai.getRemark()+" roomInspeCates.size() = "+roomInspeCates.size()+" towerInspeCates.size() = "+towerInspeCates.size());
			LogUtil.info("categoryPhotoInfos.size() = "+categoryPhotoInfos.size());
			LogUtil.info("checkCategoryId = "+checkCategoryId);
			LogUtil.info("stationId = "+stationId);
			
		}catch(Exception e){
			LogUtil.info(e.getMessage());
		}
		return resultString;
	}
	
	/**
	 * 通过总照片List，填充类别照片
	 */
	private void fillPhotoInfosFromAll(List<PhotoBO> photoList,List<PhotoInfo> categoryPhotoInfos,String checkCategoryId){
		
		if(photoList!=null)
			for(PhotoBO pb:photoList){
				if(StringUtils.isNotEmpty(pb.getPhotoType())&&"category".equals(pb.getPhotoType())&&checkCategoryId.equals(pb.getRelateId())) {
					if(StringUtils.isNotEmpty(pb.getFileLocation())){
						pb.setFileLocation(FirstqTool.getPhotoLocation( pb.getFileLocation()));
						pb.setThumbLocation(FirstqTool.getPhotoLocation( pb.getThumbLocation()));
					}
					PhotoInfo pi=new PhotoInfo();
					BeanCopier.create(PhotoBO.class, PhotoInfo.class, false).copy(pb, pi, null);
					categoryPhotoInfos.add(pi);
				}
			}
	}
	/**
	 * 获得备注
	 * @param allInspeCates
	 * @param checkCategoryId
	 * @return
	 */
	private AcceptCategoryInfo getCategoryByCheckId(List<AcceptCategoryInfo> allInspeCates,String checkCategoryId){
		if(StringUtils.isNotEmpty(checkCategoryId))
			for(AcceptCategoryInfo ai:allInspeCates){
				if(checkCategoryId.equals(ai.getCheckCategoryId())){
					return ai;
				}
			}
		return null;
	}
	
	/**TODO
	 * ajax 判断是否完成
	 */
	@RequestMapping(value="/checkIfFinish",method=RequestMethod.POST)
	public String checkIfFinish(RequestParam rp,HttpServletRequest request, HttpSession session,ModelMap model){
		
		StationInfo acceptStation =(StationInfo)session.getAttribute(SessionConstants.STATION);
		model.addAttribute("stationCheckStatusStatus", acceptStation.getCheckStatus());
		model.addAttribute("stationId", acceptStation.getStationId());
		model.addAttribute("acceptStation", acceptStation);
		
		return "";
	}
}
