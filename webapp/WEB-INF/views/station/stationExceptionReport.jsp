<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>异常上报</title>
<%@include file="../commons/head.jsp"%>
<script type="text/javascript">
	wx.config(${jsConfig});
	wx.ready(function(){
		//alert();
	});
	wx.error(function(res) {
		modalAlert("网络出现问题，请稍后重试", null);
	});
</script>
</head>
<body>
	<header class="am-topbar am-topbar-fixed-top app-tabs-head app-noborder-bottom">
		<div onclick="gohistory();">
			<span class="am-topbar-brand am-icon-angle-left"></span>
			<div class="am-topbar-brand  app-toolbar">异常上报</div>
		</div>
		<div id="headerc" class="am-scrollable-horizontal" style="white-space:nowrap;">
			<table class="am-table am-table-striped am-text-nowrap am-margin-bottom-0" style="border-bottom: 1px solid #ddd">
				<tr class="app-tabs-title">
				 <td id="roomCheck" class="app-tabs-selected am-text-primary"  style="text-align: center;"><div class="am-text-center am-vertical-align-bottom"><i class="am-icon-home" style="color:#F37B1D"></i>&nbsp;机房异常</div></td>	
				 <td id="towerCheck"  style="text-align: center;"><div class="am-text-center am-vertical-align-bottom"><span style="padding-left:16px;background:url(${ctx}/assets/fonts/xinhaota1.svg) no-repeat center;background-size:18px 18px;"></span>&nbsp;铁塔异常</div></td>	
				</tr>
			</table>
		</div>
	</header>
	<div id="takePlaceEL">&nbsp;</div>
		<div class="app-tabs-bd">
			<form id="form" name="form" onsubmit="return submitFormBy()" action="${ctx}/station/saveOrUpdatestationAbnormal" method="post">
				<input name="stationId" type="text" hidden="true" value="${stationId}"/>
				<input name="url" type="text" hidden="true" value="${url}"/>
				<input name="towerrecordId" type="text" hidden="true" value="${tower.recordId}"/>
				<input name="roomrecordId" type="text" hidden="true" value="${room.recordId}"/>
				<div data-am-widget="list_news" class="app-tabs-con am-list-news am-list-news-default am-animation-fade am-margin-0 am-padding-0" style="display:none;">
					<div class="am-g am-padding app-tb">
					  <div class="am-u-sm-12">
					  	<label class="am-checkbox">
					    	<input type="checkbox" name="abnormalCode1" value="机房基础沉降" <c:if test="${fn:contains(room.abnormalContent,'机房基础沉降')}">checked="checked"</c:if> data-am-ucheck> 机房基础沉降
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode1" value="机房倾斜" <c:if test="${fn:contains(room.abnormalContent,'机房倾斜')}">checked="checked"</c:if> data-am-ucheck> 机房倾斜
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode1" value="机房破损" <c:if test="${fn:contains(room.abnormalContent,'机房破损')}">checked="checked"</c:if>  data-am-ucheck> 机房破损
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode1" value="机房漏水" <c:if test="${fn:contains(room.abnormalContent,'机房漏水')}">checked="checked"</c:if> data-am-ucheck> 机房漏水
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode1" value="门锁有隐患" <c:if test="${fn:contains(room.abnormalContent,'门锁有隐患')}">checked="checked"</c:if> data-am-ucheck> 门锁有隐患
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode1" value="门锁需立即更换" <c:if test="${fn:contains(room.abnormalContent,'门锁需立即更换')}">checked="checked"</c:if> data-am-ucheck> 门锁需立即更换
					    </label>
					  </div>
					</div>
					<table class="am-table app-tb"> 
						<tbody>
							<tr>
								<td style="vertical-align: middle;height:30px;border-top: none" colspan="2">
									<p>异常照片：</p>
								 	<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
										<c:forEach items="${roomPhotoInfos}" var="item">
											<li id="${item.photoId}">
											    <div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
											     	<div style="height:72px;margin-bottom:2px;overflow:hidden;">
											       		<img src="${item.thumbLocation}"  <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if> alt="" data-rel="${item.fileLocation}"/>
											       	</div>
											       	<h3 class="am-gallery-title am-fr" onclick="deletePhotoInServer('${ctx}','${item.photoId}')">删除  <i class="am-icon-trash-o am-icon-sm" style="color:#dd514c;opacity:100;margin-top: 6px;"></i></h3>
											    </div>
											</li>
										</c:forEach>
										<li id="galleryAddBtn" onclick="takePhoto(this,'roomLocalIds');" data-weg>
										    <div onclick="void(0)">
										     <button type="button" class="app-add-photo"></button>
										    </div>
										</li>
									</ul>
									<input  class="localIds" type="hidden" id="roomLocalIds" name="roomLocalIds"  value="" size="100"/>
								</td>
							</tr>
							<tr>
								<td colspan="2" style="vertical-align: middle">
									<textarea id="remark1" name="remark1"  rows="3" cols="" class="am-form-field" placeholder="填写异常备注" >${room.remark}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
		
					<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
						<div class="am-u-sm-12">
							<button  onclick="javascript:document.form.submit();" type="submit" class="am-btn am-btn-danger am-radius am-btn-block">
								<i class="am-icon-frown-o"></i>
								<strong>上报异常</strong>
							</button>
						</div>
					</div>
				</div>
				<div data-am-widget="list_news" class="app-tabs-con am-list-news am-list-news-default am-animation-fade am-margin-0 am-padding-0" style="display:none;">
					<div class="am-g am-padding app-tb">
					  <div class="am-u-sm-12">
					  	<label class="am-checkbox">
					    	<input type="checkbox" name="abnormalCode" value="铁塔倾斜" <c:if test="${fn:contains(tower.abnormalContent,'铁塔倾斜')}">checked="checked"</c:if> data-am-ucheck> 铁塔倾斜
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode" value="塔构件变形" <c:if test="${fn:contains(tower.abnormalContent,'塔构件变形')}">checked="checked"</c:if> data-am-ucheck> 塔构件变形
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode" value="塔构件缺失" <c:if test="${fn:contains(tower.abnormalContent,'塔构件缺失')}">checked="checked"</c:if> data-am-ucheck> 塔构件缺失
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode" value="塔脚螺栓锈蚀" <c:if test="${fn:contains(tower.abnormalContent,'塔脚螺栓锈蚀')}">checked="checked"</c:if> data-am-ucheck> 塔脚螺栓锈蚀
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode" value="塔脚螺栓缺失" <c:if test="${fn:contains(tower.abnormalContent,'塔脚螺栓缺失')}">checked="checked"</c:if> data-am-ucheck> 塔脚螺栓缺失
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode" value="塔基沉降" <c:if test="${fn:contains(tower.abnormalContent,'塔基沉降')}">checked="checked"</c:if> data-am-ucheck> 塔基沉降
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode" value="塔基地面开裂" <c:if test="${fn:contains(tower.abnormalContent,'塔基地面开裂')}">checked="checked"</c:if> data-am-ucheck> 塔基地面开裂
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode" value="抱杆基础破损" <c:if test="${fn:contains(tower.abnormalContent,'抱杆基础破损')}">checked="checked"</c:if> data-am-ucheck> 抱杆基础破损
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode" value="抱杆锈蚀" <c:if test="${fn:contains(tower.abnormalContent,'抱杆锈蚀')}">checked="checked"</c:if> data-am-ucheck> 抱杆锈蚀
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode" value="抱杆屋面开裂" <c:if test="${fn:contains(tower.abnormalContent,'抱杆屋面开裂')}">checked="checked"</c:if> data-am-ucheck> 抱杆屋面开裂
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode" value="抱杆渗漏" <c:if test="${fn:contains(tower.abnormalContent,'抱杆渗漏')}">checked="checked"</c:if> data-am-ucheck> 抱杆渗漏
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode" value="楼面塔拉线锈蚀" <c:if test="${fn:contains(tower.abnormalContent,'楼面塔拉线锈蚀')}">checked="checked"</c:if> data-am-ucheck> 楼面塔拉线锈蚀
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode" value="楼面塔拉线断裂" <c:if test="${fn:contains(tower.abnormalContent,'楼面塔拉线断裂')}">checked="checked"</c:if> data-am-ucheck> 楼面塔拉线断裂
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode" value="拉线连接铆固点松动" <c:if test="${fn:contains(tower.abnormalContent,'拉线连接铆固点松动')}">checked="checked"</c:if> data-am-ucheck> 拉线连接铆固点松动
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode" value="拉线连接铆固点脱落" <c:if test="${fn:contains(tower.abnormalContent,'拉线连接铆固点脱落')}">checked="checked"</c:if> data-am-ucheck> 拉线连接铆固点脱落
					    </label>
					    <label class="am-checkbox">
					        <input type="checkbox" name="abnormalCode" value="拉线松弛" <c:if test="${fn:contains(tower.abnormalContent,'拉线松弛')}">checked="checked"</c:if> data-am-ucheck> 拉线松弛
					    </label>
					  </div>
					</div>
					<table class="am-table app-tb"> 
						<tbody>
							<tr>
								<td style="vertical-align: middle;height:30px;border-top: none" colspan="2">
									<p>异常照片：</p>
								 	<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
										<c:forEach items="${towerPhotoInfos}" var="item">
										    <li id="${item.photoId}">
										    <div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
										     	<div style="height:72px;margin-bottom:2px;overflow:hidden;">
										       		<img src="${item.thumbLocation}" <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if>  alt="" data-rel="${item.fileLocation}"/>
										       	</div>
										       	<h3 class="am-gallery-title am-fr" onclick="deletePhotoInServer('${ctx}','${item.photoId}')">删除  <i class="am-icon-trash-o am-icon-sm" style="color:#dd514c;opacity:100;margin-top: 6px;"></i></h3>
										    </div>
										</li>
										</c:forEach>
										<li id="galleryAddBtn1" onclick="takePhoto(this,'towerLocalIds');" data-weg>
										    <div onclick="void(0)">
										     <button type="button" class="app-add-photo"></button>
										    </div>
										</li>
									</ul>
									<input  class="localIds" type="hidden" id="towerLocalIds" name="towerLocalIds"  value=""/>
								</td>
							</tr>
							<tr>
								<td colspan="2" style="vertical-align: middle">
									<textarea id="remark" name="remark" rows="3" cols="" class="am-form-field" placeholder="填写异常备注" >${tower.remark}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
		
					<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
						<div class="am-u-sm-12">
							<button  onclick="javascript:document.form.submit();" type="submit" class="am-btn am-btn-danger am-radius am-btn-block">
								<i class="am-icon-frown-o"></i>
								<strong>上报异常</strong>
							</button>
						</div>
					</div>
				</div>
			</form>
		</div>

</body>
<script>
var galleryIsNotInit=true;
var gallery1IsNotInit=true;
initTabs(function(){
},function(i){
	if(galleryIsNotInit){
		//webUploaderSelectPhoto("#galleryAddBtn", "${ctx}");
		galleryIsNotInit=false;
	}
	if(gallery1IsNotInit && i==1 ){
		//webUploaderSelectPhoto("#galleryAddBtn1", "${ctx}");
		gallery1IsNotInit=false;
	}
   	
});
var headerch=$("#headerc").height();
//headerch+=10;
$("#takePlaceEL").attr("style","height:"+headerch+"px;background: #fff;");

echo.init({
	offset : 100,
	throttle : 250,
	unload : false
});

function submitFormBy(){
	//用store传照片saveBase64ImgData(function(){});
	return true;
}
!function(t) {
   	$("ul[data-am-widget='gallery']").one("click",function(){//点击预览图片关闭预览
		$(".am-pureview-slider").each(function(i){
			$(this).on("click",function(){
				$(".am-icon-chevron-left[data-am-close='pureview']").eq(i).triggerHandler("click");
			});
		});
	});
}(window.jQuery || window.Zepto);

</script>
</html>