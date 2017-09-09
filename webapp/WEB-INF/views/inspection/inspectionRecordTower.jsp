<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>设备盘点</title>
<%@include file="../commons/head.jsp"%>
<script type="text/javascript">
	///微信配置及相关js
	wx.config(${jsConfig});
	wx.ready(function() {
		//nothing
	});
	wx.error(function(res) {
		modalAlert("网络出现问题，请稍后重试", null);
	});
	
	function getAlertMsg(){
		$.post("${ctx}/devices/getAlertMsg.json",{},function(json){
			if(json.succ){
				modalAlert(json.msg, null);
			}
		},"json");
	}
	 function toPageUrlNew(url){
		window.location.href=url;
	}; 
	function finishJob(backUrl) {
		showLoading();
		var url = "${ctx}/quarterInspection/checkfinishStatus.json";
		$.get(url, function(json) {
			closeLoading();
			if (json.status == "0") {
				window.location.href="${ctx}/quarterInspection/finish?status=0";
			} else {
				window.location.href=backUrl;
			}
		}, "json");
	}
</script>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top app-tabs-head app-noborder-bottom">
		<div onclick="toPageUrlNew('${ctx}${toPageUrl}')">
			<span class="am-topbar-brand am-icon-angle-left"></span>
			<div class="am-topbar-brand  app-toolbar">设备盘点</div>
		</div>
	</header> 
	<div id="headerc" class="am-scrollable-horizontal app-tabs-head" style="white-space:nowrap;">
		  <table class="am-table am-table-striped am-text-nowrap" style="border-bottom: 1px solid #ddd">
			  <tr class="app-tabs-title">
				<td  style="text-align:center;" <c:if test="${selectId == 1 || null==selectId}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-info-circle" style="color:#BDB76B "></i>&nbsp;铁塔盘点</div></td>
				<td  style="text-align:center;" <c:if test="${selectId == 2}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-cogs" style="color: #7EC0EE"></i>&nbsp;机房盘点</div></td>
			  </tr>
		  </table>
	</div>
	<div class="app-tabs-bd" style="margin-top: 6px;">
		<div data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0">
			<jsp:include page="../template/towerInspection.jsp"></jsp:include>
		</div>
		<div data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0" style="display:none;">
			<jsp:include page="../template/roomInspection.jsp"></jsp:include>
		</div>
	</div>	
	<!--<jsp:include page="../template/towerInspection.jsp"></jsp:include>-->
	<jsp:include page="../template/deviceList.jsp"></jsp:include>
	<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
		<div class="am-u-sm-6">
			<button onclick="finishJob('${ctx}${toPageUrl}');" type="button" class="am-btn am-btn-success am-radius am-btn-block">
				<i class="am-icon-check"></i><strong>保存</strong>
			</button>
		</div>
		<div class="am-u-sm-6">
			<button onclick="saomiao();" type="button" class="am-btn am-btn-primary am-radius am-btn-block">
				<i class="am-icon-qrcode"></i><strong>扫码</strong>
			</button>
		</div>
	</div>
	<div class="am-modal am-modal-confirm" tabindex="-1" id="finish-confirm">
		<button type="button" class="am-btn am-radius am-modal-dialog">
			<div class="am-modal-bd am-radius">巡检尚未完成，是否结束？</div>
			<div class="am-modal-footer am-radius">
				<span class="am-modal-btn" data-am-modal-cancel>取消</span> <span
					class="am-modal-btn" data-am-modal-confirm>确定</span>
			</div>
		</button>
	</div>
</body>
<script>
initTabs();
var headerch=$("#headerc").height();
//headerch+=10;
$("#takePlaceEL").attr("style","height:"+headerch+"px;");
!function(t) {
	getAlertMsg();
}(window.jQuery || window.Zepto);

$(".am-form-group.am-padding-horizontal.am-padding-vertical-xs.app-gird :radio").on("change",function(){
	var dataEl=$(this).parent().siblings("p");
	saveOrUpdateCheck(dataEl,$(this).val(),$(this).attr("name"));
});
function saveOrUpdateCheck(dataEl,checkValue,categoryName){
	var recordId=dataEl.attr("data-pk");
	var defaultValue=dataEl.attr("data-dv");
	var checkType=dataEl.attr("data-tp");
	//var categoryName=dataEl.html();
	$(":radio").attr("disabled","disabled");
	$("button").attr("disabled","disabled");
	$.ajax({type: "POST",dataType:"json",
		url: "${ctx}/quarterInspection/saveOrUpdateCheck.json",
		data:{	recordId:recordId,
				defaultValue:defaultValue,
				checkType:checkType,
				checkValue:checkValue,
				categoryName:categoryName},
		success: function(json){
			if(json.succ){
				dataEl.attr("data-pk",json.recordId);
			};
		},
		complete:function(XHR, TS){
			$(":radio").removeAttr("disabled");
			$("button").removeAttr("disabled");
		}
	});
	
	/* $.post("${ctx}/inspection/saveOrUpdateCheck.json",{
		recordId:recordId,
		defaultValue:defaultValue,
		checkType:checkType,
		checkValue:checkValue,
		categoryName:categoryName
	},function(json){
		$("button").removeAttr("disabled","disabled");
		if(json.succ){
			dataEl.attr("data-pk",json.recordId);
		};
	},"json"); */
}
</script>
</html>