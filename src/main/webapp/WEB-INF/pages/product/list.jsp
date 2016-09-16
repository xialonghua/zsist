<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>产品管理</title>
<%@include file="/common/head.jsp"%>
	<link rel="stylesheet" type="text/css" href="${_ctx_ }/css/jquery-ui-1.10.4.custom.min.css" />
	<script type="text/javascript" src="${_ctx_ }/js/jquery-ui-1.10.4.custom.min.js"></script>
	<script type="text/javascript" src="${_ctx_ }/js/ejs.js"></script>

<script type="text/javascript">

$(document).ready(function() {
	
	if("${user.level}"==1){
		$.ajax({
			url : '${_ctx_ }/product/getProductsByCompanyUserId',// 跳转到 action
			data : {
				page : null,
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
	if("${user.level}"==2||"${user.level}"==3){
		$.ajax({
			url : '${_ctx_ }/product/getProducts',// 跳转到 action
			data : {
				page : null
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
	
});
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
					alert("删除成功！")
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

function platformChange(id){
	var objS = $("#sel_" + id);
	var grade = objS.children('option:selected').val();

	$.ajax({
		url : '${_ctx_}/product/modifyProductPlatform',// 跳转到 action
		data : {
			'product.id':id,
			'product.showPlatform':grade
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
		},
		error : function() {
			alert("异常！");
		}
	});
}

function imagesLoad(i,data){
	JiaFang.getUrl('avatar_'+i,"${fileURL}/"+data); 
}

</script>
</head>

<body>
	<div class="content">
    	 <div class="wrapper">
         	  <div class="headerBox">
                    <ul id="menuList" class="menuList">
                        <li><a href="${_ctx_ }/page/companyinfo">公司管理<font>Company management</font></a></li>
                        <li><a class="cur" href="${_ctx_ }/page/productlist">产品管理<font>Product management</font></a></li>
                    </ul>
                    <div class="clear"></div>
              </div>
              <div class="floatBox">
                    <div class="cont">
                    	  <div class="logo01"><a href="javascript:void(0);"></a></div>
                          <h3>${SysName}后台管理系统</h3>
                          <div class="infor">
                          	   <img src="${_ctx_ }/images/user.jpg">
                               <p class="ft01">用户级别：公司</p>
                               <p class="ft02">
                               		<a href="${_ctx_ }/page/modifypwd">修改密码</a>
                                    <span>|</span>
                                    <a href="${_ctx_ }/page/logout">退出</a>
                               </p>
                          </div>
                    </div>
              </div>
              <div class="breadBox">
                  <ul>
                      <li>· 您的位置：</li><li><a href="javascript:void(0);">产品管理</a></li><li>></li><li><a href="#">产品列表</a></li>
                  </ul>
              </div>
              <div class="clear"></div>
              <div class="tableBox">
              	   <div class="floatdiv01"></div>
                   <div class="toolBar">
                        <font>产品列表 </font>
                        <input class="btn" type="button" value="添加" onclick="javascript:add()">
                        <input class="btn" type="button" value="删除" onclick="deleteProduct()">
                   </div>
              	   <table class="table table1" width="100%" cellpadding="0" cellspacing="0" id="table">
                      <tr>
                          <th width="3%">序列</th>
                          <th width="8%">产品货号</th>
                          <th width="10%">产品名称</th>
                          <th width="8%">分类</th>
                          <th width="5%">品牌</th>
                          <th width="7%">图片</th>
                          <th width="6%">视频</th>
                          <th width="7%">原价</th>
                          <th width="6%">折扣价</th>
                          <th width="6%">最低订购量</th>
                          <th width="7%">库存</th>
						  <th width="7%">详情</th>
						  <th width="7%">展示</th>
                          <th width="11%">操作</th>
                      </tr>
                  </table>
              </div>
         </div>
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
				<td class="txt_word">
					<&for(var j in tr.categoryShips){ &>
						<&=tr.categoryShips[j].categoryName&>;<br>
					<& } &>
					
				</td>
				<td class="txt_word">  <&=tr.brand&></td>
	   			<td>  <img src="" id="avatar_<&=i&>" ></td>
				<td>	 
					<a id="video_<&=i&>" href="javascript:showView('<&=i&>','${videoURL}/<&=tr.video&>')">点我观看</a>
				</td>
				<td class="txt_word"><&=tr.price&></td>
				<td class="txt_word"><&=tr.discountPrice&></td>
				<td class="txt_word"><&=tr.saleCount&></td>
				<td class="txt_word"><&=tr.count&></td>
				<td class="txt-c"><a class="modify" href="${_ctx_ }/page/productinfo?productId=<&=tr.id&>">参数详情</a></td>
				<td class="txt_word">
					<select id="sel_<&=tr.id&>" onchange="platformChange(<&=tr.id&>)">

						<& if(tr.showPlatform==2){&>
							<option value="2" selected="selected">所有</option>
						<&}else {&>
						    <option value="2">所有</option>
						<&}&>
						<& if(tr.showPlatform==1){&>
							<option value="1" selected="selected">私有</option>
						<&}else {&>
						    <option value="1">私有</option>
						<&}&>
						<& if(tr.showPlatform==0){&>
							<option value="0" selected="selected">平台</option>
						<&}else {&>
						    <option value="0">平台</option>
						<&}&>
    				</select>
				</td>
				<td class="txt-c">
					<a class="modify" href="javascript:modify(<&=tr.id&>)">修改</a>&nbsp;
					<a class="modify" href="${_ctx_ }/page/productconfig?productId=<&=tr.id&>">分类配置</a>
				</td>
			</tr>
        <& } &>
</script>
