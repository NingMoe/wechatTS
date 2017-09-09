<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<table class="am-table app-tb" style="">
	<input type="hidden" value="${acceptStation.proxyPerson}" id="proPerson"/>
	<input type="hidden" value="${acceptStation.proxyPhone}" id="proPhone"/>
	<tr>
		<th style="vertical-align: middle;" colspan="2">
			<div style="padding-top: 5px;float: left;">
			基础信息<c:if test="${deviceInspectRecords != '[]'}"><font size="2" color="red">(存在异常)</font></c:if>
			</div>
			<div class="am-dropdown am-fr" data-am-dropdown="{justify: '#doc-dropdown-justify'}">
				<button class="am-btn am-radius am-dropdown-toggle" style="background: #EF7321;color: #fff;height: 30px;width: 80px;padding: 0px;">
			  		<i class="am-icon-location-arrow"></i>
			  		<font size="2">导&nbsp;&nbsp;航</font>
			  	</button>
			  	<ul class="am-dropdown-content">
			     	<li><a onclick="gobaidu();"><i class="am-icon-paw" style="color:#63B8FF;"></i>&nbsp;&nbsp;百度地图</a></li>
			    	<li><a onclick="gogaode();"><i class="am-icon-send-o" style="color:#63B8FF;"></i>&nbsp;&nbsp;高德地图</a></li>
			    	<li><a onclick="gotengxu();"><i class="am-icon-qq" style="color:#63B8FF;"></i>&nbsp;&nbsp;腾讯地图</a></li>
			    </ul>
		  	</div>
		</th>		
	</tr>
	<tr>
		<td style="vertical-align: middle;">
			基站完整度：
		</td>
		<td style="vertical-align: middle;">
			<font size="3" style="font-weight:bold;font-style:italic;color: <c:if test="${acceptStation.stationCompleteness < 50}">#FF0000</c:if>
			<c:if test="${acceptStation.stationCompleteness >= 50 && acceptStation.stationCompleteness < 70}">#F7E708</c:if>
			<c:if test="${acceptStation.stationCompleteness >= 70}">#00CC00</c:if>;">
				${acceptStation.stationCompleteness}<c:if test="${acceptStation.stationCompleteness != null && acceptStation.stationCompleteness != '[]'}">%</c:if>
			</font>
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle;width:100px;" rowspan="3">
			<c:if test="${acceptStation.thumbLocation == null}">
				<img src="${ctx}/assets/i/noPhoto.png" alt="" style="height: 80px;width: 85px;"/>
			</c:if>
			<c:if test="${acceptStation.thumbLocation != null}">
				<img src="${acceptStation.thumbLocation}" alt="" style="height: 80px;;width: 85px;"/>
			</c:if>
		</td>
		<td style="vertical-align: middle;">
			基站名称：&nbsp;&nbsp;&nbsp;&nbsp;${acceptStation.stationName}
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle;">
			动环名称：&nbsp;&nbsp;&nbsp;&nbsp;${acceptStation.stationDH}
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle;">
			基站编号：&nbsp;&nbsp;&nbsp;&nbsp;${acceptStation.stationNo}
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle;">
			基站类型:
		</td>
		<td style="vertical-align: middle;">
			${acceptStation.stationType}
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle;">
			基站负载：
		</td>
		<td style="vertical-align: middle;">
			${acceptStation.loadCurrent}<c:if test="${acceptStation.loadCurrent != null && acceptStation.loadCurrent != '[]'}">A</c:if>
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle;">
			后备时长：
		</td>
		<td style="vertical-align: middle;">
			${acceptStation.longBack}<c:if test="${acceptStation.longBack != null && acceptStation.longBack != '[]'}">H</c:if>
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle;">
			区域经理：
		</td>
		<td style="vertical-align: middle;">
			${acceptStation.regionalManager}
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle;">
			联系方式：
		</td>
		<td style="vertical-align: middle;">
			${acceptStation.managerPhone}
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle;">
			代维公司：
		</td>
		<td style="vertical-align: middle;">
			${acceptStation.proxyCompany}
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle;">
			代维人员：
		</td>
		<td style="vertical-align: middle;">
			<div id="proxyPerson"></div>	
		</td>
	</tr>
	<tr>
		<td class="am-text-nowrap" style="vertical-align: middle;">
			代维联系方式：
		</td>
		<td style="vertical-align: middle;">
			<div id="proxyPhone"></div>
			<!-- <div style="width:200px;white-space:nowrap;overflow:hidden;text-overflow: ellipsis;">${acceptStation.proxyPhone}</div> -->
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle;">
			运营商：
		</td>
		<td style="vertical-align: middle;">
			${acceptStation.mobileOperator}
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle;">
			所在县/区：
		</td>
		<td style="vertical-align: middle;">
			<c:if test="${acceptStation.provinceId != acceptStation.cityId && acceptStation.provinceId != null && acceptStation.provinceId != '[]'}">${acceptStation.provinceId}/</c:if>
			<c:if test="${acceptStation.cityId != null && acceptStation.cityId != '[]'}">${acceptStation.cityId}/</c:if>
			${acceptStation.countyId}
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle;">
			基站经纬度：
		</td>
		<td style="vertical-align: middle;">
			<span id="longitude">${acceptStation.longitude}</span><c:if test="${acceptStation.longitude != null && acceptStation.longitude != '[]'}">,</c:if>
			<span id="latitude">${acceptStation.latitude}</span>
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle;">
			地址：
		</td>
		<td style="vertical-align: middle;">
			${acceptStation.stationAddress}
		</td>
	</tr>
</table>
<table class="am-table app-tb">
	<tr>
		<th colspan="2">
			铁塔		
		</th>
	</tr>
	<tr>
		<td style="vertical-align: middle;width:100px">
			区域性质：
		</td>
		<td style="vertical-align: middle">
			${acceptTower.regionProperty}
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle">
			地貌：
		</td>
		<td style="vertical-align: middle">
			${acceptTower.landScape}
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle">
			铁塔类型：
		</td>
		<td style="vertical-align: middle">
			${acceptTower.towerType}
		</td>
	</tr>
	<tr>
		<c:if test="${towerPhotoInfos != null && towerPhotoInfos != '[]'}">
			<td colspan="2">
				铁塔外观照：
			</td>
		</c:if>
		<c:if test="${towerPhotoInfos == null || towerPhotoInfos == '[]'}">
			<td style="vertical-align: middle">
				铁塔外观照：
			</td>
			<td style="vertical-align: middle">
				无
			</td>
		</c:if>
	</tr>
	<c:if test="${towerPhotoInfos != null && towerPhotoInfos != '[]'}">
		<tr>
			<td colspan="2" style="border-top: none;">
				<ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
					<c:forEach items="${towerPhotoInfos}" var="item" varStatus="status">
					  <li style="padding: 0 0 0 0" id="${item.photoId}">
					    <div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
					    	<div style="height:72px;margin-bottom:2px;overflow:hidden;">
						       <img src="${item.thumbLocation}" <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if>  alt="" data-rel="${item.fileLocation}"/>
						     </div>
					    </div>
					  </li>
				  	</c:forEach>
				</ul>
				<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" size="100"/>
			</td>
		</tr>
	</c:if>
</table>
<table class="am-table app-tb">
    <tr>
		<th colspan="2">
			机房		
		</th>
	</tr>
	<tr>
		<td style="vertical-align: middle;width:100px">
			机房类型：
		</td>
		<td style="vertical-align: middle">
			${acceptRoom.roomType}
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle">
			建筑方式：
		</td>
		<td style="vertical-align: middle">
			${acceptRoom.buildingPattern}
		</td>
	</tr>
	<tr>
		<c:if test="${roomPhotoInfos != null && roomPhotoInfos != '[]'}">
			<td colspan="2">
				机房外观照：
			</td>
		</c:if>
		<c:if test="${roomPhotoInfos == null || roomPhotoInfos == '[]'}">
			<td style="vertical-align: middle">
				机房外观照：
			</td>
			<td style="vertical-align: middle">
				无
			</td>
		</c:if>
	</tr>
	<c:if test="${roomPhotoInfos != null && roomPhotoInfos != '[]'}">
		<tr>
			<td colspan="2" style="border-top: none;">
				<ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
					<c:forEach items="${roomPhotoInfos}" var="item" varStatus="status">
					  <li style="padding: 0 0 0 0" id="${item.photoId}">
					    <div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
					    	<div style="height:72px;margin-bottom:2px;overflow:hidden;">
						       <img src="${item.thumbLocation}" <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if>  alt="" data-rel="${item.fileLocation}"/>
						     </div>
					    </div>
					  </li>
				  	</c:forEach>
				</ul>
				<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" size="100"/>
			</td>
		</tr>
	</c:if>
	<tr>
		<td colspan="2">
			<div style='position:relative; width:100%; height:240px;'>
			    <div id="l-map" style='position:absolute; z-index:2; width:100%; height:240px; left:0px; top:0px; '></div>
			    <div id="stateMap" style='position:absolute; z-index:3; width:100%; height:240px; left:0px; top:0px;'></div>
			</div>
		</td>
	</tr>
</table>
<script type="text/javascript">	
	//Baidu全局变量
	var whole_longitude = ${acceptStation.longitude};
	var whole_latitude = ${acceptStation.latitude};
	var user_lo="";
	var user_la="";
	var script = document.createElement("script");
	script.type = "text/javascript";
	script.src = "http://api.map.baidu.com/api?type=quick&v=1.0&ak=D7bc6a051e5ac6d616f273528ac5616a&callback=init";
	document.body.appendChild(script);
	function init(){
		wx.config(${jsConfig});
		wx.ready(function() {
			getUserLocation();
		});
		wx.error(function(res) {
			modalAlert("网络出现问题，请稍后重试", null);
		});
		
	}
	
	function getUserLocation(){
		wx.getLocation({
			success : function(res) {
				weGetLocationSuccess(res.longitude,res.latitude);
			},
			cancel : function(res) {
				alert('若不允许定位，系统将无法定位您周边的基站');
			}
		});
	}
	
	function weGetLocationSuccess(Mlongitude,Mlatitude){
		var gpsPoint = new BMap.Point(Mlongitude,Mlatitude);
		my_translate(gpsPoint,1,bMapCallback);
	}
	
	bMapCallback = function(point){ //坐标转换之后调用回调函数进行加载百度地图
		user_lo=point.lng;
		user_la=point.lat;
		window.map = new BMap.Map("l-map",{vectorMapLevel:99});
		window.whole_point = new BMap.Point(whole_longitude, whole_latitude);
		map.centerAndZoom(whole_point, 13);
		window.myIcon = new BMap.Icon("${ctx}/assets/i/tower-notouch-.png", new BMap.Size(48,58));
		window.marker = new BMap.Marker(whole_point,{icon:window.myIcon});	// 创建标注
		map.addOverlay(window.marker); 
	}
	
	echo.init({
		offset : 100,
		throttle : 250,
		unload : false
	});
		
	!function(t) {
    	$("ul[data-am-widget='gallery']").one("click",function(){//点击预览图片关闭预览
			$(".am-pureview-slider").each(function(i){
				$(this).on("click",function(){
					$(".am-icon-chevron-left[data-am-close='pureview']").eq(i).triggerHandler("click");
				});
			});
		});
    	
    	var longitude = '${acceptStation.longitude}';
		var latitude = '${acceptStation.latitude}';
		if(longitude != null && longitude != '' && longitude != 'null') {
			$("#longitude").html(Number(longitude).toFixed(6));
		}
		
		if(latitude != null && latitude != '' && latitude != 'null') {
			$("#latitude").html(Number(latitude).toFixed(6));
		}
		//showImagesH5();
	}(window.jQuery || window.Zepto);
	
	function gobaidu(){
		var stationName = '${acceptStation.stationName}';
		var address = '${address}';
		var hrefStr= "http://api.map.baidu.com/direction?origin=latlng:"+user_la+","+user_lo+"|name:我的位置&destination=latlng:"+whole_latitude+","+whole_longitude+"|name:"+stationName+"&mode=driving&region="+address.city+"&output=html&src=第一象限";
		window.location.href=hrefStr;
	}
	
	function gogaode(){
		var stationName = '${acceptStation.stationName}';
		var hrefStr = "http://m.amap.com/?from="+toglat(user_la)+","+toglng(user_lo)+"(我的位置)&to="+toglat(whole_latitude)+","+toglng(whole_longitude)+"("+stationName+")&type=0&opt=0";
		window.location.href=hrefStr;
	}
	
	function gotengxu(){
		var stationName = '${acceptStation.stationName}';
		var hrefStr= "http://apis.map.qq.com/uri/v1/routeplan?type=drive&from=我的位置&fromcoord="+toglat(user_la)+","+toglng(user_lo)+"&to="+stationName+"&tocoord="+toglat(whole_latitude)+","+toglng(whole_longitude)+"&policy=0&referer=tengxun";
		window.location.href=hrefStr;
	}
	function getStr(){
		var proxyPerson=$("#proPerson").val();
		var proxyPhone=$("#proPhone").val();
		var str="";
		var phone="";
		if(proxyPerson !=null && proxyPerson !=""){
			str= proxyPerson.substring(0,10);
			if(proxyPerson.length>10){
				str=str+"...";
			}
		}
		if(proxyPhone !=null && proxyPhone !=""){
			phone= proxyPhone.substring(0,25);
			if(proxyPhone.length>25){
				phone=phone+"...";
			}
		}
		 $("#proxyPerson").html(str);
		 $("#proxyPhone").html(phone);
	}
	getStr();
	function toblng(lng){return lng+0.0065;};//经度  高德或腾讯  to 百度
	function toblat(lat){return lat+0.0060;};//纬度  高德或腾讯  to 百度
	function toglng(lng){return lng-0.0065;};//经度  百度 to 高德或腾讯
	function toglat(lat){return lat-0.0060;};//纬度  百度 to 高德或腾讯
	
</script>