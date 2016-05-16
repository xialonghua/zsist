package com.jiafang.action.client;

import com.jiafang.action.JSONAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiafang.service.VersionService;

@ParentPackage("basePackage")
@Namespace("/update")
public class UpdateAction extends JSONAction {

	@Autowired
	private VersionService versionService;
	
	private Integer platform;//O-IOS 1-ANDROID
	private String channel;
	
	@Action(value = "checkUpdate")
	public String checkUpdate() {
		
    	setData(versionService.checkUpdate(channel, platform));
	    return RETURN_JSON;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
	
}
