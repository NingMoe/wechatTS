<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<% request.setAttribute("ctx", request.getContextPath()); %>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<input type="hidden" id="ctx"  value="${ctx}"/>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" href="http://cdn.amazeui.org/amazeui/2.4.0/css/amazeui.min.css"/>
<link rel="stylesheet" href="${ctx}/assets/css/amaze.min.css">
<link rel="stylesheet" href="${ctx}/assets/css/app.css?v=1104">

 <!--[if lt IE 9]>
<script src="http://libs.baidu.com/jquery/1.11.1/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="${ctx}/assets/js/polyfill/rem.min.js"></script>
<script src="${ctx}/assets/js/polyfill/respond.min.js"></script>
<script src="${ctx}/assets/js/amazeui.legacy.js"></script>
<![endif]-->
<!--[if (gte IE 9)|!(IE)]><!-->
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="http://s.amazeui.org/assets/2.x/js/amazeui.min.js?v=ig3bx9nr"></script>
<!--<![endif]-->
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>

<script src="${ctx}/assets/js/echo.min.js"></script>
<script src="${ctx}/assets/js/wechatApp.js?v=0528"></script>
<script src="${ctx}/assets/js/baiduMapTool.js"></script>
<div id="overlay"></div>
<div class="am-modal am-modal-loading" tabindex="-1" id="modal-loading">
  <button type="button" class="am-btn am-radius am-modal-dialog" style="width:200px;background-color: black;filter:alpha(opacity:60);opacity:0.6;">
    <div class="am-modal-hd" style="font-size: 14px;color: white;" id="modal-loading-msg">正在载入...</div>
    <div class="am-modal-bd">
      <span class="am-icon-spinner am-icon-spin"></span>
    </div>
  </button>
</div>
<div class="am-modal am-modal-alert" tabindex="-1" id="modal-alert">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">提示</div>
    <div class="am-modal-bd" id="modal-alert-msg">
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-confirm>确定</span>
    </div>
  </div>
</div>
<div class="am-modal am-modal-confirm" tabindex="-1" id="app-modal-confirm">
	<div class="am-modal-dialog">
		<div class="am-modal-hd" id="app-modal-confirm-msg"></div>
		<div class="am-modal-footer">
			<span class="am-modal-btn" data-am-modal-cancel>取消</span> 
			<span class="am-modal-btn" data-am-modal-confirm>确定</span>
		</div>
	</div>
</div>


<div class="am-modal am-modal-confirm" tabindex="-1" id="next-bar-code-confirm" style="border:2px solid #CCC;">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">提示</div>
    <div class="am-modal-bd" id="next-bar-code-confirm-msg">
    	
    </div>
    <div class="am-modal-footer">
		<span class="am-modal-btn" data-am-modal-cancel>完成</span>
		<span id ="modal-next-step" class="am-modal-btn" data-am-modal-confirm>下一个</span>
    </div>
  </div>
</div>

<div class="am-modal am-modal-confirm" tabindex="-1" id="update-bar-code-confirm" style="border:2px solid #CCC;">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">提示
    <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>
    </div>
    <div class="am-modal-bd" id="update-bar-code-confirm-msg">
    	
    </div>
    <div class="am-modal-footer">
		<span class="am-modal-btn" data-am-modal-confirm>更换条码</span>
		<span id ="delete-bar-code" class="am-modal-btn" data-am-modal-cancel>删除条码</span>
    </div>
  </div>
</div>

<div class="am-modal am-modal-prompt" tabindex="-1" id="send-mail-prompt">
  <div class="am-modal-dialog">
    <div class="am-modal-hd">发送邮件</div>
    <div class="am-modal-bd">
      <input id="mail" type="email" class="am-modal-prompt-input" placeholder="输入邮箱地址" required>
    </div>
    <div class="am-modal-footer">
      <span class="am-modal-btn" data-am-modal-confirm>发送</span>
      <span class="am-modal-btn" data-am-modal-cancel>取消</span>
    </div>
  </div>
</div>