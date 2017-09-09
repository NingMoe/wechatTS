<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<%@include file="../commons/head.jsp" %>
<title>发电基站查询</title>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div >
			<!-- <span class="am-topbar-brand am-icon-angle-left" ></span> -->
			<div class="am-topbar-brand  app-toolbar">
			<table>
				<tr>
					<td width="30%">发电基站查询</td>
					<td width="60%" align="center"  style="white-space: nowrap;">
						<marquee scrollAmount=2  style="width: 80%;height: 33px;" >
							<strong>基站电源问题管理专家，创智信科专业电源维护10年</strong>
						</marquee>
					</td>
				</tr>
			</table>
			
			
			</div>
		</div>
	</header>
	<div class="am-cf">
			<%-- <input id="holeUrl" type="hidden" value="${url}">--%>
			<div style="font-size: 16px;margin-top: 20px;">截止至${curTime }共有发电基站${count}个</div> 
			<div class="am-scrollable-horizontal" style="margin-top: 20px;">
				<table class="am-table am-table-striped am-table-hover am-table-bordered am-table-compact">
					<tr>
						<th class="am-text-middle  am-text-nowrap" style="text-align:center;background: #77DDFF;">序号</th>
						<th class="am-text-middle am-text-nowrap" style="text-align:center;background: #77DDFF;">基站名称</th>
						<th class="am-text-middle am-text-nowrap" style="text-align:center;background: #77DDFF;">区域</th>
						<th class="am-text-middle am-text-nowrap" style="text-align:center;background: #77DDFF;">开始时间</th>
						<th class="am-text-middle am-text-nowrap" style="text-align:center;background: #77DDFF;">持续时长<br/>(分钟)</th>
						
					</tr>
					<c:forEach var="obj" items="${generatePowerList}" varStatus="status">
						<tr>
							<td class="am-text-middle" style="text-align:center;">${status.index+1}</td>
							<td class="am-text-middle" style="text-align:center;">${obj.stationName }</td>
							<td class="am-text-middle " style="text-align:center;width:120px;">${obj.region}</td>
							<td class="am-text-middle " style="text-align:center;width:85px;"><fmt:formatDate value="${obj.powercutTime}"  pattern="yyyy-MM-dd HH:mm:ss"  /></td>
							<td class="am-text-middle" style="text-align:center;">${obj.powerTimeLength}</td>
						</tr>
					</c:forEach>
			</table>
			</div>
	</div>
	<center>
	<%@include file="../commons/bottom.jsp" %>
	</center>
</body>
</html>