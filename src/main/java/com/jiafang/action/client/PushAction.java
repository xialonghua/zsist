package com.jiafang.action.client;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.jiafang.action.JSONAction;
import com.jiafang.service.CategoryService;
import com.jiafang.service.Page;
import com.jiafang.service.UserService;
import com.jiafang.util.PushUtil;
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

    public static void main(String[] strings){


//		JPushClient jpushClient = new JPushClient("4e6677d392cc517f210c8942", "4f3fab1e1ba1ff984e247ff8");
//		Message message = Message.newBuilder().setMsgContent("msg content").setTitle("msg title").addExtra("fuck", "fuck").build();
//
//		// For push, all you need do is to build PushPayload object.
//		PushPayload payload = PushPayload.newBuilder().setMessage(message)
//				.setPlatform(cn.jpush.api.push.model.Platform.all())
//				.setAudience(Audience.alias("42"))
//				.build();
//
//		try {
//			PushResult result = jpushClient.sendPush(payload);
//			System.out.println("push --> " + result);
//
//		} catch (APIConnectionException e) {
//			// Connection error, should retry later
////			LOG.error("Connection error, should retry later", e);
//			e.printStackTrace();
//
//		} catch (APIRequestException e) {
//			// Should review the error, and fix the request
//            e.printStackTrace();
//            System.out.println("push --> " + e.getErrorMessage());
//
//		}
//        PushUtil.newOrder(42);
    }
	
	@Action(value = "push")
	public String push() {
		return RETURN_JSON;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
