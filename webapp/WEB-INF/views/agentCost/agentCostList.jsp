<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
		<input type="hidden" name="selectId" value="2" id="selectId">
	<div class="am-cf">
		<table class="am-table am-text-left" style="padding:0px;margin: 0px;border-collapse:separate; border-spacing:0px 10px;">
				<c:forEach items="${costList}" var="obj" varStatus="status" >
					<tr style="margin-bottom: 20px;background: #fff;border-top: none;border-bottom:1px solid rgb(221, 221, 221);" onclick="gotoDetail('${obj}');" id="td_${obj }" >
						<td style="vertical-align: middle;">
							<div class="am-text-truncate am-text-lg" >${obj}&nbsp;&nbsp;代维费用明细</div>
						</td>
					</tr>
				</c:forEach>
		</table>
	</div>

<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool am-padding-bottom-0" style="padding-top: 6px">
	<a href="javascript:flashCostList();" class="am-btn am-btn-success am-radius am-btn-block" style="color: #fff;"> <strong>刷新</strong> </a>
</div>
<!-- 		<div class="am-u-sm-6">
			<button  onclick="flashCostDetail();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
				<strong>刷新</strong>
			</button>
		</div> -->

<script type="text/javascript">
function gotoDetail(fillMonth){
	$("#td_"+fillMonth).css('background-color','#C9C9C9');
	window.location.href="${ctx}/maintenancecost/getCostDetail?fillMonth="+fillMonth;
}
function flashCostList(){
	window.location.href="${ctx}/maintenancecost/goCost?selectId=2"
}
</script>