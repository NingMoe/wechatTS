package com.mcu32.firstq.wechat.util;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpClientUtil {
	
	public static void main(String[] args) {
		String url = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=JYiNEerzeVA6XVLakthXQiqu4c7TCWB9kf3p2lJsgeB1LMSbpPBadkrL_5AAdsvqb3dNVjU2faghbmHbcX_DJnsdwGHGRXXWLaqK0qjok7wXIGhAAANYW";
//		access_token=JYiNEerzeVA6XVLakthXQiqu4c7TCWB9kf3p2lJsgeB1LMSbpPBadkrL_5AAdsvqb3dNVjU2faghbmHbcX_DJnsdwGHGRXXWLaqK0qjok7wXIGhAAANYW&action=long2short&long_url=http://www.czxkpower.com
		
		try {
			System.out.println(getResult(url,"{\"action\":\"long2short\",\"long_url\":\"http://www.czxkpower.com/v2/showcase/goods?alias=128wi9shh&spm=h56083&redirect_count=1\"}","POST","utf-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
     * 通过HttpURLConnection模拟post表单提交
     * 
     * @param path
     * @param params 例如"name=zhangsan&age=21"
     * @return
     * @throws Exception
     */
    public static byte[] sendPostRequestByForm(String methodType,String path, String params,String cookie) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(methodType);// 提交模式
        // conn.setConnectTimeout(10000);//连接超时 单位毫秒
        // conn.setReadTimeout(2000);//读取超时 单位毫秒
        conn.setDoOutput(true);// 是否输入参数
        if(StringUtils.hasText(cookie)) conn.addRequestProperty("Cookie", "sessionid="+cookie+"; path=/");
        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes);// 输入参数
        InputStream inStream=conn.getInputStream();
        return readInputStream(inStream);
    }
    
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len = inStream.read(buffer)) !=-1 ){
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();//网页的二进制数据
        if(outStream != null) outStream.close();
        if(inStream != null) inStream.close();
        return data;
    }
	
	public static final String SunX509 = "SunX509";
	public static final String JKS = "JKS";
	public static final String PKCS12 = "PKCS12";
	public static final String TLS = "TLS";
	
	public static String getResult(String urlStr, String content) throws Exception {
		Long time=new Date().getTime();
		LogUtil.info("==== "+time+" request  ======= "+urlStr+"?param="+ content);
		String resString =getResult( urlStr, "param="+ content, "POST","utf-8");
		LogUtil.info("==== "+time+" response ======= "+resString);
		return resString;
	}
	public static JSONObject getJSONObjResult(String urlStr, String content) throws Exception {
		long startTime = System.currentTimeMillis();
		Long time=new Date().getTime();
		LogUtil.info("==== "+time+" request  ======= "+urlStr+"?param="+ content);
		String resString =getResult( urlStr, "param="+ content,"POST", "utf-8");
		LogUtil.info("==== "+time+" response ======= "+resString);
		long endTime = System.currentTimeMillis();
		LogUtil.info(" cost time "+(endTime-startTime)+"ms");
		return JSON.parseObject(resString);
	}
	
	public static JSONObject getResultUTF8(String urlStr, String content) throws Exception {
		Long time=new Date().getTime();
		LogUtil.info("==== "+time+" request  ======= "+urlStr+ content);
		String resString =getResult( urlStr,  content,"POST", "utf-8");
		LogUtil.info("==== "+time+" response ======= "+resString);
		return JSON.parseObject(resString);
	}
	
	public static String getResult(String urlStr, String content,String method, String encoding) throws Exception {
		URL url = null;
		HttpURLConnection connection = null;
		url = new URL(urlStr);
		connection = (HttpURLConnection) url.openConnection();// 新建连接实例
		connection.setConnectTimeout(10000);// 设置连接超时时间，单位毫秒
		connection.setReadTimeout(10000);// 设置读取数据超时时间，单位毫秒
		connection.setDoOutput(true);// 是否打开输出流 true|false
		connection.setDoInput(true);// 是否打开输入流true|false
		connection.setRequestMethod(StringUtils.isEmpty(method)?"POST":method);// 提交方法POST|GET
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
	public static HttpURLConnection getHttpURLConnection(String strUrl)throws IOException {
		URL url = new URL(strUrl);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		return httpURLConnection;
	}

	public static HttpsURLConnection getHttpsURLConnection(String strUrl)throws IOException {
		URL url = new URL(strUrl);
		HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
		return httpsURLConnection;
	}
	
	public static String getURL(String strUrl) {
		if(null != strUrl) {
			int indexOf = strUrl.indexOf("?");
			if(-1 != indexOf) {
				return strUrl.substring(0, indexOf);
			} 
			return strUrl;
		}
		return strUrl;
	}
	
	public static String getQueryString(String strUrl) {
		if(null != strUrl) {
			int indexOf = strUrl.indexOf("?");
			if(-1 != indexOf) {
				return strUrl.substring(indexOf+1, strUrl.length());
			} 
			return "";
		}
		return strUrl;
	}
	
	/**
	 * ��ѯ�ַ�ת����Map<br/>
	 * name1=key1&name2=key2&...
	 * @param queryString
	 * @return
	 */
	public static Map<String, String> queryString2Map(String queryString) {
		if(null == queryString || "".equals(queryString)) {
			return null;
		}
		
		Map<String, String> m = new HashMap<String, String>();
		String[] strArray = queryString.split("&");
		for(int index = 0; index < strArray.length; index++) {
			String pair = strArray[index];
			HttpClientUtil.putMapByPair(pair, m);
		}
		
		return m;
		
	}
	
	/**
	 * �Ѽ�ֵ�����Map<br/>
	 * pair:name=value
	 * @param pair name=value
	 * @param m
	 */
	public static void putMapByPair(String pair, Map<String, String> m) {
		
		if(null == pair || "".equals(pair)) {
			return;
		}
		
		int indexOf = pair.indexOf("=");
		if(-1 != indexOf) {
			String k = pair.substring(0, indexOf);
			String v = pair.substring(indexOf+1, pair.length());
			if(null != k && !"".equals(k)) {
				m.put(k, v);
			}
		} else {
			m.put(pair, "");
		}
	}
	
	/**
	 * BufferedReaderת����String<br/>
	 * ע��:���ر���Ҫ���д���
	 * @param reader
	 * @return String
	 * @throws IOException
	 */
	public static String bufferedReader2String(BufferedReader reader) throws IOException {
		StringBuffer buf = new StringBuffer();
		String line = null;
		while( (line = reader.readLine()) != null) {
			buf.append(line);
			buf.append("\r\n");
		}
				
		return buf.toString();
	}
	
	/**
	 * �������<br/>
	 * ע��:���ر���Ҫ���д���
	 * @param out
	 * @param data
	 * @param len
	 * @throws IOException
	 */
	public static void doOutput(OutputStream out, byte[] data, int len)
			throws IOException {
		int dataLen = data.length;
		int off = 0;
		while (off < data.length) {
			if (len >= dataLen) {
				out.write(data, off, dataLen);
				off += dataLen;
			} else {
				out.write(data, off, len);
				off += len;
				dataLen -= len;
			}
			out.flush();
		}

	}
	
	/**
	 * ��ȡSSLContext
	 * @param trustFile 
	 * @param trustPasswd
	 * @param keyFile
	 * @param keyPasswd
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws UnrecoverableKeyException 
	 * @throws KeyManagementException 
	 */
	public static SSLContext getSSLContext(
			FileInputStream trustFileInputStream, String trustPasswd,
			FileInputStream keyFileInputStream, String keyPasswd)
			throws NoSuchAlgorithmException, KeyStoreException,
			CertificateException, IOException, UnrecoverableKeyException,
			KeyManagementException {

		// ca
		TrustManagerFactory tmf = TrustManagerFactory.getInstance(HttpClientUtil.SunX509);
		KeyStore trustKeyStore = KeyStore.getInstance(HttpClientUtil.JKS);
		trustKeyStore.load(trustFileInputStream, HttpClientUtil
				.str2CharArray(trustPasswd));
		tmf.init(trustKeyStore);

		final char[] kp = HttpClientUtil.str2CharArray(keyPasswd);
		KeyManagerFactory kmf = KeyManagerFactory.getInstance(HttpClientUtil.SunX509);
		KeyStore ks = KeyStore.getInstance(HttpClientUtil.PKCS12);
		ks.load(keyFileInputStream, kp);
		kmf.init(ks, kp);

		SecureRandom rand = new SecureRandom();
		SSLContext ctx = SSLContext.getInstance(HttpClientUtil.TLS);
		ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), rand);

		return ctx;
	}
	
	/**
	 * ��ȡCA֤����Ϣ
	 * @param cafile CA֤���ļ�
	 * @return Certificate
	 * @throws CertificateException
	 * @throws IOException
	 */
	public static Certificate getCertificate(File cafile)
			throws CertificateException, IOException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		FileInputStream in = new FileInputStream(cafile);
		Certificate cert = cf.generateCertificate(in);
		in.close();
		return cert;
	}
	
	/**
	 * �ַ�ת����char����
	 * @param str
	 * @return char[]
	 */
	public static char[] str2CharArray(String str) {
		if(null == str) return null;
		
		return str.toCharArray();
	}
	
	public static void storeCACert(Certificate cert, String alias,
			String password, OutputStream out) throws KeyStoreException,
			NoSuchAlgorithmException, CertificateException, IOException {
		KeyStore ks = KeyStore.getInstance("JKS");

		ks.load(null, null);

		ks.setCertificateEntry(alias, cert);

		// store keystore
		ks.store(out, HttpClientUtil.str2CharArray(password));

	}
	
	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
	
	public static byte[] InputStreamTOByte(InputStream in) throws IOException{  
		
		int BUFFER_SIZE = 4096;  
		ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
        byte[] data = new byte[BUFFER_SIZE];  
        int count = -1;  
        
        while((count = in.read(data,0,BUFFER_SIZE)) != -1)  
            outStream.write(data, 0, count);  
          
        data = null;  
        byte[] outByte = outStream.toByteArray();
        outStream.close();
        
        return outByte;  
    } 
	public static String InputStreamTOString(InputStream in,String encoding) throws IOException{  

        return new String(InputStreamTOByte(in),encoding);
        
    }
	 public static String getURLContent(String urlStr) {             
	       /** 网络的url地址 */        
	    URL url = null;              
	       /** http连接 */    
	    //HttpURLConnection httpConn = null;            
	        /**//** 输入流 */   
	    BufferedReader in = null;   
	    StringBuffer sb = new StringBuffer();   
	    try{     
	     url = new URL(urlStr);     
	     in = new BufferedReader( new InputStreamReader(url.openStream(),"UTF-8") );   
	     String str = null;    
	     while((str = in.readLine()) != null) {    
	      sb.append( str );     
	            }     
	        } catch (Exception ex) {   
	            
	        } finally{    
	         try{             
	          if(in!=null) {  
	           in.close();     
	                }     
	            }catch(IOException ex) {      
	            }     
	        }     
	        String result =sb.toString();     
	       // System.out.println(result);     
	        return result;    
	        }    

}
