<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%> 
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>代维费用详情</title>
<%@include file="../commons/head.jsp" %>
<style type="text/css">
.app-inputPM0{
	padding:0px;
	margin:0px;
	width: 100%;
	border: none;
	text-align: center;
	height:30px;
}
.{
	background-color: #e9e9e9;
}
.app-inputContent{
	background-color: #fff !important;
}
</style>
</head>
<body>
	<header onclick="backCostList();" class="am-topbar am-topbar-fixed-top">
		<div ><span class="am-topbar-brand am-icon-angle-left" ></span>
			<div class="am-topbar-brand  app-toolbar">代维费用详情</div>
		</div>
	</header>
<div class="am-scrollable-horizontal" >
	<input type="hidden" id="month" value="${fillMonth }"/>
	<table id="tb" class="am-table am-table-bordered am-table-striped am-table-hover am-text-center am-text-nowrap">
			<tr>
				<th class="am-text-middle" style="text-align: center">地市</th>
				<th class="am-text-middle" style="text-align: center">月份</th>
				<th class="am-text-middle" style="text-align: center">宏基站铁<br/>塔数量</th>
				<th class="am-text-middle" style="text-align: center">宏基站增<br/>高架数量</th>
				<th class="am-text-middle" style="text-align: center">宏基站抱<br/>杆数量</th>
				<th class="am-text-middle" style="text-align: center">VIP宏基站<br/>站房及配<br/>套数量</th>
				<th class="am-text-middle" style="text-align: center">标准宏基站<br/>站房及配<br/>套数量</th>
				<th class="am-text-middle" style="text-align: center">其它类型<br/>基站</th>
				<th class="am-text-middle" style="text-align: center">理论应付代维<br/>费用(元)</th>
				<th class="am-text-middle" style="text-align: center">考核后实际应<br/>付代维费用(元)</th>
				<th class="am-text-middle" style="text-align: center">本月代维费用<br/>是否支付</th>
				<th class="am-text-middle" style="text-align: center">填报日期</th>
				<th class="am-text-middle" style="text-align: center">填报人员</th>
			</tr>
			<c:forEach items="${costByMonthList }" var="obj">
				<tr>
					<td align="center">${obj.cityid }</td>
					<td align="center"><fmt:formatDate value="${obj.fillMonth }" pattern="M"/>月</td>
					<td align="center">${obj.bigStationTowerNum }</td>
					<td align="center">${obj.bigStationHeightenFrameNum }</td>
					<td align="center">${obj.bigStationPoleNum }</td>
					<td align="center">${obj.vipBigStationRoomNum }</td>
					<td align="center">${obj.standardsBigStationRoomNum }</td>
					<td align="center">${obj.otherTypeStationNum }</td>
					<td align="center" id="expensesPayable">${obj.expensesPayable }</td>
					<td align="center" id="actualPayment">${obj.actualPayment }</td>
					<td align="center">
						<c:if test="${obj.isPaid == 0}">否</c:if>
						<c:if test="${obj.isPaid == 1}">是</c:if>
					</td>
					<td class="am-text-center"><fmt:formatDate value="${obj.fillDate }" pattern="yyyy/MM/dd"/></td>
					<td class="am-text-center">${obj.fillMan }</td>
				</tr>
			</c:forEach>
	</table>
</div>
<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
	<c:if test='${userInfo.deptId != "市公司" }'>
		<div class="am-u-sm-12">
			<button  onclick="flashCostDetail();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
				<strong>刷新</strong>
			</button>
		</div>
	</c:if>
	<c:if test='${userInfo.deptId == "市公司" }'>
		<div class="am-u-sm-6">
			<button  onclick="flashCostDetail();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
				<strong>刷新</strong>
			</button>
		</div>
		<div class="am-u-sm-6">
			<button  onclick="updateCostRecord();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
				<strong>修改</strong>
			</button>
		</div>
	</c:if>
		
</div>
<script type="text/javascript">
function backCostList(){
	window.location.href="${ctx}/maintenancecost/goCost?selectId=2"
}
function flashCostDetail(){
	var month=$("#month").val();
	window.location.href="${ctx}/maintenancecost/getCostDetail?fillMonth="+month;
}
function updateCostRecord(){
	var month=$("#month").val();
	window.location.href="${ctx}/maintenancecost/updateCostRecord?selectId=1&fillMonth="+month;
}
function fmoney(s,n){ 
//var s=$("#expensesPayable").text().trim();
  n = n > 0 && n <= 20 ? n : 2; 
  s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + ""; 
  var l = s.split(".")[0].split("").reverse(), 
  r = s.split(".")[1];
  t = "";
  for(i = 0; i < l.length; i ++ ){ 
    t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
  } 
  return "¥"+t.split("").reverse().join("") + "." + r; 
}
function setMoney(){
	var trs=document.getElementById("tb").rows;
	for(var i=1;i<trs.length;i++){
		var tds=trs[i].cells;
		var expensesPayable=tds[8].innerText;
		var actualPayment=tds[9].innerText;
		var str=fmoney(expensesPayable,2);
		var str1=fmoney(actualPayment,2);
		tds[8].innerText=str;
		tds[9].innerText=str1;
	}
	
}
window.onload=setMoney();
</script>
</body>
</html>
