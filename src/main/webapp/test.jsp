<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
var totalAmount = 0
$(function(){
	$('table tr').each(function() { $(this).each(function() { totalAmount ++;})});
	alert(totalAmount);
});

</script>
</head>
<body>
<!-- <form method="post" action="http://localhost/" enctype="multipart/form-data">
<input type="button" onmousemove="move(event)" value="请选择文件" size="30" />
<input type="file" id="f" onchange="this.form.submit()" name="f" style="position:absolute; filter:alpha(opacity=0); opacity:0; width:30px; " size="1" />
</form> -->

<table>
	<tr>
		<td>1</td><td>2</td>
	</tr>
	<tr>
		<td>3</td><td>4</td>
	</tr>
	<tr>
		<td>5</td>
	</tr>
</table>
<script type="text/javascript">
function move(event){
var event=event||window.event;
var a=document.getElementById('f');
    a.style.left=event.clientX-50+'px';
    a.style.top=event.clientY-10+'px';
}
</script>
</body>
</html>