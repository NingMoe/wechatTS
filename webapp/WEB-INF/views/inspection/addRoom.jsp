<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<!DOCTYPE html>
<html manifest="${ctx}/assets/pageCache.appcache">
	<head>
	<title>机房信息</title>
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
				<c:if test="${roomBO == null || roomBO == '[]'}">
				新增机房
				</c:if>
				<c:if test="${roomBO != null}">
				修改机房
				</c:if>
			</div>
		</div>
		</header>
		<form class="am-form" id="addRoom" data-am-validator onsubmit="return false;">
			<input type="hidden" id="roomId" name="roomId"  value="${roomBO.roomId}" />
			<input type="hidden" id="roomType" name="roomType"  value="${roomBO.roomType}" />
			<input type="hidden" id="roomSize" name="roomSize"  value="${roomBO.roomeSize}" />
			<table class="am-table app-tb">
				<tbody>
					<tr>
						<td style="vertical-align: middle; width: 120px;">
							机房结构：
						</td>
						<td style="vertical-align: middle;">
							<czxk:select dictTypeId="room_structure" height="200" value="${roomBO.roomStructure }" id="roomStructure" name="roomStructure"/>
						</td>
						<td style="vertical-align: middle;"><span id="structure" style="color:red;folat:left;"></span></td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							机房尺寸(长*宽)：
						</td>
						<td style="vertical-align: middle;">
							<czxk:select dictTypeId="roome_size" height="200" value="${roomBO.roomeSize }" id="roomeSize" name="roomeSize" onchange="getRoomSize(this.value)"/>
						</td>
						<td style="vertical-align: middle;"><span id="size" style="color:red;folat:left;"></span></td>
					</tr>
					<tr id="length_and_width" style="display:none;">
						<td style="vertical-align: middle">
							长*宽：
						</td>
						<td style="vertical-align: middle">
						<div style="line-height:40px;height:30px;">
						<input type="text" id="length" name="length" style="width:90px;float:left;displsy:inline;" pattern="^[0-9]+\.?[0-9]{0,2}$" placeholder="输入数字"/><span style="float:left;">&nbsp;&nbsp;*&nbsp;&nbsp;</span>
						<input type="text" id="width" name="width" style="width:90px;float:left;displsy:inline;" pattern="^[0-9]+\.?[0-9]{0,2}$" placeholder="输入数字"/>
						</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle">
							机房面积(平米)：
						</td>
						<td style="vertical-align: middle;">
							<div class="am-input-group">
								<input type="text" id="area" class="am-form-field" name="area"  min="0" max="100"  placeholder="请输入机房面积" value="${roomBO.area }" pattern="^[0-9]+\.?[0-9]{0,2}$"/>
								<span class="am-input-group-label">平米</span>
							</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle">
							所在楼层：
						</td>
						<td style="vertical-align: middle;">
						<czxk:select dictTypeId="floor_high" height="200" value="${roomBO.floor}" id="floor" name="floor" />
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle">
							灭火器数量：
						</td>
						<td style="vertical-align: middle;">
							<div class="am-input-group">
								<input type="text" id="extinguishersNum" class="am-form-field" name="extinguishersNum"  min="0" max="100"  placeholder="请输入灭火器数量" value="${roomBO.extinguishersNum }" pattern="^[0-9]*[1-9][0-9]*$"/>
								<span class="am-input-group-label">个</span>
							</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle">
							灭火器质保日期：
						</td>
						<td style="vertical-align: middle">	
						<input type="text" class="am-form-field" value="<fmt:formatDate value="${roomBO.extinguishersWarranty }" pattern="yyyy-MM-dd" />" id="extinguishersWarranty"/>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle">
							机房外围：
						</td>
						<td style="vertical-align: middle;" >
							<czxk:select dictTypeId="have_walls"  height="200" value="${roomBO.haveWalls}" id="haveWalls" name="haveWalls" />
							
						</td>
						<td style="vertical-align: middle;"><span id="wall" style="color:red;folat:left;"></span></td>
					</tr>
				</tbody>
			</table>
			<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
				<div class="am-u-sm-12">
					<button class="am-btn am-btn-success am-radius am-btn-block" onclick="submitForm();">
						<c:if test="${roomBO == null || roomBO == '[]'}">
						<strong>添加</strong>
						</c:if>
						<c:if test="${roomBO != null}">
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
			$.when($('#addRoom').validator('isFormValid')).then(function(result) {
					var roomType=$("#roomType").val();
					var roomId=$("#roomId").val();
					var roomStructure=$("#roomStructure").val();
					var roomeSize=$("#roomeSize").val();
					var length=$("#length").val();
					var width=$("#width").val();
					if(roomeSize=='规则'){
						roomeSize=length+'*'+width;
					}
					var area=$("#area").val();
					var floor=$("#floor").val();
					var extinguishersNum=$("#extinguishersNum").val();
					var extinguishersWarranty=$("#extinguishersWarranty").val();
					var haveWalls=$("#haveWalls").val();
					if(haveWalls=="" && roomStructure=="" && roomeSize==""){
						$("#structure").html("必填");
						$("#size").html("必填");
						$("#wall").html("必填");
						return false;
					}else if(roomStructure=="" && haveWalls!="" && roomeSize!=""){
						$("#size").html("");
						$("#wall").html("");
						$("#structure").html("必填");
						return false;
					}else if(roomStructure!="" && haveWalls!="" && roomeSize==""){
						$("#size").html("必填");
						$("#wall").html("");
						$("#structure").html("");
						return false;
					}else if(roomStructure!="" && haveWalls=="" && roomeSize!=""){
						$("#wall").html("必填");
						$("#structure").html("");
						$("#size").html("");
						return false;
					}else if(roomStructure=="" && haveWalls=="" && roomeSize!=""){
						$("#wall").html("必填");
						$("#structure").html("必填");
						$("#size").html("");
						return false;
					}else if(roomStructure!="" && haveWalls=="" && roomeSize==""){
						$("#wall").html("必填");
						$("#size").html("必填");
						$("#structure").html("");
						return false;
					}else if(roomStructure=="" && haveWalls!="" && roomeSize==""){
						$("#size").html("必填");
						$("#structure").html("必填");
						$("#wall").html("");
						return false;
					}
					if(result){
						$.ajax({type: "POST",
					 		   url: $("#ctx").val()+"/station/saveOrUpdateRoom.json",
					 		   data:{
					 			  	roomStructure:roomStructure,
					 			  	roomeSize:roomeSize,
					 			  	area:area,
					 			 	floor:floor,
					 			 	extinguishersNum:extinguishersNum,
					 			 	extinguishersWarranty:extinguishersWarranty,
					 			 	haveWalls:haveWalls,
					 			 	roomType:roomType,
									roomId:roomId,
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
											window.location.href="../quarterInspection/toContinueTask?selectId=2";
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
					}
						
					}, function() {
			});
	    }
		
		//$('#orgNoSelect').mobiscroll().select({
			//theme: 'android-ics',
	        //width: '300',
	       // display: 'bottom',
	       // mode: 'scroller',
	       // headerText: '机房类型',
	       // setText:'确定',
	       // cancelText:'取消',
	       // onSelect: function(valueText, inst){
				//$("#towerTypeDetail1").text($("#orgNoSelect").find("option:selected").text());
				//$("#towerTypeDetail").val($("#orgNoSelect").find("option:selected").text());
	    	//}
	    //});
	!function(t) {
		initMobiscroll("#extinguishersWarranty");
	}(window.jQuery || window.Zepto);
	
	
	/* $(function(){
		$('#roomeSize').change(function() {
            if (this.value == '规则') {
                $('#length_and_width').show();
            } else {
                $('#length_and_width').hide();
            }
        });
		
	}); */
	function getRoomeSize(){
		var val=$("#roomeSize").val();
		if (val == '规则') {
            $('#length_and_width').show();
            var roomSize=$("#roomSize").val();
            if(roomSize != "" && roomSize != null){
            	 var size=roomSize.split("*");
                 $("#length").val(size[0]);
                 $("#width").val(size[1]);
            }
        } else {
            $('#length_and_width').hide();
        }
	}
	window.onload=getRoomeSize();
	
	function getRoomSize(val){
		if (val == '规则') {
            $('#length_and_width').show();
            var roomSize=$("#roomSize").val();
            if(roomSize != "" && roomSize != null){
            	 var size=roomSize.split("*");
                 $("#length").val(size[0]);
                 $("#width").val(size[1]);
            }
        } else {
            $('#length_and_width').hide();
        }
	}
</script>

</html>