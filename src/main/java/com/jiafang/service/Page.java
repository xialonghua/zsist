package com.jiafang.service;

public class Page {

	private int index;
	private int pageSize;
	private int page;
	public Page(){}
	
	public Page(int page, int pageSize){
		this.index = page / pageSize;
		this.pageSize = pageSize;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getIndex(){
		return page * pageSize;
	}
}
