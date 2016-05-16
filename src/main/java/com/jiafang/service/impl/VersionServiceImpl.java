package com.jiafang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.dao.VersionDao;
import com.jiafang.model.Version;
import com.jiafang.service.VersionService;

@Service
@Transactional
public class VersionServiceImpl implements VersionService {

	@Autowired
	private VersionDao versionDao;

	@Override
	public BaseResp checkUpdate(String channel, int platform) {
		Version version = versionDao.queryVersion(channel, platform);
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		resp.setData(version);
		return resp;
	}
}
