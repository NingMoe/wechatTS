<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<META name=viewport content="initial-scale=1, user-scalable=no">
		<title>隐患排查</title>
		<%@include file="../commons/head.jsp"%>
		<script type="text/javascript">
			function finish(){
				if(typeof(WeixinJSBridge) != "undefined"){
					WeixinJSBridge.call('closeWindow');
				}else{
					window.close();
				}
			}
		</script>
	</head>
	<body style="background-color: #ffffff">
		<header class="am-topbar am-topbar-fixed-top">
			<div class="am-topbar-brand  app-toolbar">隐患排查</div>
		</header>
		<div align="center">
			<div style="margin: 40px auto auto auto">
				<i class="am-icon-check-square am-success am-icon-lg"></i>
			</div>
			<div style="margin:33px auto auto auto">
				<h3>
					&nbsp;提交成功！
				</h3>
			</div>

			<p>
				您此次隐患排查已完成，排查结果已发送至
				<br />
				${email }
				<br />
				请您查收！
			</p>
		</div>
		
		<div class="app-div-btn-tool">
				<button class="am-btn am-btn-success am-radius am-btn-block" onclick="finish()">
					<strong>完成</strong>
				</button>
			</div>
			<div>
				&nbsp
			</div>
	</body>
</html>