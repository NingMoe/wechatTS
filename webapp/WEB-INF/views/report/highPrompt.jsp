<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% request.setAttribute("ctx", request.getContextPath()); %>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />

<div class="am-modal am-modal-prompt" tabindex="-1" id="response-prompt" style="top:30%;">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">
		
	</div>
    <div class="am-modal-bd" style="height: 151px;padding-bottom: 0px;">
    	<table class="am-table">
    		<tr>
    			<td style="vertical-align:middle;padding:0px;width: 70px;" >问题原因：</td>
				<td style="vertical-align:middle">
					<textarea id="reason" rows="4" class="am-form-field" name="reason"  placeholder="输入原因" maxlength="340"  required></textarea>	
				</td>
    		</tr>
    		<tr>
    			<td style="vertical-align:middle;padding:0px;width: 70px;" >是否解决：</td>
				<td class="am-padding-0" style="vertical-align:middle">
					<input id="switch-state" name="handleWidth"  type="checkbox" data-size="sm" data-am-switch data-on-text="是" data-off-text="否" data-on-color="success" data-off-color="danger">
				</td>
    		</tr>
    	</table>	
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-confirm>提交</span>
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
    </div>
  </div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="response-power-prompt" style="top:30%;">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">
	</div>
    <div class="am-modal-bd" style="height: 232px;padding-bottom: 0px;">
    	<table class="am-table">
    		<tr>
    			<td style="vertical-align:middle;padding:0px;width: 70px;" >超过原因：</td>
				<td style="vertical-align:middle">
					<textarea id="reasonPower" rows="4" class="am-form-field" name="reasonPower"  placeholder="输入原因" maxlength="340"  required></textarea>	
				</td>
    		</tr>
    		<tr>
    			<td style="vertical-align:middle;padding:0px;width: 70px;" >解决办法：</td>
				<td style="vertical-align:middle">
					<textarea id="solution" rows="4" class="am-form-field" name="solution"  placeholder="输入解决办法" maxlength="340"  required></textarea>	
				</td>
    		</tr>
    	</table>	
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-confirm>提交</span>
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
    </div>
  </div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="response-bill-prompt" style="top:35%;">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">
	</div>
    <div class="am-modal-bd" style=" padding-bottom: 0px;height: 116px;">
    	<table class="am-table">
    		<tr>
    			<td style="vertical-align:middle;padding:0px;width: 90px;" >未处理原因：</td>
				<td style="vertical-align:middle">
					<textarea id="reasonBill" rows="4" class="am-form-field" name="reasonBill"  placeholder="输入原因" maxlength="340"  required></textarea>	
				</td>
    		</tr>
    	</table>	
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-confirm>提交</span>
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
    </div>
  </div>
</div>