<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>系统更新</title>
<%@include file="../commons/head.jsp"%>

<style type="text/css">
.app-inputPM0{
	padding:0px;
	margin:0px;
	width: 100%;
	border: none;
	text-align: center;
	height:39px;
}
.app-inputTitle{
	background-color: #e9e9e9;
}
.app-inputContent{
	background-color: #fff !important;
}
</style>
<script type="text/javascript">

</script>
</head>
<body>
<div class="am-topbar-fixed-top am-scrollable-horizontal app-tabs-head" style="white-space:nowrap;">
	  <table class="am-table am-table-striped am-text-nowrap" style="border-bottom: 1px solid #ddd">
		  <tr class="app-tabs-title">
			<th style="text-align:center;"  <c:if test="${selectId == 1||null==selectId}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-info-circle" style="color:#BDB76B "></i>&nbsp;系统更新通知</div></th>
			<th style="text-align:center;"  <c:if test="${selectId == 2}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-cogs" style="color: #7EC0EE"></i>&nbsp;系统升级完成通知</div></th>
		  </tr>
	  </table>
</div>
<div class="app-tabs-bd" style="margin-top: 6px;">
	<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0">
		<jsp:include page="./systemUpdate.jsp"></jsp:include>
	</div>
	<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0" style="display:none;">
		<jsp:include page="./systemUpdateFinished.jsp"></jsp:include>
	</div>
</div>
<script>
initTabs();
</script>
</body>
</html>