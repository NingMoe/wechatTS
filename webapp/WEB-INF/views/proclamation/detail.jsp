<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="../commons/tags.jsp"%>

<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<%@include file="../commons/head.jsp"%>
<%@include file="../commons/mobiscrollDatePlugin.jsp" %>
	<script type="text/javascript">
	var jsConfig=${jsConfig};
	wx.config(jsConfig);
	wx.ready(function() {
		showModalInfo();
	});
	wx.error(function(res) {
		modalAlert("网络出现问题，请稍后重试", null);
	});
	</script>
</head>
<body>
	<header onclick="toProclamationList();" class="am-topbar am-topbar-fixed-top">
		<div ><span class="am-topbar-brand am-icon-angle-left" ></span>
			<div class="am-topbar-brand  app-toolbar">公告栏详情</div>
		</div>
	</header>
<div class="am-cf">
	<!-- <div class="am-cf" style="text-align: left;margin-bottom: 5px;margin-top: 5px;margin-left: 10px;">
		<span style="font-size: medium;">公告栏详情</span>
	</div> -->
	<form id="proclamationForm" class="am-form" method="post" data-am-validator>
		
		<input id="groupName" name="groupName" value="${proclamationBO.groupName}" type="hidden"/>
		<input id="idTop" name="isTop" value="${proclamationBO.isTop}" type="hidden"/>
		<table class="am-table app-tb">
			<tr>
				<td style="vertical-align:middle"><strong>公告主题：${proclamationBO.proclamationTheme }</strong></td>
				
			</tr>
			<tr>
				<td style="vertical-align:middle">公告详情：<br/>${proclamationBO.proclamationDetail }</td>
				
			</tr>
			
			<tr>
				<td style="vertical-align:middle">填报人员：${proclamationBO.fillUser}</td>
			</tr>
			<tr>
				<td style="vertical-align:middle">联系方式：${proclamationBO.phoneNumber}</td>
				
			</tr>
			<tr>
				<td style="vertical-align:middle">填报时间：<fmt:formatDate value="${proclamationBO.fillTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				
			</tr>
			
			<tr>
				<td style="vertical-align:middle">公告周期：<br/>
					<div style="line-height:40px;height:30px;">
						<fmt:formatDate value="${proclamationBO.startTime }" pattern="yyyy-MM-dd HH:mm"/> &nbsp;至&nbsp;
						<fmt:formatDate value="${proclamationBO.endTime }" pattern="yyyy-MM-dd HH:mm"/> 
					</div>
				</td>
				
			</tr>
			<tr>
				<td style="vertical-align: middle">
						<p style="margin-bottom: 0px;">附件：</p>
						<ul style="padding-left: 0px;margin-top: 0px;" data-am-widget="gallery" class="am-gallery am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${sysFileList}" var="item">
							    <li id="${item.fileId}" style="list-style:none;" class="am-g">
							    	<c:choose>
							    		<c:when test="${fn:contains(item.fileName,'.jpg') or fn:contains(item.fileName,'.gif') or fn:contains(item.fileName,'.png') or fn:contains(item.fileName,'.jpeg') or fn:contains(item.fileName,'.bmp')}">
							    		<div class="am-gallery-item" style="width:40px;border: 2px #ddd solid;">
								     		<div style="height:20px;overflow:hidden;overflow:hidden;">
								     		<img src="${ctx}/${item.filePath}"  alt="${item.fileName}" data-rel="${ctx}/${item.filePath}"/>
								     		</div>
								     		<%-- <h3 class="am-gallery-title" style="margin-top: 0px;">${item.fileName}</h3> --%>
								     	</div>
							    		</c:when>
							    		<c:when test="${fn:contains(item.fileName,'.xls') or fn:contains(item.fileName,'.xlsx') }">
							    			<a href="javascript:uploadFile('${item.fileId}')"><i class="am-icon-file-excel-o am-icon-sm"></i><span style="padding-left: 5px;">${item.fileName}</span></a>
							    		</c:when>
							    		<c:when test="${fn:contains(item.fileName,'.doc') or fn:contains(item.fileName,'.docx') }">
							    			<a href="javascript:uploadFile('${item.fileId}')"><i class="am-icon-file-word-o am-icon-sm"></i><span style="padding-left: 5px;">${item.fileName}</span></a>
							    		</c:when>
							    		<c:when test="${fn:contains(item.fileName,'.txt') }">
							    			<a href="javascript:uploadFile('${item.fileId}')"><i class="am-icon-file-text-o am-icon-sm"></i><span style="padding-left: 5px;">${item.fileName}</span></a>
							    		</c:when>
							    	</c:choose>
								</li>
							</c:forEach>
						</ul>
					</td>
				</tr>
			
			<!-- <tr>
				<td>
					<input id="startTime" name="startTime" class="am-form-field am-text-center" placeholder="手动选择" required/>
				</td>
				<td>
					<input id="endTime" name="endTime" class="am-form-field am-text-center" placeholder="手动选择" required/>
				</td>
			</tr> -->
		</table>
		
		<div class="app-div-btn-tool am-margin-bottom">
			<div class="am-u-sm-6">
				<a id="submitProclamation" class="am-btn am-btn-success am-radius am-btn-block" style="color:white;" href="javascript:updateProclamation('${proclamationBO.proclamationId}');"><strong>修改</strong></a>
			</div>
			<div class="am-u-sm-6">
				<a class="am-btn am-btn-danger am-radius am-btn-block" style="color:white;" href="javascript:deleteDetail('${proclamationBO.proclamationId}','${proclamationBO.groupName}');"><strong>删除</strong></a>
			</div>
		</div>
		
	</form>
</div>
<script type="text/javascript">
!function(t) {
	initMobiscrollDate("#startTime");
	initMobiscrollDate("#endTime");
}(window.jQuery || window.Zepto);
function initMobiscrollDate(el,opt){
	var defaultopt = {
	    preset: 'date', //日期
	    theme: 'android-ics light', //皮肤样式
	    display: 'modal', //显示方式 
	    animate: 'pop',
	    mode: 'scroller', //日期选择模式
	    dateFormat: 'yy-mm-dd',
	    dateOrder: 'yymmdd',
	    timeFormat: 'HH:ii',
	    timeWheels: 'hhii',
		lang:'zh',
		startYear:1990, //开始年份
		maxDate:new Date()
	};
	
	if ('' != opt && null != opt && typeof (opt) != "undefined") {
		defaultopt=$.extend(defaultopt,opt);
	};
	//alert(JSON.stringify(defaultopt));
	$(el).scroller('destroy').scroller(defaultopt);
}
	function deleteDetail(proclamationId,groupName){
		modalConfirm("确定删除吗？", function(){
			window.location.href="${ctx}/proclamation/deleteProclamation?groupName="+groupName+"&proclamationId="+proclamationId;
		},null);
		
	}
	function updateProclamation(proclamationId){
		window.location.href="${ctx}/proclamation/updateProclamation?proclamationId="+proclamationId;
	}
	function toProclamationList(){
		var groupName=$("#groupName").val();
		window.location.href="${ctx}/proclamation/getProclamationList?groupName="+groupName;
	}
	function uploadFile(fileId){
		
		window.location.href="${ctx}/proclamation/export?fileId="+fileId;
	}
	
</script>
</body>
</html>

