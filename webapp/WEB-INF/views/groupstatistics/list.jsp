<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>点播量</title>
<%@include file="../commons/head.jsp"%>
</head>
<body>
<div class="am-topbar-fixed-top am-scrollable-horizontal app-tabs-head" style="white-space:nowrap;">
	  <table class="am-table am-table-striped am-text-nowrap am-margin-bottom-0" style="border-bottom: 1px solid #ddd">
		  <tr class="app-tabs-title">
			<th style="text-align:center;"  <c:if test="${selectId == 1||null==selectId}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-calendar" style="color:rgb(12, 189, 0) "></i>&nbsp;按日统计查询</div></th>
			<th style="text-align:center;"  <c:if test="${selectId == 2}">class="app-tabs-selected am-text-primary"</c:if>><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-calendar" style="color: rgb(46, 172, 220)"></i>&nbsp;按周统计查询</div></th>
		  </tr>
	  </table>
</div>
<div class="app-tabs-bd" style="margin-top: 0px;">
	<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0">
		<div class="am-cf">
			<table id="dayList" class="am-table" style="padding:0px;margin: 0px;border-collapse:separate; border-spacing:0px 10px;">
				<!-- <tr onclick="toDaysStatistics('2016-04-21');" style="margin-bottom: 20px;background: #fff;border-top: none;border-bottom:1px solid rgb(221, 221, 221);">
					<td style="vertical-align: middle;">
						<div class="am-text-truncate am-text-lg" >2016/4/21 统计</div>
					</td>
				</tr>
				<tr onclick="toDaysStatistics();" style="margin-bottom: 20px;background: #fff;border-top: none;border-bottom:1px solid rgb(221, 221, 221);">
					<td style="vertical-align: middle;">
						<div class="am-text-truncate am-text-lg" >2016/4/20 统计</div>
					</td>
				</tr> -->
			</table>
		</div>
	</div>
	<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con  am-margin-0 am-padding-0" style="display:none;">
		<div class="am-cf">
			<table id="weekList" class="am-table" style="padding:0px;margin: 0px;border-collapse:separate; border-spacing:0px 10px;">
				<!-- <tr onclick="toWeekStatistics();" style="margin-bottom: 20px;background: #fff;border-top: none;border-bottom:1px solid rgb(221, 221, 221);">
					<td style="vertical-align: middle;">
						<div class="am-text-truncate am-text-lg" >2016/4/18-2016/4/24 统计</div>
					</td>
				</tr>
				<tr onclick="toWeekStatistics();" style="margin-bottom: 20px;background: #fff;border-top: none;border-bottom:1px solid rgb(221, 221, 221);">
					<td style="vertical-align: middle;">
						<div class="am-text-truncate am-text-lg" >2016/4/11-2016/4/17 统计</div>
					</td>
				</tr> -->
			</table>
		</div>
	</div>
</div>

<div class="am-popup" id="statisticsPopup">
	<div class="am-popup-inner">
	    <div data-am-modal-close class="am-popup-hd">
	      <div style="float: left;">
			<span class="am-topbar-brand am-icon-angle-left am-icon-md" style="height: 39px;line-height: 39px;"></span>
			<div id="titleName" class="am-topbar-brand  app-toolbar" style="height: 39px;line-height: 39px;">统计</div>
		  </div>
		</div>
	    <div id="statisticsPopupBd" class="am-popup-bd">
	    
	    <div class="am-panel am-panel-secondary">
		  <div class="am-panel-hd">群点播次数排名</div>
		  <div class="am-panel-bd">
		    <table  class="am-table am-table-striped am-table-hover" style="text-align:center;margin-bottom: 0px;" >
			    <thead > <tr> <th style="text-align:center;">排名</th>  <th style="text-align:center;">群名</th> <th style="text-align:center;" >点播次数</th> </tr> </thead>
			    <tbody id="groupHits">  </tbody>
			</table>
		  </div>
		</div>
	    
	    <div class="am-panel am-panel-secondary">
		  <div class="am-panel-hd">跨群按人员点播次数排名</div>
		  <div class="am-panel-bd">
		    <table  class="am-table  am-table-striped am-table-hover" style="text-align:center;margin-bottom: 0px;" >
			    <thead > <tr> <th style="text-align:center;" >排名</th> <th style="text-align:center;" >帐号</th> <th style="text-align:center;" >点播次数</th> <th style="text-align:center;" >群名</th> </tr> </thead>
			    <tbody id="userHits">  </tbody>
			</table>
		  </div>
		</div>
 
	    <div class="am-panel am-panel-secondary">
		  <div class="am-panel-hd">跨群命令点播次数排名</div>
		  <div class="am-panel-bd">
		    <table  class="am-table  am-table-striped am-table-hover" style="text-align:center;margin-bottom: 0px;" >
			    <thead > <tr> <th style="text-align:center;" >排名</th> <th style="text-align:center;" >命令</th> <th style="text-align:center;" >点播次数</th> </tr> </thead>
			    <tbody id="instructionsHits"> </tbody>
			</table>
		  </div>
		</div>
		
		
		<div class="am-panel am-panel-secondary">
		  <div class="am-panel-hd">按群统计用户点播次数排名</div>
		  <div class="am-panel-bd">
		    <table  class="am-table  am-table-striped am-table-hover" style="text-align:center;margin-bottom: 0px;" >
			    <thead > <tr> <th style="text-align:center;" >排名</th> <th style="text-align:center;" >命令</th> <th style="text-align:center;" >点播次数</th> <th style="text-align:center;" >排名</th>  </tr> </thead>
			    <tbody id="groupUserHits">
			        <tr>
			            <td>Amaze UI</td>
			            <td>http://amazeui.org</td>
			            <td>2012-10-01</td>
			             <td class="am-active am-text-middle" rowspan="2" >2012-10-01</td>
			        </tr>
			        <tr>
			            <td>Amaze UI</td>
			            <td>http://amazeui.org</td>
			            <td>2012-10-01</td>
			        </tr>
			    </tbody>
			</table>
		  </div>
		</div>
		<div class="am-panel am-panel-secondary">
		  <div class="am-panel-hd">按群统计命令点播次数排名</div>
		  <div class="am-panel-bd">
		    <table  class="am-table  am-table-striped am-table-hover" style="text-align:center;margin-bottom: 0px;" >
			    <thead > <tr> <th style="text-align:center;" >排名</th> <th style="text-align:center;" >命令</th> <th style="text-align:center;" >点播次数</th> </tr> </thead>
			    <tbody id="groupInstructionsHits">
			        <tr>
			            <td>Amaze UI</td>
			            <td>http://amazeui.org</td>
			            <td>2012-10-01</td>
			             <td rowspan="2" >2012-10-01</td>
			        </tr>
			        <tr>
			            <td>Amaze UI</td>
			            <td>http://amazeui.org</td>
			            <td>2012-10-01</td>
			        </tr>
			    </tbody>
			</table>
		  </div>
		</div>

	    </div>
	</div>
</div>

<script>
initTabs();

var now = new Date(); //当前日期   
var nowDayOfWeek = now.getDay(); //今天本周的第几天   
var nowDay = now.getDate(); //当前日   
var nowMonth = now.getMonth(); //当前月   
var nowYear = now.getYear(); //当前年   
nowYear += (nowYear < 2000) ? 1900 : 0; //  
for(var i=0;i<30;i++){
	var dd = new Date();
	dd.setDate(dd.getDate()+-i);
	var date=formatDate(dd);
	
	var str='<tr onclick="toDaysStatistics(&apos;'+date+'&apos;);" style="margin-bottom: 20px;background: #fff;border-top: none;border-bottom:1px solid rgb(221, 221, 221);">'+
				'<td style="vertical-align: middle;">'+
					'<div class="am-text-truncate am-text-lg" >'+date+' 统计</div>'+
				'</td>'+
			'</tr>';
	$("#dayList").append(str);
	
	if(i<15){
		var startDate=getWeekStartDate(-i);
		var endDate=getWeekEndDate(-i);
		
		var weekStr='<tr onclick="toWeekStatistics(&apos;'+startDate+'&apos;,&apos;'+endDate+'&apos;);" style="margin-bottom: 20px;background: #fff;border-top: none;border-bottom:1px solid rgb(221, 221, 221);">'+
						'<td style="vertical-align: middle;">'+
							'<div class="am-text-truncate am-text-lg" >'+startDate+'-'+endDate+' 统计</div>'+
						'</td>'+
					'</tr>';
		
		$("#weekList").append(weekStr);
		
	}
	
}
function toDaysStatistics(day){
	$('#statisticsPopup').modal();
	$("#titleName").html(day+" 统计");
	
	getData({"day":day});
}
function toWeekStatistics(fromday,today){
	$('#statisticsPopup').modal();
	$("#titleName").html(fromday+"-"+today+" 统计");
	getData({"fromday":fromday,"today":today});
}

function getData(obj){

	$("#groupHits").html("");
	$("#userHits").html("");
	$("#instructionsHits").html("");
	$("#groupUserHits").html("");
	//$("#groupInstructionsHits").html("");
	
	$.ajax({type:"POST",dataType:"json",url:"${ctx}/groupStatistics/getData.json",
		data:obj,
		success:function(data){
			
			var groupHitsStr="";
			var groupHits=data.groupHits;
			for(var i=0;i<groupHits.length;i++){
				groupHitsStr+="<tr><td>"+(i+1)+"</td><td>"+groupHits[i].groupName+"</td><td>"+groupHits[i].num+"</td></tr>";
			}
			$("#groupHits").html(groupHitsStr);
			
			var userHitsStr="";
			var userHits=data.userHits;
			for(var i=0;i<userHits.length;i++){
				userHitsStr+="<tr><td>"+(i+1)+"</td><td>"+userHits[i].clientid+"</td><td>"+userHits[i].num+"</td><td>"+userHits[i].groupName+"</td></tr>";
			}
			$("#userHits").html(userHitsStr);
			
			var instructionsHitsStr="";
			var instructionsHits=data.instructionsHits;
			for(var i=0;i<instructionsHits.length;i++){
				instructionsHitsStr+="<tr><td>"+(i+1)+"</td><td>"+instructionsHits[i].content+"</td><td>"+instructionsHits[i].num+"</td></tr>";
			}
			$("#instructionsHits").html(instructionsHitsStr);
			
			var groupUserHitsStr="";
			var groupNameTemp="";
			var guRowNumTemp="";
			
			
			var guRowspanNum=0;
			var needRowpanTd="";
			var donotNeedRowpanTd="";
			var guGroupNameTempStr="";
			
			var groupUserHits=data.groupUserHits;
			for(var i=0;i<groupUserHits.length;i++){
				if(guGroupNameTempStr!=""&&guGroupNameTempStr!=groupUserHits[i].groupName){
					needRowpanTd+="<tr><td>"+groupUserHits[i].seq+"</td><td>"+groupUserHits[i].clientid+"</td><td>"+groupUserHits[i].num+"</td><td rowspan='"+guRowspanNum+"' >"+groupUserHits[i].groupName+"</td></tr>";
				
					$("#groupUserHits").append(needRowpanTd+donotNeedRowpanTd);
					
					guGroupNameTempStr=groupUserHits[i].groupName;
					guRowspanNum=0;
					needRowpanTd="";
					donotNeedRowpanTd="";
				}else{
					guRowspanNum++;
					donotNeedRowpanTd+="<tr><td>"+groupUserHits[i].seq+"</td><td>"+groupUserHits[i].clientid+"</td><td>"+groupUserHits[i].num+"</td></tr>";
				}
			}
			
			
			/* for(var i=0;i<groupUserHits.length;i++){
				var tdTempStr="";
				if(guGroupNameTempStr!=groupUserHits[i].groupName){
					if(""!=guGroupNameTempStr)
						$("#row_"+i).attr("rowspan",""+guRowspanNum);
					tdTempStr="<td id='row_"+i+"' >"+groupUserHits[i].groupName+"</td>";
					guGroupNameTempStr=groupUserHits[i].groupName;
					guRowspanNum=0;
					guRowNumTemp=""+i;
				}else{
					guRowspanNum++;
				}
				groupUserHitsStr+="<tr><td>"+groupUserHits[i].seq+"</td><td>"+groupUserHits[i].clientid+"</td><td>"+groupUserHits[i].num+"</td>"+tdTempStr+"</tr>";
					
			} */
			//$("#groupUserHits").html(groupUserHitsStr);
			//$("#row_"+guRowNumTemp).attr("rowspan",""+guRowspanNum);
			
			
			var groupInstructionsHitsStr="";
			var groupInstructionsHits=data.groupInstructionsHits;
			for(var i=0;i<groupInstructionsHits.length;i++){
				groupInstructionsHitsStr+="<tr><td></td><td></td><td></td><td></td></tr>";
			}
			$("#groupInstructionsHits").html(groupInstructionsHitsStr);
			
			
		},complete:function(){
			//closeLoading();
		}
	});
	
}

//格局化日期：yyyy-MM-dd   
function formatDate(date) {
	var myyear = date.getFullYear();   
	var mymonth = date.getMonth()+1;   
	var myweekday = date.getDate();  
	  
	if(mymonth < 10){   
	mymonth = "0" + mymonth;   
	}   
	if(myweekday < 10){   
	myweekday = "0" + myweekday;   
	}   
	return (myyear+"-"+mymonth + "-" + myweekday);   
}
//0 获得本周的开端日期   
function getWeekStartDate(w) {
	if(w){
		w=7*w;
	}else{
		w=0;
	}
	var weekStartDate = new Date(nowYear, nowMonth, nowDay - nowDayOfWeek+1 + w);   
	return formatDate(weekStartDate);   
}  
  
//0 获得本周的停止日期   
function getWeekEndDate(w) {  
	if(w){
		w=7*w;
	}else{
		w=0;
	}
	var weekEndDate = new Date(nowYear, nowMonth, nowDay + (6 - nowDayOfWeek)+1 + w);   
	return formatDate(weekEndDate);   
}  
</script>
</body>
</html>