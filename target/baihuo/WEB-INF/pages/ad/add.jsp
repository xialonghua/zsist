<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<title>添加广告产品</title>
<%@include file="/common/headAdmin.jsp"%>
<script type="text/javascript" src="${_ctx_ }/js/popup.js"></script>
<script type="text/javascript">
var page = 1;
var productIds="";
$(document).ready(function(){
});
function getProductsByPage(p){
	var pageObj = {
		 "page.page":(p-1),
		 "page.pageSize":10
	};
	page = p;
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
				$("#productList").append(trs);	
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

function submitForm(){
	$.post("${_ctx_}/category/addAdvertisementRelationship",$("#addForm").serialize(),function(data) {
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

function bindProductIds(){
	
	myPopup({
		"elem" : ".popup.lydz",
		"close" : ".popup.lydz .closeIcon"
	});
	getProductsByPage(1);
	
}
$(function() {
	$(".queding").click(function() {
		$("#productIds").val(productIds.substring(1));
		$(".popup.lydz").hide();
		$("#mask").hide();
	});
	
	$(".quxiao").click(function() {
		$(".popup.lydz").hide();
		$("#mask").hide();
	});
});
function bindProduct(id){
	productIds+=","+id;
}
</script>
</head>
<body>
    <%@include file="/common/menu.jsp"%>
    <div class="breadBox">
    	<ul>
        	<li>· 您的位置：</li><li><a href="${_ctx_ }/page/adlist">顶部广告管理</a></li><li>></li><li><a href="#">添加广告产品</a></li>
        </ul>
        <div class="clear"></div>
    </div>
    <div class="contentBox">
    	<form id="addForm">
    	<div class="toolBar">
        	<font>添加产品</font>
        </div>
        <input class="txt" type="hidden" value="${categoryId }" name="categoryId" id="categoryId"/>
        <table class="table" width="98%" cellpadding="0" cellspacing="0">
            <tr>
            	<th width="19%">产品ID</th>
                <td width="81%"><input class="txt" type="text" value="" name="productIds" id="productIds" readonly="readonly" onclick="bindProductIds()"/></td>
            </tr>
           <!-- <tr>
            	<th width="19%">广告图片</th>
            	<td>
                	 <img id="banner" alt="" src="" width="100px"> 
               		 <input class="txt" type="file" value="上传图片" name="picUrl" id="picUrl"/>
               		 <input type="hidden" name="ship.avatar" id="picUrlKey" value="">
                </td>
            	
            </tr>
            <tr>
            	<th width="19%">广告视频（.MP4）</th>
            	<td>
                	 <video controls="controls"  width="240" height="160"   id="video" src=""></video> 
               		 <input class="txt" type="file" value="上传视频" name="videoUrl" id="videoUrl"/>
               		 <input type="hidden" name="ship.video" id="videoUrlKey" value="">
                </td>
            	
            </tr> -->
            
            
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
			<table width="100%" align="center" cellpadding="10" id="productList">
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
<script type="tmpl" id="js_table_tmpl">
        <& for(var i=0,tl = data.length,tr;i < tl;i++){ &>
        <& tr = data[i];&>
		<tr name="add_tr">
				<td class="txt-c"><&=tr.id&></td>
				<td class="txt_word">  <&=tr.num&></td>
				<td class="txt_word"><a href="#"><&=tr.name&></a></td>
				<td class="txt_word"><input class="ck" name="checkbox" type="checkbox" value="<&=tr.id&>" onclick="bindProduct('<&=tr.id&>')"></td>
			</tr>
        <& } &>
</script>