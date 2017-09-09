package com.mcu32.firstq.wechat.bean;

import java.util.Date;

/**
 * @author Administrator
{“token”:””,
“station_id”:””, 
“power_brand”:””,						//开关电源品牌
“power_type”:””,						//开关电源型号
“battery_brand”:””,						//蓄电池品牌
“production_date”:””,					//蓄电池生产日期
“month_avg_poweroff”:””,				//平均每月停电次数
“once_avg_time”:””,					//平均每次停电时长
“equalizing_charge_time_input”:””,		//均充时间(H)
“equalizing_charge_voltage”:””,			//计算均充电压
“floating_charge_voltage”:””,				//计算浮充电压
“charging_current_limiter”:””,			//计算充电限流
“equalizing_charge_time”:””,				//计算均充时间(H)
“equalizing_charge_loop”:””,				//计算均充周期(天)
“transform_equalizing_current”:””,		//计算转均充电流
“transform_equalizing_capacity”:””,		//计算转均充容量
“equalizing_voltage_start”:”” 			//计算均充启动电压
}

 */
public class RequestDevicesSetParameterRecord extends InterFaceRequestBaseObj{
	
	private String station_id;
	private String power_brand;
	private String power_type;
	private String battery_brand;
	private Date production_date;
	private String month_avg_poweroff;
	private String once_avg_time;
	private String equalizing_charge_time_input;
	private String equalizing_charge_voltage;
	private String floating_charge_voltage;
	private String charging_current_limiter;
	private String equalizing_charge_time;
	private String equalizing_charge_loop;
	private String transform_equalizing_current;
	private String transform_equalizing_capacity;
	private String equalizing_voltage_start;
	public String getStation_id() {
		return station_id;
	}
	public void setStation_id(String station_id) {
		this.station_id = station_id;
	}
	public String getPower_brand() {
		return power_brand;
	}
	public void setPower_brand(String power_brand) {
		this.power_brand = power_brand;
	}
	public String getPower_type() {
		return power_type;
	}
	public void setPower_type(String power_type) {
		this.power_type = power_type;
	}
	public String getBattery_brand() {
		return battery_brand;
	}
	public void setBattery_brand(String battery_brand) {
		this.battery_brand = battery_brand;
	}
	public Date getProduction_date() {
		return production_date;
	}
	public void setProduction_date(Date production_date) {
		this.production_date = production_date;
	}
	public String getMonth_avg_poweroff() {
		return month_avg_poweroff;
	}
	public void setMonth_avg_poweroff(String month_avg_poweroff) {
		this.month_avg_poweroff = month_avg_poweroff;
	}
	public String getOnce_avg_time() {
		return once_avg_time;
	}
	public void setOnce_avg_time(String once_avg_time) {
		this.once_avg_time = once_avg_time;
	}
	public String getEqualizing_charge_time_input() {
		return equalizing_charge_time_input;
	}
	public void setEqualizing_charge_time_input(String equalizing_charge_time_input) {
		this.equalizing_charge_time_input = equalizing_charge_time_input;
	}
	public String getEqualizing_charge_voltage() {
		return equalizing_charge_voltage;
	}
	public void setEqualizing_charge_voltage(String equalizing_charge_voltage) {
		this.equalizing_charge_voltage = equalizing_charge_voltage;
	}
	public String getFloating_charge_voltage() {
		return floating_charge_voltage;
	}
	public void setFloating_charge_voltage(String floating_charge_voltage) {
		this.floating_charge_voltage = floating_charge_voltage;
	}
	public String getCharging_current_limiter() {
		return charging_current_limiter;
	}
	public void setCharging_current_limiter(String charging_current_limiter) {
		this.charging_current_limiter = charging_current_limiter;
	}
	public String getEqualizing_charge_time() {
		return equalizing_charge_time;
	}
	public void setEqualizing_charge_time(String equalizing_charge_time) {
		this.equalizing_charge_time = equalizing_charge_time;
	}
	public String getEqualizing_charge_loop() {
		return equalizing_charge_loop;
	}
	public void setEqualizing_charge_loop(String equalizing_charge_loop) {
		this.equalizing_charge_loop = equalizing_charge_loop;
	}
	public String getTransform_equalizing_current() {
		return transform_equalizing_current;
	}
	public void setTransform_equalizing_current(String transform_equalizing_current) {
		this.transform_equalizing_current = transform_equalizing_current;
	}
	public String getTransform_equalizing_capacity() {
		return transform_equalizing_capacity;
	}
	public void setTransform_equalizing_capacity(
			String transform_equalizing_capacity) {
		this.transform_equalizing_capacity = transform_equalizing_capacity;
	}
	public String getEqualizing_voltage_start() {
		return equalizing_voltage_start;
	}
	public void setEqualizing_voltage_start(String equalizing_voltage_start) {
		this.equalizing_voltage_start = equalizing_voltage_start;
	}
	
	

}
