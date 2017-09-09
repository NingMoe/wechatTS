<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>

<!DOCTYPE html>
<html>
	<head>
	<title>基站信息</title>
	<style type="text/css">
		#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;}
	</style>
	<%@include file="../commons/head.jsp"%>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=D7bc6a051e5ac6d616f273528ac5616a"></script>
	<script type="text/javascript">
		var currentPage = 1;
		var pageCounts;
		var pageSize = 10;
		
		//选择基站后，临时存储变量
		var tempStationName = '';                 //基站名称
		var tempStationId = '';                   //基站ID
		var tempStationNo = '';                   //基站编号
		var tempStationAddress = '';              //基站站址
		var tempRegionalManager = '';             //区域经理
		var tempManagerPhone = '';                //经理联系方式
		var tempMobileOperator = '';              //运营商
		var tempHaveBattery = '';                 //是否有蓄电池
		var tempLoadCurrent = '';                 //负载电流
		var tempRoomStructure = '';               //机房结构
		var tempRoomId = '';                      //机房ID
		var tempTowerId = '';                     //铁塔ID
		var tempLongitued = '';                   //经度
		var tempLatitude = '';                    //纬度
		var tempProvinceId = '';                  //省
		var tempCityId = '';                      //市
		var tempCountyId = '';                    //县
		//根据基站名称查询基站列表（ajax请求）
		function searchStation(type) {
			var staName = $("#staName").val().trim();
			if(staName == null || staName == '' || staName == 'null') {
				$("#tab").empty();
				$("#loadMore").hide();
				$("#result").hide();
				alert('基站名称不能为空');
				$("#search").attr('disabled',false); 
				return;
			}
			//showLoading();
			//$("#tab").empty();
			$("#confirm").attr('disabled',true); 
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
				url : "${ctx}/checkHiddenTrouble/searchStation.json",
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
						var roomInfos = data.roomInfos;
						var towerInfos = data.towerInfos;
						pageCounts = Math.ceil(totalRecords/pageSize);
						geneTBody($("#tab"));
						if(data.stationList.length < 10 || pageCounts == currentPage) {
							$("#loadMore").hide();
						} else {
							$("#loadMore").show();
						}
						for(var i = 0; i < stationList.length; i++) {
							var stationName = '';
							var stationId = '';
							var stationNo = '';
							var stationAddress = '';
							var regionalManager = '';
							var managerPhone = '';
							var mobileOperator = '';
							var haveBattery = '';
							var loadCurrent = '';
							var roomStructure = '';
							var roomId = '';
							var towerId = '';
							var longitude = '';
							var latitude = '';
							var provinceId = '';
							var cityId = '';
							var countyId = '';
							
							stationName = stationList[i].stationName;
							stationId = stationList[i].stationId;
							stationNo = stationList[i].stationNo;
							stationAddress = stationList[i].stationAddress;
							regionalManager = stationList[i].regionalManager;
							managerPhone = stationList[i].managerPhone;
							mobileOperator = stationList[i].mobileOperator;
							haveBattery = stationList[i].haveBattery;
							loadCurrent = stationList[i].loadCurrent;
							roomStructure = roomInfos[i].roomStructure;
							roomId = roomInfos[i].roomId;
							towerId = towerInfos[i].towerId;
							longitude = stationList[i].longitude;
							latitude = stationList[i].latitude;
							provinceId = stationList[i].provinceId;
							cityId = stationList[i].cityId;
							countyId = stationList[i].countyId;
							var detailAddress = getDetailAddress(provinceId, cityId, countyId, stationAddress);
							
							$("#tab").append(
							"<tr id='" + stationId + "' stationName='" + stationName + "' stationNo='" + stationNo + "' stationAddress='" + stationAddress + 
									"' regionalManager='" + regionalManager + "' managerPhone='" + managerPhone + "' mobileOperator='" + mobileOperator + 
									"' haveBattery='" + haveBattery + "' loadCurrent='" + loadCurrent + "' roomStructure='" + roomStructure + 
									"' roomId='" + roomId + "' towerId='" + towerId + "' longitude='" + longitude + "' latitude='" + latitude + 
									"' provinceId='" + provinceId + "' cityId='" + cityId + "' countyId='" + countyId + "'>"
								//+"<td><input type='radio' name='stationChecked'/></td>"
                              	+"<td style='white-space: nowrap;overflow: hidden;text-overflow: ellipsis;'><font size='3'>" + stationName + "</font>" + '<br/>' 
										+"<font color='#8B8970' size='2'>" + detailAddress + "</font></td>"
                             	//+"<td>" + staNo +"</td>"
                         	+"</tr>");            
						}
						
						
						
						//initPageButton(currentPage, pageCounts);
						//initPage(totalRecords, currentPage, pageCounts);
						//$("#grid").show();
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
							"<tr>" + 
								//"<th style='vertical-align: middle; width: 10%'>&nbsp;</th>" + 
								//"<th style='vertical-align: middle; width: 100%'>基站名称：</th>" + 
								//"<th style='vertical-align: middle; width: 40%'>基站编号：</th>" + 
							"</tr>" + 
						"</tbody>";
			obj.append(tbody);
		}
		
		function geneSearchResult(staName, staNum) {
			var searchResult = "“<font color='red'>"+staName+"</font>”&nbsp;&nbsp;基站名称筛选"
				+ "<font color='#8B8970' size='2'>(共<font color='red'>"+staNum+"</font>个基站)</font>";
			$("#searchResult").html(searchResult);
		}
		
		//初始化基站列表的页
		function initPage(totalRecords, currentPage, pageCounts) {
			$("#currPage").html("第" + currentPage +"/"+ pageCounts + "页");
			//$("#paSize").html("每页" + pageSize + "条");
			$("#total").html("共" + totalRecords + "条");
		}
		//初始化分页的按钮是否可用
		function initPageButton(currentPage, pageCounts) {
			currentPage = parseInt(currentPage);
			pageCounts = parseInt(pageCounts);
			if(currentPage == 1) {
				$("#first").attr('disabled',true); 
				$("#prew").attr('disabled',true); 
				if(currentPage < pageCounts) {
					$("#next").attr('disabled',false); 
					$("#last").attr('disabled',false); 
				} else if(currentPage == pageCounts) {
					$("#next").attr('disabled',true); 
					$("#last").attr('disabled',true); 
				}
			} else if(currentPage > 1) {
				$("#first").attr('disabled',false); 
				$("#prew").attr('disabled',false); 
				if(currentPage < pageCounts) {
					$("#next").attr('disabled',false); 
					$("#last").attr('disabled',false); 
				} else if(currentPage == pageCounts) {
					$("#next").attr('disabled',true); 
					$("#last").attr('disabled',true); 
				}
			}
		}
		//选择某个基站后，给页面中的元素赋值
		function initStationInfo() {
			showLoading();
			clearStationInfo();
			//alert(tempStationName + ',' + tempStationId + ',' + tempStationNo + ',' + tempStationAddress + ',' + tempRegionalManager
			//		 + ',' + tempManagerPhone + ',' + tempMobileOperator + ',' + tempHaveBattery + ',' + tempLoadCurrent);
			if(tempStationId != null && tempStationId != '' && tempStationId != 'null')
				$("#stationId").val(tempStationId);
			if(tempStationNo != null && tempStationNo != '' && tempStationNo != 'null')
				$("#stationNo").val(tempStationNo);
			if(tempStationName != null && tempStationName != '' && tempStationName != 'null')
				$("#stationName").val(tempStationName);
			if(tempStationAddress != null && tempStationAddress != '' && tempStationAddress != 'null')
				$("#stationAddress").val(tempStationAddress);
			if(tempRegionalManager != null && tempRegionalManager != '' && tempRegionalManager != 'null')
				$("#regionalManager").val(tempRegionalManager);
			if(tempManagerPhone != null && tempManagerPhone != '' && tempManagerPhone != 'null')
				$("#managerPhone").val(tempManagerPhone);
			if(tempLoadCurrent != null && tempLoadCurrent != '' && tempLoadCurrent != 'null')
				$("#loadCurrent").val(tempLoadCurrent);
			if(tempHaveBattery != null && tempHaveBattery != '' && tempHaveBattery != 'null')
				$("input[name='haveBattery'][value="+tempHaveBattery+"]")[0].checked = "checked";
			if(tempRoomStructure != null && tempRoomStructure != '' && tempRoomStructure != 'null')
				$("input[name='roomStructure'][value="+tempRoomStructure+"]")[0].checked = "checked";
			if(tempRoomId != null && tempRoomId != '' && tempRoomId != 'null')
				$("#roomId").val(tempRoomId);
			if(tempTowerId != null && tempTowerId != '' && tempTowerId != 'null')
				$("#towerId").val(tempTowerId);
			if(tempLongitude != null && tempLongitude != '' && tempLongitude != 'null') {
				$("#longitude").html(Number(tempLongitude).toFixed(2));
				$("#hlongitude").val(tempLongitude);
			}
				
			if(tempLatitude != null && tempLatitude != '' && tempLatitude != 'null') {
				$("#latitude").html(Number(tempLatitude).toFixed(2));
				$("#hlatitude").val(tempLatitude);
			}
			
			$("#selectAddbtn").removeAttr("onclick");
			$("#selectAddbtnr").addClass("am-hide");
			$("#selectAddStr").addClass("am-hide");
			
			if(tempProvinceId != null && tempProvinceId != '' && tempProvinceId != 'null')
				$("#provinceId").val(tempProvinceId);
			if(tempCityId != null && tempCityId != '' && tempCityId != 'null')
				$("#cityId").val(tempCityId);
			if(tempCountyId != null && tempCountyId != '' && tempCountyId != 'null')
				$("#countyId").val(tempCountyId);
				
			var regin = "";
			if(tempProvinceId == tempCityId){
				regin = tempCityId + "/" + tempCountyId;
			}else {
				regin = tempProvinceId + "/" + tempCityId + "/" + tempCountyId;
			}
			$("#regin").text(regin);	
			if(tempMobileOperator != null && tempMobileOperator != '' && tempMobileOperator != 'null') {
				var mobileOperator = tempMobileOperator.split(',');
				for(var i = 0; i < mobileOperator.length; i++) {
					$("input[name='mobileOperator'][value="+mobileOperator[i]+"]")[0].checked = "checked";
					//alert($("input[name='mobileOperator'][value="+mobileOperator[i]+"]").attr("checked"));
				}
			}
			getRoomPhoto();
			
			$('#submitPhotoPopup').modal('close');
		}
		//清空上次选择的基站元素的值
		function clearStationInfo() {
			
			$("#roomId").val('');
			$("#towerId").val('');
			$("#stationId").val('');
			$("#stationNo").val('');
			$("#stationName").val('');
			$("#stationAddress").val('');
			$("#regionalManager").val('');
			$("#managerPhone").val('');
			$("#loadCurrent").val('');
			$("#longitude").html('');
			$("#latitude").html('');
			$("#hlongitude").val('');
			$("#hlatitude").val('');
			
			$("#provinceId").val('');
			$("#cityId").val('');
			$("#countyId").val('');
			$("#regin").text('');	
			$("input[name='haveBattery'][value=是]")[0].checked = "checked";
			$("input[name='haveBattery'][value=否]").removeAttr("checked");
			
			$("input[name='roomStructure'][value=砖混]")[0].checked = "checked";
			$("input[name='roomStructure'][value=彩钢]").removeAttr("checked"); 
			$("input[name='roomStructure'][value=户外柜]").removeAttr("checked"); 

			$("input[name='mobileOperator'][value=移动]").removeAttr("checked"); 
			$("input[name='mobileOperator'][value=电信]").removeAttr("checked"); 
			$("input[name='mobileOperator'][value=联通]").removeAttr("checked"); 
			
			$("#roomImg").prevAll().remove();
		}
		//清空临时存储变量的值
		function clearTempStationInfo() {
			tempStationName = '';
			tempStationId = '';
			tempStationNo = '';
			tempStationAddress = '';
			tempRegionalManager = '';
			tempManagerPhone = '';
			tempMobileOperator = '';
			tempHaveBattery = '';
			tempLoadCurrent = '';
			tempRoomStructure = '';
			tempRoomId = '';
			tempTowerId = '';
			tempLongitude = '';
			tempLatitude = '';
			tempProvinceId = '';
			tempCityId = '';
			tempCountyId = '';
		}
		
		//弹框方法
		function inSure(){//点击确定按钮
			$('#submitPhotoPopup').modal('close');
		}
		
		//弹框方法
		function closeSelectLocation(){//点击确定按钮
			$('#selectLocation').modal('close');
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
				clearTempStationInfo();
				//获取基站的主键ID
				tempStationName = $curObj.attr("stationName");
				tempStationId = $curObj.attr("id");
				tempStationNo = $curObj.attr("stationNo");
				tempStationAddress = $curObj.attr("stationAddress");
				tempRegionalManager = $curObj.attr("regionalManager");
				tempManagerPhone = $curObj.attr("managerPhone");
				tempMobileOperator = $curObj.attr("mobileOperator");
				tempHaveBattery = $curObj.attr("haveBattery");
				tempLoadCurrent = $curObj.attr("loadCurrent");
				tempRoomStructure = $curObj.attr("roomStructure");
				tempRoomId = $curObj.attr("roomId");
				tempTowerId = $curObj.attr("towerId");
				tempLongitude = $curObj.attr("longitude");
				tempLatitude = $curObj.attr("latitude");
				tempProvinceId = $curObj.attr("provinceId");
				tempCityId = $curObj.attr("cityId");
				tempCountyId = $curObj.attr("countyId");
				//alert(tempStationName + ',' + tempStationId + ',' + tempStationNo + ',' + tempStationAddress + ',' + tempRegionalManager
				//	 + ',' + tempManagerPhone + ',' + tempMobileOperator + ',' + tempHaveBattery + ',' + tempLoadCurrent);
				curObj.style.background='#ffdead';
				//var ra = curObj.getElementsByTagName("input");
				//ra[0].checked = true;
				$("#confirm").removeAttr('disabled'); 
		   }
		}
		//点击下一步的逻辑
		function controlStation() {
			$("#addStation").attr("action", "${ctx}/checkHiddenTrouble/add").submit();
		}
		//点击搜索基站，弹出的div页面
		function searchStationList() {
		  	$("#staName").val("");
		  	$("#tab").empty();
		  	$("#result").hide();
		  	$("#loadMore").hide();
		  	//$("#grid").hide();
		  	$("#confirm").attr('disabled',true); 
		    $('#submitPhotoPopup').modal({
		      relatedTarget: this,
		      onConfirm: function(e) {
		        alert('你输入的是：' + e.data || '');
		      },
		      onCancel: function(e) {
		        alert('不想说!');
		      }
		    });
		}
		//点击选择地址，弹出的div页面
		function selectLocation() {
		    $('#selectLocation').modal({
		      relatedTarget: this,
		      onConfirm: function(e) {
		        alert('你输入的是：' + e.data || '');
		      },
		      onCancel: function(e) {
		        alert('不想说!');
		      }
		    });
		}
		var mapNotInit=true;
		function setCenterIconOnMap(){
			 if(mapNotInit){
				$("#selectPointFromMap").attr("src","${ctx }/checkHiddenTrouble/pointFromMap");
				mapNotInit=false;
			}
		}
		
		function getLocationDetailAddress(longitude, latitude,addressComponent) {
			
			$("#provinceId").val(addressComponent.province);	
			$("#cityId").val(addressComponent.city);	
			$("#countyId").val(addressComponent.district);	
			$("#hlongitude").val(longitude);
			$("#hlatitude").val(latitude);
			$("#longitude").html(longitude.toFixed(2));	
			$("#latitude").html(latitude.toFixed(2));
			
			var street = addressComponent.street;
			var street_number = addressComponent.streetNumber;
			var address =  street + ' ' + street_number;
			
			$("#stationAddress").val(address);
			$("#selectedAdd").html(address);
			
			var regin = "";
			if(addressComponent.province == addressComponent.city){
				regin = addressComponent.city + "/" + addressComponent.district;
			}else {
				regin = addressComponent.province + "/" + addressComponent.city + "/" + addressComponent.district;
			}
			$("#regin").text(regin);	
		};
		
		//根据roomId查询机房图片（ajax请求）
		function getRoomPhoto() {
			var roomId = $("#roomId").val();
			$.ajax({
				type : "POST",
				async : false,
				url : "${ctx}/checkHiddenTrouble/getRoomPhoto.json",
				data : "roomId=" + roomId,
				success : function(data) {
					if (data.yhPhotoInfos) {
						var roomPhotoInfoList = data.yhPhotoInfos;
						for(var i = 0; i < roomPhotoInfoList.length; i++) {
							var roomPhotoInfo = roomPhotoInfoList[i];
							initPhoto(roomPhotoInfo.recordId, roomPhotoInfo.thumbLocation, roomPhotoInfo.fileLocation);
						}
					}
					closeLoading();
				}
			});
		}
		
		function emptyStation() {
			window.location.href="${ctx}/checkHiddenTrouble/goStationInfo?clear=clear";
		}
		
		function initPhoto(id, photoUrl,srcUrl){
		    var deleteBtn='<h3 class="am-gallery-title am-fr" onclick="deleteSelectPhotoServer(&apos;${ctx}&apos;,&apos;'+id+'&apos;,this)">删除  <i class="am-icon-trash-o am-icon-sm" style="margin-top: 6px;color:#dd514c;opacity:100;"></i></h3>';
		    $("#roomImg").before(
		   		'<li>'+
		   			'<div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">'+
		   				'<div style="height:72px;margin:2px;overflow:hidden;">'+
		   					'<img src="'+photoUrl+'" alt=" " style="border:none;" data-rel="'+srcUrl+'" />'+
		   				'</div>'+
		   				deleteBtn+
		   		    '</div>'+
		   		'</li>'
		    );//86931cb5-b525-46a4-a184-cc019271beac
		    $(".am-pureview-slider").on("click",function(){
				$(".am-icon-chevron-left[data-am-close='pureview']").triggerHandler("click");
			});
		};
		
	</script>
	</head>
		<body> 
			<form class="am-form" id="addStation" method="post"  data-am-validator enctype="multipart/form-data">
			<input type="hidden" id="stationId" name="stationId" value="${stationInfo.stationId}"/>
			<input type="hidden" id="roomId" name="roomId" value="${roomInfo.roomId}"/>
			<input type="hidden" id="towerId" name="towerId" value="${towerInfo.towerId}"/>
			<table class="am-table app-tb">
				<tbody>
					<tr>
						<th style="border-top: none;text-align: center;" colspan="2">
							<div class="am-g am-g-fixed">
							  <div class="am-u-sm-2">
							  	&nbsp;
							  </div>
							  <div class="am-u-sm-8">输入基站信息</div>
							  <div class="am-u-sm-2">
							  	&nbsp;
							  </div>
							</div>
						</th>
					</tr>
					<tr>
						<td style="text-align: center;" colspan="2">
							<div class="am-g am-g-fixed">
							  <div class="am-u-sm-6">
							  	<button type="button" class="am-btn am-btn-success am-radius am-btn-block" onclick="emptyStation();" >清空基站
							  		<i class="am-icon-trash"></i>
							  	</button>
							  </div>
							  <div class="am-u-sm-6">
							  	<button type="button" class="am-btn am-btn-success am-radius am-btn-block" onclick="searchStationList();" >搜索基站
							  		<i class="am-icon-search"></i>
							  	</button>
							  </div>
							</div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle; width: 33%">
							<span style="color: red">*</span>基站编码：
						</td>
						<td>
							<input id="stationNo" name="stationNo" type="text" id="doc-vld-name-2-0"
								placeholder="输入基站编码" value="${stationInfo.stationNo}" required />
							<span id='checkFalse' style="display: none; color: red;"><i
								class='am-icon-close'></i>
							</span><span id='nameTips'></span>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							<span style="color: red">*</span>基站名称：
						</td>
						<td>
							<input id="stationName" name="stationName" type="text" placeholder="输入基站名称" value="${stationInfo.stationName}" required />
							<span id='checkFalse' style="display: none; color: red;"><i class='am-icon-close'></i>
							</span><span id='nameTips'></span>
						</td>
					</tr>
					<tr id="selectAddbtn" onclick="selectLocation();">
						<td style="vertical-align: middle;">
							<span style="color: red">*</span>经纬度：
						</td>
						<td>
							<input type="hidden" id="hlongitude" name="longitude" value="${stationInfo.longitude}">
							<input type="hidden" id="hlatitude" name="latitude" value="${stationInfo.latitude}">
							<span id="longitude">${stationInfo.longitude}</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<span id="latitude">${stationInfo.latitude}</span>
							<span id="selectAddbtnr" class="am-icon-map-marker am-icon-sm am-fr" style="color:#07C62D;"></span>
							<span id="selectAddStr" class="am-fr">定位</span>
						</td>
					</tr>
					<tr hidden>
						<td style="vertical-align: middle;">
							所在省：
						</td>
						<td>
							<input type="text" id="provinceId" name="provinceId" value="${stationInfo.provinceId }"/>
						</td>
					</tr>
					<tr hidden>
						<td style="vertical-align: middle;">
							所在市：
						</td>
						<td>
							<input type="text" id="cityId" name="cityId" value="${stationInfo.cityId }"/>
						</td>
					</tr>
					<tr hidden>
						<td style="vertical-align: middle;">
							所在区/县：
						</td>
						<td>
							<input type="text" id="countyId" name="countyId" value="${stationInfo.countyId }"/>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							<span style="color: red">*</span>归属区域：		
						</td>
						<td style="vertical-align: middle;">
							<label id="regin" style="margin-bottom: 0;font-weight: 200;font-size: 1.6rem;">
								<c:if test="${stationInfo.provinceId != stationInfo.cityId}">${stationInfo.provinceId}/</c:if>${stationInfo.cityId}/${stationInfo.countyId}
							</label>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							<span style="color: red">*</span>站址：
						</td>
						<td>
							<input id="stationAddress" name="stationAddress" type="text" id="doc-vld-name-2-0" placeholder="输入站址" value="${stationInfo.stationAddress}" required />
							<span id='checkFalse' style="display: none; color: red;"><i class='am-icon-close'></i>
							</span><span id='nameTips'></span>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							<span style="color: red">*</span>区域经理：
						</td>
						<td>
							<input id="regionalManager" name="regionalManager" type="text" id="doc-vld-name-2-0" placeholder="输入区域经理" value="${stationInfo.regionalManager}" required />
							<span id='checkFalse' style="display: none; color: red;"><i class='am-icon-close'></i>
							</span><span id='nameTips'></span>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							<span style="color: red">*</span>区域经理<br/>联系方式：
						</td>
						<td>
							<input id="managerPhone" name="managerPhone" type="text" id="doc-vld-name-2-0" placeholder="输入区域经理联系方式" value="${stationInfo.managerPhone}" required />
							<span id='checkFalse' style="display: none; color: red;"><i class='am-icon-close'></i>
							</span><span id='nameTips'></span>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							<span style="color: red">*</span>负载电流（A）：
						</td>
						<td>
							<input id="loadCurrent" name="loadCurrent" type="text" id="doc-vld-name-2-0" placeholder="输入负载电流" value="${stationInfo.loadCurrent}" required />
							<span id='checkFalse' style="display: none; color: red;"><i class='am-icon-close'></i>
							</span><span id='nameTips'></span>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							<span style="color: red">*</span>机房结构： 
						</td>
						<td style="vertical-align: middle;">
						  <div class="am-form-group" style="height: auto;margin-bottom: 0">
						      <label class="am-radio-inline">
						        <input type="radio" value="砖混" id="hz" checked="checked" name="roomStructure" required> 砖混
							  </label>
						      <label class="am-radio-inline">
						        <input type="radio" value="彩钢" id="cg" name="roomStructure"> 彩钢
						      </label>
						      <label class="am-radio-inline">
						        <input type="radio" value="户外柜" id="hwg" name="roomStructure"> 户外柜
						      </label>
						  </div>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							<span style="color: red">*</span>运营商：
						</td>
						<td style="vertical-align: middle;">
							<div class="am-form-group" style="height: auto;margin-bottom: 0">
						      <label class="am-checkbox-inline">
						        <input type="checkbox" value="移动" id="yd" name="mobileOperator" minchecked="1" maxchecked="3" required>移动
						      </label>
						      <label class="am-checkbox-inline">
						        <input type="checkbox" value="电信" id="dx" name="mobileOperator">电信
						      </label>
						      <label class="am-checkbox-inline">
						        <input type="checkbox" value="联通" id="lt" name="mobileOperator">联通
						      </label>
						    </div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<p>机房外观照：<span id="progressNumber"></span></p>
							<ul data-am-widget="gallery" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
								<c:forEach items="${yhPhotoInfos}" var="item">
									<li>
							   			<div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
							   				<div style="height:72px;margin:2px;overflow:hidden;">
							   					<img src="${item.thumbLocation}" data-rel="${item.fileLocation}" alt=" " style="border:none;" />
							   				</div>
							   				<h3 class="am-gallery-title am-fr" onclick="deleteSelectPhotoServer('${ctx}','${item.recordId}',this)">删除  <i class="am-icon-trash-o am-icon-sm" style="margin-top: 6px;color:#dd514c;opacity:100;"></i></h3>
							   		    </div>
							   		</li>
								</c:forEach>
								<li id="roomImg" onclick="void(0);">
									 <div onclick="void(0)">
										<div class="am-form-group am-form-file">
											<button type="button" class="app-add-photo"></button>
											<input id="" type="file" name="file" onchange="selectPhoto(this,'roomImg','base64Imgstr','${ctx}')" capture="camera" >
										</div>
									</div>
								</li>
							</ul>
							<input type="hidden" id="base64Imgstr" name="base64Imgstr" class="localIds"/>
						</td>
					</tr>
					<tr>
						<td style="vertical-align: middle;">
							<span style="color: red">*</span>蓄电池：
						</td>
						<td style="vertical-align: middle;">
						 <div class="am-form-group" style="height: auto;margin-bottom: 0">
						      <label class="am-radio-inline">
						        <input type="radio" value="是" id="ypz" checked="checked" name="haveBattery">已配置
						      </label>
						      <label class="am-radio-inline">
						        <input type="radio" value="否" id="wpz" name="haveBattery">未配置
						      </label>
						  </div>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="app-div-btn-tool">
				<button class="am-btn am-btn-success am-radius am-btn-block" onclick="controlStation();">
					<strong>下一步</strong>&nbsp;<i class="am-icon-arrow-right"></i>
				</button>
			</div>
			<div>&nbsp;</div>
		</form>
		
		<div class="am-popup am-scrollable-vertical" id="submitPhotoPopup">
		  <div class="am-popup-inner">
			    <div class="am-popup-hd" style="z-index: 1;">
			    	<header class="am-topbar app-tabs-head">
						<div onclick="inSure();" style="float: left;">
							<span class="am-topbar-brand am-icon-angle-left"></span>
							<div class="am-topbar-brand  app-toolbar">搜索基站</div>
						</div>
						<div style="float: right;">
							<div class="am-topbar-brand  app-toolbar">
								<button id="confirm" onclick="initStationInfo();" class="am-btn am-btn-sm am-btn-success am-radius">确定</button>
							</div>
						</div>
					</header>
			    </div>
			    <div class="am-popup-bd" style="background: white;padding:0px;margin: 0px">
			    	<div style="height: 6px;">&nbsp;</div>
			      	<form class="am-form">
						<table class="am-table app-tb" style="padding:0px;margin: 0px">
							<tbody>
								<tr style="height: 40px;">
									<td style="vertical-align: middle; width: 80%;height:40px;">
										<input type="text" id="staName"
										placeholder="输入基站名称" style="height: 40px;font-size:20px;padding: 0px;"/>
									</td>
									<td style="vertical-align: middle; width: 20%"><!--
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
						<table id="tab" class="am-table app-tb" style="table-layout: fixed;">
						</table>
						<div onclick="searchStation('next');" id="loadMore" style="height:40px;display: none;" data-am-scrollspy>
					     	&nbsp;&nbsp;点击加载更多...
						</div>
					</div>
				</div>
			</div>
		</div>
			
		<div class="am-popup am-scrollable-vertical" id="selectLocation">
		  <div class="am-popup-inner">
		    <div class="am-popup-hd" style="z-index: 1;">
		    	<header onclick="closeSelectLocation();" class="am-topbar app-tabs-head">
					<div onclick="closeSelectLocation();" style="float: left;">
						<span class="am-topbar-brand am-icon-angle-left"></span>
						<div id="selectedAdd" class="am-topbar-brand  app-toolbar">选择地址</div>
					</div>
					<div style="float: right;">
						<div class="am-topbar-brand  app-toolbar">
							<button id="confirm" onclick="closeSelectLocation();" class="am-btn am-btn-sm am-btn-success am-radius">确定</button>
						</div>
					</div>
				</header>
		    </div>
		    <iframe id="selectPointFromMap" src="" width="100%" height="100%"></iframe>
		</div>
	</div>
	<div class="am-modal am-modal-loading" tabindex="-1" id="modal-loading">
	  <button type="button" class="am-btn am-radius am-modal-dialog" style="width:200px;background-color: black;filter:alpha(opacity:60);opacity:0.6;">
	    <div class="am-modal-hd" style="font-size: 14px;color: white;" id="modal-loading-msg">正在载入...</div>
	    <div class="am-modal-bd">
	      <span class="am-icon-spinner am-icon-spin"></span>
	    </div>
	  </button>
	</div>
	</body>

<script>
	$('#loadMore').on('inview.scrollspy.amui', function() {
    	searchStation('next');
  	});
	
	function getAddress(longitude, latitude) {
	   	var Point=new BMap.Point(longitude, latitude);
	   	var geoc = new BMap.Geocoder();    
	   	geoc.getLocation(Point, function(rs){
	   		getLocationDetailAddress(longitude, latitude,rs.addressComponents);
		}); 
	}
	var mobileOperators = '${stationInfo.mobileOperator}';
	var roomStructure = '${roomInfo.roomStructure}';
	var haveBattery = '${stationInfo.haveBattery}';
	var longitude = '${stationInfo.longitude}';
	var latitude = '${stationInfo.latitude}';
	
	window.onload=function(){
		if(mobileOperators != null && mobileOperators != '' && mobileOperators != 'null') {
			var mobileOperator = mobileOperators.split(',');
			for(var i = 0; i < mobileOperator.length; i++) {
				$("input[name='mobileOperator'][value="+mobileOperator[i]+"]")[0].checked = "checked";
			}
		}
		
		if(roomStructure != null && roomStructure != '' && roomStructure != 'null') {
			$("input[name='roomStructure'][value="+roomStructure+"]")[0].checked = "checked";
		}
		
		if(haveBattery != null && haveBattery != '' && haveBattery != 'null') {
			$("input[name='haveBattery'][value="+haveBattery+"]")[0].checked = "checked";
		}
		
		if(longitude != null && longitude != '' && longitude != 'null') {
			$("#longitude").html(Number(longitude).toFixed(2));
		}
		
		if(latitude != null && latitude != '' && latitude != 'null') {
			$("#latitude").html(Number(latitude).toFixed(2));
		}
		
	};
	!function(t) {
		var staId = $("#stationId").val();
		if(staId != null && staId != '' && staId != 'null') {
			return;
		}
		var geolocation = new BMap.Geolocation();
		geolocation.getCurrentPosition(function(r){
			if(this.getStatus() == BMAP_STATUS_SUCCESS){
				getAddress(r.point.lng, r.point.lat);
			}else {
				//modalAlert("未获取到位置");
			}
		},{enableHighAccuracy: true});
		
		$("ul[data-am-widget='gallery']").one("click",function(){//点击预览图片关闭预览
			$(".am-pureview-slider").on("click",function(){
				$(".am-icon-chevron-left[data-am-close='pureview']").triggerHandler("click");
			});
		});
		$('#selectLocation').on('opened.modal.amui', function(){
			setCenterIconOnMap();
		});
	}(window.jQuery || window.Zepto);
</script>
</html>