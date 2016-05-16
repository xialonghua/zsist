<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>产品管理</title>
<%@include file="/common/headAdmin.jsp"%>

<script type="text/javascript">
var page=1;
$(document).ready(function() {	
	getProductsByPage(1);
});

function getProductsByPage(p){
	var pageObj = {
			 "page.page":(p-1),
			 "page.pageSize":10
		};
	$.ajax({
		url : '${_ctx_ }/product/getProducts',// 跳转到 action
		data : pageObj,
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if(data.code == 200){
				var entities = data.data;
				if(entities==null||entities.length==0){
					if(page<=1){
						return;
					}
					alert("已经没有数据了！");
					pre();
					return;
				}
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
function pre(){
	if(page<=1){
		page=1;
	}else{
		page=page-1;
	}
	getProductsByPage(page);
	return;
}
function next(){
	page=page+1;
	getProductsByPage(page);
	return;
}

function add(){
	location.href="${_ctx_ }/page/productadd";
}
function modify(id){
	location.href="${_ctx_ }/page/productmodify?productId="+id;
}
function deleteProduct(){
	var ids = new Array();
	$("input:checkbox").each(function(){
	 	if($(this).is(":checked")){
             ids.push($(this).val());
         }
    })
    if(ids.length==0){
    	alert("请选择产品");
    	return;
    }
	if(confirm("确认删除吗?")){
		$.ajax({
			url : '${_ctx_}/product/deleteProduct',// 跳转到 action
			data : {
				'product.id':ids[0]
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					JiaFang.showSuccessToast("删除成功！")
					location.reload();
				}else {
					JiaFang.showFailedToast("删除失败！" + data.description);
				}
			},
			error : function() {
				JiaFang.showFailedToast("异常！");
			}
		});
	}
}

function showView(i,url){
	if(!!url){
		JiaFang.getUrl("banner_video",url);
	}
	$("#dialog").dialog({
		 closeOnEscape: false,
		 width: 400,
		 height: 340,
 		 resizable: true,
		 draggable: true,
		open: function (event, ui) {
                $(".ui-dialog-titlebar-close", $(this).parent()).hide();
               },
		modal: true,
		buttons: [ { text: "Cancel", click: function() { $( this ).dialog( "close" ); } } ]
	});
}

function imagesLoad(i,data){
	JiaFang.getUrl('avatar_'+i,"${fileURL}/"+data); 
}

</script>
</head>
<body>
<%@include file="/common/menu.jsp"%>
<div class="breadBox">
    	<ul>
        	<li>· 您的位置：</li><li><a href="#">产品管理 </a></li><li>></li><li><a href="#">产品列表 </a></li>
        </ul>
        <div class="clear"></div>
</div>
<div class="contentBox">
	<form action="" method="post">
	<div class="toolBar">
    	<font>产品列表 </font>
        <input class="btn" type="button" value="删除" onclick="deleteProduct()"/>
    	
    </div>
    <table class="table" width="100%" cellpadding="0" cellspacing="0" id="table">
    	<tr>
    		<th width="3%">序列</th>
    		<th width="8%">产品货号</th>
    		<th width="10%">产品名称</th>
    		<th width="7%">图片</th>
    		<th width="6%">视频</th>
    		<th width="7%">原价</th>
    		<th width="7%">折扣价</th>
    		<th width="7%">最低订购量</th>
    		<th width="7%">库存</th>
        </tr>
        
        
    </table>
    <div class="pager">
    	<a class="btna" href="javascript:void(0);" onclick="pre()">上一页</a>
    	
        <a class="btna" href="javascript:void(0);" onclick="next()">下一页</a>
    </div>
    </form>
</div>


<div id="dialog" title="" style="display: none">
	<video controls="controls"  width="356" height="200"  id="banner_video" src=""></video>
</div>
</body>
</html>
<script type="tmpl" id="js_table_tmpl">
        <& for(var i=0,tl = data.length,tr;i < tl;i++){ &>
        <& tr = data[i];&>
		<tr name="add_tr">
				<td class="txt-c"><input class="ck" name="checkbox" type="checkbox" value="<&=tr.id&>"></td>
				<td class="txt_word">  <&=tr.num&></td>
				<td class="txt_word"><a href="#"><&=tr.name&></a></td>
	   			<td>  <img src="" id="avatar_<&=i&>" ></td>
				<td>	 
					<a id="video_<&=i&>" href="javascript:showView('<&=i&>','${videoURL}/<&=tr.video&>')">点我观看</a>
				</td>
				<td class="txt_word"><&=tr.price&></td>
				<td class="txt_word"><&=tr.discountPrice&></td>
				<td class="txt_word"><&=tr.saleCount&></td>
				<td class="txt_word"><&=tr.count&></td>
				
				
			</tr>
        <& } &>
</script>
