<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<div class="am-scrollable-horizontal">
  <table  class="am-table am-table-bordered am-table-striped am-table-hover am-text-center am-text-nowrap">
    <thead>
	  <tr>
	    <th class="am-text-middle" style="text-align:center;" rowspan="2" >序号</th>
	    <th class="am-text-middle" style="text-align:center;" rowspan="2">省份</th>
	    <th class="am-text-middle" style="text-align:center;" colspan="2">截止到2015年本地站址数量</th>
	    <th class="am-text-middle" style="text-align:center;" colspan="2">2016年计划实施安装站址数量</th>
	    <th class="am-text-middle" style="text-align:center;" rowspan="2">采购方式</th>
	    <th class="am-text-middle" style="text-align:center;" colspan="2">已完成采购站址数量</th>
	    <th class="am-text-middle" style="text-align:center;" rowspan="2">计划实施方案</th>
	    <th class="am-text-middle" style="text-align:center;" colspan="2">已完成安装站址数量</th>
	    <th class="am-text-middle" style="text-align:center;" colspan="2">已与FSU同步开通数量</th>
	    <th class="am-text-middle" style="text-align:center;" rowspan="2">填报时间</th>
	    <th class="am-text-middle" style="text-align:center;" rowspan="2">填报人</th>
	    <th class="am-text-middle" style="text-align:center;" rowspan="2">邮箱</th>
	    <th class="am-text-middle" style="text-align:center;" rowspan="2">联系方式</th>
	    <th class="am-text-middle" style="text-align:center;" rowspan="2">备注 </th>
	  </tr>
	  <tr>
	    <th class="am-text-middle" style="text-align:center;border: 1px solid #ddd;">机房</th>
	    <th class="am-text-middle" style="text-align:center;">机柜</th>
	    <th class="am-text-middle" style="text-align:center;">机房</th>
	    <th class="am-text-middle" style="text-align:center;">机柜</th>
	    <th class="am-text-middle" style="text-align:center;">机房</th>
	    <th class="am-text-middle" style="text-align:center;">机柜</th>
	    <th class="am-text-middle" style="text-align:center;">机房</th>
	    <th class="am-text-middle" style="text-align:center;">机柜</th>
	    <th class="am-text-middle" style="text-align:center;">机房</th>
	    <th class="am-text-middle" style="text-align:center;">机柜</th>
	  </tr>
    </thead>
    <tbody id="doorSystemList">
	 
    </tbody>
  </table>
</div>

<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
	<c:if test='${"group" ne source}'>
		<div class="am-u-sm-4">
			<a class="am-btn am-btn-primary am-radius am-btn-block" style="color:white;" href="javascript:getDoorSystemDate();"><strong>修改</strong></a>
		</div>
		<div class="am-u-sm-4">
			<a class="am-btn am-btn-success am-radius am-btn-block" style="color:white;" href="javascript:loadDoorSystemList();"><strong>刷新</strong></a>
		</div>
		<div class="am-u-sm-4">
			<a class="am-btn am-btn-warning am-radius am-btn-block" style="color:white;" href="javascript:sendMail();"><strong>推送邮件</strong></a>
		</div>
	</c:if>
	<c:if test='${"group" eq source}'>
		<div class="am-u-sm-6">
			<a class="am-btn am-btn-success am-radius am-btn-block" style="color:white;" href="javascript:loadDoorSystemList();"><strong>刷新</strong></a>
		</div>
		<div class="am-u-sm-6">
			<a class="am-btn am-btn-warning am-radius am-btn-block" style="color:white;" href="javascript:sendMail();"><strong>推送邮件</strong></a>
		</div>
	</c:if>
</div>

<script type="text/javascript">
function loadDoorSystemList(){
	showLoading();
	$.ajax({dataType:'json',url:'${ctx}/doorSystem/selectList.json',
		success:function(data){
			var coldataObj=data.doorSystemList;
			var coldataStr="";
			var obj={};
			if(coldataObj){
				$("#doorSystemList").html("");
				for(var i=0;i<coldataObj.length;i++){
					obj=coldataObj[i];
					
					coldataStr="<tr><td>"+
					(coldataObj.length-i)+"</td><td>"+
					obj.provinceId+"</td><td>"+
					replaceNna(obj.before2015StationRoomNum,obj.before2015StationRoomNum," &nbsp; ")+"</td><td>"+
					replaceNna(obj.before2015StationCabineNum,obj.before2015StationCabineNum," &nbsp; ")+"</td><td>"+
					replaceNna(obj.planRoomNum2016,obj.planRoomNum2016," &nbsp; ")+"</td><td>"+
					replaceNna(obj.planCabineNum2016,obj.planCabineNum2016," &nbsp; ")+"</td><td>"+
					replaceNna(obj.procurementMethods,obj.procurementMethods," &nbsp; ")+"</td><td>"+
					replaceNna(obj.finishProcurementRoomNum,obj.finishProcurementRoomNum," &nbsp; ")+"</td><td>"+
					replaceNna(obj.finishProcurementCabineNum,obj.finishProcurementCabineNum," &nbsp; ")+"</td><td>"+
					replaceNna(obj.implementationPlan,obj.implementationPlan," &nbsp; ")+"</td><td>"+
					replaceNna(obj.finishInstalledRoomNum,obj.finishInstalledRoomNum," &nbsp; ")+"</td><td>"+
					replaceNna(obj.finishInstalledCabineNum,obj.finishInstalledCabineNum," &nbsp; ")+"</td><td>"+
					replaceNna(obj.synchronousWithFsuRoomNum,obj.synchronousWithFsuRoomNum," &nbsp; ")+"</td><td>"+
					replaceNna(obj.synchronousWithFsuCabinNum,obj.synchronousWithFsuCabinNum," &nbsp; ")+"</td><td>"+
					replaceNna(obj.fillTime,obj.fillTime," &nbsp; ")+"</td><td>"+
					replaceNna(obj.fillMan,obj.fillMan," &nbsp; ")+"</td><td>"+
					replaceNna(obj.email,obj.email," &nbsp; ")+"</td><td>"+
					replaceNna(obj.phoneNumber,obj.phoneNumber," &nbsp; ")+"</td><td>"+
					replaceNna(obj.mark,obj.mark," &nbsp; ")+"</td></tr>";
					
					$("#doorSystemList").append(coldataStr);
				}
			}
		},complete:function(){
			closeLoading();
		}
	});
}
function getDoorSystemDate(){
	window.location.href="${ctx}/doorSystem/main";
}
window.onload=loadDoorSystemList();

function sendMail(){
	$('#send-mail-prompt').modal({
	      relatedTarget: this,
	      onConfirm: function(e) {
	    	 var mail=e.data;
	    	 var re = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
	    	 if(!re.test(mail)){
	    		 //$("#mail").val("");
	    		 modalAlert("邮件格式不正确", function(){
	    			$("#mail").val("");
	    		 });
	    	 }else{
	    		 $.ajax({
	 	        	type:'POST',
	 	        	url:'${ctx}/doorSystem/sendMail.json',
	 	        	data:{
	 	        		mail:mail
	 	        	},
	 	        	success:function(json){
	 	        		modalAlert("邮件发送成功", function(){
	 	        			$("#mail").val("");
	 	        		});
	 	        	},
	 	        	error:function(){
	 	        		modalAlert("邮件发送失败", function(){
	 	        			$("#mail").val("");
	 	        		});
	 	        	}
	 	        }); 
	    	 }
	        
	      },
	      onCancel: function(e) {
	    	  $("#mail").val("");
	      }
	    });
}

</script>