<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>附近基站</title>
<%@include file="../commons/head.jsp" %>
<style type="text/css">    
	.lineOverflow {         
		width: 100%;        
		border:#000 solid 1px;        
		text-overflow: ellipsis;        
		white-space: nowrap;/*禁止自动换行*/        
		overflow: hidden;    
	}
</style>
<script type="text/javascript">
	function addStation(){
		showLoading();
		window.location.href="${ctx}/station/gotoStation";
	}
	
	function entryjob(stationId, obj){
		//if(event.srcElement.tagName!="BUTTON"){//如果触发的是两个按钮就不处理
			//var $curObj=$(obj); 
			//$curObj.parent().children(":last").prevAll().css('background-color','#C9C9C9');
			var imgStationId = "img_" + stationId;
			$("#" + imgStationId).css('background-color','#C9C9C9');
			var staNameStationId = "staName_" + stationId;
			$("#" + staNameStationId).css('background-color','#C9C9C9');
			var mobOperatorStationId = "mobOperator_" + stationId;
			$("#" + mobOperatorStationId).css('background-color','#C9C9C9');
			setTimeout("entryJobJump('"+stationId+"');", 500);
			//window.location.href="${ctx}/station/entryJob?stationId="+stationId;
		//}else{
		//	return;
			//如果触发的不是这两个按钮就触发此方法
		//}
	}
	
	function entryJobJump(stationId) {
		showLoading();
		window.location.href="${ctx}/station/entryJob?stationId="+stationId;
	}
	
	function goToSearchStation(){
		showLoading();
		window.location.href="${ctx}/station/goToSearchStation";
	}
	
	function updateStation(stationId, obj){
		//if(event.srcElement.tagName=="BUTTON"){//如果触发的是两个按钮就不处理
			//var $curObj=$(obj); 
			//$curObj.css('background-color','#C9C9C9');
			var distanceStationId = "distance_" + stationId;
			$("#" + distanceStationId).css('background-color','#C9C9C9');
			var updateStationId = "update_" + stationId;
			$("#" + updateStationId).css('background-color','#C9C9C9');
			setTimeout("updateSta('"+stationId+"');", 500);
		//}else{
		//	return;
			//如果触发的不是这两个按钮就触发此方法
		//}
	}
	
	function updateSta(stationId) {
		showLoading();
		window.location.href = "${ctx}/station/toUpdateStation?stationId="+stationId;
	}
	
</script>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top">
		<div ><span class="am-topbar-brand am-icon-angle-left" ></span>
		<div class="am-topbar-brand  app-toolbar">附近1公里内基站</div>
		</div>
	</header>
	<div class="am-cf">
	<c:if test="${stationList == null or stationList == '[]'}">
		<div class="am-cf" style="text-align: center;margin-top: 60px">
			附近没有定位到任何基站
		</div>
	</c:if>
	<%--<c:if test="${stationList != null}">
		<table class="am-table">
			<tbody>
				<c:forEach items="${stationList}" var="obj" varStatus="status">
						<tr style="background: #fff">
							<td onclick="entryjob('${obj.stationId}', this);" style="border-top: none;border-bottom:1px #ddd solid;width: 80px;height:60px;padding-left: 10px;vertical-align: middle;">
								<c:if test="${obj.thumbLocation != null}">
									<img class="am-radius" style="border:1px #ddd solid" data-echo="${obj.thumbLocation}" width="60" height="55">
								</c:if>
								<c:if test="${obj.thumbLocation == null}">
									<img class="am-radius" style="border:1px #ddd solid" src="${ctx}/assets/i/noPhoto.png" width="60" height="55">
								</c:if>
							</td>
							<td onclick="entryjob('${obj.stationId}', this);" style="border-top: none;border-bottom:1px #ddd solid;vertical-align: middle;">
								${obj.stationName}
							</td>
							<td onclick="entryjob('${obj.stationId}', this);" style="border-top: none;border-bottom:1px #ddd solid;vertical-align: middle;">
								<small class="am-fr am-padding-right-sm">
									<fmt:formatNumber value="${obj.distance}" type="currency" pattern="0m"/>
								</small>
							</td>
							<td onclick="updateStation('${obj.stationId}', this)" style="border-top: none;border-bottom:1px #ddd solid;vertical-align: middle;">
								<button class="am-btn am-btn-success am-btn-xs am-radius">
									<i class="am-icon-pencil-square-o"></i><strong>修改</strong>
								</button>
							</td>
						</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	--%>
	<c:if test="${stationList != null}">
		<table class="am-table" style="padding:0px;margin: 0px;">
			<tbody>
				<c:forEach items="${stationList}" var="obj" varStatus="status">
						<tr style="background: #fff">
							<td id="img_${obj.stationId}" rowspan="2" onclick="entryjob('${obj.stationId}', this);" style="border-top: none;border-bottom:1px #ddd solid;width: 80px;height:60px;padding-left: 10px;vertical-align: middle;">
								<c:if test="${obj.thumbLocation != null}">
									<img class="am-radius" style="border:1px #ddd solid"  <c:if test="${obj.photoStatus eq '待上传'}"> data-localId="${obj.photoLocalId}" </c:if>  data-echo="${obj.thumbLocation}" width="60" height="55">
								</c:if>
								<c:if test="${obj.thumbLocation == null}">
									<img class="am-radius" style="border:1px #ddd solid" src="${ctx}/assets/i/noPhoto.png" width="60" height="55">
								</c:if>
							</td>
							<td id="staName_${obj.stationId}" onclick="entryjob('${obj.stationId}', this);" style="border-top: none;border-bottom:none;vertical-align: middle;margin-bottom: 0px;padding-bottom: 0px;">
								<div class="am-text-truncate" style="width:${s_user.screenWidth}px;"><font size='3'>${obj.stationName}</font></div>
							</td>
							<td id="distance_${obj.stationId}" onclick="updateStation('${obj.stationId}', this);" style="border-top: none;border-bottom:none;vertical-align: middle;width:80px;margin-bottom: 0px;padding-bottom: 0px;">
								<small class="am-fr am-padding-right-sm">
									<fmt:formatNumber value="${obj.distance}" type="currency" pattern="0m"/>
								</small>
							</td>	
						</tr>
						
						
						<tr style="background: #fff">
							
							<td id="mobOperator_${obj.stationId}" onclick="entryjob('${obj.stationId}', this)" style="border-top: none;border-bottom:1px #ddd solid;vertical-align: middle;margin-top: 0px;padding-top: 0px;">
								<div class="am-text-truncate" style="width:${s_user.screenWidth}px;">
									<font style='font-size:13px;display:block;height:13px;' color='#8B8970'>动环:${obj.stationDH}</font>
									<font style='font-size:13px;' color='#8B8970'>${obj.mobileOperator}</font>
								</div>
							</td>
							<td id="update_${obj.stationId}" align="right" onclick="updateStation('${obj.stationId}', this)" style="border-top: none;border-bottom:1px #ddd solid;vertical-align: middle;width:80px;margin-top: 0px;padding-top: 0px;">
								<button class="am-btn am-btn-success am-btn-xs am-radius">
									<i class="am-icon-pencil-square-o"></i><strong>修改</strong>
								</button>
							</td>
						</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
	</div>
	<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
		<div class="am-u-sm-6">
			<button  onclick="addStation();"
				<c:if test="${role == null or role == 4 }">
					disabled="disabled"
				</c:if>
			type="button" class="am-btn am-btn-success am-radius am-btn-block">
				<i class="am-icon-plus"></i><strong>添加</strong>
			</button>
		</div>
		<div class="am-u-sm-6">
			<button  onclick="goToSearchStation();" type="button" class="am-btn am-btn-primary am-radius am-btn-block">
				<i class="am-icon-search"></i><strong>搜索</strong>
			</button>
		</div>
	</div>
	<script>
		echo.init({
			offset : 100,
			throttle : 250,
			unload : false
		});
		//showImagesPhotoListH5();
	</script>
</body>
</html>