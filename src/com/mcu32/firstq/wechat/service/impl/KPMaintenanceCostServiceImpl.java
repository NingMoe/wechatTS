package com.mcu32.firstq.wechat.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.mcu32.firstq.common.bean.bo.KPMaintenanceCostBO;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.service.IKPMaintenanceCostService;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.ShortUrlUtil;
import com.mcu32.firstq.wechat.util.ToolUtil;
@Service
public class KPMaintenanceCostServiceImpl implements IKPMaintenanceCostService {
	

	
	
	//////////////////////////////////////////////////////////
	/**
	 * 发送已完成的通知
	 */
	@Override
	public void sendMaintenanceCostIsFilled(){
		Map<String,String> qryMap=new HashMap<String,String>();
		qryMap.put("provinceid", "黑龙江省");
		try {
			String content="";
			String url=FirstqTool.getToPageURL("/main/toPage?toPageUrl=maintenancecost/goCost");
			Map<String,KPMaintenanceCostBO> mcProvinceMap= FirstQInterfaces.getIKPMaintenanceCostService().maintenanceCostMap(qryMap);
			
			if(mcProvinceMap!=null){
				String filledProvinceStr=StringUtils.join(mcProvinceMap.keySet(),",");
				content="[代维费用填报省级通知]\n截止当前，"+filledProvinceStr+" "+mcProvinceMap.keySet().size()+"个地市已经完成代维费用填报，详情请查看代维费用填报统计页面"+url;
				JSONArray j=FirstqTool.sendMCToWechatGroup(content,"黑龙江省");
				System.out.println(j.toJSONString());
				System.out.println(content);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
