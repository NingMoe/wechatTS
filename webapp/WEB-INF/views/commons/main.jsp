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
				//weGetLocationSuccess("114.920930","38.590108");
			},
			cancel : function(res) {
				alert('若不允许定位，系统将无法定位您周边的基站');
			}
		});
	};
	
	function weGetLocationSuccess(Mlongitude,Mlatitude){//得到经纬度之后初始化地图
		getAddress(Mlongitude, Mlatitude,"wgs84ll");	//GPS经纬度 wgs84ll
		var gpsPoint = new BMap.Point(Mlongitude,Mlatitude);
		my_translate(gpsPoint,1,bMapCallback);
		$("#l-map").show();
		$("#addressContainer").show();
		$("#map-loading").hide();
	}
	
	
	function bMapCallback (point){ //坐标转换之后调用回调函数进行加载百度地图
		window.map = new BMap.Map("l-map");
		
		//添加标注
		window.point = new BMap.Point(point.lng, point.lat);
		map.centerAndZoom(point, 15);
		window.myIcon = new BMap.Icon("${ctx}/assets/i/5050.png", new BMap.Size(50,100));
		
		var point3 = map.pointToOverlayPixel(window.point);
		var px=point3.x-27+"px";
		var py=point3.y-50+"px";
		$("#l-map").append('<span class="BMap_Marker BMap_noprint" unselectable="on" "="" style="position: absolute; padding: 0px; margin: 0px; border: 0px; cursor: pointer; background-image: url(${ctx}/assets/i/5050.png); width: 50px; height: 50px; left: '+px+'; top: '+py+'; z-index: 8011856; background-position: initial initial; background-repeat: initial initial;"></span>');
		
		//给地图添加拖动的监听事件，
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
		
		var twoDistance=MyGetDistance(window.point.lat,window.point.lng,centerPoint.lat,centerPoint.lng);
		if(twoDistance > 3000){
			modalAlert('离定位距离:'+twoDistance+' 米,超过3000米,请重新选择!');
			map.centerAndZoom(window.point, 15);
			centerPoint=window.point;
		}
		
		whole_longitude = centerPoint.lng;
		whole_latitude = centerPoint.lat;
		getAddress(centerPoint.lng, centerPoint.lat,"bd09ll");				//百度地址调用bd09ll
	}
	
	function getAddress(longitude, latitude,coordtype) {//访问服务器，通过经纬度获取地址信息
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
	}
	
	
	function addOptions(jsonObj) {//添加下拉列表中的内容
		var addressArray = jsonObj.pois;
		//var addressComponent = jsonObj.addressComponent;
		//var nearDetailAddress = getNearDetailAddress(addressComponent);
		//$("#addressList").html("");
		if (addressArray != '' && addressArray.length > 0) {
			$("#sel1").html(addressArray[0].name);
			/* var address = addressArray[0];
			$("#addressList").append(
				"<li style='height:40px;padding:5px;' class='am-text-truncate' >"
					+"<font size='3'>" + address.name + "</font>" + ' ' 
					+"<font color='#8B8970' size='2'>" + address.addr + "</font>"
				+"</li>"
			); */
			/* for (var i = 0; i < addressArray.length; i++) {
				
			}; */
		}/* else {
			var addressListhtml = "<li style='height:40px;padding:5px;' class='am-text-truncate' ><font color='#8B8970' size='3'>没有搜索到周边地点</font></li>";
			$("#addressList").append(addressListhtml);
		} */
		/* var packUp = "<li  style='height:30px;padding:5px;border-bottom: none' onclick='setshowAdressListView(\"hide\")'><font size='3' color='orange'>收起</font></li>";
		$("#addressList").append(packUp); */
	};
	
	//
	/* function setshowAdressListView(e) {
		var obj = document.getElementById('sel2');
		if (obj) {
			if (e == 'view') {
				obj.style.display = '';
			} else if (e == 'hide') {
				obj.style.display = 'none';
			};
		};
	}; */
	
	
	/* function setAdressVal(name, x, y, addr) {//地址下拉列表中，点击每条地址信息执行的方法
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
		map.centerAndZoom(centerPoint, 15);
		map.addEventListener("moveend", mapMoveendFunction);
	}; */
	
	function getLocationDetailAddress(addressComponent) {//处理地址信息
		var address = "";
		var province = addressComponent.province;
		var city = addressComponent.city;
		var district = addressComponent.district;//区（海淀区）
		var street = addressComponent.street;
		var street_number = addressComponent.street_number;
		address = province + '，' + city + '，' + district + '，' + street + '，' + street_number;
		return address;
	};
	
	/* function getNearDetailAddress(addressComponent) {//处理附近地址信息
		var address = "";
		var province = addressComponent.province;
		var city = addressComponent.city;
		var district = addressComponent.district;//区（海淀区）
		address = province + '，' + city + '，' + district;
		return address;
	}; */
	
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
		var startBtn6 = document.getElementById('startBtn6');
		startBtn6.disabled = "";
		var startBtn7 = document.getElementById('startBtn7');
		startBtn7.disabled = "";
		
	};
	
	
////// 以上是定位和地图，一下是页面跳转 /////////////////////////////////////////////////////////
    
    
	function entryJob(jobId) {
		showLoading();
		var geoc = new BMap.Geocoder();    
		var pt = window.map.getCenter();
		geoc.getLocation(pt, function(rs){
			var addComp = rs.addressComponents;
			var screenWidth = $(window).width();
			whole_addr = encodeURI(encodeURI(addComp.province + "," + addComp.city + "," + addComp.district + "," + addComp.street + "," + addComp.streetNumber));
			window.location.href = "${ctx}/station/getNearbyList?lo="+whole_longitude+"&la="+whole_latitude+'&addr='+whole_addr+"&jobId="+jobId+"&screenWidth="+screenWidth;
		}); 
	};
	function entryResources(){
		/*跳转到待传资源页面*/
		showLoading();
		window.location.href = '${ctx}/acceptPhoto/toUploadResources';
	}
	
	function recordStation(){
		/*跳转到上站记录页面*/
		showLoading();
		window.location.href = '${ctx}/recordStation/toRecordStations';
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
			<td style="height: 45px;background:#F7F7F7">
				<table id="addressContainer" style="margin-top:2px;width: 100%;display: none">
					<tr>
						<td>
							<table  class="am-btn-default am-radius" style="width: 100%;background: #FAFCFF;">
								<tr>
									<td style="width:30px;height:40px;padding-left: 10px">
										<i class="am-icon-map-marker am-icon-sm am-success" style="color: #07C62D"></i>
									</td>
									<td>
										<div id="sel1" style="width: 100%;z-index: 9999999">正在获取地址...</div>
									</td>
									<td width="30px" onclick="resetloandla();">
										<i class="am-icon-refresh"  style="color: #AAAAAA"></i>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
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
			<td style="height: 120px;padding-top: 10px;font-size: 13px;background: #FAFCFF">
				<table class="am-table"> 
					<tr>
						<td align="center" style="border-style:none;">
							<button id="startBtn" onclick="entryJob('job_1');" type="button" class="am-btn am-btn-success" disabled="disabled" style="border-radius:6px;">
								<div class="app-btn3"></div>
							</button>
							<br/>
							日常巡检
						</td>
						<td align="center" style="border-style:none;">
							<button id="startBtn7" onclick="entryJob('job_6')" type="button" class="am-btn am-btn-warning" disabled="disabled" style="border-radius:6px;">
								<div class="app-btn3"></div>
							</button>
							<br/>
							季度巡检
						</td>
						<td align="center" style="border-style:none;">
							<button id="startBtn2" onclick="entryJob('job_3')" type="button" class="am-btn am-btn-danger" disabled="disabled" style="border-radius:6px;">
								<div class="app-btn1"></div>
							</button>
							<br/>
							参数普调
						</td>
						<td align="center" style="border-style:none;">
							<button id="startBtn3" onclick="entryJob('job_4');" type="button" class="am-btn am-btn-primary" disabled="disabled" style="border-radius:6px;">
								<div class="app-btn6"></div>
							</button>
							<br/>
							油机发电
						</td>
					</tr>
					<tr>
						<td align="center" style="border-style:none;">
							<button id="startBtn4" onclick="entryJob('job_5');" type="button" class="am-btn am-btn-secondary" disabled="disabled" style="border-radius:6px;">
								<div class="app-btn2"></div>
							</button>
							<br/>
							放电测试
						</td>
						<td align="center" style="border-style:none;">
							<button id="startBtn6" onclick="recordStation();" type="button" class="am-btn am-btn-primary" disabled="disabled" style="border-radius:6px;">
								<div class="app-btn7"></div>
							</button>
							<br/>
							上站记录
						</td>
						<td align="center" style="border-style:none;">
							<button id="startBtn1" onclick="entryJob('job_2')" type="button" class="am-btn am-btn-warning" disabled="disabled" style="border-radius:6px;">
								<div class="app-btn5"></div>
							</button>
							<br/>
							新站交维
						</td>
						<td align="center" style="border-style:none;">
							<div style="position: relative;">
								<span id="resourcesTips" class="am-badge am-badge-danger am-round" style="position: absolute;float: right;font-size: 14px;z-index: 99;left: 55px;top:-6px"></span>
								<button id="startBtn5" onclick="entryResources()" type="button" class="am-btn" disabled="disabled" style="background-color:#439B78;border-radius:6px;z-index: 98">
									<div class="app-btn4"></div>
								</button>
								<br/>
								待传资源
							</div>
						</td>
						<td align="center" style="border-style:none;"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
</body>
<script type="text/javascript">
var height = (window.innerHeight > 0) ? window.innerHeight: screen.height;
$(document.body).height(height);
//获取待传资源数量
$.getJSON("${ctx}/acceptPhoto/getCount.json", function(data){
	//clearStoreImg(data.storeArray);
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