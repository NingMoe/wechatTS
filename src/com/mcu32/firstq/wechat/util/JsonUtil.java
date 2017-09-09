package com.mcu32.firstq.wechat.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;
import net.sf.json.xml.XMLSerializer;

/**
 * 
 * 类名称: JsonUtil
 * <p/>  
 * 类描述:Json转换工具类
 * <p/>
 * 创建者: yuwenxiao 
 * <p/>
 * 创建时间: 2016年3月8日 上午10:23:35
 * <p/>
 */
public class JsonUtil {
	/**
	 * 
	 * 方法描述：私有构造方法
	 * <p/>  
	 * <p/>
	 * 创建者：yuwenxiao
	 * <p/> 
	 * 创建时间：2016年3月8日 上午10:26:14
	 */
	private JsonUtil() {
	}
	/**
	 * 
	 * 方法描述：把JSON字符串转换成Map对象（如果map内还有map,则循环转换）.
	 * <p/>
	 * @param jsonStr  json字符串
	 * @return  Map<String, Object>
	 * <p/>
	 * 创建者：yuwenxiao
	 * <p/> 
	 * 创建时间：2016年3月8日 下午5:19:55
	 */
	public static Map<String, Object> parseJSON2Map(final String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(null != jsonStr&&!("").equals(jsonStr)){
			String tempSort = jsonStr;
			if (tempSort.startsWith("[")) {
				tempSort = tempSort.substring(1);
			}
			if (tempSort.endsWith("]")) {
				tempSort = tempSort.substring(0,tempSort.length()-1);
			}
			
			JSONObject json = JSONObject.fromObject(tempSort);

			for (Iterator iterator = json.entrySet().iterator(); iterator.hasNext();) {
				String entryStr = String.valueOf(iterator.next());
				String k = entryStr.substring(0, entryStr.indexOf("="));
				// for (Object k : json.keySet()) {
				Object v = json.get(k);
				if (v instanceof JSONArray) {
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					Iterator<JSONObject> it = ((JSONArray) v).iterator();
					while (it.hasNext()) {
						JSONObject json2 = it.next();
						list.add(parseJSON2Map(json2.toString()));
					}
					map.put(k.toString(), list);
				} else {
					map.put(k.toString(), v);
				}
			}
		}
		return map;
	}
	/**
	 * 
	 * 方法描述：把JSON字符串转换成List<Map<String, Object>>集合.
	 * <p/>
	 * @param jsonStr JSON字符串
	 * @return  List <Map<String, Object>>
	 * <p/>
	 * 创建者：yuwenxiao
	 * <p/> 
	 * 创建时间：2016年3月8日 下午5:27:16
	 */
	public static List<Map<String, Object>> parseJSON2List(final String jsonStr) {
		String jsonTemp = jsonStr;
		if (!jsonTemp.startsWith("[")) {
			jsonTemp = "[" + jsonTemp;
		}
		if (!jsonTemp.endsWith("]")) {
			jsonTemp = jsonTemp + "]";
		}
		JSONArray jsonArr = JSONArray.fromObject(jsonTemp);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}
	/**
	 * 
	 * 方法描述：将Java对象格式化为JSON字符串
	 * <p/>
	 * @param o 对象
	 * @return  String
	 * <p/>
	 * 创建者：yuwenxiao
	 * <p/> 
	 * 创建时间：2016年3月8日 下午5:52:13
	 */
	public static String object2json(Object o)  {
		return object2json(o, "yyyy-MM-dd hh:mm:ss");
	}
	
	/**
	 * 
	 * 方法描述：将Java对象格式化为JSON字符串
	 * <p/>
	 * @param o 对象
	 * @param datePattern 指定java对象中日期对象,转换为json格式时的时间格式
	 * @return  
	 * <p/>
	 * 创建者：yuwenxiao
	 * <p/> 
	 * 创建时间：2016年3月8日 下午5:55:07
	 */
	public static String object2json(Object o, String datePattern) {
		if (o == null)
			return null;

			if (o instanceof Object[] || o instanceof java.util.Collection) {
				JSONArray json = JSONArray.fromObject(o,
						configJson(datePattern));
				return json.toString();
			} else {
				JSONObject json = JSONObject.fromObject(o,
						configJson(datePattern));
				return json.toString();
			}
	}
	/**
	 * 
	 * 方法描述：Json转换成对象
	 * <p/>
	 * @param json Json字符串
	 * @param clazz 类class
	 * @return  对象
	 * <p/>
	 * 创建者：yuwenxiao
	 * <p/> 
	 * 创建时间：2016年3月10日 下午2:53:00
	 */
	public static Object json2Object(String json, Class clazz) {
		
			JSONObject jsonObj = JSONObject.fromObject(json);
			return JSONObject.toBean(jsonObj, clazz);
		

	}
	/**
	 * 将JSON字符串转换成Java对象
	 * 
	 * @param json json
	 *            --json 字符串
	 * @param clazz clazz
	 *            --指定要转换成的java对象
	 * @param mapClass mapClass
	 *            --用于存放指定json字符串中的数组对象转换时要对应的java类<br>
	 *            key: json字符串中的数组对象的“属性名” value: Java对象所以对应的类
	 * 
	 * <br>
	 *            eg. 将json字符串中的"child"属性对应的集合转换成java.uitl.Map对象 json =
	 *            {"child":
	 *            [{"sex":"man","age":"3","name":"Tom"},{"sex":"woman","age"
	 *            :"6","name":"Mary"}],"sex":"man","age":"25","name":"loafer"}
	 *            classMap.put("child",java.uitl.Map.class) json2object(json,
	 *            java.uitl.Map.class, classMap) result: {sex=man,
	 *            child=[{sex=man, age=3, name=Tom}, {sex=woman, age=6,
	 *            name=Mary}], age=25, name=loafer}
	 * 
	 * @return 返回值
	 */
	public static Object json2Object(String json, Class clazz, Map mapClass) {
		JSONObject jsonObj = JSONObject.fromObject(json);
		return JSONObject.toBean(jsonObj, clazz, mapClass);
	}
	/**
	 * 将JSON文件转换成Java对象
	 * @param jsonFile jsonFile
	 * @param clazz clazz
	 * @param mapClass mapClass
	 * @return 返回值
	 * @throws Exception 
	 */
	public static Object json2Object(File jsonFile,Class clazz, Map mapClass) throws Exception {
		return json2Object(getStringFormFile(jsonFile),clazz,mapClass);
	}
	/**
	 * 读取文件
	 * @param jsonFile jsonFile
	 * @return 返回值
	 * @throws Exception 
	 */
	private static String getStringFormFile(File jsonFile) throws Exception{
		BufferedReader in = null;
		String retStr = "";
		try {
			StringBuilder json = new StringBuilder("");
			in = new BufferedReader(new InputStreamReader(new FileInputStream(jsonFile)));
			String temp = in.readLine();
			while(temp!=null){
				json.append(temp);
				temp = in.readLine();
			}
			retStr = json.toString();
			//取得回车换行
			if(!("").equals(retStr))
				retStr = retStr.replaceAll("\n\r", "");
		} catch (FileNotFoundException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new IOException(e);
		} finally{
			try {
				if(in!=null)in.close();
			} catch (Exception e) {
				throw new Exception(e);
			}
		}
		return retStr.toString();
	}

	/**
	 * 将JSON字符串数组转换成Java数组对象
	 * 
	 * @param json json
	 * @param clazz clazz
	 * @return 一个含有参数clazz类型的Object数组 返回值
	 */
	public static Object[] json2ObjectArray(String json, Class clazz) {
		return json2ObjectArray(json, clazz, null);
	}

	/**
	 * 将JSON数组转换为Java对象数组
	 * 
	 * @param json json
	 *            --json对象数组
	 * @param clazz clazz
	 *            --数组中每个json对象要转成的Java对象
	 * @param classMap classMap
	 *            --指定json对象中的数组对象转换时要对应的java对象
	 * @return 一个含有参数clazz类型的Object数组 返回值
	 */
	public static Object[] json2ObjectArray(String json, Class clazz,
			Map classMap) {
		JSONArray jsonArr = JSONArray.fromObject(json);
		Object[] objs = new Object[jsonArr.size()];
		for (int i = 0; i < jsonArr.size(); i++) {
			objs[i] = JSONObject.toBean(jsonArr.getJSONObject(i), clazz,
					classMap);
		}
		return objs;
	}
	/**
	 * 将JSON字符串转换成List对象
	 * 
	 * @param json json
	 * @param clazz clazz
	 * @return 返回值
	 */
	public static List json2ObjectList(String json, Class clazz) {
		return json2ObjectList(json, clazz, null);
	}

	/**
	 * 将JSON数组转换为Java对象列表
	 * 
	 * @param json json
	 *            --json对象数组
	 * @param clazz clazz
	 *            --数组中每个json对象要转成的Java对象
	 * @param classMap classMap
	 *            --指定json对象中的数组对象转换时要对应的java对象
	 * @return 返回值
	 */
	public static List json2ObjectList(String json, Class clazz, Map classMap) {
		JSONArray jsonArr = JSONArray.fromObject(json);
		List list = new ArrayList();
		for (int i = 0; i < jsonArr.size(); i++) {
			list.add(JSONObject.toBean(jsonArr.getJSONObject(i), clazz,
					classMap));
		}
		return list;
	}

	/**
	 * 将xml字符串转换为JSON 格式
	 * 
	 * @param xml xml
	 * @return 返回值
	 */
	public static String xml2json(String xml) {
		XMLSerializer xmlSerializer = new XMLSerializer();
		xmlSerializer.setForceTopLevelObject(true);
		JSON json = xmlSerializer.read(xml);
		return json.toString();
	}

	/**
	 * 将json 格式 转换为 xml字符串
	 * 
	 * @param json json
	 * @return 返回值
	 */
	public static String json2xml(String json) {
		JSON j = JSONSerializer.toJSON(json);
		XMLSerializer xmlSerializer = new XMLSerializer();
		xmlSerializer.setTypeHintsEnabled(false);
		return xmlSerializer.write(j);
	}

	/**
	 * 用来配置Java对象的时间的解析方式
	 * 
	 * @param datePattern datePattern
	 * @return 返回值
	 */
	private static JsonConfig configJson(String datePattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new DateJsonValueProcessor(datePattern));
		return jsonConfig;
	}

	/**
	 * 内部类 JSON 用的注册解析类，用来处理日期
	 * 
	 * 
	 * 
	 */
	private static class DateJsonValueProcessor implements JsonValueProcessor {
		/**  
		 * 请填写变量说明.  
		 */
		public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
		/**  
		 * 请填写变量说明.  
		 */
		private DateFormat dateFormat;

		/**
		 * 构造方法.
		 * 
		 * @param datePattern datePattern
		 *            日期格式
		 */
		public DateJsonValueProcessor(String datePattern) {
			try {
				dateFormat = new SimpleDateFormat(datePattern);
			} catch (Exception ex) {
				dateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
			}
		}

		/**   
		 * 方法描述：请在这里填写方法具有xxxx作用.
		 * <p/>
		 * @param value value
		 * @param jsonConfig jsonConfig
		 * @return  返回值
		 * <p/>
		 */
		public Object processArrayValue(Object value, JsonConfig jsonConfig) {
			return process(value);
		}

		/**   
		 * 方法描述：请在这里填写方法具有xxxx作用.
		 * <p/>
		 * @param key key
		 * @param value value
		 * @param jsonConfig jsonConfig
		 * @return  返回值
		 * <p/>
		 */
		public Object processObjectValue(String key, Object value,
				JsonConfig jsonConfig) {
			return process(value);
		}

		/**   
		 * 方法描述：请在这里填写方法具有xxxx作用.
		 * <p/>
		 * @param value value
		 * @return  返回值
		 * <p/>
		 */
		private Object process(Object value) {
			if (null != value) {
				return dateFormat.format((Date) value);
			} else {
				return null;
			}

		}
	}

	/**
	 * 判断给定的json字符串是否是一个有效的JSON 串
	 * 
	 * @param json json
	 * @return 返回值
	 */
	public static boolean mayBeJSON(String json) {
		return JSONUtils.mayBeJSON(json);
	}
}
