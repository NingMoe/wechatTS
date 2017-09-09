<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>设备详情</title>
<%@include file="../commons/head.jsp" %>
<%@include file="../commons/mobiscrollDatePlugin.jsp" %>
<script type="text/javascript">
	var jsconfig=${jsConfig};
	wx.config(jsconfig);
	wx.ready(function() {
	});
	wx.error(function(res) {
		modalAlert("网络出现问题，请稍后重试", null);
	});
	function saveDevice(deviceStatusIsTrue){
		$("#deviceStatus").val(deviceStatusIsTrue);
		//用store传照片saveBase64ImgData(function(){});
		$("#deviceForm").attr("action","${ctx}/devices/updateDeviceInfo");
		$("#deviceForm").submit();
	}
	
	function saveDeviceOnly(url){
		//用store传照片saveBase64ImgData(function(){});
		$("#deviceForm").attr("action",url);
		$("#deviceForm").submit();
	}
	
	function replaceBarCode(oldDeviceId, deviceType) {
		//扫描二维码
		wx.scanQRCode({
			needResult : 1,
			scanType: ["qrCode","barCode"],
			success : function(res) {
				var indexss = res.resultStr.indexOf(",") + 1;
				var deviceId = res.resultStr.substr(indexss);
				if (deviceId.length==13 || deviceId.length==38 || deviceId.length==42) {
					showLoading();
					setTimeout(function(){
						checkTheBarCodeIfInSystem(deviceId, oldDeviceId, deviceType);
			        },1500);
				} else {
					closeLoading();
					modalAlert("此条码是无效条码", null);
				}
			},
			cancel : function(res) {
			},
			complete : function(res) {
			},
			fail : function(res) {
			}
		});
	};
	
	function checkTheBarCodeIfInSystem(deviceId, oldDeviceId, deviceType){//查询是否条码已经存在
		$.post("../devices/checkBarCodeExists.json",{
			barCode:deviceId
		},function(json){
			if(json.succ){
				modalAlert(json.msg, null);
				closeLoading();
			}else{
				replaceBarCode1(deviceId,oldDeviceId, deviceType);
			}
		},"json");
	}
	function replaceBarCode1(deviceId, oldDeviceId, deviceType) {
		$.post("../devices/replaceBarCode.json",{
			barCode:deviceId,
			oldBarCode:oldDeviceId,
			deviceType:deviceType
		},function(json){
			closeLoading();
			$("#replaceResult").html(json.msg);
			$("#finish-confirm").modal(
			{
				relatedTarget : this,
				onConfirm : function(options) {
					if(json.succ){
						viewDeviceDetail(deviceId,true);
					}else{
					}
				},
				onCancel : function() {
				}
			});
			
		},"json");
	}
	function viewDeviceDetail(deviceId,isScan) {
		window.location.href="../devices/getDeviceInfo?deviceId="+deviceId+"&isScan="+isScan;
	}
</script>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div onclick="toPageUrl('${ctx}${toPageUrl}')">
			<span class="am-topbar-brand am-icon-angle-left"></span>
			<div class="am-topbar-brand  app-toolbar">设备详情</div>
		</div>
		<div style="float: right;">
			<div class="am-topbar-brand  app-toolbar">
				<button id="confirm" onclick="replaceBarCode('${baseDevice.deviceId}','${baseDevice.deviceType}');" class="am-btn am-btn-sm am-btn-success am-radius">更换条码</button>
			</div>
		</div>
	</header>
	
	<form id="deviceForm" action="" class="am-margin-bottom-lg" method="post">
	<input type="hidden" name="barCode" value="${baseDevice.deviceId}">
	<input type="hidden" name="deviceType" value="${baseDevice.deviceType}">
	<input type="hidden" id="deviceStatus" name="deviceStatus" value=""/>
	<c:if test="${baseDevice.deviceType eq 'switch_power'}">
		<table class="am-table app-tb"> 
			<tbody>
				<tr>
					<td style="vertical-align: middle;width: 100px;height:45px;border-top: none">设备类型：</td>
					<td style="vertical-align: middle;height:45px;border-top: none">
					  	${baseDevice.deviceTypeName}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;" colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
							<li id="${item.photoId}">
							    <div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
							     	<div style="height:72px;margin-bottom:2px;overflow:hidden;">
							       		<img src="${item.thumbLocation}" <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if>  alt="" data-rel="${item.fileLocation}"/>
							       	</div>
							       	<h3 class="am-gallery-title am-fr" onclick="deletePhotoInServer('${ctx}','${item.photoId}')">删除  <i class="am-icon-trash-o am-icon-sm" 
							       		style="color:#dd514c;opacity:100;margin-top: 6px;"></i></h3>
							    </div>
							</li>
							</c:forEach>
							<li id="galleryAddBtn" onclick="takePhoto(this,'deviceImgs');" data-weg>
							    <div onclick="void(0)">
							     <button type="button" class="app-add-photo"></button>
							    </div>
							</li>
						</ul>
						<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" size="100"/>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">品牌：</td>
					<td style="vertical-align: middle;">
						<czxk:select dictTypeId="switch_power_brand" height="200" id="brand" value="${baseDevice.brand }" name="brand" onchange='setDictOptions(this.value,"model","${baseDevice.model }");'  />
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">型号：</td>
					<td style="vertical-align: middle;">
						<input type="hidden" id="modelHidden" value="${baseDevice.model }" />
						<select data-am-selected="{searchBox: 1,maxHeight: 200}" id="model" name="model">
							<option value="" selected = "selected">请选择</option>
					  	</select>
					</td>
				</tr>
				<!-- 开关电源 -->
				<tr>
					<td style="vertical-align: middle;">模块数：</td>
					<td style="vertical-align: middle;">
					  	<input type="number" id="powerModNum" name="powerModNum" class="am-form-field"  placeholder="请输入数字" value="${baseDevice.powerModNum}"/>
					</td>
				</tr>
			</tbody>
		</table>
	</c:if>
	<c:if test="${baseDevice.deviceType eq 'battery'}">
		<table class="am-table app-tb"> 
			<tbody>
				<tr>
					<td style="vertical-align: middle;width: 100px;height:45px;border-top: none">设备类型：</td>
					<td style="vertical-align: middle;height:45px;border-top: none">
					  	${baseDevice.deviceTypeName}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;" colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
							    <li id="${item.photoId}">
							    <div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
							     	<div style="height:72px;margin-bottom:2px;overflow:hidden;">
							       		<img src="${item.thumbLocation}"  <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if> alt="" data-rel="${item.fileLocation}"/>
							       	</div>
							       	<h3 class="am-gallery-title am-fr" onclick="deletePhotoInServer('${ctx}','${item.photoId}')">删除  <i class="am-icon-trash-o am-icon-sm" 
							       		style="color:#dd514c;opacity:100;margin-top: 6px;"></i></h3>
							    </div>
							</li>
							</c:forEach>
							<li id="galleryAddBtn1" onclick="takePhoto(this,'deviceImgs');" data-weg>
							    <div onclick="void(0)">
							     <button type="button" class="app-add-photo"></button>
							    </div>
							</li>
						</ul>
						<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" size="100"/>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">品牌：</td>
					<td style="vertical-align: middle;">
						<czxk:select dictTypeId="battery_brand" height="200" value="${baseDevice.batteryBrand }" id="batteryBrand" name="batteryBrand" />
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">型号：</td>
					<td style="vertical-align: middle;">
						<czxk:select dictTypeId="battery_type" height="200" value="${baseDevice.batteryModel }" id="batteryModel" name="batteryModel" />
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">单体电压：</td>
					<td style="vertical-align: middle;">
						<czxk:select dictTypeId="battery_voltage"  value="${baseDevice.batteryVoltage }" id="batteryVoltage" name="batteryVoltage"/>
			 		</td>
			 	</tr>
				<tr>
					<td style="vertical-align: middle;">组数：</td>
					<td style="vertical-align: middle;">
						<czxk:select dictTypeId="battery_group_num" value="${baseDevice.groupNum }" id="groupNum" name="groupNum" />
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">生产日期：</td>
					<td style="vertical-align: middle;">
						<input id="enterNetDate" type="text" name="enterNetDate" class="am-form-field" placeholder="请选择生产日期" value="<fmt:formatDate value="${baseDevice.enterNetDate}" pattern="yyyy-MM-dd" />"  readonly/>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">安装方式：</td>
					<td style="vertical-align: middle;">
						<czxk:select dictTypeId="battery_installed"  value="${baseDevice.installationStyle}" id="installationStyle" name="installationStyle"/>
			 		</td>
			 	</tr>
			</tbody>
		</table>
	</c:if>
	<c:if test="${baseDevice.deviceType eq 'air_conditioning'}">
		<table class="am-table app-tb"> 
			<tbody>
				<tr>
					<td style="vertical-align: middle;width: 100px;height:45px;border-top: none">设备类型：</td>
					<td style="vertical-align: middle;height:45px;border-top: none">
					  	${baseDevice.deviceTypeName}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;" colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
							    <li id="${item.photoId}">
							    <div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
							     	<div style="height:72px;margin-bottom:2px;overflow:hidden;">
							       		<img src="${item.thumbLocation}" <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if>  alt="" data-rel="${item.fileLocation}"/>
							       	</div>
							       	<h3 class="am-gallery-title am-fr" onclick="deletePhotoInServer('${ctx}','${item.photoId}')">删除  <i class="am-icon-trash-o am-icon-sm" style="color:#dd514c;opacity:100;margin-top: 6px;"></i></h3>
							    </div>
							</li>
							</c:forEach>
							<li id="galleryAddBtn2" onclick="takePhoto(this,'deviceImgs');" data-weg>
							    <div onclick="void(0)">
							     <button type="button" class="app-add-photo"></button>
							    </div>
							</li>
						</ul>
						<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" size="100"/>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">品牌：</td>
					<td style="vertical-align: middle;">
						<czxk:select dictTypeId="air_conditioning" height="200" value="${baseDevice.airBrand }" name="airBrand" />
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">型号：</td>
					<td style="vertical-align: middle;">
						<input type="text" class="am-form-field" value="${baseDevice.airModel }" name="airModel" >
						<%-- <czxk:select dictTypeId="air_conditioning_model" height="200" value="${baseDevice.airModel }" name="airModel" /> --%>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">生产日期：</td>
					<td style="vertical-align: middle;">
						<input id="createTime" type="text" name="createTime" class="am-form-field" placeholder="请选择生产日期" value="<fmt:formatDate value="${baseDevice.createTime}" pattern="yyyy-MM-dd" />"  readonly/>
					</td>
				</tr>
			</tbody>
		</table>
	</c:if>
	<c:if test="${baseDevice.deviceType eq 'ac_distribution'}">
		<table class="am-table app-tb"> 
			<tbody>
				<tr>
					<td style="width: 100px;border-top: none">设备类型：</td>
					<td style="border-top: none">
					  	${baseDevice.deviceTypeName}
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
							    <li id="${item.photoId}">
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
				<tr>
					<td>总容量：</td>
					<td>
						<div class="am-input-group">
							<input type="text" class="am-form-field" value="${baseDevice.acCapacity}" name="acCapacity" >
							<span class="am-input-group-label">A</span>
						</div>
					</td>
				</tr>
				<tr>
					<td>生产厂商：</td>
					<td>
						<input type="text" class="am-form-field" value="${baseDevice.acManufacturer}" name="acManufacturer" >
					</td>
				</tr>
				<tr>
					<td>是否预留移动油机输入接口：</td>
					<td>
						<czxk:select dictTypeId="enterInterface" value="${baseDevice.haveGeneratorInterface}" name="haveGeneratorInterface"/>
					</td>
				</tr>
			</tbody>
		</table>
	</c:if>
	<c:if test="${baseDevice.deviceType eq 'dc_distribution'}">
		<table class="am-table app-tb"> 
			<tbody>
				<tr>
					<td style="width: 100px;border-top: none">设备类型：</td>
					<td style="border-top: none">
					  	${baseDevice.deviceTypeName}
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
							    <li id="${item.photoId}">
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
				<tr>
					<td>品牌：</td>
					<td>
					 	<input type="text" class="am-form-field" value="${baseDevice.boxBrand}" name="boxBrand" >
					</td>
				</tr>
				<tr>
					<td>总容量：</td>
					<td>
					 	<input type="text" class="am-form-field" value="${baseDevice.dcCapacity}" name="dcCapacity" >
					</td>
				</tr>
				<tr>
					<td>熔丝已经使用数量：</td>
					<td>
						<div class="am-input-group">
							<input type="text" class="am-form-field" value="${baseDevice.fuseUsedNum}" name="fuseUsedNum" >
							<span class="am-input-group-label">个</span>
						</div>
					</td>
				</tr>
				<tr>
					<td>未使用熔丝数量：</td>
					<td>
						<div class="am-input-group">
							<input type="text" class="am-form-field" value="${baseDevice.fuseNotUsedNum}" name="fuseNotUsedNum" >
							<span class="am-input-group-label">个</span>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</c:if>
	<c:if test="${baseDevice.deviceType eq 'transformer'}">
		<table class="am-table app-tb"> 
			<tbody>
				<tr>
					<td style="width: 100px;border-top: none">设备类型：</td>
					<td style="border-top: none">
					  	${baseDevice.deviceTypeName}
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
							    <li id="${item.photoId}">
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
				<tr>
					<td>类型：</td>
					<td>
						<czxk:select dictTypeId="transformerType" value="${baseDevice.type}" name="type" />
					</td>
				</tr>
				<tr>
					<td>额定功率：</td>
					<td>
						<input type="text" class="am-form-field" value="${baseDevice.ratedPower}" name="ratedPower" >
					</td>
				</tr>
				<tr>
					<td>生产厂商：</td>
					<td>
						<input type="text" class="am-form-field" value="${baseDevice.manufacturer}" name="manufacturer" >
					</td>
				</tr>
			</tbody>
		</table>
	</c:if>
	<c:if test="${baseDevice.deviceType eq 'grounding_lightning'}">
		<table class="am-table app-tb"> 
			<tbody>
				<tr>
					<td style="width: 100px;border-top: none">设备类型：</td>
					<td style="border-top: none">
					  	${baseDevice.deviceTypeName}
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
							    <li id="${item.photoId}">
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
				<tr>
					<td>设备型号：</td>
					<td>
						<czxk:select dictTypeId="lightningType" value="${baseDevice.gflModel}" name="gflModel"/>
					</td>
				</tr>
				<tr>
					<td>生产厂商：</td>
					<td>
						<input type="text" class="am-form-field" value="${baseDevice.supplier}" name="supplier" >
					</td>
				</tr>
			</tbody>
		</table>
	</c:if>
	<c:if test="${baseDevice.deviceType eq 'pressure_regulator'}">
		<table class="am-table app-tb"> 
			<tbody>
				<tr>
					<td style="width: 100px;border-top: none">设备类型：</td>
					<td style="border-top: none">
					  	${baseDevice.deviceTypeName}
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
							    <li id="${item.photoId}">
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
				<tr>
					<td>额定功率：</td>
					<td>
						<input type="number" class="am-form-field" name="prRatedPower" value="${baseDevice.prRatedPower}">
					</td>
				</tr>
				<tr>
					<td>生产厂商：</td>
					<td>
						<input type="text" class="am-form-field" value="${baseDevice.prManufacturer}" name="prManufacturer" >
					</td>
				</tr>
			</tbody>
		</table>
	</c:if>
	<c:if test="${baseDevice.deviceType eq 'ac_line'}">
		<table class="am-table app-tb"> 
			<tbody>
				<tr>
					<td style="width: 100px;border-top: none">设备类型：</td>
					<td style="border-top: none">
					  	${baseDevice.deviceTypeName}
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
							    <li id="${item.photoId}">
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
				<tr>
					<td>线缆材质：</td>
					<td>
						<czxk:select dictTypeId="line_material" value="${baseDevice.lineMaterial}" name="lineMaterial" />
					</td>
				</tr>
				<tr>
					<td>规格型号：</td>
					<td>
						<czxk:select dictTypeId="line_model" value="${baseDevice.lineModel}" name="lineModel" />
					</td>
				</tr>
				<tr>
					<td>架空距离：</td>
					<td>
						<div class="am-input-group">
							<input type="number" class="am-form-field" name="overheadDistance" value="${baseDevice.overheadDistance}">
							<span class="am-input-group-label">米</span>
						</div>
					 </td>
				</tr>
				<tr>
					<td>墙挂距离：</td>
					<td>
						<div class="am-input-group">
							<input type="number" class="am-form-field" name="wallDistance" value="${baseDevice.wallDistance}">
							<span class="am-input-group-label">米</span>
						</div>
					 </td>
				</tr>
				<tr>
					<td>地埋距离：</td>
					<td>
						<div class="am-input-group">
							<input type="number" class="am-form-field" name="groundDistance" value="${baseDevice.groundDistance}">
							<span class="am-input-group-label">米</span>
						</div>
					 </td>
				</tr>
				<tr>
					<td>交流引入长度：</td>
					<td>
						<div class="am-input-group">
							<input type="number" class="am-form-field" name="lineLength" value="${baseDevice.lineLength}">
							<span class="am-input-group-label">米</span>
						</div>
					 </td>
				</tr>
			</tbody>
		</table>
	</c:if>
	<c:if test="${baseDevice.deviceType eq 'monitoring'}">
		<table class="am-table app-tb"> 
			<tbody>
				<tr>
					<td style="width: 100px;border-top: none">设备类型：</td>
					<td style="border-top: none">
					  	${baseDevice.deviceTypeName}
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
							    <li id="${item.photoId}">
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
				<tr>
					<td>型号：</td>
					<td>
						<input type="text" class="am-form-field" value="${baseDevice.mModel}" name="mModel" >
					</td>
				</tr>
				<tr>
					<td>生产厂商：</td>
					<td>
						<czxk:select dictTypeId="monitoring_supplier" value="${baseDevice.mSupplier}" name="mSupplier" />
					</td>
				</tr>
				
			</tbody>
		</table>
	</c:if>
	<div class="app-div-btn-tool">
		<div class="am-g">
			<c:if test="${baseDevice.inStatus eq null}">
	 				<div class="am-u-sm-6">
	 					<button  onclick="saveDevice(true);" type="button" class="am-btn am-btn-success am-radius am-btn-block">
						<i class="am-icon-check"></i><strong>正常</strong>
					</button>
	 				</div>
	 				<div class="am-u-sm-6">
	 					<button  onclick="saveDevice(false);" type="button" class="am-btn am-btn-danger am-radius am-btn-block">
						<i class="am-icon-warning"></i><strong>异常</strong>
					</button>
	 				</div>
	 		</c:if>
	 		<c:if test="${baseDevice.inStatus ne null}">
				<button  onclick="saveDeviceOnly('${ctx}/devices/onlyUpdateDeviceInfo')" type="button" class="am-btn am-btn-success am-radius am-btn-block">
					<i class="am-icon-check"></i><strong>保存</strong>
				</button>
	 		</c:if>
		</div>
	</div>
</form>
<div class="am-modal am-modal-confirm" tabindex="-1" id="finish-confirm">
  <div class="am-modal-dialog">
    <div class="am-modal-bd" id="replaceResult">
      
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
    </div>
  </div>
</div>
</body>
<script type="text/javascript">
!function(t) {
	$("ul[data-am-widget='gallery']").one("click",function(){//点击预览图片关闭预览
		$(".am-pureview-slider").on("click",function(){
			$(".am-icon-chevron-left[data-am-close='pureview']").triggerHandler("click");
		});
	});
	initMobiscroll("#enterNetDate");
	initMobiscroll("#createTime");
	var deviceType =  '${baseDevice.deviceType}';
	if("switch_power" == deviceType) {
		setDictOptions($("#brand").val(),"model",$("#modelHidden").val());
	}
	
	//showImagesH5();
}(window.jQuery || window.Zepto);
</script>
</html>
