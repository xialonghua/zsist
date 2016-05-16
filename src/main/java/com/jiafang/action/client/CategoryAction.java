package com.jiafang.action.client;

import com.jiafang.action.JSONAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;

import com.jiafang.model.Category;
import com.jiafang.model.CategoryRelationship;
import com.jiafang.service.CategoryService;
import com.jiafang.service.Page;

@ParentPackage("basePackage")
@Namespace("/category")
public class CategoryAction extends JSONAction {

	@Autowired
	private CategoryService categoryService;
	
	private Page page;
	private Integer type;
	private Integer categoryId;
	private Integer productId;
	private Integer shipId;
	private CategoryRelationship ship;
	private Category category;
	private String productIds;
	
	@Action(value = "getCategories")
	public String getCategories() {
		setData(categoryService.getCategories(page));
		return RETURN_JSON;
	}
	
	@Action(value = "getCategory")
	public String getCategoryById() {
		setData(categoryService.getCategorieById(categoryId));
		return RETURN_JSON;
	}
	
	@Action(value = "getCategoriesByType")
	public String getCategoriesByType() {
		setData(categoryService.getCategoriesByType(type,page));
		return RETURN_JSON;
	}
	
	@Action(value = "getCategoriesOfMark")
	public String getCategoriesOfMarkByProductId() {
		setData(categoryService.getCategoriesOfMark(type,productId));
		return RETURN_JSON;
	}
	
	
	
	@Action(value = "addCategory")
	public String addCategory() {
		setData(categoryService.saveCategory(category));
		return RETURN_JSON;
	}
		
	@Action(value = "modifyCategory")
	public String modifyCategory() {
		setData(categoryService.updateCategory(category));
		return RETURN_JSON;
	}
	
	@Action(value = "deleteCategory")
	public String deleteCategory() {
		setData(categoryService.deleteCategory(category));
		return RETURN_JSON;
	}
	
	@Action(value = "getItemByShipId")
	public String getItemByShipId() {
		setData(categoryService.getItemByShipId(shipId));
		return RETURN_JSON;
	}
	
	@Action(value = "getItemsById")
	public String getItemsById() {
		setData(categoryService.getItemsById(categoryId,page));
		return RETURN_JSON;
	}
	
	@Action(value = "addRelationship")
	public String addRelationship() {
		setData(categoryService.addRelationship(ship));
		return RETURN_JSON;
	}
	
	@Action(value = "addAdvertisementRelationship")
	public String addAdvertisementRelationship() {
		setData(categoryService.addAdvertisementRelationship(categoryId,productIds));
		return RETURN_JSON;
	}
	
	
	@Action(value = "deleteRelationship")
	public String deleteRelationship() {
		setData(categoryService.deleteRelationship(ship));
		return RETURN_JSON;
	}
	
	@Action(value = "deleteRelationshipById")
	public String deleteRelationshipById() {
		setData(categoryService.deleteRelationshipById(categoryId, productId));
		return RETURN_JSON;
	}
	
	@Action(value = "updateRelationshipWeight")
	public String updateRelationshipWeight() {
		setData(categoryService.updateRelationshipWeight(ship));
		return RETURN_JSON;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public CategoryRelationship getShip() {
		return ship;
	}

	public void setShip(CategoryRelationship ship) {
		this.ship = ship;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

	public Integer getShipId() {
		return shipId;
	}

	public void setShipId(Integer shipId) {
		this.shipId = shipId;
	}
	
	
}
