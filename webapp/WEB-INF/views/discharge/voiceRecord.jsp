<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<%@include file="../commons/head.jsp"%>
		<title>第一象限</title>
		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	</head>
	<body>
		<div class="am-g">
			<div class="app-tabs-bd" style="margin-bottom: 0px">
				<form id="dischargeRecordForm" class="am-form" method="post"
					action="${ctx}/voice/save">
					<table class="am-table app-tb">
						<tr>
							<td style="width: 20%">
								电池组1：
							</td>
							<td>
								1301231111111
							</td>
						</tr>
						<tr>
							<td>
								录音：
							</td>
							<td style="padding: 3px 0 3px 0">
								<span class="voice" name="start" style="display: inline;"
									onclick="dealVoice(this)"> <label
										style="background-color: #99ee33; border-radius: 7px; padding: 6px; margin: 0px; width: 80%">
										&nbsp;&nbsp;&nbsp;
										<i style="color: #669933" class="am-icon-microphone"></i>
										<span style="color: #669933" class="statusTips">开始录音</span>
									</label> </span>
								<span class="deleteVoice" style="display: none;"
									onclick="deleteVoice(this)"><i style="color: #D7342E"
									class="am-icon-trash"></i>删除</span>
								<span><input id=${turn } type="hidden"
										class="voiceRecordIds" name="recordIds" value="" /></span>
								<span><input type="hidden" class="voiceIds"
										name="voiceIds" value="" />
								</span>
							</td>
						</tr>
						<tr style="vertical-align: middle;">
							<td>
								录音：
							</td>
							<td style="padding: 3px 0 3px 0">
								<span class="voice" name="start" style="display: inline;"
									onclick="dealVoice(this)"> <label
										style="background-color: #99ee33; border-radius: 7px; padding: 6px; margin: 0px; width: 80%">
										&nbsp;&nbsp;&nbsp;
										<i style="color: #669933" class="am-icon-bitbucket"></i>
										<span style="color: #669933" class="statusTips">开始录音</span>
									</label> </span>
								<span class="deleteVoice" style="display: none;"
									onclick="deleteVoice(this)">&nbsp;&nbsp;<i
									style="color: #D7342E" class="am-icon-trash"></i>删除</span>
								<span><input type="hidden" class="voiceRecordIds"
										name="recordIds" value="" />
								</span>
							</td>
						</tr>

						<tr>
							<td colspan="2">
								单体电压记录
								<div class="app-vcontent-2"
									style="border-top: 1px #ddd solid; border-left: 1px #ddd solid; width: 100%"></div>
							</td>
						</tr>

					</table>
					<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool">
						<button type="submit"
							class="am-btn am-btn-success am-btn-block am-radius"
							onclick="weChatUpload()" style="margin-top: 6px;">
							<i class="am-icon-upload"></i>
							<strong>上传资源</strong>
						</button>
					</div>
				</form>
			</div>

			<div style="height: 10px">
				&nbsp;
			</div>
		</div>
		<script type="text/javascript">

		//==============================================录音JS
		 var i = 0;
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
		    	if(i<(voiceIdz.length-1)){
				    i++;
				    !function playVoice(){
						 voice.localId = voiceIdz[i];
						 wx.playVoice({
						      localId:voice.localId
						    });
					 }();
				    }else{
					    $('.playOn').find('.voice').find('i').attr('class','am-icon-volume-up');
					    $('.playOn').find('.voice').attr('name','playVoice');
					    $('.playOn').find('.statusTips').text("重新播放");
				        $('.playOn').attr('class','playOff');
				        voice.localId = '';
				    }
			    }
		    });
		});
		function dealVoice(thisObj) {
			 var inputEle = $(thisObj).parent().find('input[name="recordIds"]');
			 var status = $(thisObj).attr("name");
			 var tdEl = $(thisObj).parent();
			 if(status == "start"){
				 wx.startRecord({
				      cancel: function () {
					    modalAlert('用户拒绝授权录音');
				      },
					  success: function() {
						 inputEle.val("");
				    	 tdEl.attr('class','recordOn');
				    	 $(thisObj).attr("name","stopRec");
						 $(thisObj).find('i').attr('class','am-icon-stop');
						 $(thisObj).find('.statusTips').text("停止录音");
					  }
				 });
			 }else if(status == "stopRec"){
				 tdEl.attr('class','recordOff');
				 wx.stopRecord({
				      success: function (res) {
				        voice.localId = res.localId;
				        var inputIds = inputEle.val();
					    var inputIdz;
					    if(inputIds.length>0){
					    inputIdz = inputIds.split(",");
					    inputIdz.push(voice.localId);
					    }else{
					    	inputIdz = voice.localId;
					    }
					    inputEle.val(inputIdz);
					    //保存录音信息
						 $.ajax({
							 type:"get",
							 url:"${ctx}/discharge/saveSound",
							 data:{"recordIds":inputEle.val(),"turn":inputEle.attr('id')},
							 success:function(res){
								 var resp = eval("(" + res + ")");
								 if(resp.status){
									$(thisObj).parent().find('input[name="voiceIds"]').val(resp.voiceIds);
								 }else{
									 modalAlert("录音保存失败！");
								 }
							 },
						 	 error:function(res){
								 modalAlert("录音保存失败！");
							 }
						 });
				       },
				      fail: function (res) {
				        alert(JSON.stringify(res));
				      }
				    });
				 $(thisObj).attr("name","playVoice");
				 $(thisObj).siblings('.deleteVoice').css("display","inline");
				 $(thisObj).find('i').attr('class','am-icon-volume-up');
				 $(thisObj).find('.statusTips').text("点击播放");
				 voice.localId = '';
			 }else if(status == "playVoice"){
				 tdEl.attr('class','playOn');
				 $(thisObj).attr("name","pauseVoice");
				 $(thisObj).find('i').attr('class','am-icon-pause');
				 $(thisObj).find('.statusTips').text("暂停播放");
				 var voiceIds = inputEle.val();
				 var voiceIdz = voiceIds.split(',');
				 if(voice.localId == ''){
				 	i=0;
				 }else{
					 for(j=0;j<voiceIdz.length;j++){
						 if(voiceIdz[j] == voice.localId){
							 var i = j;
						 }
					 }
				 }
				 voice.localId = voiceIdz[i];
				 wx.playVoice({
				      localId:voice.localId
				    });
			 }else if(status == "pauseVoice"){
				 tdEl.attr('class','playOff');
				 wx.pauseVoice({
				      localId: voice.localId
				    });
				 $(thisObj).attr("name","playVoice");
				 $(thisObj).find('i').attr('class','am-icon-play');
				 $(thisObj).find('.statusTips').text("继续播放");
			 }else{
				 alert("录音已损坏，请重新录音！");
			 }
		};
		function deleteVoice(thisObj){
		 $(thisObj).css("display","none");
		 $(thisObj).parent().find('input[name="recordIds"]').val('');
		 var status = $(thisObj).siblings('.voice').attr("name");
			 if(status == "pauseVoice"){
				 $(thisObj).siblings('.voice').click();
			 }
		 $(thisObj).parent().attr('class','');
		 $(thisObj).siblings('.voice').attr("name","start");
		 $(thisObj).siblings('.voice').find('i').attr('class','am-icon-microphone');
		 $(thisObj).siblings('.voice').find('.statusTips').text("开始录音");
		 voice.localId = '';
		 $.ajax({
			 type:"get",
			 url:"${ctx}/discharge/deleteSound",
			 data:{"voiceIds":$(thisObj).parent().find('input[name="voiceIds"]').val()},
			 success:function(res){
				 if(res){
					$(thisObj).parent().find('input[name="voiceIds"]').val("");
				 }else{
					 modalAlert("录音删除失败！");
				 }
			 },
		 	 error:function(res){
				 modalAlert("录音删除失败！");
			 }
			 });
		};


</script>
	</body>
</html>