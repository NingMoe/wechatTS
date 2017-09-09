<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../commons/head.jsp" %>
<title>搜索基站</title>
<script type="text/javascript">
	var currentPage = 1;
	var pageCounts;
	var pageSize = 10;
	//根据基站名称查询基站列表（ajax请求）
	function searchStation(type) {
		$("#search").attr('disabled',true); 
		var staName = $("#staName").val().trim();
		if(staName == null || staName == '' || staName == 'null') {
			$("#tab").empty();
			$("#loadMore").hide();
			$("#result").hide();
			alert('基站名称不能为空');
			//modalAlert('基站名称不能为空', null);
			$("#search").attr('disabled',false); 
			return;
		}
		//$("#tab").empty();
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
			url : "${ctx}/station/searchStation.json",
			data : "stationName=" + staName + "&pageCurrent=" + currentPage,
			success : function(data) {
				if(data.stationList.length == 0 && type == 'first') {
					$("#loadMore").hide();
					geneSearchResult(staName, 0);
					$("#result").show();
					closeLoading();
					$("#search").attr('disabled',false); 
					return;
				}
				if (data.stationList) {
					var stationList = data.stationList;
					var totalRecords = data.totalRecords;
					geneSearchResult(staName, totalRecords);
					$("#result").show();
					pageCounts = Math.ceil(totalRecords/pageSize);
					//geneTBody($("#tab"));
					if(data.stationList.length < 10 || pageCounts == currentPage) {
						$("#loadMore").hide();
					} else {
						$("#loadMore").show();
					}
					for(var i = 0; i < stationList.length; i++) {
						var stationName = '';
						var stationDH = '动环-';
						var stationId = '';
						var stationAddress = '';
						var provinceId = '';
						var cityId = '';
						var countyId = '';
						var mobileOperator = '';
						
						stationName = stationList[i].stationName;
						
						if(stationList[i].stationDH != null){
							stationDH += stationList[i].stationDH;
						}
						stationId = stationList[i].stationId;
						stationAddress = stationList[i].stationAddress;
						provinceId = stationList[i].provinceId;
						cityId = stationList[i].cityId;
						countyId = stationList[i].countyId;
						mobileOperator = stationList[i].mobileOperator;
						if(mobileOperator == '' || mobileOperator == 'null' || mobileOperator == null ) {
							mobileOperator = '&nbsp;';
						}
						var detailAddress = getDetailAddress(provinceId, cityId, countyId, stationAddress);
						
						$("#tab").append(
						"<tr id='" + stationId  + "'>"
                             	+"<td style='white-space: nowrap;overflow: hidden;text-overflow: ellipsis;width:80%'><font size='3'>" + stationName + "</font>" + '<br/>'
                             		+"<font size='2'>" + stationDH + "</font>" + '<br/>'
									+"<font color='#8B8970' size='2'>" + detailAddress + "</font>" + '<br/>' 
									+"<font color='#8B8970' size='2'>" + mobileOperator + "</font>"
								+"</td><td align='right' style='vertical-align: middle;width:20%'><font color='#63B8FF'>修改</font>&nbsp;&nbsp;<i class='am-icon-angle-right am-icon-sm' style='color:#63B8FF;'></i></td>"
                        	+"</tr>"); 
                        	
					}
				}
				$("#search").attr('disabled',false); 
				closeLoading();
			}
		});
	}
	
	function getDetailAddress(provinceId, cityId, countyId, stationAddress) {
		var detailAddress = '';
		if(provinceId != null && provinceId != '' && provinceId != 'null') {
			detailAddress = provinceId;
		}
		if(cityId != null && cityId != '' && cityId != 'null') {
			if(provinceId != cityId) {
				detailAddress = detailAddress + cityId;
			}
		}
		if(countyId != null && countyId != '' && countyId != 'null') {
			detailAddress = detailAddress + countyId;
		}
		if(stationAddress != null && stationAddress != '' && stationAddress != 'null') {
			detailAddress = detailAddress + stationAddress;
		}
		return detailAddress;
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
	
	function geneSearchResult(staName, staNum) {
		var searchResult = "“<font color='red'>"+staName+"</font>”&nbsp;&nbsp;基站名称筛选"
			+ "<font color='#8B8970' size='2'>(共<font color='red'>"+staNum+"</font>个基站)</font>";
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
</script>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top" style="padding:0px;margin: 0px;border:0px;">
		<div onclick="toPageUrl('${ctx}/station/gotoNearbyList')"><span class="am-topbar-brand am-icon-angle-left" ></span>
		<div class="am-topbar-brand  app-toolbar">搜索基站</div>
		</div>
	</header>
	<form class="am-form">
		<table class="am-table app-tb" style="padding:0px;margin: 0px;border:0px;">
			<tbody>
				<tr>
					<td style="vertical-align: middle; width: 80%;border-top: none;">
						<input type="text" id="staName"
						placeholder="输入基站名称" />
					</td>
					<td style="vertical-align: middle; width: 20%;border-top: none;"><!--
						搜索<i class="am-icon-search"></i>
						--><button id="search" onclick="searchStation('first');" type="button" class="am-btn am-btn-success am-radius">
							<i class="am-icon-search"></i><strong>搜索</strong>
						</button>
					</td>
				</tr>
				<tr>
					<td id="result" style="vertical-align: middle;display:none" colspan="2">
						<span id="searchResult"></span>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<div>
		<table class="am-table app-tb" style="table-layout: fixed;">
			<tbody id="tab" onclick="setSelectedBgColor();">
				
			</tbody>
		</table>
		<div id="loadMore" style="height:40px;display: none;" data-am-scrollspy>
	     	&nbsp;&nbsp;加载更多...
		</div>
	</div>
</body>
<script>

	$('#loadMore').on('inview.scrollspy.amui', function() {
    	searchStation('next');
  	});
</script>
</html>