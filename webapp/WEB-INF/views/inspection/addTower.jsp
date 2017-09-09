<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
	<head>
	<title>铁塔信息</title>
	<%@include file="../commons/head.jsp"%>
	<link href="${ctx}/assets/powercut/mobiscroll.custom-2.5.4.min.css" rel="stylesheet" type="text/css">
	<script src="${ctx}/assets/powercut/mobiscroll.custom-2.5.4.min.js" type="text/javascript"></script>
<style type="text/css">
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
    -webkit-appearance: none !important;
    margin: 0;
}
.am-form-group input[type="radio"] {
	display: none;
}
.am-form-group input[type="radio"]+label {
	font-weight: 400;
	color: #3B3B3B;
	border-radius: 4px;
	display: inline-block;
	background-color: #EDEDED;
	cursor: pointer;
	padding: 5px 10px;
}
.am-form-group input[type="radio"]:checked+label {
	font-weight: 700;
	background-color: #00BFFF;
	color: #FFFFFF;
}
td {
	vertical-align: middle;
}
</style>
</head>
	<body>
		<header class="am-topbar am-topbar-fixed-top">
		<div onclick="gohistory();">
			<span class="am-topbar-brand am-icon-angle-left"></span>
			<div class="am-topbar-brand  app-toolbar">
				<c:if test="${towerBO == null || towerBO == '[]'}">
				新增铁塔
				</c:if>
				<c:if test="${towerBO != null}">
				修改铁塔
				</c:if>
			</div>
		</div>
		</header>
		<form class="am-form" id="addTower" data-am-validator onsubmit="return false;">
			<input type="hidden" id="towerId" name="towerId"  value="${towerBO.towerId}" />
			<input type="hidden" id="towerTypeDetail" name="towerTypeDetail"  value="${towerBO.towerTypeDetail}" />
			<input type="hidden" id="towerType" name="towerType"  value="${towerBO.towerType}" />
			<table class="am-table app-tb">
				<tbody>
					<tr>
						<td style="vertical-align: middle; width: 120px;">
							平台数量：
						</td>
						<td>
							<div class="am-input-group">
								<input step="1" type="number" id="platformNum" class="am-form-field" name="platformNum" min="0" max="100" placeholder="请输入平台数量" value="${towerBO.platformNum}"/>
								<span class="am-input-group-label">个</span>
							</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle">
							平台平均间距：
						</td>
						<td>
							<div class="am-input-group">
								<input step="0.001" type="number" id="platformSpacing" class="am-form-field" name="platformSpacing"  min="0" max="100"  placeholder="请输入平台平均间距" value="${towerBO.platformSpacing}"/>
								<span class="am-input-group-label">米</span>
							</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle">
							抱杆数量：
						</td>
						<td>
							<div class="am-input-group">
								<input step="1" type="number" id="derrickNum" class="am-form-field" name="derrickNum"  min="0" max="100"  placeholder="请输入抱杆数量" value="${towerBO.derrickNum}"/>
								<span class="am-input-group-label">个</span>
							</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle">
							抱杆占用情况：
						</td>
						<td>
							<div class="am-input-group">
								<input step="1" type="number" id="derrickUse" class="am-form-field" name="derrickUse"  min="0" max="100"  placeholder="请输入抱杆占用情况" value="${towerBO.derrickUse}"/>
								<span class="am-input-group-label">个</span>
							</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle">
							塔高：
						</td>
						<td>
							<div class="am-input-group">
								<input step="0.001" type="number" id="towerHeight" class="am-form-field" name="towerHeight"  min="0" max="100"  placeholder="请输入塔高" value="${towerBO.towerHeight}"/>
								<span class="am-input-group-label">米</span>
							</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle" rowspan="2">
							铁塔类型：
						</td>
						<td style="vertical-align: middle">
							<div class="am-form-group" style="height: 24px">
								<input type="radio" value="地面塔" id="floor" name="towerType" <c:if test="${towerBO.towerType eq '地面塔'}"> checked="checked" </c:if>>
								<label for="floor">
									地面塔
								</label>

								<input type="radio" id="building" value="楼面塔" name="towerType" <c:if test="${towerBO.towerType eq '楼面塔'}"> checked="checked" </c:if>>
								<label for="building">
									楼面塔
								</label>
								
							</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							<label id="towerTypeDetail1">${towerBO.towerTypeDetail }</label>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
				<div class="am-u-sm-12">
					<button class="am-btn am-btn-success am-radius am-btn-block" onclick="submitForm();">
						<c:if test="${towerBO == null || towerBO == '[]'}">
						<strong>添加</strong>
						</c:if>
						<c:if test="${towerBO != null}">
						<strong>修改</strong>
						</c:if>
					</button>
				</div>
			</div>
		</form>
		<div class="am-modal am-modal-confirm" tabindex="-1" id="finish-confirm">
		  <div class="am-modal-dialog">
		    <div class="am-modal-bd" id="replaceResult">
		      
		    </div>
		    <div class="am-modal-footer">
		      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
		    </div>
		  </div>
		</div>
		<SPAN style="display: none;">
		<SELECT name="orgNoSelect" id="orgNoSelect"></SELECT>
		</SPAN>
	</body>
	<script type="text/javascript">
		function getOrgNoData(dictTypeId) {
			showLoading();
			$("#orgNoSelect").html("");
			var url = $("#ctx").val()+"/common/getDictEnterys.json?dictTypeId="+encodeURI(encodeURI(dictTypeId));
			$.getJSON(url,function(data) {
				var listHtml = "";
				$.each(data.entryList, function(i,item) {
					listHtml += '<option  value="'+ item.dictId +'" ';
					listHtml += '>'+ item.dictName +'</option>';
				});
				$("#orgNoSelect").append(listHtml);
				$("#orgNoSelect").mobiscroll('show');
				closeLoading();
		   });
		    
		}
		
		function submitForm() {
			$.when($('#addTower').validator('isFormValid')).then(function() {
				var platformNum=$("#platformNum").val();
				var platformSpacing=$("#platformSpacing").val();
				var derrickNum=$("#derrickNum").val();
				var derrickUse=$("#derrickUse").val();
				var towerHeight=$("#towerHeight").val();
				if(platformNum>100 || platformSpacing>100 || derrickNum>100 || derrickUse>100 || towerHeight>200){
					return;
				}
				$.ajax({type: "POST",
			 		   url: $("#ctx").val()+"/station/saveOrUpdateTower.json",
			 		   data:{
			 			  	platformNum:platformNum,
							platformSpacing:platformSpacing,
							derrickNum:derrickNum,
							derrickUse:derrickUse,
							towerHeight:towerHeight,
							towerType:$("#towerType").val(),
							towerId:$("#towerId").val(),
							towerTypeDetail:$("#towerTypeDetail").val(),
					   	 },
			 		   error: function (msg) {
			 			   //alert(JSON.stringify(msg));
			     		},
		    		   success: function (json) {
			     			$("#replaceResult").html(json.res);
							$("#finish-confirm").modal(
							{
								relatedTarget : this,
								onConfirm : function(options) {
									window.location.href="../quarterInspection/toContinueTask?selectId=1";
								},
								onCancel : function() {
								}
							});
			     		},
			     		beforeSend:function() {
						},  
					    complete:function(data) {  
					    }
		 		});
			}, function() {
			});
	    }
		
		$('#floor').click(function(){
			$("#towerType").val("地面塔");
		    getOrgNoData('地面塔');
	         
	    });
		
		$('#building').click(function(){
			$("#towerType").val("楼面塔");
		    getOrgNoData('楼面塔');
	         
	    });
		
		$('#orgNoSelect').mobiscroll().select({
			theme: 'android-ics',
	        width: '300',
	        display: 'bottom',
	        mode: 'scroller',
	        headerText: '铁塔类型',
	        setText:'确定',
	        cancelText:'取消',
	        onSelect: function(valueText, inst){
				$("#towerTypeDetail1").text($("#orgNoSelect").find("option:selected").text());
				$("#towerTypeDetail").val($("#orgNoSelect").find("option:selected").text());
	    	}
	    });
	
	</script>
</html>