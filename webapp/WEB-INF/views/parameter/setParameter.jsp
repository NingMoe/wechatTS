<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="../commons/tags.jsp" %>
<!DOCTYPE html>
<html>
<head>
<title>参数普调</title>
<%@include file="../commons/head.jsp" %>
<%@include file="../commons/mobiscrollDatePlugin.jsp" %> 

</head>
<body>
<header class="am-topbar am-topbar-fixed-top">
<div onclick="gohistory();">
	<span class="am-topbar-brand am-icon-angle-left"></span>
	<div class="am-topbar-brand  app-toolbar">参数普调</div>
</div>
</header>
<form id="fbean" class="am-form" modelAttribute="bParameter" method="post" action="${ctx}/parameter/getParameter" onSubmit="return validate_form(this)">
		<table class="am-table app-tb"> 
			<tbody>
				<tr>
					<th style="border-top: none;" colspan="2">
							参数信息
						</th>
				</tr>
				<tr>
					<td style="vertical-align: middle;white-space: nowrap;">
						基站名称：
					</td>
					<td >
						<input type="hidden" name="stationId" value="${stationId}">
						<input type="text" readonly name="stationName" value="${stationName}" class="am-form-field" placeholder="请输入基站名称">
					</td>
				</tr>
				<%-- <tr>
					<td style="vertical-align: middle;">
						基站编号：
					</td>
					<td>
						<input type="text" readonly name="stationNo" value="${stationNo}"  class="am-form-field" placeholder="请输入基站编号">
					</td>
				</tr> --%>
				<tr>
					<td style="vertical-align: middle;">
						开关电源品牌：
					</td>
					<td>
						<select name="powerBrand" data-am-selected>
						  <c:forEach items="${powerBrands}" var="obj" varStatus="status">
								<option value="${obj.id}">${obj.name}</option>
						  </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">
						开关电源型号：
					</td>
					<td>
						<select id="power_type" name="powerType" data-am-selected>
						  <option value="">请选择</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">
						蓄电池品牌：
					</td>
					<td>
						<select id="batteryBrand" name="batteryBrand" data-am-selected>
							<option value="">请选择</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">
						<span style="color: red">*</span>生产日期：
					</td>
					<td>
 						<input type="text" name="batterDate"  id="batterDate"  class="am-form-field"  placeholder="蓄电池生产日期"  readonly/>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">
						停电次数：
					</td>
					<td>
						<input type="text" name="monthAvgPoweroff" class="am-form-field" placeholder="平均每月停电次数" id="monthAvgPoweroff">
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">
						停电时长（小时）：
					</td>
					<td>
						<input type="text" name="onceAvgTime" class="am-form-field" placeholder="平均每次停电时长" id="onceAvgTime">
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">
						均充时间（小时）：
					</td>
					<td>
						<input type="text" name="equalizingChargeTimeInput" class="am-form-field" placeholder="均充时间" id="equalizingChargeTimeInput">
					</td>
				</tr>
			</tbody>
		</table>
		
		<div class="app-div-btn-tool">
	    	<button type="submit" onclick="getParameter();" class="am-btn am-btn-success am-radius am-btn-block"><strong>获取</strong></button>
		</div>
		<div style="height: 10px">&nbsp;</div>
</form>
<script type="text/javascript">
function validate_form(thisform){
	
	var batterDate = document.getElementById('batterDate').value.trim();
	var sbatterDate=batterDate.toString();
	
	var monthAvgPoweroff = document.getElementById('monthAvgPoweroff').value.trim();
	var smonthAvgPoweroff=monthAvgPoweroff.toString();
	
	var onceAvgTime = document.getElementById('onceAvgTime').value.trim();
	var sonceAvgTime=onceAvgTime.toString();
	
	var equalizingChargeTimeInput = document.getElementById('equalizingChargeTimeInput').value.trim();
	var sequalizingChargeTimeInput=equalizingChargeTimeInput.toString();
	
	
	if(sbatterDate == null || sbatterDate == '' || sbatterDate == 'null') {
		modalAlert('蓄电池生产日期不能为空', null);
		return false;
	}
	if(smonthAvgPoweroff != null && smonthAvgPoweroff != '' && null== smonthAvgPoweroff.match(/^(([1-9]\d{0,3})|0)$/)){
        modalAlert('停电次数请输入正确的数字', null);
        return false;
	}
	if(sonceAvgTime != null && sonceAvgTime != '' && null== sonceAvgTime.match(/^(([1-9]\d{0,3})|0)(\.\d{0,2})?$/)){
        modalAlert('停电时长请输入正确的数字', null);
        return false;
	}
	if(sequalizingChargeTimeInput != null && sequalizingChargeTimeInput != '' &&  null== sequalizingChargeTimeInput.match(/^(([1-9]\d{0,3})|0)(\.\d{0,2})?$/)){
        modalAlert('均充时间请输入正确的数字', null);
        return false;
	}
	return true;
}

if($("select[name='powerBrand']").val() != null && $("select[name='powerBrand']").val() != ""){
	$.getJSON("${ctx}/parameter/getType.json?brandId="+$("select[name='powerBrand']").val(), function(data){
		 var html = "";
		   $.each(data.typeList, function(i,item){
			   html += '<option value="'+ item.id +'">'+ item.id +'</option>';
		  }); 
		   $("#power_type").html(html);
		});
}
	$("select[name='powerBrand']").change(this,function(){
		 $.getJSON("${ctx}/parameter/getType.json?brandId="+this.value, function(data){
			 var html = "";
			   $.each(data.typeList, function(i,item){
				   html += '<option value="'+ item.id +'">'+ item.id +'</option>';
			  }); 
			   $("#power_type").html(html);
			});
	});
	
	$.getJSON("${ctx}/parameter/getBatteryBrand.json", function(data){
		var html = "";
		$.each(data.batteryList, function(i,item){
			html += '<option value="'+ item.id +'">'+ item.name +'</option>';
		}); 
		$("#batteryBrand").html(html);
	});
	
	$("#bParameter").submit(function(){
		
	});
	
	initMobiscroll("#batterDate");
</script>
</body>
</html>