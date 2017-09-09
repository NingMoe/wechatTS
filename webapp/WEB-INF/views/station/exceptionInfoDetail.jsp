<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>异常记录详情</title>
	<%@include file="../commons/head.jsp" %>
	<link rel="stylesheet" href="${ctx}/assets/css/amazeui.switch.css"/>
	<script src="${ctx}/assets/js/amazeui.switch.min.js"></script>
</head>
<script type="text/javascript">	
</script>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div  onclick="goStationDetail();"><span class="am-topbar-brand am-icon-angle-left"></span>
		<div class="am-topbar-brand  app-toolbar">异常记录详情</div>
		</div>
	</header>
	<table class="am-table app-tb" >
		<tr>
			<td style="border-top: none;"><font size="3">${stationRecord.title}</font></td>
		</tr>	
		<tr>
			<td><font size="2" color="#8B8378">${stationRecord.content}</font></td>
		</tr>	
		<tr>
			<td style="border-top: none;">
				<ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-avg-md-3 am-avg-lg-4 am-gallery-imgbordered " data-am-gallery="{pureview:{target: 'a',weChatImagePreview: false}}">
					<c:forEach items="${stationRecord.photoList}" var="item" varStatus="status">
					  <li style="padding: 0 0 0 0">
					    <div class="am-gallery-item" style="height: 80px;overflow:hidden;">
					    	<a href="${item.fileLocation}" title=" " class="am-vertical-align-middle"  style="display:inline-block;vertical-align:middle;">
					      		<img src="${item.thumbLocation}" <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if>  alt="" />
					      	</a>
					    </div>
					  </li>
				  	</c:forEach>
				</ul>
			</td>
		</tr>	
		<tr>
			<td><font size="1" color="#8B8970">${stationRecord.createUserName}&nbsp;<fmt:formatDate value="${stationRecord.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></font></td>
		</tr>
	</table>
	<%--<c:if test="${stationRecord.createUserId eq token}">
		<div class="am-g am-g-fixed">
			<div class="am-u-sm-12">
				<button type="button" onclick="deleteStatioRecord('${stationRecord.recordId}');" class="am-btn am-btn-success am-radius am-btn-block">
					<i class="am-icon-trash"></i><strong>删除</strong>
				</button>
			</div>
		</div>	
	</c:if>
	--%><div class="am-modal am-modal-confirm" tabindex="-1" id="finish-confirm">
				<div class="am-modal-dialog">
					<div class="am-modal-hd">
						确定删除该记录吗？
					</div>
					<div class="am-modal-footer">
						<span class="am-modal-btn" data-am-modal-cancel>取消</span> 
						<span class="am-modal-btn" data-am-modal-confirm>确定</span>
					</div>
				</div>
			</div>
</body>
<script type="text/javascript">	
	function deleteStatioRecord(recordId){
		$('#finish-confirm').modal({
			relatedTarget : this,
			onConfirm : function() {
				var url="${ctx}/station/deleteStationRecord?recordId=" + recordId;
				window.location.href=url;
			},
			// closeOnConfirm: false,
			onCancel : function() {
				
			}
		});
	}
	
	function goStationDetail() {
		var url="${ctx}/station/getStationDetail?jobId=3&stationId=${stationRecord.stationId}";
		window.location.href=url;
	}
	//showImagesH5();
</script>
</html>
