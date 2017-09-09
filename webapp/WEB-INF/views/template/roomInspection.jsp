<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<script type="text/javascript">
	function toAddRoom() {
		showLoading();
		window.location.href="${ctx}/quarterInspection/toAddRoom";
	}
	
	function toRoomReportException() {
		showLoading();
		window.location.href="${ctx}/quarterInspection/toRoomReportException";
	}
	
</script>
<div class="am-cf">
<c:if test="${roomBO == null || roomBO == '[]'}">
	<table class="am-table app-tb">
		<tbody>
			<tr>
				<th style="vertical-align: middle">
					机房
				</th>
				<th>
					<div style="float: right;">
						<span class="am-badge am-text-middle am-badge-success am-radius" style="padding-top:8px;width:60px;height: 30px;" onclick="toAddRoom();">添加</span>
					</div>
				</th>
			</tr>
		</tbody>
	</table>
	
</c:if>
<c:if test="${roomBO != null}">
	<table class="am-table app-tb">
		<tbody>
			<tr>
				<th style="vertical-align: middle">
					机房
				</th>
				<th>
					<div style="float: right;">
						<span class="am-badge am-text-middle am-badge-success am-radius" style="padding-top:8px;width:60px;height: 30px;" onclick="toAddRoom();">修改</span>
						<c:if test="${roomStatusBO.inStatus ne false}">
							<span class="am-badge am-text-middle am-badge-danger am-radius" style="padding-top:8px;width:60px;height: 30px;" onclick="toRoomReportException();">上报异常</span>
						</c:if>
					</div>
				</th>
			</tr>
			<tr>
				<td style="vertical-align: middle; width: 120px;">
					机房结构：
				</td>
				<td>
					${roomBO.roomStructure }
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">
					机房尺寸：
				</td>
				<td>
					${roomBO.roomeSize }
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">
					机房面积：
				</td>
				<td>
					${roomBO.area }平米
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">
					所在楼层：
				</td>
				<td>
					${roomBO.floor }
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">
					灭火器数量：
				</td>
				<td>
					${roomBO.extinguishersNum }个
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">
					灭火器质保日期：
				</td>
				<td style="vertical-align: middle">
					<fmt:formatDate value="${roomBO.extinguishersWarranty }" pattern="yyyy-MM-dd" />
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">
					机房外围：
				</td>
				<td style="vertical-align: middle">
					${roomBO.haveWalls}	
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
