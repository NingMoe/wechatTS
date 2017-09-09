<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<div class="am-scrollable-horizontal">
  <table  class="am-table am-table-bordered am-table-striped am-table-hover am-text-center am-text-nowrap">
    <thead>
	  <tr>
	    <th style="text-align:center;">省分公司</th>
	    <th style="text-align:center;">市分公司</th>
	    <th style="text-align:center;">计划整治<br>站点总数</th>
	    <th style="text-align:center;">计划整治<br>总费用（元）</th>
	    <th style="text-align:center;">近一周计划<br>整治站点数量</th>
	    <th style="text-align:center;">近一周实际<br>整治站点数量</th>
	    <th style="text-align:center;">累计开工<br>站点数量 </th>
	    <th style="text-align:center;">累计完成<br>站点数量</th>
	    <th style="text-align:center;">累计完成站点占<br>总站点比率（%）</th>
	    <th style="text-align:center;">累计支付<br>金额（元）</th>
	    <th style="text-align:center;">累计支付金<br>额完成率（%）</th>
	    <c:if test='${"黑龙江省" eq sessionScope.s_user.provinceId}'>
	    	<th style="text-align:center;">累计开工站点<br>数量(PMS线上)</th>
		    <th style="text-align:center;">累计完工交维<br>站点数量(PMS线上)</th>
		    <th style="text-align:center;">累计完工交维站点占<br>总站点比率(PMS线上)</th>
		    <th style="text-align:center;">初步决算<br>项目数量</th>
		    <th style="text-align:center;">累计转<br>资金额</th>
		    <th style="text-align:center;">转资完成率</th>
	    </c:if>
	    <th style="text-align:center;">填报时间</th>
	    <th style="text-align:center;">填报人</th>
	    <th style="text-align:center;">备注 </th>
	  </tr>
    </thead>
    <tbody id="manageBatteryRecordProvinceList">
	 
    </tbody>
  </table>
</div>
<c:if test='${dontNeedCityTotal}'>
 <div style="padding:20px;">
	${sessionScope.s_user.provinceId }未开通地市上报权限，如有使用需求，请联系创智信科厂家人员。
 </div>
</c:if>

<c:if test="${source eq 'province' || source eq 'group'}">
	<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
		<div class="am-u-sm-4">
			<a class="am-btn am-btn-primary am-radius am-btn-block" style="color:white;" href="javascript:updateManageBatteryRecord_J();"><strong>修改</strong></a>
		</div>
		<div class="am-u-sm-4">
			<a class="am-btn am-btn-success am-radius am-btn-block" style="color:white;" href="javascript:getManageBatteryRecordProvinceList();"><strong>刷新</strong></a>
		</div>
		<div class="am-u-sm-4">
			<a class="am-btn am-btn-danger am-radius am-btn-block" style="color:white;" href="javascript:addManageBatteryRecord_J();"><strong>上报</strong></a>
		</div>
	</div>
</c:if>



<c:if test="${source ne 'province'}">
<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
	<div class="am-u-sm-6">
		<a class="am-btn am-btn-primary am-radius am-btn-block" style="color:white;" href="javascript:updateManageBatteryRecord_J();"><strong>修改</strong></a>
	</div>
	<div class="am-u-sm-6">
		<a class="am-btn am-btn-success am-radius am-btn-block" style="color:white;" href="javascript:getManageBatteryRecordProvinceList();"><strong>刷新</strong></a>
	</div>
</div>
</c:if>

<script type="text/javascript">
function getManageBatteryRecordProvinceList(){
	showLoading();
	$.ajax({dataType:'json',url:'${ctx}/report/manageBatteryRecordProvinceList.json',
		success:function(data){
			var coldataObj=data.manageBatteryRecordProvinceList;
			var coldataStr="";
			if(coldataObj){
				$("#manageBatteryRecordProvinceList").html("");
				for(var i=0;i<coldataObj.length;i++){
					var tempStr="<td>"+coldataObj[i].provinceid+"</td><td>"+coldataObj[i].cityid+"</td>";
					if(-1==coldataObj[i].id){
						if(null==coldataObj[i].fillTime){
							continue;
						}
						tempStr="<td colspan='2'>"+replaceNna(coldataObj[i].provinceid,coldataObj[i].provinceid," &nbsp; ")+coldataObj[i].cityid+"</td>";
						
					}
					
					var hstr="";
					if($("#mPprovinceId").val()=='黑龙江省'){
						hstr+=replaceNna(coldataObj[i].startSumPms,coldataObj[i].startSumPms," &nbsp; ")+"</td><td>"+
						replaceNna(coldataObj[i].completeSumPms,coldataObj[i].completeSumPms," &nbsp; ")+"</td><td>"+
						replaceNna(coldataObj[i].completeSumPmsPro,coldataObj[i].completeSumPmsPro+'%'," &nbsp; ")+"</td><td>"+
						replaceNna(coldataObj[i].preliminaryFinalAccounts,coldataObj[i].preliminaryFinalAccounts," &nbsp; ")+"</td><td>"+
						replaceNna(coldataObj[i].cumulativeTransferAmount,coldataObj[i].cumulativeTransferAmount," &nbsp; ")+"</td><td>"+
						replaceNna(coldataObj[i].ascumulativeTransferAmountPro,coldataObj[i].ascumulativeTransferAmountPro+'%'," &nbsp; ")+"</td><td>";
					}
					
					coldata="<tr>"+tempStr+"<td>"+
					replaceNna(coldataObj[i].planCount,coldataObj[i].planCount," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].planCost,coldataObj[i].planCost," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].planCountWeek,coldataObj[i].planCountWeek," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].actCountWeek,coldataObj[i].actCountWeek," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].startSum,coldataObj[i].startSum," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].completeSum,coldataObj[i].completeSum," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].completePro,coldataObj[i].completePro+'%'," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].paymentAmount,coldataObj[i].paymentAmount," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].paymentPro,coldataObj[i].paymentPro+'%'," &nbsp; ")+"</td><td>"+
					hstr+
					replaceNna(coldataObj[i].fillTime,coldataObj[i].fillTime," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].fillMan,coldataObj[i].fillMan," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].mark,coldataObj[i].mark," &nbsp; ")+"</td></tr>";
					
					$("#manageBatteryRecordProvinceList").append(coldata);
				}
			}
		},complete:function(){
			closeLoading();
		}
	});
}
getManageBatteryRecordProvinceList();
function updateManageBatteryRecord_J(){
	window.location.href="${ctx}/report/manageBatteryRecordByProvince?selectId=1";
}
function addManageBatteryRecord_J(){
	$.ajax({type:"POST",dataType:"json",url:"${ctx}/report/addManageBatteryRecord_J.json",
		data:{},
		success:function(json){
			if(json.succ){
				window.location.href="${ctx}/report/manageBatteryRecordByProvince?selectId=3";
			}
		}
	});
}
</script>