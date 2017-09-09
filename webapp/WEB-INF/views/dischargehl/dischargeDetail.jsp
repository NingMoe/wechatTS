<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%> 
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>利旧放电详情</title>
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
	<header onclick="backList();" class="am-topbar am-topbar-fixed-top">
		<div ><span class="am-topbar-brand am-icon-angle-left" ></span>
			<div class="am-topbar-brand  app-toolbar">利旧放电详情</div>
		</div>
	</header>
<div class="am-cf" >
<input type="hidden" name="code" value="${code }" id="code"/>
<input type="hidden" name="selectId" value="${selectId}" id="selectId"/>
<form id="fbean" class="am-form" style="margin:0px;">
	<input type="hidden" name="selectId" value="1" >
	<table class="am-table app-tb" style="margin_bottom:none;border:none;width: 100%">
			<tr>
				<td>测试计划名称：</td>
				<td>
					${recordBO.planName }
				</td>
			</tr>
			<tr>
				<td>蓄电池组编码:</td>
				<td>
					${recordBO.recordId }
				</td>
			</tr>
			<tr>
				<td>电池生产日期:</td>
				<td>
					<fmt:formatDate value="${recordBO.batteryProductDate }" pattern="yyyy-MM-dd" />
				</td>
			</tr>
			<tr>
			<td>测试日期:</td>
			<td>
				<fmt:formatDate value="${recordBO.testDate }" pattern="yyyy-MM-dd" />
			</td>
		</tr>
		<tr>
			<td>人员:</td>
			<td>
				${recordBO.testUser }
			</td>
		</tr>
		<tr>
			<td>品牌:</td>
			<td>
				${recordBO.brand }
			</td>
		</tr>
		<tr>
			<td>标充容量</td>
			<td >
				${recordBO.standardCapacity }
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;">放电小时率:</td>
			<td id="dischargeRateId">
				${recordBO.dischargeRate }
			</td>
		</tr>
		<tr>
			<td>放电电流</td>
			<td>
				${recordBO.dischargeCurrent }
			</td>
		</tr>
		<tr>
			<td>单体电压</td>
			<td id="singletonVoltageId">
				${recordBO.singletonVoltage }
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;">
								所在省：
			</td>
			<td>
				${recordBO.provinceId}
			</td>
		</tr>
		<tr>
			<td>
								所在市：
			</td>
			<td>
				${recordBO.cityId}
			</td>
		</tr>
		<tr>
			<td>
								所在县/区：
			</td>
			<td>
				${recordBO.countyId}
			</td>
		</tr>
	</table>
</form>
<div class="am-scrollable-horizontal" style="margin:none;border_top:0px;padding-top: 0px;">
			<table class="am-table am-table-bordered am-table-striped am-table-hover am-text-center am-text-nowrap app-tb" style="margin-bottom:0px;border-bottom: 1px slide;border-top: 0px;">
				  	<tbody id="dischargeDetail">
				  	<c:if test="${hlDischargeDetailList ==null || hlDischargeDetailList=='[]'}">
					  	<tr style="border-bottom: 1px slide;">
						   	<td class="am-padding-0"><input id="serialNumber" name="serialNumber" value="序号" style="width:35px" class="am-form-field app-inputPM0" readonly></td>
						    <td class="am-padding-0"><input id="barCode" name="barCode" value="单体编号" style="width:120px" class="am-form-field app-inputPM0" readonly></td>
						    <td class="am-padding-0"><input id="preDischargeVoltage" name="preDischargeVoltage" value="放电前电压" style="width:100px" class="am-form-field app-inputPM0" required readonly></td>
						    <td class="am-padding-0"><input id="ext1" name="ext1" value="1H" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
						    <td class="am-padding-0"><input id="ext2" name="ext2" value="2H" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
						    <td class="am-padding-0"><input id="ext3" name="ext3" value="3H" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
						    <td class="am-padding-0"><input id="ext4" name="ext4" value="4H" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
						    <td class="am-padding-0"><input id="ext5" name="ext5" value="5H" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
						    <td class="am-padding-0"><input id="ext6" name="ext6" value="6H" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
						    <td class="am-padding-0"><input id="ext7" name="ext7" value="7H" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
						    <td class="am-padding-0"><input id="ext8" name="ext8" value="8H" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
						    <td class="am-padding-0"><input id="ext9" name="ext9" value="9H" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
						    <td class="am-padding-0"><input id="ext10" name="ext10" value="10H" style="width:70px" class="am-form-field app-inputPM0 " readonly></td> 
					    </tr>
					    <tr id="sumVoltage">
				    		<td class="am-padding-0"><input id="serialNumber" name="serialNumber" value="" class="am-form-field app-inputPM0 app-inputContent" readonly></td>
					 		<td class="am-padding-0"><input id="barCode" name="barCode" value="总电压" class="am-form-field app-inputPM0 app-inputContent" readonly></td>
					 		<td class="am-padding-0"><input id="sumPreDischargeVoltage" name="preDischargeVoltage"  class="am-form-field app-inputPM0 app-inputContent" readonly></td>
						    <td class="am-padding-0"><input id="ext1_999" name="ext1"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
						    <td class="am-padding-0"><input id="ext2_999" name="ext2"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
						    <td class="am-padding-0"><input id="ext3_999" name="ext3"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
						    <td class="am-padding-0"><input id="ext4_999" name="ext4"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
						    <td class="am-padding-0"><input id="ext5_999" name="ext5"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
						    <td class="am-padding-0"><input id="ext6_999" name="ext6"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
						    <td class="am-padding-0"><input id="ext7_999" name="ext7"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
						    <td class="am-padding-0"><input id="ext8_999" name="ext8"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
						    <td class="am-padding-0"><input id="ext9_999" name="ext9"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
						    <td class="am-padding-0"><input id="ext10_999" name="ext10"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
					 	</tr>
				  	</c:if>
				  	<c:if test="${hlDischargeDetailList !=null}">
				  		<c:forEach var="obj" items="${hlDischargeDetailList }" varStatus="status">
						   	<c:if test="${obj.serialNumber == 0}">
						   	<tr style="border-bottom: 1px slide">
							    <td class="am-padding-0"><input id="serialNumber" name="serialNumber" value="序号" style="width:35px" class="am-form-field app-inputPM0 " readonly></td>
						    	<td class="am-padding-0"><input id="barCode" name="barCode" value="单体编号" style="width:120px" class="am-form-field app-inputPM0 " readonly></td>
						    	<td class="am-padding-0"><input id="preDischargeVoltage" name="preDischargeVoltage" value="放电前电压" style="width:100px" class="am-form-field app-inputPM0 " required readonly></td>
						    	<td class="am-padding-0"><input id="ext1_${obj.serialNumber}" name="ext1" value="${obj.ext1 }" style="width:70px" class="am-form-field app-inputPM0 " readonly></td> 
							    <td class="am-padding-0"><input id="ext2_${obj.serialNumber}" name="ext2" value="${obj.ext2 }" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
							    <td class="am-padding-0"><input id="ext3_${obj.serialNumber}" name="ext3" value="${obj.ext3 }" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
							    <td class="am-padding-0"><input id="ext4_${obj.serialNumber}" name="ext4" value="${obj.ext4 }" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
							    <td class="am-padding-0"><input id="ext5_${obj.serialNumber}" name="ext5" value="${obj.ext5 }" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
							    <td class="am-padding-0"><input id="ext6_${obj.serialNumber}" name="ext6" value="${obj.ext6 }" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
							    <td class="am-padding-0"><input id="ext7_${obj.serialNumber}" name="ext7" value="${obj.ext7 }" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
							    <td class="am-padding-0"><input id="ext8_${obj.serialNumber}" name="ext8" value="${obj.ext8 }" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
							    <td class="am-padding-0"><input id="ext9_${obj.serialNumber}" name="ext9" value="${obj.ext9 }" style="width:70px" class="am-form-field app-inputPM0 " readonly></td>
							    <td class="am-padding-0"><input id="ext10_${obj.serialNumber}" name="ext10" value="${obj.ext10 }" style="width:70px" class="am-form-field app-inputPM0 " readonly></td> 
						   	</tr>
						   	</c:if>
						   	<c:if test="${obj.serialNumber == 999}">
						   	<tr style="border-bottom: 1px slide" id="sumVoltage">
								<td class="am-padding-0"><input id="serialNumber" name="serialNumber" value="" class="am-form-field app-inputPM0 app-inputContent" readonly></td>
				 				<td class="am-padding-0"><input id="barCode_${obj.serialNumber}" name="barCode" value="总电压" class="am-form-field app-inputPM0 app-inputContent" readonly></td>
						    	<td class="am-padding-0"><input id="preDischargeVoltage${obj.serialNumber}" name="preDischargeVoltage" value="${obj.preDischargeVoltage }"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext1_${obj.serialNumber}" name="ext1" value="${obj.ext1 }"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext2_${obj.serialNumber}" name="ext2" value="${obj.ext2 }"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext3_${obj.serialNumber}" name="ext3" value="${obj.ext3 }"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext4_${obj.serialNumber}" name="ext4" value="${obj.ext4 }"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext5_${obj.serialNumber}" name="ext5" value="${obj.ext5 }"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext6_${obj.serialNumber}" name="ext6" value="${obj.ext6 }"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext7_${obj.serialNumber}" name="ext7" value="${obj.ext7 }"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext8_${obj.serialNumber}" name="ext8" value="${obj.ext8 }"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext9_${obj.serialNumber}" name="ext9" value="${obj.ext9 }"   class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext10_${obj.serialNumber}" name="ext10" value="${obj.ext10 }"   class="am-form-field app-inputPM0 app-inputContent" readonly></td> 
						   	</tr>
						   	</c:if>
						   	<c:if test="${obj.serialNumber != 0 && obj.serialNumber != 999}">
						   	<tr style="border-bottom: 1px slide" id="every_${obj.serialNumber}">
							   	<td class="am-padding-0"><input id="serialNumber" name="serialNumber" value="${obj.serialNumber}" class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							   	<td class="am-padding-0"><input id="barCode_${obj.serialNumber}" name="barCode" value="${obj.barCode }" class="am-form-field app-inputPM0 app-inputContent"  style="color:#0000ff;" onclick="updateDetailId(this.value);" readonly></td>
							   	<td class="am-padding-0"><input id="preDischargeVoltage_${obj.serialNumber}" name="preDischargeVoltage" value="${obj.preDischargeVoltage }"  class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext1_${obj.serialNumber}" name="ext1" value="${obj.ext1 }"  class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext2_${obj.serialNumber}" name="ext2" value="${obj.ext2 }"  class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext3_${obj.serialNumber}" name="ext3" value="${obj.ext3 }"  class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext4_${obj.serialNumber}" name="ext4" value="${obj.ext4 }"  class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext5_${obj.serialNumber}" name="ext5" value="${obj.ext5 }"  class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext6_${obj.serialNumber}" name="ext6" value="${obj.ext6 }"  class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext7_${obj.serialNumber}" name="ext7" value="${obj.ext7 }"  class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext8_${obj.serialNumber}" name="ext8" value="${obj.ext8 }"  class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext9_${obj.serialNumber}" name="ext9" value="${obj.ext9 }"  class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							    <td class="am-padding-0"><input id="ext10_${obj.serialNumber}" name="ext10" value="${obj.ext10 }"  class="am-form-field app-inputPM0 app-inputContent" readonly></td>
						   	</tr>
						   	</c:if>
				  		</c:forEach>
				  	</c:if>
			    </tbody>
		  	</table>
	</div>	
</div>
<script type="text/javascript">
window.onload=setRedColor();
function setRedColor(){
	var detailTrDates=document.getElementById("dischargeDetail").getElementsByTagName("tr");
	var dischargeRate=$("#dischargeRateId").text().trim();
	var singletonVoltage=$("#singletonVoltageId").text().trim();
	for(var i=0;i<detailTrDates.length;i++){
		   var detailTds=detailTrDates[i].cells;
		   var serialNumber=detailTds[0].getElementsByTagName("INPUT")[0].value;
		   var barCode=detailTds[1].getElementsByTagName("INPUT")[0].value;
		  
		   if(serialNumber=="序号"){
			   serialNumber=0;
		   }else if(serialNumber==""){
			   serialNumber=999;
		   } else{
			   serialNumber=serialNumber-0;
			   if(barCode!="单体编号" && barCode !="总电压"){
				  if(singletonVoltage=="12V"){
					   for(var k=3;k<13;k++){
							  ext=detailTds[k].getElementsByTagName("INPUT")[0].value;
							  var tds=document.getElementById('dischargeDetail').rows[0].cells;
							  if(ext != null && ext != ''){
									  if(ext.indexOf("-")>=0){
										  $("#barCode_"+serialNumber).css("color","red");
										  var m=k-2;
										  $("#ext"+m+"_"+serialNumber).css("color","red");
									  }
							  }
						  }
				   }else if(singletonVoltage=="2V"){
				   if(dischargeRate == 3){
						 for(var k=3;k<13;k++){
							  ext=detailTds[k].getElementsByTagName("INPUT")[0].value;
							  var tds=document.getElementById('dischargeDetail').rows[0].cells;
							  if(ext != null && ext != ''){
									  if(ext.indexOf("-")>=0){
										  $("#barCode_"+serialNumber).css("color","red");
										  var m=k-2;
										  $("#ext"+m+"_"+serialNumber).css("color","red");
									  }
							  }
						  }
					   }else if(dischargeRate == '自定义'){
							 for(var k=3;k<13;k++){
								  ext=detailTds[k].getElementsByTagName("INPUT")[0].value;
								  var tds=document.getElementById('dischargeDetail').rows[0].cells;
								  if(ext != null && ext != ''){
										  if(ext.indexOf("-")>=0){
											  $("#barCode_"+serialNumber).css("color","red");
											  var m=k-2;
											  $("#ext"+m+"_"+serialNumber).css("color","red");
										  }
								  }
							  }
						}else if(dischargeRate == 5){
					 	for(var k=3;k<13;k++){
						  ext=detailTds[k].getElementsByTagName("INPUT")[0].value;
						  var tds=document.getElementById('dischargeDetail').rows[0].cells;
						  if(ext != null && ext != ''){
							  curtd=tds[k].getElementsByTagName("INPUT")[0].value;
							   if(curtd.indexOf("H")>0){
									  var single=curtd.split("H");
	 							  if(single[1] != ""){
	 								  curtd=single[0]-0+single[1].split("m")[0]/60;
	 							  }else{
	 								  curtd=single[0];
	 							  } 
								  }else{
									  var single=curtd.split("m");
									  curtd=single[0]/60;
								  }
							  for(var j=3;j<tds.length;j++){
								  td=tds[j].getElementsByTagName("INPUT")[0].value;
								  if(td.indexOf("H")>0){
									  var singles=td.split("H");
	 							  if(singles[1] != ""){
	 								  td=singles[0]-0+singles[1].split("m")[0]/60;
	 							  }else{
	 								  td=singles[0];
	 							  } 
								  }else{
									  var singles=td.split("m");
									  td=singles[0]/60;
								  }
								  if(ext.indexOf("-")<0){
									  if(curtd<3 && td<3 && ext<1.80){
										  //alert("单体编号为:"+barCode+"是落后单体");
										  //$("input[name="+barCode+"]").css("color","red");
										  $("#barCode_"+serialNumber).css("color","red");
										  var m=k-2;
										  $("#ext"+m+"_"+serialNumber).css("color","red");
									  }
								  }else{
									  $("#barCode_"+serialNumber).css("color","red");
									  var m=k-2;
									  $("#ext"+m+"_"+serialNumber).css("color","red");
								  }
								  
							  } 
						  }
					  }
				   }else if(dischargeRate == 10){
					   for(var k=3;k<13;k++){
						   ext=detailTds[k].getElementsByTagName("INPUT")[0].value;
						   var tds=document.getElementById('dischargeDetail').rows[0].cells;
						   if(ext != null && ext != ''){
							   curtd=tds[k].getElementsByTagName("INPUT")[0].value;
							   if(curtd.indexOf("H")>0){
									  var single=curtd.split("H");
	 							  if(single[1] != ""){
	 								  curtd=single[0]-0+single[1].split("m")[0]/60;
	 							  }else{
	 								  curtd=single[0];
	 							  } 
								  }else{
									  var single=curtd.split("m");
									  curtd=single[0]/60;
								  }
							   for(var j=3;j<tds.length;j++){
	 							 td=tds[j].getElementsByTagName("INPUT")[0].value;
	 							  if(td.indexOf("H")>0){
	 								  var singles=td.split("H");
		    							  if(singles[1] != ""){
		    								  td=singles[0]-0+singles[1].split("m")[0]/60;
		    							  }else{
		    								  td=singles[0];
		    							  } 
	 							  }else{
	 								  var singles=td.split("m");
	 								  td=singles[0]/60;
	 							  }
	 							  if(ext.indexOf("-")){
	 								 if(curtd<6 && td<6 && ext<1.80){
		 								 //$("input[name="+barCode+"]").css("color","red");
		 								 $("#barCode_"+serialNumber).css("color","red");
										  var m=k-2;
										  $("#ext"+m+"_"+serialNumber).css("color","red");
		 							  }
	 							  }else{
	 								 $("#barCode_"+serialNumber).css("color","red");
									  var m=k-2;
									  $("#ext"+m+"_"+serialNumber).css("color","red");
	 							  }
	 							  
	 						  }
						   }
							  
					   }
				   }
			   }
			 }
		   }
	}
}
function backList(){
	var selectId=$("#selectId").val();
	var code=$("#code").val();
	if(selectId=='2'){
		window.location.href="${ctx}/hldischarge/goStart?selectId="+selectId;
	}else if(selectId=='3'){
		window.location.href="${ctx}/hldischarge/selectMsgByCode?code="+code;
	}
}
</script>
</body>
</html>
