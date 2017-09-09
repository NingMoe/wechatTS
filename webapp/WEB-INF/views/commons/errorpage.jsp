<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="meta.jsp"%>
<%@ include file="tags.jsp"%>
<html>
<head>
<script type="text/javascript">
	var url = '${requestUrl}';
	window.onload = function() {
		setTimeout("toStart();", 1000);
	}
	function toStart() {
		if(url != '' && url != 'null' && url != null) {
			window.location.href = url;
		}
		//window.location.href = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx4607dd75af142227&redirect_uri=http://wanghuan0920.xicp.net/wechat/main/toStart.html&response_type=code&scope=snsapi_base&state=1";
	}
</script>
<title>页面发生错误</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
</head>
<body>
	<div style="text-align:center;padding:10px;width:300px;margin:0 auto;">
		<p style="font-size:15px;margin:10px 0;color:black;">页面发生错误，即将重新登录，请稍后</p>
	</div>
</body>
</html>