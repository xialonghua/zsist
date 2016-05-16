<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<title>添加产品</title>
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
});
function submitForm(){
	$.post("${_ctx_}/web/product/addProductParam",$("#addForm").serialize(),function(data) {
		if(data.code==200){
			$("#submit").attr('disabled',true);
			JiaFang.showSuccessToast("添加成功！");
			window.location.href="${_ctx_ }/page/productinfo?productId=${productId }";
			
		}
		else {
			JiaFang.showFailedToast("添加失败！");
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
        	<li>· 您的位置：</li><li><a href="${_ctx_ }/page/productlist">产品管理</a></li><li>></li><li><a href="#">添加产品</a></li>
        </ul>
        <div class="clear"></div>
    </div>
    <div class="contentBox">
    	<form id="addForm">
    	<div class="toolBar">
        	<font>添加产品参数</font>
        </div>
        <input class="txt" type="hidden" value="${productId }" name="param.productId" id="param.productId"/>
        <table class="table" width="98%" cellpadding="0" cellspacing="0">
            <!-- <tr>
            	<th width="19%">标题</th>
                <td width="81%"><input class="txt" type="text" value="" name="param.title" id="param.title"/></td>
            </tr> -->
            <tr>
            	<th width="19%">参数</th>
                <td width="81%"><input class="txt" type="text" value="" name="param.name" id="param.name"/></td>
            </tr>
            <tr>
            	<th width="19%">数值</th>
                <td width="81%"><input class="txt" type="text" value="" name="param.value" id="param.value"/></td>
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