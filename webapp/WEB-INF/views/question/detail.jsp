<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>详情</title>
<%@include file="../commons/head.jsp"%>
</head>
<body>
	<header onclick="toQList();" class="am-topbar am-topbar-fixed-top">
		<div ><span class="am-topbar-brand am-icon-angle-left" ></span>
			<div class="am-topbar-brand  app-toolbar">详情</div>
		</div>
	</header>
	<div class="am-cf">
	<input id="nickName" name="nickName" value="${nickName}" type="hidden"/>
	<input id="group" name="group" value="${groupName}" type="hidden"/>
	<form id="questionForm" class="am-form" method="post" data-am-validator>
		<input id="groupName" name="groupName" value="${qb.groupName}" type="hidden"/>
		<table id="questionList" class="am-table app-tb" style="padding:0px;margin: 0px;">
			<tr style="background: #fff;"><td colspan="2" style="border:none;"> 建议标题：${qb.questionTitle }</td></tr>
			<tr style="background: #fff;"><td colspan="2" style="border:none;"> 填报时间：<fmt:formatDate value="${qb.fillDate}" pattern="yyyy/MM/dd HH:mm" /></td></tr>  
			<tr style="background: #fff;"><td colspan="2" style="border:none;"> 联系方式：${qb.phoneNumber}</td></tr>
			<c:if test="${'第一象限' ne qb.groupName && null ne qb.groupName}">
				<tr style="background: #fff;"><td colspan="2" style="border:none;"> 填报人员：${qb.fillUser }</td></tr>
			<tr style="background: #fff;"><td style="border:none;"> 归属群：${qb.groupName}</td><td style="border:none;"> 处理状态：<span class="am-badge <c:if test="${'未处理' eq qb.questionStatus}">am-badge-warning</c:if><c:if test="${'已处理' eq qb.questionStatus}">am-badge-success</c:if> am-text-sm">${qb.questionStatus}</span></td></tr>
			</c:if>
			<c:if test="${'第一象限' eq qb.groupName || null eq qb.groupName}">
				<tr style="background: #fff;"><td style="border:none;"> 填报人员：${qb.fillUser }</td><td style="border:none;"> 处理状态：<span class="am-badge <c:if test="${'未处理' eq qb.questionStatus}">am-badge-warning</c:if><c:if test="${'已处理' eq qb.questionStatus}">am-badge-success</c:if> am-text-sm">${qb.questionStatus}</span></td></tr>
			</c:if>
			<tr>
				<td  colspan="2" style="vertical-align:middle">
					${qb.questionContent}
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;" colspan="2">
					<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
						<c:forEach items="${photoList}" var="item">
							<li id="${item.photoId}">
							    <div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
							     <div style="height:72px;margin-bottom:2px;overflow:hidden;">
							       <img src="${item.thumbLocation}" <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if>  alt="" data-rel="${item.fileLocation}"/>
							       </div>
							    </div>
							</li>
						</c:forEach>
					</ul>
				</td>
			</tr>
			
			<c:if test='${"创智信科" ne sessionScope.s_user.deptId}'>
				<c:if test="${'已处理' eq qb.questionStatus}">
					<tr>
						<td style="vertical-align:middle" colspan="2">
							回复人：${qb.responseUser}
						</td>
					</tr>
					<tr>
						<td style="vertical-align:middle" colspan="2">
							回复时间：<fmt:formatDate value="${qb.responseTime}" pattern="yyyy/MM/dd HH:mm" />
						</td>
					</tr>
				</c:if>
			
				<tr>
					<td style="vertical-align:middle" colspan="2">
						问题回复：<br>
						${qb.questionResponse}
					</td>
				</tr>
			</c:if>
			<c:if test='${"创智信科" eq sessionScope.s_user.deptId}'>
				<c:if test="${'已处理' eq qb.questionStatus}">
					<tr>
						<td style="vertical-align:middle" colspan="2">
							回复人：${qb.responseUser}
						</td>
					</tr>
					<tr>
						<td style="vertical-align:middle" colspan="2">
							回复时间：<fmt:formatDate value="${qb.responseTime}" pattern="yyyy/MM/dd HH:mm" />
						</td>
					</tr>
					<tr>
						<td style="vertical-align:middle" colspan="2">
							问题回复：<br>
							${qb.questionResponse}
						</td>
					</tr>
				</c:if>
				<c:if test="${'已处理' ne qb.questionStatus}">
					<tr>
						<td style="vertical-align:middle" colspan="2">
							<input id="questionId" name="questionId" value="${qb.questionId}" type="hidden">
							<input id="operateUserId" name="operateUserId" value="${qb.operateUserId}" type="hidden">
							<input id="questionTitle" name="questionTitle" value="${qb.questionTitle}" type="hidden">
							<textarea id="questionResponse" rows="4" class="am-form-field" name="questionResponse" placeholder="问题处理意见" maxlength="340" required>${qb.questionResponse}</textarea>
						</td>
					</tr>
				</c:if>
			</c:if>
		</table>
		<c:if test='${"创智信科" eq sessionScope.s_user.deptId && "已处理" ne qb.questionStatus}'>
			<div class="app-div-btn-tool am-margin-bottom">
				<a id="submitQuestionResponse" href="javascript:submitQuestionResponse();" class="am-btn am-btn-success am-radius am-btn-block"> <strong>提交</strong> </a>
			</div>
		</c:if>
	</form>
	</div>
<script type="text/javascript">
!function(t) {
	$("ul[data-am-widget='gallery']").one("click",function(){//点击预览图片关闭预览
		$(".am-pureview-slider").on("click",function(){
			$(".am-icon-chevron-left[data-am-close='pureview']").triggerHandler("click");
		});
	});
}(window.jQuery || window.Zepto);
function submitQuestionResponse(){
	$.when($('#questionForm').validator('isFormValid')).then(function(data) {
		if(data){
			showLoading();
			$("#submitQuestionResponse").removeAttr("href");
			var groupName=$("#group").val();
			var nickName=$("#nickName").val();
			$.ajax({type:'POST',dataType:'json',url:'${ctx}/question/updateQuestion.json',
				data:$('#questionForm').serialize(),
				success:function(data){
					if(data.succ)
						modalAlert("保存成功", function toList(){
							window.location.href="${ctx}/question/questionMain?selectId=2&group="+groupName+"&nickName="+nickName;
							closeLoading();
						});
					
				},complete:function(){
					$("#submitQuestionResponse").attr("href","javascript:submitQuestionResponse();");
				}
			});
		}else{
			
		}
	}, function() {
		console.log("...");
	});
}
var toQList=function (){
	var groupName=$("#group").val();
	var nickName=$("#nickName").val();
	window.location.href="${ctx}/question/questionMain?selectId=2&group="+groupName+"&nickName="+nickName;
}
//loadData();
</script>
</body>
</html>