<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>产品详情</title>
    <%@include file="/common/head.jsp" %>


    <style type="text/css">
        .trans_msg {
            filter: alpha(opacity=100, enabled=1) revealTrans(duration=.2, transition=1) blendtrans(duration=.2);
        }
    </style>
    <script type="text/javascript">
        var productDescription = "";
        var subProdouctCount = -1;//子产品总数
        var productDescCount = 0;//产品介绍图片总数
        var addDescCount = 0;//每次上传的产品介绍图片个数
        $(document).ready(function () {
            JiaFang.init("${baseUrl}");
            if ("${user.level}" == 1) {
                $.ajax({
                    url: '${_ctx_ }/product/getProductById',// 跳转到 action
                    data: {
                        productId: "${productId}"
                    },
                    type: 'post',
                    cache: false,
                    dataType: 'json',
                    success: function (data) {
                        if (data.code == 200) {
                            var trs = dom.ejs("js_table_tmpl", data.data);
                            $("tr[name='add_tr']").remove();
                            $("#table").append(trs);
                            $("#productName").text(data.data.name);
                            productDescription = data.data.description;

                            var trs_sub = dom.ejs("js_table_tmpl_subProduct", data.data);
                            $("tr[name='add_tr_sub']").remove();
                            $("#table_sub").append(trs_sub);


                            var trs_desc = dom.ejs("js_table_tmpl_desc", data.data);
                            $("tr[name='add_tr_desc']").remove();
                            $("#table_desc").append(trs_desc);

                            var subProducts = data.data.subProduct;
                            if (!!subProducts) {
                                subProdouctCount = subProducts.length;
                                for (var i = 0; i < subProducts.length; i++) {
                                    JiaFang.getUrl('sub_avatar_' + i, "${fileURL}/" + subProducts[i].avatar);
                                }
                            }

                            var desclist = data.data.descPics;
                            if (!!desclist) {
                                productDescCount = desclist.length;
                                for (var i = 0; i < desclist.length; i++) {
                                    JiaFang.getUrl('desc_' + i, "${fileURL}/" + desclist[i].url);
                                }
                            }
                        } else {
                            JiaFang.showFailedToast("获取产品参数失败！" + data.description);
                        }
                    },
                    error: function () {
                        // view("异常！");
                        JiaFang.showFailedToast("获取产品参数失败！");
                    }
                });
            }
            $("#subPicName").text("");
        });

        function deleteParam(id) {
            $.ajax({
                url: '${_ctx_ }/product/deleteProductParam',// 跳转到 action
                data: {
                    'param.id': id
                },
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 200) {
                        JiaFang.showSuccessToast("删除成功");
                        //location.reload();
                        $("#add_tr_" + id).remove();
                    } else {
                        JiaFang.showFailedToast("删除失败！" + data.description);
                    }
                },
                error: function () {
                    JiaFang.showFailedToast("删除失败！");
                }
            });
        }

        function addParam() {
            //location.href="${_ctx_ }/page/productaddparam?productId=${productId}";
            //单击确定按钮时，从对话框中取得数据
            var getResponse = function () {
                var self = $(this);
                JiaFang.showLoading("正在添加...");
                $.post("${_ctx_}/product/addProductParam", $("#addForm").serialize(), function (data) {
                    JiaFang.hideLoading();
                    //关闭对话框
                    self.dialog("close");
                    if (data.code == 200) {
                        var trs = dom.ejs("js_table_item_tmpl", data);
                        $("#table").append(trs);
                        JiaFang.showSuccessToast("添加成功！");
                    }
                    else {
                        JiaFang.showFailedToast("添加失败！");
                    }
                }, "json");

            };
            //单击取消按钮
            var doCancel = function () {
                $(this).dialog("close");
            };

            //设置对话框的默认值
            var dialogOpts = {
                //添加按钮
                buttons: {
                    确定: getResponse,
                    取消: doCancel
                },
                //禁止自动开发
                autoOpen: false,
                //关闭时效果
                hide: true

            };
            //加载对话框
            $("#addParamDialog").dialog(dialogOpts);
            //按钮的单击事件
            $("#addParamDialog").dialog("open");
        }

        function modifyParam(id) {
            //location.href="${_ctx_ }/page/productaddparam?productId=${productId}"
            //单击确定按钮时，从对话框中取得数据
            var name = $("#add_td_name_" + id).text();
            var value = $("#add_td_value_" + id).text();

            $("#modForm #paramName").val(name);
            $("#modForm #paramValue").val(value);
            var getResponse = function () {
                var self = $(this);
                JiaFang.showLoading("正在修改...");
                var v = $("#modForm").serialize() + "&param.id=" + id;
                $.post("${_ctx_}/product/modifyProductParam", v, function (data) {
                    JiaFang.hideLoading();
                    //关闭对话框
                    self.dialog("close");
                    if (data.code == 200) {

                        $("#add_td_name_" + data.data.id).text(data.data.name);
                        $("#add_td_value_" + data.data.id).text(data.data.value);

                        JiaFang.showSuccessToast("修改成功！");
                    }
                    else {
                        JiaFang.showFailedToast("修改失败！");
                    }
                }, "json");

            };
            //单击取消按钮
            var doCancel = function () {
                $(this).dialog("close");
            };

            //设置对话框的默认值
            var dialogOpts = {
                //添加按钮
                buttons: {
                    确定: getResponse,
                    取消: doCancel
                },
                //禁止自动开发
                autoOpen: false,
                //关闭时效果
                hide: true

            };

            $("#modParamDialog").dialog(dialogOpts);
            $("#modParamDialog").dialog("open");
        }

        function submitSub() {
            if(subProdouctCount < 0){
                JiaFang.showFailedToast("页面还没加载好");
                return false;
            }
            if(subProdouctCount >= 20){
                JiaFang.showFailedToast("子产品不能超过20个");
                return false;
            }
            var file = document.getElementById("pic_file").files[0];
            if (!/image\/\w+/.test(file.type)) {
                JiaFang.showFailedToast("请选择图片文件！");
                return false;
            }

            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function (e) {
                var imgsrc = this.result;
                imgsrc = imgsrc.substr(imgsrc.indexOf("base64,") + 7);
                JiaFang.showLoading("图片上传中..");
                $.ajax({
                    url: '${_ctx_}/res/uploadResToQiNiu',// 跳转到 action
                    data: {
                        file: imgsrc,
                    },
                    type: 'post',
                    cache: false,
                    dataType: 'json',
                    success: function (data) {
                        if (data.code == 200) {
                            var subProduct = {
                                "avatar": data.data,
                                "productId": "${productId}"
                            }
                            JiaFang.showSuccessToast("图片上传成功！");
                            addSubProduct(subProduct);
                        } else {
                            JiaFang.showFailedToast("获取失败！" + data.description);
                        }
                    },
                    error: function () {
                        // view("异常！");
                        JiaFang.showFailedToast("异常！");
                    }
                });

            };
        }
        function submitDescFile() {
            var urls = new Array();
            var fileList = document.getElementById("desc_file").files;
            if (fileList.length > 5) {
                JiaFang.showFailedToast("请上传5个以内的图片文件");
                return;
            }
            fileTimes(0, fileList, urls);
        }

        function fileTimes(i, files, urls) {
            if (i >= files.length) {
                var urlStr = "";
                for (var i in urls) {
                    urlStr += "," + urls[i];
                }
                urlStr = urlStr.substring(1);
                addDescCount = urls.length;

                addProductPics("${productId}", 2, urlStr);

                return;
            }
            var file = files[i];
            if (!/image\/\w+/.test(file.type)) {
                JiaFang.showFailedToast(file.name + "不是图片文件！");
                return false;
            }

            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function (e) {
                var imgsrc = this.result;
                imgsrc = imgsrc.substr(imgsrc.indexOf("base64,") + 7);
                JiaFang.showLoading("图片上传中..");
                $.ajax({
                    url: '${_ctx_}/res/uploadResToQiNiu',// 跳转到 action
                    data: {
                        file: imgsrc,
                    },
                    type: 'post',
                    cache: false,
                    dataType: 'json',
                    success: function (data) {
                        if (data.code == 200) {
                            //addProductPic(pic);
                            urls.push(data.data);
                            JiaFang.showSuccessToast("图片上传成功！");
                        } else {
                            JiaFang.showFailedToast("获取失败！" + data.description);
                        }
                        fileTimes(++i, files, urls);
                    },
                    error: function () {
                        JiaFang.showFailedToast("异常！");
                        fileTimes(++i, files, urls);
                    }
                });

            };
        }

        function addSubProduct(subProduct) {
            $.ajax({
                url: '${_ctx_}/product/addSubProduct',// 跳转到 action
                data: {
                    'subProduct.avatar': subProduct.avatar,
                    'subProduct.productId': subProduct.productId
                },
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 200) {
                        JiaFang.showSuccessToast("添加图片成功");
                        subProdouctCount++;
                        //showSubs();
                        appendSubToTable(data.data);
                    } else {
                        JiaFang.showFailedToast("添加图片失败！" + data.description);
                    }
                },
                error: function () {
                    JiaFang.showFailedToast("异常！");
                }
            });
        }
        function updateSubAvatar(subId) {
            var file = document.getElementById("sub_" + subId).files[0];
            if (!/image\/\w+/.test(file.type)) {
                JiaFang.showFailedToast("请选择图片！");
                return false;
            }
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function (e) {
                var imgsrc = this.result;
                imgsrc = imgsrc.substr(imgsrc.indexOf("base64,") + 7);
                $.ajax({
                    url: '${_ctx_}/res/uploadResToQiNiu',// 跳转到 action
                    data: {
                        file: imgsrc,
                    },
                    type: 'post',
                    cache: false,
                    dataType: 'json',
                    success: function (data) {
                        if (data.code == 200) {
                            var subProduct = {
                                "avatar": data.data,
                                "id": subId
                            }
                            modifySubProduct(subProduct);
                        } else {
                            JiaFang.showFailedToast("获取失败！" + data.description);
                        }
                    },
                    error: function () {
                        // view("异常！");
                        JiaFang.showFailedToast("异常！");
                    }
                });

            };
        }
        function modifySubProduct(subProduct) {
            $.ajax({
                url: '${_ctx_}/product/modifySubProduct',// 跳转到 action
                data: {
                    'subProduct.avatar': subProduct.avatar,
                    'subProduct.id': subProduct.id
                },
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 200) {
                        JiaFang.showSuccessToast("修改图片成功");
                        showSubs();
                    } else {
                        JiaFang.showFailedToast("修改图片失败！" + data.description);
                    }
                },
                error: function () {
                    JiaFang.showFailedToast("修改图片失败！");
                }
            });
        }
        function deleteSub(subId, index) {
            $.ajax({
                url: '${_ctx_}/product/deleteSubProduct',// 跳转到 action
                data: {
                    'subProduct.id': subId
                },
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 200) {
                        JiaFang.showSuccessToast("删除子产品成功");
                        showSubs();
                        /* subProdouctCount--;
                         deleteSubFromTable((Number(index)+1)); */
                    } else {
                        JiaFang.showFailedToast("删除子产品失败！" + data.description);
                    }
                },
                error: function () {
                    JiaFang.showFailedToast("异常！");
                }
            });
        }

        function addProductPics(productId, type, urls) {
            $.ajax({
                url: '${_ctx_}/product/addProductPics',// 跳转到 action
                data: {
                    "productId": productId,
                    "type": type,
                    "urls": urls
                },
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 200) {
                        JiaFang.showSuccessToast("添加图片成功");
                        showPics(type);
                        //productDescCount+=addDescCount;
                    } else {
                        JiaFang.showFailedToast("添加图片失败！" + data.description);
                    }
                },
                error: function () {
                    JiaFang.showFailedToast("添加图片失败！");
                }
            });
        }

        function updateImg(fileId, type) {
            var file = document.getElementById("U_" + type + "_" + fileId).files[0];
            if (!/image\/\w+/.test(file.type)) {
                JiaFang.showFailedToast("请选择图片！");
                return false;
            }
            var reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = function (e) {
                var imgsrc = this.result;
                imgsrc = imgsrc.substr(imgsrc.indexOf("base64,") + 7);
                $.ajax({
                    url: '${_ctx_}/res/uploadResToQiNiu',// 跳转到 action
                    data: {
                        file: imgsrc,
                    },
                    type: 'post',
                    cache: false,
                    dataType: 'json',
                    success: function (data) {
                        if (data.code == 200) {
                            var pic = {
                                "id": fileId,
                                "url": data.data,
                                "type": type
                            }
                            modifyProductPic(pic);
                        } else {
                            JiaFang.showFailedToast("获取失败！" + data.description);
                        }
                    },
                    error: function () {
                        // view("异常！");
                        JiaFang.showFailedToast("异常！");
                    }
                });

            };
        }
        function modifyProductPic(pic) {
            $.ajax({
                url: '${_ctx_}/product/modifyProductPic',// 跳转到 action
                data: {
                    'pic.id': pic.id,
                    'pic.url': pic.url
                },
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 200) {
                        JiaFang.showSuccessToast("修改图片成功");
                        //location.reload();
                        showPics(pic.type)
                    } else {
                        JiaFang.showFailedToast("修改图片失败！" + data.description);
                    }
                },
                error: function () {
                    JiaFang.showFailedToast("修改图片失败！");
                }
            });
        }

        function deletePic(fileId, picType) {
            $.ajax({
                url: '${_ctx_}/product/deleteProductPic',// 跳转到 action
                data: {
                    'pic.id': fileId
                },
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 200) {
                        JiaFang.showSuccessToast("删除图片成功");
                        showPics(picType);
                    } else {
                        JiaFang.showFailedToast("删除图片失败！" + data.description);
                    }
                },
                error: function () {
                    JiaFang.showFailedToast("异常  ,删除图片失败！！");
                }
            });
        }

        function updatePicTitle(element, picId, picType) {
            var title = $("#" + element).val();
            if (!title) {
                return;
            }
            $.ajax({
                url: '${_ctx_}/product/modifyProductPic',// 跳转到 action
                data: {
                    'pic.id': picId,
                    'pic.name': title
                },
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 200) {
                        JiaFang.showSuccessToast("修改图片标题成功");
                        //location.reload();
                        showPics(picType);
                    } else {
                        JiaFang.showFailedToast("修改图片标题失败！" + data.description);
                    }
                },
                error: function () {
                    // view("异常！");
                    JiaFang.showFailedToast("异常！");
                }
            });
        }

        function showPics(picType) {
            $.ajax({
                url: '${_ctx_}/product/getProductPics',// 跳转到 action
                data: {
                    'productId': "${productId}",
                    'picType': picType
                },
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 200) {
                        if (picType == 1) {
                            var trs_pic = dom.ejs("js_table_tmpl_pic", data.data);
                            $("tr[name='add_tr_pic']").remove();
                            $("#table_pic").append(trs_pic);
                            var piclist = data.data.pics;
                            if (!!piclist) {
                                for (var i = 0; i < piclist.length; i++) {
                                    JiaFang.getUrl('pic_' + i, "${fileURL}/" + piclist[i].url);
                                }
                            }

                        }
                        if (picType == 2) {
                            var trs_desc = dom.ejs("js_table_tmpl_desc", data.data);
                            $("tr[name='add_tr_desc']").remove();
                            $("#table_desc").append(trs_desc);
                            var desclist = data.data.descPics;
                            if (!!desclist) {
                                for (var i = 0; i < desclist.length; i++) {
                                    JiaFang.getUrl('desc_' + i, "${fileURL}/" + desclist[i].url);
                                }
                            }
                        }
                    } else {
                        JiaFang.showFailedToast("获取图片列表失败！");
                    }
                },
                error: function () {
                    JiaFang.showFailedToast("异常！获取图片列表失败！");
                }
            });
        }
        function showSubs() {
            $.ajax({
                url: '${_ctx_ }/product/getProductById',// 跳转到 action
                data: {
                    productId: "${productId}"
                },
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 200) {
                        var trs_sub = dom.ejs("js_table_tmpl_subProduct", data.data);
                        $("tr[name='add_tr_sub']").remove();
                        $("#table_sub").append(trs_sub);
                        var subProducts = data.data.subProduct;
                        if (!!subProducts) {
                            subProdouctCount = subProducts.length;
                            for (var i = 0; i < subProducts.length; i++) {
                                JiaFang.getUrl('sub_avatar_' + i, "${fileURL}/" + subProducts[i].avatar);
                            }
                        }
                    } else {
                        JiaFang.showFailedToast("获取图片列表失败！");
                    }
                },
                error: function () {
                    JiaFang.showFailedToast("异常！获取图片列表失败！");
                }
            });
        }
        function updateSubs() {
            var ids = "";
            var titles = "";
            $(".sub_title").each(function () {
                var id = $(this).prop("id").split("_")[1];
                var name = $(this).val();
                ids += "," + id;
                titles += "," + name;
            })
            ids = ids.substring(1);
            titles = titles.substring(1);
            $.ajax({
                url: '${_ctx_}/product/modifySubProductNames',// 跳转到 action
                data: {
                    'ids': ids,
                    'titles': titles
                },
                type: 'post',
                cache: false,
                dataType: 'json',
                success: function (data) {
                    if (data.code == 200) {
                        JiaFang.showSuccessToast("修改图片标题成功");
                        //location.reload();

                    } else {
                        JiaFang.showFailedToast("修改图片标题失败！" + data.description);
                    }
                },
                error: function () {
                    // view("异常！");
                    JiaFang.showFailedToast("异常！");
                }
            });
        }

        function appendSubToTable(sub) {
            var row = subProdouctCount % 8 == 0 ? (subProdouctCount / 8) - 1 : Math.floor(subProdouctCount / 8);
            var mod = subProdouctCount % 8;
            if (mod == 1) {
                /* <td width="12.5%" class="txt-c">
                 <div class="pic_box">
                 <input class="sub_title" type="text" name="title" id="title_<&=sub.id&>" placeholder="子产品标题" value="<&=sub.name&>"/>

                 </div>
                 <img src="" id="sub_avatar_<&=i&>" title="<&=sub.name&>" onclick="modifySubProdctParam(<&=sub.id&>)">
                 <div class="replace"><input class="btn1" type="button" value="替换"/><form><input type="file" id="sub_<&=sub.id&>" name="U_<&=sub.id&>" class="file" onchange="updateSubAvatar('<&=sub.id&>')"></form></div>
                 <input class="btn1" type="button" value="删除" onclick="deleteSub('<&=sub.id&>')"/>
                 <input type="hidden" class="titleId" value="<&=sub.id&>"/>
                 </td> */
                $("#table_sub").append("<tr name='add_tr_sub'><td width='12.5%' class='txt-c'><div class='pic_box'>" +
                        "<input class='sub_title' type='text' name='title' id='title_" + sub.id + "' placeholder='子产品标题' value='" + sub.name + "'/></div> " +
                        "<img src id='sub_avatar_" + (subProdouctCount - 1) + "' title='" + sub.name + "' onclick='modifySubProdctParam(" + sub.id + ")' onMouseOver='openPic(" + (subProdouctCount - 1) + ",1)' onMouseOut='toolTip();' > " +
                        "<div class='replace'><input class='btn1' type='button' value='替换'/><form><input type='file' id='sub_" + sub.id + "' name='U_" + sub.id + "' class='file' onchange='updateSubAvatar(" + sub.id + ")'></form></div>" +
                        "<input class='btn1' type='button' value='删除' onclick='deleteSub(" + sub.id + "," + (subProdouctCount - 1) + ")'/>" +
                        "<input type='hidden' class='titleId' value='" + sub.id + "'/>" +
                        "</td></tr>");
            } else {
                $("#table_sub tbody tr:eq(" + row + ")").append("<td width='12.5%' class='txt-c'><div class='pic_box'>" +
                        "<input class='sub_title' type='text' name='title' id='title_" + sub.id + "' placeholder='子产品标题' value='" + sub.name + "'/></div> " +
                        "<img src id='sub_avatar_" + (subProdouctCount - 1) + "' title='" + sub.name + "' onclick='modifySubProdctParam(" + sub.id + ")' onMouseOver='openPic(" + (subProdouctCount - 1) + ",1)' onMouseOut='toolTip();'> " +
                        "<div class='replace'><input class='btn1' type='button' value='替换'/><form><input type='file' id='sub_" + sub.id + "' name='U_" + sub.id + "' class='file' onchange='updateSubAvatar(" + sub.id + ")'></form></div>" +
                        "<input class='btn1' type='button' value='删除' onclick='deleteSub(" + sub.id + "," + (subProdouctCount - 1) + ")'/>" +
                        "<input type='hidden' class='titleId' value='" + sub.id + "'/>" +
                        "</td>");
            }
            JiaFang.getUrl('sub_avatar_' + (subProdouctCount - 1), "${fileURL}/" + sub.avatar);
        }

        function deleteSubFromTable(index) {
            /* var row = Math.floor(index/8);
             var mod = index%8==0?8:index%8;
             $("#table_sub tr:eq("+row+") td:eq("+(mod-1)+")").remove(); */
        }

        function appendProductDescToTable() {
            /* <tr name="add_tr_desc">
             <&} &>
             <td width="12.5%" class="txt-c">

             <img src="" id="desc_<&=i&>" title="<&=file.name&>" onMouseOver="openPic(<&=i&>,'2')" onMouseOut="toolTip();">
             <div class="replace"><input class="btn1" type="button" value="替换"/><form><input type="file" id="U_2_<&=file.id&>" name="U_<&=file.id&>" class="file" onchange="updateImg('<&=file.id&>',2)"></form></div>
             <input class="btn1" type="button" value="删除" onclick="deletePic('<&=file.id&>',2)"/>
             </td>
             <& } &>
             </tr> */
            /* var row = Math.floor(productDescCount/8);
             var mod = productDescCount%8;
             if(mod==1){
             $("#table_desc").append("<tr name='add_tr_desc'><td  width='12.5%' class='txt-c'>")
             .append("<img src id='desc_"+(productDescCount-1)+"' title="<&=file.name&>" onMouseOver='openPic(<&=i&>,'2')' onMouseOut="toolTip();"> ")
             }else{

             } */
        }
        function deleteProductDescTable(index) {

        }

        function modifySubProdctParam(id) {
            //location.href="${_ctx_ }/page/productaddparam?productId=${productId}"
            //单击确定按钮时，从对话框中取得数据
            $.get("${_ctx_}/product/getSubProductById", {"subProduct.id": id}, function (data) {
                var sub = data.data;
                $("#subProductPrice").val(sub.price);
                $("#subProductDiscountPrice").val(sub.discountPrice);
                $("#subProductSaleCount").val(sub.saleCount);
                $("#subProductCount").val(sub.count);
            });
            var getResponse = function () {
                var self = $(this);
                JiaFang.showLoading("正在修改...");
                var v = $("#modSubForm").serialize() + "&subProduct.id=" + id;
                $.post("${_ctx_}/product/modifySubProduct", v, function (data) {
                    JiaFang.hideLoading();
                    //关闭对话框
                    self.dialog("close");
                    if (data.code == 200) {
                        JiaFang.showSuccessToast("修改成功！");
                        doCancel();
                    }
                    else {
                        JiaFang.showFailedToast("修改失败！");
                    }
                }, "json");

            };
            //单击取消按钮
            var doCancel = function () {
                $(this).dialog("close");
            };
            //设置对话框的默认值
            var dialogOpts = {
                //添加按钮
                buttons: {
                    确定: getResponse,
                    取消: doCancel
                },
                //禁止自动开发
                autoOpen: false,
                //关闭时效果
                hide: true
            };
            $("#modSubProductDialog").dialog(dialogOpts);
            $("#modSubProductDialog").dialog("open");
        }

        function openPic(id, type) {
            if (type == 1) {
                var imgUrl = $("#sub_avatar_" + id).prop("src");
                if (imgUrl) {
                    toolTip("<img src=" + imgUrl + " width='500px' height='500px'>");
                }
            } else {
                var imgUrl = $("#desc_" + id).prop("src");
                if (imgUrl) {
                    toolTip("<img src=" + imgUrl + " width='500px' height='500px'>");
                }
            }
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
                <li>· 您的位置：</li>
                <li><a href="${_ctx_ }/page/productlist">产品管理</a></li>
                <li>></li>
                <li><a href="#">产品详情</a></li>
                <li>></li>
                <li><a href="#" id="productName"></a></li>
            </ul>
        </div>
        <div class="clear"></div>
        <div class="tableBox">
            <div class="floatdiv01"></div>
            <div class="toolBar">
                <font>产品参数列表 </font>

                <input class="btn" type="button" value="添加" onclick="addParam()">
            </div>
            <table class="table table1" width="100%" cellpadding="0" cellspacing="0" id="table">
                <tr>
                    <th width="33%">参数</th>
                    <th width="33%">数值</th>
                    <th width="34%">操作</th>
                </tr>
            </table>

            <div class="toolBar">
                <font>子产品图片</font>
                <div class="select">
                    <input class="btn" type="button" value="选择上传" onclick="pic_file.click()">
                    <input type="file" id="pic_file" name="pic_file" class="files" size="1" hidefocus="">
                    <input type="hidden" id="pictype" value="pic">
                </div>
                <input class="btn" type="button" value="上传" onclick="submitSub()">
                <label id="subPicName"></label>
                <input class="btn" type="button" value="更新标题" onclick="updateSubs()">
            </div>
            <table class="table table2" width="98%" id="table_sub">
                <thead class="noth">
                <td width="12.5%"></td>
                <td width="12.5%"></td>
                <td width="12.5%"></td>
                <td width="12.5%"></td>
                <td width="12.5%"></td>
                <td width="12.5%"></td>
                <td width="12.5%"></td>
                <td width="12.5%"></td>
                </thead>
            </table>
            <div class="toolBar">
                <font>产品介绍</font>
                <div class="select">
                    <input class="btn" type="button" value="选择上传" onclick="desc_file.click()">
                    <input type="file" id="desc_file" name="desc_file" multiple="multiple" class="files" size="1"
                           hidefocus="">
                    <input type="hidden" id="pictype" value="pic">
                </div>
                <span class="ts"> （一次支持最多5个文件的上传） </span>
                <input class="btn" type="button" value="上传" onclick="submitDescFile()">
            </div>
            <table class="table table2" width="98%" id="table_desc">
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

<div id="dialog" style="display: none">
</div>
</body>

</html>
<script type="tmpl" id="js_table_tmpl">
        <& for(var i=0,tl = params.length,tr;i < tl;i++){ &>
        <& tr = params[i]; &>
		
		<tr name="add_tr" id="add_tr_<&=tr.id&>">
	   			<td class="txt-c" id="add_td_name_<&=tr.id&>"><&=tr.name&></td>
				<td class="txt-c" id="add_td_value_<&=tr.id&>"><&=tr.value&></td>
				<td class="txt-c" id="add_td_modify<&=tr.id&>"><a class="modify" href="javascript:modifyParam(<&=tr.id&>)">修改</a>&nbsp;&nbsp;<a class="modify" href="javascript:deleteParam(<&=tr.id&>)">删除</a></td>
		</tr>
		
        <& } &>

</script>

<script type="tmpl" id="js_table_item_tmpl">
        
        <& tr = data; &>
		
		<tr name="add_tr" id="add_tr_<&=tr.id&>">
	   			<td class="txt-c" id="add_td_name_<&=tr.id&>"><&=tr.name&></td>
				<td class="txt-c" id="add_td_value_<&=tr.id&>"><&=tr.value&></td>
				<td class="txt-c" id="add_td_modify<&=tr.id&>"><a class="modify" href="javascript:modifyParam(<&=tr.id&>)">修改</a>&nbsp;&nbsp;<a class="modify" href="javascript:deleteParam(<&=tr.id&>)">删除</a></td>
		</tr>
		
     

</script>

<script type="tmpl" id="js_table_tmpl_subProduct">
        <& 
		for(var i=0,tl = subProduct.length,tr;i < tl;i++){ &>
        <& var sub = subProduct[i]; &>
		<&if(i>8){
         if(i%8==0){
		&>	
		</tr>
  		<&}}&>
        <&if(i%8==0){
        &>
		<tr name="add_tr_sub">
        <&} &>
			<td width="12.5%" class="txt-c">
				<div class="pic_box">
					<input class="sub_title" type="text" name="title" id="title_<&=sub.id&>" placeholder="子产品标题" value="<&=sub.name&>"/>  
					
				</div>
				<img src="" id="sub_avatar_<&=i&>" title="<&=sub.name&>" onclick="modifySubProdctParam(<&=sub.id&>)" onMouseOver="openPic(<&=i&>,'1')" onMouseOut="toolTip();"> 
				<div class="replace"><input class="btn1" type="button" value="替换"/><form><input type="file" id="sub_<&=sub.id&>" name="U_<&=sub.id&>" class="file" onchange="updateSubAvatar('<&=sub.id&>')"></form></div>
				<input class="btn1" type="button" value="删除" onclick="deleteSub('<&=sub.id&>','<&=i&>')"/>
				<input type="hidden" class="titleId" value="<&=sub.id&>"/>
			</td>
        <& } &>
		</tr>

</script>
<script type="tmpl" id="js_table_tmpl_desc">
        <& 
		for(var i=0,tl = descPics.length,tr;i < tl;i++){ &>
        <& var file = descPics[i]; &>
		<&if(i>8){
         if(i%8==0){
		&>	
		</tr>
  		<&}}&>
        <&if(i%8==0){
        &>
		<tr name="add_tr_desc">
        <&} &>
			<td width="12.5%" class="txt-c">
				
				<img src="" id="desc_<&=i&>" title="<&=file.name&>" onMouseOver="openPic(<&=i&>,'2')" onMouseOut="toolTip();"> 
				<div class="replace"><input class="btn1" type="button" value="替换"/><form><input type="file" id="U_2_<&=file.id&>" name="U_<&=file.id&>" class="file" onchange="updateImg('<&=file.id&>',2)"></form></div>
				<input class="btn1" type="button" value="删除" onclick="deletePic('<&=file.id&>',2)"/>
			</td>
        <& } &>
		</tr>

</script>

<div id="addParamDialog" title="添加参数" style="display: none">
    <form id="addForm">
        <input class="txt" type="hidden" value="<%=request.getParameter("productId") %>" name="param.productId"
               id="param.productId"/>
        <table class="table" width="98%" cellpadding="0" cellspacing="0">
            <tr>
                <th width="19%">参数</th>
                <td width="81%"><input class="txt" type="text" value="" name="param.name" id="param.name"/></td>
            </tr>
            <tr>
                <th width="19%">数值</th>
                <td width="81%"><input class="txt" type="text" value="" name="param.value" id="param.value"/></td>
            </tr>

        </table>
    </form>
</div>
<div id="modParamDialog" title="修改参数" style="display: none">
    <form id="modForm">
        <input class="txt" type="hidden" value="<%=request.getParameter("productId") %>" name="param.productId"
               id="param.productId"/>
        <table class="table" width="98%" cellpadding="0" cellspacing="0">
            <tr>
                <th width="19%">参数</th>
                <td width="81%"><input class="txt" type="text" value="" name="param.name" id="paramName"/></td>
            </tr>
            <tr>
                <th width="19%">数值</th>
                <td width="81%"><input class="txt" type="text" value="" name="param.value" id="paramValue"/></td>
            </tr>

        </table>
    </form>
</div>

<div id="modSubProductDialog" title="设置子产品参数" style="display: none">
    <form id="modSubForm">
        <table class="table" width="98%" cellpadding="0" cellspacing="0">
            <tr>
                <th width="19%">原价</th>
                <td width="81%"><input class="txt" type="text" value="" name="subProduct.price" id="subProductPrice"/>
                </td>
            </tr>
            <tr>
                <th width="19%">折扣价</th>
                <td width="81%"><input class="txt" type="text" value="" name="subProduct.discountPrice"
                                       id="subProductDiscountPrice"/></td>
            </tr>
            <tr>
                <th width="19%">最低订购量</th>
                <td width="81%"><input class="txt" type="text" value="" name="subProduct.saleCount"
                                       id="subProductSaleCount"/></td>
            </tr>
            <tr>
                <th width="19%">库存</th>
                <td width="81%"><input class="txt" type="text" value="" name="subProduct.count" id="subProductCount"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="toolTipLayer" style="position:absolute; visibility: hidden"></div>
<script type="text/javascript" src="${_ctx_}/js/ToolTip.js"></script>