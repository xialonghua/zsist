<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>分类管理</title>
<%@include file="/common/headAdmin.jsp"%>
<script type="text/javascript" src="${_ctx_ }/js/web_jiafang.js"></script>
<script type="text/javascript" src="${_ctx_ }/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${_ctx_ }/js/ejs.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	JiaFang.init("${baseUrl}");
	var type = $("#typeSelect").val();
	getCategoriesByType(type);
	
	$("#typeSelect").change(function(){
		var type = $("#typeSelect").val();
		getCategoriesByType(type);
	});
});
function getCategoriesByType(type){
	$.ajax({
		url : '${_ctx_ }/category/getCategoriesByType',// 跳转到 action
		data : {
			page : null,
			type: type
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if(data.code == 200){
				var trs = dom.ejs("js_table_tmpl", data);
				$("tr[name='add_tr']").remove();
				$("#table").append(trs);
				
				var list = data.data;
				if(!!list){
					for(var i=0;i<list.length;i++){
						imagesLoad(i,list[i].avatar);
					}
				}
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
function imagesLoad(i,data){
	JiaFang.getUrl('avatar_'+i,"${fileURL}/"+data); 
}

function deleteCategory(){
	var ids = new Array();
	$("input:checkbox").each(function(){
	 	if($(this).is(":checked")){
             ids.push($(this).val());
         }
    })
    if(ids.length==0){
    	alert("请选择类别");
    	return;
    }
	if(confirm("确认删除吗?")){
		$.ajax({
			url : '${_ctx_}/category/deleteCategory',// 跳转到 action
			data : {
				'category.id':ids[0]
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					alert("删除成功！");
					location.reload();
				}else {
					alert("删除失败！" + data.description);
				}
			},
			error : function() {
				alert("异常！");
			}
		});
	}
}

function addCategory(){
	window.location.href="${_ctx_}/page/categoryadd";
}
</script>
</head>
<body>
<%@include file="/common/menu.jsp"%>
<div class="breadBox">
    	<ul>
        	<li>· 您的位置：</li><li><a href="#">分类管理 </a></li><li>></li><li><a href="#">类别列表 </a></li>
        </ul>
        <div class="clear"></div>
</div>
<div class="contentBox">
	<form action="" method="post">
	<div class="toolBar">
    	<font>类别列表 </font>
        
        <select style="width:150px;" id="typeSelect">
        	<option value="0">普通类</option>
            <option value="1">专区类</option>
            <option value="2">广告类</option>
        </select>
    	<input class="btn" type="button" value="添加类名" onclick="addCategory()"/>
        <input class="btn" type="button" value="删除类名" onclick="deleteCategory()"/>
    </div>
    <table class="table" width="98%" cellpadding="0" cellspacing="0" id="table">
    	<tr>
    		<th width="9%">ID</th>
    		<th width="20%">类名</th>
    		<th width="10%">类型</th>
    		<th width="11%">图片</th>
    		<th width="11%">视频</th>
    		<th width="11%">操作</th>
        </tr>
        
        
    </table>
    
    </form>
</div>
</body>
</html>
<script type="tmpl" id="js_table_tmpl">
        <& for(var i=0,tl = data.length,tr;i < tl;i++){ &>
        <& tr = data[i]; &>
		<tr name="add_tr">
				<td class="txt-c"><input class="ck" type="checkbox" value=<&=tr.id&> ><&=tr.id&></td>
				<td><a href="#"><&=tr.name&></a></td>
	   			<td><&=tr.type&></td>
			  	<td class="txt-c"><img src="" id="avatar_<&=i&>" ></td>
				<td class="txt-c"><a class="modify" href="">视频</a></td>
				<td class="txt-c"><a class="modify" href="${_ctx_}/page/categorymodify?categoryId=<&=tr.id&>">修改分类</a></td>
			</tr>
        <& } &>
</script>