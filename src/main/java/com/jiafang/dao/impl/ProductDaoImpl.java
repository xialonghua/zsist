package com.jiafang.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.jiafang.model.*;
import org.codehaus.plexus.util.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.druid.pool.DruidDataSource.CreateConnectionTask;
import com.jiafang.dao.ProductDao;
import com.jiafang.service.Page;
import com.jiafang.util.QiniuHelper;
import com.jiafang.util.StringUtil;

@Repository
public class ProductDaoImpl implements ProductDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public ProductDaoImpl() {
	}


	@Override
	public List<Product> queryByCatrgoryId(Page page, Integer categoryId, int platform) {
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("select ship.* from CategoryRelationship ship, Product p where ship.category_id=" + categoryId +  " and ship.company_id=p.id and (p.showPlatform = 2 or p.showPlatform=" + platform + ") order by ship.weight desc, ship.avatar desc limit " + page.getIndex() + ", " + page.getPageSize());
		query.addEntity(CategoryRelationship.class);
//		Criteria query = sessionFactory.getCurrentSession().createCriteria(CategoryRelationship.class);
//		query.add(Restrictions.eq("categoryId", categoryId));
//		query.addOrder(Order.desc("weight"));
////		query.addOrder(Order.desc("id"));
//		query.addOrder(Order.desc("avatar"));
//		query.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
		List<CategoryRelationship> ships = query.list();
		List<Product> products = new ArrayList<Product>();
		for(CategoryRelationship ship : ships){
			Criteria q = sessionFactory.getCurrentSession().createCriteria(Product.class);
			q.add(Restrictions.eq("id", ship.getProductId()));
			products.add((Product) q.uniqueResult());
		}

		return products;
	}

	@Override
	public List<Product> queryByCatrgoryId(Page page, Integer categoryId, Integer companyId, int platform) {
//		Criteria query = sessionFactory.getCurrentSession().createCriteria(CategoryRelationship.class);
//		query.add(Restrictions.and(Restrictions.eq("companyId", companyId), Restrictions.eq("categoryId", categoryId)));
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery("select ship.* from CategoryRelationship ship, Product p where ship.company_id=" + companyId + " and ship.category_id=" + categoryId +  " and ship.company_id=p.id and (p.showPlatform = 2 or p.showPlatform=" + platform + ") order by ship.weight desc, ship.avatar desc limit " + page.getIndex() + ", " + page.getPageSize());
		query.addEntity(CategoryRelationship.class);

//		query.addOrder(Order.desc("weight"));
////		query.addOrder(Order.desc("id"));
//		query.addOrder(Order.desc("avatar"));
//		query.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
		List<CategoryRelationship> ships = query.list();
		List<Product> products = new ArrayList<Product>();
		for(CategoryRelationship ship : ships){
			Criteria q = sessionFactory.getCurrentSession().createCriteria(Product.class);
			q.add(Restrictions.eq("id", ship.getProductId()));
			Product product = (Product) q.uniqueResult();
			products.add(product);
		}

		return products;
	}


	@Override
	public Product queryById(Integer productId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Product.class);
		query.add(Restrictions.eq("id", productId));
		
		Product product = (Product) query.uniqueResult();
		
		if(product == null){
			return null;
		}
		query = sessionFactory.getCurrentSession().createCriteria(Brand.class);
		query.add(Restrictions.eq("id", product.getBrandId()));
		Brand brand = (Brand) query.uniqueResult();
		if(brand != null){
			product.setBrand(brand.getName());
			product.setBrandId(brand.getId());
		}
		
		query = sessionFactory.getCurrentSession().createCriteria(SubProduct.class);
		query.add(Restrictions.eq("productId", product.getId()));
		
		List<SubProduct> subs = query.list();
		
		product.setSubProduct(subs);

		for (SubProduct sub : subs){

			query = sessionFactory.getCurrentSession().createCriteria(ProductSize.class);
			query.add(Restrictions.eq("subProductId", sub.getId()));
			sub.setProductSizes(query.list());
		}
		List<Pic> pics = new ArrayList<Pic>();
		for(SubProduct p : subs){
			Pic pic = new Pic();
			pic.setType(1);
			pic.setUrl(p.getAvatar());
			pic.setVideo(p.getVideo());
			pic.setName(p.getName());
			pics.add(pic);
		}
		product.setPics(pics);
		
		query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
		query.add(Restrictions.and(Restrictions.eq("productId", product.getId()), Restrictions.eq("type", 2)));
		product.setDescPics(query.list());
		query = sessionFactory.getCurrentSession().createCriteria(Param.class);
		query.add(Restrictions.eq("productId", product.getId()));
		product.setParams(query.list());
		return product;
	}

	@Override
	public Product queryById(Integer productId, int platform) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Product.class);
		query.add(Restrictions.and(Restrictions.eq("id", productId), Restrictions.or(Restrictions.eq("showPlatform", 2), Restrictions.eq("showPlatform", platform))));

		Product product = (Product) query.uniqueResult();

		if(product == null){
			return null;
		}
		query = sessionFactory.getCurrentSession().createCriteria(Brand.class);
		query.add(Restrictions.eq("id", product.getBrandId()));
		Brand brand = (Brand) query.uniqueResult();
		if(brand != null){
			product.setBrand(brand.getName());
			product.setBrandId(brand.getId());
		}

		query = sessionFactory.getCurrentSession().createCriteria(SubProduct.class);
		query.add(Restrictions.eq("productId", product.getId()));

		List<SubProduct> subs = query.list();

		product.setSubProduct(subs);

		for (SubProduct sub : subs){

			query = sessionFactory.getCurrentSession().createCriteria(ProductSize.class);
			query.add(Restrictions.eq("subProductId", sub.getId()));
			sub.setProductSizes(query.list());
		}
		List<Pic> pics = new ArrayList<Pic>();
		for(SubProduct p : subs){
			Pic pic = new Pic();
			pic.setType(1);
			pic.setUrl(p.getAvatar());
			pic.setVideo(p.getVideo());
			pic.setName(p.getName());
			pics.add(pic);
		}
		product.setPics(pics);

		query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
		query.add(Restrictions.and(Restrictions.eq("productId", product.getId()), Restrictions.eq("type", 2)));
		product.setDescPics(query.list());
		query = sessionFactory.getCurrentSession().createCriteria(Param.class);
		query.add(Restrictions.eq("productId", product.getId()));
		product.setParams(query.list());
		return product;
	}


	@Override
	public List<Product> queryByName(Page page, String name, int platform) {
		SQLQuery sq = sessionFactory.getCurrentSession().createSQLQuery("select p.* from Product p where (p.showPlatform = 2 or p.showPlatform=" + platform + ") and p.showPlatform=" + ALL_PLAT + " and p.name like '%" + name + "%' order by p.weight desc, p.avatar desc");
		sq.addEntity(Product.class);
		sq.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
		List<Product> products = sq.list();
//		Criteria query = null;
//		for(Product product : products){
//			query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
//			query.add(Restrictions.and(Restrictions.eq("productId", product.getId()), Restrictions.eq("type", 1)));
//			product.setPics(query.list());
//			query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
//			query.add(Restrictions.and(Restrictions.eq("productId", product.getId()), Restrictions.eq("type", 2)));
//			product.setDescPics(query.list());
//			query = sessionFactory.getCurrentSession().createCriteria(Param.class);
//			query.add(Restrictions.eq("productId", product.getId()));
//			product.setParams(query.list());
//		}
		return products;
	}


	@Override
	public List<Product> queryByNameAndCategoryId(Page page, String name, Integer categoryId, Integer companyId, int platform) {
		SQLQuery sq = sessionFactory.getCurrentSession().createSQLQuery("select p.* from Product p, CategoryRelationship ship where p.company_id=" + companyId + " and ship.category_id=" + categoryId + " and p.id=ship.product_id and (p.showPlatform = 2 or p.showPlatform=" + platform + ") and p.name like '%" + name + "%' order by p.weight desc, p.avatar desc");
		sq.addEntity(Product.class);
		sq.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
		List<Product> products = sq.list();
//		Criteria query = null;
//		for(Product product : products){
//			query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
//			query.add(Restrictions.and(Restrictions.eq("productId", product.getId()), Restrictions.eq("type", 1)));
//			product.setPics(query.list());
//			query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
//			query.add(Restrictions.and(Restrictions.eq("productId", product.getId()), Restrictions.eq("type", 2)));
//			product.setDescPics(query.list());
//			query = sessionFactory.getCurrentSession().createCriteria(Param.class);
//			query.add(Restrictions.eq("productId", product.getId()));
//			product.setParams(query.list());
//		}
		return products;
	}

	@Override
	public List<Product> queryByNameAndCategoryId(Page page, String name, Integer categoryId, int platform) {
		SQLQuery sq = sessionFactory.getCurrentSession().createSQLQuery("select p.* from Product p, CategoryRelationship ship where ship.category_id=" + categoryId + " and p.id=ship.product_id and (p.showPlatform = 2 or p.showPlatform=" + platform + ") and p.name like '%" + name + "%' order by p.weight desc, p.avatar desc");
		sq.addEntity(Product.class);
		sq.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
		List<Product> products = sq.list();
//		Criteria query = null;
//		for(Product product : products){
//			query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
//			query.add(Restrictions.and(Restrictions.eq("productId", product.getId()), Restrictions.eq("type", 1)));
//			product.setPics(query.list());
//			query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
//			query.add(Restrictions.and(Restrictions.eq("productId", product.getId()), Restrictions.eq("type", 2)));
//			product.setDescPics(query.list());
//			query = sessionFactory.getCurrentSession().createCriteria(Param.class);
//			query.add(Restrictions.eq("productId", product.getId()));
//			product.setParams(query.list());
//		}
		return products;
	}


	@Override
	public List<Product> queryByCompanyId(Page page, Integer companyId, int platform) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Product.class);
		if (platform == ALL_PLAT){
			query.add(Restrictions.or(Restrictions.eq("showPlatform", PUBLIC), Restrictions.eq("showPlatform", PRIVATE), Restrictions.eq("showPlatform", ALL_PLAT)));
		}else{
			query.add(Restrictions.or(Restrictions.eq("showPlatform", platform), Restrictions.eq("showPlatform", ALL_PLAT)));
		}
		query.add(Restrictions.eq("companyId", companyId));
//		query.addOrder(Order.desc("weight"));
		query.addOrder(Order.desc("id"));
//		query.addOrder(Order.desc("avatar"));
		if(page!=null){
			query.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
		}
		List<Product> products = query.list();
//		for(Product product : products){
//			query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
//			query.add(Restrictions.and(Restrictions.eq("productId", product.getId()), Restrictions.eq("type", 1)));
//			product.setPics(query.list());
//			query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
//			query.add(Restrictions.and(Restrictions.eq("productId", product.getId()), Restrictions.eq("type", 2)));
//			product.setDescPics(query.list());
//			query = sessionFactory.getCurrentSession().createCriteria(Param.class);
//			query.add(Restrictions.eq("productId", product.getId()));
//			product.setParams(query.list());
//		}
		return products;
	}

    @Override
    public ProductSize queryProductSize(Integer sizeId) {
        Criteria query = sessionFactory.getCurrentSession().createCriteria(ProductSize.class);
        query.add(Restrictions.idEq(sizeId));
        return (ProductSize) query.uniqueResult();
    }


    @Override
	public List<Product> queryByNameAndCompanyId(Page page, String name, Integer companyId, int platform) {
		SQLQuery sq = sessionFactory.getCurrentSession().createSQLQuery("select p.* from Product p where p.company_id=" + companyId + " and (p.showPlatform = 2 or p.showPlatform=" + platform + ") and p.name like '%" + name + "%' order by p.weight desc, p.avatar desc");
		sq.addEntity(Product.class);
		sq.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
		List<Product> products = sq.list();
//		Criteria query = null;
//		for(Product product : products){
//			query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
//			query.add(Restrictions.and(Restrictions.eq("productId", product.getId()), Restrictions.eq("type", 1)));
//			product.setPics(query.list());
//			query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
//			query.add(Restrictions.and(Restrictions.eq("productId", product.getId()), Restrictions.eq("type", 2)));
//			product.setDescPics(query.list());
//			query = sessionFactory.getCurrentSession().createCriteria(Param.class);
//			query.add(Restrictions.eq("productId", product.getId()));
//			product.setParams(query.list());
//		}
		return products;
	}
	
	
	
	
	
	@Override
	public Product saveProduct(Product product){
		product.setShowPlatform(1);
		if (!StringUtils.isEmpty(product.getVideo()) && !product.getVideo().endsWith("_thumb")) {
			QiniuHelper.thumbVideo(product.getVideo());
			product.setVideo(product.getVideo() + "_thumb");
		} 
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Brand.class);
		query.add(Restrictions.eq("companyId", product.getCompanyId()));
		
		List<Brand> brands = query.list();
		if(brands.size() > 0){
			product.setBrandId(brands.get(0).getId());
			product.setBrand(brands.get(0).getName());
		}
		sessionFactory.getCurrentSession().save(product);
		return product;
	}


	@Override
	public Product updateProduct(Product product) {
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();  
		Criteria query = session.createCriteria(Product.class);
		query.add(Restrictions.eq("id", product.getId()));
		Product  temp = (Product) query.uniqueResult();
		temp.setNum(product.getNum());
	    temp.setName(product.getName());
	    temp.setCount(product.getCount());
	    temp.setDescription(product.getDescription());
	    temp.setAvatar(product.getAvatar());
	    if(!StringUtils.isEmpty(product.getVideo()) && !product.getVideo().endsWith("_thumb")){
	    	QiniuHelper.thumbVideo(product.getVideo());
	    	temp.setVideo(product.getVideo() + "_thumb");
	    }else {
	    	temp.setVideo(product.getVideo());
	    }
	    temp.setDiscountPrice(product.getDiscountPrice());
	    temp.setPrice(product.getPrice());
	    temp.setSaleCount(product.getSaleCount());
	    session.flush();
	    session.saveOrUpdate(temp);
	    session.getTransaction().commit();
		return product;
	}
	
	@Override
	public Param getParamById(Integer paramId){
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Param.class);
		query.add(Restrictions.eq("id", paramId));
		Param param = (Param) query.uniqueResult();
		return param;
	}
	
	@Override
	public Param saveParam(Param param){
		sessionFactory.getCurrentSession().save(param);
		return param;
	}
	
	@Override
	public Product deleteProduct(Product product){
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();
		String hql="delete Product as p where p.id=?";
		Query query=session.createQuery(hql);
		query.setInteger(0,product.getId());
		query.executeUpdate();
		session.getTransaction().commit();
		return product;
	}
	
	@Override
	public Param updateParam(Param param){
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();  
		Criteria query = session.createCriteria(Param.class);
		query.add(Restrictions.eq("id", param.getId()));
		Param  temp = (Param) query.uniqueResult();
		temp.setName(param.getName());
		temp.setTitle(param.getTitle());
		temp.setValue(param.getValue());
	    session.flush();
	    session.saveOrUpdate(temp);
	    session.getTransaction().commit();
		return param;
	}
	
	@Override
	public Param deleteParam(Param param){
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();
		String hql="delete Param as p where p.id=?";
		Query query=session.createQuery(hql);
		query.setInteger(0,param.getId());
		query.executeUpdate();
		session.getTransaction().commit();
		return param;
	}

	@Override
	public ProductSize saveProductSize(ProductSize param) {
		sessionFactory.getCurrentSession().save(param);
		return param;
	}

	@Override
	public ProductSize updateProductSize(ProductSize param) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Criteria query = session.createCriteria(ProductSize.class);
		query.add(Restrictions.eq("id", param.getId()));
		ProductSize  temp = (ProductSize) query.uniqueResult();
		temp.setName(param.getName());
		temp.setAvatar(param.getAvatar());
		temp.setDiscountPrice(param.getDiscountPrice());
		temp.setCount(param.getCount());
		temp.setDescription(param.getDescription());
		temp.setNum(param.getNum());
		temp.setVideo(param.getVideo());
		temp.setPrice(param.getPrice());
		temp.setSaleCount(param.getSaleCount());
		session.flush();
		session.saveOrUpdate(temp);
		session.getTransaction().commit();
		return param;
	}

	@Override
	public void updateProductPlatform(Product product) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		String hql="update Product set showPlatform=? where id=?";
		Query query=session.createSQLQuery(hql);
		query.setInteger(0, product.getShowPlatform());
		query.setInteger(1, product.getId());
		query.executeUpdate();
		session.getTransaction().commit();
	}

	@Override
	public Pic savePic(Pic pic){
		sessionFactory.getCurrentSession().save(pic);
		return pic;
	}
	
	@Override
	public void savePics(List<Pic> pics){
		Session session = sessionFactory.getCurrentSession();
		for(Pic obj : pics){
			session.save(obj);
		}
		session.flush();
		session.clear();
	}
	
	@Override
	public Pic updatePic(Pic pic){
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();  
		Criteria query = session.createCriteria(Pic.class);
		query.add(Restrictions.eq("id", pic.getId()));
		Pic  temp = (Pic) query.uniqueResult();
		if(StringUtils.isNotEmpty(pic.getUrl())){
			temp.setUrl(pic.getUrl());
		}
		if(StringUtils.isNotEmpty(pic.getVideo())){
			temp.setVideo(pic.getVideo());
		}
		if(StringUtils.isNotEmpty(pic.getName())){
			temp.setName(pic.getName());
		}
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
		session.flush();
		session.delete(temp);*/
		String hql="delete Pic as p where p.id=?";
		Query query=session.createQuery(hql);
		query.setInteger(0,pic.getId());
		query.executeUpdate();
		session.getTransaction().commit();
		return pic;
	}
	
	@Override
	public Product queryProductPics(Integer productId,Integer type) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
		query.add(Restrictions.and(Restrictions.eq("productId", productId), Restrictions.eq("type", type)));
		List<Pic> pics = query.list();
		Product product = new Product();
		if(type==1){
			product.setPics(pics);
		}
		if(type==2){
			product.setDescPics(pics);
		}
		return product;
	}

	@Override
	public List<Product> queryProducts(Page page) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Product.class);
		query.addOrder(Order.desc("weight"));
		query.addOrder(Order.desc("id"));
		query.addOrder(Order.desc("avatar"));
		if(page!=null){
			query.setMaxResults(page.getPageSize()).setFirstResult(page.getIndex());
		}
		List<Product> products = query.list();
		return products;
	}
	
	@Override
	public Integer getProdunctNumberCount(String num){
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Product.class);
		query.add(Restrictions.eq("num", num));
		query.setProjection(Projections.rowCount());  
		Long count = (Long)query.uniqueResult();  
		return count.intValue();
	}


	@Override
	public SubProduct saveSubProduct(SubProduct sub) {
		sessionFactory.getCurrentSession().save(sub);
		return sub;
	}


	@Override
	public SubProduct updateSubProduct(SubProduct sub) {
		/*Session session = sessionFactory.getCurrentSession();  
	    session.saveOrUpdate(sub);
		return sub;*/
		
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();  
		Criteria query = session.createCriteria(SubProduct.class);
		query.add(Restrictions.eq("id", sub.getId()));
		SubProduct  temp = (SubProduct) query.uniqueResult();
		if(!org.apache.commons.lang3.StringUtils.isEmpty(sub.getAvatar())){
			temp.setAvatar(sub.getAvatar());
		}
		if(!org.apache.commons.lang3.StringUtils.isEmpty(sub.getName())){
			temp.setName(sub.getName());
		}
		if(sub.getPrice()!=null){
			temp.setPrice(sub.getPrice());
		}
		if(sub.getDiscountPrice()!=null){
			temp.setDiscountPrice(sub.getDiscountPrice());
		}
		if(sub.getSaleCount()!=null){
			temp.setSaleCount(sub.getSaleCount());
		}
		if(sub.getCount()!=null){
			temp.setCount(sub.getCount());
		}
	    session.flush();
	    session.saveOrUpdate(temp);
	    session.getTransaction().commit();
		return sub;
	}


	@Override
	public SubProduct deleteSubProduct(SubProduct sub) {
		/*sessionFactory.getCurrentSession().delete(sub);
		return sub;*/
		
		Session session = sessionFactory.getCurrentSession();  
		session.beginTransaction();
        String hql="delete SubProduct as sub where sub.id=?";
        Query query=session.createQuery(hql);
        query.setInteger(0,sub.getId());
        query.executeUpdate();

        hql="delete ProductSize as sub where sub.subProductId=?";
        query=session.createQuery(hql);
        query.setInteger(0,sub.getId());
        query.executeUpdate();
		session.getTransaction().commit();
		return sub;
	}

    @Override
    public ProductSize deleteProductSize(ProductSize sub) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        String hql="delete ProductSize as sub where sub.id=?";
        Query query=session.createQuery(hql);
        query.setInteger(0,sub.getId());
        query.executeUpdate();
        session.getTransaction().commit();
        return sub;
    }


    @Override
	public SubProduct getSubProduct(Integer subId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(SubProduct.class);
        query.add(Restrictions.idEq(subId));
        SubProduct sub = (SubProduct)query.uniqueResult();

        query = sessionFactory.getCurrentSession().createCriteria(ProductSize.class);
        query.add(Restrictions.eq("subProductId", subId));
        sub.setProductSizes(query.list());

        return sub;
	}


	@Override
	public List<Pic> queryAllPicsByType(Integer type) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Pic.class);
		query.add(Restrictions.eq("type", type));
		return query.list();
	}

	@Override
	public Product querySimpleById(Integer productId) {
		Criteria query = sessionFactory.getCurrentSession().createCriteria(Product.class);
		query.add(Restrictions.eq("id", productId));
		
		Product product = (Product) query.uniqueResult();
		
		if(product == null){
			return null;
		}
		query = sessionFactory.getCurrentSession().createCriteria(Brand.class);
		query.add(Restrictions.eq("id", product.getBrandId()));
		Brand brand = (Brand) query.uniqueResult();
		if(brand != null){
			product.setBrand(brand.getName());
			product.setBrandId(brand.getId());
		}
		
		return product;
	}


	@Override
	public void delProductsByCompanyId(Integer companyId) {
		Session session = sessionFactory.getCurrentSession();  
		String hql="delete Product where company_id=" + companyId;
		session.beginTransaction();  
		
		Query q = session.createQuery(hql);
		q.executeUpdate();
	    session.flush();
	    session.getTransaction().commit();
	}

	
	@Override
	public void delParamsByProductId(Integer productId) {
		Session session = sessionFactory.getCurrentSession();  
		String hql="delete Param where product_id=" + productId;
		session.beginTransaction();  
		
		Query q = session.createQuery(hql);
		q.executeUpdate();
	    session.flush();
	    session.getTransaction().commit();
	}
	
	
}
