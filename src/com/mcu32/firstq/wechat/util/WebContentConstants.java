package com.mcu32.firstq.wechat.util;

public class WebContentConstants {
	
	//上站任务
	public final static String JOB_STATION_INSPECTION  = "job_1";//日常巡检
	public final static String JOB_STATION_RAILWAY  = "job_2";//新站交维
	public final static String JOB_PARAMETER_GENERAL  = "job_3";//参数普调
	public final static String JOB_GENERATE_POWER  = "job_4";//油机发电
	public final static String JOB_DISCHARGE  = "job_5";//放电测试
	public final static String JOB_STATION_QUARTER_INSPECTION  = "job_6";//季度巡检
	
	//设备类型
	public final static String DEVICE_BATTERY  = "battery";//蓄电池
	public final static String DEVICE_SWITCH_POWER  = "switch_power";//开关电源
	public final static String DEVICE_AIRCONDITIONING  = "air_conditioning";//空调
	
	public final static String DEVICE_OTHERDEVICE  = "other_device";//其他设备
	
	//状态
	public final static String STATUS_START  = "开始";//开始
	public final static String STATUS_PROGRESS  = "进行中";//进行中
	public final static String STATUS_COMPLETE  = "结束";//结束
	public final static String STATUS_SUSPEND  = "紧急中止";//紧急中止
	public final static String STATUS_SKIP	= "跳过检测";//
	public final static String STATUS_WAIT_UPLOAD  = "待上传";//待上传
	public final static String STATUS_UPLOAD_COMPLETE = "已上传";//已上传
	
	//工作笔记和异常
	public final static String WORKNOTE  = "备忘";//备忘
	public final static String EXCEPTION  = "异常";//异常
	
	//隐患排查
	public final static String YHDISCHARGERECORD  = "YHDischargeRdcord";//
	
	//铁塔、机房
	public final static String STATION_ROOM  = "room";//机房
	public final static String STATION_TOWER  = "tower";//铁塔
}
