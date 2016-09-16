<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<title>添加产品</title>
<%@include file="/common/head.jsp"%>
<%@include file="/common/videoUpload.jsp"%>

<link rel="stylesheet" type="text/css" href="${_ctx_ }/css/crop.css" />
<script type="text/javascript" src="${_ctx_ }/js/cropbox.js"></script>

<script type="text/javascript">

jQuery.validator.addMethod("isProductNumOnly",function(value,element){
	return this.optional(element)||!isProductNumOnlyOne(value);
},"货号重复");
var validform;
$(document).ready(function(){
	validform = $("#addForm").validate({
		rules : {
			'product.num' : {
				required : true,
				isProductNumOnly : true
			}
		}
	});
	
	$("#picUrl").change(function(){
		 var file = document.getElementById("picUrl").files[0];
		 if (!/image\/\w+/.test(file.type)) {
			 	JiaFang.showFailedToast("请选择图片！");
	            return false;
	     }
		
		 var reader = new FileReader();
	     reader.readAsDataURL(file);
	     reader.onprogress = function(e){
	    	 var percentLoaded = Math.round((e.loaded / e.total) * 100);
	    	    if (percentLoaded < 100) {
	    	    	$("#progressAvatar").val(percentLoaded);
	    	        
	    	    }
	     }
		 reader.onload = function(e) {
	            $("#banner").attr("src",this.result);
	            var imgsrc=this.result;
	            imgsrc=imgsrc.substr(imgsrc.indexOf("base64,") + 7);
	            $.ajax({
	    			url : '${_ctx_}/res/uploadResToQiNiu',// 跳转到 action
	    			data : {
	    				file : imgsrc,
	    			},
	    			type : 'post',
	    			cache : false,
	    			dataType : 'json',
	    			success : function(data) {
	    				if(data.code == 200){
	    					$("#picUrlKey").val(data.data);
	    					
	    					$("#progressAvatar").val(100);
	    				}else {
	    					alert("获取失败！" + data.description);
	    				}
	    			},
	    			error : function() {
	    				// view("异常！");
	    				alert("异常！");
	    			}
	    		});
	           
	   		 };
		 
	});
	
	
	//根据类型获取分类
	$.ajax({
		url : '${_ctx_}/category/getCategoriesByType',// 跳转到 action
		data : {
			type:1
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if(data.code == 200){
				var inputs = dom.ejs("js_radio_tmpl_1", data);
				$("input [class='add_radio_1']").remove();
				$("#radioModel_1").append(inputs);	
			}else {
				JiaFang.showFailedToast("获取普通分类失败！" + data.description);
				//alert("获取失败！" + data.description);
			}
		},
		error : function() {
			JiaFang.showFailedToast("获取普通分类失败！" );
		}
	});
	$.ajax({
		url : '${_ctx_}/category/getCategoriesByType',// 跳转到 action
		data : {
			type:0
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if(data.code == 200){
				var inputs = dom.ejs("js_radio_tmpl_0", data);
				$("input [class='add_radio_0']").remove();
				$("#radioModel_0").append(inputs);	
			}else {
				JiaFang.showFailedToast("获取专区分类失败！" + data.description);
			}
		},
		error : function() {
			JiaFang.showFailedToast("获取专区分类失败！" );
		}
	});
});
function submitForm(){
	if (!validform.form()) {
		return false;
	}
	$.post("${_ctx_}/product/addProduct",$("#addForm").serialize(),function(data) {
		if(data.code==200){
			$("#submit").attr('disabled',true);
			JiaFang.showSuccessToast("添加产品成功！");
			//alert("添加成功！");
			window.location.href="${_ctx_ }/page/productlist";
			
		}
		else {
			JiaFang.showFailedToast("添加产品失败！");
			//alert("添加产品失败！");
		}
	},"json");
}

function isProductNumOnlyOne(num){
	 var flag;
	$.ajax({
		 type:"post",
		 url:"${_ctx_ }/product/checkProductNum",
		 data : {
				"product.num":$("#productNum").val()
		 },
		 dataType : 'json',
         async:false,
         success:function(data){
            if(data.data==0)
            {
            	flag= false;
            }else{
            	flag= true;
            }
         }
      });
      return flag; 
      //JiaFang.isProductNumOnlyOne(num,function(){return true;},function(){return false;})
}

function cancel(){
	window.history.back();
}




function imgEdit(){
	var cropper = $('.imageBox').cropbox(options);
	$("#dialog").dialog({
		 closeOnEscape: false,
		 width: window.screen.availWidth,
		 height: window.screen.availHeight-100,
		 resizable: true,
		 draggable: true,
		open: function (event, ui) {
               $(".ui-dialog-titlebar-close", $(this).parent()).hide();
              },
		modal: true,
		buttons: [ { text: "OK", click: function() {
													$("#banner").prop("src",$("#cropPic").prop("src")); $( this ).dialog( "close" );
													var imgsrc=$("#cropPic").prop("src");
													imgsrc=imgsrc.substr(imgsrc.indexOf("base64,") + 7);
													if(!!imgsrc){
														JiaFang.showLoading("图片上传中..");
													}
										            $.ajax({
										    			url : '${_ctx_}/res/uploadResToQiNiu',// 跳转到 action
										    			data : {
										    				file : imgsrc,
										    			},
										    			type : 'post',
										    			cache : false,
										    			dataType : 'json',
										    			success : function(data) {
										    				if(data.code == 200){
										    					$("#picUrlKey").val(data.data);
										    					JiaFang.showSuccessToast("图片上传成功！");
										    				}else {
										    					JiaFang.showFailedToast("获取七牛资源地址失败！" + data.description);
										    				}
										    			},
										    			error : function() {
										    				// view("异常！");
										    				JiaFang.showFailedToast("获取七牛资源地址失败！");
										    			}
										    		});
													} 
					} ,
		           { text: "Cancel", click: function() { $( this ).dialog( "close" ); } } ]
	});
}

var options;
$(function(){
	options =
	{
			thumbBox: '.thumbBox',
			spinner: '.spinner',
			imgSrc: ''
	}
	var cropper = $('.imageBox').cropbox(options);
	$('#file').on('change', function(){
		 var file = document.getElementById("file").files[0];
		 if (!/image\/\w+/.test(file.type)) {
			 	JiaFang.showFailedToast("请选择图片！");
	            return false;
	     }
		
		var reader = new FileReader();
		reader.onload = function(e) {
			options.imgSrc = e.target.result;
			cropper = $('.imageBox').cropbox(options);
		}
		reader.readAsDataURL(this.files[0]);
		this.files = [];
	})
	$('#btnCrop').on('click', function(){
		var img = cropper.getDataURL();
		$('.view').html("");
		$('.view').append('<img id="cropPic" src="'+img+'">');
	})
	$('#btnZoomIn').on('click', function(){
		cropper.zoomIn();
	})
	$('#btnZoomOut').on('click', function(){
		cropper.zoomOut();
	})
});
</script>
</head>

<body>
	<div class="content">
    	 <div class="wrapper">
         	  <div class="headerBox">
                    <ul id="menuList" class="menuList">
                        <li><a class="cur"  href="${_ctx_ }/page/companyinfo">公司管理<font>Company management</font></a></li>
                        <li><a  href="${_ctx_ }/page/productlist">产品管理<font>Product management</font></a></li>
                    </ul>
                    <div class="clear"></div>
              </div>
              <div class="floatBox">
                    <div class="cont">
                    	  <div class="logo01"><a href="javascript:void(0);"></a></div>
                          <h3>${SysName}后台管理系统</h3>
                          <div class="infor">
                          	   <img src="${_ctx_ }/images/user.jpg">
                               <p class="ft01">用户级别：公司</p>
                               <p class="ft02">
                               		<a href="${_ctx_ }/page/modifypwd">修改密码</a>
                                    <span>|</span>
                                    <a href="${_ctx_ }/page/logout">退出</a>
                               </p>
                          </div>
                    </div>
              </div>
              <div class="breadBox">
                  <ul>
                      <li>· 您的位置：</li><li><a href="${_ctx_ }/page/productlist">产品管理</a></li><li>></li><li><a href="#">添加产品</a></li>
                  </ul>
              </div>
              <div class="clear"></div>
              <div class="tableBox">
              	   <div class="floatdiv01"></div>
              	   <form id="addForm">
              	   <table class="table" width="98%" cellpadding="0" cellspacing="0">
              	   		 <input class="txt" type="hidden" value="${user_level_1_company.id }" name="product.companyId" id="product.companyId"/>
                        <tr>
            				<th width="19%">普通分类</th>
                			<td width="81%" id="radioModel_0">
                	
                		</td>
            			</tr>
			        	<tr>
			            	<th width="19%">专区分类</th>
			                <td width="81%" id="radioModel_1">
			                	
			                </td>
			            </tr>
                        <tr>
                            <td width="22.7%" class="c01">产品名称</td>
                            <td width="77.3%" style=" border-top:0;"><input class="txt" type="text" value="" name="product.name" id="product.name"/></td>
                        </tr>
                        <tr>
                            <td class="c01">产品货号</td>
                            <td><input class="txt" type="text" value="" name="product.num" id="productNum"/></td>
                        </tr>
                        <tr>
                            <td class="c02 bg07">图片</td>
                            <td>
                                 <img id="banner" alt="" src="" width="100px"> 
			               		 <input type="hidden" name="product.avatar" id="picUrlKey" value="">
			               		 <a href="javascript:imgEdit()">图片上传以及编辑</a>
                            </td>
                        </tr>
                        <tr>
                            <td  class="c02">视频</td>
                            <td>
                                 <video controls="controls"  width="340" height="200"   id="banner_video" src=""></video>
				                <input type="hidden" id="token" class="token" name="token"
									value="${token}" />
								<input type="hidden" id="domain" class="domain" name="domain"
									value="${domain}" />
								<div class="col-md-12" style="width:auto; float:right">
									<div id="container">
										<a class="btn btn-default btn-lg " id="pickfiles" href="#"> <i
											class="glyphicon glyphicon-plus"></i> <span>选择文件</span>
										</a>
									</div>
									<div class="progress">
										<div id="progress-bar" class="progress-bar" role="progressbar" aria-valuenow="0"
											aria-valuemin="0" aria-valuemax="100" style="width: 50%;">
											<span class="sr-only"></span>
										</div>
									</div>
								</div>
									文件上传进度：<progress value="0" max="100" id="progressFile"></progress>
									 <input type="hidden" name="product.video" id="videoUrlKey" value="">
                            </td>
                        </tr>
                        <tr>
                            <td class="c02">原价</td>
                            <td><input class="txt" type="text" value="" name="product.price" id="product.price"/></td>
                        </tr>
                        <tr>
                            <td class="c02">折扣价</td>
                            <td><input class="txt" type="text" value="" name="product.discountPrice" id="product.discountPrice"/></td>
                        </tr>
                        <tr>
                            <td class="c02">最低订购量</td>
                            <td><input class="txt" type="text" value="" name="product.saleCount" id="product.saleCount"/></td>
                        </tr>
                        <tr>
                            <td class="c02">库存</td>
                            <td><input class="txt" type="text" value="" name="product.count" id="product.count"/></td>
                        </tr>
                        <tr>
                            <td  class="c02"></td>
                            <td class="txt-r">
                                <input class="btn" type="button" value="添加" onclick="submitForm()" id="submit">
                                <input class="btn" type="button" value="取消" onclick="cancel()">
                            </td>
                        </tr>
                    </table>
                    </form>
              </div>
         </div>
    </div>
	<div id="dialog" title="" style="display: none">
	<div class="imageBox">
        <div class="thumbBox"></div>
        <div class="spinner" style="display: none">Loading...</div>
    </div>
   
	
    <div class="action">
    	
        <input type="file" id="file" style="float:left; width: 250px">
        <input type="button" id="btnCrop" value="裁剪" style="float: right">
        <input type="button" id="btnZoomIn" value="放大" style="float: right">
        <input type="button" id="btnZoomOut" value="缩小" style="float: right">
    </div>
	
    <div class="cropped">
    	<p class="viewTxt">预览:</p>
    	<div class="view"></div>
    </div>
</div>
</body>
</html>
<script type="tmpl" id="js_radio_tmpl_1">
        <& for(var i=0,tl = data.length,tr;i < tl;i++){ &>
        <& tr = data[i]; &>
			<input class="add_radio_1" type="radio" name="product.categoryShips[1].categoryId" value="<&=tr.id&>"/><&=tr.name&>&nbsp;
        <& } &>
</script>
<script type="tmpl" id="js_radio_tmpl_0">
        <& for(var i=0,tl = data.length,tr;i < tl;i++){ &>
        <& tr = data[i]; &>
			<input class="add_radio_0" type="radio" name="product.categoryShips[0].categoryId" value="<&=tr.id&>"/><&=tr.name&>&nbsp;
        <& } &>
</script>