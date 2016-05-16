$(function() {
	var token = $("#token").val();
	var domain = $("#domain").val();
	var fileUploadType = $("#fileUploadType").val()+"";//添加：  0-公司、产品图片  1-视频文件  2-开发包  3-门店形象图片 4-公司图片列表  5-子产品图片列表 6-产品介绍图片
													
	var companyId = $("#companyId").val();
	//var elementTarget = $("#elementTarget").val();
	var uploader = Qiniu.uploader({
	    runtimes: 'html5,flash,html4',    //上传模式,依次退化
	    browse_button: 'pickfiles',       //上传选择的点选按钮，**必需**
//	    uptoken_url: '/token',
	        //Ajax请求upToken的Url，**强烈建议设置**（服务端提供）
	    uptoken : token,
	        //若未指定uptoken_url,则必须指定 uptoken ,uptoken由其他程序生成
	    unique_names: true,
	        // 默认 false，key为文件名。若开启该选项，SDK会为每个文件自动生成key（文件名）
	    save_key: true,
	        // 默认 false。若在服务端生成uptoken的上传策略中指定了 `sava_key`，则开启，SDK在前端将不对key进行任何处理
	    domain: domain,
	        //bucket 域名，下载资源时用到，**必需**
	    container: 'container',           //上传区域DOM ID，默认是browser_button的父元素，
	    max_file_size: '300mb',           //最大文件体积限制
	    flash_swf_url: 'js/plupload/Moxie.swf',  //引入flash,相对路径
	    max_retries: 3,                   //上传失败最大重试次数
	    dragdrop: true,                   //开启可拖曳上传
	    drop_element: 'container',        //拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
	    chunk_size: '4mb',                //分块上传时，每片的体积
	    auto_start: true,                 //选择文件后自动上传，若关闭需要自己绑定事件触发上传
	    persistentOps: '',
	    init: {
	        'FilesAdded': function(up, files) {
	            plupload.each(files, function(file) {
	                // 文件添加进队列后,处理相关的事情
	            	
	            });
	        },
	        'BeforeUpload': function(up, file) {
	               // 每个文件上传前,处理相关的事情
	        	if(fileUploadType=='1'){
	        		if (!/video\/mp4/.test(file.type)) {
	        			JiaFang.showFailedToast("请选择mp4文件！");
	    	            return false;
	        		}
	        	}
	        	if(fileUploadType=='0'||fileUploadType=='3'||fileUploadType=='4' ){
	        		if (!/image\/\w+/.test(file.type)) {
	    			 	JiaFang.showFailedToast("请选择图片！");
	    	            return false;
	        		}
	        	}
	        },
	        'UploadProgress': function(up, file) {
	               // 每个文件上传时,处理相关的事情
	        	var percent = up.total.percent;
	        	
	        	$("#progress-bar").attr("aria-valuenow", percent);
	        	
	        	$("#progressFile").val(percent);
	        },
	        'FileUploaded': function(up, file, info) {
	               // 每个文件上传成功后,处理相关的事情
	               // 其中 info 是文件上传成功后，服务端返回的json，形式如
	               // {
	               //    "hash": "Fh8xVqod2MQ1mocfI4S4KpRL6D98",
	               //    "key": "gogopher.jpg"
	               //  }
	               // 参考http://developer.qiniu.com/docs/v6/api/overview/up/response/simple-response.html
	        		var domain = up.getOption('domain');
	                var res = Qiniu.parseJSON(info);
	                var sourceLink = domain + res.key; //获取上传成功后的文件的Url
	                console.log("sourceLink:"+sourceLink);
	               
	                if(fileUploadType=="undefined" || !fileUploadType){
	                	$("#videoUrlKey").val(res.key);
		                JiaFang.getUrl("banner_video",sourceLink);
	                }
	                if(fileUploadType=='1'){
	                	$("#videoUrlKey").val(res.key);
		                JiaFang.getUrl("banner_video",sourceLink);
	                }
	                if(fileUploadType=='3'){
	                	var pic = {
	                			url:res.key,
	                			type:3,
	                			companyId:companyId
	                	}
	                	JiaFang.addCompanyPic(pic,function(data){
	                		JiaFang.getCompanyPics(pic);
	                		//JiaFang.appendPicToTable(data);
	                	});
	                }
	                if(fileUploadType=='4'){
	                	var pic = {
	                			url:res.key,
	                			type:0,
	                			companyId:companyId
	                	}
	                	JiaFang.addCompanyPic(pic,function(data){
	                		JiaFang.getCompanyPics(pic); 
	                		
	                	});
	                }
	                
	                $("#progressFile").val(100);
				JiaFang.showSuccessToast("上传成功");
	        },
	        'Error': function(up, err, errTip) {
	               //上传出错时,处理相关的事情
	        	alert("error:"+errTip);
	        },
	        'UploadComplete': function() {
	               //队列文件处理完毕后,处理相关的事情
	        },
	        'Key': function(up, file) {
	            // 若想在前端对每个文件的key进行个性化处理，可以配置该函数
	            // 该配置必须要在 unique_names: false , save_key: false 时才生效
	            var key = "";
	            // do something with key here
	            return key
	        }
	    }
	});
})
