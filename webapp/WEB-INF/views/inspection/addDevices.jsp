<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>新增设备</title>
<%@include file="../commons/head.jsp" %>
<%@include file="../commons/mobiscrollDatePlugin.jsp" %> 

<style type="text/css">
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
    -webkit-appearance: none !important;
    margin: 0;
}
</style>
<script type="text/javascript">
var tempDeviceType;
$(function(){
	tempDeviceType = 'switch_power';
});
wx.config(${jsConfig});
wx.ready(function() {
});
wx.error(function(res) {
	modalAlert("网络出现问题，请稍后重试", null);
});
var deviceType = '';
var batteryisnotinit=true;
var air_conditioningisnotinit=true;
var switch_powerisnotinit=true;
var ac_distributionisnotinit=true;
var dc_distributionisnotinit=true;
var transformerisnotinit=true;
var grounding_lightningisnotinit=true;
var pressure_regulatorinit=true;
var ac_lineinit=true;
var monitoringisnotinit=true;
function setDeviceType(obj) {
	deviceType = obj;
	if(deviceType == 'battery') {
		tempDeviceType = 'battery';
		if(batteryisnotinit){
			//webUploaderSelectPhoto("#galleryAddBtn1", '${ctx}');
			batteryisnotinit=false;
		}
		document.getElementById("switch_power").style.display = "none";
		document.getElementById("air_conditioning").style.display = "none";
		document.getElementById("battery").style.display = "";
		document.getElementById("ac_distribution").style.display = "none";
		document.getElementById("dc_distribution").style.display = "none";
		document.getElementById("transformer").style.display = "none";
		document.getElementById("grounding_lightning").style.display = "none";
		document.getElementById("ac_line").style.display = "none";
		document.getElementById("pressure_regulator").style.display = "none";
		document.getElementById("monitoring").style.display = "none";
	} else if(deviceType == 'air_conditioning') {
		tempDeviceType = 'air_conditioning';
		if(air_conditioningisnotinit){
			//webUploaderSelectPhoto("#galleryAddBtn2", '${ctx}');
			air_conditioningisnotinit=false;
		}
		document.getElementById("switch_power").style.display = "none";
		document.getElementById("battery").style.display = "none";
		document.getElementById("air_conditioning").style.display = "";
		document.getElementById("ac_distribution").style.display = "none";
		document.getElementById("dc_distribution").style.display = "none";
		document.getElementById("transformer").style.display = "none";
		document.getElementById("grounding_lightning").style.display = "none";
		document.getElementById("ac_line").style.display = "none";
		document.getElementById("pressure_regulator").style.display = "none";
		document.getElementById("monitoring").style.display = "none";
	} else if(deviceType == 'switch_power') {
		tempDeviceType = 'switch_power';
		if(switch_powerisnotinit){
			//webUploaderSelectPhoto("#galleryAddBtn", '${ctx}');
			switch_powerisnotinit=false;
		}
		document.getElementById("switch_power").style.display = "";
		document.getElementById("battery").style.display = "none";
		document.getElementById("air_conditioning").style.display = "none";
		document.getElementById("ac_distribution").style.display = "none";
		document.getElementById("dc_distribution").style.display = "none";
		document.getElementById("transformer").style.display = "none";
		document.getElementById("grounding_lightning").style.display = "none";
		document.getElementById("ac_line").style.display = "none";
		document.getElementById("pressure_regulator").style.display = "none";
		document.getElementById("monitoring").style.display = "none";
	}else if(deviceType == 'ac_distribution') {
		tempDeviceType = 'ac_distribution';
		if(ac_distributionisnotinit){
			//webUploaderSelectPhoto("#galleryAddBtn", '${ctx}');
			ac_distributionisnotinit=false;
		}
		document.getElementById("ac_distribution").style.display = "";
		document.getElementById("switch_power").style.display = "none";
		document.getElementById("battery").style.display = "none";
		document.getElementById("air_conditioning").style.display = "none";
		document.getElementById("dc_distribution").style.display = "none";
		document.getElementById("transformer").style.display = "none";
		document.getElementById("grounding_lightning").style.display = "none";
		document.getElementById("ac_line").style.display = "none";
		document.getElementById("pressure_regulator").style.display = "none";
		document.getElementById("monitoring").style.display = "none";
	}else if(deviceType == 'dc_distribution') {
		tempDeviceType = 'dc_distribution';
		if(dc_distributionisnotinit){
			//webUploaderSelectPhoto("#galleryAddBtn", '${ctx}');
			dc_distributionisnotinit=false;
		}
		document.getElementById("dc_distribution").style.display = "";
		document.getElementById("ac_distribution").style.display = "none";
		document.getElementById("switch_power").style.display = "none";
		document.getElementById("battery").style.display = "none";
		document.getElementById("air_conditioning").style.display = "none";
		document.getElementById("transformer").style.display = "none";
		document.getElementById("grounding_lightning").style.display = "none";
		document.getElementById("ac_line").style.display = "none";
		document.getElementById("pressure_regulator").style.display = "none";
		document.getElementById("monitoring").style.display = "none";
	}else if(deviceType == 'transformer') {
		tempDeviceType = 'transformer';
		if(transformerisnotinit){
			//webUploaderSelectPhoto("#galleryAddBtn", '${ctx}');
			transformerisnotinit=false;
		}
		document.getElementById("transformer").style.display = "";
		document.getElementById("dc_distribution").style.display = "none";
		document.getElementById("ac_distribution").style.display = "none";
		document.getElementById("switch_power").style.display = "none";
		document.getElementById("battery").style.display = "none";
		document.getElementById("air_conditioning").style.display = "none";
		document.getElementById("grounding_lightning").style.display = "none";
		document.getElementById("ac_line").style.display = "none";
		document.getElementById("pressure_regulator").style.display = "none";
		document.getElementById("monitoring").style.display = "none";
	}else if(deviceType == 'grounding_lightning') {
		tempDeviceType = 'grounding_lightning';
		if(grounding_lightningisnotinit){
			//webUploaderSelectPhoto("#galleryAddBtn", '${ctx}');
			grounding_lightningisnotinit=false;
		}
		document.getElementById("grounding_lightning").style.display = "";
		document.getElementById("transformer").style.display = "none";
		document.getElementById("dc_distribution").style.display = "none";
		document.getElementById("ac_distribution").style.display = "none";
		document.getElementById("switch_power").style.display = "none";
		document.getElementById("battery").style.display = "none";
		document.getElementById("air_conditioning").style.display = "none";
		document.getElementById("ac_line").style.display = "none";
		document.getElementById("pressure_regulator").style.display = "none";
		document.getElementById("monitoring").style.display = "none";
	}else if(deviceType == 'pressure_regulator') {
		tempDeviceType = 'pressure_regulator';
		if(pressure_regulatorinit){
			//webUploaderSelectPhoto("#galleryAddBtn", '${ctx}');
			pressure_regulatorinit=false;
		}
		document.getElementById("pressure_regulator").style.display = "";
		document.getElementById("grounding_lightning").style.display = "none";
		document.getElementById("transformer").style.display = "none";
		document.getElementById("dc_distribution").style.display = "none";
		document.getElementById("ac_distribution").style.display = "none";
		document.getElementById("switch_power").style.display = "none";
		document.getElementById("battery").style.display = "none";
		document.getElementById("air_conditioning").style.display = "none";
		document.getElementById("ac_line").style.display = "none";
		document.getElementById("monitoring").style.display = "none";
	}else if(deviceType == 'ac_line') {
		tempDeviceType = 'ac_line';
		if(ac_lineinit){
			//webUploaderSelectPhoto("#galleryAddBtn", '${ctx}');
			ac_lineinit=false;
		}
		document.getElementById("ac_line").style.display = "";
		document.getElementById("pressure_regulator").style.display = "none";
		document.getElementById("grounding_lightning").style.display = "none";
		document.getElementById("transformer").style.display = "none";
		document.getElementById("dc_distribution").style.display = "none";
		document.getElementById("ac_distribution").style.display = "none";
		document.getElementById("switch_power").style.display = "none";
		document.getElementById("battery").style.display = "none";
		document.getElementById("air_conditioning").style.display = "none";
		document.getElementById("monitoring").style.display = "none";
	}else if(deviceType == 'monitoring') {
		tempDeviceType = 'monitoring';
		if(monitoringisnotinit){
			//webUploaderSelectPhoto("#galleryAddBtn", '${ctx}');
			monitoringisnotinit=false;
		}
		document.getElementById("monitoring").style.display = "";
		document.getElementById("ac_line").style.display = "none";
		document.getElementById("pressure_regulator").style.display = "none";
		document.getElementById("grounding_lightning").style.display = "none";
		document.getElementById("transformer").style.display = "none";
		document.getElementById("dc_distribution").style.display = "none";
		document.getElementById("ac_distribution").style.display = "none";
		document.getElementById("switch_power").style.display = "none";
		document.getElementById("battery").style.display = "none";
		document.getElementById("air_conditioning").style.display = "none";
	}else{
		$('#device_type1').val("");
		$('#device_type1').find('option[value='+tempDeviceType+']').attr('selected', true);
		modalAlert("功能开发中", null);
	}
}
function addDevice(deviceStatusIsTrue) {
	//用store传照片saveBase64ImgData(function(){});
	$.when($('#deviceForm').validator('isFormValid')).then(function(result) {
		
		$("#deviceStatus").val(deviceStatusIsTrue);
		var deviceForm = document.getElementById("deviceForm");
		if(result){
			deviceForm.submit();
		}
		
	});
	
}
</script>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div onclick="toPageUrl('${ctx}${toPageUrl}')">
			<span class="am-topbar-brand am-icon-angle-left" ></span>
			<div class="am-topbar-brand  app-toolbar">新增设备</div>
		</div>
	</header>
	<form id="deviceForm" action="${ctx}/devices/saveDeviceInfo" class="am-margin-bottom-lg" method="post" class="am-form" id="form-with-tooltip" data-am-validator>
		<input type="text" id="barCode" name="barCode" hidden="true"  value="${baseDevice.deviceId}"/>
		<input type="text" id="deviceStatus" name="deviceStatus" hidden="true"  value=""/>
		<div style="background: #fff">
			<div class="am-g">
				<table class="am-table"> 
					<tbody>
						<tr>
							<td style="vertical-align: middle;border-top:none;width:100px;height: 35px">名称：</td>
							<td style="vertical-align: middle;border-top:none;">
								<czxk:select dictTypeId="device_type" height="200" id="device_type1" value="switch_power" name="deviceType" onchange="setDeviceType(this.value);"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div id="switch_power">
				<div class="am-g">
					<table class="am-table"> 
						<tbody>
							<tr>
								<td style="vertical-align: middle;width:100px;">品牌：</td>
								<td style="vertical-align: middle;">
									<czxk:select dictTypeId="switch_power_brand" height="200" id="brand" value="" name="brand" onchange='setDictOptions(this.value,"model","");'  />
								</td>
							</tr>
							<tr>
								<td style="vertical-align: middle;">型号：</td>
								<td style="vertical-align: middle;">
									<select data-am-selected="{searchBox: 1,maxHeight: 200}" id="model" name="model">
										<option value="" selected = "selected">请选择</option>
								  	</select>
								</td>
							</tr>
							<!-- 开关电源 -->
							<tr>
								<td style="vertical-align: middle;">模块数：</td>
								<td style="vertical-align: middle;">
									<input type="number" id="powerModNum" name="powerModNum" class="am-form-field" placeholder="请输入数字"/>
								</td>
							</tr>
							<tr>
								<td style="vertical-align: middle;" colspan="2">
									<p>外观照片：</p>
									<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
										<li id="galleryAddBtn" onclick="takePhoto(this,'deviceImgs');" data-weg>
										    <div onclick="void(0)">
										     <button type="button" class="app-add-photo"></button>
										    </div>
										</li>
									</ul>
									<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" size="100"/>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div id="battery" style="display: none">
				<div class="am-g">
				<table class="am-table"> 
					<tbody>
						<tr>
							<td style="vertical-align: middle;width:100px;">品牌：</td>
							<td style="vertical-align: middle;">
								<czxk:select dictTypeId="battery_brand" height="200" value="双登" id="batteryBrand" name="batteryBrand" />
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;">型号：</td>
							<td style="vertical-align: middle;">
								<czxk:select dictTypeId="battery_type" height="200" value="GFM-100" id="batteryModel" name="batteryModel" />
							</td>
						</tr>
						<!-- 蓄电池 -->
						<tr>
							<td style="vertical-align: middle;">单体电压：</td>
							<td style="vertical-align: middle;">
								<czxk:select dictTypeId="battery_voltage"  value="2" id="batteryVoltage" name="batteryVoltage"/>
					 		</td>
					 	</tr>
						<tr>
							<td style="vertical-align: middle;">组数：</td>
							<td style="vertical-align: middle;">
								<czxk:select dictTypeId="battery_group_num" value="${isIncludeBattery }" id="groupNum" name="groupNum" />
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;">生产日期：</td>
							<td style="vertical-align: middle;">
								<input id="enterNetDate" type="text" name="enterNetDate"  class="am-form-field"   placeholder="请选择生产日期"  readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;">安装方式：</td>
							<td style="vertical-align: middle;">
							<czxk:select dictTypeId="battery_installed"  value="架式-立式" id="installationStyle" name="installationStyle" />
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;" colspan="2">
								<p>外观照片：</p>
								<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
									<li id="galleryAddBtn1" onclick="takePhoto(this,'deviceImgs');" data-weg>
									    <div onclick="void(0)">
									     <button type="button" class="app-add-photo"></button>
									    </div>
									</li>
								</ul>
								<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" size="100"/>
							</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			<div id="air_conditioning" style="display: none">
				<div class="am-g">
				<table class="am-table"> 
					<tbody>
						<tr>
							<td style="vertical-align: middle;width:100px;">品牌：</td>
							<td style="vertical-align: middle;">
								<czxk:select dictTypeId="air_conditioning" height="200" value="大金" name="airBrand" />
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;">型号：</td>
							<td style="vertical-align: middle;">
								<input type="text" class="am-form-field" name="airModel" >
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;">生产日期：</td>
							<td style="vertical-align: middle;">
								<input id="createTime" type="text" name="createTime"  class="am-form-field"   placeholder="请选择生产日期"  readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;" colspan="2">
								<p>外观照片：</p>
								<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
									<li id="galleryAddBtn2" onclick="takePhoto(this,'deviceImgs');" data-weg>
									    <div onclick="void(0)">
									     <button type="button" class="app-add-photo"></button>
									    </div>
									</li>
								</ul>
								<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" size="100"/>
							</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			<div id="ac_distribution" style="display: none">
				<div class="am-g">
				<table class="am-table"> 
					<tbody>
						<!-- 交流配电箱 -->
						<tr>
							<td style="vertical-align: middle;width:100px;">总容量：</td>
							<td style="vertical-align: middle;">
								<div class="am-input-group">
									<input type="number" class="am-form-field" name="acCapacity" placeholder="请输入总容量" pattern="^[0-9]+\.?[0-9]{0,2}$" required>
									<span class="am-input-group-label">A</span>
								</div>
					 		</td>
					 	</tr>
						<tr>
							<td style="vertical-align: middle;">生产厂商：</td>
							<td style="vertical-align: middle;">
								<input id="acManufacturer" type="text" name="acManufacturer"  class="am-form-field" placeholder="请输入生产厂商" required/>
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;">是否预留接口：</td>
							<td style="vertical-align: middle;">
								<czxk:select dictTypeId="enterInterface" height="100" value="无" name="haveGeneratorInterface" id="haveGeneratorInterface"/>
							</td>
						</tr>
						<tr id="length_and_width" style="display:none;">
							<td style="vertical-align: middle;">交流引入长度：</td>
							<td>
								<div class="am-input-group">
									<input id="ac_line_length" type="number" name="acLineLength"  class="am-form-field" placeholder="请输入交流引入长度"/>
									<span class="am-input-group-label">米</span>
								</div>
								
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;" colspan="2">
								<p>外观照片：</p>
								<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
									<li id="galleryAddBtn1" onclick="takePhoto(this,'deviceImgs');" data-weg>
									    <div onclick="void(0)">
									     <button type="button" class="app-add-photo"></button>
									    </div>
									</li>
								</ul>
								<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" size="100"/>
							</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			<div id="dc_distribution" style="display: none">
				<div class="am-g">
				<table class="am-table"> 
					<tbody>
						<!-- 直流配电箱 -->
						<tr>
							<td style="vertical-align: middle;width:100px;">品牌：</td>
							<td style="vertical-align: middle;">
								<div class="am-input-group">
									<input type="text" class="am-form-field" id="boxBrand" name="boxBrand" placeholder="请输入品牌简称" required>
								</div>
					 		</td>
					 	</tr>
					 	<tr>
							<td style="vertical-align: middle;width:100px;">总容量：</td>
							<td style="vertical-align: middle;">
								<input type="number" class="am-form-field" id="dcCapacity" name="dcCapacity" placeholder="请输入总容量" pattern="^[0-9]+\.?[0-9]{0,2}$" required>
					 		</td>
					 	</tr>
						<tr>
							<td style="vertical-align: middle;">熔丝已经使用数量：</td>
							<td style="vertical-align: middle;">
								<div class="am-input-group">
									<input id="fuseUsedNum" type="number" name="fuseUsedNum"  class="am-form-field" placeholder="请输入使用数量" pattern="^[0-9]+\.?[0-9]{0,2}$" required/>
									<span class="am-input-group-label">个</span>
								</div>
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;">未使用熔丝数量：</td>
							<td style="vertical-align: middle;">
								<div class="am-input-group">
									<input id="fuseNotUsedNum" type="number" name="fuseNotUsedNum"  class="am-form-field" placeholder="请输入未使用数量"/>
									<span class="am-input-group-label">个</span>
								</div>
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;" colspan="2">
								<p>外观照片：</p>
								<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
									<li id="galleryAddBtn1" onclick="takePhoto(this,'deviceImgs');" data-weg>
									    <div onclick="void(0)">
									     <button type="button" class="app-add-photo"></button>
									    </div>
									</li>
								</ul>
								<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" size="100"/>
							</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			<div id="transformer" style="display: none">
				<div class="am-g">
				<table class="am-table"> 
					<tbody>
						<!-- 变压器 -->
						<tr>
							<td style="vertical-align: middle;width:100px;">类型：</td>
							<td style="vertical-align: middle;">
								<czxk:select dictTypeId="transformerType" value="干式" name="type" />
					 		</td>
					 	</tr>
					 	<tr>
							<td style="vertical-align: middle;width:100px;">额定功率：</td>
							<td style="vertical-align: middle;">
								<input type="number" class="am-form-field" name="ratedPower" placeholder="请输入额定功率" pattern="^[0-9]+\.?[0-9]{0,2}$" required>
					 		</td>
					 	</tr>
						<tr>
							<td style="vertical-align: middle;">生产厂商：</td>
							<td style="vertical-align: middle;">
								<input id="manufacturer" type="text" name="manufacturer"  class="am-form-field" placeholder="请输入生产厂商" required/>
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;" colspan="2">
								<p>外观照片：</p>
								<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
									<li id="galleryAddBtn1" onclick="takePhoto(this,'deviceImgs');" data-weg>
									    <div onclick="void(0)">
									     <button type="button" class="app-add-photo"></button>
									    </div>
									</li>
								</ul>
								<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" size="100"/>
							</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			<div id="grounding_lightning" style="display: none">
				<div class="am-g">
				<table class="am-table"> 
					<tbody>
						<!-- 防雷接地设备 -->
						<tr>
							<td style="vertical-align: middle;width:100px;">设备型号：</td>
							<td style="vertical-align: middle;">
								<czxk:select dictTypeId="lightningType" value="60KA" name="gflModel" />
					 		</td>
					 	</tr>
						<tr>
							<td style="vertical-align: middle;">生产厂商：</td>
							<td style="vertical-align: middle;">
								<input id="supplier" type="text" name="supplier"  class="am-form-field" placeholder="请输入生产厂商"/>
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;" colspan="2">
								<p>外观照片：</p>
								<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
									<li id="galleryAddBtn1" onclick="takePhoto(this,'deviceImgs');" data-weg>
									    <div onclick="void(0)">
									     <button type="button" class="app-add-photo"></button>
									    </div>
									</li>
								</ul>
								<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" size="100"/>
							</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			
			<div id="pressure_regulator" style="display: none">
				<div class="am-g">
				<table class="am-table"> 
					<tbody>
						<!-- 调压器 -->
						<tr>
							<td style="vertical-align: middle;width:100px;">额定功率：</td>
							<td style="vertical-align: middle;">
								<input type="number" class="am-form-field" name="prRatedPower" placeholder="请输入额定功率" pattern="^[0-9]+\.?[0-9]{0,2}$" required>
					 		</td>
					 	</tr>
						<tr>
							<td style="vertical-align: middle;">生产厂商：</td>
							<td style="vertical-align: middle;">
								<input id="prManufacturer" type="text" name="prManufacturer"  class="am-form-field" placeholder="请输入生产厂商" required/>
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;" colspan="2">
								<p>外观照片：</p>
								<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
									<li id="galleryAddBtn1" onclick="takePhoto(this,'deviceImgs');" data-weg>
									    <div onclick="void(0)">
									     <button type="button" class="app-add-photo"></button>
									    </div>
									</li>
								</ul>
								<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" size="100"/>
							</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			<div id="ac_line" style="display: none">
				<div class="am-g">
				<table class="am-table"> 
					<tbody>
						<!-- 交流引入 -->
						<tr>
							<td style="vertical-align: middle;width:100px;">线缆材质：</td>
							<td style="vertical-align: middle;">
								<czxk:select dictTypeId="line_material" value="铜" name="lineMaterial" />
					 		</td>
					 	</tr>
						<tr>
							<td style="vertical-align: middle;">规格型号：</td>
							<td style="vertical-align: middle;">
								<czxk:select dictTypeId="line_model" value="3*10+1*6" name="lineModel" />
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;width:100px;">架空距离：</td>
							<td style="vertical-align: middle;">
								<div class="am-input-group">
									<input type="number" class="am-form-field" name="overheadDistance" placeholder="请输入架空距离">
									<span class="am-input-group-label">米</span>
								</div>
					 		</td>
					 	</tr>
					 	<tr>
							<td style="vertical-align: middle;width:100px;">墙挂距离：</td>
							<td style="vertical-align: middle;">
								<div class="am-input-group">
									<input type="number" class="am-form-field" name="wallDistance" placeholder="请输入墙挂距离">
									<span class="am-input-group-label">米</span>
								</div>
					 		</td>
					 	</tr>
					 	<tr>
							<td style="vertical-align: middle;width:100px;">地埋距离：</td>
							<td style="vertical-align: middle;">
								<div class="am-input-group">
									<input type="number" class="am-form-field" name="groundDistance" placeholder="请输入地埋距离">
									<span class="am-input-group-label">米</span>
								</div>
					 		</td>
					 	</tr>
					 	<tr>
							<td style="vertical-align: middle;width:100px;">交流引入长度：</td>
							<td style="vertical-align: middle;">
								<div class="am-input-group">
									<input type="number" class="am-form-field" name="lineLength" placeholder="请输入交流引入长度">
									<span class="am-input-group-label">米</span>
								</div>
					 		</td>
					 	</tr>
						<tr>
							<td style="vertical-align: middle;" colspan="2">
								<p>外观照片：</p>
								<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
									<li id="galleryAddBtn1" onclick="takePhoto(this,'deviceImgs');" data-weg>
									    <div onclick="void(0)">
									     <button type="button" class="app-add-photo"></button>
									    </div>
									</li>
								</ul>
								<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" size="100"/>
							</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			
			<div id="monitoring" style="display: none">
				<div class="am-g">
				<table class="am-table"> 
					<tbody>
						<!-- 监控设备 -->
						<tr>
							<td style="vertical-align: middle;width:100px;">型号：</td>
							<td style="vertical-align: middle;">
								<input type="text" class="am-form-field" name="mModel" placeholder="请输入型号">
					 		</td>
					 	</tr>
						<tr>
							<td style="vertical-align: middle;">生产厂商：</td>
							<td style="vertical-align: middle;">
								<czxk:select dictTypeId="monitoring_supplier" value="天河" name="mSupplier" />
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;" colspan="2">
								<p>外观照片：</p>
								<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
									<li id="galleryAddBtn1" onclick="takePhoto(this,'deviceImgs');" data-weg>
									    <div onclick="void(0)">
									     <button type="button" class="app-add-photo"></button>
									    </div>
									</li>
								</ul>
								<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" size="100"/>
							</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
		</div>
		<div class="app-div-btn-tool" style="margin-top: 18px">
				<div class="am-g">
	  				<div class="am-u-sm-6">
	  					<button  onclick="addDevice(true);" type="button" class="am-btn am-btn-success am-radius am-btn-block">
							<i class="am-icon-check"></i><strong>正常</strong>
						</button>
	  				</div>
	  				<div class="am-u-sm-6">
	  					<button  onclick="addDevice(false);" type="button" class="am-btn am-btn-danger am-radius am-btn-block">
							<i class="am-icon-warning"></i><strong>异常</strong>
						</button>
	  				</div>
				</div>
		</div>
	</form>
</body>
<script>
!function(t) {
	$("ul[data-am-widget='gallery']").one("click",function(){//点击预览图片关闭预览
		$(".am-pureview-slider").on("click",function(){
			$(".am-icon-chevron-left[data-am-close='pureview']").triggerHandler("click");
		});
	});
	//showImagesH5();
	initMobiscroll("#enterNetDate");
	initMobiscroll("#createTime");
}(window.jQuery || window.Zepto);

$(function(){
	$('#haveGeneratorInterface').change(function() {
        if (this.value == '外引发电接口') {
            $('#length_and_width').show();
        } else {
            $('#length_and_width').hide();
        }
    });
	
});
</script>
</html>
