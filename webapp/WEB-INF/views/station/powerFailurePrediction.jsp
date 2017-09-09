<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:if test="${powerCutLines == '[]'}">
	<div class="am-cf" style="text-align: center;margin-top: 60px">
		暂无停电
	</div>
</c:if>
<c:if test="${powerCutLines != '[]'}">
<c:forEach items="${powerCutLines}" var="item" varStatus="status">
<table class="am-table app-tb">
	<tr>
		<td style="vertical-align: middle;">
			停电线路：
		</td>
		<td style="vertical-align: middle;">
			${item.linename}
			<input type="hidden" id="linename${status.index}" name="linename"  value="${item.linename}"/>
			<input type="hidden" id="lineno${status.index}" name="lineno"  value="${item.lineno}"/>
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle;">
			变电站名称：
		</td>
		<td style="vertical-align: middle;">
			${item.subsname}
		</td>
	</tr>
	<tr>
		<td style="vertical-align: middle;">
			是否在此线路：
		</td>
		<td style="vertical-align:middle;">
			<i class="am-fr"><input idpro="${status.index}" id="status${status.index}" data-am-switch name="status" type="checkbox" data-size="xs" checked  data-on-text="是" data-off-text="否" data-on-color="success" data-off-color="danger"/>&nbsp;&nbsp;&nbsp;</i>
		</td>
	</tr>
</table>
</c:forEach>
</c:if>
<script type="text/javascript">	
	var powerCutLinesCount = '${powerCutLinesCount}';
	!function(t) {
		if(powerCutLinesCount != null && powerCutLinesCount != '' && powerCutLinesCount != 'undefined') {
			for(var i = 0; i < powerCutLinesCount; i++) {
				$('#status' + i).on('switchChange.bootstrapSwitch', function(event, state) {
					/*switch的监听器*/
					if(state){
						$('#status' + $(this).attr("idpro")).val('on');
						savePowerFailurePrediction($(this).attr("idpro"));
			    	}else{
			    		$('#status' + $(this).attr("idpro")).val('off');
			    		savePowerFailurePrediction($(this).attr("idpro"));
			    	};
				});
			}
		}
	}(window.jQuery || window.Zepto);
	
	function savePowerFailurePrediction(index) {
		var status = $('#status' + index).val();
		var linename = $('#linename' + index).val();
		var lineno = $('#lineno' + index).val();
		$.ajax({
			type : "POST",
			async : false,
			url : "${ctx}/station/savePowerFailurePrediction.json",
			data : "stationId=" + stationId1 + "&status=" + status + "&linename=" + linename + "&lineno=" + lineno,
			success : function(data) {
				if(data.res == 'success') {
					alert('保存成功');
				} else {
					alert('保存失败');
				}
			}
		});
	}
</script>