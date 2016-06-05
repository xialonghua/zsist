package com.jiafang.service;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;
import com.jiafang.model.Version;

public interface VersionService extends Constants{

	BaseResp checkUpdate(String channel, int platform);

	BaseResp getVersions(Page page, int platform);

	BaseResp addVersion(Version version);

	BaseResp delVersion(Integer id);
	
}
