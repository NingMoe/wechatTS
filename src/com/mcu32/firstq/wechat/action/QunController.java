package com.mcu32.firstq.wechat.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mcu32.firstq.common.bean.bo.RobotInfoBO;
import com.mcu32.firstq.common.bean.bo.StationBO;
import com.mcu32.firstq.common.bean.bo.WeathInfoBO;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.bean.Weather;
import com.mcu32.firstq.wechat.util.HttpClientUtil;
import com.mcu32.firstq.wechat.util.ToolUtil;
@Controller
@RequestMapping(value = "/getQunMessage")
public class QunController extends BaseController{
	

    
    /**
     * 自定义的TOKEN WeiXinTest
	 * 获得信息
	 * /getWeiXinMessage/getMessages
	 */
    @RequestMapping(value = "/getMessages")
	public String getMessages(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		
    	try{
			 response.setCharacterEncoding("UTF-8");
			 request.setCharacterEncoding("UTF-8");
			 System.out.println("小桔子机器人后台转发请求开始=====");
			 
			 String  content =new String(request.getParameter("value0").getBytes("ISO-8859-1"),"UTF-8");
			 String name = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
			 String nick = new String(request.getParameter("nick").getBytes("ISO-8859-1"),"UTF-8");
			 
			 //Map <String, String[]>parm=getRealMapByReqeustMap(request);
			 
            
//            //保存群聊
             SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMDDHH24mmss");
             String d=sdf.format(new Date());
			 RobotInfoBO robotInfoBO=new RobotInfoBO();
			 robotInfoBO.setId(d);
			 robotInfoBO.setClientid(nick);
			 robotInfoBO.setContent("基站："+content);
			 robotInfoBO.setGroupName(name);
			 robotInfoBO.setIsAnswer("0");
			 robotInfoBO.setAddTime(new Date());
			 FirstQInterfaces.getIRobotInfoService().saveRobotInfo(robotInfoBO);
			 String result="亲，想要查询基站信息，请输入关键字基站+空格+基站名称 进行查询";
			
			 //匹配关键字 优先匹配级别 1、基站 2、天气 3、停电
          
				 
				 //int i=content.indexOf(" ");
				 //if(i!=-1){
				// String contentStr=(content.replaceAll(" ", "");
				// System.out.println("-----------"+contentStr);
				 //根据基站名称查询配置基站
				 HashMap<String, String> queryCondition = new HashMap<String, String>();
                 
				 queryCondition.put("provinceId", "");
				 queryCondition.put("cityId", "");
				 queryCondition.put("countyId", "");
				 queryCondition.put("stationName", content.trim());
				 queryCondition.put("stationNo", "");
				 queryCondition.put("stationAddress", "");
				 queryCondition.put("checkStatus", "");
					
				 List<StationBO> stationList=FirstQInterfaces.getIStationService().getStationsList(queryCondition, 1, 1);
					
				 //判断是否可以查询到
				 if(stationList!=null &&stationList.size()!=0){
					 for(int j=0;j<stationList.size();j++){
					   StationBO s=stationList.get(j);
					   result=s.getCityId()+s.getStationName()+"http://"+ToolUtil.getAppConfig("CallBackDomain")+getContextPath()+"/station/getStationDetail?openId=" + ""+"&stationId="+s.getStationId();
					   }
					  
				 }else{
					   result="亲，想要查询基站信息，请输入关键字基站+空格+基站名称 进行查询";
					
				 }
			

            
             
		     //响应消息 
	          String token="\"a8f08a7453f04c42a4ddbff440520c82\"";
	             
	          String json= "{\"token\":"+token+",\"message\":\""+result+"\"}";
	            
	          response.getWriter().write(json);
	          response.getWriter().flush();  
        }catch(Exception e){
        	e.printStackTrace();
        }
        return null;
    }

    /**
     * 自定义的TOKEN WeiXinTest
	 * 获得信息
	 * /getWeiXinMessage/getMessages
	 */
    @RequestMapping(value = "/getTQMessage")
	public String getTQMessage(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		
    	try{
			 response.setCharacterEncoding("UTF-8");
			 request.setCharacterEncoding("UTF-8");
			 System.out.println("小桔子机器人后台转发请求开始=====");
			 
			 String content =new String(request.getParameter("value0").getBytes("ISO-8859-1"),"UTF-8");
			 String name = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
			 String nick = new String(request.getParameter("nick").getBytes("ISO-8859-1"),"UTF-8");
			  
            
//            //保存群聊
             SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMDDHH24mmss");
             String d=sdf.format(new Date());
			 RobotInfoBO robotInfoBO=new RobotInfoBO();
			 robotInfoBO.setId(d);
			 robotInfoBO.setClientid(nick);
			 robotInfoBO.setContent("天气："+content.trim());
			 robotInfoBO.setGroupName(name);
			 robotInfoBO.setIsAnswer("0");
			 robotInfoBO.setAddTime(new Date());
			 FirstQInterfaces.getIRobotInfoService().saveRobotInfo(robotInfoBO);
			 String result="亲，点播天气预报，请输入天气+空格+城市（或区县名称），想了解更多天气情况请点击http://i.tianqi.com/index.php?c=code&id=4&num=4";

			 
				 WeathInfoBO w =new WeathInfoBO();
				 w.setCity_name(content.trim());
				 WeathInfoBO wbo=FirstQInterfaces.getIRobotInfoService().getWeathInfo(w);
				 //判断是否可以查询到
				 if(wbo !=null && wbo.getCity_id()!=null){
					 String url="http://api.k780.com:88/?app=weather.future&weaid="+wbo.getCity_id()+"&appkey=15391&sign=4ff7b16f3ee09d38a297e2e04baaa6c6&format=json";
					 JSONObject rjson = HttpClientUtil.getJSONObjResult(url, "");
					 JSON resultListJson=(JSON)rjson.get("result");
					 List<Weather>  weatherList=JSON.parseArray(resultListJson.toJSONString(),Weather.class );
					
				     if(weatherList !=null && weatherList.size()!=0){
				           Weather nowW=weatherList.get(0);
				           result=nowW.getCitynm()+"今天 "+nowW.getDays()+","+nowW.getWeek()+","+nowW.getWeather()+",最高气温："+nowW.getTemp_high()+"℃,最低气温："+nowW.getTemp_low()+"℃,"+nowW.getWinp();
				      }else{
				    	  result="没有查询相关的天气情况，请重新输入天气+空格+城市（或区县名称）进行查询";
				        }	
					   
				 }else{
					    result="亲，点播天气预报，请输入天气+空格+城市（或区县名称），想了解更多天气情况请点击http://i.tianqi.com/index.php?c=code&id=4&num=4";
					   
				 }	 
			   
             
		     //响应消息 
	          String token="\"a8f08a7453f04c42a4ddbff440520c82\"";
	             
	          String json= "{\"token\":"+token+",\"message\":\""+result+"\"}";
	            
	          response.getWriter().write(json);
	          response.getWriter().flush();  
        }catch(Exception e){
        	e.printStackTrace();
        }

        return null;

    }
    
    /**
     * 自定义的TOKEN WeiXinTest
	 * 获得信息
	 * /getWeiXinMessage/getMessages
	 */
    @RequestMapping(value = "/getPowCutMessage")
	public String getPowCutMessage(HttpServletRequest request,HttpServletResponse response, HttpSession session,ModelMap model){
		
    	try{
			 response.setCharacterEncoding("UTF-8");
			 request.setCharacterEncoding("UTF-8");
			 System.out.println("小桔子机器人后台转发请求开始=====");
			 String  content =new String(request.getParameter("value0").getBytes("ISO-8859-1"),"UTF-8");
			
			 
			 String name = new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
			 String nick = new String(request.getParameter("nick").getBytes("ISO-8859-1"),"UTF-8");		  
            
//            //保存群聊
             SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMDDHH24mmss");
             String d=sdf.format(new Date());
			 RobotInfoBO robotInfoBO=new RobotInfoBO();
			 robotInfoBO.setId(d);
			 robotInfoBO.setClientid(nick);
			 robotInfoBO.setContent("停电："+content);
			 robotInfoBO.setGroupName(name);
			 robotInfoBO.setIsAnswer("0");
			 robotInfoBO.setAddTime(new Date());
			 FirstQInterfaces.getIRobotInfoService().saveRobotInfo(robotInfoBO);
			 String result="亲，该功能正在开发中敬请期待";
             
		     //响应消息 
	          String token="\"a8f08a7453f04c42a4ddbff440520c82\"";
	             
	          String json= "{\"token\":"+token+",\"message\":\""+result+"\"}";
	            
	          response.getWriter().write(json);
	          response.getWriter().flush();  
        }catch(Exception e){
        	e.printStackTrace();
        }

        return null;

    }
  
    
}

