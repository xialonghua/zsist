<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>智盛信息管理平台-注册</title>
<link rel="stylesheet" href="${_ctx_ }/css/msgbox.css" />
<%-- <link href="${_ctx_}/css/login.css"rel="stylesheet"type="text/css"/> --%>
<link href="${_ctx_}/css/frame.css" rel="stylesheet"/>
<link href="${_ctx_}/css/base.css" rel="stylesheet"/>
<script type="text/javascript" src="${_ctx_ }/js/msgbox.js"></script>
<script type="text/javascript" src="${_ctx_}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${_ctx_}/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="${_ctx_}/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${_ctx_}/js/web_jiafang.js"></script>
<script type="text/javascript">
/* $(function(){
	$(window).resize(function(e) {
        boxResize();
    }).trigger("resize");
});
function boxResize(){
	$("#loginBox").css({"margin-top":-$("#loginBox").height()/2,"left":$(window).width()/2});
} */
var validform;
jQuery.validator.addMethod("isMobile", function(value, element) {
	var length = value.length;
	var mobile = /^[1][0-9]{10}$/;
	return this.optional(element) || (length == 11 && mobile.test(value));
}, "手机号码格式错误");
jQuery.validator.addMethod("isPwdTrue",function(value,element){
	return this.optional(element)||(function(s){//判断是否是数字或字母 
		return /^[0-9a-zA-Z\_]+$/.test(s);
	})(value);
},"密码应为字母数字组合");
var countdown = 60;
function settime(val) {
	if (countdown == 0) {
		val.removeAttribute("disabled");
		val.value = "获取验证码";
		countdown = 60;
	} else {
		val.setAttribute("disabled", true);
		val.value = "重新发送(" + countdown + ")";
		countdown--;
		setTimeout(function() {
			settime(val)
		}, 1000);
	}
};
function getCode(body){
	settime(body);
	var tel = $("#tel").val();
	$.ajax({
		url : '${_ctx_}/user/getVerifyCode',// 跳转到 action
		data : {
			tel : tel,
			verifyCodeType : 0
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if (data.code == 200) {
				JiaFang.showSuccessToast("发送成功");
				//alert("发送成功");
			} else {
				JiaFang.showFailedToast("发送失败！" + data.description);
				//alert("发送失败！" + data.description);
			}
		},
		error : function() {
			// view("异常！");
			JiaFang.showFailedToast("发送失败！");
			//alert("异常！");
		}
	});
};

$(document).ready(function() {
	JiaFang.init("${baseUrl}");
	$("input").keypress(function(e){
		$("#loginpwderror").hide();
		 var keycode = e.charCode||e.keyCode;
		 if( keycode == 13 ) {  
			submitLogin();  
            return false;
        } 
	});

	
		/* $("#check").click(function() {
			
		}); */

		validform = $("#registForm").validate({
			errorPlacement: function(error, element) {
			    if ( element.attr("id") == "verifyCode")
			        error.appendTo( element.next().parent() );
			    else
			        error.appendTo( element.parent() );
			},
			rules : {
				tel : {
					required : true,
					isMobile : true
				},
				verifyCode : {
					required : true
				},
				password : {
					required : true,
					isPwdTrue : true
				},
				tpwd : {
					required : true,
					equalTo : "#password"
				}
			}
		});
	});
	
	function submitLogin() {
		if (!validform.form()) {
			return false;
		}
		JiaFang.showLoading("正在注册...");
		var username = "";
		var password = $("#password").val();
		var tel = $("#tel").val();
		var verifyCode = $("#verifyCode").val();
		// 1.$.ajax带json数据的异步请求
		var aj = $.ajax({
			url : '${_ctx_}/user/registCompanyUser',// 跳转到 action
			data : {
				username : tel,
				password : password,
				tel : tel,
				verifyCode : verifyCode
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				JiaFang.hideLoading();
				if (data.code == 200) {
					location.href = "${_ctx_}/page/companyregister"
				} else {
					JiaFang.showFailedToast("注册失败！" + data.description);
					//alert("注册失败！" + data.description);
				}
			},
			error : function() {
				// view("异常！");
				JiaFang.hideLoading();
				JiaFang.showFailedToast("注册失败！");
				//alert("异常！");
			}
		});
	}
</script>
</head>

<body>
	<div class="content bg01">
        <div class="logo"></div>
        <div class="loginbox">
        	 <div class="registerImg"></div>
             <div class="cont">
             <form id="registForm">
             	 <table class="loginTable" cellpadding="0" cellspacing="0">
                      <tr>
                          <td class="title">手机号：</td>
                          <td class="relative"><input class="txt" type="text" value="" id="tel" name="tel"/><!-- <label for="tel" class="error">不能为空</label> --></td>
                      </tr>
                      <tr>
                          <td class="title">验证码：</td>
                          <td  class="relative">
                              <input class="txt w86" type="text" value="" id="verifyCode" name="verifyCode"/>
                              <input class="btn" style="padding:0 10px;" type="button" value="获取验证码" id="check" onclick="getCode(this);">
                          </td>
                      </tr>
                      <tr>
                          <td class="title">密码：</td>
                          <td class="relative"><input class="txt" type="password" value="" id="password" name="password"/></td>
                      </tr>
                      <tr>
                          <td class="title">确认密码：</td>
                          <td class="relative"><input class="txt" type="password" value="" id="tpwd" name="tpwd"/></td>
                      </tr>
                      <tr>
                          <td></td>
                          <td class="btnTd">
                              <input id="btnLogin" class="btn btnLogin" type="button" value="注册" onclick="submitLogin()"/>
                              <a href="${_ctx_ }/page/login" class="btnA">返回</a>
                          </td>
                      </tr>
                  </table>
             </form>
             </div>
        </div>
    </div>
</body>
</html>