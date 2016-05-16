package com.jiafang.web.service.impl;

import org.springframework.stereotype.Service;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.util.QiniuHelper;
import com.jiafang.web.service.WebResService;

@Service
public class WebResServiceImpl implements WebResService{

	@Override
	public BaseResp uploadToQiniu(String key, byte[] bytes) {
		BaseResp resp = new BaseResp();
		QiniuHelper qiniu = new QiniuHelper();
		if(qiniu.uploadImageFile(key,bytes)){
			resp.setCode(SUCCESS);
			resp.setData(key);//获取图片访问id
			return resp;
		}
		return null;
	}

}
