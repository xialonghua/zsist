<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公司管理</title>
<%@include file="/common/headAdmin.jsp"%>

<script>
$(document).ready(function(){ 	 
	$.ajax({
		url : '${_ctx_ }/company/getCompanies',// 跳转到 action
		data : {
			"page.page" :0,
			"page.pageSize": 999
			
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
});
function addNews(){
	
}
function delNews(){
	/* if(confirm("确认删除?")){
		var newsIds ="";
		$("input:checkbox").each(function(){
		 	if($(this).attr("checked")=="checked"){
		 		newsIds += $(this).attr('id') + ",";
		 	}
		 });
		if(newsIds==""){
			showMsg("请选择新闻！");
			return;
		}
		var url="${_ctx_}/news/deleteNews?ids="+newsIds;
		$.post(url,{_method:"DELETE"},function(data){
			if(data.status==200){
				showMsgtrue("删除新闻成功！ ");
				$T.first();
			}else{
				showMsgfalse("删除新闻失败! ");
			}
		},"json");
	} */
}
</script>
</head>
<body>
<c:if test="${user.level==2||user.level==3 }">
	<%@include file="/common/menu.jsp"%>
</c:if>
<c:if test="${user.level==1 }">
	<%@include file="/common/menu2.jsp"%>
</c:if>
  <%--   <%@include file="/common/menu2.jsp"%> --%>
    <div class="breadBox">
    	<ul>
        	<li>· 您的位置：</li><li><a href="#">公司信息管理</a></li><li>></li><li><a href="#">公司列表</a></li>
        </ul>
        <div class="clear"></div>
    </div>
    <div class="contentBox">
    	<form action="" method="post">
    	<div class="toolBar">
        	<font>公司列表</font>
           
        </div>
        <table class="table" width="98%" cellpadding="0" cellspacing="0" id="table">
        	<tr>
    		<th width="9%">标识列</th>
    		<th width="9%">公司ID</th>
    		<th width="20%">名称</th>
    		<th width="10%">电话</th>
    		<th width="11%">网址</th>
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
<script type="tmpl" id="js_table_tmpl">
        <& for(var i=0,tl = data.length,tr;i < tl;i++){ &>
        <& tr = data[i]; &>
		<tr name="add_tr">
				<td class="txt-c"><input class="ck" type="checkbox" id=<&=tr.id&>><&=i+1&></td>
				<td><a href="#"><&=tr.id&></a></td>
	   			
	    		<td class="txt-c"><&=tr.name&></td>
				<td class="txt-c"><&=tr.tel&></td>
 			    <td class="txt-c"><&=tr.website&></td>
		
			</tr>
        <& } &>
</script>