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
			setloandla();
		});
		wx.error(function(res) {
			modalAlert("网络出现问题，请稍后重试", null);
		});
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
		var gpsPoint = new BMap.Point(Mlongitude,Mlatitude);
		my_translate(gpsPoint,1,bMapCallback);
	}
	
	function bMapCallback (point){ //坐标转换之后调用回调函数进行加载百度地图
		window.map = new BMap.Map("l-map");
		
		//添加标注
		window.point = new BMap.Point(point.lng, point.lat);
		map.centerAndZoom(point, 15);
		
		whole_longitude = point.lng;
		whole_latitude = point.lat;
		
		entryJob($("#jobId").val());
	};
	
	
////// 以上是定位和地图，一下是页面跳转 /////////////////////////////////////////////////////////
	function entryJob(jobId) {
		var toPageUrl=$("#toPageUrl").val();
		var geoc = new BMap.Geocoder();    
		var pt = window.map.getCenter();
		geoc.getLocation(pt, function(rs){
			var addComp = rs.addressComponents;
			var screenWidth = $(window).width();
			whole_addr = encodeURI(encodeURI(addComp.province + "," + addComp.city + "," + addComp.district + "," + addComp.street + "," + addComp.streetNumber));
			window.location.href = "${ctx}/"+toPageUrl+"?lo="+whole_longitude+"&la="+whole_latitude+'&addr='+whole_addr+"&jobId="+jobId+"&screenWidth="+screenWidth;
		}); 
	};
	
</script>
</head>
<body>
<input type="hidden" id="jobId" value="${jobId}">
<input type="hidden" id="toPageUrl" value="${toPageUrl}">
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
								如果长时间未加载成功，请重新打开
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
	</table>
</div>
</body>
<script type="text/javascript">
var height = (window.innerHeight > 0) ? window.innerHeight: screen.height;
$(document.body).height(height);
</script>
</html>