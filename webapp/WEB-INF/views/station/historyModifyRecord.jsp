<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript">
	var currentPage = 1;
	var pageCounts;
	var pageSize = 10;
	//根据基站名称查询基站列表（ajax请求）
	function searchStationHistory(type) {
		if(type == 'first') {
			$("#tab").empty();
			$("#searchResult").html('');
			currentPage = 1;
		} else if(type == 'last') {
			currentPage = pageCounts;
		} else if(type == 'prew') {
			currentPage = currentPage - 1;
		} else if(type == 'next') {
			currentPage = currentPage + 1;
		}
		$.ajax({
			type : "POST",
			async : false,
			url : "${ctx}/station/searchStationHistory.json",
			data : "stationId=" + stationId1 + "&pageCurrent=" + currentPage,
			success : function(data) {
				if(data.stationHistoryBOs.length == 0 && type == 'first') {
					$("#loadMore").hide();
					geneSearchResult(0);
					$("#result").show();
					closeLoading();
					$("#search").attr('disabled',false); 
					return;
				}
				if (data.stationHistoryBOs) {
					var stationHistoryBOs = data.stationHistoryBOs;
					var totalRecords = data.totalRecords;
					geneSearchResult(totalRecords);
					$("#result").show();
					pageCounts = Math.ceil(totalRecords/pageSize);
					//geneTBody($("#tab"));
					if(data.stationHistoryBOs.length < 10 || pageCounts == currentPage) {
						$("#loadMore").hide();
					} else {
						$("#loadMore").show();
					}
					for(var i = 0; i < stationHistoryBOs.length; i++) {
						var userName = '';
						var operateTime = '';
						var operateTime1 = '';
						var operateType = '';
						var operateObject = '';
						var oldValue = '';
						var newValue = '';
						var operateProperty = '';
						
						userName = stationHistoryBOs[i].userName;
						operateTime = stationHistoryBOs[i].operateTimeDesc;
						operateType = stationHistoryBOs[i].operateType;
						operateObject = stationHistoryBOs[i].operateObject;
						oldValue = stationHistoryBOs[i].oldValue;
						newValue = stationHistoryBOs[i].newValue;
						operateProperty = stationHistoryBOs[i].operateProperty;
						if(oldValue == '' || oldValue == 'null' || oldValue == null ) {
							oldValue = '"空"';
						}
						if(newValue == '' || newValue == 'null' || newValue == null ) {
							newValue = '"空"';
						}
						if(operateProperty == '' || operateProperty == 'null' || operateProperty == null ) {
							operateProperty = '&nbsp;';
						}
						var operateSrc = stationHistoryBOs[i].operateSrc;
						if(operateType == '修改') {
							$("#tab").append(
								"<tr>" +
								"<td style='vertical-align: middle'>" + 
									"(" + operateSrc + ")" + userName + "&nbsp;&nbsp;&nbsp;&nbsp;" + operateTime + "<br/>" + 
									operateType + operateObject + operateProperty + "</font>，从<font color='#CD4F39'><s>" + oldValue + "</s></font>" + operateType + "为<font color='#008B00'>" + newValue + "</font>。" + 
								"</td></tr>"	
							);
						} else if(operateType == '添加') {
							$("#tab").append(
								"<tr>" +
								"<td style='vertical-align: middle'>" + 
									"(" + operateSrc + ")" + userName + "&nbsp;&nbsp;&nbsp;&nbsp;" + operateTime + "<br/>" + 
									operateType + operateObject + "。" + 
								"</td></tr>"	
							);
						} else if(operateType == '删除') {
							$("#tab").append(
								"<tr>" +
								"<td style='vertical-align: middle'>" + 
									"(" + operateSrc + ")" + userName + "&nbsp;&nbsp;&nbsp;&nbsp;" + operateTime + "<br/>" + 
									operateType + operateObject + "。" + 
								"</td></tr>"	
							);
						}
					}
				}
				closeLoading();
			}
		});
	}
	//生成查询完基站列表的表头
	function geneTBody(obj) {
		var tbody = "<tbody onclick='setSelectedBgColor();'>" +
						//"<tr style='visibility:hidden;height:0px;'>" + 
						//	"<th style='vertical-align: middle; width: 80%';border-bottom:0px;></th>" + 
							//"<th style='vertical-align: middle; width: 100%'>基站名称：</th>" + 
						//	"<th style='vertical-align: middle; width: 20%'></th>" + 
						//"</tr>" + 
					"</tbody>";
		obj.append(tbody);
	}
	
	function geneSearchResult(staNum) {
		var searchResult = "<font color='#8B8970' size='2'>(共<font color='red'>"+staNum+"</font>个修改历史记录)</font>";
		$("#searchResult").html(searchResult);
	}
	
	var curObj= null;
	//选择某个基站，触发的事件
	function setSelectedBgColor()
	{	
	 	if(window.event.srcElement.tagName=='TD')
		{
			if(curObj!=null) curObj.style.background='';
			curObj=window.event.srcElement.parentElement;	
			var $curObj=$(curObj); 
			//获取基站的主键ID
			var stationId = $curObj.attr("id");
			curObj.style.background='#ffdead';
			setTimeout("toUpdateStation('"+stationId+"');", 500);
	   }
	}
	function toUpdateStation(stationId) {
		showLoading();
		window.location.href = "${ctx}/station/toUpdateStation?stationId="+stationId;
	}
	
	window.onload = function() {
		//searchStationHistory('first');
		var stationHistoryBOs = ${stationHistoryBOs};
		var totalRecords = ${totalRecords};
		loadData(stationHistoryBOs,totalRecords);
	}
	
	function loadData(stationHistoryBOs,totalRecords) {
		if(stationHistoryBOs.length == 0) {
			$("#loadMore").hide();
			geneSearchResult(0);
			$("#result").show();
			closeLoading();
			return;
		}
		if (stationHistoryBOs) {
			geneSearchResult(totalRecords);
			$("#result").show();
			pageCounts = Math.ceil(totalRecords/pageSize);
			//geneTBody($("#tab"));
			if(stationHistoryBOs.length < 10 || pageCounts == currentPage) {
				$("#loadMore").hide();
			} else {
				$("#loadMore").show();
			}
			for(var i = 0; i < stationHistoryBOs.length; i++) {
				var userName = '';
				var operateTime = '';
				var operateTime1 = '';
				var operateType = '';
				var operateObject = '';
				var oldValue = '';
				var newValue = '';
				var operateProperty = '';
				
				userName = stationHistoryBOs[i].userName;
				operateTime = stationHistoryBOs[i].operateTimeDesc;
				operateType = stationHistoryBOs[i].operateType;
				operateObject = stationHistoryBOs[i].operateObject;
				oldValue = stationHistoryBOs[i].oldValue;
				newValue = stationHistoryBOs[i].newValue;
				operateProperty = stationHistoryBOs[i].operateProperty;
				if(oldValue == '' || oldValue == 'null' || oldValue == null ) {
					oldValue = '"空"';
				}
				if(newValue == '' || newValue == 'null' || newValue == null ) {
					newValue = '"空"';
				}
				if(operateProperty == '' || operateProperty == 'null' || operateProperty == null ) {
					operateProperty = '&nbsp;';
				}
				var operateSrc = stationHistoryBOs[i].operateSrc;
				if(operateType == '修改') {
					$("#tab").append(
						"<tr>" +
						"<td style='vertical-align: middle'>" + 
							"(" + operateSrc + ")" + userName + "&nbsp;&nbsp;&nbsp;&nbsp;" + operateTime + "<br/>" + 
							operateType + operateObject + operateProperty + "</font>，从<font color='#CD4F39'><s>" + oldValue + "</s></font>" + operateType + "为<font color='#008B00'>" + newValue + "</font>。" + 
						"</td></tr>"	
					);
				} else if(operateType == '添加') {
					$("#tab").append(
						"<tr>" +
						"<td style='vertical-align: middle'>" + 
							"(" + operateSrc + ")" + userName + "&nbsp;&nbsp;&nbsp;&nbsp;" + operateTime + "<br/>" + 
							operateType + operateObject + "。" + 
						"</td></tr>"	
					);
				} else if(operateType == '删除') {
					$("#tab").append(
						"<tr>" +
						"<td style='vertical-align: middle'>" + 
							"(" + operateSrc + ")" + userName + "&nbsp;&nbsp;&nbsp;&nbsp;" + operateTime + "<br/>" + 
							operateType + operateObject + "。" + 
						"</td></tr>"	
					);
				}
			}
		}
		closeLoading();
	}
</script>
<body>
	<!--<header class="am-topbar am-topbar-fixed-top">
		<div  onclick="goStationDetail();"><span class="am-topbar-brand am-icon-angle-left"></span>
		<div class="am-topbar-brand  app-toolbar">历史修改记录</div>
		</div>
	</header>-->
	<form class="am-form">
		<table class="am-table app-tb" style="padding:0px;margin: 0px;border:0px;">
			<tbody>
				<tr>
					<td id="result" style="vertical-align: middle;display:none" colspan="2">
						<span id="searchResult"></span>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<span id="searchResult"></span>
	<div>
		<table class="am-table app-tb" style="table-layout: fixed;">
			<tbody id="tab">
				
			</tbody>
		</table>
		<div id="loadMore" style="height:40px;display: none;" onclick="searchStationHistory('next');" data-am-scrollspy>
	     	&nbsp;&nbsp;加载更多...
		</div>
	</div>
<script>
	$('#loadMore').on('inview.scrollspy.amui', function() {
    	searchStationHistory('next');
  	});
</script>
