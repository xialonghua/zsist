<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="viewport"
	content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>欢迎下载${appName}</title>
<link href="${_ctx_}/css/down.css" rel="stylesheet">
<script type="text/javascript" src="${_ctx_}/js/jquery-1.11.1.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {

						function is_weixn() {
							var ua = navigator.userAgent.toLowerCase();
							if (ua.match(/MicroMessenger/i) == "micromessenger") {
								$(".ios_en").click(function() {
									$(".overlay").show();
								})
								$(".android_en").click(function() {
									$(".overlay").show();
								})
								$(".overlay").click(function() {
									$(".overlay").hide();
								});
							} else {

								/* 智能机浏览器版本信息:   */
								var browser = {
									versions : function() {
										var u = navigator.userAgent, app = navigator.appVersion;
										return {//移动终端浏览器版本信息
											trident : u.indexOf('Trident') > -1, //IE内核
											presto : u.indexOf('Presto') > -1, //opera内核
											webKit : u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
											gecko : u.indexOf('Gecko') > -1
													&& u.indexOf('KHTML') == -1, //火狐内核
											mobile : !!u
													.match(/AppleWebKit.*Mobile.*/)
													|| !!u.match(/AppleWebKit/), //是否为移动终端
											ios : !!u
													.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
											android : u.indexOf('Android') > -1
													|| u.indexOf('Linux') > -1, //android终端或者uc浏览器
											iPhone : u.indexOf('iPhone') > -1
													|| u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
											iPad : u.indexOf('iPad') > -1, //是否iPad
											webApp : u.indexOf('Safari') == -1
										//是否web应该程序，没有头部与底部
										};
									}(),
									language : (navigator.browserLanguage || navigator.language)
											.toLowerCase()
								}
								var iosUrl = "${iosUrl}";
								if (browser.versions.iPhone) {
<% String url = (String)request.getAttribute("iosUrl");
if (url.startsWith("itms")) {%>
	document.location.href = '${iosUrl}';
<%} else {%>
	document.location.href = "itms-services://?action=download-manifest&url=${iosUrl}";
<%}%>
	} else if (browser.versions.iPad) {

								} else if (browser.versions.android) {

								}

							}
						}
						is_weixn();
					});
</script>
</head>

<body>
	<div class="container">
		<div class="content">
			<header>
				<img src="${_ctx_}/image/logo.png" class="logo">
			</header>
			<section>
				<span class="line"> <i class="triangle"></i>
				</span>
				<h4>
					专业全面的${shortName}专家<br> 立刻下载${appName}App
				</h4>
				<%
				String url2 = (String)request.getAttribute("iosUrl");
					if (url2.startsWith("itms")) {
				%>
				<a class="ios_en" href="${iosUrl}"></a>
				<%
					} else {
				%>
				<a class="ios_en"
					href="itms-services://?action=download-manifest&url=${iosUrl}"></a>
				<%
					}
				%>
				<a class="android_en" href="${androidUrl}"></a>
			</section>
		</div>
		<div class="overlay" style="display: none;">
			<img src="${_ctx_}/image/arrow-1.png">
		</div>
	</div>

</body>
</html>