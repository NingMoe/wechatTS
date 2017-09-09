<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>修改基站</title>
<%@include file="../commons/head.jsp" %>
<link rel="stylesheet" href="${ctx}/assets/css/amazeui.switch.css"/>
<script src="${ctx}/assets/js/amazeui.switch.min.js"></script>
<script type="text/javascript">	
	var url1 = '/station/toUpdateStation';
	var stationId1 = '${acceptStation.stationId}';

	window.onload = loadJScript;  //异步加载地图
	//百度地图API功能
	function loadJScript() {
		$('[name="handleWidth"]').bootstrapSwitch();
		
		var script = document.createElement("script");
		script.type = "text/javascript";
		script.src = "http://api.map.baidu.com/api?type=quick&v=1.0&ak=D7bc6a051e5ac6d616f273528ac5616a&callback=init";
		document.body.appendChild(script);
	}
	//Baidu全局变量
	var whole_longitude = '${acceptStation.longitude}';
	var whole_latitude = '${acceptStation.latitude}';
	
	var user_longitude = '${user.lo}';
	var user_latitude = '${user.la}';
	var whole_addr = "";
	function init(){
		//加载百度地图
		setloandla();
		
		//微信配置js
		wx.config(${jsConfig});
		wx.ready(function() {
	
		});
		wx.error(function(res) {
			modalAlert("网络出现问题，请稍后重试", null);
		});
	}
	

	function setloandla() {
		var bdPoint = new BMap.Point(whole_longitude,whole_latitude);
		bMapCallback(bdPoint);
	};
	
	//坐标转换之后调用回调函数进行加载百度地图
	function bMapCallback(point){
		window.map = new BMap.Map("l-map",{vectorMapLevel:99});
		//添加标注
		window.point = new BMap.Point(point.lng, point.lat);
		map.centerAndZoom(point, 15);
		
		var centerPointPx = map.pointToOverlayPixel(window.point);
		var px=centerPointPx.x-28+"px";
		var py=centerPointPx.y-45+"px";
		$("#l-map").append('<span class="BMap_Marker BMap_noprint" unselectable="on" "="" style="position: absolute; padding: 0px; margin: 0px; border: 0px; cursor: pointer; background-image: url(${ctx}/assets/i/tower-touch-.png); width: 50px; height: 50px; left: '+px+'; top: '+py+'; z-index: 8011856; background-position: initial initial; background-repeat: initial initial;"></span>');
		
		
		var myIcon = new BMap.Icon("${ctx}/assets/i/person.png", new BMap.Size(31,70));
		var markerRead = new BMap.Marker(new BMap.Point(user_longitude, user_latitude),{icon:myIcon});
	    map.addOverlay(markerRead);
	    
	};
	
    function getAddress(longitude, latitude) {
    	var Point=new BMap.Point(longitude, latitude);
    	var geoc = new BMap.Geocoder();    
    	geoc.getLocation(Point, function(rs){
			var addComp = rs.addressComponents;
			whole_addr = getLocationDetailAddress(addComp);
			var city = addComp.city;
			var county = addComp.district;
			if(city != null && city.indexOf("北京") != -1) {
				if(county != null) {
					if(county.indexOf("区") != -1) {
						city = "市辖区";
					} else if(county.indexOf("县") != -1) {
						city = "县";
					}
				}
			}
			$("#provinceId").val(addComp.province);	
			$("#cityId").val(city);	
			$("#countyId").val(addComp.district);	
			
			$("#hlongitude").val(longitude);	
			$("#hlatitude").val(latitude);	
			$("#longitude").html(Number(longitude).toFixed(6));
			$("#latitude").html(Number(latitude).toFixed(6));
			$("#stationAddress").val(whole_addr);
			
			var adress = "";
			if(addComp.province == city){
				adress = addComp.city + "/" + addComp.district;
			}else {
				adress = addComp.province + "/" + city + "/" + addComp.district;
			}
			$("#regin").text(adress);	
		}); 
	}
	
	function getLocationDetailAddress(addressComponent) {
		var address = "";
		var street = addressComponent.street;
		var street_number = addressComponent.streetNumber;
		address =  street + ' ' + street_number;
		return address;
	};
	
</script>
<style type="text/css">
	input::-webkit-outer-spin-button,
	input::-webkit-inner-spin-button {
    -webkit-appearance: none !important;
    margin: 0;
	}
	.am-form-group input[type="radio"] {
		display: none;
	}
	
	.am-form-group input[type="radio"]+label {
		font-weight:400;
		color:#3B3B3B;
	    border-radius:4px;
		display: inline-block;
		background-color: #EDEDED;
		cursor: pointer;
		padding: 5px 10px;
	}
	
	.am-form-group input[type="radio"]:checked+label {
		font-weight:700;
		background-color: #00BFFF;
		color: #FFFFFF;
	}
	td{
		vertical-align: middle;
	}

</style>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div onclick="toPageUrl('${ctx}/station/gotoNearbyList')">
			<span class="am-topbar-brand am-icon-angle-left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<font size="3">修改基站</font></span>
		</div>
	</header>
	<div id="stationDeatil">
	
		<form class="am-form" id="updateStationForm" action="${ctx}/station/updateStation" method="post" data-am-validator>	    
		    <table class="am-table app-tb">
				<tr>
					<th colspan="2" style="border-top: none">
						基础信息		
						<input name="stationId" value="${acceptStation.stationId}" hidden="true">
					</th>						
				</tr>
				<tr>
					<td style="vertical-align: middle;width:100px">
						基站名称：<span style="color: red">*</span>
					</td>
					<td>
						<input name="stationName" value="${acceptStation.stationName}" class="am-form-field" type="text" id="doc-vld-name-2-0" placeholder="输入基站名称" required/>
						<span id='checkFalse' style="display: none; color: red;">
							<i class='am-icon-close'></i>
						</span>
						<span id='nameTips'></span>
					</td>
				</tr>
				<tr style="height: 50px;">
					<td style="vertical-align: middle;">
						动环名称：
					</td>
					<td style="vertical-align: middle;">
						${acceptStation.stationDH}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle">
						基站编号：
					</td>
					<td>
						<input type="text" id="station_no" value="${acceptStation.stationNo}" class="am-form-field" name="stationNo" placeholder="请输入基站编号" />
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">
						基站类型：
					</td>
					<td style="vertical-align: middle;">
						<select id="station_type" data-am-selected="{maxHeight:200}" name="stationType">
						  <option <c:if test="${acceptStation.stationType eq '宏站'}"> selected="selected" </c:if> value="宏站">宏站</option>
						  <option <c:if test="${acceptStation.stationType eq '机房'}"> selected="selected" </c:if> value="机房">机房</option>
						  <option <c:if test="${acceptStation.stationType eq '拉远'}"> selected="selected" </c:if> value="拉远">拉远</option>
						  <option <c:if test="${acceptStation.stationType eq '室分'}"> selected="selected" </c:if> value="室分">室分</option>
						  <option <c:if test="${acceptStation.stationType eq 'WLAN站'}"> selected="selected" </c:if> value="WLAN站">WLAN站</option>
						  <option <c:if test="${acceptStation.stationType eq '直放站'}"> selected="selected" </c:if> value="直放站">直放站</option>
						  <option <c:if test="${acceptStation.stationType eq '传输节点站'}"> selected="selected" </c:if> value="传输节点站">传输节点站</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle">
						基站负载：
					</td>
					<td>
						<div class="am-input-group">
							<input step="0.001" type="number" id="load_current" class="am-form-field" value="${acceptStation.loadCurrent}" name="loadCurrent"  placeholder="请输入基站负载" />
							<span class="am-input-group-label">A</span>
						</div>
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
						区域经理<br/>联系方式：
					</td>
					<td style="vertical-align: middle;">
						${acceptStation.managerPhone}
					</td>
				</tr>
				<tr hidden>
					<td style="vertical-align: middle;">
						区域经理：
					</td>
					<td>
						<input id="regionalManager" name="regionalManager" type="text" id="doc-vld-name-2-0" placeholder="输入区域经理" value="${acceptStation.regionalManager}"  />
						<span id='checkFalse' style="display: none; color: red;"><i class='am-icon-close'></i>
						</span><span id='nameTips'></span>
					</td>
				</tr>
				<tr hidden>
					<td style="vertical-align: middle;">
						区域经理<br/>联系方式：
					</td>
					<td>
						<input id="managerPhone" name="managerPhone" type="text" id="doc-vld-name-2-0" placeholder="输入区域经理联系方式" value="${acceptStation.managerPhone}"  />
						<span id='checkFalse' style="display: none; color: red;"><i class='am-icon-close'></i>
						</span><span id='nameTips'></span>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle">
						后备时长：
					</td>
					<td>
						<div class="am-input-group">
							<input step="0.001" type="number" id="longBack" class="am-form-field" name="longBack" placeholder="请输入后备时长"  value="${acceptStation.longBack}" />
							<span class="am-input-group-label">H</span>
						</div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">
						运营商：<span style="color: red">*</span>
					</td>
					<td>
						<div class="am-form-group" style="height: auto;margin-bottom: 0">
					      <label class="am-checkbox-inline">
					        <input type="checkbox" value="移动" id="yd" name="mobileOperator" minchecked="1" maxchecked="5" required>移动
					      </label>
					      <label class="am-checkbox-inline">
					        <input type="checkbox" value="电信" id="dx" name="mobileOperator">电信
					      </label>
					      <label class="am-checkbox-inline">
					        <input type="checkbox" value="联通" id="lt" name="mobileOperator">联通
					      </label>
					      <br/>
					      <label class="am-checkbox-inline">
					        <input type="checkbox" value="铁塔" id="tt" name="mobileOperator">铁塔
					      </label>
					      <label class="am-checkbox-inline">
					        <input type="checkbox" value="其他" id="qt" name="mobileOperator">其他
					      </label>
					    </div>
					</td>
				</tr>
				<tr hidden>
					<td style="vertical-align: middle;">
						所在省：
					</td>
					<td>
						<input type="text" id="provinceId" name="provinceId" value="${acceptStation.provinceId }"/>
					</td>
				</tr>
				<tr hidden>
					<td style="vertical-align: middle;">
						所在市：
					</td>
					<td>
						<input type="text" id="cityId" name="cityId" value="${acceptStation.cityId }"/>
					</td>
				</tr>
				<tr hidden>
					<td style="vertical-align: middle;">
						所在区/县：
					</td>
					<td>
						<input type="text" id="countyId" name="countyId" value="${acceptStation.countyId }"/>
					</td>
				</tr>
				<tr hidden>
					<td style="vertical-align: middle;">
						基站经度：
					</td>
					<td>
						<input id="hlongitude" type="text" name="longitude" value="${acceptStation.longitude }"/>
					</td>
				</tr>
				<tr hidden>
					<td style="vertical-align: middle;">
						基站纬度：
					</td>
					<td>
						<input id="hlatitude" type="text" name="latitude" value="${acceptStation.latitude }"/>
					</td>
				</tr>
			</table>
			<table class="am-table app-tb">
				<tr>
					<th colspan="2">
						铁塔		
						<input name="towerId" value="${acceptTower.towerId}" hidden="true">		
					</th>
				</tr>
				<tr>
					<td style="vertical-align: middle;width:100px">
						区域性质：
					</td>
					<td style="vertical-align: middle">
						<div class="am-form-group" style="height: auto;margin-bottom: 0">
							<input type="radio" value="市区" id="city" <c:if test="${acceptTower.regionProperty eq '市区'}"> checked="checked" </c:if> name="regionProperty">
							<label for="city" style="margin-bottom: 0">
								市区
							</label>
		
							<input type="radio" value="郊区" id="suburb" <c:if test="${acceptTower.regionProperty eq '郊区'}"> checked="checked" </c:if> name="regionProperty">
							<label for="suburb" style="margin-bottom: 0">
								郊区
							</label>
		
							<input type="radio" value="城镇" id="town" <c:if test="${acceptTower.regionProperty eq '城镇'}"> checked="checked" </c:if> name="regionProperty">
							<label for="town" style="margin-bottom: 0">
								城镇
							</label>
							<input type="radio" value="农村" id="vallage" <c:if test="${acceptTower.regionProperty eq '农村'}"> checked="checked" </c:if> name="regionProperty">
							<label for="vallage" style="margin-bottom: 0">
								农村
							</label>
						</div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle">
						地貌：
					</td>
					<td style="vertical-align: middle">
						<div class="am-form-group" style="height: auto;margin-bottom: 0">
							<input type="radio" value="平原" id="flat" <c:if test="${acceptTower.landScape eq '平原'}"> checked="checked" </c:if> name="landScape">
							<label for="flat" style="margin-bottom: 0">
								平原
							</label>
		
							<input type="radio" value="高山" id="hill" <c:if test="${acceptTower.landScape eq '高山'}"> checked="checked" </c:if> name="landScape">
							<label for="hill" style="margin-bottom: 0">
								高山
							</label>
		
							<input type="radio" value="沿海" id="sea" <c:if test="${acceptTower.landScape eq '沿海'}"> checked="checked" </c:if> name="landScape">
							<label for="sea" style="margin-bottom: 0">
								沿海
							</label>
						</div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle">
						铁塔类型：
					</td>
					<td style="vertical-align: middle">
						<div class="am-form-group" style="height: auto;margin-bottom: 0">
							<input type="radio" id="floor" value="地面塔" <c:if test="${acceptTower.towerType eq '地面塔'}"> checked="checked" </c:if>
								name="towerType">
							<label for="floor" style="margin-bottom: 0">
								地面塔
							</label>
		
							<input type="radio" id="building" value="楼面塔" <c:if test="${acceptTower.towerType eq '楼面塔'}"> checked="checked" </c:if>
								name="towerType">
							<label for="building" style="margin-bottom: 0">
								楼面塔
							</label>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p>铁塔外观照：</p>
						<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${towerPhotoInfos}" var="item">
							    <li id="${item.photoId}" class="photocontent">
								    <div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
								     	<div style="height:72px;margin-bottom:2px;overflow:hidden;">
								       		<img src="${item.thumbLocation}"   <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if>   alt="" data-rel="${item.fileLocation}"/>
								       	</div>
								       	<h3 class="am-gallery-title am-fr" onclick="deletePhotoInServer('${ctx}','${item.photoId}','${item.localId}')">删除  <i class="am-icon-trash-o am-icon-sm"  style="color:#dd514c;opacity:100;margin-top: 6px;"></i></h3>
								    </div>
								</li>
							</c:forEach>
							<li id="galleryAddBtn" onclick="takePhoto(this,'towerLocalIds');" data-weg>
								<div onclick="void(0)">
									<button type="button" class="app-add-photo"></button>
							    </div>
							</li>
							
						</ul>
						<input  class="localIds" type="hidden" id="towerLocalIds" name="towerLocalIds"  value=""/>
					</td>
				</tr>
			</table>
			<table class="am-table app-tb">
			    <tr>
					<th colspan="2">
						机房		
						<input name="roomId" value="${acceptRoom.roomId}" hidden="true">					
					</th>
				</tr>
				<tr>
					<td style="vertical-align: middle;width:100px">
						机房类型：
					</td>
					<td style="vertical-align: middle">
						<div class="am-form-group" style="height: auto;margin-bottom: 0">
							<input type="radio" id="selfbuild" value="自建房" <c:if test="${acceptRoom.roomType eq '自建房'}"> checked="checked" </c:if>
								name="roomType">
							<label for="selfbuild">
								自建房
							</label>
		
							<input type="radio" id="post" value="通信楼" <c:if test="${acceptRoom.roomType eq '通信楼'}"> checked="checked" </c:if>
								name="roomType">
							<label for="post">
								通信楼
							</label>
		
							<input type="radio" id="office" value="办公楼" <c:if test="${acceptRoom.roomType eq '办公楼'}"> checked="checked" </c:if>
								name="roomType">
							<label for="office">
								办公楼
							</label>
							<input type="radio" id="big" value="大型建筑" <c:if test="${acceptRoom.roomType eq '大型建筑'}"> checked="checked" </c:if>
								name="roomType">
							<label for="big" style="margin-bottom: 0">
								大型建筑
							</label>
							<input type="radio" id="live" value="居民住宅" <c:if test="${acceptRoom.roomType eq '居民住宅'}"> checked="checked" </c:if>
								name="roomType">
							<label for="live" style="margin-bottom: 0">
								居民住宅
							</label>
						</div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle">
						建筑方式：
					</td>
					<td style="vertical-align: middle">
						<div class="am-form-group" style="height: auto;margin-bottom: 0">
							<input type="radio" id="earth" value="落地" <c:if test="${acceptRoom.buildingPattern eq '落地'}"> checked="checked" </c:if>
								name="buildingPattern">
							<label for="earth" style="margin-bottom: 0">
								落地
							</label>
							<input type="radio" id="roof" value="屋顶" <c:if test="${acceptRoom.buildingPattern eq '屋顶'}"> checked="checked" </c:if>
								name="buildingPattern">
							<label for="roof" style="margin-bottom: 0">
								屋顶
							</label>
						</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p>机房外观照：</p>
						<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${roomPhotoInfos}" var="item">
							    <li id="${item.photoId}">
							    <div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
							     	<div style="height:72px;margin-bottom:2px;overflow:hidden;">
							       		<img src="${item.thumbLocation}" <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if>  alt="" data-rel="${item.fileLocation}"/>
							       	</div>
							       	<h3 class="am-gallery-title am-fr" onclick="deletePhotoInServer('${ctx}','${item.photoId}')">删除  <i class="am-icon-trash-o am-icon-sm" style="color:#dd514c;opacity:100;margin-top: 6px;"></i></h3>
							    </div>
							</li>
							</c:forEach>
							<li id="galleryAddBtn1" onclick="takePhoto(this,'roomLocalIds');" data-weg>
							    <div onclick="void(0)">
							     <button type="button" class="app-add-photo"></button>
							    </div>
							</li>
						</ul>
						<input  class="localIds" type="hidden" id="roomLocalIds" name="roomLocalIds"  value="" size="100"/>
					</td>
				</tr>
			</table>
			<div style="background-color: white;">
				<table class="am-table">
					<tr>
						<td style="vertical-align: middle;">
							归属区域：		
						</td>
						<td style="vertical-align: middle;">
							<label id="regin" style="margin-bottom: 0;font-weight: 200;font-size: 1.6rem;">
								<c:if test="${acceptStation.provinceId != acceptStation.cityId}">${acceptStation.provinceId}/</c:if>${acceptStation.cityId}/${acceptStation.countyId}
							</label>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							基站经纬度：
						</td>
						<td style="vertical-align: middle;">
							<span id="longitude">${acceptStation.longitude}</span>,
							<span id="latitude">${acceptStation.latitude}</span>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle">
							地址：
						</td>
						<td style="vertical-align: middle">
							<input id="stationAddress" name="stationAddress" value="${acceptStation.stationAddress}" class="am-form-field" type="text" placeholder="输入地址"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="padding: 5px;">
							<font style="color: #FF3333;">位置纠偏</font>
							<button id="currentPosition" type="button" disabled="disabled" class="am-btn am-radius am-fr" style="background: #EF7321;color: #fff;height: 30px;width: 80px;padding: 0px;" onclick="position();">
						  		<font size="2">
						  			<i class="am-icon-street-view"></i>当前位置
						  		</font>
						  	</button>
							<i class="am-fr"><input id="switch-state" name="handleWidth" type="checkbox" data-size="xs" checked  data-on-text="开启" data-off-text="关闭" data-on-color="success" data-off-color="danger"/>&nbsp;&nbsp;&nbsp;</i>
							<div style="height: 10px;"></div>
							<div style='position:relative; width:100%; height:240px;'>
							    <div id="l-map" style='position:absolute; z-index:2; width:100%; height:240px; left:0px; top:0px; '></div>
							    <div id="stateMap" style='position:absolute; z-index:3; width:100%; height:240px; left:0px; top:0px;'></div>
							</div>
						</td>
					</tr>
				</table>
			</div>
			<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool">
				<div style="height: 5px;"></div>
				<button class="am-btn am-btn-success am-radius am-btn-block" id="submit" onclick="submitForm();">
					<strong>保存</strong>
				</button>
			</div>
		</form>
	</div>
	<c:forEach items="${stationList}" var="obj" varStatus="status">
		<span hidden class='stationNames'>${obj.stationName}</span>
	</c:forEach>
<%@include file="../station/stationAbnormal.jsp" %>
<script type="text/javascript">
//验证基站是否存在
    $('input[name=stationName]').change(function(){
      var stationList = $('.stationNames');
      var tepName = $('input[name=stationName]').val();
      if(tepName == null || tepName == ''){
      	 $('#submit').attr('disabled','disabled');
         $('#checkFalse').css('display','inline');
         $('#nameTips').text('名称不能为空');
      }else {
      	 if(tepName != '${acceptStation.stationName}'){
      	 	for(var i=0;i<stationList.length;i++){
	          var stationName = stationList[i].innerHTML;
	          if(tepName == stationName){
	              $('#submit').attr('disabled','disabled');
	              $('#checkFalse').css('display','inline');
	              $('#nameTips').text('名称已被占用');
	              break;
	          }else{
	        	  $('#submit').attr('disabled',false);
	        	  $('#checkFalse').css('display','none');
	              $('#nameTips').text('');
	          }
	      }
      	 }else{
      	 	$('#submit').attr('disabled',false);
       	  	$('#checkFalse').css('display','none');
            $('#nameTips').text('');
      	 }
      }
     
	});
	function validateMobileOperator() {
		var val = false;
		val = $("input[name='mobileOperator']").is(':checked');
		return val;
	}
    function submitForm() {
    	//用store传照片saveBase64ImgData(function(){});
    	var flag = validateMobileOperator();
    	if(flag) {
    		$('#updateStationForm').submit(function(){
	    		showLoading("正在加载...");
	    	});
    	}
    }

	$('#switch-state').on('switchChange.bootstrapSwitch', function(event, state) {
		/*switch的监听器*/
		if(!state){
			document.getElementById('stateMap').style.display = "none";
			document.getElementById('currentPosition').disabled=false;
	    	//给地图添加拖动的监听事件，
			map.addEventListener("moveend", showInfo);
			
			map.addEventListener("zoomend", function(){
				 //alert("地图缩放至：" + this.getZoom() + "级"); 
				 //map.setCenter(window.point);
			});
    	}else{
    		document.getElementById('stateMap').style.display = "";
    		document.getElementById('currentPosition').disabled=true;
    	};
	});
	
	function showInfo(){
		var cp = map.getCenter();
		var centerPoint=new BMap.Point(cp.lng, cp.lat);
		
		//var twoDistance=MyGetDistance(window.point.lat,window.point.lng,centerPoint.lat,centerPoint.lng);
		/* if(twoDistance > 5000){
			modalAlert('离定位距离:'+twoDistance+' 米,超过5000米,请重新选择!');
			map.centerAndZoom(window.point, 15);
			centerPoint=window.point;
		}*/
		whole_longitude = centerPoint.lng;
		whole_latitude = centerPoint.lat;
		getAddress(centerPoint.lng, centerPoint.lat);				//百度地址调用bd09ll
	}
	
	function position(){
		map.centerAndZoom(new BMap.Point(user_longitude, user_latitude), 15);
		whole_longitude = user_longitude;
		whole_latitude = user_latitude;
		getAddress(user_longitude, user_latitude);				//百度地址调用bd09ll
	}
	

    !function(t) {
    	$("ul[data-am-widget='gallery']").one("click",function(){//点击预览图片关闭预览
			$(".am-pureview-slider").each(function(i){
				$(this).on("click",function(){
					$(".am-icon-chevron-left[data-am-close='pureview']").eq(i).triggerHandler("click");
				});
			});
		});
    	var mobileOperators = '${acceptStation.mobileOperator}';
    	if(mobileOperators != null && mobileOperators != '' && mobileOperators != 'null') {
			var mobileOperator = mobileOperators.split(',');
			for(var i = 0; i < mobileOperator.length; i++) {
				$("input[name='mobileOperator'][value="+mobileOperator[i]+"]")[0].checked = "checked";
			}
		}
    	
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
</script>
</body>
</html>