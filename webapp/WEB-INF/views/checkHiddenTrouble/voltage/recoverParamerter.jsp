<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../commons/tags.jsp"%>
<html>
<head>
	<title>隐患排查助手</title>
	<%@include file="../../commons/head.jsp"%>
	<style type="text/css">
		input::-webkit-outer-spin-button,
		input::-webkit-inner-spin-button {
		    -webkit-appearance: none !important;
		    margin: 0;
		}
	</style>
	<script type="text/javascript">
		function setEqualizedChargelimite(){
			var limite = "--";
			if($.isNumeric($("#cutPowerFrequency").val()) && $.isNumeric($("#cutPowerTime").val())){
				if($("#cutPowerFrequency").val() >0 && $("#cutPowerFrequency").val() < 3){ //停电频次＜3次月
					limite = "0.10c10";
				}else if($("#cutPowerFrequency").val() >= 3 && $("#cutPowerTime").val() > 0 && $("#cutPowerTime").val() <= 3){	//停电频次≥3次/月；  停电时长≤3h
					limite = "0.10c10";
				}else if($("#cutPowerFrequency").val() >= 3 && $("#cutPowerTime").val() > 3 && $("#cutPowerTime").val() <= 5){	//停电频次≥3次/月；  3h＜停电时长≤5h
					limite = "0.11c10";
				}else if($("#cutPowerFrequency").val() >= 3 && $("#cutPowerTime").val() > 5 && $("#cutPowerTime").val() <= 10){	//停电频次≥3次/月；  5h＜停电时长≤10h
					limite = "0.12c10";
				}else if($("#cutPowerFrequency").val() >= 3 && $("#cutPowerTime").val() > 10){	//停电频次≥3次/月；  停电时长＞10h
					limite = "0.13c10";
				}
			}
			$("#equalizedChargelimite").html(limite);
		}
		function setEqualizedChargeTime(){
			var time = "--";
			if($.isNumeric($("#equalizedChargeTime").val())){
				if($("#equalizedChargeTime").val() >0 && $("#equalizedChargeTime").val() < 12){
					time = "12";
				}else if($("#equalizedChargeTime").val() >= 12){
					time = $("#equalizedChargeTime").val();
				}
			}
			$("#equalizedChargeTimeResult").html(time);
		}
	</script>
</head>
<body style="background-color: #ffffff">
<header class="am-topbar am-topbar-fixed-top">
		<div>
			<div class="am-topbar-brand app-toolbar">${batteryBrand }  调参恢复 </div>
		</div>
</header><br/>
	<div class="app-div-btn-tool">
		<table class="am-table app-tb">
			<tbody>
				<tr style="height: 50px;">
					<td style="vertical-align: middle;">
						停电频次：
					</td>
					<td>
						<input id="cutPowerFrequency" name="cutPowerFrequency" onkeyup="setEqualizedChargelimite()" onchange="setEqualizedChargelimite()" type="number" class="am-form-field" placeholder="单位为  次/月  （选填）"   />
					</td>
				</tr>
				<tr style="height: 50px;">
					<td style="vertical-align: middle;">
						停电时长：
					</td>
					<td>
						<input id="cutPowerTime" name="cutPowerTime" onkeyup="setEqualizedChargelimite()" onchange="setEqualizedChargelimite()" type="number"  class="am-form-field"  placeholder="单位为  h  （选填）" />
					</td>
				</tr>
				<tr style="height: 50px;">
					<td style="vertical-align: middle;">
						均充时长：
					</td>
					<td>
						<input id="equalizedChargeTime" name="equalizedChargeTime" onkeyup="setEqualizedChargeTime()" onchange="setEqualizedChargeTime()" type="number" class="am-form-field"   placeholder="单位为  h  （选填）" />
					</td>
				</tr>
				<tr style="height: 50px;">
					<td style="vertical-align: middle; width: 33%;">
						浮充电压：
					</td>
					<td style="vertical-align: middle;">
						${floatChargeVoltage }
					</td>
				</tr>
				<tr style="height: 50px;">
					<td style="vertical-align: middle; width: 33%">
						均充电压：
					</td>
					<td style="vertical-align: middle;">
						${equalizeChargeVoltage }
					</td>
				</tr>
				<tr style="height: 50px;">
					<td style="vertical-align: middle; width: 33%">
						均充周期：
					</td>
					<td style="vertical-align: middle;">
						3月
					</td>
				</tr>
				<tr style="height: 50px;">
					<td style="vertical-align: middle; width: 33%">
						充电限流：
					</td>
					<td id="equalizedChargelimite" style="vertical-align: middle;">
						--
					</td>
				</tr>
				<tr  style="height: 50px;">
					<td style="vertical-align: middle; width: 33%">
						均充时长：
					</td>
					<td id="equalizedChargeTimeResult" style="vertical-align: middle;">
						--
					</td>
				</tr>
			</tbody>
		</table>
		<div class="am-g" >
			<div class="am-u-sm-6" data-am-widget="footer">
				<button  onclick="previousStep();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
					<i class="am-icon-arrow-left"></i>&nbsp;<strong>上一步</strong>
				</button>
			</div>
			<div class="am-u-sm-6">
				<button  onclick="nextStep();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
					<strong>下一步</strong>&nbsp;<i class="am-icon-arrow-right"></i>
				</button>
			</div>
		</div>
	</div>
	<div>&nbsp;</div>
    <script>
		echo.init({
			offset : 100,
			throttle : 250,
			unload : false
		});
	</script>
</body>
<script type="text/javascript">
function previousStep(){
	window.location.href="${ctx}/checkHiddenTrouble/toDischarge.html";
}
function nextStep(){
	window.location.href="${ctx}/checkHiddenTrouble/toGreenChannel.html";
}
</script>
</html>
