package com.jiafang.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jiafang.dao.PicDao;
import com.jiafang.model.Pic;

@Repository
public class PicDaoImpl implements PicDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Pic> getProductPics(Integer type, Integer productId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
		query.add(Restrictions.and(Restrictions.eq("productId", productId),Restrictions.eq("type", type) )).addOrder(Order.asc("id"));
		List<Pic> pics = query.list();
		return pics;
	}

	@Override
	public List<Pic> getCompanyPics(Integer type, Integer companyId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
		query.add(Restrictions.and(Restrictions.eq("companyId", companyId) ,Restrictions.eq("type", type) )).addOrder(Order.asc("id"));
		List<Pic> pics = query.list();
		return pics;
	}

	@Override
	public Pic savePic(Pic pic){
		sessionFactory.getCurrentSession().save(pic);
		return pic;
	}
	
	@Override
	public Pic updatePic(Pic pic){
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();  
		Criteria query = session.createCriteria(Pic.class);
		query.add(Restrictions.eq("id", pic.getId()));
		Pic  temp = (Pic) query.uniqueResult();
		temp.setUrl(pic.getUrl());
		temp.setVideo(pic.getVideo());
	    session.flush();
	    session.saveOrUpdate(temp);
	    session.getTransaction().commit();
		return pic;
	}
	
	@Override
	public Pic deletePic(Pic pic){
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();
		String hql="delete Pic as p where p.id=?";
		Query query=session.createQuery(hql);
		query.setInteger(0,pic.getId());
		query.executeUpdate();
		session.getTransaction().commit();
		return pic;
	}

	@Override
	public void deletePicsByCompanyId(Integer companyId) {
		Session session = sessionFactory.getCurrentSession();  
		String hql="delete Pic where company_id=" + companyId;
		session.beginTransaction();  
		
		Query q = session.createQuery(hql);
		q.executeUpdate();
	    session.flush();
	    session.getTransaction().commit();
	}

	@Override
	public void deletePicsByProductId(Integer productId) {
		Session session = sessionFactory.getCurrentSession();  
		String hql="delete Pic where product_id=" + productId;
		session.beginTransaction();  
		
		Query q = session.createQuery(hql);
		q.executeUpdate();
	    session.flush();
	    session.getTransaction().commit();
	}


}
