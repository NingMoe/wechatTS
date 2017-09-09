package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mcu32.firstq.common.bean.bo.InspectionRoomTowerBO;
//import com.mcu32.firstq.common.bean.bo.WeatherOrg;
import com.mcu32.firstq.common.util.HttpClientUtil;
import com.mcu32.firstq.common.util.LogUtil;
import com.mcu32.firstq.wechat.bean.BaseDevice;
import com.mcu32.firstq.wechat.bean.Battery;
import com.mcu32.firstq.wechat.bean.RequestBaseStationsGetNearList;
import com.mcu32.firstq.wechat.bean.SwitchPower;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.tools.TemplateMessages;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.ShortUrlUtil;
import com.mcu32.firstq.wechat.util.ToolUtil;
import com.mcu32.firstq.wechat.util.date.DateUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class MainTest {
	@Test
	public void testPara(){
		String str="impTime:2016-08-04,province:上海,city:";
		
		String[] strarr=str.split(",");
		Map<String,String> map=new HashMap<String,String>();
		for(String ps:strarr){
			String[] kvArr=ps.split(":");
			String key=kvArr[0];
			String value=kvArr.length>1?kvArr[1]:"";
			map.put(key, value);
		}
		System.out.println(JSON.toJSONString(map));
		
		String jsonStr="{\""+str.replaceAll(":", "\":\"").replaceAll("," , "\",\"")+"\"}";
		Map<String,String> pmap = JSON.parseObject(jsonStr, Map.class);
		
		
		System.out.println(pmap.get("impTime"));
		
		String fileName="aa.bb";
		String imgType=fileName.substring(fileName.indexOf(".")+1);
		
		System.out.println(imgType);
	}
	
	
	@Test
	public void testP(){
		try {
			String content="groupName=测试交流";
			String j = HttpClientUtil.getResultByReqeustMethod("http://left.wicp.net/wechatTS/proclamation/getListTopThree.json", content, "utf-8","POST");
			System.out.println(j);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testTimer(){
		//FirstqTool.checkRobotIsLogin();
		///main/toPageWithUser?toPageUrl=http://www.czxkpower.com/wechat-cs/getDianBoMessage/getVoltageMessage?orgName=%E9%83%91%E5%B7%9E&currentDemand=5b071117-e320-462b-a4fa-1a0bb3d04855&time=2016-10-18 16:59
		try {
			String urlStr="main/toPageWithUser?toPageUrl=http://www.czxkpower.com/wechat-cs/getDianBoMessage/getVoltageMessage?orgName=%E9%83%91%E5%B7%9E&currentDemand=5b071117-e320-462b-a4fa-1a0bb3d04855&time=2016-10-18 16:59";
			String toPageUrl=urlStr.substring(urlStr.indexOf("=")+1);
			System.out.println(urlStr.substring(0, urlStr.indexOf("=")+1)+URLEncoder.encode(toPageUrl,"UTF-8"));
			
			System.out.println(URLEncoder.encode("郑州","UTF-8"));
			System.out.println(URLDecoder.decode("2016-10-18+16%3A59","UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testReplace(){
		String fullUrl="http://www.czxkpower.com/wechat-cs/getDianBoMessage/getVoltageMessage?orgName=郑州&currentDemand=5b071117-e320-462b-a4fa-1a0bb3d04855&time=2016-10-18 16:59";
		System.out.println(fullUrl.replace("郑州", "111"));
	}
	@Test
	public void testShortUrl() throws Exception {
		String inHoleUrl="http://www.weather.com.cn/alarm/newalarmcontent.shtml?file=101210203-20160615122500-0202.html";
		String shortUrl = ShortUrlUtil.currentShortUrl(inHoleUrl);
		System.out.println(inHoleUrl);
		System.out.println(shortUrl);
	}
	//
/*	@Test
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
				
				String citydata=getWeatherData(pk);
				JSONObject cityjson = JSON.parseObject(citydata);
				for(Map.Entry citye:cityjson.entrySet()){
					String cityk=(String)citye.getKey();
					String cityv=(String)citye.getValue();
					WeatherOrg citywo=new WeatherOrg();
					citywo.setId(cityk);
					citywo.setParentId(pk);
					citywo.setName(cityv);
					
					String contydata=getWeatherData(cityk);
					JSONObject contyjson = JSON.parseObject(contydata);
					for(Map.Entry contye:contyjson.entrySet()){
						String contyk=(String)contye.getKey();
						String contyv=(String)contye.getValue();
						WeatherOrg contywo=new WeatherOrg();
						contywo.setId(contyk);
						contywo.setParentId(cityk);
						contywo.setName(contyv);
						
					}
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	private String getWeatherData(String areaId){
		try {
			return HttpClientUtil.getResultByReqeustMethod("http://www.weather.com.cn/data/city3jdata/provshi/"+areaId+".html?_=" + new Date().getTime(), "", "utf-8","GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	//http://www.weather.com.cn/data/city3jdata/provshi/10101.html?_=1469428671546
	@Test
	public void testGetWeather() {
		long time=new Date().getTime();
		try {
			LogUtil.info("开始从天气网查询数据");
			Map<String,String> map=new HashMap<String,String>();
			map.put("Referer", "http://www.weather.com.cn/alarm/newalarmlist.shtml");
			String j = HttpClientUtil.getResultByReqeustMethod("http://product.weather.com.cn/alarm/grepalarm_cn.php?_="+time,"", "utf-8", "GET",map);
			/*ScriptEngineManager engineManager = new ScriptEngineManager();  
	        ScriptEngine engine = engineManager.getEngineByName("JavaScript"); //得到脚本引擎
	        engine.eval(j);
	        JSONObject json=(JSONObject)JSON.toJSON(engine.get("alarminfo"));*/
	        String jsonstring=j.substring(j.indexOf("=")+1, j.indexOf(";"));
	    	JSONObject json=JSON.parseObject(jsonstring);
	        System.out.println(json.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testTime() {
		long hourmi = 28800000;
		double restTime = 0;
		long timeD = new Date().getTime() - Long.parseLong("1464688491000");
		long restTimeM = hourmi - timeD;
		restTime = restTimeM / 1000.0 / 60.0 / 60.0;
		System.out.println(new Date().getTime());// 剩余小时数
		System.out.println(DateUtil.DateToString(new Date(), "yyyyMMddhhmmssSSS"));
	}

	@Test
	public void testSet() {
		Set<String> set = new HashSet<String>(); // 定义Set集合对象
		set.add("apple"); // 向集合中添加对象
		set.add("computer");
		set.add("book");

		Set<String> set2 = new HashSet<String>(); // 定义Set集合对象
		set2.add("apple"); // 向集合中添加对象
		set2.add("computer");
		set2.add("book");
		set2.add("book1");

		set.removeAll(set2);
		System.out.println(set);
		// List<String> list = new ArrayList<String>(set);//
		// System.out.println("数组的长度是："+list.get(0)); //输出数组长度

		Map<String, String> m = new HashMap<String, String>();
		m.put("1", "1");
		m.put("2", "1");
		m.put("3", "1");
		m.put("4", "1");
		m.put("5", "1");

		Map<String, String> m1 = new HashMap<String, String>();
		m1.put("1", "2");
		m1.put("2", "2");
		m1.put("3", "2");
		m1.put("4", "2");
		m1.put("5", "2");
		m1.put("6", "2");
		m1.put("7", "2");

		m.putAll(m1);

		System.out.println(m1.keySet().toString());
		List<String> mlist = new ArrayList<String>(m.values());
		System.out.println(StringUtils.join(mlist, ","));

		List<String> list = new ArrayList<String>();
		list.add("aaa");
		System.out.println(list.contains("aaa"));

		System.out.println(m.get("ssssssss"));
	}

	@Test
	public void StringBuilder() {
		String url = getToPageURL("/main/toPage?toPageUrl=report/manageBatteryRecordByProvince?selectId=2");
		System.out.println(url);

		System.out.println(Boolean.parseBoolean(null));
	}

	private static String getToPageURL(String url) {
		String encodeP = "";
		try {
			encodeP = URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String holeURL = ToolUtil.getAppConfig("CallBackDomain") + "/" + ToolUtil.getAppConfig("WechatProjectName")
				+ "/main/toRedirectPage?toPage=" + encodeP;
		String shortUrl = ShortUrlUtil.creatShort(holeURL);
		return null == shortUrl ? holeURL : shortUrl;
		
		
	}
	@Test
	public void testLog() {
		Logger logger = Logger.getLogger(MainTest.class);
		logger.info(" test ");
		System.out.println(File.separator);
	}

	@Test
	public void name() {
		int random = (int) (Math.random() * 999999);
		random = random < 100000 ? random += 100000 : random;
		System.out.println(random);
	}

	@Test
	public void sss() {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> m = new HashMap<String, String>();
		m.put("station_id", "46132156468532");
		m.put("station_name", "安国四站");
		list.add(m);
		Map<String, String> m2 = new HashMap<String, String>();
		m2.put("station_id", "46132156468532");
		m2.put("station_name", "安国七站");
		list.add(m2);
		JSONArray json = new JSONArray();
		json.addAll(list);
		System.out.println("{\"stations\":" + json.toString() + "}");

		Map<String, Object> m4 = new HashMap<String, Object>();
		m4.put("station_id", list);

		List<Object> date = (List<Object>) m4.get("ss");
		System.out.println(date);

	}

	@Test
	public void jsontest() {
		String inspection_id = "{\"inspection_id\":\"XXXXX\"}";
		// JSONObject json=JSONObject.fromObject(inspection_id);
		// System.out.println(json.get("inspection_id"));
	}

	@Test
	public void testtrue() throws IOException {
		JSONObject json = new JSONObject();
		json.put("result", true);
		System.out.println(json.toString());

		String result = String.valueOf(json.get("result"));
		System.out.println(result);

		System.out.println(ToolUtil.getAppConfig("mail.smtp.host"));
	}

	@Test
	public void clert() {
		String name = "<a href=\"tel:3260613531086\" x-apple-data-detectors=\"true\" x-apple-data-detectors-type=\"telephone\" x-apple-data-detectors-result=\"0\">3260613531086</a>";
		Pattern p = Pattern.compile("<a[^>]*>([^<]*)</a>");
		Matcher ms = p.matcher(name);
		if (ms.find()) {
			name = ms.group(1);
		}
		System.out.println(name);
	}

	@Test
	public void tessubstring() {
		String s = "8.944745110276896";
		String[] sarray=s.split("&");
		System.out.println(JSON.toJSONString(sarray));
		
		int index = s.indexOf(".");
		System.out.println(index);
		String ss = s.substring(0, index);
		System.out.println(ss);
		StringBuilder abnormalCodeB = new StringBuilder("");
		abnormalCodeB.append(",2111");
		abnormalCodeB.deleteCharAt(0);
		System.out.println(abnormalCodeB.toString());
		String sss = "data:image/jpeg;base64";
		System.out.println(sss.indexOf("/") + 1);
		System.out.println(sss.indexOf(";") + 1);
		System.out.println(sss.substring(sss.indexOf("/") + 1, sss.indexOf(";")));
		System.out.println("<list>".startsWith("<list"));

		System.out.println("aa bb".split(" ")[1]);

		String scope = "三大飞洒发生的方式唐山市xx县";
		int f = scope.indexOf("区");
		if (-1 == f)
			f = scope.indexOf("县");
		System.out.println("abc".length()+","+"abc".substring(0, 2));
		System.out.println(scope.substring(f - 5, f + 1).replace("唐山市", "").replace("县", "市"));
		
		
		int[] arr=new int[]{1,0,0,0};
		System.out.println(arr[0]);
		
		
	}

	@Test
	public void code() throws UnsupportedEncodingException {
		RequestBaseStationsGetNearList ro = new RequestBaseStationsGetNearList();
		ro.setToken("166ee573-72b1-4ee3-afac-6b7c6596ef52");
		ro.setLo("116.298454");
		ro.setLa("40.044342");

		// JSONObject json = JSONObject.fromObject(ro);
		// String oldString=json.toString();
		// oldString = new String(oldString.getBytes("UTF-8"));
		// System.out.println(oldString);
		//
		// String newString = URLEncoder.encode(oldString, "UTF-8");
		// System.out.println("utf-8 编码：" + newString) ;
		//
		// String newStrings = URLDecoder.decode(newString, "UTF-8");
		// System.out.println("utf-8 解码：" + newStrings) ;
	}

	@Test
	public void testp() throws UnsupportedEncodingException {
		UserInfo su = new UserInfo("xx", "ss");
		su.getOpenId();
	}

	@Test
	public void GetDistance() {
		System.out.println(ToolUtil.GetDistance(116.31072893666, 40.052370567612, 116.29873, 40.04484));
	}

	@Test
	public void testFastJson() {
		RequestBaseStationsGetNearList ro = new RequestBaseStationsGetNearList();
		ro.setToken("166ee573-72b1-4ee3-afac-6b7c6596ef52");
		// ro.setLo("116.298454");
		// ro.setLa("40.044342");
		String s = com.alibaba.fastjson.JSON.toJSONString(ro, SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteNullStringAsEmpty);
		System.out.println(s);
	}

	@Test
	public void testGetINfo() {
		String url = "http://www.95598.cn/95598/outageNotice/queryOutageNoticeList";
		try {
			com.alibaba.fastjson.JSONObject rjson = HttpClientUtil.getResultUTF8(url, "");
			System.out.println(com.alibaba.fastjson.JSON.toJSONString(rjson));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testStringUtils() {
		String deviceStatusStr = null;
		StringBuilder rsft = new StringBuilder();
		String a = null;
		String b = "111";
		rsft.append(a);
		System.out.println(rsft.toString());
		 System.out.println(Boolean.parseBoolean(deviceStatusStr));
		 boolean deviceStatus=Boolean.valueOf(deviceStatusStr);
		 System.out.println(deviceStatus);
		System.out.println(StringUtils.defaultString(null,"0") + "111");
		System.out.println(StringUtils.trimToEmpty(null) + "..");
		
		System.out.println(Integer.parseInt(StringUtils.defaultString(null,"0")));
	}

	@Test
	public void testClassType() {
		BaseDevice bd = new SwitchPower();
		System.out.println(bd instanceof Object);
		System.out.println(bd instanceof BaseDevice);
		System.out.println(bd instanceof SwitchPower);
		System.out.println(bd instanceof Battery);
		Object i = 0;
		System.out.println(i instanceof Integer);
	}

	@Test
	public void testmap() {

		Date d = new Date(1436371200000L);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS").format(d));

		Map<String, String> map = new HashMap<String, String>();
		map.put(null, "aa");
		System.out.println(map.get(null));
	}

	@Test
	public void testRound() {
		double d = 1.22d;
		int i = (int) d;
		System.out.println(i);
		Double dd = new Double(1.222d);
		System.out.println(dd.intValue());
	}

	@Test
	public void testImg() {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		String imgFile = "d:\\L50.png";// 待处理的图片
		InputStream in = null;
		byte[] data = null;
		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		System.out.println(encoder.encode(data)); // 返回Base64编码过的字节数组字符串
		testBase64img(encoder.encode(data));
	}

	public void testBase64img(String imgStr) {
		// 对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null) // 图像数据为空
			System.out.println(false);
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			// Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {// 调整异常数据
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			String imgFilePath = "d:\\222.png";// 新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			System.out.println(true);
		} catch (Exception e) {
			System.out.println(false);
		}
	}

	@Test
	public void testfileName() {
		File f = new File("d:\\222.png");
		System.out.println(f.getName());
	}

	@Test
	public void testInspectionSomeCode() {
		List<InspectionRoomTowerBO> exceptionList = new ArrayList<InspectionRoomTowerBO>();
		InspectionRoomTowerBO i1 = new InspectionRoomTowerBO();
		InspectionRoomTowerBO i2 = new InspectionRoomTowerBO();
		InspectionRoomTowerBO i3 = new InspectionRoomTowerBO();
		InspectionRoomTowerBO i4 = new InspectionRoomTowerBO();
		exceptionList.add(i1);
		exceptionList.add(i2);
		exceptionList.add(i3);
		exceptionList.add(i4);

		i1.setRecordId("1111");
		i1.setCategoryName("vertical");
		i1.setCheckValue("垂直");
		i1.setDefaultValue("垂直");
		i1.setCheckType("tower");

		i2.setRecordId("2222");
		i2.setCategoryName("towerComponent");
		i2.setCheckValue("部分构件变形");
		i2.setDefaultValue("完整无变形");
		i2.setCheckType("tower");

		i3.setRecordId("3333");
		i3.setCategoryName("towerStud");
		i3.setCheckValue("锈蚀");
		i3.setDefaultValue("完整无锈蚀");
		i3.setCheckType("tower");

		i4.setRecordId("4444");
		i4.setCategoryName("towerBase");
		i4.setCheckValue("沉降");
		i4.setDefaultValue("平整无沉降");
		i4.setCheckType("tower");
		exceptionList.addAll(null);
		// List<Map> list=JSON.parseArray(JSON.toJSONString(exceptionList),
		// Map.class);

		exceptionList = exceptionList.subList(exceptionList.size() - 1, exceptionList.size());
		System.out.println(exceptionList.size());

		// InspectionRoomTowerInfo irti=new
		// InspectionRoomTowerInfo().paseInspectionRoomTowerList(exceptionList);
		// System.out.println(irti.getDamaged()+","+JSON.toJSONString(irti));
	}

	@Test
	public void testUtf() throws UnsupportedEncodingException {
		String strInput = "保定";
		System.out.println("汉字是\t" + strInput);
		String enstr = URLEncoder.encode(strInput, "UTF-8");
		String destr = URLDecoder.decode(enstr, "UTF-8");
		System.out.println("URLEncoder之后\t" + enstr);
		System.out.println("再URLDecoder之后\t" + destr);
	}

	@Test
	public void testHttp() {
		// Build parameter string
		try {
			// Send the request
			URL url = new URL("http://210.77.179.9/WeChatToken/weixin/getOrgNoInfo.do?udid=ouzHSt_ScST2vTrfAz9GsdRFVsh8&provinceNo=13102");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);

			// Get the response
			StringBuffer answer = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = reader.readLine()) != null) {
				answer.append(line);
			}
			// writer.close();
			reader.close();
			// Output the response
			System.out.println(answer.toString());

		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void testmy() {
		try {
			com.alibaba.fastjson.JSONObject tokenJson = HttpClientUtil.getJSONObjResult(
					"http://210.77.179.9/WeChatToken/weixin/getOrgNoInfo.do?udid=ouzHSt_ScST2vTrfAz9GsdRFVsh8&provinceNo=13102",
					null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testRebot() {
		// List<String> list=new ArrayList<String>();
		// list.add("0"); //向列表中添加数据
		// list.add("1"); //向列表中添加数据
		// list.add("2"); //向列表中添加数据
		// list.add(0,"插入"); //在第1+1个元素的位置添加数据
		// //通过循环输出列表中的内容
		// System.out.println(JSON.toJSONString(list));

		// com.alibaba.fastjson.JSONArray
		// jj=FirstqTool.sendMBToWechatGroup("截止当前，黑龙江省
		// 大庆市,哈尔滨市,鹤岗市,佳木斯市,七台河市,双鸭山市,伊春市
		// 7个地市已经完成蓄电池整治填报，其他地市暂未填报，详情请查看蓄电池整治统计页面http://dwz.cn/3aOHI0","黑龙江省");
		// System.out.println(jj.toJSONString());
		// com.alibaba.fastjson.JSONArray
		// jj1=FirstqTool.sendMBToWechatGroup("截止当前，各地市已经完成蓄电池整治填报，详情请查看蓄电池整治统计页面http://dwz.cn/3aOHI0","青海省");
		// System.out.println(jj1.toJSONString());
		// com.alibaba.fastjson.JSONArray
		// jj2=FirstqTool.sendMBToWechatGroup("截止当前，各地市已经完成蓄电池整治填报，详情请查看蓄电池整治统计页面http://dwz.cn/3aOHI0","重庆市");
		// System.out.println(jj2.toJSONString());
		// Map<String,String> msg=FirstqTool.checkRobotIsLogin();
//		com.alibaba.fastjson.JSONArray jj3 = FirstqTool.sendGPToWechatGroup("test", "发电测试");
//		System.out.println(jj3.toJSONString());
		FirstqTool.sendGPToWechatGroup("test", "发电测试");
		/*List<String[]> robotlistInfo=FirstqTool.getAllConfigInfoByLevel("天气预警定时群");
		for(String[] s:robotlistInfo){
			System.out.println(JSON.toJSONString(s));
		}*/
	}

	@Test
	public void testListInit() {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("first", "[油机开始通知]");
		dataMap.put("keyword1", "开始时间");
		dataMap.put("keyword2", "内容");
		dataMap.put("keyword3", "内容");
		dataMap.put("remark", "");
		TemplateMessages.send("o50AjuB8k1odSVfdFfDSabArSlWY", dataMap);
	}
	@Test
	public void testSendMsgTo() {
		JSONArray j=FirstqTool.sendDSToWechatGroup("test","全国");
		System.out.println(j.toJSONString());
	}
	
	
}