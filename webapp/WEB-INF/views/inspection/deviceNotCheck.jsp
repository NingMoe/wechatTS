<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>设备详情</title>
<%@include file="../commons/head.jsp" %>
<script type="text/javascript">
	var jsconfig=${jsConfig};
	wx.config(jsconfig);
	wx.ready(function() {
	});
	wx.error(function(res) {
		modalAlert("网络出现问题，请稍后重试", null);
	});
	var theUrl = "";
	function saveDeviceOnly(url){
		theUrl = url;
		$("#app-modal-confirm-msg").html("设备报备后设备信息将被删除，确定报备？");
		$("#app-modal-confirm").modal(
		{
			relatedTarget : this,
			onConfirm : function() {
				showLoading();
				$("#deviceForm").attr("action",theUrl);
				$("#deviceForm").submit();
			},
			onCancel : function() {
				//do nothing
			}
		});
	}
	function replaceBarCode(oldDeviceId, deviceType, outLose) {
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
						checkTheBarCodeIfInSystem(deviceId, oldDeviceId, deviceType, outLose);
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
	
	function checkTheBarCodeIfInSystem(deviceId, oldDeviceId, deviceType, outLose){//查询是否条码已经存在
		$.post("../devices/checkBarCodeExists.json",{
			barCode:deviceId
		},function(json){
			if(json.succ){
				modalAlert(json.msg, null);
				closeLoading();
			}else{
				replaceBarCode1(deviceId,oldDeviceId, deviceType, outLose);
			}
		},"json");
	}
	function replaceBarCode1(deviceId, oldDeviceId, deviceType, outLose) {
		$.post("../devices/replaceBarCode.json",{
			barCode:deviceId,
			oldBarCode:oldDeviceId,
			deviceType:deviceType,
			outLose:outLose
		},function(json){
			closeLoading();
			$("#replaceResult").html(json.msg);
			$("#finish-confirm").modal(
			{
				relatedTarget : this,
				onConfirm : function(options) {
					if(json.succ){
						viewDeviceDetail(deviceId,false);
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
<style type="text/css">
	td{
		vertical-align: middle;
		height: 45px;
	}
</style>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div onclick="toPageUrl('${ctx}${toPageUrl}')">
			<span class="am-topbar-brand am-icon-angle-left"></span>
			<div class="am-topbar-brand  app-toolbar">设备详情</div>
		</div>
		<div style="float: right;">
			<div class="am-topbar-brand  app-toolbar">
				<button id="confirm" onclick="replaceBarCode('${baseDevice.deviceId}','${baseDevice.deviceType}','${baseDevice.outLose}');" class="am-btn am-btn-sm am-btn-success am-radius">更换条码</button>
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
						<czxk:showSelect dictTypeId="switch_power_brand" value="${baseDevice.brand}"/>
					</td>
				</tr>
				<tr>
					<td>型号：</td>
					<td>
						<czxk:showSelect dictTypeId="${baseDevice.brand}" value="${baseDevice.model}"/>
					</td>
				</tr>
				<tr>
					<td>模块数：</td>
					<td>
						${baseDevice.powerModNum}
					</td>
				</tr>
				<!-- 设备详情 -->
				<c:if test="${baseDevice.inStatus eq null && baseDevice.outLose ne '-1'}">
					<tr>
						<td>设备备注：</td>
						<td>
							<czxk:select dictTypeId="device_out_lose" height="150" id="outLose1" name="outLose"/>
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</c:if>
	<c:if test="${baseDevice.deviceType eq 'battery'}">
		<table class="am-table app-tb"> 
			<tbody>
				<tr>
					<td style="width: 100px;height:45px;border-top: none">设备类型：</td>
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
							       <img src="${item.thumbLocation}"  <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if> alt="" data-rel="${item.fileLocation}"/>
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
						<czxk:showSelect dictTypeId="battery_brand" value="${baseDevice.batteryBrand}"/>
					</td>
				</tr>
				<tr>
					<td>型号：</td>
					<td>
						<czxk:showSelect dictTypeId="battery_type" value="${baseDevice.batteryModel}"/>
					</td>
				</tr>
				<tr>
					<td>单体电压：</td>
					<td>
						<czxk:showSelect dictTypeId="battery_voltage" value="${baseDevice.batteryVoltage}"/>
					</td>
				</tr>
				<tr>
					<td>组数：</td>
					<td>
						<czxk:showSelect dictTypeId="battery_group_num" value="${baseDevice.groupNum}"/>
					</td>
				</tr>
				<tr>
					<td>生产日期：</td>
					<td>
						<fmt:formatDate value="${baseDevice.enterNetDate}" pattern="yyyy-MM-dd" />
					</td>
				</tr>
				<tr>
					<td>安装方式：</td>
					<td>
						<czxk:showSelect dictTypeId="battery_installed"  value="${baseDevice.installationStyle}" />
			 		</td>
			 	</tr>
				<c:if test="${baseDevice.inStatus eq null && baseDevice.outLose ne '-1'}">
					<tr>
						<td>设备备注：</td>
						<td>
							<czxk:select dictTypeId="device_out_lose" height="150" id="outLose2" name="outLose"/>
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</c:if>
	<c:if test="${baseDevice.deviceType eq 'air_conditioning'}">
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
						<czxk:showSelect dictTypeId="air_conditioning" value="${baseDevice.airBrand}"/>
					</td>
				</tr>
				<tr>
					<td>型号：</td>
					<td>
						<input type="text" class="am-form-field" value="${baseDevice.airModel }" name="airModel" >
					</td>
				</tr>
				<tr>
					<td>生产日期：</td>
					<td>
						<fmt:formatDate value="${baseDevice.createTime}" pattern="yyyy-MM-dd" />
					</td>
				</tr>
				<!-- 设备详情 -->
				<c:if test="${baseDevice.inStatus eq null && baseDevice.outLose ne '-1'}">
					<tr>
						<td>设备备注：</td>
						<td>
							<czxk:select dictTypeId="device_out_lose" height="150" id="outLose3" name="outLose"/>
						</td>
					</tr>
				</c:if>
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
							${baseDevice.acCapacity}A
						</div>
					</td>
				</tr>
				<tr>
					<td>生产厂商：</td>
					<td>
						${baseDevice.acManufacturer}
					</td>
				</tr>
				<tr>
					<td>是否预留移动油机输入接口：</td>
					<td>
						<czxk:showSelect dictTypeId="enterInterface" value="${baseDevice.haveGeneratorInterface}"/>
					</td>
				</tr>
				<!-- 设备详情 -->
				<c:if test="${baseDevice.inStatus eq null && baseDevice.outLose ne '-1'}">
					<tr>
						<td>设备备注：</td>
						<td>
							<czxk:select dictTypeId="device_out_lose" height="150" id="outLose3" name="outLose"/>
						</td>
					</tr>
				</c:if>
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
					 	${baseDevice.boxBrand}
					</td>
				</tr>
				<tr>
					<td>总容量：</td>
					<td>
					 	${baseDevice.dcCapacity}
					</td>
				</tr>
				<tr>
					<td>熔丝已经使用数量：</td>
					<td>
						<div class="am-input-group">
							${baseDevice.fuseUsedNum}
							<span class="am-input-group-label">个</span>
						</div>
					</td>
				</tr>
				<tr>
					<td>未使用熔丝数量：</td>
					<td>
						<div class="am-input-group">
							${baseDevice.fuseNotUsedNum}
							<span class="am-input-group-label">个</span>
						</div>
					</td>
				</tr>
				<!-- 设备详情 -->
				<c:if test="${baseDevice.inStatus eq null && baseDevice.outLose ne '-1'}">
					<tr>
						<td>设备备注：</td>
						<td>
							<czxk:select dictTypeId="device_out_lose" height="150" id="outLose3" name="outLose"/>
						</td>
					</tr>
				</c:if>
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
						<czxk:showSelect dictTypeId="transformerType" value="${baseDevice.type}" />
					</td>
				</tr>
				<tr>
					<td>额定功率：</td>
					<td>
						${baseDevice.ratedPower}
					</td>
				</tr>
				<tr>
					<td>生产厂商：</td>
					<td>
						${baseDevice.manufacturer}
					</td>
				</tr>
				<!-- 设备详情 -->
				<c:if test="${baseDevice.inStatus eq null && baseDevice.outLose ne '-1'}">
					<tr>
						<td>设备备注：</td>
						<td>
							<czxk:select dictTypeId="device_out_lose" height="150" id="outLose3" name="outLose"/>
						</td>
					</tr>
				</c:if>
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
						<czxk:showSelect dictTypeId="lightningType" value="${baseDevice.gflModel}" />
					</td>
				</tr>
				<tr>
					<td>生产厂商：</td>
					<td>
						${baseDevice.supplier}
					</td>
				</tr>
				<!-- 设备详情 -->
				<c:if test="${baseDevice.inStatus eq null && baseDevice.outLose ne '-1'}">
					<tr>
						<td>设备备注：</td>
						<td>
							<czxk:select dictTypeId="device_out_lose" height="150" id="outLose3" name="outLose"/>
						</td>
					</tr>
				</c:if>
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
					  	${baseDevice.prRatedPower}
					</td>
				</tr>
				<tr>
					<td>生产厂商：</td>
					<td>
					  	${baseDevice.prManufacturer}
					</td>
				</tr>
				<!-- 设备详情 -->
				<c:if test="${baseDevice.inStatus eq null && baseDevice.outLose ne '-1'}">
					<tr>
						<td>设备备注：</td>
						<td>
							<czxk:select dictTypeId="device_out_lose" height="150" id="outLose3" name="outLose"/>
						</td>
					</tr>
				</c:if>
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
					  	<czxk:showSelect dictTypeId="line_material" value="${baseDevice.lineMaterial}" />
					</td>
				</tr>
				<tr>
					<td>规格型号：</td>
					<td>
					  	<czxk:showSelect dictTypeId="line_model" value="${baseDevice.lineModel}" />
					</td>
				</tr>
				<tr>
					<td>架空距离：</td>
					<td>
					  	${baseDevice.overheadDistance}米
					</td>
				</tr>
				<tr>
					<td>墙挂距离：</td>
					<td>
					  	${baseDevice.wallDistance}米
					</td>
				</tr>
				<tr>
					<td>地理距离：</td>
					<td>
					  	${baseDevice.groundDistance}米
					</td>
				</tr>
				<tr>
					<td>交流引入长度：</td>
					<td>
					  	${baseDevice.lineLength}米
					</td>
				</tr>
				<!-- 设备详情 -->
				<c:if test="${baseDevice.inStatus eq null && baseDevice.outLose ne '-1'}">
					<tr>
						<td>设备备注：</td>
						<td>
							<czxk:select dictTypeId="device_out_lose" height="150" id="outLose3" name="outLose"/>
						</td>
					</tr>
				</c:if>
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
					  	${baseDevice.mModel}
					</td>
				</tr>
				<tr>
					<td>生产厂商：</td>
					<td>
					  	<czxk:showSelect dictTypeId="monitoring_supplier" value="${baseDevice.mSupplier}" />
					</td>
				</tr>
				<!-- 设备详情 -->
				<c:if test="${baseDevice.inStatus eq null && baseDevice.outLose ne '-1'}">
					<tr>
						<td>设备备注：</td>
						<td>
							<czxk:select dictTypeId="device_out_lose" height="150" id="outLose3" name="outLose"/>
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</c:if>
	<c:if test="${baseDevice.inStatus eq null && baseDevice.outLose ne '-1'}">
		<div class="app-div-btn-tool">
			<button  onclick="saveDeviceOnly('${ctx}/devices/onlyUpdateDeviceInfo')" type="button" class="am-btn am-btn-danger am-radius am-btn-block">
				<i class="am-icon-check"></i><strong>报备</strong>
			</button>
		</div>
	</c:if>
	
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
<script>
!function(t) {
	$("ul[data-am-widget='gallery']").one("click",function(){//点击预览图片关闭预览
		$(".am-pureview-slider").on("click",function(){
			$(".am-icon-chevron-left[data-am-close='pureview']").triggerHandler("click");
		});
	});
	//showImagesH5();
}(window.jQuery || window.Zepto);
</script>
</html>
