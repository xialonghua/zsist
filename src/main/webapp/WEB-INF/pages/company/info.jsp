<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>公司管理</title>
<%@include file="/common/head.jsp"%>

<link rel="stylesheet" type="text/css" href="${_ctx_ }/css/crop.css" />
<script type="text/javascript" src="${_ctx_ }/js/cropbox.js"></script>

<script>
$(document).ready(function(){
    JiaFang.getCompanyByUserId(function(data) {
        if(data.code == 200){
            var company = data.data;
            $("#name").val(company.name);
            $("#tel").val(company.tel);
            $("#url").val(company.website);
            $("#address").val(company.address);
            if(!!company.avatar){
                JiaFang.getUrl("avatar","${fileURL}/"+company.avatar);
            }else{
                $("#avatar").attr("src","${_ctx_}/image/temp.jpg");
            }

            if(!!company.video){
                JiaFang.getUrl("video","${videoURL}/"+company.video);
            }

            $("#brand").val(company.brand);
            $("#joinProcess").val(company.joinProcess);
            $("#joinCondition").val(company.joinCondition);
            $("#joinSurport").val(company.joinSurport);
            $("#desp").val(company.desp);
        }else {
            JiaFang.showFailedToast("获取失败！" + data.description);
        }
    });
});

</script>
</head>
<body>
	<div class="content">
    	 <div class="wrapper">
         	  <div class="headerBox">
                    <ul id="menuList" class="menuList">
                        <li><a class="cur"  href="javascript:JiaFang.toCompanyPage();">公司管理<font>Company management</font></a></li>
                        <li><a  href="${_ctx_ }/page/productlist">产品管理<font>Product management</font></a></li>
                    </ul>
                    <div class="clear"></div>
              </div>
              <div class="floatBox">
                    <div class="cont">
                    	  <div class="logo01"><a href="javascript:void(0);"></a></div>
                          <h3>${SysName}后台管理系统</h3>
                          <div class="infor">
                          	   <img id="userAvatar" src="${_ctx_ }/images/user.jpg">
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
                      <li>· 您的位置：</li><li><a href="javascript:void(0);">公司管理</a></li><li>></li><li><a href="#">公司信息</a></li>
                  </ul>
                  <div class="alterBtn">
                  	   <a href="javascript:JiaFang.toModifyCompanyPage();"></a>
                  </div>
              </div>
              <div class="clear"></div>
              <div class="tableBox">
              	   <div class="floatdiv01"></div>
              	   <table class="table" width="98%" cellpadding="0" cellspacing="0">
                        <tr>
                            <td width="22.7%" class="c01">公司名称</td>
                            <td width="77.3%" style=" border-top:0;"><input class="txt" type="text" value="" name="name" id="name" readonly/></td>
                        </tr>
                        <tr>
                            <td width="19%" class="c01">公司电话</td>
                            <td width="81%"><input class="txt" type="text" value="" name="tel" id="tel" readonly/></td>
                        </tr>
                        <tr>
                            <td class="c02 bg07">公司网址</td>
                            <td>
                                 <input class="txt" type="text" value="" name="url" id="url" readonly/>
                            </td>
                        </tr>
                        <tr>
            				<th width="19%" class="c01">公司地址</th>
                			<td width="81%"><input class="txt" type="text" value="" name="address" id="address" readonly="readonly"/></td>
            			</tr>
                        <tr>
                            <td  class="c02">公司图标</td>
                            <td>
                                 <img  name="avatar" id="avatar" src="">
                            </td>
                        </tr>
                        <tr>
                            <td  class="c02">公司视频</td>
                            <td>
                                 <video controls="controls" src="" name="video" id="video" width="300px" height="200px"></video>
                            </td>
                        </tr>
                        <tr>
                            <td  class="c02">公司品牌</td>
                            <td>
                               <input class="txt" type="text" value="" name="brand" id="brand" readonly/>
                            </td>
                        </tr>
                        <tr>
                            <td  class="c02">公司详情</td>
                            <td>
                                <textarea class="txtArea" rows="10" cols="70" name="desp" id="desp" readonly></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td  class="c02">加盟流程</td>
                            <td>
                                <textarea class="txtArea" rows="10" cols="70" name="joinProcess" id="joinProcess" readonly></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td  class="c02">加盟条件</td>
                            <td>
                                <textarea class="txtArea" rows="10" cols="70" name="joinSurport" id="joinSurport" readonly></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td  class="c02" style="height:140px;"></td>
                            <td>
                                 
                            </td>
                        </tr>
                    </table>
                    <div class="floatdiv02">
                    	 <div class="btnA">
                         	 <a href="javascript:JiaFang.toCompanyStorePicsPage()">专卖店形象列表</a>
                             <a href="javascript:JiaFang.toCompanyPicsPage()">公司图片列表</a>
                         </div>
                    </div>
              </div>
         </div>
    </div>
</body>
</html>