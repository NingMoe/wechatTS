package com.mcu32.firstq.wechat.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.mcu32.firstq.wechat.bean.SelectEntity;
import com.mcu32.firstq.wechat.bean.SetParameterRecord;
import com.mcu32.firstq.wechat.service.IFixParameterService;

@Service
public class IFixParameterServiceImpl implements IFixParameterService {

	@Override
	public List<SelectEntity> getPowerBrandList() {
		List<SelectEntity> powerBrands = new ArrayList<SelectEntity>();
		Document document = getDocumentByXML("parameter-config.xml");
		Element node = document.getRootElement();
		@SuppressWarnings("unchecked")
		Iterator<Element> it = node.elementIterator();
        while (it.hasNext()) {  
        	Element e = it.next();   // 获取某个子节点对象  
        	SelectEntity se = new SelectEntity();
        	se.setId(e.attributeValue("id"));
        	se.setName(e.attributeValue("value"));
        	powerBrands.add(se);
        }  
		return powerBrands;
	}

	@Override
	public List<SelectEntity> getPowerType(String brandId) {
		List<SelectEntity> typeList = new ArrayList<SelectEntity>();
		Document document = getDocumentByXML("parameter-config.xml");
		Element element = (Element) document.getRootElement().selectSingleNode("//brand[@id='"+brandId+"']");
		@SuppressWarnings("unchecked")
    	Iterator<Element> it = element.elementIterator();
    	while (it.hasNext()) {
			Element e = it.next();
			SelectEntity se = new SelectEntity();
			se.setId(e.attributeValue("id"));
			se.setName(e.attributeValue("id"));
			typeList.add(se);
		}
		return typeList;
	}

	@Override
	public List<SelectEntity> getBatteryBrand() {
		List<SelectEntity> batteryList = new ArrayList<SelectEntity>();
		Document document = getDocumentByXML("calculate-config.xml");
		@SuppressWarnings("unchecked")
		List<Element> elementList = document.getRootElement().selectNodes("//battery-brand");
		for (Element element2 : elementList) {
			SelectEntity se = new SelectEntity();
			se.setId(element2.attributeValue("name"));
			se.setName(element2.attributeValue("name"));
			batteryList.add(se);
		}
		return batteryList;
	}

	@Override
	public List<SelectEntity> getParameter(HttpServletRequest request) {
		List<SelectEntity> calculateResult = new ArrayList<SelectEntity>();
		SetParameterRecord bParameter = new SetParameterRecord();
		bParameter.setStationId(request.getParameter("stationId"));
		bParameter.setPowerBrand(request.getParameter("powerBrand"));
		bParameter.setPowerType(request.getParameter("powerType"));
		bParameter.setBatteryBrand(request.getParameter("batteryBrand"));
		bParameter.setProductionDate(getDate(request, "batterDate"));
		bParameter.setMonthAvgPoweroff(request.getParameter("monthAvgPoweroff"));
		bParameter.setOnceAvgTime(request.getParameter("onceAvgTime"));
		bParameter.setEqualizingChargeTimeInput(request.getParameter("equalizingChargeTimeInput"));
		
		Document document = getDocumentByXML("parameter-config.xml");
		Element element = (Element) document.getRootElement().selectSingleNode("//modelNo[@id='"+bParameter.getPowerType()+"']");
		@SuppressWarnings("unchecked")
    	Iterator<Element> it = element.elementIterator();
    	while (it.hasNext()) {
			Element e = it.next();
			SelectEntity se = new SelectEntity();
			se.setId(e.attributeValue("unit"));
			se.setName(e.attributeValue("show"));
			se.setNodeName(e.getName());
			calculateResult.add(se);
		}
    	calculateResult = calculate(calculateResult,bParameter);
		return calculateResult;
	}

	
	public  Document getDocumentByXML(String fileName){
		Resource resource = new ClassPathResource("/parameter/"+fileName);
		SAXReader reader  = new SAXReader();
		Document document = null;
		try {
			document = reader.read(resource.getInputStream());
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}
	
	/**
	 * 根据显示字段来计算每个显示的结果
	 * 
	 * **/
	public List<SelectEntity> calculate(List<SelectEntity> calculateResult,SetParameterRecord bParameter){
		
		Document document = getDocumentByXML("calculate-config.xml");
		Element root = document.getRootElement();
		
		//计算是不是质保期内
		int shelfLifeInt = Integer.parseInt(((Element)root.selectSingleNode("//shelf-life")).attributeValue("value"));
		
		String shelfLifeStr = "";
		if (bParameter.getProductionDate() != null) {
			shelfLifeStr = Calendar.getInstance().getTime().getTime() <= justicYear(bParameter.getProductionDate(), shelfLifeInt) ? "in" : "out";
		}
		
		for (SelectEntity selectEntity : calculateResult) {
			String cString = "";
			if ("equalizing_charge_voltage".equals(selectEntity.getNodeName())) {
				if (!"".equals(shelfLifeStr)) {
					cString = ((Element)root.selectSingleNode("//battery-brand[@name='"+bParameter.getBatteryBrand()+"']/equalized-float[@shelf-life='"+shelfLifeStr+"']")).attributeValue("equalized");
					bParameter.setEqualizingChargeVoltage(cString);
				}
			}
			if ("floating_charge_voltage".equals(selectEntity.getNodeName())) {
				if (!"".equals(shelfLifeStr)) {
					cString = ((Element)root.selectSingleNode("//battery-brand[@name='"+bParameter.getBatteryBrand()+"']/equalized-float[@shelf-life='"+shelfLifeStr+"']")).attributeValue("float");
					bParameter.setFloatingChargeVoltage(cString);
				}
			}
			if ("charging_current_limiter".equals(selectEntity.getNodeName())) {
				if (!"".equals(bParameter.getMonthAvgPoweroff()) && Double.parseDouble(bParameter.getMonthAvgPoweroff()) < 3) {
					cString = "0.10";
				}else if (!"".equals(bParameter.getOnceAvgTime()) && Double.parseDouble(bParameter.getOnceAvgTime()) <= 3) {
					cString = "0.10";
				}else if (!"".equals(bParameter.getOnceAvgTime()) && Double.parseDouble(bParameter.getOnceAvgTime()) <= 5) {
					cString = "0.11";
				}else if (!"".equals(bParameter.getOnceAvgTime()) && Double.parseDouble(bParameter.getOnceAvgTime()) <= 10) {
					cString = "0.12";
				}else if(!"".equals(bParameter.getOnceAvgTime())){
					cString = "0.13";
				}
				bParameter.setChargingCurrentLimiter(cString);
			}
			if ("equalizing_charge_time".equals(selectEntity.getNodeName())) {
				if (!"".equals(bParameter.getEqualizingChargeTimeInput())) {
					if (Double.parseDouble(bParameter.getEqualizingChargeTimeInput()) < 12) {
						if ("m".equals(selectEntity.getId())) {
							cString = (12*60)+"";
						}else {
							cString = 12+"";
						}
					}else if (Double.parseDouble(bParameter.getEqualizingChargeTimeInput()) >= 12 && Double.parseDouble(bParameter.getEqualizingChargeTimeInput()) <=14 ) {
						if ("m".equals(selectEntity.getId())) {
							cString = (Double.parseDouble(bParameter.getEqualizingChargeTimeInput())*60)+"";
						}else {
							cString = bParameter.getEqualizingChargeTimeInput()+"";
						}
					}else {
						if ("m".equals(selectEntity.getId())) {
							cString = (14*60)+"";
						}else {
							cString = 14+"";
						}
					}
				}
				bParameter.setEqualizingChargeTime(cString);
			}
			if ("equalizing_charge_loop".equals(selectEntity.getNodeName())) {
				if (bParameter.getProductionDate() != null) {
					if (Calendar.getInstance().getTime().getTime() <= justicYear(bParameter.getProductionDate(), 3)) {
						if ("m".equals(selectEntity.getId())) {
							cString = (90*24*60)+"";
						}else if ("h".equals(selectEntity.getId())) {
							cString = (90*24)+"";
						}else if ("d".equals(selectEntity.getId())) {
							cString = 90+"";
						}else if ("w".equals(selectEntity.getId())){
							cString = (90/7)+"";
						}else {
							cString = "3";
						}
					}else if (Calendar.getInstance().getTime().getTime() > justicYear(bParameter.getProductionDate(), 3) && Calendar.getInstance().getTime().getTime() <= justicYear(bParameter.getProductionDate(), 4)) {
						if ("m".equals(selectEntity.getId())) {
							cString = (45*24*60)+"";
						}else if ("h".equals(selectEntity.getId())) {
							cString = (45*24)+"";
						}else if ("d".equals(selectEntity.getId())) {
							cString = 45+"";
						}else if ("w".equals(selectEntity.getId())){
							cString = (45/7)+"";
						}else {
							cString = "2";
						}
					}else {
						if ("m".equals(selectEntity.getId())) {
							cString = (30*24*60)+"";
						}else if ("h".equals(selectEntity.getId())) {
							cString = (30*24)+"";
						}else if ("d".equals(selectEntity.getId())) {
							cString = 30+"";
						}else if ("w".equals(selectEntity.getId())){
							cString = (30/7)+"";
						}else {
							cString = "1";
						}
					}
				}
				bParameter.setEqualizingChargeLoop(cString);
			}
			if ("transform_equalizing_current".equals(selectEntity.getNodeName())) {
				if ("A".equals(selectEntity.getId())) {
					cString = "20";
				}
				if ("C10".equals(selectEntity.getId())) {
					cString = "0.04";
				}
				bParameter.setTransformEqualizingCurrent(cString);
			}
			if ("transform_equalizing_capacity".equals(selectEntity.getNodeName())) {
				cString = "95";
				bParameter.setTransformEqualizingCapacity(cString);
			}
			if ("equalizing_voltage_start".equals(selectEntity.getNodeName())) {
				if ("V".equals(selectEntity.getId())) {
					cString = "49.5";
				}
				if ("%".equals(selectEntity.getId())) {
					cString = "90";
				}
				bParameter.setEqualizingVoltageStart(cString);
			}
			selectEntity.setCalculateResult(cString);
		}
		return calculateResult;
	}
	
	
	
	
	
	public  void listNodes(Element node) {  
        System.out.println("当前节点的名称：：" + node.getName());  
        // 获取当前节点的所有属性节点  
        @SuppressWarnings("unchecked")
        List<Attribute> list = node.attributes();
        // 遍历属性节点  
        for (Attribute attr : list) {  
            System.out.println(attr.getText() + "-----" + attr.getName()  + "---" + attr.getValue());  
        }  
  
        if (!(node.getTextTrim().equals(""))) {  
            System.out.println("文本内容：：：：" + node.getText());  
        }  
  
        // 当前节点下面子节点迭代器  
        @SuppressWarnings("unchecked")
        Iterator<Element> it = node.elementIterator();  
        // 遍历  
        while (it.hasNext()) {  
            // 获取某个子节点对象  
            Element e = it.next();  
            // 对子节点进行遍历  
            listNodes(e);  
        }  
    }
	
	
	public  Date getDate(HttpServletRequest request, String paraName) {  
        String tempStr = request.getParameter(paraName);  
        if (StringUtils.isBlank(tempStr)) {  
            return null;  
        }  
        try {  
            return new SimpleDateFormat("yyyy-MM-dd").parse(tempStr);  
        } catch (Exception e) {  
            return null;  
        }  
    } 	
	
	public  Float getFloatNull(HttpServletRequest request, String paraName) {  
        String tempStr = request.getParameter(paraName);  
        if (StringUtils.isBlank(tempStr)) {  
            return Float.NaN;  
        }  
        return Float.parseFloat(tempStr);  
    }
	
	/**
	 * 时间加几年，返回date
	 * */
	public  Long justicYear(Date date,int later){
		Calendar old = Calendar.getInstance();
		old.setTime(date);
		old.add(Calendar.YEAR, later);
		return old.getTime().getTime();
	}
}
