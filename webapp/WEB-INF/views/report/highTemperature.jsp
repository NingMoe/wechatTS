<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>高高温TOP10</title>
<%@include file="../commons/head.jsp" %>
<%@include file="../report/highPrompt.jsp" %>
<link rel="stylesheet" href="${ctx}/assets/css/amazeui.switch.css"/>
<script src="${ctx}/assets/js/amazeui.switch.min.js"></script>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div ><!-- <span class="am-topbar-brand am-icon-angle-left" ></span> -->
			<div class="am-topbar-brand  app-toolbar">
				<table>
					<tr>
						<td width="30%">高高温TOP10</td>
						<td width="60%" align="center"  style="white-space: nowrap;">
							<marquee scrollAmount=2  style="width: 80%;height: 33px;" >
								<strong>基站电源问题管理专家，创智信科专业电源维护10年</strong>
							</marquee>
						</td>
					</tr>
				</table>
			</div>
			<input type="hidden" id="impTime" name="impTime" value="${impTime}"> 
		</div>
	</header>
	
	<div class="am-alert am-text-lg am-text-center" style="padding:2px;margin-bottom:0px;">按天数统计超高温基站</div>
	<div class="am-scrollable-horizontal">
	  <table  class="am-table am-table-bordered am-table-striped am-table-hover am-text-center am-text-nowrap">
	    <thead>
		  <tr>
		  	<th style="text-align:center;">操作</th>
		  	<th style="text-align:center;">区县</th>
		    <th style="text-align:center;">基站名称</th>
		    <th style="text-align:center;">高温天数</th>
		    <th style="text-align:center;">是否解决</th>
		    <th style="text-align:center;">问题原因</th>
		    <th style="text-align:center;">代维公司</th>
		    <th style="text-align:center;">站址来源</th>
		    <th style="text-align:center;">基站编码</th>
		    <th style="text-align:center;">地市</th>
		    <th style="text-align:center;">省份</th>
		  </tr>
	    </thead>
	    <tbody id="hightHighNumList">
		  <c:forEach var="obj" items="${hightHightNumList}" varStatus="status">
		  	<tr id="${obj.highTemperatureId}" onclick="responseText('${obj.highTemperatureId}','${obj.reason}','${obj.statu}',this);">
		  		<td>
		  			<button id ="response_${obj.highTemperatureId}" style="border:none;" ><i class="am-icon-pencil-square-o" ></i></button>
		  		</td>
		  		<td>${obj.county}</td>
		  		<td>${obj.stationName}</td>
		  		<td>${obj.numOrLength}</td>
		  		<td class="statu">
		  		<c:if test='${"1" eq obj.statu}'>
		  			是
			    </c:if>
			    <c:if test='${"1" ne obj.statu}'>
		  			否
			    </c:if>
				</td>
				<td class="reason"> 
			    	${obj.reason}
			    </td>
			    <td>${obj.proxyCompany}</td>
			    <td>${obj.stationSource}</td>
			    <td>${obj.stationNo}</td>
			    <td>${obj.city}</td>
		  	    <td>${obj.province}</td>
		  	</tr>
		  </c:forEach>
	    </tbody>
	  </table>
	</div>
	
	<div class="am-alert am-text-lg am-text-center" style="padding:2px;margin-bottom:0px;">按时长统计超高温基站</div>
	<div class="am-scrollable-horizontal">
	  <table  class="am-table am-table-bordered am-table-striped am-table-hover am-text-center am-text-nowrap">
	    <thead>
		  <tr>
		  	<th style="text-align:center;">操作</th>
		  	<th style="text-align:center;">区县</th>
		    <th style="text-align:center;">基站名称</th>
		    <th style="text-align:center;">高温持续时长(分)</th>
		    <th style="text-align:center;">是否解决</th>
		    <th style="text-align:center;">问题原因</th>
		    <th style="text-align:center;">代维公司</th>
		    <th style="text-align:center;">站址来源</th>
		    <th style="text-align:center;">基站编码</th>
		    <th style="text-align:center;">地市</th>
		    <th style="text-align:center;">省份</th>
		  </tr>
	    </thead>
	    <tbody id="hightHighLengthList">
		  <c:forEach var="obj" items="${hightHightLengthList}" varStatus="status">
		  	<tr id="${obj.highTemperatureId}" onclick="responseText('${obj.highTemperatureId}','${obj.reason}','${obj.statu}',this);">
		  		<td>
		  			<button id ="response_${obj.highTemperatureId}" style="border:none;" ><i class="am-icon-pencil-square-o" ></i></button>
		  		</td>
		  		<td>${obj.county}</td>
		  		<td>${obj.stationName}</td>
		  		<td>${obj.numOrLength}</td>
		  		<td class="statu">
		  		<c:if test='${"1" eq obj.statu}'>
		  			是
			    </c:if>
			    <c:if test='${"1" ne obj.statu}'>
		  			否
			    </c:if>
				</td>
				<td class="reason">
			    	${obj.reason}
			    </td>
			    <td>${obj.proxyCompany}</td>
			    <td>${obj.stationSource}</td>
			    <td>${obj.stationNo}</td>
			    <td>${obj.city}</td>
		  	    <td>${obj.province}</td>
		  	</tr>
		  </c:forEach>
	    </tbody>
	  </table>
	</div>
	
	<!-- <div class="am-u-sm-12 am-margin-bottom">
		<a class="am-btn am-btn-success am-radius am-btn-block" style="color:white;" href="javascript:submitSaveChange();"><strong>保存</strong></a>
	</div> -->
<script type="text/javascript">
	var reason=$("#reason").val();
	var statuBooleanVal=$('input[name="handleWidth"]').bootstrapSwitch('state');
	var statu= statuBooleanVal?"1":"0";
	var reasonEl={};
	var statuEl={};
	var idVal="";
	function responseText(id,reasonVal,statuVal,el){
		idVal=id;
		var impTime=$("#impTime").val();
		reasonEl=$(el).find("td[class='reason']");
		statuEl=$(el).find("td[class='statu']");
		reasonVal=reasonEl.text().trim();
		statuVal=statuEl.text().trim();
		$("#reason").val(reasonVal);
		if(statuVal == "否"){
			$('input[name="handleWidth"]').bootstrapSwitch('state',false);
		}else{
			$('input[name="handleWidth"]').bootstrapSwitch('state',true);
		}
		
		$('#response-prompt').modal({
		      relatedTarget: this,
		      onConfirm: function(e) {
		    	  reason=$("#reason").val();
		    	  statuBooleanVal=$('input[name="handleWidth"]').bootstrapSwitch('state');
		    	  statu= statuBooleanVal?"1":"0";
		    	  $.ajax({type:"POST",dataType:"json",url:'${ctx}/fourHight/updateHighHighTemperature.json',
		  			data:{
		  				highTemperatureId:idVal,
		  				reason:reason,
		  				statu:statu
		  			},
		  			success:function(data){
		  				modalAlert(data.msg, function(){
		  					reasonEl.html(reason);
		  					if(statu=='0'){
		  						statuEl.html("否");
		  					}else{
		  						statuEl.html("是");
		  					}
		  				});
		  			},complete:function(){
		  				closeLoading();
		  			}
		  		});
		    	  
		      },
		      onCancel: function(e) {
		      }
		    });
	}
</script>
<center>
<%@include file="../commons/bottom.jsp" %>
</center>
</body>
</html>