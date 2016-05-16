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
           	 <li><a class="cur1" href="#">用户级别：公司</a></li>
             <li><a class="cur1" href="${_ctx_ }/page/modifypwd">修改密码</a></li>
           	 <li><a class="cur1" href="${_ctx_ }/page/logout">退出</a></li>
           </ul>
           <ul id="menuList" class="menuList">
	           	   <li class="li">
	               	 <a href="javascript:void(0)">公司管理  ∨</a>
	                   <ul class="list display_n">
	                   		<li> <a href="${_ctx_ }/page/companyinfo">信息管理</a> </li>
	                   		<div class="clear"></div>
	                   </ul>
	               </li>
	            
	               <li class="li">
	               	<a href="javascript:void(0)">产品管理  ∨</a>
	               		<ul class="list display_n">
		                   	<li> <a href="${_ctx_ }/page/productlist">产品列表</a> </li>
		                  <%--  	<li> <a href="${_ctx_ }/page/productadd">添加产品</a> </li> --%>
		                   	<div class="clear"></div>
	                   </ul>
	               </li>
	               
	               <li class="li">
	               		<ul class="list display_n">
		                   	
	                   </ul>
	               </li>
	               
           </ul>
    </div>
    
<script type="text/javascript">
var Width=$(".menuList li").width();
$(".list").css("width",Width);
</script>
</div>
</body>
</html>