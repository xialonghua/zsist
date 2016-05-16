package com.jiafang.service;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;
import com.jiafang.model.Pic;

public interface PicService extends Constants{
	
	BaseResp getProductPics(Pic pic);
	
	BaseResp getCompanyPics(Pic pic);

	BaseResp addPic(Pic pic);
	
	BaseResp updatePic(Pic pic);
	
	BaseResp deletePic(Pic pic);
}
