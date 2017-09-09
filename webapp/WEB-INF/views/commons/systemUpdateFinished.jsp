<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>升级完成</title>
<%@include file="../commons/head.jsp"%> 
<%@include file="../commons/mobiscrollDatePlugin.jsp" %> 
<script>
</script>
</head>
<body>
	<div align="center">
		<form id="updateFinished" class="am-form" data-am-validator>
			<table class="am-table app-tb">
			<!-- <thead>
					<tr>
						<td style="vertical-align: middle;border-top:none;">
							<h1 style="color: #08BAE7;">完成通知</h1>
						</td>
					</tr> 
				</thead> -->
				
				<tr>
					<td style="vertical-align:middle">标题：</td>
					<td>
						<input type="text" id="title"  name="title" value="[第一象限系统升级完成]"placeholder="输入标题" required/>
					</td>
				</tr>
				<tr>
					<td style="vertical-align:middle">类型：</td>
					<td style="vertical-align:middle">
						<textarea id="content" rows="3" class="am-form-field" name="content" placeholder="通知内容" maxlength="340"  required></textarea>
					</td>
				</tr>
				<tr>
					<td style="vertical-align:middle">完成时间：</td>
					<td>
						<input id="finishedTime" name="finishedTime" placeholder="手动选择"  required readonly/>
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
				<a class="am-btn am-btn-success am-radius am-btn-block" style="color:white;" onclick="updateFinished();" ><strong>提交</strong></a>
			</div>
    </div>
</body>
<script type="text/javascript">
!function(t) {
	initMobiscrollLongTime("#finishedTime");
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
function updateFinished(){
	$.when($('#updateFinished').validator('isFormValid')).then(function(data) {
		if(data){
			showLoading();
			$.ajax({type:'POST',dataType:'json',url:'${ctx}/systemUpdate/updateFinished.json',
				data:$('#updateFinished').serialize(),
				success:function(json){
					//var data=JSON.parse(json.info);
					//var num=0;
					//if(data["errcode"] == 0){
						//num++;
					//}
					//alert(num);
						modalAlert("通知成功", null);
						$('#updateFinished')[0].reset();
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