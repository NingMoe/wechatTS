<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div style="height: 10px">&nbsp;</div>
<div class="amz-toolbar" id="amz-toolbar" style="filter:alpha(opacity:80);opacity:0.86;width:48px;height:58px;">
	<a href="<%=request.getContextPath()%>/question/selectQuestionType" title="常见问题" class="am-icon-faq am-icon-btn am-icon-question-circle"></a>
</div>
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
<script type="text/javascript">   
/*var isdrag=false;   
var tx,ty,x,y;    
$(function(){  
    document.getElementById("amz-toolbar").addEventListener('touchend',function(e){  
        isdrag = false;  
    });  
    document.getElementById("amz-toolbar").addEventListener('touchstart',selectmouse);  
    document.getElementById("amz-toolbar").addEventListener('touchmove',movemouse); 
    
});  
function movemouse(e){   
  	if (isdrag){ 
		e.preventDefault();
		var width = window.screen.width;
		var height = window.screen.height;
		var n = tx + e.touches[0].pageX - x;  
		var h = ty + e.touches[0].pageY - y;  
		if(n < 0) {
			n = 0;
		}
		if(n > width - 208) {
			n = width - 208;
		}
		if(h < 41) {
			h = 41;
		}
		if(h > height - 449) {
			h = height - 449;
		}
		$("#amz-toolbar").css("left",n);  
		$("#amz-toolbar").css("top",h);
		return false;   
   	}   
}   
 
function selectmouse(e){
	var height = window.screen.height;
	var width = window.screen.width;
	isdrag = true;   
	//e.preventDefault();
	var left = document.getElementById("amz-toolbar").style.left;
	var top = document.getElementById("amz-toolbar").style.top;
	tx = parseInt(document.getElementById("amz-toolbar").style.left+0);  
	ty = parseInt(document.getElementById("amz-toolbar").style.top+0); 
	x = e.touches[0].pageX; 
	y = e.touches[0].pageY;
	if(left == '' || left == 'undefined' || left == 'null') {
		tx = x;
	}
	if(top == '' || top == 'undefined' || top == 'null') {
		ty = y;
	}
	return false;   
} */
</script>   