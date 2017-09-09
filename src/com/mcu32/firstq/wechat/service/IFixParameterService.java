package com.mcu32.firstq.wechat.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mcu32.firstq.wechat.bean.SelectEntity;

public interface IFixParameterService {

	/*获取开关电源品牌列表*/
	List<SelectEntity> getPowerBrandList();
	
	/*获取品牌对应的开关电源型号*/
	List<SelectEntity> getPowerType(String brandId);
	
	/*获取蓄电池的品牌*/
	List<SelectEntity> getBatteryBrand();
	
	/*获取各型号计算参数列表*/
	List<SelectEntity> getParameter(HttpServletRequest request);
}
