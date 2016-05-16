<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<title>添加专区产品</title>
<%@include file="/common/headAdmin.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$("#picUrl").change(function(){
		 var file = document.getElementById("picUrl").files[0];
		 if (!/image\/\w+/.test(file.type)) {
	            alert("请选择图片！");
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
	
	
	$("#targetType").change(function(){
		$("#productId").val("");
		$("#companyId").val("");
		var type = $("#targetType").val();
		if(type==0){
			$("#targetProduct").show();
			$("#targetCompany").hide();
		}
		if(type==1){
			$("#targetProduct").hide();
			$("#targetCompany").show();
		}
	});
});
function submitForm(){
	$.post("${_ctx_}/category/addRelationship",$("#addForm").serialize(),function(data) {
		if(data.code==200){
			$("#submit").attr('disabled',true);
			alert("添加成功！");
			setTimeout(function(){
				//window.location.href="${_ctx_ }/page/productlist";
			}, 3000);
		}
		else {
			alert("添加失败！"+data.description);
		}
	},"json");
}
function cancel(){
	
}
</script>
</head>
<body>
    <%@include file="/common/menu.jsp"%>
    <div class="breadBox">
    	<ul>
        	<li>· 您的位置：</li><li><a href="${_ctx_ }/page/zonelist">专区管理</a></li><li>></li><li><a href="#">添加专区产品</a></li>
        </ul>
        <div class="clear"></div>
    </div>
    <div class="contentBox">
    	<form id="addForm">
    	<div class="toolBar">
        	<font>添加专区产品</font>
        </div>
        <input class="txt" type="hidden" value="${categoryId }" name="ship.categoryId" id="categoryId"/>
        <table class="table" width="98%" cellpadding="0" cellspacing="0">
            <tr>
            	<th>类型</th>
                <td>
                	<select style="width:97%;" name="targetType" id="targetType">
                    	<option value="0">产品 </option>
                        <option value="1">厂家</option>
                    </select>
                </td>
            </tr>
            
            <tr id="targetProduct">
            	<th width="19%" >产品ID</th>
                <td width="81%"><input class="txt" type="text" value="" name="ship.productId" id="productId"/></td>
            </tr>
            
            <tr id="targetCompany" style="display: none">
            	<th width="19%" >厂家ID</th>
                <td width="81%"><input class="txt" type="text" value="" name="ship.companyId" id="companyId"/></td>
            </tr>
            
           <tr>
            	<th width="19%">图片</th>
            	<td>
                	 <img id="banner" alt="" src="" width="100px"> 
               		 <input class="txt" type="file" value="上传图片" name="picUrl" id="picUrl"/>
               		 <input type="hidden" name="ship.avatar" id="picUrlKey" value="">
                </td>
            	
            </tr>
            <tr>
            	<th width="19%">视频（.MP4）</th>
            	<td>
                	 <video controls="controls"  width="240" height="160"   id="video" src=""></video> 
               		 <input class="txt" type="file" value="上传视频" name="videoUrl" id="videoUrl"/>
               		 <input type="hidden" name="ship.video" id="videoUrlKey" value="">
                </td>
            	
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