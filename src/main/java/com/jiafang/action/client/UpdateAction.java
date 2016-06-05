package com.jiafang.action.client;

import com.jiafang.action.JSONAction;
import com.jiafang.model.Version;
import com.jiafang.service.Page;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
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
	private Page page;
    private Version version;

	@Action(value = "checkUpdate")
	public String checkUpdate() {

		setData(versionService.checkUpdate(channel, platform));
		return RETURN_JSON;
	}

	@Action(value = "delVersion", interceptorRefs={@InterceptorRef(ADMIN_INTERCEPTOR)})
	public String delVersion() {

		setData(versionService.delVersion(version.getId()));
		return RETURN_JSON;
	}


    @Action(value = "getVersions", interceptorRefs={@InterceptorRef(ADMIN_INTERCEPTOR)})
    public String getVersions() {

        setData(versionService.getVersions(page, platform));
        return RETURN_JSON;
    }

    @Action(value = "addVersion", interceptorRefs={@InterceptorRef(ADMIN_INTERCEPTOR)})
    public String addVersion() {
        setData(versionService.addVersion(version));
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


	public Page getPage() {
		return page;
	}

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public void setPage(Page page) {
		this.page = page;
	}
}
