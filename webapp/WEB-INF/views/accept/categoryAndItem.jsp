<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
<head>
<title>交维检查事项</title>
<%@include file="../commons/head.jsp" %>
<link rel="stylesheet" href="${ctx}/assets/css/amazeui.switch.css"/>
<script src="${ctx}/assets/js/amazeui.switch.min.js"></script>
</head>
<body>
<div class="am-g" >
	<header class="am-topbar am-topbar-fixed-top app-tabs-head">
		<div  onclick="toStationCheckItem();"><span class="am-topbar-brand am-icon-angle-left"></span>
		<div class="am-topbar-brand  app-toolbar">交维检查事项</div>
		</div>
		<div id="headerc"  class="am-scrollable-horizontal" style="white-space:nowrap;">
		  <table class="am-table am-table-striped am-text-nowrap" style="margin-bottom:0px; border-bottom:none;">
		  <tr class="app-tabs-title">
		  <c:forEach items="${categoryInfoList}" var="obj" varStatus="status">
		  	<th <c:if test="${status.index eq 0}">class="app-tabs-selected  am-text-primary"</c:if>><div class="am-text-center" >${obj.category}</div></th>
		  </c:forEach>
		  </tr>
		  </table>
		</div>
	</header>
	<div id="app-tabs-bd" class="app-tabs-bd" >
		<c:forEach items="${categoryInfoList}" var="obj1" varStatus="status1">
		  	<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con am-margin-horizontal-0 " style="display:none;">
			  <div class="am-list-news-bd">
			    <ul id="${obj1.categoryId}" class="am-list">
			    	<div class="am-text-center"><i class="am-icon-spinner am-icon-sm am-icon-pulse"></i></div>
			    </ul>
			  </div>
			</div>
		</c:forEach>
	</div>
	
	<div class="am-popup am-scrollable-vertical" id="submitPhotoPopup">
	  <div class="am-popup-inner">
	    <div class="am-popup-hd">
	    	<header class="am-topbar app-tabs-head">
				<div  onclick="inSure();"><span class="am-topbar-brand am-icon-angle-left"></span>
				<div class="am-topbar-brand  app-toolbar">照片及备注</div>
				</div>
			</header>
	    </div>
	    <div class="am-popup-bd" style="background: white">
			<input type="hidden" id="checkCategoryId" value="">
	      	<form id="categoryRemarkForm" class="am-form" action=""  data-am-validator>
	      		<div class="am-form-group">
			      <textarea class="" rows="3" id="checkCategoryRemark" placeholder="填写备注信息" <c:if test="${stationCheckStatusStatus eq '已验收'}"> disabled="disabled"</c:if>></textarea>
			    </div>
			    <ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
					<li id="galleryAddBtn" onclick="takePhoto(this,'cateImgs');" <c:if test="${stationCheckStatusStatus eq '已验收'}"> style="display:none;"</c:if> >
						 <div onclick="void(0)">
							<button type="button" class="app-add-photo"></button>
						</div>
					</li>
				</ul>
				<input class="localIds" type="hidden" id="cateImgs" name="cateImgs"  value="" size="100"/>
				<br/>
			</form>
				<c:if test="${stationCheckStatusStatus ne '已验收'}">
					<!-- <p><a onclick="SRemark()" class="am-btn am-btn-success am-btn-block" >提交</a></p> -->
					<button id="sumbitThis" onclick="SRemark();" type="button" class="am-btn am-btn-success am-radius am-btn-block"><strong id="strongs">提交</strong></button>	
				</c:if>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
//微信配置
wx.config(${jsConfig});
wx.ready(function() {
	//nothing
});
wx.error(function(res) {
	modalAlert("网络出现问题，请稍后重试", null);
});

/**业务js*****/
var stationCheckStatusStatus='${stationCheckStatusStatus}';

var initList=${categoryIdList};//类别id和类别检查id
initTabs();//初始化tabs
for(var i=0;i<initList.length;i++){
	setData(initList[i].categoryId,initList[i].checkCategoryId);
}
function setData(categoryId,checkCategoryId){
	/*通过类别id查询检查项，填充列表*/
	$.post("${ctx}/categoryItem/getItemList.json",{
		categoryId:categoryId
	},function(json){
		if("0"==json.status){
			$("#"+categoryId).html('');
			
			$("#"+categoryId).append(
				"<li  class='am-g am-list-item-desced am-margin-vertical am-padding-horizontal-xs' style='margin-bottom:10px;border:none;line-height: 16px;' onclick='submitCategoryRemarkAndPhoto(&quot;"+checkCategoryId+"&quot;)'>"+
					"<div class=' am-u-sm-11 am-list-main'><i class='am-icon-image am-text-primary'></i> 照片及备注</div>"+
					"<div class='am-u-sm-1 am-list-thumb'><i class='am-icon-angle-right am-icon-sm am-fr' style='color:#63B8FF;'></i></div>"+
				"</li>"
			);
			
			for(var i=0;i<json.itemList.length;i++){
				var item=json.itemList[i];
				//alert(item.itemContent);
				var ifChecked="checked";
				var dataOnText="是";
				var dataOffText="否";

				var dataOnColor="success";
				var dataOffColor="danger";
				
				if('有'==item.defaultValue||'无'==item.defaultValue){
					var dataOnText="有";
					var dataOffText="无";
				}
				//alert(item.itemContent+","+item.defaultValue+","+item.checkResult)
				if('否'==item.defaultValue||'无'==item.defaultValue){
					ifChecked="";
					var dataOnColor="danger";
					var dataOffColor="success";
					if('是'==item.checkResult||'有'==item.checkResult){
						ifChecked="checked";
					}
				}
				if('否'==item.checkResult||'无'==item.checkResult){
					ifChecked="";
				}
				
				var isReanOnly="";
				if("已验收"==stationCheckStatusStatus){
					isReanOnly="readonly";
				}
				var sswitch='<p class="am-u-sm-centered  am-radius">'+
							  '<input id="'+item.checkItemId+'" defaultValue="'+item.defaultValue+'" name="handleWidth"  data-am-switch type="checkbox"  data-size="xs" data-on-text="'+dataOnText+'" data-off-text="'+dataOffText+'" data-on-color="'+dataOnColor+'" data-off-color="'+dataOffColor+'" state="false" '+ifChecked+' '+isReanOnly+'>'+
							'</p>';
				
				$("#"+categoryId).append(
					'<li class="am-g am-list-item-desced am-padding-horizontal-xs">'+
					    '<div class="am-u-sm-9 am-list-main">'+
					    	(1+i)+'.'+item.itemContent+
					    '</div>'+
					    '<div class="am-u-sm-3 am-list-thumb">'+
					    	sswitch+
				    	'</div>'+
				  	'</li>'
				);
				$('#'+item.checkItemId).on('switchChange.bootstrapSwitch', function(event, state) {
					/*switch的监听器*/
					submitItemState(this.id,state,$(this).attr("defaultValue"));
				});
			}
			$('[name="handleWidth"]').bootstrapSwitch();
		}else{
			$("#"+categoryId).html(json.status);
		};
	},"json");
}
function submitCategoryRemarkAndPhoto(checkCategoryId){
	/*弹出提交备注和照片的面板*////////////////////////////////////////////////////////////////////////////////////////////////////////////
	showLoading("正在加载...");
	$('#checkCategoryId').val(checkCategoryId);
	$.post("${ctx}/categoryItem/getCategoryRemarkAndPhoto.json",{
		stationId:'${stationId}',
		checkCategoryId:checkCategoryId
	},function(json){
		$('#checkCategoryRemark').val(json.remark);
		closeLoading();
		//if(json.photoList.length>0&&StringUtils.isNotEmpty(pi.getFileLocation()))
		for(var i=0;i<json.photoList.length;i++){
			var photoinfo=json.photoList[i];
			addPhotoHere(photoinfo.photoId,photoinfo.thumbLocation,"#galleryAddBtn",photoinfo.fileLocation);
			$('.localIds').val("");
		}
	},"json");
	$('#submitPhotoPopup').modal({
		relatedTarget : this,
		onConfirm : function() {
			clearRemark();
		}
	});
}
function clearRemark(){
	/*清除备注和照片面板*/
	$('#checkCategoryId').val("");
	$('#checkCategoryRemark').val("");
	$('.localIds').val("");
	$('#galleryAddBtn').siblings().remove();
	$('.am-pureview-slider li').remove();
}
function submitItemState (checkItemId,checkResult,defaultValue){
	/*更新状态*/
	$.post("${ctx}/categoryItem/updateCategoryAndItem.json",{
		checkItemId:checkItemId,
		checkResult:checkResult,
		defaultValue:defaultValue
	},function(json){
		//alert(json.status);
	},"json");
}
function SRemark(){
	/*提交备注*/
	showLoading("正在提交...");
	var remark=$.trim($("#checkCategoryRemark").val());
	var checkCategoryId=$('#checkCategoryId').val();
	var cateImgs = $('#cateImgs').val();
	//console.log(remark);
	$.post("${ctx}/categoryItem/updateCategoryAndItem.json",{
		checkCategoryId:checkCategoryId,
		remark:remark,
		cateImgs:cateImgs
	},function(json){
		inSure();
		closeLoading();
	},"json");
	
};
//弹框方法
function inSure(){//点击确定按钮
	$('#submitPhotoPopup').modal('close');
	clearRemark();
}

function addPhotoHere(photoId,aimgId,renderObj,fileLocation){//添加照片
	var deleteBtn='<h3 class="am-gallery-title am-fr" onclick="deletePhotoInServer(&apos;${ctx}&apos;,&apos;'+photoId+'&apos;)">删除  <i class="am-icon-trash-o am-icon-sm" style="margin-top: 6px;color:#dd514c;opacity:100;"></i></h3>';
	if("已验收"==stationCheckStatusStatus){
		deleteBtn="";
	}
		$(renderObj).before(
	   		'<li class="sb" id="'+photoId+'">'+
	   			'<div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">'+
	   				'<div style="height:72px;margin:2px;overflow:hidden;">'+
	   					'<img src="'+aimgId+'" alt="" style="border:none;" data-rel="'+fileLocation+'"/>'+
	   				'</div>'+
	   				deleteBtn+
	   		    '</div>'+
	   		'</li>'
	    );
	var localIdsInput=$(renderObj).parents("ul").next(".localIds");
    var inputIds = localIdsInput.val();
    
    var inputIdz;
    if(inputIds.length>0){
	    inputIdz = inputIds.split(",");
	    inputIdz.push(aimgId);
    }else{
    	inputIdz = aimgId;
    }    	
    localIdsInput.val(inputIdz);
}
//高度
var headerch=$("#headerc").height();
headerch+=10;
$("#app-tabs-bd").attr("style","margin-top:"+headerch+"px;");
function toStationCheckItem(){
	//返回路径
	var url="${ctx}/acceptItem/checkItem.html";
	window.location.href=encodeURI(url);
}
</script>
<script type="text/javascript">

	
	var Hammer = $.AMUI.Hammer;
	var hammertime = new Hammer($(".app-tabs-bd")[0]);
	hammertime.on("swipeleft swiperight", function(ev) {
		//
		var $targetTabsBd=$(ev.target).parents(".app-tabs-bd");
		var $targetTabsCon=$targetTabsBd.find(".app-tabs-con:visible");
		if(ev.type=="swipeleft"){
			//$targetTabsCon.next().addClass("am-animation-slide-right");
			//$(".app-tabs-selected.am-text-primary").next().trigger("click");
		}
		if(ev.type=="swiperight"){
			//$targetTabsCon.prev().addClass("am-animation-slide-left");
			//$(".app-tabs-selected.am-text-primary").prev().trigger("click");
		}
		//$targetTabsCon.removeClass("am-animation-slide-right am-animation-slide-left");
		//$targetTabsCon.removeClass("am-animation-slide-left");
		//console.log(ev);
	});
	
	$.post("${ctx}/categoryItem/checkIfFinish.json",{},function(json){
		if(stationCheckStatusStatus!=json.stationCheckStatusStatus){
			//alert();
			location.reload();
		}
	},"json");
	
	!function(t) {
		$("ul[data-am-widget='gallery']").one("click",function(){//点击预览图片关闭预览
			$(".am-pureview-slider").on("click",function(){
				$(".am-icon-chevron-left[data-am-close='pureview']").triggerHandler("click");
			});
		});
	}(window.jQuery || window.Zepto);
</script>
</body>
</html>