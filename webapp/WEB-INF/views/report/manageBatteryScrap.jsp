<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<div class="am-scrollable-horizontal">
  <table  class="am-table am-table-bordered am-table-striped am-table-hover am-text-center am-text-nowrap">
    <thead>
	  <tr>
	    <th style="text-align:center;">市分公司</th>
	    <th style="text-align:center;">计划整治<br>站点总数</th>
	    <th style="text-align:center;">计划整治<br>蓄电池组数</th>
	    <th style="text-align:center;">累计新采购<br>蓄电池组数</th>
	    <th style="text-align:center;">累计退网<br>蓄电池组数</th>
	    <th style="text-align:center;">2011年及以后入<br>网使用的蓄电池组数</th>
	    <th style="text-align:center;">2011年前入网<br>使用蓄电池组数</th>
	    <th style="text-align:center;">已完成财务报<br>废蓄电池组数</th>
	    <th style="text-align:center;">填报时间</th>
	    <th style="text-align:center;">填报人</th>
	    <th style="text-align:center;">备注 </th>
	  </tr>
    </thead>
    <tbody id="manageBatteryRecordScrapList">
	
    </tbody>
  </table>
</div>
<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
	<div class="am-u-sm-12">
		<a class="am-btn am-btn-success am-radius am-btn-block" style="color:white;" href="javascript:getManageBatteryRecordScrapList();"><strong>刷新</strong></a>
	</div>
</div>
<script type="text/javascript">
function getManageBatteryRecordScrapList(){
	showLoading();
	$.ajax({dataType:'json',url:'${ctx}/report/manageBatteryRecordScrapList.json',
		success:function(data){
			var coldataObj=data.manageBatteryRecordScrapList;
			var coldataStr="";
			if(coldataObj){
				$("#manageBatteryRecordScrapList").html("");
				for(var i=0;i<coldataObj.length;i++){
					var tempStr="<td>"+coldataObj[i].cityid+"</td>";
					if(i==coldataObj.length-1){
						tempStr="<td>合计</td>";
					}
					coldata="<tr>"+tempStr+"<td>"+
					replaceNna(coldataObj[i].planCount,coldataObj[i].planCount," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].planBatteryGroupCount,coldataObj[i].planBatteryGroupCount," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].purchaseBatteryGroup,coldataObj[i].purchaseBatteryGroup," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].logoutBatteryGroup,coldataObj[i].logoutBatteryGroup," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].after2011UseBatteryGroup,coldataObj[i].after2011UseBatteryGroup," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].before2011UseBatteryGroup,coldataObj[i].before2011UseBatteryGroup," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].finishScrapBatteryGroup,coldataObj[i].finishScrapBatteryGroup," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].fillTime,coldataObj[i].fillTime," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].fillMan,coldataObj[i].fillMan," &nbsp; ")+"</td><td>"+
					replaceNna(coldataObj[i].mark,coldataObj[i].mark," &nbsp; ")+"</td></tr>";
					
					$("#manageBatteryRecordScrapList").append(coldata);
				}
			}
		},complete:function(){
			closeLoading();
		}
	});
}
getManageBatteryRecordScrapList();
</script>