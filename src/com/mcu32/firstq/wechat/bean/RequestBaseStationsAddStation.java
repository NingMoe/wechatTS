package com.mcu32.firstq.wechat.bean;

/**
 * @author Administrator
 *{
   [
    {
        "token": "",
        "station_id": "XXXX",
        "station_name": "XXXX",
        "province_id": "XXXX",
        "city_id": "XXXX",
        "county_id": "XXXX",
        "station_type": "XXXX",
        "pulling_level": "XXXX",
        "battery_num": "XXXX",
        "load_current": "XXXX",
        "longitude": "XXXX",
        "latitude": "XXXX"
    },
    {
        "token": "ad403e35-4e7a-417b-b090-a2f4cc3120cf",
        "station_id": "s",
        "station_name": "上地七街 M-GSM900M 基站",
        "province_id": "11",
        "city_id": "01",
        "county_id": "08",
        "station_type": "1",
        "pulling_level": "1",
        "battery_num": "2",
        "load_current": "20",
        "longitude": "40.0458720168",
        "latitude": "116.3057658451"
    }
]
	基站id、基站名称、经度、纬度、所属城市、所属县区、基站种类、进站难度、基站负载、创建时间、创建者、最后更新时间、最后更新人
 */
public class RequestBaseStationsAddStation extends InterFaceRequestBaseObj{
	
	private String station_id;
	private String station_name;
	private String province_id;
	private String city_id;
	private String county_id;
	private String station_type;
	private String pulling_level;
	private String battery_num;
	private String load_current;
	private String longitude;
	private String latitude;
	public String getStation_id() {
		return station_id;
	}
	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}
	public String getProvince_id() {
		return province_id;
	}
	public void setProvince_id(String province_id) {
		this.province_id = province_id;
	}
	public String getCity_id() {
		return city_id;
	}
	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}
	public String getCounty_id() {
		return county_id;
	}
	public void setCounty_id(String county_id) {
		this.county_id = county_id;
	}
	public String getStation_type() {
		return station_type;
	}
	public void setStation_type(String station_type) {
		this.station_type = station_type;
	}
	public String getPulling_level() {
		return pulling_level;
	}
	public void setPulling_level(String pulling_level) {
		this.pulling_level = pulling_level;
	}
	public String getBattery_num() {
		return battery_num;
	}
	public void setBattery_num(String battery_num) {
		this.battery_num = battery_num;
	}
	public String getLoad_current() {
		return load_current;
	}
	public void setLoad_current(String load_current) {
		this.load_current = load_current;
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
	
	

}
