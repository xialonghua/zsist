<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<title>设置优先级</title>
<%@include file="/common/headAdmin.jsp"%>
<link rel="stylesheet" type="text/css" href="${_ctx_ }/css/new_css.css" />
<script type="text/javascript" src="${_ctx_ }/js/popup.js"></script>
<script type="text/javascript">
var page = 1;
var productIds="";
$(document).ready(function(){
	$.ajax({
		url : '${_ctx_}/category/getItemByShipId',
		data : {
			shipId:"${shipId}"
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if(data.code == 200){
				$("#categoryId").val(data.data.categoryShip.categoryId);
				$("#productId").val(data.data.id);
				$("#productName").val(data.data.name);
				$("#weight").val(data.data.categoryShip.weight);
			}
		},
		error : function() {
			JiaFang.showFailedToast("异常！");
		}
	});
});

function submitForm(){
	$.post("${_ctx_}/category/updateRelationshipWeight",$("#addForm").serialize(),function(data) {
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
        	<li>· 您的位置：</li><li><a href="${_ctx_ }/page/zonelist">专区管理</a></li><li>></li><li><a href="#">专区优先级设置</a></li>
        </ul>
        <div class="clear"></div>
    </div>
    <div class="contentBox">
    	<form id="addForm">
    	<div class="toolBar">
        	<font>优先级设置</font>
        </div>
        <input class="txt" type="hidden" value="${shipId }" name="ship.id" id="shipId"/>
        <input class="txt" type="hidden" value="${categoryId }" name="categoryId" id="categoryId"/>
        <table class="table" width="98%" cellpadding="0" cellspacing="0">
            <tr>
            	<th width="19%">产品ID</th>
                <td width="81%"><input class="txt" type="text" value="" name="productId" id="productId" readonly="readonly"/></td>
            </tr>
            <tr>
            	<th width="19%">产品名称</th>
                <td width="81%"><input class="txt" type="text" value="" name="productName" id="productName" readonly="readonly"/></td>
            </tr>
            <tr>
            	<th width="19%">优先级</th>
                <td width="81%"><input class="txt" type="text" value="" name="ship.weight" id="weight" /></td>
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
    
    <div class="popup lydz">
		<div class="hd">
			<span class="title">选择产品</span>
			<div class="closeIcon">&times;</div>
		</div>
		<div class="bd">
			<table width="100%" align="center" cellpadding="10" id="apks">
				<tr>
	        		<th width="20%">ID</th>
	        		<th width="20%">产品货号</th>
	        		<th width="20%">产品名称</th>
	        		<th width="20%">绑定</th>
				</tr>
			</table>
		</div>
		<div class="pager">
        	<a class="btna" href="javascript:void(0);" onclick="pre()">上一页</a>
            <a class="btna" href="javascript:void(0);" onclick="next()">下一页</a>
        </div>
		<div class="ft">
			<input class="btn queding" type="button" value="确定"/>
            <input class="btn quxiao" type="button" value="取消"/>
		</div>
	</div>
</body>
</html>
