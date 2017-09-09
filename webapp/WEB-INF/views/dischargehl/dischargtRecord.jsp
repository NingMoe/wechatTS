<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
		<input type="hidden" name="selectId" value="2" id="selectId">
		<input type="hidden" name="serial" value="${ serial}" id="serial">
	<div class="am-cf">
		<table class="am-table" style="padding:0px;margin: 0px;">
				<c:forEach items="${recordList}" var="obj" varStatus="status" >
					<tr style="background: #fff;">
						<td style="border-top: none;vertical-align: middle;margin-bottom: 0px;padding-bottom: 0px;" onclick="getDetail('${obj.recordId}');">
							<div class="am-text-truncate">
							<font size='3'>
								<c:if test='${"" ne obj.testDate && null ne obj.testDate}'>
									<fmt:formatDate value="${obj.testDate}" pattern="yyyy-MM-dd" />
								</c:if>测试
							</font>
							</div>
						</td>
						<td id="update_${obj.recordId}" align="right" style="border-top: none;vertical-align: middle;margin-bottom: 0px;padding-bottom: 0px;" onclick="updateRecord('${obj.recordId}')">
								<button class="am-btn am-btn-xs am-btn-success">
									修改
								</button>
						</td>
					</tr>
					<tr style="background: #fff;">
						<td style="border-top: none;border-bottom:1px solid rgb(221, 221, 221);" onclick="getDetail('${obj.recordId}');">
								测试计划名称:${obj.planName }&nbsp;&nbsp;&nbsp;测试人员:${obj.testUser }
						</td>
						<td id="delete_${obj.recordId}" align="right" style="border-top: none;border-bottom:1px solid rgb(221, 221, 221);vertical-align: middle;margin-bottom: 0px;padding-bottom: 0px;" onclick="deleteRecord('${obj.recordId}');" >
								<button class=" am-btn am-btn-danger am-btn-xs">
									删除
								</button>
						</td>
					</tr>
				</c:forEach>
		</table>
		
	</div>
	<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
				<div class="am-u-sm-6">
					<button  onclick="flashBtn();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
						<strong>刷新</strong>
					</button>
				</div>
				<div class="am-u-sm-6">
					<button  onclick="saomiaoSelect();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
						<strong>扫码查询</strong>
					</button>
				</div>
	</div>
<script type="text/javascript">
function updateRecord(recordId){
	showLoading();
	window.location.href="${ctx}/hldischarge/toFillDischarge?selectId=1&saveOrUpdate=2&recordId="+recordId;
	
}
function deleteRecord(recordId){
	
	modalConfirm("确定删除吗？",function(){
		$.post($("#ctx").val()+"/hldischarge/deleteDischargeRecord.json", {
			recordId : recordId,
			
		}, function(json) {
			if(json.succ){
				$("#delete_"+recordId).parent("tr").remove();
				$("#update_"+recordId).parent("tr").remove();
			}
			
		}, "json");
	},null);
}
function flashBtn(){
	window.location.href="${ctx}/hldischarge/toFillDischargeList?selectId=2&saveOrUpdate=2";
}

function getDetail(recordId){
	var selectId=$("#selectId").val();
	window.location.href="${ctx}/hldischarge/getDischargeDetail?recordId="+recordId+"&selectId="+selectId;
}

function saomiaoSelect() {
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
					saomiaoSelectCode(deviceId);
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
function saomiaoSelectCode(deviceId) {// 查询是否子条码已经存在
	$.post("../hldischarge/judgeBarCodeExist.json", {
		barCode : deviceId
	}, function(json) {
		if (json.succ) {
			closeLoading();
			selectDetailByCode(deviceId);
		} else {
			modalAlert("此条码无放电记录", null);
			closeLoading();
			}
		}, "json");

};
function selectDetailByCode(deviceId){
	window.location.href="${ctx}/hldischarge/selectMsgByCode?code="+deviceId;
}
</script>