<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>超时工单TOP10</title>
<%@include file="../commons/head.jsp" %>
<%@include file="../report/highPrompt.jsp" %>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div ><!-- <span class="am-topbar-brand am-icon-angle-left" ></span> -->
			<div class="am-topbar-brand  app-toolbar">
				<table>
					<tr>
						<td width="30%">超时工单TOP10</td>
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
	
	<div class="am-alert am-text-lg am-text-center" style="padding:2px;margin-bottom:0px;">按区域统计超时工单个数</div>
	<div class="am-scrollable-horizontal">
	  <table  class="am-table am-table-bordered am-table-striped am-table-hover am-text-center am-text-nowrap">
	    <thead>
		  <tr>
		  	<th style="text-align:center;">操作</th>
		    <th style="text-align:center;">区县</th>
		    <th style="text-align:center;">超时工单个数</th>
		    <th style="text-align:center;">超时工单占比</th>
		    <th style="text-align:center;">未处理原因</th>
		    <th style="text-align:center;">影响基站</th>
		    <th style="text-align:center;">地市</th>
		    <th style="text-align:center;">省份</th>
		    
		  </tr>
	    </thead>
	    <tbody id="areaList">
		  <c:forEach var="obj" items="${areaList}" varStatus="status">
		  	<tr id="${obj.highTimeOutId}" onclick="responseText('${obj.highTimeOutId}','area','${obj.notDealReason}',this);"onclick="responseText('${obj.highTimeOutId}','area','${obj.notDealReason}',this);">
		  		<td>
		  			<button id ="response_${obj.highTimeOutId}" style="border:none;" ><i class="am-icon-pencil-square-o" ></i></button>
		  		</td>
			    <td>${obj.county}</td>
			    <td>${obj.timeOutNum}</td>
			    <td>${obj.timeOutRatio}</td>
			    <td class="notDealReason">${obj.notDealReason}</td>
			    <td>${obj.relatedStation}</td>
			    <td>${obj.city}</td>
			    <td>${obj.province}</td>
		  	</tr>
		  </c:forEach>
	    </tbody>
	  </table>
	</div>
	
	
	<div class="am-alert am-text-lg am-text-center" style="padding:2px;margin-bottom:0px;">按区域统计一级脱离告警次数</div>
	<div class="am-scrollable-horizontal">
	  <table  class="am-table am-table-bordered am-table-striped am-table-hover am-text-center am-text-nowrap">
	    <thead>
		  <tr>
		  	<th style="text-align:center;">操作</th>
		    <th style="text-align:center;">区县</th>
		    <th style="text-align:center;">一级脱离告警次数</th>
		    <th style="text-align:center;">未处理原因</th>
		    <th style="text-align:center;">影响基站</th>
		    <th style="text-align:center;">地市</th>
		    <th style="text-align:center;">省份</th>
		    
		  </tr>
	    </thead>
	    <tbody id="divorceList">
		  <c:forEach var="obj" items="${divorceList}" varStatus="status">
		  	<tr id="${obj.highDivorceId}" onclick="responseText('${obj.highDivorceId}','divorce','${obj.notDealReason}',this);">
		  		<td>
		  			<button id ="response_${obj.highDivorceId}" style="border:none;" ><i class="am-icon-pencil-square-o" ></i></button>
		  		</td>
			    <td>${obj.county}</td>
			    <td>${obj.divorceNum}</td>
			    <td class="notDealReason">${obj.notDealReason}</td>
			    <td>${obj.relatedStation}</td>
			    <td>${obj.city}</td>
			    <td>${obj.province}</td>
		  	</tr>
		  </c:forEach>
	    </tbody>
	  </table>
	</div>
	<div class="am-alert am-text-lg am-text-center" style="padding:2px;margin-bottom:0px;">按代维统计超时工单个数</div>
	<div class="am-scrollable-horizontal">
	  <table  class="am-table am-table-bordered am-table-striped am-table-hover am-text-center am-text-nowrap">
	    <thead>
		  <tr>
		  	<th style="text-align:center;">操作</th>
		    <th style="text-align:center;">区县</th>
		    <th style="text-align:center;">超时工单个数</th>
		    <th style="text-align:center;">未处理原因</th>
		    <th style="text-align:center;">影响基站</th>
		    <th style="text-align:center;">代维公司</th>
		    <th style="text-align:center;">地市</th>
		    <th style="text-align:center;">省份</th>
		    
		  </tr>
	    </thead>
	    <tbody id="proxyList">
		  <c:forEach var="obj" items="${proxyList}" varStatus="status">
		  	<tr id="${obj.highTimeOutId}" onclick="responseText('${obj.highTimeOutId}','proxy','${obj.notDealReason}',this);">
		  		<td>
		  			<button id ="response_${obj.highTimeOutId}" style="border:none;" ><i class="am-icon-pencil-square-o" ></i></button>
		  		</td>
			    <td>${obj.county}</td>
			    <td>${obj.timeOutNum}</td>
			    <td class="notDealReason">${obj.notDealReason}</td>
			    <td>${obj.relatedStation}</td>
			    <td>${obj.proxyCompany}</td>
			    <td>${obj.city}</td>
			    <td>${obj.province}</td>
		  	</tr>
		  </c:forEach>
	    </tbody>
	  </table>
	</div>
<script type="text/javascript">
var notDealReason=$("#reasonBill").val();
var notDealReasonEl={};
var idVal="";
var type="";
function responseText(id,typeVal,notDealReasonVal,el){
	idVal=id;
	type=typeVal;
	var impTime=$("#impTime").val();
	notDealReasonEl=$(el).find("td[class='notDealReason']");
	notDealReasonVal=notDealReasonEl.text().trim();
	$("#reasonBill").val(notDealReasonVal);
	$('#response-bill-prompt').modal({
	      relatedTarget: this,
	      onConfirm: function(e) {
	    	  notDealReason=$("#reasonBill").val();
	    	  $.ajax({type:"POST",dataType:"json",url:'${ctx}/fourHight/updateHighBillTimeOut.json',
	  			data:{
	  				billId:idVal,
	  				type:type,
	  				notDealReason:notDealReason
	  			},
	  			success:function(data){
	  				modalAlert(data.msg, function(){
	  					notDealReasonEl.html(notDealReason);
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