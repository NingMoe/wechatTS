<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	<title>设备核查</title>
	<%@include file="../commons/head.jsp"%>
	<%@include file="../commons/mobiscrollDatePlugin.jsp" %>
	<style type="text/css">
		input::-webkit-outer-spin-button,
		input::-webkit-inner-spin-button {
		    -webkit-appearance: none !important;
		    margin: 0;
		}
	</style>
	<script type="text/javascript">
	function prevStep(){
		$("#step").val("prev");
		$("#addSwitchPowerAndBattery").submit();
		//window.location.href="${ctx}/checkHiddenTrouble/goStationInfo?stationId=${s_station.stationId }";
	}
		
	function saveAndNextStep(){
		//showLoading();
		$("#addSwitchPowerAndBattery").submit();
	}
	function bondingStripChange(el){
		var id = $(el).attr("id");
		if('bondingStrip_f' == id) {
			$("#bondingStrip_fs").removeAttr("checked"); 
			$("#bondingStrip_s").removeAttr("checked");
			$("#bondingStrip_f").attr("checked","checked"); 
		} else if('bondingStrip_fs' == id) {
			$("#bondingStrip_f").removeAttr("checked");
			$("#bondingStrip_fs").attr("checked","checked");
		} else if('bondingStrip_s' == id) {
			$("#bondingStrip_f").removeAttr("checked");
			$("#bondingStrip_s").attr("checked","checked");
		} 
	}
	
	function isInflationChange(el){
		var isInflation = $('input[name="isInflation"]:checked ').val();
		if('否' == isInflation) {
			$("#inflationNum").val('');
			$("#inflationNum").attr("disabled", "disabled");
		} else if('是' == isInflation) {
			$("#inflationNum").removeAttr("disabled");
		}
	}

	function isCrackedChange(el) {
		var isInflation = $('input[name="isCracked"]:checked ').val();
		if('否' == isInflation) {
			$("#crackedNum").val('');
			$("#crackedNum").attr("disabled", "disabled");
		} else if('是' == isInflation) {
			$("#crackedNum").removeAttr("disabled");
		}
	};
	
	function isLeakageChange(el) {
		var id = $(el).attr("id");
		if('isLeakage_f' == id) {
			$("#leakageNum").val('');
			$("#leakageNum").attr("disabled", "disabled");
			$("#isLeakage_k").removeAttr("checked"); 
			$("#isLeakage_j").removeAttr("checked");
			$("#isLeakage_f").attr("checked","checked"); 
		} else if('isLeakage_k' == id) {
			$("#leakageNum").removeAttr("disabled");
			$("#isLeakage_f").removeAttr("checked");
			$("#isLeakage_k").attr("checked","checked"); 
		} else if('isLeakage_j' == id) {
			$("#leakageNum").removeAttr("disabled");
			$("#isLeakage_f").removeAttr("checked");
			$("#isLeakage_j").attr("checked","checked"); 
		} 
	};
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
			<form class="am-form" id="addSwitchPowerAndBattery" action="${ctx}/checkHiddenTrouble/addSwitchPowerAndBattery" method="post" data-am-validator enctype="multipart/form-data">
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
					<tr>
						<td colspan="2">
							<p>外观照：</p>
							
							<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
								<c:forEach items="${yhSwitchPowerPhotoInfos}" var="item">
									<li>
							   			<div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
							   				<div style="height:72px;margin:2px;overflow:hidden;">
							   					<img  src="${item.thumbLocation}" data-rel="${item.fileLocation}"  alt=" " style="border:none;" />
							   				</div>
							   				<h3 class="am-gallery-title am-fr" onclick="deleteSelectPhotoServer('${ctx}','${item.recordId}',this)">删除  <i class="am-icon-trash-o am-icon-sm" style="margin-top: 6px;color:#dd514c;opacity:100;"></i></h3>
							   		    </div>
							   		</li>
								</c:forEach>
								<li id="switchPower_add" onclick="void(0);">
									 <div onclick="void(0)">
										<div class="am-form-group am-form-file">
										<button type="button" class="app-add-photo"></button>
											<input id="" type="file" name="file" onchange="selectPhoto(this,'switchPower_add','switchPowerPhoto','${ctx}')" capture="camera" >
										</div>
									</div>
								</li>
							</ul>
							<input type="hidden" id="switchPowerPhoto" name="switchPowerPhoto" class="localIds"/>
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
						<td style="vertical-align: middle;width:33%;"><span style="color: red">*</span>品牌：</td>
						<td style="vertical-align: middle;">
							<select data-am-selected="{maxHeight: 200}" id="batteryBrand" name="batteryBrand">
								<option value="" selected = "selected">请选择</option>
								<option value="双登" <c:if test="${yhBatteryBO.batteryBrand eq '双登'}"> selected="selected" </c:if>>双登</option>
								<option value="光宇" <c:if test="${yhBatteryBO.batteryBrand eq '光宇'}"> selected="selected" </c:if>>光宇</option>
								<option value="圣阳" <c:if test="${yhBatteryBO.batteryBrand eq '圣阳'}"> selected="selected" </c:if>>圣阳</option>
								<option value="南都" <c:if test="${yhBatteryBO.batteryBrand eq '南都'}"> selected="selected" </c:if>>南都</option>
								<option value="理士" <c:if test="${yhBatteryBO.batteryBrand eq '理士'}"> selected="selected" </c:if>>理士</option>
								<option value="山东华日" <c:if test="${yhBatteryBO.batteryBrand eq '山东华日'}"> selected="selected" </c:if>>山东华日</option>
								<option value="有利" <c:if test="${yhBatteryBO.batteryBrand eq '有利'}"> selected="selected" </c:if>>有利</option>
								<option value="风帆" <c:if test="${yhBatteryBO.batteryBrand eq '风帆'}"> selected="selected" </c:if>>风帆</option>
								<option value="彩虹" <c:if test="${yhBatteryBO.batteryBrand eq '彩虹'}"> selected="selected" </c:if>>彩虹</option>
								<option value="东北" <c:if test="${yhBatteryBO.batteryBrand eq '东北'}"> selected="selected" </c:if>>东北</option>
								<option value="丰日" <c:if test="${yhBatteryBO.batteryBrand eq '丰日'}"> selected="selected" </c:if>>丰日</option>
								<option value="吉天利" <c:if test="${yhBatteryBO.batteryBrand eq '吉天利'}"> selected="selected" </c:if>>吉天利</option>
								<option value="科士达" <c:if test="${yhBatteryBO.batteryBrand eq '科士达'}"> selected="selected" </c:if>>科士达</option>
								<option value="利克" <c:if test="${yhBatteryBO.batteryBrand eq '利克'}"> selected="selected" </c:if>>利克</option>
								<option value="普天" <c:if test="${yhBatteryBO.batteryBrand eq '普天'}"> selected="selected" </c:if>>普天</option>
								<option value="天河" <c:if test="${yhBatteryBO.batteryBrand eq '天河'}"> selected="selected" </c:if>>天河</option>
								<option value="西恩迪" <c:if test="${yhBatteryBO.batteryBrand eq '西恩迪'}"> selected="selected" </c:if>>西恩迪</option>
								<option value="Super safe" <c:if test="${yhBatteryBO.batteryBrand eq 'Super safe'}"> selected="selected" </c:if>>Super safe</option>
								<option value="阳光" <c:if test="${yhBatteryBO.batteryBrand eq '阳光'}"> selected="selected" </c:if>>阳光</option>
								<option value="银泰" <c:if test="${yhBatteryBO.batteryBrand eq '银泰'}"> selected="selected" </c:if>>银泰</option>
								<option value="宇奥" <c:if test="${yhBatteryBO.batteryBrand eq '宇奥'}"> selected="selected" </c:if>>宇奥</option>
								<option value="灯塔" <c:if test="${yhBatteryBO.batteryBrand eq '灯塔'}"> selected="selected" </c:if>>灯塔</option>
								<option value="华达" <c:if test="${yhBatteryBO.batteryBrand eq '华达'}"> selected="selected" </c:if>>华达</option>
						  	</select>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;"><span style="color: red">*</span>型号：</td>
						<td style="vertical-align: middle;">
							<select data-am-selected="{maxHeight: 200}" id="batteryModel" name="batteryModel">
								<option value="" selected = "selected">请选择</option>
								<option value="GFM-100" <c:if test="${yhBatteryBO.batteryModel eq 'GFM-100'}"> selected="selected" </c:if>>GFM-100</option>
								<option value="GFM-1000" <c:if test="${yhBatteryBO.batteryModel eq 'GFM-1000'}"> selected="selected" </c:if>>GFM-1000</option>
								<option value="GFM-150" <c:if test="${yhBatteryBO.batteryModel eq 'GFM-150'}"> selected="selected" </c:if>>GFM-150</option>
								<option value="GFM-1500" <c:if test="${yhBatteryBO.batteryModel eq 'GFM-1500'}"> selected="selected" </c:if>>GFM-1500</option>
								<option value="GFM-200" <c:if test="${yhBatteryBO.batteryModel eq 'GFM-200'}"> selected="selected" </c:if>>GFM-200</option>
								<option value="GFM-300" <c:if test="${yhBatteryBO.batteryModel eq 'GFM-300'}"> selected="selected" </c:if>>GFM-300</option>
								<option value="GFM-3000" <c:if test="${yhBatteryBO.batteryModel eq 'GFM-3000'}"> selected="selected" </c:if>>GFM-3000</option>
								<option value="GFM-400" <c:if test="${yhBatteryBO.batteryModel eq 'GFM-400'}"> selected="selected" </c:if>>GFM-400</option>
								<option value="GFM-500" <c:if test="${yhBatteryBO.batteryModel eq 'GFM-500'}"> selected="selected" </c:if>>GFM-500</option>
								<option value="GFM-600" <c:if test="${yhBatteryBO.batteryModel eq 'GFM-600'}"> selected="selected" </c:if>>GFM-600</option>
								<option value="GFM-650" <c:if test="${yhBatteryBO.batteryModel eq 'GFM-650'}"> selected="selected" </c:if>>GFM-650</option>
								<option value="GFM-700" <c:if test="${yhBatteryBO.batteryModel eq 'GFM-700'}"> selected="selected" </c:if>>GFM-700</option>
								<option value="GFM-800" <c:if test="${yhBatteryBO.batteryModel eq 'GFM-800'}"> selected="selected" </c:if>>GFM-800</option>
								<option value="GFM-900" <c:if test="${yhBatteryBO.batteryModel eq 'GFM-900'}"> selected="selected" </c:if>>GFM-900</option>
						  	</select>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;"><span style="color: red">*</span>单组容量：（Ah）</td>
						<td style="vertical-align: middle;">
							<input type="number" id="singleCapacity" name="singleCapacity" class="am-form-field" placeholder="输入单组容量" value="${yhBatteryBO.singleCapacity}" required/>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;"><span style="color: red">*</span>组数（组）：</td>
						<td style="vertical-align: middle;">
							<input type="number" id="groupNum" name="groupNum" class="am-form-field" placeholder="输入组数"  <c:if test="${!empty yhBatteryBO}"> value="${yhBatteryBO.groupNum}" </c:if> <c:if test="${empty yhBatteryBO}"> value="2" </c:if> required/>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;"><span style="color: red">*</span>单体电压（V）：</td>
						<td style="vertical-align: middle;">
							<input type="number" id="cellVoltage" name="cellVoltage" class="am-form-field" placeholder="输入单体电压" value="${yhBatteryBO.cellVoltage}" required/>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;"><span style="color: red">*</span>浮充电压（V）：</td>
						<td style="vertical-align: middle;">
							<input type="number" id="floatingChargeVoltage" name="floatingChargeVoltage" class="am-form-field" placeholder="输入浮充电压" value="${yhBatteryBO.floatingChargeVoltage}" required/>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;"><span style="color: red">*</span>浮充电流（A）：</td>
						<td style="vertical-align: middle;">
							<input type="number" id="floatingChargeCurrent" name="floatingChargeCurrent" class="am-form-field" placeholder="输入浮充电流" value="${yhBatteryBO.floatingChargeCurrent}" required/>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;"><span style="color: red">*</span>启用日期：</td>
						<td style="vertical-align: middle;">
							<input type="text" name="startdate" id="startdate" class="am-form-field" placeholder="请选择启用日期"  value="<fmt:formatDate value="${yhBatteryBO.startdate}" pattern="yyyy-MM-dd" />" readonly/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<p>外观照：</p>
							<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
								<c:forEach items="${yhBatteryPhotoInfos}" var="item">
									<li>
							   			<div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
							   				<div style="height:72px;margin:2px;overflow:hidden;">
							   					<img src="${item.thumbLocation}" data-rel="${item.fileLocation}" alt=" " style="border:none;" />
							   				</div>
							   				<h3 class="am-gallery-title am-fr" onclick="deleteSelectPhotoServer('${ctx}','${item.recordId}',this)">删除  <i class="am-icon-trash-o am-icon-sm" style="margin-top: 6px;color:#dd514c;opacity:100;"></i></h3>
							   		    </div>
							   		</li>
								</c:forEach>
								<li id="battery_add" onclick="void(0);">
									 <div onclick="void(0)">
										<div class="am-form-group am-form-file">
										<button type="button" class="app-add-photo"></button>
											<input id="" type="file" name="file"  onchange="selectPhoto(this,'battery_add','batteryPhoto','${ctx}')" capture="camera" >
										</div>
									</div>
								</li>
							</ul>
							<input type="hidden" id="batteryPhoto" name="batteryPhoto" class="localIds"/>
						</td>
					</tr>
				</tbody>
			</table>
			<table class="am-table app-tb">
				<tbody>
					<tr>
						<th style="border-top: none;" colspan="2">
							蓄电池物理检查
						</th>
					</tr>
					<tr>
						<td style="vertical-align: middle; width: 33%">
							<span style="color: red">*</span>鼓胀： 
						</td>
						<td style="vertical-align: middle;">
							 <div style="height: auto;margin-bottom: 0;">
							    <input type="radio" value="是" id="isInflation_t" checked="checked" onchange="isInflationChange(this)" name="isInflation" class="isInflation">
								<label for="hz" style="margin-bottom: 0"> 是 </label>
								<input type="number" id="inflationNum" name="inflationNum" class="am-form-field" style="width:50px;height:28px;display:inline;padding:0px;" placeholder="" value="${yhBatteryBO.inflationNum}" required/>只&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" value="否" id="isInflation_f" onchange="isInflationChange(this)" name="isInflation" class="isInflation">
								<label for="cg" style="margin-bottom: 0"> 否 </label>
							 </div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle; width: 99px">
							<span style="color: red">*</span>开裂： 
						</td>
						<td style="vertical-align: middle;">
							 <div style="height: auto;margin-bottom: 0;">
							    <input type="radio" value="是" id="isCracked_t" onchange="isCrackedChange(this)" name="isCracked" checked="checked" class="isCracked">
								<label for="hz" style="margin-bottom: 0"> 是 </label>
								<input type="number" id="crackedNum" name="crackedNum" class="am-form-field" style="width:50px;height:28px;display:inline;padding:0px;" placeholder="" value="${yhBatteryBO.crackedNum}" required/>只&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<input type="radio" value="否" id="isCracked_f"  onchange="isCrackedChange(this)" name="isCracked" class="isCracked">
								<label for="cg" style="margin-bottom: 0"> 否 </label>
							 </div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle; width: 99px">
							<span style="color: red">*</span>漏液： 
						</td>
						<td style="vertical-align: middle;">
							 <div style="height: auto;margin-bottom: 0;">
							    <input type="checkbox" value="壳体" id="isLeakage_k"  onchange="isLeakageChange(this)" name="isLeakage" class="isLeakage">
								<label for="hz" style="margin-bottom: 0"> 壳体 </label>
								<input type="checkbox" value="极柱" id="isLeakage_j"  onchange="isLeakageChange(this)" name="isLeakage" class="isLeakage">
								<label for="hz" style="margin-bottom: 0"> 极柱 </label>
								<input type="number" id="leakageNum" name="leakageNum" class="am-form-field" style="width:50px;height:28px;display:inline;padding:0px;"  placeholder="" value="${yhBatteryBO.leakageNum}" required/>只&nbsp;&nbsp;
								<input type="radio" value="否" id="isLeakage_f" onchange="isLeakageChange(this)" name="isLeakage" class="isLeakage">
								<label for="cg" style="margin-bottom: 0"> 否 </label>
							 </div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle; width: 99px">
							<span style="color: red">*</span>连接条： 
						</td>
						<td style="vertical-align: middle;">
							 <div style="height: auto;margin-bottom: 0;">
							    <input type="checkbox" value="腐蚀" id="bondingStrip_fs" onchange="bondingStripChange(this)" name="bondingStrip" class="bondingStrip">
								<label for="hz" style="margin-bottom: 0"> 腐蚀 </label>
								<input type="checkbox" value="松动" id="bondingStrip_s" onchange="bondingStripChange(this)" name="bondingStrip" class="bondingStrip">
								<label for="hz" style="margin-bottom: 0"> 松动 </label>
								<input type="radio" value="否" id="bondingStrip_f" onchange="bondingStripChange(this)" name="bondingStrip" class="bondingStrip">
								<label for="cg" style="margin-bottom: 0"> 否 </label>
							 </div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<p>异常照片：</p>
							<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
								<c:forEach items="${yhBatteryExceptionPhotoInfos}" var="item">
									<li>
							   			<div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
							   				<div style="height:72px;margin:2px;overflow:hidden;">
							   					<img src="${item.thumbLocation}" data-rel="${item.fileLocation}" alt=" " style="border:none;" />
							   				</div>
							   				<h3 class="am-gallery-title am-fr" onclick="deleteSelectPhotoServer('${ctx}','${item.recordId}',this)">删除  <i class="am-icon-trash-o am-icon-sm" style="margin-top: 6px;color:#dd514c;opacity:100;"></i></h3>
							   		    </div>
							   		</li>
								</c:forEach>
								<li id="exception_add" onclick="void(0);">
									 <div onclick="void(0)">
										<div class="am-form-group am-form-file">
										<button type="button" class="app-add-photo"></button>
											<input type="file" name="file" onchange="selectPhoto(this,'exception_add','exceptionPhoto','${ctx}')" capture="camera" >
										</div>
									</div>
								</li>
							</ul>
							<input type="hidden" id="exceptionPhoto" name="exceptionPhoto" class="localIds"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" style="vertical-align: middle">
							<textarea id="remark" name="remark" rows="3" cols="" class="am-form-field" placeholder="填写备注" >${yhBatteryBO.remark}</textarea>
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
		
		<div class="am-modal am-modal-loading" tabindex="-1" id="modal-loading">
		  <button type="button" class="am-btn am-radius am-modal-dialog" style="width:200px;background-color: black;filter:alpha(opacity:60);opacity:0.6;">
		    <div class="am-modal-hd" style="font-size: 14px;color: white;" id="modal-loading-msg">正在载入...</div>
		    <div class="am-modal-bd">
		      <span class="am-icon-spinner am-icon-spin" style="color: white;"></span>
		    </div>
		  </button>
		</div>
	</body>
<script type="text/javascript">
!function(t) {
	$("ul[data-am-widget='gallery']").one("click",function(){//点击预览图片关闭预览
		$(".am-pureview-slider").on("click",function(){
			$(".am-icon-chevron-left[data-am-close='pureview']").triggerHandler("click");
		});
	});
	
	var isInflation = '${yhBatteryBO.isInflation}';
	if('否' == isInflation) {
		$("#inflationNum").attr("disabled", "disabled");
	}
	if(""==isInflation ){
		isInflation="否";
		$("#inflationNum").attr("disabled", "disabled");
	}
	$("input[name='isInflation'][value="+isInflation+"]").attr("checked",true); 
	
	var isCracked = '${yhBatteryBO.isCracked}';
	if('否' == isCracked) {
		$("#crackedNum").attr("disabled", "disabled");
	}
	if(""==isCracked ){
		isCracked="否";
		$("#crackedNum").attr("disabled", "disabled");
	}
	$("input[name='isCracked'][value="+isCracked+"]").attr("checked",true); 
	
	var isLeakages = '${yhBatteryBO.isLeakage}';
	if('否' == isLeakages) {
		$("#leakageNum").attr("disabled", "disabled");
	}
	if(""==isLeakages ){
		isLeakages="否";
		$("#leakageNum").attr("disabled", "disabled");
	}
	var isLeakage = isLeakages.split(',');
	for(var i = 0; i < isLeakage.length; i++) {
		$("input[name='isLeakage'][value="+isLeakage[i]+"]").attr("checked",true); 
	}
	
	var bondingStrips = '${yhBatteryBO.bondingStrip}';
	if(""==bondingStrips ){
		bondingStrips="否";
	}
	var bondingStrip = bondingStrips.split(',');
	for(var i = 0; i < bondingStrip.length; i++) {
		$("input[name='bondingStrip'][value="+bondingStrip[i]+"]").attr("checked",true); 
	}
	initMobiscroll("#startdate");
	
}(window.jQuery || window.Zepto);
</script>
</html>