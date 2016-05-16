<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>菜单</title>
</head>
<body>
<div class="headerBox">
   	<div class="header wd1000">
   		
       	<span><img alt="" src="${_ctx_}/image/logo_zs.png" style="width:50px;height:50px">智盛信息管理平台</span>
           <ul id="language" class="language">
             <li><a class="cur1" href="${_ctx_ }/page/modifypwd">修改密码</a></li>
           	 <li><a class="cur1" href="${_ctx_ }/page/logout">退出</a></li>
           </ul>
           <ul id="menuList" class="menuList">
	           	   <li class="li">
	               	 <a href="#">分类管理  ∨</a>
	                   <ul class="list display_n">
	                   		<li class="li">
		           				<a href="${_ctx_ }/page/categorylist">类别列表</a>
		           			</li>
	                   </ul>
	               </li>
	            
	               <li class="li">
	               	<a href="#">顶部广告管理  ∨</a>
	               		<ul class="list display_n">
		                   	<li class="li">
		           				<a href="${_ctx_ }/page/adlist">广告列表</a>
		           			</li>
	                   </ul>
	               </li>
	               
	               <li class="li">
	               	<a href="#">专区管理  ∨</a>
	               		<ul class="list display_n">
		                   	<li class="li">
		           				<a href="${_ctx_ }/page/zonelist">专区列表</a>
		           			</li>
	                   </ul>
	               </li>
	               
	               <li class="li">
	               	<a href="#">产品管理  ∨</a>
	               		<ul class="list display_n">
	               			<li class="li">
		           				<a href="${_ctx_ }/page/productlist_admin">产品列表</a>
		           			</li>
	                   </ul>
	               </li>
	               
	               <li class="li">
	               	<a href="#">公司管理  ∨</a>
	               		<ul class="list display_n">
	               			<li class="li">
		           				<a href="${_ctx_ }/page/companylist">公司列表</a>
		           			</li>
	                   </ul>
	               </li>
              		
              		<!-- <li class="li">
	              		<a href="#">账号管理  ∨</a>
	             		 <ul class="list display_n">
				       		<li class="li">
		           				<a href="">添加账号</a>
		           			</li>
	              		</ul>
	          		</li> -->
             		<%-- <li class="li">
	               	<a href="#">开发包管理  ∨</a>
	               		<ul class="list display_n">
	               			<li class="li">
		           				<a href="#">开发包列表</a>
		           			</li>
		           			<li class="li">
		           				<a href="${_ctx_ }/page/pkgupload">开发包更新</a>
		           			</li>
	                   </ul>
	               </li> --%>
           </ul>
    </div>
</div>

<script type="text/javascript">
var Width=$(".menuList li").width();
$(".list").css("width",Width);
</script>
</body>
</html>