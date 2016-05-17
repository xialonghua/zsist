package com.jiafang.filter;

import java.util.Map;

import org.apache.struts2.convention.annotation.ParentPackage;

import com.jiafang.action.JSONAction;
import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;
import com.jiafang.model.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@ParentPackage("basePackage")
public class LoginInterceptor extends AbstractInterceptor implements Constants {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        // 取得请求相关的ActionContext实例
        ActionContext ctx = invocation.getInvocationContext();
        Map session = ctx.getSession();
        User user = (User) session.get("user");

        // 如果没有登陆，或者登陆所有的用户名不是，都返回重新登陆
        JSONAction action = (JSONAction) invocation.getAction();
        action.setLoginUser(user);
        if (user != null) {
            return invocation.invoke();
        }

        BaseResp resp = new BaseResp();
        resp.setCode(USER_NOT_LOGIN);
        action.setData(resp);
        return RETURN_JSON;
    }

}
