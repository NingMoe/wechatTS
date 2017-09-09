<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>上站记录</title>
<%@include file="../commons/head.jsp" %>
<script type="text/javascript">
	var currentPage = 2; var state = 0;
	var currentPage1 = 2;var state1 = 0;
	var inspectionStationsum = '${inspectionStationsum}';
	var inspectionStationExceptionsum = '${inspectionStationExceptionsum}';
	//查询上站站数列表（ajax请求）
	function getInspectionStations() {
		$.ajax({
			type : "POST",
			async : false,
			url : "${ctx}/recordStation/recordStations.json",
			data : "pageCurrent=" + currentPage,
			success : function(data) {
				currentPage = currentPage + 1;
				if (data.inspectionStations.length == 0) {
					$("#loadMore").hide();
					state = 1;
					return;
				}
				if (data.inspectionStations) {
					if(data.inspectionStations.length < 5 || inspectionStationsum == (5 * currentPage)) {
						$("#loadMore").hide();
						state = 1;
					} else {
						$("#loadMore").show();
					}
					appinspectionStation(data.inspectionStations);
				}
			}
		});
	}
	
	//查询异常站数列表（ajax请求）
	function getInspectionStationExceptions(){
		$.ajax({
			type : "POST",
			async : false,
			url : "${ctx}/recordStation/recordStations.json",
			data : "pageCurrent=" + currentPage1,
			success : function(data) {
				if (data.inspectionStationExceptions.length == 0) {
					$("#exloadMore").hide();
					state1 = 1;
					return;
				}
				if (data.inspectionStationExceptions) {
					if(data.inspectionStationExceptions.length < 5 || inspectionStationExceptionsum == (5 * currentPage)) {
						$("#exloadMore").hide();
						state1 = 1;
					} else {
						$("#exloadMore").show();
					}
					appexinspectionStation(data.inspectionStationExceptions);
				}
				closeLoading();  
			}
		});
	}
	
	function appinspectionStation(list) {
		for(var i = 0; i < list.length; i++) {
			var stationName = list[i].stationName;
			var status = list[i].status;
			var createDate = list[i].createDate;
			var finishTime = list[i].finishTime;
			$("#inspectionStation").append(
				"<table class='am-table app-tb'>"+
				"<tr><td style='vertical-align: middle;width:100px;'>基站名称：</td>"+
				"<td style='vertical-align: middle;'>" + stationName + "</td></tr>"+
				"<tr><td style='vertical-align: middle;width:100px;'>作业状态：</td>"+
				"<td style='vertical-align: middle;'>" + status + "</td></tr>"+
				"<tr><td style='vertical-align: middle;width:100px;'>开始时间：</td>"+
				"<td style='vertical-align: middle;'>" + FormatDate(createDate) + "</td></tr>"+
				"<tr><td style='vertical-align: middle;width:100px;'>结束时间：</td>"+
				"<td style='vertical-align: middle;'>" + FormatDate(finishTime) + "</td></tr>"+
				"</table>"
			)
		} 
	}
	
	function appexinspectionStation(list) {
		for(var i = 0; i < list.length; i++) {
			var stationName = list[i].stationName;
			var status = list[i].status;
			var createDate = list[i].createDate;
			var finishTime = list[i].finishTime;
			var deviceName = list[i].deviceName;
			var abnormalCode = list[i].abnormalCode;
			$("#inspectionStationException").append(
				"<table class='am-table app-tb'>"+
				"<tr><td style='vertical-align: middle;width:100px;'>基站名称：</td>"+
				"<td style='vertical-align: middle;'>" + stationName + "</td></tr>"+
				"<tr><td style='vertical-align: middle;width:100px;'>作业状态：</td>"+
				"<td style='vertical-align: middle;'>" + status + "</td></tr>"+
				"<tr><td style='vertical-align: middle;width:100px;'>开始时间：</td>"+
				"<td style='vertical-align: middle;'>" + FormatDate(createDate) + "</td></tr>"+
				"<tr><td style='vertical-align: middle;width:100px;'>结束时间：</td>"+
				"<td style='vertical-align: middle;'>" + FormatDate(finishTime) + "</td></tr>"+
				"<tr><td style='vertical-align: middle;width:100px;'>异常信息：</td>"+
				"<td style='vertical-align: middle;'>" + deviceName + "</td></tr>"+
				"</table>"
			)
		} 
	}
	
	function FormatDate(dateStr) {
		if(dateStr==null || dateStr == ""){
			return "";
		}	
		var date = new Date(dateStr);
        var datetime = date.getFullYear()
            + "/"// "年"
            + ((date.getMonth() + 1) >= 10 ? (date.getMonth() + 1) : "0"
                    + (date.getMonth() + 1))
            + "/"// "月"
            + (date.getDate() < 10 ? "0" + date.getDate() : date
                    .getDate())
            + " "
            + (date.getHours() < 10 ? "0" + date.getHours() : date
                    .getHours())
            + ":"
            + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date
                    .getMinutes())
            + ":"
            + (date.getSeconds() < 10 ? "0" + date.getSeconds() : date
                    .getSeconds());
        
        return datetime;
    }
</script>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top app-tabs-head app-noborder-bottom">
		<div onclick="toPageUrl('${ctx}/main/toStartNotCheck')"><span class="am-topbar-brand am-icon-angle-left" ></span>
			<div class="am-topbar-brand  app-toolbar">上站记录</div>
		</div>
		<div id="headerc" class="am-scrollable-horizontal" style="white-space:nowrap;">
			<table class="am-table am-table-striped am-text-nowrap am-margin-bottom-0" style="border-bottom: 1px solid #ddd">
				<tr class="app-tabs-title">
				 <td  class="app-tabs-selected am-text-primary" style="text-align: center;"><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-gears" style="color:#6BA25E"></i>&nbsp;巡检记录(${inspectionStationsum})</div></td>	
				 <td  style="text-align: center;"><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-warning" style="color:#CC3333"></i>&nbsp;异常记录(${inspectionStationExceptionsum})</div></td>	
				</tr>
			</table>
		</div>
	</header>
	<div id="takePlaceEL">&nbsp;</div>
    <div class="app-tabs-bd">
        <div data-am-widget="list_news" class="app-tabs-con am-list-news am-list-news-default am-animation-fade am-margin-0 am-padding-0" style="display:none;">
			<c:if test="${inspectionStations == null || inspectionStations == '[]'}">
				<div class="am-cf" style="text-align: center;margin-top: 40px">
					暂无巡检记录
				</div>
			</c:if>
			<c:if test="${inspectionStations != null && inspectionStations != '[]'}">
				<div id="inspectionStation">
					<c:forEach items="${inspectionStations}" var="obj" varStatus="status">
						<table class="am-table app-tb"> 
							<tr>
								<td style="vertical-align: middle;width:100px;">基站名称：</td>
								<td style="vertical-align: middle;">
									${obj.stationName}
								</td>
							</tr>
							<tr>
								<td style="vertical-align: middle;">作业状态：</td>
								<td style="vertical-align: middle;">
									${obj.status}
								</td>
							</tr>
							<tr>
								<td style="vertical-align: middle;">开始时间：</td>
								<td style="vertical-align: middle;">
									<fmt:formatDate value="${obj.createDate}" pattern="yyyy/MM/dd  HH:mm:ss" />
								</td>
							</tr>
							<tr>
								<td style="vertical-align: middle;">结束时间：</td>
								<td style="vertical-align: middle;">
									<fmt:formatDate value="${obj.finishTime}" pattern="yyyy/MM/dd  HH:mm:ss" />
								</td>
							</tr>
						</table>
					</c:forEach>
				</div>
				<div id="loadMore" style="height:10px;display: none;margin-bottom: 20px;" align="center" onclick="getInspectionStations();">
			     	点击加载更多...
				</div>
			</c:if>
        </div>
        <div data-am-widget="list_news" class="app-tabs-con am-list-news am-list-news-default am-animation-fade am-margin-0 am-padding-0" style="display:none;">
			<c:if test="${inspectionStationExceptions == null || inspectionStationExceptions == '[]'}">
				<div class="am-cf" style="text-align: center;margin-top: 40px">
					暂无异常站数
				</div>
			</c:if>
			<c:if test="${inspectionStationExceptions != null && inspectionStationExceptions != '[]'}">
				<div id="inspectionStationException">
					<c:forEach items="${inspectionStationExceptions}" var="obj" varStatus="status">
						<table class="am-table app-tb"> 
							<tr>
								<td style="vertical-align: middle;width:100px;">基站名称：</td>
								<td style="vertical-align: middle;">
									${obj.stationName}
								</td>
							</tr>
							<tr>
								<td style="vertical-align: middle;">作业状态：</td>
								<td style="vertical-align: middle;">
									${obj.status}
								</td>
							</tr>
							<tr>
								<td style="vertical-align: middle;">开始时间：</td>
								<td style="vertical-align: middle;">
									<fmt:formatDate value="${obj.createDate}" pattern="yyyy/MM/dd  HH:mm:ss" />
								</td>
							</tr>
							<tr>
								<td style="vertical-align: middle;">结束时间：</td>
								<td style="vertical-align: middle;">
									<fmt:formatDate value="${obj.finishTime}" pattern="yyyy/MM/dd  HH:mm:ss" />
								</td>
							</tr>
							<tr>
								<td style="vertical-align: middle;">异常信息：</td>
								<td style="vertical-align: middle;">
									${obj.deviceName}
								</td>
							</tr>
						</table>
					</c:forEach>
				</div>
				<div id="exloadMore" style="height:10px;display: none;margin-bottom: 20px;" align="center" onclick="getInspectionStationExceptions();">
			     	点击加载更多...
				</div>
			</c:if>
        </div>
	 </div>
</body>
<script>
initTabs(
function(){
	if(inspectionStationsum>5){
		$("#loadMore").show();
	}
},function(index){
	if(index=="0" && inspectionStationsum > 5){
		if(state=="0"){
			$("#loadMore").show();
		}
		$("#exloadMore").hide();
	}else if(index=="1" && inspectionStationExceptionsum > 5){
		if(state1=="0"){
			$("#exloadMore").show();		
		}
		$("#loadMore").hide();
	}
});
var headerch=$("#headerc").height();
//headerch+=10;
$("#takePlaceEL").attr("style","height:"+headerch+"px;background: #fff;");

</script>
</html>