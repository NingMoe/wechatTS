<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>周报列表</title>
<%@include file="../commons/head.jsp" %>
<style type="text/css">    
	.lineOverflow {         
		width: 100%;        
		border:#000 solid 1px;        
		text-overflow: ellipsis;        
		white-space: nowrap;/*禁止自动换行*/        
		overflow: hidden;    
	}
</style>

</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div ><!-- <span class="am-topbar-brand am-icon-angle-left" ></span> -->
			<div class="am-topbar-brand  app-toolbar">
				<table>
					<tr>
						<td width="30%">周报列表</td>
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
		<table class="am-table" style="padding:0px;margin: 0px;">
			<tbody>
				<c:forEach items="${weekReportList}" var="obj" varStatus="status">
					<tr onclick="toDaysReport('${obj.countyId}','${obj.mobileOperator}','${obj.num}','${obj.stationNum}');"  style="background: #fff;border-bottom:none;">
						<td style="border-top: none;border-bottom:none;vertical-align: middle;margin-bottom: 0px;padding-bottom: 0px;">
							<div class="am-text-truncate" ><font size='3'>${obj.countyId} 周报</font></div>
						</td>
					</tr>
					<tr onclick="toDaysReport('${obj.countyId}',,'${obj.mobileOperator}','${obj.num}','${obj.stationNum}');"  style="background: #fff;">
						<td style="border-top: none;border-bottom:1px solid rgb(221, 221, 221);">
							<!-- <small class="am-fl"> -->
								发电次数:${obj.num}&nbsp;&nbsp;&nbsp;&nbsp;发电基站数:${obj.stationNum}&nbsp;&nbsp;&nbsp;&nbsp;发电总时长:${obj.mobileOperator}H
							<!-- </small> -->
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
<script type="text/javascript">
	function toDaysReport(week,timeLength,num,stationNum) {
		showLoading();
		window.location.href="${ctx}/report/toWeekReport?week="+week+"&timeLength="+timeLength+"&num="+num+"&stationNum="+stationNum;
	}
</script>
<center>
<%@include file="../commons/bottom.jsp" %>
</center>
</body>
</html>