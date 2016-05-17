<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公司图片</title>
<%@include file="/common/head.jsp"%>
<%@include file="/common/videoUpload.jsp"%>

<link href="${_ctx_ }/imgcut/dist/cropper.css" rel="stylesheet">
<link href="${_ctx_ }/imgcut/css/main.css" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
<script>
$(document).ready(function(){ 	 
	getPics();
});
function getPics(){
	var pic = {
			type:0,
			companyId:"${user_level_1_company.id }"
	}
    JiaFang.getCompanyPics(pic); 

}
function updateImg(type,fileId){
	var file = document.getElementById("U_"+type+"_"+fileId).files[0];
	if (!/image\/\w+/.test(file.type)) {
		   JiaFang.showFailedToast("请选择图片！");
           return false;
    }
	JiaFang.uploadToQiNiu(file,fileId,null,null,function(pic){
		JiaFang.modifyCompanyPic(pic,getPics);
	});
}
function deletePic(fileId){
	JiaFang.deleteCompanyPic(fileId,getPics);
}
</script>
</head>
<body>
	<div class="content">
    	 <div class="wrapper">
         	  <div class="headerBox">
                    <ul id="menuList" class="menuList">
                        <li><a class="cur"  href="${_ctx_ }/page/companyinfo">公司管理<font>Company management</font></a></li>
                        <li><a  href="${_ctx_ }/page/productlist">产品管理<font>Product management</font></a></li>
                    </ul>
                    <div class="clear"></div>
              </div>
              <div class="floatBox">
                    <div class="cont">
                    	  <div class="logo01"><a href="javascript:void(0);"></a></div>
                          <h3>${SysName }后台管理系统</h3>
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
                      <li>· 您的位置：</li><li><a href="${_ctx_ }/page/companyinfo">公司管理</a></li><li>></li><li><a href="#">公司图片列表</a></li>
                  </ul>
              </div>
              <div class="clear"></div>
              <div class="tableBox">
              	   <div class="floatdiv01"></div>
                   <div class="toolBar">
                        <font>公司图片列表 </font>
                        <input type="hidden" id="token" class="token" name="token"
							value="${token}" />
						<input type="hidden" id="domain" class="domain" name="domain"
							value="${domain}" />
						<input type="hidden" id="elementTarget" class="elementTarget" name="elementTarget" value="" />
						<input type="hidden" id="fileUploadType" class="fileUploadType" name="fileUploadType" value="4" />
						<input type="hidden" id="companyId" class="companyId" name="companyId" value="${user_level_1_company.id }" />
                        <!-- <input class="btn" type="button" value="+选择文件" id="pickfiles"> -->
                        <div class="col-md-12" style="width:auto; float:right">
							<div id="container">
								<a class="btn btn-default btn-lg " id="pickfiles" href="#"> <i
									class="glyphicon glyphicon-plus"></i> <span>选择文件</span>
								</a>
							</div>
						</div>
                   </div>
              	   <table class="table table2" width="98%" id="table2">
                      <tr class="noth">
                          <td width="12.5%"></td>
                          <td width="12.5%"></td>
                          <td width="12.5%"></td>
                          <td width="12.5%"></td>
                          <td width="12.5%"></td>
                          <td width="12.5%"></td>
                          <td width="12.5%"></td>
                          <td width="12.5%"></td>
                      </tr>
                       
            	   </table>
              </div>
         </div>
    </div>
</body>

</html>
<script type="tmpl" id="js_table_tmpl">
        <& 
		for(var i=0,tl = data.length,tr;i < tl;i++){ &>
        <& var file = data[i]; &>
		<&if(i>8){
         if(i%8==0){
		&>	
		</tr>
  		<&}}&>
        <&if(i%8==0){
        &>
		<tr name="add_tr">
        <&} &>
			<td width="12.5%" class="txt-c">
				<img src="" id="avatar_<&=i&>">
				
				<input class="btn1" type="button" value="删除" onclick="deletePic('<&=file.id&>')"/>
			</td>
        <& } &>
		</tr>
</script>
