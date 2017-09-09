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
	
</script>
<style type="text/css">
	td{
		height: 45px;
	}
</style>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div id="goHistory">
			<span class="am-topbar-brand am-icon-angle-left"></span>
			<div class="am-topbar-brand  app-toolbar">设备详情</div>
		</div>
	</header>
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
					${baseDevice.batteryBrand}
				</td>
			</tr>
			<tr>
				<td>型号：</td>
				<td>
					${baseDevice.batteryModel}
				</td>
			</tr>
			<tr>
				<td>单体电压：</td>
				<td>
					${baseDevice.batteryVoltage}v
				</td>
			</tr>
			<tr>
				<td>组数：</td>
				<td>
					组${baseDevice.groupNum}
				</td>
			</tr>
			<tr>
				<td>生产日期：</td>
				<td>
					<fmt:formatDate value="${baseDevice.enterNetDate}" pattern="yyyy-MM-dd" />
				</td>
			</tr>
		</tbody>
	</table>
	<div class="app-div-btn-tool">
		<button id="goBack" type="button" class="am-btn am-btn-danger am-radius am-btn-block">
			</i><strong>返回</strong>
		</button>
	</div>
	<div>&nbsp;</div>
</body>
<script type="text/javascript">
!function(t) {
	$("ul[data-am-widget='gallery']").one("click",function(){//点击预览图片关闭预览
		$(".am-pureview-slider").on("click",function(){
			$(".am-icon-chevron-left[data-am-close='pureview']").triggerHandler("click");
		});
	});
}(window.jQuery || window.Zepto);

	
	window.onload =goBack;
 function goBack() {
		var jobid = '${jobid}';
		if(jobid == 'job_4') {
			$("#goBack").attr("onclick", "window.location.href = '${ctx}/generatePower/toContinue'");
			$("#goHistory").attr("onclick", "window.location.href = '${ctx}/generatePower/toContinue'");
		} else if(jobid == 'job_5') {
			$("#goBack").attr("onclick", "window.location.href = '${ctx}/discharge/toContinue'");
			$("#goHistory").attr("onclick", "window.location.href = '${ctx}/discharge/toContinue'");
		}
		//showImagesH5();
	}
</script>
</html>
