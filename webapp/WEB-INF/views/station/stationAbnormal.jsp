<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div style="height: 10px">&nbsp;</div>
<div class="amz-toolbar" id="amz-toolbar" style="filter:alpha(opacity:80);opacity:0.86;width:48px;height:58px;">
	<a href="javascript:navi()" title="异常上报" class="am-icon-faq am-icon-btn" style="font-size:16px;font-weight: bold;">
		<span style="display:block;height:24px;line-height: 200%;vertical-align: text-bottom;">异常</span>
		<span style="display:block;height:24px;line-height: 100%;">上报</span>
	</a>
</div>

<script type="text/javascript">
function navi(){
	var url = url1;
	var stationId = stationId1;
	window.location.href = "<%=request.getContextPath()%>/station/getStationAbnormal?stationId="+stationId+"&url="+url;
}
</script>