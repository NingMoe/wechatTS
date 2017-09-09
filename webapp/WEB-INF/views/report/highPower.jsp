<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>高电量TOP10</title>
<%@include file="../commons/head.jsp" %>
<%@include file="../report/highPrompt.jsp" %>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div ><!-- <span class="am-topbar-brand am-icon-angle-left" ></span> -->
			<div class="am-topbar-brand  app-toolbar">
				<table>
					<tr>
						<td width="30%">高电量TOP10</td>
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
	<div class="am-alert am-text-lg am-text-center" style="padding:2px;margin-bottom:0px;">按电量与直流负载比统计基站TOP10</div>
	<div class="am-scrollable-horizontal">
	  <table  class="am-table am-table-bordered am-table-striped am-table-hover am-text-center am-text-nowrap">
	    <thead>
		  <tr>
		  	<th style="text-align:center;">操作</th>
		    <th style="text-align:center;">区县</th>
		    <th style="text-align:center;">基站名称</th>
		    <th style="text-align:center;">电量与直流负载比</th>
		    <th style="text-align:center;">超高原因</th>
		    <th style="text-align:center;">解决方法</th>
		    <th style="text-align:center;">代维公司</th>
		    <th style="text-align:center;">基站编码</th>
		    <th style="text-align:center;">站址来源</th>
		    <th style="text-align:center;">地市</th>
		    <th style="text-align:center;">省份</th>
		  </tr>
	    </thead>
	    <tbody id="hightPowerNumList">
		  <c:forEach var="obj" items="${hightPowerNumList}" varStatus="status">
		  	<tr id="${obj.highPowerId}" onclick="responseText('${obj.highPowerId}','${obj.reason}','${obj.solution}',this);">
		  		<td>
		  			<button id ="response_${obj.highPowerId}" style="border:none;" ><i class="am-icon-pencil-square-o" ></i></button>
		  		</td>
			    <td>${obj.county}</td>
			    <td>${obj.stationName}</td>
			    <td>${obj.ratio}</td>
			    <td class='reason'>
			    	${obj.reason}
			    </td>
			    <td class='solution'>
			    	${obj.solution}
			    </td>
			    <td>${obj.proxyCompany}</td>
			    <td>${obj.stationNo}</td>
			    <td>${obj.stationSource}</td>
			    <td>${obj.city}</td>
			    <td>${obj.province}</td>
			    
		  	</tr>
		  </c:forEach>
	    </tbody>
	  </table>
	</div>
	
<script type="text/javascript">
var reason=$("#reasonPower").val();
var solution=$("#solution").val();
var reasonEl={};
var solutionEl={};
var idVal="";
function responseText(id,reasonVal,solutionVal,el){
	idVal=id;
	var impTime=$("#impTime").val();
	reasonEl=$(el).find("td[class='reason']");
	solutionEl=$(el).find("td[class='solution']");
	reasonVal=reasonEl.text().trim();
	solutionVal=solutionEl.text().trim();
	 $("#reasonPower").val(reasonVal);
	 $("#solution").val(solutionVal);
	  
	$('#response-power-prompt').modal({
	      relatedTarget: this,
	      onConfirm: function(e) {
	    	  reason=$("#reasonPower").val();
	    	  solution=$("#solution").val();
	    	  $.ajax({type:"POST",dataType:"json",url:'${ctx}/fourHight/updateHighPower.json',
	  			data:{
	  				highPowerId:idVal,
	  				reason:reason,
	  				solution:solution
	  			},
	  			success:function(data){
	  				modalAlert(data.msg, function(){
	  					reasonEl.html(reason);
	  					solutionEl.html(solution);
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