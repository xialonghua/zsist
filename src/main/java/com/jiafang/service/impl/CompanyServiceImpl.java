package com.jiafang.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.dao.CompanyDao;
import com.jiafang.model.Company;
import com.jiafang.model.Pic;
import com.jiafang.service.CompanyService;
import com.jiafang.service.Page;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService{


	@Autowired
	private CompanyDao companyDao;
	
	@Override
	public BaseResp getCompaniesByPage(Page page) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		resp.setData(companyDao.query(page));
		return resp;
	}

	@Override
	public BaseResp getCompanyById(Integer companyId) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		Company company = companyDao.queryById(companyId);
		resp.setData(company);
		return resp;
	}
	
	
	
	
	
	@Override
	public BaseResp getCompanyByUserId(Integer userId) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		resp.setData(companyDao.queryByUserId(userId));
		return resp;
	}

	@Override
	public BaseResp updateCompany(Company company) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		resp.setData(companyDao.updateCompany(company));
		return resp;
	}

	@Override
	public BaseResp updateCompanyWechatAlipayAccount(Company company) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		resp.setData(companyDao.updateCompanyWechatAlipayAccount(company));
		return resp;
	}

	@Override
	public BaseResp addPic(Pic pic) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		pic = companyDao.savePic(pic);
		resp.setData(pic);
		return resp;
	}

	@Override
	public BaseResp updatePic(Pic pic) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		pic = companyDao.updatePic(pic);
		resp.setData(pic);
		return resp;
	}

	@Override
	public BaseResp deletePic(Pic pic) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		pic = companyDao.deletePic(pic);
		resp.setData(pic);
		return resp;
	}

	@Override
	public BaseResp getCompaniesByPageOrderById(Page page) {
		BaseResp resp = new BaseResp();
		resp.setCode(SUCCESS);
		resp.setData(companyDao.queryOrderById(page));
		return resp;
	}


}
