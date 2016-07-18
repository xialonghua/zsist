package com.jiafang.dao;

import java.util.List;

import com.jiafang.model.Company;
import com.jiafang.model.Pic;
import com.jiafang.model.Product;
import com.jiafang.service.Page;

public interface CompanyDao {

	public List<Company> query(Page page);
	public List<Company> queryOrderById(Page page);
	
	public Company saveCompany(Company company);
	
	public Company queryById(Integer companyId);
	
	public Company querySimpleById(Integer companyId);
	
	public List<Company> queryByName(Page page, String name);
	public List<Company> queryByNameOrderById(Page page, String name);
	
	public Company queryByUserId(Integer UserId);
	
	public Company updateCompany(Company company);
	Company updateCompanyWechatAlipayAccount(Company company);
	
	public Pic savePic(Pic pic);
	
	public Pic updatePic(Pic pic);
	
	public Pic deletePic(Pic pic);
	
	public void deleteCompany(Integer company);
}
