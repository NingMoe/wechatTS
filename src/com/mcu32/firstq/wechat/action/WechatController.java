package com.mcu32.firstq.wechat.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gtm.wechat.popular.support.TokenAndTicketManager;
import com.mcu32.firstq.common.bean.bo.PhotoBO;
import com.mcu32.firstq.common.bean.bo.PowerCutInfo;
import com.mcu32.firstq.common.bean.bo.RelationStationBo;
import com.mcu32.firstq.common.bean.bo.StationBO;
import com.mcu32.firstq.common.bean.bo.UserBO;
import com.mcu32.firstq.common.bean.bo.WeathInfoBO;
import com.mcu32.firstq.common.exception.FirstQException;
import com.mcu32.firstq.common.util.DwPowerCutInterfaceThread;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.bean.UserInfo;
import com.mcu32.firstq.wechat.bean.Weather;
import com.mcu32.firstq.wechat.tools.liufeng.bean.Article;
import com.mcu32.firstq.wechat.tools.liufeng.message.response.NewsMessage;
import com.mcu32.firstq.wechat.tools.liufeng.message.response.TextMessage;
import com.mcu32.firstq.wechat.tools.liufeng.util.MessageUtil;
import com.mcu32.firstq.wechat.tools.liufeng.util.SignUtil;
import com.mcu32.firstq.wechat.util.FirstqTool;
import com.mcu32.firstq.wechat.util.HttpClientUtil;
import com.mcu32.firstq.wechat.util.LogUtil;
import com.mcu32.firstq.wechat.util.ShortUrlUtil;
import com.mcu32.firstq.wechat.util.ToolUtil;
import com.mcu32.firstq.wechat.util.WebContentConstants;




@Controller
@RequestMapping(value = "/getWeiXinMessage")
public class WechatController extends BaseController{
	
    /**
	 * 获得信息
	 * /getWeiXinMessage/getMessages
	 */
    @RequestMapping(value = "/getMessages/**")
	public String getMessages(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		 try{
			 response.setCharacterEncoding("UTF-8");
			 request.setCharacterEncoding("UTF-8");
			 
             if ("GET".equals(request.getMethod().toUpperCase())) {
            	 //微信加密签名
                 String signature = request.getParameter("signature");
                 //时间戳
                 String timestamp = request.getParameter("timestamp");
                 //随机数
                 String nonce = request.getParameter("nonce");
                 //随机字符串
                 String echostr =request.getParameter("echostr");
            	 if(StringUtils.isNotEmpty(echostr)&&StringUtils.isNotEmpty(signature)&&StringUtils.isNotEmpty(timestamp)&&StringUtils.isNotEmpty(nonce)){
            		 echostr=SignUtil.checkAuthentication(signature, timestamp, nonce, echostr,ToolUtil.getAppConfig("dyxxweixintoken"));
            	 }
                //验证通过返回随即字串
            	response.getWriter().write(echostr);
            	response.getWriter().flush();
            }else{
                // 调用核心业务类接收消息、处理消息
            	String respMessage=processRequest(request,session);
                // 响应消息  
                PrintWriter out = response.getWriter();  
                out.print(respMessage);  
                out.close();
                //LogFileIo.WriteLogs("返回的数据: <br>\n"+respMessage);
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
        return null;
    }

	/**
	 * 得到消息类型，调用对应方法分别处理消息
	 */
	private String processRequest(HttpServletRequest request, HttpSession session) {
		String respMessage = null;
		try {
			Map<String, String> requestMap = MessageUtil.parseXml(request);// xml请求解析
			String fromUserName = requestMap.get("FromUserName");//openId
			
			//TODO user信息存到mySession里面
			UserInfo userInMySession=loginByOpenId(fromUserName,session);
			
			String msgType = requestMap.get("MsgType");// 消息类型
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {/*文本消息*/
				
				respMessage=getResponseStrFromText(requestMap);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {/*图片消息*/
			
				respMessage=textResponMessage(requestMap,"唉，视力又下降了，该换眼镜了！");
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {/*地理位置消息*/
				
				String lo=requestMap.get("Location_Y");//地理位置经度
				String la=requestMap.get("Location_X");//地理位置维度
				List<StationBO> stationBOList=new ArrayList<StationBO>();
				try {
					int calculatorDistance = Integer.parseInt(ToolUtil.getAppConfig("CalculatorDistance")); 
					int actualDistance = Integer.parseInt(ToolUtil.getAppConfig("ActualDistance")); 
					stationBOList= FirstQInterfaces.getIStationService().getStationByLocation(Double.parseDouble(lo),Double.parseDouble(la), calculatorDistance, actualDistance);
					if(stationBOList == null || stationBOList.size() == 0){
						return notFoundTextResponMessage(requestMap);
					}
					if(stationBOList.size() > 5){
						stationBOList = stationBOList.subList(0, 5);
					}
				} catch (Exception e) {
					LogUtil.error(e);
				}
			
				//获取图文列表
				List<Article> list = getStationArticleList(fromUserName,stationBOList,userInMySession.getToken());
				respMessage = responNewsMessage(requestMap, list);
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {/*链接消息*/
			
				respMessage=textResponMessage(requestMap,"还是不要乱跑了，好好放牛吧！");
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {/*音频消息*/
			
				respMessage=textResponMessage(requestMap,"老啦老啦，耳朵不好使啦！");
			} else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {/*事件推送*/
			
				// 获取事件的名称
				String eventType = requestMap.get("Event");
				if(eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)){
					String lo=requestMap.get("Longitude");//地理位置经度
					String la=requestMap.get("Latitude");//地理位置维度
					userInMySession.setLo(lo);
					userInMySession.setLa(la);
				}else if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {/*订阅*/
					UserBO user=isSubscribe(fromUserName);
					if(null==user){//没有注册返回提示
						respMessage=textResponMessage(requestMap,"亲~ 终于等到您了，可是还未进行账号绑定哦！请点击【<a href=\"http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/register/gotoRegister.html?openId="+fromUserName+"\">账号绑定</a>】完成账号绑定，轻松使用第一象限所有功能哦！");
			        }else{//曾经订阅过
			        	respMessage=textResponMessage(requestMap,"亲~ 欢迎再次回来！不要在丢下我了！");
			        }
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {/*取消订阅*/
					
					
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {/*自定义菜单点击事件*/
					
					
					//String eventKey=requestMap.get("EventKey");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			respMessage = "请求处理异常，请稍候尝试！";
		}
		
		return respMessage;
	}
	
	/**
	 * 处理文字请求，返回xml字符串
	 */
	private String getResponseStrFromText(Map<String, String> requestMap) {
		StringBuilder rsft=new StringBuilder();
		String requestContent=requestMap.get("Content");
		String openId=requestMap.get("FromUserName");
		try{
			UserBO user=isSubscribe(openId);
			if(null==user){//没有注册返回提示
	        	return textResponMessage(requestMap,"亲~ 终于等到您了，可是还未进行账号绑定哦！请点击【<a href=\"http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/register/gotoRegister?openId="+openId+"\">账号绑定</a>】完成账号绑定，轻松使用巡检功能哦！");
			}
			if("#mm".equals(requestContent)||"#MM".equals(requestContent)||"#密码".equals(requestContent)){
				String pwd = FirstQInterfaces.getIUserService().getDynamicPwd(user.getUserId(), 6);
				rsft.append(pwd);
		        if(StringUtils.isNotEmpty(rsft.toString()))
		        	return textResponMessage(requestMap, rsft.toString());
			}else if(requestContent.indexOf("天气") != -1){
				String sendContent = "亲，您是否需要查询天气信息，请输入 天气 地市名称 进行天气预报查询。";
				String[] split = requestContent.split("\\s+");

				if (split[0] != null && split.length > 0 && split[0].trim().equals("天气")) {

					WeathInfoBO w = new WeathInfoBO();
					if (split.length == 1) {// 用户只输入“天气”关键字时默认查询该群归属地市的天气
						
						w.setCity_name(user.getCountyId());
						
					} else if (split.length == 2) {
						
						w.setCity_name(split[1].trim());
						
					} else {
						return textResponMessage(requestMap,sendContent);
					}
					WeathInfoBO wbo = FirstQInterfaces.getIRobotInfoService().getWeathInfo(w);
					// 判断是否可以查询到
					if (wbo != null && wbo.getCity_id() != null) {

						// http://www.k780.com/ 网站账号 chenyan 注册的账号获取的appkey
						String url = "http://api.k780.com:88/?app=weather.future&weaid="
								+ wbo.getCity_id()
								+ "&appkey=15391&sign=4ff7b16f3ee09d38a297e2e04baaa6c6&format=json";
						JSONObject rjson = HttpClientUtil.getJSONObjResult(url, "");
						JSON resultListJson = (JSON) rjson.get("result");
						List<Weather> weatherList = JSON.parseArray(resultListJson
								.toJSONString(), Weather.class);
						String wea = "";
						if (weatherList != null && weatherList.size() != 0) {
							for (int i = 0; i < weatherList.size(); i++) {
								Weather nowW = (Weather) weatherList.get(i);
								if (i == 0) {
									wea = "今天 " + nowW.getDays() + "，"
											+ nowW.getWeek() + "，"
											+ nowW.getCitynm() + " "
											+ nowW.getWeather() + "，最高气温："
											+ nowW.getTemp_high() + "℃，最低气温："
											+ nowW.getTemp_low() + "℃，风力："
											+ nowW.getWinp() + "\n";
								}
								if (i != 0 && i < 2) {
									wea = wea + "明天 "+nowW.getDays() + "，"+nowW.getWeek()+"，" 
											+ nowW.getWeather() + "，最高气温："
											+ nowW.getTemp_high() + "℃，最低气温："
											+ nowW.getTemp_low() + "℃，风力："
											+ nowW.getWinp() + "\n";
								}
							}
							String wurl = "http://"
									+ ToolUtil.getAppConfig("CallBackDomain")
									+ "/wechat-cs/RM/W";
							String shortUrl=ShortUrlUtil.creatShort(wurl);
							if(shortUrl !=null){
								wurl=shortUrl;
							}
							//sendContent = wea + "点击查看更多天气预报" + wurl;
							 sendContent = wea;
						} else {
							sendContent = "亲，抱歉哦，没有查询到" + split[1].trim()
									+ "的天气情况。";
						}
					} else {
						sendContent = "亲，抱歉哦，没有查询到" + user.getCountyId() + "的天气情况。";
					}
				}
				return textResponMessage(requestMap,sendContent);
				
			}else if(requestContent.indexOf("停电") != -1){
				
				String sendContent = "亲，您是否需要查询停电信息，请输入 停电  地市名称 进行停电查询。";
				
				String[] split = requestContent.split("\\s+");
				String orgName = "";
				String qtime = "";
			
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				Calendar date = Calendar.getInstance();
				date.setTime(new Date());
				date.set(Calendar.DATE, date.get(Calendar.DATE)+2);

				String endTime =sf.format(date.getTime());

				if (split[0] != null && split[0].trim().equals("停电")) {

					if (split.length == 1) {// 用户只输入“停电”关键字时默认查询该群归属地市的停电
						orgName = user.getCountyId();
						if(orgName==null ||orgName==""){
							return textResponMessage(requestMap,sendContent);
						}
						qtime = sf.format(new Date());
					} else if (split.length == 2) { // 如果后面无日期 默认查询当天的
						// 设置地市查询条件
						orgName = split[1].trim();
						qtime = sf.format(new Date());

					} else if (split.length == 3) {
						orgName = split[1].trim();
						qtime = split[2].trim();

					} else {
						return textResponMessage(requestMap,sendContent);
					}

					// 将从数据库查询改为直接调用接口
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					//String totalCount = DwPowerCutInterface.getCountDwPowerCut_W(orgName, qtime, endTime);
					
					//查询停电次数
					String totalCount=DwPowerCutInterfaceThread.getCountDwPowerCut(orgName, qtime, endTime);
					    if(totalCount.equals("-1")){
					      sendContent="亲，抱歉哦，暂不支持省或直辖市查询，请输入地市或县名称进行查询。";
					     }else if(totalCount.equals("-2")){
						    sendContent="亲，抱歉哦，暂未开通该城市的停电查询。";
						 }else if(totalCount.equals("-3")){
						    sendContent="亲，抱歉哦，国家电网数据源出现故障，请稍后查询。";
						 }else if(totalCount.equals("-4")){
						    sendContent="葫芦岛不支持区县查询，请输入停电+空格+葫芦岛查询所有停电详情";
						 }else{
							//影响基站总数
							int stationCount=0;
							//查询停电线路List
							List<PowerCutInfo> powList=DwPowerCutInterfaceThread.getDwPowerCut("",orgName, qtime, endTime);
							if(powList !=null && powList.size()>0){
							 RelationStationBo relationStationBo=new RelationStationBo();
							 String lineNo="";
							 String lineName="";
							 for(PowerCutInfo line:powList){
									if(line.getLineNo()!=null)
								    	lineNo += "'"+line.getLineNo()+"',";
									lineName+="r.linename like '%"+line.getLineName()+"%' or ";
								}
								if(!lineNo.equals("")){
									lineNo = lineNo.substring(0,lineNo.length()-1);
								}
								relationStationBo.setLineno(lineNo);
								if(!lineName.equals("")){
									lineName=lineName.substring(0,lineName.length()-3);	
								}
								relationStationBo.setCity_name(orgName);
								relationStationBo.setLinename(lineName);
								List<RelationStationBo> stationList = FirstQInterfaces.getIRobotInfoService().getAllRelationStationList(relationStationBo);
					
						       if(stationList !=null){
							      stationCount=stationList.size();
						         }
							}
						sendContent = "截止"+ sdf.format(new Date())+"分，" +orgName+" 预计未来三天有" + totalCount
								+ "条线路停电，影响 "+new Integer(stationCount).toString()+" 座基站，点击";
						String url = "http://"
								+ ToolUtil.getAppConfig("CallBackDomain")
								+ ":9090/wechat-cs/RM/D?orgName="
								+ URLEncoder.encode(URLEncoder.encode(orgName, "UTF-8"),"UTF-8");
						String shortUrl=ShortUrlUtil.creatShort(url);
						if(shortUrl !=null){
							url=shortUrl;
						}
						sendContent = sendContent +url+"查看停电详情。" ;
					    }
					    return textResponMessage(requestMap,sendContent);

				}
				
			}else if("#yh".equals(requestContent)){
				//隐患排查
				List<Article> list=new ArrayList<Article>();
				
				Article a=new Article();
				a.setId(0);
				a.setPicurl("http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/assets/i/bstation.png");
				a.setUrl("http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/checkHiddenTrouble/goStationInfo.html");
				a.setTitle("隐患排查");
				a.setDescription("场景聚焦，操作便捷，报告自推送。（聚焦存量站蓄电池隐患排查，嵌入便捷、易用、务实操作流程，附加隐患报告自动推送功能）");
				list.add(a);
				return responNewsMessage(requestMap,list);
			}else if(requestContent.indexOf("日报") != -1){
				String[] split = requestContent.split("\\s+");
				List<Article> list=new ArrayList<Article>();
				Article a=new Article();
				a.setId(0);
				if(split.length ==2 && StringUtils.isNotEmpty(split[1])){
					if("发电".equals(split[1])){
						a.setPicurl("http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/assets/i/report_bg.jpg");
						a.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ToolUtil.getAppConfig("dyxxAPPID")+"&redirect_uri=http%3a%2f%2f"+ToolUtil.getAppConfig("CallBackDomain")+"%2f"+getContextPath().replace("/", "")+"%2fmain%2ftoPage%3ftoPageUrl%3dreport%2fdaysReportList&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
						a.setTitle("日报查询");
						a.setDescription("最近7日发电统计，包含发电次数、基站数、总时长");
					}
				}else{
					String sendContent = "亲，您是否需要查询周报信息，请输入 周报 发电 进行相关操作。";
					return textResponMessage(requestMap,sendContent);
				}
				list.add(a);
				return responNewsMessage(requestMap,list);
			}else if(requestContent.indexOf("周报") != -1){
				String[] split = requestContent.split("\\s+");
				List<Article> list=new ArrayList<Article>();
				Article a=new Article();
				a.setId(0);
				if(split.length ==2 && StringUtils.isNotEmpty(split[1])){
					if("发电".equals(split[1])){
						a.setPicurl("http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/assets/i/report_bg.jpg");
						a.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ToolUtil.getAppConfig("dyxxAPPID")+"&redirect_uri=http%3a%2f%2f"+ToolUtil.getAppConfig("CallBackDomain")+"%2f"+getContextPath().replace("/", "")+"%2fmain%2ftoPage%3ftoPageUrl%3dreport%2fweekReportList&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
						a.setTitle("周报查询");
						a.setDescription("最近7周发电统计，包含发电次数、基站数、总时长（表）");
					}
				}else{
					String sendContent = "亲，您是否需要查询日报信息，请输入 日报 发电 进行相关操作。";
					return textResponMessage(requestMap,sendContent);
				}
				list.add(a);
				return responNewsMessage(requestMap,list);
			}else if(requestContent.indexOf("填报") != -1){
				String[] split = requestContent.split("\\s+");
				
				List<Article> list=new ArrayList<Article>();
				Article a=new Article();
				a.setId(0);
				if(split.length ==2 && StringUtils.isNotEmpty(split[1])){
					if("发电".equals(split[1])){
						a.setPicurl("http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/assets/i/report_bg.jpg");
						a.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ToolUtil.getAppConfig("dyxxAPPID")+"&redirect_uri=http%3a%2f%2f"+ToolUtil.getAppConfig("CallBackDomain")+"%2f"+getContextPath().replace("/", "")+"%2fmain%2ftoJob%3fjobId%3djob_4&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
						a.setTitle("填报发电");
						a.setDescription("进行发电信息的填报");
					}else if("蓄电池整治".equals(split[1])){
						a.setPicurl("http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/assets/i/report_bg.jpg");
						a.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ToolUtil.getAppConfig("dyxxAPPID")+"&redirect_uri=http%3a%2f%2f"+ToolUtil.getAppConfig("CallBackDomain")+"%2f"+getContextPath().replace("/", "")+"%2fmain%2ftoPage%3ftoPageUrl%3dreport%2fmanageBatteryRecordByProvince&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
						a.setTitle("填报蓄电池整治");
						a.setDescription("进行蓄电池整治信息的填报");
					}else if ("日常巡检".equals(split[1])) {
						a.setPicurl("http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/assets/i/report_bg.jpg");
						a.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ToolUtil.getAppConfig("dyxxAPPID")+"&redirect_uri=http%3a%2f%2f"+ToolUtil.getAppConfig("CallBackDomain")+"%2f"+getContextPath().replace("/", "")+"%2fmain%2ftoJob%3fjobId%3djob_1&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
						a.setTitle("填报日常巡检");
						a.setDescription("上站进行日常巡检的工作");
					}else if("季度巡检".equals(split[1])){
						a.setPicurl("http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/assets/i/report_bg.jpg");
						a.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ToolUtil.getAppConfig("dyxxAPPID")+"&redirect_uri=http%3a%2f%2f"+ToolUtil.getAppConfig("CallBackDomain")+"%2f"+getContextPath().replace("/", "")+"%2fmain%2ftoJob%3fjobId%3djob_6&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
						a.setTitle("填报季度巡检");
						a.setDescription("上站进行季度巡检的工作");
					}else if("放电".equals(split[1])){
						a.setPicurl("http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/assets/i/report_bg.jpg");
						a.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ToolUtil.getAppConfig("dyxxAPPID")+"&redirect_uri=http%3a%2f%2f"+ToolUtil.getAppConfig("CallBackDomain")+"%2f"+getContextPath().replace("/", "")+"%2fmain%2ftoPageNotLocation%3ftoPageUrl%3dhldischarge%2fgoStart&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
						a.setTitle("填报放电测试");
						a.setDescription("放电测试填报工作");
					}else if("代维费用".equals(split[1])){
						a.setPicurl("http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/assets/i/report_bg.jpg");
						a.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ToolUtil.getAppConfig("dyxxAPPID")+"&redirect_uri=http%3a%2f%2f"+ToolUtil.getAppConfig("CallBackDomain")+"%2f"+getContextPath().replace("/", "")+"%2fmain%2ftoPageNotLocation%3ftoPageUrl%3dmaintenancecost%2fgoCost&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
						a.setTitle("填报代维费用");
						a.setDescription("代维费用填报工作");
					}else if("门禁".equals(split[1])){
						a.setPicurl("http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/assets/i/report_bg.jpg");
						a.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ToolUtil.getAppConfig("dyxxAPPID")+"&redirect_uri=http%3a%2f%2f"+ToolUtil.getAppConfig("CallBackDomain")+"%2f"+getContextPath().replace("/", "")+"%2fmain%2ftoPageNotLocation%3ftoPageUrl%3ddoorSystem%2fmain&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect");
						a.setTitle("填报门禁进度");
						a.setDescription("门禁进度填报工作");
					}
				}else{
					String sendContent = "亲，您是否需要上站进行工作，请输入 填报 发电/蓄电池/日常巡检/季度巡检 进行相关操作。";
					return textResponMessage(requestMap,sendContent);
				}
				
				list.add(a);
				return responNewsMessage(requestMap,list);
			}else if ("help".equals(requestContent) || "帮助".equals(requestContent)) {
				String sendContent = "【指令说明】：\n"+
						 "日报 发电（查询发电日报）\n"+
						 "周报 发电（查询发电周报）\n"+
						 "停电 地市（查询地市的电网停电）\n"+
						 "基站 基站名称(查询基站详情）\n"+
						 "天气 地市 (查询地市的天气信息)\n"+
						 "填报 蓄电池整治（填报蓄电池整治数据）\n"+
						 "填报 日常巡检（填报日常巡检信息）\n"+
						 "填报 季度巡检（填报季度巡检信息）\n"+
						 "填报 发电（填报发电信息）\n"+
						 "填报 代维费用（填报代维费用）\n"+
						 "填报 门禁（填报门禁）";
				
				return textResponMessage(requestMap,sendContent);
			}else if(!"".equals(requestContent)){
				String[] contentStrs = org.springframework.util.StringUtils.tokenizeToStringArray(requestContent, " ");
				if(contentStrs == null || contentStrs.length != 2){
					return errorTextResponMessage(requestMap);
				}
				HashMap<String, String> queryCondition = new HashMap<String, String>();

				//queryCondition.put("provinceId", "");
				//queryCondition.put("cityId", "");
				queryCondition.put("fuzzyArea", contentStrs[0]);
				queryCondition.put("countyId", "");
				queryCondition.put("stationName", contentStrs[1]);
				queryCondition.put("stationNo", "");
				queryCondition.put("stationAddress", "");
				queryCondition.put("checkStatus", "");
				
				
				List<StationBO> stationList=FirstQInterfaces.getIStationService().getStationsList(queryCondition, 5, 1);
				//List<StationInfo> stationList=stationService.getStationList(provinceId, cityId, countyId, stationName, stationNo, stationAddress, status, 1, 5);
				if(stationList == null || stationList.size() == 0){
					return notFoundTextResponMessage(requestMap);
				}
				//获取图文列表
				List<Article> list = getStationArticleList(openId,stationList,user.getUserId());
				String articleListString=responNewsMessage(requestMap,list);
				if(StringUtils.isNotEmpty(articleListString))
					return articleListString;
				
				if(StringUtils.isNotEmpty(rsft.toString()))
					return textResponMessage(requestMap, rsft.toString());
			}else  if("getMyOpenID".equals(requestContent)){
				
				return textResponMessage(requestMap, openId);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return defaultTextResponMessage(requestMap);
	}
	
	/**
	 * 将基站列表改成微信的图文信息
	 */
	private List<Article> getStationArticleList(String openId,List<StationBO> stationList,String userId){
		List<Article> list=new ArrayList<Article>();
		
		for(int i=0;i<stationList.size();i++){
			StationBO station=stationList.get(i);
			if(i==0){
				station.setThumbLocation("http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/assets/i/bstation.png");
			}else{
				String roomPhoto="";
				if (station.getPhotoList() != null) {
					for(PhotoBO photo:station.getPhotoList()){
						if(WebContentConstants.STATION_TOWER.equals(photo.getPhotoType())){
							if(WebContentConstants.STATUS_WAIT_UPLOAD.equals(photo.getStatus())){
								station.setThumbLocation("http://"+ToolUtil.getAppConfig("CallBackDomain") +"/wechat/assets/i/waitUpload.png");
							}else{
								station.setThumbLocation(FirstqTool.getPhotoLocation(photo.getThumbLocation()));
							}
							break;
						}else if(WebContentConstants.STATION_ROOM.equals(photo.getPhotoType())){
							if("".equals(roomPhoto)){
								if(WebContentConstants.STATUS_WAIT_UPLOAD.equals(photo.getStatus())){
									station.setThumbLocation("http://"+ToolUtil.getAppConfig("CallBackDomain") +"/wechat/assets/i/waitUpload.png");
								}else{
									roomPhoto=FirstqTool.getPhotoLocation(photo.getThumbLocation());
								}
							}
						}
					}
				}
				if(StringUtils.isEmpty(station.getThumbLocation()))
					station.setThumbLocation(roomPhoto);
				
				if(StringUtils.isEmpty(station.getThumbLocation()))
					station.setThumbLocation("http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/assets/i/bstation.png");
				
			}
		}

		for(int i=0;i<stationList.size();i++){
			StationBO s=stationList.get(i);
			Article a=new Article();
			a.setId(i);
			
			a.setPicurl(s.getThumbLocation().replace("\\", "/"));
			
			a.setUrl("http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/station/getStationDetail?openId=" + openId+"&stationId="+s.getStationId());
			
			String provence = null==s.getProvinceId() || null!=s.getProvinceId() && s.getProvinceId().equals( s.getCityId() ) ? "" : s.getProvinceId();
			String city = null==s.getCityId() ? "" : ("".equals(provence)?"":",")+s.getCityId();
			String country = null==s.getCountyId() ? "" : ("".equals(city)?"":",")+s.getCountyId();
			
			if(s.getDistance() != null){
				a.setTitle(s.getStationName()+"   "+ provence + city + country+"，距离"+s.getDistance().intValue()+"米");
			}else{
				a.setTitle(s.getStationName()+"   "+ provence + city + country);
			}
			a.setDescription("基站地址："+ provence + city + country +s.getStationAddress()+"\n基站类型："+s.getStationType());
			list.add(a);
		}
		return list;
	}
	
/////  如果只想看微信聊天框的逻辑，下面的不用看了   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 返回的图文信息
	 * @param requestMap
	 * @param respContent
	 * @return
	 */
	private String responNewsMessage(Map<String, String> requestMap,List<Article> alist){
		if(null==alist||alist.size()==0) return null;
		String respMessage=null;
		// 发送方帐号（open_id）
		String fromUserName = requestMap.get("FromUserName");
		// 公众帐号
		String toUserName = requestMap.get("ToUserName");
		List<com.mcu32.firstq.wechat.tools.liufeng.message.response.Article> mealist=new ArrayList<com.mcu32.firstq.wechat.tools.liufeng.message.response.Article>();
		for(Article a:alist){
			com.mcu32.firstq.wechat.tools.liufeng.message.response.Article ma=new com.mcu32.firstq.wechat.tools.liufeng.message.response.Article();
			ma.setDescription(a.getDescription());
			ma.setPicUrl(a.getPicurl());
			ma.setTitle(a.getTitle());
			
			ma.setUrl(a.getUrl());
			mealist.add(ma);
		}
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setFromUserName(toUserName);//发送方和接收访换位置
		newsMessage.setToUserName(fromUserName);//发送方和接收访换位置
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);
		newsMessage.setArticleCount(alist.size());
		newsMessage.setArticles(mealist);
		respMessage = MessageUtil.newsMessageToXml(newsMessage);
		return respMessage;
	}
	
	/**
	 * 返回一个自定义的文本信息
	 * @param requestMap
	 * @param respContent
	 * @return 
	 */
	private String textResponMessage(Map<String, String> requestMap,String respContent){
		
		String fromUserName = requestMap.get("FromUserName");// 发送方帐号（open_id）
		String toUserName = requestMap.get("ToUserName");// 公众帐号
		
		// 回复文本消息
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);//发送方和接收访换位置
		textMessage.setFromUserName(toUserName);//发送方和接收访换位置
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);
		textMessage.setContent(respContent);
		return MessageUtil.textMessageToXml(textMessage);
	}
	
	/**
	 * 判断是否关注，并且注册
	 * @param openId
	 * @return
	 */
	private UserBO isSubscribe(String openId){
		UserBO userBO = new UserBO();
		userBO.setWeChat(openId);
		UserBO returnUserBO = null;
		try {
			returnUserBO = FirstQInterfaces.getIUserService().getUserInfoByLogin(userBO);
			 if(null!=returnUserBO && null!=returnUserBO.getUserId() && !"".equals(returnUserBO.getUserId())) {//没有注册返回提示
				 return returnUserBO;
			 }
		} catch (FirstQException e) {
			e.printStackTrace();
		}
        return null;
	}
	/**
	 * 返回一个自定义的文本信息
	 * @param requestMap
	 * @param respContent
	 * @return 
	 */
	private String defaultTextResponMessage(Map<String, String> requestMap){
		return textResponMessage(requestMap,"亲~ 请输入城市名称+空格+基站名称,如：北京 双桥，或发送您的位置，第一象限的小伙伴会立即为您查找，赶紧试试吧！");
	}
	
	/**
	 * 返回没有找到的文本信息
	 * @param requestMap
	 * @param respContent
	 * @return 
	 */
	private String errorTextResponMessage(Map<String, String> requestMap){
		return textResponMessage(requestMap,"亲~ 很抱歉，您输的东东我们不知道哦！请输入城市名称+空格+基站名称,如：北京 双桥，或发送您的位置，第一象限的小伙伴会立即为您查找，赶紧试试吧！");
	}
	
	/**
	 * 返回没有找到的文本信息
	 * @param requestMap
	 * @param respContent
	 * @return 
	 */
	private String notFoundTextResponMessage(Map<String, String> requestMap){
		return textResponMessage(requestMap,"亲~ 很遗憾，什么都没有哦！");
	}
	
	

	/* setters and getters>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> */
	
	
	/**
	 * http://www.czxkpower.com/dyxxwechat/getWeiXinMessage/initTokenAndTicket.json
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/initTokenAndTicket")
    public String inittoken(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model) throws IOException{
		String appid=ToolUtil.getAppConfig("dyxxAPPID");
		String appsecret=ToolUtil.getAppConfig("dyxxAPPSECRET");
		
		TokenAndTicketManager.init(appid, appsecret);
		
		model.addAttribute("token", TokenAndTicketManager.getToken(appid));
		model.addAttribute("ticket", TokenAndTicketManager.getTicket(appid));
		
		//System.out.println(TokenAndTicketManager.getToken(appid)+".......init........."+TokenAndTicketManager.getTicket(appid));
		return "initTokenAndTicket";
	}
	/**
	 * http://www.czxkpower.com/dyxxwechat/getWeiXinMessage/lookTokenAndTicket.json
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/lookTokenAndTicket")
    public String lookTokenAndTicket(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model) throws IOException{
		String appid=ToolUtil.getAppConfig("dyxxAPPID");
		
		model.addAttribute(appid+" token", TokenAndTicketManager.getToken(appid)+"   refresh at "+TokenAndTicketManager.getTokenRefreshTime(appid));
		model.addAttribute(appid+" ticket", TokenAndTicketManager.getTicket(appid)+"   refresh at "+TokenAndTicketManager.getTicketRefreshTime(appid));
		
		//System.out.println(TokenAndTicketManager.getToken(appid)+".......look........."+TokenAndTicketManager.getTicket(appid));
		return "lookTokenAndTicket";
	}
	/**
	 * http://www.czxkpower.com/dyxxwechat/getWeiXinMessage/lookTokenAndTicket.json
	 * @param request
	 * @param response
	 * @param session
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getTokenAndTicket")
    public String getTokenAndTicket(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model) throws IOException{
		String appid=ToolUtil.getAppConfig("dyxxAPPID");
		
		model.addAttribute("token",  TokenAndTicketManager.getToken(appid));
		model.addAttribute("ticket",  TokenAndTicketManager.getTicket(appid));
		//System.out.println(TokenAndTicketManager.getToken(appid)+".......look........."+TokenAndTicketManager.getTicket(appid));
		return "lookTokenAndTicket";
	}
    
}
