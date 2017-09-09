<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>利旧放电</title>
<%@include file="../commons/head.jsp" %>
<%@include file="../commons/mobiscrollDatePlugin.jsp" %> 
</head>
<body>
	<header onclick="toPageUrl('${ctx}/hldischarge/goStart?selectId=2')" class="am-topbar am-topbar-fixed-top">
		<div ><span class="am-topbar-brand am-icon-angle-left" ></span>
			<div class="am-topbar-brand  app-toolbar">返回放电列表</div>
		</div>
	</header>
	<input type="hidden" name="selectId" value="3" id="selectId">
	<div class="am-cf">
		<table class="am-table" style="padding:0px;margin: 0px;margin-top: 5px;">
			<c:if test="${flag == true}">
				<tr style="background: #fff;">
						<td style="border-top: none;vertical-align: middle;margin-bottom: 0px;padding-bottom: 0px;border-bottom:1px solid rgb(221, 221, 221);" rowspan="2" onclick="saomaSelectDetail('${recordMsg.recordId}','${recordMsg.recordId}');">
							<div class="am-text-truncate" style="font-size:13px;">
								电池组条码：${recordMsg.recordId }<br/>
								测试计划名称：${recordMsg.planName }<br/>
								测试日期：<fmt:formatDate value="${recordMsg.testDate }" pattern="yyyy-MM-dd" /><br/>
								测试人员：${recordMsg.testUser }
							</div>
						</td>
						<td id="update_${recordMsg.recordId }" align="right" style="border-top: none;vertical-align: middle;margin-bottom: 0px;padding-bottom: 0px;" onclick="updateRecordByCode('${recordMsg.recordId}');">
								<button class="am-btn am-btn-xs am-btn-success">
									修改
								</button>
						</td>
					</tr>
					<tr style="background: #fff;">
						
						<td id="delete_${recordMsg.recordId }" align="right" style="border-top: none;border-bottom:1px solid rgb(221, 221, 221);vertical-align: middle;margin-bottom: 0px;padding-bottom: 0px;" onclick="deleteRecordByCode('${recordMsg.recordId}','${recordMsg.barCode}');" >
								<button class=" am-btn am-btn-danger am-btn-xs">
									删除
								</button>
						</td>
					</tr>
			</c:if >
			<c:if test="${flag == false}">
				<c:forEach items="${recordMsg }" var="obj">
					<tr style="background: #fff;">
						<td style="border-top: none;vertical-align: middle;margin-bottom: 0px;padding-bottom: 0px;border-bottom:1px solid rgb(221, 221, 221);" rowspan="2" onclick="saomaSelectDetail('${obj.recordId}','${obj.barCode}');">
							<div class="am-text-truncate" style="font-size:13px;">
								单体条码：${obj.barCode }<br/>
								测试计划名称：${obj.planName }<br/>
								测试日期：<fmt:formatDate value="${obj.testDate }" pattern="yyyy-MM-dd" /><br/>
								测试人员：${obj.testUser }
							</div>
						</td>
						<td id="update_${obj.recordId }" align="right" style="border-top: none;vertical-align: middle;margin-bottom: 0px;padding-bottom: 0px;" onclick="updateRecordByCode('${obj.recordId}');">
								<button class="am-btn am-btn-xs am-btn-success">
									修改
								</button>
						</td>
					</tr>
					<tr style="background: #fff;">
						
						<td id="delete_${obj.recordId }" align="right" style="border-top: none;border-bottom:1px solid rgb(221, 221, 221);vertical-align: middle;margin-bottom: 0px;padding-bottom: 0px;" onclick="deleteRecordByCode('${obj.recordId}','${obj.barCode}')" >
								<button class=" am-btn am-btn-danger am-btn-xs">
									删除
								</button>
						</td>
					</tr>
				</c:forEach>
			</c:if>		
		</table>
		
	</div>
	<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
				<div class="am-u-sm-12">
					<button  onclick="saomaSelect();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
						<strong>扫码查询</strong>
					</button>
				</div>
	</div>
<script type="text/javascript">
wx.config(${jsConfig});
wx.ready(function() {
	//nothing
});
wx.error(function(res) {
	modalAlert("网络出现问题，请稍后重试", null);
});
function updateRecordByCode(code){
	showLoading();
	window.location.href="${ctx}/hldischarge/toFillDischarge?selectId=1&saveOrUpdate=2&recordId="+code;
	
}
function deleteRecordByCode(recordId,barCode){
	
	modalConfirm("确定删除吗？",function(){
		if(barCode ==''){
			$.post($("#ctx").val()+"/hldischarge/deleteDischargeRecord.json", {
				recordId : recordId
			}, function(json) {
				if(json.succ){
					$("#delete_"+recordId).parent("tr").remove();
					$("#update_"+recordId).parent("tr").remove();
				}
				
			}, "json");
		}else{
			$.post($("#ctx").val()+"/hldischarge/deleteDischargeDetail.json", {
				recordId : recordId,
				barCode:barCode
			}, function(json) {
				if(json.serial){
					$("#delete_"+recordId).parent("tr").remove();
					$("#update_"+recordId).parent("tr").remove();
				}
				
			}, "json");
		}
		
	},null);
}
function saomaSelect() {
	// 扫描二维码
	wx.scanQRCode({
		needResult : 1,
		scanType : [ "qrCode", "barCode" ],
		success : function(res) {
			var indexss = res.resultStr.indexOf(",") + 1;
			var deviceId = res.resultStr.substr(indexss);
			if (deviceId.length == 13 || deviceId.length == 38 || deviceId.length == 42) {
				showLoading();
				setTimeout(function() {
					checkCode(deviceId);
				}, 5000);
			} else {
				closeLoading();
				modalAlert("此条码是无效条码", null);
			}
		},
		cancel : function(res) {
		},
		complete : function(res) {
		},
		fail : function(res) {
		}
	});
};
function checkCode(deviceId) {// 查询是否子条码已经存在
	$.post("../hldischarge/judgeBarCodeExist.json", {
		barCode : deviceId
	}, function(json) {
		if (json.succ) {
			closeLoading();
			saomaSelectByCode(deviceId);
		} else {
			modalAlert("此条码无放电记录", null);
			closeLoading();
			}
		}, "json");

};
function saomaSelectByCode(deviceId){
	window.location.href="${ctx}/hldischarge/selectMsgByCode?code="+deviceId;
}
function saomaSelectDetail(recordId,code){
	var selectId=$("#selectId").val();
	window.location.href="${ctx}/hldischarge/getDischargeDetail?recordId="+recordId+"&selectId="+selectId+"&code="+code;
}
</script>
</body>
</html>