<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>登录超时</title>
<style type="text/css">
* {
	padding: 0;
	margin: 0;
}

html {
	overflow-y: scroll;
}

body {
	background: #fff;
	font-family: '微软雅黑';
	color: #333;
	font-size: 16px;
}

img {
	border: 0;
}

.error {
	padding: 24px 48px;
}

.face {
	font-size: 100px;
	font-weight: normal;
	line-height: 120px;
	margin-bottom: 12px;
}

h1 {
	font-size: 32px;
	line-height: 48px;
}

.error h1 a {
	color: #999 !important;
	text-decoration: none;
}

.error .content {
	padding-top: 10px
}

.error .info {
	margin-bottom: 12px;
}

.error .info .title {
	margin-bottom: 3px;
}

.error .info .title h3 {
	color: #000;
	font-weight: 700;
	font-size: 16px;
}

.error .info .text {
	line-height: 24px;
}

.copyright {
	padding: 12px 48px;
	color: #999;
}

.copyright a {
	color: #000 !important;
	text-decoration: none;
}
</style>
</head>

<body>
	<div class="error">
		<p class="face">:(</p>
		<h1>登录超时<br>或没有访问权限，<br>请重新登录<br>或联系我们。</h1>
		<div class="content"></div>
	</div>
	<div class="copyright">
		<p>技术支持： 第一象限</p>
	</div>


</body>
</html>