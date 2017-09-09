package com.mcu32.firstq.wechat.bean;
/**
 * http://111.11.26.231/services/photo/addPhotoInfo.json
 * 提交照片拍摄记录
 * {“token”:””,
 *	“inspection_id”:””,    //巡站ID
 *	"device_id":"xxxx",
 *	"out_in_flag":"xxxx",
 *	"photo_name":"xxxx",//照片名为带后缀的全名
 *	"longitude":"xxxx",
 *	"latitude":"xxxx"
 * }
 */
public class RequestPhotoAddPhotoInfo extends InterFaceRequestBaseObj {
	public String inspection_id;
	public String device_id;
	public String out_in_flag;
	public String photo_name;
	public String longitude;
	public String latitude;
	public String remark;
	public String getInspection_id() {
		return inspection_id;
	}
	public void setInspection_id(String inspection_id) {
		this.inspection_id = inspection_id;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getOut_in_flag() {
		return out_in_flag;
	}
	public void setOut_in_flag(String out_in_flag) {
		this.out_in_flag = out_in_flag;
	}
	public String getPhoto_name() {
		return photo_name;
	}
	public void setPhoto_name(String photo_name) {
		this.photo_name = photo_name;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
