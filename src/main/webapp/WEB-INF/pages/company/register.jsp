<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.qiniu.util.Auth;"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta charset="utf-8">
<title>注册公司</title>
<%@include file="/common/head.jsp"%>
<%@include file="/common/videoUpload.jsp"%>

<link rel="stylesheet" type="text/css" href="${_ctx_ }/css/crop.css" />
<script type="text/javascript" src="${_ctx_ }/js/cropbox.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	$("#picUrl_avatar").change(function(){
		 var file = document.getElementById("picUrl_avatar").files[0];
		 if (!/image\/\w+/.test(file.type)) {
			 	JiaFang.showFailedToast("请选择图片！");
	            //alert("请选择图片！");
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
	            $("#banner_avatar").attr("src",this.result);
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
	    					$("#picUrlKey_avatar").val(data.data);
	    					$("#progressAvatar").val(100);
	    				}else {
	    					JiaFang.showFailedToast("获取七牛资源地址失败！" + data.description);
	    				}
	    			},
	    			error : function() {
	    				JiaFang.showFailedToast("获取七牛资源地址失败！");
	    			}
	    		});
	           
	   		 };
	});
});
function submitForm(){
	
	$.post("${_ctx_}/company/registerCompany",$("#newsForm").serialize(),function(data) {
		if(data.code==200){
			$("#submit").attr('disabled',true);
			JiaFang.showSuccessToast("注册公司成功！");
			//alert("注册公司成功！");
			setTimeout(function(){
				window.location.href="${_ctx_ }/page/companyinfo";
			}, 1000);
		}
		else {
			JiaFang.showFailedToast("注册公司失败！");
			//alert("注册公司失败！");
		}
	},"json");
}


function imgEdit(){
	options =
	{
			thumbBox: '.thumbBox',
			spinner: '.spinner',
			imgSrc: ''
	}
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
													$("#banner_avatar").prop("src",$("#cropPic").prop("src")); $( this ).dialog( "close" );
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
										    					$("#picUrlKey_avatar").val(data.data);
										    					JiaFang.showSuccessToast("图片上传成功！");
										    				}else {
										    					JiaFang.showFailedToast("获取七牛资源地址失败！" + data.description);
										    					//alert("获取七牛地址失败！" + data.description);
										    				}
										    			},
										    			error : function() {
										    				JiaFang.showFailedToast("获取七牛资源地址失败！");
										    				// view("异常！");
										    				//alert("异常！");
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
	            alert("请选择图片！");
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
<%-- <body>
    <%@include file="/common/menu2.jsp"%>
    <div class="breadBox">
    	<ul>
        	<li>· 您的位置：</li><li><a href="#">公司管理</a></li><li>></li><li><a href="#">注册公司</a></li>
        </ul>
        <div class="clear"></div>
    </div>
    <div class="contentBox">
    	<form id="newsForm">
    	<div class="toolBar">
        	<font>添加公司信息</font>
        </div>
        
        <input class="txt" type="hidden" value="${user_level_1_company.id }" name="company.id" id="company.id"/>
        <input class="txt" type="hidden" value="${user_level_1_company.userId }" name="company.userId" id="company.userId"/>
       
        <table class="table" width="98%" cellpadding="0" cellspacing="0">
            <tr>
            	<th width="19%">公司名称</th>
                <td width="81%"><input class="txt" type="text" value="" name="company.name" id="company.name"/></td>
            </tr>
            <tr>
            	<th width="19%">公司电话</th>
                <td width="81%"><input class="txt" type="text" value="" name="company.tel" id="company.tel"/></td>
            </tr>
            <tr>
            	<th width="19%">公司网址</th>
                <td width="81%"><input class="txt" type="text" value="" name="company.website" id="company.website"/></td>
            </tr>
 			<th width="19%">公司地址</th>
                <td width="81%"><input class="txt" type="text" value="" name="company.address" id="company.address"/></td>
            </tr>
            <tr>
            	<th width="19%">公司图标</th>
            	<td>
                	 <img id="banner_avatar" alt="" src="" width="100px"> 
               		 <!-- <input class="txt" type="file" value="上传图片" name="picUrl_avatar" id="picUrl_avatar"/> -->
               		 <input type="hidden" name="company.avatar" id="picUrlKey_avatar" value="">
               		 <!--  文件上传进度：<progress value="0" max="100" id="progressAvatar"></progress> -->
               		 <a href="javascript:imgEdit()">图片上传以及编辑</a>
                </td>
            </tr>
            
            <tr>
            	<th width="19%">公司品牌</th>
            	<td>
               		 <input class="txt" type="text" name="company.brand" id="picUrlKey_brand" value="">
                </td>
            </tr>
            <tr>
            	<th width="19%">公司视频</th>
                <td width="81%">
                	<video controls="controls"  width="340" height="200"   id="banner_video" src=""></video>
                	<input type="hidden" id="token" class="token" name="token"
	value="${token}" />
	<input type="hidden" id="domain" class="domain" name="domain"
	value="${domain}" />
					<input type="hidden" id="elementTarget" class="elementTarget" name="elementTarget" value="banner_video" />
					<input type="hidden" id="fileUploadType" class="fileUploadType" name="fileUploadType" value="1" />
					<div class="col-md-12">
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
					 <input type="hidden" name="company.video" id="videoUrlKey" value="">
                
                </td>
            </tr>
           
            <tr>
            	<th valign="top"></th>
                <td class="txt-r">
                	<input class="btn" type="button" value="注册" onclick="submitForm()" id="submit"/>
                    
                </td>
            </tr>
        </table>
        </form>
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
</body> --%>

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
                      <li>· 您的位置：</li><li><a href="javascript:void(0);">公司管理</a></li><li>></li><li><a href="#">注册公司</a></li>
                  </ul>
                  <div class="alterBtn">
                  	   <a href="javascript:void(0);"></a>
                  </div>
              </div>
              <div class="clear"></div>
              <div class="tableBox">
              	   <div class="floatdiv01"></div>
              	   <form id="newsForm">
                       <input class="txt" type="hidden" value="${user_level_1_company.id }" name="company.id" id="company.id"/>
        <input class="txt" type="hidden" value="${user_level_1_company.userId }" name="company.userId" id="company.userId"/>
              	   <table class="table" width="98%" cellpadding="0" cellspacing="0">
                        <tr>
                            <td width="22.7%" class="c01">公司名称</td>
                            <td width="77.3%" style=" border-top:0;"><input class="txt" type="text" name="company.name" id="company.name" value=""/></td>
                        </tr>
                        <tr>
                            <td width="19%" class="c01">公司电话</td>
                            <td width="81%"><input class="txt" type="text" value="" name="company.tel" id="company.tel"/></td>
                        </tr>
                        <tr>
                            <td class="c02 bg07">公司网址</td>
                            <td>
                                 <input class="txt" type="text" value="" name="company.website" id="company.website"/>
                            </td>
                        </tr>
                        <th width="19%">公司地址</th>
                			<td width="81%"><input class="txt" type="text" value="" name="company.address" id="company.address"/></td>
            			</tr>
                        <tr>
                            <td  class="c02">公司图标</td>
                            <td>
		                	 <img id="banner_avatar" alt="" src="" width="100px"> 
		               		 <input type="hidden" name="company.avatar" id="picUrlKey_avatar" value="">
		               		 <a href="javascript:imgEdit()">图片上传以及编辑</a>
                			</td>
                        </tr>
                        <tr>
                            <td  class="c02">公司视频</td>
                            <td>
				                		<video controls="controls"  width="340" height="200"   id="banner_video" src=""></video>
					                	<input type="hidden" id="token" class="token" name="token"
											value="${token}" />
										<input type="hidden" id="domain" class="domain" name="domain"
											value="${domain}" />
										<input type="hidden" id="elementTarget" class="elementTarget" name="elementTarget" value="banner_video" />
										<input type="hidden" id="fileUploadType" class="fileUploadType" name="fileUploadType" value="1" />
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
										<div class="FL">
											文件上传进度：<progress value="0" max="100" id="progressFile"></progress>
											 <input type="hidden" name="company.video" id="videoUrlKey" value="">
										 </div>
                            </td>
                        </tr>
                        <tr>
            				<th class="c02">公司品牌</th>
			            	<td>
			               		 <input class="txt" type="text" name="company.brand" id="picUrlKey_brand" value="">
			                </td>
            			</tr>
                        <tr>
                            <td  class="c02"></td>
                            <td class="txt-r">
                                <input class="btn" type="button" value="注册" onclick="submitForm()" id="submit"/>
                            </td>
                        </tr>
                        <tr>
                            <td  class="c02" style="height:140px;"></td>
                            <td>
                                 
                            </td>
                        </tr>
                    </table>
                    </form>
                    <div class="floatdiv02">
                    	 <div class="btnA">
                         	 <a href="${_ctx_ }/page/storePics">专卖店形象列表</a>
                             <a href="${_ctx_ }/page/companyPics">公司图片列表</a>
                         </div>
                    </div>
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