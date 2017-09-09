<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<%@include file="../commons/head.jsp"%>
</head>
<body>
		<header class="am-topbar am-topbar-fixed-top">
			<div >
				<!-- <span class="am-topbar-brand am-icon-angle-left" ></span> -->
				<div class="am-topbar-brand  app-toolbar">
					<table>
						<tr>
							<td width="30%">公告栏列表</td>
							<td width="60%" align="center"  style="white-space: nowrap;">
								<marquee scrollAmount=2  style="width: 80%;height: 33px;" >
									<strong>基站电源问题管理专家，创智信科专业电源维护10年</strong>
								</marquee>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</header>
	<div class="am-cf">
		
		<table class="am-table am-table-striped" style="font-size:12px;background: #fff;">
				<c:forEach items="${proclamationList}" var="obj" varStatus="status" >
					<tr>
						<td id="detail_${obj.proclamationId }" style="border-top: none;border-bottom:1px solid rgb(221, 221, 221);vertical-align: middle;margin-bottom: 0px;padding-bottom: 0px;" onclick="getDetail('${obj.proclamationId}');">
							<strong>公告主题:${obj.proclamationTheme }</strong><br>
							填报时间:<fmt:formatDate value="${obj.fillTime }" pattern="yyyy-MM-dd" />&nbsp;&nbsp;填报人员:${obj.fillUser }
							<div class="" style="width:200px;height:40px; line-height:20px;text-overflow: ellipsis;overflow: hidden;display: -webkit-box;-webkit-line-clamp: 2;-webkit-box-orient: vertical;">公告详情:${obj.proclamationDetail }</div>
							公告周期:<fmt:formatDate value="${obj.startTime }" pattern="yyyy-MM-dd HH:mm" />至<fmt:formatDate value="${obj.endTime }" pattern="yyyy-MM-dd HH:mm" />
						</td>
						<td id="" align="right" style="border-top: none;border-bottom:1px solid rgb(221, 221, 221);vertical-align: middle;margin-bottom: 0px;">
							<div class="am-btn-group-stacked">
								<button id="setBtn_${obj.proclamationId }" class="am-btn am-btn-xs am-btn-danger am-margin-bottom-sm" <c:if test="${obj.isTop == '1'}"> disabled </c:if> onclick="setTop('${obj.proclamationId}','${obj.groupName}');">
									&nbsp;&nbsp;置顶&nbsp;&nbsp;
								</button>
								<button id="cancleBtn_${obj.proclamationId }" class=" am-btn am-btn-success am-btn-xs" <c:if test="${obj.isTop == '0'}">disabled</c:if> onclick="cancleTop('${obj.proclamationId}','${obj.groupName}');">
									取消置顶
								</button>
							</div>
						</td>
					</tr>
				</c:forEach>
		</table>
	</div>
	
	<script type="text/javascript">
	function getDetail(proclamationId){
		//var themeId = "theme_" + proclamationId;
		//$("#" + themeId).css('background-color','#C9C9C9');
		var detailId = "detail_" + proclamationId;
		$("#" + detailId).css('background-color','#C9C9C9');
		window.location.href="${ctx}/proclamation/toDetail?proclamationId="+proclamationId;
	}
	function setTop(proclamationId,groupName){
		var setId="#setBtn_"+proclamationId;
		var cancleId="#cancleBtn_"+proclamationId;
		$(setId).attr("disabled", true);
		$(cancleId).attr("disabled", false);
		var flag=true;
		$.ajax({type:'POST',dataType:'json',url:'${ctx}/proclamation/updateTop.json',
			data:{
				proclamationId:proclamationId,
				groupName:groupName,
				flag:flag
				},
			success:function(json){
				window.location.href="${ctx}/proclamation/getProclamationList?groupName="+groupName;
			}
		});
	}
	function cancleTop(proclamationId,groupName){
		var setId="#setBtn_"+proclamationId;
		var cancleId="#cancleBtn_"+proclamationId;
		$(setId).attr("disabled", false);
		$(cancleId).attr("disabled", true);
		var flag=false;
		$.ajax({type:'POST',dataType:'json',url:'${ctx}/proclamation/updateTop.json',
			data:{
				proclamationId:proclamationId,
				groupName:groupName,
				flag:flag
				},
			success:function(json){
				window.location.href="${ctx}/proclamation/getProclamationList?groupName="+groupName;
			}
		});
	}
	</script>
	<center>
	<%@include file="../commons/bottom.jsp" %>
	</center>
</body>
</html>