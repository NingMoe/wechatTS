<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
	<head>
	<title>新增基站</title>
	<%@include file="../commons/head.jsp"%>
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
	font-weight: 400;
	color: #3B3B3B;
	border-radius: 4px;
	display: inline-block;
	background-color: #EDEDED;
	cursor: pointer;
	padding: 5px 10px;
}
.am-form-group input[type="radio"]:checked+label {
	font-weight: 700;
	background-color: #00BFFF;
	color: #FFFFFF;
}
td {
	vertical-align: middle;
}
</style>
<script type="text/javascript">
	wx.config(${jsConfig});
	wx.ready(function() {
		//nothing
	});
	wx.error(function(res) {
		modalAlert("网络出现问题，请稍后重试", null);
	});
</script>
</head>
	<body>
		<header class="am-topbar am-topbar-fixed-top">
		<div onclick="gohistory();">
			<span class="am-topbar-brand am-icon-angle-left"></span>
			<div class="am-topbar-brand  app-toolbar">
				新增基站
			</div>
		</div>
		</header>
		<form class="am-form" id="addStation" method="post" action="${ctx}/station/add" data-am-validator>
			<table class="am-table app-tb">
				<tbody>
					<tr>
						<th style="border-top: none;" colspan="2">
							基站信息
						</th>
					</tr>
					<tr>
						<td style="vertical-align: middle; width: 99px">
							基站名称：<span style="color: red">*</span>
						</td>
						<td>
							<input name="stationName" type="text" id="doc-vld-name-2-0"
								placeholder="输入基站名称" required />
							<span id='checkFalse' style="display: none; color: red;"><i
								class='am-icon-close'></i>
							</span><span id='nameTips'></span>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle">
							基站编号：
						</td>
						<td>
							<input type="text" id="station_code" class="am-form-field"
								name="stationNo" placeholder="请输入基站编号"/>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							基站类型：
						</td>
						<td style="vertical-align: middle;">
							<select data-am-selected="{maxHeight:200}" name="stationType"
								id="station_type">
								<option value="宏站">
									宏站
								</option>
								<option value="机房">
									机房
								</option>
								<option value="拉远">
									拉远
								</option>
								<option value="室分">
									室分
								</option>
								<option value="WLAN站">
									WLAN站
								</option>
								<option value="直放站">
									直放站
								</option>
								<option value="传输节点站">
									传输节点站
								</option>
							</select>
						</td>
					</tr>
					<tr hidden>
						<td style="vertical-align: middle;">
							所在省：
						</td>
						<td style="vertical-align: middle;">
							<input type="text" id="provinceId" name="provinceId"
								value='${province}' />

						</td>
					</tr>
					<tr hidden>
						<td style="vertical-align: middle;">
							所在市：
						</td>
						<td style="vertical-align: middle;">
							<input type="text" id="cityId" name="cityId" value='${city}' />
						</td>
					</tr>
					<tr hidden>
						<td style="vertical-align: middle;">
							所在县/区：
						</td>
						<td style="vertical-align: middle;">
							<input type="text" id="countyId" name="countyId"
								value='${county}' />
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							归属区域：
						</td>
						<td>
							${area}
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							基站经纬度：
						</td>
						<td style="vertical-align: middle;">
							<span id="longitude">${s_user.lo}</span>,
							<span id="latitude">${s_user.la}</span>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							详细地址：
						</td>
						<td>
							<input type="text" name="stationAddress" id="station_address"
								class="am-form-field" value="${stationAddress}"/>

						</td>
					</tr>
					<tr hidden>
						<td style="vertical-align: middle;">
							基站经度：
						</td>
						<td>
							<input type="number" id="hlongitude" name="longitude"
								placeholder="请输入基站经度0-180" min="0" max="180"
								value="${s_user.lo}" />
						</td>
					</tr>
					<tr hidden>
						<td style="vertical-align: middle;">
							基站纬度：
						</td>
						<td>
							<input type="number" id="hlatitude" name="latitude"
								placeholder="请输入基站纬度0-90" min="0" max="90" value="${s_user.la}" />
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle">
							基站负载：
						</td>
						<td>
							<div class="am-input-group">
								<input step="0.001" type="number" id="load_current" class="am-form-field" name="loadCurrent"  placeholder="请输入基站负载" />
								<span class="am-input-group-label">A</span>
							</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							区域经理：
						</td>
						<td style="vertical-align: middle">
							${regionalManager.userName}
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							区域经理<br/>联系方式：
						</td>
						<td style="vertical-align: middle">
							${regionalManager.phone}
						</td>
					</tr>
					<tr hidden>
						<td style="vertical-align: middle;">
							区域经理：
						</td>
						<td>
							<input id="regionalManager" name="regionalManager" type="text" id="doc-vld-name-2-0" placeholder="输入区域经理" value="${regionalManager.userName}"  />
							<span id='checkFalse' style="display: none; color: red;"><i class='am-icon-close'></i>
							</span><span id='nameTips'></span>
						</td>
					</tr>
					<tr hidden>
						<td style="vertical-align: middle;">
							区域经理<br/>联系方式：
						</td>
						<td>
							<input id="managerPhone" name="managerPhone" type="text" id="doc-vld-name-2-0" placeholder="输入区域经理联系方式" value="${regionalManager.phone}"  />
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
								<input step="0.001" type="number" id="longBack" class="am-form-field" name="longBack" placeholder="请输入后备时长" />
								<span class="am-input-group-label">H</span>
							</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							运营商：<span style="color: red">*</span>
						</td>
						<td style="vertical-align: middle;">
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
				</tbody>
			</table>
			<table class="am-table app-tb">
				<tbody>
					<tr>
						<th colspan="2">
							铁塔
						</th>
					</tr>
					<tr>
						<td style="vertical-align: middle; width: 99px">
							区域性质：
						</td>
						<td style="vertical-align: middle">
							<div class="am-form-group" style="height: 24px">
								<input type="radio" id="city" value="市区" checked="checked"
									name="regionProperty">
								<label for="city">
									市区
								</label>

								<input type="radio" id="suburb" value="郊区" name="regionProperty">
								<label for="suburb">
									郊区
								</label>

								<input type="radio" id="town" value="城镇" name="regionProperty">
								<label for="town">
									城镇
								</label>
								<input type="radio" id="vallage" value="农村"
									name="regionProperty">
								<label for="vallage">
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
							<div class="am-form-group" style="height: 24px">
								<input type="radio" value="平原" id="flat" name="landScape"
									checked="checked">
								<label for="flat">
									平原
								</label>

								<input type="radio" id="hill" value="高山" name="landScape">
								<label for="hill">
									高山
								</label>

								<input type="radio" id="sea" value="沿海" name="landScape">
								<label for="sea">
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
							<div class="am-form-group" style="height: 24px">
								<input type="radio" value="地面塔" id="floor" name="towerType"
									checked="checked">
								<label for="floor">
									地面塔
								</label>

								<input type="radio" id="building" value="楼面塔" name="towerType">
								<label for="building">
									楼面塔
								</label>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<p>铁搭外观照片：</p>
							<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
								<li id="galleryAddBtn" onclick="takePhoto(this,'towerImgs');" data-weg>
									 <div onclick="void(0)">
										<button type="button" class="app-add-photo"></button>
									</div>
								</li>
							</ul>
							<input  class="localIds" type="hidden" id="towerImgs" name="towerImgs"  value="" size="100"/>
						</td>
					</tr>
				</tbody>
			</table>
			<table class="am-table app-tb">
				<tbody>
					<tr>
						<th colspan="2">
							机房
						</th>
					</tr>
					<tr>
						<td style="vertical-align: middle; width: 99px">
							机房类型：
						</td>
						<td>
							<div class="am-form-group" style="height: auto">
								<input type="radio" value="自建房" id="selfbuild" name="roomType"
									checked="checked">
								<label for="selfbuild">
									自建房
								</label>

								<input type="radio" id="post" value="通信楼" name="roomType">
								<label for="post">
									通信楼
								</label>

								<input type="radio" id="office" value="办公楼" name="roomType">
								<label for="office">
									办公楼
								</label>
								<input type="radio" id="big" value="大型建筑" name="roomType">
								<label for="big">
									大型建筑
								</label>
								<input type="radio" id="live" value="居民住宅" name="roomType">
								<label for="live">
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
							<div class="am-form-group" style="height: 24px">
								<input type="radio" value="落地" id="earth" name="buildingPattern"
									checked="checked">
								<label for="earth">
									落地
								</label>
								<input type="radio" id="roof" value="屋顶" name="buildingPattern">
								<label for="roof">
									屋顶
								</label>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<p>机房外观照片：</p>
							<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
								<li id="galleryAddBtn1" onclick="takePhoto(this,'roomImgs');" data-weg>
									 <div onclick="void(0)">
										<button type="button" class="app-add-photo"></button>
									</div>
								</li>
							</ul>
							<input  class="localIds" type="hidden" id="roomImgs" name="roomImgs"  value="" size="100"/>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="app-div-btn-tool">
				<button class="am-btn am-btn-success am-radius am-btn-block"
					id="submit" disabled="disabled" onclick="submitForm();">
					<strong>提交</strong>
				</button>
			</div>
		</form>
		<c:forEach items="${stationList}" var="obj" varStatus="status">
			<span hidden class='stationNames'>${obj.stationName}</span>
		</c:forEach>
		<div style="height: 10px">&nbsp;</div>
	</body>
	<script type="text/javascript">
//验证基站是否存在
    $('input[name=stationName]').change(function(){
      var stationList = $('.stationNames');
      var tepName = $('input[name=stationName]').val();
      for(var i=0;i<stationList.length;i++){
          var stationName = stationList[i].innerHTML;
          if(tepName == stationName){
              $('#submit').attr('disabled','disabled');
              $('#checkFalse').css('display','inline');
              $('#nameTips').text('名称已被占用');
              break;
          }else{
        	  $('#checkFalse').css('display','none');
              $('#nameTips').text('');
        	  if($('input[name=stationName]').val() != ""){
	        	  $('#submit').attr('disabled',false);
              }else{
            	  $('#submit').attr('disabled','disabled');
              }
          }
      }
      if(stationList.length == 0){
    	  if($('input[name=stationName]').val() == ""){
        	  $('#submit').attr('disabled','disabled');
          }else{
        	  $('#submit').attr('disabled',false);
          }
      }
	});

	function validateMobileOperator() {
		var val = false;
		val = $("input[name='mobileOperator']").is(':checked');
		return val;
	}
    function submitForm() {
    	//saveBase64ImgData();
    	var flag = validateMobileOperator();
    	if(flag) {
    		$('#addStation').submit(function(){
	    		showLoading("正在加载...");
	    	});
    	}
    }
!function(t) {
	$.getJSON("${ctx}/common/getProvince.json", function(data){
		 var html = "";
		 $.each(data.orgs, function(i,item){
			   html += '<option parentId="'+item.id+'" value="'+ item.name +'">'+ item.name +'</option>';
		 }); 
		 $("#province").html(html);
	});
	var longitude = '${s_user.lo}';
	var latitude = '${s_user.la}';
	if(longitude != null && longitude != '' && longitude != 'null') {
		$("#longitude").html(Number(longitude).toFixed(6));
	}
	
	if(latitude != null && latitude != '' && latitude != 'null') {
		$("#latitude").html(Number(latitude).toFixed(6));
	}
	//showImagesH5();
}(window.jQuery || window.Zepto);

$("#province").change(this,function(){
  $.getJSON("${ctx}/common/getProvince.json?parentId="+$(this).find('option:selected').attr("parentId"), function(data){
	 var html = "";
	 $.each(data.orgs, function(i,item){
		  html += '<option parentId="'+item.id+'" value="'+ item.name +'">'+ item.name +'</option>';
	 }); 
	   $("#city").html(html);
	}); 
});
$("#city").change(this,function(){
  $.getJSON("${ctx}/common/getProvince.json?parentId="+$(this).find('option:selected').attr("parentId"), function(data){
	 var html = "";
	 $.each(data.orgs, function(i,item){
		  html += '<option parentId="'+item.id+'" value="'+ item.name +'">'+ item.name +'</option>';
	 }); 
	   $("#county").html(html);
	});
});
$(".am-pureview-slider").on("click",function(){
	$(".am-icon-chevron-left[data-am-close='pureview']").triggerHandler("click");
});

</script>
</html>