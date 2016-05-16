<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>智盛信息管理平台-登录</title>
<%-- <link href="${_ctx_}/css/login.css"rel="stylesheet"type="text/css"/> --%>
<link href="${_ctx_}/css/frame.css" rel="stylesheet"/>
<link href="${_ctx_}/css/base.css" rel="stylesheet"/>
<link rel="stylesheet" href="${_ctx_ }/css/msgbox.css" />
<script type="text/javascript" src="${_ctx_}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${_ctx_}/js/msgbox.js"></script>
<script type="text/javascript" src="${_ctx_}/js/web_jiafang.js"></script>
<script type="text/javascript" src="${_ctx_}/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">
$(function(){
	/* $(window).resize(function(e) {
        boxResize();
    }).trigger("resize"); */
});
/* function boxResize(){
	$("#loginBox").css({"margin-top":-$("#loginBox").height()/2,"left":$(window).width()/2});
} */
$(document).ready(function() {
	var url = "${baseUrl}";
	JiaFang.init(url);
	$("input").keypress(function(e){
		$("#loginpwderror").hide();
		 var keycode = e.charCode||e.keyCode;
		 if( keycode == 13 ) {  
			submitLogin();  
            return false;
        } 
	});
});
function submitLogin(){
	var username = $("#username").val();
	var password = $("#password").val();

	JiaFang.showLoading("正在登录...");
	// 1.$.ajax带json数据的异步请求
	var aj = $.ajax({
		url : '${_ctx_}/user/login',// 跳转到 action
		data : {
			username : username,
			password : password
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			JiaFang.hideLoading();
			if(data.code == 200){
				if(data.data.level==1){
					location.href = "${_ctx_}/page/companyinfo"
				}else if(data.data.level==3||data.data.level==2){
					location.href = "${_ctx_}/page/categorylist"
				}
			}else {
				JiaFang.showFailedToast("登录失败！" + data.description);
			}
		},
		error : function() {
			JiaFang.hideLoading();
			JiaFang.showFailedToast("登录失败！");
		}
	});
}
function goRegister(){
	window.location.href="${_ctx_}/page/register";
}

function goResetPwd(){
	window.location.href="${_ctx_}/page/resetPwd";
}
</script>
</head>
<%-- <body class="bgbody">
	<div class="content">
        <div id="loginBox" class="loginBox">
            <div class="logoBox">
                <span class="logo"></span>
                <span class="tit">信息管理平台</span>
            </div>
            <div class="formBox">
                <form id="loginForm">
                	<h3>登录</h3>
                    <table class="loginTable" cellpadding="0" cellspacing="0">
                        <tr>
                            <td class="title">账号：</td>
                            <td><input class="txt" type="text" value="" id="username" name="username"/></td>
                        </tr>
                        <tr>
                            <td class="title">密码：</td>
                            <td><input class="txt" type="password" value="" id="password" name="password"/></td>
                        </tr>
                        <!-- <tr>
                            <td></td>
                            <td><a class="pwd" href="pwd.html">忘记密码>></a></td>
                        </tr> -->
                        <tr>
                        	<td></td>
                            <td class="btnTd">
                            	<input id="btnLogin" class="btn btnLogin" type="button" value="登录" onclick="submitLogin()"/>
                                <a href="${_ctx_}/page/register" class="btnA">注册</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</body> --%>
<body>
	<div class="content bg01">
        <div class="logo"></div>
        <div class="loginbox">
        	 <div class="loginImg"></div>
             <div class="cont">
             <form id="loginForm">
             	 <table class="loginTable" cellpadding="0" cellspacing="0">
                        <tr>
                            <td class="title">账号：</td>
                            <td><input class="txt" type="text" value="" id="username" name="username"/></td>
                        </tr>
                        <tr>
                            <td class="title">密码：</td>
                            <td><input class="txt" type="password" value="" id="password" name="password"/></td>
                        </tr>
                        <tr>
                        	<td></td>
                            <td class="btnTd">
                            	<input id="btnLogin" class="btn btnLogin" type="button" value="登录" onclick="submitLogin()"/>
                                <input id="btnLogin" class="btn btnLogin" type="button" value="注册" onclick="goRegister()"/>
                                <input id="btnLogin" class="btn btnLogin" type="button" value="重置密码" onclick="goResetPwd()"/>
                            </td>
                        </tr>
                    </table>
             </form>
             </div>
        </div>
    </div>
</body>
</html>