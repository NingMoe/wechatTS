<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>优化建议</title>
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

</style>
<script type="text/javascript">
	///微信配置及相关js
	var jsconfig=${jsConfig};
	wx.config(jsconfig);
	wx.ready(function() {
	});
	wx.error(function(res) {
		modalAlert("网络出现问题，请稍后重试", null);
	});
</script>
</head>
<body>
<div class="am-topbar-fixed-top am-scrollable-horizontal app-tabs-head" style="white-space:nowrap;">
	  <table class="am-table am-table-striped am-text-nowrap" style="border-bottom: 1px solid #ddd">
		  <tr class="app-tabs-title">
			<th id="selectId1" style="text-align:center;"  <c:if test="${selectId == 1||null==selectId}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-info-circle" style="color:#BDB76B "></i>&nbsp;建议填报</div></th>
			<th id="selectId2" style="text-align:center;"  <c:if test="${selectId == 2}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-cogs" style="color: #7EC0EE"></i>&nbsp;建议列表</div></th>
		  </tr>
	  </table>
</div>
<div class="app-tabs-bd" style="margin-top: 6px;">
	<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0">
		<jsp:include page="./fillProclamation.jsp"></jsp:include>
	</div>
	<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0" style="display:none;">
		<jsp:include page="./proclamationList.jsp"></jsp:include>
	</div>
</div>
<script>
initTabs();
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