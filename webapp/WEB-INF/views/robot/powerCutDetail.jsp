<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>停电查询</title>
<%@include file="../commons/head.jsp" %>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div >
			<span class="am-topbar-brand am-icon-angle-left" ></span>
			<div class="am-topbar-brand  app-toolbar">停电查询</div>
		</div>
	</header>
	<div class="am-cf">
		<c:if test="${powList == null or powList == '[]'}">
			<div class="am-cf" style="text-align: center;margin-top: 60px">
				没有查询到相关的停电信息！
			</div>
		</c:if>
		<c:if test="${powList != null}">
			<table class="am-table am-table-hover">
				<tbody>
					<c:forEach items="${powList}" var="obj" varStatus="status">
						<tr >
						<td style="width: 120px;vertical-align: middle;">供电所名称：</td>
						<td style="vertical-align: middle;">
						  	${obj.cityname}
						</td>
				</tr>
					<tr >
						<td style="width: 120px;vertical-align: middle;">停电原因：</td>
						<td style="vertical-align: middle;">
						  	${obj.powerOffReason}
						</td>
						
					</tr>
					<tr >
						<td style="width: 120px;vertical-align: middle;">停电开始时间：</td>
						<td style="vertical-align: middle;">
						  	${obj.startTime}
						</td>
					
					</tr>
					<tr >
						<td style="width: 120px;vertical-align: middle;">停电结束时间：</td>
						<td style="vertical-align: middle;">
						  	${obj.powerTime}
						</td>
						
					</tr>
					<tr >
						<td style="width: 120px;vertical-align: middle;">影响范围：</td>
						<td style="vertical-align: middle;">
						  	${obj.scope}
						</td>
						
					</tr>
					<tr >
						<td style="width: 120px;vertical-align: middle;">公变名称：</td>
						<td style="vertical-align: middle;">
						  	${obj.pubTranName}
						</td>
						
					</tr>
					<tr>
						<td style="vertical-align: middle;" align="right">
							<i class="am-icon-angle-right am-icon-sm" style="color:#63B8FF;"></i>&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>