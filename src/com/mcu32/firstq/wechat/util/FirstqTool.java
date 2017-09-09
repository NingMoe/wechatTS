package com.mcu32.firstq.wechat.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.io.SAXReader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mcu32.firstq.common.bean.bo.YhPhotoInfoBO;
import com.mcu32.firstq.common.util.LogUtil;
import com.mcu32.firstq.wechat.bean.PhotoInfo;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.tools.TemplateMessages;

public class FirstqTool {
	/**
	 * 把jobid变成string
	 */
	public static String jobIdToString(String jobId) {
		if ("1".equals(jobId)) {
			return "巡检";
		} else if ("2".equals(jobId)) {
			return "发电";
		} else if ("3".equals(jobId)) {
			return "设备维护";
		} else if ("4".equals(jobId)) {
			return "故障处理";
		} else if ("5".equals(jobId)) {
			return "新装设备";
		} else if ("6".equals(jobId)) {
			return "割接熔纤";
		} else if ("7".equals(jobId)) {
			return "更换电池";
		} else if ("8".equals(jobId)) {
			return "随工";
		} else {
			return "其他";
		}
	}

	/**
	 * 把异常项的id转换成String
	 */
	public static String abnormalCodeToString(String abnormalCode) {
		// 1:开关电源，2.蓄电池，3.空调
		if ("11".equals(abnormalCode)) {
			return "防雷模块显示异常";
		} else if ("12".equals(abnormalCode)) {
			return "模块功能异常";
		} else if ("13".equals(abnormalCode)) {
			return "监控显示屏异常";
		} else if ("21".equals(abnormalCode)) {
			return "外观变形";
		} else if ("22".equals(abnormalCode)) {
			return "漏液";
		} else if ("23".equals(abnormalCode)) {
			return "连接片腐蚀";
		} else if ("24".equals(abnormalCode)) {
			return "蓄电池链接部位沉重塌陷变形";
		} else if ("31".equals(abnormalCode)) {
			return "出现制冷故障";
		} else if ("32".equals(abnormalCode)) {
			return "室外机破损";
		} else if ("33".equals(abnormalCode)) {
			return "室外机变形";
		} else if ("34".equals(abnormalCode)) {
			return "室外机被盗";
		} else if("41".equals(abnormalCode)){
			return "无塔高";
		} else if("42".equals(abnormalCode)){
			return "校正经纬度";
		} else if("43".equals(abnormalCode)){
			return "无塔型";
		} else {
			// 00
			return "其他";
		}
	}

	public static String getResultByPoints(double lat, double lng,String coordtype) throws Exception {
		String urlStr = "http://api.map.baidu.com/geocoder/v2/?ak=D7bc6a051e5ac6d616f273528ac5616a&location="
				+ lat + ","
				+ lng + "&output=json&pois=1&coordtype="
				+ coordtype;
		LogUtil.info(urlStr);
		String resultInfo = HttpClientUtil.getResult(urlStr, "", "POST","utf-8");
		JSONObject infoJson = JSONObject.parseObject(resultInfo);
		return infoJson.getString("result");
	}

	public static Map<String, Double> getbaiduFromGps(double lat, double lng) {
		Map<String, Double> resultMap = new HashMap<String, Double>();
		String urlStr = "http://api.map.baidu.com/geoconv/v1/?coords=" + lng
				+ "," + lat
				+ "&from=1&to=5&ak=D7bc6a051e5ac6d616f273528ac5616a";
		String resultInfo = null;
		try {
			resultInfo = HttpClientUtil.getResult(urlStr, "","POST", "utf-8");
		} catch (Exception e) {
			LogUtil.error("调用百度API发生错误", e);
		}
		if (resultInfo != null) {
			JSONObject infoJson = JSONObject.parseObject(resultInfo);
			JSONObject points = (JSONObject) JSONObject.parseArray(
					infoJson.getString("result")).get(0);
			resultMap.put("lng", points.getDouble("x"));
			resultMap.put("lat", points.getDouble("y"));
		}
		return resultMap;
	}

	public static void convertPhotoPath(List<PhotoInfo> photoList,UserInfo userInfo) {
		if (photoList != null) {
			for (PhotoInfo pi : photoList) {
				if (pi.getStatus().equals(WebContentConstants.STATUS_WAIT_UPLOAD)) {
					if (userInfo != null && pi.getCreateUserId().equals(userInfo.getUserId())) {
						pi.setThumbLocation(pi.getLocalId());
						pi.setFileLocation(pi.getLocalId());
					} else {
						String callBackDomain = ToolUtil.getAppConfig("CallBackDomain");
						String waitUploadImg = "http://" + callBackDomain+ "/wechat/assets/i/waitUpload.png";
						pi.setThumbLocation(waitUploadImg);
						pi.setFileLocation(waitUploadImg);
					}
				} else {
					String thumbLocation = getPhotoLocation(pi.getThumbLocation());
					String realLocation = getPhotoLocation(pi.getFileLocation());
					pi.setThumbLocation(thumbLocation);
					pi.setFileLocation(realLocation);
				}
			}
		}
	}
	
	public static void convertPhotoPath(List<YhPhotoInfoBO> photoList) {
		if (photoList != null) {
			for (YhPhotoInfoBO pi : photoList) {
				String thumbLocation = getPhotoLocation(pi.getThumbLocation());
				String realLocation = getPhotoLocation(pi.getFileLocation());
				pi.setThumbLocation(thumbLocation);
				pi.setFileLocation(realLocation);
			}
		}
	}

	public static String getPhotoLocation(String location) {
		if (location != null && location.indexOf("uploadFile") > 0) {
			String callBackDomain = ToolUtil.getAppConfig("CallBackDomain");
			String domain = "http://" + callBackDomain + "/services/";
			String url = location.substring(location.indexOf("uploadFile"),location.length());
			return domain + url;
		}
		return "";
	}
	
	
	public static Document getDocumentByXML(String fileName){
		Document document = null;
		try {
			URL fileUrl = ClassLoaderUtil.getExtendResource(fileName);
			SAXReader reader  = new SAXReader();
			document = reader.read(fileUrl.openStream());
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return document;
	}
	
	private static Document wechatRoomNoticeConfig=null;
	@SuppressWarnings("unused")
	private static Document getWechatRoomNoticeConfig(){
		if(null==wechatRoomNoticeConfig)
			wechatRoomNoticeConfig=FirstqTool.getDocumentByXML("wechatRoomNoticeConfig.xml");
		return wechatRoomNoticeConfig;
	}
	/**
	 * @return {"name","chatroom","robot-domain"}
	 */
	
	@SuppressWarnings("unchecked")
	public static List<String[]> getAllConfigInfoByLevel(String name) {
		Document document = FirstqTool.getDocumentByXML("wechatRoomNoticeConfig.xml");
    	Element root = document.getRootElement();
    	
    	/*获取所有的机器人名称和域名配置*/
    	Map<String,String> robotsMap =new HashMap<String,String>();
		List<Element> robotsE = root.elements(new QName("robot"));
    	for(Element robotCfg:robotsE){
    		String robotName=robotCfg.attributeValue("name");
    		String robotDomain=robotCfg.attributeValue("domain");
    		robotsMap.put(robotName, robotDomain);
    	}
    	
    	List<String[]> nodeList=new ArrayList<String[]>();
    	
    	List<Node> firstNodes=root.selectNodes("level[contains(@name,'" + name + "')]");
    	
		List<Node> holeNodes=new ArrayList<Node>();
		holeNodes.addAll(firstNodes);
		
		List<Node> levelNodes=new ArrayList<Node>();
		levelNodes.addAll(firstNodes);
		List<Node> secondNodes=new ArrayList<Node>();
		int levelNodesSize=levelNodes.size();
		
		for(int i=0;i<levelNodes.size();i++){
			Element levele=(Element)levelNodes.get(i);
			Iterator<Element> firstEI = levele.elementIterator();
			while(firstEI.hasNext()){
				Element firstE = firstEI.next();
				secondNodes.add(firstE);
			}
			if(i==levelNodesSize-1){
				holeNodes.addAll(secondNodes);
				levelNodes=secondNodes;
				secondNodes=new ArrayList<Node>();
				levelNodesSize=levelNodes.size();
				i=-1;
			}
		}
    	setNodeList(holeNodes,robotsMap,nodeList);
    	return nodeList;
	}
	public static String getWeatherSendTime(){
		String time = "";
		Document document = FirstqTool.getDocumentByXML("wechatRoomNoticeConfig.xml");
    	Element root = document.getRootElement();
		List<Element> weaEles = root.elements(new QName("weatherSendTime"));
		if(weaEles != null && weaEles.size()>0){
			Element weatherEle = weaEles.get(0);
			time = weatherEle.getText();
		}
		return time;
	}
	@SuppressWarnings("unchecked")
	public static void checkRobotIsLogin() {
		Document document = FirstqTool.getDocumentByXML("wechatRoomNoticeConfig.xml");
    	Element root = document.getRootElement();
    	
    	/*获取所有的机器人名称和域名配置*/
    	Map<String,String> robotsMap =new HashMap<String,String>();
		List<Element> robotsE = root.elements(new QName("robot"));
    	for(Element robotCfg:robotsE){
    		String robotName=robotCfg.attributeValue("name");
    		String robotDomain=robotCfg.attributeValue("domain");
    		robotsMap.put(robotName, robotDomain);
    	}
    	
		Set<String> userSet=new HashSet<String>();
		
		List<Map<String,String>> robotInfoList=new ArrayList<Map<String,String>>();
		List<Element> checkRobotStatusE = root.elements(new QName("check-robot-status"));
    	for(Element checkRobotStatusECfg:checkRobotStatusE){
    		String robotName =checkRobotStatusECfg.attributeValue("robot-name");
    		String chatroom=checkRobotStatusECfg.attributeValue("chatroom");
    		String user =checkRobotStatusECfg.attributeValue("user");
    		userSet.add(user);
    		String robotDomain=robotsMap.get(robotName);
    		
    		if(StringUtils.isNotEmpty(robotDomain)&&StringUtils.isNotEmpty(chatroom)){
    			
	    		Map<String,String> robotinfo=new HashMap<String,String>();
	    		robotinfo.put("robotName", robotName);
	    		robotinfo.put("chatroom", chatroom);
	    		robotinfo.put("robotDomain", robotDomain);
	    		
	    		robotInfoList.add(robotinfo);
    		}
    	}
    	
    	String users=StringUtils.join(userSet,",");
    	for(Map<String,String> robotinfo:robotInfoList){
    		String robotName=robotinfo.get("robotName");
    		String chatroom=robotinfo.get("chatroom");
    		String robotDomain=robotinfo.get("robotDomain");
    		
    		if(!checkRobotStatus(robotName,robotDomain,chatroom)){
    			try {
					Thread.sleep(30000);
					if(!checkRobotStatus(robotName,robotDomain,chatroom)){
						TemplateMessages.sendOffLine("机器人 "+robotName+"  请求出现异常，请进行查看", users);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	
		//[{"chatroom":"机器人测试","MsgID":"4423031075907347320","BaseResponse":{"ErrMsg":"","Ret":0},"LocalID":"1467097487021"}]
    	//{"MsgID":"","BaseResponse":{"ErrMsg":"","Ret":1101},"LocalID":""}
	}
	private static boolean checkRobotStatus(String robotName,String robotDomain,String chatroom){
		String content="检查掉线";
		//System.out.println(robotName+","+robotDomain+","+chatroom);
		JSONObject message=new JSONObject();
		try {
			JSONObject rm=sendMessageToAppointedWechatGroup(robotDomain,content,chatroom);
			if(null!=rm) message=rm;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			/*msg+="机器人 "+robotName+"  请求出现异常\\n";
			robotErrormap.put(robotName, "error");*/
		}
		System.out.println(message.toJSONString());
		if(message.isEmpty()){
			/*msg+="机器人 "+robotName+"  请求未响应\\n";
			robotErrormap.put(robotName, "error");*/
			return false;
		}
		JSONObject BaseResponse=message.getJSONObject("BaseResponse");
		if(null==BaseResponse){
			/*msg+="机器人 "+robotName+"  没有得到正确的响应\\n";
			robotErrormap.put(robotName, "error");
			*/
			return false;
		}
		//String ErrMsg= BaseResponse.getString("ErrMsg");
		String Ret= BaseResponse.getString("Ret");
		if(!"0".equals(Ret)){
//			msg+="机器人 "+robotName+"  已经掉线\\n";
//			robotErrormap.put(robotName, "error");
			return false;
		}
		return true;
	}
	
	public static JSONArray sendQuestionToWechatGroup(String content,String... levels){
		String [] tmpLevels=new String[levels.length+1];
		tmpLevels[0]="优化建议";
		for(int i=0;i<levels.length;i++){
			tmpLevels[1+i]=levels[i];
		}
		return sendToWechatGroup(content,tmpLevels);
	}
	
	public static JSONArray sendGPToWechatGroup(String content,String... levels){
		String [] tmpLevels=new String[levels.length+1];
		tmpLevels[0]="发电";
		for(int i=0;i<levels.length;i++){
			tmpLevels[1+i]=levels[i];
		}
		return sendToWechatGroup(content,tmpLevels);
	}
	
	public static JSONArray sendMBToWechatGroup(String content,String... levels){
		String [] tmpLevels=new String[levels.length+1];
		tmpLevels[0]="蓄电池整治";
		for(int i=0;i<levels.length;i++){
			tmpLevels[1+i]=levels[i];
		}
		return sendToWechatGroup(content,tmpLevels);
	}
	public static JSONArray sendDSToWechatGroup(String content,String... levels){
		String [] tmpLevels=new String[levels.length+1];
		tmpLevels[0]="门禁项目建设进度";
		for(int i=0;i<levels.length;i++){
			tmpLevels[1+i]=levels[i];
		}
		return sendToWechatGroup(content,tmpLevels);
	}
	public static JSONArray sendMCToWechatGroup(String content,String... levels){
		String [] tmpLevels=new String[levels.length+1];
		tmpLevels[0]="代维费用";
		for(int i=0;i<levels.length;i++){
			tmpLevels[1+i]=levels[i];
		}
		return sendToWechatGroup(content,tmpLevels);
	}
	public static JSONArray sendFourHighToWechatGroup(String content,String... levels){
		String [] tmpLevels=new String[levels.length+1];
		tmpLevels[0]="公告";
		for(int i=0;i<levels.length;i++){
			tmpLevels[1+i]=levels[i];
		}
		return sendToWechatGroup(content,tmpLevels);
	}
	public static JSONArray sendWAToWechatGroup(String content, String... levels) {
		String [] tmpLevels=new String[levels.length+1];
		tmpLevels[0]="天气预警";
		for(int i=0;i<levels.length;i++){
			tmpLevels[1+i]=levels[i];
		}
		return sendToWechatGroup(content,tmpLevels);
	}
	@SuppressWarnings("unchecked")
	public static JSONArray sendToWechatGroup(String content,String... levels){
		//Document document = getWechatRoomNoticeConfig();
		
		Document document = FirstqTool.getDocumentByXML("wechatRoomNoticeConfig.xml");
    	Element root = document.getRootElement();
    	
    	/*获取所有的机器人名称和域名配置*/
    	Map<String,String> robotsMap =new HashMap<String,String>();
		List<Element> robotsE = root.elements(new QName("robot"));
    	for(Element robotCfg:robotsE){
    		String robotName=robotCfg.attributeValue("name");
    		String robotDomain=robotCfg.attributeValue("domain");
    		robotsMap.put(robotName, robotDomain);
    	}
        
    	Map<String,String> roomSet=new HashMap<String,String>();
    	if(null!=levels && levels.length>0){
    		List<Node> firstNodes=root.selectNodes("level[@name='" + levels[0] + "']");
    		List<Node> holeNodes=new ArrayList<Node>();
    		holeNodes.addAll(firstNodes);
    		
    		List<Node> levelNodes=new ArrayList<Node>();
    		levelNodes.addAll(firstNodes);
    		int levelSize=0;
    		for(int i=1;i<levels.length;i++){
    			for(Node nodec:levelNodes){
    				List<Node> nodes=nodec.selectNodes("level[@name='" + levels[i] + "']");
    				if(levelSize==i){
    					levelNodes.addAll(nodes);
    				}else{
    					levelNodes=nodes;
    					levelSize=i;
    				}
    				holeNodes.addAll(nodes);
    			}
    		}
    		setNodeSet(holeNodes,roomSet);
    	}
    	
		JSONArray returnmessage=new JSONArray(); 
		
		for (Map.Entry<String, String> entry : roomSet.entrySet()) {
			String chatroom = entry.getKey();
			String robotname = entry.getValue();
			
			String robotDomain=robotsMap.get(robotname);
			if(StringUtils.isNotEmpty(robotDomain)&&StringUtils.isNotEmpty(chatroom)){
				System.out.println(robotname+","+robotDomain+","+chatroom);
				JSONObject message=new JSONObject();
				try {
					JSONObject rm=sendMessageToAppointedWechatGroup(robotDomain,content,chatroom,robotname);
					if(null!=rm) message=rm;
				} catch (Exception e) {
					e.printStackTrace();
					message.put("chatroom", chatroom);
					message.put("robotname", robotname);
					returnmessage.add(message);
				}
				System.out.println(message.toJSONString());
				if(null!=message){
					message.put("chatroom", chatroom);
					message.put("robotname", robotname);
					returnmessage.add(message);
				}
			}
		}
		return returnmessage;
	}

	/**
	 * dom4j  Element el.nodeCount() 方法
	 * 得到的数值/2 是本节点包含的字节点数
	 * 得到的数值%2 如果为1说明 元素是<span></span> 形式，如果为0说明元素是 <span  />形式
	 * @param firstNode
	 * @param map
	 */
	private static void setNodeSet(List<Node> firstNode,Map<String,String> map){
		for(Node node:firstNode){
			if(node instanceof Element){
				Element elementTemp = (Element) node;
				//System.out.println(node.valueOf("@name")+","+elementTemp.nodeCount()/2+","+node.valueOf("@sendmsg"));
				if((Boolean.parseBoolean(node.valueOf("@receive-all"))||0==(elementTemp.nodeCount()/2)) && Boolean.parseBoolean(node.valueOf("@sendmsg"))){
					map.put(node.valueOf("@chatroom"),node.valueOf("@robot-name"));
				}
			}
		}
	}
	private static void setNodeList(List<Node> firstNode,Map<String, String> robotsMap, List<String[]> nodeList){
		for(Node node:firstNode){
			if(node instanceof Element){
				Element elementTemp = (Element) node;
				//System.out.println(node.valueOf("@name")+","+elementTemp.nodeCount()/2+","+node.valueOf("@sendmsg"));
				if((Boolean.parseBoolean(node.valueOf("@receive-all"))||0==(elementTemp.nodeCount()/2)) && Boolean.parseBoolean(node.valueOf("@sendmsg"))){
					String[] nodeInfo={node.valueOf("@name"),node.valueOf("@chatroom"),robotsMap.get(node.valueOf("@robot-name")),node.valueOf("@area-name"),node.valueOf("@robot-name")};
					nodeList.add(nodeInfo);
				}
			}
		}
	}
	
	public static JSONObject sendMessageToAppointedWechatGroup(String controllerPath,String... p){
		String url="?content={0}&nickName={1}&robotName={2}";
		
		try {
			for (int i = 0; i < p.length; i++){
				url = url.replace("{" + i + "}", URLEncoder.encode(URLEncoder.encode(p[i], "utf-8"),"utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			String responseStr = HttpClientUtil.getResult(controllerPath+url,"");
        	if(StringUtils.isNotEmpty(responseStr))
        		return JSON.parseObject(responseStr);
    		return null;
		} catch (Exception e) {
			return null; 
		}
	}
	@SuppressWarnings("unchecked")
	public static List<Element> getProclamationChatRoom(){
		Document document = FirstqTool.getDocumentByXML("wechatRoomNoticeConfig.xml");
    	Element root = document.getRootElement();
		List<Element> proclamationE= root.elements(new QName("proclamation"));
		return proclamationE;
	}
	@SuppressWarnings("unchecked")
	public static List<Map<String,String>> getProclamationByChatRoom(String chatroom){
		if(StringUtils.isEmpty(chatroom)) return null;
    	
		List<Element> proclamationE= getProclamationChatRoom();
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
    	for(Element proclamationCfg:proclamationE){
    		String nameStr=proclamationCfg.attributeValue("name");
    		String chatroomStr=proclamationCfg.attributeValue("chatroom");
    		String sendmsgStr=proclamationCfg.attributeValue("sendmsg");

    		boolean sendmsg = Boolean.parseBoolean(sendmsgStr);
    		if(chatroom.equals(chatroomStr) && sendmsg){
    			List<Node> listElement=proclamationCfg.elements();
    			for(Node node:listElement){
    				Map<String,String> map =new HashMap<String,String>();
        			map.put("content", node.valueOf("@content"));
        			map.put("url", node.valueOf("@url"));
        			map.put("province", node.valueOf("@province"));
        			map.put("city", node.valueOf("@city"));
        			map.put("rate", node.valueOf("@rate"));
        			list.add(map);
    			}
    		}
    	}
		return list;
	}
	
	public static String getToPageURL(String url){
		String encodeP="";
		try {
			encodeP = URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String holeURL="http://"+ToolUtil.getAppConfig("CallBackDomain")+"/"+ToolUtil.getAppConfig("WechatProjectName")+"/main/toRedirectPage?toPage="+encodeP;
		String shortUrl = ShortUrlUtil.creatShort(holeURL);
		System.out.println(holeURL);
		return null==shortUrl?holeURL:shortUrl;
	}
	
	public static String dealParmaEncode(String url){
		try {
			if(null==url|| url.length()<1) return null;
			
			String[] paramsStrArr=url.split("[?]");
			if(paramsStrArr.length>1){
				String paramsStr=paramsStrArr[1];
				String[] paramArr= paramsStr.split("&");
				for(String s:paramArr){
					String[] kvS=s.split("=");
					if(kvS.length>1)
						url=url.replace(kvS[1], URLEncoder.encode(kvS[1],"UTF-8"));
				}
			}
		} catch (Exception e) {
			LogUtil.info("解析toPageUrl出错！！！");
			e.printStackTrace();
		}
		return url;
	}
	public static String dealUrlEncode(String url){
		StringBuffer encodeUrl = new StringBuffer();
		try {
			if (url.indexOf("?") > -1) {
				encodeUrl.append(url.split("[?]")[0]);
				String params = url.split("[?]")[1];
				if (params.indexOf("&") > -1) {
					encodeUrl.append("?");
					String[] paramArr = params.split("&");
					for (String str : paramArr) {
						if(str.indexOf("=") > -1) {
							encodeUrl.append(str.split("=")[0])
									  .append("=")
									  .append(URLEncoder.encode(URLEncoder.encode(str.split("=")[1],"UTF-8"),"UTF-8"))
									  .append("&");
						}
					}
					
				}else if(params.indexOf("=") > -1) {
					encodeUrl.append("?")	
							  .append(params.split("=")[0])
							  .append("=")
							  .append(URLEncoder.encode(URLEncoder.encode(params.split("=")[1],"UTF-8"),"UTF-8"));
				}
			}else{
				encodeUrl.append(url);
			}
		} catch (Exception e) {
			LogUtil.info("解析toPageUrl出错！！！");
			e.printStackTrace();
		}
		return encodeUrl.toString();
	}
	
}
