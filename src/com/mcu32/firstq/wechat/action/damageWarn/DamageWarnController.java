package com.mcu32.firstq.wechat.action.damageWarn;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mcu32.firstq.common.bean.bo.WeatherAlertsRealTimeBO;
import com.mcu32.firstq.common.bean.bo.WeatherAlertsTimerTaskBO;
import com.mcu32.firstq.common.util.FirstQInterfaces;
import com.mcu32.firstq.wechat.action.BaseController;

@Controller
@RequestMapping(value = "/damageWarning")
public class DamageWarnController extends BaseController {
	
	@RequestMapping(value = "/damageWarningRealTime",method = RequestMethod.GET)
	public String damageWarningRealTime(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		String uniqueId=request.getParameter("uniqueId");
		Map<String,String> map=new HashMap<String,String>();
		map.put("uniqueId", uniqueId);
		map.put("orderByClause", " PUBLISH_TIME desc ");
		
		map.put("filterAlertType", "'高温','大雾'");
		
		map.put("alertLevel", "红色");
		List<WeatherAlertsRealTimeBO> redWarnlist=FirstQInterfaces.getIWeatherAlertsService().selectByBaseConditionsRealTime(map);
		map.put("alertLevel", "橙色");
		List<WeatherAlertsRealTimeBO> orangeWarnlist=FirstQInterfaces.getIWeatherAlertsService().selectByBaseConditionsRealTime(map);
		map.put("alertLevel", "黄色");
		List<WeatherAlertsRealTimeBO> yellowWarnlist=FirstQInterfaces.getIWeatherAlertsService().selectByBaseConditionsRealTime(map);
		map.put("alertLevel", "蓝色");
		List<WeatherAlertsRealTimeBO> blueWarnlist=FirstQInterfaces.getIWeatherAlertsService().selectByBaseConditionsRealTime(map);
		
		model.addAttribute("redWarnlist", redWarnlist);
		model.addAttribute("redWarnNum", redWarnlist.size());
		model.addAttribute("orangeWarnlist", orangeWarnlist);
		model.addAttribute("orangeWarnNum", orangeWarnlist.size());
		model.addAttribute("yellowWarnlist", yellowWarnlist);
		model.addAttribute("yellowWarnNum", yellowWarnlist.size());
		model.addAttribute("blueWarnlist", blueWarnlist);
		model.addAttribute("blueWarnNum", blueWarnlist.size());
		
		return "damageWarning/damageWarning";
	}
	
	@RequestMapping(value = "/damageWarningTimerTask",method = RequestMethod.GET)
	public String damageWarningTimerTask(HttpServletRequest request,HttpServletResponse response, HttpSession session, ModelMap model) {
		
		String uniqueId="";
		String areaIdlike="";
		
		String para=request.getParameter("para");
		String[] paraArr=para.split("_");
		if(null!=paraArr&&paraArr.length>0){
			uniqueId=paraArr[0];
		}
		
		if(null!=paraArr&&paraArr.length>1){
			areaIdlike=paraArr[1];
		}
		
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("uniqueId", uniqueId);
		map.put("areaIdlike", areaIdlike);
		map.put("orderByClause", " PUBLISH_TIME desc ");
		
		map.put("filterAlertType", "'高温','大雾'");
		
		map.put("alertLevel", "红色");
		List<WeatherAlertsTimerTaskBO> redWarnlist=FirstQInterfaces.getIWeatherAlertsService().selectByBaseConditionsTimerTask(map);
		
		map.put("alertLevel", "橙色");
		List<WeatherAlertsTimerTaskBO> orangeWarnlist=FirstQInterfaces.getIWeatherAlertsService().selectByBaseConditionsTimerTask(map);
		map.put("alertLevel", "黄色");
		List<WeatherAlertsTimerTaskBO> yellowWarnlist=FirstQInterfaces.getIWeatherAlertsService().selectByBaseConditionsTimerTask(map);
		map.put("alertLevel", "蓝色");
		List<WeatherAlertsTimerTaskBO> blueWarnlist=FirstQInterfaces.getIWeatherAlertsService().selectByBaseConditionsTimerTask(map);
		
		model.addAttribute("redWarnlist", redWarnlist);
		model.addAttribute("redWarnNum", redWarnlist.size());
		model.addAttribute("orangeWarnlist", orangeWarnlist);
		model.addAttribute("orangeWarnNum", orangeWarnlist.size());
		
		model.addAttribute("yellowWarnlist", yellowWarnlist);
		model.addAttribute("yellowWarnNum", yellowWarnlist.size());
		model.addAttribute("blueWarnlist", blueWarnlist);
		model.addAttribute("blueWarnNum", blueWarnlist.size());
		return "damageWarning/damageWarning";
	}
}