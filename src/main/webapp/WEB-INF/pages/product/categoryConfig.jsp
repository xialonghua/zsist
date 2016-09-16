<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>分类配置</title>
<%@include file="/common/head.jsp"%>


<script type="text/javascript">
var type="";
$(function(){
	$("table tr:even").css("background-color","#EEE");
	categoryList();
	
});
function categoryList(){
	$.ajax({
		url : '${_ctx_}/category/getCategoriesByType',// 跳转到 action
		data : {
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if(data.code == 200){
				var trs = dom.ejs("js_table_tmpl", data);
				$("tr[name='add_tr']").remove();
				$("#tb").append(trs);
			}else {
				JiaFang.showFailedToast("获取失败！" + data.description);
			}
		},
		error : function() {
			JiaFang.showFailedToast("异常！");
		}
	});
}
function getCategoriesByType(id){
	type=id;
	$("#typeList li a").removeClass("cur");
	$("#type_"+id).addClass("cur");
	$.ajax({
		url : '${_ctx_}/category/getCategoriesOfMark',// 跳转到 action
		data : {
			type:type,
			productId:"${productId}"
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if(data.code == 200){
				var trs = dom.ejs("js_table_tmpl", data);
				$("tr[name='add_tr']").remove();
				$("#tb").append(trs);
			}else {
				JiaFang.showFailedToast("获取失败！" + data.description);
			}
		},
		error : function() {
			JiaFang.showFailedToast("异常！");
		}
	});
}

function categotyRelationShipConfig(id,mark){
	if(!type){
		JiaFang.showFailedToast("请选择类型!");
		$("input:checkbox").each(function(){
		 	$(this).attr("checked",false);
	         
	    })
		return;
	}
	if(!!mark){//删除类别关系
		$.ajax({
			url : '${_ctx_}/category/deleteRelationshipById',
			data : {
				categoryId:id,
				productId:"${productId}"
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					JiaFang.showSuccessToast("删除类别关系成功");
					getCategoriesByType(type);
				}else {
					JiaFang.showFailedToast("删除类别关系失败");
				}
			},
			error : function() {
				JiaFang.showFailedToast("异常！");
			}
		});
	}else{//添加类别关系
		$.ajax({
			url : '${_ctx_}/category/addRelationship',
			data : {
				'ship.categoryId':id,
				'ship.productId':"${productId}"
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					JiaFang.showSuccessToast("添加类别关系成功");
					getCategoriesByType(type);
				}else {
					JiaFang.showFailedToast("添加类别关系失败");
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
                      <li>· 您的位置：</li><li><a href="${_ctx_ }/page/productlist">产品管理</a></li><li>></li><li><a href="#">分类配置</a></li>
                  </ul>
              </div>
              <div class="clear"></div>
              <div class="tableBox">
              	   <div class="floatdiv01"></div>
                   <div class="toolBar">
                        <font> </font>
                   </div>
 				   <div id="tab1" class="tab1">
                        <div class="conLeft">
                            <div class="toolBar">
                                <font>类型</font>
                            </div>
                            <ul id="typeList">
                                <li>
                                    <a  id="type_0" href="javascript:getCategoriesByType('0')">·&nbsp;&nbsp;普通类型</a>
                                </li>
                                <li>
                                    <a  id="type_1" href="javascript:getCategoriesByType('1')">·&nbsp;&nbsp;专区类型</a>
                                </li>
								<li>
                                    <a  id="type_2" href="javascript:getCategoriesByType('4')">·&nbsp;&nbsp;广告类型</a>
                                </li>
                            </ul>
                            <div class="clear"></div>
                        </div>
                        <div class="conRight">
                            <div class="toolBar"><font>分类配置</font></div>
                            <table class="table" width="98%" cellpadding="0" cellspacing="0" id="tb">
                                <tr>
                                    <th width="10%">分类ID</th><th width="24%">类别名称</th>
                                </tr>
                            </table>
                            <br/>
                        </div>
                        <div class="clear"></div>
        			</div>
              </div>
         </div>
    </div>
</body>
</html>
<script type="tmpl" id="js_table_tmpl">
        <& for(var i=0,tl = data.length,tr;i < tl;i++){ &>
        <& tr = data[i]; &>
		<tr name="add_tr">
				<td>
					<input id="cat_<&=tr.id &>" onclick="categotyRelationShipConfig(<&=tr.id&>,'<&=tr.mark&>')" class="ck" type="checkbox" <& if(tr.mark){&> checked="checked" <&}&> >
					<&=tr.id &>
				</td>
				<td><&=tr.name &></td>
		
		</tr>
        <& } &>
</script>
