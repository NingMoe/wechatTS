<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
	<head>
	<title>新站交维</title>
	<%@include file="../commons/head.jsp"%>
	<script type="text/javascript">
		var url1 = '/acceptItem/checkItem';
		var stationId1 = '${acceptStation.stationId}';
		function goToAcceStatDeta() {
			var stationId = '${acceptStation.stationId}';
			window.location.href='${ctx}/station/getStationDetail?stationId=' + stationId;
		}
		
		function goToCate(checkType) {
			var stationId = '${acceptStation.stationId}';
			window.location.href='${ctx}/categoryItem/getCategoryAndItem.html?checkType='+checkType+'&stationId=' + stationId;
		}
		function updateStationCheckStatus(){
			modalConfirm("确定完成此次新站验收吗？",updateStationCheckSta,null);
		}
		function updateStationCheckSta(){
			showLoading();
			var stationId = '${acceptStation.stationId}';
			var url="${ctx}/acceptItem/updateStationCheckStatus.html?stationId=" + stationId;
			window.location.href=url;
		}
		function refreshStationList(){
			showLoading();
			window.location.href = "${ctx}/station/getNearbyList?lo=${s_user.lo}&la=${s_user.la}&jobId=job_2";
		};
		
		function updateStation(stationId){
			showLoading();
			window.location.href = "${ctx}/station/toUpdateStation?stationId="+stationId;
		}
	</script>
	</head>
		<body>
			<header class="am-topbar am-topbar-fixed-top">
				<div  onclick="refreshStationList();"><span class="am-topbar-brand am-icon-angle-left"></span>
				<div class="am-topbar-brand  app-toolbar">新站交维</div>
				</div>
			</header>
			<table class="am-table am-table-hover app-tb">
				<!--<tr onclick="updateStation('${acceptStation.stationId}')">
					-->
					<tr>
					<td style="border-top: none;width:120px;height:40px">基站名称：</td>
					<td style="border-top: none">${acceptStation.stationName}</td>
					<!--<td style="border-top: none; width:60px;text-align: right;">
						修改
					</td>
					<td style="border-top: none; width:30px;text-align: right;">
						<i class="am-icon-angle-right am-icon-sm" style="color:#63B8FF;"></i>
					</td>
				--></tr>
				<tr>
					<td style="border-bottom:none;" class="app-tb-td">交维状态：</td>
					<td style="border-bottom:none;" class="app-tb-td" colspan="2">${acceptStation.checkStatus}</td>
					<td></td>
				</tr>
			</table>
			<c:if test="${acceptStation.checkStatus eq '已验收' || acceptStation.checkStatus eq '验收中'}">
				<table class="am-table am-table-hover app-tb">
					<tr onclick="goToCate('tower');">
						<td style="height: 40px">
							铁塔检查
						</td>
						<td style="width: 50px;align:right;">
							<span class="am-icon-warning" style="color:orange;"></span>
								${towerWarningSum}
						</td>
						<td style="width:3px;text-align: right;">
							<i class="am-icon-angle-right am-icon-sm" style="color:#63B8FF;"></i>
						</td>
					</tr>
					<tr>
						<td style="height: 16px;background: #EDEEEF" colspan="3"></td>
					</tr>
					<tr onclick="goToCate('room');">
						<td style="height: 40px">
							机房检查
						</td>
						<td style="align:right;white-space: nowrap;">
							<span class="am-icon-warning" style="color:orange;"></span>
							${roomWarningSum}
						</td>
						<td style="text-align: right;">
							<i class="am-icon-angle-right am-icon-sm" style="color:#63B8FF;"></i>
						</td>
					</tr>
				</table>
			</c:if>
		  	<c:if test="${acceptStation.checkStatus eq '验收中'}">
				<div class="am-g am-g-fixed">
	  				<div class="am-u-sm-12">
	  					<button type="button" onclick="updateStationCheckStatus();" class="am-btn am-btn-success am-radius am-btn-block"><strong>完成</strong></button>
	  				</div>
				</div>			
			</c:if>
			<%@include file="../station/stationAbnormal.jsp" %>
		</body>
</html>