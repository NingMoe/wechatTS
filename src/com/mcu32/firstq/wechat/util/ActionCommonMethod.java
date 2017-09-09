package com.mcu32.firstq.wechat.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cglib.beans.BeanCopier;

import com.mcu32.firstq.common.bean.bo.ACDistributionBoxBO;
import com.mcu32.firstq.common.bean.bo.ACLineBO;
import com.mcu32.firstq.common.bean.bo.AirConditioningBO;
import com.mcu32.firstq.common.bean.bo.BaseDeviceBO;
import com.mcu32.firstq.common.bean.bo.BatteryBO;
import com.mcu32.firstq.common.bean.bo.DCDistributionBoxBO;
import com.mcu32.firstq.common.bean.bo.GroundingForLightningBO;
import com.mcu32.firstq.common.bean.bo.Monitoring;
import com.mcu32.firstq.common.bean.bo.PhotoBO;
import com.mcu32.firstq.common.bean.bo.PressureRegulatorBO;
import com.mcu32.firstq.common.bean.bo.SFUEquipmentBO;
import com.mcu32.firstq.common.bean.bo.SwitchPowerBO;
import com.mcu32.firstq.common.bean.bo.TransformerBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.bean.ACDistributionBox;
import com.mcu32.firstq.wechat.bean.ACLine;
import com.mcu32.firstq.wechat.bean.AirConditioning;
import com.mcu32.firstq.wechat.bean.BaseDevice;
import com.mcu32.firstq.wechat.bean.Battery;
import com.mcu32.firstq.wechat.bean.DCDistributionBox;
import com.mcu32.firstq.wechat.bean.GroundingForLightning;
import com.mcu32.firstq.wechat.bean.MonitoringInfo;
import com.mcu32.firstq.wechat.bean.PhotoInfo;
import com.mcu32.firstq.wechat.bean.PressureRegulator;
import com.mcu32.firstq.wechat.bean.SFUEquipment;
import com.mcu32.firstq.wechat.bean.SwitchPower;
import com.mcu32.firstq.wechat.bean.Transformer;
import com.mcu32.firstq.wechat.bean.UserInfo;


public class ActionCommonMethod {
	
	public static List<BaseDevice> getAllDevices(String stationId,String inspectionId,UserInfo user){
		List<BaseDevice> deviceList = new ArrayList<BaseDevice>();
		List<BaseDeviceBO> deviceBOs = null;
		try {
			deviceBOs = FirstQInterfaces.getIDeviceService().getAllBaseDevices(stationId, inspectionId);
		} catch (FirstQException e) {
			e.printStackTrace();
		}
		for (BaseDeviceBO baseDeviceBO : deviceBOs) {
				List<PhotoBO> photoBOList = baseDeviceBO.getPhotoList();
				List<PhotoInfo> photoInfoList = new ArrayList<PhotoInfo>();
				if (photoBOList != null && photoBOList.size() > 0) {
					for (PhotoBO photoBO : photoBOList) {
						PhotoInfo devicePhoto = new PhotoInfo();
						BeanCopier.create(PhotoBO.class, PhotoInfo.class, false).copy(photoBO, devicePhoto, null);
						photoInfoList.add(devicePhoto);
					}
					FirstqTool.convertPhotoPath(photoInfoList, user);
				}
				BaseDevice bd = null;
				if ("switch_power".equals(baseDeviceBO.getDeviceType())) {
					bd = new SwitchPower();
					BeanCopier.create(SwitchPowerBO.class, SwitchPower.class,false).copy(baseDeviceBO, bd, null);
				}else if ("battery".equals(baseDeviceBO.getDeviceType())) {
					bd = new Battery();
					BeanCopier.create(BatteryBO.class, Battery.class,false).copy(baseDeviceBO, bd, null);
				}else if ("air_conditioning".equals(baseDeviceBO.getDeviceType())) {
					bd = new AirConditioning();
					BeanCopier.create(AirConditioningBO.class, AirConditioning.class,false).copy(baseDeviceBO, bd, null);
				}else if ("ac_distribution".equals(baseDeviceBO.getDeviceType())) {
					bd = new ACDistributionBox();
					BeanCopier.create(ACDistributionBoxBO.class, ACDistributionBox.class,false).copy(baseDeviceBO, bd, null);
				}else if ("dc_distribution".equals(baseDeviceBO.getDeviceType())) {
					bd = new DCDistributionBox();
					BeanCopier.create(DCDistributionBoxBO.class, DCDistributionBox.class,false).copy(baseDeviceBO, bd, null);
				}else if ("transformer".equals(baseDeviceBO.getDeviceType())) {
					bd = new Transformer();
					BeanCopier.create(TransformerBO.class, Transformer.class,false).copy(baseDeviceBO, bd, null);
				}else if ("grounding_lightning".equals(baseDeviceBO.getDeviceType())) {
					bd = new GroundingForLightning();
					BeanCopier.create(GroundingForLightningBO.class, GroundingForLightning.class,false).copy(baseDeviceBO, bd, null);
				}else if ("pressure_regulator".equals(baseDeviceBO.getDeviceType())) {
					bd = new PressureRegulator();
					BeanCopier.create(PressureRegulatorBO.class, PressureRegulator.class,false).copy(baseDeviceBO, bd, null);
				}else if ("ac_line".equals(baseDeviceBO.getDeviceType())) {
					bd = new ACLine();
					BeanCopier.create(ACLineBO.class, ACLine.class,false).copy(baseDeviceBO, bd, null);
				}else if ("monitoring".equals(baseDeviceBO.getDeviceType())) {
					bd = new MonitoringInfo();
					BeanCopier.create(Monitoring.class, MonitoringInfo.class,false).copy(baseDeviceBO, bd, null);
				}else if ("FSU".equals(baseDeviceBO.getDeviceType())) {
					bd = new SFUEquipment();
					BeanCopier.create(SFUEquipmentBO.class, SFUEquipment.class,false).copy(baseDeviceBO, bd, null);
				}else {
					bd = new BaseDevice();
					BeanCopier.create(BaseDeviceBO.class, BaseDevice.class,false).copy(baseDeviceBO, bd, null);
				}
				bd.setPhotoList(photoInfoList);
				deviceList.add(bd);
			}
		return deviceList;
	}

}
