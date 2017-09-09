<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@include file="../commons/tags.jsp"%>
<div align="center">
	<form id="doorSystemForm" class="am-form am-form-horizontal" method="post" data-am-validator>
		<!-- <input type="hidden" name="doorSystemId" value="${doorSystem.doorSystemId }" > -->
		<table class="am-table app-tb">
			<tr>
				<th style="vertical-align: middle;">
					${fillCycle} 
				</th>
			</tr>
			<tr>
				<th style="vertical-align: middle;">
					${fillUser} 
				</th>
				 
			</tr>
			<tr>
				<td>
					本地站址数量（以截止到2015年底新建站和接收的存量站址之和为准）
					<div class="am-form-group am-margin-bottom-xs">
					  <label for="before2015StationRoomNum" class="am-u-sm-4 am-form-label">机房（座）</label>
					  <div class="am-u-sm-8">
						<input id="before2015StationRoomNum" name="before2015StationRoomNum" value="${doorSystem.before2015StationRoomNum }" placeholder="点击输入机房数量" class="am-form-field" maxlength="10" pattern="^([1-9]{1}[\d]{0,9}|0)$" required>
					  </div>
					</div>
					<div class="am-form-group am-margin-bottom-xs">
					  <label for="before2015StationCabineNum" class="am-u-sm-4 am-form-label">机柜（个）</label>
					  <div class="am-u-sm-8">
						<input id="before2015StationCabineNum" name="before2015StationCabineNum" value="${doorSystem.before2015StationCabineNum }" placeholder="点击输入机柜数量" class="am-form-field" maxlength="10" pattern="^([1-9]{1}[\d]{0,9}|0)$" required>
					  </div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					2016年计划实施安装门禁的站址数量
					<div class="am-form-group am-margin-bottom-xs">
					  <label for="planRoomNum2016" class="am-u-sm-4 am-form-label">机房（座）</label>
					  <div class="am-u-sm-8">
						<input id="planRoomNum2016" name="planRoomNum2016" value="${doorSystem.planRoomNum2016 }" placeholder="点击输入机房数量" class="am-form-field" maxlength="10" pattern="^([1-9]{1}[\d]{0,9}|0)$" required>
					  </div>
					</div>
					<div class="am-form-group am-margin-bottom-xs">
					  <label for="planCabineNum2016" class="am-u-sm-4 am-form-label">机柜（个）</label>
					  <div class="am-u-sm-8">
						<input id="planCabineNum2016" name="planCabineNum2016" value="${doorSystem.planCabineNum2016 }" placeholder="点击输入机柜数量" class="am-form-field" maxlength="10" pattern="^([1-9]{1}[\d]{0,9}|0)$" required>
					  </div>
					</div>
				</td>
			</tr>
			
			<tr>
				<td >
					<div class="am-form-group am-margin-bottom-xs" >
					  <label for="procurementMethods" class="am-u-sm-4 am-form-label">采购方式</label>
					  <div class="am-u-sm-8">
						<select id="procurementMethods" name="procurementMethods" data-am-selected>
						<c:if test="${doorSystem != null && doorSystem !=''}">
						  <c:if test="${doorSystem.procurementMethods =='公开招标' }">
							  <option value="公开招标" selected>公开招标</option>
							  <option value="公开比选">公开比选</option>
							  <option value="邀请招标">邀请招标</option>
							  <option value="邀请比选">邀请比选</option>
							  <option value="竞争性谈判">竞争性谈判</option>
							  <option value="其他">其他</option>
						  </c:if>
						  <c:if test="${doorSystem.procurementMethods =='公开比选' }">
							  <option value="公开招标">公开招标</option>
							  <option value="公开比选" selected>公开比选</option>
							  <option value="邀请招标">邀请招标</option>
							  <option value="邀请比选">邀请比选</option>
							  <option value="竞争性谈判">竞争性谈判</option>
							  <option value="其他">其他</option>
						  </c:if>
						  <c:if test="${doorSystem.procurementMethods =='邀请招标' }">
							  <option value="公开招标">公开招标</option>
							  <option value="公开比选">公开比选</option>
							  <option value="邀请招标" selected>邀请招标</option>
							   <option value="邀请比选">邀请比选</option>
							  <option value="竞争性谈判">竞争性谈判</option>
							  <option value="其他">其他</option>
						  </c:if>
						  <c:if test="${doorSystem.procurementMethods =='邀请比选' }">
							  <option value="公开招标">公开招标</option>
							  <option value="公开比选">公开比选</option>
							  <option value="邀请招标">邀请招标</option>
							  <option value="邀请比选" selected>邀请比选</option>
							  <option value="竞争性谈判">竞争性谈判</option>
							  <option value="其他">其他</option>
						  </c:if>
						  <c:if test="${doorSystem.procurementMethods =='竞争性谈判' }">
							  <option value="公开招标">公开招标</option>
							  <option value="公开比选">公开比选</option>
							  <option value="邀请招标">邀请招标</option>
							  <option value="邀请比选" >邀请比选</option>
							  <option value="竞争性谈判" selected>竞争性谈判</option>
							  <option value="其他">其他</option>
						  </c:if>
						  <c:if test="${doorSystem.procurementMethods =='其他' }">
							  <option value="公开招标">公开招标</option>
							  <option value="公开比选">公开比选</option>
							  <option value="邀请招标">邀请招标</option>
							  <option value="邀请比选" >邀请比选</option>
							  <option value="竞争性谈判">竞争性谈判</option>
							  <option value="其他" selected>其他</option>
						  </c:if>
						  </c:if>
						  <c:if test="${doorSystem == null ||  doorSystem ==''}">
						  	  <option value="公开招标">公开招标</option>
							  <option value="公开比选">公开比选</option>
							  <option value="邀请招标">邀请招标</option>
							  <option value="邀请比选" >邀请比选</option>
							  <option value="竞争性谈判">竞争性谈判</option>
							  <option value="其他">其他</option>
						  </c:if>
						</select>
					  </div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					已完成采购站址数量（以下达中标通知书或采购订单为标识）
					<div class="am-form-group am-margin-bottom-xs">
					  <label for="finishProcurementRoomNum" class="am-u-sm-4 am-form-label">机房（座）</label>
					  <div class="am-u-sm-8">
						<input id="finishProcurementRoomNum" name="finishProcurementRoomNum" value="${doorSystem.finishProcurementRoomNum }" placeholder="点击输入机房数量" class="am-form-field" maxlength="10" pattern="^([1-9]{1}[\d]{0,9}|0)$" required>
					  </div>
					</div>
					<div class="am-form-group am-margin-bottom-xs">
					  <label for="finishProcurementCabineNum" class="am-u-sm-4 am-form-label">机柜（个）</label>
					  <div class="am-u-sm-8">
						<input id="finishProcurementCabineNum" name="finishProcurementCabineNum" value="${doorSystem.finishProcurementCabineNum }" placeholder="点击输入机柜数量" class="am-form-field" maxlength="10" pattern="^([1-9]{1}[\d]{0,9}|0)$" required>
					  </div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="am-form-group am-margin-bottom-xs">
					  <label for="implementationPlan" class="am-u-sm-4 am-form-label">计划实施方案</label>
					  <div class="am-u-sm-8">
						<select id="implementationPlan" name="implementationPlan" data-am-selected>
						<c:if test="${doorSystem != null && doorSystem !=''}">
						 <c:if test="${doorSystem.implementationPlan =='方案1' }">
							  <option value="方案1" selected>方案1</option>
							  <option value="方案2">方案2</option>
							  <option value="方案3">方案3</option>
							  <option value="方案1+2">方案1+2</option>
							  <option value="方案1+3">方案1+3</option>
							  <option value="方案2+3">方案2+3</option>
							  <option value="方案1+2+3">方案1+2+3</option>
						  </c:if>
						  <c:if test="${doorSystem.implementationPlan =='方案2' }">
							  <option value="方案1">方案1</option>
							  <option value="方案2" selected>方案2</option>
							  <option value="方案3">方案3</option>
							  <option value="方案1+2">方案1+2</option>
							  <option value="方案1+3">方案1+3</option>
							  <option value="方案2+3">方案2+3</option>
							  <option value="方案1+2+3">方案1+2+3</option>
						  </c:if>
						  <c:if test="${doorSystem.implementationPlan =='方案3' }">
							  <option value="方案1">方案1</option>
							  <option value="方案2">方案2</option>
							  <option value="方案3" selected>方案3</option>
							  <option value="方案1+2">方案1+2</option>
							  <option value="方案1+3">方案1+3</option>
							  <option value="方案2+3">方案2+3</option>
							  <option value="方案1+2+3">方案1+2+3</option>
						  </c:if>
						  <c:if test="${doorSystem.implementationPlan =='方案1+2' }">
							  <option value="方案1">方案1</option>
							  <option value="方案2">方案2</option>
							  <option value="方案3">方案3</option>
							  <option value="方案1+2" selected>方案1+2</option>
							  <option value="方案1+3">方案1+3</option>
							  <option value="方案2+3">方案2+3</option>
							  <option value="方案1+2+3">方案1+2+3</option>
						  </c:if>
						  <c:if test="${doorSystem.implementationPlan =='方案1+3' }">
							  <option value="方案1">方案1</option>
							  <option value="方案2">方案2</option>
							  <option value="方案3">方案3</option>
							  <option value="方案1+2">方案1+2</option>
							  <option value="方案1+3" selected>方案1+3</option>
							  <option value="方案2+3">方案2+3</option>
							  <option value="方案1+2+3">方案1+2+3</option>
						  </c:if>
						  <c:if test="${doorSystem.implementationPlan =='方案2+3' }">
							  <option value="方案1">方案1</option>
							  <option value="方案2">方案2</option>
							  <option value="方案3">方案3</option>
							  <option value="方案1+2">方案1+2</option>
							  <option value="方案1+3">方案1+3</option>
							  <option value="方案2+3" selected>方案2+3</option>
							  <option value="方案1+2+3">方案1+2+3</option>
						  </c:if>
						  <c:if test="${doorSystem.implementationPlan =='方案1+2+3' }">
							  <option value="方案1">方案1</option>
							  <option value="方案2">方案2</option>
							  <option value="方案3">方案3</option>
							  <option value="方案1+2">方案1+2</option>
							  <option value="方案1+3">方案1+3</option>
							  <option value="方案2+3">方案2+3</option>
							  <option value="方案1+2+3" selected>方案1+2+3</option>
						  </c:if>
						</c:if>
						 <c:if test="${doorSystem == null || doorSystem == ''}">
						 	  <option value="方案1">方案1</option>
							  <option value="方案2">方案2</option>
							  <option value="方案3">方案3</option>
							  <option value="方案1+2">方案1+2</option>
							  <option value="方案1+3">方案1+3</option>
							  <option value="方案2+3">方案2+3</option>
							  <option value="方案1+2+3">方案1+2+3</option>
						 </c:if>
						</select>
					  </div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					目前已完成安装站址数量
					<div class="am-form-group am-margin-bottom-xs">
					  <label for="finishInstalledRoomNum" class="am-u-sm-4 am-form-label">机房（座）</label>
					  <div class="am-u-sm-8">
						<input id="finishInstalledRoomNum" name=finishInstalledRoomNum value="${doorSystem.finishInstalledRoomNum }" placeholder="点击输入机房数量" class="am-form-field" maxlength="10" pattern="^([1-9]{1}[\d]{0,9}|0)$" required>
					  </div>
					</div>
					<div class="am-form-group am-margin-bottom-xs">
					  <label for="finishInstalledCabineNum" class="am-u-sm-4 am-form-label">机柜（个）</label>
					  <div class="am-u-sm-8">
						<input id="finishInstalledCabineNum" name="finishInstalledCabineNum" value="${doorSystem.finishInstalledCabineNum }" placeholder="点击输入机柜数量" class="am-form-field" maxlength="10" pattern="^([1-9]{1}[\d]{0,9}|0)$" required>
					  </div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					已与FSU同步开通
					<div class="am-form-group am-margin-bottom-xs">
					  <label for="synchronousWithFsuRoomNum" class="am-u-sm-4 am-form-label">机房（座）</label>
					  <div class="am-u-sm-8">
						<input id="synchronousWithFsuRoomNum" name="synchronousWithFsuRoomNum" value="${doorSystem.synchronousWithFsuRoomNum }" placeholder="点击输入机房数量" class="am-form-field" maxlength="10" pattern="^([1-9]{1}[\d]{0,9}|0)$" required>
					  </div>
					</div>
					<div class="am-form-group am-margin-bottom-xs">
					  <label for="synchronousWithFsuCabinNum" class="am-u-sm-4 am-form-label">机柜（个）</label>
					  <div class="am-u-sm-8">
						<input id="synchronousWithFsuCabinNum" name="synchronousWithFsuCabinNum" value="${doorSystem.synchronousWithFsuCabinNum }" placeholder="点击输入机柜数量" class="am-form-field" maxlength="10" pattern="^([1-9]{1}[\d]{0,9}|0)$" required>
					  </div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="am-form-group am-margin-bottom-xs">
					  <label for="email" class="am-u-sm-4 am-form-label">邮箱</label>
					  <div class="am-u-sm-8">
						<input id="email" name="email" value="${doorSystem.email }" type="email" placeholder="点击输入邮箱" class="am-form-field" maxlength="200" required>
					  </div>
					</div>
				</td>
			</tr>
			<!-- <tr>
				<td>
					<div class="am-form-group am-margin-bottom-xs">
					  <label for="phoneNumber" class="am-u-sm-4 am-form-label">联系方式</label>
					  <div class="am-u-sm-8">
					  	<c:if test="${doorSystem == null || doorSystem ==''}"><input id="phoneNumber" name="phoneNumber" value="${userInfo.phoneNo }" placeholder="点击输入联系方式" class="am-form-field" maxlength="200" pattern="^1((3|5|4|8){1}\d{1}|70)\d{8}$" required></c:if>
						<c:if test="${doorSystem != null && doorSystem !=''}"><input id="phoneNumber" name="phoneNumber" value="${doorSystem.phoneNumber }" placeholder="点击输入联系方式" class="am-form-field" maxlength="200" pattern="^1((3|5|4|8){1}\d{1}|70)\d{8}$" required></c:if>
					  </div>
					</div>
				</td>
			</tr> -->
			<tr>
				<td>
					备注
					<textarea id="mark" name="mark" rows="4" class="am-form-field" placeholder="备注" maxlength="340">${doorSystem.mark }</textarea>
				</td>
			</tr>
		</table>
		<div class="app-div-btn-tool am-margin-bottom">
			<a id="submitdoorSystemInfo" href="javascript:submitdoorSystemInfo();" class="am-btn am-btn-success am-radius am-btn-block"> <strong>保存</strong> </a>
		</div>
	</form>
</div>
<script type="text/javascript">
	function submitdoorSystemInfo(){
		$.when($('#doorSystemForm').validator('isFormValid')).then(function(data) {
			if(data){
				showLoading();
				$("#submitdoorSystemInfo").removeAttr("href");
				$.ajax({type:'POST',dataType:'json',url:'${ctx}/doorSystem/addOrUpdate.json',
					data:$('#doorSystemForm').serialize(),
					success:function(json){
						$("#submitdoorSystemInfo").attr("href","javascript:submitdoorSystemInfo();");
						if(json.succ){
							modalAlert("保存成功",function(){
								window.location.href="${ctx}/doorSystem/main?selectId=2";
								closeLoading();
							});
							
						}
					}
				});
			}else{
				
			}
		}, function() {
			console.log("...");
		});
	}
</script>
