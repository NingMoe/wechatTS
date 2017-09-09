<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:if test="${devicesList == null || devicesList == '[]'}">
	<div class="am-cf" style="text-align: center;margin-top: 60px">
		没有设备，请先去添加设备
	</div>
</c:if>
<c:if test="${devicesList != null}">
	<c:forEach items="${devicesList}" var="baseDevice" varStatus="status">
		<c:if test="${baseDevice.deviceType eq 'switch_power'}">
			<table class="am-table app-tb"> 
				<tr onclick="viewDeviceDetail('${baseDevice.deviceId}');">
					<td style="width: 100px;vertical-align: middle;">设备类型：</td>
					<td style="vertical-align: middle;">
					  	${baseDevice.deviceTypeName}
					  	<button class="am-btn am-radius am-fr" style="background: #EF7321;color: #fff;height: 30px;width: 80px;padding: 0px;">
					  		<font size="2">
					  			<i class="am-icon-eye"></i>&nbsp;历史巡检
					  		</font>
					  	</button>
					</td>
				</tr>
				<tr>
					<c:if test="${baseDevice.photoList != null && baseDevice.photoList != '[]'}">
						<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
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
					<c:if test="${baseDevice.photoList == null || baseDevice.photoList == '[]'}">
						<td style="vertical-align: middle;">外观照片：</td>
						<td style="vertical-align: middle;">无</td>
					</c:if>
				</tr>
				<tr>
					<td style="vertical-align: middle;">品牌：</td>
					<td style="vertical-align: middle;">
						${baseDevice.brand}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">型号：</td>
					<td style="vertical-align: middle;">
						${baseDevice.model}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">模块数：</td>
					<td style="vertical-align: middle;">
						${baseDevice.powerModNum}
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${baseDevice.deviceType eq 'battery'}">
			<table class="am-table app-tb"> 
				<tr onclick="viewDeviceDetail('${baseDevice.deviceId}');">
					<td style="width: 100px;vertical-align: middle;">设备类型：</td>
					<td style="vertical-align: middle;">
					  	${baseDevice.deviceTypeName}
					  	<button class="am-btn am-radius am-fr" style="background: #EF7321;color: #fff;height: 30px;width: 80px;padding: 0px;">
					  		<font size="2">
					  			<i class="am-icon-eye"></i>&nbsp;历史巡检
					  		</font>
					  	</button>
					</td>
				</tr>
				<tr>
					<c:if test="${baseDevice.photoList != null && baseDevice.photoList != '[]'}">
						<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
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
					<c:if test="${baseDevice.photoList == null || baseDevice.photoList == '[]'}">
						<td style="vertical-align: middle;">外观照片：</td>
						<td style="vertical-align: middle;">无</td>
					</c:if>
				</tr>
				<tr>
					<td style="vertical-align: middle;">品牌：</td>
					<td style="vertical-align: middle;">
						${baseDevice.batteryBrand}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">型号：</td>
					<td style="vertical-align: middle;">
						${baseDevice.batteryModel}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">组数：</td>
					<td style="vertical-align: middle;">
						组${baseDevice.groupNum}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">生产日期：</td>
					<td style="vertical-align: middle;">
						<fmt:formatDate value="${baseDevice.enterNetDate}" pattern="yyyy-MM-dd" />
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${baseDevice.deviceType eq 'air_conditioning'}">
			<table class="am-table app-tb"> 
				<tr onclick="viewDeviceDetail('${baseDevice.deviceId}');">
					<td style="width: 100px;vertical-align: middle;">设备类型：</td>
					<td style="vertical-align: middle;">
					  	${baseDevice.deviceTypeName}
					  	<button class="am-btn am-radius am-fr" style="background: #EF7321;color: #fff;height: 30px;width: 80px;padding: 0px;">
					  		<font size="2">
					  			<i class="am-icon-eye"></i>&nbsp;历史巡检
					  		</font>
					  	</button>
					</td>
				</tr>
				<tr>
					<c:if test="${baseDevice.photoList != null && baseDevice.photoList != '[]'}">
						<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
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
					<c:if test="${baseDevice.photoList == null || baseDevice.photoList == '[]'}">
						<td style="vertical-align: middle;">外观照片：</td>
						<td style="vertical-align: middle;">无</td>
					</c:if>
				</tr>
				<tr>
					<td style="vertical-align: middle;">品牌：</td>
					<td style="vertical-align: middle;">
						${baseDevice.airBrand}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">型号：</td>
					<td style="vertical-align: middle;">
						${baseDevice.airModel}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">生产日期：</td>
					<td style="vertical-align: middle;">
						<fmt:formatDate value="${baseDevice.createTime}" pattern="yyyy-MM-dd" />
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${baseDevice.deviceType eq 'ac_distribution'}">
			<table class="am-table app-tb"> 
				<tr onclick="viewDeviceDetail('${baseDevice.deviceId}');">
					<td style="width: 100px;vertical-align: middle;">设备类型：</td>
					<td style="vertical-align: middle;">
					  	${baseDevice.deviceTypeName}
					  	<button class="am-btn am-radius am-fr" style="background: #EF7321;color: #fff;height: 30px;width: 80px;padding: 0px;">
					  		<font size="2">
					  			<i class="am-icon-eye"></i>&nbsp;历史巡检
					  		</font>
					  	</button>
					</td>
				</tr>
				<tr>
					<c:if test="${baseDevice.photoList != null && baseDevice.photoList != '[]'}">
						<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
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
					<c:if test="${baseDevice.photoList == null || baseDevice.photoList == '[]'}">
						<td style="vertical-align: middle;">外观照片：</td>
						<td style="vertical-align: middle;">无</td>
					</c:if>
				</tr>
				<tr>
					<td>总容量：</td>
					<td>
						<div class="am-input-group">
							${baseDevice.acCapacity}A
						</div>
					</td>
				</tr>
				<tr>
					<td>生产厂商：</td>
					<td>
						${baseDevice.acManufacturer}
					</td>
				</tr>
				<tr>
					<td>是否预留移动油机输入接口：</td>
					<td>
						<czxk:showSelect dictTypeId="enterInterface" value="${baseDevice.haveGeneratorInterface}"/>
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${baseDevice.deviceType eq 'dc_distribution'}">
			<table class="am-table app-tb"> 
				<tr onclick="viewDeviceDetail('${baseDevice.deviceId}');">
					<td style="width: 100px;vertical-align: middle;">设备类型：</td>
					<td style="vertical-align: middle;">
					  	${baseDevice.deviceTypeName}
					  	<button class="am-btn am-radius am-fr" style="background: #EF7321;color: #fff;height: 30px;width: 80px;padding: 0px;">
					  		<font size="2">
					  			<i class="am-icon-eye"></i>&nbsp;历史巡检
					  		</font>
					  	</button>
					</td>
				</tr>
				<tr>
					<c:if test="${baseDevice.photoList != null && baseDevice.photoList != '[]'}">
						<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
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
					<c:if test="${baseDevice.photoList == null || baseDevice.photoList == '[]'}">
						<td style="vertical-align: middle;">外观照片：</td>
						<td style="vertical-align: middle;">无</td>
					</c:if>
				</tr>
				<tr>
					<td>品牌：</td>
					<td>
					 	${baseDevice.boxBrand}
					</td>
				</tr>
				<tr>
					<td>总容量：</td>
					<td>
					 	${baseDevice.dcCapacity}
					</td>
				</tr>
				<tr>
					<td>熔丝已经使用数量：</td>
					<td>
						<div class="am-input-group">
							${baseDevice.fuseUsedNum}
							<span class="am-input-group-label">个</span>
						</div>
					</td>
				</tr>
				<tr>
					<td>未使用熔丝数量：</td>
					<td>
						<div class="am-input-group">
							${baseDevice.fuseNotUsedNum}
							<span class="am-input-group-label">个</span>
						</div>
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${baseDevice.deviceType eq 'transformer'}">
			<table class="am-table app-tb"> 
				<tr onclick="viewDeviceDetail('${baseDevice.deviceId}');">
					<td style="width: 100px;vertical-align: middle;">设备类型：</td>
					<td style="vertical-align: middle;">
					  	${baseDevice.deviceTypeName}
					  	<button class="am-btn am-radius am-fr" style="background: #EF7321;color: #fff;height: 30px;width: 80px;padding: 0px;">
					  		<font size="2">
					  			<i class="am-icon-eye"></i>&nbsp;历史巡检
					  		</font>
					  	</button>
					</td>
				</tr>
				<tr>
					<c:if test="${baseDevice.photoList != null && baseDevice.photoList != '[]'}">
						<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
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
					<c:if test="${baseDevice.photoList == null || baseDevice.photoList == '[]'}">
						<td style="vertical-align: middle;">外观照片：</td>
						<td style="vertical-align: middle;">无</td>
					</c:if>
				</tr>
				<tr>
					<td>类型：</td>
					<td>
						<czxk:showSelect dictTypeId="transformerType" value="${baseDevice.type}" />
					</td>
				</tr>
				<tr>
					<td>额定功率：</td>
					<td>
						${baseDevice.ratedPower}
					</td>
				</tr>
				<tr>
					<td>生产厂商：</td>
					<td>
						${baseDevice.manufacturer}
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${baseDevice.deviceType eq 'grounding_lightning'}">
			<table class="am-table app-tb"> 
				<tr onclick="viewDeviceDetail('${baseDevice.deviceId}');">
					<td style="width: 100px;vertical-align: middle;">设备类型：</td>
					<td style="vertical-align: middle;">
					  	${baseDevice.deviceTypeName}
					  	<button class="am-btn am-radius am-fr" style="background: #EF7321;color: #fff;height: 30px;width: 80px;padding: 0px;">
					  		<font size="2">
					  			<i class="am-icon-eye"></i>&nbsp;历史巡检
					  		</font>
					  	</button>
					</td>
				</tr>
				<tr>
					<c:if test="${baseDevice.photoList != null && baseDevice.photoList != '[]'}">
						<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
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
					<c:if test="${baseDevice.photoList == null || baseDevice.photoList == '[]'}">
						<td style="vertical-align: middle;">外观照片：</td>
						<td style="vertical-align: middle;">无</td>
					</c:if>
				</tr>
				<tr>
					<td>设备型号：</td>
					<td>
						<czxk:showSelect dictTypeId="lightningType" value="${baseDevice.gflModel}" />
					</td>
				</tr>
				<tr>
					<td>生产厂商：</td>
					<td>
						${baseDevice.supplier}
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${baseDevice.deviceType eq 'pressure_regulator'}">
			<table class="am-table app-tb"> 
				<tr onclick="viewDeviceDetail('${baseDevice.deviceId}');">
					<td style="width: 100px;vertical-align: middle;">设备类型：</td>
					<td style="vertical-align: middle;">
					  	${baseDevice.deviceTypeName}
					  	<button class="am-btn am-radius am-fr" style="background: #EF7321;color: #fff;height: 30px;width: 80px;padding: 0px;">
					  		<font size="2">
					  			<i class="am-icon-eye"></i>&nbsp;历史巡检
					  		</font>
					  	</button>
					</td>
				</tr>
				<tr>
					<c:if test="${baseDevice.photoList != null && baseDevice.photoList != '[]'}">
						<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
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
					<c:if test="${baseDevice.photoList == null || baseDevice.photoList == '[]'}">
						<td style="vertical-align: middle;">外观照片：</td>
						<td style="vertical-align: middle;">无</td>
					</c:if>
				</tr>
				<tr>
					<td>额定功率：</td>
					<td>
					  	${baseDevice.prRatedPower}
					</td>
				</tr>
				<tr>
					<td>生产厂商：</td>
					<td>
					  	${baseDevice.prManufacturer}
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${baseDevice.deviceType eq 'ac_line'}">
			<table class="am-table app-tb"> 
				<tr onclick="viewDeviceDetail('${baseDevice.deviceId}');">
					<td style="width: 100px;vertical-align: middle;">设备类型：</td>
					<td style="vertical-align: middle;">
					  	${baseDevice.deviceTypeName}
					  	<button class="am-btn am-radius am-fr" style="background: #EF7321;color: #fff;height: 30px;width: 80px;padding: 0px;">
					  		<font size="2">
					  			<i class="am-icon-eye"></i>&nbsp;历史巡检
					  		</font>
					  	</button>
					</td>
				</tr>
				<tr>
					<c:if test="${baseDevice.photoList != null && baseDevice.photoList != '[]'}">
						<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
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
					<c:if test="${baseDevice.photoList == null || baseDevice.photoList == '[]'}">
						<td style="vertical-align: middle;">外观照片：</td>
						<td style="vertical-align: middle;">无</td>
					</c:if>
				</tr>
				<tr>
					<td>线缆材质：</td>
					<td>
					  	<czxk:showSelect dictTypeId="line_material" value="${baseDevice.lineMaterial}" />
					</td>
				</tr>
				<tr>
					<td>规格型号：</td>
					<td>
					  	<czxk:showSelect dictTypeId="line_model" value="${baseDevice.lineModel}" />
					</td>
				</tr>
				<tr>
					<td>架空距离：</td>
					<td>
					  	${baseDevice.overheadDistance}米
					</td>
				</tr>
				<tr>
					<td>墙挂距离：</td>
					<td>
					  	${baseDevice.wallDistance}米
					</td>
				</tr>
				<tr>
					<td>地理距离：</td>
					<td>
					  	${baseDevice.groundDistance}米
					</td>
				</tr>
				<tr>
					<td>交流引入长度：</td>
					<td>
					  	${baseDevice.lineLength}米
					</td>
				</tr>
			</table>
		</c:if>
		<c:if test="${baseDevice.deviceType eq 'monitoring'}">
			<table class="am-table app-tb"> 
				<tr onclick="viewDeviceDetail('${baseDevice.deviceId}');">
					<td style="width: 100px;vertical-align: middle;">设备类型：</td>
					<td style="vertical-align: middle;">
					  	${baseDevice.deviceTypeName}
					  	<button class="am-btn am-radius am-fr" style="background: #EF7321;color: #fff;height: 30px;width: 80px;padding: 0px;">
					  		<font size="2">
					  			<i class="am-icon-eye"></i>&nbsp;历史巡检
					  		</font>
					  	</button>
					</td>
				</tr>
				<tr>
					<c:if test="${baseDevice.photoList != null && baseDevice.photoList != '[]'}">
						<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
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
					<c:if test="${baseDevice.photoList == null || baseDevice.photoList == '[]'}">
						<td style="vertical-align: middle;">外观照片：</td>
						<td style="vertical-align: middle;">无</td>
					</c:if>
				</tr>
				<tr>
					<td>型号：</td>
					<td>
						${baseDevice.mModel}
					</td>
				</tr>
				<tr>
					<td>生产厂商：</td>
					<td>
						<czxk:showSelect dictTypeId="monitoring_supplier" value="${baseDevice.mSupplier}" />
					</td>
				</tr>
				
			</table>
		</c:if>
		<c:if test="${baseDevice.deviceType eq 'FSU'}">
			<table class="am-table app-tb"> 
				<tr onclick="viewDeviceDetail('${baseDevice.deviceId}');">
					<td style="width: 100px;vertical-align: middle;">设备类型：</td>
					<td style="vertical-align: middle;">
					  	${baseDevice.deviceTypeName}
					  	<!-- <button class="am-btn am-radius am-fr" style="background: #EF7321;color: #fff;height: 30px;width: 80px;padding: 0px;">
					  		<font size="2">
					  			<i class="am-icon-eye"></i>&nbsp;历史巡检
					  		</font>
					  	</button> -->
					</td>
				</tr>
				<tr>
					<c:if test="${baseDevice.photoList != null && baseDevice.photoList != '[]'}">
						<td colspan="2">
						<p>外观照片：</p>
						<ul data-am-widget="gallery" style="padding: 0 0 0 0" class="am-gallery am-avg-sm-3 am-gallery-imgbordered" data-am-gallery="{pureview:{weChatImagePreview: false}}">
							<c:forEach items="${baseDevice.photoList}" var="item">
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
					<c:if test="${baseDevice.photoList == null || baseDevice.photoList == '[]'}">
						<td style="vertical-align: middle;">外观照片：</td>
						<td style="vertical-align: middle;">无</td>
					</c:if>
				</tr>
				<tr>
					<td style="vertical-align: middle;">FSU厂家：</td>
					<td style="vertical-align: middle;">
						${baseDevice.sfuCompany}
						<!-- <czxk:showSelect dictTypeId="fsu_company" value="${baseDevice.sfuCompany}" /> -->
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">品牌：</td>
					<td style="vertical-align: middle;">
						${baseDevice.brandName}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">负责人：</td>
					<td style="vertical-align: middle;">
						${baseDevice.chargePerson}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">维护人电话：</td>
					<td style="vertical-align: middle;">
						${baseDevice.chargePhone}
					</td>
				</tr>
				<tr>
					<td style="width:120px;vertical-align: middle;">无线模块型号：</td>
					<td style="vertical-align: middle;">
						${baseDevice.wirelessModleType}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">经度：</td>
					<td style="vertical-align: middle;">
						${baseDevice.longitude}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">纬度：</td>
					<td style="vertical-align: middle;">
						${baseDevice.latitude}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">信号强度：</td>
					<td style="vertical-align: middle;">
						${baseDevice.signalStrength}
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">与物理站址位置距离(米)：</td>
					<td style="vertical-align: middle;">
						${baseDevice.distance}
					</td>
				</tr>
			</table>
		</c:if>
	</c:forEach>
</c:if>
<script>
	echo.init({
		offset : 100,
		throttle : 250,
		unload : false
	});
	
	function viewDeviceDetail(deviceId) {
		var stationId = '${acceptStation.stationId}';
		showLoading();
		window.location.href="${ctx}/devices/getByIdDeviceInfo?deviceId="+deviceId+"&stationId="+stationId;
	}
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