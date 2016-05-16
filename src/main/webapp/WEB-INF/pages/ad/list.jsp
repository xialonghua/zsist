<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>顶部广告管理</title>
<%@include file="/common/headAdmin.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
	 $.ajax({
			url : '${_ctx_ }/category/getCategoriesByType',// 跳转到 action
			data : {
				page : null,
				type : 2
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					var trs = dom.ejs("js_table_tmpl_adClass", data);
					$("option[name='add_option']").remove();
					$("#adClass").append(trs);	
					var categoryId = $("#adClass").val();
					getItems(categoryId);
				}else {
					alert("获取失败！" + data.description);
				}
			},
			error : function() {
				// view("异常！");
				alert("异常！");
			}
		});
	
	
	 
});
function getItems(categoryId){
	$.ajax({
		url : '${_ctx_ }/category/getItemsById',// 跳转到 action
		data : {
			page : null,
			categoryId : categoryId
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if(data.code == 200){
				var trs = dom.ejs("js_table_tmpl", data);
				$("tr[name='add_tr']").remove();
				$("#table").append(trs);	
				
				var list = data.data[0].items;
				if(!!list){
					for(var i=0;i<list.length;i++){
						imagesLoad(i,list[i].categoryShip.avatar);
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

function add(){
	var categoryId = $("#adClass").val();
	location.href="${_ctx_}/page/adadd?categoryId="+categoryId;
}

function imagesLoad(i,data){
	JiaFang.getUrl('avatar_'+i,"${fileURL}/"+data); 
}
function showView(i,url){
	if(!!url){
		JiaFang.getUrl("banner_video",url);
	}
	$("#dialog").dialog({
		 closeOnEscape: false,
		 width: 500,
		 height: 300,
 		 resizable: false,
		 draggable: false,
		open: function (event, ui) {
                $(".ui-dialog-titlebar-close", $(this).parent()).hide();
               },
		modal: true,
		buttons: [ { text: "Cancel", click: function() { $( this ).dialog( "close" ); } } ]
	});
}
function deleteRelationShip(categoryId,productId){
	if(confirm("确认删除?")){
		$.ajax({
			url : '${_ctx_}/category/deleteRelationshipById',
			data : {
				categoryId:categoryId,
				productId:productId
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					JiaFang.showSuccessToast("删除广告成功");
					$("#product_"+productId).remove();
					//getCategoriesByType(type);
				}else {
					JiaFang.showFailedToast("删除广告失败");
				}
			},
			error : function() {
				JiaFang.showFailedToast("异常！");
			}
		});
	}
}
</script>
</head>
<body>
<%@include file="/common/menu.jsp"%>
<div class="breadBox">
    	<ul>
        	<li>· 您的位置：</li><li><a href="#">顶部广告管理 </a></li><li>></li><li><a href="#">广告列表 </a></li>
        </ul>
        <div class="clear"></div>
</div>
<div class="contentBox">
	<form action="" method="post">
	<div class="toolBar">
    	<font>广告列表 </font>
        
        <select style="width:150px;" id="adClass">
        	<!-- <option value="0">普通类</option>
            <option value="1">专区类</option>
            <option value="2">广告类</option> -->
        </select>
        <input class="btn" type="button" value="归类" onclick="add()"/>
        <!-- <input class="btn" type="button" value="删除" onclick=""/> -->
    </div>
    <table class="table" width="98%" cellpadding="0" cellspacing="0" id="table">
    	<tr>
    		<th width="10%">标识ID</th>
    		<th width="20%">产品名称</th>
    		<th width="20%">产品货号</th>
    		<th width="20%">优先级</th>
    		<th width="20%">操作</th>
        </tr>
        
        
    </table>
    <div class="pager">
    	<a class="btna" href="javascript:void(0);" onclick="">上一页</a>
        <a class="btna" href="javascript:void(0);" onclick="">下一页</a>
    </div>
    </form>
</div>

<div id="dialog" title="" style="display: none">
	<video controls="controls"  width="450" height="200"  id="banner_video" src=""></video>
</div>
</body>
</html>
<script type="tmpl" id="js_table_tmpl_adClass">
        <& for(var i=0,tl = data.length,tr;i < tl;i++){ &>
        <& tr = data[i]; &>
			<option name="add_option" value="<&=tr.id&>"><&=tr.name&></option>
        <& } &>
</script>
<script type="tmpl" id="js_table_tmpl">
        <& for(var i=0,tl = data[0].items.length,tr;i < tl;i++){ &>
        <& tr = data[0].items[i]; &>
		<tr name="add_tr" id="product_<&=tr.id&>">
				<td class="txt-c"><input class="ck" type="checkbox" id=<&=tr.categoryShip.id&>><&=tr.id&></td>
				<td class="txt-c"><a href="#"><&=tr.name&></a></td>
				<td class="txt-c"><&=tr.num&></td>
				<td class="txt-c"><&=tr.categoryShip.weight&></td>
				<td class="txt-c">
					<a href="javascript:deleteRelationShip('<&=tr.categoryShip.categoryId&>','<&=tr.id&>')">删除广告</a>
					<a href="${_ctx_ }/page/adSetWeight?shipId=<&=tr.categoryShip.id&>">设置优先级</a>
				</td>
			</tr>
        <& } &>
</script>