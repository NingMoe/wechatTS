<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<form id="formContainer" class="am-form" method="post" action=""
	data-am-validator>
	<input type="hidden" id="singleV" name="batteryVoltage" value="${batteryVoltage}" />
	<table class="am-table app-tb">
		<tbody>
			<tr>
				<td style="vertical-align: middle;border-top: none;width: 30%">单体电压：</td>
				<td style="border-top: none;width: 70%">
					${batteryVoltage}v
		 		</td>
		 	</tr>
			<tr>
				<td style="vertical-align: middle; width: 120px">
					室内温度：
				</td>
				<td>
					<div class="am-input-group">
						<input name="temp" type="number" class="am-form-field" value="${s_discharge_record.temp}" required />
						<span class="am-input-group-label">℃</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">
					放电方式：
				</td>
				<td>
					<select id="hour" data-am-selected="{maxHeight:200}" name="dischargeType"
						required>
						<option value="1" ${s_discharge_record.dischargeType == '1'?'selected':''}>
							在线放电1小时
						</option>
						<option value="2" ${s_discharge_record.dischargeType == '2'?'selected':''}>
							在线放电2小时
						</option>
						<option value="3" ${s_discharge_record.dischargeType == '3'?'selected':''}>
							在线放电3小时
						</option>
						<option value="4" ${s_discharge_record.dischargeType == '4'?'selected':''}>
							在线放电4小时
						</option>
						<option value="5" ${s_discharge_record.dischargeType == '5'?'selected':''}>
							在线放电5小时
						</option>
					</select>
				</td>
			</tr>
			<tr>
				<td style="border-top: none"></td>
				<td style="border-top: none;">
					<sub>单体测试：每30分钟记录一次，共
					<span id="times">*</span>次</sub>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;">
					放电电流：
				</td>
				<td>
					<div class="am-input-group">
						<input name="dischargeCurrent" type="number" class="am-form-field" value="${s_discharge_record.dischargeCurrent}" required />
						<span class="am-input-group-label">A</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle; width: 120px">
					放电开始电压：
					<br>
					<sub>(停电5分钟电压)</sub>
				</td>
				<td>
					<div class="am-input-group">
						<input name="startVoltage" type="number" class="am-form-field" value="${s_discharge_record.startVoltage}" placeholder=""
							required />
						<span class="am-input-group-label">V</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;" colspan="2">
					<p>放电开始电压照片：</p>
					<ul id="voltagegallery" data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
						<c:forEach items="${s_discharge_record.startVoltagePhotoList}" var="item">
						    <li id="${item.photoId}">
							    <div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
							     	<div style="height:72px;margin-bottom:2px;overflow:hidden;">
							       		<img src="${item.thumbLocation}" <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if>  alt="" data-rel="${item.fileLocation}"/>
							       	</div>
							       	<h3 class="am-gallery-title am-fr" onclick="deletePhotoInServer('${ctx}','${item.photoId}')">删除  <i class="am-icon-trash-o am-icon-sm" style="color:#dd514c;opacity:100;margin-top: 6px;"></i></h3>
							    </div>
							</li>
						</c:forEach>
						<li id="galleryAddBtn" onclick="takePhoto(this,'startVoltageImgs');" data-weg>
						    <div onclick="void(0)">
						     <button type="button" class="app-add-photo"></button>
						    </div>
						</li>
					</ul>
					<input  class="localIds" type="hidden" id="startVoltageImgs" name="startVoltageImgs"  value="" size="100"/>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="app-div-btn-tool">
		<button class="am-btn am-btn-success am-radius am-btn-block"
			type="button" onclick="submitNext();">
			<strong>下一步</strong>
		</button>
	</div>
	<div style="height: 10px"></div>
</form>
<script type="text/javascript">
	//动态控制单体测试次数
	$("#hour").change(function() {
		var hour = $("#hour option:selected").val();
		var times = 2 * hour;
		$("#times").text(times);
	});
	function submitNext(){
		var lis = $("#voltagegallery").find("li");
		if(lis){
			if(lis.length==1){
				modalAlert("请添加放电开始电压照片");
				return;
			}
		}
		$("#formContainer").attr("action","${ctx}/singleBatteryCheck/saveBatteryCheckStepOne");
		$("#formContainer").submit();
		//用store传照片saveBase64ImgData(function(){});
	}
	//showImagesH5();
</script>