<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

	<head>
	<title>问题反馈</title>
		<%@include file="../commons/head.jsp"%>
		<script type="text/javascript">
		function goBack(){
			window.history.go(-3);
			//window.location.href='${ctx}/station/getNearListPage?token=${s_user.token}&lo=${s_user.lo}&la=${s_user.la}&toPagePath=acceptItem/checkItem&addStationPath=station/gotoStation&f=f';
			//window.location.href='${ctx}/main/toStart/acceptItem_checkItem/station_gotoStation.html';
			
		}
		</script>
	</head>
	<body style="background-color: #ffffff">
		<header class="am-topbar am-topbar-fixed-top">
			<div class="am-topbar-brand  app-toolbar">问题反馈</div>
		</header>
		<div align="center">
			<div style="margin: 40px auto auto auto">
				<i class="am-icon-check-square am-success am-icon-lg"></i>
			</div>
			<div style="margin:33px auto auto auto">
				<h3>
					&nbsp提交成功！
				</h3>
			</div>

			<p>
				我们会认真处理您的反馈，尽快修复
				<br />
				和完善相关功能
			</p>
		</div>
		
		<div class="app-div-btn-tool">
				<button class="am-btn am-btn-success am-radius am-btn-block"
					onclick="goBack();">
					<strong>完成</strong>
				</button>
			</div>
			<div>
				&nbsp
			</div>
	</body>
</html>