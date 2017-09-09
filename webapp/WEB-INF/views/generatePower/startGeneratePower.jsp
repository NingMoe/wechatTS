<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<title>油机发电</title>
		<%@include file="../commons/head.jsp"%>
		<script type="text/javascript">
			var url1 = '/generatePower/electricIndex';
			var stationId1 = '${s_station.stationId}';
			function startJob(){
				showLoading();
				var url = "${ctx}/generatePower/checkStationGenerate.json?stationId="+stationId1;
				$.get(url, function(json) {
					var generatePowerRecordBO = json.generatePowerRecordBO;
					var user = json.user;
					if(generatePowerRecordBO){
						closeLoading();
						var powerCutTime = '';
						if(generatePowerRecordBO.powerCutTime){
							var JsonDateValue = new Date(generatePowerRecordBO.powerCutTime);
							powerCutTime = JsonDateValue.getFullYear()+"-"+(JsonDateValue.getMonth()+1)+"-"+JsonDateValue.getDate()+" "+JsonDateValue.getHours()+":"+JsonDateValue.getMinutes()+":"+JsonDateValue.getSeconds();
						}
						var phone = '';
						var deptId = '';
						if(user){
							phone = user.phoneNo;
							deptId = user.deptId
						}
						$("#my-alert-content").html("发电人："+generatePowerRecordBO.lastOperator+"<br>电话："+ phone +"<br>代维公司:"+ deptId +"<br>发电开始时间:" + powerCutTime);
						$('#my-alert').modal();
						return;
					}else{
						window.location.href = "${ctx}/generatePower/toStart";
					}
				}, "json");
				
				
				
			}
			function continueJob(){
				showLoading();
				window.location.href = "${ctx}/generatePower/toContinue";
			}
			function toFinishJob() {
				showLoading();
				window.location.href = "${ctx}/generatePower/toFinishJob";
			}
			function toSwitchIn(){
				showLoading();
				window.location.href = "${ctx}/generatePower/toSwitchIn";
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
			<div class="am-topbar-brand  app-toolbar">
				油机发电
			</div>
		</div>
		</header>
		<table class="am-table app-tb">
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
					<i class="am-icon-plug" style="color:#63B8FF;"></i>
					<strong>发电记录</strong>
				</td>
			</tr>
			<tr>
				<td>
					没有发电记录
				</td>
			</tr>
		</table>
	</c:if>
	<c:if test="${history eq 'true'}">
		<table class="am-table app-tb">
			<tr>
				<td>
					<i class="am-icon-plug" style="color:#63B8FF;"></i>
					<strong>油机发电</strong>
				</td>
				<td></td>
			</tr>
			<tr>
				<td style="width:120px">操作人：</td>
				<td>
					${s_generatepower_record.lastOperator}
				</td>
			</tr>
			<tr>
				<td style="width:120px">发电类型：</td>
				<td>
					<c:if test="${'0' eq s_generatepower_record.generatePowerType}">正常发电</c:if>
					<c:if test="${'1' eq s_generatepower_record.generatePowerType}">发电补报</c:if>
					<c:if test="${'2' eq s_generatepower_record.generatePowerType}">未发申报</c:if>
				</td>
			</tr>
			<tr>
				<td>基站停电时间：</td>
				<td><fmt:formatDate value="${s_generatepower_record.stationPowercutTime}" pattern="yyyy/MM/dd  HH:mm" /></td>
			</tr>
			<tr>
				<td style="width:120px">
					<c:if test="${'2' eq s_generatepower_record.generatePowerType}">恢复时间：</c:if>
					<c:if test="${'2' ne s_generatepower_record.generatePowerType}">发电开始时间：</c:if>
				</td>
				<td>
					<fmt:formatDate value="${s_generatepower_record.powerCutTime}" pattern="yyyy/MM/dd  HH:mm" />
				</td>
			</tr>
			<tr>
				<td style="width:120px">
					<c:if test="${'2' eq s_generatepower_record.generatePowerType}">填报时间：</c:if>
					<c:if test="${'2' ne s_generatepower_record.generatePowerType}">开始填报时间：</c:if>
				</td>
				<td>
					<fmt:formatDate value="${s_generatepower_record.operatTime}" pattern="yyyy/MM/dd  HH:mm" />
				</td>
			</tr>
			<tr <c:if test="${'2' eq s_generatepower_record.generatePowerType}">style="display:none;"</c:if> >
				<td>发电结束时间：</td>
				<td>
					<fmt:formatDate value="${s_generatepower_record.finishTime}" pattern="yyyy/MM/dd  HH:mm" />
				</td>
			</tr>
			<tr <c:if test="${'2' eq s_generatepower_record.generatePowerType}">style="display:none;"</c:if> >
				<td style="width:120px">结束填报时间</td>
				<td>
					<fmt:formatDate value="${s_generatepower_record.lastTime}" pattern="yyyy/MM/dd  HH:mm" />
				</td>
			</tr>
			<tr <c:if test="${'2' eq s_generatepower_record.generatePowerType}">style="display:none;"</c:if> >
				<td>发电时长：</td>
				<td>${timeSpan}&nbsp;小时</td>
			</tr>

			
			<%-- <tr>
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
			</tr> --%>
			<tr>
				<td style="width: 120px">状态：</td>
				<td>
					${s_generatepower_record.status}
				</td>
			</tr>
			<tr <c:if test="${'' eq s_generatepower_record.remark}">style="display:none;"</c:if>>
				<td style="width: 120px">备注：</td>
				<td>
					${s_generatepower_record.remark}
				</td>
			</tr>
			
		</table>
		</c:if>
		<c:if test="${status eq 'todo'}">
			<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
				<div class="am-u-sm-6">
					<button  onclick="startJob();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
						<i class="am-icon-play"></i><strong>发电开始</strong>
					</button>
				</div>
				<div class="am-u-sm-6">
					<button  onclick="toSwitchIn();" type="button" class="am-btn am-btn-danger am-radius am-btn-block">
						<i class="am-icon-plug"></i><strong>未发申报</strong>
					</button>
				</div>
			</div>
		</c:if>
		<c:if test="${status eq 'continue'}">
			<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
				<div class="am-u-sm-6">
					<button  onclick="continueJob();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
						<i class="am-icon-caret-square-o-right"></i><strong>继续发电</strong>
					</button>
				</div>
				<div class="am-u-sm-6">
					<button  onclick="toFinishJob();" type="button" class="am-btn am-btn-danger am-radius am-btn-block">
						<i class="am-icon-save"></i><strong>结束任务</strong>
					</button>
				</div>
			</div>
		</c:if>
		<form id="formContainer" class="am-form" method="post" action=""></form>
		<div style="height: 10px"></div>
		<%@include file="../station/stationAbnormal.jsp" %>
		

		<div class="am-modal am-modal-alert" tabindex="-1" id="my-alert">
		  <div class="am-modal-dialog">
		    <div class="am-modal-hd">当前基站正在发电</div>
		    <div class="am-modal-bd" id="my-alert-content" style="text-align: left;">
		      
		    </div>
		    <div class="am-modal-footer">
		      <span class="am-modal-btn">确定</span>
		    </div>
		  </div>
		</div>
	</body>
</html>
		