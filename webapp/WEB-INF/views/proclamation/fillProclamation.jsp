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
<div class="am-cf">
	<div class="am-cf" style="text-align: left;margin-bottom: 5px;margin-top: 5px;margin-left: 10px;">
		<span style="font-size: medium;">
			<table>
					<tr>
						<td width="30%">公告填报</td>
						<td width="60%" align="center"  style="white-space: nowrap;">
							<marquee scrollAmount=2  style="width: 80%;height: 20px;" >
								<strong>基站电源问题管理专家，创智信科专业电源维护10年</strong>
							</marquee>
						</td>
					</tr>
				</table>
		</span>
	</div>
	<form id="proclamationForm" class="am-form" method="post" data-am-validator>
		<c:if test="${proclamationBO ==null || proclamationBO == '[]' }">
		<input id="groupName" name="groupName" value="${groupName}" type="hidden"/>
		</c:if>
		<c:if test="${proclamationBO !=null}">
			<input id="groupName" name="groupName" value="${proclamationBO.groupName}" type="hidden"/>
			<input id="idTop" name="isTop" value="${proclamationBO.isTop}" type="hidden"/>
		</c:if>
		<input id="proclamationId" name="proclamationId" value="${proclamationBO.proclamationId}" type="hidden"/>
		<table class="am-table app-tb">
			<tr>
				<td style="vertical-align:middle;"><strong>公告主题：</strong></td>
				<td>
					<input id="proclamationTheme" name="proclamationTheme" value="${proclamationBO.proclamationTheme }" placeholder="输入主题" class="am-form-field" maxlength="30" style="font-size: 14px;" required/>
				</td>
			</tr>
			<tr>
				<td style="vertical-align:middle">公告详情：</td>
				<td style="vertical-align:middle">
					<textarea id="proclamationDetail" rows="4" class="am-form-field" name="proclamationDetail"  placeholder="输入详情" maxlength="600" style="font-size: 14px;" required>${proclamationBO.proclamationDetail }</textarea>
				</td>
			</tr>
			<tr>
				<td style="vertical-align:middle">填报人员：</td>
				<td>
					<c:if test="${proclamationBO == null || proclamationBO == '[]'}">
						<input id="fillUser" name="fillUser" value="${userInfo.userName}" placeholder="点击输入人员" class="am-form-field" maxlength="30" style="font-size: 14px;" required/>
					</c:if>
					<c:if test="${proclamationBO != null}">
						<input id="fillUser" name="fillUser" value="${proclamationBO.fillUser}" placeholder="点击输入人员" class="am-form-field" maxlength="30" style="font-size: 14px;" required/>
					</c:if>
				</td>
			</tr>
			<tr>
				<td style="vertical-align:middle">联系方式：</td>
				<td>
					<c:if test="${proclamationBO == null || proclamationBO == '[]'}">
						<input id="phoneNumber" name="phoneNumber" value="${userInfo.phoneNo}" placeholder="输入联系方式"  class="am-form-field js-pattern-mobile" pattern="^1((3|5|4|8){1}\d{1}|70)\d{8}$"  style="font-size: 14px;" required/>
					</c:if>
					<c:if test="${proclamationBO != null}">
						<input id="phoneNumber" name="phoneNumber" value="${proclamationBO.phoneNumber}" placeholder="输入联系方式"  class="am-form-field js-pattern-mobile" pattern="^1((3|5|4|8){1}\d{1}|70)\d{8}$" style="font-size: 14px;" required/>
					</c:if>
				</td>
			</tr>
			<tr>
				<td style="vertical-align:middle">填报时间：</td>
				<td id="fillTime" style="font-size: 14px;">
					<c:if test="${proclamationBO == null || proclamationBO == '[]'}"> 
						<fmt:formatDate value="${fillTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</c:if>
					<c:if test="${proclamationBO != null}"> 
						<fmt:formatDate value="${proclamationBO.fillTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</c:if>
				</td>
			</tr>
			
			<tr>
				<td style="vertical-align:middle" colspan="2">公告周期：</td>
				<!-- <td style="vertical-align: middle">
					<div style="line-height:40px;height:30px;">
						<input id="startTime" name="startTime" class="am-form-field am-text-center" style="width:150px;float:left;displsy:inline;" placeholder="手动选择" required/><span style="float:left;">&nbsp;&nbsp;至&nbsp;&nbsp;</span>
						<input id="endTime" name="endTime" class="am-form-field am-text-center" style="width:150px;float:left;displsy:inline;" placeholder="手动选择" required/>
					</div>
				</td> -->
			</tr>
			<tr>
				<td style="vertical-align: middle" colspan="2">
					<div style="line-height:40px;height:30px;">
						<input id="startTime" name="startTime" value='<fmt:formatDate value="${proclamationBO.startTime }" pattern="yyyy-MM-dd HH:mm"/>' class="am-form-field am-text-center" style="width:45%;float:left;displsy:inline;padding-left:0px;padding-right:0px;" placeholder="手动选择" required onchange="compareTime(this.id);"/><span style="float:left;">&nbsp;&nbsp;至&nbsp;&nbsp;</span>
						<input id="endTime" name="endTime"  value='<fmt:formatDate value="${proclamationBO.endTime }" pattern="yyyy-MM-dd HH:mm"/>' class="am-form-field am-text-center" style="width:45%;float:left;displsy:inline;padding-left:0px;padding-right:0px;" placeholder="手动选择" required onchange="compareTime(this.id);"/>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle" colspan="2">
						<p style="margin-bottom: 0px;">附件：</p>
						<ul style="padding-left: 0px;margin-top: 0px;" data-am-widget="gallery" class="am-gallery am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${sysFileList}" var="item">
							    <li id="${item.fileId}" style="list-style:none;" class="am-g">
							    	<div class="am-u-sm-2 am-list-thumb">
							    		<a onclick="removeFile(this,'${item.fileId}')" class="am-btn am-btn-danger am-btn-sm" style="padding:2px;">删除</a>
							    	</div>
							    	<div class="am-u-sm-10 am-list-main am-text-truncate" style="padding-left: 0px;">
							    	<c:choose>
							    		<c:when test="${fn:contains(item.fileName,'.jpg') or fn:contains(item.fileName,'.gif') or fn:contains(item.fileName,'.png') or fn:contains(item.fileName,'.jpeg') or fn:contains(item.fileName,'.bmp')}">
							    		<div class="am-gallery-item am-table-radius" style="width:40px;border: 2px #ddd solid;height:25px;overflow:hidden;">
								     		<%-- <a href="${ctx}/${item.filePath}" ><i class="am-icon-file-image-o am-icon-sm"></i><span style="padding-left: 5px;">${item.fileName}</span></a> --%>
								     		<img src="${ctx}/${item.filePath}"  alt="${item.fileName}" data-rel="${ctx}/${item.filePath}"/>
								     		<h3 class="am-gallery-title">${item.fileName}</h3>
							    		</div>
							    		</c:when>
							    		<c:when test="${fn:contains(item.fileName,'.xls') or fn:contains(item.fileName,'.xlsx') }">
							    			<a href="javascript:uploadFile('${item.fileId}')"><i class="am-icon-file-excel-o am-icon-sm"></i><span style="padding-left: 5px;">${item.fileName}</span></a>
							    		</c:when>
							    		<c:when test="${fn:contains(item.fileName,'.doc') or fn:contains(item.fileName,'.docx') }">
							    			<a href="javascript:uploadFile('${item.fileId}')"><i class="am-icon-file-word-o am-icon-sm"></i><span style="padding-left: 5px;">${item.fileName}</span></a>
							    		</c:when>
							    		<c:when test="${fn:contains(item.fileName,'.txt')}">
							    			<a href="javascript:uploadFile('${item.fileId}')"><i class="am-icon-file-text-o am-icon-sm"></i><span style="padding-left: 5px;">${item.fileName}</span></a>
							    		</c:when>
							    	</c:choose>
							    	</div>
								</li>
							</c:forEach>
						</ul>
					</td>
				</tr>
		</table>
		<%@include file="../commons/sysFileUpload.jsp"%>
		<div class="app-div-btn-tool am-margin-bottom app-div-btn-tool">
			<div class="am-u-sm-6">
				<a id="submitProclamation" class="am-btn am-btn-success am-radius am-btn-block" style="color:white;" href="javascript:submitProclamation();"><strong>保存</strong></a>
			</div>
			<c:if test="${proclamationBO == null || proclamationBO == '[]'}">
				<div class="am-u-sm-6">
					<a class="am-btn am-btn-danger am-radius am-btn-block" style="color:white;" href="javascript:cancleSave();"><strong>取消</strong></a>
				</div>
			</c:if>
			<c:if test="${proclamationBO != null}">
				<div class="am-u-sm-6">
					<a class="am-btn am-btn-danger am-radius am-btn-block" style="color:white;" href="javascript:backList('${proclamationBO.groupName }');"><strong>返回</strong></a>
				</div>
			</c:if>
		</div>
		
		
	</form>
</div>
<script type="text/javascript">
!function(t) {
			initMobiscrollDate("#startTime",{
				preset : 'datetime',
					//minDate: new Date(currYear,currmonth,currday,fillTime.getHours(),fillTime.getMinutes()),//
					//maxDate: new Date(currYear,currmonth,currday,currdate.getHours(),currdate.getMinutes())
			});
			initMobiscrollDate("#endTime",{
				preset : 'datetime',
					//minDate: new Date(currYear,currmonth,currday,fillTime.getHours(),fillTime.getMinutes()),//
					//maxDate: new Date(currYear,currmonth,currday,currdate.getHours(),currdate.getMinutes())
			});
}(window.jQuery || window.Zepto);
function initMobiscrollDate(el,opt){
	var defaultopt = {
	    preset: 'date', //日期
	    theme: 'android-ics light', //皮肤样式
	    display: 'modal', //显示方式 
	    mode: 'scroller', //日期选择模式
	    dateFormat: 'yy-mm-dd',
	    dateOrder: 'yymmdd',
		lang:'zh',
		startYear:1990, //开始年份
		//maxDate:new Date()
	};
	
	if ('' != opt && null != opt && typeof (opt) != "undefined") {
		defaultopt=$.extend(defaultopt,opt);
	};
	//alert(JSON.stringify(defaultopt));
	$(el).scroller('destroy').scroller(defaultopt);
}
	function submitProclamation(){
		$.when($('#proclamationForm').validator('isFormValid')).then(function(data) {
			
			if(data){
				showLoading();
				$("#submitProclamation").removeAttr("href");
				var proclamationTheme=$("#proclamationTheme").val();
				var groupName=$("#groupName").val();
				var fillTime=$("#fillTime").text().trim();
				var proclamationDetail=$("#proclamationDetail").val().replace(/\n|\r\n/g,"<br>");
				var fillUser=$("#fillUser").val();
				var phoneNumber=$("#phoneNumber").val();
				var startTime=$("#startTime").val();
				var endTime=$("#endTime").val();
				var proclamationId=$("#proclamationId").val();
				
				var fd = new FormData();
				fd.append("proclamationTheme", proclamationTheme);
				fd.append("groupName", groupName);
				fd.append("proclamationDetail", proclamationDetail);
				fd.append("fillUser", fillUser);
				fd.append("phoneNumber", phoneNumber);
				fd.append("startTime", startTime);
				fd.append("endTime", endTime);
				fd.append("fillTime", fillTime);
				fd.append("proclamationId", proclamationId);
				$(".myFileUpload").each(function(i){
					fd.append($(this).attr("name"), $(this).val());
				});
				
				var xhr = new XMLHttpRequest();
				//xhr.upload.addEventListener("progress", uploadProgress, false);
				xhr.addEventListener("load", function(evt){
					var date = eval('(' + evt.target.responseText + ')');
					$("#submitProclamation").attr("href","javascript:submitProclamation();");
					window.location.href="${ctx}/proclamation/getProclamationList?groupName="+groupName;
					closeLoading();
				}, false);
				xhr.addEventListener("error", function(){
					
				}, false);
				//xhr.addEventListener("abort", uploadCanceled, false);
				xhr.open("POST", '${ctx}/proclamation/addProclamation.json');
				xhr.send(fd);
				
				/* $.ajax({type:'POST',dataType:'json',url:'${ctx}/proclamation/addProclamation.json',
					data:{
						proclamationTheme:proclamationTheme,
						groupName:groupName,
						proclamationDetail:proclamationDetail,
						fillUser:fillUser,
						phoneNumber:phoneNumber,
						startTime:startTime,
						endTime:endTime,
						fillTime:fillTime,
						proclamationId:proclamationId
					},success:function(json){
						
						$("#submitProclamation").attr("href","javascript:submitProclamation();");
						
					},complete:function(){
						closeLoading();
						window.location.href="${ctx}/proclamation/getProclamationList?groupName="+groupName;
					}
				}); */

			}else{
				
			}
		}, function() {
			console.log("...");
		});
	}
	function cancleSave(){
		modalConfirm("确定取消吗？", function(){
			wx.closeWindow();
		},null);
	}
	function backList(groupName){
		window.location.href="${ctx}/proclamation/getProclamationList?groupName="+groupName;
	}
	function compareTime(id){
		var startTime=$("#startTime").val();
		var endTime=$("#endTime").val();
		var date1 = new Date(Date.parse(startTime));  
        var date2 = new Date(Date.parse(endTime));  
        if (date1.getTime() > date2.getTime()) {  
        	modalAlert("开始时间不得大于结束时间，",null);
        	var time="#"+id;
        	$(time).val("");
            return false;  
        }
	}
	function removeFile(el,fileId){
		
		$.ajax({type:'POST',dataType:'json',url:'${ctx}/proclamation/deleteFile.json',
			data:{
				fileId:fileId
			},success:function(json){
				if(json.succ){
					$(el).parents('li').remove();
				}
			},complete:function(){
				
			}
		});
	}
	function uploadFile(fileId){

		/* $.ajax({type:'POST',dataType:'json',url:'${ctx}/proclamation/export.json',
			data:{
				fileId:fileId
			},success:function(json){
				alert("下载成功")
			},complete:function(){
				
			}
		}); */
		window.location.href="${ctx}/proclamation/export?fileId="+fileId;
	}
</script>
</br>
</br>
<center>
	<%@include file="../commons/bottom.jsp" %>
</center>
</body>
</html>

