package com.jiafang.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jiafang.dao.CompanyDao;
import com.jiafang.model.Brand;
import com.jiafang.model.Company;
import com.jiafang.model.Pic;
import com.jiafang.model.Product;
import com.jiafang.service.Page;
import com.jiafang.util.QiniuHelper;
import com.qiniu.util.StringUtils;

@Repository
public class CompanyDaoImpl implements CompanyDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public CompanyDaoImpl() {
	}
	
	@Override
	public List<Company> query(Page page) {
//		Criteria query = sessionFactory.getCurrentSession().createCriteria(Company.class).addOrder(Order.desc("weight"));
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("select p.* from Company p order by p.weight desc, IF(ISNULL(p.picUpdateTime),1,0) asc, p.avatar desc limit " + page.getIndex() + "," + page.getPageSize());
		query.addEntity(Company.class);
//		query.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
//		query.addOrder(Order.desc("weight"));
////		query.addOrder(Order.desc("id"));
//		query.addOrder(Order.asc("IF(ISNULL(picUpdateTime),1,0)"));
//		query.addOrder(Order.desc("avatar"));
		
		List<Company> companys = query.list();
		Criteria q = null;
		for(Company company : companys){
			q = sessionFactory.getCurrentSession().createCriteria(Pic.class);
			q.add(Restrictions.eq("companyId", company.getId()));
			company.setPics(q.list());
		}
		return companys;
	}
	
	@Override
	public List<Company> queryOrderById(Page page) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Company.class).addOrder(Order.desc("weight"));
		query.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
		query.addOrder(Order.desc("id"));
		
		List<Company> companys = query.list();
		for(Company company : companys){
			query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
			query.add(Restrictions.eq("companyId", company.getId()));
			company.setPics(query.list());
		}
		return companys;
	}

	@Override
	public Company saveCompany(Company company) {
		if (!StringUtils.isNullOrEmpty(company.getVideo()) && !company.getVideo().endsWith("_thumb")) {
			QiniuHelper.thumbVideo(company.getVideo());
			company.setVideo(company.getVideo() + "_thumb");
		} 
		
		sessionFactory.getCurrentSession().save(company);
		Brand brand = new Brand();
		brand.setCompanyId(company.getId());
		brand.setName(company.getBrand());
		brand.setAvatar(company.getAvatar());
		sessionFactory.getCurrentSession().save(brand);
		List<Brand> brands = new ArrayList<Brand>();
		brands.add(brand);
		company.setBrands(brands);
		return company;
	}
	
	@Override
	public List<Company> queryByName(Page page, String name) {
		SQLQuery sq = sessionFactory.getCurrentSession().createSQLQuery("select p.* from Company p where p.name like '%" + name + "%' order by p.weight desc, p.avatar desc");
		sq.addEntity(Company.class);
		sq.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
		List<Company> products = sq.list();
		return products;
	}

	@Override
	public Company queryById(Integer companyId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Company.class);
		query.add(Restrictions.eq("id", companyId));
		Company company = (Company) query.uniqueResult();
		if(company == null){
			return null;
		}
		query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
		query.add(Restrictions.eq("companyId", company.getId()));
		query.add(Restrictions.eq("type", 0));
		company.setPics(query.list());
		query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
		query.add(Restrictions.eq("companyId", company.getId()));
		query.add(Restrictions.eq("type", 3));
		company.setStorePics(query.list());
		query = sessionFactory.getCurrentSession().createCriteria(Brand.class);
		query.add(Restrictions.eq("companyId", company.getId()));
		List<Brand> brands = (List<Brand>) query.list();
		company.setBrands(brands);
		if(brands!=null&&brands.size()>0){
			company.setBrand(brands.get(0).getName());
		}
		return company;
	}
	
	
	
	@Override
	public Company updateCompany(Company company) {
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();  
		Criteria query = session.createCriteria(Company.class);
		query.add(Restrictions.eq("id", company.getId()));
	    Company  temp = (Company) query.uniqueResult();
	    temp.setName(company.getName());  
	    temp.setTel(company.getTel());
	    temp.setWebsite(company.getWebsite());
	    temp.setDesp(company.getDesp());
	    temp.setCulture(company.getCulture());
	    temp.setBrand(company.getBrand());
	    
	    query = session.createCriteria(Brand.class);
	    query.add(Restrictions.eq("companyId", company.getId()));
	    temp.setBrands(query.list());
	    if(temp.getBrands().size()==0){
	    	Brand brand = new Brand();
	    	brand.setCompanyId(company.getId());
	    	temp.getBrands().add(brand);
	    }
	    temp.getBrands().get(0).setName(company.getBrand());
	    temp.getBrands().get(0).setAvatar(company.getAvatar());
	    temp.setAvatar(company.getAvatar());
	    temp.setJoinCondition(company.getJoinCondition());
	    temp.setJoinProcess(company.getJoinProcess());
	    temp.setJoinSurport(company.getJoinSurport());
	    if(!StringUtils.isNullOrEmpty(company.getVideo()) && !company.getVideo().endsWith("_thumb")){
	    	QiniuHelper.thumbVideo(company.getVideo());
	    	temp.setVideo(company.getVideo() + "_thumb");
	    }else {
	    	temp.setVideo(company.getVideo());
	    }
	    
	    temp.setAddress(company.getAddress());
	    session.flush();
	    session.saveOrUpdate(temp);
	    
	    session.saveOrUpdate(temp.getBrands().get(0));
	    session.getTransaction().commit();
	    company.setBrands(temp.getBrands());
		//sessionFactory.getCurrentSession().update(company);
		return company;
	}
	
	
	@Override
	public Company queryByUserId(Integer userId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Company.class);
		query.add(Restrictions.eq("userId", userId));
		Company company = (Company) query.uniqueResult();
		if(company == null){
			return null;
		}
		query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
		query.add(Restrictions.eq("companyId", company.getId()));
		query.add(Restrictions.eq("type", 0));
		company.setPics(query.list());
		query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
		query.add(Restrictions.eq("companyId", company.getId()));
		query.add(Restrictions.eq("type", 3));
		company.setStorePics(query.list());
		query = sessionFactory.getCurrentSession().createCriteria(Brand.class);
		query.add(Restrictions.eq("companyId", company.getId()));
		List<Brand> brands = (List<Brand>) query.list();
		company.setBrands(brands);
		if(brands!=null&&brands.size()>0){
			company.setBrand(brands.get(0).getName());
		}
		
		return company;
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
		/*Pic temp = (Pic)session.get(Pic.class,pic.getId());
		session.delete(temp);*/
		String hql="delete Pic as p where p.id=?";
		Query query=session.createQuery(hql);
		query.setInteger(0,pic.getId());
		query.executeUpdate();
		session.getTransaction().commit();
		return pic;
	}

	@Override
	public Company querySimpleById(Integer companyId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Company.class);
		
		query.add(Restrictions.eq("id", companyId));
		
		Company company = (Company) query.uniqueResult();
		
		if(company == null){
			return null;
		}
		company.setJoinCondition("");
		company.setJoinProcess("");
		company.setJoinSurport("");
		company.setDesp("");
		return company;
	}

	@Override
	public void deleteCompany(Integer company) {
		Session session = sessionFactory.getCurrentSession();  
		String hql="delete Company where id=" + company;
		String hql2="delete Brand where company_id=" + company;
		session.beginTransaction();  
		
		Query q = session.createQuery(hql2);
		q.executeUpdate();
		q = session.createQuery(hql);
		q.executeUpdate();
	    session.flush();
	    session.getTransaction().commit();
	}

	@Override
	public List<Company> queryByNameOrderById(Page page, String name) {
		SQLQuery sq = sessionFactory.getCurrentSession().createSQLQuery("select p.* from Company p where p.name like '%" + name + "%' order by p.id desc");
		sq.addEntity(Company.class);
		sq.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
		List<Company> products = sq.list();
		return products;
	}

}
