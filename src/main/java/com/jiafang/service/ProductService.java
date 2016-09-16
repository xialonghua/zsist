package com.jiafang.service;

import java.util.List;

import com.jiafang.action.resp.BaseResp;
import com.jiafang.common.Constants;
import com.jiafang.model.*;

public interface ProductService extends Constants{

	BaseResp getProductsByCategory(Page page, Integer categoryId, int platform);
	BaseResp getProductsByCategory(Page page, Integer categoryId, Integer companyId, int platform);

	BaseResp getProductsByCompanyId(Page page, Integer companyId, int platform);
	
	BaseResp searchProducts(Page page, String name, int platform);
	
	BaseResp searchCompanies(Page page, String name);

	BaseResp searchProductsByCategory(Page page, String name, Integer categoryId, int platform);

	BaseResp searchProductsByCategory(Page page, String name, Integer categoryId, Integer companyId, int platform);


	BaseResp searchProductsByCompany(Page page, String name, Integer companyId, int platform);
	
	BaseResp getProductById(Integer productId);
	
	BaseResp getProductsByCompanyUserId(Page page, Integer userId, int platform);
	
	BaseResp addProduct(Product product);
	
	BaseResp modifyProduct(Product product);
	
	BaseResp deleteProduct(Product product);

	BaseResp addProductParam(Param param);

	BaseResp updateProductParam(Param param);

	BaseResp deleteProductParam(Param param);

	BaseResp addProductSize(ProductSize param);

	BaseResp updateProductSize(ProductSize param);

	BaseResp updateProductPlatform(Product product);

	BaseResp deleteProductSize(ProductSize param);
	
	BaseResp addPic(Pic pic);
	
	BaseResp addPics(List<Pic> pics);
	
	BaseResp updatePic(Pic pic);
	
	BaseResp deletePic(Pic pic);
	
	BaseResp getSubProduct(Integer subId);
	
	BaseResp saveSubProduct(SubProduct sub);
	
	BaseResp updateSubProduct(SubProduct sub);
	
	BaseResp deleteSubProduct(SubProduct sub);
	
	BaseResp getProductPics(Integer productId, Integer type);
	
	BaseResp getProductParamById(Integer paramId);
	
	BaseResp getProducts(Page page);
	
	BaseResp getProdunctNumberCount(String num);
}
