package com.jiafang.dao;

import java.util.List;

import com.jiafang.model.Param;
import com.jiafang.model.Pic;
import com.jiafang.model.Product;
import com.jiafang.model.SubProduct;
import com.jiafang.service.Page;

public interface ProductDao {
	
	public List<Pic> queryAllPicsByType(Integer type);

	public List<Product> queryByCatrgoryId(Page page, Integer categoryId);
	public List<Product> queryByCatrgoryId(Page page, Integer categoryId, Integer comapnyId);

	public List<Product> queryByCompanyId(Page page, Integer companyId);
	
	public Product queryById(Integer productId);
	public Product querySimpleById(Integer productId);
	
	public List<Product> queryByName(Page page, String name);
	
	public List<Product> queryByNameAndCategoryId(Page page, String name, Integer categoryId);
	
	public List<Product> queryByNameAndCompanyId(Page page, String name, Integer companyId);

	public Product saveProduct(Product product);
	
	public Product updateProduct(Product product);
	
	public Product deleteProduct(Product product);
	
	public Param saveParam(Param param);
	
	public Param updateParam(Param param);
	
	public Param deleteParam(Param param);
	
	public Pic savePic(Pic pic);
	
	public void savePics(List<Pic> pics);
	
	public Pic updatePic(Pic pic);
	
	public Pic deletePic(Pic pic);
	
	public SubProduct saveSubProduct(SubProduct sub);
	
	public SubProduct updateSubProduct(SubProduct sub);
	
	public SubProduct deleteSubProduct(SubProduct sub);
	
	public SubProduct getSubProduct(Integer subId);
	
	public Product queryProductPics(Integer productId, Integer type);
	
	public Param getParamById(Integer paramId);
	
	public List<Product> queryProducts(Page page);
	
	public Integer getProdunctNumberCount(String num);
	
	public void delProductsByCompanyId(Integer companyId);
	
	public void delParamsByProductId(Integer productId);
}
