<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>专区管理</title>
<%@include file="/common/headAdmin.jsp"%>
<script type="text/javascript" src="${_ctx_ }/js/web_jiafang.js"></script>
<script type="text/javascript" src="${_ctx_ }/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${_ctx_ }/js/ejs.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	JiaFang.init("${baseUrl}");
	 $.ajax({
			url : '${_ctx_ }/category/getCategoriesByType',// 跳转到 action
			data : {
				page : null,
				type : 1
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					var trs = dom.ejs("js_table_tmpl_zoneClass", data);
					$("option[name='add_option']").remove();
					$("#zoneClass").append(trs);	
					var categoryId = $("#zoneClass").val();
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
	
	
	 $("#zoneClass").change(function(){
		 var categoryId = $("#zoneClass").val();
		 getItems(categoryId);
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
</script>
</head>
<body>
<%@include file="/common/menu.jsp"%>
<div class="breadBox">
    	<ul>
        	<li>· 您的位置：</li><li><a href="#">专区管理 </a></li><li>></li><li><a href="#">专区列表 </a></li>
        </ul>
        <div class="clear"></div>
</div>
<div class="contentBox">
	<form action="" method="post">
	<div class="toolBar">
    	<font>专区列表 </font>
        
        <select style="width:150px;" id="zoneClass">
            <!-- <option value="8">精品推荐</option>
            <option value="9">特价推荐</option> -->
        </select>
        <!-- <input class="btn" type="button" value="删除" onclick=""/> -->
    </div>
    <table class="table" width="98%" cellpadding="0" cellspacing="0" id="table">
    	<tr>
    		<th width="10%">标识列</th>
    		<th width="10%">类型</th>
    		<th width="20%">名称</th>
    		<th width="20%">优先级</th>
    		<!-- <th width="20%">图片</th>
    		<th width="20%">视频</th> -->
    		<th width="20%">操作</th>
        </tr>
        
        
    </table>
    <div class="pager">
    	<a class="btna" href="javascript:void(0);" onclick="">上一页</a>
    	
        <a class="btna" href="javascript:void(0);" onclick="">下一页</a>
    </div>
    </form>
</div>
</body>
</html>
<script type="tmpl" id="js_table_tmpl_zoneClass">
        <& for(var i=0,tl = data.length,tr;i < tl;i++){ &>
        <& tr = data[i]; &>
			<option name="add_option" value="<&=tr.id&>"><&=tr.name&></option>
        <& } &>
</script>
<script type="tmpl" id="js_table_tmpl">
        <& for(var i=0,tl = data[0].items.length,tr;i < tl;i++){ &>
        <& tr = data[0].items[i]; &>
		<tr name="add_tr">
				<td class="txt-c"><input class="ck" type="checkbox" id=<&=tr.categoryShip.id&>><&=i+1&></td>
				<td class="txt-c"><&=tr.targetType&></td>
				<td class="txt-c"><a href="#"><&=tr.name&></a></td>
			    <td class="txt-c">
					<a href="#"><&=tr.categoryShip.weight&></a>
				</td>
				<td class="txt-c"><a href="${_ctx_ }/page/zoneSetWeight?shipId=<&=tr.categoryShip.id&>">设置优先级</a></td>
			</tr>
        <& } &>
</script>