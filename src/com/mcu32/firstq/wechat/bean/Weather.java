package com.mcu32.firstq.wechat.bean;

public class Weather {
	
	private String weaid;
	private String days;
	private String week;
	private String citynm;
	private String temperature;
	private String weather;
	private String temp_high;
	private String temp_low;
	private String winp;//微风
	public String getWeaid() {
		return weaid;
	}
	public void setWeaid(String weaid) {
		this.weaid = weaid;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getCitynm() {
		return citynm;
	}
	public void setCitynm(String citynm) {
		this.citynm = citynm;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getTemp_high() {
		return temp_high;
	}
	public void setTemp_high(String tempHigh) {
		temp_high = tempHigh;
	}
	public String getTemp_low() {
		return temp_low;
	}
	public void setTemp_low(String tempLow) {
		temp_low = tempLow;
	}
	public String getWinp() {
		return winp;
	}
	public void setWinp(String winp) {
		this.winp = winp;
	}


}
