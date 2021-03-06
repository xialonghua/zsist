package com.jiafang.dao;

import java.util.List;

import com.jiafang.model.*;
import com.jiafang.service.Page;

public interface UserDao {

	public User queryByUsername(String username, String tel);

	public List<User> getUsers(Page page);

    public User queryByUserId(Integer userId);

    public List<UserTag> getUserTags(Integer userId);

	public void updateUserInfo(Integer userId, String nickname, String avatar);
	
	public User saveUser(User user);

	UserCompanyBind saveUserCompanyBind(UserCompanyBind user);

	public UserCompanyBind queryUserCompanyBind(Integer userId, Integer companyId);

    List<UserCompanyBind> queryUserCompanyBinds(Integer companyId, Page page);

	public User updateUserPwd(Integer userId, String password);
	
	public User updateUserPwd(String tel, String password);
	
	public Address saveOrModifyAddress(Address address);
	
	public void delAddress(Integer addressId, Integer userId);

	public List<Address> getAddresses(Integer userId);

	public Address getAddressById(Integer userId, Integer addressId);
	
	public void setDefaultAddress(Integer addressId, Integer userId);
	
	public void delUser(Integer userId, Integer companyId, List<Product> products);

	public void updateUserLocation(Integer userId, String country, String province, String city, String cityCode,
								   String adCode, String address, String road, String poiName, String district, Float lng, Float lat);

	public List<Province> getProvinces();
}
