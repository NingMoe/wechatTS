<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>放电检测</title>
<%@include file="../commons/head.jsp"%>
<style type="text/css">
	input::-webkit-outer-spin-button,
	input::-webkit-inner-spin-button {
	    -webkit-appearance: none !important;
	    margin: 0;
	}
	.app-btn-active{
		background-color: #ddd;
	}
	.testborder{
		border:1px solid red;
	}
</style>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div>
			<div class="am-topbar-brand app-toolbar"> 放电检测 </div>
		</div>
	</header>
	
	<table  id="" class="am-table app-tb am-margin-bottom-0">
	   	<tr >
			<td style="border-top: none;" colspan="2">
				<div class="am-g">
					<div class="am-u-sm-2 am-padding-horizontal-0">
						<button id="startTheTimer" onclick="startTheTimer();" type="button" class="am-btn am-btn-success am-btn-sm">开始</button>
					</div>
					<div class="am-u-sm-10 am-padding-top-xs">
						<div class="am-progress am-progress-striped am-active am-margin-bottom-0" style="font-size: 1.4rem;text-align: right;">
							<div id="timeProcess" class="am-progress-bar app-border-right-black"  style="width: 0%;text-align: right;"></div>
							<p id="remainingTime" style="display: inline;">30:00</p>
						</div>
					</div>
				</div>
			</td>
		</tr>
	</table>
<form class="am-form" id="checkDischargeForm" method="post" data-am-validator>
	<table class="am-table app-tb">
  		<tr>
			<td style="border-top: none;" colspan="2"> <strong>步骤一：放电5分钟记录：</strong> </td>
		</tr>
		<tr>
			<td style="vertical-align: middle; width: 99px">
				端电压：
			</td>
			<td>
				<div class="am-input-group">
				  <input id="fiveTerminalVoltage" value="${dr.fiveTerminalVoltage}" type="text" class="am-form-field" placeholder="端电压">
				  <span class="am-input-group-label">V</span>
				</div>
			</td>
		</tr>
  	</table>
  	
  	<table class="am-table app-tb">
  		<tr>
			<td style="border-top: none;" colspan="2"> <strong>步骤二：按站内实际情况选择：</strong> </td>
		</tr>
		<tr>
			<td class="am-padding-horizontal-0">
				<div id="headerc" class="am-scrollable-horizontal app-tabs-head" style="white-space:nowrap;">
					<table class="am-table am-table-striped am-text-nowrap am-margin-bottom-0" style="border-bottom: 1px solid #ddd">
						<tr class="app-tabs-title">
						 <td id="unqualified" onclick="changeBatteryQT('整组不合格');" class="app-tabs-selected am-text-primary"><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-warning" style="color:#EE4000 "></i>&nbsp;整组不合格</div></td>
						 <td id="allq" onclick="changeBatteryQT('整组合格');" ><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-thumbs-o-up" style="color:#4AAA4A"></i>&nbsp;整组合格</div></td>	
						 <td id="other" onclick="changeBatteryQT('其他异常');" ><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-info-circle" style="color:#F37B1D"></i>&nbsp;其他异常</div></td>	
						</tr>
					</table>
				</div>
				<div class="app-tabs-bd">
					<div data-am-widget="list_news" class="app-tabs-con am-list-news am-list-news-default am-animation-fade am-margin-0 am-padding-0" style="display:none;">
						<table class="am-table app-tb">
					  		<tr>
								<td style="border-top: none;" colspan="2"> <strong>终止放电记录，电池无法满足30分钟备电</strong> </td>
							</tr>
							<tr>
								<td style="vertical-align: middle; width: 99px">
									放电时长：
								</td>
								<td>
									<div class="am-input-group">
									  <input id="dischargeLength_unqualified" <c:if test="${dr.batteryQuality eq '整组不合格'}"> value='${dr.dischargeLength}' </c:if> type="text" class="am-form-field" placeholder="放电时长">
									  <span class="am-input-group-label">分钟</span>
									</div>
								</td>
							</tr>
					  	</table>
					</div>
					<div data-am-widget="list_news" class="app-tabs-con am-list-news am-list-news-default am-animation-fade am-margin-0 am-padding-0" style="display:none;">
						<table class="am-table app-tb">
					  		<tr>
								<td style="border-top: none;" colspan="2"> <strong>放电30分钟记录：</strong> </td>
							</tr>
							<tr>
								<td style="border-top: none;" colspan="2">
									组1单体电压：
									<div style="border-top: 1px #ddd solid; border-left: 1px #ddd solid; width: 100%">
										<ul class="am-avg-sm-6">
											<c:forEach begin="1" end ="24" step="1" varStatus="status">
												<li class="am-padding-0" style="border:none;">
													<input name="voltage" onblur="void(0);" id="${status.index }_1" data-pk="" type="number" style="border-left:none;border-top:none;background-color: #EDEEEF" />
												</li>
											</c:forEach>
										</ul>
									</div>
								</td>
							</tr>
							<tr>
								<td style="border-top: none;" colspan="2">
									组2单体电压：
									<div style="border-top: 1px #ddd solid; border-left: 1px #ddd solid; width: 100%">
										<ul class="am-avg-sm-6">
											<c:forEach begin="1" end ="24" step="1" varStatus="status">
												<li class="am-padding-0" style="border:none;">
													<input name="voltage" onblur="void(0);"id="${status.index }_2" data-pk=""  type="number" style="border-left:none;border-top:none;background-color: #EDEEEF" />
												</li>
											</c:forEach>
										</ul>
									</div>
								</td>
							</tr>
							<tr>
								<td style="vertical-align: middle; width: 99px">
									端电压：
								</td>
								<td>
									<div class="am-input-group">
									  <input id="terminalVoltage_allq" <c:if test="${dr.batteryQuality eq '整组合格'}"> value='${dr.terminalVoltage}' </c:if> type="text" class="am-form-field" placeholder="端电压">
									  <span class="am-input-group-label">V</span>
									</div>
								</td>
							</tr>
							<tr>
								<td style="vertical-align: middle;" colspan="2">
									<div class="am-form-group">
								      <textarea rows="3" id="remark_allq" placeholder="备注"><c:if test="${dr.batteryQuality eq '整组合格'}">${dr.remark} </c:if></textarea>
								    </div>
								</td>
							</tr>
					  	</table>
					</div>
					<div data-am-widget="list_news" class="app-tabs-con am-list-news am-list-news-default am-animation-fade am-margin-0 am-padding-0" style="display:none;">
						<table class="am-table app-tb">
					  		<tr>
								<td style="border-top: none;" colspan="2"> <strong>其他异常记录：</strong> </td>
							</tr>
							<tr>
								<td style="vertical-align: middle; width: 99px">
									放电时长：
								</td>
								<td>
									<div class="am-input-group">
									  <input id="dischargeLength_other"  <c:if test="${dr.batteryQuality eq '其他异常'}"> value='${dr.dischargeLength}' </c:if> type="text" class="am-form-field" placeholder="放电时长">
									  <span class="am-input-group-label">分钟</span>
									</div>
								</td>
							</tr>
							<tr>
								<td style="vertical-align: middle; width: 99px">
									端电压：
								</td>
								<td>
									<div class="am-input-group">
									  <input id="terminalVoltage_other"  <c:if test="${dr.batteryQuality eq '其他异常'}"> value='${dr.terminalVoltage}' </c:if> type="text" class="am-form-field" placeholder="端电压">
									  <span class="am-input-group-label">V</span>
									</div>
								</td>
							</tr>
							<tr>
								<td style="vertical-align: middle;" colspan="2">
									<div class="am-form-group">
								      <textarea class="" rows="3" id="remark_other" placeholder="异常描述"><c:if test="${dr.batteryQuality eq '其他异常'}">${dr.remark}</c:if></textarea>
								    </div>
								</td>
							</tr>
					  	</table>
					</div>
				</div>
			</td>
		</tr>
  	</table>
  	
	<div class="app-div-btn-tool" style="margin-top: 18px">
		<div class="am-g">
			<div class="am-u-sm-6">
				<button  onclick="prevStep();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
					<i class="am-icon-arrow-left"></i>&nbsp;<strong>上一步</strong>
				</button>
			</div>
			<div class="am-u-sm-6">
				<button  onclick="saveAndNextStep();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
					<strong>下一步</strong>&nbsp;<i class="am-icon-arrow-right"></i>
				</button>
			</div>
		</div>
	</div>
	<div>&nbsp;</div>
</form>
<div class="am-modal am-modal-alert" tabindex="-1" id="modal-alert">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">提示</div>
    <div class="am-modal-bd" id="modal-alert-msg">
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
    </div>
  </div>
</div>
</body>
<script type="text/javascript">
initTabs();
var process = -1;
var theTimer;
function startTheTimer(){
	$('#startTheTimer').attr('disabled','disabled');
	process = 0;
	theTimer=setInterval("timerDoing()",1000);
}
function timerDoing(){
	var remainingTime = 1800 - process;
	var minute = Math.floor(remainingTime/60) < 10 ? "0"+Math.floor(remainingTime/60) : Math.floor(remainingTime/60);
	var second = remainingTime % 60 < 10 ? "0"+ (remainingTime % 60 ) : (remainingTime % 60 );
	$("#timeProcess").width(process/18+"%");
	$("#timeProcess").addClass("am-progress-bar-success");
	$("#remainingTime").html(minute + ":" + second);
	if(300==process){
		modalAlert("请填写放电5分钟记录");
	}else if((process/18) == 100){
		clearInterval(theTimer);
		$('#startTheTimer').attr('disabled','disabled');
	}
	process++;
}

var batteryQualityType="整组不合格";
function changeBatteryQT(type){
	batteryQualityType=type;
}

var step="next";
function prevStep(){
	step="prev";
	saveAndNextStep();
}
function saveAndNextStep(){
	if(batteryQualityType=="整组不合格"){
		saveGroupUnqualified();
	}else if(batteryQualityType=="整组合格"){
		saveGroupQualified();
	}else if(batteryQualityType=="其他异常"){
		saveOtherException();
	}
}
function recoverParamerter(json){
	if(json.succ){
		if("prev"==step){
			window.location.href="${ctx}/checkHiddenTrouble/toAdjust";
		}else{
			window.location.href="${ctx}/checkHiddenTrouble/recoverParamerter";
		}
	}
}
function saveGroupUnqualified(){
	
	var fiveTerminalVoltagev=$.trim($("#fiveTerminalVoltage").val());
	var dischargeLength_unqualified=$.trim($("#dischargeLength_unqualified").val());
	
	var msg="";
	if(""==fiveTerminalVoltagev){
		msg="请填写5分钟端电压";
	}
	if(""!=msg){
		modalAlert(msg);
		return;
	}
	$.post("${ctx}/checkHiddenTrouble/saveOrUpdateDischargeRecord.json",{
		batteryQuality:"整组不合格",
		fiveTerminalVoltage:fiveTerminalVoltagev,
		dischargeLength:dischargeLength_unqualified
	},function(json){
		recoverParamerter(json);
	},"json");
}

function saveGroupQualified(){
	var fiveTerminalVoltagev=$.trim($("#fiveTerminalVoltage").val());
	var terminalVoltage_allq=$.trim($("#terminalVoltage_allq").val());
	var remark_allq=$.trim($("#remark_allq").val());
	
	var voltageArr=new Array();
	$("input[name=voltage]").each(function(i) {
		var oid=$(this).attr("id").split("_");
		var oval=$(this).val();
		var orecord_id= $(this).attr("data-pk");
		if(""!=oval){
			var batteryD={sequence:oid[0],recordValue:oval,batteryGroup:oid[1],record_id:orecord_id};
			voltageArr.push(batteryD);
		}
	});
	var msg="";
	if(""==fiveTerminalVoltagev){
		msg="请填写5分钟端电压";
	}
	if(""!=msg){
		modalAlert(msg);
		return;
	}
	$.post("${ctx}/checkHiddenTrouble/saveOrUpdateDischargeRecord.json",{
		batteryQuality:"整组合格",
		fiveTerminalVoltage:fiveTerminalVoltagev,
		dischargeLength:30,
		terminalVoltage:terminalVoltage_allq,
		remark:remark_allq,
		voltageList:JSON.stringify(voltageArr)
	},function(json){
		recoverParamerter(json);
	},"json");
}

function saveOtherException(){
	var fiveTerminalVoltagev=$.trim($("#fiveTerminalVoltage").val());
	var dischargeLength_other=$.trim($("#dischargeLength_other").val());
	var terminalVoltage_other=$.trim($("#terminalVoltage_other").val());
	var remark_other=$.trim($("#remark_other").val());
	var msg="";
	if(""==fiveTerminalVoltagev){
		msg="请填写5分钟端电压";
	}
	if(""!=msg){
		modalAlert(msg);
		return;
	}
	$.post("${ctx}/checkHiddenTrouble/saveOrUpdateDischargeRecord.json",{
		batteryQuality:"其他异常",
		fiveTerminalVoltage:fiveTerminalVoltagev,
		dischargeLength:dischargeLength_other,
		terminalVoltage:terminalVoltage_other,
		remark:remark_other
	},function(json){
		recoverParamerter(json);
	},"json");
}



var batteryQuality='${dr.batteryQuality}';
function setBatteryQuality(){
	if(batteryQuality=="整组不合格"){
		$("#unqualified").triggerHandler("click");
	}else if(batteryQuality=="整组合格"){
		$("#allq").triggerHandler("click");
	}else if(batteryQuality=="其他异常"){
		$("#other").triggerHandler("click");
	}
}
setBatteryQuality();

var bdlist='${batteryDetailList}';
if(''!=bdlist){
	bdlist=JSON.parse(bdlist);
}
function SetBdList(){
	if(null!=bdlist && ''!=bdlist){
		for(var i=0;i<bdlist.length;i++){
			var bd=bdlist[i];
			$("#"+bd.sequence+"_"+bd.batteryGroup).val(bd.recordValue);
			$("#"+bd.sequence+"_"+bd.batteryGroup).attr("data-pk",bd.record_id);
		};
	};
}
SetBdList();
</script>
</html>