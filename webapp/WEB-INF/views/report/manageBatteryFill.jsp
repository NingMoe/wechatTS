<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
<script type="text/javascript">
	
	
</script>
<div class="am-cf">
<form id="fbean" class="am-form" data-am-validator  modelAttribute="manageBatteryRecord" method="POST" action="${ctx}/report/addManageBatteryRecord" onSubmit="return validate_form(this)">
	<input type="hidden" name="source" value="${source}" >
	<input type="hidden" name="selectId" value="2" >
	<input type="hidden" id="mPprovinceId" value='${sessionScope.s_user.provinceId}'>
	
	<table class="am-table app-tb">
		<tbody>
			<tr>
				<th style="vertical-align: middle;" colspan="2">
					${dateString} 
				</th>
			</tr>
			<tr>
				<td style="vertical-align: middle;width: 180px;">计划整治站点总数：
					<br/><span style="font-size:12px;">上周数据${manageBatteryRecordpre.planCount}</span>
				</td>
				<td>
					<div class="am-input-group">
						<input type="number"  name="planCount" class="am-form-field" value="${manageBatteryRecord.planCount}" placeholder="点击输入"  pattern="^[0-9]{1,10}$"  required>
						<span class="am-input-group-label">座</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">计划整治总费用：
					<br/><span style="font-size:12px;">上周数据${manageBatteryRecordpre.planCost}</span>
				</td>
				<td>
					<div class="am-input-group">
						<input type="number"  name="planCost" class="am-form-field" value="${manageBatteryRecord.planCost}"  placeholder="点击输入"  pattern="^[0-9]{1,10}$" required />
						<span class="am-input-group-label">元</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">本周计划整治站点数量：
					<br/><span style="font-size:12px;">上周数据${manageBatteryRecordpre.planCountWeek}</span>
				</td>
				<td>
					<div class="am-input-group">
						<input type="number" value="${manageBatteryRecord.planCountWeek}" name="planCountWeek" class="am-form-field" placeholder="点击输入" id="planCountWeek"  pattern="^[0-9]{1,10}$" required>
						<span class="am-input-group-label">座</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">本周实际整治站点数量：
					<br/><span style="font-size:12px;">上周数据${manageBatteryRecordpre.actCountWeek}</span>
				</td>
				<td>
					<div class="am-input-group">
						<input type="number" value="${manageBatteryRecord.actCountWeek}" name="actCountWeek" class="am-form-field" placeholder="点击输入" id="actCountWeek"  pattern="^[0-9]{1,10}$" required>
						<span class="am-input-group-label">座</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">累计开工站点数量：
					<br/><span style="font-size:12px;">上周数据${manageBatteryRecordpre.startSum}</span>
				</td>
				<td>
					<div class="am-input-group">
						<input type="number" value="${manageBatteryRecord.startSum}" name="startSum" class="am-form-field" placeholder="点击输入" id="startSum"  pattern="^[0-9]{1,10}$" required>
						<span class="am-input-group-label">座</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">累计完成站点数量：
					<br/><span style="font-size:12px;">上周数据${manageBatteryRecordpre.completeSum}</span>
				</td>
				<td style="vertical-align: middle">
					<div class="am-input-group">
						<input type="number" value="${manageBatteryRecord.completeSum}" name="completeSum" class="am-form-field" placeholder="点击输入" id="completeSum"   pattern="^[0-9]{1,10}$" required>
						<span class="am-input-group-label">座</span>
					</div>	
				</td>
			</tr>
			<tr>
				<td style="vertical-align: middle">累计支付金额：
					<br/><span style="font-size:12px;">上周数据${manageBatteryRecordpre.paymentAmount}</span>
				</td>
				<td style="vertical-align: middle">
					<div class="am-input-group">
						<input type="number" value="${manageBatteryRecord.paymentAmount}" name="paymentAmount" class="am-form-field" placeholder="点击输入" id="paymentAmount"  pattern="^[0-9]{1,10}$" required>
						<span class="am-input-group-label">元</span>
					</div>	
				</td>
			</tr>
			<c:if test='${"黑龙江省" eq sessionScope.s_user.provinceId && "city" eq source}'>
			
				<tr>
					<td style="vertical-align: middle">累计开工站点数量(PMS线上)：
						<br/><span style="font-size:12px;">上周数据${manageBatteryRecordpre.startSumPms}</span>
					</td>
					<td style="vertical-align: middle">
						<div class="am-input-group">
							<input type="number" value="${manageBatteryRecord.startSumPms}" name="startSumPms" class="am-form-field" placeholder="点击输入" id="startSumPms"  pattern="^[0-9]{1,10}$" required>
							<span class="am-input-group-label">座</span>
						</div>	
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle">累计完工交维站点数量(PMS线上)：
						<br/><span style="font-size:12px;">上周数据${manageBatteryRecordpre.completeSumPms}</span>
					</td>
					<td style="vertical-align: middle">
						<div class="am-input-group">
							<input type="number" value="${manageBatteryRecord.completeSumPms}" name="completeSumPms" class="am-form-field" placeholder="点击输入" id="completeSumPms"  pattern="^[0-9]{1,10}$" required>
							<span class="am-input-group-label">座</span>
						</div>	
					</td>
				</tr>
								<tr>
					<td style="vertical-align: middle">初步决算项目数量：
						<br/><span style="font-size:12px;">上周数据${manageBatteryRecordpre.preliminaryFinalAccounts}</span>
					</td>
					<td style="vertical-align: middle">
						<div class="am-input-group">
							<input type="number" value="${manageBatteryRecord.preliminaryFinalAccounts}" name="preliminaryFinalAccounts" class="am-form-field" placeholder="点击输入" id="preliminaryFinalAccounts"  pattern="^[0-9]{1,10}$" required>
							<span class="am-input-group-label">个</span>
						</div>	
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle">累计转资金额：
						<br/><span style="font-size:12px;">上周数据${manageBatteryRecordpre.cumulativeTransferAmount}</span>
					</td>
					<td style="vertical-align: middle">
						<div class="am-input-group">
							<input type="number" value="${manageBatteryRecord.cumulativeTransferAmount}" name="cumulativeTransferAmount" class="am-form-field" placeholder="点击输入" id="cumulativeTransferAmount"  pattern="^[0-9]{1,10}$" required>
							<span class="am-input-group-label">元</span>
						</div>	
					</td>
				</tr>
			</c:if>
			
			<tr>
				<td style="vertical-align: middle">备注：
					<br/><span style="font-size:12px;"></span>
				</td>
				<td style="vertical-align: middle">
					<input  value="${manageBatteryRecord.mark}" name="mark" class="am-form-field" placeholder="点击输入" id="mark" >
				</td>
			</tr>
			
			<c:if test='${"黑龙江省" eq sessionScope.s_user.provinceId && "city" eq source}'>
			
				<tr>
					<th style="vertical-align: middle;" colspan="2">
						蓄电池报废
					</th>
				</tr>
				<tr>
					<td style="vertical-align: middle">计划整治蓄电池组数：
						<br/><span style="font-size:12px;">上周数据${manageBatteryRecordScrapPre.planBatteryGroupCount}</span>
					</td>
					<td>
						<div class="am-input-group">
							<input type="number"  value="${manageBatteryRecordScrap.planBatteryGroupCount}"  name="planBatteryGroupCount" class="am-form-field" placeholder="点击输入"  pattern="^[0-9]{1,10}$" required />
							<span class="am-input-group-label">组</span>
						</div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle">累计新采购蓄电池组数：
						<br/><span style="font-size:12px;">上周数据${manageBatteryRecordScrapPre.purchaseBatteryGroup}</span>
					</td>
					<td>
						<div class="am-input-group">
							<input type="number" value="${manageBatteryRecordScrap.purchaseBatteryGroup}" name="purchaseBatteryGroup" class="am-form-field" placeholder="点击输入" id="purchaseBatteryGroup"  pattern="^[0-9]{1,10}$" required>
							<span class="am-input-group-label">组</span>
						</div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle">累计退网蓄电池组数：
						<br/><span style="font-size:12px;">上周数据${manageBatteryRecordScrapPre.logoutBatteryGroup}</span>
					</td>
					<td>
						<div class="am-input-group">
							<input type="number" value="${manageBatteryRecordScrap.logoutBatteryGroup}" name="logoutBatteryGroup" class="am-form-field" placeholder="点击输入" id="logoutBatteryGroup"  pattern="^[0-9]{1,10}$" required>
							<span class="am-input-group-label">组</span>
						</div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle">2011年及以后入网使用的蓄电池组数：
						<br/><span style="font-size:12px;">上周数据${manageBatteryRecordScrapPre.after2011UseBatteryGroup}</span>
					</td>
					<td>
						<div class="am-input-group">
							<input type="number" value="${manageBatteryRecordScrap.after2011UseBatteryGroup}" name="after2011UseBatteryGroup" class="am-form-field" placeholder="点击输入" id="after2011UseBatteryGroup"  pattern="^[0-9]{1,10}$" required>
							<span class="am-input-group-label">组</span>
						</div>
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle">2011年前入网使用蓄电池组数：
						<br/><span style="font-size:12px;">上周数据${manageBatteryRecordScrapPre.before2011UseBatteryGroup}</span>
					</td>
					<td style="vertical-align: middle">
						<div class="am-input-group">
							<input type="number" value="${manageBatteryRecordScrap.before2011UseBatteryGroup}" name="before2011UseBatteryGroup" class="am-form-field" placeholder="点击输入" id="before2011UseBatteryGroup"   pattern="^[0-9]{1,10}$" required>
							<span class="am-input-group-label">组</span>
						</div>	
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle">已完成财务报废蓄电池数：
						<br/><span style="font-size:12px;">上周数据${manageBatteryRecordScrapPre.finishScrapBatteryGroup}</span>
					</td>
					<td style="vertical-align: middle">
						<div class="am-input-group">
							<input type="number" value="${manageBatteryRecordScrap.finishScrapBatteryGroup}"  name="finishScrapBatteryGroup" class="am-form-field" placeholder="点击输入" id="finishScrapBatteryGroup"  pattern="^[0-9]{1,10}$" required>
							<span class="am-input-group-label">只</span>
						</div>	
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle">备注：
						<br/><span style="font-size:12px;"></span>
					</td>
					<td style="vertical-align: middle">
						<input  value="${manageBatteryRecordScrap.mark}" name="scrapMark" class="am-form-field" placeholder="点击输入" id="scrapMark" >
					</td>
				</tr>
			</c:if>
			<tr>
				<th style="vertical-align: middle;" colspan="2">
					<c:if test='${null ne manageBatteryRecord && "" ne manageBatteryRecord.fillMan && null ne manageBatteryRecord.fillMan}'>
			    		<c:if test='${"province" eq source || "group" eq source}'>${manageBatteryRecord.provinceid }本周已上报，上报时间${manageBatteryRecord.fillTime }，上报人${manageBatteryRecord.fillMan }，您可修改数据重新上报</c:if><c:if test='${"city" eq source}'>${manageBatteryRecord.cityid }本周已填报，填报时间${manageBatteryRecord.fillTime }，填报人${manageBatteryRecord.fillMan }，您可修改数据重新填报</c:if>
			    	</c:if>
			    	<c:if test='${null eq manageBatteryRecord || "" eq manageBatteryRecord.fillMan || null eq manageBatteryRecord.fillMan}'>
			    		<c:if test='${"province" eq source || "group" eq source}'>${sessionScope.s_user.provinceId }本周未上报</c:if><c:if test='${"city" eq source}'>${sessionScope.s_user.cityId}本周未填报</c:if>
			    	</c:if>
				</th>
			</tr>
		</tbody>
	</table>
	
    <c:if test='${"province" eq source || "group" eq source}'>
		<div class="am-u-sm-6">
			<a class="am-btn am-btn-success am-radius am-btn-block" style="color:white;" href="javascript:submitpage();"><strong>填报</strong></a>
		</div>
		<div class="am-u-sm-6">
			<a class="am-btn am-btn-success am-radius am-btn-block" style="color:white;" href="javascript:toProvincePage();"><strong>导入</strong></a>
		</div>
	    
	</c:if>
    <c:if test='${"city" eq source}'>
    	<div class="am-u-sm-12">
			<a class="am-btn am-btn-success am-radius am-btn-block" style="color:white;" href="javascript:submitpage();"><strong>填报</strong></a>
		</div>
    </c:if>
    
    <div style="height: 10px">&nbsp;</div>
</form>	
</div>
<script>

function validate_form(thisform){
	//var fbean=document.getElementById("fbean");
	//if(thisform){
		//fbean.submit();
		
	//}
	return thisform;
}
function submitpage(){
	$("#fbean").submit();
}

function toProvincePage(){
	$("#selectId2").trigger("click");
}
</script>
