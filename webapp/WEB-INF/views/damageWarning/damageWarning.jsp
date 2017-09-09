<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>灾害预警列表</title>
<%@include file="../commons/head.jsp" %>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div >
			<!-- <span class="am-topbar-brand am-icon-angle-left" ></span> -->
			<div class="am-topbar-brand  app-toolbar">灾害预警详情</div>
		</div>
	</header>
	<div class="am-cf">
			<div style="font-size: 16px;margin-top: 20px;">当前红色预警${redWarnNum}条，详细信息如下:</div>
			<div class="am-scrollable-horizontal">
				<table class="am-table am-table-striped am-table-hover am-table-bordered am-table-compact">
					<tr>
						<th class="am-text-middle am-text-nowrap" style="text-align:center;background: #dd514c;">序号</th>
						<th class="am-text-middle" style="text-align:center;background: #dd514c;">预警名称</th>
						<th class="am-text-middle" style="text-align:center;background: #dd514c;">发布时间</th>
					</tr>
					<c:forEach var="obj" items="${redWarnlist }" varStatus="status">
						<tr onclick="toWeatherPage('${obj.pageUrl }');">
							<td class="am-text-middle" style="text-align:center;">${status.index+1}</td>
							<td class="am-text-middle" style="text-align:center;"><a>${obj.alertName }</a></td>
							<td class="am-text-middle" style="text-align:center;width:85px;"><fmt:formatDate value="${obj.publishTime }"  pattern="yyyy-MM-dd HH:mm:ss"  /></td>
						</tr>
					</c:forEach>
					
				</table>
			</div>
			<div style="font-size: 16px;margin-top: 20px;">当前橙色预警${orangeWarnNum}条，详细信息如下:</div>
			<div class="am-scrollable-horizontal">
				<!-- <div class="am-topbar-brand  app-toolbar">当前橙色预警${orangeWarnNum}条，详细信息如下:</div> -->
				<table class="am-table am-table-striped am-table-hover am-table-bordered am-table-compact" >
					<tr>
						<th class="am-text-middle am-text-nowrap" style="text-align:center;background: #f37b1d;">序号</th>
						<th class="am-text-middle" style="text-align:center;background: #f37b1d;">预警名称</th>
						<th class="am-text-middle" style="text-align:center;background: #f37b1d;">发布时间</th>
					</tr>
					<c:forEach var="obj" items="${orangeWarnlist }" varStatus="status">
						<tr onclick="toWeatherPage('${obj.pageUrl }');">
							<td class="am-text-middle" style="text-align:center;">${status.index+1}</td>
							<td class="am-text-middle" style="text-align:center;"><a>${obj.alertName }</a></td>
							<td class="am-text-middle" style="text-align:center;width:85px;"><fmt:formatDate value="${obj.publishTime }"  pattern="yyyy-MM-dd HH:mm:ss"  /></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			
			<div style="font-size: 16px;margin-top: 20px;">当前黄色预警${yellowWarnNum}条，详细信息如下:</div>
			<div class="am-scrollable-horizontal">
				<!-- <div class="am-topbar-brand  app-toolbar">当前橙色预警${orangeWarnNum}条，详细信息如下:</div> -->
				<table class="am-table am-table-striped am-table-hover am-table-bordered am-table-compact">
					<tr>
						<th class="am-text-middle am-text-nowrap" style="text-align:center;background: #FFF68F;">序号</th>
						<th class="am-text-middle" style="text-align:center;background: #FFF68F;">预警名称</th>
						<th class="am-text-middle" style="text-align:center;background: #FFF68F;">发布时间</th>
					</tr>
					<c:forEach var="obj" items="${yellowWarnlist }" varStatus="status">
						<tr onclick="toWeatherPage('${obj.pageUrl }');">
							<td class="am-text-middle" style="text-align:center;">${status.index+1}</td>
							<td class="am-text-middle" style="text-align:center;"><a>${obj.alertName }</a></td>
							<td class="am-text-middle" style="text-align:center;width:85px;"><fmt:formatDate value="${obj.publishTime }"  pattern="yyyy-MM-dd HH:mm:ss"  /></td>
						</tr>
					</c:forEach>
				</table>
			</div>
			
			<div style="font-size: 16px;margin-top: 20px;">当前蓝色预警${blueWarnNum}条，详细信息如下:</div>
			<div class="am-scrollable-horizontal">
				<!-- <div class="am-topbar-brand  app-toolbar">当前橙色预警${orangeWarnNum}条，详细信息如下:</div> -->
				<table class="am-table am-table-striped am-table-hover am-table-bordered am-table-compact" >
					<tr>
						<th class="am-text-middle am-text-nowrap" style="text-align:center;background: #3bb4f2;">序号</th>
						<th class="am-text-middle" style="text-align:center;background: #3bb4f2;">预警名称</th>
						<th class="am-text-middle" style="text-align:center;background: #3bb4f2;">发布时间</th>
					</tr>
					<c:forEach var="obj" items="${blueWarnlist }" varStatus="status">
						<tr onclick="toWeatherPage('${obj.pageUrl }');">
							<td class="am-text-middle" style="text-align:center;">${status.index+1}</td>
							<td class="am-text-middle" style="text-align:center;"><a>${obj.alertName }</a></td>
							<td class="am-text-middle" style="text-align:center;width:85px;"><fmt:formatDate value="${obj.publishTime }"  pattern="yyyy-MM-dd HH:mm:ss"  /></td>
						</tr>
					</c:forEach>
				</table>
			</div>
	</div>
<script type="text/javascript">
function toWeatherPage(pageUrl){
	window.location.href="http://www.weather.com.cn/alarm/newalarmcontent.shtml?file="+pageUrl;
}
</script>
</body>
</html>