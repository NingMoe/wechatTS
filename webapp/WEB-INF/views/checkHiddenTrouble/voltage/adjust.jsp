<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
	<title>隐患排查助手</title>
	<%@include file="../../commons/head.jsp"%>
</head>
<body style="background-color: #ffffff">
<header class="am-topbar am-topbar-fixed-top">
		<div>
			<div class="am-topbar-brand app-toolbar">${powerBrand}-开关电源浮充电压调整方法 </div>
		</div>
</header><br/>
	<c:choose>
		<c:when test="${powerBrand eq '艾默生'}">
	       <div title="艾默生普通型" style="padding:20px;" id="vof1">
	           <ol>
	           <li><strong>涉及型号：</strong>PS48600-2B/50 | PS48600-2B/50-150A | PS48600-2B150</li><br/>
	           <li><strong>涉及的可能密码：</strong>0 | 1 | 11 | 124 | 123456 | 640275 | 000000 | 111111</li><br/>
	           <li><strong>调整方法：</strong>
	           <p>①开关电源显示主界面内有当前充电状态，基站负载电流与工作电压</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/ap01.jpg"/>
	           <p>②按ENT键进入主菜单，按向下↓选择参数设置点击ENT。</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/ap02.jpg"/>
	           <p>③进入密码界面后输入密码。（上下键调节数值，左右键调节位置。）点击ENT。</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/ap03.jpg"/>
	           <p>④键入参数设置界面，按向下↓选择电池参数，点击ENT。</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/ap04.jpg"/>
	           <p>⑤进入电池参数界面，按向下↓选择基本参数，点击ENT。</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/ap05.jpg"/>
	           <p>⑥进入充电管理界面，记录浮充电压，根据方案设置浮充电压47V，点击ESC键，开始记录电池组单体电压</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/ap06.jpg"/>
	           <p></p>
	           </li>
	           </ol>
	       </div>
	       <div title="艾默生数字型" style="overflow:auto;padding:20px;" id="vof2">
	           <ol>
	           <li><strong>涉及型号：</strong>PS48300-2/30 | PS48600-3B/2900-x2</li><br/>
	           <li><strong>涉及的可能密码：</strong>0 | 1 | 11 | 124 | 123456 | 640275 | 000000 | 111111</li><br/>
	           <li><strong>调整方法：</strong>
	           <p>①主界面内可以看到直流电压，负载电流和系统状态，点击F2（菜单键）</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/as01.jpg"/>
	           <p>②进入主菜单，点击键盘上数字6</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/as02.jpg"/>
	           <p>③进入输入密码界面，输入登入密码。点击ENT</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/as03.jpg"/>
	           <p>④进入系统参数设置界面，按数字数字键4</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/as04.jpg"/>
	           <p>⑤进入电池管理设置界面，点击数字键2</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/as05.jpg"/>
	           <p>⑥进入充电管理参数界面，调整浮充电压至47v ，点击ENT确认</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/as06.jpg"/>
	           <p></p>
	           </li>
	           </ol>
	       </div>
       </c:when>
       <c:when test="${powerBrand eq '普天洲际'}">
       <div title="普&nbsp;天&nbsp;洲&nbsp;际" style="padding:20px;" id="vof3">
           <ol>
           <li><strong>涉及型号：</strong>DUM23-50 | DUM25F06-48/50（600）LV | DUM23 48/50（600x）</li><br/>
           <li><strong>涉及的可能密码：</strong>123456</li><br/>
           <li><strong>调整方法：</strong>
           <p>①在主界面中可以找到负载、当前电压、充电状态数据。点击电池键</p>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/pt01.jpg"/>
           <p>②显示系统浮充电压值（浮充电压），点击增加</p>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/pt02.jpg"/>
           <p>③点击确认所要调整的数值闪烁，点击增加或减少对数值进行调整，调整浮充电压至47V，最后点击确认为设置成功。</p>
           <p></p>
           </li>
           </ol>
       </div>
       </c:when>
       <c:when test="${powerBrand eq '通力环'}">
       <div title="通&nbsp;力&nbsp;环&nbsp;" style="padding:20px;" id="vof4">
           <ol>
           <li><strong>涉及的可能密码：</strong>0000 | 1234 | 1111 | 9999 | 4444  5555 | 6666</li><br/>
           <li><strong>调整方法：</strong>
           <p>①进入主界面可以找到直流电压，基站负载，系统状态数据。选择设置，点击确认</p>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/tl01.jpg"/>
           <p>②输入密码</p>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/tl02.jpg"/>
           <p>③进入系统设置界面，选择设置使能，点击确认</p>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/tl03.jpg"/>
           <p>④对设置使能进行设置，按上下键把禁止改为允许，点击确认</p>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/tl04.jpg"/>
           <p>⑤回到系统设置界面，选择参数设置，点击确认</p>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/tl05.jpg"/>
           <p>⑥进入电源参数设置界面，找到浮充电压点击确认进入设置，左右键选择光标在哪一位，上下键调节光标在的那位数值大小，调整到47V后点击确认为设置成功。</p>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/tl06.jpg"/>
           <p></p>
           </li>
           </ol>
       </div>
       </c:when>
        <c:when test="${powerBrand eq '中达电通'}">
	       <div title="中&nbsp;达&nbsp;电&nbsp;通" style="padding:20px;" id="vof5">
	           <ol>
	           <li><strong>涉及型号：</strong>DUM94A-48/50</li><br/>
	           <li><strong>涉及的可能密码：</strong>1234 | 0000</li><br/>
	           <li><strong>调整方法：</strong>
	           <p>①主界面可以找到电压，电流（负载），系统状态。点击参数键</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/zd01.jpg"/>
	           <p>②进入参数设置界面，选择模块功能设定，点选择</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/zd02.jpg"/>
	           <p>③进入模块参数设定界面，选择浮均充电压设定，点击选择</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/zd03.jpg"/>
	           <p>④进入均浮充设置界面，选择设定浮充电压，点击选择</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/zd04.jpg"/>
	           <p>⑤进入浮充电压设置界面，设置浮充电压47V。点击上一层</p>
	           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img style="width:230px;height: 170px" data-echo="${ctx}/assets/i/voltageImgs/zd05.jpg"/>
	           <p></p>
	           </li>
	           </ol>
	       </div>
       </c:when>
       <c:otherwise>
       		暂无调参。。
		</c:otherwise>
       </c:choose>
       <input type="hidden" id="deviceCheck" name="deviceCheck" value="${deviceCheck}"/>
	<div class="app-div-btn-tool" style="margin-top: 18px">
		<div class="am-g">
			<div class="am-u-sm-6">
				<button  onclick="previousStep();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
					<i class="am-icon-arrow-left"></i>&nbsp;<strong>上一步</strong>
				</button>
			</div>
			<div class="am-u-sm-6">
				<button  onclick="nextStep();" type="button" class="am-btn am-btn-success am-radius am-btn-block">
					<strong>下一步</strong>&nbsp;<i class="am-icon-arrow-right"></i>
				</button>
			</div>
		</div>
	</div>
	<div>&nbsp;</div>
    <script>
		echo.init({
			offset : 100,
			throttle : 250,
			unload : false
		});
	</script>
</body>
<script type="text/javascript">
/* 
alert($("#powerBrand").val());

$('#batteryType').change(function(){
	var type = $('#batteryType').val();
	if(type == "0"){
		$('#vof1').css("display","none");
		$('#vof2').css("display","none");
		$('#vof3').css("display","none");
		$('#vof4').css("display","none");
		$('#vof5').css("display","none");
	}else if(type == "1"){
		$('#vof1').css("display","inline");
		$('#vof2').css("display","none");
		$('#vof3').css("display","none");
		$('#vof4').css("display","none");
		$('#vof5').css("display","none");
	}else if(type == "2"){
		$('#vof1').css("display","none");
		$('#vof2').css("display","inline");
		$('#vof3').css("display","none");
		$('#vof4').css("display","none");
		$('#vof5').css("display","none");
	}else if(type == "3"){
		$('#vof1').css("display","none");
		$('#vof2').css("display","none");
		$('#vof3').css("display","inline");
		$('#vof4').css("display","none");
		$('#vof5').css("display","none");
	}else if(type == "4"){
		$('#vof1').css("display","none");
		$('#vof2').css("display","none");
		$('#vof3').css("display","none");
		$('#vof4').css("display","inline");
		$('#vof5').css("display","none");
	}else if(type == "5"){
		$('#vof1').css("display","none");
		$('#vof2').css("display","none");
		$('#vof3').css("display","none");
		$('#vof4').css("display","none");
		$('#vof5').css("display","inline");
	}
});
 */
function previousStep(){
	var deviceCheck = $("#deviceCheck").val();
	if(deviceCheck == 'deviceCheck') {
		window.location.href="${ctx}/checkHiddenTrouble/goDeviceCheck";
	} else {
		gohistory();
	}
} 

function nextStep(){
	window.location.href="${ctx}/checkHiddenTrouble/toDischarge.html";
}
</script>
</html>
