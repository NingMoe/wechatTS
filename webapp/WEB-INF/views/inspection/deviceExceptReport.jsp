<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>异常上报</title>
<%@include file="../commons/head.jsp" %>
<script type="text/javascript">
	wx.config(${jsConfig});
	wx.ready(function(){
		//alert();
	});
	wx.error(function(res) {
		modalAlert("网络出现问题，请稍后重试", null);
	});
</script>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div onclick="toPageUrl('${ctx}${toPageUrl}')">
			<span class="am-topbar-brand am-icon-angle-left"></span>
			<div class="am-topbar-brand  app-toolbar">异常上报</div>
		</div>
	</header>
<form action="${ctx}/devices/submitDeviceExceptReport" method="post"  data-am-validator>
	<div class="am-g am-padding app-tb">
		<c:if test="${deviceType eq 'switch_power'}">
			<div class="am-g">
			  <div class="am-u-sm-12 am-form-group">
			    <label class="am-checkbox">
			    	<input type="checkbox" name="abnormalCode" value="11" minchecked="1"  required> 防雷模块显示异常
			    </label>
			    <label class="am-checkbox">
			        <input type="checkbox" name="abnormalCode" value="12" > 模块功能异常
			    </label>
			    <label class="am-checkbox">
			        <input type="checkbox" name="abnormalCode" value="13" > 监控显示屏异常
			    </label>
			    <label class="am-checkbox">
			        <input type="checkbox" name="abnormalCode" value="00" > 其他
			    </label>
			  </div>
			</div>
		</c:if>
		<c:if test="${deviceType eq 'battery'}">
			<div class="am-g">
			  <div class="am-u-sm-12 am-form-group">
			    <label class="am-checkbox">
		        	<input type="checkbox" name="abnormalCode" value="21" minchecked="1"  required> 外观变形
			    </label>
			    <label class="am-checkbox">
		        	<input type="checkbox" name="abnormalCode" value="22" > 漏液
			    </label>
			    <label class="am-checkbox">
		        	<input type="checkbox" name="abnormalCode" value="23" > 连接片腐蚀
			    </label>
			    <label class="am-checkbox">
		       		<input type="checkbox" name="abnormalCode" value="24" > 蓄电池连接部位沉重塌陷变形
			    </label>
			    <label class="am-checkbox">
		        	<input type="checkbox" name="abnormalCode" value="00" > 其他
			    </label>
			  </div>
			</div>
		</c:if>
		
		<c:if test="${deviceType eq 'air_conditioning'}">
			<div class="am-g">
			  <div class="am-u-sm-12 am-form-group">
			    <label class="am-checkbox ">
		        	<input type="checkbox" name="abnormalCode" value="31" minchecked="1"  required> 出现制冷故障
			    </label>
			    <label class="am-checkbox">
		        	<input type="checkbox" name="abnormalCode" value="32" > 室外机破损
			    </label>
			    <label class="am-checkbox">
		        	<input type="checkbox" name="abnormalCode" value="33" > 室外机变形
			    </label>
			    <label class="am-checkbox">
		        	<input type="checkbox" name="abnormalCode" value="34" > 室外机被盗
			    </label>
			    <label class="am-checkbox">
		        	<input type="checkbox" name="abnormalCode" value="00" > 其他
			    </label>
			  </div>
			</div>
		</c:if>
		
		<c:if test="${deviceType eq 'tower'}">
			<div class="am-g">
			  <div class="am-u-sm-12 am-form-group">
			    <label class="am-checkbox ">
		        	<input type="checkbox" name="abnormalCode" value="41" minchecked="1"  required> 无塔高
			    </label>
			    <label class="am-checkbox">
		        	<input type="checkbox" name="abnormalCode" value="42" > 校正经纬度
			    </label>
			    <label class="am-checkbox">
		        	<input type="checkbox" name="abnormalCode" value="43" > 无塔型
			    </label>
			    <label class="am-checkbox">
		        	<input type="checkbox" name="abnormalCode" value="00" > 其他
			    </label>
			  </div>
			</div>
		</c:if>
		
		<!--<c:if test="${deviceType eq 'room'}">
			<div class="am-g">
			  <div class="am-u-sm-12 am-form-group">
			    <label class="am-checkbox ">
		        	<input type="checkbox" name="abnormalCode" value="41" minchecked="1"  required> 无房高
			    </label>
			    <label class="am-checkbox">
		        	<input type="checkbox" name="abnormalCode" value="42" > 校正经纬度
			    </label>
			    <label class="am-checkbox">
		        	<input type="checkbox" name="abnormalCode" value="43" > 无房型
			    </label>
			    <label class="am-checkbox">
		        	<input type="checkbox" name="abnormalCode" value="00" > 其他
			    </label>
			  </div>
			</div>
		</c:if>-->
	</div>
	<table class="am-table app-tb"> 
		<tbody>
			<tr>
				<td style="vertical-align: middle;height:45px;border-top: none" colspan="2">
					<p>异常照片：</p>
					<font color="#8B8878">要求：设备异常特征或告警灯清晰可见</font>
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
			<tr>
				<td colspan="2" style="vertical-align: middle">
					<textarea id="remark" name="remark" rows="3" cols="" class="am-form-field" placeholder="填写异常备注" required ></textarea>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="app-div-btn-tool">
		<input type="hidden" id="deviceId" name="deviceId"  value="${deviceId}">
		<input type="hidden" id="deviceType" name="deviceType"  value="${deviceType}">
		<button type="submit" class="am-btn am-btn-danger am-radius am-btn-block am-margin-vertical">
			<i class="am-icon-frown-o"></i>
			<strong>上报异常</strong>
		</button>
	</div>
</form>
</body>
<script type="text/javascript">
//showImagesH5();
</script>
</html>
