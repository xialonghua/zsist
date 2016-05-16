package com.jiafang.service;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;

public interface VersionService extends Constants{

	BaseResp checkUpdate(String channel, int platform);
	
}
