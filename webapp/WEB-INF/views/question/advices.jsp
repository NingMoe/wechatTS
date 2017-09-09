<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../commons/tags.jsp"%>
	<input type="hidden" name="selectId" value="2" >
	<input id="groupName" name="groupName" value="${groupName}" type="hidden"/>
	<input id="nickName" name="nickName" value="${nickName}" type="hidden"/>
	<div class="am-cf am-margin-bottom">
		<table id="questionList" class="am-table" style="padding:0px;margin: 0px;">
		</table>
	</div>
	<a id="loadMore" href="javascript:loadMore();" class="am-btn am-btn-default am-btn-block am-margin-bottom" data-am-scrollspy><i class="am-icon-arrow-down"></i>加载更多</a>
<script type="text/javascript">
var pageCurrent=1;
var pageLength=5;
var totalPage=0;
function reloadTable(){
	pageCurrent=1;
	totalPage=0;
	$("#questionList").html("");
	loadData();
}

function loadMore(){
	if(totalPage!=pageCurrent){
		pageCurrent=pageCurrent+1;
		loadData();
	}else{
		$('#loadMore').hide();
	}
}
function loadData(){
	showLoading();
	$.ajax({dataType:'json',url:'${ctx}/question/getQuestionList.json?pageCurrent='+pageCurrent+'&pageLength='+pageLength,
		success:function(data){
			totalPage=data.totalPage;
			var coldataObj=data.questionList;
			var coldataStr="";
			
			if(coldataObj){
				//$("#questionList").html("");
				for(var i=0;i<coldataObj.length;i++){
					var obj=coldataObj[i];
					coldataStr= '<tr onclick="toDetail(&apos;'+obj.questionId+'&apos;)" style="background: #fff;"><td colspan="2" style="border:none;"> 建议标题：'+obj.questionTitle+'</td></tr>'+
								'<tr onclick="toDetail(&apos;'+obj.questionId+'&apos;)" style="background: #fff;"><td colspan="2" style="border:none;"> 填报时间：'+obj.fillDate+'</td></tr>'+
								'<tr onclick="toDetail(&apos;'+obj.questionId+'&apos;)" style="background: #fff;"><td colspan="2" style="border:none;"> 联系方式：'+obj.phoneNumber+'</td></tr>';
								if(obj.groupName != '第一象限' && obj.groupName != null){
									coldataStr+='<tr onclick="toDetail(&apos;'+obj.questionId+'&apos;)" style="background: #fff;"><td colspan="2" style="border:none;"> 归属群：'+obj.groupName+'</td></tr>';
								}
								coldataStr+='<tr onclick="toDetail(&apos;'+obj.questionId+'&apos;)" style="background: #fff;"><td style="border:none;border-bottom: 1px solid #ddd;"> 填报人员：'+obj.fillUser+'</td><td style="border:none;border-bottom: 1px solid #ddd;"> 处理状态：<span class="am-badge '+('未处理'==obj.questionStatus?'am-badge-warning':'am-badge-success')+' am-text-sm">'+obj.questionStatus+'</span></td></tr>';
								
					$("#questionList").append(coldataStr);
				}
			}
			if(totalPage==pageCurrent){
				$('#loadMore').hide();
			}
			
		},complete:function(){
			closeLoading();
		}
	});
}
reloadTable();

function toDetail(questionId){
	var groupName=$("#groupName").val();
	var nickName=$("#nickName").val();
	window.location.href='${ctx}/question/toDetail?questionId='+questionId+"&group="+groupName+"&nickName="+nickName;
}
$('#loadMore').on('inview.scrollspy.amui', function() {
	loadMore();
});
</script>