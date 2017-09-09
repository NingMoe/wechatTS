<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="../commons/tags.jsp" %>
	<div class="am-g">
		<table class="am-table am-table-hover"> 
			<tbody>
				<tr>
					<td style="border-top: none;">异常照片：</td>
					<td style="border-top: none;" onclick="">
						<img src="ss"/>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;border-top: none;" colspan="2">
						<font color="#8B8878">要求：设备异常特征或告警灯清晰可见</font>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;" colspan="2">
						<textarea id="remark" rows="3" cols="" class="am-form-field" placeholder="备注" required ></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<button type="button" onclick="goScanDevice();" class="am-btn am-btn-success am-radius am-btn-block"><strong>上报</strong></button>
	</div>
<script type="text/javascript">
	function goScanDevice() {
		window.location.href='${ctx}/discharge/getDeviceList.html';
	}
</script>