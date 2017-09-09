<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<%@include file="../commons/mobiscrollDatePlugin.jsp" %>

<form id="formContainer" class="am-form" method="post" action="" data-am-validator>
	<table class="am-table app-tb">
		<tbody>
			<tr>
				<td style="vertical-align: middle;">
					<span style="color: red">*</span>发电类型：
				</td>
				<td>
					<select data-am-selected="{maxHeight: 200}" name="generatePowerType" id="generatePowerType" >
						<option value="0" selected = "selected">正常发电</option>
						<option value="1" >发电补报</option>
				  	</select>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle; width: 120px"><span style="color: red">*</span>基站停电时间：</td>
				<td>
					<div class="am-input-group date">
					  <input size="16" id="stationPowercutTime" value="<fmt:formatDate value="${s_generatepower_record.stationPowercutTime }" pattern="yyyy-MM-dd  HH:mm" />" name="stationPowercutTime" type="text"  class="am-form-field" readonly required>
					  <span class="am-input-group-label add-on"><i class="icon-th am-icon-calendar"></i></span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle; width: 120px"><span style="color: red">*</span>发电开始时间：</td>
				<td>
					<div class="am-input-group date">
					  <input size="16" id="powerCutTime" value="<fmt:formatDate value="${s_generatepower_record.powerCutTime }" pattern="yyyy-MM-dd  HH:mm" />" name="powerCutTime" type="text"  class="am-form-field" readonly required>
					  <span class="am-input-group-label add-on"><i class="icon-th am-icon-calendar"></i></span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle"><span style="color: red">*</span>发电开始电压：</td>
				<td>
					<div class="am-input-group">
						<input type="number" class="am-form-field" id="powercutVoltage" name="powercutVoltage" placeholder="" value="${s_generatepower_record.powercutVoltage}" required />
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
							       		${item.thumbLocation}
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
				<td colspan="2" style="vertical-align: middle">
					<input type="hidden" id="step" name="step" value="${step}">
					<input type="hidden" id="recordId" name="recordId" value="${s_generatepower_record.recordId}">
					<textarea id="" rows="3" class="am-form-field" id="remark" name="remark" placeholder="填写备注信息"  >${s_generatepower_record.remark}</textarea>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px;">
		<a id="submigA" class="am-btn am-btn-success am-radius am-btn-block" href="javascript:submitNext();"><strong style="color:#ffffff;">保存</strong></a>
	</div>
	<div style="height: 10px"></div>
</form>
<script type="text/javascript">
function submitNext(){
	var generatePowerType=$('#generatePowerType').val();
	var stationPowercutTime=$('#stationPowercutTime').val();
	var powerCutTime=$('#powerCutTime').val();
	var powercutVoltage=$('#powercutVoltage').val();
	if(""!=stationPowercutTime){
		if(""!=powerCutTime){
			stationPowercutTime=new Date(powerCutTime);
			powerCutTime=new Date(powerCutTime);
			var distancesM=Math.abs((new Date().getTime()-powerCutTime)/1000/60);
			//alert(generatePowerType+","+distancesM+","+typeof distancesM);
			if("0"==generatePowerType && distancesM > 30){
				modalAlert("发电开始时间跟当前时间相差应小于30分钟");
				return;
			}
			if("1"==generatePowerType && distancesM > 60){
				modalAlert("发电开始时间跟当前时间相差应小于60分钟");
				return;
			}
			/* distancesM=(powerCutTime-stationPowercutTime);
			if(distancesM<0){
				modalAlert("发电开始时间不能小于基站停电时间");
				return;
			} */
		}else{
			modalAlert("请填写发电开始时间");
			return;
		}
	}else{
		modalAlert("请填写基站发电时间");
		return;
	}
	
	if(""==powercutVoltage){
		modalAlert("请填写发电开始电压");
		return;
	}
	/* var lis = $("#startgallery").find("li");
	if(lis){
		if(lis.length==1){
			modalAlert("请添加停电开始时间照片");
			return;
		}
	} */
	$("#submigA").removeAttr("href");
	
	$.ajax({type:"POST",dataType:"json",url:"${ctx}/generatePowerSingleBatteryCheck/saveBatteryCheckStepOne.json",
		data:{
			generatePowerType:generatePowerType,
			stationPowercutTime:$('#stationPowercutTime').val(),
			powerCutTime:$('#powerCutTime').val(),
			powercutVoltage:powercutVoltage,
			startTimeImg:$("#startTimeImg").val(),
			step:$("#step").val(),
			recordId:$("#recordId").val(),
			remark:$("#remark").val()
		},
		success:function(json){
			if(json.succ){
				window.location.href="${ctx}/generatePower/electricIndex";
			}else{
				modalAlert(json.msg);
				$("#submigA").attr("href","javascript:submitNext();");
			}
		}
	});
	
	/* $("#formContainer").attr("action","${ctx}/generatePowerSingleBatteryCheck/saveBatteryCheckStepOne");
	$("#formContainer").submit(); */
}
//showImagesH5();

var currdate={};
var gpt=-1;
function dealTimeStart(){
	var generatePowerType=$('#generatePowerType').val();
	if(gpt!=generatePowerType){
		gpt=generatePowerType;
		var date=new Date();
		var m=1000*60*30;
		
		if("1"==generatePowerType){
			m=1000*60*60;
		}
		
		date.setTime(currdate.getTime()-m);
		
		console.log(m+".."+generatePowerType +","+date+","+currdate);
		
		$("#powerCutTime").scroller('destroy');
		
		initMobiscroll("#powerCutTime",{
			preset : 'datetime',
			minDate: new Date(date.getFullYear(),date.getMonth(),date.getDate(),date.getHours(),date.getMinutes()), 
			maxDate: new Date(currdate.getFullYear(),currdate.getMonth(),currdate.getDate(),currdate.getHours(),currdate.getMinutes())
		});
	}
}
getNowTimeFromServer();
function getNowTimeFromServer(){
	$.ajax({type: 'POST',dataType:"json",url: '${ctx}/main/getNowTimeFromServer.json',
		data: {},
		success: function(json){
			currdate = new Date(json.serverTimestamp);
			var currYear = currdate.getFullYear();	
			var currmonth = currdate.getMonth();
			var currday = currdate.getDate();
			
			dealTimeStart();
			
			initMobiscroll("#stationPowercutTime",{
				preset : 'datetime',
				maxDate: new Date(currYear,currmonth,currday,currdate.getHours(),currdate.getMinutes())
				/* minDate: new Date(currYear,currmonth,currday,00,00), 
				maxDate: new Date(currYear,currmonth,currday,currdate.getHours(),currdate.getMinutes()) */
			});
			
			$("#generatePowerType").on("change",dealTimeStart);
		}
	});
};
</script>