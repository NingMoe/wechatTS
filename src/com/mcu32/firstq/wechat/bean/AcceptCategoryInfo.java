package com.mcu32.firstq.wechat.bean;

import java.util.List;

public class AcceptCategoryInfo {
	private String checkCategoryId;
	private String stationId;
	private String checkType;
	private String remark;
	private String categoryId;
	private String category;
	private List<AcceptItemInfo> items ;
	
	public String getCheckCategoryId() {
		return checkCategoryId;
	}
	public void setCheckCategoryId(String checkCategoryId) {
		this.checkCategoryId = checkCategoryId;
	}
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<AcceptItemInfo> getItems() {
		return items;
	}
	public void setItems(List<AcceptItemInfo> items) {
		this.items = items;
	}
	
}
