<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>未发申报</title>
<%@include file="../commons/head.jsp"%>
<%@include file="../commons/mobiscrollDatePlugin.jsp" %>
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
			<div class="am-topbar-brand app-toolbar">
				未发申报
			</div>
		</div>
	</header>
<form id="formContainer" class="am-form" method="post" action="" data-am-validator>
	<table class="am-table app-tb">
		<tbody>
			<tr style="display:none;">
				<td style="vertical-align: middle;">
					<span style="color: red">*</span>发电类型：
				</td>
				<td>
					<input value="2" name="generatePowerType" id="generatePowerType" />
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle; width: 120px"><span style="color: red">*</span>恢复时间：</td>
				<td>
					<div class="am-input-group date">
					  <input size="16" id="powerCutTime" name="powerCutTime" type="text"  class="am-form-field" readonly required>
					  <span class="am-input-group-label add-on"><i class="icon-th am-icon-calendar"></i></span>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="vertical-align: middle">
					<input type="hidden" name="step" id="step" value="${step}">
					<textarea id="remark" rows="3" class="am-form-field" name="remark" placeholder="现场状况描述"  ></textarea>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;" colspan="2">
					<p><span style="color: red">*</span>上传事件照片：</p>
					<ul id="startgallery" data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
						<li id="galleryAddBtn" onclick="takePhoto(this,'startTimeImg');" data-weg>
						    <div onclick="void(0)">
						     <button type="button" class="app-add-photo"></button>
						    </div>
						</li>
					</ul>
					<input  class="localIds" type="hidden" id="startTimeImg" name="startTimeImg"  value="" size="100"/>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px;">
		<a id="submitNextA" class="am-btn am-btn-success am-radius am-btn-block" href="javascript:submitNext();"><strong style="color:#ffffff;">保存</strong></a>
	</div>
	<div style="height: 10px"></div>
</form>
<script type="text/javascript">
wx.config(${jsConfig});
wx.ready(function() {
});
wx.error(function(res) {
	modalAlert("网络出现问题，请稍后重试", null);
});

function submitNext(){
	var generatePowerType=$('#generatePowerType').val();
	var powerCutTime=$('#powerCutTime').val();
	
	if(""!=powerCutTime){
		powerCutTime=new Date(powerCutTime);
		var distancesM=Math.abs((new Date().getTime()-powerCutTime)/1000/60);
		/* if("2"==generatePowerType && distancesM > 30){
			modalAlert("恢复时间跟当前时间相差应小于30分钟");
			return;
		} */
	}else{
		modalAlert("请填写恢复时间");
		return;
	}
	var remark = $("#remark").val();
	if(""==remark){
		modalAlert("请添加现场状况描述");
		return;
	}
	
	var lis = $("#startgallery").find("li");
	if(lis &&lis.length==1){
		modalAlert("请添加事件照片");
		return;
	}
	
	$("#submitNextA").removeAttr("href");
	
	$.ajax({type:"POST",dataType:"json",url:"${ctx}/generatePowerSingleBatteryCheck/saveBatteryCheckStepOne.json",
		data:{
			generatePowerType:generatePowerType,
			powerCutTime:$('#powerCutTime').val(),
			step:$("#step").val(),
			startTimeImg:$("#startTimeImg").val(),
			remark:$("#remark").val()
		},
		success:function(json){
			if(json.succ){
				window.location.href="${ctx}/generatePower/electricIndex";
			}else{
				modalAlert(json.msg);
				$("#submitNextA").attr("href","javascript:submitNext();");
			}
		}
	});
	
	/* $("#formContainer").attr("action","${ctx}/generatePowerSingleBatteryCheck/saveBatteryCheckStepOne");
	$("#formContainer").submit(); */
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
			
			initMobiscroll("#powerCutTime",{
				preset : 'datetime',
				minDate: new Date(currYear,currmonth,currday,00,00), 
				maxDate: new Date(currYear,currmonth,currday,currdate.getHours(),currdate.getMinutes())
			});
		}
	});
}
!function(t) {
	$("ul[data-am-widget='gallery']").one("click",function(){//点击预览图片关闭预览
		$(".am-pureview-slider").on("click",function(){
			$(".am-icon-chevron-left[data-am-close='pureview']").triggerHandler("click");
		});
	});
}(window.jQuery || window.Zepto);
</script>