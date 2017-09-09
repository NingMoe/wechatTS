package com.mcu32.firstq.wechat.util;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mcu32.firstq.wechat.bean.UserInfo;

//import com.mcu32.common.util.ClassLoaderUtil;

/**
 * 通用工具类 该类提供公用方法
 */
public class ToolUtil {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ToolUtil.class);

	
	

	
///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 生成订单号
	 * @return str1_str2_Long.toString(new Date().getTime())
	 */
	public static String getPayNumber(String str1,String str2){
		String paynumber=Long.toString(new Date().getTime());
		return str1+"_"+str2+"_"+paynumber;
	}
///////////////////////////////////////////////////////////////////////////////////////////////
	static final int C1 = 52846;// 密匙，必须与解密数值相同
	static final int C2 = 22710;// 密匙，必须与解密数值相同
	static final String CHARACTERSET = "GBK";

	static final String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z" };

	/**
	 * 加密字符串
	 * 
	 * @param source
	 *            没有加密的字符串，明文
	 * @return 返回加密后的字符串，密文
	 * */
	public static String MyEncrypt(String source) {

		String result = "";
		int Key = 13221;// 密匙，必须与解密数值相同
		if ((source == null) || source.equals(""))
			return "";

		byte[] tmparray = null;
		try {
			tmparray = source.getBytes(CHARACTERSET);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
			return "";
		}
		byte[] s1 = new byte[tmparray.length + 2];

		int i;
		int aasc = 0;// 口令最大长度8位
		char c;
		// String s1= "";
		int Hig4Value1, Hig4Value2;
		// 算法原理：根据原密匙(C1,C2,KEY)，通过一定数学换算（解密时必须相同）形成加密字符串。
		// 解密时，根据与加密时相同的密匙，用加密时的逆过程换算出原字符串
		// 是为了不管在何字符集的系统软件中都能顺利执行, 加密后的字符串为英文串，

		// 原理为：当字符的高位为1（16进制）时，被系统软件认为是汉字字符串。为了让系统软件认为是英文
		// 字符串，强制将其高位为1（16进制）的字符串分解为两部分，将高变为0, 即少于$80(10进制128)
		result = "";
		Hig4Value1 = 0x30;// 十六进制　30
		Hig4Value2 = 0x40;// 十六进制　40

		i = (int) (Math.random() * (tmparray.length % 250)); // 只加在前250字符之内
		if (i < 0)
			i = 0;
		while (aasc < 33 || aasc > 126) {
			aasc = (int) (Math.random() * 126);
		}
		// 插入一个可打印字符
		// i =1; aasc =102;
		// s1 = s1.insert( i, (byte)aasc );
		for (int tmpi = 0; tmpi < tmparray.length; tmpi++) {
			if (tmpi < i)
				s1[tmpi + 1] = tmparray[tmpi];
			else if (tmpi == i) {
				s1[tmpi + 1] = (byte) aasc;
				s1[tmpi + 1 + 1] = (byte) tmparray[tmpi];
			} else
				s1[tmpi + 1 + 1] = (byte) tmparray[tmpi];

		}
		s1[0] = (byte) (i + 1);
		// s1.insert(0,(char)(i+1) );//写入的位置为字符在字符串中位置，从1开始
		/*
		 * 
		 * for I := 1 to Length(S1) do begin c := char(byte(S1[I]) xor (Key shr
		 * 8));//加密算法 d := d + char(Hig4Value1 + (ord(c) mod 16));
		 * //Hig4Value1没有实际意义，目的完全是转换为可显示的英文字符串， //可以为任意值，但后一位（16进制）必须为0
		 * //Hig4Value1如果大于$80则变成了汉字字符串 d := d + char(Hig4Value2 + (ord(c) div
		 * 16)); //转换为可显示的英文字符串，$40没有实际意义 Key := (byte(d[I]) + Key) C1 + C2;
		 * //改变密匙 end;
		 */
		for (i = 0; i < s1.length; i++) {
			c = (char) ((s1[i]) ^ (Key >> 8));
			result = result + (char) (Hig4Value1 + ((c & 0x00ff) % 16));
			result = result + (char) (Hig4Value2 + (c & 0x00ff) / 16);
			// c = char(s );
			Key = (((((char) (result.getBytes()[i])) & 0x00ff) + Key) * C1 + C2) & 0x0000ffff;

		}

		return result;

	}

	/**
	 * 解密字符串
	 * 
	 * @param source
	 *            返回加密后的字符串，密文
	 * @return 没有加密的字符串，明文
	 * */
	public static String MyDecrypt(String source) {
		String result = "";
		// final int C1=52846,C2=22710;
		int i = 0;
		int key = 13221;
		char c;
		if ((source == null) || source.equals(""))
			return "";

		byte[] tmparray = null;
		try {
			tmparray = source.getBytes(CHARACTERSET);
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}
		byte[] resultArray = new byte[source.length() / 2];
		for (i = 0; i < source.length() / 2; i++) {
			c = (char) ((tmparray[2 * i] % 16) + (tmparray[2 * i + 1] % 16) * 16);
			resultArray[i] = (byte) ((c) ^ (key >> 8));
			// result = result + (char)( (c)^(key >>8 ) ) ;
			key = (((char) tmparray[i] + key) * C1 + C2) & 0x0000ffff; // 改变密匙，需与加密的改变方法一致
		}
		// i = (char)(result.getBytes()[0] );
		i = resultArray[0];
		// 过滤要首字符与随机字符
		byte[] s1 = new byte[resultArray.length - 2];
		int tmps1 = 0;
		for (int tmpi = 1; tmpi < resultArray.length; tmpi++) {
			if (tmpi != i) {
				s1[tmps1] = resultArray[tmpi];
				tmps1++;
			}

		}
		result = new String(s1);
		return result;
	}

	private static boolean isChinease(char input) {
		if (input > 128 || input < -127)
			return true;
		else
			return false;
	}

///////////////////////////////////////////////////////////////////////////////////////////////
	private static final double EARTH_RADIUS = 6378137;
	private static double rad(double d){
		return d * Math.PI / 180.0;
	}
	/**
	* 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
	* @param lng1
	* @param lat1
	* @param lng2
	* @param lat2
	* @return
	*/
	public static double GetDistance(double lng1, double lat1, double lng2, double lat2){
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	* 获取应用程序配置信息
	* @param key
	* @return
	*/
	public static String getAppConfig(String key) {
		return getAppConfig("app",key);
	}
	public static String getAppConfig(String properties,String key) {
		ResourceBundle appcfg = ResourceBundle.getBundle(properties);
		return appcfg.getString(key);
	}
	public static void initProperties(){
		ResourceBundle.clearCache();
	}
	
	public void getPath() throws Exception{
	//.class.getResource("/");
	/*String fileName=this.getClass().getResource("/app.properties").getPath();
	File f=new File(fileName);
	
	BufferedReader reader=null;
	InputStreamReader in=new InputStreamReader(new FileInputStream(f),"UTF-8");
	reader = new BufferedReader(in);
	String tempString = null;
	int line = 1;
	// 一次读入一行，直到读入null为文件结束
	while ((tempString = reader.readLine()) != null) {
	// 显示行号
	System.out.println(tempString);
	line++;
	}
	reader.close();*/
	}
///////////////////////////////////////////////////////////////////////////////////////////////
	/*因为百度bae的log输出不好使，所以自己写一个log输出的工具类*/
	private static URL url=null;
	private static File f=null;
	/*public static void WriteLogs(String str) throws IOException{
		ToolUtil.setfile();
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(f,true),"UTF-8");
		out.write(str+"\n");
		out.flush();
		out.close();
	}*/
	/*public static OutputStreamWriter WriteLogByStep(String str) throws IOException{
		ToolUtil.setfile();
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(f,true),"UTF-8");
		out.write(str+"\n");
		out.flush();
		return out;
	}*/
	public static InputStream openLogStream() throws IOException{
		ToolUtil.setfile();
		return url.openStream();
	}
	private static void setfile() throws IOException{
		if(null==url){
			url=ClassLoaderUtil.getExtendResource("../../log.log");
		}
		if(null==f){
			f=new File(URLDecoder.decode(url.getFile(), "UTF-8"));
		}
	}
	
	public static void clearFile() throws Exception{
		setfile();
		FileOutputStream out = new FileOutputStream(f,false); 
		out.write(new String("").getBytes()); 
		out.close(); 
	}
///////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 验证是否为有效手机号
	 */
	public static boolean isValidPhone(String input) {
		if (null == input || input.equals(""))
			return false;
		Pattern p = Pattern.compile("1[0-9]{10}"); // 正则表达式

		Matcher m = p.matcher(input); // 代匹配的字符串

		boolean b = m.matches();
		return b;

	}
///////////////////////////////////////////////////////////////////////////////////////////////
	public static String replace(String resource, String oldstr, String newstr) { // 用新字符串替换字符串中的旧字符串
		StringBuffer sbuf = new StringBuffer();
		try {
			if ((oldstr == null)) {
				return ""; // 异常
			}
			if ((resource == null)) {
				return ""; // 异常
			}
			if ((newstr == null)) {
				return ""; // 异常
			}
			if (resource.indexOf(oldstr) == -1) {
				return resource; // 没有要替换内容，返回原来的串
			}
			int start = 0;
			int end = 0;
			String rest = "";
			end = resource.indexOf(oldstr);
			String left = "";
			while (end != -1) {
				left = resource.substring(start, end);
				sbuf.append(left);
				sbuf.append(newstr);
				rest = resource.substring(end + oldstr.length());
				resource = rest;
				end = resource.indexOf(oldstr);
			}
			sbuf.append(rest);
		} catch (Exception e) {
			e.printStackTrace();
			return ""; // 异常
		}
		return sbuf.toString();
	}
///////////////////////////////////////////////////////////////////////////////////////////////
	
	public static String separatorChar(String resource) {// 对\\ 和 / 处理 window
		// 和liunx 不同
		if (File.separatorChar == '/') {
			resource = ToolUtil.replace(resource, "\\", "/");
		} else {
			resource = ToolUtil.replace(resource, "/", "\\");
		}
		return resource;
	}
///////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 一个将字符串分隔为指定字节长度的字符串数组的函数,ascII 为一字节，汉字为两个字节
	 * 
	 * @param input
	 *            输入的字符串
	 * @param aLength
	 *            长度
	 * @return 如果字符串为空或"",返回长度为零的字符串数组
	 */
	public static String[] getInfoAry(String input, int aLength) {
		ArrayList<String> ary = new ArrayList<String>();
		char[] strbyt = input.toCharArray();

		int aPos = 0;
		int curSize = 0;

		for (int i = 0; i < strbyt.length; i++) {
			if ((curSize < aLength) && !isChinease(input.charAt(i))) {
				curSize++;
			} else if (curSize < aLength - 1 && isChinease(input.charAt(i))) {
				curSize = curSize + 2;
			} else {
				ary.add(input.substring(aPos, i));
				aPos = i;
				curSize = 0;
				if (isChinease(input.charAt(i))) {
					curSize = curSize + 2;
				} else {
					curSize = curSize + 1;
				}

			}

		}
		if (curSize != 0) {
			ary.add(input.substring(aPos));
		}
		String[] aresult = new String[ary.size()];
		for (int i = 0; i < ary.size(); i++) {
			aresult[i] = (ary.get(i));
		}
		return aresult;
	}

	/**
	 * 将字符串aStr中的回车换行“\r\n”转换成“<BR>
	 * ”
	 * 
	 * @return 转换后的字符串
	 * @param sStr为需要转换的字符串
	 */
	public static String returnToBr(String sStr) {

		if (sStr == null || sStr.equals("")) {
			return "&nbsp;";
		}
		StringBuffer sTmp = new StringBuffer();
		int i = 0;
		while (i <= sStr.length() - 1) {
			if (sStr.charAt(i) == '\n') {
				sTmp = sTmp.append("<br>");
			} else if (sStr.charAt(i) == '\r') { // 忽略此字符
			} else if (sStr.charAt(i) == ' ') {
				sTmp = sTmp.append("&nbsp;");
			} else {
				sTmp = sTmp.append(sStr.substring(i, i + 1));
			}
			i++;
		}
		String S1;
		S1 = sTmp.toString();
		return S1;
	}

	/**
	 * 将字符串aStr中的回车换行“\r\n”转换成“&lt;BR&gt;”,"&"号转换成"&amp;" 注：在组合XML时用
	 * 
	 * @return 转换后的字符串
	 * @param sStr为需要转换的字符串
	 */
	public static String OtherReturnToBr(String sStr) {

		if (sStr == null || sStr.equals("")) {
			return null;
		}
		StringBuffer sTmp = new StringBuffer();
		int i = 0;
		while (i <= sStr.length() - 1) {
			if (sStr.charAt(i) == '\n') {
				sTmp = sTmp.append("&lt;br&gt;"); // &lt;br&gt;
			} else if (sStr.charAt(i) == '\r') { // 忽略此字符
			} else if (sStr.charAt(i) == '<') {
				sTmp = sTmp.append("&lt;");
			} else if (sStr.charAt(i) == '&') {
				sTmp = sTmp.append("&amp;");
			} else {
				sTmp = sTmp.append(sStr.substring(i, i + 1));
			}
			i++;
		}
		String S1;
		S1 = sTmp.toString();
		return S1;
	}

	/**
	 * 将数字转化成字母
	 * 
	 * @param number
	 *            要转化的数字
	 * @return
	 */
	public static String getLetterFromInteger(int number) {
		String temp = "";
		if (number >= 0) {
			String aNum = number + "";
			String[] nums = aNum.split("");
			for (int i = 1; i < nums.length; i++) {
				int index = new Integer(nums[i]).intValue();
				temp += letters[index];
			}
		}
		return temp;

	}

	/**
	 * 将数字字符串转化成字母
	 * 
	 * @param numberStr
	 *            要转化的数字字符串
	 * @return
	 */
	public static String getLetterFromInteger(String numberStr) {
		String temp = "";
		if (numberStr != null && !"".equals(numberStr)) {
			if (numberStr.indexOf(",") > 0) {
				String[] nums = numberStr.split(",");
				for (int i = 0; i < nums.length; i++) {
					int index = new Integer(nums[i]).intValue();
					temp += letters[index];
				}
			} else {
				int index = new Integer(numberStr).intValue();
				temp = letters[index];
			}
		}
		return temp;
	}

	/**
	 * 获取倒计时 反馈信息
	 * 
	 * @param userName
	 *            用户名姓名
	 * @param startTime
	 *            开始时间
	 * @param miniutes
	 *            时长 (分钟)
	 * @return
	 */
	public static String getCountDown(String userName, Calendar startTime,
			int miniutes) {

		String message = "";
		// 设置结束时间
		Calendar timeend = Calendar.getInstance();
		timeend.setTime(startTime.getTime());
		timeend.add(Calendar.MINUTE, miniutes);
		long end = timeend.getTimeInMillis();

		// 获取当前时间
		Calendar rightNow = Calendar.getInstance();
		long now = rightNow.getTimeInMillis();

		// 计算剩余时间
		int left = (int) (end - now);
		if (left <= 0) {
			// 时间到
			message = "over";
		} else {
			int leftHour = left / (1000 * 60 * 60);
			left = left % (1000 * 60 * 60);
			int leftMinute = left / (1000 * 60);
			left = left % (1000 * 60);
			int leftSecond = left / (1000);
			message = userName + ",您好，距交卷还有：  " + leftHour + "时" + leftMinute
					+ "分" + leftSecond + "秒";
		}
		return message;
	}

	/**
	 * 判断 指定的时间 距 当前时间 是否超过 miniutes 分钟数
	 * 
	 * @param startTime
	 *            制定的时间
	 * @param miniutes
	 *            分钟数
	 * @return 如果超过 返回 true；如果没有超过 返回false
	 */
	public static boolean isOverTime(Calendar startTime, int miniutes) {
		// 设置结束时间
		Calendar timeend = Calendar.getInstance();
		timeend.setTime(startTime.getTime());
		timeend.add(Calendar.MINUTE, miniutes);
		long end = timeend.getTimeInMillis();

		// System.out.println("结束时间：================"+timeend.getTime().toLocaleString());
		// 获取当前时间
		Calendar rightNow = Calendar.getInstance();
		long now = rightNow.getTimeInMillis();
		// System.out.println("当前时间：================"+rightNow.getTime().toLocaleString());
		// 计算剩余时间
		int left = (int) (end - now);
		if (left > 0)
			return false;
		else
			return true;
	}

	/**
	 * 文件转化为字节数组
	 */
	public static byte[] getBytesFromFile(File f) {
		if (f == null) {
			return null;
		}
		try {
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1)
				out.write(b, 0, n);
			stream.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e) {
		}
		return null;
	}

	/**
	 * 把字节数组保存为一个文件
	 */
	public static File getFileFromBytes(byte[] b, String outputFile) {
		BufferedOutputStream stream = null;
		File file = null;
		try {
			file = new File(outputFile);
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return file;
	}

	/**
	 * 从字节数组获取对象
	 */
	public static Object getObjectFromBytes(byte[] objBytes) throws Exception {
		if (objBytes == null || objBytes.length == 0) {
			return null;
		}
		ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
		ObjectInputStream oi = new ObjectInputStream(bi);
		return oi.readObject();
	}

	/**
	 * 从对象获取一个字节数组
	 */
	public static byte[] getBytesFromObject(Serializable obj) throws Exception {
		if (obj == null) {
			return null;
		}
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		ObjectOutputStream oo = new ObjectOutputStream(bo);
		oo.writeObject(obj);
		return bo.toByteArray();
	}
	
	public static boolean ObjEquals(Object a, Object b, String[] props) {
		boolean flag = false;
		Object args = new Object[0];
		Class<? extends Object> oClass = a.getClass();
		Method[] methods = oClass.getMethods();
		for (Method method : methods) {
			for (String prop : props) {
				if (method.getName().equals(prop)) {
					try {
						
						Object oa = method.invoke(a, args);
						Object ob = method.invoke(b, args);
						if (oa == null || !oa.equals(ob)) {
							flag = false;
						} else {
							flag = true;
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return flag;
	}
	
	//返回一个map以不同的属性名为key,value为一个list分别存obj1,obj2此属性名的值
	public static Map<String,List<Object>> equals(Object obj1,Object obj2){
	   Map<String,List<Object>> map=new HashMap<String,List<Object>>();
	   if(obj1.getClass()==obj2.getClass()){
		 //只有两个对象都是同一类型的才有可比性
	     Class<? extends Object> clazz=obj1.getClass();
	     //获取object的属性描述
	     PropertyDescriptor[] pds;
	     try {
			pds = Introspector.getBeanInfo(clazz,Object.class).getPropertyDescriptors();
			for(PropertyDescriptor pd:pds){//这里就是所有的属性了
				String name=pd.getName();//属性名
			    Method readMethod=pd.getReadMethod();//get方法
		        //在obj1上调用get方法等同于获得obj1的属性值
		        Object o1=readMethod.invoke(obj1);
		        //在obj2上调用get方法等同于获得obj2的属性值
		        Object o2=readMethod.invoke(obj2);
		        if(o2==null){
		        	o2 = "";
		        }
		        if(o1!=null && !o1.equals(o2)){
		        	//比较这两个值是否相等,不等就可以放入map了
			        List<Object> list=new ArrayList<Object>();
			        list.add(o1);
			        list.add(o2);
			        map.put(name,list);
		        }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	   }
	   return map;
	}
	
	public static void main(String[] args) {
		UserInfo user1 = new UserInfo();
		user1.setUserId("111");
		user1.setUserName("333");
		user1.setDeptId("111");
		UserInfo user2 = new UserInfo();
		user2.setUserId("111");
		user2.setUserName("222");
		user2.setDeptId("222");
		Map<String,List<Object>> map = ToolUtil.equals(user1, user2);
		Iterator<Entry<String, List<Object>>> it = map.entrySet().iterator();
		while (it.hasNext()) {
		    Entry<String, List<Object>> entry = it.next();
		    //String key = (String)entry.getKey();
		    List<Object> value = entry.getValue();
		    System.out.println(value.get(0));
	    }
   } 

}
