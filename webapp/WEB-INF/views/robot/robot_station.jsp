<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>第一象限</title>
<%@include file="../commons/head.jsp"%>
<script type="text/javascript">
	window.onload = loadJScript;  //异步加载地图
	//百度地图API功能
	function loadJScript() {
		var script = document.createElement("script");
		script.type = "text/javascript";
		script.src = "http://api.map.baidu.com/api?type=quick&v=1.0&ak=D7bc6a051e5ac6d616f273528ac5616a&callback=init";
		document.body.appendChild(script);
	}
	//Baidu全局变量
	var whole_longitude = "";
	var whole_latitude = "";
	var whole_addr = "";
	
	function init(){
		//微信配置js
		wx.config(${jsConfig});
		wx.ready(function(){
			$("#l-map").hide();
			setloandla();
		});
		wx.error(function(res) {
			modalAlert("网络出现问题，请稍后重试", null);
		});
		weGetLocationSuccess(116.298744,40.04464);//打开注释可以通过访问http://localhost/wechat/main/toStartNotCheck.html 调试页面样式
	}
	function resetloandla(){
		$("#map-loading").show();
		$("#addressContainer").hide();
		setloandla();
	}
	
	function setloandla() {//定位基站
		wx.getLocation({
			success : function(res) {
				weGetLocationSuccess(res.longitude,res.latitude);
			},
			cancel : function(res) {
				alert('若不允许定位，系统将无法定位您周边的基站');
			}
		});
	};
	
	function weGetLocationSuccess(Mlongitude,Mlatitude){//得到经纬度之后初始化地图
		//getAddress(Mlongitude, Mlatitude,"wgs84ll");	//GPS经纬度 wgs84ll
		var gpsPoint = new BMap.Point(Mlongitude,Mlatitude);
		my_translate(gpsPoint,1,bMapCallback);
		$("#l-map").show();
		$("#addressContainer").show();
		$("#map-loading").hide();
	}
	
	
	bMapCallback = function(point){ //坐标转换之后调用回调函数进行加载百度地图
		window.map = new BMap.Map("l-map");
		//添加标注
		window.point = new BMap.Point(point.lng, point.lat);
		//addStationPointsOnMap(point.lng, point.lat);
		map.centerAndZoom(point, 17);
		window.myIcon = new BMap.Icon("${ctx}/assets/i/5050.png", new BMap.Size(50,100));
		
		var point3 = map.pointToOverlayPixel(window.point);
		var px=point3.x-8+"px";
		var py=point3.y-7+"px";
		$("#l-map").append('<span class="BMap_Marker BMap_noprint" unselectable="on" "="" style="position: absolute; padding: 0px; margin: 0px; border: 0px; cursor: pointer; background-image: url(http://left.wicp.net/wechat/assets/i/center-b-13-13.png); width: 13px; height: 13px; left: '+px+'; top: '+py+'; z-index: 8011856; background-position: initial initial; background-repeat: initial initial;"></span>');

		var marker = new BMap.Marker(point);
		map.addOverlay(marker);
		
		//给地图添加拖动的监听事件，
		addStationPointsOnMap(point.lng, point.lat);
		map.addEventListener("moveend", mapMoveendFunction);
		map.addEventListener("zoomend", function(){
			 //alert("地图缩放至：" + this.getZoom() + "级"); 
			 map.setCenter(window.point);
		});
		
		$("#anchorBL").hide();
		whole_longitude = point.lng;
		whole_latitude = point.lat;
	};
	
	function mapMoveendFunction(){
		var cp = map.getCenter();
		var centerPoint=new BMap.Point(cp.lng, cp.lat);
		
		addStationPointsOnMap(cp.lng, cp.lat);
		var twoDistance=MyGetDistance(window.point.lat,window.point.lng,centerPoint.lat,centerPoint.lng);
		if(twoDistance > 1000){
			modalAlert('离定位距离:'+twoDistance+' 米,超过1000米,请重新选择!');
			map.centerAndZoom(window.point, 17);
			centerPoint=window.point;
		}
		
		whole_longitude = centerPoint.lng;
		whole_latitude = centerPoint.lat;
		//getAddress(centerPoint.lng, centerPoint.lat,"bd09ll");				//百度地址调用bd09ll
	}
	
	
	var lastMarker={};
    function addStationPointsOnMap(lo,la){//通过经纬度得到基站列表，在地图上画点
    	$.post("${ctx}/station/getStationList.json",{
    		lo:lo,
    		la:la
    	},function(json){
    		var stationList=json.stationList;
    		map.clearOverlays();
    		for(var i=0;i<stationList.length;i++){
    			var station=stationList[i];
    			var stationInfos=station.stationName+","+station.stationType+","+Math.round(station.distance)+"m";//+station.stationAddress+","
    			var marker = new BMap.Marker(new BMap.Point(station.longitude, station.latitude));
    			
    			if(i==0){
    				lastMarker=marker;
    				marker.setIcon(window.myIcon);
    				
    				$("#stationInfo").html(stationInfos+'<i class="am-icon-angle-right am-icon-sm am-fr" style="color:#63B8FF;"></i>');
        			buttonEnabled();//按钮可点
        			$("#stationInfotr").on("click",function(){
        				updateStation(station.stationId);
        			});
        		}
    			
    			marker.stationInfo=stationInfos;
    			marker.stationId=station.stationId;
        		map.addOverlay(marker);
        		marker.addEventListener("click", function(event){
        			lastMarker.setIcon(this.getIcon());
        			this.setIcon(window.myIcon);
        			lastMarker=this;
        			$("#stationInfo").html(this.stationInfo+'<i class="am-icon-angle-right am-icon-sm am-fr" style="color:#63B8FF;"></i>');
        			buttonEnabled();//按钮可点
        			var stationId =this.stationId;
        			$("#stationInfotr").on("click",function(){
        				updateStation(stationId);
        			});
        		});
    		};
    		if(stationList.length==0){
    			$("#stationInfo").html('附近无基站<i class="am-icon-angle-right am-icon-sm am-fr" style="color:#63B8FF;"></i>');
    		};
    	},"json");
    }
	function updateStation(stationId){
		showLoading();
		window.location.href = "${ctx}/station/toUpdateStation?stationId="+stationId;
	}
/* 	function getAddress(longitude, latitude,coordtype) {//访问服务器，通过经纬度获取地址信息
		$.post("${ctx}/common/getAddress.json", {
			longitude : longitude,
			latitude : latitude,
			coordtype :coordtype
		}, function(json) {
			if (json) {
				var jsonObj = jQuery.parseJSON(json.result);
				var addressArray = jsonObj.pois;
				var addressComponent = jsonObj.addressComponent;
				whole_addr = getLocationDetailAddress(addressComponent);
				if (addressArray.length > 0) {
					//whole_longitude = addressArray[0].point.x;
					//whole_latitude = addressArray[0].point.y;
				} else {
					var address = jsonObj.formatted_address;
					if (!address) {
						address = "未知地点";
					}
					$("#sel1").html(address);
					//whole_longitude = jsonObj.location.lng;
					//whole_latitude = jsonObj.location.lat;
				}
				addOptions(jsonObj);
				buttonEnabled();//按钮可点
				closeLoading();
			};
		}, "json");
	} */
	
	
	function addOptions(jsonObj) {//添加下拉列表中的内容
		var addressArray = jsonObj.pois;
		var addressComponent = jsonObj.addressComponent;
		var nearDetailAddress = getNearDetailAddress(addressComponent);
		$("#addressList").html("");
		if (addressArray != '' && addressArray.length > 0) {
			var sel1html = addressArray[0].name + '';
			$("#sel1").html(sel1html);
			for (var i = 0; i < addressArray.length; i++) {
				var address = addressArray[i];
				var addressListhtml ="<li style='height:40px;padding:5px;' class='am-text-truncate' onclick='setAdressVal(&quot;"+ address.name + "&quot;,&quot;" + address.point.x + "&quot;,&quot;" + address.point.y + "&quot;,&quot;" + nearDetailAddress+"，"+address.addr + "&quot;)'>"
										+"<font size='3'>" + address.name + "</font>" + ' ' 
										+"<font color='#8B8970' size='2'>" + address.addr + "</font>"
									+"</li>";
				$("#addressList").append(addressListhtml);
			};
		} else {
			var addressListhtml = "<li style='height:40px;padding:5px;' class='am-text-truncate' onclick='setshowAdressListView(\"hide\")'><font color='#8B8970' size='3'>没有搜索到周边地点</font></li>";
			$("#addressList").append(addressListhtml);
		}
		var packUp = "<li  style='height:30px;padding:5px;border-bottom: none' onclick='setshowAdressListView(\"hide\")'><font size='3' color='orange'>收起</font></li>";
		$("#addressList").append(packUp);
	};
	
	//
	function setshowAdressListView(e) {
		var obj = document.getElementById('sel2');
		if (obj) {
			if (e == 'view') {
				obj.style.display = '';
			} else if (e == 'hide') {
				obj.style.display = 'none';
			};
		};
	};
	
	
	function setAdressVal(name, x, y, addr) {//地址下拉列表中，点击每条地址信息执行的方法
		whole_longitude = x;
		whole_latitude = y;
		whole_addr = addr;
		$("#sel1").html(name);
		setshowAdressListView('hide');
		var centerPoint=new BMap.Point(x, y);
		
		var twoDistance=MyGetDistance(window.point.lat,window.point.lng,centerPoint.lat,centerPoint.lng);
		if(twoDistance > 1000){
			modalAlert('离定位距离:'+twoDistance+' 米,超过1000米,请重新选择!');
			centerPoint=window.point;
		}
		
		map.removeEventListener("moveend", mapMoveendFunction);
		map.centerAndZoom(centerPoint, 17);
		map.addEventListener("moveend", mapMoveendFunction);
	};
	
	function getNearDetailAddress(addressComponent) {//处理附近地址信息
		var address = "";
		var province = addressComponent.province;
		var city = addressComponent.city;
		var district = addressComponent.district;//区（海淀区）
		address = province + '，' + city + '，' + district;
		return address;
	};
	
	function buttonEnabled() {//定位完成时，让所有的按钮可用
		var startBtn = document.getElementById('startBtn');
		startBtn.disabled = "";
		var startBtn1 = document.getElementById('startBtn1');
		startBtn1.disabled = "";
		var startBtn2 = document.getElementById('startBtn2');
		startBtn2.disabled = "";
		var startBtn3 = document.getElementById('startBtn3');
		startBtn3.disabled = "";
		var startBtn4 = document.getElementById('startBtn4');
		startBtn4.disabled = "";
		var startBtn5 = document.getElementById('startBtn5');
		startBtn5.disabled = "";
	};
	

	
////// 以上是定位和地图，一下是页面跳转 /////////////////////////////////////////////////////////
    
    
	function entryJob(jobId) {
		showLoading();
		var geoc = new BMap.Geocoder();    
		var pt = window.map.getCenter();
		geoc.getLocation(pt, function(rs){
			var addComp = rs.addressComponents;
			whole_addr = encodeURI(encodeURI(addComp.province + "," + addComp.city + "," + addComp.district + "," + addComp.street + "," + addComp.streetNumber));
			window.location.href = "${ctx}/station/getNearbyList?lo="+whole_longitude+"&la="+whole_latitude+'&addr='+whole_addr+"&jobId="+jobId;
		}); 
	};
	function entryResources(){
		/*跳转到待传资源页面*/
		showLoading();
		window.location.href = '${ctx}/acceptPhoto/toUploadResources';
	}
	
	function togosel2(){
		$('#sel2').toggle();
	}
	
</script>
</head>
<body>
<div class="am-page app-from-page" id="mainPage">
	<table class="app-tb" style="width: 100%;height: 100%">
		<tr>
			<td>
				<div id="map-loading" style="height:100%;width:100%;background-color: #F7F7F7;color: #777777">
					<table style="width: 100%;">
						<tr>
							<td style="height: 120px;vertical-align: bottom;text-align: center;">
								<i class="am-icon-spinner am-icon-spin am-icon-md"></i>
								正在请求中...
							</td>
						</tr>
						<tr>
							<td style="height: 120px;vertical-align: top;text-align: center;">
								如果长时间未定位成功，请重新打开
							</td>
						</tr>
						<tr>
							<td style="height: 1000px">
								
							</td>
						</tr>
					</table>
				</div>
				<div id="l-map" style="height:100%;width:100%;"></div>
			</td>
		</tr>
		<tr>
			<td style="height: 20px;font-size: 13px;background: #FAFCFF">
				<table  class="am-table" style="margin:0px;">
					<tr id="stationInfotr">
						<td id="stationInfo" align="center"  colspan="2">&nbsp;</td>
					</tr>
					<tr>
						<td style="border-right: 1px solid #ddd" id="navigation" align="center" >到这里去</td>
						<td style="border-left: 1px solid #ddd" id="correctLocation" align="center" >位置纠正</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td style="height: 20px;font-size: 13px;background: #FAFCFF;">
				<table class="am-table" style="margin:0px;">
					<tr>
						<td align="center" style="border-style:none;padding:0px;">
							<button id="startBtn" onclick="entryJob('job_1');" type="button" class="am-btn am-btn-success" disabled="disabled" style="border-radius:6px;">
								<div class="app-btn3"></div>
							</button>
							<br/>
							日常巡检
						</td>
						<td align="center" style="border-style:none;padding:0px;">
							<button id="startBtn1" onclick="entryJob('job_2')" type="button" class="am-btn am-btn-warning" disabled="disabled" style="border-radius:6px;">
								<div class="app-btn5"></div>
							</button>
							<br/>
							新站交维
						</td>
						<td align="center" style="border-style:none;padding:0px;">
							<button id="startBtn2" onclick="entryJob('job_3')" type="button" class="am-btn am-btn-danger" disabled="disabled" style="border-radius:6px;">
								<div class="app-btn1"></div>
							</button>
							<br/>
							参数普调
						</td>
						<td align="center" style="border-style:none;padding:0px;">
							<button id="startBtn3" onclick="entryJob('job_4');" type="button" class="am-btn am-btn-primary" disabled="disabled" style="border-radius:6px;">
								<div class="app-btn6"></div>
							</button>
							<br/>
							油机发电
						</td>
					</tr>
					<tr>
						<td align="center" style="border-style:none; padding:0px;">
							<button id="startBtn4" onclick="entryJob('job_5');" type="button" class="am-btn am-btn-secondary" disabled="disabled" style="border-radius:6px;">
								<div class="app-btn2"></div>
							</button>
							<br/>
							放电测试
						</td>
						<td align="center" style="border-style:none;padding:0px;">
							<div>
								<button id="startBtn5" onclick="entryResources()" type="button" class="am-btn" disabled="disabled" style=background-color:#439B78;border-radius:6px;position: relative;">
									<div class="app-btn4"></div>
								</button>
								<span id="resourcesTips" class="am-badge am-badge-danger am-round" style="position: absolute;float: right;margin: -7px 0 0 -16px;font-size: 14px;"></span>
								<br/>
								待传资源
							</div>
						</td>
						<td align="center" style="border-style:none;padding:0px;">
							&nbsp;
						</td>
						<td align="center" style="border-style:none;padding:0px;">
							&nbsp;
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<div class="am-modal am-modal-alert" tabindex="-1" id="modal-alert">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">提示</div>
    <div class="am-modal-bd" id="modal-alert-msg">
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
    </div>
  </div>
</div>

<div class="am-modal am-modal-loading" tabindex="-1" id="modal-loading">
  <button type="button" class="am-btn am-radius am-modal-dialog" style="width:200px;background-color: black;filter:alpha(opacity:60);opacity:0.6;">
    <div class="am-modal-hd" style="font-size: 14px;color: white;" id="modal-loading-msg">正在载入...</div>
    <div class="am-modal-bd">
      <span class="am-icon-spinner am-icon-spin"></span>
    </div>
  </button>
</div>
</body>
<script type="text/javascript">
var height = (window.innerHeight > 0) ? window.innerHeight: screen.height;
$(document.body).height(height);
//获取待传资源数量
 $.getJSON("${ctx}/acceptPhoto/getCount.json", function(data){
	if(typeof(data.result) == "number"){
		if(data.result == 0){
			$("#resourcesTips").hide();
		}else{
			$("#resourcesTips").html(data.result);
			$("#resourcesTips").show();
		} 
	}
}); 
</script>
</html>