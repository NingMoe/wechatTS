package com.mcu32.firstq.wechat.bean;

import com.mcu32.firstq.wechat.util.LogUtil;

public enum PhotoType {

	SWITCH_POWER("开关电源","switch_power"),
	BATTERY("蓄电池","battery"),
	AIR_CONDITIONING("空调","air_conditioning"),
	ROOM("机房","room"),
	TOWER("铁塔","tower"),
	AC_DISTRIBUTION("交流配电箱","ac_distribution"),
	DC_DISTRIBUTION("直流配电箱","dc_distribution"),
	TRANSFORMER("变压器","transformer"),
	GROUNDING_LIGHTING("防雷接地设备","grounding_lightning"),
	PRESSURE_REGULATOR("调压器","pressure_regulator"),
	AC_LINE("交流引入","ac_line"),
	MONITORING("监控设备","monitoring"),
	CATEGORY("检查项","category"),
	DEVICEEXCEPT("异常上报","deviceExcept"),
	CUTPOWERSTARTTIME("停电开始","cutPowerStartTime"),
	GENERATESTARTVOLTAGE("发电开始电压","generateStartVoltage"),
	GENERATEENDVOLTAGE("发电结束电压","generateEndVoltage"),
	STARTVOLTAGE("放电开始电压","startVoltage"),
	ENDVOLTAGE("放电结束电压","endVoltage"),
	OTHER("其他","other"),
	//operateType操作
	UPDATE("修改","update"),
	DELETE("删除","delete"),
	INSERT("添加","insert"),
	//operateObject操作对象
	STATION("基站","station"),
	TOWERPHOTO("铁塔外观照片","towerPhoto"),
	SWITCH_POWERPHOTO("开关电源外观照片","switch_powerPhoto"),
	BATTERYPHOTO("蓄电池外观照片","batteryPhoto"),
	AIR_CONDITIONINGPHOTO("空调外观照片","air_conditioningPhoto"),
	ROOMPHOTO("机房外观照片","roomPhoto"),
	AC_DISTRIBUTIONPHOTO("交流配电箱外观照片","ac_distributionPhoto"),
	DC_DISTRIBUTIONPHOTO("直流配电箱外观照片","dc_distributionPhoto"),
	TRANSFORMERPHOTO("变压器外观照片","transformerPhoto"),
	GROUNDING_LIGHTINGPHOTO("防雷接地设备外观照片","grounding_lightningPhoto"),
	PRESSURE_REGULATORPHOTO("调压器外观照片","pressure_regulatorPhoto"),
	AC_LINEPHOTO("交流引入外观照片","ac_linePhoto"),
	MONITORINGPHOTO("监控设备外观照片","monitoringPhoto"),
	
	//operateProperty
	//stationOperateProperty
	STATIONSTATIONNAME("基站名称","stationName"),
	STATIONSTATIONNO("基站编号","stationNo"),
	STATIONSTATIONTYPE("基站类型","stationType"),
	STATIONLOADCURRENT("基站负载","loadCurrent"),
	STATIONREGIONALMANAGER("区域经理","regionalManager"),
	STATIONMANAGERPHONE("区域经理联系方式：","managerPhone"),
	STATIONLONGBACK("后备时长","longBack"),
	STATIONMOBILEOPERATOR("运营商","mobileOperator"),
	STATIONPROVINCEID("所在省","provinceId"),
	STATIONCITYID("所在市","cityId"),
	STATIONCOUNTYID("所在县","countyId"),
	STATIONLONGITUDE("基站经度","longitude"),
	STATIONLATITUDE("基站纬度","latitude"),
	STATIONREGIONPROPERTY("区域性质","regionProperty"),
	STATIONLANDSCAPE("地貌","landScape"),
	STATIONTOWERTYPE("铁塔类型","towerType"),
	STATIONBUILDINGPATTERN("建筑方式","buildingPattern"),
	STATIONROOMTYPE("机房类型","roomType"),
	STATIONSTATIONADDRESS("地址","stationAddress"),
	
	//switch_power
	SWITCHPOWERBRAND("品牌","brand"),
	SWITCHPOWERMODEL("型号","model"),
	SWITCHPOWERPOWERMODNUM("模块数","powerModNum"),
	
	//battery
	BATTERYBATTERYBRAND("品牌","batteryBrand"),
	BATTERYBATTERYMODEL("型号","batteryModel"),
	BATTERYBATTERYVOLTAGE("单体电压","batteryVoltage"),
	BATTERYGROUPNUM("组数","groupNum"),
	BATTERYENTERNETDATE("生产日期","enterNetDate"),
	
	//devices outlose
	DEVICESOUTLOSE("备注","outLose"),
	
	NORMALEQUIPMENT("设备正常","0"),
	LABELLOSS("标签丢失","2"),
	DEVICELOSS("设备丢失","3"),
	EQUIPMENTBACKNETWORK("设备退网","4"),
	
	//tower
	TOWERTOWERTYPEDETAIL("塔型详情", "towerTypeDetail"),
	TOWERPLATFORMNUM("平台数量", "platformNum"),
	TOWERPLATFORMSPACING("平台平均间距","platformSpacing"),
	TOWERDERRICKNUM("抱杆数量", "derrickNum"),
	TOWERDERRICKUSE("抱杆占用情况","derrickUse"),
	TOWERTOWERHEIGHT("塔高", "towerHeight"),
	
	//air_conditioning
	AIRCONDITIONINGCREATETIME("生产日期", "createTime"),
	//ac_distribution
	ACDISTRIBUTIONCAPACITY("总容量", "acCapacity"),
	ACDISTRIBUTIONMANUFACTURER("生产厂商", "acManufacturer"),
	ACDISTRIBUTIONINTERFACE("预留接口", "haveGeneratorInterface"),
	//dc_distribution
	DCDISTRIBUTIONBOXBRAND("品牌", "boxBrand"),
	DCDISTRIBUTIONCAPACITY("总容量", "dcCapacity"),
	DCDISTRIBUTIONFUSEUSEDNUM("熔丝已经使用数量", "fuseUsedNum"),
	DCDISTRIBUTIONFUSENOTUSEDNUM("未使用熔丝数量", "fuseNotUsedNum"),
	//transformer
	TRANSFORMERTYPE("类型", "type"),
	TRANSFORMERRATEDPOWER("额定功率", "ratedPower"),
	TRANSFORMERMANUFACTURER("生产厂商", "manufacturer"),
	//grounding_lightning
	GROUNDINGLIGHTINGMODEL("设备型号", "gflModel"),
	GROUNDINGLIGHTINGSUPPLIER("生产厂商", "supplier"),
	//pressure_regulator
	PRESSUREREGULATORRATEDPOWER("额定功率", "prRatedPower"),
	PRESSUREREGULATORMANUFACTURER("生产厂商", "prManufacturer"),
	//ac_line
	ACLINEMATERIAL("线缆材质", "lineMaterial"),
	ACLINEMODEL("规格型号", "lineModel"),
	ACLINEOVERHEADDISTANCE("架空距离", "overheadDistance"),
	ACLINEWALLDISTANCE("墙挂距离", "wallDistance"),
	ACLINEGROUNDDISTANCE("地埋距离", "groundDistance"),
	ACLINELENGTH("交流引入长度", "lineLength"),
	//monitoring
	MONITORINGMODEL("型号", "mModel"),
	MONITORINGSUPPLIER("生产厂商", "mSupplier"),
	//room
	ROOMStructure("机房结构", "roomStructure"),
	ROOMSIZE("机房尺寸", "roomeSize"),
	ROOMAREA("机房面积", "area"),
	ROOMFLOOR("所在楼层", "floor"),
	ROOMEXTINGUISHERSNUM("灭火器数量", "extinguishersNum"),
	ROOMEXTINGUISHERSWARRANTY("灭火器质保日期", "extinguishersWarranty"),
	ROOMHAVEWALLS("机房外围", "haveWalls");
	
	
	private String showName;
	private String hiddenName;
	private PhotoType(String showName,String hiddenName){
		this.showName = showName;
		this.hiddenName = hiddenName;
	}
	public static String getName(String hiddenName){
		LogUtil.info(hiddenName);
		for (PhotoType photoType : PhotoType.values()) {
			if (hiddenName.equals(photoType.getHiddenName())) {
				return photoType.showName;
			}
		}
		return PhotoType.getName("other");
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getHiddenName() {
		return hiddenName;
	}
	public void setHiddenName(String hiddenName) {
		this.hiddenName = hiddenName;
	}
	
}
