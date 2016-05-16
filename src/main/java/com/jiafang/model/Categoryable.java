package com.jiafang.model;

import java.util.List;

public interface Categoryable {

	public String getTargetType();
	
	public void setTargetType(String t);
	
	public List<CategoryRelationship> getCategoryShips();

	public void setCategoryShips(List<CategoryRelationship> categoryShip);

	public CategoryRelationship getCategoryShip();

	public void setCategoryShip(CategoryRelationship categoryShip);
}
