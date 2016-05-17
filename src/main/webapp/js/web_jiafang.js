/*global plupload ,mOxie*/
/*global ActiveXObject */
/*exported Qiniu */
/*exported QiniuJsSDK */

//var baseUrl = "/bu/";//用于本地测试
var baseUrl = '/textile/';  //用于外网，正式环境
var fileURL = '';
//var baseUrl = "/baihuo/"; 

function JiaFang() {
	
	this.init = function(url){
		baseUrl = url;
	};
	
	this.initFileURL = function(url){
		fileURL = url;
	};
	
	this.showLoading = function(tip){
		ZENG.msgbox.show(tip, 6);
	}
	
	this.showSuccessToast = function(tip){
		ZENG.msgbox.show(tip, 4, 1500);
	}
	
	this.showFailedToast = function(tip){
		ZENG.msgbox.show(tip, 5, 1500);
	}

	this.hideLoading = function(){
		ZENG.msgbox._hide();
	}

	this.login = function() {
		var username = $("#username").val();
		var password = $("#password").val();
		// 1.$.ajax带json数据的异步请求
		var aj = $.ajax({
			url : 'user/login',// 跳转到 action
			data : {
				username : username,
				password : password
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					if(data.data.level==1){
						location.href = baseUrl + "page/companyinfo"
					}else if(data.data.level==3){
						location.href = baseUrl + "page/categorylist"
					}
					
				}else {
					alert("登录失败！" + data.description);
				}
			},
			error : function() {
				// view("异常！");
				alert("异常！");
			}
		});
	};
	
	this.regist = function(){
		var username = $("#username").val();
		var password = $("#password").val();
		// 1.$.ajax带json数据的异步请求
		var aj = $.ajax({
			url : 'user/regist',// 跳转到 action
			data : {
				username : username,
				password : password
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					location.href = baseUrl + "login.jsp"
				}else {
					alert("登录失败！" + data.description);
				}
			},
			error : function() {
				// view("异常！");
				alert("异常！");
			}
		});
	}
	
	this.registCompanyUser = function(){
		var username = $("#username").val();
		var password = $("#password").val();
		var tel = $("#tel").val();
		var verifyCode = $("#verifyCode")
		// 1.$.ajax带json数据的异步请求
		var aj = $.ajax({
			url : 'web/user/registCompanyUser',// 跳转到 action
			data : {
				username : username,
				password : password,
				tel : tel,
				verifyCode : verifyCode
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					location.href = baseUrl + "login.jsp"
				}else {
					alert("登录失败！" + data.description);
				}
			},
			error : function() {
				// view("异常！");
				alert("异常！");
			}
		});
	}
	
	this.getCategories = function(){
		var page={};
		// 1.$.ajax带json数据的异步请求
		var aj = $.ajax({
			url : '/bu/category/getCategories',// 跳转到 action
			data : {
				page : page,
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					return data;
				}else {
					alert("获取失败！" + data.description);
				}
			},
			error : function() {
				// view("异常！");
				alert("异常！");
			}
		});
	}
	
	this.getCompany = function() {
		// 1.$.ajax带json数据的异步请求
		var aj = $.ajax({
			url : 'tmp/getCompany',// 跳转到 action
			data : {
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					var coms = data.data;
					for(var i=0;i<coms.length;i++){
						var o = coms[i];
						$("#companyTable").append("<tr>");
						$("#companyTable").append("<td>" + o.name + "<input type='hidden' id='uid' value='" + o.user.id + "'/><input type='hidden' id='cid' value='" + o.id + "'/></td>");
						$("#companyTable").append("<td>" + o.avatar + "</td>");
						$("#companyTable").append("<td>" + o.description + "</td>");
						$("#companyTable").append("<td>" + o.website + "</td>");
						$("#companyTable").append("<td><a onclick='Jiafang.delCompany(" + o.id + ")'>删除</a>&nbsp;&nbsp;&nbsp;&nbsp;<a onclick='Jiafang.edCompany(" + o.id + ")'>编辑</a></td>");
						$("#companyTable").append("</tr>");
					}
				}else {
					alert("获取失败！" + data.description);
				}
			},
			error : function() {
				// view("异常！");
				alert("异常！");
			}
		});
	};

	this.addCompany = function() {

		var username = $("#username").val();
		var password = $("#password").val();
		var name = $("#name").val();
		var avatar = $("#avatar").val();
		var desc = $("#desc").val();
		var website = $("#website").val();
		// 1.$.ajax带json数据的异步请求
		var aj = $.ajax({
			url : 'tmp/addCompany',// 跳转到 action
			data : {
				'user.username' : username,
				'user.password' : password,
				'company.name' : name,
				'company.avatar' : avatar,
				'company.description' : desc,
				'company.website' : website
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					alert("添加成功！");
				}else {
					alert("添加失败！" + data.description);
				}
			},
			error : function() {
				// view("异常！");
				alert("异常！");
			}
		});
	};
	
	this.addCompanyPic = function(pic,call){
		$.ajax({
			url : baseUrl+'/company/addCompanyPic',// 跳转到 action
			data : {
				'pic.url': pic.url,
				'pic.type': pic.type,
				'pic.companyId': pic.companyId
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					JiaFang.showSuccessToast("添加图片成功");
					if(call){
						call.call(window,data.data);
					}
				}else {
					JiaFang.showFailedToast("添加图片失败！" + data.description);
				}
			},
			error : function() {
				JiaFang.showFailedToast("添加图片失败！");
			}
		});
	};
	
	this.modifyCompanyPic = function(pic,call){
		$.ajax({
			url : baseUrl+'/company/modifyCompanyPic',// 跳转到 action
			data : {
				'pic.id': pic.id,
				'pic.url': pic.url
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					JiaFang.showSuccessToast("修改图片成功");
					if(call){
						call.call(window);
					}
				}else {
					JiaFang.showFailedToast("修改图片失败！" + data.description);
				}
			},
			error : function() {
				JiaFang.showFailedToast("修改图片失败！");
			}
		});
	};
	
	this.deleteCompanyPic = function(picId,call){
		$.ajax({
			url :  baseUrl+'/company/deleteCompanyPic',// 跳转到 action
			data : {
				'pic.id': picId
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					JiaFang.showSuccessToast("删除图片成功");
					if(call){
						call.call(window);
					}
				}else {
					JiaFang.showFailedToast("删除图片失败！" + data.description);
				}
			},
			error : function() {
				JiaFang.showFailedToast("异常！");
			}
		});
	};
	
	this.getCompanyPics = function(pic){
		$.ajax({
			url : baseUrl+'/pic/getCompanyPics',// 跳转到 action
			data : {
				'pic.type': pic.type,
				'pic.companyId': pic.companyId
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					if(pic.type==3||pic.type=='3'){
						var storePics = dom.ejs("js_storePics_tmpl", data);
						$("tr[name='add_storePics']").remove();
						$("#table1").append(storePics);	
					}
					if(pic.type==0||pic.type=='0'){
						var trs = dom.ejs("js_table_tmpl", data);
						$("tr[name='add_tr']").remove();
						$("#table2").append(trs);	
					}
					var list = data.data;
					if(!!list){
						for(var i=0;i<list.length;i++){
							JiaFang.getUrl('avatar_'+i,fileURL+"/"+list[i].url); 
						}
					}
				}else {
					JiaFang.showFailedToast("获取家纺厂图片失败！" + data.description);
				}
			},
			error : function() {
				JiaFang.showFailedToast("获取家纺厂图片失败！");
			}
		});
	};
	
	this.appendPicToTable = function(pic){
		var totalAmount = 0;
		$('table tr').each(function() { $(this).each(function() { totalAmount++;})});
		//var index = totalAmount+1;
		var picId = pic.id;
		if(totalAmount%8==0){
			$("table").append("<tr name='add_storePics'>").append("<td width='12.5%' class='txt-c'>"+
					"<img src id='avatar_"+(totalAmount)+"'>"+
					
					"<input class='btn1' type='button' value='删除' onclick='deletePic("+picId+")'/>"+
				"</td>").append("</tr>");
		}else{
			$('table').find("tr:last").append("<td width='12.5%' class='txt-c'>"+
				"<img src id='avatar_"+(totalAmount)+"'>"+
				
				"<input class='btn1' type='button' value='删除' onclick='deletePic("+picId+")'/>"+
			"</td>");
		}
		JiaFang.getUrl('avatar_'+(totalAmount),fileURL+"/"+pic.url); 
	};
	
	this.uploadToQiNiu = function(file,fileId,showElement,inputElement,call){
		var reader = new FileReader();
	    reader.readAsDataURL(file);
	    reader.onprogress = function(e){
	    	 var percentLoaded = Math.round((e.loaded / e.total) * 100);
	    	    if (percentLoaded < 100) {
	    	    	$("#progressAvatar").val(percentLoaded);
	    	    }
	     }
		reader.onload = function(e) {
	           var imgsrc=this.result;
	           if(showElement){
	        	   $("#"+showElement).attr("src",this.result); 
	           }
	           imgsrc=imgsrc.substr(imgsrc.indexOf("base64,") + 7);
	           JiaFang.showLoading("图片上传中..");
	           $.ajax({
		   			url : baseUrl+'/res/uploadResToQiNiu',// 跳转到 action
		   			data : {
		   				file : imgsrc,
		   			},
		   			type : 'post',
		   			cache : false,
		   			dataType : 'json',
		   			success : function(data) {
		   				if(data.code == 200){
		   					$("#progressAvatar").val(100);
		   					if(inputElement){
		   						$("#"+inputElement).val(data.data);
		   					}
		   					var pic ={
		   							"id":fileId||null,
		   							"url":data.data
		   					}
		   					JiaFang.showSuccessToast("图片上传成功！");
		   					if(call){
								call.call(window,pic);
							}
		   				}else {
		   					JiaFang.showFailedToast("获取七牛资源地址失败！" + data.description);
		   				}
		   			},
		   			error : function() {
		   				JiaFang.showFailedToast("获取七牛资源地址失败！" + data.description);
		   			}
	   			});
	          
	  		 };
	};
	
	this.uploadToQiNiu = function(fileStream,call){
			if(fileStream){
				JiaFang.showLoading("图片上传中..");
			}
	           $.ajax({
		   			url : baseUrl+'/res/uploadResToQiNiu',// 跳转到 action
		   			data : {
		   				file : fileStream,
		   			},
		   			type : 'post',
		   			cache : false,
		   			dataType : 'json',
		   			success : function(data) {
		   				if(data.code == 200){
		   					$("#progressAvatar").val(100);
		   					var pic ={
		   							"id":null,
		   							"url":data.data
		   					}
		   					JiaFang.showSuccessToast("图片上传成功！");
		   					if(call){
								call.call(window,pic);
							}
		   				}else {
		   					JiaFang.showFailedToast("获取七牛资源地址失败！" + data.description);
		   				}
		   			},
		   			error : function() {
		   				JiaFang.showFailedToast("获取七牛资源地址失败！" + data.description);
		   			}
	   			});
	};
	
	this.addSubProduct = function(subProduct,call){
		$.ajax({
			url : baseUrl+'/product/addSubProduct',// 跳转到 action
			data : {
				'subProduct.avatar': subProduct.avatar,
				'subProduct.productId': subProduct.productId
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					JiaFang.showSuccessToast("添加图片成功");
					if(call){
						call.call(window);
					}
				}else {
					JiaFang.showFailedToast("添加图片失败！" + data.description);
				}
			},
			error : function() {
				JiaFang.showFailedToast("异常！");
			}
		});
	};
	
	this.modifySubProduct = function(subProduct,call){
		$.ajax({
			url : baseUrl+'/product/modifySubProduct',// 跳转到 action
			data : {
				'subProduct.avatar': subProduct.avatar,
				'subProduct.id': subProduct.id
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					JiaFang.showSuccessToast("修改图片成功");
					if(call){
						call.call(window);
					}
				}else {
					JiaFang.showFailedToast("修改图片失败！" + data.description);
				}
			},
			error : function() {
				JiaFang.showFailedToast("修改图片失败！");
			}
		});
	};
	
	this.deleteSub = function(subId,call){
		$.ajax({
			url : baseUrl+'/product/deleteSubProduct',// 跳转到 action
			data : {
				'subProduct.id': subId
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					JiaFang.showSuccessToast("删除子产品成功");
					if(call){
						call.call(window);
					}
				}else {
					JiaFang.showFailedToast("删除子产品失败！" + data.description);
				}
			},
			error : function() {
				JiaFang.showFailedToast("异常！");
			}
		});
	};
	
	this.updateSubPics = function(){
		var ids = "";
		var titles = "";
		$(".sub_title").each(function(){
		 	var id = $(this).prop("id").split("_")[1];
		 	var name = $(this).val();
		 	ids+=","+id;
		 	titles+=","+name;
	    })
	    ids=ids.substring(1);
		titles=titles.substring(1);
		$.ajax({
			url : baseUrl+'/product/modifySubProductNames',// 跳转到 action
			data : {
				'ids': ids,
				'titles': titles
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(data) {
				if(data.code == 200){
					JiaFang.showSuccessToast("修改图片标题成功");
				}else {
					JiaFang.showFailedToast("修改图片标题失败！" + data.description);
				}
			},
			error : function() {
				JiaFang.showFailedToast("异常！");
			}
		});
	};
	
	





	/////////////////新版网页\\\\\\\\\\\\\\\\

    this.toCompanyPicsPage = function(){
        location.href= baseUrl + "/page/companyPics";
    }

    this.toCompanyStorePicsPage = function(){
        location.href= baseUrl + "/page/storePics";
    }

    this.toModifyCompanyPage = function(){
        location.href= baseUrl + "/page/companymodify";
    }

    this.toCompanyPage = function(){
        location.href= baseUrl + "/page/companyinfo";
    }

    this.toProductPage = function(){
        location.href= baseUrl + "/page/productlist";
    }

    this.logout = function(){
        location.href= baseUrl + "/page/logout";
    }

    this.toModifyPwd = function(){
        location.href= baseUrl + "/page/modifypwd";
    }

    this.getUrl = function(element, data){
        $.get(baseUrl+"/qiniu/getToken",{"url":data},function(json){
            $("#"+element).attr("src", json);
        },"json");
    };

    this.getCompanyByUserId = function(success){
        $.ajax({
            url : baseUrl + '/company/getCompanyByUserId',// 跳转到 action
            data : {
            },
            type : 'post',
            cache : false,
            dataType : 'json',
            success : success,
            error : function() {
                JiaFang.showFailedToast("网络异常！");
            }
        });
    };
	
}

var JiaFang = new JiaFang();
