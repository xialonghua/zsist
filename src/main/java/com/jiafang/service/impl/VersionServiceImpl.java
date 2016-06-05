package com.jiafang.service.impl;

import com.jiafang.service.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.dao.VersionDao;
import com.jiafang.model.Version;
import com.jiafang.service.VersionService;

import java.util.List;

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

    @Override
    public BaseResp getVersions(Page page, int platform) {
        List<Version> versions = versionDao.queryVersions(page, platform);
        BaseResp resp = new BaseResp();
        resp.setCode(SUCCESS);
        resp.setData(versions);
        resp.setPage(page);
        return resp;
    }

    @Override
    public BaseResp addVersion(Version version) {

        versionDao.addVersion(version);
        BaseResp resp = new BaseResp();
        resp.setCode(SUCCESS);
        resp.setData(version);
        return resp;
    }

    @Override
    public BaseResp delVersion(Integer id) {
        versionDao.delVersion(id);
        BaseResp resp = new BaseResp();
        resp.setCode(SUCCESS);
        return resp;
    }
}
