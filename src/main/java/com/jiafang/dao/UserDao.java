package com.jiafang.dao;

import java.util.List;

import com.jiafang.model.Address;
import com.jiafang.model.Product;
import com.jiafang.model.User;

public interface UserDao {

	public User queryByUsername(String username, String tel);
	
	public User queryByUserId(Integer userId);
	
	public void updateUserInfo(Integer userId, String nickname, String avatar);
	
	public User saveUser(User user);
	
	public User updateUserPwd(Integer userId, String password);
	
	public User updateUserPwd(String tel, String password);
	
	public Address saveOrModifyAddress(Address address);
	
	public void delAddress(Integer addressId, Integer userId);

	public List<Address> getAddresses(Integer userId);

	public Address getAddressById(Integer userId, Integer addressId);
	
	public void setDefaultAddress(Integer addressId, Integer userId);
	
	public void delUser(Integer userId, Integer companyId, List<Product> products);
}
