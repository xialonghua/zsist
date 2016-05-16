package com.jiafang.dao;

import com.jiafang.model.Version;

public interface VersionDao {

	public Version queryVersion(String channel, int platform);
	
}
