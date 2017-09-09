<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<%@include file="../commons/mobiscrollDatePlugin.jsp" %>

<form id="formContainer" class="am-form" method="post" action="" data-am-validator>
	<table class="am-table app-tb">
		<tbody>
			<tr style="display:none;">
				<td style="vertical-align: middle;border-top: none;width: 30%">单体电压：</td>
				<td style="border-top: none;width: 70%">
					<div class="am-input-group">
						<input type="number" class="am-form-field" id="batteryVoltage" name="batteryVoltage" placeholder="" value="${s_generatepower_record.batteryVoltage}" required readonly/>
						<span class="am-input-group-label">V</span>
					</div>
		 		</td>
		 	</tr>
			<tr style="display:none;">
				<td style="vertical-align: middle; width: 120px">
					室内温度：
				</td>
				<td>
					<div class="am-input-group">
						<input name="temp" type="number" value="${s_generatepower_record.temp }" class="am-form-field" required readonly/>
						<span class="am-input-group-label">℃</span>
					</div>
				</td>
			</tr>
			<tr style="display:none;">
				<td style="vertical-align: middle">
					油机功率：
				</td>
				<td>
					<div class="am-input-group">
						<input name="oilEnginePower" type="number" value="${s_generatepower_record.oilEnginePower }" class="am-form-field" required readonly/>
						<span class="am-input-group-label">KW</span>
					</div>
				</td>
			</tr>
			<tr style="display:none;">
				<td style="vertical-align: middle">
					油机编号：
				</td>
				<td>
					<div class="am-input-group">
						<input name="oilEngineSerial" value="${s_generatepower_record.oilEngineSerial }" type="text" class="am-form-field"  />
					</div>
				</td>
			</tr>
			<tr style="display:none;">
				<td style="vertical-align: middle;">
					基站负载：
				</td>
				<td>
					<div class="am-input-group">
						<input name="dischargeCurrent" value="${s_generatepower_record.dischargeCurrent }" type="number" class="am-form-field" readonly/>
						<span class="am-input-group-label">A</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;">
					<span style="color: red">*</span>发电类型：
				</td>
				<td>
					<input value="<c:if test="${s_generatepower_record.generatePowerType eq '0'}">正常发电</c:if><c:if test="${s_generatepower_record.generatePowerType eq '1'}">发电补报</c:if>" type="text" class="am-form-field"  readonly/>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle; width: 120px"><span style="color: red">*</span>发电开始时间：</td>
				<td>
					<div class="am-input-group date">
					  <input size="16" id="powerCutTime" value="<fmt:formatDate value="${s_generatepower_record.powerCutTime }" pattern="yyyy-MM-dd  HH:mm" />" name="powerCutTime" type="text"  class="am-form-field" readonly>
					  <span class="am-input-group-label add-on"><i class="icon-th am-icon-calendar"></i></span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle; width: 120px"><span style="color: red">*</span>基站停电时间：</td>
				<td>
					<div class="am-input-group date">
					  <input size="16" id="stationPowercutTime" value="<fmt:formatDate value="${s_generatepower_record.stationPowercutTime }" pattern="yyyy-MM-dd  HH:mm" />" name="stationPowercutTime" type="text"  class="am-form-field" readonly>
					  <span class="am-input-group-label add-on"><i class="icon-th am-icon-calendar"></i></span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">
					<span style="color: red">*</span>发电开始电压：
				</td>
				<td>
					<div class="am-input-group">
						<input type="number" class="am-form-field" id="powercutVoltage" name="powercutVoltage" placeholder="" value="${s_generatepower_record.powercutVoltage}" readonly/>
						<span class="am-input-group-label">V</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;" colspan="2">
					<p>发电开始电压照片(开关电源显示电压)：</p>
					<ul id="startgallery" data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
						<c:forEach items="${s_generatepower_record.powerCutPhotos}" var="item">
						    <li id="${item.photoId}">
							    <div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
							     	<div style="height:72px;margin-bottom:2px;overflow:hidden;">
							       		<img src="${item.thumbLocation}" <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if>  alt="" data-rel="${item.fileLocation}"/>
							       	</div>
							       	<h3 class="am-gallery-title am-fr" onclick="deletePhotoInServer('${ctx}','${item.photoId}')">删除  <i class="am-icon-trash-o am-icon-sm" style="color:#dd514c;opacity:100;margin-top: 6px;"></i></h3>
							    </div>
							</li>
						</c:forEach>
						<li id="galleryAddBtn" onclick="takePhoto(this,'startTimeImg');" data-weg>
						    <div onclick="void(0)">
						     <button type="button" class="app-add-photo"></button>
						    </div>
						</li>
					</ul>
					<input  class="localIds" type="hidden" id="startTimeImg" name="startTimeImg"  value="" size="100"/>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle; width: 120px">
					<span style="color: red">*</span>发电结束电压
				</td>
				<td>
					<div class="am-input-group">
						<input type="number" class="am-form-field" id="endVoltage" name="endVoltage" placeholder="" value="${s_generatepower_record.endVoltage}" required />
						<span class="am-input-group-label">V</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle; width: 120px"><span style="color: red">*</span>发电结束时间：</td>
				<td>
					<div class="am-input-group date">
					  <input size="16" id="finishTime" name="finishTime" type="text"  class="am-form-field" readonly>
					  <span class="am-input-group-label add-on"><i class="icon-th am-icon-calendar"></i></span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;" colspan="2">
					<p>发电结束电压照片：</p>
					<ul id="endVoltageGallery" data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
						<c:forEach items="${s_generatepower_record.endVoltagePhotos}" var="item">
						    <li id="${item.photoId}">
							    <div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
							     	<div style="height:72px;margin-bottom:2px;overflow:hidden;">
							       		<img src="${item.thumbLocation}" <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if>  alt="" data-rel="${item.fileLocation}"/>
							       	</div>
							       	<h3 class="am-gallery-title am-fr" onclick="deletePhotoInServer('${ctx}','${item.photoId}')">删除  <i class="am-icon-trash-o am-icon-sm" style="color:#dd514c;opacity:100;margin-top: 6px;"></i></h3>
							    </div>
							</li>
						</c:forEach>
						<li id="galleryAddBtn1" onclick="takePhoto(this,'endVoltageImg');" data-weg>
						    <div onclick="void(0)">
						     <button type="button" class="app-add-photo"></button>
						    </div>
						</li>
					</ul>
					<input class="localIds" type="hidden" id="endVoltageImg" name="endVoltageImg"  value=""/>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="vertical-align: middle">
					<textarea id="" rows="3" class="am-form-field" name="remark" placeholder="填写备注信息"  >${s_generatepower_record.remark}</textarea>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
		<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px;">
			<a id="submigB" class="am-btn am-btn-success am-radius am-btn-block" href="javascript:submitFinish();"><strong style="color:#ffffff;">保存</strong></a>
		</div>
	</div>
	<div style="height: 10px"></div>
	<div class="am-modal am-modal-confirm" tabindex="-1" id="finish-confirm">
		<button type="button" class="am-btn am-radius am-modal-dialog">
			<div class="am-modal-bd am-radius">发电尚未完成，是否结束？</div>
			<div class="am-modal-footer am-radius">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span>
				<span class="am-modal-btn" data-am-modal-confirm>确定</span>
			</div>
		</button>
	</div>
	
</form>
<script type="text/javascript">
var powerCutTimeVal=$("#powerCutTime").val();
function submitFinish(){
	var finishTimeVal=$("#finishTime").val();
	if(''==finishTimeVal){
		$("#finish-confirm").modal({
			relatedTarget : this,
			onConfirm : function() {
				window.location.href = "${ctx}/generatePower/updateRecordStatus?status=1";
			},
			onCancel : function() {
				modalAlert("请添加发电结束时间");
				return;
			}
		});
	}else {
		var finishTimeValDate=new Date(finishTimeVal);
		var distancesM=(new Date().getTime()-finishTimeValDate.getTime());
		if(distancesM < 0){
			modalAlert("发电结束时间不能大于当前时间");
			return;
		}
	}
	
	/* if(''==finishTimeVal){
		return;
	} */
	if(''==finishTimeVal){
		//modalAlert("请添加发电结束时间");
		return;
	}
	var endVoltage=$("#endVoltage").val();
	if(""==endVoltage){
		modalAlert("请添加发电结束电压");
		return;
	}
	
	/* var lis = $("#startgallery").find("li");
	if(lis){
		if(lis.length==1){
			modalAlert("请添加发电开始时间照片");
			return;
		}
	}
	var lis2 = $("#endVoltageGallery").find("li");
	if(lis2){
		if(lis2.length==1){
			modalAlert("请添加发电结束电压照片");
			return;
		}
	} */
	/* $("#formContainer").attr("action","${ctx}/generatePowerSingleBatteryCheck/saveBatteryCheckFinish");
	$("#formContainer").submit(); */
	$("#submigB").removeAttr("href");
	$.ajax({type:"POST",dataType:"json",url:"${ctx}/generatePowerSingleBatteryCheck/saveBatteryCheckFinish",
			data:$('#formContainer').serialize(),
			success:function(json){
				if(json.succ){
					window.location.href="${ctx}/generatePower/electricIndex";
					
				}else{
					modalAlert(json.msg);
					$("#submigB").attr("href","javascript:submitNext();");
				}
			}
	});
}
//showImagesH5();
getNowTimeFromServer();
function getNowTimeFromServer(){
	$.ajax({type: 'POST',dataType:"json",url: '${ctx}/main/getNowTimeFromServer.json',
		data: {},
		success: function(json){
			var currdate = new Date(json.serverTimestamp);
			var currYear = currdate.getFullYear();	
			var currmonth = currdate.getMonth();
			var currday = currdate.getDate();
			var powerCutTime=new Date(Date.parse(powerCutTimeVal.replace(/-/g, "/")));

			initMobiscroll("#finishTime",{
				preset : 'datetime',
				minDate: new Date(currYear,currmonth,currday,powerCutTime.getHours(),powerCutTime.getMinutes()),//
				maxDate: new Date(currYear,currmonth,currday,currdate.getHours(),currdate.getMinutes())
			});
		}
	});
}



</script>
