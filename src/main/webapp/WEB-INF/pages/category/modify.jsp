<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta charset="utf-8">
<title>修改类别</title>
<%@include file="/common/headAdmin.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		url : '${_ctx_}/category/getCategory',// 跳转到 action
		data : {
			'categoryId':"${categoryId}"
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if(data.code == 200){
				var category = data.data;
				
				$("#name").val(category.name);
				$("#type").val(category.type);
				
				$("#banner").attr("src","${fileURL}/"+category.avatar);
				$("#video").attr("src","${fileURL}/"+category.video);
				$("#picUrlKey").val(category.avatar);
				$("#videoUrlKey").val(category.video);
				
				$("#type option").each(function() { 
			     	if($(this).val().replace(/[ ]/g,"") == category.type){
			     		$(this).attr("selected","selected");
			     	}else{
			    		$(this).remove();
			   		}
			     });
			}else {
				alert("获取失败！" + data.description);
			}
		},
		error : function() {
			alert("异常！");
		}
	});
	
	
	$("#picUrl").change(function(){
		 var file = document.getElementById("picUrl").files[0];
		 if (!/image\/\w+/.test(file.type)) {
	            alert("请选择图片！");
	            return false;
	     }
		 if(file.size>200*1024){
			 alert("图片大小不能超过200kb！");
	         return false;
		 }
		 var reader = new FileReader();
	     reader.readAsDataURL(file);
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
	
	$("#videoUrl").change(function(){
		 var file = document.getElementById("videoUrl").files[0];
		 alert(file.type);
		 var reader = new FileReader();
	     reader.readAsDataURL(file);
		 reader.onload = function(e) {
	            $("#video").attr("src",this.result);
	            var videosrc=this.result;
	            videosrc=videosrc.substr(videosrc.indexOf("base64,") + 7);
	            $.ajax({
	    			url : '${_ctx_}/res/uploadResToQiNiu',// 跳转到 action
	    			data : {
	    				file : videosrc,
	    			},
	    			type : 'post',
	    			cache : false,
	    			dataType : 'json',
	    			success : function(data) {
	    				if(data.code == 200){
	    					$("#videoUrlKey").val(data.data);
	    				}else {
	    					alert("获取失败！" + data.description);
	    				}
	    			},
	    			error : function() {
	    				// view("异常！");
	    				alert("异常！");
	    			}
	    		});
		 }
	});
});
function submitForm(){
	$.post("${_ctx_}/category/modifyCategory",$("#modForm").serialize(),function(data) {
		if(data.code==200){
			alert("修改分类成功！");
			window.location.href="${_ctx_ }/page/categorylist";
		}
		else {
			alert("修改分类失败！");
		}
	},"json");
}
function cancel(){
	window.history.back();
}
</script>
</head>
<body>
    <%@include file="/common/menu.jsp"%>
    <div class="breadBox">
    	<ul>
        	<li>· 您的位置：</li><li><a href="${_ctx_ }/page/categorylist">分类管理</a></li><li>></li><li><a href="#">类别添加</a></li>
        </ul>
        <div class="clear"></div>
    </div>
    <div class="contentBox">
    	<form id="modForm">
    	<div class="toolBar">
        	<font>添加类别</font>
        </div>
        <input class="txt" type="hidden" value="${categoryId}" name="category.id" id="categoryId"/>
        <table class="table" width="98%" cellpadding="0" cellspacing="0">
            <tr>
            	<th width="19%">名称</th>
                <td width="81%"><input class="txt" type="text" value="" name="category.name" id="name"/></td>
            </tr>
            
            <tr>
            	<th width="19%">类型</th>
                <td>
                	<select style="width:97%;" name="category.type" id="type">
                    	<option value="0">普通类</option>
            			<option value="1">专区类</option>
           	 			<option value="2">广告类</option>
                    </select>
                </td>
            </tr>
     
             <tr>
            	<th width="19%">图片</th>
            	<td>
                	 <img id="banner" alt="" src="" width="100px"> 
               		 <input class="txt" type="file" value="上传图片" name="picUrl" id="picUrl"/>
               		 <input type="hidden" name="category.avatar" id="picUrlKey" value="">
                </td>
            	
            </tr>
            <tr>
            	<th width="19%">视频（.MP4）</th>
            	<td>
                	 <video controls="controls"  width="240" height="160"   id="video" src=""></video> 
               		 <input class="txt" type="file" value="上传视频" name="videoUrl" id="videoUrl"/>
               		 <input type="hidden" name="category.video" id="videoUrlKey" value="">
                </td>
            	
            </tr>
            <tr>
            	<th valign="top"></th>
                <td class="txt-r">
                	<input class="btn" type="button" value="修改" onclick="submitForm()" id="submit"/>
                    <input class="btn" type="button" value="取消" onclick=""/>
                </td>
            </tr>
        </table>
        </form>
    </div>
</body>
</html>