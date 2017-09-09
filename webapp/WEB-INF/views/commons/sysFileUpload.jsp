<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="am-form-group am-form-file">
	<a type="button" class="am-btn am-btn-danger am-radius am-btn-block"><i class="am-icon-cloud-upload"></i>添加上传文件</a>
	<input id="doc-form-file" onchange="myFileTypeOnChange(this)" type="file">
</div>
<div id="file-list"></div>
<div  data-am-widget="list_news" class="am-list-news am-list-news-default am-animation-fade app-tabs-con am-margin-horizontal-0 ">
  <div class="am-list-news-bd">
    <ul id="fileulList" class="am-list">
    </ul>
  </div>
</div>
<script>
	function myFileTypeOnChange(file){
		readFile(file.files,0,function(index,base64Str){
			//console.log(file.files[index].name+","+index+","+base64Str);
			var fileName=file.files[index].name;
			//var result =/\.[^\.]+/.exec(fileName);
			var ldot = fileName.lastIndexOf(".");
		    var type = fileName.substring(ldot + 1);
			if(type != "jpg" && type != "gif" && type != "png" && type != "jpeg" && type != "bmp" && type != "doc" && type != "docx" && type != "xls" && type != "xlsx" && type != "txt"){
				modalAlert("不符合附件上传格式，请重新上传", null);
				return;
			}
			$('#fileulList').append('<li class="am-g am-list-item-desced am-padding-horizontal-xs">'+
							    		'<div class="am-u-sm-2 am-list-thumb"><a onclick="removeFileContent(this)" class="am-btn am-btn-danger am-btn-sm" style="padding:2px;">删除</a></div>'+
										'<div class="am-u-sm-10 am-list-main am-text-truncate">'+file.files[index].name +'</div>'+
										'<input class="myFileUpload" name="myFileUpload|'+file.files[index].name+'" value="'+base64Str+'" type="hidden">'+
						    		'</li>');
		});
	}
    function removeFileContent(el){
    	$(el).parents('li').remove();
    }
    function readFile(files,index,callBack){
    	if(index < files.length ) {
	    	var reader = new FileReader();
	        reader.onload = function(e) {
				callBack(index,e.target.result);
				readFile(files,++index,callBack);
			}
	        reader.readAsDataURL(files[index]);
    	}
    }
</script>