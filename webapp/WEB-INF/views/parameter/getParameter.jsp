<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="../commons/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>普调方案</title>
<%@include file="../commons/head.jsp" %>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
$(function(){
/* 	$("#bParameter").submit(function(){
		
	}); */
});
//微信配置js
wx.config(${jsConfig});
wx.ready(function () {
});
wx.error(function (res) {
	//alert(res.errMsg);
});
function weChatCloseThisPage(){
	/*关闭当前网页*/
	wx.closeWindow();
}
function gotoStationList(){
	window.location.href="${ctx}/station/gotoNearbyList.html";
}
function submitDate(){
	//提交调参
	showLoading();
	var parameterRecord=$("#parameterRecord").val();
	$.post("${ctx}/parameter/setParameterRecord.json",{
		parameterRecord:parameterRecord
	},function(json){
		closeLoading();
		if(json.succ){
			modalAlert("调参完成",gotoStationList);
		}else{
			modalAlert("调参失败",null);
		}
	},"json");
}


</script>
</head>
<body>
<header class="am-topbar am-topbar-fixed-top">
<div onclick="gohistory();">
	<span class="am-topbar-brand am-icon-angle-left"></span>
	<div class="am-topbar-brand  app-toolbar">普调方案</div>
</div>
</header>
	<input type="hidden" id="parameterRecord" name="parameterRecord" value="${parameterRecord}" >
	<div class="am-g">
		<table class="am-table app-tb"> 
			<tbody>
					<c:forEach items="${calculateResult}" var="obj" varStatus="status">
					
						<tr height="50px;">
							<td  style='<c:if test="${status.index == 0}">
											border-top: none;
										</c:if>vertical-align: middle;white-space: nowrap;'>
								${obj.name}：
							</td>
							<td style='<c:if test="${status.index == 0}">
									border-top: none;
								</c:if>vertical-align: middle;white-space: nowrap;' >${obj.calculateResult}</td>
						</tr>
					</c:forEach>
			</tbody>
		</table>
		<div class="app-div-btn-tool">
	    	<button id="submitDate" onclick="submitDate()"  class="am-btn am-btn-success am-radius am-btn-block"><strong>确定</strong></button>
		</div>
		<div style="height: 10px">&nbsp;</div>
	</div>

</body>
</html>