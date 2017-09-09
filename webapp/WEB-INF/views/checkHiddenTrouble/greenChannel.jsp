<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>绿色通道</title>
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
.testborder{
	border:1px solid red;
}
</style>
<script type="text/javascript">
function prevStep(){
	var deviceCheckNoBattery = $("#deviceCheckNoBattery").val();
	if(deviceCheckNoBattery == 'deviceCheckNoBattery') {
		window.location.href="${ctx}/checkHiddenTrouble/goDeviceCheckNoBattery";
	} else {
		gohistory();
	}
}
	

function checkPhone(value){   
	if(/^1\d{10}$/g.test(value)){
		return true;
	} else {
		return false;
	}
}

function save(){
	 if(!checkPhone($("#phone").val())){
		 modalAlert('请输入正确的手机号码。', null);
		 return;
	 }
	$("#easyAccessForm").submit();
	/* $.ajax({
         type: "POST",
         dataType: "json",
         url: "${ctx}/checkHiddenTrouble/saveOrUpdateasyAccess.json",
         data: $('#easyAccessForm').serialize(),
         success: function (json) {
        	 if(json.succ){
        		 window.location.href = "${ctx}/checkHiddenTrouble/toFinish?email="+$("#email").val();
        		 //modalAlert("保存成功！");
        	 }
         },
         error: function(data) {
        	 modalAlert("服务器繁忙");
         }
     }); */
}
</script>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div>
			<div class="am-topbar-brand app-toolbar"> 绿色通道 </div>
		</div>
	</header>
<form class="am-form" id="easyAccessForm" action="${ctx}/checkHiddenTrouble/saveOrUpdateasyAccess" method="post" data-am-validator>
	<input type="hidden" name="recordId" value="${yhea.recordId}"/>
	<input type="hidden" id="deviceCheckNoBattery" name="deviceCheckNoBattery" value="${deviceCheckNoBattery}"/>
	<table class="am-table app-tb">
  		<tr>
			<td style="border-top: none;" colspan="2"> <strong>亲，感谢使用，我们将在24小时内把排查报表发送给您，登录邮箱即可查询。</strong> </td>
		</tr>
		<tr>
			<td style="vertical-align: middle; width: 99px">
				姓名：
			</td>
			<td>
				<input name="userName" value="${yhea.userName }" type="text" class="am-form-field" placeholder="姓名" required>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle; width: 99px">
				电话：
			</td>
			<td>
				<input id="phone" name="phone" value="${yhea.phone }" type="number"  class="am-form-field" placeholder="电话" required />
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle; width: 99px">
				邮箱：
			</td>
			<td>
				
				<input id="email" name="email" value="${yhea.email }" type="email"  placeholder="输入邮箱" required />
			</td>
		</tr>
		<tr>
			<td style="border-top: none;" colspan="2"> <strong>请长按下面二维码，关注“第一象限运维”公众号，可直接在web平台查看排查报表，更有众多实用功能等你来。</strong> </td>
		</tr>
		<tr>
			<td style="border-top: none;" colspan="2" align="center"> <img alt="" src="${ctx }/assets/i/code.png"> </td>
		</tr>
  	</table>
  	
	<div class="app-div-btn-tool" style="margin-top: 18px">
		<div class="am-g">
			<div class="am-u-sm-6">
				<button  onclick="prevStep();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
					<i class="am-icon-arrow-left"></i>&nbsp;<strong>上一步</strong>
				</button>
			</div>
			<div class="am-u-sm-6">
				<button  onclick="save();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
					<i class="am-icon-check"></i>&nbsp;<strong>完成</strong>
				</button>
			</div>
		</div>
	</div>
	<div>&nbsp;</div>
</form>
<div class="am-modal am-modal-alert" tabindex="-1" id="modal-alert">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">提示</div>
    <div class="am-modal-bd" id="modal-alert-msg">
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
    </div>
  </div>
</div>
</body>
</html>