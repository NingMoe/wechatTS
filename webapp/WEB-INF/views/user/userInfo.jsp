<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
	<title>个人信息</title>
		<%@include file="../commons/head.jsp"%>
	</head>
	<body style="background-color: #ffffff">
		<header class="am-topbar am-topbar-fixed-top">
			<span class="am-topbar-brand am-icon-angle-left" onclick="weChatCloseThisPage();"></span>
			<div class="am-topbar-brand  app-toolbar" onclick="weChatCloseThisPage();">个人信息</div>
		</header>
		<div class="am-cf admin-main">
			<table class="am-table am-form">

				<tbody>
					<tr>
						<td style="border:none">
							&nbsp;&nbsp;&nbsp;真实姓名:
						</td>
						<td style="border:none">
							${userInfo.userName}
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;&nbsp;&nbsp;手机号码:
						</td>
						<td>
							${userInfo.phoneNo}
						</td>
					</tr>

					<tr>
						<td>
							&nbsp;&nbsp;&nbsp;所在省:
						</td>
						<td>
							${userInfo.provinceId}
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;&nbsp;&nbsp;所在市:
						</td>
						<td>
							${userInfo.cityId}
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;&nbsp;&nbsp;所在县/区:
						</td>
						<td>
							${userInfo.countyId}
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;&nbsp;&nbsp;职位:
						</td>
						<td>
							${userInfo.roleName}
						</td>
					</tr>
					<tr>
						<td style="white-space: nowrap;border-bottom:1px solid #ddd;">
							&nbsp;&nbsp;&nbsp;所属公司:
						</td>
						<td style="border-bottom:1px solid #ddd;">
							${userInfo.deptId}
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript">
			///微信配置及相关js
			wx.config(${jsConfig});
			wx.ready(function () {
				//saomiao();
			});
			
			wx.error(function (res) {
				modalAlert("网络出现问题，请稍后重试",null);
			});
			
			function weChatCloseThisPage(){
				/*关闭当前网页*/
				wx.closeWindow();
			}
		</script>
	</body>
</html>