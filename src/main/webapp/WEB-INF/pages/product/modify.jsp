<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="utf-8">
<title>修改产品</title>
<%@include file="/common/head.jsp"%>
<%@include file="/common/imagecut.jsp" %>

<script type="text/javascript">
$(document).ready(function(){

	$.ajax({
		url : '${_ctx_}/product/getProductById',// 跳转到 action
		data : {
			productId:"${productId}"
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(data) {
			if(data.code == 200){
				var product = data.data;
				$("#name").val(product.name);
				$("#num").val(product.num);
				$("#picUrlKey").val(product.avatar);
				$("#videoUrlKey").val(product.video);
				$("#price").val(product.price);
				$("#discountPrice").val(product.discountPrice);
				$("#saleCount").val(product.saleCount);
				$("#count").val(product.count);
				$("#description").val(product.description);
				
				if(!!product.avatar){
					//$("#banner_avatar").attr("src","${fileURL}/"+company.avatar);
					JiaFang.getUrl("banner_avatar","${fileURL}/"+product.avatar);
				}else{
					$("#banner_avatar").attr("src","${_ctx_}/image/temp.jpg");
				}
				
				if(!!product.video){
					/* $("#banner_video").attr("src","${fileURL}/"+company.video); */
					JiaFang.getUrl("banner_video","${videoURL}/"+product.video);
				}
			}else {
				JiaFang.showFailedToast("获取产品信息失败！" + data.description);
				//alert("获取失败！" + data.description);
			}
		},
		error : function() {
			// view("异常！");
			JiaFang.showFailedToast("获取产品信息失败！");
		}
	});
});

function submitForm(){
	$.post("${_ctx_}/product/modifyProduct",$("#addForm").serialize(),function(data) {
		if(data.code==200){
			$("#submit").attr('disabled',true);
			JiaFang.showSuccessToast("修改产品成功！");
			setTimeout(function(){
				window.location.href="${_ctx_ }/page/productlist";
			}, 3000);
		}
		else {
			JiaFang.showFailedToast("修改产品失败！"+data.description);
		}
	},"json");
}
function cancel(){
	window.history.back();
}

function deleteVideo(){
	$("#banner_video").attr("src", '');
	$("#videoUrlKey").val('');
}

</script>
</head>
<body>
	<div class="content">
    	 <div class="wrapper">
         	  <div class="headerBox">
                    <ul id="menuList" class="menuList">
                        <li><a href="${_ctx_ }/page/companyinfo">公司管理<font>Company management</font></a></li>
                        <li><a class="cur" href="${_ctx_ }/page/productlist">产品管理<font>Product management</font></a></li>
                    </ul>
                    <div class="clear"></div>
              </div>
              <div class="floatBox">
                    <div class="cont">
                    	  <div class="logo01"><a href="javascript:void(0);"></a></div>
                          <h3>${SysName}后台管理系统</h3>
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
                      <li>· 您的位置：</li><li><a href="${_ctx_ }/page/productlist">产品管理</a></li><li>></li><li><a href="#">修改产品</a></li>
                  </ul>
              </div>
              <div class="clear"></div>
              <div class="tableBox">
              	   <div class="floatdiv01"></div>
              	   <form id="addForm">
              	   <table class="table" width="98%" cellpadding="0" cellspacing="0">
              	   		 <input class="txt" type="hidden" value="${productId }" name="product.id" id="product.id"/>
        <input class="txt" type="hidden" value="${user_level_1_company.id }" name="product.companyId" id="product.companyId"/>
                        <tr>
                            <td width="22.7%" class="c01">产品名称</td>
                            <td width="77.3%" style=" border-top:0;"><input class="txt" type="text" value="" name="product.name" id="name"/></td>
                        </tr>
                        <tr>
                            <td class="c01">产品货号</td>
                            <td><input class="txt" type="text" value="" name="product.num" id="num"/></td>
                        </tr>
                        <tr>
                            <td class="c02 bg07">图片</td>
                            <td>
                                 <img id="banner_avatar" alt="" src="" width="100px"> 
               		 			 <input type="hidden" name="product.avatar" id="picUrlKey" value="">
                                <!-- Button trigger modal -->
                                <button type="button" data-target="#modal" data-toggle="modal">
                                    图片上传以及编辑
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td  class="c02">视频</td>
                            <td>
								<input type="hidden" name="product.video" id="videoUrlKey" value="">
                                 <video controls="controls"  width="340" height="200"   id="banner_video" src=""></video>
                                <div style="width:auto; float:left">
                                    <div id="container">
                                        <a class="btn " id="pickfiles" href="#"> <i
                                                class="glyphicon glyphicon-plus"></i> <span>选择文件</span>
                                        </a>
                                        <a class="btn btn-default btn-lg " href="javascript:deleteVideo();"> <i
                                                class="glyphicon"></i> <span>删除视频</span>
                                        </a>
                                    </div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td class="c02">原价</td>
                            <td><input class="txt" type="text" value="" name="product.price" id="price"/></td>
                        </tr>
                        <tr>
                            <td class="c02">折扣价</td>
                            <td><input class="txt" type="text" value="" name="product.discountPrice" id="discountPrice"/></td>
                        </tr>
                        <tr>
                            <td class="c02">最低订购量</td>
                            <td><input class="txt" type="text" value="" name="product.saleCount" id="saleCount"/></td>
                        </tr>
                        <tr>
                            <td class="c02">库存</td>
                            <td><input class="txt" type="text" value="" name="product.count" id="count"/></td>
                        </tr>
                        <tr>
                            <td  class="c02"></td>
                            <td class="txt-r">
                                <input class="btn" type="button" value="修改" onclick="submitForm()" id="submit">
                                <input class="btn" type="button" value="取消" onclick="cancel()">
                            </td>
                        </tr>
                    </table>
                    </form>
              </div>
         </div>
    </div>

	<script type="text/javascript">
        var token = "111";

        function up(token){
            $('#pickfiles').uploadify({
                'debug':false,
                'swf': "../js/uploadify/css/uploadify.swf",
                'uploader': 'http://upload.qiniu.com/',
                'formData':{'token':token, 'key': new Date().getTime()},
                'buttonText':'上传文件',
                'fileObjName':'file',
                'multi':false,
                'removeCompleted':true,
                'checkExisting':false,
                'progressData':'speed',
                'removeTimeout' : 5,
                'onUploadStart':function(file){
                    //$("#file_upload").uploadify('disable', true);
                    //$(".uploadify-queue-item .cancel").hide();
                },
                'onUploadSuccess':function(file, data, response){
                    //alert('文件' + file.name + ' 上传结果: ' + response + ':' + data.key);
//                    $.ajax({
//                        type: 'POST',
//                        url: "foot/addFoot",
//                        data:"foot.name=" + JSON.parse(data).key + "&foot.type=" + file.type,
//                        success: function(d){
//                            //alert("" + d.data.id);
//
//                        }
//                    });
                    var key = JSON.parse(data).key;
                    var sourceLink = "${fileURL }/" + key;
                    $("#videoUrlKey").val(key);
                    JiaFang.getUrl("banner_video",sourceLink);
                    setTimeout('up(token)', 200);
                },
                'onUploadError':function(file, errorCode, errorMsg, errorString){
                    //alert('文件： ' + file.name + errorMsg + ' 上传失败: ' + errorString);
                }
            });
        }

        $.ajax({
            type: 'POST',
            url: "../qiniu/getUpToken",
            success: function(data){

                token = data;
                up(token);
            }
        });
	</script>
</body>
</html>