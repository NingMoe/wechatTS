<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<div class="am-cf" >
<form id="costForm" class="am-form" data-am-validator>
	<input type="hidden" name="selectId" value="1" >
	<input type="hidden" name="maintenanceCostId" value="${maintenanceCostBO.maintenanceCostId }" id="maintenanceCostId">
	<table class="am-table app-tb" style="margin:none;border:none;width: 100%">
		<tr>
			<td width="57%" style="vertical-align: middle" class="am-padding-right-0">填报日期</td>
			<td id="fillDate" class="am-padding-left-0">
				<fmt:formatDate value="${fillDate }" pattern="yyyy-MM-dd"/>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;" class="am-padding-right-0">填报月份</td>
			<td class="am-padding-left-0">
				<div class="am-input-group">
					<input  type="text" name="fillMonth" class="am-form-field" style="border-bottom:1px solid rgb(221, 221, 221);" id="fillMonth" value="<c:if test="${maintenanceCostBO == null || maintenanceCostBO == '' }"><fmt:formatDate value="${fillDate }" pattern="yyyy-MM" /></c:if><c:if test="${maintenanceCostBO != null && maintenanceCostBO != '' }"><fmt:formatDate value="${maintenanceCostBO.fillMonth }" pattern="yyyy-MM"/></c:if>" onchange="getRecordMsg(this.value);">		
					<span class="am-input-group-label am-padding-horizontal-xs"><i class="am-icon-calendar am-icon-fw"></i></span>
				</div>	
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;" class="am-padding-right-0">宏基站铁塔数量</td>
			<td class="am-padding-left-0">
				<input type="number" style="height: 35px;" placeholder="点击输入" id="bigStationTowerNum" name="bigStationTowerNum" class="am-form-field" value="${maintenanceCostBO.bigStationTowerNum }" pattern="^([1-9]{1}[\d]{0,9}|0)$" required>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;" class="am-padding-right-0">宏基站增高架数量</td>
			<td class="am-padding-left-0">
				<input  type="number" style="height: 35px;" name="bigStationHeightenFrameNum" class="am-form-field" placeholder="点击输入" id="bigStationHeightenFrameNum" value="${maintenanceCostBO.bigStationHeightenFrameNum }" pattern="^([1-9]{1}[\d]{0,9}|0)$" required>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;" class="am-padding-right-0">宏基站抱杆数量</td>
			<td class="am-padding-left-0">
				<input type="number" style="height: 35px;" placeholder="点击输入" id="bigStationPoleNum" name="bigStationPoleNum" class="am-form-field" value="${maintenanceCostBO.bigStationPoleNum }" pattern="^([1-9]{1}[\d]{0,9}|0)$" required>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;" class="am-padding-right-0">VIP宏基站站房及配套数量</td>
			<td class="am-padding-left-0">
				<input type="number" style="height: 35px;" placeholder="点击输入" id="vipBigStationRoomNum" name="vipBigStationRoomNum" class="am-form-field" value="${maintenanceCostBO.vipBigStationRoomNum }" pattern="^([1-9]{1}[\d]{0,9}|0)$" required>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;" class="am-padding-right-0">标准宏基站站房及配套数量</td>
			<td class="am-padding-left-0">
				<input type="number" style="height: 35px;" placeholder="点击输入" id="standardsBigStationRoomNum" name="standardsBigStationRoomNum" class="am-form-field" value="${maintenanceCostBO.standardsBigStationRoomNum }" pattern="^([1-9]{1}[\d]{0,9}|0)$" required>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;" class="am-padding-right-0">其它类型基站</td>
			<td class="am-padding-left-0">
				<input type="number" style="height: 35px;" placeholder="点击输入" id="otherTypeStationNum" name="otherTypeStationNum" class="am-form-field" value="${maintenanceCostBO.otherTypeStationNum }" pattern="^([1-9]{1}[\d]{0,9}|0)$" required>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;" class="am-padding-right-0">理论应付代维费用(元)</td>
			<td class="am-padding-left-0">
				<input type="number" style="height: 35px;" placeholder="点击输入" id="expensesPayable" name="expensesPayable" class="am-form-field" value="${maintenanceCostBO.expensesPayable }" pattern="^([1-9]{1}[\d]{0,9}|0)([.]{1}[\d]{1,2})?$" required>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;" class="am-padding-right-0">考核后实际应付代维费用</td>
			<td class="am-padding-left-0">
				<input type="number" style="height: 35px;" placeholder="点击输入" id="actualPayment" name="actualPayment" class="am-form-field" value="${maintenanceCostBO.actualPayment }" pattern="^([1-9]{1}[\d]{0,9}|0)([.]{1}[\d]{1,2})?$" required>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;" class="am-padding-right-0">本月代维费用是否支付</td>
			<td class="am-padding-left-0">
				<input type="hidden" id="isPaid" name="isPaid" value="${maintenanceCostBO.isPaid}"> 
				<input id="switch-state" name="handleWidth" type="checkbox" data-size="sm" data-am-switch data-on-text="是" data-off-text="否" data-on-color="success" data-off-color="danger">
			</td>
		</tr>
	</table>
</form>
</div>

<div class="app-div-btn-tool am-margin-bottom">
	<a href="javascript:saveMaintenanceCost();" class="am-btn am-btn-success am-radius am-btn-block"> <strong>保存</strong> </a>
</div>

<script>
var isPaid=$("#isPaid").val()-0;
if(isPaid == 0){
	$('input[name="handleWidth"]').bootstrapSwitch('state',false);
}else{
	$('input[name="handleWidth"]').bootstrapSwitch('state',true);
}

$('input[name="handleWidth"]').on('switchChange.bootstrapSwitch', function(event, state) {
	/*switch的监听器*/
	if(!state){
		$("#isPaid").val(0);
	}else{
		$("#isPaid").val(1);
	};
});
!function(t) {
	initMobiscrollDate("#fillMonth");
}(window.jQuery || window.Zepto);

function initMobiscrollDate(el,opt){
	var defaultopt = {
	    preset: 'date', //日期
	    theme: 'android-ics light', //皮肤样式
	    display: 'modal', //显示方式 
	    mode: 'scroller', //日期选择模式
	    dateFormat: 'yy-mm',
	    dateOrder: 'yymm',
		lang:'zh',
		startYear:1990, //开始年份
		maxDate:new Date()
	};
	
	if ('' != opt && null != opt && typeof (opt) != "undefined") {
		defaultopt=$.extend(defaultopt,opt);
	};
	//alert(JSON.stringify(defaultopt));
	$(el).scroller('destroy').scroller(defaultopt);
}

function saveMaintenanceCost(){
	$.when($('#costForm').validator('isFormValid')).then(function(result) {
		//var costForm = document.getElementById("costForm");
		var maintenanceCostId=$("#maintenanceCostId").val();
		var fillDate=$("#fillDate").text().trim();
		var fillMonth=$("#fillMonth").val();
		var bigStationTowerNum=$("#bigStationTowerNum").val();
		var bigStationHeightenFrameNum=$("#bigStationHeightenFrameNum").val();
		var bigStationPoleNum=$("#bigStationPoleNum").val();
		var vipBigStationRoomNum=$("#vipBigStationRoomNum").val();
		var standardsBigStationRoomNum=$("#standardsBigStationRoomNum").val();
		var otherTypeStationNum=$("#otherTypeStationNum").val();
		var expensesPayable=$("#expensesPayable").val();
		var actualPayment=$("#actualPayment").val();
		var isPaid=$("#isPaid").val();
		if(result){
			showLoading();
			$.post("${ctx}/maintenancecost/saveMaintenanceCostInfo.json", {
				maintenanceCostId : maintenanceCostId,
				fillDate:fillDate,
				fillMonth:fillMonth,
				bigStationTowerNum:bigStationTowerNum,
				bigStationHeightenFrameNum:bigStationHeightenFrameNum,
				bigStationPoleNum:bigStationPoleNum,
				vipBigStationRoomNum:vipBigStationRoomNum,
				standardsBigStationRoomNum:standardsBigStationRoomNum,
				otherTypeStationNum:otherTypeStationNum,
				expensesPayable:expensesPayable,
				actualPayment:actualPayment,
				isPaid :isPaid
			}, function(json) {
				window.location.href="${ctx}/maintenancecost/goCost?selectId=2"
			}, "json");
		}
		
	});
	
}
function getRecordMsg(value){
	var fillMonth=value;
	$.post("${ctx}/maintenancecost/getRecordMsg.json", {
		fillMonth:fillMonth
	}, function(json) {
		var maintenanceCostBO=json.maintenanceCostBO;
		$("#bigStationTowerNum").val(maintenanceCostBO.bigStationTowerNum);
		$("#bigStationHeightenFrameNum").val(maintenanceCostBO.bigStationHeightenFrameNum);
		$("#bigStationPoleNum").val(maintenanceCostBO.bigStationPoleNum);
		$("#vipBigStationRoomNum").val(maintenanceCostBO.vipBigStationRoomNum);
		$("#standardsBigStationRoomNum").val(maintenanceCostBO.standardsBigStationRoomNum);
		$("#otherTypeStationNum").val(maintenanceCostBO.otherTypeStationNum);
		$("#expensesPayable").val(maintenanceCostBO.expensesPayable);
		$("#actualPayment").val(maintenanceCostBO.actualPayment);
		$("#isPaid").val(maintenanceCostBO.isPaid);
		if(maintenanceCostBO.isPaid == 0){
			$('input[name="handleWidth"]').bootstrapSwitch('state',false);
		}else{
			$('input[name="handleWidth"]').bootstrapSwitch('state',true);
		}
	}, "json");
	
}
</script>