<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>日报</title>
<%@include file="../commons/head.jsp" %>
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<style type="text/css">    
	.lineOverflow {         
		width: 100%;        
		border:#000 solid 1px;        
		text-overflow: ellipsis;        
		white-space: nowrap;/*禁止自动换行*/        
		overflow: hidden;    
	}
</style>

</head>
<body>
	<header onclick="gohistory();" class="am-topbar am-topbar-fixed-top">
		<div ><span class="am-topbar-brand am-icon-angle-left" ></span>
			<div class="am-topbar-brand  app-toolbar">
				<table>
					<tr>
						<td width="30%">${day}日报</td>
						<td width="60%" align="center" style="white-space: nowrap;">
							<marquee scrollAmount=2  style="width: 80%;height: 33px;" >
								<strong>基站电源问题管理专家，创智信科专业电源维护10年</strong>
							</marquee>
						</td>
					</tr>
				</table>
			</div>
		</div>
	</header>
	<input type="hidden" id="day" value="${day}">
	<div class="am-cf">
		<!-- <div id="generateTimeByArea"   style="width: 100%;height:264px"></div> 
		<div id="generateStationNumByArea"   style="width: 100%;height:264px"></div>
		<div id="generateNumByArea"   style="width: 100%;height:264px"></div>
		-->
		<!-- <div style="font-size: 16px;margin-top: 15px;margin-bottom: 10px;margin-left:30%;"><Strong>按区域统计发电时长</Strong></div> -->
		<div style="margin-top: 15px;margin-bottom: 10px;">${userInfo.cityId }&nbsp;&nbsp;发电基站数:${stationNum}&nbsp;&nbsp;发电总时长:${timeLength }H</div>
		<table class="am-table am-table-hover" style="border: 1px solid; ">
			<thead>
				<tr style="">
					<th class="am-text-middle am-text-nowrap" style="text-align:center;border-bottom:1px solid;background: #77DDFF;">序号</th>
					<th class="am-text-middle am-text-nowrap" style="text-align:center;border-left:1px solid;border-bottom:1px solid;background: #77DDFF;">基站名称</th>
					<th class="am-text-middle am-text-nowrap" style="text-align:center;border-left:1px solid;border-bottom:1px solid;background: #77DDFF;">发电时长</th>
				</tr>
			</thead>
			<tbody id="dayReportList">
	 			<c:forEach var="obj" items="${generateTimeByAreaData }" varStatus="status">
	 			 	<tr>
	 			 		<td class="am-text-middle" style="text-align:center;">${status.index+1}</td>
	 			 		<td class="am-text-middle" style="text-align:center;border-left:1px solid;">${obj.stationName}</td>
	 			 		<td class="am-text-middle" style="text-align:center;border-left:1px solid;">${obj.mobileOperator}H</td>
	 			 	</tr>
	 			</c:forEach>
    		</tbody>	
		</table>
	</div>
<script type="text/javascript">
//按区域统计站数
var generateStationNumByAreaOption={};
var generateStationNumByAreaChart={};
//按区域统计次数
var generateNumByAreaOption={};
var generateNumByAreaChart={};
//按区域统计时长
var generateTimeByAreaOption={};
var generateTimeByAreaChart={};


require.config({ paths: {echarts: 'http://echarts.baidu.com/build/dist'}});
require(['echarts','echarts/chart/line','echarts/chart/bar','echarts/chart/pie'], //按需加载
		function (ec) {
			$.ajax({type:"POST",dataType:"json",url:"${ctx}/report/getDaysReportData.json",
		    	data:{"day":$("#day").val()},
		    	success :function(json){
		    		generateTimeByAreaChart = ec.init(document.getElementById('generateTimeByArea'));
		    		generateTimeByAreaOption=$.extend(true, {}, daysBaseOption);
		    		generateTimeByAreaOption.title.text='按区域统计发电时长';
		    		generateTimeByAreaOption.legend.data=["时长"];
		    		generateTimeByAreaOption.xAxis[0].data=json.generateTimeByAreaData[0];
		    		generateTimeByAreaOption.series[0].data=json.generateTimeByAreaData[1];
		    		generateTimeByAreaOption.series[0].name="时长";
		    		generateTimeByAreaChart.setOption(generateTimeByAreaOption);
		    	}
		    });
		}
);
    
var daysBaseOption={
	title : {
        text: '',
        x:'center'
    },
	tooltip : {
		show:true,
		trigger: 'axis',
		axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		}
	},
	grid:{//坐标
		borderWidth:0,
       	z: 0,
       	x: 50,
       	y: 35,
       	x2: 30,
       	y2: 55
	},
	legend: {
		show:false,
	    data:[]
	},
	calculable : false,
	xAxis : [
	    {
	        type : 'category',
	        data : [],
	        axisLabel:{
				textStyle:{
					fontWeight:'bolder'
				},
				interval:0,
				rotate:-75
			},
			splitLine:{
				show:false  //网格显示
			}
	    }
	],
	yAxis : [
		{
			type : 'value',
			axisLabel:{
				textStyle:{
					fontWeight:'bolder'
				}
			},
			splitLine:{
				show:false  //网格显示
			}
		}
	],
	series : [
		{
			name:' ',
	        type:'bar',
	        data : []
	    }
	]
};

</script>
<center>
<%@include file="../commons/bottom.jsp" %>
</center>
</body>
</html>