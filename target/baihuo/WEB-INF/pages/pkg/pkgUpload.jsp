<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<title>添加客户端应用包</title>
<%@include file="/common/head.jsp"%>
<%@include file="/common/videoUpload.jsp"%>
<link rel="stylesheet" type="text/css" href="${_ctx_ }/css/crop.css" />
<script type="text/javascript" src="${_ctx_ }/js/cropbox.js"></script>
<script type="text/javascript">

var validform;
$(document).ready(function(){
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
	
});
function submitForm(){
	/* if (!validform.form()) {
		return false;
	} */
	$.post("${_ctx_}/product/addProduct",$("#addForm").serialize(),function(data) {
		if(data.code==200){
			$("#submit").attr('disabled',true);
			JiaFang.showSuccessToast("上传开发包产品成功！");
			window.location.href="${_ctx_ }/page/productlist";
		}
		else {
			JiaFang.showFailedToast("上传开发包失败！");
		}
	},"json");
}

function cancel(){
	window.history.back();
}
</script>
</head>
<body>
    <%@include file="/common/menu2.jsp"%>
    <div class="breadBox">
    	<ul>
        	<li>· 您的位置：</li><li><a href="${_ctx_ }/page/productlist">开发包管理</a></li><li>></li><li><a href="#">添加开发包</a></li>
        </ul>
        <div class="clear"></div>
    </div>
    <div class="contentBox">
    	<form id="addForm">
    	<div class="toolBar">
        	<font>添加开发包</font>
        </div>
        <table class="table" width="98%" cellpadding="0" cellspacing="0">
        	<tr>
            	<th width="19%">类型</th>
                <td>
                	<select style="width:97%;" name="platform" id="platform">
                    	<option value="0">IOS</option>
            			<option value="1">Android</option>
                    </select>
                </td>
            </tr>
            <tr>
            	<th width="19%">开发包</th>
            	<td>
               		 <video controls="controls"  width="340" height="200"   id="banner_video" src=""></video>
                	<input type="hidden" id="token" class="token" name="token"
	value="${token}" />
	<input type="hidden" id="domain" class="domain" name="domain"
	value="${domain}" />
									<input type="hidden" id="fileUploadType"  name="fileUploadType" value="" />
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
					 <input type="hidden" name="url" id="url" value="">
               		 
                </td>
            </tr>
            
            
            <tr>
            	<th width="19%">版本</th>
                <td width="81%"><input class="txt" type="text" value="" name="version" id="version"/></td>
            </tr>
            <tr>
            	<th width="19%">描述</th>
                <td width="81%"><input class="txt" type="text" value="" name="desp" id="desp"/></td>
            </tr>
           
            <tr>
            	<th valign="top"></th>
                <td class="txt-r">
                	<input class="btn" type="button" value="添加" onclick="submitForm()" id="submit"/>
                    <input class="btn" type="button" value="取消" onclick="cancel()"/>
                </td>
            </tr>
        </table>
        </form>
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