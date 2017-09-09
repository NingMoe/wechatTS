<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<div class="am-cf">
<c:if test="${devicesList == null || devicesList == '[]'}">
	<div class="am-cf" style="text-align: center;margin-top: 60px">
		没有设备，请通过扫码添加设备
	</div>
</c:if>
<c:if test="${devicesList != null}">
	<table class="am-table am-table-hover">
		<tbody>
			<c:forEach items="${devicesList}" var="obj" varStatus="status">
				<tr onclick="viewDeviceDetail('${obj.deviceId}',false);" style="background: #fff">
					<td style="border-top: none;border-bottom:1px #ddd solid;width: 60px;height:55px;text-align: right;">
						<c:if test="${!empty obj.photoList}">
							<img class="am-radius" style="border:1px #ddd solid"  <c:if test="${obj.photoList[0].status eq '待上传'}"> data-localId="${obj.photoList[0].localId}" </c:if> data-echo="${obj.photoList[0].thumbLocation}" width="60" height="55">
						</c:if>
						<c:if test="${empty obj.photoList}">
							<img class="am-radius" style="border:1px #ddd solid" src="${ctx}/assets/i/noPhoto.png" width="60" height="55">
						</c:if>
					</td>
					<td style="border-top: none;border-bottom:1px #ddd solid;vertical-align: middle;">
						${obj.deviceTypeName}
						<c:if test="${obj.outLose eq '-1'}">(需关联条码)</c:if>
					</td>
					<td style="width: 80px;vertical-align: middle;border-top: none;border-bottom:1px #ddd solid;vertical-align: middle;">
						<c:if test="${obj.inStatus eq 'true'}">
							<span class="am-badge am-text-middle am-badge-success am-radius" style="padding-top:8px;width:60px;height: 30px">正&nbsp;&nbsp;&nbsp;常</span>
						</c:if>
						<c:if test="${obj.inStatus eq 'false'}">
							<span class="am-badge am-text-middle am-badge-danger am-radius" style="padding-top:8px;width:60px;height: 30px">异&nbsp;&nbsp;&nbsp;常</span>
						</c:if>
						<c:if test="${obj.inStatus eq null}">
							<span class="am-badge am-text-middle am-radius" style="padding-top:8px;width:60px;height: 30px">未检测</span>
						</c:if>
					</td>
				</tr>
			</c:forEach>
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
