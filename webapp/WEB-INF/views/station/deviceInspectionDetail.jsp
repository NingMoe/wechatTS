<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>设备巡检记录</title>
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
		vertical-align: middle;
		height: 45px;
	}
</style>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div onclick="goBack();">
			<span class="am-topbar-brand am-icon-angle-left"></span>
			<div class="am-topbar-brand  app-toolbar">设备巡检记录</div>
		</div>
	</header>
	<c:if test="${deviceInspectRecords == null || deviceInspectRecords == '[]'}">
		<table class="am-table app-tb">
			<tr>
				<td style="border-top: none;">
					<div class="am-cf" style="text-align: center;margin-top: 20px">
						没有历史巡检记录
					</div>
				</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${deviceInspectRecords != null || deviceInspectRecords != '[]'}">
		<c:forEach items="${deviceInspectRecords}" var="obj" varStatus="status">
			<table class="am-table app-tb">
				<tr>
					<td style="vertical-align: middle;width:120px;border-top: none;">巡检人：</td>
					<td style="vertical-align: middle;border-top: none;">
						${obj.creator}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;width:120px">扫描时间：</td>
					<td style="vertical-align: middle;">
						<fmt:formatDate value="${obj.scanDate}" pattern="yyyy/MM/dd  HH:mm:ss" />
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;width:120px">设备状态：</td>
					<td style="vertical-align: middle;">
						<c:if test="${obj.deviceStatus eq true}">
							正常
						</c:if>
						<c:if test="${obj.deviceStatus eq false}">
							异常
						</c:if>
					</td>
				</tr>
				<c:if test="${obj.deviceStatus eq false}">
					<tr>
						<td style="vertical-align: middle;width:120px;">异常原因：</td>
						<td style="vertical-align: middle;">
							${obj.abnormalCode}
						</td>
					</tr>
					<tr>
						<c:if test="${obj.photoList != null && obj.photoList != '[]'}">
							<td colspan="2">
							<p>异常照片：</p>
							<ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
								<c:forEach items="${obj.photoList}" var="item">
								    <li style="padding: 0 0 0 0" id="${item.photoId}">
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
						</c:if>
						<c:if test="${obj.photoList == null || obj.photoList == '[]'}">
							<td style="vertical-align: middle;">异常照片：</td>
							<td style="vertical-align: middle;">无</td>
						</c:if>
					</tr>
				</c:if>
			</table>
		</c:forEach>
	</c:if>
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

function goBack(){
	var stationId = '${stationId}';
	window.location.href="${ctx}/station/getStationDetail?jobId=2&stationId="+stationId;
}
</script>
</html>
