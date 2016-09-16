package com.jiafang.service;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;
import com.jiafang.model.Category;
import com.jiafang.model.CategoryRelationship;

public interface CategoryService extends Constants {
	
	BaseResp getCategorieById(int id);

	BaseResp getCategories(Page page);

	BaseResp getCategories(Page page, Integer companyId);
	
	BaseResp getCategoriesByType(Integer type, Page page);
	
	BaseResp getCategoriesOfMark(Integer type, Integer productId);
	
	BaseResp getItemsById(int id, Page page);
	
	BaseResp getItemByShipId(int shipId);
	
	public BaseResp getAdsProduct(int platform);
    BaseResp getAdsProduct(Integer companyId, int platform);
	
	public BaseResp getAdsCompany();
	
	public BaseResp getZones(Integer count);
    BaseResp getZones(Integer count, Integer companyId);
	
	
	public BaseResp addRelationship(CategoryRelationship ship);
	
	public BaseResp addAdvertisementRelationship(int categoryId, String productIds);
	
	public BaseResp deleteRelationship(CategoryRelationship ship);
	
	public BaseResp deleteRelationshipById(Integer categoryId, Integer productId);
	
	
	public BaseResp saveCategory(Category category);
	
	public BaseResp updateCategory(Category category);
	
	public BaseResp deleteCategory(Category category);
	
	public BaseResp updateRelationshipWeight(CategoryRelationship ship);
	
}
