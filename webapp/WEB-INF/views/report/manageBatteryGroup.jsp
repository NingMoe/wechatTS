<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<div class="am-scrollable-horizontal">
  <table class="am-table am-table-bordered am-table-striped am-table-hover am-text-center am-text-nowrap">
    <thead>
	  <tr>
	    <th style="text-align:center;">省分公司</th>
	    <th style="text-align:center;">计划整治<br>站点总数</th>
	    <th style="text-align:center;">计划整治<br>总费用（元）</th>
	    <th style="text-align:center;">近一周计划<br>整治站点数量</th>
	    <th style="text-align:center;">近一周实际<br>整治站点数量</th>
	    <th style="text-align:center;">累计开工<br>站点数量 </th>
	    <th style="text-align:center;">累计完成<br>站点数量</th>
	    <th style="text-align:center;">累计完成站点占<br>总站点比率（%）</th>
	    <th style="text-align:center;">累计支付<br>金额（元）</th>
	    <th style="text-align:center;">累计支付金<br>额完成率（%）</th>
	    <th style="text-align:center;">填报时间</th>
	    <th style="text-align:center;">填报人</th>
	    <th style="text-align:center;">备注 </th>
	  </tr>
    </thead>
    <tbody id="manageBatteryRecordGroupList">
	 
    </tbody>
  </table>
</div>
<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
	<div class="am-u-sm-12">
		<a class="am-btn am-btn-success am-radius am-btn-block" style="color:white;" href="javascript:getManageBatteryRecordGroupList();"><strong>刷新</strong></a>
	</div>
</div>
<script type="text/javascript">
function getManageBatteryRecordGroupList(){
	showLoading();
	$.ajax({dataType:'json',url:'${ctx}/report/manageBatteryRecordGroupList.json',
		success:function(data){
			var coldataObj=data.manageBatteryRecordGroupList;
			var coldataStr="";
			if(coldataObj){
				$("#manageBatteryRecordGroupList").html("");
				for(var i=0;i<coldataObj.length;i++){
					var theMark=coldataObj[i].mark;
					if(null==theMark){
						theMark="&nbsp;";
					}
					coldata="<tr><td>"+
						replaceNna(coldataObj[i].provinceid,coldataObj[i].provinceid," &nbsp; ")+"</td><td>"+
						replaceNna(coldataObj[i].planCount,coldataObj[i].planCount," &nbsp; ")+"</td><td>"+
						replaceNna(coldataObj[i].planCost,coldataObj[i].planCost," &nbsp; ")+"</td><td>"+
						replaceNna(coldataObj[i].planCountWeek,coldataObj[i].planCountWeek," &nbsp; ")+"</td><td>"+
						replaceNna(coldataObj[i].actCountWeek,coldataObj[i].actCountWeek," &nbsp; ")+"</td><td>"+
						replaceNna(coldataObj[i].startSum,coldataObj[i].startSum," &nbsp; ")+"</td><td>"+
						replaceNna(coldataObj[i].completeSum,coldataObj[i].completeSum," &nbsp; ")+"</td><td>"+
						replaceNna(coldataObj[i].completePro,coldataObj[i].completePro+'%'," &nbsp; ")+"</td><td>"+
						replaceNna(coldataObj[i].paymentAmount,coldataObj[i].paymentAmount," &nbsp; ")+"</td><td>"+
						replaceNna(coldataObj[i].paymentPro,coldataObj[i].paymentPro+'%'," &nbsp; ")+"</td><td>"+
						replaceNna(coldataObj[i].fillTime,coldataObj[i].fillTime," &nbsp; ")+"</td><td>"+
						replaceNna(coldataObj[i].fillMan,coldataObj[i].fillMan," &nbsp; ")+"</td><td>"+
						theMark+"</td></tr>";
					$("#manageBatteryRecordGroupList").append(coldata);
				}
			}
		},complete:function(){
			closeLoading();
		}
	});
}
getManageBatteryRecordGroupList();
</script>