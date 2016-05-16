package com.jiafang.service.bean;

import java.util.List;

import com.jiafang.model.Category;
import com.jiafang.model.Categoryable;

public class Ad {

	private Category category;
	private List<? extends Categoryable> items;
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public List<? extends Categoryable> getItems() {
		return items;
	}
	public void setItems(List<? extends Categoryable> items) {
		this.items = items;
	}
	
}
