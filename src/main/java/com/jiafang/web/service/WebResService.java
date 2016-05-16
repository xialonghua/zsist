package com.jiafang.web.service;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;

public interface WebResService extends Constants{
	
	BaseResp uploadToQiniu(String key, byte[] bytes);
}
