<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>公司信息修改</title>
    <%@include file="/common/head.jsp" %>
    <%@include file="/common/imagecut.jsp" %>
    <script>

        var companyAvatar = '';
        $(document).ready(function () {
            JiaFang.getCompanyByUserId(function (data) {
                if (data.code == 200) {
                    var company = data.data;
                    $("#name").val(company.name);
                    $("#tel").val(company.tel);
                    $("#website").val(company.website);
                    $("#address").val(company.address);
                    $("#picUrlKey_avatar").val(company.avatar);
                    $("#picUrlKey_video").val(company.video);
                    $("#brand").val(company.brand);
                    $("#desp").val(company.desp);
                    $("#joinProcess").val(company.joinProcess);
                    $("#joinCondition").val(company.joinCondition);
                    $("#joinSurport").val(company.joinSurport);

                    if (!!company.avatar) {
                        JiaFang.getUrl("banner_avatar", "${fileURL}/" + company.avatar);
                        companyAvatar = "${fileURL}/" + company.avatar;
                    }
                    if (!!company.video) {
                        JiaFang.getUrl("banner_video", "${videoURL}/" + company.video);
                    }
                } else {
                    JiaFang.showFailedToast("获取失败！" + data.description);
                }
            });

//            $("#picUrl_avatar").change(function () {
//                var file = document.getElementById("picUrl_avatar").files[0];
//                if (!/image\/\w+/.test(file.type)) {
//                    JiaFang.showFailedToast("请选择图片！");
//                    return false;
//                }
//
//                JiaFang.uploadToQiNiu(file, null, "banner_avatar", "picUrlKey_avatar", function (pic) {
//
//                });
//            });

        });
        function submitForm() {
            if (!!$("#desp").val() && $("#desp").val().length > 2000) {
                JiaFang.showFailedToast("详情描述不能超过2000个字符");
                return;
            }
            $("#submit").attr('disabled', true);
            $.post("${_ctx_}/company/modifyCompany", $("#modForm").serialize(), function (data) {
                if (data.code == 200) {
                    $("#submit").attr('disabled', false);
                    JiaFang.showSuccessToast("修改公司信息成功！");
                }
                else {
                    $("#submit").attr('disabled', false);
                    JiaFang.showFailedToast("修改公司信息失败！");
                }
            }, "json");
        }

        function deleteVideo() {
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
                <li><a class="cur" href="javascript:JiaFang.toCompanyPage();">公司管理<font>Company management</font></a></li>
                <li><a href="javascript:JiaFang.toProductPage();">产品管理<font>Product management</font></a></li>
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
                        <a href="javascript:JiaFang.toModifyPwd();">修改密码</a>
                        <span>|</span>
                        <a href="javascript:JiaFang.logout();">退出</a>
                    </p>
                </div>
            </div>
        </div>
        <div class="breadBox">
            <ul>
                <li>· 您的位置：</li>
                <li><a href="javascript:JiaFang.toCompanyPage();">公司管理</a></li>
                <li>></li>
                <li><a href="#">修改公司信息</a></li>
            </ul>
            <div class="alterBtn">
                <a href="javascript:void(0);"></a>
            </div>
        </div>
        <div class="clear"></div>
        <div class="tableBox">
            <div class="floatdiv01"></div>
            <form id="modForm">
                <input class="txt" type="hidden" value="${user_level_1_company.id }" name="company.id" id="company.id"/>
                <input class="txt" type="hidden" value="${user_level_1_company.userId }" name="company.userId"
                       id="company.userId"/>
                <table class="table" width="98%" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="22.7%" class="c01">公司名称</td>
                        <td width="77.3%" style=" border-top:0;"><input class="txt" type="text" name="company.name"
                                                                        id="name" value=""/></td>
                    </tr>
                    <tr>
                        <td width="19%" class="c01">公司电话</td>
                        <td width="81%"><input class="txt" type="text" value="" name="company.tel" id="tel"/></td>
                    </tr>
                    <tr>
                        <td class="c02 bg07">公司网址</td>
                        <td>
                            <input class="txt" type="text" value="" name="company.website" id="website"/>
                        </td>
                    </tr>
                    <tr>
                        <th width="19%" class="c01">公司地址</th>
                        <td width="81%"><input class="txt" type="text" value="" name="company.address" id="address"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="c02">公司图标</td>
                        <td>
                            <img id="banner_avatar" alt="" src="" width="100px">
                            <input type="hidden" name="company.avatar" id="picUrlKey_avatar" value="">
                            <!-- Button trigger modal -->
                            <button type="button" data-target="#modal" data-toggle="modal">
                                图片上传以及编辑
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td class="c02">公司视频</td>
                        <td>
                            <input type="hidden" name="company.video" id="videoUrlKey" value="">
                            <video controls="controls" width="340" height="200" id="banner_video"></video>
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
                        <td class="c02">公司品牌</td>
                        <td>
                            <input class="txt" type="text" value="" name="company.brand" id="brand"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="c02">公司详情</td>
                        <td>
                            <textarea class="txtArea" rows="10" cols="70" name="company.desp" id="desp"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="c02">加盟流程</td>
                        <td>
                            <textarea class="txtArea" rows="10" cols="70" name="company.joinProcess"
                                      id="joinProcess"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="c02">加盟条件</td>
                        <td>
                            <textarea class="txtArea" rows="10" cols="70" name="company.joinCondition"
                                      id="joinCondition"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="c02">加盟支持</td>
                        <td>
                            <textarea class="txtArea" rows="10" cols="70" name="company.joinSurport"
                                      id="joinSurport"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="c02"></td>
                        <td class="txt-r">
                            <input class="btn" type="button" value="修改" onclick="submitForm()" id="submit">
                            <input class="btn" type="button" value="取消" onclick="cancel()">
                        </td>
                    </tr>
                    <tr>
                        <td class="c02" style="height:140px;"></td>
                        <td>

                        </td>
                    </tr>
                </table>
            </form>
            <div class="floatdiv02">
                <div class="btnA">
                    <a href="javascript:JiaFang.toCompanyStorePicsPage()">专卖店形象列表</a>
                    <a href="javascript:JiaFang.toCompanyPicsPage()">公司图片列表</a>
                </div>
            </div>
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