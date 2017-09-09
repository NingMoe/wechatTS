<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

	<!-- <div class="am-cf" style="text-align: left;margin-top: 1px">
		<span id="actionNum"></span>
	</div> -->
	<table id="stationTab" class="am-table app-tb" style="">
		
	</table>

<script type="text/javascript">	
	function getStationAction(){
		var stationId1 = '${acceptStation.stationId}';
		$.ajax({
			type : "POST",
			dataType:"json",
			url : "${ctx}/station/motion.json",
			data : {"stationId":stationId1},
			async : true,
			success : function(data) {
				var coldataStr="";
				var actionList=data.actionList;
					for(var i=0;i<actionList.length;i++){
						var message="";
						var dateResource="";
						var billId="";
						var alarmName="";
						var timeLength="";
						var voltage="";
						var endVoltage="";
						var finishTime="";
						var generatePeople="";
						message=actionList[i].message;
						dateResource=actionList[i].dateResource;
						var noticeTime=actionList[i].noticeTime;
						billId=actionList[i].billId;
						alarmName=actionList[i].alarmName;
						timeLength=actionList[i].timeLength;
						voltage=actionList[i].voltage;
						endVoltage=actionList[i].endVoltage;
						generatePeople=actionList[i].generatePeople;
						var finishTime=actionList[i].finishTime;
						if(message == '告警信息'){
							coldataStr+='<tr><td style="vertical-align: middle;">'+message+
							'&nbsp;&nbsp;<span class="am-badge am-badge-danger am-text-sm">'+dateResource+
							'</span>&nbsp;&nbsp;'+noticeTime+'<br/>'+alarmName+'&nbsp;&nbsp;';
							if(voltage == '' || voltage ==null){
								coldataStr+='退服时长'+timeLength +'分';
							}else{
								coldataStr+='当前电压'+voltage+'V &nbsp;&nbsp; 停电时长'+timeLength+'分';
							}
							coldataStr+='</td></tr>';
						}
						if(message == '已超时工单' || message == '即将超时工单' || message == '新工单'){
							coldataStr+='<tr><td style="vertical-align: middle;">'+message+
							'&nbsp;&nbsp;<span class="am-badge am-badge-danger am-text-sm">'+dateResource+
							'</span>&nbsp;&nbsp;'+noticeTime+'<br/>';
							
							coldataStr+=billId+'&nbsp;&nbsp;'+alarmName;
							
							coldataStr+='</td></tr>';
						}
						if(message == 'FSU离线'){
							coldataStr+='<tr><td style="vertical-align: middle;">'+message+
							'&nbsp;&nbsp;<span class="am-badge am-badge-danger am-text-sm">'+dateResource+
							'</span>&nbsp;&nbsp;'+noticeTime+'<br/>';
							
							coldataStr+=alarmName+'&nbsp;&nbsp;离线时长'+timeLength+'分';
							
							coldataStr+='</td></tr>';
						}
						if(message == '发电信息'){
							coldataStr+='<tr><td style="vertical-align: middle;">'+message+
							'&nbsp;&nbsp;<span class="am-badge am-badge-warning am-text-sm">'+dateResource+
							'</span>&nbsp;&nbsp;';
							if(finishTime == '' || finishTime == null){
								coldataStr+=noticeTime+'<br/>';
							}else{
								coldataStr+=finishTime+'<br/>';
							}
							coldataStr+='发电人员&nbsp;'+generatePeople+'&nbsp;&nbsp;';
							
							if(finishTime == '' || finishTime == null){
								coldataStr+='发电开始电压'+voltage+'V';
							}else{
								coldataStr+='发电结束电压'+endVoltage+'V';
							}
							coldataStr+='</td></tr>';
						}
				}
				$("#stationTab").append(coldataStr);
			},
			error:function(){
				alert("请求失败");
			}
	});
}
	function geneResultNum(staNum) {
		var searchResult = "<font color='#8B8970' size='2'>共<font color='red'>"+staNum+"</font>条基站动作记录</font>";
		$("#actionNum").html(searchResult);
	}
	window.onload=getStationAction();
		
</script>