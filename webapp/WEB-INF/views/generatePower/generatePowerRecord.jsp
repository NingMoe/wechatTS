<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<%@include file="../commons/head.jsp"%>
<title>油机发电</title>
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
			<div class="am-topbar-brand app-toolbar">
				油机发电
			</div>
		</div>
	</header>
	<div id="generateContainer" >
		<c:if test="${'true' ne isFinishGP}">
			<jsp:include page="generatePowerRecordDetail.jsp"></jsp:include>
		</c:if>
		<c:if test="${'true' eq isFinishGP}">
			<jsp:include page="toFinishGeneratePowerRecord.jsp"></jsp:include>
		</c:if>
	</div>
<script type="text/javascript">
	wx.config(${jsConfig});
	wx.ready(function() {
	});
	wx.error(function(res) {
		modalAlert("网络出现问题，请稍后重试", null);
	});
	!function(t) {
		$("ul[data-am-widget='gallery']").one("click",function(){//点击预览图片关闭预览
			$(".am-pureview-slider").on("click",function(){
				$(".am-icon-chevron-left[data-am-close='pureview']").triggerHandler("click");
			});
		});
	}(window.jQuery || window.Zepto);
</script>
</body>
</html>