package com.jiafang.filter;

import com.jiafang.action.JSONAction;
import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;
import com.jiafang.model.User;
import com.jiafang.model.UserLevel;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.struts2.convention.annotation.ParentPackage;

import java.util.Map;

@ParentPackage("basePackage")
public class AdminInterceptor extends AbstractInterceptor implements Constants {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        // 取得请求相关的ActionContext实例
        ActionContext ctx = invocation.getInvocationContext();
        Map session = ctx.getSession();
        User user = (User) session.get("user");

        if (user.getLevel() != UserLevel.ADMIN.ordinal()){
            BaseResp resp = new BaseResp();
            resp.setCode(INVALIDAT_REQUEST);
            return RETURN_JSON;
        }
        return invocation.invoke();
    }

}
