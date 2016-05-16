package com.jiafang.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jiafang.dao.UserDao;
import com.jiafang.model.Address;
import com.jiafang.model.Company;
import com.jiafang.model.Product;
import com.jiafang.model.User;
import com.jiafang.util.StringUtil;
import com.qiniu.util.StringUtils;

@Repository
public class UserDaoImpl implements UserDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public UserDaoImpl() {
	}

	@Override
	public User queryByUsername(String username, String tel) {
		
		Criteria query = sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.or(Restrictions.eq("username", username), Restrictions.eq("tel", tel)));
		return (User) query.uniqueResult();
	}

	@Override
	public User saveUser(User user) {
		sessionFactory.getCurrentSession().save(user);
		return user;
	}

	@Override
	public User updateUserPwd(String tel, String password) {
//		sessionFactory.getCurrentSession().up
		
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();  
		Criteria query = session.createCriteria(User.class);
		query.add(Restrictions.eq("tel", tel));
	    User temp = (User) query.uniqueResult();
	    temp.setPassword(password);  
	    session.flush();
	    session.saveOrUpdate(temp);
	    session.getTransaction().commit();
		return temp;
	}
	
	@Override
	public User updateUserPwd(Integer userId, String password) {
//		sessionFactory.getCurrentSession().up
		
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();  
		Criteria query = session.createCriteria(User.class);
		query.add(Restrictions.eq("id", userId));
	    User temp = (User) query.uniqueResult();
	    temp.setPassword(password);  
	    session.flush();
	    session.saveOrUpdate(temp);
	    session.getTransaction().commit();
		return temp;
	}

	@Override
	public User queryByUserId(Integer userId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.idEq(userId));
		return (User) query.uniqueResult();
	}

	@Override
	public void updateUserInfo(Integer userId, String nickname, String avatar) {
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();  
		Criteria query = session.createCriteria(User.class);
		query.add(Restrictions.idEq(userId));
	    User temp = (User) query.uniqueResult();
	    if(!StringUtils.isNullOrEmpty(avatar)){
	    	temp.setAvatar(avatar);
	    }
	    if(!StringUtils.isNullOrEmpty(nickname)){
	    	temp.setNickname(nickname);
	    }
	   
	    session.flush();
	    session.saveOrUpdate(temp);
	    session.getTransaction().commit();
	}

	@Override
	public Address saveOrModifyAddress(Address address) {
		if(address.getUserId() == null){
			throw new NullPointerException();
		}
		Session session = sessionFactory.getCurrentSession();  
		session.saveOrUpdate(address);
		return address;
	}

	@Override
	public void delAddress(Integer addressId, Integer userId) {
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();  
		Criteria query = session.createCriteria(Address.class);
		query.add(Restrictions.and(Restrictions.idEq(addressId), Restrictions.eq("userId", userId)));
	    Address temp = (Address) query.uniqueResult();
	    
	    session.delete(temp);
	    
	    session.flush();
	    session.getTransaction().commit();
	}

	@Override
	public List<Address> getAddresses(Integer userId) {
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();  
		Criteria query = session.createCriteria(Address.class);
		query.add(Restrictions.eq("userId", userId));
		return query.list();
	}

	@Override
	public Address getAddressById(Integer userId, Integer addressId) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria query = session.createCriteria(Address.class);
		query.add(Restrictions.and(Restrictions.idEq(addressId), Restrictions.eq("userId", userId)));
		return (Address) query.uniqueResult();
	}

	@Override
	public void setDefaultAddress(Integer addressId, Integer userId) {
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();  
		Criteria query = session.createCriteria(Address.class);
		query.add(Restrictions.and(Restrictions.idEq(addressId), Restrictions.eq("userId", userId)));
	    Address temp = (Address) query.uniqueResult();
	    
	    session.saveOrUpdate(temp);
	    
	    query = session.createCriteria(Address.class);
		query.add(Restrictions.and(Restrictions.idEq(addressId), Restrictions.eq("userId", userId), Restrictions.eq("isDefault", 1)));
	    temp = (Address) query.uniqueResult();
	    temp.setIsDefault(0);
	    
	    session.saveOrUpdate(temp);
	    session.flush();
	    session.getTransaction().commit();
	}

	@Override
	public void delUser(Integer userId, Integer companyId, List<Product> products) {
		
		
		Session session = sessionFactory.getCurrentSession(); 
//		String queryCompany = "select id, avatar, video from company where user_id=?";
//		String queryProducts = "select id from product where company_id=?";
//		
//		String queryPics = "select avatar, video from product where product_id=?";
//		String queryPics2 = "select avatar, video from company where company_id=?";
//
//		String queryPics3 = "select avatar, video from pic where company_id=? or product_id=?";
//
//		String queryPics4 = "select avatar, video from categoryrelationship where company_id=? or product_id=?";
//		String queryPics5 = "select avatar from param where product_id=?";
//		String queryPics6 = "select avatar, video from subproduct where product_id=?";
//		String queryPics7 = "select avatar from brand where companyId=?";

		
		String delBrand = "delete from brand where company_id=" + companyId;
		String delParam = "delete from param where product_id=?";
		String delRelationship = "delete from categoryrelationship where product_id=?";
		String delPicProduct = "delete from pic where product_id=?";
		String delPicCompany = "delete from pic where company_id=" + companyId;
		String delProduct = "delete from product where id=?";
		String delSubProduct = "delete from subproduct where productId=?";
		String delCompany = "delete from company where id=" + companyId;
		String delUser = "delete from user where id=" + userId;
		session.beginTransaction();  
		
		SQLQuery q = session.createSQLQuery(delPicCompany);
		q.executeUpdate();
		for(Product p : products){
			q = session.createSQLQuery(delPicProduct);
			q.setParameter(0, p.getId());
			q.executeUpdate();
			q = session.createSQLQuery(delParam);
			q.setParameter(0, p.getId());
			q.executeUpdate();
			q = session.createSQLQuery(delRelationship);
			q.setParameter(0, p.getId());
			q.executeUpdate();
			q = session.createSQLQuery(delSubProduct);
			q.setParameter(0, p.getId());
			q.executeUpdate();
			q = session.createSQLQuery(delProduct);
			q.setParameter(0, p.getId());
			q.executeUpdate();
		}
		q = session.createSQLQuery(delBrand);
		q.executeUpdate();
		q = session.createSQLQuery(delCompany);
		q.executeUpdate();
		q = session.createSQLQuery(delUser);
		q.executeUpdate();
//		Company c = (Company) q.uniqueResult();
//		if(c == null){
//			SQLQuery qq = session.createSQLQuery(delUser);
//			
//			qq.setParameter(0, userId);
//			q.executeUpdate();
//			session.flush();
//			session.getTransaction().commit();
//			return;
//		}
//		q = session.createSQLQuery(queryProducts);
//		q.setParameter(0, c.getId());
//		q.executeUpdate();
//		
//		List<Product> products = q.list();
//		
//		for(Product product : products){
//			
//			
//		}
//		
////		q = session.createQuery(hql);
//		q.executeUpdate();
	    session.flush();
	    session.getTransaction().commit();
	}

	
}
