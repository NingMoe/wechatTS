<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>地图</title>
<%@include file="../commons/head.jsp"%>
<style type="text/css">
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
    -webkit-appearance: none !important;
    margin: 0;
}
.app-btn-active{
	background-color: #ddd;
}
.testborder{
	border:1px solid red;
}
</style>
</head>
<body>
	<div id="allmap" style="height:500px;width:100%;"></div>
	<div class="am-modal am-modal-loading" tabindex="-1" id="modal-loading">
	  <button type="button" class="am-btn am-radius am-modal-dialog" style="width:200px;background-color: black;filter:alpha(opacity:60);opacity:0.6;">
	    <div class="am-modal-hd" style="font-size: 14px;color: white;" id="modal-loading-msg">正在载入...</div>
	    <div class="am-modal-bd">
	      <span class="am-icon-spinner am-icon-spin"></span>
	    </div>
	  </button>
	</div>
</body>
<script>
	function loadJScript() {
		showLoading();
		var script = document.createElement("script");
		script.type = "text/javascript";
		script.src = "http://api.map.baidu.com/api?v=2.0&ak=D7bc6a051e5ac6d616f273528ac5616a&callback=init";
		document.body.appendChild(script);
	}
	window.onload = loadJScript;  //异步加载地图
	var map={};
	function init() {
		map = new BMap.Map("allmap");// 创建Map实例
		
		var geolocationControl = new BMap.GeolocationControl();
		geolocationControl.addEventListener("locationSuccess", function(e){
		  // 定位成功事件
		  /* var address = '';
		  address += e.addressComponent.province;
		  address += e.addressComponent.city;
		  address += e.addressComponent.district;
		  address += e.addressComponent.street;
		  address += e.addressComponent.streetNumber;
		  alert("当前定位地址为：" + address); */
		});
		geolocationControl.addEventListener("locationError",function(e){
		  // 定位失败事件
		  alert(e.message);
		});
		map.addControl(geolocationControl);
		
		var geolocation = new BMap.Geolocation();
		geolocation.getCurrentPosition(function(r){
			if(this.getStatus() == BMAP_STATUS_SUCCESS){
				window.point = new BMap.Point(r.point.lng, r.point.lat); // 创建点坐标
				map.centerAndZoom(window.point,15);
				setCenterIconOnMap();
			}else {
				//modalAlert("未获取到位置");
				var myCity = new BMap.LocalCity();
				myCity.get(function(){
					var cityName = result.name;
			      	map.centerAndZoom(cityName,15);
			      	window.point=map.getCenter();
			      	setCenterIconOnMap();
				});
			}
		},{enableHighAccuracy: true});
		map.enableInertialDragging();
		map.addEventListener("moveend", function showInfo(){
			window.point=map.getCenter();
			window.parent.getAddress(window.point.lng, window.point.lat);				//百度地址调用bd09ll
		});
	}
	
	
	/* function setMapCenter(){
		
	} */

	var mapNotInit=true;
	function setCenterIconOnMap(){
		 if(mapNotInit){
			var pointi = map.pointToOverlayPixel(window.point);
			var px = pointi.x-25+"px";
			var py = pointi.y-50+"px";
			//alert(px +","+ py);
			$("#allmap").append('<div class=" anchorBL" style="width: 50px; height: 50px; position: absolute; z-index: 99999999; bottom: 0px; right: auto; top: auto; left:'+ px +'; top:'+py+';" data-unuse="1"><img style="border:none;width: 50px; height: 50px;" src="${ctx}/assets/i/5050.png"></div>');
			mapNotInit=false;
			closeLoading();
		}
	}
	
	!function(t) {
	}(window.jQuery || window.Zepto);
</script>
</html>