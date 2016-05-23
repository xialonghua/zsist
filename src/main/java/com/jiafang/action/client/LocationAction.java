package com.jiafang.action.client;

import com.jiafang.action.JSONAction;
import com.jiafang.action.resp.BaseResp;
import com.jiafang.model.Address;
import com.jiafang.model.Company;
import com.jiafang.model.User;
import com.jiafang.model.UserLevel;
import com.jiafang.service.CompanyService;
import com.jiafang.service.UserService;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@ParentPackage("basePackage")
@Namespace("/location")
public class LocationAction extends JSONAction {

	@Autowired
	private UserService userService;

	private User user;
    @Action(value = "uploadLocation", interceptorRefs={@InterceptorRef(LOGIN_INTERCEPTOR)})
    public String uploadLocation(){
        User loginUser = getLoginUser();
        user.setId(loginUser.getId());
        BaseResp resp = userService.uploadLocation(user);
        setData(resp);
        return RETURN_JSON;
//		SELECT id, ( 3959 * acos( cos( radians(37) ) * cos( radians( lat ) ) * cos( radians( lng ) - radians(-122) ) + sin( radians(37) ) * sin( radians( lat ) ) ) ) AS distance FROM markers HAVING distance < 25 ORDER BY distance LIMIT 0 , 20;
    }


    @Action(value = "getProvinces")
    public String getProvinces(){
        BaseResp resp = userService.getProvinces();
        setData(resp);
        return RETURN_JSON;
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
