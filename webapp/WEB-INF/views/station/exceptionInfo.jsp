<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:if test="${deviceInspectRecords == '[]'}">
	<div class="am-cf" style="text-align: center;margin-top: 60px">
		暂无异常
	</div>
</c:if>
<c:if test="${deviceInspectRecords != '[]'}">
	<c:forEach items="${deviceInspectRecords}" var="obj" varStatus="status">
		<table class="am-table app-tb">
			<tr>
				<th style="vertical-align: middle;" colspan="2">
					${obj.deviceName}
				</th>
			</tr>
			<tr>
				<td style="vertical-align: middle;width:120px;">异常原因：</td>
				<td style="vertical-align: middle;">
					${obj.abnormalCode}
				</td>
			</tr>
			<tr>
				<c:if test="${obj.photoList != null && obj.photoList != '[]'}">
					<td colspan="2">
					<p>异常照片：</p>
					<ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
						<c:forEach items="${obj.photoList}" var="item">
						    <li style="padding: 0 0 0 0" id="${item.photoId}">
						    <div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">
						     <div style="height:72px;margin-bottom:2px;overflow:hidden;">
						       <img src="${item.thumbLocation}" <c:if test="${item.status eq '待上传'}"> data-localId="${item.localId}" </c:if>  alt="" data-rel="${item.fileLocation}"/>
						       </div>
						    </div>
						</li>
						</c:forEach>
					</ul>
					<input  class="localIds" type="hidden" id="deviceImgs" name="deviceImgs"  value="" size="100"/>
					</td>
				</c:if>
				<c:if test="${obj.photoList == null || obj.photoList == '[]'}">
					<td style="vertical-align: middle;">异常照片：</td>
					<td style="vertical-align: middle;">无</td>
				</c:if>
			</tr>
			<tr>
				<td style="vertical-align: middle;width:120px;">备注：</td>
				<td style="vertical-align: middle;">
					${obj.remark}
				</td>
			</tr>
		</table>
	</c:forEach>
</c:if>
<script type="text/javascript">	
	echo.init({
		offset : 100,
		throttle : 250,
		unload : false
	});
	
	!function(t) {
    	$("ul[data-am-widget='gallery']").one("click",function(){//点击预览图片关闭预览
			$(".am-pureview-slider").each(function(i){
				$(this).on("click",function(){
					$(".am-icon-chevron-left[data-am-close='pureview']").eq(i).triggerHandler("click");
				});
			});
		});
    	//showImagesH5();
	}(window.jQuery || window.Zepto);
</script>
