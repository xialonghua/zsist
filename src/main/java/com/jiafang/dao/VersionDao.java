package com.jiafang.dao;

import com.jiafang.model.Version;
import com.jiafang.service.Page;

import java.util.List;

public interface VersionDao {

	public Version queryVersion(String channel, int platform);

	public List<Version> queryVersions(Page page, int platform);

    public Version addVersion(Version version);

	public void delVersion(Integer id);
	
}
