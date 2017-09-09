package com.mcu32.firstq.wechat.util;



public class SessionConstants {

	public final static String SUSER = "s_user";//用户
	public final static String SPERMISSION = "s_permission";//权限
	public final static String OPENID = "openId";//微信唯一标识
	public final static String STATION = "s_station";//检查的基站对象
	public final static String STATIONLIST = "s_stationList";//附近基站列表
	public final static String STATION_INSPECT_RECORD = "s_station_inspect_record";//基站巡检记录
	public final static String DISCHARGE_RECORD = "s_discharge_record";//放电测试记录
	public final static String GENERATEPOWER_RECORD = "s_generatepower_record";	//放电测试记录
	public final static String SDEVICESLIST = "s_devicesList";//设备列表
	public final static String INSPECTION_ROOM_TOWER_EXCEPTION = "s_inspection_R_T_E";//设备列表
	
	public final static String INSPECTIONID = "s_inspectionId";//巡检id
	/**未注册的用户跳过Main页面直接进行具体的上站作业的时，保存注册前与注册后的jobId，并无他用，区分不同类型的上站作业还需要通过Session中的user中的jobId*/
	public final static String JOBID = "s_jobid";
	
	public final static String ALLINSPECATES = "s_allInspeCates";//检查的大类对象集合
	public final static String TOWERINSPECATES = "s_towerInspeCates";//检查的铁塔对象集合
	public final static String ROOMINSPECATES = "s_roomInspeCates";//检查的机房对象集合
	
	
	public final static String TOPAGEPATH = "s_toPagePath";//点击基站列表跳转到的页面
	public final static String ADDSTATIONPATH = "s_addStationPath";//添加基站页面
	public final static String ADDSTATIONRETURNPATH = "s_addStationReturnPath";//添加基站返回页面
	
	public final static String YHSWITCHPOWER_STRING = "s_switchPower";//隐患排查开关电源
	public final static String YHBATTERY = "s_yh_battery";//隐患排查蓄电池
	
	public final static String TOWERBO = "towerBO";//铁塔
	public final static String ROOMBO = "roomBO";//机房
}
