package com.mcu32.firstq.wechat.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import com.mcu32.firstq.wechat.bean.UserInfo;

public class MySession {
	public static String SESSIONID = "sessionId";//唯一id
	private static int TIMESCOPE = 60;//单位：分钟
	private static String LASTREFRESHTIME = "lastRefreshTime";//
	
	private MySession(){}
	
	private static Map<String, Map<String, Object>> mySessionContext = new ConcurrentHashMap<String, Map<String, Object>>();

	///  set ////////////////////////////////////
	
	/* //不启用
	 * public static void setMySessionContext(Map<String, Map<String, Object>> sessionContext) {
		WechatSession.mySessionContext = sessionContext;
	}*/
	
	public static void setSession(String sessionId,Map<String, Object> sessionAttributs) {
		
		//LogUtil.debug("setSession ");
		if(null!=sessionId && null!=sessionAttributs){
			sessionAttributs.put(LASTREFRESHTIME, new Date().getTime());
			mySessionContext.put(sessionId, sessionAttributs);
		}
	}
	
	public static void setAttribute(String sessionId, String key, Object obj) {
		//LogUtil.debug("setAttribute sessionId is"+sessionId+" key is "+key+" obj is "+obj);
		if(null!=sessionId){
			if(null==mySessionContext.get(sessionId)){
				Map<String,Object> sessionAttributs=new ConcurrentHashMap<String,Object>();
				sessionAttributs.put(key, obj);
				sessionAttributs.put(LASTREFRESHTIME, new Date().getTime());
				
				mySessionContext.put(sessionId, sessionAttributs);
			}else{
				updateSessionLastRefreshTimeById(sessionId);
				mySessionContext.get(sessionId).put(key, obj);
			}
		}
	}
	
	///  get ////////////////////////////////////
	
	/* //不启用
	 * public static Map<String, Map<String, Object>> getMySessionContext() {
		return mySessionContext;
	}*/
	
	private static Map<String, Object> getSessionById(String sessionId) {
		return mySessionContext.get(sessionId);
	}

	
	@SuppressWarnings("unchecked")
	public static <T> T getAttribute(String sessionId, String key) {
		//LogUtil.debug("getAttribute sessionId is"+sessionId+" key is "+key);
		if(null!=mySessionContext.get(sessionId)){
			updateSessionLastRefreshTimeById(sessionId);
			return (T) mySessionContext.get(sessionId).get(key);
		}
		return null;
	}
	
	//	remove ////////////////////////
	public static void removeSessionById(String sessionId) {
		mySessionContext.remove(sessionId);
	}
	
	public static void removeAttribute(String sessionId, String key) {
		if(null!=mySessionContext.get(sessionId)){
			updateSessionLastRefreshTimeById(sessionId);			
			mySessionContext.get(sessionId).remove(key);
		}
	}

	
	
//  Tools ///////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 根据 key 更新用户 Map 的 末次刷新时间 LastRefreshTime
	 * 如果没有通过key 得到Map，不操作
	 * @param key
	 */
	public static void updateSessionLastRefreshTimeById(String sessionId) {
		//LogUtil.debug("updateSessionLastRefreshTimeById sessionId is"+sessionId);
		Map<String, Object> map = MySession.getSessionById(sessionId);
		if(map != null){
			map.put(LASTREFRESHTIME, new Date().getTime());
		}
	}
	/**
	 * 启动 定时器，定时清除用户
	 */
	public static void startClearSessionTimer(final int minute){
		if(minute<2) return;
		int harfminute=minute/2;
		Timer timer = new Timer(true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				clearSessionByTimeScope(minute);
				//System.out.println("ticket is "+ticket.getTicket()+" refesh at "+DateUtil.DateToString(new Date(),"yyyy-MM-dd HH:mm:ss:SSS"));
			}
		},0,1000*60*harfminute);
	}
	
	/**
	 * 启动 定时器，定时清除用户，默认60分钟
	 */
	public static void startClearSessionTimer(){
		startClearSessionTimer(TIMESCOPE);
	}
	/** 清一次过时的map */
	public static void clearSessionByTimeScope() {
		clearSessionByTimeScope(TIMESCOPE);
	}
	/** 清一次过时的map
	 * 如果 map中的 最后刷新时间 跟当前时间 差值大于 时间参数，就清除map
	 * 获取存储后台用户的集合，然后遍历集合，获取每个后台用户的登录时间（只要调用后台获取登录令牌接口，该时间就更新）,接着计算当前系统时间与用户登录时间差，若大于规定范围内时长，则清空集合中，该后台用户的信息（适用于，若后台数据库，用户数据被误删了，等到时间一到，就会让该微信用户重新注册）
	 * @param minute 分钟数
	 */
	public static void clearSessionByTimeScope(int minute) {
		showSessionContext();
		if(mySessionContext != null && mySessionContext.size() > 0) {//若baseMap不为空，则进行遍历
			Iterator<Map.Entry<String, Map<String, Object>>> it = mySessionContext.entrySet().iterator(); 
	        while(it.hasNext()){//获取 map 中最后的刷新时间，并与当前时间进行比较，若大于规定时长，则将该用户清空
	            Map.Entry<String, Map<String, Object>> entry= it.next(); 
	            Map<String, Object> value = entry.getValue();
	            if(value != null && value.size() > 0) {
	            	Long sessionTime=(Long)value.get(LASTREFRESHTIME);
	            	Long currentTime=new Date().getTime();
	            	//System.out.println("clearSessionByTimeScope sessionTime is" +sessionTime+" currentTime is "+currentTime +" currentTime-sessionTime == " +(currentTime-sessionTime) +" minute*60*1000 is "+(minute*60*1000) +" distance is "+(currentTime-sessionTime> minute*60*1000));
	            	if(currentTime-sessionTime> minute*60*1000){
	            		it.remove();
	            	}
	            }
	        } 
		} else {
			//System.out.println("clearSessionByTimeScope " +new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()) + " baseMap is empty");
		}
	}

	/**
	 * 遍历map中的内容，打印出来
	 */
	public static void showSessionContext() {
		
		String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
		System.out.println("showSessionContext currentTime = " + currentTime);
		//若baseMap不为空，则进行遍历，获取每个用户的登录时间，并与当前时间进行比较，若大于规定时长，则将该用户清空
		if(mySessionContext != null && mySessionContext.size() > 0) {
			Iterator<Map.Entry<String, Map<String, Object>>> it = mySessionContext.entrySet().iterator(); 
	        while(it.hasNext()){
	            Map.Entry<String, Map<String, Object>> entry= it.next(); 
	            Map<String, Object> value = entry.getValue();
	            System.out.println("key = " + entry.getKey() + ",value=" + value);
	            if(value != null && value.size() > 0) {
		            Iterator<Map.Entry<String, Object>> its = value.entrySet().iterator(); 
		            while(its.hasNext()) {
		            	Map.Entry<String, Object> entrys = its.next(); 
			            String keys = entrys.getKey(); 
			            Object values = entrys.getValue();
			            if(values instanceof UserInfo){
			            	UserInfo u=(UserInfo)values;
			            	CopyObject.printInvoke(u);
			            }
			            System.out.println("keys = " + keys + ",values=" + values);
		            }
	            }
	        } 
		} else {
			System.out.println("showSessionContext "+currentTime + " mySessionContext is empty");
		}
	}
	

}
