package com.jiafang.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gotye.config.CodeDefs;
import com.gotye.entity.User;
import com.gotye.entity.req.user.ImportUsersReq;
import com.gotye.entity.resp.user.ImportUsersResp;
import com.gotye.remote.PasswordTokenGenerator;
import com.gotye.remote.TokenGenerator;
import com.gotye.remote.proxy.UserApiProxy;

public class IMUtil {
	private static String appkey = Config.getAppKey();

	public static String url = "http://rest.gotye.com.cn/api";
	private static String email = "14847672@qq.com";
	private static String pwd = "123456z";
	private static TokenGenerator tokenGenerator = new PasswordTokenGenerator(url, email, pwd);
	
	public static boolean importUser(Integer userId){
		UserApiProxy userApiProxy = new UserApiProxy();
		userApiProxy.setTokenGenerator(tokenGenerator);
		userApiProxy.setDebug(true);
		List<User> users = new ArrayList<User>();
		User user = new User();
		user.setAccount("" + userId);
		users.add(user);

		ImportUsersReq importUsersReq = new ImportUsersReq();
		importUsersReq.setUsers(users);
		importUsersReq.setAppkey(appkey);

		ImportUsersResp importUsersResp;
		try {
			importUsersResp = userApiProxy.importUsers(importUsersReq);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		if(CodeDefs.SUCCESS == importUsersResp.getStatus() || CodeDefs.USER.USER_IS_EXSITS == importUsersResp.getStatus()){
			return true;
		}
		return false;
	}
}
