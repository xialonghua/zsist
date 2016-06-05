package com.jiafang.action.client;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;
import com.jiafang.action.JSONAction;
import com.jiafang.service.CategoryService;
import com.jiafang.service.Page;
import com.jiafang.service.UserService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

@ParentPackage("basePackage")
@Namespace("/push")
public class PushAction extends JSONAction {

	@Autowired
	private CategoryService categoryService;
	@Autowired
	private UserService userService;
	
	private String content;
	
	@Action(value = "push")
	public String push() {
//		d484285ddf9fe790c68acec0
//		JPushClient jpushClient = new JPushClient("ac0f77b782f51df9a62f3ad3", "d484285ddf9fe790c68acec0", 3);
//
//		// For push, all you need do is to build PushPayload object.
//		PushPayload payload = buildPushObject_all_all_alert();
//
//		try {
//			PushResult result = jpushClient.sendPush(payload);
////			LOG.info("Got result - " + result);
//
//		} catch (APIConnectionException e) {
//			// Connection error, should retry later
////			LOG.error("Connection error, should retry later", e);
//            e.printStackTrace();
//
//		} catch (APIRequestException e) {
//			// Should review the error, and fix the request
////			LOG.error("Should review the error, and fix the request", e);
////			LOG.info("HTTP Status: " + e.getStatus());
////			LOG.info("Error Code: " + e.getErrorCode());
////			LOG.info("Error Message: " + e.getErrorMessage());
//
//		}

		return RETURN_JSON;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
