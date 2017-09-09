<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>基站肖像</title>
<%@include file="../commons/head.jsp"%>
<link rel="stylesheet" href="${ctx}/assets/css/amazeui.switch.css"/>
<script src="${ctx}/assets/js/amazeui.switch.min.js"></script>
<%@include file="../commons/mobiscrollDatePlugin.jsp" %> 
</head>
<body>
	<div id="headerc" class="am-topbar-fixed-top am-scrollable-horizontal app-tabs-head" style="white-space:nowrap;">
		  <table class="am-table am-table-striped am-text-nowrap" style="border-bottom: 1px solid #ddd">
			  <tr class="app-tabs-title">
				  <td <c:if test="${jobId == 1}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-info-circle" style="color:#BDB76B "></i>&nbsp;基站信息</div></td>
				  <td <c:if test="${jobId == 5}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-calculator" style="color: #7EC0EE"></i>&nbsp;基站动作</div></td>	
				  <!-- <td <c:if test="${jobId == 5}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-calculator" style="color:#DA70D6"></i>&nbsp;停电预测</div></td> -->
				  <td <c:if test="${jobId == 2}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-cogs" style="color: #7EC0EE"></i>&nbsp;设备状况</div></td>	
				  <td <c:if test="${jobId == 3}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-warning" style="color:#EE4000 "></i>&nbsp;异常隐患</div></td>	
				  <td <c:if test="${jobId == 4}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-pencil-square-o" style="color:#9ACD32 "></i>&nbsp;修改记录</div></td>
				  
			  </tr>
		  </table>
	</div>
	<div class="app-tabs-bd" style="margin-top: 6px;">
		<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0" style="display:none;">
			<%@include file="baseStationInfo.jsp" %>
		</div>
		<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0" style="display:none;">
	    	<%@include file="stationAction.jsp" %>
		</div>
		<%-- <div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0" style="display:none;">
			<%@include file="powerFailurePrediction.jsp" %>
		</div> --%>
		<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0" style="display:none;">
	    	<%@include file="deviceInfo.jsp" %>
		</div>
		<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0" style="display:none;">				    
			<%@include file="exceptionInfo.jsp" %>
		</div>
		<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0" style="display:none;">
	    	<%@include file="historyModifyRecord.jsp" %> 
	    	<%--<div class="am-cf" style="text-align: center;margin-top: 60px">
				正在努力开发中...
			</div>--%>
		</div>
	</div>	
<script type="text/javascript">
	var url1 = '/station/getStationDetail';
	var stationId1 = '${acceptStation.stationId}';
	initTabs();
</script>
<%@include file="../station/stationAbnormal.jsp" %>
</body>
</html>