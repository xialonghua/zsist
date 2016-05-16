package com.jiafang.model;

import org.hibernate.annotations.Entity;
import org.hibernate.annotations.Table;

public class Test implements java.io.Serializable{

	private String name = "2";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
