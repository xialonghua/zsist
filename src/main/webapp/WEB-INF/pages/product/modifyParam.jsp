<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<title>修改产品参数</title>
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
		url : '${_ctx_}/web/product/getProductParamById',// 跳转到 action
		data : {
			paramId:"${paramId}"
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if(data.code == 200){
				var param = data.data;
				$("#name").val(param.name);
				//$("#title").val(param.title);
				$("#value").val(param.value);
				$("#productId").val(param.productId);
			}else {
				JiaFang.showFailedToast("获取产品参数失败！" + data.description);
			}
		},
		error : function() {
			JiaFang.showFailedToast("获取产品参数失败！");
		}
	});
	
});

function submitForm(){
	$.post("${_ctx_}/web/product/modifyProductParam",$("#addForm").serialize(),function(data) {
		if(data.code==200){
			$("#submit").attr('disabled',true);
			JiaFang.showSuccessToast("修改产品参数成功！");
			location.reload();
		}
		else {
			JiaFang.showFailedToast("修改产品参数失败！");
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
        	<li>· 您的位置：</li><li><a href="${_ctx_ }/page/productlist">产品管理</a></li><li>></li><li><a href="#">修改产品参数</a></li>
        </ul>
        <div class="clear"></div>
    </div>
    <div class="contentBox">
    	<form id="addForm">
    	<div class="toolBar">
        	<font>修改产品参数</font>
        </div>
        <input class="txt" type="hidden" value="${paramId}" name="param.id" id="param.id"/>
        <input class="txt" type="hidden" value="${produtId}" name="param.productId" id="param.productId"/>
        <table class="table" width="98%" cellpadding="0" cellspacing="0">
           <!--  <tr>
            	<th width="19%">标题</th>
                <td width="81%"><input class="txt" type="text" value="" name="param.title" id="title"/></td>
            </tr> -->
            <tr>
            	<th width="19%">参数</th>
                <td width="81%"><input class="txt" type="text" value="" name="param.name" id="name"/></td>
            </tr>
            <tr>
            	<th width="19%">数值</th>
                <td width="81%"><input class="txt" type="text" value="" name="param.value" id="value"/></td>
            </tr>
           
            <tr>
            	<th valign="top"></th>
                <td class="txt-r">
                	<input class="btn" type="button" value="修改" onclick="submitForm()" id="submit"/>
                    <input class="btn" type="button" value="取消" onclick="cancel()"/>
                </td>
            </tr>
        </table>
        </form>
    </div>
</body>
</html>