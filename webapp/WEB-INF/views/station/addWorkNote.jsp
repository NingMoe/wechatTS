<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>添加工作备忘</title>
	<%@include file="../commons/head.jsp" %>
</head>
<script type="text/javascript">	
</script>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div  onclick="goStationDetail();"><span class="am-topbar-brand am-icon-angle-left"></span>
		<div class="am-topbar-brand  app-toolbar">添加工作备忘</div>
		</div>
	</header>
	<div>
	    <form id="categoryRemarkForm" class="am-form" action="${ctx}/station/addWorkNote" method="post" data-am-validator>
		    <div class="am-popup-bd" style="background: white">
			  	<div class="am-form-group">
			  		<input type="hidden" name="stationId" value="${stationId}">
			  		<input type="hidden" name="recordType" value="备忘">
			    	<input type="text" id="title" name="title" placeholder="标题" required>
			    </div>
				<div class="am-form-group">
			    	<textarea class="" rows="3" id="content" name="content" placeholder="内容" required ></textarea>
			    </div>
			    <div class="am-form-group" style="padding-top: 0">
			    	<font size="3" color="#C0C0C0">添加图片</font>
				    <ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
						 <li id="galleryAddBtn" style="padding: 0 0 0 0" onclick="takePhoto(this,'workNoteLocalIds');" data-weg>
						    <div onclick="void(0)">
						    <button type="button" class="app-add-photo"></button>
						    </div>
						 </li>
					</ul>
					<input  class="localIds" type="hidden" id="workNoteLocalIds" name="workNoteLocalIds"  value="" size="100"/>
				</div>
			</div>
			<fieldset>
				<button onclick="goAddWorkNote();" type="button" class="am-btn am-btn-secondary am-radius am-btn-block"><strong>提交</strong></button>	
			</fieldset>
		</form>
	</div>
</body>
<script type="text/javascript">	
	//微信配置js
	wx.config(${jsConfig});
	wx.ready(function() {

	});
	wx.error(function(res) {
		modalAlert("网络出现问题，请稍后重试", null);
	});
	
	function validate_form()
		{
			var title = document.getElementById('title').value.trim();
			var content = document.getElementById('content').value.trim();
			if(title == null || title == '' || title == 'null') {
				modalAlert('标题不能为空', null);
				return false;
			}
			if(content == null || content == '' || content == 'null') {
				modalAlert('内容不能为空', null);
				return false;
			} 
			return true;
		}
	
	function goAddWorkNote() {
		var flag = validate_form();
		if(flag) {
			showLoading();
			var categoryRemarkForm = document.getElementById("categoryRemarkForm");
			categoryRemarkForm.submit();
		}
	}
	
	function goStationDetail() {
		var url="${ctx}/station/getStationDetail?jobId=4&stationId=${stationId}";
		window.location.href=url;
	}
	//showImagesH5();
</script>
</html>
