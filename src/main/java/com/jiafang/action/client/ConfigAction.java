package com.jiafang.action.client;

import com.jiafang.action.JSONAction;
import com.jiafang.action.resp.BaseResp;
import com.jiafang.model.Company;
import com.jiafang.model.User;
import com.jiafang.service.CategoryService;
import com.jiafang.service.Page;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@ParentPackage("basePackage")
@Namespace("/config")
public class ConfigAction extends JSONAction {

	private User user;
	private Company company;

	@Action(value = "getConfig")
	public String getConfig() {
		BaseResp resp = new BaseResp();

		Map<String, String> map = new HashMap<>();

        map.put("aliPaySK", "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAK2nNbBZnsZhgf0u1KiCyZf2tJnByGbwaf8kDgHYQRP0yngFIYUjdgEoZ9F7OplTBqaTysFhlgdNO6aphPHPlIjkyqx8Z3C9yKpRjzygF1jqD552oicZIaLVaPObYqmTlEDrPtU4TKGbKI9M0plVtGcETLgy7r8A/l+KxcYNS/83AgMBAAECgYAElNG8ylB/CVmFlXO00maBJNgU6mDgHl2dhpT8BXmKDj49Nl1xBsN5Oliq1SZxnxgg0utZeLqdjLMTLYu9WW2gt87PzJzXm3tgSBa+mzOJfsqMjprm1ysPXF0GsIIMy1tdVYeyaxI4I0lAfrwzanfG/JWqX3IRFifaqaq/kTRisQJBANouOHCWkcngzUMrP3E2ZbLongF6VpmJrfEyJY8EeoEyzPOYZP9J6Ndp/f9TydrVJxX5CxOIBzlOJ/nclBL1yCkCQQDLwRU5LRTMq+7nJnCuCwty9oim/BHugdElTeURJPyrmg8BHx3vLl6FDmfY3f4FSCihrpfJBAeNgjJOhKBKl/hfAkEA1TCwyaL3FdJp/6Y0ucUznpwaOMt371NO+g3MEXGI2tHu3mAc4C8yK7tQC7ut0/zP0nKlVhJnLKGoAkh9EAHF8QJBAImyiSdEwiO3phJstgXIzQn6dktzWqL29GLji4TUnDVUPgOiBdbYXWRKYf/7tmnXZC4wEb/1iy0k6d6KXtFKdr0CQQCM01pRQJMn0D1fVpAn92zmbTOEPQtaiFq6MWb61Zot2kPFetxyytxrA6PMm4P7BLpYrrD/PL3f3lpmU3f1FdJA");
        map.put("aliPayPartner", "2088121694110155");
        map.put("aliPaySellerId", "2827168@qq.com");
		map.put("aliPayCallback", "http://textile.zsist.com/textile/order/aliPayCallback");
		map.put("imAppKey", "319380fc-09e2-45b6-a847-188a66babf98");

		resp.setData(map);
		resp.setCode(SUCCESS);
		return RETURN_JSON;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
