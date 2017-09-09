<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<title>设备核查</title>
	<%@include file="../commons/head.jsp"%>
	<script type="text/javascript">
	
	function prevStep(){
		$("#step").val("prev");
		$("#addSwitchPowerAndBattery").submit();
		//window.location.href="${ctx}/checkHiddenTrouble/goStationInfo?stationId=${s_station.stationId }";
	}
	function saveAndNextStep(){
		$("#addSwitchPowerAndBattery").submit();
	}
	
	$(document).ready(function() {
		var potentialTypes = '${yhBatteryBO.potentialType}';
		var potentialType = potentialTypes.split(',');
		for(var i = 0; i < potentialType.length; i++) {
			$("input[name='potentialType'][value="+potentialType[i]+"]").attr("checked",true); 
		}
		
		var demandConfirms = '${yhBatteryBO.demandConfirm}';
		var demandConfirm = demandConfirms.split(',');
		for(var i = 0; i < demandConfirm.length; i++) {
			$("input[name='demandConfirm'][value="+demandConfirm[i]+"]").attr("checked",true); 
		}
		
		var remark = '${yhBatteryBO.remark}';
		$("#remark").val(remark);
	});
	
	</script>
	</head>
		<body>
			<table class="am-table app-tb">
				<tbody>
					<tr>
						<th style="border-top: none;text-align: center;" colspan="2">
							设备核查
						</th>
					</tr>
				</tbody>
			</table>
			<form class="am-form" id="addSwitchPowerAndBattery" action="${ctx}/checkHiddenTrouble/addSwitchPowerAndBattery" method="post" data-am-validator>
				<input type="hidden" name="stationId" value="${s_station.stationId }"/>
				<input type="hidden" name=yhSwitchPowerId value="${yhSwitchPowerBO.recordId }"/>
				<input type="hidden" name="yhBatteryId" value="${yhBatteryBO.recordId }"/>
				<input type="hidden" name="step" value="next" id="step"/>
			<table class="am-table app-tb">
				<tbody>
					<tr>
						<th style="border-top: none;" colspan="2">
							开关电源
						</th>
					</tr>
					<tr>
						<td style="vertical-align: middle;width:33%;"><span style="color: red">*</span>品牌：</td>
						<td style="vertical-align: middle;">
							<select data-am-selected="{maxHeight: 200}" id="powerBrand" name="powerBrand">
								<option value="" selected = "selected">请选择</option>
								<option value="艾默生" <c:if test="${yhSwitchPowerBO.powerBrand eq '艾默生'}"> selected="selected" </c:if>>艾默生</option>
								<option value="动力源" <c:if test="${yhSwitchPowerBO.powerBrand eq '动力源'}"> selected="selected" </c:if>>动力源</option>
								<option value="普天洲际" <c:if test="${yhSwitchPowerBO.powerBrand eq '普天洲际'}"> selected="selected" </c:if>>普天洲际</option>
								<option value="华为" <c:if test="${yhSwitchPowerBO.powerBrand eq '华为'}"> selected="selected" </c:if>>华为</option>
								<option value="中达-斯米克" <c:if test="${yhSwitchPowerBO.powerBrand eq '中达-斯米克'}"> selected="selected" </c:if>>中达-斯米克</option>
								<option value="中达电通" <c:if test="${yhSwitchPowerBO.powerBrand eq '中达电通'}"> selected="selected" </c:if>>中达电通</option>
								<option value="中兴" <c:if test="${yhSwitchPowerBO.powerBrand eq '中兴'}"> selected="selected" </c:if>>中兴</option>
								<option value="通力环" <c:if test="${yhSwitchPowerBO.powerBrand eq '通力环'}"> selected="selected" </c:if>>通力环</option>
						  	</select>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;"><span style="color: red">*</span>型号：</td>
						<td style="vertical-align: middle;">
							<select data-am-selected="{maxHeight: 200}" id="powerModel" name="powerModel">
								<option value="" selected = "selected">请选择</option>
								<option value="PS48600-2B/50-150A" <c:if test="${yhSwitchPowerBO.powerModel eq 'PS48600-2B/50-150A'}"> selected="selected" </c:if>>PS48600-2B/50-150A</option>
								<option value="PS48300-2/30" <c:if test="${yhSwitchPowerBO.powerModel eq 'PS48300-2/30'}"> selected="selected" </c:if>>PS48300-2/30</option>
								<option value="PS48600-3B/2900-x2" <c:if test="${yhSwitchPowerBO.powerModel eq 'PS48600-3B/2900-x2'}"> selected="selected" </c:if>>PS48600-3B/2900-x2</option>
								<option value="PS48600-3A/2900-x3" <c:if test="${yhSwitchPowerBO.powerModel eq 'PS48600-3A/2900-x3'}"> selected="selected" </c:if>>PS48600-3A/2900-x3</option>
								<option value="PS48600-2B150" <c:if test="${yhSwitchPowerBO.powerModel eq 'PS48600-2B150'}"> selected="selected" </c:if>>PS48600-2B150</option>
								<option value="PS2900-2B150" <c:if test="${yhSwitchPowerBO.powerModel eq 'PS2900-2B150'}"> selected="selected" </c:if>>PS2900-2B150</option>
								<option value="PS48600-2B/50" <c:if test="${yhSwitchPowerBO.powerModel eq 'PS48600-2B/50'}"> selected="selected" </c:if>>PS48600-2B/50</option>
								<option value="DUM23 48/50（600x）" <c:if test="${yhSwitchPowerBO.powerModel eq 'DUM23 48/50（600x）'}"> selected="selected" </c:if>>DUM23 48/50（600x）</option>
								<option value="DUM25 48/50（600x）" <c:if test="${yhSwitchPowerBO.powerModel eq 'DUM25 48/50（600x）'}"> selected="selected" </c:if>>DUM25 48/50（600x）</option>
								<option value="DUM25F06-48/50（600）LV" <c:if test="${yhSwitchPowerBO.powerModel eq 'DUM25F06-48/50（600）LV'}"> selected="selected" </c:if>>DUM25F06-48/50（600）LV</option>
								<option value="TP68400B" <c:if test="${yhSwitchPowerBO.powerModel eq 'TP68400B'}"> selected="selected" </c:if>>TP68400B</option>
								<option value="DUM94A" <c:if test="${yhSwitchPowerBO.powerModel eq 'DUM94A'}"> selected="selected" </c:if>>DUM94A</option>
								<option value="MCS3000D" <c:if test="${yhSwitchPowerBO.powerModel eq 'MCS3000D'}"> selected="selected" </c:if>>MCS3000D</option>
								<option value="DUM-48/50B1" <c:if test="${yhSwitchPowerBO.powerModel eq 'DUM-48/50B1'}"> selected="selected" </c:if>>DUM-48/50B1</option>
								<option value="DUM-48/50H2" <c:if test="${yhSwitchPowerBO.powerModel eq 'DUM-48/50H2'}"> selected="selected" </c:if>>DUM-48/50H2</option>
								<option value="ZDUX68 T601V4.1" <c:if test="${yhSwitchPowerBO.powerModel eq 'ZDUX68 T601V4.1'}"> selected="selected" </c:if>>ZXDU68 T601V4.1R04M01</option>
								<option value="PS48300/25" <c:if test="${yhSwitchPowerBO.powerModel eq 'PS48300/25'}"> selected="selected" </c:if>>PS48300/25</option>
						  	</select>
						</td>
					</tr>
				</tbody>
			</table>
			<table class="am-table app-tb">
				<tbody>
					<tr>
						<th style="border-top: none;" colspan="2">
							蓄电池
						</th>
					</tr>
					
					<tr>
						<td style="vertical-align: middle;" colspan="2">
							<div>
								<span style="color: red">*</span>隐患类别：
							</div>
							 <div class="am-g">
								  <div class="am-u-sm-12">
								    <label class="am-checkbox">
								    	<input type="checkbox" name="potentialType" value="11" data-am-ucheck required minchecked="1" maxchecked="3">应配未配：原配置电池被盗
								    </label>
								    <label class="am-checkbox">
								        <input type="checkbox" name="potentialType" value="12" data-am-ucheck>应配未配：2家或以上共享站点
								    </label>
								    <label class="am-checkbox">
								        <input type="checkbox" name="potentialType" value="13" data-am-ucheck>应配未配：单家独享，但运营商确认配置要求
								    </label>
								  </div>
							</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle; width: 33%">
							<span style="color: red">*</span>需求确认方：
						</td>
						<td style="vertical-align: middle;">
							<div class="am-form-group" style="height: auto;margin-bottom: 0">
						      <label class="am-checkbox-inline">
						        <input type="checkbox" value="移动" id="yd" name="demandConfirm" minchecked="1" maxchecked="3" required>移动
						      </label>
						      <label class="am-checkbox-inline">
						        <input type="checkbox" value="电信" id="dx" name="demandConfirm">电信
						      </label>
						      <label class="am-checkbox-inline">
						        <input type="checkbox" value="联通" id="lt" name="demandConfirm">联通
						      </label>
						    </div>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="vertical-align: middle">
							<textarea id="remark" name="remark" rows="3" cols="" class="am-form-field" placeholder="填写备注" required ></textarea>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="app-div-btn-tool">
				<div class="am-g">
	 				<div class="am-u-sm-6">
	 					<button  onclick="prevStep();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
						<strong>上一步</strong>
					</button>
	 				</div>
	 				<div class="am-u-sm-6">
	 					<button  onclick="saveAndNextStep();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
						<strong>下一步</strong>
					</button>
	 				</div>
				</div>
			</div>
			<div>&nbsp;</div>
		</form>
	</body>
</html>