<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>待传资源</title>
<%@include file="../commons/head.jsp"%>
<script>
	wx.config(${jsConfig});
	wx.ready(function() {
		//saomiao();
	});
	wx.error(function(res) {
		modalAlert("网络出现问题，请稍后重试", null);
	});

	function notice(){
		$.ajax({
			   type: "POST",
			   url: "${ctx}/acceptPhoto/notice.json",
			   success: function(data){
			    	alert(data.info);
			   }
		});
	}
</script>
<title>待传资源</title>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top" >
		<div onclick="toPageUrl('${ctx}/main/toStart?openId=${openId }');">
			<span class="am-topbar-brand am-icon-angle-left" ></span>
			<div class="am-topbar-brand  app-toolbar">待传资源 </div>
			<span id="photoNum" class="am-badge am-badge-primary am-round" style="margin: 14px 0 0 -1px;font-size: 14px;"></span>
		</div>
	</header>
	<div class="am-cf">
		<table id="tb_photo" class="am-table am-table-striped am-table-hover">
			<tbody>
				<c:if test="${photoInfos==null or photoInfos=='[]'}">
					<tr id="nonePhoto">
						<td style="background: #EEEEEE;border: none">
							<div class="am-cf" style="text-align: center;margin-top: 60px">
								<!-- <a href="#" onclick="notice()">测试发送消息</a> -->
							</div>
						</td>
					</tr>
				</c:if>
				<c:forEach items="${photoInfos}" var="obj" varStatus="status">
					<tr id="${obj.photoId}" localId="${obj.localId }">
						<td style="border-top: none;border-bottom:1px #ddd solid;width: 60px;height:55px;text-align: right;">
							<img class="am-radius" src="${obj.localId}" width="60" height="55">
							<span id="${obj.photoId}Badge" class="am-badge am-badge-danger am-round" style="position: absolute;float: right;margin: -60px 5px 0 45px;font-size: 14px;"></span>
						</td>
						<td style="border-top: none; vertical-align: middle; border-bottom: 1px solid #ddd;">
							${obj.photoType }<br/>
							<fmt:formatDate value="${obj.createTime }" pattern="yyyy/MM/dd HH:mm:ss" />
						</td>
						<td width="45px" onclick="deletePhotoId('${obj.photoId}');" class="am-text-left" style="border-top: none; vertical-align: middle; border-bottom: 1px solid #ddd;">
							<i class="am-icon-trash-o am-icon-sm am-fl" style="line-height:55px;color:#dd514c;opacity:100;"></i>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div id="photoButton" class="am-topbar am-topbar-fixed-bottom app-div-btn-tool"  style="padding-top: 6px;">
		<div class="am-u-sm-6">
			<button   type="button" class="am-btn am-btn-success am-btn-block am-radius" onclick="weChatUpload()" >
				<i class="am-icon-upload"></i> <strong>上传照片</strong>
			</button>
		</div>
		<div class="am-u-sm-6">
			<button   type="button" class="am-btn am-btn-primary am-btn-block am-radius" onclick="lookPhoto()" >
				<i class="am-icon-search-plus"></i> <strong>重新加载</strong>
			</button>
		</div>
	</div>
</body>
<script type="text/javascript">
	var photoInfosJson='${photoInfosJson}';
	var photoInfosJsonInitLength=0;
	if(''!=photoInfosJson){
		photoInfosJson=JSON.parse(photoInfosJson);
		photoInfosJsonInitLength=photoInfosJson.length;
	}
	
	window.onload=showPhotos;
	function showPhotos(){
		//showNum();
	}
	function showNum(){
		var photoNum = $('#tb_photo tr:not(#nonePhoto)').length;
		if(photoNum == 0){
			$('#photoNum').text("");
			$("#tb_photo").html('<tr id="nonePhoto"><td style="background: #EEEEEE;border: none"><a href="#" onclick="notice()">测试发送消息</a></tr>');
		}else{
			$('#photoNum').text(photoNum);
		}
	}
	
	var localIdArray=new Array();
	function localIdIndexInArray(localId){
		for(var i=0;i<localIdArray.length;i++){
			if(localIdArray[i]==localId){
				return i;
			};
		};
		return -1;
	};
	function lookPhoto(){
		showLoading();
		$("#tb_photo td").find("img").each(function(){
			localIdArray.push($(this).attr("src"));
		});
		wx.chooseImage({
			sizeType: ['compressed'],
		    sourceType: ['album'], // 可以指定来源是相册还是相机，默认二者都有
		    success: function (res) {
		        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
		        for(var i=0;i<localIds.length;i++){
		        	var index= localIdIndexInArray(localIds[i]);
		        	if(-1 != index){
		        		$("#tb_photo td").find("img[src='"+localIds[i]+"']").replaceWith('<img class="am-radius" src="'+localIds[i]+'" width="60" height="55">');
		        		$("#tb_photo td").find("img[src='"+localIds[i]+"'] + span").html("");
		        		localIdArray.splice(index,1);
		        	}
		        }
				closeLoading();
		    },cancel : function(res) {
				closeLoading();
			},
			complete : function(res) {
				closeLoading();
			},
			fail : function(res) {
				closeLoading();
			}
		});
		//closeLoading();
	}
	var uploadNotSuccessArray=new Array();
	var uploadNotSuccess=true;
	var notShow=true;
	function weChatUpload() {
		uploadNotSuccess=true;
		if(0==photoInfosJson.length){
			closeLoading();
			if(0!=uploadNotSuccessArray.length){
				photoInfosJson=photoInfosJson.concat(uploadNotSuccessArray);
				modalAlert(uploadNotSuccessArray.length+"张图片上传失败！上传失败原因：<br><span class='am-fl'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、图片大小大于2兆</span><br><span class='am-fl'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、照片已移除</span><br><span class='am-fl'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、网络异常</span><span>&nbsp;</span>", null);
				uploadNotSuccessArray.splice(0,uploadNotSuccessArray.length);
				photoInfosJsonInitLength=photoInfosJson.length;
			}
			notShow=true;
			return;
		}
		var msg="正在上传第"+(photoInfosJsonInitLength-photoInfosJson.length+1)+"张,共"+photoInfosJsonInitLength+"张";
		if(notShow){
			showLoading(msg);
			notShow=false;
		}else{
			$("#modal-loading-msg").html(msg);
		}
		
		var thislocalId=photoInfosJson[0].localId;
		var thisphotoId=photoInfosJson[0].photoId;
		
		var timeoutId=setTimeout(function(){
			if(uploadNotSuccess){
				uploadNotSuccessArray.push(photoInfosJson[0]);
				photoInfosJson.splice(0,1);
				$("#"+thisphotoId+"Badge").html("!");
				weChatUpload();
			}
		}, 30000);
		
		wx.uploadImage({
			localId : thislocalId,
			success : function(res) {
				firstQUpload(thisphotoId, res.serverId,timeoutId);
				thislocalId=null;
				thisphotoId=null;
			},
			fail : function(res) {
				//alert(JSON.stringify(res));
				clearTimeout(timeoutId);
				uploadNotSuccessArray.push(photoInfosJson[0]);
				photoInfosJson.splice(0,1);
				$("#"+thisphotoId+"Badge").html("!");
				weChatUpload();
			},
			complete:function(res){
				
			}
			
		});
	}
	function firstQUpload(photoId, mediaId,timeoutId) {
		uploadNotSuccess=false;
		var fd = new FormData();
	    fd.append('mediaId', mediaId);
	    fd.append('photoId', photoId);
	    var xhr = new XMLHttpRequest();
	    xhr.upload.addEventListener("progress", function(evt){}, false);
	    xhr.addEventListener("load", function(evt){
	    	var data= eval('(' + evt.target.responseText + ')');
	    	clearTimeout(timeoutId);
			if (data.status) {
				photoInfosJson.splice(0,1);
				$("#"+photoId).remove();
				//showNum();
			}else{
				uploadNotSuccessArray.push(photoInfosJson[0]);
				photoInfosJson.splice(0,1);
				$("#"+photoId+"Badge").html("!");
			}
			weChatUpload();
	    }, false);
	    xhr.addEventListener("error", function(evt){}, false);
	    xhr.addEventListener("abort", function(evt){}, false);
	    xhr.open("POST", "${ctx}/acceptPhoto/uploadById.json");
	    xhr.send(fd);
	}
	
	
	var pagePhotoId="";//amazeui回调函数中的变量要用全局变量
	var pageVoiceIds="";
	var trIds = "";
	function deletePhotoId(photoId){
		pagePhotoId=photoId;
		$("#app-modal-confirm-msg").html("是否删除照片？");
		$("#app-modal-confirm").modal(
		{
			relatedTarget : this,
			onConfirm : function(){
				returnDelete();
			},
			onCancel : function() {
			}
		});
	}
	function returnDelete(){
		showLoading();
		$.post("${ctx}/acceptPhoto/delete.json",{
			photoId:pagePhotoId
		},function(json){
			closeLoading();
			if(json.succ){
				$("#"+pagePhotoId).remove();
				//showNum();
				
				var notsuccessid=elInArray(pagePhotoId,uploadNotSuccessArray);
				if(notsuccessid>-1){
					uploadNotSuccessArray.splice(notsuccessid,1);
				}
				
				var photocontentid=elInArray(pagePhotoId,photoInfosJson);
				if(photocontentid>-1){
					photoInfosJson.splice(photocontentid,1);
				}
			}else{
				alert("网络繁忙，请稍后重试");
			}
		},"json");
	}
</script>
</html>