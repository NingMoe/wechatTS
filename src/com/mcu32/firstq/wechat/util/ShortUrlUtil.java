package com.mcu32.firstq.wechat.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ShortUrlUtil {
	
	
	public static void main(String[] args) {
		try {
//			System.out.println("http://www.czxkpower.com/wechat/main/toRedirectPage?toPage="+URLEncoder.encode("/main/toPage?toPageUrl=report/manageBatteryRecordByProvince", "UTF-8"));
//			System.out.println(creatShort("www.vwbl.cn/wechat/main/toRedirectPage?toPage="+URLEncoder.encode("/main/toPage?toPageUrl=report/manageBatteryRecordByProvince", "UTF-8")));
			
			System.out.println(creatShort(URLEncoder.encode("http://www.czxkpower.com:9092/wechat-cs/getDianBoMessage/getVoltageMessage?orgName=%E5%B9%BF%E5%B7%9E&currentDemand=54e8f84e-926e-45c2-a2db-4d046570a636&time=2016-08-03 11:27", "UTF-8")));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String creatShort(String longUrl){
		String shortUrl = "";
		try{
			shortUrl = myShortUrl(longUrl);
			//调用微信的短连接api
			if (StringUtils.isEmpty(shortUrl)) shortUrl = wxShortUrl(longUrl);
			//调用自己写的短连接api
			if (StringUtils.isEmpty(shortUrl))  shortUrl = currentShortUrl(longUrl);
			return shortUrl;
		}catch (Exception e) {
			LogUtil.error(e);
			if (StringUtils.isEmpty(shortUrl)){
				try {
					shortUrl = wxShortUrl(longUrl);
					if (StringUtils.isEmpty(shortUrl)){
						try {
							shortUrl = currentShortUrl(longUrl);
						} catch (Exception e2) {
							LogUtil.error(e2);
							return longUrl;
						}
					}  
				} catch (Exception e1) {
					LogUtil.error(e1);
					if (StringUtils.isEmpty(shortUrl)){
						try {
							shortUrl = currentShortUrl(longUrl);
						} catch (Exception e2) {
							LogUtil.error(e2);
							return longUrl;
						}
					}  
				}
			}
			return shortUrl;
		}
	}
	
	/**
	 * s.codepanda.cn短连接api
	 */
	public static String myShortUrl(String longUrl) throws Exception{
		String shortUrl = "";
		String url = "http://s.codepanda.cn/api/v1/shorten";
		String resultStr = new String(HttpClientUtil.sendPostRequestByForm("POST",url,"long_url="+longUrl,""));
		JSONObject shorUrlObj = JSONObject.parseObject(resultStr);
		String statusCode = shorUrlObj.getString("status_code");
		if ("200".equals(statusCode)) {
			shortUrl = shorUrlObj.getString("short_url");
		}
		return shortUrl;
	}
	
	
	/**
	 * 50r.cn短连接api
	 */
	public static String currentShortUrl(String longUrl) throws Exception{
		String shortUrl = "";
		String url = "http://50r.cn/urls/add.json";
		String shortUrl50 = sendGet(url, "ak=5763bb957f3541890591bd0e&url="+longUrl);
		shortUrl = JSON.parseObject(shortUrl50).get("url").toString();
		return shortUrl;
	}
	
	/**
	 * 微信短连接api
	 */
	public static String wxShortUrl(String longUrl) throws Exception{
		String shortUrl = "";
		String token = getResult(ToolUtil.getAppConfig("WechatDomain")+"/"+ToolUtil.getAppConfig("WechatProjectName")+"/getWeiXinMessage/getTokenAndTicket.json","","utf-8");
		String url = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token="+JSON.parseObject(token).get("token");
		String wxResult = getResult(url,"{\"action\":\"long2short\",\"long_url\":\""+longUrl+"\"}","utf-8");
		shortUrl = (String) JSON.parseObject(wxResult).get("short_url");
		return shortUrl;
	}
	
	/**
	 * 百度短连接api
	 */
	public static String baiduShortUrl(String longUrl) throws Exception {
		String shortUrl = "";
		longUrl = "url="+longUrl;
		URL url = new URL("http://dwz.cn/create.php");
        java.net.URLConnection connection = url.openConnection();  //打开url连接
        connection.setDoOutput(true); 
        PrintWriter out = new PrintWriter(connection.getOutputStream());
        out.write(longUrl); //写入长网址到PORT请求
        out.flush();
        out.close();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		if (in != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int i = -1;
			while ((i = in.read()) != -1) {
				baos.write(i);
			}
			shortUrl = baos.toString("UTF-8");
			shortUrl = JSON.parseObject(shortUrl).getString("tinyurl");
		}
		return shortUrl;
	}
	
	 public static String sendGet(String url, String param) {
	        String result = "";
	        BufferedReader in = null;
	        try {
	            String urlNameString = url + "?" + param;
	            LogUtil.info(urlNameString);
	            URL realUrl = new URL(urlNameString);
	            // 打开和URL之间的连接
	            URLConnection connection = realUrl.openConnection();
	            // 设置通用的请求属性
	            connection.setRequestProperty("accept", "*/*");
	            connection.setRequestProperty("connection", "Keep-Alive");
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
	            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送GET请求出现异常！" + e);
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

	
	
	public static String getResult(String urlStr, String content, String encoding) throws Exception {
		URL url = null;
		HttpURLConnection connection = null;
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			connection.setConnectTimeout(10000);// 设置连接超时时间，单位毫秒
			connection.setReadTimeout(10000);// 设置读取数据超时时间，单位毫秒
			connection.setDoOutput(true);// 是否打开输出流 true|false
			connection.setDoInput(true);// 是否打开输入流true|false
			connection.setRequestMethod("POST");// 提交方法POST|GET
			connection.setRequestProperty("Charset",encoding);
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();// 打开连接端口
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());// 打开输出流往对端服务器写数据
			//out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
			out.write(content.getBytes(encoding));
			out.flush();// 刷新
			out.close();// 关闭输出流
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));// 往对端写完数据对端服务器返回数据
			// ,以BufferedReader流来读取
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			if (connection != null) {
				connection.disconnect();// 关闭连接
			}
			//LogUtil.info("interface request: "+content);
			//LogUtil.info("interface response: "+buffer.toString());
			return buffer.toString();
			
		
	}

}