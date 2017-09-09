<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
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
</style>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div onclick="toPageUrl('${ctx}/generatePower/electricIndex');">
			<span class="am-topbar-brand am-icon-angle-left"></span>
			<div class="am-topbar-brand  app-toolbar">
				单体电压测试
			</div>
		</div>
	</header>
	<div class="app-toolbar" style="text-align: center;background: #fff;padding: 6px;border-bottom: 1px #ddd solid">
		<div class="am-btn-group">
			<button id="discharge_btn" type="button" class="am-btn am-btn-default am-radius am-btn-sm">
			<span class="am-badge am-badge-primary am-radius">2</span>
				放电检测
			</button>
		</div>
	</div>
	<div id="dischargeContainer">
		<div id="headerc" class="am-scrollable-horizontal app-tabs-head"
			style="white-space: nowrap;">
			<table class="am-table am-table-striped am-text-nowrap"
				style="margin-bottom: 0px; border-bottom: none;">
				<tr class="app-tabs-title">
					<c:forEach items="${turntimeList}" var="turn">
						<c:forEach items="${checkRecordMap}" var="map">
							<c:if test="${map.key eq turn}">
								<td style="border-top: none"
									<c:if test="${map.value[0].checkTime eq 1}">class="app-tabs-selected  am-text-primary"</c:if>>
										第${map.value[0].checkTime}轮
										<i class="am-icon-clock-o"></i>
										<fmt:formatDate value="${map.value[0].finishTime}" pattern="HH:mm:ss" />
								</td>
							</c:if>
						</c:forEach>
					</c:forEach>
				</tr>
			</table>
		</div>
		<div class="app-tabs-bd" style="margin-bottom: 0px">
			<form id="formContainer" class="am-form"
				action="${ctx}/discharge/saveDischargeRecordStepTwo" method="post">
				<c:forEach items="${turntimeList}" var="turn">
					<c:forEach items="${checkRecordMap}" var="map">
						<c:if test="${map.key eq turn}">
							<div data-am-widget="list_news"
								class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0"
								style="display: none;">
								<c:forEach items="${map.value}" var="item" varStatus="status">
									<div class="am-accordion-content am-margin-0 am-padding-0">
										<table class="am-table app-tb">
											<tr onclick="viewBatteryDetail('${item.batteryGroupId}')">
												<td style="width: 30%;height: 45px;vertical-align: middle;">
													<i class="am-icon-bolt"></i>
													蓄电池${status.index+1}
												</td>
												<td style="vertical-align: middle;text-align: right;">
													查看&nbsp;&nbsp;&nbsp;&nbsp;
													<i class="am-icon-angle-right am-icon-sm am-fr" style="color:#63B8FF;"></i>
												</td>
											</tr>
											<tr>
												<td>
													录音：
												</td>
												<td style="padding: 3px 0 3px 0">
												<c:if test="${empty item.soundList}">
												<span class="voice" name="start" style="display: inline;" onclick="dealVoice(this,'${ctx}')">
													<label style="background-color: #99ee33; border-radius: 7px; padding: 6px; margin: 0px;width: 80%">&nbsp;&nbsp;&nbsp;
														<i style="color: #669933" class="am-icon-microphone"></i>
														<span style="color: #669933" class="statusTips">开始录音</span>
													</label> 
												</span>
												<span class="deleteVoice" style="display: none;" onclick="deleteVoice(this,'${ctx}')"><i style="color: #D7342E" class="am-icon-refresh"></i>重录</span>
									 			<span><input id=${item.recordId} type="hidden" class="voiceRecordIds" name="recordIds" value="" /></span>
												<span><input type="hidden" class="voiceIds" name="voiceIds" value=""/></span>
												<span><input type="hidden" name="tempVoiceId" value=""/></span>
												</c:if>
												<c:if test="${not empty item.soundList}">
													<span class="voice" name="playVoice" style="display: inline;" onclick="dealVoice(this,'${ctx}')">
														<label style="background-color: #99ee33; border-radius: 7px; padding: 6px; margin: 0px;width: 80%">&nbsp;&nbsp;&nbsp;
															<i style="color: #669933" class="am-icon-volume-up"></i>
																<span style="color: #669933" class="statusTips">点击播放</span>
														</label> 
													</span>
													<span class="deleteVoice" style="display: inline;" onclick="deleteVoice(this,'${ctx}')"><i style="color: #D7342E" class="am-icon-refresh"></i>重录</span>
										 			<span><input id=${item.recordId} type="hidden" class="voiceRecordIds" name="recordIds" value="<c:forEach items="${item.soundList}" var="sound" varStatus="status"><c:if test="${status.index eq 0}">${sound.mediaId}</c:if><c:if test="${status.index ne 0}">,${sound.mediaId}</c:if></c:forEach>" /></span>
													<span><input type="hidden" class="voiceIds" name="voiceIds" value="<c:forEach items="${item.soundList}" var="sound" varStatus="status"><c:if test="${status.index eq 0}">${sound.soundId}</c:if><c:if test="${status.index ne 0}">,${sound.soundId}</c:if></c:forEach>"/></span>
													<span><input type="hidden" name="tempVoiceId" value=""/></span>
												</c:if>
												</td>
											</tr>
											<tr>
												<td colspan="2">
													输入单体电压记录
													<div style="border-top: 1px #ddd solid; border-left: 1px #ddd solid; width: 100%">
														<ul class="am-avg-sm-6">
															<c:forEach items="${item.detailList}" var="detail" varStatus="status">
																<li class="am-padding-0" style="border:none;">
																	<c:if test="${detail.recordValue eq 0.0}">
																		<input type="number" name="voltage" style="border-left:none;border-top:none;background-color: #EDEEEF" 
																			onblur="saveBatteryV(this,'${item.batteryGroupId}','${turn}','${detail.sequence}')"></input>
																	</c:if>
																	<c:if test="${detail.recordValue ne 0.0}">
																		<input type="number" name="voltage" style="border-left:none;border-top:none;background-color: #EDEEEF" 
																			onblur="saveBatteryV(this,'${item.batteryGroupId}','${turn}','${detail.sequence}')" value="${detail.recordValue}"></input>
																	</c:if>
																</li>
															</c:forEach>
														</ul>
													</div>
												</td>
											</tr>
											<tr>
												<td style="vertical-align: middle;">
													端电压：
												</td>
												<td>
													<div class="am-input-group">
														<input type="number" class="am-form-field" placeholder="" value="${item.terminalVoltage}"
															onblur="saveTerminalV(this,'${item.batteryGroupId}','${turn}')"/>
														<span class="am-input-group-label">V</span>
													</div>
												</td>
											</tr>
										</table>
									</div>
								</c:forEach>
							</div>
						</c:if>
					</c:forEach>
				</c:forEach>
			</form>
		</div>
		<div class="app-div-btn-tool">
			<button onclick="submitNext()" type="button"
				class="am-btn am-btn-success am-radius am-btn-block">
				<strong>下一步</strong>
			</button>
		</div>
		<div style="height: 10px"></div>
	</div>
	<div class="am-modal am-modal-confirm" tabindex="-1" id="finish-confirm">
		<button type="button" class="am-btn am-radius am-modal-dialog" style="width: 100%">
			<div id="result" class="am-modal-bd am-radius"></div>
			<div class="am-modal-footer am-radius">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span>
				<span class="am-modal-btn" data-am-modal-confirm>确定</span>
			</div>
		</button>
	</div>
<script type="text/javascript">
	!function(t) {
		initTabs();
	}(window.jQuery || window.Zepto);
	function saveBatteryV(obj,group_id,turn_num,check_num){
		var v = $(obj).val();
		var url = "${ctx}/generatePowerSingleBatteryCheck/saveBatteryCheckRecord?groupId="+group_id+"&turnNum="+turn_num+"&checkNum="+check_num+"&voltage="+v;
		$.get(url,function(data,status){
			if(status=="success"){
				//nothing
			}
		});
	}
	function saveTerminalV(obj,group_id,turn_num){
		var v = $(obj).val();
		var url = "${ctx}/generatePowerSingleBatteryCheck/saveTerminalVoltage?groupId="+group_id+"&turnNum="+turn_num+"&voltage="+v;
		$.get(url,function(data,status){
			if(status=="success"){
				//nothing
			}
		});
	}
	function submitNext(){
		showLoading("正在提交中...");
		var url="${ctx}/generatePower/checkBatteryCheckStatus.json";
		$.get(url,function(json) {
			closeLoading();
			if (json.status == "0") {
				window.location.href="${ctx}/generatePowerSingleBatteryCheck/saveBatteryCheckStepTwo";
			} else {
				$("#result").html(json.status);
				$("#finish-confirm").modal({
					relatedTarget : this,
					onConfirm : function(
							options) {
						window.location.href="${ctx}/generatePowerSingleBatteryCheck/saveBatteryCheckStepTwo?detectionStatus=skip";
					},
					onCancel : function() {
						//do nothing
					}
				});
			}
		}, "json");
	}
	function viewBatteryDetail(batteryId) {
		window.location.href="${ctx}/discharge/getBatteryInfo?batteryId="+batteryId;
	}
	//==============================================录音JS
	 var voice = {
	    localId: '',
	    serverId: ''
	  };
	wx.config(${jsConfig});
	wx.error(function(res) {
		modalAlert("网络出现问题，请稍后重试", null);
	});
	wx.ready(function () {
		//监听录音自动结束
		wx.onVoiceRecordEnd({
		    complete: function (res) {
		      voice.localId = res.localId;
		      var inputIds = $(".recordOn").find('input[name="recordIds"]').val();
			    var inputIdz;
			    if(inputIds.length>0){
			    inputIdz = inputIds.split(",");
			    inputIdz.push(voice.localId);
			    }else{
			    	inputIdz = voice.localId;
			    }
		      $('.recordOn').find('input[name="recordIds"]').val(inputIdz);
		      voice.localId = '';
		      //重新调用录音
		      wx.startRecord({
			      cancel: function () {
		    	  modalAlert('用户拒绝授权录音');
			      }
			    });
		    }
		  });
		//监听播放结束
	    wx.onVoicePlayEnd({
		    success: function (res) {
	    	var voiceIds = $('.playOn').find('input[name="recordIds"]').val();
			var voiceIdz = voiceIds.split(',');
			var i = 0;
			 for(j=0;j<voiceIdz.length;j++){
				 if(voiceIdz[j] == voice.localId){
					 var i = j;
				 }
			 }
	    	if(i<(voiceIdz.length-1)){
			    i++;
			    !function playVoice(){
					 voice.localId = voiceIdz[i];
					 wx.playVoice({
					      localId:voice.localId
					    });
				 }();
			    }else{
				    $('.playOn').find('input[name="tempVoiceId"]').val("");
				    $('.playOn').find('.voice').find('i').attr('class','am-icon-volume-up');
				    $('.playOn').find('.voice').attr('name','playVoice');
				    $('.playOn').find('.statusTips').text("重新播放");
			        $('.playOn').attr('class','playOff');
			        voice.localId = '';
			    }
		    }
	    });
	});
</script>
	</body>
</html>