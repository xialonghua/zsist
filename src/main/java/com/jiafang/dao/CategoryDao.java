package com.jiafang.dao;

import java.util.List;

import com.jiafang.model.Category;
import com.jiafang.model.CategoryRelationship;
import com.jiafang.service.Page;

public interface CategoryDao {

	public List<Category> query(Page page);

	public List<Category> queryByType(Integer type, Page page);
	public List<Category> queryByType(Integer type, Page page, Integer companyId);
	
	public Category queryById(int id);
	
	public CategoryRelationship queryRelationshipByShipId(Integer shipId);
	
	public List<CategoryRelationship> queryRelationshipByCatrgoryId(Page page, Integer categoryId);
    List<CategoryRelationship> queryRelationshipByCatrgoryId(Page page, Integer categoryId, Integer companyId);
	
	public List<CategoryRelationship> queryRelationshipsByProductId(Integer productId);
	
	public List<CategoryRelationship> queryRelationshipsByCompanyId(Integer companyId);

	public Category saveCategory(Category category);
	
	public Category updateCategory(Category category);
	
	public Category deleteCategory(Category category);
	
	public CategoryRelationship saveRelationship(CategoryRelationship ship);
	
	public CategoryRelationship updateRelationship(CategoryRelationship ship);
	
	public void deleteRelationship(CategoryRelationship ship);
	
	public void deleteRelationshipByCategoryId(int categoryId);
	
	public void deleteRelationshipByCategoryIdProductId(Integer categoryId, Integer productId);
	
	public Integer queryShipIdByProductCategory(int productId, int categoryId);
	
	public Integer queryShipIdByCompanyCategory(int companyId, int categoryId);
	
	public Integer queryMaxWeight();
	
	public CategoryRelationship updateRelationshipWeight(CategoryRelationship ship);

	public void delCategorysByProductId(Integer productId);
}
