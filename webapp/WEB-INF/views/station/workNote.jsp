<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<style type="text/css">    
	.lineOverflow {         
		width: 100%;        
		border:#000 solid 1px;        
		text-overflow: ellipsis;        
		white-space: nowrap;/*禁止自动换行*/        
		overflow: hidden;    
	}
</style>    
<div id="noteDeatil">
	<div style="height: 120px;">
		<c:if test="${workNoteList == null || workNoteList == '[]'}">
			<div class="am-cf" style="text-align: center;margin-top: 60px">
				没有工作备忘，请添加工作备忘
			</div>
		</c:if>
		<c:forEach items="${workNoteList}" var="obj">
			<table class="am-table app-tb" onclick="window.location.href='${ctx}/station/goWorkNoteDetail?recordId=${obj.recordId}'"  style="table-layout:fixed;">
				<tr>
					<td class='lineOverflow' style="text-align: left; padding-left:4px; padding-bottom:0px;margin:0px; border: 0px;" colspan="2">
						<font size="3">${obj.title}</font><br/>
					</td>
					
					<td style="width:80px;vertical-align: middle;padding:4px; margin:0px; border: 0px;" rowspan="3">
						<c:if test="${obj.photoNum eq 0}">
							<img src="${ctx}/assets/i/noPhoto.png" style="width:80px;height: 80px; position: relative;"/>
						</c:if>
						<c:if test="${obj.photoNum ne 0}">
							<img src="${obj.photoList[0].thumbLocation}" style="width:80px;height: 80px; position: relative;"/>
						</c:if>					
						<div style="width:30px; height:20px; background:black; text-align:center; middle;float: right;position: absolute;margin: -20px 0px -20px 50px;filter:alpha(Opacity=80);">
							<font color="white">${obj.photoNum}图</font>
						</div> 
					</td>
				</tr>
				
				<tr> 
					<td class='lineOverflow' style="text-align: left; padding-left:4px; padding-bottom:0px;margin:0px; border: 0px;" colspan="2">
						<font size="2" color="#8B8378">${obj.content}</font>
					</td>
				</tr>
				<tr> 
					<td class='lineOverflow' style="text-align: left;padding-left:4px; padding-top:2px;margin:0px; border: 0px;" colspan="2">
						<font size="1" color="#8B8970">${obj.createUserName}&nbsp;<fmt:formatDate value="${obj.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></font>
					</td>
				</tr>
			</table>
		</c:forEach>
		<div style="height:40px;"></div>
	</div>
	<div class="am-topbar am-topbar-fixed-bottom app-div-btn-tool" style="padding-top: 6px">
		<div class="am-u-sm-12">
			<button onclick="submitAddWorkNote();" type="button"
				class="am-btn am-btn-secondary am-radius am-btn-block">
				<i class="am-icon-pencil"></i><strong>添&nbsp;&nbsp;加</strong>
			</button>
		</div>
	</div>
</div>

<script type="text/javascript">	
	function submitAddWorkNote(){
		var url="${ctx}/station/goAddWorkNote?stationId=${acceptStation.stationId}";
		window.location.href=url;
	}
</script>