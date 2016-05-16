package com.jiafang.action.client;

import com.jiafang.action.JSONAction;
import com.jiafang.util.QiniuHelper;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;

import com.qiniu.util.Auth;

@ParentPackage("basePackage")
@Namespace("/qiniu")
public class QiniuAction extends JSONAction {

	private static Auth auth = Auth.create("Oo0xoMRiwPxyVjzIq0bNynY6YCh2ZSc8m5CbBJiT", "KJt0Is640mwbJ9sj6Aov6MMcbDACfH9xOjd8Duvq");
	
	private String url;

	@Action(value = "getToken")
	public String getToken() {
		//指定时长
		String urlSigned2 = auth.privateDownloadUrl(getUrl(), 3600 * 24);
		setData(urlSigned2);
		return RETURN_JSON;
	}

	@Action(value = "getUpToken")
	public String getUpToken() {
		//指定时长
		String urlSigned2 = QiniuHelper.getToken();
		setData(urlSigned2);
		return RETURN_JSON;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}