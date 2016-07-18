package com.jiafang.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jiafang.dao.CategoryDao;
import com.jiafang.model.Category;
import com.jiafang.model.CategoryRelationship;
import com.jiafang.service.Page;

@Repository
public class CategoryDaoImpl implements CategoryDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public CategoryDaoImpl() {
	}

	@Override
	public List<Category> query(Page page) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Category.class);
		query.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
		return query.list();
	}

    @Override
    public List<CategoryRelationship> queryRelationshipByCatrgoryId(Page page, Integer categoryId) {
        Criteria query = sessionFactory.getCurrentSession().createCriteria(CategoryRelationship.class);
        query.add(Restrictions.eq("categoryId", categoryId));
        query.addOrder(Order.desc("weight"));
        query.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
        return query.list();
    }

    @Override
    public List<CategoryRelationship> queryRelationshipByCatrgoryId(Page page, Integer categoryId, Integer companyId) {
        Criteria query = sessionFactory.getCurrentSession().createCriteria(CategoryRelationship.class);
        query.add(Restrictions.and(Restrictions.eq("categoryId", categoryId), Restrictions.eq("companyId", companyId)));
        query.addOrder(Order.desc("weight"));
        query.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
        return query.list();
    }
	
	@Override
	public CategoryRelationship queryRelationshipByShipId(Integer shipId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(CategoryRelationship.class);
		query.add(Restrictions.eq("id", shipId));
		return (CategoryRelationship) query.uniqueResult();
	}

	@Override
	public List<Category> queryByType(Integer type, Page page) {
		Session session = sessionFactory.getCurrentSession();
		Criteria query = session.createCriteria(Category.class);
		if(type!=null){
			query.add(Restrictions.eq("type", type));
		}
		query.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
		return query.list();
	}

	@Override
	public List<Category> queryByType(Integer type, Page page, Integer companyId) {
		//TODO 暂时返回一样的
        Session session = sessionFactory.getCurrentSession();
		Criteria query = session.createCriteria(Category.class);
		if(type != null){
			query.add(Restrictions.eq("type", type));
		}
		query.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
		return query.list();
	}
	
	@Override
	public Category queryById(int id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria query = session.createCriteria(Category.class);
		query.add(Restrictions.eq("id", id));
		return (Category) query.uniqueResult();
	}
	
	
	
	
	@Override
	public Category saveCategory(Category category){
		sessionFactory.getCurrentSession().save(category);
		return category;
	}
	
	@Override
	public Category updateCategory(Category category){
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();  
		Criteria query = session.createCriteria(Category.class);
		query.add(Restrictions.eq("id", category.getId()));
		Category  temp = (Category) query.uniqueResult();
		temp.setType(category.getType());
		temp.setName(category.getName());
	    if(StringUtils.isNotEmpty(category.getAvatar())){
	    	temp.setAvatar(category.getAvatar());
	    }
	    if(StringUtils.isNoneEmpty(category.getVideo())){
	    	temp.setVideo(category.getVideo());
	    }
	    session.flush();
	    session.saveOrUpdate(temp);
	    session.getTransaction().commit();
		return temp;
	}
	
	@Override
	public Category deleteCategory(Category category){
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();  
		String hql="delete Category as c where c.id=?";
		Query query=session.createQuery(hql);
		query.setInteger(0,category.getId());
		query.executeUpdate();
	    session.getTransaction().commit();
		return category;
	}

	@Override
	public CategoryRelationship saveRelationship(CategoryRelationship ship) {
		sessionFactory.getCurrentSession().save(ship);
		return ship;
	}
	
	@Override
	public CategoryRelationship updateRelationship(CategoryRelationship ship) {
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();  
		Criteria query = session.createCriteria(CategoryRelationship.class);
		query.add(Restrictions.eq("id", ship.getId()));
		CategoryRelationship  temp = (CategoryRelationship) query.uniqueResult();
	    temp.setCategoryId(ship.getCategoryId());
	    if(StringUtils.isNotEmpty(ship.getAvatar())){
	    	temp.setAvatar(ship.getAvatar());
	    }
	    if(StringUtils.isNoneEmpty(ship.getVideo())){
	    	temp.setVideo(ship.getVideo());
	    }
	    session.flush();
	    session.saveOrUpdate(temp);
	    session.getTransaction().commit();
		return temp;
	}
	
	@Override
	public void deleteRelationship(CategoryRelationship ship) {
		//sessionFactory.getCurrentSession().delete(ship);
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();
		String hql="delete CategoryRelationship as c where c.id=?";
		Query query=session.createQuery(hql);
		query.setInteger(0,ship.getId());
		query.executeUpdate();
		session.getTransaction().commit();
	}
	
	@Override
	public void deleteRelationshipByCategoryId(int categoryId) {
		//sessionFactory.getCurrentSession().delete(ship);
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();
		String hql="delete CategoryRelationship as c where c.categoryId=?";
		Query query=session.createQuery(hql);
		query.setInteger(0,categoryId);
		query.executeUpdate();
		session.getTransaction().commit();
	}
	
	@Override
	public void deleteRelationshipByCategoryIdProductId(Integer categoryId,Integer productId) {
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();
		String hql="delete CategoryRelationship as c where c.categoryId=? and c.productId=?";
		Query query=session.createQuery(hql);
		query.setInteger(0,categoryId);
		query.setInteger(1,productId);
		query.executeUpdate();
		session.getTransaction().commit();
	}

	@Override
	public Integer queryShipIdByCompanyCategory(int companyId,int categoryId) {
		SQLQuery sq = sessionFactory.getCurrentSession().createSQLQuery("select id from CategoryRelationship ship where ship.company_id=" + companyId +" and ship.category_id="+ categoryId);
		Integer shipId = (Integer) sq.uniqueResult();
		return shipId;
	}
	
	@Override
	public Integer queryShipIdByProductCategory(int productId,int categoryId) {
		SQLQuery sq = sessionFactory.getCurrentSession().createSQLQuery("select id from CategoryRelationship ship where ship.product_id=" + productId +" and ship.category_id="+ categoryId);
		Integer shipId = (Integer) sq.uniqueResult();
		return shipId;
	}

	@Override
	public List<CategoryRelationship> queryRelationshipsByProductId(Integer productId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(CategoryRelationship.class);
		query.add(Restrictions.eq("productId", productId));
		return query.list();
	}

	@Override
	public List<CategoryRelationship> queryRelationshipsByCompanyId(Integer companyId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(CategoryRelationship.class);
		query.add(Restrictions.eq("companyId", companyId));
		return query.list();
	}

	@Override
	public Integer queryMaxWeight() {
		SQLQuery sq = sessionFactory.getCurrentSession().createSQLQuery("select max(weight) weight from CategoryRelationship ship ");
		Integer maxWeight = (Integer) sq.uniqueResult();
		return maxWeight;
	}

	@Override
	public CategoryRelationship updateRelationshipWeight(
			CategoryRelationship ship) {
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();  
		Criteria query = session.createCriteria(CategoryRelationship.class);
		query.add(Restrictions.eq("id", ship.getId()));
		CategoryRelationship  temp = (CategoryRelationship) query.uniqueResult();
	    temp.setWeight(ship.getWeight());
	    session.flush();
	    session.saveOrUpdate(temp);
	    session.getTransaction().commit();
		return temp;
	}

	@Override
	public void delCategorysByProductId(Integer productId) {
		Session session = sessionFactory.getCurrentSession();  
		String hql="delete CategoryRelationship where product_id=" + productId;
		session.beginTransaction();  
		
		Query q = session.createQuery(hql);
		q.executeUpdate();
	    session.flush();
	    session.getTransaction().commit();
	}

}
