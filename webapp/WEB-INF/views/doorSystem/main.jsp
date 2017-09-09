<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>门禁填报</title>
<%@include file="../commons/head.jsp"%>
<script type="text/javascript">
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
<body>
<input type="hidden" id="canFill" value="${canFill }">
<div id="headerc" class="am-topbar-fixed-top am-scrollable-horizontal app-tabs-head" style="white-space:nowrap;">
	<table class="am-table am-table-striped am-text-nowrap" style="border-bottom: 1px solid #ddd">
		<tr class="app-tabs-title">
			<c:if test='${"group" ne source}'>
				<th id="selectId1" style="text-align:center;"  <c:if test="${selectId == 1||null==selectId}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-info-circle" style="color:#BDB76B "></i>&nbsp;门禁改造填报</div></th>
			</c:if>
	 		<th id="selectId2" style="text-align:center;"  <c:if test="${selectId == 2}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-cogs" style="color: #7EC0EE"></i>&nbsp;集团统计查询</div></th>
		</tr>
	</table>
</div>
<div class="app-tabs-bd" style="margin-top: 6px;">
	<c:if test='${"group" ne source}'>
		<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0">
			<jsp:include page="./fill.jsp"></jsp:include>
		</div>
		<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0" style="display:none;">
		<jsp:include page="./list.jsp"></jsp:include>
	</div>
	</c:if>
	<c:if test='${"group" eq source}'>
	<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0">
		<jsp:include page="./list.jsp"></jsp:include>
	</div>
	</c:if>
</div>

<!-- <div class="am-modal am-modal-no-btn" tabindex="-1" id="modalInfo">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">提示</div>
    <div class="am-modal-bd am-text-default">
		您好，您没有填报蓄电池整治权限，如需开通权限，请联系省级管理员！
		<br><br><br>
		<button type="button" onclick="weChatCloseThisPage();" class="am-btn am-btn-primary am-btn-block">确定</button>
    </div>
  </div>
</div> -->

<script type="text/javascript">
initTabs();
function showModalInfo(){
	/* var canFill=$("#canFill").val();
	if('true'!=canFill)
	$("#modalInfo").modal({closeViaDimmer: 0,height: 225}); */
}

function weChatCloseThisPage(){
	wx.closeWindow();
};
function replaceNna(obj,obj1,obj2){
	if(null!=obj)
		return obj1;
	return obj2;
}
</script>
</body>
</html>