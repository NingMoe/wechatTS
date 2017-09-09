package com.mcu32.firstq.wechat.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.mcu32.firstq.common.bean.bo.OrgBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;

public class ConfigManager {
	
	
	/** 私有构造函数 */
	private ConfigManager() {
		reload();
	}

	public void reload() {
		orgTree();
	}
	
	private static ConfigManager instance = null;
	public static ConfigManager getInstance() {
		if (instance == null) {
			return instance = new ConfigManager();
		}
		return instance;
	}
	
	
	public Map<String,List<Map<String, List<OrgBO>>>> orgMaps = new LinkedHashMap<String,List<Map<String, List<OrgBO>>>>();

	public void initOrg(){
		LogUtil.info("开始初始化组织机构代码。。。。。。。。");
		try {
			List<OrgBO> tempProvinceList = FirstQInterfaces.getIOrganizationService().getParents();
			for (OrgBO orgBO : tempProvinceList) {
				List<OrgBO> tempCityList = FirstQInterfaces.getIOrganizationService().getSons(orgBO.getId());
				List<Map<String, List<OrgBO>>> tempCityMaps = new ArrayList<Map<String, List<OrgBO>>>();
				for (OrgBO orgBO2 : tempCityList) {
					Map<String, List<OrgBO>> tempCity = new LinkedHashMap<String, List<OrgBO>>();
					List<OrgBO> tempCountryList = FirstQInterfaces.getIOrganizationService().getSons(orgBO2.getId());
					tempCity.put(orgBO2.getName(), tempCountryList);
					tempCityMaps.add(tempCity);
				}
				orgMaps.put(orgBO.getName(), tempCityMaps);
			}
			LogUtil.info("组织机构代码初始化完成。。。。。。。。。");
		} catch (FirstQException e) {
			LogUtil.error(e.getMessage());
		}
		
	}
	/**
	 * 
	 * 方法描述：组织部门加入缓存map(原缓存方法是initOrg)
	 * <p/>  
	 * <p/>
	 * 创建者：Administrator
	 * <p/> 
	 * 创建时间：2016年5月25日 上午9:43:00
	 */
	public void orgTree(){
		LogUtil.info("开始初始化组织机构代码。。。。。。。。");
		List<OrgBO> orgTreeAll = FirstQInterfaces.getIOrganizationService().getOrgTree();
		
		OrgBO tempOrg = null;
		OrgBO rootOrg = null;
		OrgBO firstOrg = null;
		Iterator<OrgBO> orgIte = orgTreeAll.iterator();
		while(orgIte.hasNext()){
			tempOrg = orgIte.next();
			//省
			if(null == tempOrg.getParentId() || "" == tempOrg.getParentId() || "".equals(tempOrg.getParentId())){
				rootOrg = tempOrg;
				List<Map<String, List<OrgBO>>> firstOrgList = new ArrayList<Map<String, List<OrgBO>>> ();
				orgMaps.put(tempOrg.getName(), firstOrgList);
			//市
			}else if(null != rootOrg && tempOrg.getParentId().equals( rootOrg.getId())){
				firstOrg = tempOrg;
				Map<String, List<OrgBO>> secondMap = new LinkedHashMap<String, List<OrgBO>>();
				secondMap.put(tempOrg.getName(), new ArrayList<OrgBO>());
				orgMaps.get(rootOrg.getName()).add(secondMap);
			//县
			}else if(null != firstOrg && tempOrg.getParentId().equals( firstOrg.getId())){
				int size = orgMaps.get(rootOrg.getName()).size();
				orgMaps.get(rootOrg.getName()).get(size - 1).get(firstOrg.getName()).add(tempOrg);
			}
		}
	}
	
	/**
	 * 获取省List
	 * @return
	 */
	public static List<String> getProvince(){
		List<String>  provinces = new ArrayList<String>(ConfigManager.getInstance().orgMaps.keySet());
		return provinces;
	}
	
	/**
	 * 根据省获取市
	 * @param province
	 * @return
	 */
	public static List<String> getCity(String province){
		List<String> citys  = new ArrayList<String>();
		List<Map<String, List<OrgBO>>> citysList = ConfigManager.getInstance().orgMaps.get(province);
		for (Map<String, List<OrgBO>> map : citysList) {
			citys.addAll(map.keySet());
		}
		return citys;
	}
	
	public static List<OrgBO> getCountry(String province,String city){
		List<Map<String, List<OrgBO>>> citysList = ConfigManager.getInstance().orgMaps.get(province);
		for (Map<String, List<OrgBO>> map : citysList) {
			if(map.containsKey(city)){
				List<OrgBO> countrys = map.get(city);
				return countrys;
			}
		}
		return null;
	}
}
