<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>系统更新</title>
<%@include file="../commons/head.jsp"%>
<%@include file="../commons/mobiscrollDatePlugin.jsp" %> 
<script>
</script>
</head>
<body>
	<div align="center">
		<form id="updateSubmit" class="am-form" data-am-validator>
			<table class="am-table app-tb">
			<!-- <thead>
					<tr>
						<td style="vertical-align: middle;border-top:none;">
							<h1 style="color: #08BAE7;">系统更新</h1>
						</td>
					</tr> 
				</thead> -->
				
				<tr>
					<td style="vertical-align:middle">标题：</td>
					<td>
						<input type="text" id="title"  name="title" value="[第一象限系统升级]"placeholder="输入标题" required/>
					</td>
				</tr>
				<tr>
					<td style="vertical-align:middle">通知内容：</td>
					<td style="vertical-align:middle">
						<textarea id="content" rows="4" class="am-form-field" name="content" placeholder="通知内容" maxlength="340"  required></textarea>
					</td>
				</tr>
				<tr>
					<td style="vertical-align:middle">开始时间：</td>
					<td>
						<input id="startTime" name="startTime" placeholder="手动选择"  required readonly/>
					</td>
				</tr>
				<tr>
					<td style="vertical-align:middle">结束时间：</td>
					<td>
						<input id="endTime" name="endTime" placeholder="手动选择" required readonly/>
					</td>
				</tr>
				<tr>
					<td style="vertical-align:middle">备注：</td>
					<td style="vertical-align:middle">
						<textarea id="remark" rows="4" class="am-form-field" name="remark" placeholder="备注" maxlength="340" ></textarea>
					</td>
				</tr>
			</table>
</form>
</div>
	<div class="am-topbar app-div-btn-tool" style="padding-top: 6px">
			<div class="am-u-sm-12">
				<a class="am-btn am-btn-danger am-radius am-btn-block" style="color:white;" onclick="wechatSystemUpdate();" ><strong>更新</strong></a>
			</div>
    </div>
</body>
<script type="text/javascript">
!function(t) {
	initMobiscrollLongTime("#startTime");
	initMobiscrollLongTime("#endTime");
}(window.jQuery || window.Zepto);
/**初始化Mobiscroll日期选择控件*/
function initMobiscrollLongTime(el){
	var opt = {
	        preset: 'datetime', //日期
	        theme: 'android-ics light', //皮肤样式
	        display: 'modal', //显示方式 
	        mode: 'scroller', //日期选择模式
	        dateFormat: 'yyyy-mm-dd',
			lang:'zh',
			startYear:1990, //开始年份
			endYear:2020
			//maxDate:new Date()
	    };
	$(el).scroller('destroy').scroller(opt);
}
function wechatSystemUpdate(){
	$.when($('#updateSubmit').validator('isFormValid')).then(function(data) {
		if(data){
			showLoading();
			$.ajax({type:'POST',dataType:'json',url:'${ctx}/systemUpdate/update.json',
				data:$('#updateSubmit').serialize(),
				success:function(json){
						modalAlert("系统更新成功", null);
						$('#updateSubmit')[0].reset();		
				},complete:function(){
					closeLoading();
				}
			});
		}else{
			
		}
	}, function() {
		console.log("...");
	});
}

</script>
</html>