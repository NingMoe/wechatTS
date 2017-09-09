<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>开始检测</title>
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
		<div onclick="toPageUrl('${ctx}/discharge/readyStart');">
			<span class="am-topbar-brand am-icon-angle-left"></span>
			<div class="am-topbar-brand app-toolbar">
				开始检测
			</div>
		</div>
	</header>
	<div class="app-toolbar" style="text-align: center;background: #fff;padding: 6px;border-bottom: 1px #ddd solid">
		<div class="am-btn-group">
			<button type="button" class="am-btn am-btn-default am-radius am-btn-sm app-btn-active">
			<span class="am-badge am-badge-primary am-radius">1</span>
				放电检测
			</button>
		</div>
	</div>
	<div id="dischargeContainer">
		<jsp:include page="dischargeRecordDetail.jsp"></jsp:include>
	</div>
<script type="text/javascript">
	wx.config(${jsConfig});
	wx.ready(function() {
	});
	wx.error(function(res) {
		modalAlert("网络出现问题，请稍后重试", null);
	});
</script>
</body>
</html>