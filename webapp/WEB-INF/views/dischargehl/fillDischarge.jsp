<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<%@include file="../commons/mobiscrollDatePlugin.jsp" %> 
<div class="am-cf" >
<form id="fbean" class="am-form" data-am-validator>
	<input type="hidden" name="source" value="${source}" >
	<input type="hidden" name="selectId" value="1" >
	<input type="hidden" name="serial" value="${serial }" id="serial">
	<input type="hidden" value="${recordBO.recordId }" id="oldRecordId">
	<input type="hidden" value="${recordBO.provinceId }" id="curProvince">
	<input type="hidden" value="${recordBO.cityId }" id="curCity">
	<input type="hidden" value="${recordBO.countyId }" id="curCounty">
	<input type="hidden" name="saveOrUpdate" value="${saveOrUpdate}" id="saveOrUpdate">
	<table class="am-table app-tb" style="margin:none;border:none;width: 100%">
			<tr>
				<td style="vertical-align: middle" width="120px">测试计划名称</td>
				<td>
					<input id="planName" placeholder="点击输入名称" name="planName" class="am-form-field am-text-center" value="${recordBO.planName }" style="height: 35px;" required>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;">蓄电池组编码</td>
				<td>
					<div class="am-input-group">
				      <input placeholder="点击扫描添加" class="am-form-field am-text-center" id="recordId" name="recordId" value="${recordBO.recordId }" style="height: 35px;font-size: 14px;" maxlength="42" readonly required>
				      <span class="am-input-group-btn">
				       <c:if test="${recordBO ==null || recordBO == '[]'}">
					       <button onclick="saomiao1()" class="am-btn am-btn-success am-btn-xs" type="button" style="height: 35px;">扫码</button>
				      </c:if>
				      <c:if test="${recordBO !=null && (recordBO.recordId == '' || recordBO.recordId == null)}">
				      	<button onclick="saomiao1()" class="am-btn am-btn-success am-btn-xs" type="button" style="height: 35px;">扫码</button>
				      </c:if>
				      <c:if test="${recordBO !=null && recordBO.recordId != ''&& recordBO.recordId != null}">
				      	<button onclick="modalConfirm('确定更换条码吗?',updateRecordId,null);" class="am-btn am-btn-success am-btn-xs" type="button" style="height: 35px;">更换条码</button> 
				      </c:if>
				      </span>
				    </div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;">电池生产日期</td>
				<td style="border: 1">
					<input  type="text" name="batteryProductDate" class="am-form-field am-text-center" style="height: 35px;" placeholder="手动选择" id="batteryProductDate" value="<fmt:formatDate value="${recordBO.batteryProductDate }" pattern="yyyy-MM-dd" />" readonly>
				</td>
			</tr>
			<tr>
			<td  style="vertical-align: middle;">测试日期</td>
			<td style="border: 1">
				<c:if test="${recordBO ==null || recordBO == '[]'}">
					<input name="testDate" class="am-form-field am-text-center" placeholder="手动选择" style="height: 35px;" id="testDate" value="<fmt:formatDate value="${curDate }" pattern="yyyy-MM-dd" />" required readonly>
				</c:if>
			      <c:if test="${recordBO !=null && (recordBO.recordId == '' || recordBO.recordId == null)}">
			      	<input name="testDate" class="am-form-field am-text-center" placeholder="手动选择" style="height: 35px;" id="testDate" value="<fmt:formatDate value="${curDate }" pattern="yyyy-MM-dd" />" required readonly>
			      </c:if>
				
				<c:if test="${recordBO !=null && recordBO.recordId != ''&& recordBO.recordId != null}">
					<input  style="height: 35px;" name="testDate" class="am-form-field am-text-center" placeholder="手动选择" id="testDate" value="<fmt:formatDate value="${recordBO.testDate }" pattern="yyyy-MM-dd" />" required readonly>
				</c:if>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;">人员</td>
			<td>
				<input style="height: 35px;" placeholder="点击输入" id="testUser" name="testUser" class="am-form-field am-text-center" value="${recordBO.testUser }" required>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;">品牌</td>
			<td>
				<input  style="height: 35px;" name="brand" class="am-form-field am-text-center" placeholder="手动选择" id="brand" value="${recordBO.brand }">
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;">标充容量</td>
			<td >
				<input style="height: 35px;" placeholder="点击输入" id="standardCapacity" name="standardCapacity" class="am-form-field am-text-center" value="${recordBO.standardCapacity }" pattern="^[0-9]*[1-9][0-9]*$">
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;">放电小时率</td>
			<td>
				<!-- <input placeholder="点击输入"  class="am-form-field am-text-center"> -->
				<c:if test="${recordBO ==null || recordBO == '[]' || recordBO.dischargeRate == '' || recordBO.dischargeRate == null}">
					<czxk:select dictTypeId="discharge_rate"  value="3" id="dischargeRate" name="dischargeRate" height="35px;"/>
				</c:if>
				<c:if test="${recordBO !=null && recordBO.dischargeRate !='' && recordBO.dischargeRate !=null}">
				<czxk:select dictTypeId="discharge_rate"  value="${recordBO.dischargeRate }"  id="dischargeRate" name="dischargeRate" height="35px;"/>
				</c:if>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;">单体电压</td>
			<td>
				<!-- <input placeholder="点击输入"  class="am-form-field am-text-center"> -->
				<c:if test="${recordBO ==null || recordBO == '[]' || recordBO.singletonVoltage == null || recordBO.singletonVoltage == ''}">
					<czxk:select dictTypeId="singleton_voltage"  value="2" id="singletonVoltage" name="singletonVoltage" height="35px;"/>
				</c:if>
				<c:if test="${recordBO !=null && recordBO.singletonVoltage != null && recordBO.singletonVoltage != ''}">
				<czxk:select dictTypeId="singleton_voltage"  value="${recordBO.singletonVoltage }" id="singletonVoltage" name="singletonVoltage" height="35px;"/>
				</c:if>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;">放电电流</td>
			<td>
				<input style="height: 35px" placeholder="点击输入" id="dischargeCurrent" name="dischargeCurrent" class="am-form-field am-text-center" value="${recordBO.dischargeCurrent }" pattern="^[0-9]*[1-9][0-9]*$">
			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;">
								所在省：
			</td>
			<td style="vertical-align: middle;">
				<select data-am-selected="{maxHeight:200}" id="provinceId" name="provinceId" onchange="getCity()">
						<c:forEach items="${provinceList}" var="obj1" varStatus="status1" >
							<option  value="${obj1}" 
							<c:if test="${obj1 eq recordBO.provinceId }">selected</c:if>
							 >${obj1}</option>
						</c:forEach>
				</select>

			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;">
								所在市：
			</td>
			<td style="vertical-align: middle;">
				<select data-am-selected="{maxHeight:200}" id="cityId" name="cityId" onchange="getCounty()">
						<c:forEach items="${cityList}" var="obj2" varStatus="status2" >
							<option  value="${obj2}" 
							<c:if test="${obj2 eq recordBO.cityId }">selected</c:if>
							 >${obj2}</option>
						</c:forEach>
				</select>

			</td>
		</tr>
		<tr>
			<td style="vertical-align: middle;">
								所在县/区：
			</td>
			<td style="vertical-align: middle;">
				<select data-am-selected="{maxHeight:200,dropUp: 1}" id="countyId" name="countyId">
						<c:forEach items="${countryList}" var="obj3" varStatus="status3" >
							<option  value="${obj3.name}" 
							<c:if test="${obj3.name eq recordBO.countyId }">selected</c:if>
							 >${obj3.name}</option>
						</c:forEach>
				</select>

			</td>
		</tr>
	</table>
</form>
<div class="am-scrollable-horizontal" style="margin-top: 0px;">
			<table class="am-table am-table-bordered am-table-striped am-table-hover am-text-center am-text-nowrap app-tb" style="border-top:none;margin-bottom:0px;">
				  	<tbody id="dischargeDetail">
				  	<c:if test="${hlDischargeDetailList ==null || hlDischargeDetailList=='[]'}">
					  	<tr style="border-bottom: 1px slide;">
						   	<td class="am-padding-0"><input id="serialNumber" name="serialNumber" value="序号" style="width:35px" class="am-form-field app-inputPM0 app-inputTitle" readonly></td>
						    <td class="am-padding-0"><input id="barCode" name="barCode" value="单体编号" style="width:120px" class="am-form-field app-inputPM0 app-inputTitle" readonly></td>
						    <td class="am-padding-0"><input id="preDischargeVoltage" name="preDischargeVoltage" value="放电前电压" style="width:100px" class="am-form-field app-inputPM0 app-inputTitle" required readonly></td>
						    <td class="am-padding-0"><input id="ext1" name="ext1" value="1H" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
						    <td class="am-padding-0"><input id="ext2" name="ext2" value="2H" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
						    <td class="am-padding-0"><input id="ext3" name="ext3" value="3H" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
						    <td class="am-padding-0"><input id="ext4" name="ext4" value="4H" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
						    <td class="am-padding-0"><input id="ext5" name="ext5" value="5H" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
						    <td class="am-padding-0"><input id="ext6" name="ext6" value="6H" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
						    <td class="am-padding-0"><input id="ext7" name="ext7" value="7H" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
						    <td class="am-padding-0"><input id="ext8" name="ext8" value="8H" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
						    <td class="am-padding-0"><input id="ext9" name="ext9" value="9H" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
						    <td class="am-padding-0"><input id="ext10" name="ext10" value="10H" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td> 
					    </tr>
					    <tr id="sumVoltage">
				    		<td class="am-padding-0"><input id="serialNumber" name="serialNumber" value="" class="am-form-field app-inputPM0 app-inputContent" readonly></td>
					 		<td class="am-padding-0"><input id="barCode" name="barCode" value="总电压" class="am-form-field app-inputPM0 app-inputContent" readonly></td>
					 		<td class="am-padding-0"><input id="sumPreDischargeVoltage" name="preDischargeVoltage" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
						    <td class="am-padding-0"><input id="ext1_999" name="ext1" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"    class="am-form-field app-inputPM0 app-inputContent" ></td>
						    <td class="am-padding-0"><input id="ext2_999" name="ext2" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
						    <td class="am-padding-0"><input id="ext3_999" name="ext3" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
						    <td class="am-padding-0"><input id="ext4_999" name="ext4" onchange="regV(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
						    <td class="am-padding-0"><input id="ext5_999" name="ext5" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
						    <td class="am-padding-0"><input id="ext6_999" name="ext6" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
						    <td class="am-padding-0"><input id="ext7_999" name="ext7" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
						    <td class="am-padding-0"><input id="ext8_999" name="ext8" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
						    <td class="am-padding-0"><input id="ext9_999" name="ext9" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
						    <td class="am-padding-0"><input id="ext10_999" name="ext10" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"    class="am-form-field app-inputPM0 app-inputContent" ></td>
					 	</tr>
				  	</c:if>
				  	<c:if test="${hlDischargeDetailList !=null}">
				  		<c:forEach var="obj" items="${hlDischargeDetailList }" varStatus="status">
						   	<c:if test="${obj.serialNumber == 0}">
						   	<tr style="border-bottom: 1px slide">
							    <td class="am-padding-0"><input id="serialNumber" name="serialNumber" value="序号" style="width:35px" class="am-form-field app-inputPM0 app-inputTitle" readonly></td>
						    	<td class="am-padding-0">
						    		<input type="hidden" id="barCode" name="barCode" value="${obj.barCode }" > 
						    		<input  value="单体编号" style="width:120px" class="am-form-field app-inputPM0 app-inputTitle" > 
						    	</td>
						    	<td class="am-padding-0"><input id="preDischargeVoltage" name="preDischargeVoltage" value="放电前电压" style="width:100px" class="am-form-field app-inputPM0 app-inputTitle" required readonly></td>
						    	<td class="am-padding-0"><input id="ext1_${obj.serialNumber}" name="ext1" value="${obj.ext1 }" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td> 
							    <td class="am-padding-0"><input id="ext2_${obj.serialNumber}" name="ext2" value="${obj.ext2 }" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
							    <td class="am-padding-0"><input id="ext3_${obj.serialNumber}" name="ext3" value="${obj.ext3 }" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
							    <td class="am-padding-0"><input id="ext4_${obj.serialNumber}" name="ext4" value="${obj.ext4 }" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
							    <td class="am-padding-0"><input id="ext5_${obj.serialNumber}" name="ext5" value="${obj.ext5 }" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
							    <td class="am-padding-0"><input id="ext6_${obj.serialNumber}" name="ext6" value="${obj.ext6 }" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
							    <td class="am-padding-0"><input id="ext7_${obj.serialNumber}" name="ext7" value="${obj.ext7 }" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
							    <td class="am-padding-0"><input id="ext8_${obj.serialNumber}" name="ext8" value="${obj.ext8 }" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
							    <td class="am-padding-0"><input id="ext9_${obj.serialNumber}" name="ext9" value="${obj.ext9 }" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td>
							    <td class="am-padding-0"><input id="ext10_${obj.serialNumber}" name="ext10" value="${obj.ext10 }" style="width:70px" class="am-form-field app-inputPM0 app-inputTitle"></td> 
						   	</tr>
						   	</c:if>
						   	<c:if test="${obj.serialNumber == 999}">
						   	<tr style="border-bottom: 1px slide" id="sumVoltage">
								<td class="am-padding-0"><input id="serialNumber" name="serialNumber" value="" class="am-form-field app-inputPM0 app-inputContent" readonly></td>
				 				<td class="am-padding-0">
				 					<input type="hidden" id="barCode_${obj.serialNumber}" name="barCode" value="${obj.barCode }" class="am-form-field app-inputPM0 app-inputContent" >
				 					<input value="总电压" class="am-form-field app-inputPM0 app-inputContent" >
				 				</td>
						    	<td class="am-padding-0"><input id="preDischargeVoltage${obj.serialNumber}" name="preDischargeVoltage" value="${obj.preDischargeVoltage }" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"    class="am-form-field app-inputPM0 app-inputContent"></td>
							    <td class="am-padding-0"><input id="ext1_${obj.serialNumber}" name="ext1" value="${obj.ext1 }" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext2_${obj.serialNumber}" name="ext2" value="${obj.ext2 }" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext3_${obj.serialNumber}" name="ext3" value="${obj.ext3 }" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext4_${obj.serialNumber}" name="ext4" value="${obj.ext4 }" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext5_${obj.serialNumber}" name="ext5" value="${obj.ext5 }" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext6_${obj.serialNumber}" name="ext6" value="${obj.ext6 }" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext7_${obj.serialNumber}" name="ext7" value="${obj.ext7 }" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext8_${obj.serialNumber}" name="ext8" value="${obj.ext8 }" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext9_${obj.serialNumber}" name="ext9" value="${obj.ext9 }" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"   class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext10_${obj.serialNumber}" name="ext10" value="${obj.ext10 }" onchange="reg(this.id,this.value);" onblur="checkVoltage(this.id,this.value)"  class="am-form-field app-inputPM0 app-inputContent" ></td> 
						   	</tr>
						   	</c:if>
						   	<c:if test="${obj.serialNumber != 0 && obj.serialNumber != 999}">
						   	<tr style="border-bottom: 1px slide" id="every_${obj.serialNumber}">
							   	<td class="am-padding-0"><input id="serialNumber" name="serialNumber" value="${obj.serialNumber}" class="am-form-field app-inputPM0 app-inputContent" readonly></td>
							   	<td class="am-padding-0"><input id="barCode_${obj.serialNumber}" name="barCode" value="${obj.barCode }" class="am-form-field app-inputPM0 app-inputContent"  style="color:#0000ff;" onclick="updateDetailId(this.value);" readonly></td>
							   	<td class="am-padding-0"><input id="preDischargeVoltage_${obj.serialNumber}" name="preDischargeVoltage" value="${obj.preDischargeVoltage }" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" required></td>
							    <td class="am-padding-0"><input id="ext1_${obj.serialNumber}" name="ext1" value="${obj.ext1 }" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext2_${obj.serialNumber}" name="ext2" value="${obj.ext2 }" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext3_${obj.serialNumber}" name="ext3" value="${obj.ext3 }" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext4_${obj.serialNumber}" name="ext4" value="${obj.ext4 }" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext5_${obj.serialNumber}" name="ext5" value="${obj.ext5 }" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext6_${obj.serialNumber}" name="ext6" value="${obj.ext6 }" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext7_${obj.serialNumber}" name="ext7" value="${obj.ext7 }" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext8_${obj.serialNumber}" name="ext8" value="${obj.ext8 }" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext9_${obj.serialNumber}" name="ext9" value="${obj.ext9 }" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>
							    <td class="am-padding-0"><input id="ext10_${obj.serialNumber}" name="ext10" value="${obj.ext10 }" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>
						   	</tr>
						   	</c:if>
				  		</c:forEach>
				  	</c:if>
			    </tbody>
			    <tr >
				 	<td style="text-align:left;" colspan="13"><input id="saoma-insert" class="am-btn am-btn-success am-btn-xs" style="width:90px;height: 35px;" type="button" value="扫码添加" onclick="saomiao2(0);"></td>
				</tr>
		  	</table>
	</div>	
	

	<div class="am-topbar app-div-btn-tool" style="padding-top: 6px">
			<div class="am-u-sm-6">
				<a class="am-btn am-btn-danger am-radius am-btn-block" style="color:white;" onclick="cancleSave();" ><strong>取消</strong></a>
			</div>
		<div class="am-u-sm-6">
			<button class="am-btn am-btn-success am-radius am-btn-block" style="color:white;" onclick="saveDischargeRecord();"><strong>保存</strong></button>
		</div>
    </div>
</div>


<script>
!function(t) {
	initMobiscroll("#batteryProductDate");
	initMobiscroll("#testDate");
}(window.jQuery || window.Zepto);

function updateRecordId(){
	// 扫描二维码
	wx.scanQRCode({
		needResult : 1,
		scanType : [ "qrCode", "barCode" ],
		success : function(res) {
			var indexss = res.resultStr.indexOf(",") + 1;
			var deviceId = res.resultStr.substr(indexss);
			if (deviceId.length == 13 || deviceId.length == 38 || deviceId.length == 42) {
				showLoading();
				setTimeout(function() {
					updateRecordIdDeal(deviceId);
				}, 5000);
			} else {
				closeLoading();
				modalAlert("此条码是无效条码", null);
			}
		},
		cancel : function(res) {
		},
		complete : function(res) {
		},
		fail : function(res) {
		}
	});
}

function updateRecordIdDeal(deviceId){
	$.post("../hldischarge/judgeRecordIdExist.json", {
		barCode : deviceId
	}, function(json) {
		if (json.isExist) {
			closeLoading();
			modalAlert("条码已存在，请重新扫描！", null);
		} else {//存吗不存在
			var oldRecordId=$("#recordId").val();
			$.post("../hldischarge/updateDischargeRecordId.json", {
				recordId : deviceId,
				oldRecordId : oldRecordId
				}, 
				function(json) {
					$("#recordId").val(deviceId);
					closeLoading();
					modalAlert("更换成功！", null);
				}, "json");
		}
		}, "json");
}



function saveDischargeRecord() {
	$.when($('#fbean').validator('isFormValid')).then(function(result) {
		var planName=$("#planName").val();
		var recordId=$("#recordId").val();
		var batteryProductDate=$("#batteryProductDate").val();
		var testDate=$("#testDate").val();
		var testUser=$("#testUser").val();
		var brand=$("#brand").val();
		var standardCapacity=$("#standardCapacity").val();
		var dischargeRate=$("#dischargeRate").val();
		var dischargeCurrent=$("#dischargeCurrent").val();
		var provinceId=$("#provinceId").val();
		var cityId=$("#cityId").val();
		var countyId=$("#countyId").val();
		var singletonVoltage=$("#singletonVoltage").val();
		var detailTrDates=document.getElementById("dischargeDetail").getElementsByTagName("tr");
		if(result){
			showLoading();
			$.ajax({type: "POST",
		 		   url: $("#ctx").val()+"/hldischarge/updateDischargeRecord.json",
		 		  async:false,
		 		   data:{
		 			  	planName:planName,
		 				recordId:recordId,
		 				batteryProductDate:batteryProductDate,
		 				testDate:testDate,
		 				testUser:testUser,
		 				brand:brand,
		 				standardCapacity:standardCapacity,
		 				dischargeRate:dischargeRate,
		 				dischargeCurrent:dischargeCurrent,
		 				provinceId:provinceId,
		 				cityId:cityId,
		 				countyId:countyId,
		 				singletonVoltage:singletonVoltage
				   	 },
		 		   error: function (msg) {
		 			   //alert(JSON.stringify(msg));
		     		},
	    		   success: function (json) {
	    			   for(var i=0;i<detailTrDates.length;i++){
	    				   var detailTds=detailTrDates[i].cells;
	    				   var serialNumber=detailTds[0].getElementsByTagName("INPUT")[0].value;
	    				   var barCode=detailTds[1].getElementsByTagName("INPUT")[0].value;
	    				   if(barCode=="单体编号" || barCode =="总电压"){
	    					   barCode=barCode+"_"+recordId;
	    				   }
	    				   if(serialNumber=="序号"){
	    					   serialNumber=0;
	    				   }else if(serialNumber==""){
	    					   serialNumber=999;
	    				   } else{
	    					   serialNumber=serialNumber-0;
	    					   if(barCode!="单体编号" && barCode !="总电压"){
	    						   if(singletonVoltage=="12V"){
		    						   lapl:for(var k=3;k<13;k++){
	 									  ext=detailTds[k].getElementsByTagName("INPUT")[0].value;
	 									  var tds=document.getElementById('dischargeDetail').rows[0].cells;
	 									  if(ext != null && ext != ''){
	 											  if(ext.indexOf("-")>=0){
	 												  alert("单体编号为:"+barCode+"是落后单体");
	 												  break lapl;
	 											  }
	 									  }
	 								  }
		    					   }else{
	    						   if(dischargeRate == 3){
	    							   lapl:for(var k=3;k<13;k++){
	    									  ext=detailTds[k].getElementsByTagName("INPUT")[0].value;
	    									  var tds=document.getElementById('dischargeDetail').rows[0].cells;
	    									  if(ext != null && ext != ''){
	    											  if(ext.indexOf("-")>=0){
	    												  alert("单体编号为:"+barCode+"是落后单体");
	    												  break lapl;
	    											  }
	    									  }
	    								  }
	    							   }else if(dischargeRate == '自定义'){
		    								 lap:for(var k=3;k<13;k++){
		    									  ext=detailTds[k].getElementsByTagName("INPUT")[0].value;
		    									  var tds=document.getElementById('dischargeDetail').rows[0].cells;
		    									  if(ext != null && ext != ''){
		    											  if(ext.indexOf("-")>=0){
		    												  alert("单体编号为:"+barCode+"是落后单体");
		    												  break lap;
		    											  }
		    									  }
		    								  }
		    							   }else if(dischargeRate == 5){
		    						 	lap:for(var k=3;k<13;k++){
			    						  ext=detailTds[k].getElementsByTagName("INPUT")[0].value;
			    						  var tds=document.getElementById('dischargeDetail').rows[0].cells;
			    						  if(ext != null && ext != ''){
			    							  curtd=tds[k].getElementsByTagName("INPUT")[0].value;
			    							   if(curtd.indexOf("H")>0){
				    								  var single=curtd.split("H");
					    							  if(single[1] != ""){
					    								  curtd=single[0]-0+single[1].split("m")[0]/60;
					    							  }else{
					    								  curtd=single[0];
					    							  } 
				    							  }else{
				    								  var single=curtd.split("m");
				    								  curtd=single[0]/60;
				    							  }
			    							  for(var j=3;j<tds.length;j++){
				    							  td=tds[j].getElementsByTagName("INPUT")[0].value;
				    							  if(td.indexOf("H")>0){
				    								  var singles=td.split("H");
					    							  if(singles[1] != ""){
					    								  td=singles[0]-0+singles[1].split("m")[0]/60;
					    							  }else{
					    								  td=singles[0];
					    							  } 
				    							  }else{
				    								  var singles=td.split("m");
				    								  td=singles[0]/60;
				    							  }
				    							  if(ext.indexOf("-")<0){
				    								  if(curtd<3 && td<3 && ext<1.80){
					    								  alert("单体编号为:"+barCode+"是落后单体");
					    								  //$("input[name="+barCode+"]").css("color","red");
					    								  break lap;
					    							  }
				    							  }else{
				    								  alert("单体编号为:"+barCode+"是落后单体");
				    								  break lap;
				    							  }
				    							  
				    						  } 
			    						  }
			    					  }
			    				   }else if(dischargeRate == 10){
			    					   lapl:for(var k=3;k<13;k++){
			    						   ext=detailTds[k].getElementsByTagName("INPUT")[0].value;
			    						   var tds=document.getElementById('dischargeDetail').rows[0].cells;
			    						   if(ext != null && ext != ''){
			    							   curtd=tds[k].getElementsByTagName("INPUT")[0].value;
			    							   if(curtd.indexOf("H")>0){
				    								  var single=curtd.split("H");
					    							  if(single[1] != ""){
					    								  curtd=single[0]-0+single[1].split("m")[0]/60;
					    							  }else{
					    								  curtd=single[0];
					    							  } 
				    							  }else{
				    								  var single=curtd.split("m");
				    								  curtd=single[0]/60;
				    							  }
			    							   for(var j=3;j<tds.length;j++){
					    							 td=tds[j].getElementsByTagName("INPUT")[0].value;
					    							  if(td.indexOf("H")>0){
					    								  var singles=td.split("H");
						    							  if(singles[1] != ""){
						    								  td=singles[0]-0+singles[1].split("m")[0]/60;
						    							  }else{
						    								  td=singles[0];
						    							  } 
					    							  }else{
					    								  var singles=td.split("m");
					    								  td=singles[0]/60;
					    							  }
					    							  if(ext.indexOf("-")<0){
					    								  if(curtd<6 && td<6 && ext<1.80){
						    								  alert("单体编号为:"+barCode+"是落后单体");
						    								  //$("input[name="+barCode+"]").css("color","red");
						    								  break lapl;
						    							  } 
					    							  }else{
					    								  alert("单体编号为:"+barCode+"是落后单体");
					    								  break lapl;
					    							  }
					    							  
					    						  }
			    						   }
				    						  
			    					   }
			    				   }
	    					   }
		    				 }
	    				   }
	    					   $.ajax({
		    					   type: "POST",
		    					   async:false,
		    					   data:{
		    						   serialNumber:serialNumber,
		    						   barCode:barCode,
		    						   preDischargeVoltage:detailTds[2].getElementsByTagName("INPUT")[0].value,
		    						   ext1:detailTds[3].getElementsByTagName("INPUT")[0].value,
		    						   ext2:detailTds[4].getElementsByTagName("INPUT")[0].value,
		    						   ext3:detailTds[5].getElementsByTagName("INPUT")[0].value,
		    						   ext4:detailTds[6].getElementsByTagName("INPUT")[0].value,
		    						   ext5:detailTds[7].getElementsByTagName("INPUT")[0].value,
		    						   ext6:detailTds[8].getElementsByTagName("INPUT")[0].value,
		    						   ext7:detailTds[9].getElementsByTagName("INPUT")[0].value,
		    						   ext8:detailTds[10].getElementsByTagName("INPUT")[0].value,
		    						   ext9:detailTds[11].getElementsByTagName("INPUT")[0].value,
		    						   ext10:detailTds[12].getElementsByTagName("INPUT")[0].value,
		    						   recordId:recordId
		    					   },
		    			 		   url: $("#ctx").val()+"/hldischarge/updateDischargeDetail.json",
		    			 		   error: function (msg) {
		    			 			   //alert(JSON.stringify(msg));
		    			     		},
		    		    		   success: function (json) {
		    		    			   
		    			     		}
		    				   });
	    			   }
	    			   
		     		},complete:function(){
						window.location.href="../hldischarge/goStart?selectId=2";
		     		}
		     		
	 		});
		}
		
		
	});
	
}
function regValue(id,val){
	var reg=/^-?\d{4}$/;
	val = val.replace('-','');
	if(!reg.test(val)){
		alert("请输入四位数字");
		var s="#"+id;
		$(s).val('');
		return false;
	}
}
function cancleSave(){
	var saveOrUpdate=$("#saveOrUpdate").val();
	var recordId=$("#recordId").val();
	modalConfirm("确定取消吗?",function(){
		if(saveOrUpdate == 2){
			window.location.href="../hldischarge/goStart?selectId=2";
		}else{
			if(recordId == null || recordId ==""){
				window.location.href="../hldischarge/goStart?selectId=1";
				
			}else{
				window.location.href="../hldischarge/cancleDischargeRecord?recordId="+recordId;
			}
			
		}
	},null);
	
}
function reg(id,val){
	var reg=/^[0-9]+\.?[0-9]{0,2}$/;
	if(!reg.test(val)){
		alert("请输入正整数");
		var s="#"+id;
		$(s).val('');
		return false;
	}
}

function updateDetailId(value){
	$("#update-bar-code-confirm-msg").html("确定更新条码或者删除条码吗?");
	var recordId=$("#recordId").val();
	$('#update-bar-code-confirm').modal(
			{
				relatedTarget : this,
				onConfirm: function() {
					saomiao3(value);
			        },
			     onCancel: function() {
			    	 showLoading();
			    	 $.ajax({
			    		 type: "POST",
						  data:{
							   barCode:value,
							   recordId:recordId
						   },
				 		  url: $("#ctx").val()+"/hldischarge/deleteDischargeDetail.json",
				 		  async:false,
				 		  error: function (msg) {
				 			   
				     		},
			    		  success: function (json) {
			    			  //showLoading();
			    			  var serial=json.serial;
			    			  $("#serial").val(serial);
			    			  var planName=$("#planName").val();
			    			  var batteryProductDate=$("#batteryProductDate").val();
			    			  var testDate=$("#testDate").val();
			    			  var testUser=$("#testUser").val();
			    			  var brand=$("#brand").val();
			    			  var standardCapacity=$("#standardCapacity").val();
			    			  var dischargeRate=$("#dischargeRate").val();
			    			  var dischargeCurrent=$("#dischargeCurrent").val();
			    			  var provinceId=$("#provinceId").val();
			    			  var cityId=$("#cityId").val();
			    			  var countyId=$("#countyId").val();
			    			  var singletonVoltage=$("#singletonVoltage").val();
			    			  $.ajax({
			    				  type: "POST",
			   		 		   	  url: $("#ctx").val()+"/hldischarge/updateDischargeRecord.json",
			   		 		      async:false,
			   		 		      data:{
			   		 			  	planName:planName,
			   		 				recordId:recordId,
			   		 				batteryProductDate:batteryProductDate,
			   		 				testDate:testDate,
			   		 				testUser:testUser,
			   		 				brand:brand,
			   		 				standardCapacity:standardCapacity,
			   		 				dischargeRate:dischargeRate,
			   		 				dischargeCurrent:dischargeCurrent,
			   		 				provinceId:provinceId,
				 					cityId:cityId,
				 					countyId:countyId,
				 					singletonVoltage:singletonVoltage
			   				   	  },
			   		 		      error: function (msg) {
			   		 			   //alert(JSON.stringify(msg));
			   		     		  },
			   	    		     success: function (json) {
			   	    		       //window.location.reload();
			   	    		       var saveOrUpdate=$("#saveOrUpdate").val();
			   	    			   window.location.href=$("#ctx").val()+"/hldischarge/toFillDischarge?selectId=1&saveOrUpdate="+saveOrUpdate+"&recordId="+recordId;
			   	    		      }
			    			   });
			    			  
			    			  //window.location.reload();
				     		}
			    	 } );
			    	 
			}
}); 
}

window.onload=setColor();
function setColor(){
	var singletonVoltage=$("#singletonVoltage").val();
	var detailTrDates=document.getElementById("dischargeDetail").getElementsByTagName("tr");
	var dischargeRate=$("#dischargeRate").val();
	for(var i=0;i<detailTrDates.length;i++){
		   var detailTds=detailTrDates[i].cells;
		   var serialNumber=detailTds[0].getElementsByTagName("INPUT")[0].value;
		   var barCode=detailTds[1].getElementsByTagName("INPUT")[0].value;
		   if(serialNumber=="序号"){
			   serialNumber=0;
		   }else if(serialNumber==""){
			   serialNumber=999;
		   } else{
			   serialNumber=serialNumber-0;
			   if(barCode!="单体编号" && barCode !="总电压"){
				  if(singletonVoltage=="12V"){
					   for(var k=3;k<13;k++){
							  ext=detailTds[k].getElementsByTagName("INPUT")[0].value;
							  var tds=document.getElementById('dischargeDetail').rows[0].cells;
							  if(ext != null && ext != ''){
									  if(ext.indexOf("-")>=0){
										  $("#barCode_"+serialNumber).css("color","red");
										  var m=k-2;
										  $("#ext"+m+"_"+serialNumber).css("color","red");
									  }
							  }
						  }
				   }else{
				   if(dischargeRate == 3){
						 for(var k=3;k<13;k++){
							  ext=detailTds[k].getElementsByTagName("INPUT")[0].value;
							  var tds=document.getElementById('dischargeDetail').rows[0].cells;
							  if(ext != null && ext != ''){
									  if(ext.indexOf("-")>=0){
										  $("#barCode_"+serialNumber).css("color","red");
										  var m=k-2;
										  $("#ext"+m+"_"+serialNumber).css("color","red");
									  }
							  }
						  }
					   }else if(dischargeRate == '自定义'){
							 for(var k=3;k<13;k++){
								  ext=detailTds[k].getElementsByTagName("INPUT")[0].value;
								  var tds=document.getElementById('dischargeDetail').rows[0].cells;
								  if(ext != null && ext != ''){
										  if(ext.indexOf("-")>=0){
											  $("#barCode_"+serialNumber).css("color","red");
											  var m=k-2;
											  $("#ext"+m+"_"+serialNumber).css("color","red");
										  }
								  }
							  }
						   }else if(dischargeRate == 5){
					 	for(var k=3;k<13;k++){
						  ext=detailTds[k].getElementsByTagName("INPUT")[0].value;
						  var tds=document.getElementById('dischargeDetail').rows[0].cells;
						  if(ext != null && ext != ''){
							  curtd=tds[k].getElementsByTagName("INPUT")[0].value;
							   if(curtd.indexOf("H")>0){
									  var single=curtd.split("H");
	 							  if(single[1] != ""){
	 								  curtd=single[0]-0+single[1].split("m")[0]/60;
	 							  }else{
	 								  curtd=single[0];
	 							  } 
								  }else{
									  var single=curtd.split("m");
									  curtd=single[0]/60;
								  }
							  for(var j=3;j<tds.length;j++){
								  td=tds[j].getElementsByTagName("INPUT")[0].value;
								  if(td.indexOf("H")>0){
									  var singles=td.split("H");
	 							  if(singles[1] != ""){
	 								  td=singles[0]-0+singles[1].split("m")[0]/60;
	 							  }else{
	 								  td=singles[0];
	 							  } 
								  }else{
									  var singles=td.split("m");
									  td=singles[0]/60;
								  }
								  if(ext.indexOf("-")<0){
									  if(curtd<3 && td<3 && ext<1.80){
										  //alert("单体编号为:"+barCode+"是落后单体");
										  //$("input[name="+barCode+"]").css("color","red");
										  $("#barCode_"+serialNumber).css("color","red");
										  var m=k-2;
										  $("#ext"+m+"_"+serialNumber).css("color","red");
									  }
								  }else{
									  $("#barCode_"+serialNumber).css("color","red");
									  var m=k-2;
									  $("#ext"+m+"_"+serialNumber).css("color","red");
								  }
								  
							  } 
						  }
					  }
				   }else if(dischargeRate == 10){
					   for(var k=3;k<13;k++){
						   ext=detailTds[k].getElementsByTagName("INPUT")[0].value;
						   var tds=document.getElementById('dischargeDetail').rows[0].cells;
						   if(ext != null && ext != ''){
							   curtd=tds[k].getElementsByTagName("INPUT")[0].value;
							   if(curtd.indexOf("H")>0){
									  var single=curtd.split("H");
	 							  if(single[1] != ""){
	 								  curtd=single[0]-0+single[1].split("m")[0]/60;
	 							  }else{
	 								  curtd=single[0];
	 							  } 
								  }else{
									  var single=curtd.split("m");
									  curtd=single[0]/60;
								  }
							   for(var j=3;j<tds.length;j++){
	 							 td=tds[j].getElementsByTagName("INPUT")[0].value;
	 							  if(td.indexOf("H")>0){
	 								  var singles=td.split("H");
		    							  if(singles[1] != ""){
		    								  td=singles[0]-0+singles[1].split("m")[0]/60;
		    							  }else{
		    								  td=singles[0];
		    							  } 
	 							  }else{
	 								  var singles=td.split("m");
	 								  td=singles[0]/60;
	 							  }
	 							  if(ext.indexOf("-")){
	 								 if(curtd<6 && td<6 && ext<1.80){
		 								 //$("input[name="+barCode+"]").css("color","red");
		 								 $("#barCode_"+serialNumber).css("color","red");
										  var m=k-2;
										  $("#ext"+m+"_"+serialNumber).css("color","red");
		 							  }
	 							  }else{
	 								 $("#barCode_"+serialNumber).css("color","red");
									  var m=k-2;
									  $("#ext"+m+"_"+serialNumber).css("color","red");
	 							  }
	 							  
	 						  }
						   }
							  
					   }
				   }
			   }
			 }
		   }
	}
}


function saomiao1() {
	// 扫描二维码
	wx.scanQRCode({
		needResult : 1,
		scanType : [ "qrCode", "barCode" ],
		success : function(res) {
			var indexss = res.resultStr.indexOf(",") + 1;
			var deviceId = res.resultStr.substr(indexss);
			if (deviceId.length == 13 || deviceId.length == 38 || deviceId.length == 42) {
				showLoading();
				setTimeout(function() {
					checkBarCode(deviceId);
				}, 5000);
			} else {
				closeLoading();
				modalAlert("此条码是无效条码", null);
			}
		},
		cancel : function(res) {
		},
		complete : function(res) {
		},
		fail : function(res) {
		}
	});
};
function checkBarCode(deviceId) {// 查询是否条码已经存在
	$.post("../hldischarge/judgeRecordIdExist.json", {
		barCode : deviceId
	}, function(json) {
		if (json.isExist) {
			//条码存在
			window.location.href="../hldischarge/toFillDischarge?selectId=1&recordId="+deviceId;
		} else {//存吗不存在
				var oldRecordId=$("#recordId").val();
				//alert(oldRecord);
				$("#recordId").val(deviceId);
				$.post("../hldischarge/saveDischargeRecord.json", {
					recordId : deviceId,
					testDate:$("#testDate").val()
					}, function(json) {
							closeLoading();
					}, "json");
				
				/* if(oldRecordId=="" || oldRecordId == null){
					$.post("../hldischarge/saveDischargeRecord.json", {
						recordId : deviceId,
						testDate:$("#testDate").val()
						}, function(json) {
								closeLoading();
						}, "json");
				}else{
					$.post("../hldischarge/updateDischargeRecordId.json", {
						recordId : deviceId,
						oldRecordId : oldRecordId,
						}, function(json) {
								closeLoading();
						}, "json");
				} */
			}
			}, "json");
}
function saomiao2(serial) {
	
	var recordId=$("#recordId").val();
	if(recordId==null || recordId==''|| recordId=='null'){
		modalAlert("蓄电池编码不能为空", null);
	}else{
		serial=$("#serial").val()-0;
		if(serial==0){
			var detailTrDates=document.getElementById("dischargeDetail").getElementsByTagName("tr");
			if(detailTrDates.length==2){
				serial=1;
			}else{
				var tds=detailTrDates[detailTrDates.length-2].cells;
				serial=tds[0].getElementsByTagName("INPUT")[0].value-0+1;
			}
			
		}
		// 扫描二维码
		wx.scanQRCode({
			needResult : 1,
			scanType : [ "qrCode", "barCode" ],
			success : function(res) {
				var indexss = res.resultStr.indexOf(",") + 1;
				var deviceId = res.resultStr.substr(indexss);
				if (deviceId.length == 13 || deviceId.length == 38 || deviceId.length == 42) {
					showLoading();
					setTimeout(function() {
						
						checkCode(deviceId,serial,recordId);
					}, 5000);
				} else {
					closeLoading();
					modalAlert("此条码是无效条码", null);
					
				}
			},
			cancel : function(res) {
			},
			complete : function(res) {
			},
			fail : function(res) {
			}
		});
	}
	
};
function checkCode(deviceId,serial,recordId) {// 查询是否子条码已经存在
		$.post("../hldischarge/judgeBarCodeExist.json", {
			barCode : deviceId,
			recordId:recordId
		}, function(json) {
			if (json.succ) {
				modalAlert(json.msg, null);
				closeLoading();
			} else {
					closeLoading();
					modalNextConfirm(deviceId,recordId,serial);
					}
				}, "json");
	
};
function modalNextConfirm(deviceId,recordId,serial) {
	saveDischargeDetailBarCode(deviceId,recordId,serial);
	$("#sumVoltage").before('<tr style="border-bottom: 1px slide" id="every_'+serial+'">'+
		   	'<td class="am-padding-0"><input id="serialNumber" name="serialNumber" value="'+serial+'"  class="am-form-field app-inputPM0 app-inputContent"></td>'+
		   	'<td class="am-padding-0"><input id="barCode" name="barCode" value="'+deviceId+'" onclick="updateDetailId(this.value);"  style="color:#0000ff;" class="am-form-field app-inputPM0 app-inputContent" readonly ></td>'+
		   	'<td class="am-padding-0"><input id="preDischargeVoltage_'+serial+'" name="preDischargeVoltage" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);"  class="am-form-field app-inputPM0 app-inputContent" required></td>'+
		   	'<td class="am-padding-0"><input id="ext1_'+serial+'" name="ext1" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>'+
		   	'<td class="am-padding-0"><input id="ext2_'+serial+'" name="ext2" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>'+
		   	'<td class="am-padding-0"><input id="ext3_'+serial+'" name="ext3" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>'+
		   	'<td class="am-padding-0"><input id="ext4_'+serial+'" name="ext4" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>'+
		   	'<td class="am-padding-0"><input id="ext5_'+serial+'" name="ext5" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>'+
		   	'<td class="am-padding-0"><input id="ext6_'+serial+'" name="ext6" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>'+
		   	'<td class="am-padding-0"><input id="ext7_'+serial+'" name="ext7" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>'+
		   	'<td class="am-padding-0"><input id="ext8_'+serial+'" name="ext8" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>'+
		   	'<td class="am-padding-0"><input id="ext9_'+serial+'" name="ext9" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>'+
		   	'<td class="am-padding-0"><input id="ext10_'+serial+'" name="ext10" onchange="regValue(this.id,this.value);" onblur="currencyTo(this.id,this.value);" class="am-form-field app-inputPM0 app-inputContent" ></td>'+
	    '</tr>'
		);
	serial=serial+1;
	$("#serial").val(serial);
	$("#next-bar-code-confirm-msg").html('编码为：<span id="code">'+deviceId+'</span><br><span>继续扫码点击下一个，结束点击完成</span>');
	$('#next-bar-code-confirm').modal(
			{
				relatedTarget : this,
				onConfirm: function() {
					if(serial<=24){
						$("#modal-next-step").on('click',saomiao2(serial));
					}else{
						modalAlert("24个蓄电池单体添加完成",null);
					}
			        },
			     onCancel: function() {
			    	
			        }
			});

};
function saveDischargeDetailBarCode(deviceId,recordId,serial) {
	$.post("../hldischarge/saveDischargeDetailBarCode.json", {
		recordId : recordId,
		barCode:deviceId,
		serialNumber:serial
	}, function(json) {
		
	}, "json");
}

function currencyTo(id,obj){
	var singletonVoltage=$("#singletonVoltage").val();
	var str=obj;
	if(str == null || str==''){
		return;
	}
	if(singletonVoltage=="2V"){
		str = formatDot(str,1);
	}else if(singletonVoltage=="12V"){
		str = formatDot(str,2);
	}
	var s="#"+id;
	$(s).val(str);
}

function checkVoltage(id,str){
	if(str == null || str==''){
		return;
	}
	str = formatDot(str,2);
	$("#"+id).val(str);
}

function formatDot(num,bit){
	if(num.indexOf("-") > -1 && num.indexOf(".") < 0){
		var str1=num.substr(1,bit);
		var str2=num.substr(bit+1,4-bit);
		num="-"+str1+"."+str2;
	}else if((num.indexOf("-") > -1&&num.indexOf(".") > -1) || num.indexOf(".") > -1){
		num=num;
	}else{
		if(num.length>2){
			var str1=num.substr(0,bit);
			var str2=num.substr(bit,4-bit);
			num=str1+"."+str2;
		}
		
	}
	return num;
}

function saomiao3(oldBarCode) {
	var recordId=$("#recordId").val();
	// 扫描二维码
	wx.scanQRCode({
		needResult : 1,
		scanType : [ "qrCode", "barCode" ],
		success : function(res) {
			var indexss = res.resultStr.indexOf(",") + 1;
			var deviceId = res.resultStr.substr(indexss);
			if (deviceId.length == 13 || deviceId.length == 38 || deviceId.length == 42) {
				showLoading();
				setTimeout(function() {
					check(deviceId,oldBarCode,recordId);
				}, 5000);
			} else {
				closeLoading();
				modalAlert("此条码是无效条码", null);
				
			}
		},
		cancel : function(res) {
		},
		complete : function(res) {
		},
		fail : function(res) {
		}
	});
};    
function check(deviceId,oldBarCode,recordId) {// 查询是否子条码已经存在
	$.post("../hldischarge/judgeBarCodeExist.json", {
		barCode : deviceId,
		recordId:recordId
	}, function(json) {
		if (json.succ) {
			modalAlert(json.msg, null);
			closeLoading();
		} else {
				updateBarCode(deviceId,oldBarCode,recordId);
			
				}
			}, "json");
};
function updateBarCode(deviceId,oldBarCode,recordId){
	$.post("../hldischarge/updateDischargeDetailBarCode.json", {
		barCode : deviceId,
		oldBarCode:oldBarCode,
		recordId:recordId
	}, function(json) {
		  showLoading();
		  //var recordId=$("#recordId").val();
		  var planName=$("#planName").val();
		  var batteryProductDate=$("#batteryProductDate").val();
		  var testDate=$("#testDate").val();
		  var testUser=$("#testUser").val();
		  var brand=$("#brand").val();
		  var standardCapacity=$("#standardCapacity").val();
		  var dischargeRate=$("#dischargeRate").val();
		  var dischargeCurrent=$("#dischargeCurrent").val();
		  var provinceId=$("#provinceId").val();
		  var cityId=$("#cityId").val();
		  var countyId=$("#countyId").val();
		  var singletonVoltage=$("#singletonVoltage").val();
		  $.ajax({
			  type: "POST",
	 		   	  url: $("#ctx").val()+"/hldischarge/updateDischargeRecord.json",
	 		      async:false,
	 		      data:{
	 			  	planName:planName,
	 				recordId:recordId,
	 				batteryProductDate:batteryProductDate,
	 				testDate:testDate,
	 				testUser:testUser,
	 				brand:brand,
	 				standardCapacity:standardCapacity,
	 				dischargeRate:dischargeRate,
	 				dischargeCurrent:dischargeCurrent,
	 				provinceId:provinceId,
	 				cityId:cityId,
	 				countyId:countyId,
	 				singletonVoltage:singletonVoltage
			   	  },
	 		      error: function (msg) {
	 			   //alert(JSON.stringify(msg));
	     		  },
 		          success: function (json) {
 		    	     //window.location.reload();
 		        	 var saveOrUpdate=$("#saveOrUpdate").val();
 			         window.location.href=$("#ctx").val()+"/hldischarge/toFillDischarge?selectId=1&saveOrUpdate="+saveOrUpdate+"&recordId="+recordId;
 		          }
		        });
		
		//window.location.href="../hldischarge/toFillDischarge?selectId=1&saveOrUpdate=2&recordId="+recordId;
			}, "json");
}

function getCity(){
	 $.getJSON("${ctx}/common/getCity.json?province="+$("#provinceId").val(), function(data){
		 var html = "";
		 $.each(data.orgs, function(i,item){
			  html += '<option parentId="'+item+'" value="'+ item +'">'+ item +'</option>';
		 }); 
		   $("#cityId").html(html);
		});
	}

function getCounty(){
	 $.getJSON("${ctx}/common/getCountry.json?province="+$("#provinceId").val()+"&city="+$("#cityId").val(), function(data){
		 var html = "";
		 $.each(data.orgs, function(i,item){
			 html += '<option parentId="'+item.id+'" value="'+ item.name +'">'+ item.name +'</option>';
		 }); 
		   $("#countyId").html(html);
		}); 
	}
</script>
