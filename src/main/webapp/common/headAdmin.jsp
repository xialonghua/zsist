<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<link href="${_ctx_ }/css/frame.css" rel="stylesheet"/>
<link href="${_ctx_ }/css/gotye.css" rel="stylesheet"/>
<link href="${_ctx_ }/css/home.css" rel="stylesheet"/>

<link href="${_ctx_ }/js/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://cdn.staticfile.org/jquery/3.0.0-alpha1/jquery.min.js"></script>
<script type="text/javascript" src="${_ctx_ }/js/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="${_ctx_ }/js/json2.js"></script>


<script type="text/javascript" src="${_ctx_}/js/ejs.js"></script>
<script type="text/javascript" src="${_ctx_}/js/table.js"></script>

<link rel="stylesheet" href="${_ctx_ }/css/msgbox.css" />
<link rel="stylesheet" href="${_ctx_ }/css/jquery-ui-1.10.4.custom.min.css" />
<script type="text/javascript" src="${_ctx_ }/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${_ctx_ }/js/jquery-ui-1.10.4.custom.min.js"></script>
<script type="text/javascript" src="${_ctx_ }/js/msgbox.js"></script>
<script type="text/javascript" src="${_ctx_ }/js/ejs.js"></script>	
<script type="text/javascript" src="${_ctx_ }/js/web_jiafang.js"></script>

<script type="text/javascript">
$(document).ready(function() {
	
	JiaFang.init("${baseUrl}");
	JiaFang.initFileURL("${fileURL}")
});
</script>





