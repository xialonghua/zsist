package com.jiafang.service;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;
import com.jiafang.model.Company;
import com.jiafang.model.Pic;

public interface CompanyService extends Constants{

	BaseResp getCompaniesByPage(Page page);
	BaseResp getCompaniesByPageOrderById(Page page);
	BaseResp getCompanyById(Integer companyId);
	
	BaseResp getCompanyByUserId(Integer userId);
	
	BaseResp updateCompany(Company company);
	
	BaseResp addPic(Pic pic);
	
	BaseResp updatePic(Pic pic);
	
	BaseResp deletePic(Pic pic);
}
