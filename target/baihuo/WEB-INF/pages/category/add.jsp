<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta charset="utf-8">
<title>添加类别</title>
<%@include file="/common/headAdmin.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
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
});
function submitForm(){
	$.post("${_ctx_}/category/addCategory",$("#addForm").serialize(),function(data) {
		if(data.code==200){
			alert("添加类名成功！");
			window.location.href="${_ctx_ }/page/categorylist";
		}
		else {
			alert("添加类名失败！");
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
    	<form id="addForm">
    	<div class="toolBar">
        	<font>添加类别</font>
        </div>
        <table class="table" width="98%" cellpadding="0" cellspacing="0">
            <tr>
            	<th width="19%">名称</th>
                <td width="81%"><input class="txt" type="text" value="" name="category.name" id="category.name"/></td>
            </tr>
            
            <tr>
            	<th width="19%">类型</th>
                <td>
                	<select style="width:97%;" name="category.type" id="type">
                    	<option value="0">普通类</option>
            			<!-- <option value="1">专区类</option>
           	 			<option value="2">广告类</option> -->
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
            <!-- <tr>
            	<th width="19%">视频（.MP4）</th>
            	<td>
                	 <video controls="controls"  width="240" height="160"   id="video" src=""></video> 
               		 <input class="txt" type="file" value="上传视频" name="videoUrl" id="videoUrl"/>
               		 <input type="hidden" name="category.video" id="videoUrlKey" value="">
                </td>
            	
            </tr> -->
            <tr>
            	<th valign="top"></th>
                <td class="txt-r">
                	<input class="btn" type="button" value="确定" onclick="submitForm()" id="submit"/>
                    <input class="btn" type="button" value="取消" onclick="cancel()"/>
                </td>
            </tr>
        </table>
        </form>
    </div>
</body>
</html>