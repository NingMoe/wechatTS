<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
	<head>
	<title>放电检测</title>
		<%@include file="../commons/head.jsp"%>
		<script type="text/javascript">
			var url1 = '/discharge/readyStart';
			var stationId1 = '${s_station.stationId}';
			function startJob(){
				if(${batteryNum} > 0){
					showLoading();
					window.location.href = "${ctx}/discharge/toStart";
				}else{
					alert("基站没有蓄电池，请添加之后再做放电检测！");
				}
			}
			function continueJob(){
				showLoading();
				window.location.href = "${ctx}/discharge/toContinue";
			}
			function finishJob() {
				showLoading();
				var url = "${ctx}/discharge/checkAllStatus.json";
				$.get(url, function(json) {
					var finishUrl = "${ctx}/discharge/updateRecordStatus?status="+json.status;
					closeLoading();
					if (json.status == "0") {
						window.location.href = finishUrl;
					} else {
						$("#result").html(json.result);
						$("#finish-confirm").modal({
							relatedTarget : this,
							onConfirm : function(options) {
								window.location.href = finishUrl;
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
		<div onclick="toPageUrl('${ctx}/station/gotoNearbyList')">
			<span class="am-topbar-brand am-icon-angle-left"></span>
			<div class="am-topbar-brand  app-toolbar">放电检测</div>
		</div>
		</header>
		<table class="am-table app-tb am-table-hover">
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
					<i class="am-icon-bolt" style="color:#63B8FF;"></i>&nbsp;&nbsp;
					<strong>放电检测记录</strong>
				</td>
			</tr>
			<tr>
				<td>
					没有放电检测记录
				</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${history eq 'true'}">
		<table class="am-table app-tb">
			<tr>
				<td>
					<i class="am-icon-bolt" style="color:#63B8FF;"></i>&nbsp;&nbsp;
					<strong>放电检测记录</strong>
				</td>
				<td></td>
			</tr>
			<tr>
				<td style="width:120px">操作人：</td>
				<td>
					${s_discharge_record.lastOperator}
				</td>
			</tr>
			<tr>
				<td style="width:120px">进站时间：</td>
				<td>
				<fmt:formatDate value="${s_discharge_record.operatTime}" pattern="yyyy/MM/dd  HH:mm:ss" />
				</td>
			</tr>
			<tr>
				<td>出站时间：</td>
				<td>
				<fmt:formatDate value="${s_discharge_record.finishTime}" pattern="yyyy/MM/dd  HH:mm:ss" />
				</td>
			</tr>
			<tr>
				<td>进站时长：</td>
				<td>${timeSpan}&nbsp;小时</td>
			</tr>
			<tr>
				<td style="width: 120px">完成度：</td>
				<td>
					<table style="width: 100%">
						<tr>
							<td style="vertical-align: bottom;">
								<div class="am-progress am-progress-striped am-active">
									  <div class="am-progress-bar am-progress-bar-secondary"  style="width: ${progress2 }%"></div>
								</div>
							</td>
							<td style="width: 20px;vertical-align: top;">
								&nbsp;${progress2 }%
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td style="width: 120px">状态：</td>
				<td>
					${s_discharge_record.status}
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
						<i class="am-icon-caret-square-o-right"></i><strong>继续放电</strong>
					</button>
				</div>
				<div class="am-u-sm-6">
					<button  onclick="finishJob();" type="button" class="am-btn am-btn-danger am-radius am-btn-block">
						<i class="am-icon-save"></i><strong>结束任务</strong>
					</button>
				</div>
			</div>
		</c:if>
		<form id="formContainer" class="am-form" method="post" action=""></form>
			<div class="am-modal am-modal-confirm" tabindex="-1" id="finish-confirm">
			<button type="button" class="am-btn am-radius am-modal-dialog" style="width: 100%">
				<div id="result" class="am-modal-bd am-radius"></div>
				<div class="am-modal-footer am-radius">
					<span class="am-modal-btn" data-am-modal-cancel>取消</span>
					<span class="am-modal-btn" data-am-modal-confirm>确定</span>
				</div>
			</button>
		</div>
		<div style="height: 10px"></div>
		<%@include file="../station/stationAbnormal.jsp" %>
	</body>
</html>
		