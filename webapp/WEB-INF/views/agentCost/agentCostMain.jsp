<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>代维费用</title>
<%@include file="../commons/head.jsp"%>
<%@include file="../commons/mobiscrollDatePlugin.jsp" %>
<link rel="stylesheet" href="${ctx}/assets/css/amazeui.switch.css"/>
<script src="${ctx}/assets/js/amazeui.switch.min.js"></script>
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
///微信配置及相关js
var jsConfig=${jsConfig};
wx.config(jsConfig);
wx.ready(function() {
	showModalInfo();
});
wx.error(function(res) {
	modalAlert("网络出现问题，请稍后重试", null);
});
</script>
</head>
<body style="padding-bottom:0px;">
<input id="provinceId" value="${sessionScope.s_user.provinceId}" type="hidden">
<div class="am-topbar-fixed-top am-scrollable-horizontal app-tabs-head" style="white-space:nowrap;">
	  <table class="am-table am-table-striped am-text-nowrap" style="border-bottom: 1px solid #ddd">
		  <tr class="app-tabs-title">
		  	<c:if test='${userInfo.deptId != "市公司" }'><th style="text-align:center;"  <c:if test="${selectId == 2}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-cogs" style="color: #7EC0EE"></i>&nbsp;填报历史查询</div></th></c:if>
		  	<c:if test='${userInfo.deptId == "市公司" }'>
		  		<th style="text-align:center;"  <c:if test="${selectId == 1||null==selectId}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-info-circle" style="color:#BDB76B "></i>&nbsp;代维费用填报</div></th>
				<th style="text-align:center;"  <c:if test="${selectId == 2}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-cogs" style="color: #7EC0EE"></i>&nbsp;填报历史查询</div></th>
		  	</c:if>
			
		  </tr>
	  </table>
</div>
<div class="app-tabs-bd" style="margin-top: 6px;">
	<c:if test='${userInfo.deptId != "市公司" }'>
		<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0">
			<jsp:include page="./agentCostList.jsp"></jsp:include>
		</div>
	</c:if>
	<c:if test='${userInfo.deptId == "市公司" }'>
		<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0">
			<jsp:include page="./fillAgentCost.jsp"></jsp:include>
		</div>
		<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0" style="display:none;">
			<jsp:include page="./agentCostList.jsp"></jsp:include>
		</div>
	</c:if>
</div>
<div class="am-modal am-modal-no-btn" tabindex="-1" id="modalInfo">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">提示</div>
    <div class="am-modal-bd am-text-default">
		您好，您没有填报代维费用的权限，如需开通权限，请联系省级管理员！
		<br><br><br>
		<button type="button" onclick="weChatCloseThisPage();" class="am-btn am-btn-primary am-btn-block">确定</button>
    </div>
  </div>
</div>
<script>
initTabs();
function showModalInfo(){
	var provinceId=$("#provinceId").val();
	if('黑龙江省'!=provinceId)
		$("#modalInfo").modal({closeViaDimmer: 0,height: 225});
}
function weChatCloseThisPage(){
	wx.closeWindow();
};
</script>
</body>
</html>