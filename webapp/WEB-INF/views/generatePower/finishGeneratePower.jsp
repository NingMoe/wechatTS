<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>完成发电</title>
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
</style>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div onclick="toPageUrl('${ctx}/generatePower/electricIndex');">
			<span class="am-topbar-brand am-icon-angle-left"></span>
			<div class="am-topbar-brand  app-toolbar">
				完成发电
			</div>
		</div>
	</header>
	<div class="app-toolbar" style="text-align: center;background: #fff;padding: 6px;border-bottom: 1px #ddd solid">
		<div class="am-btn-group">
			<button id="discharge_btn" type="button" class="am-btn am-btn-default am-radius am-btn-sm" >
				<span class="am-badge am-badge-primary am-radius">3</span>
				油机发电
			</button>
		</div>
	</div>
	<div id="dischargeContainer">
		<form id="formContainer" class="am-form" method="post" action=""
			data-am-validator>
			<table class="am-table app-tb">
				<tbody>
					<tr>
						<td style="border-top: none;vertical-align: middle; width: 120px">
							发电开始电压
						</td>
						<td style="border-top: none">
							<div class="am-input-group">
								<input type="number" class="am-form-field" id="startVoltage" name="startVoltage" placeholder="" value="${s_generatepower_record.startVoltage}"
									required />
								<span class="am-input-group-label">V</span>
							</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;" colspan="2">
							<p>发电开始电压照片：</p>
							<ul id="startVoltageGallery" data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
								<c:forEach items="${s_generatepower_record.startVoltagePhotos}" var="item">
								    <li id="${item.photoId}">
									    <div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
									     	<div style="height:72px;margin-bottom:2px;overflow:hidden;">
									       		<img src="${item.thumbLocation}" <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if>  alt="" data-rel="${item.fileLocation}"/>
									       		${item.thumbLocation}
									       	</div>
									       	<h3 class="am-gallery-title am-fr" onclick="deletePhotoInServer('${ctx}','${item.photoId}')">删除  <i class="am-icon-trash-o am-icon-sm" 
									       		style="color:#dd514c;opacity:100;margin-top: 6px;"></i></h3>
									    </div>
									</li>
								</c:forEach>
								<li id="galleryAddBtn" onclick="takePhoto(this,'startVoltageImg');" data-weg>
								    <div onclick="void(0)">
								     <button type="button" class="app-add-photo"></button>
								    </div>
								</li>
							</ul>
							<input class="localIds" type="hidden" id="startVoltageImg" name="startVoltageImg"  value=""/>
						</td>
					</tr>
					<tr>
						<td style="border-top: none;vertical-align: middle; width: 120px">
							发电结束电压
						</td>
						<td style="border-top: none">
							<div class="am-input-group">
								<input type="number" class="am-form-field" id="endVoltage" name="endVoltage" placeholder="" value="${s_generatepower_record.endVoltage}"
									required />
								<span class="am-input-group-label">V</span>
							</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;" colspan="2">
							<p>发电结束电压照片：</p>
							<ul id="endVoltageGallery" data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
								<c:forEach items="${s_generatepower_record.endVoltagePhotos}" var="item">
								    <li id="${item.photoId}">
									    <div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
									     	<div style="height:72px;margin-bottom:2px;overflow:hidden;">
									       		<img src="${item.thumbLocation}" <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if>  alt="" data-rel="${item.fileLocation}"/>
									       		${item.thumbLocation}
									       	</div>
									       	<h3 class="am-gallery-title am-fr" onclick="deletePhotoInServer('${ctx}','${item.photoId}')">删除  <i class="am-icon-trash-o am-icon-sm" style="color:#dd514c;opacity:100;margin-top: 6px;"></i></h3>
									    </div>
									</li>
								</c:forEach>
								<li id="galleryAddBtn1" onclick="takePhoto(this,'endVoltageImg');" data-weg>
								    <div onclick="void(0)">
								     <button type="button" class="app-add-photo"></button>
								    </div>
								</li>
							</ul>
							<input class="localIds" type="hidden" id="endVoltageImg" name="endVoltageImg"  value=""/>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="vertical-align: middle">
							<textarea id="" rows="3" class="am-form-field" name="remark" placeholder="填写备注信息"  >${s_generatepower_record.remark}</textarea>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="app-div-btn-tool">
				<button type="button" class="am-btn am-btn-success am-radius am-btn-block" onclick="finishJob();">
					<strong>完成</strong>
				</button>
			</div>
			<div style="height: 10px"></div>
		</form>
	</div>
	<div class="am-modal am-modal-confirm" tabindex="-1" id="finish-confirm">
		<button type="button" class="am-btn am-radius am-modal-dialog" style="width: 100%">
			<div id="result" class="am-modal-bd am-radius"></div>
			<div class="am-modal-footer am-radius">
				<span class="am-modal-btn" data-am-modal-cancel>终止</span> <span class="am-modal-btn" data-am-modal-confirm>确定</span>
			</div>
		</button>
	</div>
<script type="text/javascript">
	wx.config(${jsConfig});
	wx.ready(function() {
	});
	wx.error(function(res) {
		modalAlert("网络出现问题，请稍后重试", null);
	});
	function finishJob() {
		var lis1 = $("#startVoltageGallery").find("li");
		if(lis1){
			if(lis1.length==1){
				modalAlert("请添加开始电压照片");
				return;
			}
		}
		var lis2 = $("#endVoltageGallery").find("li");
		if(lis2){
			if(lis2.length==1){
				modalAlert("请添加结束电压照片");
				return;
			}
		}
		$("#formContainer").attr("action","${ctx}/generatePowerSingleBatteryCheck/saveBatteryCheckStepThree");
		$("#formContainer").submit();
		//用store传照片saveBase64ImgData(function(){});
	}
	//showImagesH5();
</script>
</body>
</html>
		