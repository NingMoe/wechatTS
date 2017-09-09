<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="meta.jsp"%>
<%@ include file="tags.jsp"%>
<html>
<head>
<title>第一象限</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0 , maximum-scale=1.0, user-scalable=0">
</head>
<body>
<script type="text/javascript">
	var url = '${requestUrl}';
	window.onload = function() {
		setTimeout("toStart();", 10);
	}
	function toStart() {
		if(url != '' && url != 'null' && url != null) {
			window.location.href = url;
		}
	}
</script>
</body>
</html>