package com.mcu32.firstq.wechat.util.export;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mcu32.firstq.wechat.bean.YHExportDetail;

public class ExportDoc {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {

		exportDoc();
	}
	
	public static String exportDoc(){
		String filePath = "D:/存量站蓄电池隐患排查记录表-保定曲阳产德基站.doc";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("stationNo", "电信河北保定自有000756");
		dataMap.put("stationName", "保定曲阳产德基站");
		dataMap.put("stationAddress", "信息路 1号院-2号楼");
		dataMap.put("loadCurrent", "20.000");
		dataMap.put("mobileOperator", "移动,电信");
		
		dataMap.put("roomStructure", "砖混");
		
		dataMap.put("startdate", "2015/9/1");
		dataMap.put("batteryBrand", "光宇");
		dataMap.put("batteryModel", "GFM-100");
		dataMap.put("groupNum", "2");
		dataMap.put("singleCapacity", "11");
		dataMap.put("floatingChargeVoltage", "1.000");
		dataMap.put("floatingChargeCurrent", "2.000");
		dataMap.put("isInflation", "是");
		dataMap.put("inflationNum", "1");
		dataMap.put("isCracked", "是");
		dataMap.put("crackedNum", "1");
		dataMap.put("isLeakage", "壳体");
		dataMap.put("leakageNum", "1");
		dataMap.put("bondingStrip", "腐蚀");
		dataMap.put("batteryRemark", "这是备注信息。。。。。。。。。。。。。。。。");
		
		dataMap.put("terminalVoltage", "50");
		List<YHExportDetail> battertDetal = new ArrayList<YHExportDetail>();
		for (int i = 1; i <= 12; i++) {
			YHExportDetail yDetail = new YHExportDetail();
			yDetail.setFirstSeq1(i);
			yDetail.setFirestDetail1("");
			yDetail.setFirstSeq2(i+12);
			yDetail.setFirstDetail2(i+12+"");
			
			yDetail.setSecondSeq1(i);
			yDetail.setSecondDetail1(i+"");
			yDetail.setSecondSeq2(i+12);
			yDetail.setSecondDetail2(i+12+"");
			battertDetal.add(yDetail);
		}

		dataMap.put("battertDetal", battertDetal);
		
		MDoc mdoc = new MDoc();
		try {
			mdoc.createDoc(dataMap, filePath);
			return filePath;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

}
