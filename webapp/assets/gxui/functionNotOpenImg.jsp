<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String msg=request.getParameter("msg");
	if("1".equals(msg)){
		msg="凝聚知识   产生力量";
	}else if("2".equals(msg)){
		msg="快捷沟通   便捷运维";
	}else{
		msg="";
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>功能暂未开放～</title>
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<style type="text/css">
* {
	padding: 0;
	margin: 0;
}
body {
	background: #3664b2;
	font-family: '微软雅黑';
	color: #0ddd23;
	font-size: 20px;
}
img {
	border: 0;
	margin:0 auto;
}
#container{background:#3664b2; text-align:center;margin:0 auto;} 
</style>
</head>
<body>
<div id="container">
	<div style="text-align:center;width:320px;height:480px;margin:0 auto;">
		<div style="text-align:center;position:absolute;width:320px;height:480px;margin:0 auto;">
			<img src="../i/knowlage.png" width="320" height="480" />
			<span style="position:absolute; top:301px;left:0px;width:320px;"><%=msg%></span>
		</div>
	</div>
</div> 
<script type="text/javascript">
$(function(){
$('#container').height($(window).height());
$('#container').width($(window).width());
});
</script>
</body>
</html>