package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcu32.firstq.common.bean.bo.ManageBatteryRecordBO;
import com.mcu32.firstq.common.bean.bo.WeatherAlertsRealTimeBO;
import com.mcu32.firstq.common.bean.bo.WeatherAlertsTimerTaskBO;
import com.mcu32.firstq.common.dao.GeneratePowerRecordMapper;
import com.mcu32.firstq.common.dao.ManageBatteryRecordMapper;
import com.mcu32.firstq.common.service.IWeatherAlertsService;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.service.IManageBatteryRecordSendService;
import com.mcu32.firstq.wechat.service.IProclamationService;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.ShortUrlUtil;
import com.mcu32.firstq.wechat.util.ToolUtil;
import com.mcu32.firstq.wechat.util.date.DateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring*.xml" })
public class MybatisTest {
	@Autowired ManageBatteryRecordMapper manageBatteryRecordMapper;
	@Autowired	private GeneratePowerRecordMapper generatePowerRecordMapper;
	@Autowired	private IWeatherAlertsService weatherAlertsService;
	@Autowired private IManageBatteryRecordSendService bmrsService;
	@Autowired private IProclamationService proclamationService;
	//@Autowired private WeatherOrgMapper weaTherOrdMapper;
	
	@Test
	public void sendProclamation(){
		try {
			proclamationService.sendProclamation();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*@Test
	public void testGetWeatherOrg(){
		try {
			String dataJson="{'10101':'北京','10102':'上海','10103':'天津','10104':'重庆','10105':'黑龙江','10106':'吉林','10107':'辽宁','10108':'内蒙古','10109':'河北','10110':'山西','10111':'陕西','10112':'山东','10113':'新疆','10114':'西藏','10115':'青海','10116':'甘肃','10117':'宁夏','10118':'河南','10119':'江苏','10120':'湖北','10121':'浙江','10122':'安徽','10123':'福建','10124':'江西','10125':'湖南','10126':'贵州','10127':'四川','10128':'广东','10129':'云南','10130':'广西','10131':'海南','10132':'香港','10133':'澳门','10134':'台湾'}";
			JSONObject pjson=JSON.parseObject(dataJson);
			for(Map.Entry pe:pjson.entrySet()){
				String pk=(String)pe.getKey();
				String pv=(String)pe.getValue();
				WeatherOrg pwo=new WeatherOrg();
				pwo.setId(pk);
				pwo.setParentId("0");
				pwo.setName(pv);
				weaTherOrdMapper.insertSelective(pwo);
				String citydata=getWeatherDataProvshi(pk);
				JSONObject cityjson = JSON.parseObject(citydata);
				for(Map.Entry citye:cityjson.entrySet()){
					String cityk=(String)citye.getKey();
					String cityv=(String)citye.getValue();
					WeatherOrg citywo=new WeatherOrg();
					citywo.setId(pk+cityk);
					citywo.setParentId(pk);
					citywo.setName(cityv);
					weaTherOrdMapper.insertSelective(citywo);
					String contydata=getWeatherDataStation(citywo.getId());
					JSONObject contyjson = JSON.parseObject(contydata);
					for(Map.Entry contye:contyjson.entrySet()){
						String contyk=(String)contye.getKey();
						String contyv=(String)contye.getValue();
						WeatherOrg contywo=new WeatherOrg();
						if(contyk.startsWith(pk)){
							contywo.setId(contyk);
						}else{
							contywo.setId(citywo.getId()+contyk);
						}
						contywo.setParentId(citywo.getId());
						contywo.setName(contyv);
						weaTherOrdMapper.insertSelective(contywo);
					}
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	private String getWeatherDataProvshi(String areaId){
		try {
			return sendGet("http://www.weather.com.cn/data/city3jdata/provshi/"+areaId+".html?_=" + new Date().getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	private String getWeatherDataStation(String areaId){
		try {
			return sendGet("http://www.weather.com.cn/data/city3jdata/station/"+areaId+".html?_=" + new Date().getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("Charset", "utf-8");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
//	            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
          /*  for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

	
	private static String getToPageURL(String url){
		String encodeP="";
		try {
			encodeP = URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String holeURL="http://"+ToolUtil.getAppConfig("CallBackDomain")+"/"+ToolUtil.getAppConfig("WechatProjectName")+"/main/toRedirectPage?toPage="+encodeP;
		String shortUrl = ShortUrlUtil.creatShort(holeURL);
		return null==shortUrl?holeURL:shortUrl;
	}
	@Test
	public void sendWeatherTimerTaskToGroup(){
		LogUtil.info("开始每天定时查询");
		String uniqueId=DateUtil.DateToString(new Date(), "yyyyMMddHHmmssSSS");
		try {
			LogUtil.info("开始查询天气数据，uniqueId是"+uniqueId);
			List<WeatherAlertsTimerTaskBO> list=weatherAlertsService.getWeatherTimerTaskFromInterFace(uniqueId);
			if(null==list || list.size()==0)return;
			
			int[] wholeCountry={0,0,0,0};
			Map<String,int[]> areaSize= new HashMap<String,int[]>();
			for(int i=0;i<list.size();i++){
				WeatherAlertsTimerTaskBO watt=list.get(i);
				if("高温".equals(watt.getAlertType())|| "大雾".equals(watt.getAlertType()))
					continue;
				
				String weatherId=watt.getPageUrl().replace(".html", "");
				String[] alertInfo=weatherId.split("-");
				String provinceAreaId=alertInfo[0].substring(0, 5);
				String cityAreaId="";
				if(alertInfo[0].length()>=7){
					cityAreaId=alertInfo[0].substring(0, 7);
				}
	    		if("红色".equals(watt.getAlertLevel())){
	    			wholeCountry[0]++;
	    			if(areaSize.containsKey(provinceAreaId)){
	    				areaSize.get(provinceAreaId)[0]++;
	    			}else{
	    				areaSize.put(provinceAreaId, new int[]{1,0,0,0});
	    			}
	    			
	    			if(StringUtils.isNotEmpty(cityAreaId)){
		    			if(areaSize.containsKey(cityAreaId)){
		    				areaSize.get(cityAreaId)[0]++;
		    			}else{
		    				areaSize.put(cityAreaId, new int[]{1,0,0,0});
		    			}
	    			}
	    		}else if("橙色".equals(watt.getAlertLevel())){
	    			wholeCountry[1]++;
	    			if(areaSize.containsKey(provinceAreaId)){
	    				areaSize.get(provinceAreaId)[1]++;
	    			}else{
	    				areaSize.put(provinceAreaId, new int[]{0,1,0,0});
	    			}
	    			
	    			if(StringUtils.isNotEmpty(cityAreaId)){
		    			if(areaSize.containsKey(cityAreaId)){
		    				areaSize.get(cityAreaId)[1]++;
		    			}else{
		    				areaSize.put(cityAreaId, new int[]{0,1,0,0});
		    			}
	    			}
	    		}else if("黄色".equals(watt.getAlertLevel())){
	    			wholeCountry[2]++;
	    			if(areaSize.containsKey(provinceAreaId)){
	    				areaSize.get(provinceAreaId)[2]++;
	    			}else{
	    				areaSize.put(provinceAreaId, new int[]{0,0,1,0});
	    			}
	    			
	    			if(StringUtils.isNotEmpty(cityAreaId)){
		    			if(areaSize.containsKey(cityAreaId)){
		    				areaSize.get(cityAreaId)[2]++;
		    			}else{
		    				areaSize.put(cityAreaId, new int[]{0,0,1,0});
		    			}
	    			}
	    		}else if("蓝色".equals(watt.getAlertLevel())){
	    			wholeCountry[3]++;
	    			if(areaSize.containsKey(provinceAreaId)){
	    				areaSize.get(provinceAreaId)[3]++;
	    			}else{
	    				areaSize.put(provinceAreaId, new int[]{0,0,0,1});
	    			}
	    			
	    			if(StringUtils.isNotEmpty(cityAreaId)){
		    			if(areaSize.containsKey(cityAreaId)){
		    				areaSize.get(cityAreaId)[3]++;
		    			}else{
		    				areaSize.put(cityAreaId, new int[]{0,0,0,1});
		    			}
	    			}
	    		}
	    	}
			areaSize.put("全国", wholeCountry);
			List<String[]> robotlistInfo=FirstqTool.getAllConfigInfoByLevel("天气预警定时群");
			for(String[] robotInfoArr:robotlistInfo){
				String areaId=robotInfoArr[0];
				String chatroom=robotInfoArr[1];
				String robotDomain=robotInfoArr[2];
				String areaName=robotInfoArr[3];
				String robotName=robotInfoArr[4];
				
				int[] areaData=areaSize.get(areaId);
				String areaIdNotAll="全国".equals(areaId)?"":areaId;
				String purl=URLEncoder.encode("/main/toPageNotLocation?toPageUrl=damageWarning/damageWarningTimerTask?para="+uniqueId+"_"+areaIdNotAll, "utf-8");
				String pholeURL="http://"+ToolUtil.getAppConfig("CallBackDomain")+"/"+ToolUtil.getAppConfig("WechatProjectName")+"/main/toRedirectPage?toPage="+purl;
				String shortUrli = ShortUrlUtil.creatShort(pholeURL);
				if(null==shortUrli) shortUrli=pholeURL;
				
				String content="[灾害预警定时提醒]\n截止当前时间"+areaName+"共发布红色预警"+0+"条，橙色预警"+0+"条，详情点击"+shortUrli;
				if(null!=areaData){
					content="[灾害预警定时提醒]\n截止当前时间"+areaName+"共发布红色预警"+areaData[0]+"条，橙色预警"+areaData[1]+"条，详情点击"+shortUrli;
				}
				
				if(StringUtils.isNotEmpty(robotDomain)&&StringUtils.isNotEmpty(chatroom)){
					JSONObject rm=FirstqTool.sendMessageToAppointedWechatGroup(robotDomain,content,chatroom,robotName);
					if(null==rm || rm.isEmpty()){
						LogUtil.info("发送失败，"+robotDomain+","+chatroom);
					}else{
						LogUtil.info("天气预警推送成功，"+robotDomain+","+chatroom);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void checkWeatherAlerts() {
		System.out.println("开始5分钟实时查询");
		String uniqueId=DateUtil.DateToString(new Date(), "yyyyMMddHHmmssSSS");
		List<WeatherAlertsRealTimeBO> list=weatherAlertsService.getWeatherRealTimeFromInterFace(uniqueId);
		if(null==list || list.size()==0)return;
		LogUtil.info("开始处理需要提醒的天气数据");
		StringBuffer widsb=new StringBuffer();
		try {
			String msg="";
			for(WeatherAlertsRealTimeBO wart:list){
				widsb.append(",'"+wart.getWeatherId()+"'");
				
				/*if("高温".equals(wart.getAlertType())|| "大雾".equals(wart.getAlertType()))
					continue;
				
				String inHoleUrl="http://www.weather.com.cn/alarm/newalarmcontent.shtml?file="+wart.getPageUrl();
        		String shortUrl = ShortUrlUtil.creatShort(inHoleUrl);
        		if(null==shortUrl) shortUrl=inHoleUrl;
        		msg="[灾害预警实时提醒]\n【"+wart.getAlertName()+"】，详情点击"+shortUrl;
        		
        		if( ("红色".equals(wart.getAlertLevel()))){
        			LogUtil.info("有红色预警，给全国发消息");
    				JSONArray jaa=FirstqTool.sendWAToWechatGroup(msg,"全国");
    				LogUtil.info("有红色预警，给全国发消息的结果为："+jaa.toJSONString());
        		}
        		
        		String weatherId=wart.getPageUrl().replace(".html", "");
				String[] alertInfo=weatherId.split("-");
				String provinceId=alertInfo[0].substring(0, 5);
				
				LogUtil.info("遍历提醒的信息为："+wart.getAlertName()+"，向机器人发消息"+msg);
				if(alertInfo[0].length()>=7){
					String cityAreaId=alertInfo[0].substring(0, 7);
					//JSONArray jaa=FirstqTool.sendWAToWechatGroup(msg,wart.getAlertLevel(),cityAreaId);
					//LogUtil.info("遍历提醒，发消息的结果为："+jaa.toJSONString());
				}*/
				//JSONArray jaa=FirstqTool.sendWAToWechatGroup(msg,wart.getAlertLevel(),provinceId);
				//LogUtil.info("遍历提醒，发消息的结果为："+jaa.toJSONString());
			}
        	if(widsb.length()>0){
	        	widsb.deleteCharAt(0);
	        	FirstQInterfaces.getIWeatherAlertsService().updateToAltertById(widsb.toString());
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void name() {
		Map<String,String> m=new HashMap<String,String>();
		m.put("week", "thisweek");
		List<ManageBatteryRecordBO> manageBatteryRecordProvinceList = manageBatteryRecordMapper.manageBatteryRecordlist_J(m);
		System.out.println(manageBatteryRecordProvinceList.size());
		
	}
	@Test
	public void testInsert() {
		/*GeneratePowerRecordBO generatePowerRecord=new GeneratePowerRecordBO();
		generatePowerRecord.setRecordId("111111111111111111111111111111111");
		generatePowerRecord.setStationId("9cb46d7e-4214-4c90-880a-300b6aaeb1de");
		generatePowerRecord.setMasterRecordId("41e9964f-17cc-42e0-b860-234a55d7b006");
		try {
			int num = generatePowerRecordMapper.addGeneratePowerRecord(generatePowerRecord);
			System.out.println(num);
		} catch (FirstQException e) {
			e.printStackTrace();
		}*/
		bmrsService.sendThisWeekCityEmail();
		//bmrsService.sendManageBatteryNotFill();
//		bmrsService.sendThisWeekCityEmail();//蓄电池整治全国
//		
//		bmrsService.sendThisWeekCountyEmail();//蓄电池整治全国
	}
	
}
