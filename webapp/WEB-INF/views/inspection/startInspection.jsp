<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>日常巡检</title>
<%@include file="../commons/head.jsp" %>
<script type="text/javascript">
	var url1 = '/inspection/readyStartTask';
	
	var stationId1 = '${s_station.stationId}';
	
	function startJob(){
		showLoading();
		window.location.href = "${ctx}/inspection/toStart";
	}
	function continueJob(){
		showLoading();
		window.location.href = "${ctx}/inspection/toContinueTask";
	}
	function finishJob() {
		showLoading();
		var url = "${ctx}/inspection/checkfinishStatus.json";
		$.get(url, function(json) {
			closeLoading();
			if (json.status == "0") {
				window.location.href="${ctx}/inspection/finish?status=0";
			} else {
				$("#finish-confirm").modal(
					{
						relatedTarget : this,
						onConfirm : function(options) {
							window.location.href="${ctx}/inspection/finish?status=1";
						},
						onCancel : function() {
							//do nothing
						}
					});
			}
		}, "json");
	}
	
	function updateStation(stationId){
		showLoading();
		window.location.href = "${ctx}/station/toUpdateStation?stationId="+stationId;
	}
</script>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div onclick="toPageUrl('${ctx}/station/gotoNearbyList')"><span class="am-topbar-brand am-icon-angle-left"></span>
		<div class="am-topbar-brand  app-toolbar">日常巡检</div>
		</div>
	</header>
	<table class="am-table app-tb am-table-hover ">
		<!--<tr onclick="updateStation('${s_station.stationId}')">
			-->
			<tr>
			<td style="border-top: none;width:120px;height:40px">基站名称：</td>
			<td style="border-top: none">${s_station.stationName}</td>
			<!--<td style="border-top: none; width:60px;text-align: right;">
				修改
			</td>
			<td style="border-top: none; width:30px;text-align: right;">
				<i class="am-icon-angle-right am-icon-sm" style="color:#63B8FF;"></i>
			</td>
		--></tr>
	</table>
	<c:if test="${history eq 'false'}">
		<table class="am-table app-tb">
			<tr>
				<td>
					<i class="am-icon-history" style="color:#63B8FF;"></i>
					<strong>历史巡检记录</strong>
				</td>
			</tr>
			<tr>
				<td>
					没有历史巡检记录
				</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${history eq 'true'}">
	<table class="am-table app-tb">
		<tr>
			<td colspan="2">
				<i class="am-icon-history" style="color:#63B8FF;"></i>
				<strong>历史巡检记录</strong>
			</td>
		</tr>
		<tr>
			<td style="width:120px">操作人：</td>
			<td>
				${s_station_inspect_record.creator}
			</td>
		</tr>
		<tr>
			<td style="width:120px">进站时间：</td>
			<td>
				<fmt:formatDate value="${s_station_inspect_record.createDate}" pattern="yyyy/MM/dd  HH:mm:ss" />
			</td>
		</tr>
		<tr>
			<td style="width:120px">出站时间：</td>
			<td>
				<fmt:formatDate value="${s_station_inspect_record.finishTime}" pattern="yyyy/MM/dd  HH:mm:ss" />
			</td>
		</tr>
		<tr>
			<td>进站时长：</td>
			<td>${timeSpan}&nbsp;小时</td>
		</tr>
		<tr>
			<td class="app-td-bottom-border">作业状态：</td>
			<td class="app-td-bottom-border">${s_station_inspect_record.status}</td>
		</tr>
	</table>
	<table class="am-table app-tb">
		<tbody>
			<tr>
				<td colspan="2">
					<i class="am-icon-line-chart" style="color:#63B8FF;"></i>
					<strong>上站巡检统计</strong>
				</td>
			</tr>
			<tr>
				<td>设备总数</td>
				<td>${totalNum }个</td>
			</tr>
			<tr>
				<td>异常设备</td>
				<td>${errorNum }个</td>
			</tr>
			<tr>
				<td style="width: 120px">完成度：</td>
				<td>
					<table style="width: 100%">
						<tr>
							<td style="vertical-align: bottom;">
								<div class="am-progress am-progress-striped am-active">
									  <div class="am-progress-bar am-progress-bar-secondary"  style="width: ${progress }%"></div>
								</div>
							</td>
							<td style="width: 20px;vertical-align: top;">
								&nbsp;${progress }%
							</td>
						</tr>
					</table>
				</td>
			</tr>
	</table>
	</c:if>
	<c:if test="${status eq 'todo'}">
		<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
			<div class="am-u-sm-12">
				<button  onclick="startJob();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
					<i class="am-icon-play"></i><strong>新的开始</strong>
				</button>
			</div>
		</div>
	</c:if>
	<c:if test="${status eq 'continue'}">
	
		<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
			<div class="am-u-sm-6">
				<button  onclick="continueJob();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
					<i class="am-icon-caret-square-o-right"></i><strong>继续巡检</strong>
				</button>
			</div>
			<div class="am-u-sm-6">
				<button  onclick="finishJob();" type="button" class="am-btn am-btn-danger am-radius am-btn-block">
					<i class="am-icon-save"></i><strong>结束任务</strong>
				</button>
			</div>
		</div>
	</c:if>
	<div class="am-modal am-modal-confirm" tabindex="-1" id="finish-confirm">
		<button type="button" class="am-btn am-radius am-modal-dialog">
			<div class="am-modal-bd am-radius">巡检尚未完成，是否结束？</div>
			<div class="am-modal-footer am-radius">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
					class="am-modal-btn" data-am-modal-confirm>确定</span>
			</div>
		</button>
	</div>
	<div style="height: 10px"></div>
<%@include file="../station/stationAbnormal.jsp" %>
</body>
</html>