<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>账户密码修改</title>
<%@include file="/common/head.jsp"%>
<script type="text/javascript">
var validform;
jQuery.validator.addMethod("isPwdTrue",function(value,element){
	return this.optional(element)||(function(s){//判断是否是数字或字母 
		return /^[0-9a-zA-Z\_]+$/.test(s);
	})(value);
},"密码输入有误");
$(document).ready(function() {
	validform = $("#modForm").validate({
		rules: {
			oldPwd:{
				required : true,
				isPwdTrue:true
			},
		    pwd:{
		    	required : true,
				isPwdTrue:true
		    },
			tpwd:{
				required : true,
				equalTo:"#pwd"
			}
		}
	});
});

function formSubmit(){
	if(!validform.form()){return false;}
	var tel = "${user.tel}";
	$.post("${_ctx_}/user/modifyPwd",{"tel":tel,"oldPwd":$("#oldPwd").val(),"password":$("#pwd").val()},function(data){
		if(data.code==200){
			alert("修改账号成功！ ");
		}else if(data.code==404){
			alert("旧密码输入有误！ ");
		}else{
			alert("修改账号失败！");
		}
	},"json");
}
</script>
</head>
<body>
	<div class="content bg01">
        <div class="logo"></div>
        <div class="loginbox">
        	 <div class="chpwImg"></div>
             <div class="cont">
             	<form id="modForm" >
             	 <table class="loginTable" cellpadding="0" cellspacing="0">
                        <tr>
                            <td class="title">旧密码：</td>
                            <td class="relative"><input type="password" class="txt" name="oldPwd" id="oldPwd" aria-required="true" aria-invalid="true"></td>
                        </tr>
                        <tr>
                            <td class="title">新密码：</td>
                            <td class="relative"><input type="password" class="txt" name="pwd" id="pwd" aria-required="true" aria-invalid="true"></td>
                        </tr>
                        <tr>
                            <td class="title">确认密码：</td>
                            <td class="relative"><input type="password" class="txt" name="tpwd" id="tpwd" aria-required="true" aria-invalid="true"></td>
                        </tr>
                        <tr>
                        	<td></td>
                            <td class="btnTd">
                            	<input id="btnLogin" class="btn btnLogin" type="button" value="确定" onclick="formSubmit();"/>
                                <input id="btnLogin" class="btn btnLogin" type="button" value="取消" onclick="document.getElementById('modForm').reset()"/>
                            </td>
                        </tr>
                    </table>
                   </form>
             </div>
        </div>
    </div>
</body>
</html>
