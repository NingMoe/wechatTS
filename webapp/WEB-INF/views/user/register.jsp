<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
	<title>用户注册</title>
		<%@include file="../commons/head.jsp"%>
		<style type="text/css">
			input::-webkit-outer-spin-button,
			input::-webkit-inner-spin-button {
			    -webkit-appearance: none !important;
			    margin: 0;
			}
			.app-btn-active{
				background-color: #ddd;
			}
		</style>
		<script type="text/javascript">
		
	</script>


	</head>

	<body style="background-color: #ffffff">
				<header class="am-topbar am-topbar-fixed-top">
			<span class="am-topbar-brand am-icon-angle-left" onclick="gohistory();"></span>
			<div class="am-topbar-brand  app-toolbar">用户注册</div>
		</header>
		<div class="am-cf admin-main">
			<h2 align="center">
				<font color="red">${Msg}</font>
			</h2>
			<form class="am-form" id="addUser" action="${ctx}/register/register?openId=${openId}&ForwardJsp=${ForwardJsp}" method="post" data-am-validator>
				<input name="toPageUrl" id="toPageUrl" value="${toPageUrl}" type="hidden">
				<table class="am-table">
					<tbody>
						<tr>
							<td style="vertical-align: middle;">
								真实姓名：<span style="color: red">*</span>
							</td>
							<td style="vertical-align: middle;">
								<input name="userName" id="userName" type="text"
									class="am-form-field" placeholder="请输入姓名" required>
								<span id='checkFalse' style="display: none; color: red;"><i
									class='am-icon-close'></i>
								</span><span id='nameTips'></span>
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;">
								手机号码：<span style="color: red">*</span>
							</td>
							<td style="vertical-align: middle;">
								<input name="phoneNumber" id="phoneNumber" type="number"
									class="am-form-field" placeholder="手机号码" required>
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;">
								所在省：
							</td>
							<td style="vertical-align: middle;">
								<select data-am-selected="{maxHeight:200}" id="province" name="province">
									<option value="">请选择</option>
								</select>

							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;">
								所在市：
							</td>
							<td style="vertical-align: middle;">
								<select data-am-selected="{maxHeight:200}" id="city" name="city">
									<option  value="">请选择</option>
								</select>
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;">
								所在县/区：
							</td>
							<td style="vertical-align: middle;">
								<select data-am-selected="{maxHeight:200,dropUp: 1}" id="county" name="county">
									<option selected value="">请选择</option>
								</select>
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;">
								职位：
							</td>
							<td style="vertical-align: middle;">
								<select data-am-selected name="roleName" id="roleName" >
		   							<option value="铁塔公司管理人员">铁塔公司管理人员</option>
		   							<option value="代维公司管理人员">代维公司管理人员</option>
		   							<option value="代维人员">代维人员</option>
		   							<option value="其他人员">其他人员</option>
		    					</select>
							</td>
						</tr>
						<tr>
							<td style="vertical-align: middle;">
								所属公司：
							</td>
							<td style="vertical-align: middle;">
								<select data-am-selected="{maxHeight:200,dropUp: 1}" id="deptId" name="deptId">
									<option  value="集团公司">集团公司</option>
									<option  value="省公司">省公司</option>
									<option  selected value="市公司">市公司</option>
								</select>
							</td>
						</tr>
					</tbody>
				</table>
					<div class="app-div-btn-tool">
		<button disabled="disabled" id="submit" class="am-btn am-btn-success am-radius am-btn-block" onclick="submitForm();">
			<strong>注册</strong>
		</button>
	</div>
	
			</form>
		</div>
		<div>&nbsp</div>
	</body>
<script type="text/javascript">
$('input[name=phoneNumber]').change(function(){
   	var phoneNumber = document.getElementById('phoneNumber').value.trim();
    if(phoneNumber != null && phoneNumber != '' && phoneNumber != 'null'){
	   	 var flag = checkPhone(phoneNumber);
	   	 if(flag) {
	   		 $('#submit').attr('disabled',false);
	   	 } else {
	   		 $('#submit').attr('disabled','disabled');
	   	 }
    }else{
     	 $('#submit').attr('disabled','disabled');
    }
});

function checkPhone(value){   
	//if(/^13\d{9}$/g.test(value)||(/^15[0-35-9]\d{8}$/g.test(value))||(/^18[05-9]\d{8}$/g.test(value))){
	if(/^1\d{10}$/g.test(value)){
		$('#submit').attr('disabled','disabled');
		return true;
	} else {
		$('#submit').attr('disabled',false);
  			modalAlert('请输入正确的手机号码。', null);
  			return false;
  		}
}
	function validateUserName() {
		var val = false;
		var userName = $("#userName").val().trim();
		if(userName != '' && userName != 'null' && userName != null) {
			val = true;
		}
		return val;
	}
   function submitForm() {
  		var flag = validateUserName();
    	if(flag) {
    		showLoading("正在加载...");
    		$('#addUser').submit(function(){
	    	});
    	}
   }
!function(t) {
	$.getJSON("${ctx}/common/getProvince.json?parentId=", function(data){
		 var html = "";
		   $.each(data.orgs, function(i,item){
			   html += '<option parentId="'+item+'" value="'+ item +'">'+ item +'</option>';
		  }); 
		   $("#province").html(html);
	});  
}(window.jQuery || window.Zepto);


$("#province").change(this,function(){
	showLoading();
	$.ajax({type:"POST",dataType:"json",url:"${ctx}/common/getCity.json",
		data:{
			"province":$(this).val()
		},success:function(data){
			var html = "";
			$.each(data.orgs, function(i,item){
			  html += '<option parentId="'+item+'" value="'+ item +'">'+ item +'</option>';
			}); 
			$("#city").html(html);
		},complete:function(){
			closeLoading();
		}
	});
}); 

 
$("#city").change(this,function(){
	showLoading();
	$.ajax({type:"POST",dataType:"json",url:"${ctx}/common/getCountry.json",
		data:{
			"province":$("#province").val(),
			"city":$(this).val()
		},success:function(data){
			var html = "";
			$.each(data.orgs, function(i,item){
			 html += '<option parentId="'+item.id+'" value="'+ item.name +'">'+ item.name +'</option>';
			});
			$("#county").html(html);
		},complete:function(){
			closeLoading();
		}
	});
/* 
	  $.getJSON("${ctx}/common/getCountry.json?province="+province+"&city="+city, function(data){
		 var html = "";
		 $.each(data.orgs, function(i,item){
			 html += '<option parentId="'+item.id+'" value="'+ item.name +'">'+ item.name +'</option>';
		 }); 
		   $("#county").html(html);
		});  */
	  
	  //初始哈职位select
	  $("#roleName").val("铁塔公司管理人员").attr("selected", "selected"); 
	  
	  //根据省市获取代维公司
	 /* 
	  $("#deptId").html('<option selected value="">请选择</option>');
	  var provinceId = $("#province").find('option:selected').attr("value");
	  if(provinceId != null && provinceId != '' && provinceId != 'null' && provinceId !='undefined') {
		 provinceId = encodeURI(encodeURI(provinceId));
		 $.getJSON("${ctx}/common/getAgentCompany.json?provinceId="+provinceId+"&cityId="+encodeURI(encodeURI($(this).find('option:selected').attr("value"))), function(data){
		 var html1 = "";
		 $.each(data.agentCompanyList, function(i,item){
			  html1 += '<option deptId="'+item.id+'" value="'+ item.companyName +'">'+ item.companyName +'</option>';
		 }); 
		   $("#deptId").html(html1);
		});
	  } */
   
}); 

$("#roleName").change(this,function(){
	var roleName = $("#roleName").find('option:selected').attr("value");
	
	if(roleName != null && roleName != '' && roleName != 'null' && roleName !='undefined'){
		$("#deptId").html('<option selected value="">请选择</option>');
		if(roleName == "代维公司管理人员" || roleName == "代维人员"){
			var provinceId = $("#province").find('option:selected').attr("value");
			  if(provinceId != null && provinceId != '' && provinceId != 'null' && provinceId !='undefined') {
				 provinceId = encodeURI(encodeURI(provinceId));
				 $.getJSON("${ctx}/common/getAgentCompany.json?provinceId="+provinceId+"&cityId="+encodeURI(encodeURI($("#city").find('option:selected').attr("value"))), function(data){
					 var html1 = "";
					 $.each(data.agentCompanyList, function(i,item){
						  html1 += '<option deptId="'+item.id+'" value="'+ item.companyName +'">'+ item.companyName +'</option>';
					 }); 
					 html1 += '<option  value="无">无</option>';
					 $("#deptId").html(html1);
				});
			  } 
		}else if(roleName == "铁塔公司管理人员"){
			 var html1 = "";
			 html1 += '<option  value="集团公司">集团公司</option>';
			 html1 += '<option  value="省公司">省公司</option>';
			 html1 += '<option selected value="市公司">市公司</option>';
			 $("#deptId").html(html1);
		}else{
			$("#deptId").html('<option  value="其他">其他</option>');
		}
	}
});

</script>
</html>