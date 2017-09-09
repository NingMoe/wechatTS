<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<script type="text/javascript">
	function toAddTower() {
		showLoading();
		window.location.href="${ctx}/quarterInspection/toAddTower";
	}
	
	function toReportException() {
		showLoading();
		window.location.href="${ctx}/quarterInspection/toReportException";
	}
	
</script>
<div class="am-cf">
<c:if test="${towerBO == null || towerBO == '[]'}">
	<table class="am-table app-tb">
		<tbody>
			<tr>
				<th style="vertical-align: middle">
					铁塔
				</th>
				<th>
					<div style="float: right;">
						<span class="am-badge am-text-middle am-badge-success am-radius" style="padding-top:8px;width:60px;height: 30px;" onclick="toAddTower();">添加</span>
					</div>
				</th>
			</tr>
		</tbody>
	</table>
	
</c:if>
<c:if test="${towerBO != null}">
	<table class="am-table app-tb">
		<tbody>
			<tr>
				<th style="vertical-align: middle">
					铁塔
				</th>
				<th>
					<div style="float: right;">
						<span class="am-badge am-text-middle am-badge-success am-radius" style="padding-top:8px;width:60px;height: 30px;" onclick="toAddTower();">修改</span>
						<c:if test="${towerStatusBO.inStatus ne false}">
							<span class="am-badge am-text-middle am-badge-danger am-radius" style="padding-top:8px;width:60px;height: 30px;" onclick="toReportException();">上报异常</span>
						</c:if>
					</div>
				</th>
			</tr>
			<tr>
				<td style="vertical-align: middle; width: 120px;">
					平台数量：
				</td>
				<td>
					${towerBO.platformNum }个
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">
					平台平均间距：
				</td>
				<td>
					${towerBO.platformSpacing }米
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">
					抱杆数量：
				</td>
				<td>
					${towerBO.derrickNum }个
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">
					抱杆占用情况：
				</td>
				<td>
					${towerBO.derrickUse }个
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">
					塔高：
				</td>
				<td>
					${towerBO.towerHeight }米
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle" rowspan="2">
					铁塔类型：
				</td>
				<td style="vertical-align: middle">
					${towerBO.towerType }/${towerBO.towerTypeDetail }
						
				</td>
			</tr>
		</tbody>
	</table>
</c:if>
</div>
<script>
	echo.init({
		offset : 100,
		throttle : 250,
		unload : false
	});
	//showImagesPhotoListH5();
</script>
