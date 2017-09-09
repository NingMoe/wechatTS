<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="../commons/tags.jsp"%>
<div align="center">
	<form id="questionForm" class="am-form" method="post" data-am-validator>
		<input name="userId" value="${sessionScope.s_user.userId}" type="hidden"/>
		<input id="groupName" name="groupName" value="${groupName}" type="hidden"/>
		<input id="nickName" name="nickName" value="${nickName}" type="hidden"/>
		<table class="am-table app-tb">
			<tr>
				<td style="vertical-align:middle">建议标题：</td>
				<td>
					<input id="questionTitle" name="questionTitle" placeholder="点击输入标题" class="am-form-field" maxlength="30" required/>
				</td>
			</tr>
			<tr>
				<td style="vertical-align:middle">填报人员：</td>
				<td>
					<input id="fillUser" name="fillUser" value="${sessionScope.s_user.userName}" placeholder="点击输入人员" class="am-form-field" maxlength="30" required/>
				</td>
			</tr>
			<tr>
				<td style="vertical-align:middle">联系方式：</td>
				<td>
					<input id="phoneNumber" name="phoneNumber" value="${sessionScope.s_user.phoneNo}" placeholder="点击输入联系方式"  class="am-form-field js-pattern-mobile" pattern="^1((3|5|4|8){1}\d{1}|70)\d{8}$"  required/>
				</td>
			</tr>
			<tr>
				<td style="vertical-align:middle">所属单位：</td>
				<td> <%-- value="${sessionScope.s_user.deptId}" --%>
					<input id="deptId" name="deptId"   placeholder="点击输入单位" class="am-form-field" maxlength="60" required/>
				</td>
			</tr>
			<c:if test="${'第一象限' ne groupName && !empty groupName}">
				<tr>
					<td style="vertical-align:middle">归属群：</td>
					<td> 
						<input id="group" name="group"  value="${groupName}" class="am-form-field" maxlength="60" readonly/>
					</td>
				</tr>
			</c:if>
			
	<!-- 		<tr>
			<td style="vertical-align:middle">填报时间：</td>
			<td>
				<input id="fillDate" name="fillDate" class="am-form-field am-text-center" placeholder="手动选择" required/>
			</td>
			</tr> -->
			<tr>
				<td style="vertical-align:middle">填报内容：</td>
				<td style="vertical-align:middle">
					<textarea id="questionContent" rows="4" class="am-form-field" name="questionContent" placeholder="简要描述问题或意见" maxlength="340"  required></textarea>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;" colspan="2">
					<p>上传照片：</p>
						<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<li id="galleryAddBtn" onclick="takePhoto(this,'deviceImgs');" data-weg>
								<div onclick="void(0)">
									<button type="button" class="app-add-photo"></button>
								 </div>
							</li>
						</ul>
					<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" />
				</td>
			</tr>
		</table>
		<div class="app-div-btn-tool am-margin-bottom">
			<a id="submitQuestion" href="javascript:submitQuestion();" class="am-btn am-btn-success am-radius am-btn-block"> <strong>提交</strong> </a>
		</div>
	</form>
</div>
<script type="text/javascript">
var localIdArray=new Array();
var mediaIds="";

function weChatUpload() {
	if(null!=localIdArray && localIdArray.length >0){
		var thislocalId=localIdArray.shift();
		wx.uploadImage({
			localId : thislocalId,
			success : function(res) {
				mediaIds+=","+res.serverId;
			},fail : function(res) {
			},complete:function(res){
				weChatUpload();
			}
		});
	}else{
		if(""!=mediaIds){
			mediaIds=mediaIds.substring(1,mediaIds.length);
		}
		submitQuestionToServer();
	}

}


	function submitQuestion(){
		$.when($('#questionForm').validator('isFormValid')).then(function(data) {
			if(data){
				showLoading();
				$("#submitQuestion").removeAttr("href");
				
				var localIds=$("#deviceImgs").val();
				localIdArray=localIds.split(",");
				weChatUpload();

			}else{
				
			}
		}, function() {
			console.log("...");
		});
	}
	
	function submitQuestionToServer(){
		var fromData=$('#questionForm').serialize()+"&mediaImgs="+mediaIds;
		$.ajax({type:'POST',dataType:'json',url:'${ctx}/question/addQuestion.json',
			data:fromData,
			success:function(json){
				$("#submitQuestion").attr("href","javascript:submitQuestion();");
				/* if(json.succ){
				} */
				
			},complete:function(){
				closeLoading();
				var groupName=$("#groupName").val();
				var nickName=$("#nickName").val();
				window.location.href="${ctx}/question/questionMain?selectId=2&group="+groupName+"&nickName="+nickName;
			}
		});
	}
</script>
