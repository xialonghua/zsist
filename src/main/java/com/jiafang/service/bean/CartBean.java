package com.jiafang.service.bean;

import java.util.ArrayList;
import java.util.List;

import com.jiafang.model.Cart;
import com.jiafang.model.Company;
import com.jiafang.model.Product;
import com.jiafang.model.SubProduct;

public class CartBean {

	private Company company;
	private List<Product> products = new ArrayList<>();
	private List<SubProduct> subProduts = new ArrayList<>();
	private List<Cart> carts = new ArrayList<>();
	private Double totalPrice;
	
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public List<SubProduct> getSubProduts() {
		return subProduts;
	}
	public void setSubProduts(List<SubProduct> subProduts) {
		this.subProduts = subProduts;
	}


//	public List<Cart> getCarts() {
//		return carts;
//	}
//	public void setCarts(List<Cart> carts) {
//		this.carts = carts;
//	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartBean other = (CartBean) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		return true;
	}
	public List<Cart> getCarts() {
		return carts;
	}
	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
}
