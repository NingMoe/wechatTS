(function($) {
	'use strict';

	$(function() {
		var $fullText = $('.admin-fullText');
		$('#admin-fullscreen').on('click', function() {
			$.AMUI.fullscreen.toggle();
		});

		$(document).on($.AMUI.fullscreen.raw.fullscreenchange, function() {
			$fullText.text($.AMUI.fullscreen.isFullscreen ? '退出全屏' : '开启全屏');
		});
	});
})(jQuery);

function gohistory(refresh) {
	if (refresh) {
		window.location.href = document.referrer;// 返回上一页并刷新
	} else {
		window.history.go(-1);
	}
}

function initTabs(initCallback, tabClickCallback) {
	$(".app-tabs-title").children().on("click",function() {
		// 用到了4个class
		// app-tabs-head //和app-tabs-con同级别
		// --app-tabs-title // 在app-tabs-head 下任何位置
		
		// app-tabs-bd //和app-tabs-head同级别
		// --app-tabs-con // 在app-tabs-bd 下任何位置

		$(this).siblings(".app-tabs-selected").removeClass();
		$(this).addClass("am-text-primary app-tabs-selected");
		var index = $(this).index();

		var $appTabsBd = $(this).parents(".app-tabs-head").nextAll(".app-tabs-bd:first");
		var $appTabsConEqIndex = $appTabsBd.find(".app-tabs-con:eq("+ index + ")");

		// $appTabsConEqIndex.siblings().removeClass("am-animation-slide-right
		// am-animation-slide-left");
		// $appTabsConEqIndex.nextAll().addClass("am-animation-slide-right");
		// $appTabsConEqIndex.prevAll().addClass("am-animation-slide-left");
		$appTabsConEqIndex.siblings().hide();
		$appTabsConEqIndex.show();
		if (null != tabClickCallback && "null" != tabClickCallback && '' != tabClickCallback) {
			tabClickCallback(index);
		}
	});
	$(".app-tabs-title").children(".app-tabs-selected").trigger("click");

	if (null != initCallback && "null" != initCallback && '' != initCallback) {
		initCallback();
	}
}
/* 显示遮罩层 */
function showOverlay() {
	$("#overlay").height(pageHeight());
	$("#overlay").width(pageWidth());

	// fadeTo第一个参数为速度，第二个为透明度
	// 多重方式控制透明度，保证兼容性，但也带来修改麻烦的问题
	$("#overlay").fadeTo(200, 0.5);
}

/* 隐藏覆盖层 */
function hideOverlay() {
	$("#overlay").fadeOut(200);
}

/* 当前页面高度 */
function pageHeight() {
	return document.body.scrollHeight;
}

/* 当前页面宽度 */
function pageWidth() {
	return document.body.scrollWidth;
}

/* 微信公众号页面上传图片，微信api上传开始 */

/* 拍照 */
function takePhoto(obj, transId) {
	var thisObj = obj;
	wx.chooseImage({
		sizeType : [ 'compressed' ],
		sourceType : [ 'album', 'camera' ],
		success : function(res) {
			var localIds = res.localIds;
			for (var i = 0; i < localIds.length; i++) {
				var imgId = localIds[i];
				addPhoto(imgId, thisObj);
			}
		}
	});
};
function addPhoto(aimgId, renderObj) {
	var localNum = aimgId.substr(20);
	var deleteBtn = '<h3 class="am-gallery-title am-fr" onclick="deletePhoto(this)">删除  <i class="am-icon-trash-o am-icon-sm" style="margin-top: 6px;color:#dd514c;opacity:100;"></i></h3>';
	$(renderObj)
			.before(
					'<li id="img_'
							+ localNum
							+ '">'
							+ '<div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">'
							+ '<div style="height:72px;margin:2px;overflow:hidden;">'
							+ '<img src="' + aimgId
							+ '" alt=" " style="border:none;" />' + '</div>'
							+ deleteBtn + '</div>' + '</li>');

	var localIdsInput = $(renderObj).parents("ul").next(".localIds");
	var inputIds = localIdsInput.val();

	var inputIdz;
	if (inputIds.length > 0) {
		inputIdz = inputIds.split(",");
		inputIdz.push(aimgId);
	} else {
		inputIdz = aimgId;
	}
	localIdsInput.val(inputIdz);

	$(".am-pureview-slider").on("click",function() {
		$(".am-icon-chevron-left[data-am-close='pureview']").triggerHandler("click");
	});
};

function deletePhoto(removeBtnDom) {
	var localIdsInput = $(removeBtnDom).parents("ul").next(".localIds");
	var inputIds = localIdsInput.val();
	var inputIdz = inputIds.split(",");
	var aimgId = $(removeBtnDom).parents("li").find("img").attr("src");
	inputIdz.splice(jQuery.inArray(aimgId, inputIdz), 1);// 返回被删除项
	localIdsInput.val(inputIdz);
	var uindex = $(".am-gallery").index($(removeBtnDom).parents("ul"));
	var index = $(removeBtnDom).parents("li").index();
	$(".am-pureview-slider:eq(" + uindex + ") li:eq(" + index + ")").remove();
	$(removeBtnDom).parents("li").remove();
};

function deletePhotoInServer(serverHost, aimgId) {
	showLoading();
	$.post(serverHost + "/acceptPhoto/delete.json", {
		photoId : aimgId
	}, function(json) {
		closeLoading();
		if (json.succ) {
			// deletePhoto(removeBtnDom);
			var uindex = $(".am-gallery").index($("#" + aimgId).parents("ul"));
			var index = $("#" + aimgId).index();
			$(".am-pureview-slider:eq(" + uindex + ") li:eq(" + index + ")").remove();
			$("#" + aimgId).remove();
		} else {
			alert("网络繁忙，请稍后重试");
		}
	}, "json");
};
/* 微信公众号页面上传图片，微信api上传结束，webuploader待传资源开始 */
var store = $.AMUI.store;
function webUploaderSelectPhoto(el, serverHost) {
	var uploader = new WebUploader.Uploader({
		auto : false,// 选完文件后，是否自动上传。
		swf : serverHost + '/assets/webuploader/Uploader.swf',// swf文件路径
		server : '',// 文件接收服务端。
		pick : {// 内部根据当前运行是创建，可能是input元素，也可能是flash.
			id : el,// 选择文件的按钮。可选。
			multiple : false
		},
		quality : 70,
		accept : { // 只允许选择图片文件。
			title : 'Images',
			extensions : 'gif,jpg,jpeg,bmp,png',
			mimeTypes : 'image/*'
		},
		thumb : {
			allowMagnify : false,
			crop : false
		// 是否允许裁剪。
		}
	});

	uploader.on('fileQueued',function(file) {// 当有文件添加进来的时候
		showLoading("正在压缩图片...");
		var myuuid = new UUID().id;
		var deleteBtn = '<h3 class="am-gallery-title am-fr" onclick="deletePhotoH5(this,&apos;'+ myuuid+ '&apos;)">删除  <i class="am-icon-trash-o am-icon-sm" style="margin-top: 6px;color:#dd514c;opacity:100;"></i></h3>';
		// var fileurl=uploader.webuploderfile;
		// console.log(fileurl);
		var $li = $('<li class="photocontent">'
				+ '<div class="am-gallery-item am-table-radius" style="width:85px;border: 2px #ddd solid;">'
				+ '<div style="height:72px;margin:2px;overflow:hidden;">'
				+ '<img alt=" " src="" data-rel="" style="border:none;" />'
				+ '</div>' + deleteBtn + '</div>' + '</li>'),
				$img = $li.find('img');

		// var fileurl=window.webkitURL.createObjectURL(file);
		uploader.makeThumb(file,function(error, src) {
			closeLoading();
			if (error) {
				$img.replaceWith('<span>不能预览</span>');
				return;
			}
			try{
				store.set(myuuid, src);
			}catch(e){
				alert("本地资源空间已满，请先上传资源");
				return;
				//alert(JSON.stringify(e));
			}
			$img.attr('src', src);
			$img.attr('data-rel', src);
			$(el).before($li);
			$(".am-pureview-slider").on("click",function() {
				$(".am-icon-chevron-left[data-am-close='pureview']").triggerHandler("click");
			});
			var localIdsInput = $(el).parents("ul").next(".localIds");
			var inputIds = localIdsInput.val();
			// alert(localIdsInput.val());
			var inputIdz;
			if (inputIds.length > 0) {
				inputIdz = inputIds.split(",");
				inputIdz.push(myuuid);
			} else {
				inputIdz = myuuid;
			}
			localIdsInput.val(inputIdz);
			// 清空内存
			// el=null;
			myuuid = null;
			deleteBtn = null;
			$li = null;
			$img = null;
			inputIds = null;
			localIdsInput = null;
			inputIdz = null;
			src = null;
		}, 680, 680);
	});
}

/* 微信公众号页面上传图片，webuploader上传结束，H5待传资源开始 */
function addPhotoH5(el, renderObj, serverHost) {

	showLoading("正在压缩图片...");
	var file = el.files[0];
	var fileurl = window.webkitURL.createObjectURL(file);
	// console.log(file);
	// alert(fileurl);
	if (!/image\/\w+/.test(file.type)) {
		modalAlert("请确保文件为图像类型");
		return false;
	}
	var myuuid = new UUID().id;
	// serverHost+"/checkHiddenTrouble/uploadImgBase64data.json
	var deleteBtn = '<h3 class="am-gallery-title am-fr" onclick="deletePhotoH5(this,&apos;'+ myuuid + '&apos;)">删除  <i class="am-icon-trash-o am-icon-sm" style="margin-top: 6px;color:#dd514c;opacity:100;"></i></h3>';
	var imgElement = '<img src="' + fileurl + '" alt=" " style="border:none;" />';
	$("#" + renderObj).before(
					'<li>'
							+ '<div class="am-gallery-item am-table-radius" style="position:relative;width:85px;border: 2px #ddd solid;">'
							+ '<div style="height:72px;margin:2px;overflow:hidden;">'
							+ imgElement + '</div>' + deleteBtn + '</div>'
							+ '</li>');

	/*
	 * lrz(file, {quality : 0.7,width: 600,height:600}).then(function (rst) {
	 * //处理成功会执行 store.set(myuuid, rst.base64); }) .always(function () { //
	 * 不管是成功失败，都会执行 closeLoading(); });
	 */

	var localIdsInput = $("#" + renderObj).parents("ul").next(".localIds");
	var inputIds = localIdsInput.val();
	// alert(localIdsInput.val());
	// alert(myuuid);
	var inputIdz;
	if (inputIds.length > 0) {
		inputIdz = inputIds.split(",");
		inputIdz.push(myuuid);
	} else {
		inputIdz = myuuid;
	}
	localIdsInput.val(inputIdz);
	$(".am-pureview-slider").on("click",function() {
		$(".am-icon-chevron-left[data-am-close='pureview']").triggerHandler("click");
	});
}

function deletePhotoH5(removeBtnDom, localId) {
	var localIdsInput = $(removeBtnDom).parents("ul").next(".localIds");
	var inputIds = localIdsInput.val();
	var inputIdz = inputIds.split(",");

	var uindex = $(".am-gallery").index($(removeBtnDom).parents("ul"));
	var index = $(removeBtnDom).parents("li").index();

	inputIdz.splice(index - 1, 1);// 返回被删除项
	localIdsInput.val(inputIdz);
	$(".am-pureview-slider:eq(" + uindex + ") li:eq(" + index + ")").remove();
	$(removeBtnDom).parents("li").remove();
	store.remove(localId);
};

function deletePhotoInServerH5(serverHost, aimgId, localId) {
	showLoading();
	$.post(serverHost + "/acceptPhoto/delete.json", {
		photoId : aimgId
	}, function(json) {
		closeLoading();
		if (json.succ) {
			// deletePhoto(removeBtnDom);
			var uindex = $(".am-gallery").index($("#" + aimgId).parents("ul"));
			var index = $("#" + aimgId).index();
			$(".am-pureview-slider:eq(" + uindex + ") li:eq(" + index + ")")
					.remove();
			$("#" + aimgId).remove();
			store.remove(localId);
		} else {
			alert("网络繁忙，请稍后重试");
		}
	}, "json");
};
function showImagesH5() {// 从缓存里面加载图片
	var serverHost=$("#ctx").val();
	if ('' != serverHost && null != serverHost && typeof (serverHost) != "undefined") {
		$("li[data-weg]").each(function(i) {
			webUploaderSelectPhoto(this, serverHost);
		});
	}

	$("ul[data-am-widget='gallery'] img[data-localId]").each(function(i) {
		var localId = $(this).attr("data-localId");
		var imgdata = store.get(localId);
		if (typeof (imgdata) != "undefined") {
			$(this).attr("src", imgdata);
			$(this).attr("data-rel", imgdata);
		}
	});
}
function showImagesPhotoListH5() {// 从缓存里面加载图片
	$("img[data-localId]").each(
			function(i) {
				var localId = $(this).attr("data-localId");
				var imgdata = store.get(localId);
				if (typeof (imgdata) != "undefined") {
					$(this).attr("src", imgdata);
					var dataEchoVal = $(this).attr("data-echo");
					if ('' != dataEchoVal && null != dataEchoVal && typeof (dataEchoVal) != "undefined") {
						$(this).attr("data-echo", imgdata);
					}
				}
			});
}
function saveBase64ImgData(callback) {
	/*
	 * for(var i=0;i<base64ImgThmpArray.length;i++){
	 * store.set(base64ImgThmpArray[i].uuid,
	 * base64ImgThmpArray[i].base64ImgData); base64ImgThmpArray.splice(i, 1); }
	 */
	if (null != callback && "null" != callback && '' != callback) {
		callback();
	}
}

function clearStoreImg(elArray) {
	store.forEach(function(key, val) {
		if (-1 == elInArray(key, elArray)) {
			store.remove(key);
			// alert(key);
		}
	});
}
function elInArray(el, elArray) {
	for (var i = 0; i < elArray.length; i++) {
		if (elArray[i] == el) {
			return i;
		}
	}
	return -1;
}

/* 微信公众号页面上传图片，H5待传资源结束，隐患排查直接上传图片开始 */
var canvas = document.createElement("canvas");// 用于压缩图片的canvas
var ctx = canvas.getContext('2d');

var tCanvas = document.createElement("canvas");// 瓦片canvas
var tctx = tCanvas.getContext("2d");

var maxsize = 100 * 1024;
var base64ImgstrId = "";
function selectPhoto(el, renderObj, base64Imgstr, serverHost) {
	base64ImgstrId = base64Imgstr;
	showLoading("正在压缩图片...");
	var file = el.files[0];
	var fileurl = window.webkitURL.createObjectURL(el.files[0]);
	// console.log(file);
	// console.log(fileurl);
	if (!/image\/\w+/.test(file.type)) {
		modalAlert("请确保文件为图像类型");
		return false;
	}
	var imgElement = '<img src="' + fileurl+ '" alt=" " style="border:none;" />';
	var deleteBtn = '<h3 class="am-gallery-title am-fr" onclick="deleteSelectPhoto(&apos;'+ serverHost + '&apos;,this)">删除  <i class="am-icon-trash-o am-icon-sm" style="margin-top: 6px;color:#dd514c;opacity:100;"></i></h3>';
	$("#" + renderObj).before(
					'<li class="addFromDevice">'
							+ '<div class="am-gallery-item am-table-radius" style="position:relative;width:85px;border: 2px #ddd solid;">'
							+ '<div style="height:72px;margin:2px;overflow:hidden;">' + imgElement
							+ '</div>'
							+ deleteBtn
							+ '<div class="app-uploding" style="text-align:center;line-height:72px">0%</div>'
							+ '</div>' + '</li>');
	$(".am-pureview-slider").on("click",function() {
		$(".am-icon-chevron-left[data-am-close='pureview']").triggerHandler("click");
	});

	var url = serverHost + "/checkHiddenTrouble/base64ImgUpload.json";
	var reader = new FileReader();
	// Closure to capture the file information.
	reader.onload = function() {
		var result = this.result;
		// console.log(result);
		var img = new Image();
		img.src = result;
		// 如果图片大小小于100kb，则直接上传
		if (result.length <= maxsize) {
			img = null;
			closeLoading();
			uploadBase64Data(result, file.type, url);
			return;
		}
		// 图片加载完毕之后进行压缩，然后上传
		if (img.complete) {
			callback();
		} else {
			img.onload = callback;
		}
		function callback() {
			var data = compress(img);
			closeLoading();
			uploadBase64Data(data, file.type, url);
			img = null;
		}
	};
	reader.readAsDataURL(file);// Read in the image file as a data URL.
	return true;
};
function deleteSelectPhoto(serverHost, removeBtnDom) {
	var hiddenInput = $(removeBtnDom).parents("ul").next(".localIds");
	var hiddenInputVal = hiddenInput.val();
	var hiddenInputValArray = hiddenInputVal.split("_");
	var addFromDeviceindex = $(removeBtnDom).parents("li").filter(".addFromDevice").index($(removeBtnDom).parents("li"));
	var deleteDomArray = hiddenInputValArray.splice(addFromDeviceindex, 1);
	hiddenInput.val(hiddenInputValArray.join("_"));

	deleteSelectPhotoServer(serverHost, deleteDomArray[0], removeBtnDom);
}
function deleteSelectPhotoServer(serverHost, photoId, removeBtnDom) {
	showLoading();
	$.post(serverHost + "/checkHiddenTrouble/deleteYhPhoto.json", {
		recordId : photoId
	},
			function(json) {
				closeLoading();
				if (json.succ) {
					var ulindex = $(".am-gallery").index($(removeBtnDom).parents("ul"));
					var index = $(removeBtnDom).parents("li").index();
					$(".am-pureview-slider:eq(" + ulindex + ") li:eq("+ index + ")").remove();
					$(removeBtnDom).parents("li").remove();
				} else {
					alert("网络繁忙，请稍后重试");
				}
			}, "json");
}

// 使用canvas对大图片进行压缩
function compress(img) {
	// var initSize = img.src.length;
	var width = img.width;
	var height = img.height;
	// 如果图片大于四百万像素，计算压缩比并将大小压至400万以下
	var ratio;
	if ((ratio = width * height / 4000000) > 1) {
		ratio = Math.sqrt(ratio);
		width /= ratio;
		height /= ratio;
	} else {
		ratio = 1;
	}
	canvas.width = width;
	canvas.height = height;
	// 铺底色
	ctx.fillStyle = "#fff";
	ctx.fillRect(0, 0, canvas.width, canvas.height);
	// 如果图片像素大于100万则使用瓦片绘制
	var count;
	if ((count = width * height / 1000000) > 1) {
		count = ~~(Math.sqrt(count) + 1); // 计算要分成多少块瓦片
		// 计算每块瓦片的宽和高
		var nw = ~~(width / count);
		var nh = ~~(height / count);
		tCanvas.width = nw;
		tCanvas.height = nh;
		for (var i = 0; i < count; i++) {
			for (var j = 0; j < count; j++) {
				tctx.drawImage(img, i * nw * ratio, j * nh * ratio, nw * ratio,nh * ratio, 0, 0, nw, nh);
				ctx.drawImage(tCanvas, i * nw, j * nh, nw, nh);
			}
		}
	} else {
		ctx.drawImage(img, 0, 0, width, height);
	}
	// 进行最小压缩
	var ndata = canvas.toDataURL('image/jpeg', 0.1);
	// console.log('压缩前：' + initSize);
	// console.log('压缩后：' + ndata.length);
	// console.log('压缩率：' + ~~(100 * (initSize - ndata.length) / initSize) +
	// "%");
	tCanvas.width = tCanvas.height = canvas.width = canvas.height = 0;
	// alert('压缩前：' + initSize+'压缩后：' + ndata.length+'压缩率：' + ~~(100 * (initSize
	// - ndata.length) / initSize) + "%");
	return ndata;
}

function uploadBase64Data(data, type, serverUrl) {
	var fd = new FormData();
	fd.append("imgdata", data);
	var xhr = new XMLHttpRequest();
	xhr.upload.addEventListener("progress", uploadProgress, false);
	xhr.addEventListener("load", uploadComplete, false);
	xhr.addEventListener("error", uploadFailed, false);
	xhr.addEventListener("abort", uploadCanceled, false);
	xhr.open("POST", serverUrl);
	xhr.send(fd);
}
// 获取blob对象的兼容性写法
function getBlob(buffer, format) {
	var Builder = window.WebKitBlobBuilder || window.MozBlobBuilder;
	if (Builder) {
		var builder = new Builder;
		builder.append(buffer);
		return builder.getBlob(format);
	} else {
		return new window.Blob([ buffer ], {
			type : format
		});
	}
}
function uploadProgress(evt) {
	if (evt.lengthComputable) {
		var percentComplete = Math.round(evt.loaded * 100 / evt.total).toString();
		$(".app-uploding").html(percentComplete + '%');
		if ("100" == percentComplete) {

		}
	} else {
	}
}

function uploadComplete(evt) {
	$(".app-uploding").remove();
	/* This event is raised when the server send back a response */
	var date = eval('(' + evt.target.responseText + ')');
	// alert($("#"+base64ImgstrId).val());
	if ('' != $("#" + base64ImgstrId).val()) {
		var all = $("#" + base64ImgstrId).val() + "_" + date.recordId;
		$("#" + base64ImgstrId).val(all);
	} else {
		$("#" + base64ImgstrId).val(date.recordId);
		// alert($("#"+base64ImgstrId).val());
	}
}
function uploadFailed(evt) {
	alert("There was an error attempting to upload the file.");
}
function uploadCanceled(evt) {
	alert("The upload has been canceled by the user or the browser dropped the connection.");
}

// 页面跳转
function toPageUrl(url) {
	window.location.href = url;
};
function showLoading(msg) {
	if (msg !== null || msg !== undefined || msg !== '') {
		$("#modal-loading-msg").html(msg);
	}
	var $modal = $('#modal-loading');
	$modal.modal();
};
function closeLoading() {
	var $modal = $('#modal-loading');
	$modal.modal('close');
	$("#modal-loading-msg").html("正在载入...");
};
function modalAlert(msg, callBack) {
	$("#modal-alert-msg").html(msg);
	$('#modal-alert').modal({
		relatedTarget : this,
		onConfirm : function() {
			if (null != callBack && "" != callBack) {
				callBack();
			}
		}
	});
};
var confirmCallbackFunction;
var cancelCallbackFunction;
function modalConfirm(msg, onConfirmCallback, onCancelCallback) {
	confirmCallbackFunction=onConfirmCallback;
	cancelCallbackFunction=onCancelCallback;
	$("#app-modal-confirm-msg").html(msg);
	$('#app-modal-confirm').modal(
			{
				relatedTarget : this,
				onConfirm : function() {
					if (confirmCallbackFunction) {
						confirmCallbackFunction();
					}
					return true;
				},
				// closeOnConfirm: false,
				onCancel : function() {
					if (cancelCallbackFunction) {
						cancelCallbackFunction();
					}
					return false;
				}
			});
};


function saomiao() {
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
					checkTheBarCodeIfInSystem(deviceId);
				}, 2000);
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

function checkTheBarCodeIfInSystem(deviceId) {// 查询是否条码已经存在
	$.post("../devices/barCodeBelongtoOtherStation.json", {
		barCode : deviceId
	}, function(json) {
		if (json.succ) {
			modalAlert(json.msg, null);
			closeLoading();
		} else {
			viewDeviceDetail(deviceId, true);
		}
	}, "json");
}
function viewDeviceDetail(deviceId, isScan) {
	window.location.href = "../devices/getDeviceInfo?deviceId=" + deviceId
			+ "&isScan=" + isScan;
}
// ============================================================录音
function dealVoice(thisObj, hostServer) {
	var status = $(thisObj).attr("name");
	if (status == "start") {
		showLoading();
		setTimeout('closeLoading()', 5000);
		wx.startRecord({
			cancel : function() {
				closeLoading();
				modalAlert('用户拒绝授权录音');
			},
			success : function() {
				$(thisObj).parent().find('input[name="recordIds"]').val("");
				$(thisObj).parent().attr('class', 'recordOn');
				$(thisObj).attr("name", "stopRec");
				$(thisObj).find('i').attr('class', 'am-icon-stop');
				$(thisObj).find('.statusTips').text("停止录音");
				closeLoading();
			}
		});
	} else if (status == "stopRec") {
		showLoading("录音上传中...");
		$(thisObj).parent().attr('class', 'recordOff');
		$(thisObj).attr("name", "playVoice");
		$(thisObj).siblings('.deleteVoice').css("display", "inline");
		$(thisObj).find('i').attr('class', 'am-icon-volume-up');
		$(thisObj).find('.statusTips').text("点击播放");
		wx.stopRecord({
			success : function(res) {
				voice.localId = res.localId;
				var inputIds = $(thisObj).parent().find(
						'input[name="recordIds"]').val();
				var inputIdz;
				if (inputIds.length > 0) {
					inputIdz = inputIds.split(",");
					inputIdz.push(voice.localId);
				} else {
					inputIdz = voice.localId;
				}
				$(thisObj).parent().find('input[name="recordIds"]').val(
						inputIdz);
				// 保存录音信息
				var inputEle = $(thisObj).parent().find(
						'input[name="recordIds"]');
				$.ajax({
					type : "get",
					url : hostServer + "/singleBatteryCheck/saveSound",
					data : {
						"recordIds" : inputEle.val(),
						"relatedId" : inputEle.attr('id'),
						"soundType" : "discharge"
					},
					success : function(res) {
						var resp = eval("(" + res + ")");
						if (resp.status) {
							$(thisObj).parent().find('input[name="voiceIds"]')
									.val(resp.voiceIds);
							closeLoading();
						} else {
							closeLoading();
							modalAlert("录音保存失败！");
						}
					},
					error : function(res) {
						closeLoading();
						modalAlert("录音保存失败！");
					}
				});
			},
			fail : function(res) {
				closeLoading();
				alert(JSON.stringify(res));
			}
		});
		voice.localId = '';
	} else if (status == "playVoice") {
		$(thisObj).parent().attr('class', 'playOn');
		$(thisObj).attr("name", "pauseVoice");
		$(thisObj).find('i').attr('class', 'am-icon-pause');
		$(thisObj).find('.statusTips').text("暂停播放");
		var voiceIds = $(thisObj).parent().find('input[name="recordIds"]').val();
		var voiceIdz = voiceIds.split(',');
		var tempVoiceId = $(thisObj).parent().find('input[name="tempVoiceId"]').val();
		if (!tempVoiceId == "") {
			voice.localId = tempVoiceId;
		} else {
			voice.localId = voiceIdz[0];
		}
		wx.playVoice({
			localId : voice.localId
		});
	} else if (status == "pauseVoice") {
		$(thisObj).parent().attr('class', 'playOff');
		wx.pauseVoice({
			localId : voice.localId
		});
		var inputs = $(thisObj).parent();
		var input = $(thisObj).parent().find('input[name="tempVoiceId"]').val(
				voice.localId);
		$(thisObj).attr("name", "playVoice");
		$(thisObj).find('i').attr('class', 'am-icon-play');
		$(thisObj).find('.statusTips').text("继续播放");
	} else {
		alert("录音已损坏，请重新录音！");
	}
};
function deleteVoice(obj, hostServer) {
	var thisObj = obj;
	$(thisObj).css("display", "none");
	$(thisObj).parent().find('input[name="recordIds"]').val('');
	var status = $(thisObj).siblings('.voice').attr("name");
	if (status == "pauseVoice") {
		$(thisObj).siblings('.voice').click();
	}
	$(thisObj).parent().attr('class', '');
	$(thisObj).siblings('.voice').attr("name", "start");
	$(thisObj).siblings('.voice').find('i').attr('class', 'am-icon-microphone');
	$(thisObj).siblings('.voice').find('.statusTips').text("开始录音");
	var voiceIds = $(thisObj).parent().find('input[name="voiceIds"]').val();
	$.ajax({
		type : "get",
		url : hostServer + "/singleBatteryCheck/deleteSound",
		data : {
			"voiceIds" : voiceIds
		},
		success : function(res) {
			if (res) {
				$(thisObj).parent().find('input[name="voiceIds"]').val("");
			} else {
				modalAlert("录音删除失败！");
			}
		},
		error : function(res) {
			modalAlert("录音删除失败！");
		}
	});
}

/**初始化Mobiscroll日期选择控件*/
function initMobiscroll(el,opt){
	var defaultopt = {
	    preset: 'date', //日期
	    theme: 'android-ics light', //皮肤样式
	    display: 'modal', //显示方式 
	    mode: 'scroller', //日期选择模式
	    dateFormat: 'yy-mm-dd',
	    dateOrder: 'yymmdd',
		lang:'zh',
		startYear:1990, //开始年份
		maxDate:new Date()
	};
	
	if ('' != opt && null != opt && typeof (opt) != "undefined") {
		defaultopt=$.extend(defaultopt,opt);
	};
	//alert(JSON.stringify(defaultopt));
	$(el).scroller('destroy').scroller(defaultopt);
}

/**初始化Mobiscroll日期选择控件*/
function initMobiscrollTime(el){
	var opt = {
	        preset: 'datetime', //日期
	        theme: 'android-ics light', //皮肤样式
	        display: 'modal', //显示方式 
	        mode: 'scroller', //日期选择模式
	        dateFormat: 'yyyy-mm-dd',
			lang:'zh',
			startYear:1990, //开始年份
			maxDate:new Date()
	    };
	$(el).scroller('destroy').scroller(opt);
}
/**
 * 根据dictTypeId生成selectId节点的options，并且有defaultValue的默认选中
 */
function setDictOptions(dictTypeId,selectId,defaultValue){
	var html = '';
	if(defaultValue == null || defaultValue == ""){
		html += '<option value="" selected = "selected">请选择</option>';
	}
	$.getJSON($("#ctx").val()+"/common/getDictEnterys.json?dictTypeId="+encodeURI(encodeURI(dictTypeId)), function(data){
	 $.each(data.entryList, function(i,item){
		  html += '<option  value="'+ item.dictId +'" ';
		  if(item.dictId == defaultValue){
			  html += 'selected = "selected"';
		  }
		  html += '>'+ item.dictName +'</option>';
	 }); 
	   $("#"+selectId).html(html);
	});
}

/**
 * 日期格式化
 */
Date.prototype.format = function(format){
	var o = { 
		"M+" : this.getMonth()+1, //month 
		"d+" : this.getDate(), //day 
		"h+" : this.getHours(), //hour 
		"m+" : this.getMinutes(), //minute 
		"s+" : this.getSeconds(), //second 
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
		"S" : this.getMilliseconds() //millisecond 
	};
	
	if(/(y+)/.test(format)) { 
	format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
	} 
	
	for(var k in o) {
		if(new RegExp("("+ k +")").test(format)) { 
			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
		}
	} 
	return format; 
};
function myDateFormat(date,format){
//    var date = this;    
    /*   
    函数：填充0字符   
    参数：value-需要填充的字符串, length-总长度   
    返回：填充后的字符串   
    */   
    var zeroize = function (value, length) {    
        if (!length) {    
            length = 2;    
        }    
        value = new String(value);    
        for (var i = 0, zeros = ''; i < (length - value.length); i++) {    
            zeros += '0';    
        }    
            return zeros + value;    
    };    
    return formatStr.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g, function($0) {    
        switch ($0) {    
            case 'd': return date.getDate();    
            case 'dd': return zeroize(date.getDate());    
            case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][date.getDay()];    
            case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][date.getDay()];    
            case 'M': return date.getMonth() + 1;    
            case 'MM': return zeroize(date.getMonth() + 1);    
            case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][date.getMonth()];    
            case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][date.getMonth()];    
            case 'yy': return new String(date.getFullYear()).substr(2);    
            case 'yyyy': return date.getFullYear();    
            case 'h': return date.getHours() % 12 || 12;    
            case 'hh': return zeroize(date.getHours() % 12 || 12);    
            case 'H': return date.getHours();    
            case 'HH': return zeroize(date.getHours());    
            case 'm': return date.getMinutes();    
            case 'mm': return zeroize(date.getMinutes());    
            case 's': return date.getSeconds();    
            case 'ss': return zeroize(date.getSeconds());    
            case 'l': return date.getMilliseconds();    
            case 'll': return zeroize(date.getMilliseconds());    
            case 'tt': return date.getHours() < 12 ? 'am' : 'pm';    
            case 'TT': return date.getHours() < 12 ? 'AM' : 'PM';    
        }    
    });    
}
